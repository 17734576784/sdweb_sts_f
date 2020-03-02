package com.sts.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import gnu.io.SerialPort;
import com.kesd.common.SDDef;
import com.kesd.common.WebConfig;
import com.sts.StsTaskQueue;

public class SerialOperation implements Observer {
	
	//静态类内部加载单例模式
	private static class SerialOperationHandler{
		private static SerialOperation instance = new SerialOperation();
	}
	
	private SerialOperation(){
		
	}
	
	public static SerialOperation getInstance(){
		return SerialOperationHandler.instance;
	}
	
	private static SerialReader sr = null;
	
	static{
		//在该类被ClassLoader加载时就初始化一个SerialReader对象
		if(sr == null){
			sr = new SerialReader();
		}
	}

	private static HashMap<String, Comparable> initSerialParam(){
		HashMap<String, Comparable> params = new HashMap<String, Comparable>();

		String port = WebConfig.encryptModuleParam.commport;
		String rate = WebConfig.encryptModuleParam.baudrate;
		String dataBit = WebConfig.encryptModuleParam.databit;
		String stopBit = WebConfig.encryptModuleParam.stopbit;
		int parityInt = SerialPort.PARITY_NONE;

		params.put(SerialReader.PARAMS_PORT, port); // 端口名称
		params.put(SerialReader.PARAMS_RATE, rate); // 波特率
		params.put(SerialReader.PARAMS_DATABITS, dataBit); // 数据位
		params.put(SerialReader.PARAMS_STOPBITS, stopBit); // 停止位
		params.put(SerialReader.PARAMS_PARITY, parityInt); // 无奇偶校验
		params.put(SerialReader.PARAMS_TIMEOUT, 100); // 设备超时时间 1秒
		params.put(SerialReader.PARAMS_DELAY, 100); // 端口数据准备时间 1秒
		return params;
	}
	
	private StringBuffer serialResult = null;
	 
	
	public String getSerialResult(){
		if(this.serialResult.length() > 0){
			return this.serialResult.toString();
		}
		else{
			return null;
		}
	} 
	
	/**
	 * 接收串口返回数据，在此处处理串口返回数据
	 * 应该是监听端口，发生变化时取值 
	 * */
	public void update(Observable o, Object arg) {
		byte[] result = (byte[]) arg;
		serialResult = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			serialResult.append((char) result[i]);
			RxBuf.byte_write(result[i]);
		}
		
		//将返回数据放入stsResponseQueue队列,另起一个读线程进行操作处理。
		StsTaskQueue stsTaskQueue = StsTaskQueue.getInstance();
		stsTaskQueue.addStsResponseQueue(serialResult.toString().trim());
	}

	/**
	 * 往串口发送数据,实现双向通讯.
	 * 
	 * @param string
	 * 		  message
	 */
	@SuppressWarnings("finally")
	public synchronized Map<String, String> send(byte[] message) {
		sr.open(initSerialParam());
		sr.addObserver(this);
		Map<String, String> map = new HashMap<String, String>();
		try {
			sr.start();
			sr.run(message);
			Thread.sleep(1000);	//线程休眠1s，等待返回完毕后再关闭
			map.put("errorCode", SDDef.SUCCESS);
			map.put("errorDesc", SDDef.EMPTY);
		} catch (InterruptedException e) {
			map.put("errorCode", SDDef.FAIL);
			map.put("errorDesc", e.getMessage());
		}
		finally{
			sr.close();
			return map;
		}
	}
	
	public static void main(String[] args) {
		SerialOperation test = new SerialOperation();
		String str = StsEncryptor.api_GetIden_Req();
		byte[] frame = StsEncryptor.makeFrame(StsEncryptor.makeTail(str, str.length()));
		
//		for(int i=0; i<10; i++){
			test.send(frame);
//		}
	}
}
