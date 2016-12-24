function forceLogin() {
	window.location.href = "/login.jsp";
}


function logout() {
	postAjaxRequest("/ecs/user/logout.do", {}, function(data) {
		window.location.href = "https://" + document.location.host
				+ "/login.jsp";
	}, false);
}




function formatterADCampaignDateOperation(val,row){
	if(val){
		return val;
	}else{
		return "不限";
	}
}

function formatterchargeTypeOperation(val,row){
	if(val=="1"){
		return "按点击付费";
	}else{
		return "按千次展示付费";
	}
}


function formatterCampaignIsOnline(val, row){
	var value;
	if(val){
		value="投放中";
	}else{
		value="投放停止";
	}
	return value;
}


function formatterCampaignIsApproved(val, row){
	var value;
	if(val){
		value="已审核";
	}else{
		value="待审核";
	}
	return value;
}



function formatterCampaignPreview(val,row){
	return "<a href='/pages/ad/preview.jsp?campaignId="+row.id+"' target='_blank' >预览</a>";
}



function formatterInvoiceInfo(val, row){
	
	
	var invoiceAttachFile = decodeURI(row.invoiceAttachFile);
	
	if(row.invoiceNo){
		
		return '<a href="#" onclick="displayInvoiceInfo(\'' + row.invoiceNo + '\', \''+ invoiceAttachFile +'\');">详情</a>';
	}
	
	return "";
	

	
}

function displayInvoiceInfo(invoiceNo, invoiceAttachFile){
	
	$("#invoiceno").html(invoiceNo);
	
	if(invoiceAttachFile){
		$("#attachimg").attr('src', invoiceAttachFile);
	}
	$("#invoicecontent").window('open');
}


function formatterBillDate(val, row){
	
	return row.year + "-" + row.month;
}



function formatterMoneyOperation(val, row){
	if(val){
		return "￥"+ parseFloat(val).toFixed(2);
	}else{
		
		return "￥0";
	}
}


function formatterFloat(val, row){
	if(val){
		return  parseFloat(val).toFixed(2);
	}else{
		
		return "0";
	}
}


function formatterMoneyTaxOperation(val, row){
	
	return (row.payMoneyBeforTax - row.payMoneyAfterTax) + "￥";
}


function formatterPicView(val, row){
	
	return '<a  target="_blank" href="' + val + '"> <img height=100  src="' + val + '" </img> </a>';
}

function formatterMediaOperation(val, row) {
	return '<a href="#" onclick=loadRemotePage("media/add&id='
			+ row.id 
			+ '")> 编辑 </a><a style="margin-left:5px" href="#" onclick=deleteMedia("'
			+ row.id + '")> 删除 </a>';
}

function formatterMyMediaOperation(val, row) {
	return '<a href="#" onclick=loadRemotePage("media/addmine&id='
	+ row.id 
	+ '")> 编辑 </a>';
//<a style="margin-left:5px" href="#" onclick=deleteMyMedia("'+ row.id + '")> 删除 </a>'
}


function formatterAdminCoopViewOperation(val, row){
	return '<a href="#" onclick=loadRemotePage("admin/code/addcoop&id='
	+ row.id 
	+ '")> 编辑 </a>';
	
}


function formatterCoopPlatform(val, row){

	if( val == "10"){
		
		return "百度广告";
	}else if( val == "11"){
		
		return "百度搜索";
	}else if( val == "20"){
		
		return "Criteo广告";
	}
	
}

function formatterCodeOperation(val, row) {
	return ' <a style="margin-left:5px" href="#" onclick=getCode("'
			+ row.id + '")> 获取代码 </a>';
}

function formatterUserCodeOperation(val, row) {
	return ' <a style="margin-left:5px" href="#" onclick=getCode("'
			+ row.id + '")> 获取代码 </a>';
}


function formatterCodeViewOperation(val, row){
	
	return '<a style="margin-left:5px" href="#" onclick=getCode("' + row.id + '")> 获取代码 </a>';
}


