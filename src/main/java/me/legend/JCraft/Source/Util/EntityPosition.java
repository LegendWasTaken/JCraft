package me.legend.JCraft.Source.Util;

import com.github.steveice10.mc.protocol.data.game.Position;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import me.legend.JCraft.Source.Bot.Bot;

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

    protected void setPosX(double x){ this.lastX = this.posX; this.posX = x; }
    protected void setPosY(double y){ this.lastY = this.posY; this.posY = y; }
    protected void setPosZ(double z){ this.lastZ = this.posZ; this.posZ = z; }
    protected void setYaw(float yaw){ this.yaw = yaw; }
    protected void setPitch(float pitch){ this.pitch = pitch; }

    public double getPosX() { return this.posX; }
    public double getPosY() { return this.posY; }
    public double getPosZ() { return this.posZ; }
    public double getLastX() { return this.lastX; }
    public double getLastY() { return this.lastY; }
    public double getLastZ() { return this.lastZ; }
    public float getYaw() { return this.yaw; }
    public float getPitch() { return this.pitch; }

    public Position toPosition(){
        return new Position((int) this.posX, (int) this.posY, (int) this.posZ);
    }

    public void look(Bot bot){
        bot.session.send(new ClientPlayerPositionRotationPacket(false, this.posX, this.posY, this.posZ, this.yaw, this.pitch));
    }

    public void look(Bot bot, float yaw, float pitch){
        this.yaw = yaw;
        this.pitch = pitch;
        bot.session.send(new ClientPlayerPositionRotationPacket(false, this.posX, this.posY, this.posZ, yaw, pitch));
    }

}
