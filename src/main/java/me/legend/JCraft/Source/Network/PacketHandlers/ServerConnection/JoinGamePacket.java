package me.legend.JCraft.Source.Network.PacketHandlers.ServerConnection;

import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerMultiChunkDataPacket;
import me.legend.JCraft.Source.Bot.Bot;
import me.legend.JCraft.Source.Bot.World.ChunkColumn;
import me.legend.JCraft.Source.Exceptions.InvalidSectionSizeException;

public class JoinGamePacket {
    public JoinGamePacket(ServerJoinGamePacket packet, Bot bot){
        bot.fireLoginEvent(bot);
    }
}
