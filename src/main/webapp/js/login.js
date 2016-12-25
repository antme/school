$(document).ready(function() {


	$("#login-form").form({
		url : '/user/back/login.do',
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			dealMessageWithCallBack(data, "登录信息", function(){
				window.location.reload();
			});
			
		},
		error : function() {
			
		}
	});
	
});
window.load=function(){
	$("#userName").val("");
	$("#password").val("");
};
function getPwdCode() {
	$("#forget_pwd_form").form({
		url : '/ecs/user/forgot/pwd/getCode.do',
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			dealMessageWithCallBack(data, "获取手机验证码", function(){
				sendMsgButtonDisable("getPwdCode");
				$(".color_6").show();
			});
		}
	});

	$("#forget_pwd_form").submit();

}

function resetPwdByMobile() {
	$("#reset_pwd_form").form({
		url : '/ecs/user/forgot/pwd/reset.do',
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			dealMessage(data, "重设置密码", "/login.jsp");
		},
		error :function(){
			alert("设置密码失败！");
		}
	});
	adm('reset_pwd_form');
}

function  adm(id){
	
	if(window.ActiveXObject)
    {
        var browser=navigator.appName;
        var b_version=navigator.appVersion; 
        var version=b_version.split(";"); 
        var trim_Version=version[1].replace(/[ ]/g,""); 
        if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE6.0") 
        { 
        	$("#"+id).submit();
        }else{
        }
    }else{
    }
}


function getRegCode() {

	var mobile = $("#mobileNumber").val();
	var imgCode = $("#userRegImgCode").val();
	if (!mobile || mobile == "") {
		alert("请输入手机号码");
		return;
	}
	
	if (!imgCode || imgCode == "") {
		alert("请输入验证码");
		return;
	}

	postAjaxRequest("/ecs/user/reg/getCode.do", {
		"mobileNumber" : mobile,
		"imgCode" : imgCode
		
	}, function(data) {
		$.messager.alert("信息", "验证码已发送手机，请注意查收", 'info');
		// 60秒倒计时
		sendMsgButtonDisable("getRegCode");
	});
}

function sendMsgButtonDisable(id) {
	var i = 90;
	var sid = window.setInterval(function() {
		$("#" + id).hide();
		$("#getRegCodeInfo").show();
		$("#getRegCodeInfo").html(i + "秒后可以再次发送");
		i = i - 1;

		if (i == 0) {
			$("#" + id).show();
			$("#getRegCodeInfo").hide();
			window.clearInterval(sid);
		}
	}, 1000);
}





function whichButton(event)
{
	if(event.keyCode==13){
		$("#submit").click();
	}
}