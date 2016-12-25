<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<style>

.highcharts-axis-labels text{
    font-size: 12px !important;
}
</style>
<div>
	<label style="font-size:20px">搜索统计数据查询</label>
	
</div>
<br><br>

<div class="content">
    <div class="tr_line">
         <span class="td_line">
            <label>渠道：</label>
            <input id="channel" class="easyui-combobox  width150" style="height:26px;" type="text" name="channel" data-options="url:'/ad/report/listmybaidusearchchannel.do',
                    method:'post',
                    valueField:'coopName',
                    textField:'coopName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
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
        
		<sapn class="postion">
			  <select onchange="select_searchvalue(this.value)" class="width150 height26" style="font-size:18px">
			  <option value="广告统计数据查询">广告统计数据查询</option>
			  <option value="搜索统计数据查询" selected>搜索统计数据查询</option>
			  </select>
		</sapn>
    </div>
    
    <div class="page_tip" style="margin-left:10px;color:#aaa;font-size:10px">说明：如果不按媒体来搜索报表，每日数据为所有媒体的统计数累加</div><br/>
    
    <div class="tr_line">
        
        <span class="td_line radios">
            <input id="Show" type="radio" name="check" checked="checked" value="0" onclick="updateSelectedReportType(0);"/>
            <label for="Show">展现量</label>
            <input id="clickQuantity" type="radio" name="check" value="1" onclick="updateSelectedReportType(1);"/>
            <label for="clickQuantity">点击量</label>
            <input id="clickRate" type="radio"  name="check" value="2" onclick="updateSelectedReportType(2);"/>
            <label for="clickRate">点击率</label>
            <input id="showIncome" type="radio" name="check" value="3" onclick="updateSelectedReportType(3);"/>
            <label for="showIncome">每千次展现收入</label>
            <input id="forecast" type="radio" name="check" value="5" onclick="updateSelectedReportType(5);"/>
            <label for="forecast">预估收入</label>
        </span>
    </div>
    <div id="container" class="tr_line" style="display:none;">
   
    </div>
    
    

<table id="statList" url="/ad/report/baidusearch/search.do" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true" iconCls="icon-save"
	sortOrder="asc" pagination="true" >
	<thead>
		<tr>
			<th align="center" field="statDate" width="200" sortable="false" resizable="true">时间</th>
			<th align="center" field="media" width="200" sortable="false" resizable="true">域名</th>
			<th align="center" field="channel" width="200" sortable="false" resizable="true">渠道</th>
			<th align="center" field="views" width="200" sortable="false" resizable="true">展现量</th>
			<th align="center" field="clicks" width="200" sortable="false" resizable="true">点击量</th>
			<th align="center" field="clickRatio" width="200" sortable="false" resizable="true"  data-options="formatter:formatterFloat">点击率</th>
			<th align="center" field="cpm" width="200" sortable="false" resizable="true"  data-options="formatter:formatterMoneyOperation">每千次展现收入</th>
			<th align="center" field="income" width="200" sortable="false" resizable="true"  data-options="formatter:formatterMoneyOperation">预估收入</th>
		</tr>
	</thead>
</table>


    <script>
    
    	function reportPageFilter(data){
    		var responseData = {};
    		responseData.rows = data;
    		return pagerFilter(responseData, "statList");
    			
    	}
        var selected = "展现量";
     	var reportType = 0;
        
        function updateSelectedReportType(rtype){
        	reportType = rtype;
        }
        var serieName = "展现量";
    	var unit = "次";
    	
   		function searchReport(){
   		 var channel =$("#channel").combobox('getText');
    	
    	 if(reportType == 0){        		 
    		 unit = "次";
    		 serieName = "展现量";
    	 }else if(reportType == 1){
    		 unit = "次";
    		 serieName = "点击量";
    	 }else if(reportType == 2){
    		 unit = "百分比";
    		 serieName = "点击率";
    	 }else if(reportType == 3){
    		 unit = "元";
    		 serieName = "每千次展现收入";
    	 }else if(reportType == 5){
    		 unit = "元";
    		 serieName = "预估收入";
    	 }
    	 
    	 
    	 
    	 var start_time = $("#startTime").datebox("getValue");
    	 var end_time = $("#endTime").datebox("getValue");
    	 postAjaxRequest("/ad/report/baidusearch/sum.do",{channel:channel,reportType:reportType,startDate:start_time,endDate:end_time}, function(data){
 			drowReport(data);
 			
 			$('#statList').datagrid({
 				queryParams: {channel:channel,reportType:reportType,startDate:start_time,endDate:end_time}
 			});
 			
 			
 		});
   		}
   		
      	
        $(".radios").find("input").click(function(){
        	searchReport();
        });
        
        $(function () {
    		postAjaxRequest("/ad/report/sum.do",{}, function(data){
    			drowReport(data);
    		});
        });
    	
    	
        function drowReport(data){
        	
        	$('#startTime').datebox('setValue', data.data.startDate);
        	$('#endTime').datebox('setValue', data.data.endDate);
        	
        	var statDate=data.data.statDate;
        	
        	var statisticList=data.data.statisticList;
        	
        
      
        	//$('#statList').datagrid('loadData',data.data.reportList);
        	
			if(data.data.displayMediaName){
				$('#statList').datagrid('showColumn',"media");
        	}else{
        		$('#statList').datagrid('hideColumn',"media");
        	}
    		

        	
        	$('#container').highcharts({
                title: {
                    text: '广告搜索报告',
                    x: -20 //center
                },
                subtitle: {
                    text: unit,
                    x: -20
                },
                xAxis: {
                    categories: statDate
                },
                yAxis: {
                    title: {
                        text: unit
                    },
                    plotLines: [{
                        value: 0,
                        width: 1,
                        color: '#808080'
                    }]
                },
                tooltip: {
                    valueSuffix: '                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         '
                },
                legend: {
                    layout: 'vertical',
                    align: 'right',
                    verticalAlign: 'middle',
                    borderWidth: 0
                },
                series: [{
                    name: serieName,
                    data: statisticList
                }]
            });
         }
    </script>
</div>

