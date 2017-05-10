<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	

	function sendSms() {



		$('#datalist').datagrid({
			url: "/sms/school/book/list.do",
		    queryParams: {
		    	schoolId : $("#schoo_select").combobox('getValue'),
		    	startNumber : $("#startNumber").val(),
		    	endNumber : $("#endNumber").val()
			}
		});

	/* 		$.messager.confirm('发送通知', '确认发送通知？', function(r) {
				if (r) {
	}); */
	/* 				postAjaxRequest('/sms/school/book/list.do', data,
							function(data) {
								$.messager.alert('提示', '发送成功');
								$("#datalist").datagrid('reload');
							});
				} */


	}

	function exportData(id) {
		var data = {
			id : id
		}

		postAjaxRequest(
				"/sms/school/book/failed/export.do",
				data,
				function(data) {
					var ifram = '<iframe frameborder="no" border="0" scrolling="no" style="border:0px; border:none;" src ="/download/' + data.path + '" height="0" width="0" ></iframe>';
					var y = document.createElement("div");
					y.innerHTML = ifram;
					document.body.appendChild(y);
				});

	}
	
	function formatterSignDate(val, row){
		return row.signDate + " " + row.startTime +"-" + row.endTime;
	}
</script>

<div>
	<label style="font-size: 20px">通知管理</label>
</div>
<br>
<br>

<div>
	<span class="tab-m"> <label>选择校区: </label> <input
		class="easyui-combobox" type="text"
		style="width: 200px; height: 30px;" id="schoo_select"
		data-options="url:'/sch/admin/select.do',
                    method:'get',
                    valueField:'id',
                    textField:'name',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}" />
	</span> <span class="tab-m"> <label style="margin-left: 10px;">号段:
	</label> <input class=" easyui-validatebox" type="number" name="startNumber"
		value="" style="height: 26px; width: 50px;" id="startNumber" /> <label>
			- </label> <input class=" easyui-validatebox" type="number" name="endNumber"
		value="" style="height: 26px; width: 50px;" id="endNumber" />
	</span> 

	<button class="search_btn_noWidth" onclick="sendSms();">查询</button>
</div>


<p></p>

<table id="datalist" class="easyui-datagrid"
	data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true"
	url="/sms/school/book/list.do" iconCls="icon-save" sortOrder="asc"
	pagination="true">
	<thead>
		<tr>
			<th align="center" field="schoolName" width="120" sortable="false"
				resizable="true">校区</th>
			<th align="center" field="startNumber" width="70" sortable="false"
				resizable="true">开始号码</th>
			<th align="center" field="endNumber" width="70" sortable="false"
				resizable="true">结束号码</th>
			<th align="center" field="successCount" width="50" sortable="false"
				resizable="true">总数</th>
			<th align="center" field="signDate" width="150"
				data-options="formatter:formatterSignDate" sortable="false"
				resizable="true">报名日期</th>
			<th align="center" field="place" width="200" sortable="false"
				resizable="true">报名地址</th>

			<th align="center" field="createdOn" width="150" sortable="false"
				resizable="true" data-options="">发送时间</th>
	
		</tr>
	</thead>
</table>





