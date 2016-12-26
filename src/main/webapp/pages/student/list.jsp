<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	function search() {

		$('#datalist').datagrid({
			url: "/user/admin/listStudent.do",
		    queryParams: {
				name : $("#name").val(),
				parentName : $("#parentName").val(),
				mobileNumber : $("#mobileNumber").val()
			}
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
		
		return '<a style="margin-left:5px" href="?p=/student/edit&id=' + row.id + '"> 编辑 </a>' +
		 '<a style="margin-left:5px" onclick="deleteStudent(\'' + row.id + '\');" href="#"> 删除 </a>';
		
	}
	

	

	function formatterSex(val, row){
		if(val=="f"){
			return '女性';	
		}else if(val == "m"){
			return '男性';	
		}
	}
	

	


	
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
	<button class="search_btn_noWidth" onclick="search();">搜索</button>
	<button class="search_btn_noWidth" onclick="search();">导出</button>
</div>
<p></p>
<table id="datalist" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/user/admin/listStudent.do"  iconCls="icon-save" sortOrder="asc" pagination="true" >
	<thead>
		<tr>
		    <th data-options="field:'ck',checkbox:true"></th>
			<th align="center" field="name" width="100" sortable="false" resizable="true" >姓名</th>					
			<th align="center" field="sex" width="50" sortable="false" resizable="true" data-options="formatter:formatterSex">性别</th>	
			<th align="center" field="birthday" width="80" sortable="false" resizable="true" >出生日期</th>
			<th align="center" field="parentName" width="100" sortable="false" resizable="true" >家长姓名</th>	
			<th align="center" field="parentMobileNumber" width="100" sortable="false" resizable="true" >家长手机</th>	
			<th align="center" field="parentCreatedOn" width="150" sortable="false" resizable="true" >家长注册时间</th>	
			<th align="center" field="createdOn" width="150" sortable="false" resizable="true" >学生注册时间</th>	
		<th align="center" data-options="field:'id',formatter:formatterOperation,width:100" >操作</th>
		</tr>
	</thead>
</table>


