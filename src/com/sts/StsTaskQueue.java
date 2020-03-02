package com.sts;

import java.util.ArrayList;
import java.util.List;

public class StsTaskQueue {

	private static StsTaskQueue instance = new StsTaskQueue(); 
	
	private StsTaskQueue(){
		
	}
	
	public static StsTaskQueue getInstance(){
		return instance;
	}
	
	//list<自定义类,暂时定义为String类型>串口请求队列,主线程发送缴费请求后存入
	public static List<String> stsRequestQueue = new ArrayList<String>();
	
	//list<自定义类,暂时定义为String类型>串口应答队列,串口应答后存入
	public static List<String> stsResponseQueue = new ArrayList<String>();
	
	public void addStsRequestQueue(String item){
		synchronized(stsRequestQueue){
			stsRequestQueue.add(item);
		}
	}
	
	public void removeStsRequestQueue(String item){
		synchronized(stsRequestQueue){
			if(stsRequestQueue.indexOf(item) >= 0){
				stsRequestQueue.remove(stsRequestQueue.indexOf(item));
			}
		}
	}
	
	public void addStsResponseQueue(String item){
		synchronized(stsResponseQueue){
			stsResponseQueue.add(item);
		} 
	}
	
	public void removeStsResponseQueue(int index){
		synchronized(stsResponseQueue){
			if(stsResponseQueue.isEmpty() || index < 0){
				return;
			}
			else{
				stsResponseQueue.remove(index);
			}
		}
	}
	
	/**
	 * 请求队列是否为空
	 * */
	public boolean isRequestQueueEmpty(){
		synchronized(stsRequestQueue){
			if(stsRequestQueue.isEmpty()){
				return true;
			}
			else{
				return false;
			}
		}
	}
	
	/**
	 * 应答队列是否为空
	 * */
	public boolean isResponseQueueEmpty(){
		synchronized(stsResponseQueue){
			if(stsResponseQueue.isEmpty()){
				return true;
			}
			else{
				return false;
			}
		}
	}
	
	/**
	 * 判断该message对象在Request队列中是否存在,返回对象的索引位置
	 * */
	public int isRequestExist(String message){
		synchronized(stsRequestQueue){
			return stsRequestQueue.indexOf(message);
		}
	}
	
	/**
	 * 判断该message对象在Response队列中是否存在,返回对象的索引位置
	 * */
	public int isResponseExist(String message){
		synchronized(stsResponseQueue){
			return stsResponseQueue.indexOf(message);
		}
	}
	
	public String requestToString(){
		synchronized(stsRequestQueue){
			StringBuffer sbf = new StringBuffer();
			for(int i=0,nums=stsRequestQueue.size(); i<nums; i++){
				sbf.append(stsRequestQueue.get(i)).append(",");
			}
			return null;
		}
	}
	
	public String responseToString(){
		synchronized(stsResponseQueue){
			StringBuffer sbf = new StringBuffer();
			for(int i=0,nums=stsResponseQueue.size(); i<nums; i++){
				sbf.append(stsResponseQueue.get(i)).append(",");
			}
			return sbf.toString();
		}
	}
	
	
}
