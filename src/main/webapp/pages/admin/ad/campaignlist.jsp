<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script>

    function formattermaterialOperation(val,row){
    	return "<a href='#' onclick=loadRemotePage('ad/addcampaign&pageId=admin&id="
    			+row.id+"')>编辑</a>&nbsp;<a href='#'>删除</a>";
    }
	
    function add(){
    	loadRemotePage('ad/addcampaign&pageId=admin');
    }

    function onlineCampaigns(){	
		batchCallApiOnGrid("campaignList", "/ad/campaign/online.do", "确认上线这些广告?", "上线成功");	
	}
	
	function offlineCampaigns(){
		batchCallApiOnGrid("campaignList", "/ad/campaign/offline.do", "确认下线这些广告?", "下线成功");
	}
	
    function approveCampaigns(){	
		batchCallApiOnGrid("campaignList", "/ad/campaign/approve.do", "确认审核这些广告?", "审核成功");	
	}
	

</script>

<div>
	<label style="font-size:20px">广告管理</label>
</div>
<br><br>

<!-- <div class="form_items">
	<button class="sub_btn_noWidth" onclick="add()">新建广告</button>
	 <button class="sub_btn_noWidth" onclick="onlineCampaigns();">批量上线</button>
	 <button class="sub_btn_noWidth" onclick="offlineCampaigns();">批量下线</button>
	 <button class="sub_btn_noWidth" onclick="approveCampaigns();">批量审核</button>
</div>
 -->

<div class="" style="display:none;">
	<label class="r-edit-label width100">物料名称:</label>
	<input class="width100" type="text" name="codeName" id="codeName"/> 

	<button onclick="search();">搜索</button>
</div>
<p></p>

<table id="campaignList" class="easyui-datagrid" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/ad/campaign/admin/list.do" iconCls="icon-save" sortOrder="asc" pagination="true" >
	<thead>
		<tr>
			<th field="CheckBox" checkbox="true"></th>
			<th align="center" field="adName" width="300" sortable="false" resizable="true">广告名称</th>
			<th align="center" field="userName" width="200" sortable="false" resizable="true">所属账户</th>
			<th align="center" field="isOnline" width="150" sortable="false" resizable="true" data-options="formatter:formatterCampaignIsOnline">投放状态</th>
			<th align="center" field="isApproved" width="150" sortable="false" resizable="true" data-options="formatter:formatterCampaignIsApproved">审核状态</th>			
			<th align="center" field="startDate" width="150" sortable="false" resizable="true" >开始时间</th>
			<th align="center" field="endDate" width="150" sortable="false" resizable="true" formatter="formatterADCampaignDateOperation">结束时间</th>
			<th align="center" field="views" width="150" sortable="false" resizable="true" >展示次数</th>
			<th align="center" field="clicks" width="150" sortable="false" resizable="true" >点击次数</th>
			<th align="center" field="downloads" width="150" sortable="false" resizable="true" >下载次数</th>
			<th align="center" field="chargeType" width="150" sortable="false" resizable="true" formatter="formatterchargeTypeOperation">计费方式</th>
		</tr>
	</thead>

</table>

<div id="codecontent" class="easyui-window" title="获取代码位代码" data-options="iconCls:'icon-save',modal:true, closed:true" style="width:500px;height:200px;padding:10px;">
        The window content.
</div>
