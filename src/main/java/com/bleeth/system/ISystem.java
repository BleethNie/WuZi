package com.bleeth.system;

import com.bleeth.chess.Point;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

import java.util.List;


public interface ISystem {

    void init();

    boolean isWin(List<Integer> list);

    boolean isRight(int x, int y);

    void playChess(Display display, GC gc, Point p);

    int getWinMessage();

}
