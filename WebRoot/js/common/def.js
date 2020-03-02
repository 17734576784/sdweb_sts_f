var def = {
	basePath : basePath,				//
	point    : 2	//小数位数
}
$(document).ready(function() {
	$(document.body).keyup(function(){
		if(window.event.keyCode == 27){	//ESC
			var tmp = typeof window.top.close_frame;
			if(tmp == "function"){
				window.top.close_frame();
			}else{
				window.close();
			}
		}
	});
});
