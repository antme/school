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


<script>

function formatterYDClickFloat(val,row){
	return parseFloat((row.originalClicks/row.originalViews)*100).toFixed(2) + "%";
}

</script>

<div class="row maTop70">
   <div class="tab-tool">
 		<span class="tab-m">
            <label>时间：</label>
            <input id="startDate" class="easyui-datebox" style="height:26px;"/>
            <label>—</label>
            <input id="endDate" class="easyui-datebox" style="height:26px;"/>
       </span> 
        <span class="tab-m">
           <label>平台</label>
           <select id="platform" class="easyui-combobox"   style="width:100px;height:30px;" name="platform">
			   <option value=""  selected>所有</option>
			   <option value="1"  >PC</option>
			   <option value="0"  >移动</option>
		   </select>
        </span>
        
       <span class="tab-m">
           <label>广告位</label>
           <input class="easyui-combobox"  type="text" style="width:100px;height:30px;" id="codeId" data-options="url:'/ad/report/adspace/admin/select.do',
                    method:'get',
                    valueField:'codeId',
                    textField:'codeId',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"/>
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
        <span class="tab-m">
           <label>广告源</label>
           <select id="adpName" class="easyui-combobox  "   style="width:100px;height:30px;" name="adpName">
          	   <option value=""  >所有</option>
			   <option value="baidu" selected >百度</option>
			   <option value="yd"  >赢纳</option>
			   <option value="self"  >本地广告</option>
		   </select>
        </span>
    </div>
</div>
 <span class="rightB">
    <label class="ti">预估收入</label>
    <label class="nu" id="income">-</label>
</span>
<div class="row">
    <div class="effect-i " style="display:none;">
        <span>
            <label class="ti">展现量</label>
            <label class="nu" id="views"></label>
            <label class="pj" id="avg_views"></label>
        </span>
        <span>
            <label class="ti">点击量</label>
            <label class="nu" id="clicks"></label>
            <label class="pj" id="avg_clicks"></label>
        </span>
        <span>
            <label class="ti">点击率</label>
            <label class="nu" id="click_ratio"></label>
            <label class="pj" id="avg_click_ratio"></label>
        </span>
        <span class="width16">
            <label class="ti">每千次展现收入</label>
            <label class="nu" id="cpm"></label>
            <label class="pj" id="avg_cpm"></label>
        </span>
        <span>
            <label class="ti">填充率</label>
            <label class="nu" id="fill_rate"></label>
            <label class="pj" id="avg_fill_rate"></label>
        </span>
        <span class="rightB">
            <label class="ti">预估收入</label>
            <label class="nu" id="income">-</label>
            <label class="pj" id="avg_income"></label>
        </span>
    </div>
</div>
<div class="row">
    <div class="effect ">
        <div class="effect-title">
            <label id="query_date_range"></label>
            <div class="radios">
                <span class="radioBtn select"  onclick="updateSelectedReportType(this,0)">展现量</span>
                <span class="radioBtn" onclick="updateSelectedReportType(this,1)">点击量</span>
                <span class="radioBtn" onclick="updateSelectedReportType(this,2)">点击率</span>
           <!--      <span class="radioBtn" onclick="updateSelectedReportType(this,3)">每千次展现收入</span>
                <span class="radioBtn" onclick="updateSelectedReportType(this,4)">填充率</span>
                <span class="radioBtn" onclick="updateSelectedReportType(this,5)">预估收入</span> -->
                 <span class="radioBtn" onclick="updateSelectedReportType(this,6)">UV</span>
                 <span class="radioBtn" onclick="updateSelectedReportType(this,7)">AP数</span>
            </div>
        </div>
        <div id="effect" class="effect-table" style="height:300px;"></div>
    </div>
</div>
<div class="row">
    <table id="reportlist" class="easyui-datagrid" style="height:auto"
            data-options="striped:true,singleSelect:true,checkOnSelect:false,collapsible:false,fitColumns:true,pagination:true">
        <thead>
            <tr>
                <th data-options="field:'statDate',width:150,align:'center',sortable:true">时间</th>
                <th data-options="field:'originalViews',width:150,align:'center',sortable:true">YD展现数</th>
                <th data-options="field:'originalClicks',width:150,align:'center',sortable:true">YD点击量</th>
                <th data-options="field:'clickRatio1',width:150,align:'center',sortable:true" formatter="formatterYDClickFloat">YD点击率</th>                
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
	
	$('#codeId').combobox({
		onSelect: function(record){
			searchAdSpaceReport();
		}
	});
	
	$('#adpName').combobox({
		onSelect: function(record){
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
	
	$('#platform').combobox({
		onSelect: function(record){
			searchAdSpaceReport();
		}
	});
});

function loadSpaceId(){
	$("#codeId").combobox("setValue", spaceId);
}


</script>
</div>

