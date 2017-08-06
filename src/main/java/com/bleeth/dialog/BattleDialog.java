package com.bleeth.dialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.bleeth.chess.Point;
import com.bleeth.event.Event;
import com.bleeth.event.MyDataListenerAdapter;
import com.bleeth.system.ASystem;
import com.bleeth.system.NetSystem;


public class BattleDialog {

	protected Shell shlWuzi;
	private Display display;
	Boolean isRun=false;
	private Text text;
	private Text text_1;
	private ASystem system;

	public static void main(String[] args) {
		BattleDialog bd = new BattleDialog();
		NetSystem net = new NetSystem("192.168.1.104");
		bd.open(net);
	}

	/**
	 * Open the window.
	 */
	public void open(ASystem sys) {
		System.err.println("进入对战页面");
		display = Display.getDefault();
		createContents();
		shlWuzi.open();
		shlWuzi.layout();
		system = sys;
		System.err.println("system = sys;");
		system.init();


		while (!shlWuzi.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlWuzi = new Shell();
		shlWuzi.setSize(1050, 650);
		shlWuzi.setText("WuZi");

		Point  point  = new Point();
		Composite composite = new Composite(shlWuzi, SWT.NONE);
		GC gc = new GC(composite);
		composite.setBackgroundImage(SWTResourceManager.getImage(BattleDialog.class, "/com/bleeth/resource/image/棋盘.png"));
		composite.setBounds(0, 0, 600, 600);

		Composite composite_1 = new Composite(shlWuzi, SWT.NONE);
		composite_1.setBackgroundImage(SWTResourceManager.getImage(BattleDialog.class, "/com/bleeth/resource/image/控制面板.png"));
		composite_1.setBounds(600, 0, 430, 300);

		Button btnNewButton = new Button(composite_1, SWT.NONE);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if(isRun){

				}else{
					isRun = true;
				}
				system.gc = gc;
				system.display = display;
			}
		});
		btnNewButton.setBounds(29, 48, 80, 27);
		btnNewButton.setText("开始");

		Button btnNewButton_1 = new Button(composite_1, SWT.NONE);
		btnNewButton_1.setBounds(184, 48, 80, 27);
		btnNewButton_1.setText("暂停");

		Button btnNewButton_2 = new Button(composite_1, SWT.NONE);
		btnNewButton_2.setBounds(340, 48, 80, 27);
		btnNewButton_2.setText("重新开始");

		Label lblNewLabel = new Label(composite_1, SWT.NONE);
		lblNewLabel.setBounds(29, 145, 391, 53);
		lblNewLabel.setText("计时器");

		Composite composite_2 = new Composite(shlWuzi, SWT.NONE);
		composite_2.setBackgroundImage(SWTResourceManager.getImage(BattleDialog.class, "/com/bleeth/resource/image/控制面板.png"));
		composite_2.setBounds(600, 300, 430, 300);

		text = new Text(composite_2, SWT.BORDER);
		text.setBounds(24, 55, 385, 147);

		text_1 = new Text(composite_2, SWT.BORDER);
		text_1.setBounds(24, 208, 385, 49);

		Button btnNewButton_3 = new Button(composite_2, SWT.NONE);
		btnNewButton_3.setBounds(24, 263, 80, 27);
		btnNewButton_3.setText("发送");

		Button btnNewButton_4 = new Button(composite_2, SWT.NONE);
		btnNewButton_4.setBounds(329, 263, 80, 27);
		btnNewButton_4.setText("撤销");
		composite.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Point p = changePoint(e,point,system);
				if(p==null){
					return;
				}


			system.playChess(display,gc,p);
			if(system.getWinMessage()==1){
				WinDialog wd = new WinDialog();
				wd.open("黑方");
				isRun = !isRun;
			}else if(system.getWinMessage()==2){
				WinDialog wd = new WinDialog();
				wd.open("白方");
				isRun = !isRun;
			}else{
				System.err.println("未出结果");
			}

			}
		});

		NetSystem.mmd.registerListener(new MyDataListenerAdapter() {
			@Override
			public void playData(final Event e) {
				if(system.isBlack&&!system.state){
					int x = system.white.getRecord().get(system.white.getRecord().size()-1)/100;
					int y = system.white.getRecord().get(system.white.getRecord().size()-1)%100;
					system.white.playChess(display,gc ,new Point(x,y));
				}
				if(!system.isBlack&&!system.state){
					int x = system.black.getRecord().get(system.black.getRecord().size()-1)/100;
					int y = system.black.getRecord().get(system.black.getRecord().size()-1)%100;
					system.black.playChess(display,gc ,new Point(x,y));
				}

				 Display.getDefault().syncExec(new Runnable() {

					@Override
					public void run() {
						if(system.getWinMessage()==1){
							WinDialog wd = new WinDialog();
							wd.open("黑方");
							isRun = !isRun;
						}else if(system.getWinMessage()==2){
							WinDialog wd = new WinDialog();
							wd.open("白方");
							isRun = !isRun;
						}else{
							System.err.println("未出结果");
						}
						}

				 });
			}});
	}



	public  Point changePoint(MouseEvent e,Point p,ASystem system ){
		System.out.println("changePoint");
		System.err.println("isBlack"+system.isBlack+" "+"state"+system.state);
		if(!isRun){
			return null;
		}
		int x,y;
		if (e.x <= 30 || e.x >= 540 || e.y <= 30 || e.y >= 540) {
			return null;
		}
		if((e.x-40)<=0){
			x = 1;
		}else{
			x = e.x-40;
		}
		if((e.y-40)<=0){
			y = 1;
		}else{
			y = e.y-40;
		}

		x = x / 35+(x % 35 > 10 ? 1 : 0);
		y = y / 35+(y % 35 > 10 ? 1 : 0);
		if(!system.isRight(x,y)){
			return null;
		}

		System.out.println("isRun = "+isRun+"  "+"x = "+x+"  "+"y = "+y);
		return	p.setPoint(x,y);

	}


}
