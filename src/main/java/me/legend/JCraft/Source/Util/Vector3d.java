package me.legend.JCraft.Source.Util;

import com.github.steveice10.mc.protocol.data.game.Position;
import me.legend.JCraft.API.Enums.Direction;

public class Vector3d {
    public double x;
    public double y;
    public double z;

    public Vector3d(Direction direction){
        switch(direction){
            case UP:
                this.x = 0;
                this.y = 1;
                this.z = 0;
                break;
            case DOWN:
                this.x = 0;
                this.y = -1;
                this.z = 0;
            case EAST:
                this.x = 1;
                this.y = 0;
                this.z = 0;
                break;
            case WEST:
                this.x = -1;
                this.y = 0;
                this.z = 0;
            case SOUTH:
                this.x = 1;
                this.y = 0;
                this.z = 0;
                break;
            case NORTH:
                this.x = -1;
                this.y = 0;
                this.z = 0;
                break;
        }

    }

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3d floor() {
        this.x = Math.floor(x);
        this.y = Math.floor(y);
        this.z = Math.floor(z);
        return this;
    }

    public Vector3d ceil() {
        this.x = Math.ceil(x);
        this.y = Math.ceil(y);
        this.z = Math.ceil(z);
        return this;
    }

    public Vector3d normalize(){
        double t = (this.x + this.y + this.z);
        this.x /= t;
        this.y /= y;
        this.z /= z;
        return this;
    }

    public Vector3d round() {
        this.x = (int) x;
        this.y = (int) y;
        this.z = (int) z;
        return this;
    }

    public Vector3d negate() {
        this.x = -x;
        this.y = -y;
        this.z = -z;
        return this;
    }

    public Vector3d add(Vector3d v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        return this;
    }

    public int getBlockX() { return (int) this.floor().round().x; }

    public int getBlockY() { return (int) this.floor().round().y; }

    public int getBlockZ() { return (int) this.floor().round().z; }

    public Vector3d setX(double x) {
        this.x = x;
        return this;
    }

    public Vector3d setY(double y) {
        this.y = y;
        return this;
    }

    public Vector3d setZ(double z) {
        this.z = z;
        return this;
    }
    public Vector3d clone() {
        return new Vector3d(this.x, this.y, this.z);
    }

    public String toString() {
        return x + " " + y + " " + z;
    }

    public static Vector3d fromPosition(Position p) {
        return new Vector3d(p.getX(), p.getY(), p.getZ());
    }

    public static Position toPosition(Vector3d d) {
        return new Position((int) Math.floor(d.x), (int) Math.floor(d.y), (int) Math.floor(d.z));
    }

    public static Vector3d fromString(String s) {
        // String format: "X Y Z"
        try {
            double x = Double.parseDouble(s.split(" ")[0]);
            double y = Double.parseDouble(s.split(" ")[1]);
            double z = Double.parseDouble(s.split(" ")[2]);
            return new Vector3d(x, y, z);
        }
        catch (Exception e) {
            throw new IllegalArgumentException(s);
        }
    }

    public Position toPosition() {
        return toPosition(this);
    }
}
