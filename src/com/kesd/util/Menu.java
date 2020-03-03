package com.kesd.util;

import java.util.ArrayList;
import java.util.List;

import com.kesd.common.SDDef;
import com.kesd.common.WebConfig;
import com.kesd.dbpara.YffManDef;

public class Menu {
	
	public static final int allflag  = -1;
	public static final int openflag = 0;		//开户权限
	public static final int payflag  = 1;		//购电权限
	public static final int paraflag = 2;		//修改权限
	public static final int ctrlflag = 3;		//控制权限
	public static final int viewflag = 4;		//报表权限
	
	private static byte 	have = 1;
	private static String 	picpath = "images/index/";//菜单按钮图片路径
	

	public static class LeftMenu{
		String 		id;				//<td id=……	//id:["localcard","ycje","ycbd","zzfk","query","report","tool","print"],
		String 		desc;			//描述
		String		picsrc;			//标题图片
		boolean 	show = false;	//在界面上是否显示本菜单//web-cfg.xml里配置
		
		public LeftMenu( String id, String desc,String picsrc, boolean show){
			this.id 	= id;
			this.picsrc	= picsrc;
			this.desc 	= desc;
			this.show 	= show;
		}
		
	}
	public static class RightMenu{
		String 		desc;			//描述
		String 		text;			//功能简介
		String 		picsrc;			//图片文件名
		String 		url;			//链接
		int 		type = allflag;	//权限类型：-1所有 0开户权限1购电权限2修改权限3控制权限4报表权限
		boolean 	show = false;	//是否显示详细信息
		
		public RightMenu(String desc, String text, String picsrc, String url, int type, boolean show){
			this.desc = desc;
			this.url = url;
			this.text = text;
			this.picsrc = picsrc;
			this.type = type;
			this.show = show;
		}
	}
	
	
	public static List<LeftMenu> dy_leftMenu 	= new ArrayList<LeftMenu>();
	public static List<LeftMenu> gy_leftMenu 	= new ArrayList<LeftMenu>();
	public static List<LeftMenu> np_leftMenu 	= new ArrayList<LeftMenu>();
	public static List<LeftMenu> docs_leftMenu 	= new ArrayList<LeftMenu>();
	public static List<LeftMenu> other_leftMenu = new ArrayList<LeftMenu>();
	
	
	public static List<RightMenu> dy_localcard 		= new ArrayList<RightMenu>();
	public static List<RightMenu> dy_localcardExt 	= new ArrayList<RightMenu>();
	public static List<RightMenu> dy_main 			= new ArrayList<RightMenu>();
	public static List<RightMenu> dy_localremote 	= new ArrayList<RightMenu>();
	public static List<RightMenu> dy_search 		= new ArrayList<RightMenu>();
	public static List<RightMenu> dy_report 		= new ArrayList<RightMenu>();
	public static List<RightMenu> dy_tool 			= new ArrayList<RightMenu>();
	public static List<RightMenu> dy_print 			= new ArrayList<RightMenu>();

	public static List<RightMenu> gy_localcard 	= new ArrayList<RightMenu>();
	public static List<RightMenu> gy_localbd 	= new ArrayList<RightMenu>();
	public static List<RightMenu> gy_localmoney = new ArrayList<RightMenu>();
	public static List<RightMenu> gy_main 		= new ArrayList<RightMenu>();
	public static List<RightMenu> gy_search 	= new ArrayList<RightMenu>();
	public static List<RightMenu> gy_report 	= new ArrayList<RightMenu>();
	public static List<RightMenu> gy_tool 		= new ArrayList<RightMenu>();
	public static List<RightMenu> gy_print 		= new ArrayList<RightMenu>();
	
	
	public static List<RightMenu> np_localcard 	= new ArrayList<RightMenu>();
	public static List<RightMenu> np_search 	= new ArrayList<RightMenu>();
	public static List<RightMenu> np_report 	= new ArrayList<RightMenu>();
	public static List<RightMenu> np_tool 		= new ArrayList<RightMenu>();
	public static List<RightMenu> np_print 		= new ArrayList<RightMenu>();
	
	public static List<RightMenu> docs_pb 		= new ArrayList<RightMenu>();
	public static List<RightMenu> docs_dy 		= new ArrayList<RightMenu>();
	public static List<RightMenu> docs_gy 		= new ArrayList<RightMenu>();
	public static List<RightMenu> docs_np 		= new ArrayList<RightMenu>();
	
	public static List<RightMenu> other_ctrl   = new ArrayList<RightMenu>();
	public static List<RightMenu> other_down   = new ArrayList<RightMenu>();
	public static List<RightMenu> other_extend = new ArrayList<RightMenu>();
	
	
	public static void initLeftMenu(){
		dy_leftMenu.add(new LeftMenu("localcard", "Contrôle local des coûts", "menu_kb.gif", false));
		dy_leftMenu.add(new LeftMenu("localremote", "本地费控(远程)", "menu_bd.gif", false));
		dy_leftMenu.add(new LeftMenu("main", "主站费控", "menu_zz.gif", false));
		dy_leftMenu.add(new LeftMenu("localcardExt", "本地费控(外接卡式)", "menu_kb.gif", false));
		dy_leftMenu.add(new LeftMenu("query", "Enquête", "menu_query.gif", false));
		dy_leftMenu.add(new LeftMenu("report", "Rapports", "menu_report.gif", false));
		dy_leftMenu.add(new LeftMenu("tool", "Outil", "menu_tool.gif", false));
		dy_leftMenu.add(new LeftMenu("print", "Impression", "menu_print.gif", false));
		
//		gy_leftMenu.add(new LeftMenu("localcard",		"本地费控(卡式)", 			"menu_kb.gif",		false));
//		gy_leftMenu.add(new LeftMenu("localbd",			"本地费控(远程表底)",		"menu_bd.gif", 		false));
//		gy_leftMenu.add(new LeftMenu("localmoney",		"本地费控(远程金额)",		"menu_je.gif", 		false));
//		gy_leftMenu.add(new LeftMenu("main",			"主站费控",				"menu_zz.gif", 		false));
//		gy_leftMenu.add(new LeftMenu("query",  			"查询", 					"menu_query.gif",	false));
//		gy_leftMenu.add(new LeftMenu("report", 			"报表", 					"menu_report.gif",	false));
//		gy_leftMenu.add(new LeftMenu("tool",   			"工具", 					"menu_tool.gif",	false));
//		gy_leftMenu.add(new LeftMenu("print",  			"打印", 					"menu_print.gif",	false));
//		
//		np_leftMenu.add(new LeftMenu("localcard",		"本地费控(卡式)", 			"menu_kb.gif",		false));
//		np_leftMenu.add(new LeftMenu("query",  			"查询", 					"menu_query.gif",	false));
//		np_leftMenu.add(new LeftMenu("report", 			"报表", 					"menu_report.gif",	false));
//		np_leftMenu.add(new LeftMenu("tool",   			"工具", 					"menu_tool.gif",	false));
//		np_leftMenu.add(new LeftMenu("print",  			"打印", 					"menu_print.gif",	false));
//		
		docs_leftMenu.add(new LeftMenu("public",		"Public Archives", 				"menu_ggda.gif",	false));
		docs_leftMenu.add(new LeftMenu("dyfk",  		"Low Voltage Cost Control Archives", 			"menu_dyda.gif",	false));
		docs_leftMenu.add(new LeftMenu("log",  			"Requête du journal des opérations", 			"menu_npda.gif",	false));

//		docs_leftMenu.add(new LeftMenu("npfk",  		"Arrigation Cost Control Archives", 			"menu_npda.gif",	false));

		
//		other_leftMenu.add(new LeftMenu("ctrl", 		"System Control", 				"menu_ctrl.gif",	false));
		other_leftMenu.add(new LeftMenu("down", 		"Zone de téléchargement",	 			"menu_download.gif",false));
		other_leftMenu.add(new LeftMenu("extend", 		"Fonction étendue",	 			"menu_gnkz.gif",    false));
		
	}
	
