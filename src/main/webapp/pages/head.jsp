<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<style>
	.sys-sta {
		font-size:15px;
		color: red;
	}

</style>
	<div id="head" class="head">
	    <div class="titles">百花取号系统</div>
         <div class="user_info">
             <label>欢迎: <% out.print(session.getAttribute("userName")); %></label>&nbsp;&nbsp;&nbsp;
             <a href="/index.jsp" title="返回首页"><img style="border:0px;" src="/images/title_icon_home.png">&nbsp;<b>返回首页</b></a>&nbsp;&nbsp;&nbsp;
             <a href="?p=user/changepwd" title="修改密码"><img style="border:0px;" src="/images/title_icon_help.png">&nbsp;<b>修改密码</b></a>&nbsp;&nbsp;&nbsp;
             <a href="/user/logout.do" title="退出系统"><img style="border:0px;" src="/images/title_icon_quit.png">&nbsp;<b>退出系统</b></a>
             <a href="?p=sys/help" title="系统帮助" style="display:none;"><img style="border:0px;" height="20" width="20" src="/images/Help.png">&nbsp;<b>系统帮助</b></a>
         </div>
	
<div style="float:left; margin-left:50px;">

	<div style="margin-left:20px; float:left; margin-top:10px;">
		<a href="index.jsp?p=student/list"><img src="/images/income_report.png" height="50" width="50"></img><br>
		<span style="margin-left: 10px;">学生信息管理</span></a>
	</div>

	
	<div style="float:left;margin-left:20px; margin-top:10px;">
		<a href="index.jsp?p=school/list"><img src="/images/import.png"height="50" width="50"></img><br>
		<span style="margin-left: 5px;">报名批次管理</span></a>
	</div>
	
	<div style="float:left;margin-left:20px; margin-top:10px;">
		<a href="index.jsp?p=admin/ADReport/importapp"><img src="/images/import.png" height="50" width="50"></img><br>
		<span style="margin-left: 5px;">短信消息管理</span></a>
	</div>
			
	<div style="float:left;margin-left:20px; margin-top:10px;">
		<a href="index.jsp?p=admin/code/list"><img src="/images/space_setting.png" height="50" width="50"></img><br>
		<span style="margin-left: 5px;">报表</span></a>
	</div>
	

	
	<div style="float:left;margin-left:20px; margin-top:10px;">
		<a href="index.jsp?p=admin/sys/setting"><img src="/images/sys.png" height="50" width="50"></img><br>
		<span style="margin-left: 5px;">系统设置</span></a>
	</div>


</div>

</div>