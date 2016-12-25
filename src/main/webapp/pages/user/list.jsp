<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	function search() {
		$("#userList").datagrid('load',{
			userName : $("#userName").val()
		});
	}
	
	function loadFilter(data){
		$("#count").html(data.total);
		return data;
	}
	

	function formatterUserOperation(val, row) {
		return '<a href="#" onclick=loadRemotePage("admin/user/edit&id=' + row.id + '")>编辑</a>'+'&nbsp;&nbsp;&nbsp;'
		+ '<a href="javascript:void(0);" onclick=addCID("'+ row.id +'","'+row.cid +'")>关联账号 </a>';
	}

	function addCID(id, cid) {
		if (cid) {
			alert("已经配置Cid:" + cid);
		}
		var cid = prompt();
		if(!cid){
			return;
		}
		var data = {};
		data.cid = cid;
		data.id = id;
		$.messager.confirm('操作提示', '确认关联Cid：' + cid + '?', function(r) {
			if (r) {
				$.post('/user/admin/configCid.do', {
					cid : cid,
					id : id
				}, function(data) {
					if (data.code == 1) {
						alert('关联成功！可以手动刷新查看配置状态！');
					}
				});
			}
		});
	}
</script>
<div>
	<label id="addnewuser" style="font-size:20px">用户管理</label>
</div>
<br><br>
<div >
	<span class="r-edit-label">用户名:</span>
	<input class="height26" type="text" name="userName" id="userName" placeholder="账号/父级账号/名称"  /> 

	<button class="search_btn_noWidth" onclick="search();">搜索</button>

</div>


<p></p>

<table id=userList class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, multiSort:true" url="/user/admin/list.do" iconCls="icon-save"
	sortOrder="asc" pagination="true" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="userName" width="250"  resizable="true" sortable="true" >账号</th>
			<th align="center" field="pName" width="250"  resizable="true" sortable="true" >父级账号</th>
			<th align="center" field="name" width="250"  resizable="true" sortable="true" >名称</th>
			<th align="center" field="cid" data-options="field:'id'" >CID</th>
			<th align="center" field="shareProportion" width="250"  resizable="true" sortable="true" >分成比率</th>
			<th align="center" field="updatedOn" width="250"  resizable="true" sortable="true" >更新日期</th>
			<th align="center" data-options="field:'id',formatter:formatterUserOperation"  width="250">操作</th>
		</tr>
	</thead>
	
</table>