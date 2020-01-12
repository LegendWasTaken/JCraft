package me.legend.JCraft.Source.Network.PacketHandlers.ServerConnection;

import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.legend.JCraft.Source.Bot.Bot;

public class ChatPacket {
    public ChatPacket (ServerChatPacket packet, Bot bot){
        String chatmsg = packet.getMessage().getFullText();
        bot.fireChatEvent(chatmsg, bot);

        //if(!chatmsg.equals("chat.type.announcement")) bot.getConsole().log(chatmsg); else bot.getConsole().debug("This message type is not yet supported by JCraft");

    }
}
