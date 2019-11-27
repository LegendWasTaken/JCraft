package me.legend.JCraft.API.JWorld.JBlock;

import me.legend.JCraft.API.Utils.BlockLocation;

public class JBlock {

    private Integer ID;
    private Short Data;
    private BlockLocation location;

    public JBlock(Integer ID, BlockLocation loc){
        this(ID, (short) 0, loc);
    }

    public JBlock(Integer ID, Integer Data, BlockLocation loc){
        this(ID, Data.shortValue(), loc);
    }

    public JBlock (Integer ID, Short Data, BlockLocation loc){
        this.ID = ID;
        this.Data = Data;
        this.location = loc;
    }

    public BlockLocation getLocation(){
        return this.location;
    }

    public Integer getID(){
        return this.ID;
    }

    public Integer getData(){
        return (int) this.Data;
    }

}
