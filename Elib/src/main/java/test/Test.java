package test;

import java.util.concurrent.Executors;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

public class Test {

	public static void main(String[] args) {
		EventBus eb = new AsyncEventBus(Executors.newFixedThreadPool(2));
		eb.register(new EventListner());
		eb.register(new Event2Listner());
		eb.register(new Event3Listner());
		eb.post(22);
		eb.post("test");
		eb.post("ffff" + 122);
		System.out.println("OK");
	}
}
