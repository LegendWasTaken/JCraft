package me.legend.JCraft.Source.BotHandler;

import me.legend.JCraft.Source.Bot;
import me.legend.JCraft.Source.Exceptions.InvalidBotException;
import me.legend.JCraft.Source.Exceptions.InvalidIDException;
import me.legend.JCraft.Source.Exceptions.InvalidSessionException;
import org.spacehq.packetlib.Session;

import java.util.ArrayList;
import java.util.List;

public class BotHandler {

    public static List<Bot> bots = new ArrayList<Bot>();


    /**
     * Adds the passed in bot to the current list;
     * Warning ( Not a HashSet, so duplicates can occur )
     *
     * @param bot The bot you want to add to the list
     *
     * @return
     */
    public static Integer addBot(Bot bot){
        bots.add(bot);
        return bots.size() - 1;
    }

    /**
     *
     * @param ID The bots id ( The value you get when you call addBot )
     * @return Returns the bot based on the ID you pass in
     * @throws InvalidIDException This gets thrown if you fucked up
     */
    public static Bot getBotByID(Integer ID) throws InvalidIDException {
        if(ID < 0 || ID + 2 > bots.size()) throw new InvalidIDException();
        return bots.get(ID);
    }

    /**
     *
     * @param bot The bot who's ID you want to get
     * @return The bot's ID
     * @throws InvalidBotException Honestly, if you can get this to be thrown. DM me on discord [ NotLegend#9328 ]
     */
    public static Integer getBotID(Bot bot) throws InvalidBotException {
        for(int i=0; i<bots.size(); i++){
            if(bot.session == bots.get(i).session){
                return i;
            }
        }
        throw new InvalidBotException();
    }

    /**
     * Gets a bot by session ( Useful for packet and network handling )
     *
     * @param session The session that the bot has ( For custom packet handling, only use if you know what you're doing )
     * @return Returns the bot based on the session you pass in for the first parameter
     * @throws InvalidSessionException If the session you pass in, is somehow invalid. This exception will be thrown.
     */

    public static Bot getBotBySession(Session session) throws InvalidSessionException {
        for(Bot bot : bots){
            if(bot.session.equals(session)){
                return bot;
            }
        }
        throw new InvalidSessionException();
    }

}
