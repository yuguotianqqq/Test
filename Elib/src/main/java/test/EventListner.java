package test;

import com.google.common.eventbus.Subscribe;

public class EventListner {

	@Subscribe
	public void doEvent(String message) {
		System.out.println(message);
	}
}
