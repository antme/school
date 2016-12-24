<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<style>

.highcharts-axis-labels text{
    font-size: 12px !important;
}
</style>

<div>
	<label style="font-size:20px">Page View数据查询 </label><a style="float:right; margin-right:100px;" href="index.jsp?p=admin/ADReport/pfqreport">漂浮球查询</a>
	
</div>
<br><br>

<div class="content">

    <div class="tr_line">


         <span class="td_line">
            <label>渠道号：</label>
            <input id="channelId"   type="text" name="channelId" ></input>
         </span>
         
         <span class="td_line">
            <label>页面：</label>
            <input id="pageUrl"   type="text" name="pageUrl" style="width:300px;"></input>
         </span>

        
         <span class="td_line">
            <label>时间：</label>
            <input id="startTime" class="easyui-datebox" style="height:26px;"/>
            <label>—</label>
            <input id="endTime" class="easyui-datebox" style="height:26px;"/>
        </span>
        <span class="td_line">
        	<button class="sub_btn_noWidth" onclick="searchReport();">搜索</button>
        </span>
        

    </div>
    

    
    

<table id="statList" url="/ad/report/pv/search.do" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true" iconCls="icon-save"
	sortOrder="asc" pagination="true" >
	<thead>
		<tr>
			<th align="center" field="reportDate" width="200" sortable="false" resizable="true">时间</th>
			<th align="center" field="pv" width="200" sortable="false" resizable="true">PV</th>
			<th align="center" field="uv" width="200" sortable="false" resizable="true">UV</th>
			<th align="center" field="ips" width="200" sortable="false" resizable="true">独立IP</th>
			
		</tr>
	</thead>

    
</table>


<script>
   	function searchReport(){
   		 var channelId =$("#channelId").val();
   		 var pageUrl = $("#pageUrl").val();    	 
    	 
    	 var start_time = $("#startTime").datebox("getValue");
    	 var end_time = $("#endTime").datebox("getValue");
    		
			$('#statList').datagrid({
				queryParams: {channelId:channelId, pageUrl:pageUrl,startDate:start_time,endDate:end_time}
			});
   		}
  
</script>
</div>

