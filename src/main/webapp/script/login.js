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
                url: ajaxUrlBase+"/user/login.do",
                async: true,
                data: inData,
                dataType: 'jsonp',
                jsonp: "callback",
                success: function (data) {
                    var jsonData = eval(data);
                    var res = Number(jsonData['code']);
                    if(res == 1){
                        window.location.href = indexUrl;
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
