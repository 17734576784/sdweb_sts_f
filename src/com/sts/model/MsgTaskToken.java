package com.sts.model;

import java.io.Serializable;

public class MsgTaskToken implements Serializable{

	/**
	 * 向串口传递数据结构(公共结构)
	 */
	private static final long serialVersionUID = 5012049062859404502L;

	private int 	op_i_id;		//操作类型
	private int 	data_bytenum;	//每个数据单元的长度
	private MsgTaskTokenBase data;	//业务操作参数
	
	public MsgTaskToken(){
		
	}
	
	public MsgTaskToken(int op_i_id, int data_bytenum, MsgTaskTokenBase data){
		this.op_i_id = op_i_id;
		this.data_bytenum = data_bytenum;
		this.data = data;
	}

	public int getOp_i_id() {
		return op_i_id;
	}

	public void setOp_i_id(int opIId) {
		op_i_id = opIId;
	}

	public int getData_bytenum() {
		return data_bytenum;
	}

	public void setData_bytenum(int dataBytenum) {
		data_bytenum = dataBytenum;
	}

	public MsgTaskTokenBase getData() {
		return data;
	}

	public void setData(MsgTaskTokenBase data) {
		this.data = data;
	}
}
