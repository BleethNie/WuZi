package com.bleeth.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import com.bleeth.event.MyData;

public class Server  implements Runnable {

	private Boolean shutdown ;
	private String otherIp = "";
	private MyData wu;
	final int port ;

	public  Server(MyData data,String ip,int port){
		shutdown = false;
		wu = data;
		otherIp = ip;
		this.port = port;
	}

		@Override
		public void run() {
			System.err.println("服务器正在启动");
			ServerSocket serverSocket = null;

			try {
				serverSocket = new ServerSocket(port);
			} catch (final IOException e) {
				e.printStackTrace();
				System.exit(1);
			}

			while (!shutdown) {
				try {
					System.err.println("服务器正在监听 本机" + port+" 端口");
					final Socket socket = serverSocket.accept();
					final InputStream inputStream = socket.getInputStream();
					final ObjectInputStream oInputStream = new ObjectInputStream(inputStream);
					MyData md = parse(oInputStream);
					System.err.println("解析出来的WuData为 " + md.toString());
					if(md.getXPoint()==200){
						final ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
						out.writeObject(md);
					}else if(md.getXPoint()==-1){
						shutdown = true;
						sentData(new MyData(-1,-1,-1));
					}else{
						wu.receive(md);
					}

					socket.close();
				} catch (final Exception e) {
					e.printStackTrace();
					continue;
				}
			}

			System.err.println("服务器已经关闭");
		}



		public  MyData parse(final InputStream input) throws ClassNotFoundException, IOException {
			if (input instanceof ObjectInputStream) {
				return (MyData) ((ObjectInputStream) input).readObject();
			}
			return null;
		}


		public void sentData(final MyData WuData){
			new Thread(new Runnable(){
				public void run(){
					try{
						System.err.println("发送数据 "+WuData.toString()+" 到的IP为 = "+otherIp);
						final Socket client = new Socket(otherIp, port);
						final ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
						out.writeObject(WuData);
						out.close();
						if (client != null) {
							client.close();
						}
					}catch(Exception  e){
						System.err.println("数据发送失败");
					}
				}
			}).start();
		}

		public String getMyIp(){
				String localIP = null;
				try {
					localIP = InetAddress.getLocalHost().getHostAddress();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				return localIP;
			}
	}

