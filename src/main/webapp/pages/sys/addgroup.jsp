<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String id = request.getParameter("id"); %>

<script type="text/javascript">
	var id = "<%=id%>";

	$(document).ready(function() {
		initFormSubmit("addRoleGroupForm", "/sys/group/add.do", "添加权限组", function(){
			alert("提交成功");
			loadRemotePage("sys/grouplist&a=5");
		});
		
		
		if(id!="null"){
			postAjaxRequest("/sys/group/get.do", {id:id}, function(data){
				
				$("#addRoleGroupForm").form('load',data.data);
				
			});
			$("#addrolegroup").text("修改信息");
		} else {
			$("#addrolegroup").text("新增角色");
		}
		
	});
</script>

<div style="padding: 10px 60px 20px 60px">
<form action="" id="addRoleGroupForm" method="post" novalidate>
	<div class="form-container">
		
		<div>
			<label id="addrolegroup" style="font-size:20px"></label>
		</div>
		<br><br>
		
		<input id="id" name="id" type="hidden" /> 
		<input  id="permissions" name="permissions" type="hidden" />
		<div class="form_items">
			<label class="r-edit-label width100"> 角色名字 :</label> <input class="ui-widget-content ui-corner-all ui-input tabs_input_css easyui-validatebox"
				id="groupName" name="groupName" size="55" required missingMessage="请输入名称" />
		</div>
		
		<div class="form_items">
			<label class="r-edit-label width100">显示顺序 :</label> <input class="easyui-validatebox textbox input-title" type="number" name="displayOrder" value="0" ></input>
			<span style="color:#aaa;font-size:12px;">数值高的将显示在管理列表后面</span>
		</div>
		<div class="form_items">
			<label for="description"  class="r-edit-label width100"> 描述 : </label>
			<textarea rows="10" cols="50" id="description" name="description" style="font-size:12px;"></textarea>
		</div>

		<div class="form_items">
			 <label class="r-edit-label width100">&nbsp;</label><input type="submit" class="sub_btn" value="提交">
		</div>
	</div>
</form>
</div>
