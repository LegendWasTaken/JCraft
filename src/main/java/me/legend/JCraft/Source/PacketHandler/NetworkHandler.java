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
                    bot.world.placeBlock(new Vector3d(posX, posY, posZ), new Vector3d(-1, 0, 0), e.getSession(), bot.inventory.getHeldItem());
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
}
