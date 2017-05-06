/**
 * Created by chun on 2016/12/24.
 */

//登录校验
checkLogin();

$(document).ready(function(){
    //获取家长信息
    function parentGet(){
        $.ajax({
            type: 'get',
            url: ajaxUrlBase+"/user/parent/mine.do",
            async: true,
            data: '',
            dataType: 'jsonp',
            jsonp: "callback",
            success: function (data) {
                var jsonData = eval(data);
                var dataObj = jsonData['data'];
                $("#userTel").text(dataObj['mobileNumber']);
                $("#parentName").text(dataObj['name']);
            },
            error:function(res){
            }
        });
    }
    parentGet();
});
