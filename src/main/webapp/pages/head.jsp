<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<style>
	.sys-sta {
		font-size:15px;
		color: red;
	}

</style>
	<div id="head" class="head">
	    <div class="titles">赢达网络科技</div>
         <div class="user_info">
             <label>欢迎: <% out.print(session.getAttribute("userName")); %></label>&nbsp;&nbsp;&nbsp;
             <a href="/index.jsp" title="返回首页"><img style="border:0px;" src="/resources/images/title_icon_home.png">&nbsp;<b>返回首页</b></a>&nbsp;&nbsp;&nbsp;
             <a href="?p=user/changepwd" title="修改密码"><img style="border:0px;" src="/resources/images/title_icon_help.png">&nbsp;<b>修改密码</b></a>&nbsp;&nbsp;&nbsp;
             <a href="/user/logout.do" title="退出系统"><img style="border:0px;" src="/resources/images/title_icon_quit.png">&nbsp;<b>退出系统</b></a>
             <a href="?p=sys/help" title="系统帮助" style="display:none;"><img style="border:0px;" height="20" width="20" src="/resources/images/Help.png">&nbsp;<b>系统帮助</b></a>
         </div>
	</div>
