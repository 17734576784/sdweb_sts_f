package com.kesd.dbpara;

public class YffAlarmPara {
	private Short      id;        		//编号
	private String	   describe;   		//名称
	private Byte 	   type;        	//报警方式
	private Integer    alarm1;  		//报警值1
	private Integer    alarm2;    		//报警值2
	private Integer    alarm3;   		//报警值3
	private Byte 	   payalmFlag;		/*缴费通知标志*/
	private Byte 	   hzalmFlag;		/*合闸通知标志*/
	private Byte 	   tzalmFlag;		/*跳闸通知标志*/

	private Byte 	   dxalmFlag;		/*短信告警标志*/
	private Byte 	   syalmFlag;		/*声音告警标志*/

	private Byte 	   dxalmcgkFlag;	/*短信告警发送成功后再控制标志*/
	private Byte 	   syalmcgkFlag;	/*声音告警发送成功后再控制标志*/
	
	private Double     reserve1;   		//
	private Double     reserve2;  		//
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
	public Byte getType() {
		return type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	public Integer getAlarm1() {
		return alarm1;
	}
	public void setAlarm1(Integer alarm1) {
		this.alarm1 = alarm1;
	}
	public Integer getAlarm2() {
		return alarm2;
	}
	public void setAlarm2(Integer alarm2) {
		this.alarm2 = alarm2;
	}
	public Integer getAlarm3() {
		return alarm3;
	}
	public void setAlarm3(Integer alarm3) {
		this.alarm3 = alarm3;
	}
	public Byte getPayalmFlag() {
		return payalmFlag;
	}
	public void setPayalmFlag(Byte payalmFlag) {
		this.payalmFlag = payalmFlag;
	}
	public Byte getHzalmFlag() {
		return hzalmFlag;
	}
	public void setHzalmFlag(Byte hzalmFlag) {
		this.hzalmFlag = hzalmFlag;
	}
	public Byte getTzalmFlag() {
		return tzalmFlag;
	}
	public void setTzalmFlag(Byte tzalmFlag) {
		this.tzalmFlag = tzalmFlag;
	}
	public Byte getDxalmFlag() {
		return dxalmFlag;
	}
	public void setDxalmFlag(Byte dxalmFlag) {
		this.dxalmFlag = dxalmFlag;
	}
	public Byte getSyalmFlag() {
		return syalmFlag;
	}
	public void setSyalmFlag(Byte syalmFlag) {
		this.syalmFlag = syalmFlag;
	}
	public Byte getDxalmcgkFlag() {
		return dxalmcgkFlag;
	}
	public void setDxalmcgkFlag(Byte dxalmcgkFlag) {
		this.dxalmcgkFlag = dxalmcgkFlag;
	}
	public Byte getSyalmcgkFlag() {
		return syalmcgkFlag;
	}
	public void setSyalmcgkFlag(Byte syalmcgkFlag) {
		this.syalmcgkFlag = syalmcgkFlag;
	}
	public Double getReserve1() {
		return reserve1;
	}
	public void setReserve1(Double reserve1) {
		this.reserve1 = reserve1;
	}
	public Double getReserve2() {
		return reserve2;
	}
	public void setReserve2(Double reserve2) {
		this.reserve2 = reserve2;
	}

	
}
