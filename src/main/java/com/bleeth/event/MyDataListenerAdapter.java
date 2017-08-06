package com.bleeth.event;

public abstract class MyDataListenerAdapter  implements MyDataListener{

	public void receiveData(Event e){

	}

	public void addData(Event e){

	}

	public void playData(Event e){
		System.err.println("MyDataListenerAdapter");
	}

}
