<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">
        $(function(){
        	 $.messager.defaults = {
        		  ok : "继续上传",
        		  cancel : "去查看"
        	};
            $('#dataimport').form({
                url:'/user/admin/import.do',
                onSubmit:function(){
                	if($(this).form('validate')){
                		ajaxLoading();
                		return true;
                	}               	
                	return false;
                },
                success:function(data){
                    ajaxLoadEnd();

                	var response = undefined;
                	if (data instanceof Object) {
                		response = data;
                	} else {
                		eval("response=" + data);
                	}
                	
                	if(response.code == 1 && response.msg){
                	
                		var msgs = response.msg;

                		var log="";
                		for(i in msgs){
                			log = log + msgs[i] + "<br>";
                		}
                		$("#send_text").html(log);
                		$("#send_text").show();
                	}else{
                		if(response.msg){
                			$.messager.alert('信息', response.msg, 'info');
                		}else{
                			$.messager.alert('信息', "数据导入成功", 'info');
                		}
                		
                	}
                }
            });
            
            
            $('#studentdataimport').form({
                url:'/user/admin/student/import.do',
                onSubmit:function(){
                	if($(this).form('validate')){
                		ajaxLoading();
                		return true;
                	}               	
                	return false;
                },
                success:function(data){
                    ajaxLoadEnd();

                	var response = undefined;
                	if (data instanceof Object) {
                		response = data;
                	} else {
                		eval("response=" + data);
                	}
                	
                	if(response.code == 1 && response.msg){
                	
                		var msgs = response.msg;

                		var log="";
                		for(i in msgs){
                			log = log + msgs[i] + "<br>";
                		}
                		$("#student_send_text").html(log);
                		$("#student_send_text").show();
                	}else{
                		if(response.msg){
                			$.messager.alert('信息', response.msg, 'info');
                		}else{
                			$.messager.alert('信息', "数据导入成功", 'info');
                		}
                		
                	}
                }
            });
            
           
        });
    </script>
    
    <div class="page_tip" style="font-size:20px; font-weight:bold;padding:5px;">家长数据导入</div>

    <div class="page_tip" style="color:#aaa;font-size:10px">说明： 数据导入目前只支持excel文件, 名字作为是否为会员的判断</div>
	<div class="seacher_btn" style="padding:10px;">
	   
		<form id="dataimport" method="post" novalidate enctype="multipart/form-data">
			<div class="k-edit-field" style="width:600px;">
			    <label class="title_url">上传家数据文件：</label>
			    <label class="dd_url"></label>
			    <div class="push_url"><input name="file" id="file" class=" k-text" type="file" size="15" required="required" missingMessage="请选择文件"  onchange="get_text()"/></div> 
			</div>
		
			<div class="k-edit-field" style="margin-left:115px;*+margin-left:50px;">
                <input class="send_url sub_btn_noWidth" type="submit" value="确认上传">
			</div>
		</form>
		
		<div class="send_text"  id="send_text" style="display:none;margin-left:110px;"></div>
		<script type="text/javascript">
		$(".push_url").click(function(){
            $("#file").click();
        });
		   function get_text(){
			   var filepath=document.getElementById("file").value;
			   if(filepath != ""){
                   var extStart = filepath.lastIndexOf("."); 
                   var ext=filepath.substring(extStart,filepath.length).toLocaleLowerCase(); 
                   if(ext == ".xls" || ext ==".xlsx"){ 
                	   $(".dd_url").text($("#file").val());
                   }else{
                	   displayInfoMsg("文件格式仅支持xls和xlsx");                	   
                   }
               }
		   }
		</script>
	</div>
	
	<hr/>
	
	<div class="page_tip" style="font-size:20px; font-weight:bold;padding:5px;">学生数据导入</div>
	
    <div class="page_tip" style="color:#aaa;font-size:10px">说明： 数据导入目前只支持excel文件</div>
	<div class="seacher_btn" style="padding:10px;">
	   
		<form id="studentdataimport" method="post" novalidate enctype="multipart/form-data">
			<div class="k-edit-field" style="width:600px;">
			    <label class="title_url">上传家数据文件：</label>
			    <label class="dd_url"></label>
			    <div class="student_push_url"><input name="studentfile" id="studentfile" class=" k-text" type="file" size="15" required="required" missingMessage="请选择文件"  onchange="get_text()"/></div> 
			</div>
		
			<div class="k-edit-field" style="margin-left:115px;*+margin-left:50px;">
                <input class="send_url sub_btn_noWidth" type="submit" value="确认上传">
			</div>
		</form>
		
		<div class="send_text"  id="student_send_text" style="display:none;margin-left:110px;"></div>
		<script type="text/javascript">
		$(".student_push_url").click(function(){
            $("#studentfile").click();
        });
	
		</script>
	</div>
	
	
