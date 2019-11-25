package me.legend.JCraft.Source.Network.PacketHandlers.ServerConnection;

import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import me.legend.JCraft.Source.Bot.Bot;
import me.legend.JCraft.Source.Bot.World.Block.Block;

public class ChatPacket {
    public ChatPacket (ServerChatPacket packet, Bot bot){
        String chatmsg = packet.getMessage().getFullText();
        if(chatmsg.contains("getblock")){
            int posX;
            int posY;
            int posZ;
            try{
                posX = Integer.parseInt(chatmsg.split(",")[1]);
                posY = Integer.parseInt(chatmsg.split(",")[2]);
                posZ = Integer.parseInt(chatmsg.split(",")[3]);
                bot.session.send(new ClientChatPacket("[JCraft] Attempting to get the block at X: " + posX + ", Y: " + posY + ", Z: " + posZ));
                Block block = bot.world.getBlock(posX, posY, posZ);
                bot.session.send(new ClientChatPacket("[JCraft] Found block at position! Block ID: " + block.type + ", Block Data: " + block.data));

            } catch (Exception ex){
                ex.printStackTrace();
                bot.session.send(new ClientChatPacket("[JCraft] There was an error getting specified block"));
            }
            // bot.console.log("Received message: " + packet.getMessage().getText());
        }

    }
}
