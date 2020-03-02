package com.kesd.dbpara;

public class RtuModel {
	private Short 	id;			//终端型号编号
	private String 	describe;	//名称
	private Short 	dataCap;	//数据容量
	private Short 	factory;	//生产厂家
	private Byte 	chanType1;	//通道类型1
	private Byte 	chanType2;	//通道类型2
	private Byte 	meterPort;	//电表端口号
	private Short 	safeInter;	//心跳周期
	private Byte 	prot;		//通信协议
	private Byte 	pwdFlag;	//通信加密
	private Byte 	eveReport;	//事件主动上报
	private Byte 	taskReport;	//任务主动上报
	private Byte 	addrJz;		//地址录入进制
	private Byte 	pwdId;		//密码算法编号
	private Short 	keyId;		//密钥
	private Byte 	jcFlag;		//交流采样
	private Byte 	ycFlag;		//远方抄表
	private Byte 	dyjcFlag;	//电压监测
	private Byte 	xbjcFlag;	//谐波监测
	private Byte 	pbjcFlag;	//配变监测
	private Byte 	bdzjcFlag;	//变电站监测
	private Byte 	jcId;		//交采编号
	private Byte 	jcflNum;	//交采费率个数
	private Byte 	dndataInt;	//电能示值整数
	private Byte 	dndataDec;	//电能示值小数
	private Short 	meterNum;	//采集电表总数
	private Byte 	dayDataCap; //日数据最大容量
	private Byte 	monDataCap; //月数据最大容量
	
	public Short getId() {
		return id;
	}
	public void setId(Short id) {
		this.id = id;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public Short getDataCap() {
		return dataCap;
	}
	public void setDataCap(Short dataCap) {
		this.dataCap = dataCap;
	}
	public Short getFactory() {
		return factory;
	}
	public void setFactory(Short factory) {
		this.factory = factory;
	}
	public Short getSafeInter() {
		return safeInter;
	}
	public Byte getChanType1() {
		return chanType1;
	}
	public void setChanType1(Byte chanType1) {
		this.chanType1 = chanType1;
	}
	public Byte getChanType2() {
		return chanType2;
	}
	public void setChanType2(Byte chanType2) {
		this.chanType2 = chanType2;
	}
	public Byte getMeterPort() {
		return meterPort;
	}
	public void setMeterPort(Byte meterPort) {
		this.meterPort = meterPort;
	}
	public void setSafeInter(Short safeInter) {
		this.safeInter = safeInter;
	}
	public Byte getProt() {
		return prot;
	}
	public void setProt(Byte prot) {
		this.prot = prot;
	}
	public Byte getPwdFlag() {
		return pwdFlag;
	}
	public void setPwdFlag(Byte pwdFlag) {
		this.pwdFlag = pwdFlag;
	}
	public Byte getEveReport() {
		return eveReport;
	}
	public void setEveReport(Byte eveReport) {
		this.eveReport = eveReport;
	}
	public Byte getTaskReport() {
		return taskReport;
	}
	public void setTaskReport(Byte taskReport) {
		this.taskReport = taskReport;
	}
	public Byte getAddrJz() {
		return addrJz;
	}
	public void setAddrJz(Byte addrJz) {
		this.addrJz = addrJz;
	}
	public Byte getPwdId() {
		return pwdId;
	}
	public void setPwdId(Byte pwdId) {
		this.pwdId = pwdId;
	}
	public Short getKeyId() {
		return keyId;
	}
	public void setKeyId(Short keyId) {
		this.keyId = keyId;
	}
	public Byte getJcFlag() {
		return jcFlag;
	}
	public void setJcFlag(Byte jcFlag) {
		this.jcFlag = jcFlag;
	}
	public Byte getYcFlag() {
		return ycFlag;
	}
	public void setYcFlag(Byte ycFlag) {
		this.ycFlag = ycFlag;
	}
	public Byte getDyjcFlag() {
		return dyjcFlag;
	}
	public void setDyjcFlag(Byte dyjcFlag) {
		this.dyjcFlag = dyjcFlag;
	}
	public Byte getXbjcFlag() {
		return xbjcFlag;
	}
	public void setXbjcFlag(Byte xbjcFlag) {
		this.xbjcFlag = xbjcFlag;
	}
	public Byte getPbjcFlag() {
		return pbjcFlag;
	}
	public void setPbjcFlag(Byte pbjcFlag) {
		this.pbjcFlag = pbjcFlag;
	}
	public Byte getBdzjcFlag() {
		return bdzjcFlag;
	}
	public void setBdzjcFlag(Byte bdzjcFlag) {
		this.bdzjcFlag = bdzjcFlag;
	}
	public Byte getJcId() {
		return jcId;
	}
	public void setJcId(Byte jcId) {
		this.jcId = jcId;
	}
	public Byte getJcflNum() {
		return jcflNum;
	}
	public void setJcflNum(Byte jcflNum) {
		this.jcflNum = jcflNum;
	}
	public Byte getDndataInt() {
		return dndataInt;
	}
	public void setDndataInt(Byte dndataInt) {
		this.dndataInt = dndataInt;
	}
	public Byte getDndataDec() {
		return dndataDec;
	}
	public void setDndataDec(Byte dndataDec) {
		this.dndataDec = dndataDec;
	}
	public Short getMeterNum() {
		return meterNum;
	}
	public void setMeterNum(Short meterNum) {
		this.meterNum = meterNum;
	}
	public Byte getDayDataCap() {
		return dayDataCap;
	}
	public void setDayDataCap(Byte dayDataCap) {
		this.dayDataCap = dayDataCap;
	}
	public Byte getMonDataCap() {
		return monDataCap;
	}
	public void setMonDataCap(Byte monDataCap) {
		this.monDataCap = monDataCap;
	}
}
