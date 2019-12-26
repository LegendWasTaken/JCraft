package me.legend.JCraft.Source.Network.PacketHandlers.WorldData;

import com.github.steveice10.mc.protocol.data.game.Position;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerBlockChangePacket;
import me.legend.JCraft.Source.Bot.Bot;
import me.legend.JCraft.Source.Bot.World.Block.Block;

public class BlockChangePacket {
    public BlockChangePacket(ServerBlockChangePacket packet, Bot bot){
        Position target = packet.getRecord().getPosition();
        Block block = new Block(packet.getRecord().getData(), packet.getRecord().getId(), target.getX(), target.getY(), target.getZ());
        bot.getWorld().setBlock(target.getX(), target.getY(), target.getZ(), block);
    }
}
