package me.legend.JCraft.Source.Bot;

import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.game.ItemStack;
import me.legend.JCraft.Source.Bot.BotConsole.BotConsole;
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

    private String host;
    private Integer port;
    private EntityPosition location;
    private World world;
    private Inventory inventory;
    private BotConsole console;

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
        this.inventory = new Inventory(this, new ItemStack[36]);

        world = new World();

        this.premium = false;
        init();
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
        this.username = "Pre-Auth";
        this.email = email;
        this.password = password;
        this.host = host;
        this.port = port;
        this.console = new BotConsole(this, true, true);
        this.inventory = new Inventory(this, new ItemStack[36]);

        this.premium = true;
        init();
    }

    private void init(){
        if(this.premium) {
            try {
                minecraftProtocol = new MinecraftProtocol(this.email, this.password, false);
                this.console.debug("Authenticated user " + minecraftProtocol.getProfile().getName());
                this.username = minecraftProtocol.getProfile().getName();
            } catch(RequestException e) {
                this.console.debug("There was an error with authenticating the bot, details are as follows");
                this.console.debug(e.getMessage());
                return;
            }
        } else {
            minecraftProtocol = new MinecraftProtocol(this.username);
        }

        client = new Client(this.host, this.port, minecraftProtocol, new TcpSessionFactory(Proxy.NO_PROXY));
        client.getSession().setFlag(MinecraftConstants.AUTH_PROXY_KEY, Proxy.NO_PROXY);
        client.getSession().addListener(new NetworkHandler(this));
        session = client.getSession();
    }

    public void connect(){
        client.getSession().connect();
    }

    public String getName(){
        return this.username;
    }

    public String getHost(){ return this.host; }
    public Integer getPort(){ return this.port; }
    public EntityPosition getLocation(){ return this.location; }
    public World getWorld(){ return this.world; }
    public Inventory getInventory(){ return this.inventory; }
    public BotConsole getConsole(){ return this.console; }

    public void setLocation(EntityPosition entityPosition){ this.location = entityPosition; }

    public double getX() { return this.location.getPosX(); }
    public double getY() { return this.location.getPosY(); }
    public double getZ() { return this.location.getPosZ(); }
    public float getYaw() { return this.location.getYaw(); }
    public float getPitch() { return this.location.getPitch(); }
}
