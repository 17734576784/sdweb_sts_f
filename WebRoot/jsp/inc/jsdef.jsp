<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@taglib prefix="i18n" uri="taglibs/i18n-1.0"%>
<script>
var comm_i18n = {
	
	gridopt_loading : "<i18n:message key="comm.gridopt.loading" />",
	gridopt_rowsperpage : "<i18n:message key="comm.gridopt.rowsperpage" />",
	gridopt_showallrows : "<i18n:message key="comm.gridopt.showallrows" />",
	gridopt_rowsinfo : "<i18n:message key="comm.gridopt.rowsinfo" />",
	gridopt_num_needed : "<i18n:message key="comm.gridopt.num_needed" />",
	gridopt_showdetail : "<i18n:message key="comm.gridopt.showdetail" />",
	gridopt_hidedetail : "<i18n:message key="comm.gridopt.hidedetail" />",
	gridopt_del_cond : "<i18n:message key="comm.gridopt.del_cond" />",
	gridopt_del_query : "<i18n:message key="comm.gridopt.del_query" />",
	gridopt_del_fail : "<i18n:message key="comm.gridopt.del_fail" />",
	gridopt_update_cond : "<i18n:message key="comm.gridopt.update_cond" />",
	gridopt_add_fail : "<i18n:message key="comm.gridopt.add_fail" />",
	gridopt_update_fail : "<i18n:message key="comm.gridopt.update_fail" />"
	
};

var vali_i18n = {
	notnull : "<i18n:message key="validate.notnull" />",
	num_needed : "<i18n:message key="validate.num_needed" />",
	between : "<i18n:message key="validate.between" />",
	small : "<i18n:message key="validate.small" />",
	err : "<i18n:message key="validate.err" />"
};
function getText_i18n(key,params){//params格式:参数1,参数2,……,参数n
	var par = params.toString();
	par = par + ",";
	var param  = par.split(",");
	var rel = new RegExp("{","g");
	var rer = new RegExp("}","g");
	key = key.replace(rel,"#");
	key = key.replace(rer,"#");
	for(var i=0;i<param.length - 1;i++){
   		var re = new RegExp("#"+i+"#","g");
   		key = key.replace(re,param[i]);
   	}
	return key; 
}

</script>