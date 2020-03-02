<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef,com.kesd.common.WebConfig"%>
<%@page import="com.kesd.common.CommFunc"%>
<%@page import="com.kesd.dbpara.YffManDef"%>
<%@page import="com.kesd.util.Menu"%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<i18n:bundle baseName="<%=SDDef.BUNDLENAME%>" id="bundle" locale="<%=WebConfig.app_locale%>" scope="application" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>Sous-système de vente d'électricité</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<link href="css/zh/index.css" rel="stylesheet" type="text/css" id="index_theme" />
		<link rel="Shortcut Icon" href="images/addrbar.ico" />
		<link rel="Bookmark" href="images/addrbar.ico"/>
		<script src="js/jquery-1.4.2.min.js"></script>
		<script src="js/index.js"></script>
		<script src="js/common/card_comm.js"></script>
		<script src="js/common/def.js"></script>
		<script src="js/common/cookie.js"></script>
		<script src="js/common/webprint.js"></script>
		<script src="js/common/modalDialog.js"></script>
		<script src="js/dialog/opdetail.js"></script>
		<script src="js/common/loading.js"></script>
		<script src="js/common/jsonString.js"></script>
		<script src="js/common/dbupdate.js"></script>
	</head>
	<body id="bodyMain">
	<table width=100% cellpadding="0" cellspacing="0" id="top_nav" style="background:url('images/topbg.gif');">
	<tr>
	<td style="width: 450px; height:75px; background: url('images/logo.jpg') no-repeat;" id="logo_td"> </td>
	<td>
	<%
	YffManDef yffman = (YffManDef)session.getAttribute(SDDef.SESSION_USERNAME);
	if(yffman == null){response.sendRedirect("login.jsp");return;}
	%>
	<table cellpadding="0" cellspacing="0" align="center">
	<tr>
	<%=Menu.getTopMenu(yffman)%>
	</tr>
	</table>
	</td>
	<td align="right">
	<table style="color: white;" >

	<tr>
<!--		<td align="left" colspan="2" style="padding-right: 6px;">Current Operator:<span id="operman" title="Account:<%=yffman.getName()%>"><%=yffman.getDescribe()%></span></td>-->
		<td align="left">Opérateur actuel:</td>
		<td align="left" style="padding-left: 6px;"><%=yffman.getDescribe()%></td>
		<td align="left" style="padding-left: 6px;">Temps de connexion:</td>
		<td align="left" style="padding-left: 6px;"><%=yffman.getReserve2()%></td>
	</tr>
	<tr>
