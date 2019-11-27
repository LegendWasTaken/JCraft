package me.legend.JCraft.API.JBot;

import me.legend.JCraft.API.JBot.JConsole.JConsole;
import me.legend.JCraft.API.JWorld.JWorld;
import me.legend.JCraft.Source.Bot.Bot;

public class JBot {

    private String email, username, password, host;
    private Integer port;
    private Bot bot;

    private JWorld world;
    private JConsole console;

    public JBot(String username, String host, Integer port){
        this(username, null, host, port);
    }

    public JBot(String email, String password, String host, Integer port){
        console = new JConsole(this, true, true);
        this.email = password == null ? null : email;


        if(password != null){
            bot = new Bot(email, password, host, port);
        } else {
            bot = new Bot(email, host, port);
        }
        this.password = password;
        this.host = host;
        this.port = port;

        this.world = new JWorld(bot.getWorld(), this.bot);
    }

    public JWorld getWorld(){ return this.world; }
    public String getName(){ return this.username; }

    public void connect(){
        bot.connect();
    }

}
