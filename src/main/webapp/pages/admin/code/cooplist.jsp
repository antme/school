<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>

	function search() {

		$('#coopList').datagrid('load', {
			codeName : $("#codeName").val()
		});

	}

</script>


<div class="">
	<label class="r-edit-label width100">代码位名字:</label>
	<input type="text" name="codeName" id="codeName" class="height24"/> 

	<button class="sub_btn_noWidth" onclick="search();">搜索</button>
</div>
<p></p>

<div class="form_items">
	<button class="sub_btn_noWidth" onclick="loadRemotePage('admin/code/addcoop');">新建合作</button>
</div>


<table id="codeList" class="easyui-datagrid" data-options="singleSelect: true, checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/coop/admin/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" >
	<thead>
		<tr>
			<th align="center" field="platform" width="200" sortable="false" resizable="true" data-options="formatter:formatterCoopPlatform">平台</th>
			<th align="center" field="coopId" width="100" sortable="false" resizable="true">Code</th>
			<th align="center" field="coopName" width="100" sortable="false" resizable="true">合作名称</th>
			<th align="center" field="description" width="100" sortable="false" resizable="true">描述</th>
			<th align="center" field="createdOn" width="120" sortable="false" resizable="true">创建时期</th>
			<th align="center" data-options="field:'id',formatter:formatterAdminCoopViewOperation" >操作</th>

		</tr>
	</thead>

</table>

