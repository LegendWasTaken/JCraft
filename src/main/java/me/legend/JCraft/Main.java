package me.legend.JCraft;

import me.legend.JCraft.Source.Bot.Bot;
import me.legend.JCraft.Source.BotHandler.BotHandler;
import me.legend.JCraft.Source.Exceptions.InvalidIDException;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Integer> ids = new ArrayList<Integer>();

        for(int i=0; i<1; i-=-1){
            ids.add(BotHandler.addBot(new Bot("Cute", "127.0.0.1", 25565)));
        }

        for(Integer botID : ids){
            try{
                BotHandler.getBotByID(botID).session.connect();
            } catch (InvalidIDException ignored){}
        }
    }

}