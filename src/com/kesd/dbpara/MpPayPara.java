package com.kesd.dbpara;

import java.io.Serializable;

public class MpPayPara implements Serializable{
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
	private static final long serialVersionUID = -7663425015701050678L;
	private Integer rtuId;			/*终端编号*/	
	private Short 	mpId;			/*计量点编号*/
	private Byte    useFlag;		/*使用标志*/
	private Byte	caclType;		/*算费类型 0:无 1:金额计费 2:表底计费 */
	private Byte 	feectrlType;	/*费控方式--智能电表本地 远程*/
	private Byte 	payType;		/*缴费方式*/
	private String 	writecardNo;	/*写卡户号*/
	private Byte	yffmeterType;	/*预付费表类型	--hzhw 20111119 售电升级	*/
	private Short	feeprojId;		/*费率方案*/
	private Short	yffalarmId;		/*报警方案*/
	
	private Integer feeBegindate;	/*费率启用日期*/
	private	Byte	protSt;			/*保电开始时间*/    
    private	Byte	protEd;			/*保电结束时间*/
    
	private Byte	ngloprotFlag;	/*不参与全局保电标志*/
	private	Byte	keyVersion;		/*密钥版本, 单用于智能表*/   
	//private	Byte	tariffIndex;	/*Tariff Index费率指数*/ 

	private Byte	cryplinkId;		/*所属加密机ID, 单用于智能表*/
	
	private Integer	payAdd;			/*缴费附加值*/	
	private Integer tzVal;			/*透支值*/

	private Byte	powerRelaf;		/*动力关联标志*/
	private Short	powerRela1;		/*动力关联1 与其它表一起算费*/
	private Short	powerRela2;		/*动力关联2 与其它表一起算费*/
	private Byte	powerRelask1;	/*动力关联1可控标志*/
	private Byte	powerRelask2;	/*动力关联2可控标志*/

	//费率更改
	private Byte	feeChgf;		/*费率更改标志*/
	private Short	feeChgid;		/*费率更改ID*/
	private Integer feeChgdate;		/*费率更改日期*/
	private Integer feeChgtime;		/*费率更改时间*/
	
	private Short 	jtCycleMd;		/*周期切换时间MMDD*/
	private Byte 	cbCycleType;	/*抄表周期类型 0:每月抄表 1:双月抄表  2:单月抄表 3:季度抄表 4：半年抄表 5：年抄表*/
	private Short 	cbDayhour;		/*抄表日DDHH*/
	private Byte 	jsDay;			/*结算日*/
	
	private Byte 	fxdfFlag;		/*是否发行电费*/
	private Integer	fxdfBegindate;	/*发行电费起始日期*/
	private Byte 	localMaincalcf;/*主站算费标志	0 不算费， 1 算费*/
	
	//--20140606 新增外接表 zp
	private Integer ocardprojId;	/*外接卡表类型*/
	private String 	cardRand;		/*随机数*/	/* 现在是外接卡表使用*/
	private String 	cardPass;		/*密码*/		/* 现在是外接卡表使用*/
	private String 	cardArea;		/*区域码*/	/* 现在是外接卡表使用*/
	//--20140606 zkz end
   
	private Integer	reserve1;	
	private Integer	reserve2;
	private Integer	reserve3;
	
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
	
	public Short getPowerRela1() {
		return powerRela1;
	}
	public void setPowerRela1(Short powerRela1) {
		this.powerRela1 = powerRela1;
	}
	public Short getPowerRela2() {
		return powerRela2;
	}
	public void setPowerRela2(Short powerRela2) {
		this.powerRela2 = powerRela2;
	}
	
	public Byte getKeyVersion() {
		return keyVersion;
	}
	public void setKeyVersion(Byte keyVersion) {
		this.keyVersion = keyVersion;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Byte getCaclType() {
		return caclType;
	}
	public void setCaclType(Byte caclType) {
		this.caclType = caclType;
	}
	public Byte getYffmeterType() {
		return yffmeterType;
	}
	public void setYffmeterType(Byte yffmeterType) {
		this.yffmeterType = yffmeterType;
	}
	public Short getFeeprojId() {
		return feeprojId;
	}
	public void setFeeprojId(Short feeprojId) {
		this.feeprojId = feeprojId;
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
	public Byte getCryplinkId() {
		return cryplinkId;
	}
	public void setCryplinkId(Byte cryplinkId) {
		this.cryplinkId = cryplinkId;
	}
	public Integer getPayAdd() {
		return payAdd;
	}
	public void setPayAdd(Integer payAdd) {
		this.payAdd = payAdd;
	}
	public Integer getTzVal() {
		return tzVal;
	}
	public void setTzVal(Integer tzVal) {
		this.tzVal = tzVal;
	}
	public Byte getPowerRelaf() {
		return powerRelaf;
	}
	public void setPowerRelaf(Byte powerRelaf) {
		this.powerRelaf = powerRelaf;
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
	public Byte getPayType() {
		return payType;
	}
	public void setPayType(Byte payType) {
		this.payType = payType;
	}
	public Byte getPowerRelask1() {
		return powerRelask1;
	}
	public void setPowerRelask1(Byte powerRelask1) {
		this.powerRelask1 = powerRelask1;
	}
	public Byte getPowerRelask2() {
		return powerRelask2;
	}
	public void setPowerRelask2(Byte powerRelask2) {
		this.powerRelask2 = powerRelask2;
	}	

	public Integer getFeeBegindate() {
		return feeBegindate;
	}
	public void setFeeBegindate(Integer feeBegindate) {
		this.feeBegindate = feeBegindate;
	}
	public Short getJtCycleMd() {
		return jtCycleMd;
	}
	public void setJtCycleMd(Short jtCycleMd) {
		this.jtCycleMd = jtCycleMd;
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
	public Integer getOcardprojId() {
		return ocardprojId;
	}
	public void setOcardprojId(Integer ocardprojId) {
		this.ocardprojId = ocardprojId;
	}
	public String getCardRand() {
		return cardRand;
	}
	public void setCardRand(String cardRand) {
		this.cardRand = cardRand;
	}
	public String getCardPass() {
		return cardPass;
	}
	public void setCardPass(String cardPass) {
		this.cardPass = cardPass;
	}
	public String getCardArea() {
		return cardArea;
	}
	public void setCardArea(String cardArea) {
		this.cardArea = cardArea;
	}
	public Integer getReserve1() {
		return reserve1;
	}
	public void setReserve1(Integer reserve1) {
		this.reserve1 = reserve1;
	}
	public Integer getReserve2() {
		return reserve2;
	}
	public void setReserve2(Integer reserve2) {
		this.reserve2 = reserve2;
	}
	public Integer getReserve3() {
		return reserve3;
	}
	public void setReserve3(Integer reserve3) {
		this.reserve3 = reserve3;
	}
	
//	public Byte getTariffIndex() {
//		return tariffIndex;
//	}
//	public void setTariffIndex(Byte tariffIndex) {
//		this.tariffIndex = tariffIndex;
//	}
}