package com.sts.model;

import static com.kesd.common.CommFunc.leftPadZero;
import static com.kesd.common.CommFunc.rightPadSpace;

import java.io.Serializable;

/**
 * @author dbr
 *
 */
public class EncryptManagementFunctionToken implements Serializable{
	
	/**  描述   (@author: dbr) */    
	    
	private static final long serialVersionUID = 1L;
	private String destinationDevice;
	private String requestIndicator;
	private String commandCode;
	
	private String dispenserPan;
	private String registerNumber;
	private String supplyCode;
	private String tariffIndex;
	
	private String keyRevision;
	private int keyExpiry;
	private String managementFunction ;
	private int tokenId;
	private int transferAmount;
	
	public String getDestinationDevice() {
		return destinationDevice;
	}

	public void setDestinationDevice(String destinationDevice) {
		this.destinationDevice = destinationDevice;
	}

	public String getRequestIndicator() {
		return requestIndicator;
	}

	public void setRequestIndicator(String requestIndicator) {
		this.requestIndicator = requestIndicator;
	}

	public String getCommandCode() {
		return commandCode;
	}

	public void setCommandCode(String commandCode) {
		this.commandCode = commandCode;
	}

	public String getDispenserPan() {
		return dispenserPan;
	}

	public void setDispenserPan(String dispenserPan) {
		this.dispenserPan = dispenserPan;
	}

	public String getRegisterNumber() {
		return registerNumber;
	}

	public void setRegisterNumber(String registerNumber) {
		this.registerNumber = registerNumber;
	}

	public String getSupplyCode() {
		return supplyCode;
	}

	public void setSupplyCode(String supplyCode) {
		this.supplyCode = supplyCode;
	}

	public String getTariffIndex() {
		return tariffIndex;
	}

	public void setTariffIndex(String tariffIndex) {
		this.tariffIndex = tariffIndex;
	}

	public String getKeyRevision() {
		return keyRevision;
	}

	public void setKeyRevision(String keyRevision) {
		this.keyRevision = keyRevision;
	}

	public String getManagementFunction() {
		return managementFunction;
	}

	public void setManagementFunction(String managementFunction) {
		this.managementFunction = managementFunction;
	}

	public int getKeyExpiry() {
		return keyExpiry;
	}

	public void setKeyExpiry(int keyExpiry) {
		this.keyExpiry = keyExpiry;
	}

	public int getTokenId() {
		return tokenId;
	}

	public void setTokenId(int tokenId) {
		this.tokenId = tokenId;
	}

	public int getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(int transferAmount) {
		this.transferAmount = transferAmount;
	}

	@Override
	public String toString() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("SM").append("?").append("TM").append(rightPadSpace(dispenserPan, 19));
		sb.append(registerNumber).append(leftPadZero(supplyCode, 6));
		sb.append(leftPadZero(tariffIndex, 2)).append(keyRevision);
		sb.append(leftPadZero(Integer.toHexString(keyExpiry), 2)).append(leftPadZero(managementFunction, 2));
		sb.append(leftPadZero(Integer.toHexString(tokenId), 6)).append(leftPadZero(Integer.toHexString(transferAmount),4));

		return sb.toString();
	}
	
	public static void main(String[] args) {
 		System.out.println(leftPadZero(Integer.toHexString(150), 6));
	}
	
}