package me.legend.JCraft.Source.Bot.BotConsole;

import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import me.legend.JCraft.Source.Bot.Bot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BotConsole {

    private Bot bot;
    private Boolean log, debug, ingame;

    public BotConsole(Bot bot, Boolean log, Boolean debug){ this.bot = bot; this.log = log; this.debug = debug; }

    public void log(String message){
        if(this.log) System.out.println("[" + (new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()))) + " INFO - " + this.bot.getName() + "] " + message);
    }

    public void debug(String message){
        if(this.debug) System.out.println("[" + (new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()))) + " DEBUG] " + message);
    }

}
