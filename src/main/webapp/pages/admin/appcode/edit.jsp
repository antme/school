<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<style>
.form_items label, .form_items_btn label{
	padding: 0 10px 0 0;
}
</style>
<div style="padding: 10px 60px 20px 60px">
	<form id="ff" method="post" novalidate>
		<div class="form-container">
			<div>
				<label id="infolabel" style="font-size: 20px">编辑APP广告位</label>
			</div>
			<br/>
			<input type="hidden" name="id" id="id" value="${param.id}" />
			<div class="form_items">
				<label class="r-edit-label width150">广告位名称:</label> <input
					class="easyui-validatebox textbox width300" type="text"
					name="adPublishName" id="adPublishName" readonly="readonly"></input>
			</div>
			<div class="form_items">
				<label class="r-edit-label width150">广告位ID:</label> <input
					class="easyui-validatebox textbox width300" type="text"
					name="codeId" id="codeId" readonly="readonly"></input>
			</div>
			<div class="form_items">
				<label class="r-edit-label width150">百度应用id(安卓):</label> <input
					class="easyui-validatebox textbox width300 appIds-js" type="text"
					key="1" name="appIds[1]"></input>
			</div>
			<div class="form_items">
				<label class="r-edit-label width150">横幅代码位id(安卓):</label> <input
					class="easyui-validatebox textbox width300 adSlotIds-js" 
					type="text" key="1_1" name="adSlotIdsMap[1_1]" maxlength=8/>
			</div>
			<div class="form_items">
				<label class="r-edit-label width150">开屏代码位id(安卓):</label> <input
					class="easyui-validatebox textbox width300 adSlotIds-js"
					type="text" key="1_2" name="adSlotIdsMap[1_2]" maxlength=8></input>
			</div>
			<div class="form_items">
				<label class="r-edit-label width150">插屏代码位id(安卓):</label> <input
					class="easyui-validatebox textbox width300 adSlotIds-js"
					type="text" key="1_3" name="adSlotIdsMap[1_3]" maxlength=8></input>
			</div>
			<div class="form_items">
				<label class="r-edit-label width150">信息流代码位id(安卓):</label> <input
					class="easyui-validatebox textbox width300 adSlotIds-js"
					type="text" key="1_4" name="adSlotIdsMap[1_4]" maxlength=8></input>
			</div>

			<div class="form_items">
				<label class="r-edit-label width150">百度应用id(IOS):</label> <input
					class="easyui-validatebox textbox width300 appIds-js" type="text"
					key="2" name="appIds[2]"></input>
			</div>
			<div class="form_items">
				<label class="r-edit-label width150">横幅代码位id(IOS):</label> <input
					class="easyui-validatebox textbox width300 adSlotIds-js"
					type="text" key="2_1" name="adSlotIdsMap[2_1]" maxlength=8></input>
			</div>
			<div class="form_items">
				<label class="r-edit-label width150">开屏代码位id(IOS):</label> <input
					class="easyui-validatebox textbox width300 adSlotIds-js"
					type="text" key="2_2" name="adSlotIdsMap[2_2]" maxlength=8></input>
			</div>
			<div class="form_items">
				<label class="r-edit-label width150">插屏代码位id(IOS):</label> <input
					class="easyui-validatebox textbox width300 adSlotIds-js"
					type="text" key="2_3" name="adSlotIdsMap[2_3]" maxlength=8></input>
			</div>
			<div class="form_items">
				<label class="r-edit-label width150">信息流代码位id(IOS):</label> <input
					class="easyui-validatebox textbox width300 adSlotIds-js"
					type="text" key="2_4" name="adSlotIdsMap[2_4]" maxlength=8></input>
			</div>
						<div class="form_items">
				<div class="item-left">
					<label class="r-edit-label width150 ">安卓投放类型:</label>
				</div>
				<div class="item-right">
					<label><input name="types" type="checkbox" value="1_1"  data="1_1"/>横幅</label>
					<label><input name="types" type="checkbox" value="1_2" data="1_2"/>开屏</label>
					<label><input name="types" type="checkbox" value="1_3" data="1_3"/>插屏</label>
					<label><input name="types" type="checkbox" value="1_4" data="1_4"/>信息流</label>
				</div>
			</div>
			<div class="form_items">
				<div class="item-left">
					<label class="r-edit-label width150 ">苹果投放类型:</label>
				</div>
				<div class="item-right">
					<label><input name="types" type="checkbox" value="2_1" data="2_1"/>横幅</label>
					<label><input name="types" type="checkbox" value="2_2" data="2_2" />开屏</label>
					<label><input name="types" type="checkbox" value="2_3" data="2_3"/>插屏</label>
					<label><input name="types" type="checkbox" value="2_4" data="2_4"/>信息流</label>
				</div>
			</div>
			<div class="form_items"  style="display:none" id="size_1_1">
				<div class="item-left">
					<label class="r-edit-label width150 ">(安卓)横幅尺寸:</label>
				</div>
				<div class="item-right">
				<select name="sizes">
					<option value="580*90(20:3)">580*90(20:3)</option>
					<option value="360*300(6:5)">360*300(6:5)</option>
					<option value="480*60(8:1)">480*60(8:1)</option>
					<option value="640*270(7:3)">640*270(7:3)</option>
				</select>
				</div>
			</div>
			<div class="form_items"  style="display:none" id="size_1_2">
				<div class="item-left">
					<label class="r-edit-label width150 ">(安卓)开屏尺寸:</label>
				</div>
				<div class="item-right">
				<select name="sizes">
					<option value="640*960(2:3)">640*960(2:3)</option>
					<option value="720*1280(9:16)">720*1280(9:16)</option>
					<option value="1024*768(4:3)">1024*768(4:3)</option>
				</select>
				</div>
			</div>
			<div class="form_items"  style="display:none" id="size_1_3">
				<div class="item-left">
					<label class="r-edit-label width150 ">(安卓)插屏尺寸:</label>
				</div>
				<div class="item-right">
				<select name="sizes">
					<option value="360*300(6:5)">360*300(6:5)</option>
				</select>
				</div>
			</div>
			<div class="form_items"  style="display:none" id="size_1_4">
				<div class="item-left">
					<label class="r-edit-label width150 ">(安卓)信息流尺寸:</label>
				</div>
				<div class="item-right">
				<select name="sizes">
					<option value="600*300(2:1)">600*300(2:1)</option>
					<option value="600*500(6:5)">600*500(6:5)</option>
					<option value="640*270(7:3)">640*270(7:3)</option>
					<option value="960*640(3:2)">960*640(3:2)</option> 
					<option value="800*120(20:3)">800*120(20:3)</option> 
					<option value="140*100(7:5)">140*100(7:5)</option> 
					<option value="1280*720(16:9)">1280*720(16:9)</option>
				</select>
				</div>
			</div>
			<div class="form_items"  style="display:none" id="size_2_1">
				<div class="item-left">
					<label class="r-edit-label width150 ">(IOS)横幅尺寸:</label>
				</div>
				<div class="item-right">
				<select name="sizes">
					<option value="580*90(20:3)">580*90(20:3)</option>
					<option value="360*300(6:5)">360*300(6:5)</option>
					<option value="480*60(8:1)">480*60(8:1)</option>
					<option value="640*270(7:3)">640*270(7:3)</option>
				</select>
				</div>
			</div>
			<div class="form_items"  style="display:none" id="size_2_2">
				<div class="item-left">
					<label class="r-edit-label width150 ">(IOS)开屏尺寸:</label>
				</div>
				<div class="item-right">
				<select name="sizes">
					<option value="640*960(2:3)">640*960(2:3)</option>
					<option value="720*1280(9:16)">720*1280(9:16)</option>
					<option value="1024*768(4:3)">1024*768(4:3)</option>
				</select>
				</div>
			</div>
			<div class="form_items"  style="display:none" id="size_2_3">
				<div class="item-left">
					<label class="r-edit-label width150 ">(IOS)插屏尺寸:</label>
				</div>
				<div class="item-right">
				<select name="sizes">
					<option value="360*300(6:5)">360*300(6:5)</option>
				</select>
				</div>
			</div>
			<div class="form_items"  style="display:none" id="size_2_4">
				<div class="item-left">
					<label class="r-edit-label width150 ">(IOS)信息流尺寸:</label>
				</div>
				<div class="item-right">
				<select name="sizes">
					<option value="600*300(2:1)">600*300(2:1)</option>
					<option value="600*500(6:5)">600*500(6:5)</option>
					<option value="640*270(7:3)">640*270(7:3)</option>
					<option value="960*640(3:2)">960*640(3:2)</option> 
					<option value="800*120(20:3)">800*120(20:3)</option> 
					<option value="140*100(7:5)">140*100(7:5)</option> 
					<option value="1280*720(16:9)">1280*720(16:9)</option>
				</select>
				</div>
			</div>
			<div class="form_items">
				<label class="r-edit-label width150">&nbsp;</label>
				<button class="sub_btn" type="button" onclick="submitform();">提交</button>
			</div>
		</div>
	</form>
