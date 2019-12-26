package me.legend.JCraft.Source.Events;

import me.legend.JCraft.Source.Bot.Bot;
import me.legend.JCraft.Source.Entity.Entity;

public interface NewEntityEvent {
    void newEntityEvent(Bot bot, Entity entity);
}
