
var reportData = {};
var reportType = 0;
var serieName = "展现量";
var unit = "次";
var startDate = "";
var endDate = "";
var valueUnit ="次";

function reportPageFilter(data) {
	var responseData = {};
	responseData.rows = data;
	return pagerFilter(responseData, "statList");

}

function searchAdSpaceReport() {

	var codeId = "";
	if ($("#codeId").length > 0) {
		codeId = $("#codeId").combobox('getValue');
	}
	
	var ownerId = "";
	if ($("#name").length > 0) {
		ownerId = $("#name").combobox('getValue');
	}
	
	var adpName = "";
	if ($("#adpName").length > 0) {
		adpName = $("#adpName").combobox('getValue');
	}

	if (typeof (spaceId) != "undefined" && spaceId != "") {
		codeId = spaceId;
	}

	var apGroup = "";

	if ($("#apGroup").length > 0) {
		apGroup = $("#apGroup").combobox('getValue');
	}

	startDate = $("#startDate").datebox("getValue");
	endDate = $("#endDate").datebox("getValue");
	 
	var platform = "";
	if ($("#platform").length > 0) {
		platform = $("#platform").combobox('getValue');
	}
	
	var adpId = "";
	if ($("#adpId").length > 0) {
		adpId = "adpId";
	}

	var param = {
		call : 'call_search_space_report',
		startDate : startDate,
		endDate : endDate,
		codeId : codeId,
		apGroup : apGroup,
		adpName : adpName,
		ownerId : ownerId,
		platform: platform,
		adpId: adpId

	};

	postAjaxRequest("/ad/report/space/search.do", param, function(data) {
		reportData = data.data;
		/* 		endDate = new Date(Date.parse(reportData.endDate.split(" ")[0]));
		 startDate = new Date(Date.parse(reportData.startDate.split(" ")[0]));

		 console.log(endDate);
		 console.log(endDate.format('YYYY-MM-DD')); */
		
		
		
		$('#startDate').datebox('setValue', reportData.startDate);
		$('#endDate').datebox('setValue', reportData.endDate);	
		

		$("#clicks").html(reportData.summary.clicks);
		$("#avg_clicks").html("平均点击量: " + reportData.summary.avgClicks);
		$("#views").html(reportData.summary.views);
		$("#avg_views").html("平均展现量: " + reportData.summary.avgViews);
		$("#click_ratio").html(formatterClickFloat(reportData.summary.clickRatio));
		$("#avg_click_ratio").html("平均点击率: " + formatterClickFloat(reportData.summary.clickRatio));

		$("#fill_rate").html("-");
		$("#avg_fill_rate").html("平均填充率: " + "-");
		$("#income").html(reportData.summary.income);
		$("#avg_income").html("平均预估收入: " + reportData.summary.avgIncome);
		$("#cpm").html(reportData.summary.cpm);
		$("#avg_cpm").html("平均每千次展现收入: " + reportData.summary.avgCpm);
		$("#query_date_range").html(
				reportData.startDate.split(" ")[0] + "至"
						+ reportData.endDate.split(" ")[0]);
		

//		$("#total_clicks").html(reportData.totalSummary.clicks);
//		$("#total_views").html(reportData.totalSummary.views);
//		$("#total_clicksRatio").html(formatterClickFloat(reportData.totalSummary.clickRatio));
//		$("#total_cpm").html("¥" + reportData.totalSummary.cpm);
//		//$("#total_fillRate").html(reportData.totalSummary.fillRate + "%");
//		$("#total_fillRate").html('-');
		
		updateReport(reportData);

		param.call = "call_search_space_report_list";
		$('#reportlist').datagrid({
			url : "/ad/report/search.do",
			method : "get",
			queryParams : param
		});
	});

}

function updateSelectedReportType(obj, rtype) {
	reportType = rtype;
	$(".radioBtn").removeClass("select");
	$(obj).addClass("select");

	updateReport(reportData);
}

