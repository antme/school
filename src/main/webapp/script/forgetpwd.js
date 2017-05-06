/**
 * Created by chun on 2016/12/24.
 */
$(document).ready(function(){
    //允许获取验证码
    allowGetVcode(true);
    //获取验证码
    $("#getVcode").on('click', function(ev){
        stopDefault(ev);
        if(telCheck($("#userTel"))){
            //禁止频繁获取验证码，显示倒计时
            allowGetVcode(false);
            var inData = {
                mobileNumber: $("#userTel").val()
            };
            $.ajax({
                type: 'get',
                url: ajaxUrlBase+"/sms/user/password/forgot.do",
                async: true,
                data: inData,
                dataType: 'jsonp',
                jsonp: "callback",
                success: function (data) {
                    var jsonData = eval(data);
                    var res = Number(jsonData['code']);
                    if(res == 1){
                    }else{
                        tipShow($("#userTel"), jsonData['msg']);
                    }
                },
                error:function(res){
                }
            });
        }
    });

    //提交
    $("#inSubmit").on('click', function(){
        if(telCheck($("#userTel")) && vcodeCheck($('#vcode')) && passwdCheck($("#userPasswd")) && passwd2Check($("#userPasswd2")) ) {
            var inData = new getInData();
            $.ajax({
                type: 'post',
                url: ajaxUrlBase+"/user/password/forgot/update.do",
                async: true,
                data: inData,
                dataType: 'jsonp',
                jsonp: "callback",
                success: function (data) {
                    var jsonData = eval(data);
                    var res = Number(jsonData['code']);
                    if(res == 1){
                        var $toast = $('#toast');
                        $toast.fadeIn(100);
                        setTimeout(function(){
                            $toast.fadeOut(100);
                            //跳转到登录页面
                            window.location.href = loginUrl;
                        }, 2000);
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
    inData.validCode = $("#vcode").val();
    inData.password = $("#userPasswd").val();
    inData.password2 = $("#userPasswd2").val();
    return inData;
}