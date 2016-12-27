<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	function search() {
		$('#datalist').datagrid({
			url : "/sch/admin/list.do",
			queryParams : {
				name : $("#name").val(),
				parentName : $("#parentName").val(),
				mobileNumber : $("#mobileNumber").val()
			}
		});
	}

	function deleteSchool(id) {
		$.messager.confirm('删除数据', '确认删除此学生信息和所有的取号记录？', function(r) {
			if (r) {
				var data = {
					id : id
				};
				postAjaxRequest('/sch/admin/delete.do', data, function(data) {
					$.messager.alert('提示', '删除成功');
					$("#datalist").datagrid('reload');
				});
			}
		});
	}

	function formatterOperation(val, row) {
		return '<a style="margin-left:5px" href="?p=school/edit&id=' + row.id
				+ '"> 修改 </a>'
				+ '<a style="margin-left:5px" onclick="deleteSchool(\''
				+ row.id + '\');" href="#"> 删除 </a>';

	}

	function formatterTakeStatus(val, row) {
		if (val == "0") {
			return "已结束";
		} else if (val == "1") {
			return "进行中";
		} else {
			return "未开始";
		}

	}
	
	function formatterDeliveryStatus(val, row){
		if(val){
			return "已发布";
		}
		return "未发布";
		
	}
	

	function add(id) {
		$.messager.confirm('添加数据', '确认添加此校区？', function(r) {
			if (r) {
				var data = {
					id : id
				};
				postAjaxRequest('/sch/admin/delete.do', data, function(data) {
					$.messager.alert('提示', '删除成功');
					$("#datalist").datagrid('reload');
				});
			}
		});
	}
	
</script>

<div>
	<label style="font-size: 20px">校区信息管理</label>
</div>
<br>
<br>
<div>
	<span class="r-edit-label">校区名字:</span> <input class="height24"
		type="text" name="name" id="name" />
	<button class="search_btn_noWidth" onclick="add();">新增</button>
	<button class="search_btn_noWidth" onclick="search();">搜索</button>
</div>
<p></p>
<table id="datalist" class="easyui-datagrid"
	data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true"
	url="/sch/admin/list.do" iconCls="icon-save" sortOrder="asc"
	pagination="true">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th align="center" field="name" width="100" sortable="false"
				resizable="true">校区</th>
			<th align="center" field="startDateTime" width="150" sortable="false"
				resizable="true">开始时间</th>
			<th align="center" field="endDateTime" width="150" sortable="false"
				resizable="true">结束时间</th>
			<th align="center" field="takeStatus" width="100" sortable="false"
				resizable="true" data-options="formatter:formatterTakeStatus">状态</th>
			<th align="center" field="isDisplayForWx" width="100"
				sortable="false" resizable="true" data-options="formatter:formatterDeliveryStatus">发布状态</th>
			<th align="center"
				data-options="field:'id',formatter:formatterOperation,width:100">操作</th>
		</tr>
	</thead>
</table>


