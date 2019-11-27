package me.legend.JCraft;

import me.legend.JCraft.API.JBot.EventHandler;
import me.legend.JCraft.API.JBot.JBot;
import me.legend.JCraft.API.Utils.BlockLocation;

public class Main {

    public static void main(String[] args) {



        JBot bot = new JBot("JCraft", "127.0.0.1", 25565);
        TestHandler test = new TestHandler(bot);
        bot.registerEventHandler(test);
        bot.connect();


    }

}

class TestHandler extends EventHandler {

    private JBot bot;

    public TestHandler(JBot bot) {
        super(bot);
        this.bot = bot;
    }

    @Override
    public void LoginEvent(){
        System.out.println("Bot has logged in");
    }

    @Override
    public void ChatEvent(String message){
        System.out.println("I have a new chat message");
    }

}