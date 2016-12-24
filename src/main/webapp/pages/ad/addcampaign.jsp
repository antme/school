<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String id = request.getParameter("id"); %>
<% String pageid = request.getParameter("pageId"); %>
<script src="resources/js/nicEdit.js" type="text/javascript"></script>
<script>
	   var id="<%=id%>";
	   var pageid="<%=pageid%>";
	   if(id!="null"){
		   postAjaxRequest("/ad/campaign/load.do", {id:id}, function(data){
               $("#campaign_form").form('load',data.data);
		   });
	   }


	 $(document).ready(function(){
		$(".cb").click(function(){
			  $(".c").hide();
			  $(".g").show();
			  $("input[name=totalLimitClicks]").val("");
			  $("input[name=totalLimitViews]").val("");
		});
		$(".gb").click(function(){
			  $(".g").hide();
			  $(".c").show();
		});
		
		$(".s-date").click(function(){
			  $(".da").hide();
			  $(".a-date").show();
		});
		$(".d-date").click(function(){
			  $(".da").show();
			  $(".a-date").hide();
			  $("#endDate").datetimebox('setValue','');
		});
		
		$("#changesType").combobox({
			onSelect:function(){
				if($(this).combobox('getValue')=="1"){
					$("#cpc").show();
					$("#cpm").hide();
				}else{
					$("#cpc").hide();
					$("#cpm").show();
				}
			}
		});
		$("#clickOrview").combobox({
			onSelect:function(){
				if($(this).combobox('getValue')=="1"){
					$("#dailyLimitClicks").show();
					$("#dailyLimitViews").hide();
				}else{
					$("#dailyLimitClicks").hide();
					$("#dailyLimitViews").show();
				}
			}
		});
		 initFormSubmit('campaign_form', "/ad/campaign/create.do", "添加广告", function(){
			 alert("提交成功");
			 if(pageid && pageid == "admin"){
				loadRemotePage('admin/ad/campaignlist');
			}else{
				loadRemotePage('ad/campaignlist');
			}
	     });
		 
		 if(id!="null"){
			 $("#addnewcampaign").html("修改广告信息");
		 }else {
			 $("#addnewcampaign").text("新建广告");
		 }
	  });
</script>

<div style="padding: 10px 60px 20px 60px">

	   <div>
			<label id="addnewcampaign" style="font-size:20px"></label>
		</div>
		<br><br>
	   
	   <form id="campaign_form" method="post" autocomplete="off" novalidate>
	   <input type="hidden" name="id"/>
	   <div id="adtext" class="form_items">
		   <label class="r-edit-label width100">广告名称:</label> 
		   <input class="width200 easyui-validatebox" type="text" name="adName" required/>
	   </div>
	   <div id="adtext" class="form_items">
		   <label class="r-edit-label width100">开始日期:</label> 
		   <input class="width200 easyui-datetimebox" type="text" required name="startDate" style="height:26px;" />
	   </div>
	   <div id="adtext" class="form_items">
		   <label class="r-edit-label width100">结束日期:</label> 
		   <label class="da">不设时间 |</label><a class="a-href da s-date">设定时间</a>
		   <a class="a-date">
		       <input id="endDate" class="width200 easyui-datetimebox" type="text" name="endDate" style="height:26px;" />
		   </a>
		   <a class="a-href a-date d-date">不设时间</a>
	   </div>
	   <div id="adtext" class="form_items">
		   <label class="r-edit-label width100">说明:</label> 
           <textarea class="width500" name="description" rows="5" cols=""></textarea>
	   </div>
	   <div class="form_items">
		   <label class="r-edit-label width100">广告物料:</label> 
		   <input class="easyui-combobox width200" type="text" name="materialId" data-options="url:'/ad/material/mine.do',
                    method:'post',
                    valueField:'id',
                    textField:'materialName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}" style="height:26px;"/>
	   </div>
	   
	   <div class="form_items">
		   <label class="r-edit-label width100">计费方式:</label> 
		   <select id="changesType" class="easyui-combobox width200" name="chargeType" style="height:26px;">
		        <option value="1" selected>按点击付费</option>
		        <option value="2">按千次展示付费</option>
		   </select>
	   </div>
	   
	   <div id="cpc" class="form_items">
		   <label class="r-edit-label width100">每次点击费用:</label> 
		   <input class="width100 easyui-validatebox" type="text" name="cpc" value="0" required="required" style="height:26px;" validType="isNumber"/>
		   <label class="r-edit-label width100">点击数量:</label> 
		   <input class="width100 easyui-validatebox c" type="text" value="0" name="totalLimitClicks" validType="number"/>
		   <label class="c">次</label>
		   <label class="g">不限</label>
		   <a class="a-href gb g">更改数量</a>
		   <a class="a-href cb c">不限</a>
	   </div>
	   
	   <div id="cpm" class="form_items">
		   <label class="r-edit-label width100">千次展示费用:</label> 
		   <input class=" width100 easyui-validatebox" type="text" name="cpm" value="0" required="required" style="height:26px;" validType="isNumber"/>
		   <label class="r-edit-label width100">展示数量:</label> 
		   <input class="width100 easyui-validatebox c" type="text" value="0" name="totalLimitViews" validType="number"/>
		   <label class="c">次</label>
		   <label class="g">不限</label>
		   <a class="a-href gb g">更改数量</a>
		   <a class="a-href cb c">不限</a>
	   </div>
	   
	   <div class="form_items">
		   <label class="r-edit-label width100">日投放预算:</label> 
		   <label>每天</label>
		   <select id="clickOrview" class="easyui-combobox width60" style="height:26px;">
		       <option value="1" selected>点击</option>
		       <option value="2">展示</option>
		   </select>
		   <label><=</label>
		   <input id="dailyLimitClicks" class="width100 easyui-validatebox" type="text" name="dailyLimitClicks" value="0" validType="number"/>
		   <input id="dailyLimitViews" class="width100 easyui-validatebox" type="text" name="dailyLimitViews" value="0"  style="display:none;" validType="number"/>
		   <label>次</label>
	   </div>
	   
	   <div class="form_items_btn">
		       <label class="r-edit-label width100">&nbsp;</label> 
		       <input class="search_btn_noWidth" type="submit" value="确定" />
		</div>
   </form>
</div>


<script>

</script>

<div id="codecontent" class="easyui-window" title="获取代码位代码" data-options="iconCls:'icon-save',modal:true, closed:true" style="width:500px;height:200px;padding:10px;">
        The window content.
</div>
