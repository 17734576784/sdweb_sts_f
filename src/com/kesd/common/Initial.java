package com.kesd.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kesd.common.SDDef;
import com.kesd.common.WebConfig;
import com.kesd.comnt.ComntProc;
import com.kesd.dbpara.YffManDef;
import com.kesd.util.AutoTask;
import com.kesd.util.Menu;
import com.kesd.util.OnlineUserState;
import com.kesd.util.Rd;
import com.kesd.util.OnlineUserState.User;
import com.libweb.dao.HibSessionFactory;
import com.sts.rsa.GenRSAKeys;

public class Initial implements Filter {

	private String filter;
	private String nofilter;
	private String filterFiles[];
	private String nofilterFiles[];
	private String sendRedirect;
	
	
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		
		
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		
		String realpath = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+realpath+"/";
		String path = request.getServletPath();
		//System.out.println(path);
		HttpSession session = request.getSession();
		
		if(isInArray(path,nofilterFiles)){		//无需过滤页面
			if(path.equals("/login.jsp")){
				if (checkUser(session)) {
					//response.sendRedirect("index.jsp");
				}
			}
			chain.doFilter(request, response);
			return ;
		}
		
		if(startWith(path,filterFiles)){		//需要过滤页面
			
			if (checkUser(session)) {	//检测session判断用户是否已登录
				
				if(path.indexOf("/jsp/dyjc/") >= 0 || path.indexOf("/jsp/spec/") >= 0 || path.indexOf("/jsp/np/") >= 0){
					
					YffManDef yffman = (YffManDef)session.getAttribute(SDDef.SESSION_USERNAME);
					String name = yffman.getName();
					Map<String,User> map = OnlineUserState.getOnlineUser();
					User userinfo = map.get(name);
					if(userinfo != null){
						userinfo.setOld_time(new Date());
					}
					if(yffman.getName().equals(SDDef.ADMIN)){
						chain.doFilter(request, response);
						return;
					}
					
					if(Menu.enable(yffman, path)){
						chain.doFilter(request, response);
						return;
					}else{
						timeout(response, basePath, "\u65E0\u6743\u9650!");
						return;
					}
					
				}else{
					chain.doFilter(request, response);
					return;
				}
			} else {	//用户未登录或登录超时，返回登录页面
				//timeout(response, basePath, "\u767B\u5F55\u8D85\u65F6\uFF0C\u8BF7\u91CD\u65B0\u767B\u5F55！");
				timeout(response, basePath, "Your login session has expired, Please relogin and try again!");
				return;
			}
		}else{
			chain.doFilter(request, response);
		}
	}
	private void timeout(HttpServletResponse response, String basePath, String timeout) {
		try {
			response.setCharacterEncoding("GBK");
			PrintWriter out;
			out = response.getWriter();
			out.print("<html><meta http-equiv=\"Content-Type\" content=\"text/html;charset=GBK\"/><body><script>alert('"+timeout+"');window.top.close();window.open('"+basePath+sendRedirect+"','','left=100,top=50,height=600,width=950');</script></body></html>");
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private boolean startWith(String path,String[] filters){
		for(int i=0;i<filters.length;i++){
			if(path.startsWith(filters[i])){
				return true;
			}
		}
		return false;
	}
	private boolean checkUser(HttpSession session) throws IOException {
		
		Object obj = session.getAttribute(SDDef.SESSION_USERNAME);
		if (obj == null) {
			return false;
		}
		
			YffManDef yffman = (YffManDef)session.getAttribute(SDDef.SESSION_USERNAME);			
		
		String name = yffman.getName();
		Map<String,User> map = OnlineUserState.getOnlineUser();
		User userinfo = map.get(name);
		if(userinfo == null){
			session.removeAttribute(SDDef.SESSION_USERNAME);			
			return false;
		}
		
		return true;
	}

	private boolean isInArray(String path, String nofilterFiles[]) {
		for (int i = 0; i < nofilterFiles.length; i++) {
			String nofilterFile = nofilterFiles[i];
			if (nofilterFile.equals(path)) {
				return true;
			}
		}
		return false;
	}
	
	public void init(FilterConfig config) throws ServletException {
		
		filter = "/ajax,/jsp/";
		filterFiles = filter.split(",");
		nofilter = "/login.jsp,/regMsg.jsp,/ajax/actLogin!regMsg.action,/ajax/actLogin.action,/ajax/actLogin!logout.action";
		nofilterFiles = nofilter.split(",");
		sendRedirect = "login.jsp";
		
		HibSessionFactory.cparaSF();
		WebConfig.init();
		Rd.init();
		Menu.initMenu();
		AutoTask.initAutoTask();
		AutoTask.initCreateNewDataBaseTableAutoTask();
//		try{
//			GenKeys.setPublicAndPrivateFilePath();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		ComntProc.startComntProc();	//启动通讯线程
	}
	
	public void destroy() {
		ComntProc.stopComntProc();	//终止通讯线程	
	}
}
