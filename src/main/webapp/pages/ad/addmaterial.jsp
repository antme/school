<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String id = request.getParameter("id"); %>
<% String pageid = request.getParameter("pageId"); %>
<script src="resources/js/nicEdit.js" type="text/javascript"></script>
<script>
	   var id="<%=id%>";
	   var pageid="<%=pageid%>";
	   $(document).ready(function(){
		 initFormSubmit('materielltexts_form', "/ad/material/create.do", "添加广告物料", function(){
				 alert("提交成功");
				if(pageid && pageid == "admin"){
					loadRemotePage('admin/ad/materiallist');
				}else{
					loadRemotePage('ad/materiallist');
				}
		 });
		 
		 initFormSubmit('materiellimgs_form', "/ad/material/create.do", "添加广告物料", function(){
			 alert("提交成功");
			 if(pageid && pageid == "admin"){
				 loadRemotePage('admin/ad/materiallist');
			 }else{
			 	loadRemotePage('ad/materiallist');
			 }
	 	});
		 
		 initFormSubmit('materiellcode_form', "/ad/material/create.do", "添加广告物料", function(){
			 alert("提交成功");
			 if(pageid && pageid == "admin"){
				 loadRemotePage('admin/ad/materiallist');
			 }else{
			 	loadRemotePage('ad/materiallist');
			 }
		 });
		 
		 initFormSubmit('materiellapp_form', "/ad/material/create.do", "添加广告物料", function(){
			 alert("提交成功");
			 if(pageid && pageid == "admin"){
				 loadRemotePage('admin/ad/materiallist');
			 }else{
			 	loadRemotePage('ad/materiallist');
			 }
	 	});
		 
		 if(id!="null"){
			 $("#addnewmateriel").html("修改广告物料信息");
		 }else {
			 $("#addnewmateriel").text("新增广告物料");
		 }
		 
	   });
	   
	   if(id!="null"){
		   postAjaxRequest("/ad/material/load.do", {id:id}, function(data){
			   $("#materialName").val(data.data.material.materialName);
			   if(data.data.material.materialType=="1"){
				   $("#materialType").find("input[value=1]").click();
				   $("#materielltexts_form").form("load",data.data.material);
				   $('#picFileUpload_ad1').attr('src', data.data.picUrl);
			   }
			   if(data.data.material.materialType=="2"){
				   $("#materialType").find("input[value=2]").click();
				   $("#materiellimgs_form").form("load",data.data.material);
				   var picUrl = data.data.picUrl;
				   $('#picFileUpload_ad2').attr('src', data.data.picUrl);
			   }
			   if(data.data.material.materialType=="3"){
				   $("#materialType").find("input[value=3]").click();
				   $("#materiellcode_form").form("load",data.data.material);
			   }
			   if(data.data.material.materialType=="4" || data.data.material.materialType=="5"){
				   if(data.data.material.materialType=="4"){
				  	   $("#materialType").find("input[value=4]").click();
				   }else{
					   $("#materialType").find("input[value=5]").click();  
				   }
				   $("#materiellapp_form").form("load",data.data.material);
				   //$("#materiellapp_form").form("load",data.data.appInfo); 
				   $("input[name=appName]").val(data.data.appInfo.appName);
				   $("input[name=version]").val(data.data.appInfo.version);
				   $("input[name=size]").val(data.data.appInfo.size);
				   $("input[name=adapter]").val(data.data.appInfo.adapter);
				   $("input[name=developer]").val(data.data.appInfo.developer);
				   $("input[name=description]").val(data.data.appInfo.description);
				   $("input[name=downloadUrl]").val(data.data.appInfo.downloadUrl);
				   if(data.data.appInfo.campaignImg){
					   $("#previewCampaignImg").show();
					   $("#previewCampaignImg").append("<img src="+data.data.appInfo.campaignImg+" />");
				   }
			   }	
			   
			   $("#materialType").hide();
		   });
	   }
	   
	   function materielSubmit(obj){
			 var materialName = $("#materialName").val();
			 var materialType = $("#materialType").find("input[name='materialType']:checked").val();
			 if(materialName!="" && materialType!=""){
				 $("#"+obj).find("input[name=materialName]").val(materialName);
				 $("#"+obj).find("input[name=materialType]").val(materialType);
			 }else{
				 alert("请输入物料名称！");
				 return false;
			 }
			
		  }
	  function selectType(obj,clas){
	       $("."+clas).hide();
	       $("#"+obj).show();
	  }  
	  bkLib.onDomLoaded(function() {
          new nicEditor({ fullPanel: true }).panelInstance('txtContent');
      });
	    
</script>

