package me.legend.JCraft.Source.Util;

import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerChangeHeldItemPacket;
import me.legend.JCraft.Source.Bot;
import me.legend.JCraft.Source.BotHandler.BotHandler;
import me.legend.JCraft.Source.Exceptions.InvalidSessionException;
import org.spacehq.packetlib.Session;

public class EntityPosition {

    private double posX, lastX;
    private double posY, lastY;
    private double posZ, lastZ;
    private float pitch, yaw;

    public EntityPosition(double x, double y, double z){
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.yaw = 0F;
        this.pitch = 0F;
    }

    public EntityPosition(double x, double y, double z, double yaw, double pitch){
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.yaw = (float) yaw;
        this.pitch = (float) pitch;
    }

    public void setPosX(double x){ this.posX = x; }
    public void setPosY(double y){ this.posY = y; }
    public void setPosZ(double z){ this.posZ = z; }
    public void setYaw(float yaw){ this.yaw = yaw; }
    public void setPitch(float pitch){ this.pitch = pitch; }

    public double getPosX() { return this.posX; }
    public double getPosY() { return this.posY; }
    public double getPosZ() { return this.posZ; }
    public double getLastX() { return this.lastX; }
    public double getLastY() { return this.lastY; }
    public double getLastZ() { return this.lastZ; }
    public float getYaw() { return this.yaw; }
    public float getPitch() { return this.pitch; }

    public void look(Session session){
        try{
            Bot bot = BotHandler.getBotBySession(session);
            bot.session.send(new ClientPlayerPositionRotationPacket(false, this.posX, this.posY, this.posZ, this.yaw, this.pitch));
        } catch (InvalidSessionException ignored) {}
    }

    public void look(Session session, float yaw, float pitch){
        try{
            Bot bot = BotHandler.getBotBySession(session);
            bot.session.send(new ClientPlayerPositionRotationPacket(false, this.posX, this.posY, this.posZ, yaw, pitch));
        } catch (InvalidSessionException ignored) {}
    }

}
