<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String id = request.getParameter("id"); %>
<% String pay = request.getParameter("pay"); %>
<div class="row ">
	<form id="bill_submit" method="post" novalidate>

	   <div id="form_error"></div>
	   <input type="hidden" name="id" id="id"/>

	   
	  <div class="form_items">
		   <div class="item-left"><label class="r-edit-label width100">月份:</label> </div>
		   <div class="item-right"><label class="r-edit-label width100" id="month"></label></div>
	   </div>
	   
	    <div class="form_items">
		   <div class="item-left"><label class="r-edit-label width100">收益:</label> </div>
		   <div class="item-right"><label class="r-edit-label width100" id="payMoney"></label></div>
	   </div>
	   <div style="margin-left:100px;">
	   	<table id="dg" class="easyui-datagrid" title="发票信息" style="width:700px; height:400px"
	            data-options="rownumbers:true, singleSelect:true,
	            method:'get', iconCls: 'icon-edit'">
	        <thead>
	            <tr>
	                <th data-options="field:'invoiceNumber',width:150,editor:{options: {required:true}, type:'textbox'}">发票号</th>
	                <th data-options="field:'noTaxMoney',width:100,editor:{ type:'numberbox',options:{precision:2, required:true}}">不含税金额</th>
	                <th data-options="field:'taxMoney',width:100,align:'right',editor:{type:'numberbox',options:{precision:2, required:true}}">税额</th>	           
	            </tr>
	        </thead>
	    </table>
	  </div>
	  
		<div class="form_items">
				<label class="r-edit-label width100">&nbsp;</label>
				<input style="display:none;" id="paybutton" class="sub_btn" type="button" value="付款确认" onclick="payBill();" style="margin-left:10px;">
				<input class="sub_btn" type="button" value="返回" onclick="loadRemotePage('admin/bill/listnew');" style="margin-left:10px;">
		</div>
	</form>
</div>



<script>
var id = "<%=id%>";
var pay = "<%=pay%>";
var billData = {};

$(document).ready(function(){
	if (id != "null" && id != "") {
		var param = {
			"id" : id
		}
		postAjaxRequest("/bill/admin/load.do", param, function(data) {
			billData = data.data;
			$("#month").text(data.data.year + '-' +  data.data.month);	
			$("#payMoney").text("￥" + data.data.payMoney);	
			console.log(billData.invoiceList);
			if(billData.invoiceList){
				$('#dg').datagrid({data: billData.invoiceList});
			}
			$("#bill_submit").form('load',data.data);
			

		});
	}
	
	initFormSubmit("bill_submit", "/bill/admin/confirm.do", "付款确认", function(){
		alert("提交成功");
		loadRemotePage("admin/bill/listnew");
	});
	
	if(pay == "1"){
		$("#paybutton").show();
	}
	

});

function payBill(){
		$.messager.confirm('付款确认', '确认已经打款给渠道？', function(r){
			
	 		if(r){
		 		$("#action").val("confirm");
		 		$('#bill_submit').form('submit');
	 		}
		});
}
</script>


