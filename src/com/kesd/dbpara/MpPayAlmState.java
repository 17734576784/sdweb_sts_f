package com.kesd.dbpara;

import java.io.Serializable;

public class MpPayAlmState implements Serializable{

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	private static final long serialVersionUID = -6857111136358220117L;
	private Integer 	rtuId;			/*终端编号*/
	private Short		mpId;			/*总加组编号0-7，对应规约1-8*/
	
	private Byte		qrAl11State;	/*报警1-1确认状态(短信方式) 低6位(重试次数0~63) 高2位(0:初始态 1:成功 2:失败)*/
	private Byte		qrAl12State; 	/*报警1-2确认状态(声音方式)*/
	private Byte		qrAl13State; 	/*报警1-3确认状态(备用方式)*/	

	private Byte		qrAl21State; 	/*报警2-1确认状态(短信方式) 低6位(重试次数0~63) 高2位(0:初始态 1:成功 2:失败)*/
	private Byte		qrAl22State; 	/*报警2-2确认状态(声音方式)*/
	private Byte		qrAl23State; 	/*报警2-3确认状态(备用方式)*/	
						
	private Byte		qrFhzState;		/*分合闸确认状态*/
	private Byte		qrFhzRf1State;	/*分合闸确认状态(动力关联1)*/
	private Byte		qrFhzRf2State;	/*分合闸确认状态(动力关联2)*/
	private Byte		qrFzTimes;		/*分闸次数*/
	private Byte		qrFzRf1Times;	/*分闸次数(动力关联1)*/
	private Byte		qrFzRf2Times;	/*分闸次数(动力关联2)*/
	private Integer 	qrAl11Mdhmi;	/*报警1-1确认状态(短信方式) 发送时间*/
	private Integer 	qrAl12Mdhmi;	/*报警1-2确认状态(声音方式) 发送时间*/
	private Integer 	qrAl13Mdhmi;	/*报警1-3确认状态(备用方式) 发送时间*/

	private Integer 	qrAl21Mdhmi;	/*报警2-1确认状态(短信方式) 发送时间*/
	private Integer 	qrAl22Mdhmi;	/*报警2-2确认状态(声音方式) 发送时间*/
	private Integer 	qrAl23Mdhmi;	/*报警2-3确认状态(备用方式) 发送时间*/

	private Integer 	qrFhzMdhmi;		/*分合闸确认状态 发送时间*/
	private Integer 	cgFhzMdhmi;		/*成功分合闸时间-MMDDHHMI*/			
	private Integer 	qrAl11Uuid;		/*报警1-1确认状态(短信方式) UUID*/
	private Integer 	qrAl21Uuid;		/*报警2-1确认状态(短信方式) UUID*/

	private String 		outInfo;		/*信息输出*/

