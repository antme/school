<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<script>
	var menuids = "";
	var groupid = "";
	function formatterMenuOperation(val, row){		
		if(menuids.indexOf(row.id) ==-1){
			return '<input  type="checkbox" onclick="checkIds(this,' + '\'' + row.id + '\');" />';
		}else{
			return '<input checked type="checkbox" onclick="checkIds(this,' + '\'' + row.id + '\');" />';
		}
		
	}
	
	function checkIds(checkbox, id){
		
		if(checkbox.checked){
			
			menuids = menuids + "," + id;
		}else{
			menuids = menuids.replace(id, "");
		}
		
		
	}
	
	function saveGroupMenu(){
		
		var data = {
				id : groupid,
				menuIds : menuids
				
		};
		var value = $("#language").combobox("getValue");
		if(value=="" || value ==null){
			alert("请选择角色！");
		}else{
			postAjaxRequest("/sys/groupmenu/save.do", data, function(response) {
				alert("保存成功");		
			});
		}
	}
</script>

<div>
	<label style="font-size:20px">菜单设置</label>
</div>
<br><br>
			

<label>选择角色：</label>
<input id="language" class="easyui-combobox" name="language" style="height:26px;" data-options="url:'/sys/group/listall.do?_=', method:'get', valueField:'id', textField:'groupName', panelHeight:'auto',
loadFilter:function(data){return data.rows;}, onSelect: function(rec){loadMenu(rec);}"/>


<button class="search_btn_noWidth" onclick="saveGroupMenu();" >保存菜单</button>

<table id="menulist" class="" style="height:1500px; display:none;" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true" url="/sys/menu/list.do?_=" iconCls="icon-save" sortOrder="asc"
	pagination="false" singleSelect="true">
	<thead>
		<tr>
			<th align="center" field="title" width="350" sortable="false" resizable="true">菜单</th>

			<th align="center" data-options="field:'id',formatter:formatterMenuOperation" width="120"></th>

		</tr>
	</thead>
</table>


 <script type="text/javascript">
        function loadMenu(rec){
        	menuids = "";
        	if(rec.menuIds){
        		menuids = rec.menuIds;
        	}
        	
        	groupid = rec.id;
        	
            $('#menulist').datagrid({
                view: detailview,
                detailFormatter:function(index,row){
                    return '<div class="ddv" style="padding:2px; border: solid 1px; "></div>';
                },
                onExpandRow: function(index,row){               	
                    var ddv = $(this).datagrid('getRowDetail',index).find('div.ddv');
                    ddv.datagrid({
                        url:'/sys/menuitem/list.do?id=' + row.id,
                        fitColumns:true,
                        singleSelect:true,
                        rownumbers:true,
                        loadMsg:'',
                        height:'auto',
                        columns:[[
                            {field:'title',title:'二级菜单',align:'center', width:277},
                            {field:'description',title:'描述',align:'center', width:277},
                            {field:'id',title:'',width:100,align:'center', formatter:formatterMenuOperation}
                        ]],
                        onResize:function(){
                            $('#menulist').datagrid('fixDetailRowHeight',index);
                        },
                        onLoadSuccess:function(){
                            setTimeout(function(){
                                $('#menulist').datagrid('fixDetailRowHeight',index);
                            },0);
                        }
                    });
                    $('#menulist').datagrid('fixDetailRowHeight',index);
                }
            });
            
            
            $('#menulist').datagrid('expandRow'); 
        };
        
        
    </script>
    
    