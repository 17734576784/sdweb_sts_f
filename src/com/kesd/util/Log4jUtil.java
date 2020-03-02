package com.kesd.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
 
public class Log4jUtil {
	//Logger实例  
    public Logger logger = null;  
      
    //将Log类封装为单例模式  
    private static Log4jUtil log;  
      
     //构造函数，用于初始化Logger配置需要的属性  
    private Log4jUtil() {  
         //获得当前目录路径     
        String real_basepath = new File(Log4jUtil.class.getResource("/").getPath()).getParent();
       
        try {
			real_basepath = URLDecoder.decode(real_basepath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        
        
        //找到log4j.properties配置文件所在的目录(已经创建好)
        String filePath = real_basepath + "\\classes\\log4j.properties";
     
        //获得日志类logger的实例     
        logger=Logger.getLogger(this.getClass());     
        //logger所需的配置文件路径     
        PropertyConfigurator.configure(filePath);   
    }  
      
    public static Log4jUtil getLogger() {  
        if(log != null) return log;  
        else return new Log4jUtil();  
    }  
}
