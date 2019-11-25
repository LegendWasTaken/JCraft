package me.legend.JCraft.Source.Network.PacketHandlers.Location;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import me.legend.JCraft.Source.Bot.Bot;
import me.legend.JCraft.Source.Util.EntityPosition;

public class PlayerPositionRoationPacket {
    public PlayerPositionRoationPacket(ServerPlayerPositionRotationPacket packet, Bot bot){
        bot.setLocation(new EntityPosition(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch()));
    }
}