</div>
<script type="text/javascript">
	function submitform() {
		$('#ff').form('submit', {
			url : '/v/setappstylecodes.do',
			onSubmit : function() {
				replaceBlank();
				var flag=checkAdSlotIds()&&checkAppIds();
				if(flag)appendSize();
				return flag;
			},
			success : function(data) {
				var resp = $.parseJSON(data)
				if (resp.code != 1) {
					debugger;
					$.messager.alert('错误',resp.msg[0].message,'Error');
					return;
				}else{
					$.messager.alert('提示','配置成功','Info',function(){
 						loadRemotePage("admin/appcode/list");
					});
				}
		}
		});
	}
	function appendSize(){
		$("input:checked").each(function(){
			var checkedTypes=$(this);
			var data=checkedTypes.attr('data');
			var sizeEle=$('#size_'+data).find('select');
			checkedTypes.val(data+'_'+sizeEle.val());
		});
	}
	function replaceBlank(){
		$('.adSlotIds-js').each(function(){
			$(this).val($(this).val().replace(/[\s+\D+]/g,""))
		});
	}
	function checkAdSlotIds() {
		var flag=true;
		$('input:checked').each(function(index) {
			var checkedElem = $(this);
			var inputElem=$('input[key="'+checkedElem.attr('data')+'"]')
			flag &= !!inputElem.val();
		});
		if (!flag){
			$.messager.alert('错误',"请填写勾选的投放类型对应的代码位!",'Error');
		}
		return flag;
	}

	function checkAppIds() {
		var flag=false;
		if($('input:checked[data^="1"]').length){//安卓类型
			flag=!!$('input[key="1"]').val();
		}
		if($('input:checked[data^="2"]').length){//安卓类型
			flag=!!$('input[key="2"]').val();
		}
		if (!flag){
			$.messager.alert('错误',"请配置投放类型对应百度应用ID!",'Error');
		}
		return flag;
	};
	//绑定尺寸显示事件
	$(function(){
		$('input[name="types"]').click(function(){
			var typeEle=$(this);
			var data=typeEle.attr('data');
			var type=data.split('_');
			var style=type[1];
			var platForm=type[0];
			var siezEle = $('#size_' + data);
			siezEle.is(':hidden')?siezEle.show():siezEle.hide();
		});
	});

	$(document).ready(function() {
		var id = $('#id').val();
		if (!id) {
			alert("appcodeId不能为空")
			loadRemotePage("admin/appcode/list")
			return;
		}

		postAjaxRequest("/v/queryappstylecodes.do", {
			id : id
		}, function(json) {

			$('.adSlotIds-js').each(function(index){
				var elem=$(this);
				var key=elem.attr('key');
				var value=json.adSlotIdsMap[key];
				if(value!=undefined)elem.val(value);
			});
			$('.appIds-js').each(function(index) {
				var elem = $(this);
				var key = elem.attr('key');
				var value = json.appIds[key];
				if (value != undefined){
					elem.val(value);
				}
			});

		});
		
		postAjaxRequest("/v/queryappcode.do", {
			id : id
		}, function(json) {
			$('#adPublishName').val(json.adPublishName);
			$('#codeId').val(json.codeId);
			for ( var i in json.types) {
				var spl=json.types[i].split('_');
				var size=spl[2];
				var style=spl[1];
				var platForm=spl[0];
				var type=platForm+'_'+style;
				$("input[type=checkbox]").each(function(index) {
					if (type == $(this).val()) {
						$(this).click();
						return true;
					}
				});
				var siezEle = $('#size_' + type);
				var sel=siezEle.find('select');
				sel.val(size);
			} 
		});
	});
</script>