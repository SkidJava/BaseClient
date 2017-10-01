package client.eventapi.events;

import client.eventapi.Event;

public class KeyboardEvent extends Event
{
    public int key;
    
    public KeyboardEvent(int key)
    {
	this.key = key;
    }
}
