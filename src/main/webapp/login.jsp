<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Expires" content="0">
<meta http-equiv="kiben" content="no-cache">
<meta name="keywords" content="用户登录" />
<meta name="description" content="用户登录" />
<title>用户登录</title>
<link rel="stylesheet" type="text/css" href="/css/easyui.css">
<link rel="stylesheet" type="text/css" href="/css/public.css">
<script src="/js/jquery-1.11.1.min.js"></script>
<script src="/js/login.js"></script>
<script type="text/javascript" src="/js/jquery.easyui.min.js"></script>

<script type="text/javascript" src="/js/eweblib.js"></script>
<script type="text/javascript" src="/js/validation.js"></script>
</head>
<%
    if(session.getValue("back_login")!=null){
    	response.sendRedirect("index.jsp");     
    }
%>
<script type="text/javascript">
window.onload = function(){
	document.getElementById("userName").focus();
}

</script>


<style>
#form-login .div_input_login {
	width: 150px;
}
</style>

<body class="background-logo">
   <div class="top">
  
    </div>
	<div class="view-login login_back">
	    <div class="login_head">登录 | login</div>
		<form id="login-form" method="post" novalidate>
		    <div class="form_items">
		        <label class="left width60">用户名:</label> 
		        <input name="userName" required id="userName" class="txt easyui-validatebox width180" type="text"   missingMessage="请输入用户名" />
		    </div>
		    <div class="form_items">
		        <label class="left width60">密码:</label> 
		        <input name="password"  required id="password"   class="txt easyui-validatebox width180"  missingMessage="请输入密码"   type="password" />
		    </div>
		    <div class="form_items">
		        <label class="left width60">验证码:</label> 
				<input  name="imgCode" id="" class="txt_shot easyui-validatebox" type="text" deltaX="50" size="15" style="_height:30px;width:130px; " validType="unnormal" required missingMessage="请输入验证码"/>
                                <span><a class="verification"><img style="width:63px; height:29px; cursor: pointer; margin-top:1px;" id="randomcode" src="/user/img.do" onclick="changeImage();" ></a> </span>              
                                <script type="text/javascript">
                                    function changeImage(){
                                         $("#randomcode").attr("src", "/user/img.do?_id=" + +Math.random());
                                    }
                                </script>
			</div>
			<div class="form_items bk_color2">
			   <input class="sub_btn2" type="submit" value="登录" />
			   
		    </div>
		</form>
	</div>
	
	<noscript>警告！为正确操作管理后台，JavaScript 必须启用。</noscript>
</body>
</html>