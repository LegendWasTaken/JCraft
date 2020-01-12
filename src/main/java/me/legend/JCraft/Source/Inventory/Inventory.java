package me.legend.JCraft.Source.Inventory;

import com.github.steveice10.mc.protocol.data.game.ItemStack;
import com.github.steveice10.mc.protocol.data.game.Position;
import com.github.steveice10.mc.protocol.data.game.values.Face;
import com.github.steveice10.mc.protocol.data.game.values.entity.player.PlayerAction;
import com.github.steveice10.mc.protocol.data.game.values.window.ClickItemParam;
import com.github.steveice10.mc.protocol.data.game.values.window.CreativeGrabParam;
import com.github.steveice10.mc.protocol.data.game.values.window.WindowAction;
import com.github.steveice10.mc.protocol.data.game.values.window.WindowActionParam;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientChangeHeldItemPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerActionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerMovementPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientCreativeInventoryActionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientWindowActionPacket;
import me.legend.JCraft.Source.Bot.Bot;

public class Inventory {

    private ItemStack[] items;
    private int heldSlot;
    private static int actionNumber = 0;

    public Inventory(ItemStack[] i) { items = i; }

    public void setSlot(int slot, ItemStack item){
        items[slot] = item;
    }
    public ItemStack getItem(int i) { return items[i]; }

    public void updateHeldItem(Bot bot){
        /* Need to figure out how to update the currently held item to all of the users*/
    }

    public void setHeldItem(int i, Bot bot) {
        heldSlot = i;
        updateHeldItem(bot);
    }
    public ItemStack getHeldItem() {
        return items[heldSlot + 36];
    }

    public void creativeSetItem(Bot bot, int slot, int itemid, int amt){
        ItemStack targetItem = new ItemStack(itemid, amt);
        bot.session.send(new ClientCreativeInventoryActionPacket(slot, targetItem));
        updateHeldItem(bot);
    }

    public void creativeSetItem(Bot bot, int slot, int itemid){
        this.creativeSetItem(bot, slot, itemid, 1);
    }

    public void dropAllItem(Bot bot, boolean fast){
        for(int i=0; i<9; i++){
            // Drop items in the hotbar
            setHeldItem(i, bot);
            bot.session.send(new ClientPlayerActionPacket(PlayerAction.DROP_ITEM_STACK, bot.getLocation().toPosition(), Face.BOTTOM));
            if(!fast) sleep();
        }
        for(int i=9; i<45; i++){
            int wID = 0;
            WindowActionParam param = ClickItemParam.LEFT_CLICK;
            WindowAction act = WindowAction.DROP_ITEM;
            ItemStack dropped = null;
            actionNumber++;
            bot.session.send(new ClientWindowActionPacket(wID, actionNumber, i, dropped, act, param));
            if(!fast) sleep();
        }
        updateHeldItem(bot);
    }

    public int getHeldSlot(){ return this.heldSlot; }

    public void setItems(ItemStack[] items){this.items = items; }

    private void sleep(){
        try {
            Thread.sleep(150L);
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }
}
