package netTest;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import SWT.work.Event;
import SWT.work.MyData;
import SWT.work.MyDataListenerAdapter;
import SWT.work.Server;

public class Client {

	private static Boolean flag;
	private static MyData wu = new MyData();

	public static void main(final String[] args)  {
		int myTime = (int) (System.currentTimeMillis() % 100000);
		final Server com = new Server(wu);
		final Thread th = new Thread(com);
		th.start();

		wu.registerListener(new MyDataListenerAdapter() {
			@Override
			public void addData(final Event e) {
					if (e.getSource().getXPoint() == -1 && e.getSource().getYPoint() == -1) {
						flag = false;
						com.sentData(wu);
						return;
					}
					//发送相同的数据，也让对方服务器设置flag=false;
					com.sentData(wu);
			}

			@Override
			public void receiveData(final Event e) {
				if (e.getSource().getXPoint() == 111 && e.getSource().getYPoint() == 111) {
						if (myTime > e.getSource().getMessageType()) {
							System.err.println(com.getMyIp() + " 服务器状态 flag =  true");
							flag = true;
							//发送数据表示我方状态为true，且你方状态必须设置为false;
							com.sentData(new MyData(112, 112, 112));
						} else {
							//发送数据表示我方状态为false，且你方状态必须设置为true;
							com.sentData(new MyData(113, 113, 113));
							System.err.println(com.getMyIp() + " 服务器为 flag =  false");
							flag = false;
						}
					return;
				}

				if (e.getSource().getXPoint() == 112 && e.getSource().getYPoint() == 112) {
					System.err.println(com.getMyIp() + " 服务器为 flag =  false");
					flag = false;
					return;
				}

				if (e.getSource().getXPoint() == 113 && e.getSource().getYPoint() == 113) {
					System.err.println(com.getMyIp() + " 服务器为 flag =  true");
					flag = true;
					return;
				}

				flag = !flag;
				System.err.println("实现相关业务");
			}
		});

		//初始化发送一条消息，用于分配双方状态
		com.sentData(new MyData(111, 111, myTime));

		// 检测代码
		new Thread(() -> {
			int i;
			while (true) {
				if (flag == null) {
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					continue;
				}
				if (flag) {
					flag = !flag;
					final Scanner sc = new Scanner(System.in);
					final String sss = sc.nextLine();
					final MyData w = new MyData(Integer.parseInt(sss.split(" ")[0]),
							Integer.parseInt(sss.split(" ")[1]), Integer.parseInt(sss.split(" ")[2]));
					wu.add(w);
				}
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}).start();

	}

}