<!--		<td align="left"><span id="alarm_ggtz" style="width: 15px; background: url('images/bullet.gif') no-repeat right;"> </span><span style="padding-right: 6px; color: white; cursor: pointer;"><span id="glo_ggtz">Change Notice</span></span></td>-->
		
		<td align="left"><a href="javascript:chg_pwd();">Changer le mot de passe</a></td>
		<td align="left" style="padding-left: 6px;"><a href="javascript:window.location.reload();">Rafraîchir</a></td>
		<td align="left" style="padding-left: 6px;"><a href="javascript:;" onclick="logout();">Se déconnecter</a></td>
		<td></td>
		
	</tr>

	</table>
	<OBJECT id="YFFWEBCTRL" name="YFFWEBCTRL"  width="0" height="0"
		CLASSID="CLSID:9A285C16-B8AB-435C-B05D-693261E7DAAC"
		CODEBASE="libyffwebctrl.dll#version=1,0,0,1">
	</object>
	</td>
	</tr>
	<tr style="height: 10px; background: url(images/menubg.gif) repeat-x;">
	<td colspan=3>
	</td></tr>
	</table>
	<script type="text/javascript">
		var contH=$(window).height()-80;
		var _lang 			= '<i18n:message key="lang" />';
		var _rank 			= <%=yffman.getRank()%>;
		var _doc_per 		= <%=yffman.getRese1_flag()%>;
		var _pubdoc_per 	= <%=yffman.getRese3_flag()%>;
		var _yffmanId		= <%=yffman.getId()%>;
		var NAVIG_SPRT 		= "<%=SDDef.NAVIG_SPRT%>";
		var _login_single 	= <%=WebConfig.login_single%>;
		
		var ComntUseropDef = {};
		ComntUseropDef.YFF_GYOPER_GPARASTATE 	= <%=com.kesd.comntpara.ComntUseropDef.YFF_GYOPER_GPARASTATE%>;
		ComntUseropDef.YFF_DYOPER_GPARASTATE 	= <%=com.kesd.comntpara.ComntUseropDef.YFF_DYOPER_GPARASTATE%>;
		ComntUseropDef.YFF_NPOPER_GPARASTATE 	= <%=com.kesd.comntpara.ComntUseropDef.YFF_NPOPER_GPARASTATE%>;

		var gynegativemoney = {
			gycard:	 {payflag:<%=WebConfig.gyNegativeMoney.gycard[0]%>, 
					  totflag:<%=WebConfig.gyNegativeMoney.gycard[1]%>, 
					  remainflag:<%=WebConfig.gyNegativeMoney.gycard[2]%>},
			gylocbd: {payflag:<%=WebConfig.gyNegativeMoney.gylocbd[0]%>, 
					  totflag:<%=WebConfig.gyNegativeMoney.gylocbd[1]%>, 
					  remainflag:<%=WebConfig.gyNegativeMoney.gylocbd[2]%>},
			gylocmny:{payflag:<%=WebConfig.gyNegativeMoney.gylocmny[0]%>, 
					  totflag:<%=WebConfig.gyNegativeMoney.gylocmny[1]%>, 
					  remainflag:<%=WebConfig.gyNegativeMoney.gylocmny[2]%>},
			gymain:  {payflag:<%=WebConfig.gyNegativeMoney.gymain[0]%>, 
					  totflag:<%=WebConfig.gyNegativeMoney.gymain[1]%>,
					  remainflag:<%=WebConfig.gyNegativeMoney.gymain[2]%>}
		}

		var dynegativemoney = {
			dycard:	 {payflag:<%=WebConfig.dyNegativeMoney.dycard[0]%>, 
					  totflag:<%=WebConfig.dyNegativeMoney.dycard[1]%>, 
					  remainflag:<%=WebConfig.dyNegativeMoney.dycard[2]%>},
			dylocmny:{payflag:<%=WebConfig.dyNegativeMoney.dylocmny[0]%>, 
					  totflag:<%=WebConfig.dyNegativeMoney.dylocmny[1]%>, 
					  remainflag:<%=WebConfig.dyNegativeMoney.dylocmny[2]%>},
			dymain:  {payflag:<%=WebConfig.dyNegativeMoney.dymain[0]%>, 
					  totflag:<%=WebConfig.dyNegativeMoney.dymain[1]%>,
					  remainflag:<%=WebConfig.dyNegativeMoney.dymain[2]%>}
		}

		var npnegativemoney = {
			npcard:	 {payflag:<%=WebConfig.npNegativeMoney.npcard[0]%>, 
					  totflag:<%=WebConfig.npNegativeMoney.npcard[1]%>, 
					  remainflag:<%=WebConfig.npNegativeMoney.npcard[2]%>}
		}
		
		var provinceMisFlag   = "<%=WebConfig.provinceMisFlag%>";
		var rewrite_maxnum_np = <%=WebConfig.rewrite_maxnum_np%>;
		var reread_maxnum_np  = <%=WebConfig.reread_maxnum_np%>;
	
		var SDDef		   = {};
		SDDef.SUCCESS	 					= "<%=SDDef.SUCCESS%>";

		SDDef.YFF_CARDMTYPE_NULL	= "<%=SDDef.YFF_CARDMTYPE_NULL%>";
		SDDef.YFF_CARDMTYPE_KE001	= "<%=SDDef.YFF_CARDMTYPE_KE001%>";
		SDDef.YFF_CARDMTYPE_KE002	= "<%=SDDef.YFF_CARDMTYPE_KE002%>";
		SDDef.YFF_CARDMTYPE_KE003	= "<%=SDDef.YFF_CARDMTYPE_KE003%>";
		SDDef.YFF_CARDMTYPE_KE005	= "<%=SDDef.YFF_CARDMTYPE_KE005%>";
		SDDef.YFF_CARDMTYPE_KE006	= "<%=SDDef.YFF_CARDMTYPE_KE006%>";
		
		SDDef.YFF_METER_TYPE_ZNK	= "<%=SDDef.YFF_METER_TYPE_ZNK%>";
		SDDef.YFF_METER_TYPE_6103	= "<%=SDDef.YFF_METER_TYPE_6103%>";
		SDDef.YFF_METER_TYPE_NP		= "<%=SDDef.YFF_METER_TYPE_NP%>";
		SDDef.YFF_METER_TYPE_ZNK2	= "<%=SDDef.YFF_METER_TYPE_ZNK2%>";
		SDDef.YFF_METERTYPE_XCB		= "<%=SDDef.YFF_METERTYPE_XCB%>";
		SDDef.YFF_METERTYPE_KEZG09	= "<%=SDDef.YFF_METERTYPE_KEZG09%>";
	</script>
	
	</body>
	
</html>
