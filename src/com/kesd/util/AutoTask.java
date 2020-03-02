/**
 *  dbr 2016-11-7上午11:01:00
 */
package com.kesd.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.sf.json.JSONArray;

import com.kesd.common.CommFunc;
import com.kesd.common.WebConfig;
import com.libweb.dao.JDBCDao;
import com.sts.ReadRechargeInfo;

/**
 * @author dbr
 *
 */
public class AutoTask {

	public static String real_basepath = "";
	public static void initAutoTask(){
		
		Runnable task = new Runnable() {
			public void run() {
				// 要执行的任务
				try {
					Log4jUtil.getLogger().logger.info("hello");
					new ReadRechargeInfo().readRechargeRecord();
 				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("阿偶，异常了！");
					Log4jUtil.getLogger().logger.info("自动任务出现异常，异常信息： "+e.getMessage());
//					AutoTask.initAutoTask();
				}
			 
			}
		};
		
		ScheduledExecutorService taskService = Executors.newSingleThreadScheduledExecutor();
		taskService.scheduleAtFixedRate(task, 0, 10, TimeUnit.MINUTES);
	}

	public static void initCreateNewDataBaseTableAutoTask(){
		
		TimerTask task = new TimerTask() {
			public void run() {
				// 要执行的任务
				try {
					//判断是否是12月份
					if(CommFunc.nowMonth().equals("12")){
						Log4jUtil.getLogger().logger.info("执行建表任务");
						createDataBaseTable();						
					}
 				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("异常.");
					Log4jUtil.getLogger().logger.info("自动任务出现异常，异常信息： "+e.getMessage());
				}
			}
		};
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, (long)24*60*60*1000, (long)24*60*60*1000);
	}	
	
	//读文件 创建表
	private static boolean createDataBaseTable(){
		real_basepath = new File(new File(WebConfig.class.getResource("/").getPath()).getParent()).getParent();
		
		try {
			//获取sql脚本信息
			real_basepath = URLDecoder.decode(real_basepath, "UTF-8");
			StringBuffer sb 			= 	new StringBuffer();
			StringBuffer sbTable		=	new StringBuffer();
			JSONArray	 tableArray 	=	new JSONArray();
			JSONArray	 tableNameArray =	new JSONArray();
			try {
				String file_path = real_basepath + "\\dbscripts\\sql\\install\\yd_datatable.sql";
				file_path = URLDecoder.decode(file_path,"UTF-8");
				File file = new File(file_path);
				BufferedReader br = new BufferedReader(new FileReader(file));
				String  s 	 = "";
				boolean flag = false;	//判断是否为一个表脚本
				while((s = br.readLine()) != null){
					//if(s.contains("-- 标记开始")){
					if(s.contains("-- MarkStart")){
						flag = true;
					//}else if(s.contains("-- 标记结束")){
					}else if(s.contains("-- MarkEnd")){
						tableArray.add(sbTable.toString());
						sbTable.delete(0, sbTable.length());
						flag = false;
					}
					if(flag == true){
						if(!s.equals("go")){
							sbTable.append(s+"\n");							
						}
						if(s.contains("create table ")){
							tableNameArray.add(s);
						}
					}
					sb.append(s+"\n");
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			//判断库中是否存在表
			JDBCDao jdbcDao = new JDBCDao();
			for(int i = 0; i < tableArray.size();i++){
				String sql = "select count(*) from " + "yddataben.." + tableNameArray.getString(i).split("table ")[1].replace("2015", ""+CommFunc.nowYear());
				try{
					jdbcDao.executeQuery(sql);
				}catch(Exception e){
					if(jdbcDao.executeUpdate("use YDDataBen " + tableArray.get(i).toString().replace("2015", ""+CommFunc.nowYear()))){
						System.out.print("创建成功." + tableNameArray.getString(i).split("table ")[1].replace("2015", ""+CommFunc.nowYear())+"\n");
					}
				}				
			}

			//不存在,执行建表脚本
			 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}		
		return true;
	}
	
	public static void main(String[] args) {
		AutoTask.initAutoTask();
	}
}

