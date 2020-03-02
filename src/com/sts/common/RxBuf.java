package com.sts.common;

public class RxBuf {
	static int head;
	static int tail;
	static byte[] rx_buf = new byte[2048];
	
	public static void byte_write(byte b) {
		rx_buf[tail] = b;
		tail = (tail+1) % 2048;
	}
	
	public static byte byte_read() {
		byte ret = 0;
		ret = rx_buf[head];
		head = (head+1) % 2048;
		return ret;
	}
}
