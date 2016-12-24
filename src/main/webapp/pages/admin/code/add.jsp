<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String id = request.getParameter("id"); %>


<script type="text/javascript">
	var id = "<%=id%>";
	$(document).ready(function() {
		initFormSubmit("add-code", "/code/admin/add.do", "添加代码位", function(){
			alert("提交成功");
			loadRemotePage("admin/code/list");
		});

		if(id!="null"){
			postAjaxRequest("/code/load.do", {id:id}, function(data){
				$("#add-code").form('load',data.data);			

			});
			$("#addnewcode").text("修改信息");
		} else {
			$("#addnewcode").text("新建广告位");
		}

	});

</script>



<div style="padding: 10px 60px 20px 60px">

	<form id="add-code" method="post" novalidate>

		<div class="form-container">
		
			<div>
				<label id="addnewcode" style="font-size:20px"></label>
			</div>
			<br><br>

			<input class="" type="hidden" name="id" />
			<div class="form_items">

				<label class="r-edit-label width100">代码位ID:</label> <input class="easyui-validatebox textbox width300" type="text" name="codeId" required="required"></input>

			</div>
			<div class="form_items">

				<label class="r-edit-label width100">代码位名字:</label> <input class="easyui-validatebox textbox width300" type="text" name="codeName" required="required"></input>

			</div>
			<div class="form_items">

				<label class="r-edit-label width100">样式:</label> <input class="easyui-validatebox textbox width300" type="text" name="adStyle" required="required"></input>

			</div>
			
			<div class="form_items">
				<label class="r-edit-label width100">所属账户:</label>  <input class="easyui-combobox textbox width300" style="height:30px;" type="text" name="ownerId" 
				data-options="url:'/user/channel/select.do',
                    method:'get',
                    valueField:'id',
                    textField:'userName',
                    panelHeight:'auto',
                    loadFilter:function(data){
						return data.rows;
					}"></input>
			</div>


			<div class="form_items">

				<label class="r-edit-label width100">状态:</label> <select class="easyui-combobox width300" name="isOnline" style="height: 30px;">

					<option value="1" selected="selected">启用</option>

					<option value="0">停用</option>

				</select>

			</div>




			<div class="form_items">

				<label class="r-edit-label width100">&nbsp;</label> <input class="sub_btn" type="submit" value="提交"></a>

			</div>

		</div>

	</form>