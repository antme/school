<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div>
	<label style="font-size: 20px">APP广告位管理</label>
</div>
<div class="">
	<label class="r-edit-label width100">关键字:</label> <input type="text"
		name="keyword" id="keyword" placeholder="代码位名称/所属账号" class="height24" />
	<button class="search_btn_noWidth" onclick="search();">搜索</button>
</div>

<table id="dg" class="easyui-datagrid"
	data-options="striped:true,singleSelect:true,collapsible:false,fitColumns:true,pagination:true, url:'/v/listuserappcode.do', method:'get'">
	<thead>
		<tr>
			<th data-options="field:'adPublishName',align:'center'">广告位名称</th>
			<th data-options="field:'codeId',align:'center'">广告位ID</th>
			<th data-options="field:'ownerName',align:'center'">所属账号</th>
			<th data-options="field:'publishStatus',align:'center',formatter:formatpublishStatus">启用状态</th>
			<th data-options="field:'configStatus',align:'center',formatter:formatconfigStatus">配置更新状态</th>
			<th data-options="field:'createdOn',align:'center'">创建时间</th>
			<th data-options="field:'updatedOn',align:'center'">更新时间</th>
			<th data-options="field:'id',align:'center',formatter:formatterCodeOpt">操作</th>
		</tr>
	</thead>
</table>
<script>
function search() {
	$('#dg').datagrid('load', {
		keyword : $("#keyword").val()
	});
}
	function formatterCodeOpt(val, row) {
		var toStatus=!row.publishStatus;
		var disableLabel =toStatus?"启用":"停用";
		return '<a href="#" onclick=loadRemotePage("admin/appcode/edit&id='
				+ val + '")>分配</a>  '+"<a href=\"#\" onclick=setPublishStatus('" + row.id + "'," +toStatus +  ");>" + disableLabel + "</a>";;
	}
	function setPublishStatus(id, publishStatus){
		var msg = publishStatus?"是否确认启用此广告位?":"是否确认停用此广告位?"
		$.messager.confirm('广告位', msg, function(r){
	        if (r){
	        	var data = { appcodeId : id, publishStatus:publishStatus };
	        	postAjaxRequest("/v/setpublishstatus.do", data, function(data) {
	        		$("#dg").datagrid('reload');
	        	});
	        }
	    }); 
	}
	function formatpublishStatus(val,row){
		return val?"启用":"停用";
	}
	function formatconfigStatus(val,row){
		return val?"":"有更新";
	}
</script>