	public static void initDy(){//低压暂时没使用。下面是正确的低压菜单。不要删除。

		dy_localcard.add(new RightMenu("Purchase Electricity",		"Achat d'électricité: recharge du consommateur après l'ouverture d'un compte.",		    			"op_jiaofei.gif",		"jsp/dyjc/localcard/dypay.jsp",			payflag,  false));
		dy_localcard.add(new RightMenu("Open an Account",			"Ouvrir un compte: le consommateur achète d'abord de l'électricité après l'installation du compteur.",						"op_kaihu.gif",			"jsp/dyjc/localcard/dyaddcus.jsp",		openflag, false));
		dy_localcard.add(new RightMenu("Account Cancellation",		"Annulation de compte: le consommateur annule son compte avant de se déconnecter / annuler / retirer le compteur.",		    			"op_xiaohu.gif",		"jsp/dyjc/localcard/dydestroycus.jsp",	openflag, false));
		
		dy_search.add(new RightMenu("Query all operation record information",					"Recherchez toutes les informations d'enregistrement d'opération",					"op_zhcx_sts.gif",			"jsp/dyjc/query/search.jsp",		viewflag, false));
		dy_search.add(new RightMenu("Query customer information",								"Demander des informations client",							"op_yhxx_sts.gif",			"jsp/dyjc/query/yhxx.jsp",			viewflag, false));
		dy_search.add(new RightMenu("Query of Parameter and status information of prepayment",	"Requête d'informations sur les paramètres et l'état du prépaiement",				"op_yhzt_sts.gif",			"jsp/dyjc/query/yhzt.jsp",			viewflag, false));

		dy_report.add(new RightMenu("Report of Purchase electricity details",		  "Rapport d'achat Détails sur l'électricité",			"op_gdmx.gif",			"jsp/dyjc/report/gdmxb.jsp",			viewflag, false));
		dy_report.add(new RightMenu("Report of time accumulated purchase information","Rapport des informations d'achat cumulées","op_yhgd.gif",			"jsp/dyjc/report/yhgdb.jsp",			viewflag, false));
		dy_report.add(new RightMenu("Report of Purchase summary",					  "Résumé du rapport d'achat",						"op_gdhz.gif",			"jsp/dyjc/report/gdhzb.jsp",			viewflag, false));
		dy_report.add(new RightMenu("Report of salesperson sales information",  	  "Rapport d'informations sur les ventes du vendeur",		"op_fxdf.gif",			"jsp/dyjc/report/opmansalsum.jsp",		viewflag, false));  // 操作员售电汇总表
		dy_report.add(new RightMenu("Report of sales information of area",  		  "Rapport des informations de vente de la région",	    	"op_kzxx.gif",			"jsp/dyjc/report/rtucoltmoney.jsp",		viewflag, false));  // 台区售电汇总表
		dy_tool.add(new RightMenu("STS Tool","cet outil est utilisé pour tester le jeton STS","op_makecard.gif", 	"jsp/dyjc/tool/makermcard.jsp",		ctrlflag, false));

		dy_print.add(new RightMenu("Print Tickets","Imprimer les tickets de vente",	"op_bdfp.gif",	"jsp/dyjc/print/bdfp.jsp", payflag, false));
	}
	
	//****************高压--开始***************
	
	//初始化高压操作菜单（右边菜单）。
	public static void initGy(){
		
		gy_localcard.add(new RightMenu("缴费",			"正常充值业务，用户缴费操作。",						"op_jiaofei.gif",	"jsp/spec/localcard/gypay.jsp",				payflag,  false));   //缴费",
		gy_localcard.add(new RightMenu("冲正",			"对系统缴费金额与实际缴费金额不一致的问题进行改正。",	"op_chongzheng.gif","jsp/spec/localcard/gyrever.jsp",			payflag,  false));   //冲正",
		gy_localcard.add(new RightMenu("补写缴费记录",	"用户缴费后存数据库失败，补写缴费记录。",				"op_bxjl.gif",		"jsp/spec/localcard/gypayadd.jsp",			payflag,  false)); 	 //补写缴费记录
		gy_localcard.add(new RightMenu("换表",			"更换电表继续用电的操作。",							"op_huanbiao.gif",	"jsp/spec/localcard/gychgmeter.jsp",		payflag,  false));   //换表",
		gy_localcard.add(new RightMenu("补卡",			"为用户补办新卡，继续购电。",							"op_buka.gif",		"jsp/spec/localcard/gyrepair.jsp",			payflag,  false));   //补卡",
		gy_localcard.add(new RightMenu("清空卡",			"清空购电卡内信息。",				    				"op_qingkongka.gif","jsp/spec/localcard/gyclear.jsp",			openflag, false));   //清空卡",
		gy_localcard.add(new RightMenu("更改费率",		"更改用户的电价、基本费。",							"op_ggfl.gif",		"jsp/spec/localcard/gychgrate.jsp",			paraflag, false));   //更改费率
		//20131130新增一项
		gy_localcard.add(new RightMenu("写卡更改费率",	"写卡更换费率、切换时间，缴费(2013版表)。",        	"op_ggfl.gif",			"jsp/spec/localcard/gypaychgfee.jsp",			payflag,  false));
	    //end
		gy_localcard.add(new RightMenu("开户",			"本地卡式费控，初始化用户信息。",						"op_kaihu.gif",		"jsp/spec/localcard/gyaddcus.jsp",			openflag, false));   //开户",
		gy_localcard.add(new RightMenu("销户",			"电力用户用电结束，不再用电。",						"op_xiaohu.gif",	"jsp/spec/localcard/gydestroycus.jsp",		openflag, false));   //销户",
		gy_localcard.add(new RightMenu("保电",			"保电、取消保电、分合闸操作。",						"op_baodian.gif",	"jsp/spec/localcard/gyrmtctrl.jsp",			ctrlflag, true));    //保电",

		gy_localmoney.add(new RightMenu("缴费",			"正常充值业务，用户缴费操作。",						"op_jiaofei.gif",	"jsp/spec/localmoney/gypay.jsp",			payflag,  true));    //缴费",
		gy_localmoney.add(new RightMenu("冲正",			"对系统缴费金额与实际缴费金额不一致的问题进行改正。",	"op_chongzheng.gif","jsp/spec/localmoney/gyrever.jsp",			payflag,  true));    //冲正",
		gy_localmoney.add(new RightMenu("补写记录",		"远程开户缴费，存数据库失败，补写缴费记录。",			"op_bxjl.gif",		"jsp/spec/localmoney/gypayadd.jsp",			payflag,  false)); 	 //补写缴费记录
		gy_localmoney.add(new RightMenu("换表",			"更换电表继续用电的操作。",							"op_huanbiao.gif",	"jsp/spec/localmoney/gychgmeter.jsp",		payflag,  true));    //换表",
		gy_localmoney.add(new RightMenu("更改费率",		"更改用户的电价、基本费。",							"op_ggfl.gif",		"jsp/spec/localmoney/gychgrate.jsp",		paraflag, true));    //更改费率
		gy_localmoney.add(new RightMenu("开户",			"远程金额费控，初始化并启用费控功能。",				"op_kaihu.gif",		"jsp/spec/localmoney/gyaddcus.jsp",			openflag, true));    //开户",
		gy_localmoney.add(new RightMenu("销户",			"电力用户用电结束，不再用电。",						"op_xiaohu.gif",	"jsp/spec/localmoney/gydestroycus.jsp",		openflag, false));   //销户",
		gy_localmoney.add(new RightMenu("保电",			"保电、取消保电、分合闸操作。",						"op_baodian.gif",	"jsp/spec/tool/gyrmtctrl.jsp?type=je",		ctrlflag, true));    //保电",
		gy_localmoney.add(new RightMenu("远程读售电信息",	"远程读取费控终端内的用电信息。",				    "op_ycdsdxx.gif",	"jsp/spec/tool/ycdsd.jsp?type=je",		    payflag,  true));    //远程读表
		
		gy_localbd.add(new RightMenu("缴费",				"正常充值业务，用户缴费操作。",						"op_jiaofei.gif",	"jsp/spec/localbd/gypay.jsp",				payflag,  true));    //缴费",	
		gy_localbd.add(new RightMenu("冲正",				"对系统缴费金额与实际缴费金额不一致的问题进行改正。",	"op_chongzheng.gif","jsp/spec/localbd/gyrever.jsp",				payflag,  true));    //冲正",	
		gy_localbd.add(new RightMenu("补写记录",			"远程开户缴费，存数据库失败，补写缴费记录。",			"op_bxjl.gif",		"jsp/spec/localbd/gypayadd.jsp",			payflag,  true));    //补写缴费记录
		gy_localbd.add(new RightMenu("换表",				"更换电表继续用电的操作。",							"op_huanbiao.gif",	"jsp/spec/localbd/gychgmeter.jsp",			payflag,  true));    //换表",	
		gy_localbd.add(new RightMenu("更改费率",			"更改用户的电价、基本费。",							"op_ggfl.gif",		"jsp/spec/localbd/gychgrate.jsp",			paraflag, true));    //更改费率",
		gy_localbd.add(new RightMenu("开户",				"远程表底费控，初始化并启用费控功能。",				"op_kaihu.gif",		"jsp/spec/localbd/gyaddcus.jsp",			openflag, true));   //开户",	
		gy_localbd.add(new RightMenu("销户",				"电力用户用电结束，不再用电。",						"op_xiaohu.gif",	"jsp/spec/localbd/gydestroycus.jsp",		openflag, true));   //销户",	
		gy_localbd.add(new RightMenu("保电",				"保电、取消保电、分合闸操作。",						"op_baodian.gif",	"jsp/spec/tool/gyrmtctrl.jsp?type=bd",		ctrlflag, true));    //保电",
		gy_localbd.add(new RightMenu("远程读售电信息",	"远程读取费控终端内的用电信息。",				        "op_ycdsdxx.gif",	"jsp/spec/tool/ycdsd.jsp?type=bd",		    payflag,  true));    //远程读表

		gy_main.add(new RightMenu("缴费",				"正常充值业务，用户缴费操作。",						"op_jiaofei.gif",	"jsp/spec/main/gypay.jsp",					payflag,  false));   //缴费",		
		gy_main.add(new RightMenu("冲正",				"对系统缴费金额与实际缴费金额不一致的问题进行改正。",	"op_chongzheng.gif","jsp/spec/main/gyrever.jsp",				payflag,  false));   //冲正",		
		gy_main.add(new RightMenu("换表",				"更换电表继续用电的操作。",							"op_huanbiao.gif",	"jsp/spec/main/gychgmeter.jsp",				payflag,  false));   //换表",		
		gy_main.add(new RightMenu("更改费率",				"更改用户的电价、基本费。",						"op_ggfl.gif",		"jsp/spec/main/gychgrate.jsp",				paraflag, false));   //更改费率",
		
		gy_main.add(new RightMenu("开户",				"主站费控，初始化并启用费控功能。",					"op_kaihu.gif",		"jsp/spec/main/gyaddcus.jsp",				openflag, false));   //开户",		
		gy_main.add(new RightMenu("销户",				"电力用户用电结束，不再用电。",						"op_xiaohu.gif",	"jsp/spec/main/gydestroycus.jsp",			openflag, false));   //销户",
		gy_main.add(new RightMenu("保电",				"保电、取消保电、分合闸操作。",						"op_baodian.gif",	"jsp/spec/tool/gyrmtctrl.jsp?type=zz",		ctrlflag, true));    //保电",
		gy_main.add(new RightMenu("暂停",				"修改重要参数，暂停控制。",							"op_zanting.gif",	"jsp/spec/main/gypause.jsp",				openflag, false));   //暂停",		
		gy_main.add(new RightMenu("恢复",				"重新开始计算控制。",									"op_huifu.gif",		"jsp/spec/main/gyrestore.jsp",				openflag, false));   //恢复",		
		
		gy_search.add(new RightMenu("综合查询",		"查询用户的所有操作记录信息。",			"op_zhcx.gif",		"jsp/spec/query/search.jsp",		viewflag, false));  //综合查询",	
		gy_search.add(new RightMenu("用户信息查询",	"查询用户的售电档案信息",					"op_yhxx.gif",		"jsp/spec/query/yhxx.jsp",			viewflag, false));  //用户信息查询"
		gy_search.add(new RightMenu("用户状态查询",	"查询用户预付费参数及其状态信息。",		"op_yhzt.gif",		"jsp/spec/query/yhzt.jsp",			viewflag, false));  //用户状态查询"
		gy_search.add(new RightMenu("用户余额查询",	"查询用户结算日、表底及余额信息。",		"op_yhye.gif",		"jsp/spec/query/yhye.jsp",			viewflag, false));  //用户余额查询"
		gy_search.add(new RightMenu("控制信息查询",	"查询用户缴费合闸、欠费跳闸记录信息。",	"op_kzxx.gif",		"jsp/spec/query/kzxx.jsp",			viewflag, false));  //控制信息查询"
		gy_search.add(new RightMenu("结算信息查询",	"查询用户结算补差操作信息。",				"op_jscx.gif",		"jsp/spec/query/jscx.jsp",			viewflag, false));  //结算信息查询"
		
		gy_report.add(new RightMenu("购电明细表",	"显示每笔购电记录的详细信息",				"op_gdmx.gif",		"jsp/spec/report/gdmxb.jsp",		viewflag, false));  // 购电明细表
		gy_report.add(new RightMenu("用户购电表",	"显示区域时间段内用户的累计购电信息",		"op_yhgd.gif",		"jsp/spec/report/yhgdb.jsp",		viewflag, false));  // 用户购电表
		gy_report.add(new RightMenu("购电汇总表",	"显示每天各个供电所的购电累计信息",		"op_gdhz.gif",		"jsp/spec/report/gdhzb.jsp",		viewflag, false));  // 购电汇总表
		gy_report.add(new RightMenu("操作员售电汇总表","显示区域时间段内的操作员售电信息",		"op_gdhz.gif",		"jsp/spec/report/opmansalsum.jsp",	viewflag, false));  // 操作员售电汇总表
		gy_report.add(new RightMenu("发行电费表",	"显示各个用户发行电费后的详细信息",		"op_fxdf.gif",		"jsp/spec/report/fxdfb.jsp",		viewflag, false));  // 购电汇总表
		
		gy_tool.add(new RightMenu("特殊修正",	    "解决售电系统中报警失败不能进行控制的问题",	"op_tsxz.gif",	    "jsp/spec/tool/tsxz.jsp",		payflag, true));	//特殊修正
		gy_tool.add(new RightMenu("手工上传",   	 	"系统自动上传失败，采用手工方式上传缴费记录", 	"op_dzcx.gif",	    "jsp/spec/tool/checkmis.jsp",	payflag, false));
		gy_tool.add(new RightMenu("购电卡信息",	 	"购买电卡的信息",							"op_gdkxx.gif",	    "jsp/spec/tool/gdkxx.jsp",	    payflag, true));   //电卡信息
				
		if (WebConfig.gyMisJsConfig.hb_sg186 != null) {
			gy_tool.add(new RightMenu("结算补差",	"发行电费后，与算费系统结算补差操作。",			"op_jsbc.gif",		"jsp/spec/tool/gyjs.jsp",		payflag, false));  //结算补差",
			gy_tool.add(new RightMenu("批量结算",	"发行电费后，与算费系统结算补差操作，批量处理。",	"op_pljsbc.gif",		"jsp/spec/tool/gyjs_pl.jsp",	payflag, false));  //批量结算",
		}
		else {
			gy_tool.add(new RightMenu("结算补差",	"发行电费后，与算费系统结算补差操作。",			"op_jsbc.gif",		"jsp/spec/tool/gyjs1.jsp",		payflag, false));  //结算补差",	
			gy_tool.add(new RightMenu("批量结算",	"发行电费后，与算费系统结算补差操作，批量处理。",	"op_pljsbc.gif",		"jsp/spec/tool/gyjs_pl1.jsp",		payflag, false));  //批量结算",	
		}
		gy_tool.add(new RightMenu("手工合闸",	 	"376规约的终端进行手工控制合闸",					"op_sghz.gif",	    "jsp/spec/tool/gysghz376.jsp",	    ctrlflag, true));   //手工合闸
		
		gy_print.add(new RightMenu("补打发票",		"补打购电票据。",						"op_bdfp.gif",		"jsp/spec/print/bdfp.jsp",		payflag, false));//补打发票
		
	}
	
