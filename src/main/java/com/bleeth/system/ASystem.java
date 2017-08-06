package com.bleeth.system;

import java.util.List;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

import com.bleeth.chess.Player;
import com.bleeth.event.MyData;

public abstract class ASystem implements ISystem {

	public Player white = new Player(Player.WHITE_COLOR);
	public Player black = new Player(Player.BLACK_COLOR);
	public static MyData data = new MyData();
	public static MyData mmd = new MyData();
	public GC gc;
	public Display display;
	public Boolean isRun;
	public Boolean state = false;
	public Boolean isBlack;


	public Boolean isWin(List<Integer> list) {
		if (list == null || list.size() < 5) {
			return false;
		}

		int tmp = list.get(list.size() - 1);
		int h = 0;
		for (int i = 0; i <= 10; i++) {
			if (list.contains(tmp - 500 + i * 100)) {
				++h;
				if (h == 5) {
					return true;
				}
			} else {
				h = 0;
			}
		}

		h = 0;
		for (int i = 0; i <= 10; i++) {
			if (list.contains(tmp - 5 + i)) {
				++h;
				if (h == 5) {
					return true;
				}
			} else {
				h = 0;
			}
		}

		 h = 0;
		for (int i = 0; i <= 10; i++) {
			if (list.contains(tmp - 505 + 101 * i)) {
				++h;
				if (h == 5) {
					return true;
				}
			} else {
				h = 0;
			}
		}

		h = 0;
		for (int i = 0; i <= 10; i++) {
			if (list.contains(tmp - 495 + 99 * i)) {
				++h;
				if (h == 5) {
					return true;
				}
			} else {
				h = 0;
			}
		}
		return false;
	}




}
