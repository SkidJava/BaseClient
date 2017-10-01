package client.event.events.render;

import client.event.Event;

public class EventRender2D extends Event {

	public int width, height;

	public EventRender2D(int width, int height) {
		this.width = width;
		this.height = height;
	}
}
