<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<meta charset="utf-8" />

<script type="text/javascript" src="/util.js"></script>
<script type="text/javascript" src="/resources/js/eweblib.js"></script>
<script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
<style>
#html{float:left;width:500px;height:auto;border-top:1px solid #cdcdcd;border-left:1px solid #cdcdcd;margin-right:10px;}
#html div{line-height:30px;border-right:1px solid #cdcdcd;border-bottom:1px solid #cdcdcd;overflow:hidden;}
#html span{float:left;width:115px;display:inline-block;padding-left:10px;}
#html lable{float:left; display:inline-block;width:350px;border-left:1px solid #cdcdcd;padding-left:10px;}
#campaignView{float:left;}

#views{float:left;margin-left:20;width:382px; position:relative;}
#views img{position:absolute;z-index:998;}
#views iframe{position:absolute;z-index:999;top:119px;left:33px;}
</style>
<script type="text/javascript">
$(function(){
	var campaignId = getQueryString("campaignId");
	var materialId = getQueryString("materialId");
	if(campaignId && campaignId!=""){
		$("#campaignView").attr("src",'/pages/ad/adp.jsp?campaignId='+campaignId);
		
	}
	else if(materialId && materialId!=""){
		$("#campaignView").attr("src",'/pages/ad/adp.jsp?materialId='+materialId);
		
	}
});


</script>
</head>
<body>
   <div id="html">
       
   </div>
   <div id="views">
        <img id="back-one" width="100%" height="400" src="/resources/images/iPhone.jpg" />
        <iframe id="campaignView" width="320" height="50" scrolling="no" marginheight="0px" marginwidth="0px" frameborder="0" src=""></iframe>
        <img id="back-two" width="100%" src="/resources/images/iPhone.jpg" />
   </div>
   
</body>
</html>
