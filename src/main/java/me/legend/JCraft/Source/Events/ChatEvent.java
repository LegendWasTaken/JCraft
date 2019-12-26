package me.legend.JCraft.Source.Events;

import me.legend.JCraft.Source.Bot.Bot;

public interface ChatEvent {

    void chatEvent(String message, Bot bot);

}
