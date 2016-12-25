<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>



<div>
	<label style="font-size:20px">广告统计数据查询</label>
	
</div>
<br><br>

<div class="content">
    <div class="tr_line">

        
         <span class="td_line">
            <label>时间：</label>
            <input id="startTime" class="easyui-datebox" style="height:26px;"/>
            <label>—</label>
            <input id="endTime" class="easyui-datebox" style="height:26px;"/>
        </span>
        <span class="td_line">
        	<button class="sub_btn_noWidth" onclick="drowReport();">搜索</button>
        </span>

    </div>

    
    

<table id="campaignReportList" url="/ad/report/campaign/list.do" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true" iconCls="icon-save"
	sortOrder="asc" pagination="true" >
	<thead>
		<tr>
			<th align="center" field="statDate" width="200" sortable="false" resizable="true">时间</th>
			<th align="center" field="campaignName" width="200" sortable="false" resizable="true">广告名称</th>
			<th align="center" field="views" width="200" sortable="false" resizable="true">展现量</th>
			<th align="center" field="clicks" width="200" sortable="false" resizable="true">点击量</th>
		</tr>
	</thead>

    
    
</table>


    <script>

    	
        function drowReport(){
      
       		var start_time = $("#startTime").datebox("getValue");
    	 	var end_time = $("#endTime").datebox("getValue");
    	 
    		$('#campaignReportList').datagrid({
 				queryParams: {startDate:start_time,endDate:end_time}
 			});

         }
    </script>
</div>

