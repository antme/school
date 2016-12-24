<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div style="padding: 10px 60px 20px 60px">
	<form id="userform" method="post" novalidate>
		<div class="form-container">
			<div>
				<label id="infolabel" style="font-size: 20px">用户设置</label>
			</div>
			<input type="hidden" name="id" id="id" />
			<p>
			
			<div class="form_items" >
				<label class="r-edit-label width100" style="margin-top:20px;">账号</label> 
				<div style="float:left; margin-left:10px;margin-top:20px"><input class="easyui-validatebox textbox width300" type="text" name="userName" id="userName" disabled="disabled"></input></div>
		                
			</div>
			<div class="form_items" >
				<label class="r-edit-label width100" style="margin-top:20px;">名字</label> 
				<div style="float:left; margin-left:10px;margin-top:20px"><input class="easyui-validatebox textbox width300" type="text" name="name" id="name" disabled="disabled"></input></div>
		                
			</div>
			<div class="form_items" style="height:100px;">
				<label class="r-edit-label width100" style="margin-top:20px;">分成比率</label> 
				<div style="float:left; margin-left:10px;margin-top:20px"><input type="hidden" id="shareProportion" name="shareProportion" class="easyui-slider" style="width:300px" data-options="
		                showTip:true,
		                value:50,
		                max:100,
		                tipFormatter:function(value){return value+'%';},
		                rule: [0,'|',50,'|',100]"></div>
		                
			</div>
		

			
			<div class="form_items">

				<label class="r-edit-label width100">&nbsp;</label> <input class="sub_btn" type="submit" value="提交"></a>
			</div>
		</div>
	</form>
</div>
<% String id = request.getParameter("id"); %>

<script type="text/javascript">
	var id = "<%=id%>";
	$(document).ready(function() {
		initFormSubmit("userform", "/user/admin/edit.do", "用户编辑", function(){
			alert("提交成功");
			loadRemotePage("admin/user/list");
		});
	
		if(id!="null"){
			postAjaxRequest("/user/admin/load.do", {id:id}, function(data){
		
				$("#userform").form('load',data.data);	
				$("#id").val(data.data.id);
	
			});
		}
	
	});

</script>