package me.legend.JCraft.Source.Events;

import me.legend.JCraft.Source.Bot.Bot;

public interface DisconnectEvent {
    void disconnectEvent(Bot bot, String reason);
}
