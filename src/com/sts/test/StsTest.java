package com.sts.test;

import com.sts.StsAddRequestQueueTask;
import com.sts.StsDelRequestQueueTask;
import com.sts.StsTaskQueue;

public class StsTest {
	
	public static void main(String[] args) {
//		String[] items = {"ab.","sg.","ak.","lk.","sm."};
//		StsTaskQueue stsTaskQueue = StsTaskQueue.getInstance();
//		for(int i=0,nums=items.length; i<items.length;i++){
//			stsTaskQueue.addStsRequestQueue(null);
//		}
//		System.out.println(stsTaskQueue.stsRequestQueue);
//		Thread add_thread = new Thread(new StsAddRequestQueueTask()); 
//		Thread del_thread = new Thread(new StsDelRequestQueueTask()); 
//		add_thread.start();
//		del_thread.start();
//		
//		try {
//			add_thread.join();
//			del_thread.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.out.println(stsTaskQueue.stsRequestQueue);
		System.out.print(getStsAmount(180225));
		System.out.print(getStsAmount(getPoolRemain("7449")));
	}
	private static int getPoolRemain(String remainHex){
		int remainInt = Integer.parseInt(remainHex, 16);
		int e = (remainInt >>> 13) & 0x03;
		int m = remainInt & 0x3FFF;
		
		int t = 0;	//最终转换出来的钱数
		if(e == 0){
			t = pow(10, e) * m;
		}
		else{
			int t0 = pow(10, e) * m;
			int t1 = 0;
			int t2 = pow(2, 14);
			for(int i=1; i<=e; i++){
				t1 += (t2 * pow(10, i-1)); 
			}	
			t = t0 + t1;
		}
		return t;
	}
	
	//该方法只限于本算法计算(整数之间进行计算)
	private static int pow(int m, int n){		
		int i = 0, result = 1;
		while(i<n){
			result = result * m;
			i++;
		}
		return result;
	}	
	
	public static int getStsAmount(int val)
	{
		int e = 0, rate = val;
		
		if (rate >= 0 && rate <= 16383) 				e = 0;
		else if (rate >= 16384 && rate <= 180214)		e = 1;
		else if (rate > 180214 && rate < 180224)		e = 2;//误差
		else if (rate >= 180224 && rate <= 1818524)		e = 2;
		else if (rate > 1818524 && rate < 1818624)		e = 3;//误差
		else if (rate >= 1818624 && rate <= 18201624)	e = 3;
		
		int m = 0;
		if (e == 0) m = rate;
		
		int v = 0;
		for (int n = 1; n <= e; n++) {
			v += Math.pow(2, 14) *  Math.pow(10,n-1);
		}
		
		m = Math.min((int)Math.pow(2, 14)-1, ((int)Math.ceil((rate-v)/Math.pow(10,e))));
		
		//[plen++] = (byte)((e<<6) | m>>>8);
		//para[plen++] = (byte)(m & 0xff);
		int i1 = (byte)((e<<6) | m>>>8);
		int i2 = (byte)(m & 0xff);
		
		i1 = (i1<0 ? 256+i1 : i1);
		i2 = (i2<0 ? 256+i2 : i2);
		
		return i1*256 + i2;
	}	
}
