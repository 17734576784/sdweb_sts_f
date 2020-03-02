package com.kesd.dbpara;

public class YffManDef {
	private Short      id;				//编号
	private String     name;			//姓名
	private String	   describe;		//描述
	private String     passwd;      	//密码
	private Byte	   apptype;			/*低压居民权限*/
	private Byte	   ctrlFlag;        //控制权限
	private Byte	   openflag;		/*开户销户权限*/
	private Byte	   payflag;			/*购电 补卡换表 冲正权限*/
	private Byte	   paraflag;		/*修改参数权限*/
	private Byte	   viewflag;		/*报表查看*/
	private Byte	   rese1_flag;		/*权限扩展1*/
	private Byte	   rese2_flag;		/*权限扩展2*/
	private Byte	   rese3_flag;		/*权限扩展3*/
	private Byte	   rese4_flag;		/*权限扩展4*/
	private Byte       rank;			//权限范围
	private Short      orgId;			//选择供电所
	private Short      fzmanId;			//选择联系人
	private String     reserve1;		//扩展1
	private String     reserve2;		//扩展2
	public Short getId() {
		return id;
	}
	public void setId(Short id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public Byte getCtrlFlag() {
		return ctrlFlag;
	}
	public void setCtrlFlag(Byte ctrlFlag) {
		this.ctrlFlag = ctrlFlag;
	}
	public Byte getRank() {
		return rank;
	}
	public void setRank(Byte rank) {
		this.rank = rank;
	}
	public Short getOrgId() {
		return orgId;
	}
	public void setOrgId(Short orgId) {
		this.orgId = orgId;
	}
	public Short getFzmanId() {
		return fzmanId;
	}
	public void setFzmanId(Short fzmanId) {
		this.fzmanId = fzmanId;
	}
	public String getReserve1() {
		return reserve1;
	}
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	public String getReserve2() {
		return reserve2;
	}
	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}

	public Byte getApptype() {
		return apptype;
	}
	public void setApptype(Byte apptype) {
		this.apptype = apptype;
	}
	public Byte getOpenflag() {
		return openflag;
	}
	public void setOpenflag(Byte openflag) {
		this.openflag = openflag;
	}
	public Byte getPayflag() {
		return payflag;
	}
	public void setPayflag(Byte payflag) {
		this.payflag = payflag;
	}
	public Byte getParaflag() {
		return paraflag;
	}
	public void setParaflag(Byte paraflag) {
		this.paraflag = paraflag;
	}

	public Byte getViewflag() {
		return viewflag;
	}
	public void setViewflag(Byte viewflag) {
		this.viewflag = viewflag;
	}
	public Byte getRese1_flag() {
		return rese1_flag;
	}
	public void setRese1_flag(Byte rese1Flag) {
		rese1_flag = rese1Flag;
	}
	public Byte getRese2_flag() {
		return rese2_flag;
	}
	public void setRese2_flag(Byte rese2Flag) {
		rese2_flag = rese2Flag;
	}
	public Byte getRese3_flag() {
		return rese3_flag;
	}
	public void setRese3_flag(Byte rese3Flag) {
		rese3_flag = rese3Flag;
	}
	public Byte getRese4_flag() {
		return rese4_flag;
	}
	public void setRese4_flag(Byte rese4Flag) {
		rese4_flag = rese4Flag;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}

	
}
