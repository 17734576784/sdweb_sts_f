$(document).ready(function(){
	
	$.post(def.basePath + "ajax/actCommon!getOperName.action", {}, function(data){
		if(data.result != ""){
			if(data.result != SDDef.ADMIN){
				$("#residentLabel").remove();
				$("#residentBtn").remove();
			}
		}
	});	
	initDropdown();
	initGrid();
	$("#pldel").click(function(){
		gridopt.plDel();
	});
	$("#tianjia").click(function(){
		getApptype();
		if(check()){gridopt.addOrEdit('add')};
	});
	$("#xiugai").click(function(){
		getApptype();
		if(check()){gridopt.addOrEdit('edit')}
	});
	
	$("#rank").change(function(){
		disableChoose();
	});
	
	$("#selectConsDialog").click(function(){
		var selectId = gridopt.grid.getSelectedId();
		modalDialog.height = 450;
		modalDialog.width = 650;
		modalDialog.url = def.basePath + "jsp/dialog/selectConsPermission.jsp";
		modalDialog.param = {"yffmanOrg":"","yffmanId":selectId,",consInfo":"","sghz_flag":""};
		var o = modalDialog.show();
	});
	
	//随供电所联动线路负责人
	$("#orgId").change(function(){
		getLineFzMan();
	});
	
});

//根据权限范围，禁用、启用供电所、线路负责人下拉列表
function disableChoose(){
	var rank_data = $("#rank").val();
	if(rank_data == 0){
		$("#orgId").attr("disabled", "disabled");
 	}
	else if(rank_data == 1){
		$("#orgId").attr("disabled", false);
	}
	else{
		$("#orgId").attr("disabled", false);
	}
}

