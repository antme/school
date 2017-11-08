/**
 * Created by chun on 2016/12/24.
 */
//登录校验
checkLogin();

$(document).ready(function(){
    //定义只能拥有学生数量
    var stdNum = 1;

    function stdListInit(){
        $("#studentList").hide();
        $("#stdAddBtn").show();
        //获取列表
        $.ajax({
            type: 'get',
            url: ajaxUrlBase+"/user/parent/listStudent.do",
            async: true,
            data: '',
            dataType: 'jsonp',
            jsonp: "callback",
            success: function (data) {
                var jsonData = eval(data);
                var $studentList = $("#studentList");
                var $list = $("#stulistBody");
                $studentList.hide();
                $list.html('');
                var res = Number(jsonData['code']);
                if(res == 1){
                    $list.hide();
                    var dataArr = jsonData['rows'];
                    var $stdAddBtn = $("#stdAddBtn");
                    var arrSize = dataArr.length;
                    if(arrSize >= stdNum){
                        $stdAddBtn.hide();
                    }else{
                        $stdAddBtn.show();
                    }
                    for(var i=0; i<arrSize; i++){
                        var curData = dataArr[i];
                        var $cloneHtml = $("#stulistDemo").find('tr').clone();
                        $cloneHtml.attr('data-id',curData['id']);
                        $cloneHtml.find(".sort").text(i+1);
                        $cloneHtml.find(".name").text(curData['name']);
                        $cloneHtml.find(".birth").text(curData['birthday']);
                        //转换性别
                        $cloneHtml.find(".sex").text(sexFat(curData['sex']));
                        //追加到列表
                        $list.append($cloneHtml[0]);
                    }
                    if(arrSize <= 0){
                        $studentList.hide();
                    }else{
                        $studentList.show();
                    }
                    $list.show();
                }else{
                    tipShow($list, jsonData['msg']);
                }
            },
            error:function(res){
            }
        });
    }
    //列表初始化
    stdListInit();
});