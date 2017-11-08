/**
 * Created by chun on 2016/12/24.
 */

//登录校验
checkLogin();

$(document).ready(function(){
    //提交
    $("#inSubmit").on('click', function(){
        if( passwdCheck($("#userPasswd")) && passwd2Check($("#userPasswd2")) ) {
            var inData = new getInData();
            $.ajax({
                type: 'post',
                url: ajaxUrlBase+"/user/password/update.do",
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
                            window.location.href = indexUrl;
                        }, 1000);
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
    inData.password = $("#userPasswd").val();
    inData.password2 = $("#userPasswd2").val();
    return inData;
}
