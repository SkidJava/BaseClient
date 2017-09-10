package client.eventapi.events;

import client.eventapi.Event;

public class UseItemEvent extends Event
{
    public State state;
    
    public UseItemEvent(State state)
    {
	this.state = state;
    }
}