	public Integer getRtuId() {
		return rtuId;
	}
	public void setRtuId(Integer rtuId) {
		this.rtuId = rtuId;
	}
	public Short getMpId() {
		return mpId;
	}
	public void setMpId(Short mpId) {
		this.mpId = mpId;
	}
	public Byte getQrAl11State() {
		return qrAl11State;
	}
	public void setQrAl11State(Byte qrAl11State) {
		this.qrAl11State = qrAl11State;
	}
	public Byte getQrAl12State() {
		return qrAl12State;
	}
	public void setQrAl12State(Byte qrAl12State) {
		this.qrAl12State = qrAl12State;
	}
	public Byte getQrAl13State() {
		return qrAl13State;
	}
	public void setQrAl13State(Byte qrAl13State) {
		this.qrAl13State = qrAl13State;
	}
	public Byte getQrAl21State() {
		return qrAl21State;
	}
	public void setQrAl21State(Byte qrAl21State) {
		this.qrAl21State = qrAl21State;
	}
	public Byte getQrAl22State() {
		return qrAl22State;
	}
	public void setQrAl22State(Byte qrAl22State) {
		this.qrAl22State = qrAl22State;
	}
	public Byte getQrAl23State() {
		return qrAl23State;
	}
	public void setQrAl23State(Byte qrAl23State) {
		this.qrAl23State = qrAl23State;
	}
	public Byte getQrFhzState() {
		return qrFhzState;
	}
	public void setQrFhzState(Byte qrFhzState) {
		this.qrFhzState = qrFhzState;
	}
	public Byte getQrFzTimes() {
		return qrFzTimes;
	}
	public void setQrFzTimes(Byte qrFzTimes) {
		this.qrFzTimes = qrFzTimes;
	}
	public Integer getQrAl11Mdhmi() {
		return qrAl11Mdhmi;
	}
	public void setQrAl11Mdhmi(Integer qrAl11Mdhmi) {
		this.qrAl11Mdhmi = qrAl11Mdhmi;
	}
	public Integer getQrAl12Mdhmi() {
		return qrAl12Mdhmi;
	}
	public void setQrAl12Mdhmi(Integer qrAl12Mdhmi) {
		this.qrAl12Mdhmi = qrAl12Mdhmi;
	}
	public Integer getQrAl13Mdhmi() {
		return qrAl13Mdhmi;
	}
	public void setQrAl13Mdhmi(Integer qrAl13Mdhmi) {
		this.qrAl13Mdhmi = qrAl13Mdhmi;
	}
	public Integer getQrAl21Mdhmi() {
		return qrAl21Mdhmi;
	}
	public void setQrAl21Mdhmi(Integer qrAl21Mdhmi) {
		this.qrAl21Mdhmi = qrAl21Mdhmi;
	}
	public Integer getQrAl22Mdhmi() {
		return qrAl22Mdhmi;
	}
	public void setQrAl22Mdhmi(Integer qrAl22Mdhmi) {
		this.qrAl22Mdhmi = qrAl22Mdhmi;
	}
	public Integer getQrAl23Mdhmi() {
		return qrAl23Mdhmi;
	}
	public void setQrAl23Mdhmi(Integer qrAl23Mdhmi) {
		this.qrAl23Mdhmi = qrAl23Mdhmi;
	}
	public Integer getQrFhzMdhmi() {
		return qrFhzMdhmi;
	}
	public void setQrFhzMdhmi(Integer qrFhzMdhmi) {
		this.qrFhzMdhmi = qrFhzMdhmi;
	}

	public Integer getCgFhzMdhmi() {
		return cgFhzMdhmi;
	}
	public void setCgFhzMdhmi(Integer cgFhzMdhmi) {
		this.cgFhzMdhmi = cgFhzMdhmi;
	}
	public Integer getQrAl11Uuid() {
		return qrAl11Uuid;
	}
	public void setQrAl11Uuid(Integer qrAl11Uuid) {
		this.qrAl11Uuid = qrAl11Uuid;
	}
	public Integer getQrAl21Uuid() {
		return qrAl21Uuid;
	}
	public void setQrAl21Uuid(Integer qrAl21Uuid) {
		this.qrAl21Uuid = qrAl21Uuid;
	}
	public String getOutInfo() {
		return outInfo;
	}
	public void setOutInfo(String outInfo) {
		this.outInfo = outInfo;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Byte getQrFhzRf1State() {
		return qrFhzRf1State;
	}
	public void setQrFhzRf1State(Byte qrFhzRf1State) {
		this.qrFhzRf1State = qrFhzRf1State;
	}
	public Byte getQrFhzRf2State() {
		return qrFhzRf2State;
	}
	public void setQrFhzRf2State(Byte qrFhzRf2State) {
		this.qrFhzRf2State = qrFhzRf2State;
	}
	public Byte getQrFzRf1Times() {
		return qrFzRf1Times;
	}
	public void setQrFzRf1Times(Byte qrFzRf1Times) {
		this.qrFzRf1Times = qrFzRf1Times;
	}
	public Byte getQrFzRf2Times() {
		return qrFzRf2Times;
	}
	public void setQrFzRf2Times(Byte qrFzRf2Times) {
		this.qrFzRf2Times = qrFzRf2Times;
	}
	
}