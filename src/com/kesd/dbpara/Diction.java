package com.kesd.dbpara;

import java.io.Serializable;

public class Diction implements Serializable{
	
	private static final long serialVersionUID = 7456635211917442688L;
	private Short    		typeNo;			
	private String   		typeName;	
	private String          itemName;    
	private Integer   		value;			
	
	public Short getTypeNo() {
		return typeNo;
	}
	public void setTypeNo(Short typeNo) {
		this.typeNo = typeNo;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}

}
