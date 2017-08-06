package com.bleeth.chess;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.wb.swt.SWTResourceManager;

import com.bleeth.Constans;
import com.bleeth.dialog.BasicDialog;

public class Player {

	private String name;
	private int color;
	private List<Integer> record = new ArrayList<>();

	public final static  int WHITE_COLOR = 1;
	public final static  int BLACK_COLOR = 0;

	public void playChess(Display display, GC gc,Point p) {
		record.add(p.getX()*100+p.getY());
		Image image=	SWTResourceManager.getImage(BasicDialog.class, "/com/bleeth/resource/image"+"/"+color+".png");
		gc.drawImage(image, p.getX()*35+27, p.getY()*35+27);
	}

	public Player(String name,int color){
		this.name = name;
		this.color = color;
	}

	public Player(int color){
		this.name = "";
		this.color = color;
	}

	public String getName() {
		return name;
	}


	public int getColor() {
		return color;
	}

	public List<Integer> getRecord() {
		return record;
	}

	public Boolean  hasPoint(Point p){
		if(p==null){
			return true;
		}

		if(record.contains(p.getX()*100+p.getY())){
			return true;
		}

		return false;
	}




}