function updateReport(reportData) {

	if(document.getElementById('effect')!=null){
		var data = [];
	
		if (reportType == 0) {
			unit = "次";
			valueUnit="次";
			serieName = "展现量";
			data = reportData.viewList;
		} else if (reportType == 1) {
			unit = "次";
			valueUnit="次";
			serieName = "点击量";
			data = reportData.clickList;
		} else if (reportType == 2) {
			unit = "百分比";
			valueUnit="%";
			serieName = "点击率";
			data = reportData.clickRatioList;
	
			for(var i=0; i<data.length; i++){
				
				if(data[i] == null){
					data[i] = 0;
				}
				else{
					data[i] = parseFloat(data[i] * 100).toFixed(1);
				}
			}
		} else if (reportType == 3) {
			unit = "元";
			valueUnit="元";
			serieName = "每千次展现收入";
			data = reportData.cpmList;
	
		} else if (reportType == 4) {
			unit = "百分比";
			valueUnit="%";
			serieName = "填充率";
			data = reportData.fillRateList;
	
		} else if (reportType == 5) {
			unit = "元";
			valueUnit="元";
			serieName = "预估收入";
			data = reportData.incomeList;
	
		}else if (reportType == 6) {
			unit = "人";
			valueUnit="人";
			serieName = "UV";
			data = reportData.uvList;
	
		}else if (reportType == 7) {
			unit = "个";
			valueUnit="个";
			serieName = "AP数";
			data = reportData.apNumList;
	
		}
	
		var effect = echarts.init(document.getElementById('effect'));
		var effect_option = {
			tooltip : {
				trigger : 'axis'
			},
			toolbox : {
				show : false,
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				boundaryGap : false,
				//时间数据
				data : reportData.statDate
			} ],
	/*		yAxis : [ {
				type : 'value'
			} ],*/
			yAxis: [{
	            type: 'value',
	            axisLabel: {
	                formatter: '{value}'+valueUnit
	            }
	        }],
			//展现数据
			series : [ {
				name : serieName,
				type : 'line',
				smooth : true,
				data : data
			} ]
		};
		effect.setOption(effect_option);
	}
}

function formatSpaceStyle(val, row) {

	if (val == 1) {
		return "注入式";
	} else if (val == 2) {
		return "自定义";
	}

	return "";
}

function formatSpaceStatus(val, row) {

	if (val == 1) {
		return "新建";
	} else if (val == 0) {
		return "停用";
	} else if (val == 2) {
		return "启用";
	}

	return "";
}

function getSpaceCode(id) {
	var data = {
		id : id,
		call : 'call_get_space_code'
	};
	postAjaxRequest("/adcenter/api/", data, function(data) {
		$("#codecontent").html(data.data.code);
		$("#codecontent").window('open');
	});

}



function searchSpace(){
	 $('#space-grid').datagrid('load', {
		keyword: $("#keyword").val(),
		spaceType: $('#spaceType').combobox('getValue'),
		status: $('#status').combobox('getValue')
	});
}




function formatSpaceUserOperation(val, row){
	
	var disableLabel = "停用";
	if(row.status == 0){
		disableLabel ="启用";
	}

	

		return " <a href=\"/gg/spaces/" + row.id + "\">修改</a> " + 
			"<a  href=\"#\" onclick=getSpaceCode('" + row.id + "');>获取代码</a> <a href=\"#\" onclick=openDisableSpaceWindow('" + row.id + "'," + row.status +  ");> " + disableLabel + "</a>" + 
			"<a href=\"/gg/spaces/report/" + row.codeId + "\" >查看报告</a>";
	
	
}

function openDisableSpaceWindow(id, status){
	var msg = "是否确认停用此广告位?"

	if(status == 0){
		msg = "是否确认启用此广告位?"
	}
	$.messager.confirm('广告位', msg, function(r){
        if (r){
        	
        	var data = { id : id, call : 'call_online_space_code' };
        	postAjaxRequest("/adcenter/api/", data, function(data) {
        		$("#space-grid").datagrid('reload');
        	});
        }
    }); 
	
}

