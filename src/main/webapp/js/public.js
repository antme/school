function body_height(n){	

	var height=0;
	var panel_list = $("#left").find(".easyui-panel");
	for(var i=0;i<panel_list.length;i++){
		height=height+$(panel_list[i]).find("a").length*26+40;		
	}
	var body_height = $(document).height();
	if(body_height>=height){
		return body_height-n;
	}else{
		return height;
	}
	
}
$(document).ready(function(){
	var object = $("#left").find(".title");
	object.click(function(){
		$(this).next(".l_tool").click();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	});
	$(".l_tool").click(function(){
		if($(this).find("a").hasClass('tools')){
			$(this).find("a").removeClass("tools");
			var heights = $(this).parent().next(".panel-body").find("a").length;
			heights=heights*26;
			$(this).parent().next(".panel-body").animate({height:heights+"px"});
			$(this).parent().next(".panel-body").css("height","auto");
			
		}else{
			$(this).find("a").addClass("tools");
			$(this).parent().next(".panel-body").animate({height:"0px"});
			
		}
		
	});
});
