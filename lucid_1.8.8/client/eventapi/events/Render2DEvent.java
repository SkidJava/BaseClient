package client.eventapi.events;

import client.eventapi.Event;

public class Render2DEvent extends Event
{
    public int width;
    public int height;
    
    public Render2DEvent(int width, int height)
    {
	this.width = width;
	this.height = height;
    }
}
