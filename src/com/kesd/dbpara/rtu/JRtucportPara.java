package com.kesd.dbpara.rtu;
/**
 * ���񼯳�ͨ�Ŷ˿ڲ����
 */
import java.io.Serializable;

public class JRtucportPara implements Serializable{
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
	private static final long serialVersionUID = -39995634817970066L;
	
	private Integer rtuId;		//*�ն˱��*/
	private Byte 	jcCommport;	//*����ͨ�Ŷ˿ں�*/	
	private String	jcCtlstr;	/*����ͨ�ſ�����*/
	private Byte	wtfFmTime;	/*���յȴ��ĳ�ʱʱ��*/
	private	Byte	wtfBtTime;	/*���յȴ��ֽڳ�ʱʱ��*/
	private Byte	cvfRsTimes;	/*���?����ʧ���ط�����*/
	
	

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
	public Byte getWtfFmTime() {
		return wtfFmTime;
	}
	public void setWtfFmTime(Byte wtfFmTime) {
		this.wtfFmTime = wtfFmTime;
	}
	public Byte getWtfBtTime() {
		return wtfBtTime;
	}
	public void setWtfBtTime(Byte wtfBtTime) {
		this.wtfBtTime = wtfBtTime;
	}
	public Byte getCvfRsTimes() {
		return cvfRsTimes;
	}
	public void setCvfRsTimes(Byte cvfRsTimes) {
		this.cvfRsTimes = cvfRsTimes;
	}
}
