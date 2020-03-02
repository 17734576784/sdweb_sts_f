package com.kesd.dbpara.rtu;

import java.io.Serializable;

public class ResidentPara implements Serializable{
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
	private static final long serialVersionUID = 120009535052715590L;
	private Integer rtuId;		/*�ն˱��*/		
	private Short  id;   		/*���*/
	private String describe;   	/*���*/
	private String consNo;     	/*���񻧺�*/
	private String address;    	/*�����õ��ַ*/ 
	private String post;      	/*�����ʱ�*/
	private String phone;      	/*�绰*/     
	private String mobile;		/*�ƶ��绰*/
	private String fax;       	/*����*/
	private String mail;		/*��������*/
	private String infCode1;	/*�ӿڱ���1*/
	private String infCode2;	/*�ӿڱ���2*/
	private String infCode3;	/*�ӿڱ���3*/
	
	public Integer getRtuId() {
		return rtuId;
	}
	public void setRtuId(Integer rtuId) {
		this.rtuId = rtuId;
	}
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
	public String getConsNo() {
		return consNo;
	}
	public void setConsNo(String consNo) {
		this.consNo = consNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	 
}