	//低压-生成左侧菜单-menu-dysd.jsp的代码
	public static String getDy_menu(YffManDef yffman){
		
		if(yffman == null) return "";
		
		StringBuffer ret = new StringBuffer();
		String left_ids = "", ids = "";
		for (int i = 0; i < dy_leftMenu.size(); i++) {
			
			boolean flag = WebConfig.menuShow.get("dy_leftmenu" + i);
			if(!flag) {
				continue;
			}
			
			left_ids += ",\"dy_leftmenu_" + i + "\"";
			ids += ",\"" + dy_leftMenu.get(i).id + "\"";
			
			LeftMenu lm = dy_leftMenu.get(i);
			
			ret.append("<tr id='dy_leftmenu_" + i + "' onmouseover='mover(this)' onmouseout='mout(this);' onclick='mclick(this)'>");
			ret.append("<td width=3></td>");
			ret.append("<td width=30><img src=\"" + picpath + lm.picsrc + "\" height=30px;/></td>");
			ret.append("<td class='lm_padleft'>" + lm.desc + "</td>");
			ret.append("<td width=3></td></tr>");
		}
		
		if(left_ids.length() > 0) left_ids = left_ids.substring(1);
		if(ids.length() > 0) ids = ids.substring(1);
		
		ret.append("<tr><td><script>var leftmenu = {left_id :[" + left_ids + "],id:[" + ids + "]};</script></td></tr>");
		
		return ret.toString();
	}
	
	//高压-生成左侧菜单-menu-gysd.jsp的代码
	public static String getGy_menu(YffManDef yffman){
		
		if(yffman == null) return "";
		
		StringBuffer ret = new StringBuffer();
		String left_ids = "", ids = "";
		for (int i = 0; i < gy_leftMenu.size(); i++) {
			
			boolean flag = WebConfig.menuShow.get("gy_leftmenu" + i);
			if(!flag) {	
				continue;
			}
			
			left_ids += ",\"gy_leftmenu_" + i + "\"";
			ids += ",\"" + gy_leftMenu.get(i).id + "\"";
			
			LeftMenu lm = gy_leftMenu.get(i);
			
			ret.append("<tr id='gy_leftmenu_" + i + "' onmouseover='mover(this)' onmouseout='mout(this);' onclick='mclick(this)'>");
			ret.append("<td width=3></td>");
			ret.append("<td width=30><img src=\"" + picpath + lm.picsrc + "\" height=30px;/></td>");
			ret.append("<td class='lm_padleft'>" + lm.desc + "</td>");
			ret.append("<td width=3></td></tr>");
		}
		
		if(left_ids.length() > 0) left_ids = left_ids.substring(1);
		if(ids.length() > 0) ids = ids.substring(1);
		
		ret.append("<tr><td><script>var leftmenu = {left_id :[" + left_ids + "],id:[" + ids + "]};</script></td></tr>");
		
		return ret.toString();
	}

