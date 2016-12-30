<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	function search() {
		$('#datalist').datagrid({
			url : "/sch/admin/plan/list.do",
			queryParams : {
				name : $("#name").val(),
				parentName : $("#parentName").val(),
				mobileNumber : $("#mobileNumber").val()
			}
		});
	}

	function deleteSchoolPlan(id) {
		$.messager.confirm('删除数据', '确认删除此取号批次？此批次下所有选号记录也将删除', function(r) {
			if (r) {
				var data = {
					id : id
				};
				postAjaxRequest('/sch/admin/plan/delete.do', data, function(data) {
					$.messager.alert('提示', '删除成功');
					$("#datalist").datagrid('reload');
				});
			}
		});
	}
	
	function deliverySchoolPlan(id){
		$.messager.confirm('删除数据', '确认发布此取号批次？', function(r) {
			if (r) {
				var data = {
					id : id
				};
				postAjaxRequest('/sch/admin/plan/delivery.do', data, function(data) {
					$.messager.alert('提示', '发布成功');
					$("#datalist").datagrid('reload');
				});
			}
		});
	}
	
	function cancelSchoolPlan(id){
		$.messager.confirm('删除数据', '确认撤销此取号批次', function(r) {
			if (r) {
				var data = {
					id : id
				};
				postAjaxRequest('/sch/admin/plan/cancel.do', data, function(data) {
					$.messager.alert('提示', '撤销成功');
					$("#datalist").datagrid('reload');
				});
			}
		});
	}

	function formatterOperation(val, row) {
		var a =  '<a style="margin-left:5px" onclick="openEditWindow(\'' + row.id + '\')" href="#"> 修改 </a>'
				+ '<a style="margin-left:5px" onclick="deleteSchoolPlan(\''
				+ row.id + '\');" href="#"> 删除 </a>';
				
		if(row.isDisplayForWx){
			a += '<a style="margin-left:5px" onclick="cancelSchoolPlan(\''
					+ row.id + '\');" href="#"> 撤销 </a>';
		}else{
			a += '<a style="margin-left:5px" onclick="deliverySchoolPlan(\''
					+ row.id + '\');" href="#"> 发布 </a>';
		}
		
		return a;

	}

	function formatterTakeStatus(val, row) {
		if (val == "0") {
			return "未开始";
		} else if (val == "1") {
			return "进行中";
		} else {
			return "已结束";
		}

	}
	
	function formatterDeliveryStatus(val, row){
		if(val){
			return "已发布";
		}
		return "未发布";
		
	}
	

	function add() {
		$.messager.confirm('添加数据', '确认添加此校区？', function(r) {
			if (r) {
				var data = {
					name : $("#name").val()
				};
				postAjaxRequest('/sch/admin/add.do', data, function(data) {
					$.messager.alert('提示', '添加成功');
					$("#schoo_select").combobox('reload');
				});
			}
		});
	}
	
	function submitPlan(){
		var schoolId = $("#schoo_select").combobox('getValue');
		var takeDate = $("#takeDate").datebox('getValue');
		var startTime = $("#startTime").timespinner('getValue');
		var endTime = $("#endTime").timespinner('getValue');
		
		var can_submit = true;
		if(!schoolId){
			$.messager.alert('提示', '请选择校区');
			can_submit = false;
		}
		
		else if(!takeDate){
			$.messager.alert('提示', '请选择取号日期');
			can_submit = false;
		}
		
		else if(!startTime){
			$.messager.alert('提示', '请选择取号开始时间');
			can_submit = false;
		}
		
		else if(!endTime){
			$.messager.alert('提示', '请选择取号结束时间');
			can_submit = false;
		}
		
		var data = {
				takeNumberDate: takeDate,
				startTime: startTime,
				endTime: endTime,
				schoolId: schoolId
		};
		
		if(can_submit){
			$.messager.confirm('添加取号', '确认添加新取号批次？', function(r) {
				if (r) {
				
					postAjaxRequest('/sch/admin/plan/add.do', data, function(data) {
						$.messager.alert('提示', '添加成功');
						$("#datalist").datagrid('reload');
					});
				}
			});		
		}
		
	}
	
	

	function openEditWindow(id){
		if(id){	
			if(id!="null"){
				postAjaxRequest("/sch/admin/plan/load.do", {id:id}, function(data){
					$("#edit_form").form('load',data.data);
					$("#editwindow").window('open');
				});
				
			}
	
		}
	}
	
	
 	function submitStudentInfo(){
 		$.messager.confirm('修改确认', '确认修改此批次信息?', function(r){			
	 		if(r){
		 		$("#action").val("submit");
		 		$('#edit_form').form('submit');
	 		}
 		});
 	}

	$(document).ready(function() {
		
		initFormSubmit("edit_form", "/sch/admin/plan/update.do", "修改取号批次", function(){
			$.messager.alert('提示', '修改成功');
			$("#editwindow").window('close');
			$("#datalist").datagrid('reload');
		});
		
	});
	
