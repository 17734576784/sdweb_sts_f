package com.kesd.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.libweb.common.CommBase;
import com.libweb.dao.JDBCDao;

public class WebConfig {
	
	public static Locale	app_locale;
	
	/**
	 * 工程根目录  如 C:\Tomcat 6.0\webapps\ydweb
	 */
	public static String real_basepath;
	public static String tree_glo;
	public static long session_timeout;
	public static String cop_name;
	public static String login_single;
	public static String gycard_repairpay_flag;
	public static String dycard_repairpay_flag;
	public static String dylkcard_jtzbjs_flag;
	public static String nptool_whitelist_showflag;
	public static String nptool_blacklist_showflag;
	public static String provinceMisFlag;
	public static int rewrite_maxnum_np;		//农排写卡异常时重新写卡最大次数
	public static int reread_maxnum_np;		//农排读卡异常时重新读卡最大次数
	public static String  peak_time="";
	public static int sts_baseDate = 1;		//
	
	
	public static class EncryptModuleParam
	{
		public String   commport 	= "COM3";
		public String   useflag  	= "1";
		public String   baudrate	= "9600";
		public String   checkflag  	= "0";
		public String   databit		= "8";
		public String   stopbit     = "1";
	}
	
	public static EncryptModuleParam encryptModuleParam = new EncryptModuleParam();
	
	public static HashMap<String,Boolean> menuShow = new HashMap<String, Boolean>();
	
	//查询、报表、费控档案(低压、专变、农排)部分，页面加载时是否自动加载数据
	public static HashMap<String,String> autoShow	= new HashMap<String, String>();
	
	//MIS文件方式对账配置
	public static class GyMisJsConfig
	{
		//hbsg186---河北SG186
		public static class HBSg186
		{
			public String   bd_cons_no 		= "用户编号";
			public String   bd_cons_desc  	= "用户名称";
			public String   bd_factory_no	= "出厂编号";
			public String   bd_data_type  	= "示数类型";
			public String   bd_cur_data		= "本次示数";
			public boolean  bd_use_factnof	= false;
			public int		bd_title_lineno = 1;
			
			public String   ye_cons_no		= "用户编号";
			public String   ye_cons_desc	= "用户名称";
			public String   ye_remain		= "预收金额";
			public int		ye_title_lineno = 1;
		}
		
		//other_mis--其他地区MIS,没用
		public static class OtherMis
		{
			public String   bd_cons_no 		="用户编号";
		}
		
		public String       cur_type 		="hbsg186";
		
		public HBSg186   hb_sg186 = null;
		public OtherMis  ot_mis   = null;
	};
	
	public static class GyNegativeMoney
	{
		public int fuck = 1;
		public int gycard[] 	= new int[3];
		public int gylocbd[] 	= new int[3];
		public int gylocmny[] 	= new int[3];
		public int gymain[] 	= new int[3];
	}

	public static class DyNegativeMoney
	{
		public int dycard[] 	= new int[3];
		public int dylocmny[] 	= new int[3];
		public int dymain[] 	= new int[3];
	}
	
	public static class NpNegativeMoney
	{
		public int npcard[] 	= new int[3];
	}
	
	public static class DyMisJsConfig
	{
		//hbsg186---河北SG186
		public static class HBSg186
		{
			public String   bd_cons_no 		= "用户编号";
			public String   bd_cons_desc  	= "用户名称";
			public String   bd_factory_no	= "出厂编号";
			public String   bd_data_type  	= "示数类型";
			public String   bd_cur_data		= "本次示数";
			public int		bd_title_lineno = 1;
			
			public String   ye_cons_no		= "用户编号";
			public String   ye_cons_desc	= "用户名称";
			public String   ye_remain		= "预收金额";
			
			public String   ye_dbremain		= "电表余额";
			
			public int		ye_title_lineno = 1;
		}
		
		//other_mis--其他地区MIS,没用
		public static class OtherMis
		{
			public String   bd_cons_no 		="用户编号";
		}
		
		public String       cur_type 		="hbsg186";
		
		public HBSg186   hb_sg186 = null;
		public OtherMis  ot_mis   = null;
	};
	
	public static class DyDlMisJsConfig
	{
		//hnsg186---河南SG186
		public static class HNDlSg186
		{
			public String   dl_cons_no 		= "用户编号";
			public String   dl_cons_desc  	= "用户名称";
			public String   dl_cur_data		= "累计电量";
			public int		dl_title_lineno = 1;
		}

		//other_mis--其他地区MIS,没用
		public static class OtherDlMis
		{
			public String   dl_cons_no 		= "用户编号";
		}

