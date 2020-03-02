package com.kesd.dbpara.rtu;
/**
 * �ն˹��ص�ز����  
 */
public class CtrlextPara {
	private Integer    rtuId;			//�ն˱��
	private Double     safevalue;		//������ֵ
	private Byte       protectTime;     //�Զ�����
	private Byte       disconnTime;    //��������վ������ͨ��ʱ��
	private String     pollalarmTime;	//�ִθ澯ʱ��
	private String     soundalarmFlag;	//�����澯��־
	private String     feealarmFlag;	//�߷Ѹ澯��־
	private String     eventrecFlag;	//�¼���¼��Ч��־λ
	private String     eventimpFlag;	//�¼���Ҫ�Եȼ���־λ
	private Byte	   ctrlLogic;		//�����߼�
	private String     reserve1;		//��չ�ֶ�1
	
	public Integer getRtuId() {
		return rtuId;
	}
	public void setRtuId(Integer rtuId) {
		this.rtuId = rtuId;
	}
	public Double getSafevalue() {
		return safevalue;
	}
	public void setSafevalue(Double safevalue) {
		this.safevalue = safevalue;
	}
	public Byte getDisconnTime() {
		return disconnTime;
	}
	public void setDisconnTime(Byte disconnTime) {
		this.disconnTime = disconnTime;
	}
	public String getPollalarmTime() {
		return pollalarmTime;
	}
	public void setPollalarmTime(String pollalarmTime) {
		this.pollalarmTime = pollalarmTime;
	}
	public String getSoundalarmFlag() {
		return soundalarmFlag;
	}
	public void setSoundalarmFlag(String soundalarmFlag) {
		this.soundalarmFlag = soundalarmFlag;
	}
	public String getFeealarmFlag() {
		return feealarmFlag;
	}
	public void setFeealarmFlag(String feealarmFlag) {
		this.feealarmFlag = feealarmFlag;
	}
	public String getEventrecFlag() {
		return eventrecFlag;
	}
	public void setEventrecFlag(String eventrecFlag) {
		this.eventrecFlag = eventrecFlag;
	}
	public String getEventimpFlag() {
		return eventimpFlag;
	}
	public void setEventimpFlag(String eventimpFlag) {
		this.eventimpFlag = eventimpFlag;
	}
	public String getReserve1() {
		return reserve1;
	}
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	public Byte getProtectTime() {
		return protectTime;
	}
	public void setProtectTime(Byte protectTime) {
		this.protectTime = protectTime;
	}
	public Byte getCtrlLogic() {
		return ctrlLogic;
	}
	public void setCtrlLogic(Byte ctrlLogic) {
		this.ctrlLogic = ctrlLogic;
	}
}
