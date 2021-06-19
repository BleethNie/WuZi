package com.bleeth.event;

import java.io.Serializable;


public class NetData implements Serializable {

	private static final long serialVersionUID = 9002831334L;
	private transient MyDataListener listener;
	private int messageType;
	private int xPoint;
	private int yPoint;

	public NetData() {
		super();
		xPoint = 0;
		yPoint = 0;
		messageType = 0;
	}

	public NetData(final int xPoint, final int yPoint, final int messageType) {
		super();
		this.xPoint = xPoint;
		this.yPoint = yPoint;
		this.messageType = messageType;
	}


	public void registerListener(final MyDataListener listener) {
		this.listener = listener;
	}

	public void receive(final NetData wuData) {
		if (listener != null) {
			xPoint = wuData.getXPoint();
			yPoint = wuData.getYPoint();
			messageType = wuData.getMessageType();
			listener.receiveData(new Event(this));
		}
	}

	public void add(final NetData wuData) {
		if (listener != null) {
			xPoint = wuData.getXPoint();
			yPoint = wuData.getYPoint();
			messageType = wuData.getMessageType();
			listener.addData(new Event(this));
		}
	}

	public void play(int i) {
		if (listener != null) {
			listener.playData(new Event(this));
			System.err.println("i = "+i);
		}
	}


	@Override
	public String toString() {
		return "[ xPoint = " + xPoint + "; yPoint = " + yPoint + "; messageType = " + messageType + " ]";
	}



	@Override
	public boolean equals(final Object obj) {
		 if (this == obj) {
		        return true;
		    }

		if (obj instanceof NetData) {
			NetData otherMyData = (NetData)obj;

			if (otherMyData.xPoint ==this.xPoint && otherMyData.yPoint == this.yPoint&& otherMyData.messageType == this.messageType) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int  hashCode(){
		 int result = 17;
		 result = 37 * result + this.xPoint;
		 result = 37 * result + this.yPoint;
		 result = 37 * result + this.messageType;
		return result;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public int getXPoint() {
		return xPoint;
	}

	public void setXPoint(int xPoint) {
		this.xPoint = xPoint;
	}

	public int getYPoint() {
		return yPoint;
	}

	public void setYPoint(int yPoint) {
		this.yPoint = yPoint;
	}


}
