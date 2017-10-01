package client.eventapi.events;

import client.eventapi.Event;

public class MoveEvent extends Event
{
    public double x, y, z;
    
    public MoveEvent(double x, double y, double z)
    {
	this.x = x;
	this.y = y;
	this.z = z;
    }
}
