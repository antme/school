<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	function search() {
		$('#datalist').datagrid({
			url : "/sch/admin/plan/baoming/list.do",
			queryParams : {
				schoolId : $("#schoo_select").combobox('getValue'),
				signUpDate : $("#signUpDateQuery").datebox('getValue')
			}
		});
		
		
	}
	
	

	
	function formatterOperation(val, row){
		
		return '<a style="margin-left:5px" onclick="openEditPlanWindow(\'' + row.id + '\')" href="#">修改</a>'+
		'<a style="margin-left:5px" onclick="deleteBaomingInfo(\'' + row.id + '\')" href="#">删除</a>';
		
	}

	function deleteBaomingInfo(id){
 		$.messager.confirm('确认', '确认删除此报名计划?', function(r){			
	 		if(r){
	 			postAjaxRequest("/sch/admin/plan/baoming/delete.do", {id:id}, function(data){
	 				$("#datalist").datagrid('reload');
				});
	 		}
 		});
 	}

	
	function add(){
		$('#edit_form').form('clear');
		$("#schoolId").combobox('readonly', false);
		var data = {
			"keepOnHours": 0.5,
			"skipHours": 0.5,
			"signUpCount": 30
		}
		$("#edit_form").form('load',data);
		$("#editwindow").window('open');
	}

	function openEditPlanWindow(id){
		if(id){
			
			if(id!="null"){
				postAjaxRequest("/sch/admin/plan/baoming/load.do", {id:id}, function(data){
					$("#edit_form").form('load',data.data);
					$("#schoolId").combobox('readonly', true);
					$("#editwindow").window('open');
				});
				
			}
			
			
		}


	}

	

	$(document).ready(function() {
		initFormSubmit("edit_form", "/sch/admin/plan/baoming/add.do", "提交报名计划", function(){
			$.messager.alert('提示', '提交成功');
			$("#editwindow").window('close');
			$("#datalist").datagrid('reload');
		});
		
	
		
	});
	
	
	function submitBaomingInfo(){
 		$.messager.confirm('确认', '确认提交此报名计划?', function(r){			
	 		if(r){
		 		$("#action").val("submit");
		 		$('#edit_form').form('submit');
	 		}
 		});
 	}
</script>

<div>
	<label style="font-size: 20px">报名计划管理</label>
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

 	<span class="r-edit-label">报名日期:</span>
 	<input class="easyui-datebox width300"  name="signUpDate" id="signUpDateQuery" /> 
			  
	<button class="search_btn_noWidth" onclick="search();">搜索</button>
</div>

<p></p>

<div style="height:30px;">
<button class="search_btn_noWidth" onclick="add();" style="float:right;">新增报名计划</button>
</div>
<p></p>
<table id="datalist" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/sch/admin/plan/baoming/list.do"  iconCls="icon-save" sortOrder="asc" pagination="true" >
	<thead>
		<tr>
			<th align="center" field="schoolName" width="100" sortable="false" resizable="true" >校区</th>
			<th align="center" field="signUpDate" width="80" sortable="false" resizable="true">报名日期</th>	
			<th align="center" field="startTime" width="70" sortable="false" resizable="true">开始时间</th>
			<th align="center" field="keepOnHours" width="80" sortable="false" resizable="true" >单批次报名持续时间</th>
			<th align="center" field="skipHours" width="100" sortable="false" resizable="true" >每批次报名间隔时间</th>	
			<th align="center" field=signUpCount width="70" sortable="false" resizable="true">单批次报名人数</th>	
			<th align="center" field=place width="150" sortable="false" resizable="true">报名地址</th>	
			<th align="center" data-options="field:'id',formatter:formatterOperation,width:100" >操作</th>
			
		</tr>
	</thead>
</table>





<div id="editwindow" class="easyui-window" title="修改报名批次" data-options="iconCls:'icon-save',modal:true, closed:true, maximizable:false, minimizable:false, draggable:false" style="width:800px;height:520px;padding:10px; top:100px;">
       
<div style="padding: 0px 0px 0px 60px">
	<form id="edit_form" method="post" novalidate>
		<div class="form-container">
			<input class="" type="hidden" name="id" />
			<input class="" type="hidden" name="action" id="action" />
		
			<br><br>
			<div class="form_items">
				<label class="r-edit-label width100">校区:</label> 
 				<input class="easyui-combobox"  type="text" style="width:150px; height:30px;" id="schoolId" name="schoolId" data-options="url:'/sch/admin/select.do',
                    method:'get',
                    valueField:'id',
                    textField:'name',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"/>
			</div>
				
			<div class="form_items">
				<label class="r-edit-label width100">报名日期:</label> <input required class="easyui-datebox width300" name="signUpDate" ></input>
			</div>

			<div class="form_items">
				<label class="r-edit-label width100">开始时间:</label>  <input class=" easyui-validatebox easyui-timespinner" required style="height:30px; width:200px" name="startTime" id="startTime"/>
			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">单批次报名持续时间:</label>  <input class=" easyui-validatebox" type="number"  required style="height:30px; width:200px" name="keepOnHours" id="keepOnHours"/> <label class="r-edit-label ">小时</label>
			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">每批次报名间隔时间:</label>  <input class=" easyui-validatebox" type="number"  required style="height:30px; width:200px" name="skipHours" id="skipHours"/> <label class="r-edit-label ">小时</label>
			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">单批次报名人数:</label>  <input class=" easyui-validatebox" type="number"  required style="height:30px; width:200px" name="signUpCount" id="signUpCount"/> <label class="r-edit-label ">人</label>
			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">最后批次少于:</label>  <input class=" easyui-validatebox" type="number"  required style="height:30px; width:200px" value="0" name="lastMergeSignUpCount" id="lastMergeSignUpCount"/> <label class="r-edit-label ">人合并到上批次</label>
			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">报名地址:</label><textarea name="place" cols="50" rows="5"></textarea>
			</div>
			<div class="form_items">
				<label class="r-edit-label width100">&nbsp;</label>
				<input class="sub_btn" type="button" value="提交"  onclick="submitBaomingInfo();" style="margin-left:10px;">
			</div>
		</div>
	</form>
	
</div>
       
</div>

