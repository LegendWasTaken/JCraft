package me.legend.JCraft.Source.Network.PacketHandlers.Inventory;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerChangeHeldItemPacket;
import me.legend.JCraft.Source.Bot.Bot;

public class ChangeHeldItemPacket {
    public ChangeHeldItemPacket(ServerChangeHeldItemPacket packet, Bot bot){
        if(bot.getInventory() != null) bot.getInventory().setHeldItem(packet.getSlot() - 36, bot);
    }
}
