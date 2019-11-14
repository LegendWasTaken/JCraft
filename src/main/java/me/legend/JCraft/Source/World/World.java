package me.legend.JCraft.Source.World;

import com.github.steveice10.mc.protocol.data.game.Chunk;
import com.github.steveice10.mc.protocol.data.game.ItemStack;
import com.github.steveice10.mc.protocol.data.game.Position;
import com.github.steveice10.mc.protocol.data.game.ShortArray3d;
import com.github.steveice10.mc.protocol.data.game.values.Face;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPlaceBlockPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerRotationPacket;
import me.legend.JCraft.Source.Bot;
import me.legend.JCraft.Source.Util.Vec3;
import me.legend.JCraft.Source.Util.Vector3d;
import me.legend.JCraft.Source.World.Block.Block;
import me.legend.JCraft.Source.Entity.Entity;
import me.legend.JCraft.Source.Exceptions.InvalidChunkSectionException;
import org.spacehq.packetlib.Session;

import java.util.ArrayList;
import java.util.List;

public class World {

    public List<Entity> entities = new ArrayList<Entity>();

    public List<ChunkColumn> chunks = new ArrayList<ChunkColumn>();

    public void addChunkColumn(ChunkColumn chunkColumn){
        chunks.add(chunkColumn);
    }

    public World(){ }

    public List<ChunkColumn> getChunks() { return chunks; }
    public List<Entity> getEntities() { return entities; }

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
        block.setType(
          chunkColumn.sections[y >> 4].getBlocks().getBlock(relativeX, relativeY, relativeZ));
        block.setData(chunkColumn.sections[y >> 4].getBlocks().getData(relativeX, relativeY, relativeZ));
        return block;
    }

    public void setBlock(int x, int y, int z, Block block){
        ShortArray3d blocks = getChunk(x, y, z).getBlocks();
        int relativeX = x - ((x >> 4) * 16), relativeY = y - ((y >> 4) * 16), relativeZ = z - ((z >> 4) * 16);
        blocks.setBlock(relativeX, relativeY, relativeZ, block.type);
        blocks.setData(relativeX, relativeY, relativeZ, block.data);
    }

    public void placeBlock(Vector3d blockLocation, Vector3d offset, Session session, ItemStack held) {
        Face face = Face.WEST;
        float cursorX = 0, cursorY = 0, cursorZ = 0;
        // West = Place block south
        // East =
        // South =
        // North =
        // Bottom =
        // Top =
        if( offset.x + offset.y > 1 ||
            offset.y + offset.z > 1 ||
            offset.x + offset.z > 1) // Just a check to make sure that it isn't an invalid offset
            return;
        if(offset.x + offset.z == 0){
            // Up and down

        }
        switch (face) {
            case TOP:
                // cursorX = 8;
                // cursorZ = 8;
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
        session.send(new ClientPlayerPlaceBlockPacket(Vector3d.toPosition(blockLocation), Face.WEST, held, 8, 8, 8));
    }

    public void placeBlock(Face face, Vector3d referenceBlock, ItemStack held, Session session){
        float cx = 0; // stArtInG it WiTh 0 Is RedUnDeNt
        float cy = 0;// fucking good luck gamer
        float cz = 0;
        System.out.println("Final block location X: " + referenceBlock.x + ", Y: " + referenceBlock.y + ", Z: " + referenceBlock.z );
        session.send(new ClientPlayerPlaceBlockPacket(Vector3d.toPosition(referenceBlock), face, held, 8, 8, 8));
    }
}
