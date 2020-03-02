package com.kesd.dbpara.rtu;

public class RtuCommPara {
	private Integer   	rtuId;
	
	private Byte	  	safeInter;		/*��������*/
	
	private String		rtuIpaddr;		/*�ն�IP��ַ*/
	private String 		rtuIpmask;		/*�ն�IP��������*/
	private Integer		rtuIpport;		/*�ն�IP�˿�*/
	private String 		telno;			/*Modem�绰---hzhw*/

	private String 		authCode;		/*��Ϣ��֤��-ͨ������*/
	private Byte 		authCodelen;	/*��Ϣ��֤�볤��*/
	private Integer  	authCodeFano;	/*��Ϣ��֤������*/
	private Integer		authCodeFacs;	/*��Ϣ��֤��������*/
	
	private Byte		onlineType;		/*�������߷�ʽ*/
	private Byte		nettcpType;		/*����TCP���� --����ģ���֪��;*/
	
	private Byte		proxyType;		/*��������*/
	private String		proxyIpaddr;	/*����IP��ַ*/
	private Integer		proxyIpport;	/*����˿�*/
	private Byte		proxyLinktype;	/*������������ӷ�ʽ*/
	private String		proxyUsername;	/*�����û���*/
	private String		proxyUserpwd;	/*��������*/
	
	private String		apnName;		/*APN���*/
	private String		vpnUser;		/*����ר���û���*/
	private String		vpnPwd;			/*����ר������*/

	private String		masterIpaddr1;	/*��վIP��ַ1*/
	private Integer		masterIpport1;	/*��վ�˿�1*/
	private String		masterIpaddr2;	/*��վIP��ַ2*/
	private Integer		masterIpport2;	/*��վ�˿�2*/
	private String		gatewayIpaddr;	/*���IP��ַ*/
	private Integer		gatewayIpport;	/*��ض˿�*/

	private String		mstTelno;		/*��վ�绰����*/
	private String		scaNo;			/*�������ĺ���*/		

	private Byte		relayFlag;		/*�м�ת����־*/
	private Byte		relayType;		/*�м̷�ʽ 0:�Զ��м�: 1�̶��м�*/
	public Integer getRtuId() {
		return rtuId;
	}

	public void setRtuId(Integer rtuId) {
		this.rtuId = rtuId;
	}

	public Byte getSafeInter() {
		return safeInter;
	}

	public void setSafeInter(Byte safeInter) {
		this.safeInter = safeInter;
	}

	public String getRtuIpaddr() {
		return rtuIpaddr;
	}

	public void setRtuIpaddr(String rtuIpaddr) {
		this.rtuIpaddr = rtuIpaddr;
	}

	public String getRtuIpmask() {
		return rtuIpmask;
	}

	public void setRtuIpmask(String rtuIpmask) {
		this.rtuIpmask = rtuIpmask;
	}

	public Integer getRtuIpport() {
		return rtuIpport;
	}

	public void setRtuIpport(Integer rtuIpport) {
		this.rtuIpport = rtuIpport;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public Byte getAuthCodelen() {
		return authCodelen;
	}

	public void setAuthCodelen(Byte authCodelen) {
		this.authCodelen = authCodelen;
	}

	public Integer getAuthCodeFano() {
		return authCodeFano;
	}

	public void setAuthCodeFano(Integer authCodeFano) {
		this.authCodeFano = authCodeFano;
	}

	public Integer getAuthCodeFacs() {
		return authCodeFacs;
	}

	public void setAuthCodeFacs(Integer authCodeFacs) {
		this.authCodeFacs = authCodeFacs;
	}

	public Byte getOnlineType() {
		return onlineType;
	}

	public void setOnlineType(Byte onlineType) {
		this.onlineType = onlineType;
	}

	public Byte getNettcpType() {
		return nettcpType;
	}

	public void setNettcpType(Byte nettcpType) {
		this.nettcpType = nettcpType;
	}

	public Byte getProxyType() {
		return proxyType;
	}

	public void setProxyType(Byte proxyType) {
		this.proxyType = proxyType;
	}

	public String getProxyIpaddr() {
		return proxyIpaddr;
	}

	public void setProxyIpaddr(String proxyIpaddr) {
		this.proxyIpaddr = proxyIpaddr;
	}

	public Integer getProxyIpport() {
		return proxyIpport;
	}

	public void setProxyIpport(Integer proxyIpport) {
		this.proxyIpport = proxyIpport;
	}

	public Byte getProxyLinktype() {
		return proxyLinktype;
	}

	public void setProxyLinktype(Byte proxyLinktype) {
		this.proxyLinktype = proxyLinktype;
	}

	public String getProxyUsername() {
		return proxyUsername;
	}

	public void setProxyUsername(String proxyUsername) {
		this.proxyUsername = proxyUsername;
	}

	public String getProxyUserpwd() {
		return proxyUserpwd;
	}

	public void setProxyUserpwd(String proxyUserpwd) {
		this.proxyUserpwd = proxyUserpwd;
	}

	public String getApnName() {
		return apnName;
	}

	public void setApnName(String apnName) {
		this.apnName = apnName;
	}

	public String getVpnUser() {
		return vpnUser;
	}

	public void setVpnUser(String vpnUser) {
		this.vpnUser = vpnUser;
	}

	public String getVpnPwd() {
		return vpnPwd;
	}

	public void setVpnPwd(String vpnPwd) {
		this.vpnPwd = vpnPwd;
	}

	public String getMasterIpaddr1() {
		return masterIpaddr1;
	}

	public void setMasterIpaddr1(String masterIpaddr1) {
		this.masterIpaddr1 = masterIpaddr1;
	}

	public Integer getMasterIpport1() {
		return masterIpport1;
	}

	public void setMasterIpport1(Integer masterIpport1) {
		this.masterIpport1 = masterIpport1;
	}

	public String getMasterIpaddr2() {
		return masterIpaddr2;
	}

	public void setMasterIpaddr2(String masterIpaddr2) {
		this.masterIpaddr2 = masterIpaddr2;
	}

	public Integer getMasterIpport2() {
		return masterIpport2;
	}

	public void setMasterIpport2(Integer masterIpport2) {
		this.masterIpport2 = masterIpport2;
	}

	public String getGatewayIpaddr() {
		return gatewayIpaddr;
	}

	public void setGatewayIpaddr(String gatewayIpaddr) {
		this.gatewayIpaddr = gatewayIpaddr;
	}

	public Integer getGatewayIpport() {
		return gatewayIpport;
	}

	public void setGatewayIpport(Integer gatewayIpport) {
		this.gatewayIpport = gatewayIpport;
	}

	public String getMstTelno() {
		return mstTelno;
	}

	public void setMstTelno(String mstTelno) {
		this.mstTelno = mstTelno;
	}

	public String getScaNo() {
		return scaNo;
	}

	public void setScaNo(String scaNo) {
		this.scaNo = scaNo;
	}

	public Byte getRelayFlag() {
		return relayFlag;
	}

	public void setRelayFlag(Byte relayFlag) {
		this.relayFlag = relayFlag;
	}

	public Byte getRelayType() {
		return relayType;
	}

	public void setRelayType(Byte relayType) {
		this.relayType = relayType;
	}

}
