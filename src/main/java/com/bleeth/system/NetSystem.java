package com.bleeth.system;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Display;

import com.bleeth.chess.Point;
import com.bleeth.event.Event;
import com.bleeth.event.MyData;
import com.bleeth.event.MyDataListenerAdapter;
import com.bleeth.net.Server;

public class NetSystem extends ASystem{

	int port ;
	String ip;



	public NetSystem(String ip,int port){
		this.port =port;
		this.ip= ip;
	}

	public NetSystem(String ip){
		this.port =8989;
		this.ip= ip;
	}

	public void init(){
		System.err.println("系统初始化开始");
		int myTime = (int) (System.currentTimeMillis() % 10000000);
		//int myTime = 8585390;
		final Server com = new Server(data,ip,port);
		final Thread th = new Thread(com);
		th.start();

		data.registerListener(new MyDataListenerAdapter() {
			@Override
			public void addData(final Event e) {
					if (e.getSource().getXPoint() == -1 && e.getSource().getYPoint() == -1) {
						com.sentData(data);
						return;
					}
					//发送相同的数据，也让对方服务器设置flag=false;
					com.sentData(data);
			}

			@Override
			public void receiveData(final Event e) {
					if (e.getSource().getXPoint() == 111 && e.getSource().getYPoint() == 111) {
						if (myTime > e.getSource().getMessageType()) {
							System.err.println(com.getMyIp() + " 服务器状态 flag =  true");
							isBlack = true;
							state = true;
							//发送数据表示我方状态为true，且你方状态必须设置为false;
							com.sentData(new MyData(112, 112, 112));
						} else {
							//发送数据表示我方状态为false，且你方状态必须设置为true;
							com.sentData(new MyData(113, 113, 113));
							System.err.println(com.getMyIp() + " 服务器为 flag =  false");
							state = false;
							isBlack = false;
						}
					return;
				}


				if (e.getSource().getXPoint() == 112 && e.getSource().getYPoint() == 112) {
					System.err.println(com.getMyIp() + " 服务器为 flag =  false");
					isBlack = false;
					state = false;
					return;
				}

				if (e.getSource().getXPoint() == 113 && e.getSource().getYPoint() == 113) {
					System.err.println(com.getMyIp() + " 服务器为 flag =  true");
					isBlack = true;
					state = true;
					return;
				}

				if(isBlack&&!state){
					white.getRecord().add(e.getSource().getXPoint()*100+e.getSource().getYPoint());
				}
				if(!isBlack&&!state){
					black.getRecord().add(e.getSource().getXPoint()*100+e.getSource().getYPoint());
				}
				mmd.play(555);
				state = !state;
			}
		});

		//初始化发送一条消息，用于分配双方状态
		com.sentData(new MyData(111, 111, myTime));
	}


	public  void playChess(Display display,GC gc,Point p){
		if(isBlack&&state){
			black.playChess(display,gc ,p);

		}
		if(!isBlack&&state){
			white.playChess(display,gc ,p);

		}
		data.add(new MyData(p.getX(),p.getY(),0));
		state = !state;
	}


	public void addChess(){

	}


	@Override
	public boolean isRight(int x,int y) {
		if(!state){
			return false;
		}

		if(black.getRecord().contains(x*100+y)||white.getRecord().contains(x*100+y)){
			return false;
		}

		return true;
	}


	/**
	 * 黑赢 1
	 * 白赢 2
	 * 都没有 0
	 * @return
	 */
	public int getWinMessage(){
		int w= 0;
		int b = 0;

		b =  super.isWin(black.getRecord())?1:0;
		w =  super.isWin(white.getRecord())?2:0;

		if(b==1){
			return 1;
		}

		if(w==2){
			return 2;
		}

		return 0;

	}




}
