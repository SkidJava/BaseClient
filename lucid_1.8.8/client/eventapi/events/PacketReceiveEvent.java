package client.eventapi.events;

import client.eventapi.Event;
import net.minecraft.network.Packet;

public class PacketReceiveEvent extends Event
{
    public Packet packet;
    
    public PacketReceiveEvent(Packet packet)
    {
	this.packet = packet;
    }
}
