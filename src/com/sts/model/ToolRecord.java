package com.sts.model;

import java.io.Serializable;

public class ToolRecord implements Serializable{

	/**
	 * 工具维护记录类
	 */
	private static final long serialVersionUID = 7003705739729758496L;

	private Integer rtuId;
	private Short   mpId;
	private String	resNo;
	private String  resDesc;
	private String  opMan;
	private Byte	opType;
	private Integer opDate;
	private Integer opTime;
	private String  wasteno;
	private String  rewasteno;
	private Byte    opResult;
	private Byte    visibleFlag;
	private Byte    updateFlag;
	
	private String  tableName;
	
	public ToolRecord(){
		
	}

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

	public String getResNo() {
		return resNo;
	}

	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	public String getResDesc() {
		return resDesc;
	}

	public void setResDesc(String resDesc) {
		this.resDesc = resDesc;
	}

	public String getOpMan() {
		return opMan;
	}

	public void setOpMan(String opMan) {
		this.opMan = opMan;
	}

	public Byte getOpType() {
		return opType;
	}

	public void setOpType(Byte opType) {
		this.opType = opType;
	}

	public Integer getOpDate() {
		return opDate;
	}

	public void setOpDate(Integer opDate) {
		this.opDate = opDate;
	}

	public Integer getOpTime() {
		return opTime;
	}

	public void setOpTime(Integer opTime) {
		this.opTime = opTime;
	}

	public String getWasteno() {
		return wasteno;
	}

	public void setWasteno(String wasteno) {
		this.wasteno = wasteno;
	}

	public String getRewasteno() {
		return rewasteno;
	}

	public void setRewasteno(String rewasteno) {
		this.rewasteno = rewasteno;
	}

	public Byte getOpResult() {
		return opResult;
	}

	public void setOpResult(Byte opResult) {
		this.opResult = opResult;
	}

	public Byte getVisibleFlag() {
		return visibleFlag;
	}

	public void setVisibleFlag(Byte visibleFlag) {
		this.visibleFlag = visibleFlag;
	}

	public Byte getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(Byte updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
