package me.legend.JCraft.Source.Network.PacketHandlers.Inventory;

import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerSetSlotPacket;
import me.legend.JCraft.Source.Bot.Bot;

public class SetSlotPacket {

    public SetSlotPacket(ServerSetSlotPacket packet, Bot bot){
        if(packet.getWindowId() == 0) bot.getInventory().setSlot(packet.getSlot(), packet.getItem());
    }

}
