package client.eventapi.events;

import client.eventapi.Event;
import net.minecraft.entity.Entity;

public class PreAttackEvent extends Event
{
    public Entity attacked;
    
    public PreAttackEvent(Entity attacked)
    {
	this.attacked = attacked;
    }
    
}
