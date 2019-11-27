package me.legend.JCraft;

import me.legend.JCraft.API.JBot.JBot;
import me.legend.JCraft.API.Utils.BlockLocation;

public class Main {

    public static void main(String[] args) {

        JBot bot = new JBot("JCraft", "127.0.0.1", 25565);
        bot.connect();
    }

}