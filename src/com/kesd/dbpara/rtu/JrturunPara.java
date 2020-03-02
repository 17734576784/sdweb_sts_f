package com.kesd.dbpara.rtu;
/**
 * ���������������в���� 
 */
public class JrturunPara {
	private Integer id;			//*�ն˱��*/
	private Byte 	jcCommport;	//*����ͨ�Ŷ˿ں�*/	
	private Short 	runCtrl;	//*̨���г������п�����*/
	private Byte	cbTimes;	//*�������
	private String 	cbDays;		//*������*/
	private Short 	cbTime;		//*�ճ���ʱ��*/
	private Byte 	cbInterTm;	//*������ʱ��*/
	private Integer brcastTm;	//*�Ե��㲥Уʱ��ʱʱ��*/
	private Byte 	allowNum;	//*���?��ʱ����*/
	private Short 	allowBeg1;	//*��1�����?�?ʼʱ��*/
	private Short 	allowEnd1;	//*��1�����?�����ʱ��*/
	private Short 	allowBeg2;	//*��2�����?�?ʼʱ��*/
	private Short 	allowEnd2;	//*��2�����?�����ʱ��*/
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Byte getJcCommport() {
		return jcCommport;
	}
	public void setJcCommport(Byte jcCommport) {
		this.jcCommport = jcCommport;
	}
	public Short getRunCtrl() {
		return runCtrl;
	}
	public void setRunCtrl(Short runCtrl) {
		this.runCtrl = runCtrl;
	}
	public String getCbDays() {
		return cbDays;
	}
	public void setCbDays(String cbDays) {
		this.cbDays = cbDays;
	}
	public Short getCbTime() {
		return cbTime;
	}
	public void setCbTime(Short cbTime) {
		this.cbTime = cbTime;
	}
	public Byte getCbInterTm() {
		return cbInterTm;
	}
	public void setCbInterTm(Byte cbInterTm) {
		this.cbInterTm = cbInterTm;
	}
	public Integer getBrcastTm() {
		return brcastTm;
	}
	public void setBrcastTm(Integer brcastTm) {
		this.brcastTm = brcastTm;
	}
	public Byte getAllowNum() {
		return allowNum;
	}
	public void setAllowNum(Byte allowNum) {
		this.allowNum = allowNum;
	}
	public Short getAllowBeg1() {
		return allowBeg1;
	}
	public void setAllowBeg1(Short allowBeg1) {
		this.allowBeg1 = allowBeg1;
	}
	public Short getAllowEnd1() {
		return allowEnd1;
	}
	public void setAllowEnd1(Short allowEnd1) {
		this.allowEnd1 = allowEnd1;
	}
	public Short getAllowBeg2() {
		return allowBeg2;
	}
	public void setAllowBeg2(Short allowBeg2) {
		this.allowBeg2 = allowBeg2;
	}
	public Short getAllowEnd2() {
		return allowEnd2;
	}
	public void setAllowEnd2(Short allowEnd2) {
		this.allowEnd2 = allowEnd2;
	}
	public Byte getCbTimes() {
		return cbTimes;
	}
	public void setCbTimes(Byte cbTimes) {
		this.cbTimes = cbTimes;
	}

}
