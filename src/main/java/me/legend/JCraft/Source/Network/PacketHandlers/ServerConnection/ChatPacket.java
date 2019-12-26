package me.legend.JCraft.Source.Network.PacketHandlers.ServerConnection;

import com.github.steveice10.mc.protocol.data.game.values.MessageType;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.legend.JCraft.Source.Bot.Bot;
import me.legend.JCraft.Source.Bot.World.Block.Block;

public class ChatPacket {
    public ChatPacket (ServerChatPacket packet, Bot bot){
        String chatmsg = packet.getMessage().getFullText();
        // hacked way of testing for this, but it works so....
        if (chatmsg.equals("chat.type.announcement")) {
                JsonObject JSON = (JsonObject) packet.getMessage().toJson();
                JsonArray ARRAY = (JsonArray) JSON.get("with");
                JsonObject nameObj = (JsonObject) ARRAY.get(0);
                JsonObject textObj = (JsonObject) ARRAY.get(1);
                JsonArray textArray = (JsonArray) textObj.get("extra");
                JsonObject textString = (JsonObject) textArray.get(0);
            //This is hardcoded, but it doesn't matter until there is an issue with another chat type. (With different JSON syntax)
            if(bot.getChatToConsole()) bot.getConsole().log("[" + nameObj.get("text").getAsString() + "]" + " " + textString.get("text").getAsString());
        } else {
            if(bot.getChatToConsole()) bot.getConsole().log(chatmsg);
        }
        bot.fireChatEvent(chatmsg, bot);

        //if(!chatmsg.equals("chat.type.announcement")) bot.getConsole().log(chatmsg); else bot.getConsole().debug("This message type is not yet supported by JCraft");

    }
}
