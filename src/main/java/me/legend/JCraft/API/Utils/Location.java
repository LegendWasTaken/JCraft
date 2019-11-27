package me.legend.JCraft.API.Utils;

import me.legend.JCraft.API.Enums.Direction;

public class Location {

    private Double x,y,z;
    public Float yaw, pitch;

    public Location(){
        this(0D, 0D, 0D);
    }

    public Location(Double x, Double y, Double z){
        this(x, y, z, 0F, 0F);
    }

    public Location(Double x, Double y, Double z, Float yaw, Float pitch){
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Location shift(Direction direction, Double amount){
        Location location = new Location(this.x, this.y, this.z, this.yaw, this.pitch);
        switch(direction){
            case WEST:
                location.x -= amount;
                break;
            case EAST:
                location.x += amount;
                break;
            case DOWN:
                location.y -= amount;
                break;
            case UP:
                location.y += amount;
            case NORTH:
                location.z -= amount;
                break;
            case SOUTH:
                location.z += amount;
                break;
        }
        return location;
    }


}
