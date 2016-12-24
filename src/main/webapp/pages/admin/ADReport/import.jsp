<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script type="text/javascript">
        $(function(){
        	 $.messager.defaults = {
        		  ok : "继续上传",
        		  cancel : "去查看"
        	};
            $('#apimport').form({
                url:'/ad/report/import.do',
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
                			$.messager.confirm('上传成功', '继续导入或跳转到查询页面?', function(r){
                	                if (!r){
                	                    window.location.href="index.jsp";
                	                }else{
                	                	
                	                }
                	        });
                		}
                		
                	}
                }
            });
            
           
        });
    </script>
    
    <div class="page_tip" style="font-size:20px; font-weight:bold;padding:5px;">百度广告数据导入</div>
	<hr/>
    <div class="page_tip" style="color:#aaa;font-size:10px">说明： 数据导入目前只支持excel文件，从百度联盟下载的excel后再导入</div>
    <div class="page_tip" style="color:#aaa;font-size:10px">备注： 筛选的数据请按媒体单个筛选</div>
	<div class="seacher_btn" style="padding:10px;">
	   
		<form id="apimport" method="post" novalidate enctype="multipart/form-data">
			<div class="k-edit-field" style="width:600px;">
			    <label class="title_url">上传模板文件：</label>
			    <label class="dd_url"></label>
			    <div class="push_url"><input name="file" id="file" class=" k-text" type="file" size="15" required="required" missingMessage="请选择文件"  onchange="get_text()"/></div> 
			</div>
			<!-- <div class="k-edit-field" style="margin-left:115px;width:100%;">
			    <input name="isOverride" id="isOverride" type="checkbox" value="true"/> 
			    <label for="isOverride" style="font-size:14px">覆盖已有的媒体统计数据</label>
			    
			</div> -->
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
	
	
