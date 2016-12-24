<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<div>

	<div style="margin-left:20px; float:left; margin-top:10px;">
		<a href="index.jsp?p=admin/ADReport/report"><img src="/resources/images/income_report.png" height="100"></img><br>
		<span style="margin-left: 10px;">广告位报表</span></a>
	</div>
	
	<div style="float:left;margin-left:20px; margin-top:10px;">
		<a href="index.jsp?p=admin/code/list"><img src="/resources/images/space_setting.png" height="100"></img><br>
		<span style="margin-left: 5px;">广告位配置</span></a>
	</div>
	
	<div style="float:left;margin-left:20px; margin-top:10px;">
		<a href="index.jsp?p=admin/ADReport/import"><img src="/resources/images/import.png" height="100"></img><br>
		<span style="margin-left: 5px;">数据导入</span></a>
	</div>
	<div style="float:left;margin-left:20px; margin-top:10px;">
		<a href="index.jsp?p=admin/ADReport/importapp"><img src="/resources/images/import.png" height="100"></img><br>
		<span style="margin-left: 5px;">APP数据导入</span></a>
	</div>
	
	<div style="float:left;margin-left:20px; margin-top:10px;">
		<a href="index.jsp?p=admin/user/list"><img src="/resources/images/account.png" height="100"></img><br>
		<span style="margin-left: 5px;">账号设置</span></a>
	</div>
	<div style="float:left;margin-left:20px; margin-top:10px;">
		<a href="index.jsp?p=admin/bill/listnew"><img src="/resources/images/account.png" height="100"></img><br>
		<span style="margin-left: 5px;">账单管理</span></a>
	</div>
	<div style="float:left;margin-left:20px; margin-top:10px;">
		<a href="index.jsp?p=admin/appcode/list"><img src="/resources/images/account.png" height="100"></img><br>
		<span style="margin-left: 5px;">APP广告位</span></a>
	</div>
	
	<div style="float:left;margin-left:20px; margin-top:10px;">
		<a href="index.jsp?p=admin/sys/setting"><img src="/resources/images/sys.png" height="100"></img><br>
		<span style="margin-left: 5px;">系统设置</span></a>
	</div>
	
	<div style="float:left;margin-left:20px; margin-top:10px;position: relative;">
		<a href="index.jsp?p=admin/blockstrategy/list"><img src="/resources/images/sys.png" height="100"><br>
		<span style="margin-left: 5px;">广告源策略</span></a>
    	<span class="strategy_num" style="position: absolute; top: 0px;right: -20px;padding:3px 20px;border: solid  1px blue;background-color:#fff;border-radius: 10px;" id="strategyNum">0</span>
	</div>
	<br>
	<br>
	<br>

</div>
<script type="text/javascript">
$.get("/strategy/updatestrategytobaidunumber.do",{},function(data){
	  if(data && data !== ''){
			if(data.number){
				if(data.number > 99){
					$('#strategyNum').text('99+');
				}else{
					$('#strategyNum').text(data.number);
				}
			}
	  }
		});
</script>
