package me.legend.JCraft.Source.Network.PacketHandlers.Inventory;

import com.github.steveice10.mc.protocol.data.game.ItemStack;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerWindowItemsPacket;
import me.legend.JCraft.Source.Bot.Bot;
import me.legend.JCraft.Source.Bot.Inventory.Inventory;

public class WindowItemsPacket {

    public WindowItemsPacket(ServerWindowItemsPacket packet, Bot bot){
        ItemStack[] items = packet.getItems();
        if(packet.getWindowId() == 0) {
            bot.setInventory(new Inventory(items));
        }
    }

}
