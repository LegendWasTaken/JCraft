package me.legend.JCraft.Source.Bot.World.Block;

public class Block {

    public int data = 0;
    public int type = 0;

    public Block(){
        this.data = 0;
        this.type = 0;
    }

    public Block(int type){
        this.data = 0;
        this.type = type;
    }

    public Block(int data, int type){
        this.data = data;
        this.type = type;
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
