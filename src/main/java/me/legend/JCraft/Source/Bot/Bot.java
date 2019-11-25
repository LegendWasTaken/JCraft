package me.legend.JCraft.Source.Bot;

import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.game.ItemStack;
import me.legend.JCraft.Source.Bot.BotConsole.BotConsole;
import me.legend.JCraft.Source.BotHandler.BotHandler;
import me.legend.JCraft.Source.Bot.Inventory.Inventory;
import me.legend.JCraft.Source.Network.NetworkHandler;
import me.legend.JCraft.Source.Util.EntityPosition;
import me.legend.JCraft.Source.Bot.World.World;
import org.spacehq.mc.auth.exception.request.RequestException;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.tcp.TcpSessionFactory;

import java.net.Proxy;

public class Bot {
    private String username;
    private String email;
    private String password;
    private Boolean premium;

    public String host;
    public Integer port;
    public EntityPosition location;
    public World world;
    public Inventory inventory;
    public BotConsole console;
    public static Boolean debug = true;

    public MinecraftProtocol minecraftProtocol;
    public Client client;
    public Session session;

    /**
     * This bot constructor is made for non premium servers ( Without mojang authentication )
     *
     * @param username Bots username
     * @param host Target server IP
     * @param port Port of target server
     */
    public Bot(String username, String host, Integer port){
        this.username = username;
        this.host = host;
        this.port = port;
        this.console = new BotConsole(this, true, true);
        this.inventory = new Inventory(this.session, new ItemStack[36]);

        world = new World();

        this.premium = false;
        init();
        BotHandler.addBot(this);
    }

    /**
     * This bot constructor is for premium servers ( With mojang authentication )
     *
     * @param email The accounts email
     * @param password The accounts password
     * @param host Target server IP
     * @param port Port of target server
     */
    public Bot(String email, String password, String host, Integer port){
        this.email = email;
        this.password = password;
        this.host = host;
        this.port = port;

        this.premium = true;
        init();
        BotHandler.addBot(this);
    }

    private void init(){
        if(this.premium) {
            try {
                minecraftProtocol = new MinecraftProtocol(this.email, this.password, false);
                if(debug) System.out.println("Successfully authenticated user.");
            } catch(RequestException e) {
                if(debug) e.printStackTrace();
                return;
            }
        } else {
            minecraftProtocol = new MinecraftProtocol(this.username);
        }

        client = new Client(this.host, this.port, minecraftProtocol, new TcpSessionFactory(Proxy.NO_PROXY));
        client.getSession().setFlag(MinecraftConstants.AUTH_PROXY_KEY, Proxy.NO_PROXY);
        client.getSession().addListener(new NetworkHandler(this));
        session = client.getSession();
        client.getSession().connect();
    }

    public static void debug(String message){
        if(debug) System.out.println("[DEBUG] " + message);
    }

    public String getName(){
        return this.username;
    }

    public double getX() { return this.location.getPosX(); }
    public double getY() { return this.location.getPosY(); }
    public double getZ() { return this.location.getPosZ(); }
    public float getYaw() { return this.location.getYaw(); }
    public float getPitch() { return this.location.getPitch(); }

}
