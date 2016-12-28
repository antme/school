<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="com.wx.school.service.impl.UserServiceImpl"%>
<%@ page import="com.wx.school.service.IUserService"%>
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
</head>

<%
	WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletConfig().getServletContext());
	IUserService us = (UserServiceImpl)ctx.getBean("userService");

	if (request.getParameter("skey") == null) {
		String skey = request.getParameter("skey");
	}
%>

<body >

</body>
</html>