		public String       cur_type 		= "hnsg186";
		public HNDlSg186   	hndl_sg186 		= null;
		public OtherDlMis  	otdl_mis   		= null;
	};

	public static GyMisJsConfig 	gyMisJsConfig 	= null;
	public static DyMisJsConfig 	dyMisJsConfig 	= null;
	
	public static DyDlMisJsConfig 	dyDlMisJsConfig = null;
	
	public static GyNegativeMoney 	gyNegativeMoney = null;
	public static DyNegativeMoney 	dyNegativeMoney = null;
	public static NpNegativeMoney 	npNegativeMoney = null;
	
	public static double		  	npMoneyLimit    = 2000.0;
	
	public static void init(){
		
		real_basepath = new File(new File(WebConfig.class.getResource("/").getPath()).getParent()).getParent();
		
		try {
			real_basepath = URLDecoder.decode(real_basepath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		Element root = getRoot();
		
		if(root == null){
			return;
		}
		setApp_locale(root);
		setmenuShow(root);
		cop_name  			= getCop_name(root);
		tree_glo 			= getTree_Glo(root);
		session_timeout 	= getSessionTimeout(root);
		login_single		= getLogin_single(root);
		gycard_repairpay_flag = getGyCard_repairpay_flag(root);
		dycard_repairpay_flag = getDyCard_repairpay_flag(root);
		dylkcard_jtzbjs_flag  = getDylkCard_jtzbjs_flag(root);
		nptool_whitelist_showflag =getNpTool_Remote_showflag(root,"nptool_whitelist_showflag");
		nptool_blacklist_showflag =getNpTool_Remote_showflag(root,"nptool_blacklist_showflag");
		provinceMisFlag		= getProvinceMisFlag(root,"provinceMisFlag");
		rewrite_maxnum_np	= getReDoMaxNumNP(root,"rewrite_maxnum_np");
		reread_maxnum_np	= getReDoMaxNumNP(root,"reread_maxnum_np");
		sts_baseDate        = getStsBaseDate(root,"sts_basedate");
		
		initGyMisJsConfig(root);
		initDyMisJsConfig(root);
		
		initDyDlMisJsConfig(root);
		
		initGyNegativeMoney(root);
		initDyNegativeMoney(root);
		initNpNegativeMoney(root);
		
		initNpMoneyLimit(root);
		
		getEncryptModuleParam(root);
		
		//获取页面加载时是否进行自动查询参数,赋值给autoShow
		getAutoShowFlag(root);
		
		createDataBaseTable();
	}
	
	public static Element getRoot(){
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Element root = null;
		try {
			DocumentBuilder    builder = factory.newDocumentBuilder();
			String file_path = real_basepath + "\\WEB-INF\\classes\\web_cfg.xml";
			file_path = URLDecoder.decode(file_path,"UTF-8");
			InputStream is   = new FileInputStream(file_path);
			Document    doc  = builder.parse(is);
			root = doc.getDocumentElement();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return root;
	}
	
	@SuppressWarnings("unchecked")
	public static List getYzje(){
		List list = new ArrayList();
		Element root = getRoot();
		
		NodeList nl = root.getElementsByTagName("yzje");
		Node node1 = nl.item(0);
		
		Element e = (Element)node1;
		nl = e.getElementsByTagName("item");
		for (int i = 0; i < nl.getLength(); i++) {
			
			String[] yzje = new String[2];
			
			Node an = nl.item(i);
			NamedNodeMap node_map = an.getAttributes();
			Node node2   = node_map.getNamedItem("value");
			yzje[0] = node2.getNodeValue();
			node2   = node_map.getNamedItem("text");
			yzje[1] = node2.getNodeValue();
			
			list.add(yzje);
		}
		
		return list;
	}
	
	public static String getShowFlag(String menuId){
		String rtn = "blank";
		try{
		if(!menuShow.get(menuId)){
			rtn = "none";
		}
		}catch(Exception e){
			
		}
		
		return rtn;
	}
	
	public static void setmenuShow(Element root){
		NodeList nl = root.getElementsByTagName("menu");
		Node node1 = nl.item(0);
		
		Element e = (Element)node1;
		nl = e.getElementsByTagName("item");
		for (int i = 0; i < nl.getLength(); i++) {
			
			Node an = nl.item(i);
			NamedNodeMap node_map = an.getAttributes();
			Node node2   = node_map.getNamedItem("menuid");
			String menuId = node2.getNodeValue();
			node2   = node_map.getNamedItem("showflag");
			Boolean showFlag = node2.getNodeValue().endsWith("0") ? false : true;
			menuShow.put(menuId,showFlag);
		}
	}
	
	public static void setApp_locale(Element root){
		if(root == null){
			app_locale = Locale.SIMPLIFIED_CHINESE;
		}
		NodeList ra = root.getElementsByTagName("app_locale");
		Node station = ra.item(0);
		NamedNodeMap node_map = station.getAttributes();
		Node stationNode   = node_map.getNamedItem("value");
		String locale = stationNode.getNodeValue();
		if(locale.equalsIgnoreCase("cn")){
			app_locale = Locale.SIMPLIFIED_CHINESE;
		}else if(locale.equals("en")){
			app_locale = Locale.US;
		}else{
			app_locale = Locale.SIMPLIFIED_CHINESE;
		}
	}

	private static String getCop_name(Element root){
		
		String tmp_str = "石家庄科林电气股份有限公司";
		
		if(root == null){
			return tmp_str;
		}
		NodeList ra = root.getElementsByTagName("cop_name");
		Node station = ra.item(0);
		NamedNodeMap node_map = station.getAttributes();
		Node stationNode   = node_map.getNamedItem("name");
		tmp_str = stationNode.getNodeValue();
		return tmp_str;
	}
	
	private static String getGyCard_repairpay_flag(Element root){
		
		String tmp_str = "1";
		
		if(root == null){
			return tmp_str;
		}
		NodeList ra = root.getElementsByTagName("gycard_repairpay_flag");
		Node station = ra.item(0);
		NamedNodeMap node_map = station.getAttributes();
		Node stationNode   = node_map.getNamedItem("value");
		tmp_str = stationNode.getNodeValue();
		return tmp_str;
	}
	
	private static String getDyCard_repairpay_flag(Element root){
		
		String tmp_str = "1";
		
		if(root == null){
			return tmp_str;
		}
		NodeList ra = root.getElementsByTagName("dycard_repairpay_flag");
		Node station = ra.item(0);
		NamedNodeMap node_map = station.getAttributes();
		Node stationNode   = node_map.getNamedItem("value");
		tmp_str = stationNode.getNodeValue();
		return tmp_str;
	}
	
	private static long getSessionTimeout(Element root){
		
		long timeout = 30;
		
		if(root == null){
			return timeout;
		}
		NodeList ra = root.getElementsByTagName("session");
		Node station = ra.item(0);
		NamedNodeMap node_map = station.getAttributes();
		Node stationNode   = node_map.getNamedItem("timeout");
		try{
			timeout = Long.parseLong(stationNode.getNodeValue());
			if(timeout <= 0){
				timeout = 30;
			}
		}catch (NumberFormatException e) {
			timeout = 30;
		}
		
		timeout = timeout * 1000 * 60;
		
		return timeout;
	}
	
	private static String getDylkCard_jtzbjs_flag(Element root)
	{
		String tmp_str = "1";
		
		if(root == null){
			return tmp_str;
		}
		NodeList ra = root.getElementsByTagName("dylkcard_jtzbjs_flag");
		Node station = ra.item(0);
		NamedNodeMap node_map = station.getAttributes();
		Node stationNode   = node_map.getNamedItem("value");
		tmp_str = stationNode.getNodeValue();
		return tmp_str;		
	}
	
	private static String getNpTool_Remote_showflag(Element root,String tagName){
		
		String tmp_str = "1";
		
		if(root == null){
			return tmp_str;
		}
		NodeList ra = root.getElementsByTagName(tagName);
		Node station = ra.item(0);
		NamedNodeMap node_map = station.getAttributes();
		Node stationNode   = node_map.getNamedItem("value");
		tmp_str = stationNode.getNodeValue();
		return tmp_str;
	}

	private static String getTree_Glo(Element root){
		
		String tree_glo = "";
		
		if(root == null){
			return tree_glo;
		}
		NodeList ra = root.getElementsByTagName("tree_global");
		Node station = ra.item(0);
		NamedNodeMap node_map = station.getAttributes();
		Node stationNode   = node_map.getNamedItem("name");
		tree_glo = stationNode.getNodeValue();
		return tree_glo;
	}
	
	private static String getLogin_single(Element root){
		
		String ls = "";
		
		if(root == null){
			return ls;
		}
		NodeList ra = root.getElementsByTagName("login_single");
		Node station = ra.item(0);
		NamedNodeMap node_map = station.getAttributes();
		Node stationNode   = node_map.getNamedItem("value");
		ls = stationNode.getNodeValue();
		return ls;
	}
	
	public static void initGyMisJsConfig(Element root){
		gyMisJsConfig = new GyMisJsConfig();
		
		NodeList misjs_s = root.getElementsByTagName("gyjs_file");
		Element node = (Element)misjs_s.item(0);
		
		NamedNodeMap node_map = node.getAttributes();
		Node sNode   = node_map.getNamedItem("type");
		gyMisJsConfig.cur_type = sNode.getNodeValue().trim();
		
		if (gyMisJsConfig.cur_type.compareToIgnoreCase("hbsg186") == 0) {
			gyMisJsConfig.hb_sg186 = new GyMisJsConfig.HBSg186();
			
			NodeList hbsg186_nodels = node.getElementsByTagName("hbsg186");
			Element  hbsg186_node = (Element)hbsg186_nodels.item(0);
			
			NodeList bd_nodels = hbsg186_node.getElementsByTagName("bd");
			Node bd_node = (Element)bd_nodels.item(0);
			
			//表底文件配置参数
			node_map = bd_node.getAttributes();
			
			//表底-用户编号
			sNode   = node_map.getNamedItem("cons_no");			
			gyMisJsConfig.hb_sg186.bd_cons_no = sNode.getNodeValue().trim();
			
			//表底-用户名称
			sNode   = node_map.getNamedItem("cons_desc");					
			gyMisJsConfig.hb_sg186.bd_cons_desc = sNode.getNodeValue().trim();			
			
			//表底-出厂编号
			sNode   = node_map.getNamedItem("factory_no");					
			gyMisJsConfig.hb_sg186.bd_factory_no = sNode.getNodeValue().trim();			
			
			//表底-示数类型
			sNode   = node_map.getNamedItem("data_type");
			gyMisJsConfig.hb_sg186.bd_data_type = sNode.getNodeValue().trim();
			
			//表底-本次示数
			sNode   = node_map.getNamedItem("cur_data");
			gyMisJsConfig.hb_sg186.bd_cur_data = sNode.getNodeValue().trim();
			
			//表底-是否使用出厂编号
			sNode   = node_map.getNamedItem("use_factnof");
			String tmp_str = sNode.getNodeValue().trim();
			gyMisJsConfig.hb_sg186.bd_use_factnof = ((tmp_str.compareToIgnoreCase("0") == 0) ? false : true);

			//表底-标题行号
			sNode   = node_map.getNamedItem("title_lineno");
			tmp_str = sNode.getNodeValue().trim();
			gyMisJsConfig.hb_sg186.bd_title_lineno = CommBase.strtoi(tmp_str);			
			
			//余额文件配置参数
			NodeList ye_nodels = hbsg186_node.getElementsByTagName("ye");
			Node ye_node = (Element)ye_nodels.item(0);
			
			node_map = ye_node.getAttributes();
			
			//余额-用户编号
			sNode   = node_map.getNamedItem("cons_no");
			gyMisJsConfig.hb_sg186.ye_cons_no = sNode.getNodeValue().trim();
			
			//余额-用户名称
			sNode   = node_map.getNamedItem("cons_desc");
			gyMisJsConfig.hb_sg186.ye_cons_desc = sNode.getNodeValue().trim();			

			//余额-预收金额
			sNode   = node_map.getNamedItem("remain");
			gyMisJsConfig.hb_sg186.ye_remain = sNode.getNodeValue().trim();
			
			//余额-标题行号
			sNode   = node_map.getNamedItem("title_lineno");
			tmp_str = sNode.getNodeValue().trim();
			gyMisJsConfig.hb_sg186.ye_title_lineno = CommBase.strtoi(tmp_str);					
		}
		else if (gyMisJsConfig.cur_type.compareToIgnoreCase("other_mis") == 0) {
			gyMisJsConfig.ot_mis = new GyMisJsConfig.OtherMis();
		}
	}
	
	public static void initDyMisJsConfig(Element root){
		dyMisJsConfig = new DyMisJsConfig();
		
		NodeList misjs_s = root.getElementsByTagName("dyjs_file");
		Element node = (Element)misjs_s.item(0);
		
		NamedNodeMap node_map = node.getAttributes();
		Node sNode   = node_map.getNamedItem("type");
		dyMisJsConfig.cur_type = sNode.getNodeValue().trim();
		
		if (dyMisJsConfig.cur_type.compareToIgnoreCase("hbsg186") == 0) {
			dyMisJsConfig.hb_sg186 = new DyMisJsConfig.HBSg186();
			
			NodeList hbsg186_nodels = node.getElementsByTagName("hbsg186");
			Element  hbsg186_node = (Element)hbsg186_nodels.item(0);
			
			NodeList bd_nodels = hbsg186_node.getElementsByTagName("bd");
			Node bd_node = (Element)bd_nodels.item(0);
			
			//表底文件配置参数
			node_map = bd_node.getAttributes();
			
			//表底-用户编号
			sNode   = node_map.getNamedItem("cons_no");			
			dyMisJsConfig.hb_sg186.bd_cons_no = sNode.getNodeValue().trim();
			
			//表底-用户名称
			sNode   = node_map.getNamedItem("cons_desc");					
			dyMisJsConfig.hb_sg186.bd_cons_desc = sNode.getNodeValue().trim();			
			
			//表底-出厂编号
			sNode   = node_map.getNamedItem("factory_no");					
			dyMisJsConfig.hb_sg186.bd_factory_no = sNode.getNodeValue().trim();			
			
			//表底-示数类型
			sNode   = node_map.getNamedItem("data_type");
			dyMisJsConfig.hb_sg186.bd_data_type = sNode.getNodeValue().trim();
			
			//表底-本次示数
			sNode   = node_map.getNamedItem("cur_data");
			dyMisJsConfig.hb_sg186.bd_cur_data = sNode.getNodeValue().trim();

			//表底-标题行号
			sNode   = node_map.getNamedItem("title_lineno");
			String tmp_str = sNode.getNodeValue().trim();
			dyMisJsConfig.hb_sg186.bd_title_lineno = CommBase.strtoi(tmp_str);			
			
			//余额文件配置参数
			NodeList ye_nodels = hbsg186_node.getElementsByTagName("ye");
			Node ye_node = (Element)ye_nodels.item(0);
			
			node_map = ye_node.getAttributes();
			
			//余额-用户编号
			sNode   = node_map.getNamedItem("cons_no");
			dyMisJsConfig.hb_sg186.ye_cons_no = sNode.getNodeValue().trim();
			
			//余额-用户名称
			sNode   = node_map.getNamedItem("cons_desc");
			dyMisJsConfig.hb_sg186.ye_cons_desc = sNode.getNodeValue().trim();			

			//余额-预收金额
			sNode   = node_map.getNamedItem("remain");
			dyMisJsConfig.hb_sg186.ye_remain = sNode.getNodeValue().trim();
			
			//余额-预收金额
			sNode   = node_map.getNamedItem("dbremain");
			dyMisJsConfig.hb_sg186.ye_dbremain = sNode.getNodeValue().trim();
			
			//余额-标题行号
			sNode   = node_map.getNamedItem("title_lineno");
			tmp_str = sNode.getNodeValue().trim();
			dyMisJsConfig.hb_sg186.ye_title_lineno = CommBase.strtoi(tmp_str);					
		}
		else if (gyMisJsConfig.cur_type.compareToIgnoreCase("other_mis") == 0) {
			dyMisJsConfig.ot_mis = new DyMisJsConfig.OtherMis();
		}
	}

	public static void initDyDlMisJsConfig(Element root) {
		dyDlMisJsConfig = new DyDlMisJsConfig();
		
		NodeList misjs_s = root.getElementsByTagName("dydljs_file");
		Element node = (Element)misjs_s.item(0);
		
		NamedNodeMap node_map = node.getAttributes();
		Node sNode   = node_map.getNamedItem("type");
		dyDlMisJsConfig.cur_type = sNode.getNodeValue().trim();
		
		if (dyDlMisJsConfig.cur_type.compareToIgnoreCase("hndlsg186") == 0) {
			dyDlMisJsConfig.hndl_sg186 = new DyDlMisJsConfig.HNDlSg186();
			
			NodeList hbsg186_nodels = node.getElementsByTagName("hndlsg186");
			Element  hbsg186_node = (Element)hbsg186_nodels.item(0);
			
			NodeList bd_nodels = hbsg186_node.getElementsByTagName("dl");
			Node dl_node = (Element)bd_nodels.item(0);
			
			//电量文件配置参数
			node_map = dl_node.getAttributes();
			
			//电量-用户编号
			sNode   = node_map.getNamedItem("cons_no");			
			dyDlMisJsConfig.hndl_sg186.dl_cons_no = sNode.getNodeValue().trim();
			
			//电量-用户名称
			sNode   = node_map.getNamedItem("cons_desc");					
			dyDlMisJsConfig.hndl_sg186.dl_cons_desc = sNode.getNodeValue().trim();			

			//电量-
			sNode   = node_map.getNamedItem("tot_dl");
			dyDlMisJsConfig.hndl_sg186.dl_cur_data = sNode.getNodeValue().trim();

			//电量-标题行号
			sNode   = node_map.getNamedItem("title_lineno");
			String tmp_str = sNode.getNodeValue().trim();
			dyDlMisJsConfig.hndl_sg186.dl_title_lineno = CommBase.strtoi(tmp_str);			
		}
		else if (gyMisJsConfig.cur_type.compareToIgnoreCase("other_mis") == 0) {
			dyDlMisJsConfig.otdl_mis = new DyDlMisJsConfig.OtherDlMis();
		}
	}
	
	public static void initGyNegativeMoney(Element root){
		gyNegativeMoney = new GyNegativeMoney();
		
		NodeList gymoneys = root.getElementsByTagName("gynegativemoney");
		Element  gymoney = (Element)gymoneys.item(0);

		//card
		NodeList gycards = gymoney.getElementsByTagName("gycard");
		Node gycard = (Element)gycards.item(0);	

		//卡式文件配置参数
		NamedNodeMap node_map = gycard.getAttributes();
			
		Node sNode   = node_map.getNamedItem("payflag");			
		gyNegativeMoney.gycard[0] = CommBase.strtoi(sNode.getNodeValue().trim());

		sNode   = node_map.getNamedItem("totflag");
		gyNegativeMoney.gycard[1] = CommBase.strtoi(sNode.getNodeValue().trim());
		
		sNode   = node_map.getNamedItem("remainflag");					
		gyNegativeMoney.gycard[2] = CommBase.strtoi(sNode.getNodeValue().trim());			
		
		//bd	
		NodeList gylocbds = gymoney.getElementsByTagName("gylocbd");
		Node gylocbd = (Element)gylocbds.item(0);	

		node_map = gylocbd.getAttributes();
			
		sNode   = node_map.getNamedItem("payflag");			
		gyNegativeMoney.gylocbd[0] = CommBase.strtoi(sNode.getNodeValue().trim());

		sNode   = node_map.getNamedItem("totflag");
		gyNegativeMoney.gylocbd[1] = CommBase.strtoi(sNode.getNodeValue().trim());
		
		sNode   = node_map.getNamedItem("remainflag");					
		gyNegativeMoney.gylocbd[2] = CommBase.strtoi(sNode.getNodeValue().trim());			

		//money	
		NodeList gylocmnys = gymoney.getElementsByTagName("gylocmny");
		Node gylocmny = (Element)gylocmnys.item(0);	

		node_map = gylocmny.getAttributes();
			
		sNode   = node_map.getNamedItem("payflag");			
		gyNegativeMoney.gylocmny[0] = CommBase.strtoi(sNode.getNodeValue().trim());

		sNode   = node_map.getNamedItem("totflag");
		gyNegativeMoney.gylocmny[1] = CommBase.strtoi(sNode.getNodeValue().trim());
		
		sNode   = node_map.getNamedItem("remainflag");					
		gyNegativeMoney.gylocmny[2] = CommBase.strtoi(sNode.getNodeValue().trim());
		
		//gymain	
		NodeList gymains = gymoney.getElementsByTagName("gymain");
		Node gymain = (Element)gymains.item(0);	

		node_map = gymain.getAttributes();
			
		sNode   = node_map.getNamedItem("payflag");			
		gyNegativeMoney.gymain[0] = CommBase.strtoi(sNode.getNodeValue().trim());

		sNode   = node_map.getNamedItem("totflag");
		gyNegativeMoney.gymain[1] = CommBase.strtoi(sNode.getNodeValue().trim());
		
		sNode   = node_map.getNamedItem("remainflag");					
		gyNegativeMoney.gymain[2] = CommBase.strtoi(sNode.getNodeValue().trim());
	}
	
	
	public static void initDyNegativeMoney(Element root){
		dyNegativeMoney = new DyNegativeMoney();
		
		NodeList dymoneys = root.getElementsByTagName("dynegativemoney");
		Element  dymoney = (Element)dymoneys.item(0);

		//card
		NodeList dycards = dymoney.getElementsByTagName("dycard");
		Node dycard = (Element)dycards.item(0);	

		//卡式文件配置参数
		NamedNodeMap node_map = dycard.getAttributes();
			
		Node sNode   = node_map.getNamedItem("payflag");			
		dyNegativeMoney.dycard[0] = CommBase.strtoi(sNode.getNodeValue().trim());

		sNode   = node_map.getNamedItem("totflag");
		gyNegativeMoney.gycard[1] = CommBase.strtoi(sNode.getNodeValue().trim());
		
		sNode   = node_map.getNamedItem("remainflag");					
		gyNegativeMoney.gycard[2] = CommBase.strtoi(sNode.getNodeValue().trim());			
		
		//money	
		NodeList dylocmnys = dymoney.getElementsByTagName("dylocmny");
		Node dylocmny = (Element)dylocmnys.item(0);	

		node_map = dylocmny.getAttributes();
			
		sNode   = node_map.getNamedItem("payflag");			
		dyNegativeMoney.dylocmny[0] = CommBase.strtoi(sNode.getNodeValue().trim());

		sNode   = node_map.getNamedItem("totflag");
		dyNegativeMoney.dylocmny[1] = CommBase.strtoi(sNode.getNodeValue().trim());
		
		sNode   = node_map.getNamedItem("remainflag");					
		dyNegativeMoney.dylocmny[2] = CommBase.strtoi(sNode.getNodeValue().trim());

		//dymain	
		NodeList dymains = dymoney.getElementsByTagName("dymain");
		Node dymain = (Element)dymains.item(0);	

		node_map = dymain.getAttributes();
			
		sNode   = node_map.getNamedItem("payflag");			
		dyNegativeMoney.dymain[0] = CommBase.strtoi(sNode.getNodeValue().trim());

		sNode   = node_map.getNamedItem("totflag");
		dyNegativeMoney.dymain[1] = CommBase.strtoi(sNode.getNodeValue().trim());
		
		sNode   = node_map.getNamedItem("remainflag");					
		dyNegativeMoney.dymain[2] = CommBase.strtoi(sNode.getNodeValue().trim());
	}
	
	public static void initNpNegativeMoney(Element root){
		npNegativeMoney = new NpNegativeMoney();
		
		NodeList npmoneys = root.getElementsByTagName("npnegativemoney");
		Element  npmoney = (Element)npmoneys.item(0);

		//card
		NodeList npcards = npmoney.getElementsByTagName("npcard");
		Node npcard = (Element)npcards.item(0);	

		//卡式文件配置参数
		NamedNodeMap node_map = npcard.getAttributes();
			
		Node sNode   = node_map.getNamedItem("payflag");			
		npNegativeMoney.npcard[0] = CommBase.strtoi(sNode.getNodeValue().trim());

		sNode   = node_map.getNamedItem("totflag");
		npNegativeMoney.npcard[1] = CommBase.strtoi(sNode.getNodeValue().trim());
		
		sNode   = node_map.getNamedItem("remainflag");					
		npNegativeMoney.npcard[2] = CommBase.strtoi(sNode.getNodeValue().trim());			
	}
	
	public static void initNpMoneyLimit(Element root)	{
		if(root == null){
			npMoneyLimit = 2000;
		}
		NodeList ra = root.getElementsByTagName("np_monylimit");
		Node station = ra.item(0);
		NamedNodeMap node_map = station.getAttributes();
		Node stationNode   = node_map.getNamedItem("value");
		String str_val = stationNode.getNodeValue();
		
		npMoneyLimit = CommBase.strtof(str_val);
		if (npMoneyLimit < 50) npMoneyLimit = 50;
		else if (npMoneyLimit > 10000000)	npMoneyLimit = 10000000;
	}
	
	public static String getProvinceMisFlag(Element root,String tagName){
		String tmp_str = "HB";
		
		if(root == null){
			return tmp_str;
		}
		NodeList ra = root.getElementsByTagName(tagName);
		Node station = ra.item(0);
		NamedNodeMap node_map = station.getAttributes();
		Node stationNode   = node_map.getNamedItem("value");
		tmp_str = stationNode.getNodeValue();
		return tmp_str;
	}
	
	public static int getReDoMaxNumNP(Element root,String tagName){
		String tmp_str = "1";
		
		if(root == null){
			return 1;
		}
		NodeList ra = root.getElementsByTagName(tagName);
		Node station = ra.item(0);
		NamedNodeMap node_map = station.getAttributes();
		Node stationNode   = node_map.getNamedItem("value");
		tmp_str = stationNode.getNodeValue();
		int maxNum = Integer.parseInt(tmp_str);
		
		if(maxNum > 3){
			maxNum = 3;
		}
		if(maxNum < 1){
			maxNum = 1;
		}
		return maxNum;
	}
	

	//读取查询、报表、费控档案页面是否自动加载参数配置
	public static void getAutoShowFlag(Element root){
		NodeList ra = root.getElementsByTagName("data_autoview");
		Element node = (Element)ra.item(0);
		NodeList itemList =  node.getElementsByTagName("item");
		
		int item_length = itemList.getLength();
		//flag中个元素分别对应:autoshow,grade_1,grade_2,grade_3,grade_4,grade_5
		for(int i = 0; i < item_length; i++){
			String flag = "[";
			
			//获取item节点
			Node item = itemList.item(i);
			NamedNodeMap node_map = item.getAttributes();
			
			//获取item节点下的所有属性
			Node type = node_map.getNamedItem("type");
			String type_desc = type.getNodeValue();		//对应autoShow中的key：低压->dy_show、专变->spec_show、农排->np_show
			
			Node autoshow = node_map.getNamedItem("autoshow");
			flag += autoshow.getNodeValue() + ",";
			
			//获取paydoc节点下所有属性值
			node = (Element)itemList.item(i);
			NodeList doc_node = node.getElementsByTagName("paydoc");
			Node doc_flag = (Element)doc_node.item(0);
			NamedNodeMap doc_map = doc_flag.getAttributes();
			
			Node grade_1 = doc_map.getNamedItem("grade_1");
			flag += grade_1.getNodeValue() + ",";
			
			Node grade_2 = doc_map.getNamedItem("grade_2");
			flag += grade_2.getNodeValue() + ",";
			
			Node grade_3 = doc_map.getNamedItem("grade_3");
			flag += grade_3.getNodeValue() + ",";
			
			Node grade_4 = doc_map.getNamedItem("grade_4");
			flag += grade_4.getNodeValue() + ",";
			
			Node grade_5 = doc_map.getNamedItem("grade_5");
			flag += grade_5.getNodeValue() + "]";
			
			autoShow.put(type_desc, flag);
		}	
	}
	//qjl add 20150313复费率峰时段时间start
	public static boolean getPeakTime() {
		Element root = getRoot();
		NodeList nl_webservice_addr = root.getElementsByTagName("peaktime");
		JSONObject jsonPeaktime = new JSONObject();
		
		if (nl_webservice_addr == null || nl_webservice_addr.getLength() <= 0) return false;
		
		Node node = nl_webservice_addr.item(0);
		if (node == null) return false;
		
		NamedNodeMap node_map = node.getAttributes();
		Node flagNode = node_map.getNamedItem("time1");
		String time1 = flagNode.getNodeValue();
		flagNode = node_map.getNamedItem("time2");
		String time2 = flagNode.getNodeValue();
		if((time1==null) || (time2==null)) return false;
		jsonPeaktime.put("time1", time1);
		jsonPeaktime.put("time2", time2);
		peak_time = jsonPeaktime.toString();
		return true;
	}
	//qjl add 20150313复费率峰时段时间end	
	
	//初始化通道信息
	public static boolean getEncryptModuleParam(Element root) {
		
		NodeList encryptModule = root.getElementsByTagName("EncryptModule");
		
		if (encryptModule == null || encryptModule.getLength() <= 0) return false;
		
		Node node = encryptModule.item(0);
		if (node == null) return false;
		
		NamedNodeMap node_map = node.getAttributes();
		
		Node nodeItem = node_map.getNamedItem("commport");
		String commport = nodeItem.getNodeValue();
		
		nodeItem = node_map.getNamedItem("useflag");
		String useflag = nodeItem.getNodeValue();
		
		nodeItem = node_map.getNamedItem("baudrate");
		String baudrate = nodeItem.getNodeValue();
		
		nodeItem = node_map.getNamedItem("checkflag");
		String checkflag = nodeItem.getNodeValue();
		
		nodeItem = node_map.getNamedItem("databit");
		String databit = nodeItem.getNodeValue();

		nodeItem = node_map.getNamedItem("stopbit");
		String stopbit = nodeItem.getNodeValue();
		
		encryptModuleParam.commport 	= commport;
		encryptModuleParam.useflag  	= useflag;
		encryptModuleParam.baudrate 	= baudrate;
		encryptModuleParam.checkflag 	= checkflag;
		encryptModuleParam.databit 		= databit;
		encryptModuleParam.stopbit 		= stopbit;
		
		return true;
	}
	
	public static int getStsBaseDate(Element root,String tagName){
		String tmp_str = null;
		
		if(root == null){
			return 1;
		}
		NodeList ra = root.getElementsByTagName(tagName);
		Node station = ra.item(0);
		NamedNodeMap node_map = station.getAttributes();
		Node stationNode   = node_map.getNamedItem("value");
		tmp_str = stationNode.getNodeValue();
		int value = Integer.parseInt(tmp_str);
		
		return value;
	}
	
	public static boolean createDataBaseTable(){
		
		String real_basepath1 = new File(new File(WebConfig.class.getResource("/").getPath()).getParent()).getParent();
		
		try {
			//获取sql脚本信息
			real_basepath1 = URLDecoder.decode(real_basepath1, "UTF-8");
			StringBuffer sb 			= 	new StringBuffer();
			StringBuffer sbTable		=	new StringBuffer();
			JSONArray	 tableArray 	=	new JSONArray();
			JSONArray	 tableNameArray =	new JSONArray();
			try {
				String file_path = real_basepath1 + "\\dbscripts\\sql\\install\\yd_datatable.sql";
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
	
}
