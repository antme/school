/**
 * Created by chun on 2016/12/24.
 */
$(document).ready(function(){
    //登录
    $("#inSubmit").on('click', function(){
        if (telCheck($("#userTel")) && passwdCheck($("#userPasswd"))) {
            var inData = new getInData();
            $.ajax({
                type: 'post',
                url: ajaxUrlBase+"/user/login.do?ajax_session=true",
                async: true,
                data: inData,
                dataType: 'jsonp',
                jsonp: "callback",
                success: function (data) {
                    var jsonData = eval(data);
                    var res = Number(jsonData['code']);
                    if(res == 1){
                    	//window.location.href='tnstate.html';
                    	$("body").append('<iframe id="sessionframe" name="sessionframe" onload="redirect();" src=' + ajaxUrlBase + '/safari_login.jsp?skey="' + jsonData["s_key"] +' style="display:none;"></iframe>');                  
                    }else{
                        tipShow($("#inSubmit"), jsonData['msg']);
                    }
                },
                error:function(res){
                }
            });
        }
    });
});
//获取输入数据
function getInData(){
    var inData = {};
    inData.mobileNumber = $("#userTel").val();
    inData.password = $("#userPasswd").val();
    return inData;
}

function redirect(){
    window.location.href='tnstate.html';
}