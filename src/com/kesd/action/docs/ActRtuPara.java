/********************************************************************************************************
*                                        用电WEB Ver2.0													*
*																										*
*                           (c) Copyright 2010~,   KLD Automation Co., Ltd.								*
*                                           All Rights Reserved											*
*																										*
*	FileName	:	ActRtuPara.java																		*
*	Description	:	终端档案 业务处理类																	*
*	Author		:																						*
*	Date		:	2010/12/29																			*
*																										*
*   No.         Date         Modifier        Description												*
*   -----------------------------------------------------------------------------------------------     *
*********************************************************************************************************/
package com.kesd.action.docs;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.json.annotations.JSON;

import com.kesd.common.SDDef;
import com.kesd.common.SDOperlog;
import com.kesd.common.YDTable;
import com.kesd.dao.HibPage;
import com.kesd.dao.SqlPage;
import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;
import com.libweb.dao.JDBCDao;
import com.kesd.dbpara.rtu.CtrlextPara;
import com.kesd.dbpara.rtu.RtuCommExtPara;
import com.kesd.dbpara.rtu.RtuCommPara;
import com.kesd.dbpara.rtu.RtuExtPara;
import com.kesd.dbpara.rtu.RtuLinkcomPara;
import com.kesd.dbpara.ConsPara;
import com.kesd.dbpara.RtuPara;
import com.kesd.dbpara.RtuPayCtrl;
import com.kesd.dbpara.YffManDef;
import com.kesd.dbpara.rtu.RtuRelayPara;
import com.kesd.dbpara.rtu.JRtucportPara;
import com.kesd.dbpara.rtu.JrturunPara;
import com.kesd.dbpara.rtu.SimCard;
import com.kesd.common.CommFunc;
import com.kesd.common.Dict;
import com.kesd.service.DBOper;
import com.kesd.util.OnlineRtu;
import com.kesd.util.Rd;
import com.opensymphony.xwork2.ActionSupport;

/** ******************* CLASS DESCRIPTION *******************
* <p>
* <p>NAME        : ActRtuPara
* <p>
* <p>DESCRIPTION : 终端档案 业务处理类 
* <p>
* <p>  No.         Date         Modifier        Description	
* <p>---------------------------------------------------------
**************************************************************/
public class ActRtuPara extends ActionSupport
{
	private static final long serialVersionUID = -4855371030068681477L;
	
