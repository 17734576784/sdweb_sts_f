package com.sts.model;

public class MsgTaskTokenPay extends MsgTaskTokenBase{
	/**
	 * 支付业务操作结构
	 */
	private static final long serialVersionUID = 2815696055194271603L;

	private int payQuantity;			//购买电量,单位100w	
	
	public MsgTaskTokenPay() {
		super();
	}

	public MsgTaskTokenPay(String dispenserPan, String keyRegNo, String sgc,
			int tariffIndex, String keyRevisionNumber,
			String keyExpiryNumber, byte creditType, String tokenId, int payQuantity) {
		super(dispenserPan, keyRegNo, sgc, tariffIndex, keyRevisionNumber,
				keyExpiryNumber, creditType, tokenId);
		this.payQuantity = payQuantity; 
	}

	public int getPayQuantity() {
		return payQuantity;
	}

	public void setPayQuantity(int payQuantity) {
		this.payQuantity = payQuantity;
	}
	
}
