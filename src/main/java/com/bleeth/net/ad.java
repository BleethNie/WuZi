package com.bleeth.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.bleeth.event.MyData;

public class ad {

	public static void main(String[] args) throws UnknownHostException, IOException {
		ad a = new ad();
		System.err.println(a.isGoodIP("", 8899));
	}

	Boolean isGoodIP(String ip, int port) {
		try {
			Socket client = new Socket("192.168.1.104", port);
			final ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
			out.writeObject(new MyData(200, 200, 200));
			final InputStream is = client.getInputStream();
			final ObjectInputStream ois = new ObjectInputStream(is);
			MyData md = (MyData) ((ObjectInputStream) ois).readObject();
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
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
