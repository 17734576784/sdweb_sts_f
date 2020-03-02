package com.kesd.dbpara;

import java.io.Serializable;

public class ZjgPaypara implements Serializable{
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
	private static final long serialVersionUID = -3814847666202273814L;
	private		Integer		rtuId;		    	/*终端编号*/    
    private		Short 		zjgId;				/*测量点编号*/      
                                                            
    private		Byte	  	useFlag;			/*使用标志*/      
    private		Byte	  	caclType;			/*计费方式 0:无 1:金额计费 2:表底计费 */
    private		Byte		feectrlType;		/*费控方式*/        
    private		Byte	  	payType;			/*缴费方式*/
    
    private 	String		writecardNo;		/*写卡户号 -主要6103 智能表也可 同186*/
    private		Byte		cardmeterType;		/*卡表类型*/
    private 	Byte		yffctrlType;		/*预付费控制类型*/
    
    private		Short		feeprojId;			/*测量点1-费率方案*/
    private		Short		feeproj1Id;			/*测量点2-费率方案*/
    private		Short		feeproj2Id;			/*测量点3-费率方案*/
	
    private		Short		yffalarmId;			/*报警方案1*/
    private 	Integer 	feeBegindate;		/*费率启用日期YYYYMMDD*/
	
    private		Byte		protSt;				/*保电开始时间*/    
    private		Byte		protEd;				/*保电结束时间*/	
    
    private		Byte		ngloprotFlag; 		/*不参与全局保电标志*/
	private		Byte		keyVersion;			/*密钥版本, 单用于智能表*/   
	private 	Byte		cryplinkId;			/*所属加密机ID, 单用于智能表*/
	
    private		Double		payAdd1;			/*缴费附加值1*/
    private		Double		payAdd2;			/*缴费附加值2*/
    private		Double		payAdd3;			/*缴费附加值3*/
    
    private		Integer		tzVal;				/*透支值*/

    private		Short		plusTime;			/*负荷脉冲闭合时间*/
    private		Byte		useGfh;				/*是否启用高负荷控制*/
    private		Short		hfhTime;			/*连续高负荷时间*/
    private		Integer		hfhShutdown;		/*高负荷断电值*/
    private		Byte		csStand;			/*功率因数标准*/
    
    
	//20120808 增加力调 start
    private		Byte		powrateFlag;		/*力调算费标志 	0 不执行， 		1 执行*/
    private		Byte		prizeFlag;			/*奖罚标志   		0 执行奖罚， 	1 只罚不奖*/
    private		Byte		stopFlag;			/*报停标志   		0 不报停， 		1 报停*/
    private		Integer		stopBegdate;		/*报停开始日期*/
    private		Integer		stopEnddate;		/*报停结束日期*/
    //20120808 增加力调 end
    
    
	//费率更改
    private		Byte		feeChgf;			/*费率更改标准*/
    private		Short		feeChgid;			/*测量点1-费率更改ID*/
    private		Short		feeChgid1;			/*测量点2-费率更改ID*/
    private		Short		feeChgid2;			/*测量点3-费率更改ID*/	
    private		Integer		feeChgdate;			/*费率更改日期*/
    private		Integer		feeChgtime;			/*费率更改时间*/
	
	//基本费更改
    private		Byte		jbfChgf;			/*基本费更改标准*/
	private		Double		jbfChgval;			/*基本费更改值*/	
	private		Integer		jbfChgdate;			/*基本费更改日期*/
	private		Integer		jbfChgtime;			/*基本费更改时间*/

	
	private		Byte 		cbCycleType;		/*抄表周期类型 0:每月抄表 1:双月抄表  2:单月抄表 3:季度抄表 4：半年抄表 5：年抄表*/
	private		Short		cbDayhour;			/*抄表日DDHH*/
	private		Byte		jsDay;				/*结算日*/
	private 	Byte		cbjsFlag;			/*抄表日结算标志0 不结算， 1 结算*/
	private		Byte 		fxdfFlag;			/*是否发行电费*/
	private		Integer		fxdfBegindate;		/*发行电费起始日期*/
	private		Byte 		localMaincalcf;		/*主站算费标志	0 不算费， 1 算费*/
	
	
	private 	String		reserve1; 			
    

