package com.bleeth.net;

import java.net.InetAddress;

public class NetUtils {
	
	public static String[] getAllLocalHostIP() {

		String[] ret = null;
		try {
			final String hostName = getLocalHostName();
			if (hostName.length() > 0) {
				final InetAddress[] addrs = InetAddress.getAllByName(hostName);
				if (addrs.length > 0) {
					ret = new String[addrs.length];
					for (int i = 0; i < addrs.length; i++) {
						ret[i] = addrs[i].getHostAddress();
					}
				}
			}
		} catch (final Exception ex) {
			ret = null;
		}

		return ret;
	}

	public static String getLocalHostName() {
		String hostName;
		try {
			final InetAddress addr = InetAddress.getLocalHost();
			hostName = addr.getHostName();
		} catch (final Exception ex) {
			hostName = "";
		}

		return hostName;
	}

	
}
