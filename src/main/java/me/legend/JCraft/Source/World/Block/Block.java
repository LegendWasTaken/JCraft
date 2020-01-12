package me.legend.JCraft.Source.World.Block;

import me.legend.JCraft.Source.Util.Vector3d;

public class Block {

    public int data;
    public int type;
    public Vector3d location = null;

    public Block(){
        this.data = 0;
        this.type = 0;
    }

    public Block(int type){
        this.data = 0;
        this.type = type;
    }

    public Block(int data, int type, int x, int y, int z){
        this.data = data;
        this.type = type;
        this.location = new Vector3d(x, y, z);
    }

    public void setData(int data){
        this.data = data;
    }

    public void setType(int type){
        this.type = type;
    }

    public void setDataAndType(int data, int type){
        this.data = data;
        this.type = type;
    }

}
