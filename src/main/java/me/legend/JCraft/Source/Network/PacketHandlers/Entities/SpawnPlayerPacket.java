package me.legend.JCraft.Source.Network.PacketHandlers.Entities;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnPlayerPacket;
import me.legend.JCraft.Source.Bot.Bot;
import me.legend.JCraft.Source.Entity.Player;

public class SpawnPlayerPacket {
    public SpawnPlayerPacket(ServerSpawnPlayerPacket packet, Bot bot){
        System.out.println(packet.getUUID());
        int entityID = packet.getEntityId();
        double x = packet.getX(), y = packet.getY(), z = packet.getZ();
        String name = "Player";
        Player player = new Player( name, entityID, x, y, z);
        bot.fireNewPlayerEvent(bot, player);
        bot.getWorld().addPlayer(player);
    }
}
