<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	$(document).ready(function() {
		initFormSubmit("changepwd", "/user/password/update.do", "修改密码", function(){
			alert("修改成功");
		});
	
	});
</script>
<div style="padding: 10px 60px 20px 60px">
	<form id="changepwd" method="post" novalidate>
		<div class="form-container">

			<div>
				<label style="font-size:20px">修改密码</label>
			</div>
			<br><br>
			
			<div class="form_items">
				<label class="r-edit-label width150">新密码：</label> <input class="easyui-validatebox textbox width300" name="password" id="password" required missmessage="请输入新密码" autocomplete="off" onfocus="this.type='password'" 
					type="password" validType="pwd" /> <span class="get_span"></span>
			</div>
			<div class="form_items">
				<label class="r-edit-label width150">确认密码：</label> <input class="easyui-validatebox textbox width300" name="password2" autocomplete="off" required  missmessage="请输入新密码确认"  onfocus="this.type='password'"
					type="password" validType="pwdEquals['#password']" /> <span class="get_span"></span>
			</div>
			
			<div  class="form_items">
			    <label class="r-edit-label width150">&nbsp;</label>
				<input class="sub_btn" type="submit" value="提交" />
			</div>
		</div>
	</form>
	</div>