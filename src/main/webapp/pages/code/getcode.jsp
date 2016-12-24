<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>

	function search() {

		$('#codeList').datagrid('load', {
			codeName : $("#codeName").val(),
			isOnline : $("#isOnline").combobox('getValue')
		});

	}

	function onlineCodes(){	
		batchCallApiOnGrid("codeList", "/code/online.do", "确认上线这些代码位?", "上线成功");	
	}
	
	function offlineCodes(){
		batchCallApiOnGrid("codeList", "/code/offline.do", "确认下线这些代码位?", "下线成功");
	}
	

	
	function getCode(id){
		var data = { id : id };
		postAjaxRequest("/code/load.do", data, function(data) {
			$("#codecontent").html(data.data.code);
			$("#codecontent").window('open');
		});
		
	}

</script>

<div>
	<label style="font-size:20px">代码位管理</label>
</div>
<br><br>

<div class="">
	<label class="r-edit-label width100">代码位名字:</label>
	<input type="text" name="codeName" id="codeName" class="height24" /> 
	<label class="r-edit-label width100">状态:</label>
	<select class="easyui-combobox width150" name="isOnline" id="isOnline" style="height: 26px;">
		<option value=""></option>
		<option value="1">启用</option>
		<option value="0">停用</option>
	</select>

	<button class="search_btn_noWidth" onclick="search();">搜索</button>
</div>
<p></p>
<!-- 
<div class="form_items">
	<button class="sub_btn_noWidth" onclick="onlineCodes();">批量启用</button>
	<button class="sub_btn_noWidth" onclick="offlineCodes();">批量停用</button>
</div>
 -->
<table id="codeList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/code/user/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" >
	<thead>
		<tr>
			<th field="CheckBox" checkbox="true"></th>
			<th align="center" field="codeName" width="300" sortable="false" resizable="true">代码位名字</th>
			<th align="center" field="isOnline" width="100" sortable="false" resizable="true" data-options="formatter:formatterisOnline">状态</th>
			<th align="center" field="userName" width="300" sortable="false" resizable="true">所属帐号</th>
			<th align="center" field="createdOn" width="200" sortable="false" resizable="true">创建时期</th>
			<th align="center" width="200" data-options="field:'id',formatter:formatterUserCodeOperation" >操作</th>

		</tr>
	</thead>

</table>

<div id="codecontent" class="easyui-window" title="获取代码位代码" data-options="iconCls:'icon-save',modal:true, closed:true" style="width:600px;height:400px;padding:10px;">
       
</div>
