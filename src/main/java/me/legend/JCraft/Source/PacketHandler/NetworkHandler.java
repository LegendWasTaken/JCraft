package me.legend.JCraft.Source.PacketHandler;

import com.github.steveice10.mc.protocol.data.game.Position;
import com.github.steveice10.mc.protocol.data.game.values.Face;
import com.github.steveice10.mc.protocol.data.game.values.world.block.BlockChangeRecord;
import com.github.steveice10.mc.protocol.data.message.Message;
import com.github.steveice10.mc.protocol.data.message.TranslationMessage;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerChangeHeldItemPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerWindowItemsPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerBlockChangePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerChunkDataPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerMultiBlockChangePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerMultiChunkDataPacket;
import me.legend.JCraft.Source.Exceptions.InvalidSessionException;
import me.legend.JCraft.Source.Inventory.Inventory;
import me.legend.JCraft.Source.Util.EntityPosition;
import me.legend.JCraft.Source.Util.Vector3d;
import me.legend.JCraft.Source.World.Block.Block;
import me.legend.JCraft.Source.Bot;
import me.legend.JCraft.Source.BotHandler.BotHandler;
import me.legend.JCraft.Source.Exceptions.InvalidSectionSizeException;
import me.legend.JCraft.Source.World.ChunkColumn;
import me.legend.JCraft.Source.World.World;
import org.spacehq.packetlib.event.session.DisconnectedEvent;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.SessionAdapter;

public class NetworkHandler extends SessionAdapter {

    private static Bot bot;

    public NetworkHandler(Bot bot){
        NetworkHandler.bot = bot;
    }

    /* Dear Future me, I'm very very sorry for having put the weight of reformting this on your shoulder.
    *  Even though you may not believe it. I am actually sorry, and I wish you the best of luck.
    *  Much love, past you <3 xoxo
    * */

