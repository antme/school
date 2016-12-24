<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script>
	function search() {

		$('#codeList').datagrid('load', {
			keyword : $("#keyword").val(),
			owner:$("#owner").val()
		});

	}

	function formatterCodeOpt(val, row) {
		var statusdisplay = row.status == 1 ?"启用"  : "停用";

		return '<a href="#" onclick=loadRemotePage("admin/code/addcoop&id='
				+ row.id
				+ '")>分配</a>'
				+ ' <a style="margin-left:5px;" href="#" onclick=loadRemotePage("admin/code/copycoop&id='
				+ row.id
				+ '&ownerId='
				+ row.ownerId
				+ '")>复制</a>'
				+ ' <a style="margin-left:5px;" href="#" onclick=setOnlineADSpaceCode(\'' + row.id +"\',\'"  + row.status + "\');>" + statusdisplay + "</a>";
	}

	function setOnlineADSpaceCode(id,status) {
		 if (status == 0) {
			$.messager.alert('错误', '该广告用户尚未启用');
			return;
		} 
		var msg = "是否确认启用此广告位?"
		if (status == 2) {
			msg = "是否确认停用此广告位?"
		}
		status = status == 1 ? 2 : 1;//当前是否管理员禁用若禁用则改为用户启用2,若未禁用则改为管理员禁用1.
		$.messager.confirm('广告位', msg, function(r) {
			if (r) {
				var data = {
					id : id,
					status : status
				};
				postAjaxRequest("/adspace/admin/setAdminOnlineADSpaceCode.do", data, function(data) {
					$("#codeList").datagrid('reload');
				});
			}
		});
	}
	function formatterEnableMobScene(val, row) {
		if (val) {
			return '<a href="#" onclick=enableMobScene(\'' + row.id
					+ '\')>停用</a>';
		} else {
			return '<a href="#" onclick=enableMobScene(\'' + row.id
					+ '\')>启用</a>';
		}
	}
	
	function formatterEnableEnhanceIncome(val, row){
		if (val) {
			return '<a href="#" onclick=enableEnhanceIncome(\'' + row.id
					+ '\')>停用</a>';
		} else {
			return '<a href="#" onclick=enableEnhanceIncome(\'' + row.id
					+ '\')>启用</a>';
		}
	}

	function formatterBaiduCode(val, row) {

		if (val != null && val != "") {
			var data = {};
			eval('data=' + val);
			return "移动20:3 " + data.mob + "</br>" + "PC_300_250: " + data.pc3_2
					+ "</br>" + "移动插屏: " + data.mobScreen + "</br>";
		}

		return "";

	}

	function formatterYnPercent(val, row) {

		if (val != null && val != "") {
			var data = {};
			eval('data=' + val);
			return data.scenePercent;
		}

		return "";
	}

	function formatterYnCode(val, row) {

		val = row.baiduCode;
		if (val != null && val != "") {
			var data = {};
			eval('data=' + val);
			return data.ynMobSceneCode;
		}

		return "";
	}

	function enableMobScene(id) {
		postAjaxRequest("/adspace/admin/enableMobscene.do", {
			id : id
		}, function(data) {
			search();
		});
	}
	
	function enableEnhanceIncome(id){
		postAjaxRequest("/adspace/admin/enableEnhanceIncome.do", {
			id : id
		}, function(data) {
			search();
		});
	}
</script>

<div>
	<label style="font-size: 20px">代码位管理</label>
</div>
<br>
<br>

<div class="">
	<label class="r-edit-label width100">代码位关键字:</label> <input type="text"
		name="keyword" id="keyword" placeholder="代码位名称/codeId/百度代码" class="height24" />
	<label class="r-edit-label width100">所属账号:</label> <input type="text"
		name="owner" id="owner" placeholder="所属账号/父级账号" class="height24" />
	<button class="search_btn_noWidth" onclick="search();">搜索</button>
</div>
<p></p>


<table id="codeList" class="easyui-datagrid"
	data-options="checkOnSelect:false, singleSelect:true, remoteFilter:true, fitColumns: true"
	url="/adspace/admin/list.do" iconCls="icon-save" sortOrder="asc"
	pagination="true">
	<thead>
		<tr>
			<th align="center" field="codeName" width="100" sortable="false"
				resizable="true">代码位名称</th>
			<th align="center" field="codeId" width="50" sortable="false"
				resizable="true">代码位ID</th>
			<th align="center" field="userName" width="60" sortable="false"
				resizable="true">所属帐号</th>
			<th align="center" field="pName" width="60" sortable="false"
				resizable="true">父级账号</th>
			<th align="center" width="100" sortable="false" resizable="true"
				data-options="field:'baiduCode',formatter:formatterYnPercent">赢纳场景比率</th>
			<th align="center" width="100" sortable="false" resizable="true"
				data-options="field:'createdOn',formatter:formatterYnCode">赢纳场景</th>
	
			<th align="center" width="80"
				data-options="field:'enableMobScene',formatter:formatterEnableMobScene">场景管家</th>
			<th align="center" width="80"
				data-options="field:'enableEnhanceIncome',formatter:formatterEnableEnhanceIncome">增强变现</th>	
			<th align="center" field="updatedOn" width="100" sortable="false"
				resizable="true">最后更新</th>
			<th align="center" width="120"
				data-options="field:'id',formatter:formatterCodeOpt">操作</th>
		</tr>
	</thead>

</table>
