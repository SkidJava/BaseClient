package client.eventapi.events;

import client.eventapi.Event;

public class StepEvent extends Event
{
    public double stepHeight;
    public boolean bypass;
    
    public StepEvent(double stepHeight)
    {
	this.stepHeight = stepHeight;
    }
    
}
