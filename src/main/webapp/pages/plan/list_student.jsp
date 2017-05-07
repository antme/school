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
				remark : $("#remark").val(),
				number : $("#number").val(),
				isVip: $("#isVip").val()
			}
		});
		
		sum();
	}
	function formatterSex(val, row){
		if(val=="f"){
			return '女性';	
		}else if(val == "m"){
			return '男性';	
		}
	}
	
	function formatterVip(val, row){
		if(val){
			return "是";
		}
		
		return "否";
	}
	

	function exportData(){
		var data = {
				name : $("#name").val(),
				parentName : $("#parentName").val(),
				mobileNumber : $("#mobileNumber").val(),
				schoolId : $("#schoo_select").combobox('getValue'),
				remark : $("#remark").val(),
				number : $("#number").val(),
				isVip: $("#isVip").val()
		}
		
		postAjaxRequest("/sch/student/number/export.do", data, function(data){		
			var ifram =  '<iframe frameborder="no" border="0" scrolling="no" style="border:0px; border:none;" src ="/download/' + data.path + '" height="0" width="0" ></iframe>';
			var y = document.createElement("div");
			y.innerHTML = ifram;
			document.body.appendChild(y);	
		});
		
	}
	
	
	function sum(){		
		var data = {
				name : $("#name").val(),
				parentName : $("#parentName").val(),
				mobileNumber : $("#mobileNumber").val(),
				schoolId : $("#schoo_select").combobox('getValue'),
				remark : $("#remark").val(),
				number : $("#number").val(),
				isVip: $("#isVip").val()
		}
		
		postAjaxRequest("/sch/student/vip/sum.do", data, function(data){
			$("#isVipCount").html(data.count);
		});
	}
	$(document).ready(function() {
		sum();
	});
	
	function formatterRemark(val, row){
		var r = "";
		if(val){
			r =  '<div style="height: 40px;  overflow: auto;  padding: 10px; ">' + val + "<div>";
		}
		
		return r;
	}
	

	

	function openRemarkEditWindow(id){
		if(id){
			if(id!="null"){
				postAjaxRequest("/sch/admin/student/plan/remark.do", {id:id}, function(data){
					if(!data.data.remark){
						data.data.remark = "";
					}
					$("#edit_form").form('load',data.data);
					$("#editwindow").window('open');
				});
				
			}
			
		}

	}

	function formatterOperation(val, row){
		
		return '<a style="margin-left:5px" onclick="openRemarkEditWindow(\'' + row.id + '\')" href="#"> 修改  </a>';
		
	}
	
	function submitRemarkInfo(){
	
		 $("#action").val("submit");
		 $('#edit_form').form('submit');
	 	
	}
	
	$(document).ready(function() {
		initFormSubmit("edit_form", "/sch/admin/student/plan/remark/update.do", "修改学生信息", function(){
			$.messager.alert('提示', '备注成功');
			$("#editwindow").window('close');
			$("#datalist").datagrid('reload');
		});
	});
	
	
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
 	

 	<br><br>
 	<span class="r-edit-label">号码:</span>
 	<input class="height24" type="number" name="number" id="number" style="width:150px;" /> 
 	
 	<span class="r-edit-label">备注:</span>
 	<input class="height24" type="text" name="remark" id="remark" /> 
 	<span class="r-edit-label width100">是否会员:</span>
			
				  <select class="width150 height26" style="font-size:12px" name="isVip" id="isVip">
				  	<option value="">所有</option>
				  	<option value="true">是</option>
				  	<option value="false" >否</option>
				  </select>
	<button class="search_btn_noWidth" onclick="search();">搜索</button>
	<button class="search_btn_noWidth" onclick="exportData();">导出</button>
</div>

<p></p>

<div style="height:30px; width:100%;">
 	<div style="float:right; margin-right:100px;">
	<span class="r-edit-label">会员总数: </span>
	<label id="isVipCount" style="color:red; margin-left:5px;"></label>
 	</div>
</div>


<table id="datalist" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/sch/student/plan/list.do"  iconCls="icon-save" sortOrder="asc" pagination="true" >
	<thead>
		<tr>
		    <th align="center" field="number" width="50" sortable="false" resizable="true" >号数</th>	
			<th align="center" field="name" width="100" sortable="false" resizable="true" >学生姓名</th>					
			<th align="center" field="sex" width="50" sortable="false" resizable="true" data-options="formatter:formatterSex">性别</th>	
			<th align="center" field="birthday" width="80" sortable="false" resizable="true" >出生日期</th>
			<th align="center" field="parentName" width="100" sortable="false" resizable="true" >家长姓名</th>	
			<th align="center" field="mobileNumber" width="100" sortable="false" resizable="true" >家长手机</th>	
			<th align="center" field="isVip" width="70" sortable="false" resizable="true" data-options="formatter:formatterVip" >会员</th>	
			<th align="center" field="studentRegDate" width="150" sortable="false" resizable="true" >学生注册时间</th>	
			<th align="center" field="createdOn" width="150" sortable="false" resizable="true" >取号时间</th>
			<th align="center" field="schoolName" width="150" sortable="false" resizable="true" >校区</th>
			<th align="center" field="remark" width="250" sortable="false" resizable="true"  data-options="formatter:formatterRemark">备注</th>
			<th align="center" data-options="field:'id',formatter:formatterOperation,width:70" >操作</th>
			
		</tr>
	</thead>
</table>



<div id="editwindow" class="easyui-window" title="修改备注" data-options="iconCls:'icon-save',modal:true, closed:true, maximizable:false, minimizable:false, draggable:false" style="width:600px;height:300px;padding:10px; top:100px;">
       
<div style="padding: 10px 60px 20px 60px">
	<form id="edit_form" method="post" novalidate>
		<div class="form-container">
			<input class="" type="hidden" name="id" />
			<input class="" type="hidden" name="action" id="action" />
		
		
			
			<div class="form_items">
				<label class="r-edit-label width100">备注:</label><textarea name="remark" cols="50" rows="5"></textarea>
			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">&nbsp;</label>
				<input class="sub_btn" type="button" value="提交"  onclick="submitRemarkInfo()" style="margin-left:10px;">
			</div>
		</div>
	</form>
	
</div>
       
</div>

