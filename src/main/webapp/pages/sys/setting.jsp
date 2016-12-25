<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
%>


<script type="text/javascript">
	var id = "<%=id%>";
	$(document).ready(function() {
		initFormSubmit("sys_location_form", "/sys/setting/save.do", "系统设置", function() {
			alert("提交成功");
		});

		postAjaxRequest("/sys/setting/load.do", {}, function(data) {

			
			if(data && data.data){
				$("#sys_location_form").form('load', data.data);
				var cpmSetting = null;
				var ycad = null;
		
				if(data.data.cpmSetting){
					eval("cpmSetting = " + data.data.cpmSetting);
					$("#sys_ad_form").form('load', cpmSetting);
	
				}
				
				if(data.data.ycad){
					eval("ycad = " + data.data.ycad);
					$("#sys_sys_form").form('load', ycad);
				}
				$("#sys_tag_form").form('load', data.data);
				$("#sys_sys_form").form('load', data.data);
			}
		});
		
		
		initFormSubmit("sys_ad_form", "/sys/setting/cpm/save.do", "CPM设置", function() {
			alert("提交成功");
		});
		
		initFormSubmit("sys_tag_form", "/sys/setting/tag/save.do", "用户标签设置", function() {
			alert("提交成功");
		});
		
		initFormSubmit("sys_sys_form", "/sys/setting/sys/save.do", "系统设置", function() {
			alert("提交成功");
		});

	});
</script>



