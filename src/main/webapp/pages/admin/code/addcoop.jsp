<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String id = request.getParameter("id"); %>


<script type="text/javascript">
	var id = "<%=id%>";
	$(document).ready(function() {
		initFormSubmit("add-coop", "/adspace/admin/baiducode/update.do", "添加合作", function(){
			alert("提交成功");
			loadRemotePage("admin/code/list");
		});

		if(id!="null"){
			postAjaxRequest("/adspace/admin/baiducode/load.do", {id:id}, function(data){
			
				if(data.data.bcode  && data.data.bcode.mobScree && data.data.bcode.mobScree == "u2359167"){
					data.data.bcode.mobScree ="";
				}
				$("#add-coop").form('load',data.data.bcode);	
				$("#id").val(data.data.id);
				$("#codeId").val(data.data.codeId);
				$("#codeName").val(data.data.codeName);

			});
			$("#infolabel").text("样式支持配置多个百度代码位，投放时候取最后一个，计费取所有配置的代码位, 空格隔开");
		} 
	});

</script>



<div style="padding: 10px 60px 20px 60px">

	<form id="add-coop" method="post" novalidate>

		<div class="form-container">
		
			<div>
				<label id="infolabel" style="font-size:20px;color:red;"></label>
			</div>
			<br><br>

			<input type="hidden" name="id"  id="id"  value="" />
			
			<div class="form_items">

				<label class="r-edit-label width100">平台:</label> <select class="easyui-combobox width300" name="platform" style="height: 30px;">

					<option value="baidu">百度联盟广告</option>
	

				</select>

			</div>
			<div class="form_items">

				<label class="r-edit-label width100">广告位名称:</label> <input class="easyui-validatebox textbox width300" type="text" name="codeName" id="codeName"　 disabled="disabled"></input>

			</div>
			<div class="form_items">

				<label class="r-edit-label width100">广告位ID:</label> <input class="easyui-validatebox textbox width300" type="text" name="codeId" id="codeId" disabled="disabled"></input>

			</div>
			<div class="form_items">

				<label class="r-edit-label width100">移动20:3:</label> <input class="easyui-validatebox textbox width300" type="text" name="mob" ></input>

			</div>
			<div class="form_items">
				<label class="r-edit-label width100">PC_580_90:</label> <input class="easyui-validatebox textbox width300" type="text" name="pc"></input>
			</div>
			<div class="form_items">
				<label class="r-edit-label width100">PC_300_250:</label> <input class="easyui-validatebox textbox width300" type="text" name="pc3_2"></input>
			</div>

			<div class="form_items">
				<label class="r-edit-label width100">移动插屏:</label> <input class="easyui-validatebox textbox width300" type="text" name="mobScreen" ></input>
			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">PC图+:</label> <input class="easyui-validatebox textbox width300" type="text" name="pcPicPlus" ></input>
			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">PC_300_250_原生:</label> <input class="easyui-validatebox textbox width300" type="text" name="pcNative3_2" ></input>
			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">Mob图+:</label> <input class="easyui-validatebox textbox width300" type="text" name="mobPicPlus"></input>
			</div>

			<div class="form_items">
				<label class="r-edit-label width100">Mob度宝:</label> <input class="easyui-validatebox textbox width300" type="text" name="mobDuBao"></input>
			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">百度飞虹:</label> <input class="easyui-validatebox textbox width300" type="text" name="feiHong"></input>
			</div>
		
			<div class="form_items">
				<label class="r-edit-label width100">移动场景管家:</label> <input class="easyui-validatebox textbox width300" type="text" name="mobScene"></input>
			</div>
	
			<div class="form_items">
				<label class="r-edit-label width100">赢纳场景:</label> <input class="easyui-validatebox textbox width300" type="text" name="ynMobSceneCode"></input>
			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">移动场景管家1:</label> <input class="easyui-validatebox textbox width300" type="text" name="mobSceneWithLimit"></input>
				<label>只投放“下拉底部插入”和“识别文尾插入”样式</label>
			</div>
	
			<div class="form_items">
				<label class="r-edit-label width100">赢纳场景1:</label> <input class="easyui-validatebox textbox width300" type="text" name="ynMobSceneCodeWithLimit"></input>
				<label>只投放“下拉底部插入”和“识别文尾插入”样式</label>
			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">场景切出百分比:</label>
			    	<div style="float:left; margin-left:10px;margin-top:12px"><input type="hidden" id="scenePercent" name="scenePercent" class="easyui-slider" style="width:300px" data-options="
		                showTip:true,
		                value:50,
		                max:100,
		                tipFormatter:function(value){return value+'%';},
		                rule: [0,'|',50,'|',100]"></div>
			</div>
			<div class="form_items">
				<label class="r-edit-label width100">应用到其他代码位:</label> <input class="easyui-validatebox checkbox width100" type="checkbox" name="copyToOther" value="1"></input>
				<label class="r-edit-label">备注:该选项只对场景切出百分比和赢纳场景代码位有效</label>
			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">垂直类中间页:</label> <input class="easyui-validatebox textbox width300" type="text" name="chuizhi"></input>
			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">内容频道:</label> <input class="easyui-validatebox textbox width300" type="text" name="contentChannel"></input>
			</div>

			<div class="form_items">

				<label class="r-edit-label width100">&nbsp;</label> <input class="sub_btn" type="submit" value="提交"></a>

			</div>

		</div>

	</form>