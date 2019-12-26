package me.legend.JCraft.Source.Network.PacketHandlers.Entities;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerDestroyEntitiesPacket;
import me.legend.JCraft.Source.Bot.Bot;

public class DestroyEntitiesPacket {
    public DestroyEntitiesPacket(ServerDestroyEntitiesPacket packet, Bot bot){
        int[] targets = packet.getEntityIds();
        for(int i=0; i<targets.length; i++){
            bot.getWorld().removeEntity(targets[i]);
        }
    }
}
