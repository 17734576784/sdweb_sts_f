package com.kesd.dbpara;

import java.io.Serializable;

public class NppayPara implements Serializable{

	private static final long serialVersionUID = 4309191528568663971L;

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
	
	private Integer rtuId;			/*终端编号*/
	private Short	mpId;			/*测量点编号*/
	private Byte	useFlag;		/*使用标志*/
	private Byte 	caclType;		/*算费类型 	0:无 1:金额计费 2:表底计费 */
	private Byte	feectrlType;	/*费控方式 	0:本地费控 1:主站费控 */
	private Byte	payType;		/*缴费方式 	0:卡式 1:远程 2:主站 */
	private Byte	yffmeterType;	/*预付费表类型*/ 	/*备用*//*厂家型号可能都要扩展*/
	private Byte	yffctrlType;	/*预付费控制类型*/   /*备用*//*厂家型号可能都要扩展*/
	private Short	feeprojId;		/*费率方案*/
	private Short	yffalarmId;    /*报警方案*/
	private Integer feeBegindate;	/*费率启用日期*/
	private Integer	powLimit;		/*限定功率*/
	private	Byte	nocycutFlag;	/*无采样自动断电功能*/
	private	Short	nocycutMin;		/*无采样自动断电时间*/  
	private Byte	powerupProt;	/*上电保护功能*/
	private Byte	keyVersion;		/*密钥版本*/			/*备用*/
	private	Byte	cryplinkId;		/*所属加密机ID*/  	/*备用*/

	private	Double	payAdd1;		/*缴费附加值1*/		/*备用*/
	private Double	payAdd2;		/*缴费附加值2*/		/*备用*/
	private	Double	payAdd3;		/*缴费附加值3*/		/*备用*/
	private	Integer	tzVal;			/*透支值*/			/*备用*/

	private	Byte	cbCycleType;	/*抄表周期类型 0:每月抄表 1:双月抄表  2:单月抄表 3:季度抄表 4：半年抄表 5：年抄表*/
	private Short	cbDayhour;		/*抄表日DDHH*/    /*备用*/
	private Byte	jsDay;			/*结算日*/		  /*备用*/

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
	public Byte getCaclType() {
		return caclType;
	}
	public void setCaclType(Byte caclType) {
		this.caclType = caclType;
	}
	public Byte getFeectrlType() {
		return feectrlType;
	}
	public void setFeectrlType(Byte feectrlType) {
		this.feectrlType = feectrlType;
	}
	public Byte getPayType() {
		return payType;
	}
	public void setPayType(Byte payType) {
		this.payType = payType;
	}
	public Byte getYffmeterType() {
		return yffmeterType;
	}
	public void setYffmeterType(Byte yffmeterType) {
		this.yffmeterType = yffmeterType;
	}
	public Byte getYffctrlType() {
		return yffctrlType;
	}
	public void setYffctrlType(Byte yffctrlType) {
		this.yffctrlType = yffctrlType;
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
	public Integer getFeeBegindate() {
		return feeBegindate;
	}
	public void setFeeBegindate(Integer feeBegindate) {
		this.feeBegindate = feeBegindate;
	}
	public Integer getPowLimit() {
		return powLimit;
	}
	public void setPowLimit(Integer powLimit) {
		this.powLimit = powLimit;
	}
	public Byte getNocycutFlag() {
		return nocycutFlag;
	}
	public void setNocycutFlag(Byte nocycutFlag) {
		this.nocycutFlag = nocycutFlag;
	}
	public Short getNocycutMin() {
		return nocycutMin;
	}
	public void setNocycutMin(Short nocycutMin) {
		this.nocycutMin = nocycutMin;
	}
	public Byte getPowerupProt() {
		return powerupProt;
	}
	public void setPowerupProt(Byte powerupProt) {
		this.powerupProt = powerupProt;
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
	public Integer getTzVal() {
		return tzVal;
	}
	public void setTzVal(Integer tzVal) {
		this.tzVal = tzVal;
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
	
	public NppayPara(){
		
	}
}