</script>

<div>
	<label style="font-size: 20px">校区报名批次管理</label>
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

<div>
	      <span class="tab-m">
           <label>选择校区: </label>
           <input class="easyui-combobox"  type="text" style="width:200px; height:30px;" id="schoo_select" data-options="url:'/sch/admin/select.do',
                    method:'get',
                    valueField:'id',
                    textField:'name',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"/>
        </span>
        <span class="tab-m">
           	<label>取号日期: </label>
            <input class="easyui-datebox" label="Start Date:" labelPosition="top" style="width:100%; height:30px;" id="takeDate"/>
         </span>    
         <span class="tab-m">
            <input class="easyui-timespinner" label="Start Time:" labelPosition="top" value="09:00" style="width:100%; height:30px;" id="startTime"/>
            <label> - </label>
	        <input class="easyui-timespinner" label="End Time:" labelPosition="top" value="18:00" style="width:100%; height:30px;" id="endTime"/>
        </span>
        <button class="search_btn_noWidth" onclick="submitPlan();">提交</button>
</div>


<p></p>
<table id="datalist" class="easyui-datagrid"
	data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true"
	url="/sch/admin/plan/list.do" iconCls="icon-save" sortOrder="asc"
	pagination="true">
	<thead>
		<tr>
			<th data-options="field:'ck',checkbox:true"></th>
			<th align="center" field="name" width="150" sortable="false"
				resizable="true">校区</th>
			<th align="center" field="takeNumberDate" width="120" sortable="false"
				resizable="true">取号日期</th>	
			<th align="center" field="startTime" width="100" sortable="false"
				resizable="true">开始时间</th>
			<th align="center" field="endTime" width="100" sortable="false"
				resizable="true">结束时间</th>
			<th align="center" field="takeStatus" width="100" sortable="false"
				resizable="true" data-options="formatter:formatterTakeStatus">状态</th>
			<th align="center" field="isDisplayForWx" width="100"
				sortable="false" resizable="true" data-options="formatter:formatterDeliveryStatus">发布状态</th>
			<th align="center"
				data-options="field:'id',formatter:formatterOperation,width:120">操作</th>
		</tr>
	</thead>
</table>





<div id="editwindow" class="easyui-window" title="修改取号批次" data-options="iconCls:'icon-save',modal:true, closed:true, maximizable:false, minimizable:false, draggable:false" style="width:600px;height:400px;padding:10px; top:100px;">
       
<div style="padding: 10px 60px 20px 60px">
	<form id="edit_form" method="post" novalidate>
		<div class="form-container">
			<input class="" type="hidden" name="id" />
			<input class="" type="hidden" name="action" id="action" />
		
			<br><br>
			<div class="form_items">
				<label class="r-edit-label width100">校区:</label> <input required class="easyui-validatebox textbox width300" type="text" name="name" disabled ></input>
			</div>
				
			<div class="form_items">
				<label class="r-edit-label width100">取号日期:</label> <input required class="easyui-datebox width300" name="takeNumberDate" ></input>
			</div>

			<div class="form_items">
				<label class="r-edit-label width100">开始时间:</label>  <input class="easyui-timespinner easyui-validatebox" required style="height:30px; width:200px" name="startTime" id="startTime"/>
			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">结束时间:</label>  <input class="easyui-timespinner easyui-validatebox"  required style="height:30px; width:200px" name="endTime" id="startTime"/>
			</div>
		
			<div class="form_items">
				<label class="r-edit-label width100">&nbsp;</label>
				<input class="sub_btn" type="button" value="提交"  onclick="submitStudentInfo()" style="margin-left:10px;">
			</div>
		</div>
	</form>
	
</div>
       
</div>

