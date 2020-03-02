package com.kesd.util;

import net.sf.json.JSONObject;

public class MSG_RESP {
	
	private int 	retcode;
	private JSONObject obj ;
	
	public int getRetcode() {
		return retcode;
	}
	public void setRetcode(int retcode) {
		this.retcode = retcode;
	}
	public JSONObject getObj() {
		return obj;
	}
	public void setObj(JSONObject obj) {
		this.obj = obj;
	}
	
}
