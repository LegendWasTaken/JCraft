package me.legend.JCraft.Source.Network.PacketHandlers.Entities;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnMobPacket;
import me.legend.JCraft.Source.Bot.Bot;
import me.legend.JCraft.Source.Entity.Entity;

public class SpawnMobPacket {
    public SpawnMobPacket(ServerSpawnMobPacket packet, Bot bot){
        bot.getWorld().addEntity(
          new Entity(
            packet.getEntityId(),
            packet.getType().name(),
            packet.getX(), packet.getY(), packet.getZ(),
            packet.getYaw(),
            packet.getPitch()));

    }
}
