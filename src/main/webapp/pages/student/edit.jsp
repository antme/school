<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String id = request.getParameter("id"); %>

<script type="text/javascript">
	var id = "<%=id%>";

	$(document).ready(function() {
		initFormSubmit("bill_submit", "/bill/admin/save.do", "提交账单", function(){
			alert("提交成功");
			loadRemotePage("admin/bill/listnew");
		});
		
		if(id!="null"){
			postAjaxRequest("/bill/admin/load.do", {id:id}, function(data){
				$("#bill_submit").form('load',data.data);
			});
			
		}
		$("#rewardMoney").blur(function(){updatePayMoney();});
		$("#deductedMoney").blur(function(){updatePayMoney();});
	});
	
 	function updatePayMoney(){
 		var shareProportion = parseFloat($('#shareProportion').slider('getValue'));
 		var adForecastMoney = parseFloat($("#adForecastMoney").val());
 		var adForecastMoneyOfLastMonth =parseFloat($("#adForecastMoneyOfLastMonth").val());
 		
 		
		var rewardMoney = parseFloat($("#rewardMoney").val());
		var deductedMoney = parseFloat($("#deductedMoney").val());
		var adActureMoney = parseFloat((parseFloat(adForecastMoneyOfLastMonth) + parseFloat(adForecastMoney))*shareProportion/100).toFixed(2);
		$("#adActureMoney").val(adActureMoney);

		
		var payMoney = parseFloat(adActureMoney) + parseFloat(rewardMoney)-parseFloat(deductedMoney);
		$("#payMoney").val(payMoney.toFixed(2));
	} 
	
 	function saveBill(){
 		$("#action").val("save");
 		$('#bill_submit').form('submit');
 	}
 	
 	function submitBill(){
 		$.messager.confirm('账单确认', '确认提交账单?提交后渠道可以看到此批账单！', function(r){
 			
	 		if(r){
		 		$("#action").val("submit");
		 		$('#bill_submit').form('submit');
	 		}
 		});
 	}

	
</script>

<div style="padding: 10px 60px 20px 60px">
	<form id="bill_submit" method="post" novalidate>
		<div class="form-container">
			<input class="" type="hidden" name="id" />
			<input class="" type="hidden" name="action" id="action" />
			
			<div>
				<label style="font-size:20px">账单调整</label>
			</div>
			<br><br>
				
			<div class="form_items">
				<label class="r-edit-label width100">账户:</label> <input class="easyui-validatebox textbox width300" type="text" name="userName" disabled="disabled"></input>
			</div>

			<div class="form_items">
				<label class="r-edit-label width100">广告预估收入:</label> <input class="easyui-validatebox textbox width300" type="text" required  disabled="disabled" id="adForecastMoney" name="adForecastMoney"></input>
			</div>
			<div class="form_items">
				<label class="r-edit-label width100">上月累计:</label> <input class="easyui-validatebox textbox width300" type="text" required  disabled="disabled" id="adForecastMoneyOfLastMonth" name="adForecastMoneyOfLastMonth"></input>
			</div>
			
			<div class="form_items" style="height:100px;">
				<label class="r-edit-label width100" style="margin-top:20px;">分成比率</label> 
				<div style="float:left; margin-left:10px;margin-top:20px"><input type="hidden" id="shareProportion" name="shareProportion" class="easyui-slider" style="width:300px" data-options="
		                showTip:true,
		                value:50,
		                max:100,
		                onChange : function(newValue,oldValue){updatePayMoney();},
		                tipFormatter:function(value){return value+'%';},
		                rule: [0,'|',50,'|',100]"></div>
		                
			</div>
		
			
			
			<div class="form_items">
				<label class="r-edit-label width100">分成收入:</label> <input id='adActureMoney' class="easyui-validatebox textbox width300" type="text" required name="adActureMoney" ></input><span class="font10">元</span>
			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">调整:</label> <input id="rewardMoney" class="easyui-validatebox textbox width300 " type="text" required name="rewardMoney" value="0"></input><span class="font10">元</span>
			</div>
			<div class="form_items">
				<label class="r-edit-label width100">扣减:</label> <input id="deductedMoney" class="easyui-validatebox textbox width300" type="text" required name="deductedMoney" value="0"></input><span class="font10">元</span>
			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">实际收入:</label> <input id='payMoney' class="easyui-validatebox textbox width300" type="text" required name="payMoney" readonly="readonly" ></input><span class="font10">元</span>
			</div>

			<div class="form_items">

				<label class="r-edit-label width100">备注:</label>
				<textarea class="easyui-validatebox textbox width300" style="width: 450px; height: 150px;" type="text" name="remark"></textarea>

			</div>
		
			
			<div class="form_items">
				<label class="r-edit-label width100">&nbsp;</label>
				<input class="sub_btn" type="button" value="保存"  onclick="saveBill();" style="margin-left:10px;">
				<input class="sub_btn" type="button" value="提交"  onclick="submitBill()" style="margin-left:10px;">
				<input class="sub_btn" type="button" value="取消"  onclick="loadRemotePage('admin/bill/listnew');" style="margin-left:10px;">
			</div>
		</div>
	</form>
	
</div>
