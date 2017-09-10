package client.eventapi.events;

import client.eventapi.Event;

public class MouseEvent extends Event
{
    public int key;
    
    public MouseEvent(int key)
    {
	this.key = key;
    }
}
