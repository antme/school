/**
 * Created by chun on 2016/12/24.
 */

//登录校验
checkLogin();

$(document).ready(function(){
    //获取列表
    $.ajax({
        type: 'get',
        url: ajaxUrlBase+"/sch/student_school/mine.do",
        async: true,
        data: '',
        dataType: 'jsonp',
        jsonp: "callback",
        success: function (data) {
            var jsonData = eval(data);
            var $list = $("#mytnList");
            $list.html('');
            var res = Number(jsonData['code']);
            if(res == 1){
                $list.hide();
                var dataArr = jsonData['rows'];
                var arrSize = dataArr.length;
                for(var i=0; i<arrSize; i++){
                    var curData = dataArr[i];
                    var $cloneHtml = $("#mytnliDemo").clone();
                    //标题
                    var $tit = $cloneHtml.find(".mytnli-tit");
                    $tit.find(".campname").text(curData['school']['name']);
                    $tit.find(".sort").text(curData['number']);
                    //内容
                    var $con = $cloneHtml.find(".mytnli-con");
                    //会员
                    var studentObj = curData['student'];
                    var sexStr = sexFat(studentObj['sex']);
                    var birthStr = birthFat(studentObj['birthday']);
                    $con.find(".member .txt").text(studentObj['name']+"-"+sexStr+"-"+birthStr);
                    //家长信息
                    $con.find(".parent .txt").text(curData['parent']['name']);
                    $con.find(".tel .txt").text(curData['parent']['mobileNumber']);
                    //取号时间
                    $con.find(".create .txt").text(curData['createdOn']);
                    $con.find(".message .txt").text(curData['baomingMsg']);
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