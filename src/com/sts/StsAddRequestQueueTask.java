package com.sts;

public class StsAddRequestQueueTask implements Runnable {
	
	private StsTaskQueue stsTaskQueue;
	private String message;				//添加到stsTaskQueue的数据			
	
	public StsAddRequestQueueTask(){
		
	}
	
	public StsAddRequestQueueTask(String message){
		this.message = message;
	}
	
	public void run() {
		//web端收到缴费、开户信息，存入stsRequestQueue队列。
		stsTaskQueue = StsTaskQueue.getInstance();
		stsTaskQueue.addStsRequestQueue(message);
	}

}
