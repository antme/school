<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script>
	


	function formatterOperation(val, row) {
		var a = "";
		if (row.successCount < row.totalSend) {
			a = '<a style="margin-left:5px" onclick="exportData(\'' + row.id
					+ '\');" href="#"> 下载失败号码 </a>';
		}
		return a;
	}

	function sendSms() {
		var schoolId = $("#schoo_select").combobox('getValue');
		var startNumber = $("#startNumber").val();
		var endNumber = $("#endNumber").val();
		var place = $("#place").val();

		var signDate = $("#signDate").datebox('getValue');
		var startTime = $("#startTime").timespinner('getValue');
		var endTime = $("#endTime").timespinner('getValue');

		var can_submit = true;
		if (!schoolId) {
			$.messager.alert('提示', '请选择校区');
			can_submit = false;
		}

		else if (!startNumber) {
			$.messager.alert('提示', '请输入号段开始');
			can_submit = false;
		}

		else if (!endNumber) {
			$.messager.alert('提示', '请输入号段结束');
			can_submit = false;
		}

		else if (!place) {
			$.messager.alert('提示', '请输入报名地点');
			can_submit = false;
		} else if (!signDate) {
			$.messager.alert('提示', '请选择报名日期');
			can_submit = false;
		} else if (!startTime) {
			$.messager.alert('提示', '请选择报名开始时间');
			can_submit = false;
		} else if (!endTime) {
			$.messager.alert('提示', '请选择报名结束时间');
			can_submit = false;
		} else if (startNumber > endNumber) {
			$.messager.alert('提示', '结束号段不能小于开始号段');
			can_submit = false;
		}

		var data = {
			signDate : signDate,
			startTime : startTime,
			endTime : endTime,
			schoolId : schoolId,
			place : place,
			endNumber : endNumber,
			startNumber : startNumber

		};

		if (can_submit) {
			$.messager.confirm('发送短信', '确认发送短信通知？', function(r) {
				if (r) {

					postAjaxRequest('/sms/school/book/notice.do', data,
							function(data) {
								$.messager.alert('提示', '发送成功');
								$("#datalist").datagrid('reload');
							});
				}
			});
		}

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
	<label style="font-size: 20px">短信通知管理</label>
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
		value="" style="height: 26px; width: 50px;" id="endNumber" /> <label
		style="margin-left: 10px;">校区地址: </label> <input
		class=" easyui-validatebox" name="place" value=""
		style="height: 26px; width: 350px;" id="place" />
	</span> <br> <br> <span class="tab-m"> <label> 报名时间:
	</label> <input class=" easyui-validatebox easyui-datebox" type="number"
		name="cpc" value="" style="height: 26px;" id="signDate" /> <input
		class="easyui-timespinner" label="Start Time:" labelPosition="top"
		value="09:00" style="width: 100%; height: 30px;" id="startTime" /> <label>
			- </label> <input class="easyui-timespinner" label="End Time:"
		labelPosition="top" value="18:00" style="width: 100%; height: 30px;"
		id="endTime" />
	</span> </span>

	<button class="search_btn_noWidth" onclick="sendSms();">发送短信</button>
</div>


<p></p>

<label>由于短信发送有延迟，建议在发送6个小时候才下载失败号码</label>
<table id="datalist" class="easyui-datagrid"
	data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true"
	url="/sms/school/book/list.do" iconCls="icon-save" sortOrder="asc"
	pagination="true">
	<thead>
		<tr>
			<th align="center" field="schoolName" width="100" sortable="false"
				resizable="true">校区</th>
			<th align="center" field="startNumber" width="80" sortable="false"
				resizable="true">开始号码</th>
			<th align="center" field="endNumber" width="80" sortable="false"
				resizable="true">结束号码</th>
			<th align="center" field="successCount" width="80" sortable="false"
				resizable="true">成功数量</th>
			<th align="center" field="totalSend" width="80" sortable="false"
				resizable="true">发送总数</th>

			<th align="center" field="signDate" width="80"
				data-options="formatter:formatterSignDate" sortable="false"
				resizable="true">报名日期</th>
			<th align="center" field="place" width="200" sortable="false"
				resizable="true">报名地址</th>

			<th align="center" field="createdOn" width="150" sortable="false"
				resizable="true" data-options="">发送时间</th>
			<th align="center"
				data-options="field:'id',formatter:formatterOperation,width:100">操作</th>
		</tr>
	</thead>
</table>