	private String   result;
	private String   pageNo;
	private String   pageSize;
	private RtuPara  rtuPara;
	private RtuCommPara rtuCommPara;
	private String	 field;
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : addOrEdit
	* <p>
	* <p>DESCRIPTION : 添加或修改终端档案 
	* <p>
	* <p>COMPLETION
	* <p>INPUT       :  
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public String addOrEdit() throws Exception
	{
		HibDao 			hib_dao    		= new HibDao();
		
		RtuExtPara 		rtuextpara 		= new RtuExtPara();
		//RtuCommPara		rtucommpara 	= new RtuCommPara();
		RtuCommExtPara 	rtucommextpara 	= new RtuCommExtPara();
		//不关联rturelaypara表  2019.02.19修改
		//RtuRelayPara 	rturelaypara 	= new RtuRelayPara();
		//RtuPayCtrl		rtupayctrl		= new RtuPayCtrl();
		//不关联rtulinkcompara表  2019.02.19修改
		//RtuLinkcomPara 	rtulinkcompara 	= new RtuLinkcomPara();
		//JrturunPara		jrturunpara		= new JrturunPara();
		//CtrlextPara 	ctrlextpara		= new CtrlextPara();
		JRtucportPara	jrtucportpara1 	= new JRtucportPara();
		JRtucportPara	jrtucportpara2 	= new JRtucportPara();
		
		boolean 		add        		= false;
		Integer         maxid           = SDDef.JMRTUID;		//居民区 终端编号从30000000开始
		
		boolean 		hasSimId 		= true;					//SIM卡号是否存在标识
		Object[] 		temp 			= null;
		//sim卡号码
		String telno = rtuCommPara.getTelno();
		//sim卡ip地址
		String ipaddr = rtuCommPara.getRtuIpaddr();

		if(rtuPara.getId() == null){
			
			short st = 0;
			rtuPara.setResidentNum(st);
			rtuPara.setJlpNum(st);
			
			synchronized (hib_dao) {
				String hql = "select max(id) from "+YDTable.TABLECLASS_RTUPARA+" as a where id>=" + maxid + "and a.appType=" + SDDef.APPTYPE_JC;
				List list = hib_dao.loadAll(hql);
				if(list.get(0) != null) {
					maxid = (Integer)list.get(0);
					maxid ++;
				}
				rtuPara.setId(maxid);
				
				if(!hib_dao.saveOrUpdate(rtuPara)){
					result = SDDef.FAIL;
					return SDDef.SUCCESS;
				}
			}
			
			rtuCommPara.setRtuId(maxid);
			rtuCommPara.setSafeInter((byte)5);
			
			rtuextpara.setRtuId(maxid);			
			rtucommextpara.setRtuId(maxid);
			//rturelaypara.setRtuId(maxid);
			//rtulinkcompara.setRtuId(maxid);
			//jrturunpara.setId(maxid);
			//ctrlextpara.setRtuId(maxid);
			//rtupayctrl.setRtuId(maxid);
			
			jrtucportpara1.setRtuId(maxid);
			jrtucportpara1.setJcCommport((byte)2);
			jrtucportpara1.setJcCtlstr("01101011");
			jrtucportpara1.setWtfBtTime((byte)200);		/*深圳江机要求大于170，暂定修改为200, 售电升级(20131231)，对应前置程序修改此处改为10,*/
			jrtucportpara1.setWtfFmTime((byte)10);
			jrtucportpara1.setCvfRsTimes((byte)3);
			jrtucportpara2.setRtuId(maxid);
			jrtucportpara2.setJcCommport((byte)31);
			jrtucportpara2.setJcCtlstr("01101011");
			jrtucportpara2.setWtfBtTime((byte)240);
			jrtucportpara2.setWtfFmTime((byte)20);
			jrtucportpara2.setCvfRsTimes((byte)3);
			add = true;
		}

		if(add){
			//如果终端档案的SIM卡号是一个新的SIM卡，则在simcard档案中添加一条记录
			if(hasSimId == false){
				
				//Object[] obj = {rtuCommPara,rtuextpara,rtucommextpara,rturelaypara,rtulinkcompara,jrturunpara,ctrlextpara,jrtucportpara1,jrtucportpara2,rtupayctrl};
				Object[] obj = {rtuCommPara,rtuextpara,rtucommextpara,jrtucportpara1,jrtucportpara2};
								
				temp = obj;
			}
			//如果建档案时不添加simcard卡号或添加的是simCard档案中已经存在的号码，则只更新一下rtuPara,rtuCommPara中对应的字段值
			else{
				//Object[] obj = {rtuCommPara,rtuextpara,rtucommextpara,rturelaypara,rtulinkcompara,jrturunpara,ctrlextpara,jrtucportpara1,jrtucportpara2,rtupayctrl};
				Object[] obj = {rtuCommPara,rtuextpara,rtucommextpara,jrtucportpara1,jrtucportpara2};
								
				temp = obj;
			}
			
			if(hib_dao.saveOrUpdate(temp)){
				//重点测试--js可能有错
				//result = TableIdAndName.init(YDTable.TABLECLASS_RTUPARA+SDDef.SPLITCOMA+"JM") + SDDef.SPLITCOMA +YDTable.TABLECLASS_RTUPARA;
				result = SDDef.SUCCESS;
				
				//TableIdAndName.init(YDTable.TABLECLASS_SIMCARD);	//20130604   更新SIM卡档案
				OnlineRtu.init();
			}
			else {
				result = SDDef.FAIL;
			}
		}
		//修改
		else{
			String rhql1 =  "update " + YDTable.TABLECLASS_RTUPARA   + "  set describe='" + rtuPara.getDescribe() + "'" 
						  	+ ", useFlag=" 	+ rtuPara.getUseFlag() 	+ ", protType=" 	+ rtuPara.getProtType()
						  	+ ", rtuAddr=" 	+ rtuPara.getRtuAddr() 	+ ", areaCode='" 	+ rtuPara.getAreaCode()		  + "'"
						  	+ ", rtuModel=" + rtuPara.getRtuModel()	+ ", runStatus="	+ rtuPara.getRunStatus()
						  	+ ", fzcbId=" 	+ rtuPara.getFzcbId()
						   	+ ", chanMain=" + rtuPara.getChanMain() + ", chanBak="		+ rtuPara.getChanBak()
				 			+ ", assetNo='" + rtuPara.getAssetNo() 	+ "', barCode='"	+ rtuPara.getBarCode() 	+ "'"
				 			+ ", factory=" 	+ rtuPara.getFactory() 	+ ", madeNo='"		+ rtuPara.getMadeNo() 	+ "'"
				 			+ ", infCode1='"+ rtuPara.getInfCode1() + "',infCode2='"	+ rtuPara.getInfCode2() + "'"
				 			+ ", instSite=" + rtuPara.getInstSite() + ", runDate="		+ rtuPara.getRunDate()
				 			+ ", instMan="	+ rtuPara.getInstMan()  + ", instDate="  	+ rtuPara.getInstDate()
				 			+ ", stopDate="	+ rtuPara.getStopDate() + ", simcardId=" 	+ rtuPara.getSimcardId()
				 			+ ", consId="	+ rtuPara.getConsId() 	+ ", residentNum=" 	+ rtuPara.getResidentNum()
						  	+ ", jlpNum=" 	+ rtuPara.getJlpNum() 	
							+ " where id=" 	+ rtuPara.getId();		

		String rhql2 =  "update "  + YDTable.TABLECLASS_RTUCOMMPARA + "  set telno='" 	+ rtuCommPara.getTelno() + "'"
					+ ", authCode='"  + rtuCommPara.getAuthCode()	+ "',authCodelen=" 	+ rtuCommPara.getAuthCodelen()
					+ ", rtuIpaddr='" + rtuCommPara.getRtuIpaddr()	+ "',onlineType=" 	+ rtuCommPara.getOnlineType()
					+ ", rtuIpport="  + rtuCommPara.getRtuIpport()
			 		+ " where rtuId=" + rtuPara.getId();			
				
		String[] hqltmp = {rhql1, rhql2} ;
		
		if (hib_dao.updateByHql(hqltmp)) {			

				//重点测试----js返回可能有误
//				result = TableIdAndName.init(YDTable.TABLECLASS_RTUPARA+SDDef.SPLITCOMA+"JM") + SDDef.SPLITCOMA +YDTable.TABLECLASS_RTUPARA;
		if(add){
			SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_ADD, "Add Terminal Archive["+ rtuPara.getId() +"]");

		}else{
			
			SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_UPDATE, "Modify Terminal Archive["+ rtuPara.getId() +"]");

		}
//			
			
			result = SDDef.SUCCESS;
			
//				if(hasSimId == false){
//					TableIdAndName.init(YDTable.TABLECLASS_SIMCARD);	//20130604   更新SIM卡档案
//				}
				OnlineRtu.init();	
			}else{
				result = SDDef.FAIL;
			}
		}
		return SDDef.SUCCESS;
	}

	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : delRtuParaById
	* <p>
	* <p>DESCRIPTION : 根据ID删除终端档案（批量） 
	* <p>
	* <p>COMPLETION
	* <p>INPUT       :  
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	
	public String delRtuParaById()
	{
		
		HibDao  hib_dao = new HibDao();
		String  id[]    = result.split(SDDef.JSONSPLIT);
		Integer ids[] 	= new Integer[id.length];
		byte   flag	   	  = SDDef.HAVE_TYPE_NONE;
		
		//Class[] obj = {RtuPara.class,RtuExtPara.class,RtuCommPara.class,RtuCommExtPara.class,RtuRelayPara.class,RtuLinkcomPara.class,JrturunPara.class,CtrlextPara.class};
		//注意没有删除,JRtucportPara.class
		for(int i = 0; i < id.length; i++) {
			ids[i]  = Integer.parseInt(id[i]);
			rtuPara = (RtuPara) hib_dao.loadById(RtuPara.class, Integer.parseInt(id[i]));
			
			if(rtuPara.getJlpNum() == null)rtuPara.setJlpNum((short)0);
			if(rtuPara.getZjgNum() == null)rtuPara.setZjgNum((short)0);
			
			if (rtuPara.getJlpNum() != null && rtuPara.getZjgNum() !=null && (rtuPara.getJlpNum() > 0 || rtuPara.getZjgNum() > 0)) {
				result = "Terminal【" + rtuPara.getDescribe().trim() + "】 have resident files please delete them first！";
				flag   = SDDef.HAVE_TYPE_MP;
				break;
			}
		}
		
		if (flag == SDDef.HAVE_TYPE_NONE) {
			
			for(int i = 0; i < id.length; i++) {
				ids[i]  = Integer.parseInt(id[i]);
				
				String[] hql = new String[12];
				hql[0] = "delete from " + YDTable.TABLECLASS_RTUPARA 	+ " as a where a.id = " + id[i];
				hql[1] = "delete from " + YDTable.TABLECLASS_RTUEXTPARA 	+ " as a where a.id = " + id[i];
				hql[2] = "delete from " + YDTable.TABLECLASS_RTUCOMMPARA 	+ " as a where a.id = " + id[i];
				hql[3] = "delete from " + YDTable.TABLECLASS_RTUCOMMEXTPARA 	+ " as a where a.id = " + id[i];
				//hql[4] = "delete from " + YDTable.TABLECLASS_RTURELAYPARA 	+ " as a where a.id = " + id[i];
				//hql[4] = "delete from " + YDTable.TABLECLASS_RTULINKPARA 	+ " as a where a.id = " + id[i];
				
				//hql[4] = "delete from " + YDTable.TABLECLASS_RTUPAYCTRL 	+ " as a where a.id = " + id[i];
				//hql[6] = "delete from " + YDTable.TABLECLASS_JRTURUNPARA 	+ " as a where a.id = " + id[i];
				//hql[7] = "delete from " + YDTable.TABLECLASS_CTRLEXTPARA 	+ " as a where a.id = " + id[i];
				//hql[8] = "delete from " + YDTable.TABLECLASS_RTULIMITPARA 	+ " as a where a.id = " + id[i];
				hql[4] = "delete from " + YDTable.TABLECLASS_JRTUCPORTPARA 	+ " as a where a.jcCommport = 2  and a.rtuId = " + id[i];
				hql[5] = "delete from " + YDTable.TABLECLASS_JRTUCPORTPARA 	+ " as b where b.jcCommport = 31 and b.rtuId = " + id[i];
				try{
					if(!hib_dao.updateByHql(hql)){
						flag = SDDef.HAVE_TYPE_UNKNOWN;
						break;
					}else{
						SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_DELETE, "Delete Terminal Archive["+ id[i] +"]");
					}					
				}catch(Exception e){e.printStackTrace();};
			}
			
			if (flag == SDDef.HAVE_TYPE_NONE) {
				
				result = SDDef.SUCCESS;
				//TableIdAndName.init(YDTable.TABLECLASS_RTUPARA);
				//OnlineRtu.init();
			}
			else {
				result = SDDef.FAIL;
			}
		}
			
		return SDDef.SUCCESS;
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : getRtuParaById
	* <p>
	* <p>DESCRIPTION : 根据ID取得终端信息 
	* <p>
	* <p>COMPLETION
	* <p>INPUT       :  
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@JSON(serialize = false)
	public String getRtuParaById()
	{
		if (result == null) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		StringBuffer ret_buf = new StringBuffer();
		HibDao 		 hib_dao = new HibDao();
		rtuPara              = (RtuPara) hib_dao.loadById(RtuPara.class, Integer.parseInt(result));
		if(rtuPara==null){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		String titles[] = field.split(SDDef.SPLITCOMA);
		ret_buf.append(SDDef.JSONSELDATA);
		for(int i = 0; i < titles.length; i ++) {
			ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(CommFunc.getMethodValue(titles[i], RtuPara.class,rtuPara)) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
		}
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();
		
		return SDDef.SUCCESS;
	}
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : getRtuParaCommParaById
	* <p>
	* <p>DESCRIPTION : 根据ID取得终端信息 /终端通信参数
	* <p>
	* <p>COMPLETION
	* <p>INPUT       :  
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	@JSON(serialize = false)
	public String getRtuParaCommParaById()
	{
		if (result == null) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		StringBuffer ret_buf = new StringBuffer();
		HibDao 		 hib_dao = new HibDao();
		ret_buf.append(SDDef.JSONSELDATA);
		
		String titles[] = field.split(SDDef.SPLITCOMA);
		String col = "";
		int i = 0;
		
		for(i = 0; i < titles.length - 5; i++ ) {
			col+=",a."+titles[i];
		}
		
		for(; i < titles.length; i++ ) {
			col+=",b."+titles[i];
		}
		
		col = col.substring(1);
		String hql = "select " + col + " from " + YDTable.TABLECLASS_RTUPARA + " as a," + YDTable.TABLECLASS_RTUCOMMPARA + " as b where a.id = b.rtuId and a.id=" + result;
		List list = hib_dao.loadAll(hql);
		if(list.size() == 0){
			RtuCommPara rcp = new RtuCommPara();
			rcp.setRtuId(Integer.parseInt(result));
			hib_dao.saveOrUpdate(rcp);
			
			i = 0;
			col = "";
			for(i = 0; i < titles.length - 1; i++ ) {
				col+=",a."+titles[i];
			}
			
			for(; i < titles.length; i++ ) {
				col+=",b."+titles[i];
			}
			
			col = col.substring(1);
			hql = "select " + col + " from " + YDTable.TABLECLASS_RTUPARA + " as a," + YDTable.TABLECLASS_RTUCOMMPARA + " as b where a.id = b.rtuId and a.id=" + result;
			list = hib_dao.loadAll(hql);
		}
		Iterator it = list.iterator();
		while(it.hasNext()){
			Object[] obj = (Object[])it.next();
			for(i=0;i<titles.length;i++){
				ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(obj[i]) + SDDef.JSONQUOT  + SDDef.JSONCOMA);
			}
		}
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRACES);
		result = ret_buf.toString();
		
		return SDDef.SUCCESS;
	}
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : execute
	* <p>
	* <p>DESCRIPTION : 分页获取终端档案记录
	* <p>
	* <p>COMPLETION
	* <p>INPUT       :  
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public String execute() throws Exception 
	{	
		String order = " order by b.orgId,b.lineFzManId,b.id";
		
		List         list      = null;
		StringBuffer ret_buf   = new StringBuffer();
		int 		 page	   = Integer.parseInt(pageNo == null? "1" : pageNo);
		int 		 pagesize  = Integer.parseInt(pageSize == null? "10" : pageSize);
		
		String describe   = null;
		
		YffManDef user = CommFunc.getYffMan();
		String getRtuhql = "";
		String counthql  = "";
		if(user.getOrgId() == null){
			//simcardId
			getRtuhql  = "select a.id,a.describe,a.useFlag,a.rtuModel,a.protType,a.rtuAddr,a.chanMain,a.residentNum,a.jlpNum,a.consId from " + YDTable.TABLECLASS_RTUPARA + " as a";
			getRtuhql += "," + YDTable.TABLECLASS_CONSPARA + " as b where a.consId=b.id and b.appType="+SDDef.APPTYPE_JC;
			
			counthql   = "select count(a.id) from " + YDTable.TABLECLASS_RTUPARA + " as a";
			counthql  += "," + YDTable.TABLECLASS_CONSPARA + " as b where a.consId=b.id and b.appType="+SDDef.APPTYPE_JC;
			
			
			if(result != null && !result.isEmpty()) {
				JSONObject jsonObj  = JSONObject.fromObject(result);
				describe   = jsonObj.getString("describe");
			}
			
			if(describe != null && !describe.isEmpty()){
				getRtuhql += " and a.describe like '%" + describe + "%'";
				counthql  += " and a.describe like '%" + describe + "%'";
			}
			
			if(user.getRank() != 0){
				getRtuhql += " and b.orgId =" + user.getOrgId();
				counthql  += " and b.orgId =" + user.getOrgId();
	 		}			
		}else{
			getRtuhql  = "select a.id,a.describe,a.useFlag,a.rtuModel,a.protType,a.rtuAddr,a.chanMain,a.residentNum,a.jlpNum,a.consId from " + YDTable.TABLECLASS_RTUPARA + " as a";
			getRtuhql += "," + YDTable.TABLECLASS_CONSPARA + " as b,"+ YDTable.TABLECLASS_Userrankbound +" as u where  u.consId = b.id and a.consId=b.id and b.appType="+SDDef.APPTYPE_JC +" and u.userId = " + user.getId();
			
			counthql   = "select count(a.id) from " + YDTable.TABLECLASS_RTUPARA + " as a";
			counthql  += "," + YDTable.TABLECLASS_CONSPARA + " as b,"+ YDTable.TABLECLASS_Userrankbound +" as u where u.consId = b.id and a.consId=b.id and b.appType="+SDDef.APPTYPE_JC +" and u.userId = " + user.getId();
			
			
			if(result != null && !result.isEmpty()) {
				JSONObject jsonObj  = JSONObject.fromObject(result);
				describe   = jsonObj.getString("describe");
			}
			
			if(describe != null && !describe.isEmpty()){
				getRtuhql += " and a.describe like '%" + describe + "%'";
				counthql  += " and a.describe like '%" + describe + "%'";
			}
			
			if(user.getRank() != 0){
				getRtuhql += " and b.orgId =" + user.getOrgId();
				counthql  += " and b.orgId =" + user.getOrgId();
	 		}			
		}
		
		HibPage hib_page = new HibPage(page, pagesize);
		hib_page.setTotalrecords(counthql);
		
		if (pagesize == 0) {
			pagesize = hib_page.getTotalrecords();
			hib_page.setPagesize(pagesize);
		} 
		if(hib_page.getTotalrecords()== 0) {
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		ret_buf.append(SDDef.JSONTOTAL + hib_page.getTotalrecords() + SDDef.JSONPAGEROWS);
		
		getRtuhql += order;
		
		list = hib_page.getRecord(getRtuhql);
		Iterator it = list.iterator();
		
		int no = pagesize * (hib_page.getCurrentpage() - 1) + 1;
		
		Map<Integer,String> map_useflag = Rd.getDict(Dict.DICTITEM_USEFLAG);    
		Map<Integer,String> map_prottype = Rd.getDict(Dict.DICTITEM_JMRTUPROTTYPE);
		
		
		while(it.hasNext()) {
			int i = 0;
			Object[] obj = (Object[])it.next();
			ret_buf.append(SDDef.JSONLBRACES);
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + obj[i++] + SDDef.JSONDATA);
			ret_buf.append(SDDef.JSONQUOT + (no++) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + "<a href='javascript:redoOnRowDblClicked("+ obj[0] +");'>" + CommFunc.CheckString(obj[i++]) + "</a>" + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + getDictByKey(map_useflag, obj[i++]) + SDDef.JSONCCM);
			
			ret_buf.append(SDDef.JSONQUOT + DBOper.getDescribeById("ydparaben.dbo.rtumodel", obj[i++])  + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + getDictByKey(map_prottype,obj[i++]) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(obj[i++])                                + SDDef.JSONCCM);
			
			ret_buf.append(SDDef.JSONQUOT + DBOper.getDescribeById("ydparaben.dbo.chanpara", obj[i++])  + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(obj[i++])                                + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommFunc.CheckString(obj[i++])                                + SDDef.JSONCCM);
			
			ret_buf.append(SDDef.JSONQUOT + DBOper.getDescribeById("ydparaben.dbo.conspara", obj[i++])  + SDDef.JSONCCM);
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);
		}
		
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRBRBR);
		result = ret_buf.toString();
		return SDDef.SUCCESS;
	}
	private String getDictByKey(Map<Integer,String> map,Object key){
		if(key == null)return "";
		int key1 = Integer.parseInt(key.toString());
		String value = map.get(key1);
		if(value == null)return "";
		
		return value;
	}
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : checkRtuAddrAreaCode
	* <p>
	* <p>DESCRIPTION : 终端通信参数中是否有和    本地址和行政编码同时    重复的 同一个通道下
	* <p>
	* <p>COMPLETION
	* <p>INPUT       :  
	* <p>OUTPUT      : 
	* <p>RETURN      : String
	* <p>
	*-----------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	@JSON(serialize = false)
	public String checkRtuAddrAreaCode()
	{
		if (field == null) {
			field = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}

		String 		 hql;
		HibDao 		 hib_dao = new HibDao();
		String titles[] = field.split(SDDef.SPLITCOMA);
		
		if (titles[4].compareToIgnoreCase("add") == 0) {
			hql = "select id,describe from " + YDTable.TABLECLASS_RTUPARA + "  where rtuAddr = " + titles[0] + " and chanMain = " + titles[1] + " and areaCode = '" + titles[2] + "'";
		}
		else {
			hql = "select id,describe from " + YDTable.TABLECLASS_RTUPARA + "  where rtuAddr = " + titles[0] + " and chanMain = " + titles[1] + " and areaCode = '" + titles[2] + "' and id <> " + titles[3];
		}
		
		Iterator it = hib_dao.loadAll(hql).iterator();
		
		field = "";
		while(it.hasNext()){
			Object[] obj = (Object[])it.next();
			field = CommFunc.CheckString(obj[1]);
			return SDDef.SUCCESS;
		}

		return SDDef.SUCCESS;
	}
	
	/**
	 * 获取SIM号码表中最大id
	 * @return  最大id+1
	 */
	@SuppressWarnings("unchecked")
	@JSON(serialize = false)
	public short getMaxSIMcard(){
		String hql = "select max(id) from " + YDTable.TABLECLASS_SIMCARD;
		HibDao 		 hib_dao = new HibDao();
		List list = hib_dao.loadAll(hql);
		//无数据返回的时[null]
		Short mid = (Short)list.get(0);
		if(mid != null) return (short) (mid + 1);
		else return 1;
	}
	
	/**
	 * 查询SIM档案表中是否有此数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@JSON(serialize = false)
	public short hasSIMNo(String telno){
		String hql = "select id from " + YDTable.TABLECLASS_SIMCARD + " where tel_no = '" + telno + "'";
		HibDao hib_dao = new HibDao();
		List list = hib_dao.loadAll(hql);
		//如果该号码已经存在，则返回其id
		if(list.size() != 0){
			Short mid = (Short)list.get(0);
			return mid;
		}
		//如果不存在返回-1
		else{
			return -1;
		}
	} 
	
	//获取所有的居民区
	public String loadAllConspara(){
		YffManDef user = CommFunc.getYffMan();
		//全局所还是一个所权限
		String sql = "";
		if(user.getOrgId() !=null){
			sql = "select cons.* from " + YDTable.TABLECLASS_CONSPARA + " cons," + YDTable.TABLECLASS_YFFMANDEF
			+ " man,"+ YDTable.TABLECLASS_Userrankbound +" u where u.cons_id = cons.id and u.user_id = "+ user.getId() +" and cons.org_id = man.org_id and cons.app_type = " + SDDef.APPTYPE_JC + " and man.id = " + user.getId();
			if(result != null && !result.isEmpty()){
				sql += " and cons.describe like '%" + result + "%'";
			}
			
			if(user.getOrgId() !=null){
				sql += " AND man.org_id= " + user.getOrgId();
			}
		}else{
			sql = "select cons.* from " + YDTable.TABLECLASS_CONSPARA + " cons" + " where cons.app_type = " + SDDef.APPTYPE_JC;
			if(result != null && !result.isEmpty()){
				sql += " and cons.describe like '%" + result + "%'";
			}		
		}

		
		JDBCDao jdbc_dao = new JDBCDao();
		
		List list = jdbc_dao.result(sql);
		
		Iterator<Map<String,Object>> it = list.iterator();
		
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		JSONArray	j_array1 = new JSONArray();
		JSONArray	j_array2 = new JSONArray();
		
		Map<String,Object> temp = null;
		int index = 1;
		while(it.hasNext()){
			temp = it.next();
			json1.put("id", temp.get("id"));
			
			j_array1.add(index++);//序号 
			j_array1.add(temp.get("describe"));//描述
			
			json1.put("data", j_array1);
			j_array2.add(json1);
			json1.clear();
			j_array1.clear();
		}
		json2.put("rows", j_array2);
		result = json2.toString();
		return SDDef.SUCCESS;
	}
	
	
	//获取所有的居民区
	public String loadAllConsparaPerssion(){
		JSONObject resultJson = JSONObject.fromObject(result);
		List 			list 	 = null;
		StringBuffer 	ret_buf  = new StringBuffer();		
		int 			page	 = Integer.parseInt(pageNo == null  ? "1" : pageNo);
		int 			pagesize = Integer.parseInt(pageSize == null? "20" : pageSize);
		int 			count 	 = 0;
		SqlPage sql_page = new SqlPage(page, pagesize);
		StringBuffer countSql=new StringBuffer();		
		
		HibDao 		hib_dao = new HibDao();
		JDBCDao		jdbcDao = new JDBCDao();
		YffManDef 	yffmandef = (YffManDef) hib_dao.loadById(YffManDef.class, Short.parseShort(resultJson.getString("yffId")));		
		
		String sql = "";String countSqls = "";
		if(yffmandef.getOrgId() != null){
			sql = " cons.*,org.[describe] orgDesc from " + YDTable.TABLECLASS_CONSPARA + " cons," + YDTable.TABLECLASS_YFFMANDEF
			+ " man,orgpara org where cons.org_id = man.org_id and cons.app_type = " + SDDef.APPTYPE_JC + " and man.id = " + resultJson.getInt("yffId")+ " AND org.id = cons.org_id ";
			countSqls = "select count(*) from " + YDTable.TABLECLASS_CONSPARA + " cons," + YDTable.TABLECLASS_YFFMANDEF
			+ " man,orgpara org where cons.org_id = man.org_id and cons.app_type = " + SDDef.APPTYPE_JC + " and man.id = " + resultJson.getInt("yffId") + " AND org.id = cons.org_id ";		
			countSql.append(countSqls);		
			
			if(result != null && !result.isEmpty()){
			sql += " and cons.describe like '%" + resultJson.getString("describe") + "%'";
			countSql.append(" and cons.describe like '%" + resultJson.getString("describe") + "%'");
			}			
		}else{
			sql = " cons.*,org.[describe] orgDesc from " + YDTable.TABLECLASS_CONSPARA + " cons,"+"orgpara org where cons.org_id = org.id and cons.app_type = " + SDDef.APPTYPE_JC;
			countSqls = "select count(*) from " + YDTable.TABLECLASS_CONSPARA + " cons," + "orgpara org where cons.org_id = org.id and cons.app_type = " + SDDef.APPTYPE_JC;		
			countSql.append(countSqls);		
			
			if(result != null && !result.isEmpty()){
			sql += " and cons.describe like '%" + resultJson.getString("describe") + "%'";
			countSql.append(" and cons.describe like '%" + resultJson.getString("describe") + "%'");
			}						
		}

		sql += " order by cons.id desc) sub order by sub.id";
		sql_page.setTotalrecords(countSql.toString());
		count = sql_page.getTotalrecords();
		
		if(pagesize == 0){
			 sql="select top " + count + " * from (select top "+ count +" " + sql;
		}else{
			if(pagesize > count){
				sql = "select top "+pagesize+" * from (select top "+ count +" " + sql;
			}else{
				sql = "select top "+pagesize+" * from (select top "+(count - pagesize*(page-1)) + sql;
			}
		}
		if(sql_page.getTotalrecords()==0){
			result = SDDef.EMPTY;
			return SDDef.SUCCESS;
		}
		if (pagesize == 0) {
			pagesize = sql_page.getTotalrecords();
			sql_page.setPagesize(pagesize);
		}
		list = sql_page.getRecord(sql);
		String queryYffPerssion = "select * from userrankbound where user_id =" + resultJson.getString("yffId");
		List listYffPerssion = jdbcDao.result(queryYffPerssion);

		int no = pagesize * (sql_page.getCurrentpage() - 1) + 1;
		ret_buf.append(SDDef.JSONTOTAL + sql_page.getTotalrecords() + SDDef.JSONPAGEROWS);		
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map =  (Map<String, Object>) list.get(i);
			ret_buf.append(SDDef.JSONLBRACES); 
			ret_buf.append(SDDef.JSONQUOT + "id" + SDDef.JSONQACQ + map.get("id") + "\",data:[");			
			if(yffmandef.getOrgId() != null){
				boolean flag = false;
				for(int j =0;j< listYffPerssion.size();j++){
					Map<String, Object> mapYffPerssion =  (Map<String, Object>) listYffPerssion.get(j);
					if(Short.parseShort(mapYffPerssion.get("cons_id").toString()) == Short.parseShort(map.get("id").toString())){
						ret_buf.append(SDDef.JSONQUOT + 1 + SDDef.JSONCCM);
						flag = true;
						break;
					}
				}
				if(flag == false){
					ret_buf.append(SDDef.JSONQUOT + 0 + SDDef.JSONCCM);
				}				
			}else{
				ret_buf.append(SDDef.JSONQUOT + 1 + SDDef.JSONCCM);
			}
			ret_buf.append(SDDef.JSONQUOT + (no++) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("orgDesc")) + SDDef.JSONCCM);
			ret_buf.append(SDDef.JSONQUOT + CommBase.CheckString(map.get("describe")) + SDDef.JSONCCM);
			ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
			ret_buf.append(SDDef.JSONRBCM);
		}
		ret_buf.setCharAt(ret_buf.length() - 1, SDDef.JSONRBRACKET.charAt(0));
		ret_buf.append(SDDef.JSONRBRBRBR);
		result = ret_buf.toString();
		return SDDef.SUCCESS;
	}	
	
	public String insertConsparaPerssion(){
		JDBCDao 		jdbcDao	    = new JDBCDao();
		JSONObject		resultJson	= JSONObject.fromObject(result);
		
		String			deleteSql	= "delete from userrankbound where user_id ="+ resultJson.getInt("yffmanId");
		String[] 		conIdsStr	= null;
		if(jdbcDao.executeUpdate(deleteSql)){
			if(!resultJson.getString("consIds").equals("")){
				conIdsStr = resultJson.getString("consIds").split(SDDef.JSONSPLIT);
				int count = 0;
				for(int i = 0; i< conIdsStr.length;i++){
					String 	insertSql = "insert into userrankbound(id,user_id,type,cons_id) " +
										 "values("+getUserrankboundId()+","+resultJson.getInt("yffmanId")+","+"3"+","+conIdsStr[i]+")";
					if(jdbcDao.executeUpdate(insertSql)){
						count++;
					}				
				}
				if(count == conIdsStr.length){
					result = SDDef.SUCCESS;
				}else{
					result = SDDef.FAIL;
				}				
			}else{
				result = SDDef.SUCCESS;
			}
		}else{
			result = SDDef.FAIL;
		}
		return SDDef.SUCCESS;
	}
	
	private int getUserrankboundId(){
		int id = 1;
		JDBCDao jdbc = new JDBCDao();
		String sql = "select max(id) id from userrankbound";
		List<Map<String, Object>> list = jdbc.result(sql);
		if(list.size() == 0){
			return 1;
		}else{
			id = CommFunc.objectToInt(list.get(0).get("id")) + 1;
		}
		return id;
	}	
	public String getResult() 
	{
		return result;
	}
	
	public void setResult(String result) 
	{
		this.result = result;
	}
	
	public String getPageNo() 
	{
		return pageNo;
	}
	
	public void setPageNo(String pageNo) 
	{
		this.pageNo = pageNo;
	}
	
	public String getPageSize() 
	{
		return pageSize;
	}
	
	public void setPageSize(String pageSize) 
	{
		this.pageSize = pageSize;
	}
	
	public RtuPara getRtuPara() {
		return rtuPara;
	}

	public void setRtuPara(RtuPara rtuPara) {
		this.rtuPara = rtuPara;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public RtuCommPara getRtuCommPara() {
		return rtuCommPara;
	}

	public void setRtuCommPara(RtuCommPara rtuCommPara) {
		this.rtuCommPara = rtuCommPara;
	}
	
}

