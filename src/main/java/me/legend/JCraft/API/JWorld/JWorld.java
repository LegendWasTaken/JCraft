package me.legend.JCraft.API.JWorld;

import me.legend.JCraft.API.Enums.Direction;
import me.legend.JCraft.API.JWorld.JBlock.JBlock;
import me.legend.JCraft.API.Utils.BlockLocation;
import me.legend.JCraft.Source.Bot.Bot;
import me.legend.JCraft.Source.Bot.World.Block.Block;
import me.legend.JCraft.Source.Bot.World.World;
import me.legend.JCraft.Source.Util.Vector3d;

public class JWorld {

    private World world;
    private Bot bot;

    public JWorld(World world, Bot bot){
        this.bot = bot;
        this.world = world;
    }

    public JBlock getBlock(BlockLocation targetLocation){
        Block block = world.getBlock(targetLocation.x, targetLocation.y, targetLocation.z);
        return new JBlock(block.type, block.data, targetLocation);
    }

    public void placeBlock(JBlock placeBlock, Direction placeDirection){
        world.placeBlock(placeBlock.getLocation().vector3dFromBlockLocation(), new Vector3d(placeDirection), bot.session, bot.getInventory().getHeldItem());
    }

}
