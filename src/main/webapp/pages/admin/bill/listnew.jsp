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
	
	function cancel(id){		
		$.messager.confirm('账单撤销', '确认撤销此账单', function(r){
    		if (r){
    			var data = {id: id};
		       	postAjaxRequest('/bill/admin/cancel.do', data, function(data) {
		       	 	$.messager.alert('提示','提交成功');
					$("#billList").datagrid('reload');
				}); 
    		}
   		});   
		
	}

	function formatterInvoiceStatus(val, row){		
		if(val == 0){			
			return "待确认";
		}
		
		if(val == 1 ){
			return "待付款";
		}
		
		if(val == 2 ){
			return "已付款";
		}
		
		if(val == 3 ){
			return "累计到下月结算";
		}
				
	}
	
	$(document).ready(function() { 
		var param = getCookie("bill_list_param");
		var data = null;
		if(param){
			data = JSON.parse(param);
		}
		
		if(data){
			$('#queryDate').combobox('setValue',data.queryDate);
			$("#billStatus").combobox('setValue', data.billStatus);
			$("#userName").val(data.userName);
			search();
		}else{
			$("#queryDate").combobox({			
				url :'/bill/admin/month.do',
				loadFilter:function(data){
					return data.rows;
				},
				valueField:'queryDate',
	            textField:'queryDate',
	            method:'get',
				onLoadSuccess: function(param){
					$('#queryDate').combobox('setValue',param[0].queryDate);
				}
			});		
		}
	});
	

	function formatterInvoiceOperation(val, row){
		if(row.isInvoiceSubmited){
			return '<a style="margin-left:5px" href="?p=/admin/bill/invoice&id=' + row.id + '"> 查看发票 </a>';	
		}
	}
	
	function getSearchStr(){
		var billStatus = 0;
		var queryDate = $("#queryDate").combobox('getValue');;
		if ($("#billStatus").length > 0) {
			 billStatus = $("#billStatus").combobox('getValue');
		}
		var data = {
			userName : $("#userName").val(),
			billStatus : billStatus,
			queryDate : queryDate
		}
		
		addCookie("bill_list_param",JSON.stringify(data), 0.5);
		//return "&param=" +  encodeURIComponent(JSON.stringify(data));

	}
	

	function addCookie(objName, objValue, objHours) {//添加cookie
		var str = objName + "=" + escape(objValue);
		if (objHours > 0) {//为0时不设定过期时间，浏览器关闭时cookie自动消失
			var date = new Date();
			var ms = objHours * 3600 * 1000;
			date.setTime(date.getTime() + ms);
			str += "; expires=" + date.toGMTString();
		}
		document.cookie = str;
	}

	function getCookie(objName) {//获取指定名称的cookie的值
		var arrStr = document.cookie.split("; ");
		for (var i = 0; i < arrStr.length; i++) {
			var temp = arrStr[i].split("=");
			if (temp[0] == objName)
				return unescape(temp[1]);
		}
	}
</script>

<div>
	<label style="font-size:20px">账单管理</label>
</div>
<br><br>
<div >
	<span class="tab-m">
           <label>月份</label>
           <input class="easyui-combobox"  type="text" style="width:100px;height:30px;" id="queryDate" />
    </span>
	<span class="r-edit-label">用户名:</span>
	<input class="height26" type="text" name="userName" id="userName" /> 
 	<span class="tab-m">
       <label>状态:</label>
       <select id="billStatus" class="easyui-combobox"   style="width:100px;height:30px;" name="billStatus">
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
<table id="billList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/bill/admin/listnew.do?billStatus=0"  iconCls="icon-save" sortOrder="asc" pagination="true" >
	<thead>
		<tr>
		    <th data-options="field:'ck',checkbox:true"></th>
			<th align="center" field="year" width="50" sortable="false" resizable="true" data-options="formatter:formatterBillDate">月份</th>					
			<th align="center" field="name" width="100" sortable="false" resizable="true">账户</th>	
			<th align="center" field="adForecastMoney" width="100" sortable="false" resizable="true" data-options="formatter:formatterMoneyOperation">预估收入</th>
			<th align="center" field="adForecastMoneyOfLastMonth" width="100" sortable="false" resizable="true" data-options="formatter:formatterMoneyOperation">上月累计</th>
			
			<th align="center" field="shareProportion" width="100" sortable="false" resizable="true" >分成比率</th>
			
			<th align="center" field="adActureMoney" width="100" sortable="false" resizable="true" data-options="formatter:formatterMoneyOperation">分成收入</th>
			<th align="center" field="deductedMoney" width="100" sortable="false" resizable="true" data-options="formatter:formatterMoneyOperation">扣款</th>			
			<th align="center" field="rewardMoney" width="100" sortable="false" resizable="true" data-options="formatter:formatterMoneyOperation">调整款</th>
			<th align="center" field="payMoney" width="100" sortable="false" resizable="true" data-options="formatter:formatterMoneyOperation">实际收入</th>	
			<th align="center" field="billStatus" width="100" sortable="false" resizable="true"  data-options="formatter:formatterInvoiceStatus">状态</th>	
			<th align="center" field="billStatus1" width="100" sortable="false" resizable="true"  data-options="formatter:formatterInvoiceOperation">发票</th>		
				
			<th align="center" data-options="field:'id',formatter:formatterAdminBillOperation,width:100" >操作</th>
			<th align="center" field="remark" width="200"  sortable="false" resizable="true" >备注</th>
		</tr>
	</thead>
</table>


