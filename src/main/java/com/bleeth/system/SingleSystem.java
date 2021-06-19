package com.bleeth.system;

import com.bleeth.chess.Point;
import com.bleeth.util.ResourceUtil;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;


public class SingleSystem extends WuZiSystem {

    @Override
    public void init() {

    }

    @Override
    public boolean isRight(int x, int y) {
        return true;
    }

    @Override
    public void playChess(Display display, GC gc, Point p) {
        if (isBlack) {
            black.playChess(display, gc, p);

        }
        if (!isBlack) {
            white.playChess(display, gc, p);

        }
        isBlack = !isBlack;

        //加点声音
        ResourceUtil.playSound();
    }

    @Override
    public int getWinMessage() {
        int w = 0;
        int b = 0;

        b = super.isWin(black.getRecord()) ? 1 : 0;
        w = super.isWin(white.getRecord()) ? 2 : 0;

        if (b == 1) {
            return 1;
        }

        if (w == 2) {
            return 2;
        }

        return 0;
    }


}
