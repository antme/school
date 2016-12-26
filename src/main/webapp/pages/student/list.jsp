<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	function search() {
		var billStatus = 0;		
		var queryDate = $("#queryDate").combobox('getValue');;
		if ($("#billStatus").length > 0) {
			billStatus = $("#billStatus").combobox('getValue');
		}
	
		
		$('#billList').datagrid({
			
			url: "/bill/admin/listnew.do",
		    queryParams: {
				userName : $("#userName").val(),
				billStatus : billStatus,
				queryDate : queryDate
			}
		});
		
		getSearchStr();
		
	}
	
	function submiteBillData(){        
	   	var ids = getGridCheckedIds("billList", "billStatus", '0');	
	   	if(ids.length == 0){
	   		 $.messager.alert('提示','请在列表勾选状态为待确认的渠道');
	   	}else{
	   		$.messager.confirm('账单确认', '确认提交账单?提交后渠道可以看到这批账单！', function(r){
	    		if (r){
	    			var data = {ids: ids};
			       	postAjaxRequest('/bill/admin/submit.do', data, function(data) {
			       	 	$.messager.alert('提示','提交成功');
						$("#billList").datagrid('reload');
					}); 
	    		}
	   		});          		
	   	}
   
			
	}
	
	function confirmBillData(){
		var ids = getGridCheckedIds("billList", "isInvoiceSubmited", 'true');	
	   	if(ids.length == 0){
	   		 $.messager.alert('提示','请在列表勾选状态为待付款的渠道');
	   	}else{
	   		$.messager.confirm('付款确认', '确认提交此批账单为已付款？', function(r){
	    		if (r){
	    			var data = {ids: ids};
			       	postAjaxRequest('/bill/admin/confirm.do', data, function(data) {
			       	 	$.messager.alert('提示','提交成功');
						$("#billList").datagrid('reload');
					}); 
	    		}
	   		});          		
	   	}
   
	}
	
	function formatterAdminBillOperation(val, row){
		if(row.billStatus==0){
			return '<a style="margin-left:5px" href="?p=/admin/bill/edit&id=' + row.id + '"> 确认 </a>';	
		}else if(row.billStatus==1 && row.isInvoiceSubmited){
			return '<a style="margin-left:5px" href="?p=/admin/bill/invoice&pay=1&id=' + row.id + '"> 付款 </a>';	
		}else if(row.billStatus==1 && !row.isInvoiceSubmited){
			return '<a style="margin-left:5px" onclick="cancel(\'' + row.id + '\');" href="#"> 撤销 </a>';
		}
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
	<span class="r-edit-label">姓名:</span>
	<input class="height26" type="text" name="userName" id="userName" /> 
 	<span class="tab-m">
       <label>状态:</label>
       <select id="school" class="easyui-combobox"   style="width:100px;height:30px;" name="school">
           <option value=""  >所有</option>
		   <option value="0" selected >新账单</option>
		   <option value="1"  >待付款</option>
		   <option value="2"  >已付款</option>	
		   <option value="3"  >下月结算</option>		   
	   </select>
    </span>
	<button class="search_btn_noWidth" onclick="search();">搜索</button>
</div>
<p></p>
<table id="billList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/user/admin/listStudent.do"  iconCls="icon-save" sortOrder="asc" pagination="true" >
	<thead>
		<tr>
		    <th data-options="field:'ck',checkbox:true"></th>
			<th align="center" field="name" width="50" sortable="false" resizable="true" >姓名</th>					
			<th align="center" field="sex" width="100" sortable="false" resizable="true" data-options="formatter:formatterSex">性别</th>	
			<th align="center" field="birthday" width="100" sortable="false" resizable="true" >出生日期</th>
			<th align="center" field="schoolName" width="100" sortable="false" resizable="true">报名校区</th>
			<th align="center" field="parentName" width="100" sortable="false" resizable="true" >家长姓名</th>	
			<th align="center" field="parentMobilNumber" width="100" sortable="false" resizable="true" >家长手机</th>	
		<th align="center" data-options="field:'id',formatter:formatterAdminBillOperation,width:100" >操作</th>
		</tr>
	</thead>
</table>