	public Integer getRtuId() {
		return rtuId;
	}
	public void setRtuId(Integer rtuId) {
		this.rtuId = rtuId;
	}
	public Short getZjgId() {
		return zjgId;
	}
	public void setZjgId(Short zjgId) {
		this.zjgId = zjgId;
	}
	public Byte getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(Byte useFlag) {
		this.useFlag = useFlag;
	}
	
	public Byte getFeectrlType() {
		return feectrlType;
	}
	public void setFeectrlType(Byte feectrlType) {
		this.feectrlType = feectrlType;
	}
	
	public Double getPayAdd1() {
		return payAdd1;
	}
	public void setPayAdd1(Double payAdd1) {
		this.payAdd1 = payAdd1;
	}
	public Double getPayAdd2() {
		return payAdd2;
	}
	public void setPayAdd2(Double payAdd2) {
		this.payAdd2 = payAdd2;
	}
	public Double getPayAdd3() {
		return payAdd3;
	}
	public void setPayAdd3(Double payAdd3) {
		this.payAdd3 = payAdd3;
	}
	
	public Byte getPayType() {
		return payType;
	}
	public void setPayType(Byte payType) {
		this.payType = payType;
	}
		
	public Byte getProtSt() {
		return protSt;
	}
	public void setProtSt(Byte protSt) {
		this.protSt = protSt;
	}
	public Byte getProtEd() {
		return protEd;
	}
	public void setProtEd(Byte protEd) {
		this.protEd = protEd;
	}
	
	public Short getPlusTime() {
		return plusTime;
	}
	public void setPlusTime(Short plusTime) {
		this.plusTime = plusTime;
	}
	public Byte getUseGfh() {
		return useGfh;
	}
	public void setUseGfh(Byte useGfh) {
		this.useGfh = useGfh;
	}
	
	public Byte getYffctrlType() {
		return yffctrlType;
	}
	public void setYffctrlType(Byte yffctrlType) {
		this.yffctrlType = yffctrlType;
	}
	public Short getHfhTime() {
		return hfhTime;
	}
	public void setHfhTime(Short hfhTime) {
		this.hfhTime = hfhTime;
	}
	
