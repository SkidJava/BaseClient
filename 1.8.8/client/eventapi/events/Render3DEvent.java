package client.eventapi.events;

import client.eventapi.Event;

public class Render3DEvent extends Event
{
    public float partialTicks;
    
    public Render3DEvent(float partialTicks)
    {
	this.partialTicks = partialTicks;
    }
}
