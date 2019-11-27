package me.legend.JCraft.API.JBot;

import me.legend.JCraft.Source.Bot.World.ChunkColumn;
import me.legend.JCraft.Source.Util.EntityPosition;

public class EventHandler {

    private JBot bot;

    public EventHandler(JBot bot){ this.bot = bot; }

    // Low level events
    public void ReceiveChunkColumn(ChunkColumn chunk){

    }

    // Server Events
    public void LoginEvent(){}

    // Chat Events
    public void ChatEvent(String message){}

    // Movement Events
    public void PlayerChangeCameraEvent(EntityPosition position){}
    public void PlayerChangeLocationEvent(){}

}
