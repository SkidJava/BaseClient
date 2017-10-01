package client.event.events.player;

import client.event.Event;

public class EventPlayerVelocity extends Event {

	public double mx, my;

	public EventPlayerVelocity(double x, double y) {
		this.mx = x;
		this.my = y;
	}

}
