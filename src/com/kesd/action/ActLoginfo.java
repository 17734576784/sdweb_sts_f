package com.kesd.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.interfaces.RSAPrivateKey;
import java.sql.ResultSet;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.kesd.common.CommFunc;
import com.kesd.common.SDDef;
import com.kesd.common.SDOperlog;
import com.kesd.common.WebConfig;
import com.kesd.common.YDTable;
import com.kesd.dbpara.YffManDef;
import com.kesd.util.OnlineUserState;
import com.libweb.dao.HibDao;
import com.libweb.dao.JDBCDao;
import com.opensymphony.xwork2.ActionSupport;
import com.sts.rsa.PrivateKeyReader;

public class ActLoginfo extends ActionSupport{

	private static final long serialVersionUID = 8025393588357472410L;
	
	private String opername;							//登录名称
	private String operdesc;							//登录名称
	private String pwd;									//登录密码
	private String result;
	private String subCode;                             //密钥串,注册码
	
	public String chgPsw(){
		
		//密码非法字符
		Pattern pat = Pattern.compile("[='<>]");
		Matcher mat = pat.matcher(pwd);
		if(mat.find()){
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		
		String old_pwd = CommFunc.getYffMan().getPasswd();
		if(old_pwd == null){
			old_pwd = "";
		}
		old_pwd = old_pwd.trim();
		
		if(!result.equals(old_pwd)){
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		
		opername = CommFunc.getYffMan().getName().trim();
		JDBCDao j_dao = new JDBCDao();
		
		String sql = "update yffmandef set passwd='" + pwd + "' where name='" + opername + "'";
		
		if(j_dao.executeUpdate(sql)){
			result = SDDef.SUCCESS;
			
			YffManDef yffman = CommFunc.getYffMan();
			yffman.setPasswd(pwd);
			
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			
			session.setAttribute(SDDef.SESSION_USERNAME, yffman);
			
		}else{
			result = SDDef.FAIL;
		}
		
		return SDDef.SUCCESS;
	}
	
	public String checkUser() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		
		YffManDef yffman = (YffManDef)session.getAttribute(SDDef.SESSION_USERNAME);
		if(yffman != null) {
			String s_id = session.getId();
			String name = CommFunc.getYffMan().getName();
			result = SDDef.SUCCESS;
			if(!OnlineUserState.check(s_id, name)) {
//				result = "您的账号在其他地点登录，您被迫下线!";
				result = "Login to achieve VendingSystem elsewhere, forced offline!";
			}
			else {
				opername = name; 
				operdesc = CommFunc.getYffMan().getDescribe();
			}
		}
		
		return SDDef.SUCCESS;
	}
	
	/**用户注销*/
	public String logout() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		
		YffManDef yffman = (YffManDef)session.getAttribute(SDDef.SESSION_USERNAME);
	    if(yffman != null) {
	    	if(result == null || !result.equals("true")) {
	    		OnlineUserState.user_logout(yffman.getName());
	    	}
	    	session.removeAttribute(SDDef.SESSION_USERNAME);
			SDOperlog.operlog(yffman.getId(), SDDef.LOG_LOGOUT, "Operman ["+ yffman.getName() +"] Logout System");

	    }
	    result = SDDef.SUCCESS;
		
		return SDDef.SUCCESS;
	}
	
	/**注册校验code是否正确*/
	public String regMsg() throws Exception{
		String operName = opername;
		//解密成明文
		String rtnClearStr = getClearCode(subCode);
		if(rtnClearStr.equals("fail")){
			return SDDef.SUCCESS;
		}
		//根据操作员名称获取所Id,读库得到注册码,
	    JSONObject rtnJson =getDataByOperName(operName);
	    if(rtnJson.getInt("listSize")==0){
	    	result = "No Archives.";
	    	return SDDef.SUCCESS;
	    }
	    //解密 
	    String regCode = rtnJson.getString("regCode");
		String dbClearStr = getClearCode(regCode);
		if(dbClearStr.equals("fail")){
			result = "Please Register Subscription Code.";
			return SDDef.SUCCESS;
		}
	    
		if(rtnClearStr.equals(dbClearStr)){
			result = SDDef.SUCCESS;			
		}else{
			result = "Subscription Code Error.";
			return SDDef.SUCCESS;
		}
		
		return SDDef.SUCCESS;
	}
	
	//根据操作员名称，获取注册码 
	public JSONObject getDataByOperName(String operName){
		List<Map<String, Object>>	list	= null;
		JSONObject resultParam = new JSONObject();
		
		JDBCDao j_dao = new JDBCDao();
		String sql = "SELECT orgr.org_id,orgr.reg_code,orgr.use_flag from org_reg orgr,yffmandef yffm " +
				     "WHERE yffm.org_id = orgr.org_id and yffm.name = '"+operName+"'";
		list = j_dao.result(sql);
		if(list.size()==0){
			resultParam.put("listSize", 	0);
		}else{
			resultParam.put("listSize", 	1);
			resultParam.put("orgId", 		list.get(0).get("org_id"));			
			resultParam.put("regCode", 		list.get(0).get("reg_code") == null ? "" : list.get(0).get("reg_code"));
			resultParam.put("use_flag", 	list.get(0).get("use_flag") == null ? 1 : list.get(0).get("use_flag"));
		}
		return resultParam;
	}
	
