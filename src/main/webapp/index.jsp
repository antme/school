<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="com.apads.web.service.impl.UserServiceImpl"%>
<%@ page import="com.apads.web.service.IUserService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.eweblib.cfg.ConfigManager" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>

<%@ page import="org.springframework.web.context.WebApplicationContext" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta name="keywords" content="首页" />
<meta name="description" content="首页" />
<meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' />

<title>首页</title>
<link rel="stylesheet" type="text/css" href="/resources/css/easyui.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/icon.css">
<link rel="stylesheet" type="text/css" href="/resources/css/public.css">

		<script type="text/javascript" src="/resources/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="/resources/js/eweblib.js"></script>
		<script type="text/javascript" src="/resources/js/json2.js"></script>
		<script type="text/javascript" src="/resources/js/ap.js"></script>
		<script type="text/javascript" src="/resources/js/validation.js"></script>
		<script type="text/javascript" src="/resources/js/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="/resources/js/highcharts.js"></script>
		<script type="text/javascript" src="/resources/js/public.js"></script>	
</head>

<%
	if (session.getAttribute("userName") == null) {
		String url = request.getServerName();
		response.sendRedirect("/login.jsp");
	}
%>

<%
	String userRoleName = null;
	if (session.getAttribute("roleName") != null) {
		userRoleName = session.getAttribute("roleName").toString();
	}
	
	WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletConfig().getServletContext());
	IUserService us = (UserServiceImpl)ctx.getBean("userService");

%>

<body onResize="resizeTabAndGrid();">

	<%@ include file="pages/head.jsp"%>


	<div id="left1" class="left1" style="display:none;" >
		
			
	</div>

	<div id="right" class="right">
		<div id="content-right-info" style="color: red; font-size: 18px; font-weight: bold;"></div>
		<div id="content-right" style="margin-top: 5px; height: auto; overflow: hidden">
					<% 
		               String pagePath = request.getParameter("p"); 
		               String accindex = request.getParameter("a"); 
		               if(pagePath == null){	
		                    if(us.isCloudAdmin()){
								pagePath = "main";
							 }else{
		                        pagePath = "admin/ADReport/apreport";	
		                     }                   
		               }
		               if(pagePath != null && pagePath!="null" && pagePath!=""){
		                   if(pagePath!="admin/ADReport/report" && !us.isCloudAdmin()){
		                       pagePath = "admin/ADReport/apreport";	
		                   }
		                   pageContext.setAttribute("pagePath","pages/"+pagePath+".jsp");                           		          
		               }
		            %>
		           	<jsp:include page="${pagePath}" />	
		</div>
	</div>
	<%@ include file="pages/bottom.jsp"%>
	<div style="display: none;">
		<div id="detailWindow">
			<span id="detailspan" height="200" style="margin-top: 20px;"></span>
		</div>
	</div>
	 <div id="dlg"  title="详情" data-options="iconCls:'icon-save'" style="width:500px;height:250px;padding:10px;">
     </div>
	<script type="text/javascript">
        var roleName = "<%=userRoleName%>";
        var pagePath = "<%=pagePath%>";
        var hi=body_height(70);
        $("#right").css("min-height",hi+"px");
     </script>
</body>
</html>