package com.kesd.dbpara;

public class AreaPara {
	private Integer id;			/*片区编号*/
	private Short	orgId;		/*所属供电所*/
	private String 	describe;	/*片区名称*/
	
	private String 	areaCode;	/*区域号*/
	private Short feeprojId;	/*费率方案*/
	private Short yffalarmId;	/*报警方案*/
	private Integer feeBegindate;/*费率启用日期*/
	private String 	remark;		/*备注*/
	
	private String infCode1;	/*接口编码1*/	
	private String infCode2;	/*接口编码2*/	
	private String infCode3;	/*接口编码3*/	
	private String reserve1;	/*扩展字段1*/
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Short getOrgId() {
		return orgId;
	}
	public void setOrgId(Short orgId) {
		this.orgId = orgId;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getInfCode1() {
		return infCode1;
	}
	public void setInfCode1(String infCode1) {
		this.infCode1 = infCode1;
	}
	public String getInfCode2() {
		return infCode2;
	}
	public void setInfCode2(String infCode2) {
		this.infCode2 = infCode2;
	}
	public String getInfCode3() {
		return infCode3;
	}
	public void setInfCode3(String infCode3) {
		this.infCode3 = infCode3;
	}
	public String getReserve1() {
		return reserve1;
	}
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	
}
