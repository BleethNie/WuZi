package com.bleeth.system;

import java.util.List;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

import com.bleeth.chess.Point;


public interface ISystem {

	default void init(){}

	public Boolean isWin(List<Integer> list);

	public  Boolean isRight(int x,int y);

	public void playChess(Display display,GC gc,Point p);

	public int getWinMessage();

}
