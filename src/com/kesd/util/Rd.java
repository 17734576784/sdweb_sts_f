package com.kesd.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kesd.common.Dict;
import com.kesd.common.SDDef;
import com.kesd.common.YDTable;
import com.kesd.dbpara.Diction;
import com.kesd.dbpara.YffAlarmPara;
import com.kesd.dbpara.YffRatePara;
import com.libweb.common.CommBase;
import com.libweb.dao.HibDao;

@SuppressWarnings("unchecked")
public class Rd {
	
	public static Map<String, List<Object>> db = new HashMap<String, List<Object>>();//内存中的数据
	
	public static void init(){
		initDict();
	}

	/**数据字典 放入内存*/
	public static void initDict(){
		
		if(db == null){
			db = new HashMap<String, List<Object>>();
		}
		try{
			HibDao hib_dao = new HibDao();
			String hql = "from " + YDTable.TABLECLASS_DICTION + " order by typeNo, value";
			List list = hib_dao.loadAll(hql);
			Iterator it = list.iterator();
			List obj_list = new ArrayList();
			while(it.hasNext()){
				Diction dict = (Diction)it.next();
				obj_list.add(dict);
			}
			db.put(YDTable.TABLECLASS_DICTION, obj_list);//数据字典
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getDict(String type, Object value){
		if(value == null) return "";
		if(type.indexOf("_") < 0) {
			return "";
		}
		
		int pos = type.lastIndexOf("_");
		short type_no = (short)CommBase.strtoi(type.substring(pos + 1));
		
		int val = CommBase.strtoi(value.toString());
		
		String ret_str = "";
		List list = db.get(YDTable.TABLECLASS_DICTION);
		Diction dict = null;
		
		int high = list.size() - 1;
		int low = 0;
		int mid = 0;
		
		//二分查找
		while(high >= low){
			mid = (high + low) / 2;
			
			dict = (Diction)list.get(mid);
			
			if(dict.getTypeNo() > type_no){
				high = mid - 1;
			}else if(dict.getTypeNo() < type_no){
				low = mid + 1;
			}else{
				//找到type_no 继续找value
				
				int i = 1;
				while(val != dict.getValue()) {
					
					if(val > dict.getValue()) {
						dict = (Diction)list.get(mid + i); 
					}
					else {
						dict = (Diction)list.get(mid - i);
					}
					
					if(dict.getTypeNo() != type_no){
						return "";
					}
					
					i++;
				}
				
				ret_str = dict.getItemName();
				break;
			}
		}
		/*
		for (int i = 0; i < list.size(); i++) {
			dict = (Diction)list.get(i);
			if(dict.getTypeNo() == type_no && dict.getValue() == val){
				ret_str = dict.getItemName();
				break;
			}
		}
		*/
		return ret_str;
	}
	
	
	/**根据类型名称查找数据字典*/
	public static Map<Integer, String> getDict(String type){
		
		Map<Integer, String> map = new HashMap<Integer, String>();
		
		if(type.indexOf("_") < 0) {
			return map;
		}
		
		int pos = type.indexOf("_");
		short type_no = (short)CommBase.strtoi(type.substring(pos + 1));
		
		List list = db.get(YDTable.TABLECLASS_DICTION);
		Diction dict = null;
		
		int high = list.size() - 1;
		int low = 0;
		int mid = 0;
		
		//二分查找
		while(high >= low){
			mid = (high + low) / 2;
			
			dict = (Diction)list.get(mid);
			
			if(dict.getTypeNo() > type_no){
				high = mid - 1;
			}else if(dict.getTypeNo() < type_no){
				low = mid + 1;
			}else{
				//找到type_no 定位该type到第一条
				int i = 1;
				while(true){
					if((mid - i) < 0) {
						break;
					}
					
					dict = (Diction)list.get(mid - i);
					short tmp_no = dict.getTypeNo();
					
					if(tmp_no != type_no) {
						break;
					}
					i++;
				}
				
				for (int j = mid - i + 1; j < list.size(); j++) {
					dict = (Diction)list.get(j);
					short tmp = dict.getTypeNo();
					if(tmp != type_no)break;
					map.put(dict.getValue(), dict.getItemName());
				}
				break;
			}
			
		}
		
		/*
		for (int i = 0; i < list.size(); i++) {
			dict = (Diction)list.get(i);
			
			//找到所需数据字典 并将各项放入容器
			if(dict.getTypeNo() == type_no){
				for (int j = i; j < list.size(); j++) {
					dict = (Diction)list.get(j);
					short tmp = dict.getTypeNo();
					if(tmp != type_no)break;
					map.put(dict.getValue(), dict.getItemName());
				}
				
				break;
			}
		}
		*/
		return map;
	}
	
	/**在数据库中查找费率信息*/
	public static List<YffRatePara> getYffRate() {
		List<YffRatePara> list = new ArrayList<YffRatePara>();
		HibDao hib_dao = new HibDao();
		list = hib_dao.loadAll("from " + YDTable.TABLECLASS_YFFRATEPARA);
		return list;
	}
	
	/**根据id获取一条费率信息*/
	public static YffRatePara getYffRate(List<YffRatePara> list, short id) {
		YffRatePara tmp_rate = null;
		for(int j = 0; j < list.size(); j++) {
			tmp_rate = list.get(j);
			if(tmp_rate.getId() == id) {
				return tmp_rate;
			}
		}
		return tmp_rate;
	}
	
	/**在数据库中查找报警信息*/
	public static List<YffAlarmPara> getAlarmPara() {
		List<YffAlarmPara> list = new ArrayList<YffAlarmPara>();
		HibDao hib_dao = new HibDao();
		list = hib_dao.loadAll("from " + YDTable.TABLECLASS_YFFALARMPARA);
		return list;
	}
	
	/**根据id获取一条报警信息*/
	public static YffAlarmPara getAlarmPara(List<YffAlarmPara> list, short id) {
		YffAlarmPara tmp_rate = null;
		for(int j = 0; j < list.size(); j++) {
			tmp_rate = list.get(j);
			if(tmp_rate.getId() == id) {
				return tmp_rate;
			}
		}
		return tmp_rate;
	}
	
	/**根据id在数据库中查找一条记录，主要用于yffrate和alarm，主键short型*/
	public static Object getRecord(String o_name, Short id){
		
		if(o_name == null || o_name.isEmpty() || id == null) return null;
		
		HibDao hib_dao = new HibDao();
		
		try {
			o_name = "com.kesd.dbpara." + o_name;
			return hib_dao.loadById(Class.forName(o_name), id);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	

	/**
	 * 根据YffRatePara取得"费率方案描述"字符串
	 * @param YffRatePara
	 * @return String
	 */
	public static String getYffRateDesc(YffRatePara yffrate){
		if(yffrate == null)return "";
		
		String strDesc = null;
			
		switch(yffrate.getFeeType()){
		case SDDef.YFF_FEETYPE_DFL: 
			strDesc = "total:" + yffrate.getRatedZ();
			break;
		case SDDef.YFF_FEETYPE_FFL: 
			strDesc= "尖:"+yffrate.getRatefJ()+" 峰:"+yffrate.getRatefF()+" 平:"+yffrate.getRatefP()+" 谷:"+yffrate.getRatefG();
			break;
//		case SDDef.YFF_FEETYPE_MIX: 
//			strDesc="混一:"+yffrate.getRateh1()+"["+yffrate.getRatehBl1()+"] 混二:"+yffrate.getRateh2()+"["+yffrate.getRatehBl2()+"] 混三:"+yffrate.getRateh3()+"["+yffrate.getRatehBl3()+"] 混四:"+yffrate.getRateh4()+"["+yffrate.getRatehBl4()+"]";
//			break;
		case SDDef.YFF_FEETYPE_JTFL:	
			strDesc = "梯度1:" + yffrate.getRatejR1() + "[" + yffrate.getRatejTd1() + "],梯度2:" + yffrate.getRatejR2() + "[" + yffrate.getRatejTd2() + "],梯度3:" + yffrate.getRatejR3() + "[" + yffrate.getRatejTd3() + "],梯度4:" + yffrate.getRatejR4();
			break;
//		case SDDef.YFF_FEETYPE_MIXJT:	//混合阶梯:混一(阶梯):[阶梯费率1/2/3/4/:{0}/{1}/{2}/{3}, 梯度值1/2/3:{4}{5}{6}][{7}%],混二:{8}[{9}%],混三:{10}[{11}%]
//			strDesc = "混一(阶梯):[阶梯费率1/2/3/4/:"+yffrate.getRatehjHr1R1()+"/"+yffrate.getRatehjHr1R2()+"/"+yffrate.getRatehjHr1R3()+"/"+yffrate.getRatehjHr1R4()+", " +
//					  "梯度值1/2/3:"+yffrate.getRatehjHr1Td1()+"/"+yffrate.getRatehjHr1Td2()+"/"+yffrate.getRatehjHr1Td3()+"]["+yffrate.getRatehjBl1()+"%]," +
//					  "混二:"+yffrate.getRatehjHr2()+"["+yffrate.getRatehjBl2()+"%]," +
//					  "混三:"+yffrate.getRatehjHr3()+"["+yffrate.getRatehjBl3()+"%]";
//			break;
		}
//		strDesc += "    MaxCreditLimit:" + yffrate.getMoneyLimit();
		
		if(strDesc == null){
			strDesc = I18N.getText("invalid")+I18N.getText("flfa");	
		}
		
		return strDesc;
	}

	/**
	 * 根据YffRatePara取得"费率方案"费率
	 * @param YffRatePara
	 * @return String
	 */
	public static String getYffRateVal(YffRatePara yffrate){
		if(yffrate == null)return "";
		
		String strDesc = null;

		switch(yffrate.getFeeType()){
		case SDDef.YFF_FEETYPE_DFL:
			strDesc=yffrate.getRatedZ() + "";
			break;
		case SDDef.YFF_FEETYPE_FFL:
			strDesc=yffrate.getRatefJ()+","+yffrate.getRatefF()+","+yffrate.getRatefP()+","+yffrate.getRatefG();
			break;
		case SDDef.YFF_FEETYPE_MIX: 
			strDesc=yffrate.getRateh1()+","+yffrate.getRatehBl1()+","+yffrate.getRateh2()+","+yffrate.getRatehBl2()+","+yffrate.getRateh3()+","+yffrate.getRatehBl3()+","+yffrate.getRateh4()+","+yffrate.getRatehBl4();
			break;
		case SDDef.YFF_FEETYPE_JTFL:
			strDesc=yffrate.getRatejR1()+","+yffrate.getRatejTd1()+","+yffrate.getRatejR2()+","+yffrate.getRatejTd2()+","+yffrate.getRatejR3()+","+yffrate.getRatejTd3()+","+yffrate.getRatejR4();
			break;
			
		}
		
		if(strDesc == null){
			strDesc = I18N.getText("invalid")+I18N.getText("flfa");	
		}
		
		return strDesc;
	}

	/**
	 * 根据YffAlarmPara取得"报警方案描述"字符串
	 * @param YffAlarmPara
	 * @return String	
	 */
	public static String getYffAlarmDesc(YffAlarmPara yffalarm){
		if (yffalarm == null) return "";
		String strDesc = null;
			
		int type = yffalarm.getType();
		strDesc=I18N.getText("bjfams",getDict(Dict.DICTITEM_ALARMTYPE, type),yffalarm.getAlarm1(),yffalarm.getAlarm2());	//"报警方式:%s, 报警值一:%d, 报警值二:%d",					
		
		if(strDesc == null){
			strDesc=I18N.getText("invalid")+I18N.getText("bjfa");	
		}
		
		return strDesc;
	}
	
}
