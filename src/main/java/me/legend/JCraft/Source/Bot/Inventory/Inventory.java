package me.legend.JCraft.Source.Bot.Inventory;

import com.github.steveice10.mc.protocol.data.game.ItemStack;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientChangeHeldItemPacket;
import me.legend.JCraft.Source.BotHandler.BotHandler;
import me.legend.JCraft.Source.Exceptions.InvalidSessionException;
import org.spacehq.packetlib.Session;

public class Inventory {

    private Session session;
    private ItemStack[] items;
    private int heldSlot;

    public Inventory(Session session, ItemStack[] i) {
        this.session = session;
        items = i;
    }

    public ItemStack getItem(int i) { return items[i]; }

    public Inventory setHeldItem(int i, boolean serverSide) {
        if(!serverSide) {
            session.send(new ClientChangeHeldItemPacket(i));
            /*
            * For some reason who ever coded the held item update code
            * is completely fucking brain dead, and only updates.
            * When you actually send a "entity move packet"
            * So fuck you to the retard who coded this at Mojang, and all of the fucking times I thought my code wasn't fucking working.
            * you fucking piece of retarded shit
            * you are a stale ham sandwich of a human
            * */
            try{
                BotHandler.getBotBySession(session).location.look(session);
            } catch (InvalidSessionException ignored) {}

        }
        heldSlot = i;
        return this;
    }

    public ItemStack getHeldItem() {
        return items[heldSlot + 36 /* Magic value is magic */];
    }
}
