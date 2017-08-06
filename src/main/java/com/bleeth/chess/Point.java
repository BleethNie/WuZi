package com.bleeth.chess;

public class Point {
	private int x;
	private int y;


	public Point(){

	}

	public Point(int x,int y){
		this.x = x;
		this.y = y;

	}

	public int getX() {
		return x;
	}



	public int getY() {
		return y;
	}

	public  Point  setPoint(int x, int y){
		this.x = x;
		this.y = y;
		return this;
	}

}