<div style="padding: 10px 60px 20px 60px">

		<input class="" name="id" type="hidden" />
		
		<div>
			<label id="addnewmateriel" style="font-size:20px"></label>
		</div>
		<br><br>

       <div class="form_items">
		   <label class="r-edit-label width100">广告物料名称:</label> 
		   <input id="materialName" class="easyui-validatebox textbox width300" type="text" name="materialName" required></input>
	   </div>
	   <div id="materialType" class="form_items">
		   <label class="r-edit-label width100">广告类型:</label> 
		   <input id="text" type="radio" value='1' autocomplete="off" name="materialType" checked onclick="selectType('materielltexts','materiellStyle')"/>
		   <label for="text">图文</label>
		   <input id="images" type="radio" value='2'autocomplete="off"  name="materialType"  onclick="selectType('materiellimgs','materiellStyle')"/>
		   <label for="images">图片</label>
		   <!-- <input id="media" type="radio" value='3' autocomplete="off" name="materialType"  onclick="selectType('materiellcode','materiellStyle')"/>
		   <label for="media">富媒体</label> -->
		   <input id="apps" type="radio" value='4' autocomplete="off" name="materialType" onclick="selectType('materiellapp','materiellStyle')"/>
		   <label for="apps">应用下载</label>
		   <input id="h5" type="radio" value='5' autocomplete="off" name="materialType" onclick="selectType('materiellapp','materiellStyle')"/>
		   <label for="apps">h5游戏</label>
	   </div>
	   
	   
	<div id="materielltexts" class="materiellStyle">
	   <form id="materielltexts_form" method="post" autocomplete="off" novalidate action="/ad/material/create.do" enctype="multipart/form-data">
	   <input type="hidden" name="id"/>
	   <input type="hidden" name="materialName"/>
	   <input type="hidden" name="materialType"/>
	   <div class="form_items">
		   <label class="r-edit-label width100">广告语:</label> 
		   <input class="width300 easyui-validatebox" type="text" name="materialContent" required/>
	   </div>
	  
	   <div class="form_items">
		   <label class="r-edit-label width100">Icon:</label> 
		   <input class="width300 easyui-validatebox" type="file" name="picFileUpload"/>
		   <br> 
		   <label class="r-edit-label width100">&nbsp;</label> 
		   <label style="color:#aaa;font-size:10px">请上传正确尺寸的App图标，如72x72、120x120等</label>
		    <br>
		   <img alt="" src="" id="picFileUpload_ad1" height="120" widht="120" style="margin-left:150px;"/>
	   </div>
	   <div class="form_items">
		   <label class="r-edit-label width100">点击链接:</label> 
		   <input class="easyui-validatebox textbox width300" type="text" name="targetURL" required="required" />
	   </div>
	   <div class="form_items">
		       <label class="r-edit-label width100" >&nbsp;</label> 
		       <button class="search_btn_noWidth" onclick="materielSubmit('materielltexts_form')" >确定</button>
		   </div>
	   </form>
	</div>
	   
	<div id="materiellimgs" class="materiellStyle" style="display:none;">
	 <form id="materiellimgs_form" method="post" autocomplete="off" novalidate  action="/ad/material/create.do" enctype="multipart/form-data">
	   <input type="hidden" name="id"/>
	   <input type="hidden" name="materialName"/>
	   <input type="hidden" name="materialType"/>
	   <div class="form_items">
		   <label class="r-edit-label width100">图片文件:</label> 
		   <input id="upload" name="picFrom" type="radio"  value="1" autocomplete="off" checked="checked" onclick="selectType('files','imgHref')"/>
		   <label for="upload">上传</label>
		   <input id="remote" name="picFrom" type="radio" value="2" autocomplete="off" onclick="selectType('href','imgHref')"/>
		   <label for="remote">远程</label>
		    <br>
		   <img alt="" src="" id="picFileUpload_ad2" height="120" widht="120" style="margin-left:150px;"/>
	   </div>
	   <div class="form_items">
		   <label class="r-edit-label width100">&nbsp;</label> 
		   <input id="files" class="width300 imgHref easyui-validatebox" type="file" name="picFileUpload"/>
		   <input id="href" class="width300 imgHref easyui-validatebox" type="text" name="picUrl" style="display:none;" required/>
	   </div>
	    <div class="form_items">
		   <label class="r-edit-label width100">图片描述:</label> 
		   <input class="easyui-validatebox textbox width300" type="text" name="description"  />
	    </div>
	    <div class="form_items">
		   <label class="r-edit-label width100">点击链接:</label> 
		   <input class="easyui-validatebox textbox width300" type="text" name="targetURL" required="required" />
	   </div>
	   <div class="form_items_btn">
		       <label class="r-edit-label width100" >&nbsp;</label> 
		       <input type="submit" class="search_btn_noWidth" onclick="materielSubmit('materiellimgs_form')" value="确定" />
		   </div>
	   </form>
	 </div>
	   
	 <div id="materiellcode" class="materiellStyle" style="display:none;">
	    <form id="materiellcode_form" method="post" autocomplete="off" novalidate action="/ad/material/create.do" enctype="multipart/form-data">
	       <input type="hidden" name="id"/>
	       <input type="hidden" name="materialName"/>
	       <input type="hidden" name="materialType"/>
	   	   <div class="form_items">
	          <label class="r-edit-label width100">代码:</label> 
	          <div class="rights_txt">
	               <textarea id="txtContent" class="easyui-validatebox textbox width500" style="height:100px;"  name="materialContent" required="required"></textarea>
	          </div>
		   </div>
		   <div class="form_items">
		       <label class="r-edit-label width100">点击链接:</label> 
		       <input class="easyui-validatebox textbox width300" type="text" name="targetURL" required="required" />
	       </div>
	       <div class="form_items_btn">
		       <label class="r-edit-label width100" >&nbsp;</label> 
		       <input type="submit" class="search_btn_noWidth" onclick="materielSubmit('materiellcode_form')" value="确定" />
		   </div>
		</form>
	 </div>
	 
	  <div id="materiellapp" class="materiellStyle" style="display:none;" >
	     <form id="materiellapp_form" method="post" autocomplete="off" novalidate action="/ad/material/create.do" enctype="multipart/form-data">
	       <input type="hidden" name="id"/>
	       <input type="hidden" name="materialName"/>
	       <input type="hidden" name="materialType"/>
	       <input type="hidden" name="appId"/>
	   	   <div class="form_items">
	       		<label class="r-edit-label width100">app名称:</label> 
		   		<input class="r-edit-label width300 easyui-validatebox" name="appName" data-options="required:true" />
		   </div>
		   <div class="form_items">
	       		<label class="r-edit-label width100">版本:</label> 
		   		<input class="r-edit-label width300 easyui-validatebox" name="version" data-options="required:true"/>
		   </div>
		   <div class="form_items">
	       		<label class="r-edit-label width100">大小:</label> 
		   		<input class="r-edit-label width300 easyui-validatebox" name="size" data-options="required:true"/>
		   </div>
		   <div class="form_items">
	       		<label class="r-edit-label width100">适配机型:</label> 
		   		<input class="r-edit-label width300 easyui-validatebox" name="adapter" data-options="required:true"/>
		   </div>
		   <div class="form_items">
	       		<label class="r-edit-label width100">开发者:</label> 
		   		<input class="r-edit-label width300 easyui-validatebox" name="developer" />
		   </div>
		   <div class="form_items">
	       		<label class="r-edit-label width100">应用简介:</label> 
		   		<textarea class="r-edit-label width300 easyui-validatebox" name="description" rows="5"></textarea>
		   </div>
		   
		   <div class="form_items">
	       		<label class="r-edit-label width100">icon:</label> 
		   		<input class="r-edit-label width300" name="appIcon" type="file"  data-options="required:true"/>
		   </div>
		   <div class="form_items">
	       		<label class="r-edit-label width100">广告横幅:</label> 
		   		<input class="r-edit-label width300" name="campaignImg" type="file"  data-options="required:true"/>
		   		<div id="previewCampaignImg"></div>
		   </div>
		   <div class="form_items">
	       		<label class="r-edit-label width100">APP地址:</label> 
		   		<input class="r-edit-label width300" name=downloadUrl type="text"  data-options="required:true"/>
		   </div>
		   <div class="form_items">
	       		<label class="r-edit-label width100">图片一:</label> 
		   		<input class="r-edit-label width300" name=imageUrl1 type="file"   data-options="required:true"/>
		   </div>
		   <div class="form_items">
	       		<label class="r-edit-label width100">图片二:</label> 
		   		<input class="r-edit-label width300" name=imageUrl2 type="file"  />
		   </div>
		   <div class="form_items">
	       		<label class="r-edit-label width100">图片三:</label> 
		   		<input class="r-edit-label width300" name=imageUrl3 type="file"  />
		   </div>
		   <div class="form_items">
	       		<label class="r-edit-label width100">图片四:</label> 
		   		<input class="r-edit-label width300" name=imageUrl4 type="file"  />
		   </div>
		   <div class="form_items">
	       		<label class="r-edit-label width100">图片五:</label> 
		   		<input class="r-edit-label width300" name=imageUrl5 type="file"  />
		   </div>
		   
		   <div class="form_items_btn">
		       <label class="r-edit-label width100" >&nbsp;</label> 
		       <input type="submit" class="search_btn_noWidth" onclick="materielSubmit('materiellapp_form')" value="确定" />
		   </div>
		 </form>
	  </div>
	  
	       
</div>

