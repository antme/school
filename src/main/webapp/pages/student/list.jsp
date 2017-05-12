<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	function search() {

	
		$('#datalist').datagrid({
			url: "/user/admin/listStudent.do",
		    queryParams: {
				name : $("#name").val(),
				parentName : $("#parentName").val(),
				remark : $("#remark").val(),
				mobileNumber : $("#mobileNumber").val()
			}
		});

	
	}
	
	function exportData(){
		var data = {
				name : $("#name").val(),
				parentName : $("#parentName").val(),
				remark : $("#remark").val(),
				mobileNumber : $("#mobileNumber").val()
		}
		
		postAjaxRequest("/user/admin/student/export.do", data, function(data){		
			var ifram =  '<iframe frameborder="no" border="0" scrolling="no" style="border:0px; border:none;" src ="/download/' + data.path + '" height="0" width="0" ></iframe>';
			var y = document.createElement("div");
			y.innerHTML = ifram;
			document.body.appendChild(y);	
		});
		
	}
	

	function deleteStudent(id){
	   	
   		$.messager.confirm('删除数据', '确认删除此学生信息和所有的取号记录？', function(r){
    		if (r){
    			var data = {id: id};
		       	postAjaxRequest('/user/admin/student/delete.do', data, function(data) {
		       	 	$.messager.alert('提示','删除成功');
					$("#datalist").datagrid('reload');
				}); 
    		}
   		});          		
	  
   
	}
	
	function formatterOperation(val, row){
		
		return '<a style="margin-left:5px" onclick="openStudentEditWindow(\'' + row.id + '\')" href="#"> 修改  </a>' +
		 '<a style="margin-left:5px" onclick="deleteStudent(\'' + row.id + '\');" href="#"> 删除 </a>';
		
	}
	

	

	function formatterSex(val, row){
		if(val=="f"){
			return '女性';	
		}else if(val == "m"){
			return '男性';	
		}
	}
	

	function formatterRemark(val, row){
		return '<div style="height: 40px; width: 220px; overflow: auto;  padding: 10px; ">' + val + "<div>";
	}
	

	function openStudentEditWindow(id){
		if(id){
			
			
			if(id!="null"){
				postAjaxRequest("/user/admin/student/load.do", {id:id}, function(data){
					if(!data.data.remark){
						data.data.remark = "";
					}
					$("#edit_form").form('load',data.data);
					$("#editwindow").window('open');
				});
				
			}
			
			
		}


	}

	
 	function submitStudentInfo(){
 		$.messager.confirm('修改确认', '确认修改此学生信息！', function(r){			
	 		if(r){
		 		$("#action").val("submit");
		 		$('#edit_form').form('submit');
	 		}
 		});
 	}
 	

	$(document).ready(function() {
		initFormSubmit("edit_form", "/user/admin/student/update.do", "修改学生信息", function(){
			$.messager.alert('提示', '修改成功');
			$("#editwindow").window('close');
			$("#datalist").datagrid('reload');
		});
		
		
		postAjaxRequest("/user/admin/user/sum.do", {}, function(data){
			$("#parentCount").html(data.parentCount);
			$("#studentCount").html(data.studentCount);
		});
		
	});
	
</script>

<div>
	<label style="font-size:20px">学生信息管理</label>
</div>
<br><br>
<div >
	<span class="r-edit-label">学生姓名:</span>
	<input class="height24" type="text" name="name" id="name" /> 
	
	<span class="r-edit-label">家长姓名:</span>
 	<input class="height24" type="text" name="parentName" id="parentName" /> 
 	
 	<span class="r-edit-label">家长手机号:</span>
 	<input class="height24" type="text" name="mobileNumber" id="mobileNumber" /> 
 	
 	<span class="r-edit-label">备注:</span>
 	<input class="height24" type="text" name="remark" id="remark" /> 
	<button class="search_btn_noWidth" onclick="search();">搜索</button>
	<button class="search_btn_noWidth" onclick="exportData();">导出</button>
</div>
<p></p>

<div style="height:20px; width:100%;">
 	<div style="float:right; margin-right:100px;">
	<span class="r-edit-label">家长总数: </span>
	<label id="parentCount" style="color:red; margin-left:5px;"></label>
	<span class="r-edit-label" style="margin-left:20px;">学生总数: </span>
 	<label id="studentCount" style="color:red; margin-left:5px;"></label>
 	</div>
</div>

<p></p>
<table id="datalist" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/user/admin/listStudent.do"  iconCls="icon-save" sortOrder="asc" pagination="true" >
	<thead>
		<tr>
			<th align="center" field="name" width="70" sortable="false" resizable="true" >姓名</th>					
			<th align="center" field="sex" width="50" sortable="false" resizable="true" data-options="formatter:formatterSex">性别</th>	
			<th align="center" field="birthday" width="80" sortable="false" resizable="true" >出生日期</th>
			<th align="center" field="parentName" width="70" sortable="false" resizable="true" >家长姓名</th>	
			<th align="center" field="parentMobileNumber" width="80" sortable="false" resizable="true" >家长手机</th>	
			<th align="center" field="parentCreatedOn" width="130" sortable="false" resizable="true" >家长注册时间</th>	
			<th align="center" field="createdOn" width="130" sortable="false" resizable="true" >学生注册时间</th>	
			<th align="center" field="remark" width="250" sortable="false" resizable="true"  data-options="formatter:formatterRemark">备注</th>	
		<th align="center" data-options="field:'id',formatter:formatterOperation,width:100" >操作</th>
		</tr>
	</thead>
</table>


<div id="editwindow" class="easyui-window" title="修改学生信息" data-options="iconCls:'icon-save',modal:true, closed:true, maximizable:false, minimizable:false, draggable:false" style="width:650px; height:500px; padding:10px; top:100px;">
       
<div style="padding: 10px 60px 20px 60px">
	<form id="edit_form" method="post" novalidate>
		<div class="form-container">
			<input class="" type="hidden" name="id" />
			<input class="" type="hidden" name="action" id="action" />
		
			<br><br>
				
			<div class="form_items">
				<label class="r-edit-label width100">姓名:</label> <input class="easyui-validatebox textbox width300" type="text" name="name" ></input>
			</div>

			<div class="form_items">
				<label class="r-edit-label width100">出生日期(YYYY-MM):</label> 
				 <input class="easyui-validatebox textbox width300"  style="height:30px; width:200px" name="birthday" id="birthday"/>
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
				<label class="r-edit-label width100">备注:</label><textarea name="remark" cols="50" rows="5"></textarea>
			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">&nbsp;</label>
				<input class="sub_btn" type="button" value="提交"  onclick="submitStudentInfo()" style="margin-left:10px;">
			</div>
		</div>
	</form>
	
</div>
       
</div>