    @Override
    public void packetReceived(PacketReceivedEvent e) {
        if (e.getPacket() instanceof ServerChunkDataPacket) {
            Bot bot = null;
            try {
                bot = BotHandler.getBotBySession(e.getSession());
            } catch (InvalidSessionException ignored) { }
            if(bot != null){
                try {
                    ServerChunkDataPacket p = e.getPacket();
                    bot.world.addChunkColumn(new ChunkColumn(p.getX(), p.getZ(), p.getChunks()));
                } catch (InvalidSectionSizeException ignored) { }
            }

        } else if (e.getPacket() instanceof ServerMultiChunkDataPacket) {
            Bot bot = null;
            try {
                bot = BotHandler.getBotBySession(e.getSession());
            } catch (InvalidSessionException ignored) { }
            if(bot != null){
                for (int i = 0; i < e.<ServerMultiChunkDataPacket>getPacket().getColumns(); i++) {
                    int chunkX = e.<ServerMultiChunkDataPacket>getPacket().getX(i);
                    int chunkZ = e.<ServerMultiChunkDataPacket>getPacket().getZ(i);
                    try {
                        bot.world.addChunkColumn( new ChunkColumn(chunkX, chunkZ, e.<ServerMultiChunkDataPacket>getPacket().getChunks(i)));
                    } catch (InvalidSectionSizeException ignored) { }
                }
            }

        } else if (e.getPacket() instanceof ServerJoinGamePacket) {
            e.getSession().send(new ClientChatPacket("[JCraft] Logged on the server!"));

        } else if (e.getPacket() instanceof ServerChatPacket) {
            Message message = e.<ServerChatPacket>getPacket().getMessage();
//            System.out.println("Message: " + message);

            // Temporary for debugging in game
            if(message.getFullText().contains("getchunks")){
                e.getSession().send(new ClientChatPacket("[JCraft] Sending a list of all of the currently loaded chunk columns [ Check Console ]"));
                try {
                    for(ChunkColumn chunkColumn : BotHandler.getBotBySession(e.getSession()).world.chunks){
                        System.out.println("[DEBUG] [CHUNKS] Chunk @ X: " + chunkColumn.chunkX + ", Z: " + chunkColumn.chunkZ);
                    }
                } catch (InvalidSessionException ex) {
                    ex.printStackTrace();
                }
            } else if(message.getFullText().contains("getblock")){
                String chatmsg = message.getFullText();
                int posX;
                int posY;
                int posZ;
                try{
                    posX = Integer.parseInt(chatmsg.split(",")[1]);
                    posY = Integer.parseInt(chatmsg.split(",")[2]);
                    posZ = Integer.parseInt(chatmsg.split(",")[3]);
                    e.getSession().send(new ClientChatPacket("[JCraft] Attempting to get the block at X: " + posX + ", Y: " + posY + ", Z: " + posZ));
                    Block block = BotHandler.getBotBySession(e.getSession()).world.getBlock(posX, posY, posZ);
                    e.getSession().send(new ClientChatPacket("[JCraft] Found block at position! Block ID: " + block.type + ", Block Data: " + block.data));

                } catch (Exception ex){
                    ex.printStackTrace();
                    e.getSession().send(new ClientChatPacket("[JCraft] There was an error getting specified block"));
                }

            } else if(message.getFullText().contains("setslot")){
                String msg = message.getFullText();
                e.getSession().send(new ClientChatPacket("[JCraft] Setting the item slot"));
                try{
                    int slot = Integer.parseInt(msg.split(",")[1]);
                    if(slot < 0 || slot > 8){
                        e.getSession().send(new ClientChatPacket("[JCraft] Invalid item slot"));
                    } else {
                        BotHandler.getBotBySession(e.getSession()).inventory.setHeldItem(slot, false);
                    }
                } catch (Exception ignored) {}
            } else if(message.getFullText().contains("placeblock")){
                String chatmsg = message.getFullText();
                int posX;
                int posY;
                int posZ;
                try{
                    Bot bot = BotHandler.getBotBySession(e.getSession());
                    posX = Integer.parseInt(chatmsg.split(",")[1]);
                    posY = Integer.parseInt(chatmsg.split(",")[2]);
                    posZ = Integer.parseInt(chatmsg.split(",")[3]);
                    Block block = bot.world.getBlock(posX, posY, posZ);
                    e.getSession().send(new ClientPlayerPositionRotationPacket(false, bot.getX(), bot.getY(), bot.getZ(), bot.getYaw(), bot.getPitch()));
                    bot.world.placeBlock(new Vector3d(posX, posY, posZ), new Vector3d(posX, posY, posZ), e.getSession(), bot.inventory.getHeldItem());
                    e.getSession().send(new ClientChatPacket("[JCraft] Attempting to place the block"));

                } catch (Exception ex){
                    ex.printStackTrace();
                    e.getSession().send(new ClientChatPacket("[JCraft] There was an unknown error"));
                }
            }
        } else if (e.getPacket() instanceof ServerPlayerPositionRotationPacket){
            ServerPlayerPositionRotationPacket packet = e.getPacket();
            try{
                BotHandler.getBotBySession(e.getSession()).location = new EntityPosition(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch());
            } catch (InvalidSessionException ignored) {}
        } else if (e.getPacket() instanceof ServerBlockChangePacket){
            ServerBlockChangePacket packet = e.getPacket();
            World world = null;
            try {
                world = BotHandler.getBotBySession(e.getSession()).world;
            } catch (InvalidSessionException ignored) {}
            if(world != null){
                Position target = packet.getRecord().getPosition();
                Block block = new Block(packet.getRecord().getData(), packet.getRecord().getId());
                world.setBlock(target.getX(), target.getY(), target.getZ(), block);
            }
        } else if (e.getPacket() instanceof ServerMultiBlockChangePacket){
            ServerMultiBlockChangePacket packet = e.getPacket();
            World world = null;
            try {
                world = BotHandler.getBotBySession(e.getSession()).world;
            } catch (InvalidSessionException ignored) {}
            if(world != null){
                BlockChangeRecord[] records = packet.getRecords();
                for(int i = 0; i<records.length; i++){
                    BlockChangeRecord record = records[i];
                    Position target = record.getPosition();
                    Block block = new Block(record.getId(), record.getData());
                    world.setBlock(target.getX(), target.getY(), target.getZ(), block);
                }
            }
        } else if (e.getPacket() instanceof ServerChangeHeldItemPacket){
            try {
                Inventory inventory = BotHandler.getBotBySession(e.getSession()).inventory;
                if(inventory != null){
                    inventory.setHeldItem(e.<ServerChangeHeldItemPacket>getPacket().getSlot(), true);
                }
            } catch (InvalidSessionException ignored) { }
        } else if (e.getPacket() instanceof ServerWindowItemsPacket){
            ServerWindowItemsPacket packet = e.getPacket();
            try{
                BotHandler.getBotBySession(e.getSession()).inventory = new Inventory(e.getSession(), packet.getItems());
            } catch (InvalidSessionException ignored) {}

        }
    }

