package me.legend.JCraft;

import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import me.legend.JCraft.Source.Bot.Bot;
import me.legend.JCraft.Source.Events.ChatEvent;
import me.legend.JCraft.Source.Events.LoginEvent;
import me.legend.JCraft.Source.Util.Vector3d;

public class Main {

    public static void main(String[] args) {
        Bot bot = new Bot("JCraft", "127.0.0.1", 25566);
        bot.connect();
        bot.setChatEvent(new ChatEventHandler());
        bot.setLoginEvent(new LoginEventHandler());
    }

}

class ChatEventHandler implements ChatEvent {

    @Override
    public void chatEvent(String message, Bot bot) {
        if(message.contains("ping")) bot.session.send(new ClientChatPacket("pong!"));
    }
}

class LoginEventHandler implements LoginEvent {
    @Override
    public void loginEvent(Bot bot){
        System.out.println("Bot has logged on");
    }
}