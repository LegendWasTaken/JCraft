package me.legend.JCraft.Source.Network.PacketHandlers.WorldData;

import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerMultiBlockChangePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerMultiChunkDataPacket;
import me.legend.JCraft.Source.Bot.Bot;
import me.legend.JCraft.Source.Bot.World.ChunkColumn;
import me.legend.JCraft.Source.Exceptions.InvalidSectionSizeException;

public class MultiChunkPacket {

    public MultiChunkPacket(ServerMultiChunkDataPacket packet, Bot bot){
        for (int i = 0; i < packet.getColumns(); i++) {
            int chunkX = packet.getX(i);
            int chunkZ = packet.getZ(i);
            try {
                bot.world.addChunkColumn( new ChunkColumn(chunkX, chunkZ, packet.getChunks(i)));
            } catch (InvalidSectionSizeException ignored) { }
        }
    }

}