    @Override
    public void disconnected(DisconnectedEvent e) {
        System.out.println("Disconnected: " + Message.fromString(e.getReason()).getFullText());
        if(e.getCause() != null) {
            e.getCause().printStackTrace();
        }
    }


    /*
    @Override public void packetReceived(PacketReceivedEvent event) {
        if (event.getPacket() instanceof ServerJoinGamePacket) {
            event.getSession().send(new ClientChatPacket("PhaseBot has joined the game."));
            PhaseBot.getBot().entityId = event.<ServerJoinGamePacket> getPacket().getEntityId();
        }
        else if (event.getPacket() instanceof ServerPlayerPositionRotationPacket) {
            PhaseBot.getBot().pos.x = event.<ServerPlayerPositionRotationPacket> getPacket().getX();
            PhaseBot.getBot().pos.y = event.<ServerPlayerPositionRotationPacket> getPacket().getY();
            PhaseBot.getBot().pos.z = event.<ServerPlayerPositionRotationPacket> getPacket().getZ();
            PhaseBot.getBot().pitch = event.<ServerPlayerPositionRotationPacket> getPacket().getPitch();
            PhaseBot.getBot().yaw = event.<ServerPlayerPositionRotationPacket> getPacket().getYaw();
            PhaseBot.getConsole().println("My Position: " + PhaseBot.getBot().pos.x + "," + PhaseBot.getBot().pos.y
              + "," + PhaseBot.getBot().pos.z);
            event.getSession().send(new ClientPlayerPositionRotationPacket(false, PhaseBot.getBot().pos.x,
              PhaseBot.getBot().pos.y, PhaseBot.getBot().pos.z, PhaseBot.getBot().pitch, PhaseBot.getBot().yaw));
        }
        else if (event.getPacket() instanceof ServerSpawnObjectPacket) {
            int entityId = event.<ServerSpawnObjectPacket> getPacket().getEntityId();
            double x = event.<ServerSpawnObjectPacket> getPacket().getX();
            double y = event.<ServerSpawnObjectPacket> getPacket().getY();
            double z = event.<ServerSpawnObjectPacket> getPacket().getZ();
            String type = event.<ServerSpawnObjectPacket> getPacket().getType().toString();
            new Entity(entityId, type, x, y, z);
        }
        else if (event.getPacket() instanceof ServerSpawnPlayerPacket) {
            int entityId = event.<ServerSpawnPlayerPacket> getPacket().getEntityId();
            double x = event.<ServerSpawnPlayerPacket> getPacket().getX();
            double y = event.<ServerSpawnPlayerPacket> getPacket().getY();
            double z = event.<ServerSpawnPlayerPacket> getPacket().getZ();
            UUID u = event.<ServerSpawnPlayerPacket> getPacket().getUUID();
            new Player(entityId, u, x, y, z);
        }
        else if (event.getPacket() instanceof ServerSpawnMobPacket) {
            int entityId = event.<ServerSpawnMobPacket> getPacket().getEntityId();
            double x = event.<ServerSpawnMobPacket> getPacket().getX();
            double y = event.<ServerSpawnMobPacket> getPacket().getY();
            double z = event.<ServerSpawnMobPacket> getPacket().getZ();
            String type = event.<ServerSpawnMobPacket> getPacket().getType().toString();
            new Entity(entityId, type, x, y, z);
        }
        else if (event.getPacket() instanceof ServerDestroyEntitiesPacket) {
            for (int i : event.<ServerDestroyEntitiesPacket> getPacket().getEntityIds()) {
                if (Entity.getEntities().containsKey(i)) {
                    Entity e = Entity.byId(i);
                    if (e instanceof Player) Player.players.remove((Player) e);
                    e.remove();
                }
            }
        }
        else if (event.getPacket() instanceof ServerUpdateHealthPacket) {
            if (event.<ServerUpdateHealthPacket> getPacket().getHealth() <= 0) {
                event.getSession().send(new ClientRequestPacket(ClientRequest.RESPAWN));
            }
        }
        else if (event.getPacket() instanceof ServerEntityPositionRotationPacket) {
            ServerEntityMovementPacket p = event.<ServerEntityPositionRotationPacket> getPacket();
            int id = p.getEntityId();
            Entity e = Entity.byId(id);
            e.x += p.getMovementX();
            e.y += p.getMovementY();
            e.z += p.getMovementZ();
            e.pitch = p.getPitch();
            e.yaw = p.getYaw();
        }
        else if (event.getPacket() instanceof ServerEntityTeleportPacket) {
            ServerEntityTeleportPacket p = event.<ServerEntityTeleportPacket> getPacket();
            int id = p.getEntityId();
            Entity e = Entity.byId(id);
            e.x = p.getX();
            e.y = p.getY();
            e.z = p.getZ();
            e.pitch = p.getPitch();
            e.yaw = p.getYaw();
        }
        else if (event.getPacket() instanceof ServerMultiBlockChangePacket) {
            ServerMultiBlockChangePacket packet = event.<ServerMultiBlockChangePacket> getPacket();
            for (BlockChangeRecord r : packet.getRecords()) {
                Position p = r.getPosition();
                int id = r.getId();
                ChunkColumn.setBlock(p, id >> 4);
                PhaseBot.getBot().setInteruptMoveAlong(true);
            }
        }
        else if (event.getPacket() instanceof ServerBlockChangePacket) {
            Position p = event.<ServerBlockChangePacket> getPacket().getRecord().getPosition();
            int id = event.<ServerBlockChangePacket> getPacket().getRecord().getId();
            ChunkColumn.setBlock(p, id >> 4);
            PhaseBot.getBot().setInteruptMoveAlong(true);
        }
        else if (event.getPacket() instanceof ServerWindowItemsPacket) {
            ServerWindowItemsPacket p = event.<ServerWindowItemsPacket> getPacket();
            if (p.getWindowId() == 0) {// Player's inventory
                PhaseBot.getBot().setInventory(new Inventory(p.getItems()));
                PhaseBot.getConsole().println("Inventory recieved!");
            }
        }
        else if (event.getPacket() instanceof ServerSetSlotPacket) {
            ServerSetSlotPacket p = event.<ServerSetSlotPacket> getPacket();
            if (p.getWindowId() == 0) {// Player's Inventory
                PhaseBot.getBot().getInventory().setItem(p.getSlot(), p.getItem());
                // if(p.getItem() != null)
                // PhaseBot.getConsole().println("Inventory Slot #" +
                // p.getSlot() + " change to " + p.getItem().getId());
            }
        }
        else if (event.getPacket() instanceof ServerChatPacket) {
            Message message = event.<ServerChatPacket> getPacket().getMessage();
            try {
                String t = message.getFullText();
                PhaseBot.getConsole().addChatMessage(t);
                ChatMessage m = new ChatMessage(t);
                if (!m.isCommand()) return;
                if (!Arrays.asList(PhaseBot.getOwners()).contains(m.getSender())) {
                    event.getSession().send(new ClientChatPacket("/msg " + m.getSender() + " You are not my master!"));
                    return;
                }
                if (m.getMessage().startsWith(PhaseBot.getPrefix())) {
                    String command = m.getMessage().replaceFirst(PhaseBot.getPrefix(), "");
                    command = Script.replaceVariables(command);
                    PhaseBot.getBot().runCommand(command, true);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override public void disconnected(DisconnectedEvent event) {
        PhaseBot.getConsole().println("Disconnected: " + Message.fromString(event.getReason()).getFullText(), false,
          Color.RED);
    }*/
}
