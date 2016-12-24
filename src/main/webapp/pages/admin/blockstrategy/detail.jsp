<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<style>
.form_items label, .form_items_btn label{
	padding: 0 10px 0 0;
}
</style>
<div style="padding: 10px 60px 20px 60px">
	<form id="ff" method="post" novalidate>
		<div class="form-container">
			<div>
				<label id="infolabel" style="font-size: 20px">广告源过滤详情</label>
			</div>
			<br/>
			<input type="hidden" name="id" id="id" value="${param.id}" />
			<div class="form_items">
			    <label class="r-edit-label width150">所属账号：</label>
			    <div style="float:left;" id="userName">
			    </div>
			</div>
			<div class="form_items">
			    <label class="r-edit-label width150">行业标签：</label>
			    <div style="float:left;" id="blockTags">
			    </div>
			</div>
			<div class="form_items">
			    <label class="r-edit-label width150">禁用网站：</label>
			    <div style="float:left;" id="advertiserDomains">
			    </div>
			</div>
			<div class="form_items">
				<label class="r-edit-label width150">&nbsp;</label>
				<button class="sub_btn" type="button" onclick="setConfigStatus();">更新配置状态</button>
			</div>
		</div>
	</form>
</div>
<script type="text/javascript">
	function setConfigStatus(){
		$.messager.confirm('提示','确认已在百度更新广告主屏蔽', function(r){
	        if (r){
	        	var data = { id :  $('#id').val() };
	        	postAjaxRequest("/strategy/updatestrategytobaidu.do", data, function(data) {
					if (data.code != 1) {
						$.messager.alert('错误',data.msg[0].message,'Error');
						return;
					}else{
						$.messager.alert('提示','配置成功','Info',function(){
	 						loadRemotePage("admin/blockstrategy/list");
						});
					}
	        	});
	        }
	    }); 
	}

	$(document).ready(function() {
		var id = $('#id').val();
		if (!id) {
			$.messager.alert('错误','ID不能为空'.message,'Error',function(){
					loadRemotePage("admin/blockstrategy/list");
			});
		}
		
		postAjaxRequest("/strategy/getstrategyinfoforadmin.do", {
		    id : id
		}, function(data) {
		    if(data && data !== ''){
		        if( data.code === '1' ){
		            data = data.data;
		            var treeNodes = data.treeNodes;
		            var _html = '';
		            var selectedList = [];
		            if(data.adBlockStrategy){
		                data = data.adBlockStrategy;
		                $('#userName').html(data.userName);
		                if(data.advertiserDomains !== ''){
		                    //var _list = data.advertiserDomains.split('\n');
		                	$('#advertiserDomains').html(data.advertiserDomains.replace('\n','<br>'));
		                }
		                if(data.blockTags && data.blockTags.length > 0 ){
		                    for(var i in treeNodes){
		                    	selectedList = [];
		                        var _children = treeNodes[i].children;
		                        if(_children && _children.length>0 ){
		                            for(var j in _children){
		                                if(data.blockTags.indexOf(_children[j].code) !== -1){
		                                    selectedList.push(_children[j].textField);
		                                }
		                            }
		                            if(selectedList.length > 0 ){
		                            	_html += treeNodes[i].textField + '（' + selectedList.join('，') + '）<br>';
		                            }
		                        }
		                    }
		                    $('#blockTags').html(_html);
		                }

		            }
		        }
		    }
		});
	});
</script>