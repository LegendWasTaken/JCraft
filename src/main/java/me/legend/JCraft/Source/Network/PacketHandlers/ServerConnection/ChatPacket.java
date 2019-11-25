package me.legend.JCraft.Source.Network.PacketHandlers.ServerConnection;

import com.github.steveice10.mc.protocol.data.game.values.MessageType;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import me.legend.JCraft.Source.Bot.Bot;
import me.legend.JCraft.Source.Bot.World.Block.Block;

public class ChatPacket {
    public ChatPacket (ServerChatPacket packet, Bot bot){
        String chatmsg = packet.getMessage().getFullText();
        // hacked way of testing for this, but it works so....
        if(!chatmsg.equals("chat.type.announcement")) bot.getConsole().log(chatmsg); else bot.getConsole().debug("This message type is not yet supported by JCraft");

    }
}
