/**
 * Created by chun on 2016/12/24.
 */

//登录校验
checkLogin();

$(document).ready(function(){
    //获取校区取号列表
    $.ajax({
        type: 'post',
        url: ajaxUrlBase+"/sch/list.do",
        async: true,
        data: '',
        dataType: 'jsonp',
        jsonp: "callback",
        success: function (data) {
            var jsonData = eval(data);
            var res = Number(jsonData['code']);
            var $list = $("#campusList");
            $list.html('');
            if(res == 1){
                $list.hide();
                var dataArr = jsonData['rows'];
                var arrSize = dataArr.length;
                for(var i=0; i<arrSize; i++){
                    var curData = dataArr[i];
                    var $cloneHtml = $("#campusliDemo").clone();
                    //校区名称
                    var $tit = $cloneHtml.find(".campusli-tit");
                    $tit.find(".sort").text(i+1);
                    $tit.find(".name").text(curData['name']);
                    //取号时间
                    var $time = $cloneHtml.find(".campusli-time");
                    $time.find(".day").text(curData['takeNumberDate']);
                    $time.find(".starttime").text(curData['startTime']);
                    $time.find(".endtime").text(curData['endTime']);
                    //跳转按钮
                    var $link = $cloneHtml.find(".campusli-go a");
                    $link.attr('data-id',curData['id']);
                    $link.find(".name").text(curData['name']);
                    var takeStatus = curData['takeStatus'];
                    $link.attr('data-take', takeStatus);
                    if(takeStatus != "1"){
                        $link.addClass("valid");
                    }else{
                        $link.removeClass("valid");
                        //填充链接地址
                        var myurl = tngoUrl+"?id="+curData['id']+"&name="+curData['name'];
                        // encodeURI 编码
                        myurl = encodeURI(myurl);
                        $link.attr('href', myurl);
                    }
                    //注册点击事件
                    $link.on('click', function(){
                        var $this = $(this);
                        var takeStatus = $this.attr('data-take');
                        if(takeStatus == "0"){
                            tipShow($this, "取号时间未到！");
                            return false;
                        }else if(takeStatus == "2"){
                            tipShow($this, "取号时间已过！");
                            return false;
                        }else{
                            window.location.href = $this.attr('href');
                        }
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