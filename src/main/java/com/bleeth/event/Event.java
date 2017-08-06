package com.bleeth.event;

public class Event {

	private MyData source;

	public Event() {

	}

	public Event(final MyData source) {
		this.source = source;
	}

	public MyData getSource() {
		return source;
	}

	public void setSource(final MyData source) {
		this.source = source;
	}
	
public static void main(String[] args) {
	System.err.println(System.currentTimeMillis()%100000);
}
}