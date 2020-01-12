package me.legend.JCraft.Source.Network.PacketHandlers.ServerConnection;

import com.github.steveice10.mc.protocol.packet.ingame.server.ServerDisconnectPacket;
import me.legend.JCraft.Source.Bot.Bot;

public class DisconnectPacket {
    public DisconnectPacket(ServerDisconnectPacket packet, Bot bot) {
        bot.fireDisconnectEvent(bot, packet.getReason().getFullText());
    }
}
