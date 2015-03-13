package test;

import com.google.common.eventbus.Subscribe;

public class Event3Listner {

	@Subscribe
	public void doEvent(String message) {
		System.out.println(message);
	}
}
