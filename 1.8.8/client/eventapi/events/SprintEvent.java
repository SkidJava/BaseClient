package client.eventapi.events;

import client.eventapi.Event;

public class SprintEvent extends Event
{
    public boolean sprint;
    
    public SprintEvent(boolean sprint)
    {
	this.sprint = sprint;
    }
}
