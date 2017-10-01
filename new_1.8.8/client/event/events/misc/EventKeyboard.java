package client.event.events.misc;

import client.event.Event;

public class EventKeyboard extends Event {

	private int k;

	public EventKeyboard(int k) {
		this.k = k;
	}

	public int getKey() {
		return this.k;
	}

}