	//rsa 解密成明文
	public String getClearCode(String subcode) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");	
		String real_basepath = new File(new File(WebConfig.class.getResource(
				"/").getPath()).getParent()).getParent();
		try{
			try {
				real_basepath = URLDecoder.decode(real_basepath, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String privateKeyFilePath = real_basepath
					+ "\\WEB-INF\\classes\\privateRegSubCodeFile";			
			RSAPrivateKey privKey = (RSAPrivateKey) PrivateKeyReader.get(privateKeyFilePath);
			cipher.init(Cipher.DECRYPT_MODE, privKey); 
			byte[] plainText = cipher.doFinal(CommFunc.hexStringToBytes(subcode));
			return new String(plainText);
		}catch(Exception e){
			result = "Subscription Code invalid.";
		}
		return SDDef.FAIL;
	}
	
	
	@SuppressWarnings("unchecked")
	public String execute() {		//登陆验证
		
		result = SDDef.FAIL;
		if(opername == null || opername.isEmpty() || pwd == null || pwd.isEmpty()){
			result = SDDef.FAIL;
			return SDDef.SUCCESS;
		}
		
		HibDao hib_dao = new HibDao();
		
		try{
			String hql = "from " + YDTable.TABLECLASS_YFFMANDEF + " as a where a.name='" + opername + "'";
			List list = hib_dao.loadAll(hql);
			if(list.size() == 1){
				YffManDef yffman = (YffManDef)list.get(0);
				
				if(!yffman.getName().trim().equals(opername)) {
					result = SDDef.FAIL;
					return SDDef.SUCCESS;
				}
				
				String passwd = yffman.getPasswd();
				
				if(/*(opername.equals(SDDef.ADMIN) && pwd.equals(SDDef.ADMIN_PWD)) ||*/ passwd.equals(pwd)){
					
					if(!opername.equals("sdadmin")){
						//根据操作员名称获取所Id,读库得到注册码,
					    JSONObject rtnJson =getDataByOperName(opername);
					    if(rtnJson.getInt("listSize")==0){
					    	result = "No Archives.";
					    	return SDDef.SUCCESS;
					    }
					    //添加标志位	//1是使用、0是不是使用
					    if(rtnJson.getInt("use_flag") == 1){
						    //regCode为null时,约定为没有注册过.
						    if(rtnJson.get("regCode")==null){
						    	result = "Please Register Subscription Code";
						    	return SDDef.SUCCESS;
						    }
						    //解密 
						    String regCode = rtnJson.getString("regCode");
							String dbClearStr = getClearCode(regCode);
							if(dbClearStr.equals("fail")){
								result = "Subscription Code Error.";
								return SDDef.SUCCESS;
							}
							
							//校验是否过期
							Format f = new SimpleDateFormat("yyyy-MM-dd");
							Calendar c = Calendar.getInstance();
							
							//格式:2018-11-27
							String dbInvalidDate = dbClearStr.split("_")[1];

							if(CommFunc.objectToInt(CommFunc.YMDtoInt(f.format(c.getTime()),1)) > CommFunc.objectToInt(CommFunc.YMDtoInt(dbInvalidDate,1))){
								result = "Subscription Code has expired.";
								return SDDef.SUCCESS;						
							}					    		
					    }
					}
					
					HttpServletRequest request = ServletActionContext.getRequest();
					HttpSession session = request.getSession();
					
					Date date = new Date();
					java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
					yffman.setReserve2(sdf.format(date));
					
					session.setAttribute(SDDef.SESSION_USERNAME, yffman);
					
					OnlineUserState.user_login(session.getId(), opername, date, request.getRemoteAddr());
					
					SDOperlog.operlog(CommFunc.getUser().getId(), SDDef.LOG_LOGIN, "Operman ["+ opername +"]Login System");

					//手动设置登录超时时间（秒）
					//session.setMaxInactiveInterval(30 * 60);
					
					
					result = SDDef.SUCCESS;
				}else{
					result = SDDef.FAIL;
				}
			}
			
		}catch (Exception e) {
			result = SDDef.FAIL;
		}
		
		return SDDef.SUCCESS;
	}
	
	public String getOpername() {
		return opername;
	}
	public void setOpername(String opername) {
		this.opername = opername;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	public String getOperdesc() {
		return operdesc;
	}
	public void setOperdesc(String operdesc) {
		this.operdesc = operdesc;
	}
	
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}	
}
