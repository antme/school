<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String id = request.getParameter("id");
   String ownerId = request.getParameter("ownerId");
%>

<script type="text/javascript">

	var id = "<%=id%>";
	var ownerId = "<%=ownerId%>";
	
	$(document).ready(function() {
		$("#id").val(id);
		initFormSubmit("copy-coop", "/adspace/admin/baiducode/copyBaiduCode.do", "复制广告位", function(){
			alert("提交成功");
			loadRemotePage("admin/code/list");
		});

 		if(ownerId){
 			$('#sourceAp').combobox({   
 			    url:'/adspace/admin/baiducode/loadListByOwner.do?ownerId='+ownerId+'&id='+id,   
 			    valueField:'baiduCode',   
 			    textField:'showMsg',
		        loadFilter:function(data){
		        	   var adspaceList = new Array();
		        	   $(data.rows).each(function(){
		        			this.showMsg = this.codeId+this.codeName;
		        			adspaceList.push(this);
		        	   });
                       return adspaceList;
                   }
 			});  
		}
	});

</script>



<div style="padding: 10px 60px 20px 60px">

	<form id="copy-coop" method="post" novalidate>

		<div class="form-container">
		
			<div>
				<label id="infolabel" style="font-size:20px">复制</label>
			</div>
			<br><br>

			<input type="hidden" id="id" name="id"/>
			
			<div class="form_items">
			    <label class="r-edit-label width100">广告位:</label>
			    <input class="easyui-combobox" id="sourceAp" name="baiduCode"/>

			</div>
			

			<div class="form_items">

				<label class="r-edit-label width100">&nbsp;</label> <input class="sub_btn" type="submit" value="提交"></a>

			</div>

		</div>

	</form>