package me.legend.JCraft.Source.Events;

import me.legend.JCraft.Source.Bot.Bot;
import me.legend.JCraft.Source.Entity.Player;

public interface NewPlayerEvent {
    void newPlayerEvent(Bot bot, Player player);
}
