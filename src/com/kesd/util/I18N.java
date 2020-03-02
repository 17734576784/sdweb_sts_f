package com.kesd.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import com.kesd.common.SDDef;
import com.kesd.common.WebConfig;

public class I18N {
	
	private static ResourceBundle rb = ResourceBundle.getBundle(SDDef.BUNDLENAME, WebConfig.app_locale);
	
	public static String getText(String key, Object... obj){
		String msg = "";
		try{
			msg = rb.getString(key);	
		}catch(Exception e){
			System.out.println("can't find key:" + key);
		}
		return 	MessageFormat.format(msg, obj);
	}
}
