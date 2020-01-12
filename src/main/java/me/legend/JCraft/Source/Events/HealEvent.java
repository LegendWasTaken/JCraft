package me.legend.JCraft.Source.Events;

import me.legend.JCraft.Source.Bot.Bot;

public interface HealEvent {
    void healEvent(Bot bot, float healed);
}