	//高压-生成右侧操作菜单-本地费控-卡式
	public static String getGy_localcard(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("gy_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "专变售电" + SDDef.NAVIG_SPRT + "本地费控(卡式)";
		String menuId = "localcard";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < gy_localcard.size(); i++) {
			RightMenu rm = gy_localcard.get(i);
			int type = rm.type;
			if(type == allflag){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	
	//高压-生成右侧操作菜单-本地费控(远程表底)
	public static String getGy_localbd(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("gy_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "专变售电" + SDDef.NAVIG_SPRT + "本地费控(远程表底)";
		String menuId = "localbd";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < gy_localbd.size(); i++) {
			RightMenu rm = gy_localbd.get(i);
			int type = rm.type;
			if(type == allflag){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	
	//高压-生成右侧操作菜单-主站费控
	public static String getGy_main(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("gy_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "专变售电" + SDDef.NAVIG_SPRT + "主站费控";
		String menuId = "main";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < gy_main.size(); i++) {
			RightMenu rm = gy_main.get(i);
			int type = rm.type;
			if(type == allflag){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}	
	
	//高压-生成右侧操作菜单-本地费控(远程金额)
	public static String getGy_localmoney(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("gy_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "专变售电" + SDDef.NAVIG_SPRT + "本地费控(远程金额)";
		String menuId = "localmoney";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < gy_localmoney.size(); i++) {
			RightMenu rm = gy_localmoney.get(i);
			int type = rm.type;
			if(type == allflag){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	
	//高压-生成右侧操作菜单-查询
	public static String getGy_search(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("gy_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "专变售电" + SDDef.NAVIG_SPRT + "查询";
		String menuId = "query";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < gy_search.size(); i++) {
			RightMenu rm = gy_search.get(i);
			int type = rm.type;
			if(type == allflag){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	
	//高压-生成右侧操作菜单-报表
	public static String getGy_report(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("gy_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "专变售电" + SDDef.NAVIG_SPRT + "报表";
		String menuId = "report";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < gy_report.size(); i++) {
			RightMenu rm = gy_report.get(i);
			int type = rm.type;
			if(type == allflag){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	
	//高压-生成右侧操作菜单-工具
	public static String getGy_tool(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("gy_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "专变售电" + SDDef.NAVIG_SPRT + "工具";
		String menuId = "tool";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < gy_tool.size(); i++) {
			RightMenu rm = gy_tool.get(i);
			int type = rm.type;
			if(type == allflag){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	
	//高压-生成右侧操作菜单-打印
	public static String getGy_print(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("gy_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "专变售电" + SDDef.NAVIG_SPRT + "打印";
		String menuId = "print";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < gy_print.size(); i++) {
			RightMenu rm = gy_print.get(i);
			int type = rm.type;
			if(type == allflag){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	
	//**************高压--结束*****************
	
	//****************农排--开始***************
	
	//初始化农排操作菜单（右边菜单）。
	public static void initNp(){
		
		np_localcard.add(new RightMenu("缴费",			"正常充值业务，用户缴费操作。",						"op_jiaofei.gif",	"jsp/np/localcard/nppay.jsp",				payflag,  false));   //缴费",
		np_localcard.add(new RightMenu("冲正",			"对系统缴费金额与实际缴费金额不一致的问题进行改正。",	"op_chongzheng.gif","jsp/np/localcard/nprever.jsp",				payflag,  false));   //冲正",
		np_localcard.add(new RightMenu("补缴费记录",		"用户缴费后存数据库失败，补写缴费记录。",				"op_bxjfjl.gif",		"jsp/np/localcard/nppayadd.jsp",			payflag,  false)); 	 //补写缴费记录
		np_localcard.add(new RightMenu("补冲正记录",		"用户冲正后存数据库失败，补写冲正记录。",				"op_bxczjl.gif",		"jsp/np/localcard/npreveradd.jsp",			payflag,  false));   //补冲正记录
		np_localcard.add(new RightMenu("补卡",			"为用户补办新卡，继续购电。",							"op_buka.gif",		"jsp/np/localcard/nprepair.jsp",			payflag,  false));   //补卡",
		np_localcard.add(new RightMenu("清空卡",			"清空购电卡内信息。",				    				"op_qingkongka.gif","jsp/np/localcard/npclear.jsp",			openflag, false));   //清空卡",
		np_localcard.add(new RightMenu("开户",			"本地卡式费控，初始化用户信息。",						"op_kaihu.gif",		"jsp/np/localcard/npaddcus.jsp",			openflag, false));   //开户",
		np_localcard.add(new RightMenu("销户",			"电力用户用电结束，不再用电。",						"op_xiaohu.gif",	"jsp/np/localcard/npdestroycus.jsp",		openflag, false));   //销户",
		
		
		np_search.add(new RightMenu("综合查询",		"查询用户的所有操作记录信息。",			"op_zhcx.gif",		"jsp/np/query/search.jsp",		viewflag, false));  //综合查询",	
		np_search.add(new RightMenu("用户信息查询",	"查询用户的售电档案信息",					"op_yhxx.gif",		"jsp/np/query/yhxx.jsp",		viewflag, false));  //用户信息查询"
		np_search.add(new RightMenu("用户状态查询",	"查询用户预付费参数及其状态信息。",		"op_yhzt.gif",		"jsp/np/query/yhzt.jsp",		viewflag, false));  //用户状态查询"

		np_report.add(new RightMenu("购电明细表",	"显示每笔购电记录的详细信息",				"op_gdmx.gif",		"jsp/np/report/gdmxb.jsp",		viewflag, false));  // 购电明细表
		np_report.add(new RightMenu("用户购电表",	"显示区域时间段内用户的累计购电信息",		"op_yhgd.gif",		"jsp/np/report/yhgdb.jsp",		viewflag, false));  // 用户购电表
		np_report.add(new RightMenu("购电汇总表",	"显示每天各个供电所的购电累计信息",		"op_gdhz.gif",		"jsp/np/report/gdhzb.jsp",		viewflag, false));  // 购电汇总表
		np_report.add(new RightMenu("操作员售电汇总表","显示区域时间段内的操作员售电信息",		"op_gdhz.gif",		"jsp/np/report/opmansalsum.jsp",viewflag, false));  // 操作员售电汇总表
		//20130426 --添加  时间段片区售电汇总表
		np_report.add(new RightMenu("区域时间段片区售电汇总表","显示区域时间段内的片区购电累计信息",	"op_gdhz.gif",		"jsp/np/report/areasalsum.jsp",	viewflag, false));  // 时间段片区售电汇总表
		np_report.add(new RightMenu("用电信息查询",	"显示区域时间段内每笔用电记录的详细信息",	"op_khje.gif",		"jsp/np/report/khje.jsp",		viewflag, false));
		//qjl add 20150204 start --添加"按台区汇总电表的电量及售电金额并作出对比"
		np_report.add(new RightMenu("用电金额对比",	"显示区域时间段内电费差额",	"op_khje.gif",		"jsp/np/report/dfce.jsp",		viewflag, false));
		//qjl add 20150204 end
		//20140930--添加 时间段片区用电汇总  --河南清丰
		np_report.add(new RightMenu("用电汇总 ",	"显示区域时间段内用电汇总信息",	"op_ydhz.gif",		"jsp/np/report/ydhz.jsp",		viewflag, false));

		np_tool.add(new RightMenu("特殊修正",	    "解决售电系统中报警失败不能进行控制的问题",	"op_tsxz.gif",	    "jsp/np/tool/tsxz.jsp",		payflag, true));	//特殊修正
		//np_tool.add(new RightMenu("购电卡信息",	 	"购买电卡的信息",							"op_gdkxx.gif",	    "jsp/np/tool/gdkxx.jsp",	payflag, true));   //电卡信息
		np_tool.add(new RightMenu("电表记录",	 	"显示客户用电记录、挂起客户记录及刷卡故障记录","op_zdjl.gif",	    "jsp/np/tool/zdjl.jsp",	    viewflag, false));
		np_tool.add(new RightMenu("工具卡制作",	 	"工具卡制作",								"op_card.gif",	    "jsp/np/tool/make_card.jsp",payflag, false));
		np_tool.add(new RightMenu("灰锁解锁",	 	"灰锁解锁",									"op_hsjs.gif",	    "jsp/np/tool/key.jsp",		ctrlflag, false));
		np_tool.add(new RightMenu("远程操作",	 	"远程操作",									"op_yccz.gif",	    "jsp/np/tool/callset.jsp",	ctrlflag, false));
		np_tool.add(new RightMenu("批量导入电卡信息","批量导入农排电卡信息。",						"op_dkpl.gif",		"jsp/docs/yffparapl_npk.jsp",	paraflag, false));  //农排-电卡信息批量导入

		np_print.add(new RightMenu("补打发票",		"补打购电票据。",							"op_bdfp.gif",		"jsp/np/print/bdfp.jsp",	payflag, false));//补打发票
		
	}
	
	
	//农排-生成左侧菜单-menu-npsd.jsp的代码
	public static String getNp_menu(YffManDef yffman){
		
		if(yffman == null) return "";
		
		StringBuffer ret = new StringBuffer();
		String left_ids = "", ids = "";
		for (int i = 0; i < np_leftMenu.size(); i++) {
			
			boolean flag = WebConfig.menuShow.get("np_leftmenu" + i);
			if(!flag) {	
				continue;
			}
			
			left_ids += ",\"np_leftMenu_" + i + "\"";
			ids += ",\"" + np_leftMenu.get(i).id + "\"";
			
			LeftMenu lm = np_leftMenu.get(i);
			
			ret.append("<tr id='np_leftMenu_" + i + "' onmouseover='mover(this)' onmouseout='mout(this);' onclick='mclick(this)'>");
			ret.append("<td width=3></td>");
			ret.append("<td width=30><img src=\"" + picpath + lm.picsrc + "\" height=30px;/></td>");
			ret.append("<td class='lm_padleft'>" + lm.desc + "</td>");
			ret.append("<td width=3></td></tr>");
		}
		
		if(left_ids.length() > 0) left_ids = left_ids.substring(1);
		if(ids.length() > 0) ids = ids.substring(1);
		
		ret.append("<tr><td><script>var leftmenu = {left_id :[" + left_ids + "],id:[" + ids + "]};</script></td></tr>");
		
		return ret.toString();
	}

	//农排-生成右侧操作菜单-本地费控-卡式
	public static String getNp_localcard(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("np_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "农排售电" + SDDef.NAVIG_SPRT + "本地费控(卡式)";
		String menuId = "localcard";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < np_localcard.size(); i++) {
			RightMenu rm = np_localcard.get(i);
			int type = rm.type;
			if(type == allflag){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	
	
	//农排-生成右侧操作菜单-查询
	public static String getNp_search(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("np_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "农排售电" + SDDef.NAVIG_SPRT + "查询";
		String menuId = "query";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < np_search.size(); i++) {
			RightMenu rm = np_search.get(i);
			int type = rm.type;
			if(type == allflag){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	
	//农排-生成右侧操作菜单-报表
	public static String getNp_report(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("np_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "农排售电" + SDDef.NAVIG_SPRT + "报表";
		String menuId = "report";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		int realnum = 0, addflag = 0;
		for (int i = 0; i < np_report.size(); i++) {
			RightMenu rm = np_report.get(i);
			int type = rm.type;
			addflag = 0;
			if (realnum % 2 == 0) ret.append("<tr>");
			if(type == allflag){
				ret.append(getOptr2(rm, naviga, menuId, i));	addflag = 1;
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr2(rm, naviga, menuId, i));	addflag = 1;
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr2(rm, naviga, menuId, i));	addflag = 1;
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr2(rm, naviga, menuId, i));	addflag = 1;
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr2(rm, naviga, menuId, i));	addflag = 1;
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr2(rm, naviga, menuId, i));	addflag = 1;
			}
//			ret.append("\n");
			if (addflag == 1)	realnum++;
			if (realnum % 2 == 0) ret.append("</tr>");
		}
		if (realnum % 2 == 1){			//如果为单行 添加 补齐
			ret.append("<td></td></tr>");
		}
		ret.append("</table>");
		return ret.toString();
	}
	
	//农排-生成右侧操作菜单-工具
	public static String getNp_tool(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("np_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "农排售电" + SDDef.NAVIG_SPRT + "工具";
		String menuId = "tool";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < np_tool.size(); i++) {
			RightMenu rm = np_tool.get(i);
			int type = rm.type;
			if(type == allflag){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	
	//农排-生成右侧操作菜单-打印
	public static String getNp_print(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("np_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "农排售电" + SDDef.NAVIG_SPRT + "打印";
		String menuId = "print";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < np_print.size(); i++) {
			RightMenu rm = np_print.get(i);
			int type = rm.type;
			if(type == allflag){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	
	//**************农排--结束*****************
	
	
	
	
	//**************档案--开始*****************
	//初始化档案操作菜单（右边菜单）。
	public static void initDoc(){
		
		docs_pb.add(new RightMenu("Tariff Project",	"Gestion de projet tarifaire.",	"op_flfa.gif",	"jsp/docs/yffratepara.jsp",		paraflag, false));  //费率方案
		docs_pb.add(new RightMenu("STS Parameters",	"Gestion des paramètres STS.",	"op_stspara.gif",	"jsp/docs/stspara.jsp",		paraflag, false));  //费率方案
		docs_pb.add(new RightMenu("Prepaid personnel management",	"Archives du personnel d'exploitation prépayé。",	"op_yffry.gif",	"jsp/docs/yffmandef.jsp",		paraflag,  false));  //预付费人员
		docs_pb.add(new RightMenu("Operation Log Query",	"Requête du journal des opérations",	"op_fkcs.gif",	"jsp/docs/operlog.jsp",		paraflag, false));  //高压-费控参数
		docs_pb.add(new RightMenu("Import Meter Archive",	"Importer une archive de compteur",	"op_fkpl.gif",	"jsp/docs/importMeterArchive.jsp",		paraflag, false));  //excel导入电表
		docs_pb.add(new RightMenu("Import Resident Archive",	"Importer une archive résidente",	"op_fkpl.gif",	"jsp/docs/importResidentArchive.jsp",		paraflag, false));  //excel导入居民
	
//		docs_pb.add(new RightMenu("报警方案",	"报警方案基础档案。",						"op_bjfa.gif",	"jsp/docs/yffalarmpara.jsp",	paraflag, false));  //报警方案		
//		docs_pb.add(new RightMenu("预付费人员",	"预付费操作人员档案。",					"op_yffry.gif",	"jsp/docs/yffmandef.jsp",		paraflag,  false));  //预付费人员
//		docs_pb.add(new RightMenu("节假日保电",	"节假日保电基础档案。",					"op_jjrbd.gif",	"jsp/docs/gloprotect.jsp",		paraflag,  false));  //节假日保电
//		docs_pb.add(new RightMenu("力调方案",	"力调方案基础档案。",						"op_ltfa.gif",	"jsp/docs/cosstand.jsp",		paraflag,  false));  //力调电费
//		docs_pb.add(new RightMenu("节假日定义",	"节假日定义档案。",						"op_jjr.gif",	"jsp/docs/holidaydef.jsp",		paraflag,  false));  //节假日
//		docs_pb.add(new RightMenu("节假日组",	"节假日组档案。",							"op_jjrz.gif",	"jsp/docs/holidaygroup.jsp",		paraflag,  false));  //节假日
//		
		//20150305 zp for BENGAL
		docs_dy.add(new RightMenu("Residential Area Archive",	"Gestion des archives des zones résidentielles.",						"op_communityhov_obj.gif",	"jsp/docs/conspara.jsp",	paraflag, false));  		//低压-居民区档案
		docs_dy.add(new RightMenu("Terminal Archive",	"Gestion des archives du terminal.",						"op_terminal.gif",	"jsp/docs/rtuparaBase.jsp",	paraflag, false));  		//低压-终端档案
		docs_dy.add(new RightMenu("Resident Archive",	"Gestion des archives des résidents.",						"op_resident.gif",	"jsp/docs/rtuparaResidentpara.jsp",	paraflag, false));  //低压-居民档案
		docs_dy.add(new RightMenu("Residential Meter Archive",	"Gestion des paramètres de contrôle des coûts des compteurs résidentiels.",					"op_kabiao1.gif",	"jsp/docs/yffpara_dyjc.jsp",	paraflag, false));  //低压-费控参数
//		docs_dy.add(new RightMenu("费控批量",	"批量修改居民终端费控参数。",				"op_fkpl.gif",	"jsp/docs/yffparapl_dyjc.jsp",	paraflag, false));  //低压-费控 批量
//		docs_dy.add(new RightMenu("预付费时间",	"预付费启用时间、与SG186对账时间设置。",	"op_yffsj.gif",	"jsp/docs/yffsj.jsp?apptype=3",	paraflag,  false));  //低压-预付费时间
	
		

//		docs_gy.add(new RightMenu("费控批量",	"批量修改专变终端费控参数。",				"op_fkpl.gif",	"jsp/docs/yffparapl_spec.jsp",			paraflag, false));  //高压-费控批量
//		docs_gy.add(new RightMenu("预付费时间",	"预付费启用时间、与SG186对账时间设置。",	"op_yffsj.gif",	"jsp/docs/yffsj.jsp?apptype=1",			paraflag,  false));  //高压-预付费时间
//		docs_gy.add(new RightMenu("扩展信息",	"用户费控扩展信息档案。",					"op_fkkz.gif",	"jsp/docs/yffparaext_spec.jsp",			paraflag,  false));  //高压-费控扩展参数
//		docs_gy.add(new RightMenu("档案模板",	"专变费控信息档案模板。",					"op_fkmb.gif",	"jsp/docs/yffparamodel_spec.jsp",		paraflag,  false));//高压-费控参数快速建档模板
//		docs_gy.add(new RightMenu("快速建档",	"专变费控信息快速建档。",					"op_ksjd.gif",	"jsp/docs/yffpara_spec.jsp?type=1",		paraflag,  false));//高压-费控参数快速建档
//		
//		
//		docs_np.add(new RightMenu("电表控制参数","农排电表控制参数管理。",					"op_fkcs.gif",	"jsp/docs/yffpara_np.jsp",		paraflag, false));  //农排-电表控制参数
//		docs_np.add(new RightMenu("电表控制批量","批量修改农排电表控制参数。",				"op_fkpl.gif",	"jsp/docs/yffparapl_np.jsp",	paraflag, false));  //农排-电表控制批量
//
//		docs_np.add(new RightMenu("批量导入电表信息","批量导入农排电表信息。",				"op_ndbpl.gif",	"jsp/docs/yffparapl_npb.jsp",	paraflag, false));  //农排-电表信息批量导入
		
//		docs_np.add(new RightMenu("片区费控参数","农排片区费控参数管理。",					"op_fkcs.gif",	"jsp/docs/yffpara_area.jsp",	paraflag, false));  //农排-片区费控参数
//		docs_np.add(new RightMenu("片区费控批量","批量修改农排片区费控参数。",				"op_fkpl.gif",	"jsp/docs/yffparapl_area.jsp",	paraflag, false));  //农排-片区费控批量
//		docs_np.add(new RightMenu("预付费时间",	"预付费启用时间、与SG186对账时间设置。",	"op_yffsj.gif",	"jsp/docs/yffsj.jsp?apptype=1",	paraflag,  false));  //农排-预付费时间
//		docs_np.add(new RightMenu("扩展信息",	"用户费控扩展信息档案。",					"op_fkkz.gif",	"jsp/docs/yffparaext_spec.jsp",	paraflag,  false));  //农排-费控扩展参数
	}
	
	//档案-生成左侧菜单的代码
	public static String getDoc_menu(YffManDef yffman){
		
		StringBuffer ret = new StringBuffer();
		
		Byte ggda = yffman.getRese3_flag();
		if(ggda == null) ggda = 0;
		//int t = 0;

		
		//生成管理栏的内容
		byte app_type = yffman.getApptype();
		List<Integer> lIndex = new ArrayList<Integer>(); 
		
		if(ggda != 0) {
			lIndex.add(0);
		}
		if((app_type & SDDef.YFF_APPTYPE_DYQX) == SDDef.YFF_APPTYPE_DYQX){
			lIndex.add(1);

		}
//		if((app_type & SDDef.YFF_APPTYPE_GYQX) == SDDef.YFF_APPTYPE_GYQX){
//			lIndex.add(2);
//		}
 
//		if((app_type & SDDef.YFF_APPTYPE_NPQX) == SDDef.YFF_APPTYPE_NPQX){
//			lIndex.add(3);
//		}

		String left_ids = "", ids = "";

		for (int j = 0; j < lIndex.size() ; j++) {
			int i = lIndex.get(j);
			boolean flag = WebConfig.menuShow.get("da_leftmenu" + i);
			if(!flag) {
				continue;
			}
			
			left_ids += ",\"da_leftmenu_" + i + "\"";
			ids += ",\"" + docs_leftMenu.get(i).id + "\"";
			
			LeftMenu lm = docs_leftMenu.get(i);
			
			ret.append("<tr id='da_leftmenu_" + i + "' onmouseover='mover(this)' onmouseout='mout(this);' onclick='mclick(this)'>");
			ret.append("<td width=3></td>");
			ret.append("<td width=30><img src=\"" + picpath + lm.picsrc + "\" height=30/></td>");
			ret.append("<td class='lm_padleft'>" + lm.desc + "</td>");
			ret.append("<td width=3></td></tr>");
		}
		
		if(left_ids.length() > 0) left_ids = left_ids.substring(1);
		if(ids.length() > 0) ids = ids.substring(1);
		
		ret.append("<tr><td><script>var leftmenu = {left_id :[" + left_ids + "],id:[" + ids + "]};</script></td></tr>");
		
		return ret.toString();
	}
	
	//档案-生成右侧操作菜单-公共档案
	public static String getDocPublic(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("da_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "Archives Management" + SDDef.NAVIG_SPRT + "Public Archives";
		String menuId = "public";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < docs_pb.size(); i++) {
			RightMenu rm = docs_pb.get(i);
			ret.append(getOptr(rm, naviga, menuId, i));
			ret.append("\n");
		}
				
		ret.append("</table>");
		return ret.toString();
	}

	//档案-生成右侧操作菜单-专变费控档案
	public static String getDocGy(YffManDef yffman, int menu_idx){

		boolean flag = WebConfig.menuShow.get("da_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "Archives Management" + SDDef.NAVIG_SPRT + "Operation Log Query";
		String menuId = "log";
		
		StringBuffer ret = new StringBuffer();
		//只有低压权限
		byte app_type = yffman.getApptype();
		if(app_type == SDDef.YFF_APPTYPE_DYQX){
			return SDDef.EMPTY;
		}
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < docs_gy.size(); i++) {
			RightMenu rm = docs_gy.get(i);
			ret.append(getOptr(rm, naviga, menuId, i));
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	
	//档案-生成右侧操作菜单-低压费控档案
	public static String getDocDy(YffManDef yffman, int menu_idx){

		boolean flag = WebConfig.menuShow.get("da_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "Archives Management" + SDDef.NAVIG_SPRT + "Low Voltage Cost Control Archives";
		String menuId = "dyfk";
		
		StringBuffer ret = new StringBuffer();
		//只有低压权限
		byte app_type = yffman.getApptype();
		if(app_type == SDDef.YFF_APPTYPE_GYQX){
			return SDDef.EMPTY;
		}
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < docs_dy.size(); i++) {
			RightMenu rm = docs_dy.get(i);
			ret.append(getOptr(rm, naviga, menuId, i));
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	
	//档案-生成右侧操作菜单-农排费控档案
	public static String getDocNp(YffManDef yffman, int menu_idx){

		boolean flag = WebConfig.menuShow.get("da_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "档案管理" + SDDef.NAVIG_SPRT + "农排费控档案";
		String menuId = "npfk";
		
		StringBuffer ret = new StringBuffer();
		//只有低压权限
		byte app_type = yffman.getApptype();
		if(app_type == SDDef.YFF_APPTYPE_DYQX){
			return SDDef.EMPTY;
		}
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < docs_np.size(); i++) {
			RightMenu rm = docs_np.get(i);
			ret.append(getOptr(rm, naviga, menuId, i));
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}

	//****************档案--结束***************
	
	//****************其他--开始****************
	//初始化其他操作菜单（右边菜单）。
	public static void initOther(){
		
//		other_ctrl.add(new RightMenu("报警控制",	"报警控制设置。",	"op_alarm.gif",		"jsp/other/alarm.jsp",		ctrlflag, false)); 
//		other_ctrl.add(new RightMenu("节假保电",	"节假日保电设置。",	"op_baodian.gif",	"jsp/other/baodian.jsp",	ctrlflag, false));
		other_down.add(new RightMenu("System Tools",	"Logiciel système",		"op_xtgj.gif",	"jsp/other/systools.jsp",		paraflag, false));
		other_down.add(new RightMenu("Debug Tools",	"Outils de débogage système",	"op_tsgj.gif",	"jsp/other/debugtools.jsp",		paraflag, false));
		
		//20131211添加对账操作，从系统控制移动"打印模板"到扩展功能。
		other_extend.add(new RightMenu("Print Template",	"Paramètres du modèle d'impression。",	"op_dypz.gif",		"jsp/other/print.jsp",		ctrlflag, false));
		
		//20150128 zkz 修改为缴费权限
//		other_extend.add(new RightMenu("手工对账",	"和MIS系统进行高低压对账。",	"op_mischeck.gif",		"jsp/other/misCheck.jsp",		payflag, false));
		
	}

	//其他-生成左侧菜单的代码
	public static String getOther_menu(YffManDef yffman){
		
		if(yffman == null) return "";
		
		StringBuffer ret = new StringBuffer();
		String left_ids = "", ids = "";
		for (int i = 0; i < other_leftMenu.size(); i++) {
			
			boolean flag = WebConfig.menuShow.get("other_leftmenu" + i);
			if(!flag) {
				continue;
			}
			
			left_ids += ",\"other_leftmenu_" + i + "\"";
			ids += ",\"" + other_leftMenu.get(i).id + "\"";
			
			LeftMenu lm = other_leftMenu.get(i);
			
			ret.append("<tr id='other_leftmenu_" + i + "' onmouseover='mover(this)' onmouseout='mout(this);' onclick='mclick(this)'>");
			ret.append("<td width=3></td>");
			ret.append("<td width=30><img src=\"" + picpath + lm.picsrc + "\" height=30px;/></td>");
			ret.append("<td class='lm_padleft'>" + lm.desc + "</td>");
			ret.append("<td width=3></td></tr>");
		}
		
		if(left_ids.length() > 0) left_ids = left_ids.substring(1);
		if(ids.length() > 0) ids = ids.substring(1);
		
		ret.append("<tr><td><script>var leftmenu={left_id :[" + left_ids + "],id:[" + ids + "]};</script></td></tr>");
		
		return ret.toString();
	}	
	//其他-生成右侧操作菜单-公共档案
	public static String getOtherCtrl(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("other_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "其他" + SDDef.NAVIG_SPRT + "系统控制";
		String menuId = "ctrl";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < other_ctrl.size(); i++) {
			RightMenu rm = other_ctrl.get(i);
			ret.append(getOptr(rm, naviga, menuId, i));
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	
	public static String getOtherDown(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("other_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "Others" + SDDef.NAVIG_SPRT + "Download";
		String menuId = "down";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < other_down.size(); i++) {
			RightMenu rm = other_down.get(i);
			ret.append(getOptr(rm, naviga, menuId, i));
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	
	public static String getOtherExtend(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("other_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "Others" + SDDef.NAVIG_SPRT + "Extended function";
		String menuId = "extend";
		byte app_type = yffman.getApptype();
		byte rank = yffman.getRank();
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < other_extend.size(); i++) {
			RightMenu rm = other_extend.get(i);
			//MIS对账权限:低压、高压、系统内所有终端
			//20150128 zkz 修改为缴费权限 低压 || 高压 || 系统内所有 原来为&& 
			if(i==1){
				if ((rank == 0) || ((app_type & SDDef.YFF_APPTYPE_DYQX) == SDDef.YFF_APPTYPE_DYQX) || ((app_type & SDDef.YFF_APPTYPE_GYQX) == SDDef.YFF_APPTYPE_GYQX)){
					ret.append(getOptr(rm, naviga, menuId, i));
				}else{
					continue;
				}
			}
			else{
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	
	//***************其他--结束*****************
	
	
	
	//*************低压--开始*******
	public static String getDy_print(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("dy_leftmenu" + menu_idx);
		if(!flag) return "";
		
//		String naviga = "居民售电" + SDDef.NAVIG_SPRT + "打印";
		String naviga = "Residents" + SDDef.NAVIG_SPRT + "Print";
		String menuId = "print";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < dy_print.size(); i++) {
			RightMenu rm = dy_print.get(i);
			int type = rm.type;
			if(type == allflag){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
		
	}
	
	
	public static String getDy_tool(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("dy_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "Residential Electricity" + SDDef.NAVIG_SPRT + "Tool";
		String menuId = "tool";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		int realnum = 0, addflag = 0;
		for (int i = 0; i < dy_tool.size(); i++) {
			RightMenu rm = dy_tool.get(i);
			int type = rm.type;
			addflag = 0;
			if (realnum % 2 == 0) ret.append("<tr>");
			if(type == allflag){
				ret.append(getOptr2(rm, naviga, menuId, i));	addflag = 1;
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr2(rm, naviga, menuId, i));	addflag = 1;
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr2(rm, naviga, menuId, i));	addflag = 1;
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr2(rm, naviga, menuId, i));	addflag = 1;
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));	addflag = 1;
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr2(rm, naviga, menuId, i));	addflag = 1;
			}
//			ret.append("\n");
			
			if (addflag == 1)	realnum++;
			if (realnum % 2 == 0) ret.append("</tr>");
			 
		}
		
		if (realnum % 2 == 1){			//如果为单行 添加 补齐
			ret.append("<td></td></tr>");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	
//	public static String getDy_tool(YffManDef yffman, int menu_idx){
//		
//		boolean flag = WebConfig.menuShow.get("dy_leftmenu" + menu_idx);
//		if(!flag) return "";
//		
//		String naviga = "居民售电" + SDDef.NAVIG_SPRT + "工具";
//		String menuId = "tool";
//		
//		StringBuffer ret = new StringBuffer();
//		
//		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
//		
//		for (int i = 0; i < dy_tool.size(); i++) {
//			RightMenu rm = dy_tool.get(i);
//			int type = rm.type;
//			if(type == allflag){
//				ret.append(getOptr(rm, naviga, menuId, i));
//			}else if(type == openflag && yffman.getOpenflag() == have){
//				ret.append(getOptr(rm, naviga, menuId, i));
//			}else if(type == payflag && yffman.getPayflag() == have){
//				ret.append(getOptr(rm, naviga, menuId, i));
//			}else if(type == paraflag && yffman.getParaflag() == have){
//				ret.append(getOptr(rm, naviga, menuId, i));
//			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
//				ret.append(getOptr(rm, naviga, menuId, i));
//			}else if(type == viewflag && yffman.getViewflag() == have){
//				ret.append(getOptr(rm, naviga, menuId, i));
//			}
//			ret.append("\n");
//		}
//		
//		ret.append("</table>");
//		return ret.toString();
//	}

	public static String getDy_report(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("dy_leftmenu" + menu_idx);
		if(!flag) return "";
		
//		String naviga = "居民售电" + SDDef.NAVIG_SPRT + "报表";
		String naviga = "Residents" + SDDef.NAVIG_SPRT + "Report";
		String menuId = "report";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < dy_report.size(); i++) {
			RightMenu rm = dy_report.get(i);
			int type = rm.type;
			if(type == allflag){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	public static String getDy_search(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("dy_leftmenu" + menu_idx);
		if(!flag) return "";
		
//		String naviga = "居民售电" + SDDef.NAVIG_SPRT + "查询";
		String naviga = "Residents" + SDDef.NAVIG_SPRT + "Query";
		String menuId = "query";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < dy_search.size(); i++) {
			RightMenu rm = dy_search.get(i);
			int type = rm.type;
			if(type == allflag){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	public static String getDy_localremote(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("dy_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "居民售电" + SDDef.NAVIG_SPRT + "本地费控(远程)";
		String menuId = "localremote";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < dy_localremote.size(); i++) {
			RightMenu rm = dy_localremote.get(i);
			int type = rm.type;
			if(type == allflag){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	public static String getDy_main(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("dy_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "居民售电" + SDDef.NAVIG_SPRT + "主站费控";
		String menuId = "main";
		
		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < dy_main.size(); i++) {
			RightMenu rm = dy_main.get(i);
			int type = rm.type;
			if(type == allflag){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	
	public static String getDy_localcard(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("dy_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "Residential Electricity" + SDDef.NAVIG_SPRT + "Local Cost Control";
		String menuId = "localcard";

		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < dy_localcard.size(); i++) {
			RightMenu rm = dy_localcard.get(i);
			int type = rm.type;
			if(type == allflag){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}

	public static String getDy_localcardExt(YffManDef yffman, int menu_idx){
		
		boolean flag = WebConfig.menuShow.get("dy_leftmenu" + menu_idx);
		if(!flag) return "";
		
		String naviga = "居民售电" + SDDef.NAVIG_SPRT + "本地费控(外接卡式)";
		String menuId = "localcardExt";

		StringBuffer ret = new StringBuffer();
		
		ret.append("<table id=\"" + menuId + "\" class=\"menuright\" cellpadding=\"0\" cellspacing=\"0\" align=center style=\"display: none;\">");
		
		for (int i = 0; i < dy_localcardExt.size(); i++) {
			RightMenu rm = dy_localcardExt.get(i);
			int type = rm.type;
			if(type == allflag){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == openflag && yffman.getOpenflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == payflag && yffman.getPayflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == paraflag && yffman.getParaflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == ctrlflag && yffman.getCtrlFlag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}else if(type == viewflag && yffman.getViewflag() == have){
				ret.append(getOptr(rm, naviga, menuId, i));
			}
			ret.append("\n");
		}
		
		ret.append("</table>");
		return ret.toString();
	}
	
	//*************低压--结束***********
	
	//顶级菜单
	public static String getTopMenu(YffManDef yffman){
		StringBuffer ret = new StringBuffer();
		byte doc_per = yffman.getRese1_flag() == null ? 0 : yffman.getRese1_flag();
		byte app_type = yffman.getApptype();
		String js_top_menus = "", js_top_menus_desc = "";
		
		//居民权限
		if((app_type & SDDef.YFF_APPTYPE_DYQX) == SDDef.YFF_APPTYPE_DYQX){
			ret.append("<td class='menu_td' id='top_dysd'><img src='images/dysd.gif' width=40/><div>Resident</div></td>\n");
			ret.append("<td class='sep_td'></td>\n");
			js_top_menus += "'top_dysd',";
			js_top_menus_desc += "'Residential Electricity',";
		}
//		//高压权限
//		if((app_type & SDDef.YFF_APPTYPE_GYQX) == SDDef.YFF_APPTYPE_GYQX){
//			ret.append("<td class='menu_td' id='top_gysd'><img src='images/gysd.gif' width=40/><div>Special</div></td>\n");
//			ret.append("<td class='sep_td'></script></td>\n");
//			js_top_menus += "'top_gysd',";
//			js_top_menus_desc += "'专变售电',";
//		}
//		//农排权限
//		if((app_type & SDDef.YFF_APPTYPE_NPQX) == SDDef.YFF_APPTYPE_NPQX){
//			ret.append("<td class='menu_td' id='top_npsd'><img src='images/npsd.gif' width=40/><div>Irrigation</div></td>\n");
//			ret.append("<td class='sep_td'></script></td>\n");
//			js_top_menus += "'top_npsd',";
//			js_top_menus_desc += "'农排售电',";
//		}
//		//居民&高压权限
//		else if(app_type == SDDef.YFF_APPTYPE_DGQX){
//			ret.append("<td class='menu_td' id='top_dysd'><img src='images/dysd.gif' width=40/><div>&nbsp;"+I18N.getText("index.dysd")+"</div></td>\n");
//			ret.append("<td class='sep_td'></td>\n");
//			ret.append("<td class='menu_td' id='top_gysd'><img src='images/gysd.gif' width=40/><div>&nbsp;"+I18N.getText("index.gysd")+"</div></td>\n");
//			ret.append("<td class='sep_td'></td>\n");
//			js_top_menus = "'top_dysd','top_gysd',";
//			js_top_menus_desc = "'居民售电','专变售电',";
//		}

		
		//档案权限
		if(doc_per != 0) {
			ret.append("<td class='menu_td' id='top_xtda'><img src='images/da.gif' width=40/><div>Archives</div></td>\n");
			ret.append("<td class='sep_td'></td>\n");
			
			js_top_menus += "'top_xtda',";
			js_top_menus_desc += "'Archives',";
		}
		
		js_top_menus += "'top_other'";
		js_top_menus_desc += "'Others'";
		js_top_menus = "<script>var top_menus = [" + js_top_menus + "], menuguide = [" + js_top_menus_desc + "];</script>";
		
		//其他
		ret.append("<td class='menu_td' id='top_other'><img src='images/other.gif' width=40/><div>Others</div>" + js_top_menus + "</td>\n");
		
		return ret.toString();
	}
	
	
	private static List<List<RightMenu>> dy_list 	= new ArrayList<List<RightMenu>>();
	private static List<List<RightMenu>> gy_list 	= new ArrayList<List<RightMenu>>();
	private static List<List<RightMenu>> np_list 	= new ArrayList<List<RightMenu>>();
	private static List<List<RightMenu>> docs_list 	= new ArrayList<List<RightMenu>>();
	private static List<List<RightMenu>> other_list = new ArrayList<List<RightMenu>>();
	
	
	//初始化菜单。tomcat启动时加载。
	public static void initMenu(){
		
		initDy();
//		initGy();
//		initNp();
		initDoc();
		initOther();
		initLeftMenu();
		
		dy_list.add(dy_localcard);
		dy_list.add(dy_localcardExt);
		dy_list.add(dy_main);
		dy_list.add(dy_localremote);
		dy_list.add(dy_search);
		dy_list.add(dy_report);
		dy_list.add(dy_tool);
		dy_list.add(dy_print);
		
//		gy_list.add(gy_localcard);
//		gy_list.add(gy_localmoney);
//		gy_list.add(gy_localbd);
//		gy_list.add(gy_main);
//		gy_list.add(gy_search);
//		gy_list.add(gy_report);
//		gy_list.add(gy_tool);
//		gy_list.add(gy_print);
//		
//		np_list.add(np_localcard);
//		np_list.add(np_search);
//		np_list.add(np_report);
//		np_list.add(np_tool);
//		np_list.add(np_print);
		
		docs_list.add(docs_pb);
		docs_list.add(docs_dy);
		docs_list.add(docs_gy);
//		docs_list.add(docs_np);
		
		other_list.add(other_ctrl);
		
	}
	
	/**
	 * 用户是否有该url页面权限
	 * 仅限居民专变售电部分
	 */
	public static boolean enable(YffManDef yffman, String url){
		
		if(url.indexOf("/dialog/") >= 0){
			return true;
		}
		
		boolean flag = false;
		byte app_type = yffman.getApptype();
		
		if(url.subSequence(0, 1).equals("/")){
			url = url.substring(1);
		}
		
		if(url.indexOf("/dyjc/") >= 0){		//低压
			if (url.indexOf("printTemplate.jsp") > 0) {
				return true;
			}
			
			
			if((app_type & SDDef.YFF_APPTYPE_DYQX) != SDDef.YFF_APPTYPE_DYQX) return false;
			
			boolean break_flag = false;
			for (int i = 0; i < dy_list.size(); i++) {
				List<RightMenu> list = dy_list.get(i);
				
				for (int j = 0; j < list.size(); j++) {
					RightMenu rm = list.get(j);
					if(url.equals(rm.url)){
						int type = rm.type;
						switch(type){
							case allflag:
								flag = true;
								break;
							case openflag:
								if(yffman.getOpenflag() == have){
									flag = true;
								}
								break;
							case payflag:
								if(yffman.getPayflag() == have){
									flag = true;
								}
								break;
							case paraflag:
								if(yffman.getParaflag() == have){
									flag = true;
								}
								break;
							case ctrlflag:
								if(yffman.getCtrlFlag() == have){
									flag = true;
								}
								break;
							case viewflag:
								if(yffman.getViewflag() == have){
									flag = true;
								}
								break;
						}
						break_flag = true;
						break;
					}
				}
				if(break_flag)break;
			}
			
		}
		else if(url.indexOf("/spec/") >= 0){//专变
			
			if((app_type & SDDef.YFF_APPTYPE_GYQX) != SDDef.YFF_APPTYPE_GYQX) return false;
			
			boolean break_flag = false;
			for (int i = 0; i < gy_list.size(); i++) {
				List<RightMenu> list = gy_list.get(i);
				
				for (int j = 0; j < list.size(); j++) {
					RightMenu rm = list.get(j);
					if(rm.url.indexOf(url) >= 0){
						int type = rm.type;
						switch(type){
							case allflag:
								flag = true;
								break;
							case openflag:
								if(yffman.getOpenflag() == have){
									flag = true;
								}
								break;
							case payflag:
								if(yffman.getPayflag() == have){
									flag = true;
								}
								break;
							case paraflag:
								if(yffman.getParaflag() == have){
									flag = true;
								}
								break;
							case ctrlflag:
								if(yffman.getCtrlFlag() == have){
									flag = true;
								}
								break;
							case viewflag:
								if(yffman.getViewflag() == have){
									flag = true;
								}
								break;
						}
						break_flag = true;
						break;
					}
				}
				if(break_flag)break;
			}
		}
		else if(url.indexOf("/np/") >= 0){//农排
			
			if((app_type & SDDef.YFF_APPTYPE_NPQX) != SDDef.YFF_APPTYPE_NPQX) return false;
			
			boolean break_flag = false;
			for (int i = 0; i < np_list.size(); i++) {
				List<RightMenu> list = np_list.get(i);
				
				for (int j = 0; j < list.size(); j++) {
					RightMenu rm = list.get(j);
					if(rm.url.indexOf(url) >= 0){
						int type = rm.type;
						switch(type){
							case allflag:
								flag = true;
								break;
							case openflag:
								if(yffman.getOpenflag() == have){
									flag = true;
								}
								break;
							case payflag:
								if(yffman.getPayflag() == have){
									flag = true;
								}
								break;
							case paraflag:
								if(yffman.getParaflag() == have){
									flag = true;
								}
								break;
							case ctrlflag:
								if(yffman.getCtrlFlag() == have){
									flag = true;
								}
								break;
							case viewflag:
								if(yffman.getViewflag() == have){
									flag = true;
								}
								break;
						}
						break_flag = true;
						break;
					}
				}
				if(break_flag)break;
			}
		}
		
		return flag;
	}
	
	/** 右边操作菜单。生成一个操作栏的代码（一行）*********
	 * @param rm：菜单类
	 * @param navig：导航内容
	 * @param menuId：左侧（上级）菜单ID
	 * @param i：当前RightMenu在list中的index。
	 * @return：html代码（一组）
	 */
	private static String getOptr(RightMenu rm, String navig, String menuId, int i){
		
		StringBuffer bf = new StringBuffer();
		
		bf.append("<tr id='op_" + menuId + i + "' onmouseover='rt_mover(this)' onmouseout='rt_mout(this);' " +
				  "onclick='window.top.open_frame(\"" + rm.url + "\",\"" + navig + SDDef.NAVIG_SPRT + rm.desc + "\"," + rm.show + ");'><td width=3></td>");
		bf.append("<td width=85><img src=\"images/index/" + rm.picsrc + "\" height=\"60\" /></td>");
		bf.append("<td>" + rm.text + "</td>");
		bf.append("<td width=3></td></tr>");
		
		return bf.toString();
		
	}
	
	
	/** 右边操作菜单。生成一个操作栏的代码（一行）*********
	 * @param rm：菜单类
	 * @param navig：导航内容
	 * @param menuId：左侧（上级）菜单ID
	 * @param i：当前RightMenu在list中的index。
	 * @return：html代码（一组）
	 */
	private static String getOptr2(RightMenu rm, String navig, String menuId, int i){
		
		StringBuffer bf = new StringBuffer();

		bf.append("<td><table cellspacing=0 cellpadding=0 width=100%><tr id='op_" + menuId + i + "' onmouseover='rt_mover(this)' onmouseout='rt_mout(this);' " +
				  "onclick='window.top.open_frame(\"" + rm.url + "\",\"" + navig + SDDef.NAVIG_SPRT + rm.desc + "\"," + rm.show + ");'><td width=3></td>");
		bf.append("<td width=85><img src=\"images/index/" + rm.picsrc + "\" height=\"60\" /></td>");
		bf.append("<td>" + rm.text + "</td>");
		bf.append("<td width=3></td></tr></table></td>");
		
		return bf.toString();
		
	}
}
