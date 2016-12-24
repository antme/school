<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<meta charset="utf-8" />

<script type="text/javascript" src="/util.js"></script>
<script type="text/javascript">var ydcp_id = ''; var yd_preview=true;</script>
<script type="text/javascript" src="/ydadjs_m.js"></script>

<script type="text/javascript">

var campaignId = getQueryString("campaignId");
var materialId = getQueryString("materialId");

if(campaignId){
	document.write("<script src=\"/delivery/preview.do?ydcp_id=" + ydcp_id + "&campaignId=" + campaignId + "\" type=\"text/javascript\"><\/script>");
}

if(materialId){
	document.write("<script src=\"/delivery/preview.do?ydcp_id=" + ydcp_id + "&materialId=" + materialId + "\" type=\"text/javascript\"><\/script>");
}
</script>
</head>
<body>
	<script type=""></script>
   
</body>
</html>
