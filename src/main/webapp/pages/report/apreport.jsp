<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<style>

.highcharts-axis-labels text{
    font-size: 12px !important;
}
</style>

<style>
.page-content{position:relative;}
.page-bar{display:none;}
.page-title{width:100%;height:70px;line-height:70px;position:absolute;top:0px;left:0px;background:#fff;}
.page-title label{float:left;line-height:70px;text-indent:20px;font-size:24px;}
.page-title a{float:right;margin-right:20px;}
</style>

<script type="text/javascript" src="/resources/js/space.js"></script>
<script type="text/javascript" src="/resources/js/echarts-all.js"></script>




<div class="row maTop70">
   <div class="tab-tool">
 		<span class="tab-m">
            <label>时间：</label>
            <input id="startDate" class="easyui-datebox" style="height:26px;"/>
            <label>—</label>
            <input id="endDate" class="easyui-datebox" style="height:26px;"/>
            <input id="adpId" type="hidden" id="adpId" value="adpId"/>
            
       </span>      
        <span class="tab-m">
           <label>帐号</label>
           <input class="easyui-combobox"  type="text" style="width:100px;height:30px;" id="name" data-options="url:'/ad/report/admin/selectuser.do',
                    method:'get',
                    valueField:'id',
                    textField:'name',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"/>
        </span>
        
    </div>
</div>

<div class="row">
    <table id="reportlist" class="easyui-datagrid" style="height:auto"
            data-options="striped:true,singleSelect:true,checkOnSelect:false,collapsible:false,fitColumns:true,pagination:true">
        <thead>
            <tr>
                <th data-options="field:'statDate',width:150,align:'center',sortable:true">时间</th>
                <th data-options="field:'views',width:250,align:'center',sortable:true">展现量</th>
                <th data-options="field:'clicks',width:250,align:'center',sortable:true">点击量</th>
                <th data-options="field:'clickRatio',width:200,align:'center',sortable:true" formatter="formatterClickFloat">点击率</th>
                <th data-options="field:'displayIncome',width:150,align:'center',sortable:true" formatter="formatterMoneyFloat">收益</th>
                <th data-options="field:'cpm',width:150,align:'center',sortable:true" formatter="formatterMoneyFloat">CPM</th>
              	<th data-options="field:'uv',width:150,align:'center',sortable:true">UV</th>
               	<th data-options="field:'apNum',width:150,align:'center',sortable:true">AP数</th>
            </tr>
        </thead>
       
    </table>
</div>


<script>

var spaceId= "";


$(document).ready(function() { 
	searchAdSpaceReport();
	
	
	$('#name').combobox({
		onSelect: function(record){
			var url = '/ad/report/adspace/admin/select.do?ownerId='+$('#name').combobox('getValue');
            $('#codeId').combobox('reload', url);
			searchAdSpaceReport();
		}
	});
	
	
	$('#startDate').datebox({
		onSelect: function(date){
			searchAdSpaceReport();
		}
	});
	
	$('#endDate').datebox({
		onSelect: function(date){
			searchAdSpaceReport();
		}
	});

});




</script>
</div>

