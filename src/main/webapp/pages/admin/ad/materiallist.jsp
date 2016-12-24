<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>

	
	function formatterAdminMaterialOperation(val, row) {
		//return "<a href='#' onclick=loadRemotePage('ad/addmaterial&id="+row.id+"')>编辑</a>&nbsp;<a href='#'>删除</a>";
		return "<a href='#' onclick=loadRemotePage('ad/addmaterial&pageId=admin&id="
				+ row.id + "')>编辑</a>&nbsp;";
	}

	function add() {
		loadRemotePage('ad/addmaterial&pageId=admin');
	}

	function deleteMaterial(id) {
		var data = {
			id : id
		};
		callApiOnConfirm("material", "/ad/material/delete.do", data,
				"确认删除此广告物料?", "删除成功");
	}

	function formattermaterialTypeOperation(val, row) {
		if (val == 1)
			return "图文";
		if (val == 2)
			return "图片";
		if (val == 3)
			return "富媒体";
		if (val == 4)
			return "应用下载";
		if (val == 5)
			return "H5游戏";

	}

	function formattertargetTypeOperation() {
		return "打开网页";
	}

	function formatterShow(val, row) {
		return "<a href='/pages/ad/preview.jsp?materialId=" + row.id
				+ "' target='_blank'>预览</a>";
	}
</script>

<div>
	<label style="font-size:20px">物料库管理</label>
</div>
<br><br>



<div class="" style="display:none;">
	<label class="r-edit-label width100">物料名称:</label>
	<input class="width100" type="text" name="codeName" id="codeName"/> 

	<button onclick="search();">搜索</button>
</div>
<p></p>

<table id="material" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ad/material/admin/list.do" iconCls="icon-save" sortOrder="asc" pagination="true" >
	<thead>
		<tr>
			<th align="center" field="materialName" width="400" sortable="false" resizable="true">广告物料名称</th>
			<th align="center" field="userName" width="400" sortable="false" resizable="true">所属帐号</th>
			<th align="center" field="materialType" width="150" sortable="false" resizable="true" formatter="formattermaterialTypeOperation">广告类型</th>
			<th align="center" field="targetType" width="150" sortable="false" resizable="true" formatter="formattertargetTypeOperation">点击后</th>
			<th align="center" field="createdOn" width="150" sortable="false" resizable="true">创建时间</th>
			<th align="center" field="show" width="150" sortable="false" resizable="true" formatter="formatterShow">展示</th>
		</tr>
	</thead>

</table>

