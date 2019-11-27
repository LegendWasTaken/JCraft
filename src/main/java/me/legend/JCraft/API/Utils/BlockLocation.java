package me.legend.JCraft.API.Utils;

import me.legend.JCraft.API.Enums.Direction;
import me.legend.JCraft.Source.Util.Vector3d;

public class BlockLocation {

    public Integer x,y,z, chunkX, chunkZ;

    public BlockLocation(){
        this(0, 0, 0);
    };

    public BlockLocation(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
        this.chunkX = this.x >> 4;
        this.chunkZ = this.z >> 4;
    }

    public Vector3d vector3dFromBlockLocation(){
        return new Vector3d(this.x, this.y, this.z);
    }

    public BlockLocation shift(Direction direction){
        BlockLocation location = new BlockLocation(this.x, this.y, this.z);
        switch(direction){
            case WEST:
                location.x -= 1;
                break;
            case EAST:
                location.x += 1;
                break;
            case DOWN:
                location.y -= 1;
                break;
            case UP:
                location.y += 1;
            case NORTH:
                location.z -= 1;
                break;
            case SOUTH:
                location.z += 1;
                break;
        }
        return location;
    }

}
