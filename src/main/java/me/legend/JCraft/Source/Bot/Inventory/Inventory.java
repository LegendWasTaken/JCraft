package me.legend.JCraft.Source.Bot.Inventory;

import com.github.steveice10.mc.protocol.data.game.ItemStack;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientChangeHeldItemPacket;
import me.legend.JCraft.Source.Bot.Bot;

public class Inventory {

    private ItemStack[] items;
    private int heldSlot;

    public Inventory(ItemStack[] i) { items = i; }

    public void setSlot(int slot, ItemStack item){ items[slot] = item; }
    public ItemStack getItem(int i) { return items[i]; }
    public void setHeldItem(int i) { heldSlot = i; }
    public ItemStack getHeldItem() { return items[heldSlot + 36]; }

    public void setItems(ItemStack[] items){this.items = items; }
}
