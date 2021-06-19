package com.bleeth.event;

public class Event {

	private NetData source;

	public Event() {

	}

	public Event(final NetData source) {
		this.source = source;
	}

	public NetData getSource() {
		return source;
	}

	public void setSource(final NetData source) {
		this.source = source;
	}
	
public static void main(String[] args) {
	System.err.println(System.currentTimeMillis()%100000);
}
}