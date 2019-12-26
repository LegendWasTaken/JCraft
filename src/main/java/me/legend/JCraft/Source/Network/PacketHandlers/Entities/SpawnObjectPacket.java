package me.legend.JCraft.Source.Network.PacketHandlers.Entities;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnObjectPacket;
import me.legend.JCraft.Source.Bot.Bot;
import me.legend.JCraft.Source.Entity.Entity;

public class SpawnObjectPacket {
    public SpawnObjectPacket(ServerSpawnObjectPacket packet, Bot bot){
        int entityID = packet.getEntityId();
        double x = packet.getX(), y = packet.getY(), z = packet.getZ();
        String name = packet.getType().name();
        bot.getWorld().addEntity(new Entity(entityID, name, x, y, z));
    }
}
