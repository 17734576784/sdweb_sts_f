package com.kesd.dbpara;

public class LinePara {
	private Short    	id;				//��·���
	private String  	describe;		//���
	private Byte 	  	type;			//����
	private Byte 	  	voltGrade;		//��ѹ�ȼ�
	private Double   	lineLength;		//��·����
	private Short       subsId;			//���վ���
	
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
	public Byte getVoltGrade() {
		return voltGrade;
	}
	public void setVoltGrade(Byte voltGrade) {
		this.voltGrade = voltGrade;
	}
	public Double getLineLength() {
		return lineLength;
	}
	public void setLineLength(Double lineLength) {
		this.lineLength = lineLength;
	}
	public Short getSubsId() {
		return subsId;
	}
	public void setSubsId(Short subsId) {
		this.subsId = subsId;
	}
}
