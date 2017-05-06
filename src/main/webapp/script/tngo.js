/**
 * Created by chun on 2016/12/24.
 */

//登录校验
checkLogin();

$(document).ready(function(){
    var goTextArr = ["仅限百花大班在读会员取号", "已经取号"];

    var hrefPara = GetRequest();
    //标题
    var $tngoCompName = $("#tngoCompName");
    $tngoCompName.text(hrefPara['name']);
    $tngoCompName.attr('data-id',hrefPara['id']);
    var inData = {
        id: hrefPara['id']
    };
    //获取校区取号列表
    $.ajax({
        type: 'post',
        url: ajaxUrlBase+"/sch/student/avaliable/mine.do",
        async: true,
        data: '',
        dataType: 'jsonp',
        jsonp: "callback",
        success: function (data) {
            var jsonData = eval(data);
            var $list = $("#tngoList");
            $list.html('');
            var res = Number(jsonData['code']);
            if(res == 1){
                $list.hide();
                var dataArr = jsonData['rows'];
                var arrSize = dataArr.length;
                var $tngoExplain = $("#tngoExplain");
                if(arrSize < 1){
                    $tngoExplain.show();
                }else{
                    $tngoExplain.hide();
                }
                for(var i=0; i<arrSize; i++){
                    var curData = dataArr[i];
                    var $cloneHtml = $("#tngoliDemo").clone();
                    //学生信息
                    var stdInfo = curData['name']+'-'+sexFat(curData['sex'])+'-'+birthFat(curData['birthday']);
                    $cloneHtml.find(".tngoli-std").text(stdInfo);
                    //取号按钮
                    var $link = $cloneHtml.find(".tngoli-link a");
                    $link.attr('data-id',curData['id']);
                    //判断是否已经取号
                    var hasNumber = curData['hasNumber']; //是否已经取号
                    var linkText = goTextArr[0];
                    $link.attr('data-num', hasNumber);
                    if(hasNumber){
                        $link.addClass("valid");
                        linkText = goTextArr[1];
                    }else{
                        $link.removeClass("valid");
                    }
                    $link.text(linkText);
                    //注册点击事件
                    $link.on('click', function(){
                        var $this = $(this);
                        if($this.hasClass("valid")){
                            tipShow($this, "此学生已经取号");
                            return ;
                        }
                        //提示框
                        layer.confirm('一位小朋友只能选择一个校区，并且只有一次取号机会，请谨慎确认校区，您确认取号吗？', {
                            title: ['提示',"color:#FFF;background:#4376a7;"],
                            btn: ['取号','取消']
                        }, function(){
                            //取号
                            var inData = {
                                schoolId: $("#tngoCompName").attr('data-id'),
                                studentId: $this.attr('data-id')
                            };
                            //发送服务器
                            $.ajax({
                                type: 'get',
                                url: ajaxUrlBase+'/sch/book.do',
                                async: true,
                                data: inData,
                                dataType: 'jsonp',
                                jsonp: "callback",
                                success:function(data){
                                    var jsonData = eval(data);
                                    var res = Number(jsonData['code']);
                                    if(res == 1){
                                        var msgStr = "取号已成功，您的号码为 "+$("#tngoCompName").text()+"-"+jsonData['data']['number'] +"号 。请您留意手机短信，在报名前仔细阅读《校区报名须知》，提前准备好资料，谢谢!";
                                        layer.alert(msgStr, {
                                            title: ['提示',"color:#FFF;background:#4376a7;"],
                                            btn: ['确定']
                                        }, function(){
                                            window.location.href = mytnUrl;
                                        });
                                        $this.addClass("valid").text(goTextArr[1]);
                                    }else{
                                        layer.msg('', {time: 10});
                                        tipShow($this, jsonData['msg']);
                                    }
                                },
                                error:function(res){
                                }
                            });
                        }, function(){
                            return;
                        });
                    });
                    //追加到列表
                    $list.append($cloneHtml[0]);
                }
                $list.show();
            }else{
                tipShow($list, jsonData['msg']);
            }
        },
        error:function(res){
        }
    });
});
