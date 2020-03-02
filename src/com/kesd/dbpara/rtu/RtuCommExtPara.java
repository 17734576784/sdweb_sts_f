package com.kesd.dbpara.rtu;

public class RtuCommExtPara {
	private Integer   rtuId;			/*���*/
	private Byte	  jcCommport;		/*����ͨ�Ŷ˿ں�*/
	private String	  jcCtlstr;			/*����ͨ�ſ�����*/
	private Byte	  pubcommMode;  	/*����ͨ��ģ�鹤��ģʽ*/
	private Short 	  altmRecalltime;	/*��������ģʽ�ز����*/
	
	public Integer getRtuId() {
		return rtuId;
	}
	public void setRtuId(Integer rtuId) {
		this.rtuId = rtuId;
	}
	public Byte getJcCommport() {
		return jcCommport;
	}
	public void setJcCommport(Byte jcCommport) {
		this.jcCommport = jcCommport;
	}
	public String getJcCtlstr() {
		return jcCtlstr;
	}
	public void setJcCtlstr(String jcCtlstr) {
		this.jcCtlstr = jcCtlstr;
	}
	public Byte getPubcommMode() {
		return pubcommMode;
	}
	public void setPubcommMode(Byte pubcommMode) {
		this.pubcommMode = pubcommMode;
	}
	public Short getAltmRecalltime() {
		return altmRecalltime;
	}
	public void setAltmRecalltime(Short altmRecalltime) {
		this.altmRecalltime = altmRecalltime;
	}
}
