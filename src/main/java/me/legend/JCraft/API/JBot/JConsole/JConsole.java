package me.legend.JCraft.API.JBot.JConsole;

import me.legend.JCraft.API.JBot.JBot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JConsole {

    private JBot bot;
    private Boolean log, debug;

    public JConsole(JBot bot, Boolean log, Boolean debug){
        this.bot = bot;
        this.log = log;
        this.debug = debug;
    }

    public void log(String message){
        if(this.log) System.out.println("[" + (new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()))) + " INFO - " + this.bot.getName   () + "] " + message);
    }

    public void debug(String message){
        if(this.debug) System.out.println("[" + (new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()))) + " DEBUG - " + this.bot.getName() +"] " + message);
    }

    public void setLogging(Boolean in){ this.log = in; }
    public void setDebugging(Boolean in){ this.debug = in; }



}
