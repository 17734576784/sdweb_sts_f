package com.sts.model;

public class KMFStructs {
	
	private String recordType;		//Record Type
	private String kFunction;		//Key Function
	private String kRegisterNO;		//Key Register NO
	private String kType;			//Key Type
	private String kParity;			//Key parity
	private String kEMethod;		//Key Encryption method
	private String pRNumber;		//Parent Register Number		
	private String kValue;			//Key Value	
	private String kCDigits;		//Key Check Digits		
	private String dateSent;		//Date Sent
	
	private String timeSent;		//Time Sent
	private String dateActive;		//Date Active
	private String timeActive;		//Time Active
	private String kRNumber;		//Key Revision Number
	private String kENumber;		//Key Expiry Number
	private String sGCode;			//Supply Group Code
	private String sGName;			//Supply Group Name
	
	public KMFStructs(){
		
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getkFunction() {
		return kFunction;
	}

	public void setkFunction(String kFunction) {
		this.kFunction = kFunction;
	}

	public String getkRegisterNO() {
		return kRegisterNO;
	}

	public void setkRegisterNO(String kRegisterNO) {
		this.kRegisterNO = kRegisterNO;
	}

	public String getkType() {
		return kType;
	}

	public void setkType(String kType) {
		this.kType = kType;
	}

	public String getkParity() {
		return kParity;
	}

	public void setkParity(String kParity) {
		this.kParity = kParity;
	}

	public String getkEMethod() {
		return kEMethod;
	}

	public void setkEMethod(String kEMethod) {
		this.kEMethod = kEMethod;
	}

	public String getpRNumber() {
		return pRNumber;
	}

	public void setpRNumber(String pRNumber) {
		this.pRNumber = pRNumber;
	}

	public String getkValue() {
		return kValue;
	}

	public void setkValue(String kValue) {
		this.kValue = kValue;
	}

	public String getkCDigits() {
		return kCDigits;
	}

	public void setkCDigits(String kCDigits) {
		this.kCDigits = kCDigits;
	}

	public String getDateSent() {
		return dateSent;
	}

	public void setDateSent(String dateSent) {
		this.dateSent = dateSent;
	}

	public String getTimeSent() {
		return timeSent;
	}

	public void setTimeSent(String timeSent) {
		this.timeSent = timeSent;
	}

	public String getDateActive() {
		return dateActive;
	}

	public void setDateActive(String dateActive) {
		this.dateActive = dateActive;
	}

	public String getTimeActive() {
		return timeActive;
	}

	public void setTimeActive(String timeActive) {
		this.timeActive = timeActive;
	}

	public String getkRNumber() {
		return kRNumber;
	}

	public void setkRNumber(String kRNumber) {
		this.kRNumber = kRNumber;
	}

	public String getkENumber() {
		return kENumber;
	}

	public void setkENumber(String kENumber) {
		this.kENumber = kENumber;
	}

	public String getsGCode() {
		return sGCode;
	}

	public void setsGCode(String sGCode) {
		this.sGCode = sGCode;
	}

	public String getsGName() {
		return sGName;
	}

	public void setsGName(String sGName) {
		this.sGName = sGName;
	}

}
