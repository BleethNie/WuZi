package com.bleeth.system;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

import com.bleeth.chess.Point;


public class SingleSystem extends ASystem{

	@Override
	public void init() {

	}

	@Override
	public boolean isRight(int x, int y) {
		return true;
	}

	@Override
	public void playChess(Display display, GC gc, Point p) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getWinMessage() {
		// TODO Auto-generated method stub
		return 0;
	}



}
