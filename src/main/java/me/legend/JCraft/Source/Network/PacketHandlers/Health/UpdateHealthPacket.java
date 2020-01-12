package me.legend.JCraft.Source.Network.PacketHandlers.Health;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerUpdateHealthPacket;
import me.legend.JCraft.Source.Bot.Bot;

public class UpdateHealthPacket {
    public UpdateHealthPacket(ServerUpdateHealthPacket packet, Bot bot){
        System.out.println("New Health: " + packet.getHealth() + ", Bot Health: " + bot.getHealth());
        Float newHealth = packet.getHealth();
        Float botHealth = bot.getHealth();
        if(newHealth < botHealth) bot.fireDamageEvent(bot, botHealth - newHealth);
        if(newHealth > botHealth) bot.fireHealEvent(bot, newHealth - botHealth);
        if(newHealth <= 0.0F) bot.fireDeathEvent(bot);
    }
}
