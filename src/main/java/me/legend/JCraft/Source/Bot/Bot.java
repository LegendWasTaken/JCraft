package me.legend.JCraft.Source.Bot;

import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.game.values.entity.player.GameMode;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import me.legend.JCraft.Source.Inventory.Inventory;
import me.legend.JCraft.Source.Entity.Entity;
import me.legend.JCraft.Source.Entity.Player;
import me.legend.JCraft.Source.Events.*;
import me.legend.JCraft.Source.Network.NetworkHandler;
import me.legend.JCraft.Source.Util.EntityPosition;
import me.legend.JCraft.Source.World.World;
import org.spacehq.mc.auth.exception.request.RequestException;
import org.spacehq.packetlib.Client;
import org.spacehq.packetlib.Session;
import org.spacehq.packetlib.tcp.TcpSessionFactory;

import java.net.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bot {
    private String username;
    private String email;
    private String password;
    private Boolean premium, log, debug;

    private String host;
    private Integer port;
    private EntityPosition location;
    private World world;
    private Inventory inventory;
    private GameMode gameMode;
    private Float health = 20.0F;

    private ChatEvent chatEvent = null;
    private LoginEvent loginEvent = null;
    private TeleportEvent teleportEvent = null;
    private NewEntityEvent newEntityEvent = null;
    private NewPlayerEvent newPlayerEvent = null;
    private DisconnectEvent disconnectEvent = null;
    private DamageEvent damageEvent = null;
    private HealEvent healEvent = null;
    private DeathEvent deathEvent = null;


    private Boolean ChatToConsole = false;

    private MinecraftProtocol minecraftProtocol;
    private Client client;
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
        this.log = true;
        this.debug = true;
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
        this.log = true;
        this.debug = false;
        this.premium = true;
        init();
    }

    private void init(){
        if(this.premium) {
            try {
                minecraftProtocol = new MinecraftProtocol(this.email, this.password, false);
                this.debug("Authenticated user " + minecraftProtocol.getProfile().getName());
                this.username = minecraftProtocol.getProfile().getName();
            } catch(RequestException e) {
                this.debug("There was an error with authenticating the bot, details are as follows");
                this.debug(e.getMessage());
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
    public void disconnect(String message) { client.getSession().disconnect(message);}

    String getName(){
        return this.username;
    }

    public String getHost(){ return this.host; }
    public Integer getPort(){ return this.port; }
    public EntityPosition getLocation(){ return this.location; }
    public World getWorld(){ return this.world; }
    public Inventory getInventory(){ return this.inventory; }
    public Float getHealth(){ return this.health; }

    public void setInventory(Inventory inventory){ this.inventory = inventory; }

    public void setLocation(EntityPosition entityPosition){ this.location = entityPosition; }

    public double getX() { return this.location.getPosX(); }
    public double getY() { return this.location.getPosY(); }
    public double getZ() { return this.location.getPosZ(); }
    public float getYaw() { return this.location.getYaw(); }
    public float getPitch() { return this.location.getPitch(); }

    // Chat stuff
    public void chat(String message){ this.session.send(new ClientChatPacket(message));}

    // Bot st

    // Events and handling
    public void setChatEvent(ChatEvent event){ this.chatEvent = event; }
    public void fireChatEvent(String text, Bot bot){ if(this.chatEvent != null) chatEvent.chatEvent(text, bot); }

    public void setLoginEvent(LoginEvent event){ this.loginEvent = event; }
    public void fireLoginEvent(Bot bot){ if(this.loginEvent != null) this.loginEvent.loginEvent(bot); }

    public void setTeleportEvent(TeleportEvent event){ this.teleportEvent = event; }
    public void fireTeleportEvent(Bot bot){ if(this.teleportEvent != null) this.teleportEvent.teleportEvent(bot);}

    public void setNewEntityEvent(NewEntityEvent event){ this.newEntityEvent = event; }
    public void fireNewEntityEvent(Bot bot, Entity entity){ if(this.newEntityEvent != null) this.newEntityEvent.newEntityEvent(bot, entity);}

    public void setNewPlayerEvent(NewPlayerEvent event){ this.newPlayerEvent = event; }
    public void fireNewPlayerEvent(Bot bot, Player player){ if(this.newPlayerEvent != null) this.newPlayerEvent.newPlayerEvent(bot, player);}

    public void setDisconnectEvent(DisconnectEvent event){ this.disconnectEvent = event; }
    public void fireDisconnectEvent(Bot bot, String reason){ if(this.disconnectEvent != null) this.disconnectEvent.disconnectEvent(bot, reason);}

    public void setDamageEvent(DamageEvent event){ this.damageEvent = event; }
    public void fireDamageEvent(Bot bot, Float damage){ if(this.damageEvent != null) this.damageEvent.damageEvent(bot, damage);}

    public void setHealEvent(HealEvent event){ this.healEvent = event; }
    public void fireHealEvent(Bot bot, Float heal){ if(this.healEvent != null) this.healEvent.healEvent(bot, heal);}

    public void setDeathEvent(DeathEvent event){ this.deathEvent = event; }
    public void fireDeathEvent(Bot bot){ if(this.deathEvent != null) this.deathEvent.deathEvent(bot);}

    // Logging stuff

    public void log(String message){
        if(this.log)System.out.println("[" + (new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()))) + " INFO - " + this.getName() + "] " + message);
    }

    public void debug(String message){
        if(this.debug)System.out.println("[" + (new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()))) + " DEBUG - " + this.getName() + "] " + message);
    }

}
