<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.kesd.common.SDDef"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>card information</title>
	</head>
	<link href="<%=basePath%>css/zh/page.css" rel="stylesheet" type="text/css" id="cont" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>css/zh/cont.css" id="cont" />	
	<style type="text/css">
	.tbl tr{
		event:expression(
		onmouseover = function(){this.style.backgroundColor='#99FFCC'},
		onmouseout = function(){this.style.backgroundColor=''}
		)
	}
	.td_ali{
		text-align: center;
	}
	</style>
	<script  src="<%=basePath%>/js/jquery-1.4.2.min.js"></script>
	<script  src="<%=basePath%>js/common/def.js"></script>
	<script  src="<%=basePath%>js/common/card_comm.js"></script>
	<script  src="<%=basePath%>js/common/loading.js"></script>
	<script  src="<%=basePath%>js/common/jsonString.js"></script>
	<script  src="<%=basePath%>js/dialog/cardinfoBengal.js"></script>	
	<body>
	
	<OBJECT id="YFFWEBCTRL" name="YFFWEBCTRL"  width="0" height="0"
	CLASSID="CLSID:9A285C16-B8AB-435C-B05D-693261E7DAAC"
	CODEBASE="libyffwebctrl.dll#version=1,0,0,1">
	</object>

	<%
	int i = 1;
	int j = 1;
	%>
	<div class="tbl">
	<table width="480" id="tbother" style="display:blank" align="center">
	<tr bgcolor="#E7F8F2" align="center"><td width="40%">Serial No.</td><td width="60%">Describe</td><td width="45%">Value</td></tr>
	<tr><td colspan =3 >Block1</td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Answer To Reset</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Version</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Meter ID</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Consumer ID</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Utility ID</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Sanctioned Load</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Meter Type</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Last Recharge Amount </td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Last Recharge Date</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Last Transaction ID</td><td id="other<%=j++%>"></td></tr>
	<tr><td colspan =3 >Block2</td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Card Type</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Card Used Flag</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Token Total Number</td><td id="other<%=j++%>"></td></tr>
	<tr><td colspan =3 >Block3</td></tr>
	<%for(int k = 1; k <= 25; k++) {%>
		<tr><td class="td_ali"><%=i++%></td><td>Token<%=k%></td><td id="other<%=j++%>"></td></tr>
	<%}%>
	<tr><td colspan =3 >Block4</td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Meter ID</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Consumer ID</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Utility ID</td><td id="other<%=j++%>"></td></tr>
	<tr><td colspan =3 >Block6</td></tr>
	<%for(int k = 1; k <= 25; k++) {%>
		<tr><td class="td_ali"><%=i++%></td><td>Token<%=k%> Return Code</td><td id="other<%=j++%>"></td></tr>
	<%}%>
	<tr><td class="td_ali"><%=i++%></td><td>Recharge Date Time</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Recharge Amount</td><td id="other<%=j++%>"></td></tr>
	<tr><td colspan =3 >Block7</td></tr>
	<tr><td></td><td colspan =2 >LastMon1</td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Billing Date&Time</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active energy import</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Taka Recharged</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Taka Used</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active Maximum Power</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Reactive Maximum Power</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active energy import T1</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active energy import T2</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Reactive energy import T1</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Reactive energy import T2</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Total Charge T1</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Total Charge T2</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Number of Power Failures</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Number of Sanctioned Load Exceeded</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Month Average Power Factor</td><td id="other<%=j++%>"></td></tr>
	<tr><td></td><td colspan =2 >LastMon2</td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Billing Date&Time</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active energy import</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Taka Recharged</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Taka Used</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active Maximum Power</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Reactive Maximum Power</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active energy import T1</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active energy import T2</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Reactive energy import T1</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Reactive energy import T2</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Total Charge T1</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Total Charge T2</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Number of Power Failures</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Number of Sanctioned Load Exceeded</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Month Average Power Factor</td><td id="other<%=j++%>"></td></tr>
	<tr><td></td><td colspan =2 >LastMon3</td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Billing Date&Time</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active energy import</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Taka Recharged</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Taka Used</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active Maximum Power</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Reactive Maximum Power</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active energy import T1</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active energy import T2</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Reactive energy import T1</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Reactive energy import T2</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Total Charge T1</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Total Charge T2</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Number of Power Failures</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Number of Sanctioned Load Exceeded</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Month Average Power Factor</td><td id="other<%=j++%>"></td></tr>
	<tr><td></td><td colspan =2 >LastMon4</td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Billing Date&Time</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active energy import</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Taka Recharged</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Taka Used</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active Maximum Power</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Reactive Maximum Power</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active energy import T1</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active energy import T2</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Reactive energy import T1</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Reactive energy import T2</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Total Charge T1</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Total Charge T2</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Number of Power Failures</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Number of Sanctioned Load Exceeded</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Month Average Power Factor</td><td id="other<%=j++%>"></td></tr>
	<tr><td></td><td colspan =2 >LastMon5</td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Billing Date&Time</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active energy import</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Taka Recharged</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Taka Used</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active Maximum Power</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Reactive Maximum Power</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active energy import T1</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active energy import T2</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Reactive energy import T1</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Reactive energy import T2</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Total Charge T1</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Total Charge T2</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Number of Power Failures</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Number of Sanctioned Load Exceeded</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Month Average Power Factor</td><td id="other<%=j++%>"></td></tr>
	<tr><td></td><td colspan =2 >LastMon6</td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Billing Date&Time</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active energy import</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Taka Recharged</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Taka Used</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active Maximum Power</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Reactive Maximum Power</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active energy import T1</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Active energy import T2</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Reactive energy import T1</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Reactive energy import T2</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Total Charge T1</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Total Charge T2</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Number of Power Failures</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Number of Sanctioned Load Exceeded</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Month Average Power Factor</td><td id="other<%=j++%>"></td></tr>
	<tr><td colspan =3 >Block8</td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>ReturnToken</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Update Flag</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Key No.</td><td id="other<%=j++%>"></td></tr>
	<tr><td class="td_ali"><%=i++%></td><td>Seq No.</td><td id="other<%=j++%>"></td></tr>	
	<tr><td colspan =3 style="border: 0"></td></tr>
	
	</table>
	</div>
	</body>
</html>
