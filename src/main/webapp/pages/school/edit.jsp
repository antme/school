<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String id = request.getParameter("id"); %>

<script type="text/javascript">
	var id = "<%=id%>";

	$(document).ready(function() {
		initFormSubmit("edit_form", "/user/admin/student/update.do", "修改学生信息", function(){
			alert("提交成功");
			loadRemotePage("student/list");
		});
		
		if(id!="null"){
			postAjaxRequest("/user/admin/student/load.do", {id:id}, function(data){
				$("#edit_form").form('load',data.data);
			});
			
		}
	});

 	
 	function submitStudentInfo(){
 		$.messager.confirm('修改确认', '确认修改此学生信息！', function(r){			
	 		if(r){
		 		$("#action").val("submit");
		 		$('#edit_form').form('submit');
	 		}
 		});
 	}

	
</script>

<div style="padding: 10px 60px 20px 60px">
	<form id="edit_form" method="post" novalidate>
		<div class="form-container">
			<input class="" type="hidden" name="id" />
			<input class="" type="hidden" name="action" id="action" />
			
			<div>
				<label style="font-size:20px">学生信息修改</label>
			</div>
			<br><br>
				
			<div class="form_items">
				<label class="r-edit-label width100">姓名:</label> <input class="easyui-validatebox textbox width300" type="text" name="name" ></input>
			</div>

			<div class="form_items">
				<label class="r-edit-label width100">出生日期:</label>  <input class="easyui-datebox" style="height:30px; width:200px" name="birthday" id="birthday"/>
			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">性别:</label>
			
				  <select class="width150 height26" style="font-size:12px" name="sex">
				  	<option value="m" selected>男性</option>
				  	<option value="f" >女性</option>
				  </select>
			  </input>
			</div>

		
			
			<div class="form_items">
				<label class="r-edit-label width100">&nbsp;</label>
				<input class="sub_btn" type="button" value="提交"  onclick="submitStudentInfo()" style="margin-left:10px;">
				<input class="sub_btn" type="button" value="取消"  onclick="loadRemotePage('student/list');" style="margin-left:10px;">
			</div>
		</div>
	</form>
	
</div>
