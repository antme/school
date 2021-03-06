/**
 * Created by chun on 2016/12/24.
 */

//登录校验
checkLogin();
$(document).ready(function(){
    var $mytnTips = $("#mytnTips"); 
    $mytnTips.hide();
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
                    var baomingMsg = curData['baomingMsg'];
                    if (baomingMsg.length == 0) {
                        baomingMsg = "校区取号时间段结束后，再过10-15分钟在此处查看报名时间地点通知";
                    }

                    $con.find(".message .txt").text(baomingMsg);
                    //追加到列表
                    $list.append($cloneHtml[0]);
                }
                $list.show();
                if (arrSize > 0) {
                    $mytnTips.hide();    
                }else{
                    $mytnTips.show();    
                }
                
            }else{
                tipShow($list, jsonData['msg']); 
            }
        },
        error:function(res){
        }
    });
});