	public Integer getHfhShutdown() {
		return hfhShutdown;
	}
	public void setHfhShutdown(Integer hfhShutdown) {
		this.hfhShutdown = hfhShutdown;
	}
	public Byte getCsStand() {
		return csStand;
	}
	public void setCsStand(Byte csStand) {
		this.csStand = csStand;
	}
	public Byte getPowrateFlag() {
		return powrateFlag;
	}
	public void setPowrateFlag(Byte powrateFlag) {
		this.powrateFlag = powrateFlag;
	}
	public Byte getPrizeFlag() {
		return prizeFlag;
	}
	public void setPrizeFlag(Byte prizeFlag) {
		this.prizeFlag = prizeFlag;
	}
	public Byte getStopFlag() {
		return stopFlag;
	}
	public void setStopFlag(Byte stopFlag) {
		this.stopFlag = stopFlag;
	}
	public Integer getStopBegdate() {
		return stopBegdate;
	}
	public void setStopBegdate(Integer stopBegdate) {
		this.stopBegdate = stopBegdate;
	}
	public Integer getStopEnddate() {
		return stopEnddate;
	}
	public void setStopEnddate(Integer stopEnddate) {
		this.stopEnddate = stopEnddate;
	}
	public Byte getCaclType() {
		return caclType;
	}
	public void setCaclType(Byte caclType) {
		this.caclType = caclType;
	}
	public Short getFeeprojId() {
		return feeprojId;
	}
	public void setFeeprojId(Short feeprojId) {
		this.feeprojId = feeprojId;
	}
	public Short getFeeproj1Id() {
		return feeproj1Id;
	}
	public void setFeeproj1Id(Short feeproj1Id) {
		this.feeproj1Id = feeproj1Id;
	}
	public Short getFeeproj2Id() {
		return feeproj2Id;
	}
	public void setFeeproj2Id(Short feeproj2Id) {
		this.feeproj2Id = feeproj2Id;
	}
	public Short getYffalarmId() {
		return yffalarmId;
	}
	public void setYffalarmId(Short yffalarmId) {
		this.yffalarmId = yffalarmId;
	}
	public Byte getNgloprotFlag() {
		return ngloprotFlag;
	}
	public void setNgloprotFlag(Byte ngloprotFlag) {
		this.ngloprotFlag = ngloprotFlag;
	}
	public Integer getTzVal() {
		return tzVal;
	}
	public void setTzVal(Integer tzVal) {
		this.tzVal = tzVal;
	}
	public Byte getFeeChgf() {
		return feeChgf;
	}
	public void setFeeChgf(Byte feeChgf) {
		this.feeChgf = feeChgf;
	}
	public Short getFeeChgid() {
		return feeChgid;
	}
	public void setFeeChgid(Short feeChgid) {
		this.feeChgid = feeChgid;
	}
	public Short getFeeChgid1() {
		return feeChgid1;
	}
	public void setFeeChgid1(Short feeChgid1) {
		this.feeChgid1 = feeChgid1;
	}
	public Short getFeeChgid2() {
		return feeChgid2;
	}
	public void setFeeChgid2(Short feeChgid2) {
		this.feeChgid2 = feeChgid2;
	}
	public Integer getFeeChgdate() {
		return feeChgdate;
	}
	public void setFeeChgdate(Integer feeChgdate) {
		this.feeChgdate = feeChgdate;
	}
	public Integer getFeeChgtime() {
		return feeChgtime;
	}
	public void setFeeChgtime(Integer feeChgtime) {
		this.feeChgtime = feeChgtime;
	}
	public Byte getJbfChgf() {
		return jbfChgf;
	}
	public void setJbfChgf(Byte jbfChgf) {
		this.jbfChgf = jbfChgf;
	}
	public Double getJbfChgval() {
		return jbfChgval;
	}
	public void setJbfChgval(Double jbfChgval) {
		this.jbfChgval = jbfChgval;
	}
	public Integer getJbfChgdate() {
		return jbfChgdate;
	}
	public void setJbfChgdate(Integer jbfChgdate) {
		this.jbfChgdate = jbfChgdate;
	}
	public Integer getJbfChgtime() {
		return jbfChgtime;
	}
	public void setJbfChgtime(Integer jbfChgtime) {
		this.jbfChgtime = jbfChgtime;
	}
	public String getReserve1() {
		return reserve1;
	}
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	public Byte getKeyVersion() {
		return keyVersion;
	}
	public void setKeyVersion(Byte keyVersion) {
		this.keyVersion = keyVersion;
	}
	public Byte getCryplinkId() {
		return cryplinkId;
	}
	public void setCryplinkId(Byte cryplinkId) {
		this.cryplinkId = cryplinkId;
	}
	public Integer getFeeBegindate() {
		return feeBegindate;
	}
	public void setFeeBegindate(Integer feeBegindate) {
		this.feeBegindate = feeBegindate;
	}
	public Byte getCbCycleType() {
		return cbCycleType;
	}
	public void setCbCycleType(Byte cbCycleType) {
		this.cbCycleType = cbCycleType;
	}
	public Short getCbDayhour() {
		return cbDayhour;
	}
	public void setCbDayhour(Short cbDayhour) {
		this.cbDayhour = cbDayhour;
	}
	public Byte getJsDay() {
		return jsDay;
	}
	public void setJsDay(Byte jsDay) {
		this.jsDay = jsDay;
	}
	public Byte getFxdfFlag() {
		return fxdfFlag;
	}
	public void setFxdfFlag(Byte fxdfFlag) {
		this.fxdfFlag = fxdfFlag;
	}
	public Integer getFxdfBegindate() {
		return fxdfBegindate;
	}
	public void setFxdfBegindate(Integer fxdfBegindate) {
		this.fxdfBegindate = fxdfBegindate;
	}
	public Byte getLocalMaincalcf() {
		return localMaincalcf;
	}
	public void setLocalMaincalcf(Byte localMaincalcf) {
		this.localMaincalcf = localMaincalcf;
	}
	public String getWritecardNo() {
		return writecardNo;
	}
	public void setWritecardNo(String writecardNo) {
		this.writecardNo = writecardNo;
	}
	public Byte getCbjsFlag() {
		return cbjsFlag;
	}
	public void setCbjsFlag(Byte cbjsFlag) {
		this.cbjsFlag = cbjsFlag;
	}
	public Byte getCardmeterType() {
		return cardmeterType;
	}
	public void setCardmeterType(Byte cardmeterType) {
		this.cardmeterType = cardmeterType;
	}

	
}