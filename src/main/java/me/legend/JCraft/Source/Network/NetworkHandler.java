package me.legend.JCraft.Source.Network;

import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerChangeHeldItemPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerBlockChangePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerChunkDataPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerMultiChunkDataPacket;
import me.legend.JCraft.Source.Network.PacketHandlers.Inventory.ChangeHeldItemPacket;
import me.legend.JCraft.Source.Network.PacketHandlers.Location.PlayerPositionRoationPacket;
import me.legend.JCraft.Source.Network.PacketHandlers.ServerConnection.ChatPacket;
import me.legend.JCraft.Source.Network.PacketHandlers.ServerConnection.JoinGamePacket;
import me.legend.JCraft.Source.Network.PacketHandlers.WorldData.BlockChangePacket;
import me.legend.JCraft.Source.Network.PacketHandlers.WorldData.MultiChunkPacket;
import me.legend.JCraft.Source.Network.PacketHandlers.WorldData.SingleChunkPacket;
import me.legend.JCraft.Source.Bot.Bot;
import org.spacehq.packetlib.event.session.PacketReceivedEvent;
import org.spacehq.packetlib.event.session.SessionAdapter;
import org.spacehq.packetlib.packet.Packet;

public class NetworkHandler extends SessionAdapter {

    private Bot bot;

    public NetworkHandler(Bot bot){
        this.bot = bot;
    }

    @Override
    public void packetReceived(PacketReceivedEvent e) {
        try{
        Packet packet = e.getPacket();

        if(packet instanceof ServerChunkDataPacket) new SingleChunkPacket((ServerChunkDataPacket) packet, this.bot);
        else if(packet instanceof ServerMultiChunkDataPacket) new MultiChunkPacket((ServerMultiChunkDataPacket) packet, this.bot);
        else if(packet instanceof ServerJoinGamePacket) new JoinGamePacket((ServerJoinGamePacket) packet, this.bot);
        else if(packet instanceof ServerChatPacket) new ChatPacket((ServerChatPacket) packet, this.bot);
        else if(packet instanceof ServerPlayerPositionRotationPacket) new PlayerPositionRoationPacket((ServerPlayerPositionRotationPacket) packet, this.bot);
        else if(packet instanceof ServerBlockChangePacket) new BlockChangePacket((ServerBlockChangePacket) packet, this.bot);
        else if(packet instanceof ServerChangeHeldItemPacket) new ChangeHeldItemPacket((ServerChangeHeldItemPacket) packet, this.bot);
        } catch (Exception ex) {ex.printStackTrace();}
    }
}
