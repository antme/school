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
     var sexArr = [
            {value:0, label:'男', code:'m'},
            {value:1, label:'女', code:'f'}
        ];
 //性别
    $('#sex').on('click', function () {
        weui.picker(sexArr, {
            onChange: function (result) {
            },
            onConfirm: function (result) {
                var $sex = $('#sex');
                $sex.text(sexArr[result].label);
                $sex.attr('data-code',sexArr[result].code);
            }
        });
    });
    //选择生日
    $('#birthDay').on('click', function () {
        weui.datePicker({
            start: 2006,
            depth:2,
            end: new Date().getFullYear(),
            onChange: function (result) {
            },
            onConfirm: function (result) {
                result = result.splice(0,2);
                $("#birthDay").text(result.join('-'));
            }
        });
    });
    //学生姓名校验
    function studentNameCheck($elem){
        var curVal = $elem.val();
        if (curVal == "") {
            tipShow($("#inSubmit"), "小朋友姓名不能为空");
            return false;
        } else {
            $elem.val(jQuery.trim(curVal));
            tipHide();
            return true;
        }
    }
    //性别校验
    function sexCheckText($elem){
        var curVal = $elem.text();
        if (curVal == "") {
            tipShow($("#inSubmit"), "性别不能为空");
            return false;
        } else {
            $elem.text(jQuery.trim(curVal));
            tipHide();
            return true;
        }
    }
    //生日校验
    function birthCheckText($elem){
        var curVal = $elem.text();
        if (curVal == "") {
            tipShow($elem, "生日不能为空");
            return false;
        } else {
            $elem.text(jQuery.trim(curVal));
            tipHide();
            return true;
        }
    }
    //注册
    $("#inSubmit").on('click', function(){
        if( studentNameCheck($("#studentName")) && sexCheckText($("#sex")) && birthCheckText($("#birthDay")) && nameCheck($("#parentName"))  && telCheck($("#userTel")) && vcodeCheck($('#vcode')) && passwdCheck($("#userPasswd")) && passwd2Check($("#userPasswd2")) ) {
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
    inData.studentName = $("#studentName").val();
    inData.sex = $("#sex").attr('data-code');
    inData.birthDay = $("#birthDay").text()+'-1';
    return inData;
}
