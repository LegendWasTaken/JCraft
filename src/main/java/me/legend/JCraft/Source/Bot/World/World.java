package me.legend.JCraft.Source.Bot.World;

import com.github.steveice10.mc.protocol.data.game.Chunk;
import com.github.steveice10.mc.protocol.data.game.ItemStack;
import com.github.steveice10.mc.protocol.data.game.ShortArray3d;
import com.github.steveice10.mc.protocol.data.game.values.Face;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPlaceBlockPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import me.legend.JCraft.Source.Bot.Bot;
import me.legend.JCraft.Source.Entity.Player;
import me.legend.JCraft.Source.Util.EntityPosition;
import me.legend.JCraft.Source.Util.Vector3d;
import me.legend.JCraft.Source.Bot.World.Block.Block;
import me.legend.JCraft.Source.Entity.Entity;
import me.legend.JCraft.Source.Exceptions.InvalidChunkSectionException;
import org.spacehq.packetlib.Session;

import java.util.ArrayList;
import java.util.List;

public class World {

    private List<Entity> entities = new ArrayList<Entity>();
    private List<ChunkColumn> chunks = new ArrayList<ChunkColumn>();

    public void addChunkColumn(ChunkColumn chunkColumn){
        chunks.add(chunkColumn);
    }

    public World(){ }

    public List<ChunkColumn> getChunks() { return chunks; }
    public List<Entity> getEntities() { return entities; }
    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<Player>();
        for(int i=0; i<this.entities.size(); i++){
            Entity cur = this.entities.get(i);
            if(cur instanceof Player) players.add((Player) cur);
        }
        return players;
    }

    public void addEntity(Entity entity){ this.entities.add(entity); }
    public void addPlayer(Player player){ this.entities.add(player); }
    public void removeEntity(Integer id){
        for(int i=0; i<this.entities.size(); i++){
            if(this.entities.get(i).getEntityID().equals(id)) {
                this.entities.remove(i);
                break;
            }
        }
    }

    public ChunkColumn getChunk(int x, int z){
        int chunkX = x >> 4;
        int chunkZ = z >> 4;
        for(ChunkColumn chunk : chunks){
            if(chunk.chunkX == chunkX && chunk.chunkZ == chunkZ) return chunk;
        }
        return null;
    }

    public Chunk getChunk(int x, int y, int z){
        int chunkX = x >> 4;
        int chunkY = y >> 4;
        int chunkZ = z >> 4;
        for(ChunkColumn chunk : chunks){
            if(chunk.chunkX == chunkX && chunk.chunkZ == chunkZ) {
                try{
                    return chunk.getSection(chunkY);
                } catch (InvalidChunkSectionException e){
                    return null;
                }

            }
        }
        return null;
    }

    public Block getBlock(int x, int y, int z){
        ChunkColumn chunkColumn = getChunk(x, z);
        Block block = new Block();
        int relativeX = x - ((x >> 4) * 16);
        int relativeY = y - ((y >> 4) * 16);
        int relativeZ = z - ((z >> 4) * 16);
        block.setType(chunkColumn.sections[y >> 4].getBlocks().getBlock(relativeX, relativeY, relativeZ));
        block.setData(chunkColumn.sections[y >> 4].getBlocks().getData(relativeX, relativeY, relativeZ));
        block.location = new Vector3d(x, y, z);
        return block;
    }

    public void setBlock(int x, int y, int z, Block block){
        ShortArray3d blocks = getChunk(x, y, z).getBlocks();
        int relativeX = x - ((x >> 4) * 16), relativeY = y - ((y >> 4) * 16), relativeZ = z - ((z >> 4) * 16);
        blocks.setBlock(relativeX, relativeY, relativeZ, block.type);
        blocks.setData(relativeX, relativeY, relativeZ, block.data);
    }

    public void placeBlock(Bot bot, Vector3d blockLocation, Vector3d offset) {
        Face face = Face.INVALID;
        offset.floor();
        if(!(offset.x + offset.y + offset.z > 2)) {
            face =
              // X Axis check
              offset.x == 1 ? Face.SOUTH : offset.x == - 1 ? Face.NORTH :
                // Y Axis check
                offset.y == 1 ? Face.TOP : offset.y == - 1 ? Face.BOTTOM :
                  // Z Axis Check
                  offset.z == 1 ? Face.WEST : offset.z == - 1 ? Face.EAST : Face.INVALID;
        }

        float cursorX = 0, cursorY = 0, cursorZ = 0;
        switch (face) {
            case TOP:
                cursorX = 0;
                cursorY = 0;
                cursorZ = 0;
                break;
            case BOTTOM:
                cursorX = 8;
                cursorY = 16;
                cursorZ = 8;
                break;
            case NORTH:
                cursorX = 8;
                cursorY = 8;
                break;
            case SOUTH:
                cursorX = 8;
                cursorY = 8;
                cursorZ = 16;
                break;
            case EAST:
                cursorX = 16;
                cursorY = 8;
                cursorZ = 8;
                break;
            case WEST:
                cursorX = 8;
                cursorZ = 0;
                break;
            case INVALID:
            default:
                return;
        }
        bot.session.send(new ClientPlayerPositionRotationPacket(true, bot.getX(), bot.getY(), bot.getZ(), bot.getYaw(), bot.getPitch()));
        bot.session.send(new ClientPlayerPlaceBlockPacket(Vector3d.toPosition(blockLocation.floor()), face, bot.getInventory().getHeldItem(), cursorX, cursorY, cursorZ));
    }
}