function onlineADSpace(id){
	var data = { id : id, call : 'call_online_space_code' };
	postAjaxRequest("/adcenter/api/", data, function(data) {
		$("#space-grid").datagrid('reload');
	});
}



function showUserTagReport(){
	var p = { call : 'call_ut_report' };
	postAjaxRequest("/adcenter/api/", p, function(data) {
		showParentUserTagReport(data.rows);
	});
}

function showParentUserTagReport(data){

	option = {
		    title : {
		        text: '点击标签查看二级标签分布',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {d}%"
		    },
		    toolbox: {
		        show : false,
		        feature : {
		            mark : {show: true},
		            dataView : {show: true, readOnly: false},
		            magicType : {
		                show: true, 
		                type: ['pie', 'funnel'],
		                option: {
		                    funnel: {
		                        x: '25%',
		                        width: '50%',
		                        funnelAlign: 'left',
		                        max: 1548
		                    }
		                }
		            },
		            restore : {show: true},
		            saveAsImage : {show: false}
		        }
		    },
		    calculable : true,
		    series : [
		        {
		            name:'一级标签',
		            type:'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data: data
		        }
		    ]
		};
	
	var effect = echarts.init(document.getElementById('utag_one'));
	effect.setOption(option);
	effect.on(echarts.config.EVENT.CLICK, function(param){		
		showChildUserTagReport(data[param.dataIndex]);
	});
	
	if(data[0]){
		showChildUserTagReport(data[0]);
	}else{
		showChildUserTagReport([]);
	}
}



function showChildUserTagReport(parent){
	
	var p = { call : 'call_ut_report', name : parent.name };
	postAjaxRequest("/adcenter/api/", p, function(data) {
		option = {
			    title : {
			        text: parent.name,
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {d}%"
			    },
			    toolbox: {
			        show : false,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType : {
			                show: true, 
			                type: ['pie', 'funnel'],
			                option: {
			                    funnel: {
			                        x: '25%',
			                        width: '50%',
			                        funnelAlign: 'left',
			                        max: 1548
			                    }
			                }
			            },
			            restore : {show: true},
			            saveAsImage : {show: false}
			        }
			    },
			    calculable : true,
			    series : [
			        {
			            name:'二级标签',
			            type:'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:data.rows
			        }
			    ]
			};
		
		var effect = echarts.init(document.getElementById('utag_two'));

		effect.setOption(option);
	});
	
	
	
//	ecConfig = require('echarts/config');
//	console.log(ecConfig);
//
//	effect.on(CLICK, function(param){
//		
//		console.log(param);
//	});
}




function viewApGroups(divId, rId){
	rowId = rId;
	var rows = $('#' + divId).datagrid('getRows'); 
	for(var i=0; i < rows.length; i++){		
		if(rows[i].id == rowId){
			var dg = $('#apgroupsgrid').datagrid({
				data: eval(rows[i].targetApGroups)
			});
			
			$("#apgroupswindow").window('open');
			break;
		}
	}
	
}

function searchApGroups(id){
	var rows = $('#' + id).datagrid('getRows');
	var apgrows = [];
	for(var i=0; i < rows.length; i++){		
		if(rows[i].id == rowId){
			apgrows = eval(rows[i].targetApGroups);			
			break;
		}
	}
	var frows = [];

	if($("#aogroup_keyword").val()!=""){		
		for(var i=0; i < apgrows.length; i++){
			if(apgrows[i].name.indexOf($("#aogroup_keyword").val())!=-1){
				frows.push(apgrows[i]);
			}
		}			
	}else{
		frows = apgrows;	
	}
	
	var dg = $('#apgroupsgrid').datagrid({
		data: frows
	});
}

function formatterClickFloat(val, row){
	if(val){
		return parseFloat(val * 100).toFixed(1) + "%"
	}else{
		return "0%";
	}
}

function formatterMoneyFloat(val, row){
	if(val){
		return "￥" + parseFloat(val).toFixed(2);
	}else{
		return "￥0";
	}
}

function formatterFillRate(val, row){
	
	return "-";
}
