/**
 * Created by chun on 2016/12/24.
 */

//登录校验
checkLogin();

$(document).ready(function(){
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
            start: 1980,
            end: new Date().getFullYear(),
            onChange: function (result) {
            },
            onConfirm: function (result) {
                $("#birthDay").text(result.join('-'));
            }
        });
    });

    //性别校验
    function sexCheckText($elem){
        var curVal = $elem.text();
        if (curVal == "") {
            tipShow($elem, "性别不能为空");
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
    //提交
    $("#inSubmit").on('click', function(){
        if (nameCheck($("#stdName")) && sexCheckText($("#sex")) && birthCheckText($("#birthDay"))) {
            var $this = $(this);
            if($this.hasClass("valid")){
                return;
            }
            var inData = new getInData();
            $.ajax({
                type: 'post',
                url: ajaxUrlBase+"/user/parent/submitStudent.do",
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
                        var $toast = $('#toast');
                        $toast.fadeIn(100);
                        setTimeout(function(){
                            $toast.fadeOut(100);
                            //跳转到学生列表页面
                            window.location.href = stdListUrl;
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
    inData.name = $("#stdName").val();
    inData.sex = $("#sex").attr('data-code');
    inData.birthday = $("#birthDay").text();
    return inData;
}