function initGrid(){
	
	gridopt.gridHeader           = "<img src='" + def.basePath + "images/grid/imgs/item_chk0.gif' onclick='gridopt.selectAllOrNone(this);' />,Numéro de série.,Identifiant,La description,Plage de permission,Source de courant,Autorisation de rapport,Autorisation de compte,L'achat de l'autorisation d'électricité,Modifier les autorisations,Autorisations de contrôle,Autorisations d'archivage,Autorisations de gestion des utilisateurs,Autorisations d'archivage public,&nbsp;";
	gridopt.gridColAlign         = "center,center,left,left,left,left,left,left,left,left,left,left,left,left,left";
	gridopt.gridColTypes         = "ch,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro,ro";
	gridopt.gridWidths           = "40,50,80,100,120,100,100,100,100,100,100,100,100,100,*";
	gridopt.gridTooltips         = "false,false,true,false,false,false,false,false,false,false,false,false,false,false";
	
	$("#vfreeze").val(1);
	$("#hfreeze").val(0);
	$("#header").val(encodeURI(gridopt.gridHeader));
	$("#colType").val("int,int,str,str,str,str,str,str,str,str,str,str,str,str,str");
	$("#filename").val(encodeURI("personnel prépayé"));
	
	gridopt.gridGetDataUrl       = def.basePath  + "ajaxdocs/actYffManDef.action";
	gridopt.gridSelRowUrl        = def.basePath  + "ajaxdocs/actYffManDef!getYffManDefById.action";
	gridopt.gridAddOrEditUrl     = def.basePath  + "ajaxdocs/actYffManDef!addOrEdit.action";
	gridopt.gridDelRowUrl        = def.basePath  + "ajaxdocs/actYffManDef!delYffManDefById.action";
	gridopt.gridTitleSimpleParas = "id,name,describe,apptype,rank,orgId,fzmanId,viewflag,openflag,payflag,paraflag,ctrlFlag";
	gridopt.gridTitleDetailParas = "id,name,describe,passwd,apptype,rank,orgId,viewflag,openflag,payflag,paraflag,ctrlFlag,rese1_flag,rese2_flag,rese3_flag";
	gridopt.redoOnRowSelected    = true;
	gridopt.selectRow			 = 0;
	gridopt.filter(100);

}
function initDropdown(){
	var text = '{item:["' + Dict.DICTITEM_YFFRANK + '","' + Dict.DICTITEM_DOCRIGHT + '"]}';
	$.post(def.basePath + "ajax/actCommon!retDict.action", {text : text}, function(data)
	{
		var json = eval('(' + data.result + ')');
		var iid = 0;
		var dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_YFFRANK);
		for ( var i = 0; i < dict.length; i++) {
			//if(dict[i].value == 2)continue;		//线路负责人解禁20140709

			$("#rank").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
		/*var dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_YFFAPPTYPE);
		for ( var i = 0; i < dict.length; i++) {
			$("#apptype").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}*/
		var dict = eval("json.rows[" + (iid++) + "]."+Dict.DICTITEM_DOCRIGHT);
		for ( var i = 0; i < dict.length; i++) {
			$("#rese1_flag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
			$("#rese3_flag").append("<option value=" + dict[i].value + ">" + dict[i].text + "</option>");
		}
	});
	
	$.post(def.basePath + "ajax/actCommon!getOrgByYHQX.action",{},function(data)
	{
		if(data.result != ""){
			var json = eval('(' + data.result + ')');
			for(var i=0;i<json.length;i++){
				$("#orgId").append("<option value="+json[i].value+">"+json[i].text+"</option>");
			}
		}
	});
}

//json为对选中行重新进行数据库查询返回的数据
function redoOnRowSelected(json){
	var rowsplit = gridopt.gridTitleDetailParas.split(SDDef.SPLITCOMA);
	var orgId    = -1;
	var linefzid = -1;
	for(var i = 0; i < rowsplit.length; i ++){
		if(rowsplit[i] == "orgId"){
			orgId = json.data[i];
		}
		if(rowsplit[i] == "fzmanId"){
			linefzid = json.data[i];
		}
		if(rowsplit[i] == "viewflag"){
			if(json.data[i] == 0){
				$("#viewFlag_chk").attr("checked",false);
			}
			else if(json.data[i] == 1){
				$("#viewFlag_chk").attr("checked",true);
			}
		}
		else if(rowsplit[i] == "openflag"){
			if(json.data[i] == 0){
				$("#openFlag_chk").attr("checked",false);
			}
			else if(json.data[i] == 1){
				$("#openFlag_chk").attr("checked",true);
			}
		}
		else if(rowsplit[i] == "payflag"){
			if(json.data[i] == 0){
				$("#payFlag_chk").attr("checked",false);
			}
			else if(json.data[i] == 1){
				$("#payFlag_chk").attr("checked",true);
			}
		}
		else if(rowsplit[i] == "paraflag"){
			if(json.data[i] == 0){
				$("#paraFlag_chk").attr("checked",false);
			}
			else if(json.data[i] == 1){
				$("#paraFlag_chk").attr("checked",true);
			}
		}
		else if(rowsplit[i] == "ctrlFlag"){
			if(json.data[i] == 0){
				$("#ctrlFlag_chk").attr("checked",false);
			}
			else if(json.data[i] == 1){
				$("#ctrlFlag_chk").attr("checked",true);
			}
		}else if(rowsplit[i] == "rese1_flag"){
			if(json.data[i] == ""){
				$("#rese1_flag").val(0);
			}
		}else if(rowsplit[i] == "rese2_flag"){
			if(json.data[i] == 0){
				$("#rese2_flag_chk").attr("checked",false);
			}
			else if(json.data[i] == 1){
				$("#rese2_flag_chk").attr("checked",true);
			}
		}else if(rowsplit[i] == "rese3_flag"){
			if(json.data[i] == ""){
				$("#rese3_flag").val(0);
			}
		}else if(rowsplit[i] == "apptype"){
			var app_type = json.data[i];
			$("#dyqx_chk").attr("checked", ((parseInt(app_type) & SDDef.YFF_APPTYPE_DYQX) == SDDef.YFF_APPTYPE_DYQX));
			$("#zbqx_chk").attr("checked", ((parseInt(app_type) & SDDef.YFF_APPTYPE_GYQX) == SDDef.YFF_APPTYPE_GYQX));
			$("#npqx_chk").attr("checked", ((parseInt(app_type) & SDDef.YFF_APPTYPE_NPQX) == SDDef.YFF_APPTYPE_NPQX));
		}
		disableChoose();
		$("#"+rowsplit[i]).val(json.data[i]);//给界面对应位置赋值
	}
	getLineFzMan(linefzid);
}

function getLineFzMan(linefzid){//根据供电所ID查询线路负责人列表，填充$("#fzmanId")。
	var orgid=$("#orgId").val();
	if(!orgid){
		$("#fzmanId").html("<option value=''>--Veuillez sélectionner--</option>");	
		return;
	}
	$.post(def.basePath + "ajax/actCommon!getLineFzByOrg.action",
			{
				value:orgid
			},
			function(data){
			if(data.result == ""){
				
			}
			else{
				var json = eval('(' + data.result + ')');
				var option = "<option value=''>--Veuillez sélectionner--</option>";
				for(var i=0; i<json.length; i++){
					option += "<option value=" + json[i].value + ">" + json[i].text + "</option>";
				}
				$("#fzmanId").html(option);	
				//将选中行的线路负责人信息赋值到对应下拉列表
				$("#fzmanId").val(linefzid);
			}
	});
}

function check() {
	if($("#viewFlag_chk").attr("checked")){
		$("#viewflag").val(1)
	}
	else if(!($("#viewFlag_chk").attr("checked"))){
		$("#viewflag").val(0)
	}
	
	if($("#openFlag_chk").attr("checked")){
		$("#openflag").val(1)
	}
	else if(!($("#openFlag_chk").attr("checked"))){
		$("#openflag").val(0)
	}
	
	if($("#payFlag_chk").attr("checked")){
		$("#payflag").val(1)
	}
	else if(!($("#payFlag_chk").attr("checked"))){
		$("#payflag").val(0)
	}
	
	if($("#paraFlag_chk").attr("checked")){
		$("#paraflag").val(1)
	}else if(!($("#paraFlag_chk").attr("checked"))){
		$("#paraflag").val(0)
	}
	
	if($("#ctrlFlag_chk").attr("checked")){
		$("#ctrlFlag").val(1)
	}
	else if(!($("#ctrlFlag_chk").attr("checked"))){
		$("#ctrlFlag").val(0)
	}
	
	if($("#rese2_flag_chk").attr("checked")){
		$("#rese2_flag").val(1)
	}
	else if(!($("#reserve2_chk").attr("checked"))){
		$("#rese2_flag").val(0)
	}
	if (!ckChar("name", name, 64, true)) {
		return false;
	}
	return true;
}

function getApptype(){
	var apptype = 0;
	if($("#dyqx_chk").attr("checked")){
		apptype = parseInt($("#dyqx_chk").val()) + apptype;
	};
	if($("#zbqx_chk").attr("checked")){
		apptype = parseInt($("#zbqx_chk").val())+ apptype;
	};
	if($("#npqx_chk").attr("checked")){
		apptype = parseInt($("#npqx_chk").val())+ apptype;
	};
	$("#apptype").val(apptype);
}

