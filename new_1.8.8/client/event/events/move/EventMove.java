package client.event.events.move;

import client.event.Event;

public class EventMove extends Event {

	public double x;
	public double y;
	public double z;

	public EventMove(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

}
