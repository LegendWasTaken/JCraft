package me.legend.JCraft.Source.Network.PacketHandlers.WorldData;

import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerChunkDataPacket;
import me.legend.JCraft.Source.Bot.Bot;
import me.legend.JCraft.Source.World.ChunkColumn;
import me.legend.JCraft.Source.Exceptions.InvalidSectionSizeException;

public class SingleChunkPacket {

    public SingleChunkPacket(ServerChunkDataPacket packet, Bot bot){
        try {
            bot.getWorld().addChunkColumn(new ChunkColumn(packet.getX(), packet.getZ(), packet.getChunks()));
        } catch (InvalidSectionSizeException e){
            bot.debug("There was invalid chunk packet sent from the server [ X: " + packet.getX() + ", Z: " + packet.getZ() + "]");
        }
    }

}