<div style="padding: 10px 60px 20px 60px">


	<div class="form-container">

		<div>
			<label id="" style="font-size: 20px">系统设置</label>
		</div>
		<br>
		<br>
		<div class="easyui-tabs">
			<div title="地域投放设置" style="padding: 10px">
				<form id="sys_location_form" method="post" novalidate>

					<input class="" type="hidden" name="id" />
					<div class="form_items">
						<label class="r-edit-label width100">第三方域名:</label>
						<textarea name="limitedLocationsFroOtherDomain" cols="50" rows="5"></textarea>
						<span class="">第三方域名投放限制区域, 空为不限制:</span>
					</div>

					<div class="form_items">
						<label class="r-edit-label width100">百度系/百度媒体方域名:</label>
						<textarea name="limitedLocationsFroBaiduDomain" cols="50" rows="5"></textarea>
						<span class="">百度系/百度广告主域名投放限制区域,空为所有区域的都不投放百度广告:</span>
					</div>

					<div class="form_items">

						<label class="r-edit-label width100">图+限制:</label>
						<textarea name="picPlusLimitedLocations" cols="50" rows="5"></textarea>
						<span class="">限制的区域图+只投放icon样式和tag样式，不投放图集样式:</span>

					</div>

					<div class="form_items">
						<label class="r-edit-label width100">场景管家</label>
						<textarea name="mobSceneLimitedLocations" cols="50" rows="5"></textarea>
						<span class="">“下拉底部插入”和“识别文尾插入”投放的地域,空为所有区域不限制样式:</span>
					</div>

					<div class="form_items">
						<label class="r-edit-label width100">广告限制:</label>
						<textarea name="adLimitedLocations" cols="50" rows="5"></textarea>
						<span class="">广告不超过3个的地域，空为所有区域不限制广告个数:</span>
					</div>
					<div class="form_items">

						<label class="r-edit-label width100">PC弹窗间隔:</label> <input
							type="number" name="pcAdSkipNumber" value="0" /><span>个</span>

					</div>
					<div class="form_items">
						<label class="r-edit-label width100">PC弹窗间隔区域:</label>
						<textarea name="pcAdSkipLimitedLocations" cols="50" rows="5"></textarea>
						<span class="">PC弹窗间隔生效的区域，空为所有区域不限制弹窗间隔:</span>
					</div>


					<div class="form_items">

						<label class="r-edit-label width100">增强变现功能:</label>
						<textarea name="enhanceIncomeIgnoreLocations" cols="50" rows="5"
							id="description"></textarea>
						<span class="">增强变现功能不生效的地域,空为所有区域的都不启用增强变现能力:</span>

					</div>
					
					<div class="form_items">
						<label class="r-edit-label width100">互众广告:</label>
						<textarea name="hzLimitedLocations" cols="50" rows="5"></textarea>
						<span class="">互众广告投放限制区域, 空为不限制:</span>
					</div>
					
					<div class="form_items">
						<label class="r-edit-label width100">内容联盟推送屏蔽区域:</label>
						<textarea name="cpuPushLimitedLocations" cols="50" rows="5"></textarea>
						<span class="">内容联盟推送样式屏蔽区域, 空为不限制:</span>
					</div>

					<div class="form_items">

						<label class="r-edit-label width100">&nbsp;</label> <input
							class="sub_btn" type="submit" value="提交"></a>

					</div>
				</form>
			</div>
			<div title="本地广告分成设置" style="padding: 10px">
				<form id="sys_ad_form" method="post" novalidate>
			
				<div class="form_items">
					<label class="r-edit-label width100">CPM > 10</label>
					<input class="width200 easyui-validatebox cpc" type="number" value="" name="cpmlt10" validType="number"/>
					<span class="">提成比率（%）</span>
				</div>
				<div class="form_items">
					<label class="r-edit-label width100">CPM > 6</label>
					<input class="width200 easyui-validatebox cpc" type="number" value="" name="cpmlt6" validType="number"/>
					<span class="">提成比率（%）</span>
				</div>
				<div class="form_items">
					<label class="r-edit-label width100">CPM > 4</label>
					<input class="width200 easyui-validatebox cpc" type="number" value="" name="cpmlt4" validType="number"/>
					<span class="">提成比率（%）</span>
				</div>
				
				<div class="form_items">
					<label class="r-edit-label width100">CPM > 3</label>
					<input class="width200 easyui-validatebox cpc" type="number" value="" name="cpmlt3" validType="number"/>
					<span class="">提成比率（%）</span>
				</div>
				
				<div class="form_items">
					<label class="r-edit-label width100">CPM > 2</label>
					<input class="width200 easyui-validatebox cpc" type="number" value="" name="cpmlt2" validType="number"/>
					<span class="">提成比率（%）</span>
				</div>
				<label class="r-edit-label width100">&nbsp;</label> <input
							class="sub_btn" type="submit" value="提交"></a>
				</form>
			</div>
			<div title="用户标签设置" style="padding: 10px">
			<form id="sys_tag_form" method="post" novalidate>
				<label class="r-edit-label width100">不获取用户标签的广告位，逗号分隔:</label>
				<textarea name="codeIdsNoTag" cols="50" rows="5"
							id="codeIdsNoTag"></textarea>
				<label class="r-edit-label width100">&nbsp;</label> <input
							class="sub_btn" type="submit" value="提交"></a>
				</form>
			</div>

			<div title="系统设置" style="padding: 10px">
				<form id="sys_sys_form" method="post" novalidate>
		
				<div class="form_items">
					<label class="r-edit-label width100">银城服务器地址</label>
					<input class="width200 easyui-validatebox cpc" type="text" value="" name="ycurl" />
				</div>
				<div class="form_items">
					<label class="r-edit-label width100">银城P参数</label>
					<input class="width200 easyui-validatebox cpc" type="text" value="" name="ycp" />
				</div>
				<div class="form_items">
					<label class="r-edit-label width100">银城广告位ID</label>
					<input class="width200 easyui-validatebox cpc" type="text" value="" name="ycid" />
				</div>
					<div class="form_items">

						<label class="r-edit-label width100">ES服务器启用:</label> <select
							class="easyui-combobox width300" name="enableEs"
							style="height: 30px;">

							<option value="true" selected="selected">启用</option>

							<option value="false">停用</option>

						</select>

					</div>
					<div class="form_items">

						<label class="r-edit-label width100">&nbsp;</label> <input
							class="sub_btn" type="submit" value="提交"></a>

					</div>

				</form>
			</div>
		</div>

	</div>