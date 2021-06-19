package com.bleeth.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import com.bleeth.event.NetData;

public class NetWorkScanner {

	public static void main(String[] args) throws InterruptedException {
		NetWorkScanner b  = new NetWorkScanner();
		b.scan();
		b.getIP(8899);
	}

	public static LinkedBlockingQueue<String> arrayIP = new LinkedBlockingQueue<>();
	public static LinkedBlockingQueue<String> userIP = new LinkedBlockingQueue<>();
	public static LinkedBlockingQueue<String> goodIP = new LinkedBlockingQueue<>();

	public static String getLocalIP() {
		String localIP = null;
		try {
			localIP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return localIP;
	}

	public static LinkedBlockingQueue<String> getLanIP() {
		int firstIP = 2;
		int lastIP = 255;
		String localIP = getLocalIP();

		int lastPointIndex = localIP.lastIndexOf('.');
		String stringIPHead = localIP.substring(0, ++lastPointIndex);
		String stringIP = null;
		try {
			for (int i = firstIP; i <= lastIP; i++) {
				stringIP = stringIPHead + i;
				arrayIP.put(stringIP);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrayIP;
	}

	public Boolean scan() throws InterruptedException {
		long before = System.currentTimeMillis();
		arrayIP = getLanIP();
		ExecutorService executor = Executors.newFixedThreadPool(30);
		while (!arrayIP.isEmpty()) {
			Runnable worker = new IPScannerThread(arrayIP.take());
			executor.execute(worker);
		}
		executor.shutdown();
		while (!executor.isTerminated())
			;
		long after = System.currentTimeMillis();
		System.err.println("用时 = " + (after - before) / 1000);
		return true;
	}

	private boolean isUsedIPAddress(String ip) {
		Process process = null;
		BufferedReader bufReader = null;
		String bufReadLineString = null;
		try {
			process = Runtime.getRuntime().exec("ping " + ip + " -w 100 -n 1");
			bufReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
			for (int i = 0; i < 6 && bufReader != null; i++) {
				bufReader.readLine();
			}
			bufReadLineString = bufReader.readLine();
			if (bufReadLineString == null) {
				process.destroy();
				return false;
			}
			if (bufReadLineString.indexOf("timed out") > 0 || bufReadLineString.length() < 17
					|| bufReadLineString.indexOf("invalid") > 0) {
				process.destroy();
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		process.destroy();
		return true;
	}

	class IPScannerThread implements Runnable {

		String ip;

		public IPScannerThread(String ip) {
			this.ip = ip;
		}

		@Override
		public void run() {
			if (isUsedIPAddress(ip)) {
				try {
					userIP.put(ip);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}



	public Boolean getIP(int port){
		System.err.println("getIP");
		Thread th = new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					while(!arrayIP.isEmpty()^!userIP.isEmpty()){
						String ip = userIP.take();
						System.err.println(ip);
						if(isGoodIP(ip, port)){
							System.err.println("good ip"+ip);
							goodIP.add(ip);
						}
					}
				}catch(Exception e){

				}
			}
		});

		th.start();
		while(th.isAlive());
		return true;

	}

	Boolean isGoodIP(String ip, int port) {
		try {
			Socket client = new Socket(ip, port);
			client.setSoTimeout(2000);
			final ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			out.writeObject(new NetData(200, 200, 200));
			final InputStream is = client.getInputStream();
			final ObjectInputStream ois = new ObjectInputStream(is);
			NetData md = (NetData) ((ObjectInputStream) ois).readObject();
			if (client != null) {
				client.close();
			}
			if (ois != null) {
				ois.close();
			}
			if (out != null) {
				out.close();
			}
			System.err.println(md);
			if (md.getXPoint() == 200) {
				return true;
			}
		} catch (IOException e) {
			return false;
		} catch (ClassNotFoundException e) {
			return false;
		}
		return false;
	}



}
