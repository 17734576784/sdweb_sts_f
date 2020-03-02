package com.kesd.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import com.kesd.common.WebConfig;

/** 登录用户状态 异地登录被迫下线 */
public class OnlineUserState {
	private static Timer timer = null;
	public static int TIMEOUT = 0;

	/** 用户信息 */
	public static class User {
		public String session_id; // session id 唯一
		public String username; // 登录用户
		public Date log_time; // 登录时间
		public Date old_time; // 扫描前的时间，扫描后更新
		public String log_ip; // 登录IP

		public String getSession_id() {
			return session_id;
		}

		public void setSession_id(String sessionId) {
			session_id = sessionId;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public Date getLog_time() {
			return log_time;
		}

		public void setLog_time(Date logTime) {
			log_time = logTime;
		}

		public Date getOld_time() {
			return old_time;
		}

		public void setOld_time(Date oldTime) {
			old_time = oldTime;
		}

		public String getLog_ip() {
			return log_ip;
		}

		public void setLog_ip(String logIp) {
			log_ip = logIp;
		}

	}
	/**在线用户列表*/
	private static Map<String,User> online_user     = null;
	
	/** +++++++++++++++ FUNCTION DESCRIPTION ++++++++++++++++++
	* <p>
	* <p>NAME        : scan
	* <p>
	* <p>DESCRIPTION : 扫描任务，间隔TASKINTERVAL时自动执行一次
	* <p>
	* <p>COMPLETION
	* <p>INPUT       : 
	* <p>OUTPUT      : 
	* <p>RETURN      : void
	* <p>
	*-----------------------------------------------------------*/
	public static void scan(){
		
		try{
//			DeleteTempFiles.doDelete();
			
			if(online_user == null || online_user.size() == 0){
				return;
			}
			
			Iterator<Entry<String,User>> it = online_user.entrySet().iterator();
			long tmp = 0;
			while(it.hasNext()){
				Entry<String,User> en = (Entry<String,User>) it.next();
				User user_tmp = en.getValue();
				tmp = (new Date()).getTime() - user_tmp.getOld_time().getTime();
				
				if (tmp >= WebConfig.session_timeout) {	//查看session是否过期，若过期将用户从map中删除
					user_logout(en.getKey());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			
			if(TIMEOUT <= 3){
				System.out.println("restart scan...");
				Timer timer = new Timer(true);
				timer.schedule(new UserTask(), 0, WebConfig.session_timeout);
			}
			TIMEOUT++;
		}
	}
		
 

	/** 用户登录时 加入列表 */
	public static void user_login(String s_id, String name, Date log_time,
			String log_ip) {
		if (online_user == null) { // 初始化时启动任务
			online_user = new HashMap<String, User>();
			timer = new Timer();
			timer.schedule(new UserTask(), 0,WebConfig.session_timeout + 10000);
		}

		// 加入用户信息
		User user = new User();
		user.session_id = s_id;
		user.username = name;
		user.log_time = log_time;
		user.old_time = log_time;
		user.log_ip = log_ip;

		online_user.put(user.username, user);
	}

	/** 用户注销 */
	public static void user_logout(String name) {
		if (online_user == null || online_user.size() == 0)
			return;
		online_user.remove(name);
	}

	public static boolean check(String s_id, String name) {
		boolean ret = true;
		for (int i = 0; i < online_user.size(); i++) {
			User tmp = online_user.get(name);
			if (name.equals(tmp.username) && !tmp.session_id.equals(s_id)) {
				ret = false;
				break;
			}
		}

		return ret;
	}

	public static Map<String, User> getOnlineUser() {
		return online_user;
	}
}

/**
 * ******************* CLASS DESCRIPTION *******************
 * <p>
 * <p>
 * NAME : UserTask
 * <p>
 * <p>
 * DESCRIPTION : 扫描任务类
 * <p>
 * <p>
 * No. Date Modifier Description
 * <p>
 * ---------------------------------------------------------
 **************************************************************/
class UserTask extends TimerTask {
	public void run() {
		OnlineUserState.scan();
	}
}
