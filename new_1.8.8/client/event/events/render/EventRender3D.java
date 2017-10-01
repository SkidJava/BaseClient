package client.event.events.render;

import client.event.Event;

public class EventRender3D extends Event {

	public float particlTicks;

	public EventRender3D(float particlTicks) {
		this.particlTicks = particlTicks;
	}

}
