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
                url: ajaxUrlBase+"/sms/parent/reg.do",
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

    //注册
    $("#inSubmit").on('click', function(){
        if( nameCheck($("#parentName")) && telCheck($("#userTel")) && vcodeCheck($('#vcode')) && passwdCheck($("#userPasswd")) && passwd2Check($("#userPasswd2")) ) {
            var $this = $(this);
            if($this.hasClass("valid")){
                return;
            }
            var inData = new getInData();
            $.ajax({
                type: 'post',
                url: ajaxUrlBase+"/user/parent/submit.do",
                async: true,
                data: inData,
                dataType: 'jsonp',
                jsonp: "callback",
                success: function (data) {
                    $this.addClass("valid");
                    setTimeout(function(){
                        $this.removeClass("valid");
                    }, 3000);
                    var jsonData = eval(data);
                    var res = Number(jsonData['code']);
                    if(res == 1){
                        var msgStr = "请点击“个人中心”，进入“我的小朋友”，添加小朋友信息。否则您将无法取号！";
                        layer.alert(msgStr, {
                            title: ['提示',"color:#FFF;background:#4376a7;"],
                            closeBtn: 0,
                            btn: ['确定']
                        }, function(){
                            //登录
                            var inPara = {
                                mobileNumber: $("#userTel").val(),
                                password: $("#userPasswd").val()
                            };
                            $.ajax({
                                type: 'post',
                                url: ajaxUrlBase+"/user/login.do",
                                async: true,
                                data: inPara,
                                dataType: 'jsonp',
                                jsonp: "callback",
                                success: function (data) {
                                    var jsonData = eval(data);
                                    var res = Number(jsonData['code']);
                                    if(res == 1){
                                        layer.msg('登录成功！', {icon:1,time:1000});
                                        setTimeout(function(){
                                            window.location.href = indexUrl;
                                        }, 1000);
                                    }else{
                                        layer.msg('登录失败！', {icon:2,time:1000});
                                        setTimeout(function(){
                                            window.location.href = loginUrl;
                                        }, 1000);
                                    }
                                },
                                error:function(res){
                                }
                            });
                        });
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
    inData.name = $("#parentName").val();
    return inData;
}
