package me.legend.JCraft.Source.Network.PacketHandlers.WorldData;

import com.github.steveice10.mc.protocol.data.game.Position;
import com.github.steveice10.mc.protocol.data.game.values.world.block.BlockChangeRecord;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerMultiBlockChangePacket;
import me.legend.JCraft.Source.Bot.Bot;
import me.legend.JCraft.Source.Bot.World.Block.Block;

public class MultiBlockChangePacket {
    public MultiBlockChangePacket(ServerMultiBlockChangePacket packet, Bot bot){
        for(int i=0; i<packet.getRecords().length; i++){
            BlockChangeRecord record = packet.getRecords()[i];
            Position target = record.getPosition();
            Block block = new Block(record.getData(), record.getId());
            bot.getWorld().setBlock(target.getX(), target.getY(), target.getZ(), block);
        }
    }
}
