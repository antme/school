<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div>
	<label style="font-size: 20px">广告源过滤管理</label>
</div>
<div class="">
	<label class="r-edit-label width100">关键字:</label> <input type="text"
		name="keyword" id="keyword" placeholder="所属账号" class="height24" />
	<button class="search_btn_noWidth" onclick="search();">搜索</button>
</div>

<table id="dg" class="easyui-datagrid"
	data-options="striped:true,singleSelect:true,collapsible:false,fitColumns:true,pagination:true, url:'/strategy/listforadmin.do', method:'get'">
	<thead>
		<tr>
			<th data-options="field:'userName',align:'center'">所属账号</th>
			<th data-options="field:'configStatus',align:'center',formatter:formatconfigStatus">配置更新状态</th>
			<th data-options="field:'createdOn',align:'center'">创建时间</th>
			<th data-options="field:'updatedOn',align:'center'">更新时间</th>
			<th data-options="field:'id',align:'center',formatter:formatterCodeOpt">操作</th>
		</tr>
	</thead>
</table>
<script>
	function search() {
		var param={};
		param.keyword=$("#keyword").val();
		$('#dg').datagrid('load', param
		);
	}
	
	function formatterCodeOpt(val, row) {
		return '<a href="#" onclick=loadRemotePage("admin/blockstrategy/detail&id='
				+ val + '")>详情</a>';
	}

	function formatconfigStatus(val,row){
		return val?"":"有更新";
	}
</script>