function formatterGroupOperation(val, row) {
	
	if(row.id == "05c07bcc-833e-4b22-a8be-3c3a63609ac1" || row.id =="05c07bcc-833e-4b22-a8be-3c3a63609ac2" ){
		return "";
	}else{
		return '<a href="#" onclick=loadRemotePage("sys/addgroup&a=5&id='
				+ row.id
				+ '")> 编辑 </a><a style="margin-left:5px" href="#" onclick=deleteGroup("'
				+ row.id + '")> 删除 </a>';
	}
	
}



function formatterChannelUserOperation(val, row) {
	return '<a href="#" onclick=loadRemotePage("user/addchanneluser&id=' + row.id
			+ '&from=list")> 编辑 </a><a style="margin-left:5px" href="#" onclick=loadRemotePage("user/changeuserpwd&from=channel&id='
			+ row.userId+ '")> 修改登录密码 </a>';
}

function formatterMerchantUserOperation(val, row) {
	return '<a href="#" onclick=loadRemotePage("user/addmerchantuser&id=' + row.id
			+ '&from=list")> 编辑 </a><a style="margin-left:5px" href="#" onclick=loadRemotePage("user/changeuserpwd&from=merchant&id='
			+ row.userId+ '")> 修改登录密码 </a>';
}

function formatterAPOperation(val, row) {
	return '<a href="#" onclick=loadRemotePage("ap/add&id=' + row.id + '")> 编辑 </a>  ';
}



function formatterEntityType(val,row) {
	if(val=="1"){
		return "公司";
	} else {
		return "个人";
	}
}


function formatterLogOperation(val, row) {

	if (row.logType != "msg") {
		return '<a href="#" onclick=openLogDetails("' + row.id
				+ '")> 数据库修改详情 </a>';
	}
}



function formatteAttachFileLink(val, row) {
	if (val) {
		return '<a target="_blank" href="' + val + '")> 下载 </a>';
	}
}


function formatterIndustryOperation(val,row){
	return '<a href="#" onclick=loadRemotePage("industry/add&id=' + row.id
	+ '")> 编辑 </a>';
}

function formatterWebSiteOperation(val,row) {
	return '<button style="margin-left:5px" onclick=saveWebSite("' + row.id + '","' + row.name + '","' + row.categoryId 
	+ '")> 保存 </button>';
}

function formatterCategoryOperation(val,row) {
	return '<a href="#" onclick=loadRemotePage("admin/category/add&id=' +row.id
	+ '")> 编辑 </a>';
}

function formatterWebSiteType(val, row){

	if( val == "shopping"){
		return "购物";
	}else if( val == "download"){
		return "下载";
	}else if( val == "search"){
		return "搜索";
	}else if( val == "music"){
		return "音乐";
	}else if( val == "shenghuo"){
		return "生活";
	}else if( val == "email"){
		return "邮件";
	}else if( val == "video"){
		return "视频";
	}else if( val == "game"){
		return "游戏";
	}else if( val == "novel"){
		return "小说";
	}else if( val == "study"){
		return "学习";
	}
	
}


function formatterWebSiteDomain(val, row){

	return '<a target="_blank" href="http://' + val + '">' + val + '</a>';
	
}

function formatterAPisOnline(val, row){
	var value;
	if(val){
		value="Online";
	}else{
		value="Offline";
	}
	return value;
}

function formatterisOnline(val, row){
	var value;
	if(val){
		value="启用";
	}else{
		value="停用";
	}
	return value;
}



function select_searchvalue(value){
	if(value=="广告统计数据查询"){
		loadRemotePage('ADReport/report');
	}else if(value=="搜索统计数据查询"){
		loadRemotePage('ADReport/searchreport');
	}
}

function disabled(id,uID){
	if(id!="null"){
		$("#"+uID).attr("readOnly","true");
	}else{
		$("#"+uID).removeAttr("readOnly");
	}
}


function doCopy(obj) {
	var rng = document.body.createTextRange();
	rng.moveToElementText(obj);
	rng.scrollIntoView();
	rng.select();
	rng.execCommand("Copy");
	rng.collapse(false);
}
function doCopy2(ID) { 
	if (document.all){
		 textRange = document.getElementById(ID).createTextRange(); 
		 textRange.execCommand("Copy");
		 alert('复制成功');
	}
	else{
		 alert("此功能只能在IE上有效")
	}
}
