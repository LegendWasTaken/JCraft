package me.legend.JCraft.Source.Network.PacketHandlers.ServerConnection;

import com.github.steveice10.mc.protocol.data.game.values.ClientRequest;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientRequestPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import me.legend.JCraft.Source.Bot.Bot;

public class JoinGamePacket {
    public JoinGamePacket(ServerJoinGamePacket packet, Bot bot) {
        bot.session.send(new ClientRequestPacket(ClientRequest.RESPAWN));
        bot.fireLoginEvent(bot);
    }
}
