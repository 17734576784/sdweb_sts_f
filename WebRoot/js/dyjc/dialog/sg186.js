function showSG186Detail(){
	modalDialog.height = 300;
	modalDialog.width = 350;
	modalDialog.url = def.basePath + "jsp/dyjc/dialog/sg186Detail.jsp";
	modalDialog.param = {"rtuId":$("#rtu_id").val() ,"mpId":$("#mp_id").val() ,"yhbh":$("#userno").html()};
	modalDialog.show();

}	
