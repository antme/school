<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	function search() {
		$('#datalist').datagrid({
			url : "/sch/student/plan/list.do",
			queryParams : {
				name : $("#name").val(),
				parentName : $("#parentName").val(),
				mobileNumber : $("#mobileNumber").val(),
				schoolId : $("#schoo_select").combobox('getValue'),
				number : $("#number").val()
			}
		});
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
	<label style="font-size: 20px">取号信息查询</label>
</div>
<br>
<br>
<div >

 	<span class="r-edit-label">校区:</span>
 	 <input class="easyui-combobox"  type="text" style="width:150px; height:30px;" id="schoo_select" data-options="url:'/sch/admin/select.do',
                    method:'get',
                    valueField:'id',
                    textField:'name',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"/>
					
	<span class="r-edit-label">学生姓名:</span>
	<input class="height24" type="text" name="name" id="name" /> 
	
	<span class="r-edit-label">家长姓名:</span>
 	<input class="height24" type="text" name="parentName" id="parentName" /> 
 	
 	<span class="r-edit-label">家长手机号:</span>
 	<input class="height24" type="text" name="mobileNumber" id="mobileNumber" /> 
 	

 	
 	<span class="r-edit-label">号码:</span>
 	<input class="height24" type="number" name="number" id="number" style="width:50px;" /> 
 	
	<button class="search_btn_noWidth" onclick="search();">搜索</button>
	<button class="search_btn_noWidth" onclick="search();">导出</button>
</div>

<p></p>

<table id="datalist" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/sch/student/plan/list.do"  iconCls="icon-save" sortOrder="asc" pagination="true" >
	<thead>
		<tr>
		    <th data-options="field:'ck',checkbox:true"></th>
		    <th align="center" field="number" width="100" sortable="false" resizable="true" >号数</th>	
			<th align="center" field="name" width="100" sortable="false" resizable="true" >学生姓名</th>					
			<th align="center" field="sex" width="50" sortable="false" resizable="true" data-options="formatter:formatterSex">性别</th>	
			<th align="center" field="birthday" width="80" sortable="false" resizable="true" >出生日期</th>
			<th align="center" field="parentName" width="100" sortable="false" resizable="true" >家长姓名</th>	
			<th align="center" field="mobileNumber" width="100" sortable="false" resizable="true" >家长手机</th>	
			<th align="center" field="studentRegDate" width="150" sortable="false" resizable="true" >学生注册时间</th>	
			<th align="center" field="createdOn" width="150" sortable="false" resizable="true" >取号时间</th>
			<th align="center" field="schoolName" width="150" sortable="false" resizable="true" >校区</th>
		</tr>
	</thead>
</table>

