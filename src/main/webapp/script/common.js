/**
 * Created by chun on 2016/12/24.
 */

//项目定义
var ajaxUrlBase = "https://queue.dandgedu.com";
if(window.location.href.indexOf("queuetest.dandgedu.com") != -1){
    ajaxUrlBase = "https://queuetest.dandgedu.com";
}
//通信协议强制切换到https
var curProtocol = window.location.protocol;
var curHost = window.location.host;
if((curHost == 'queue.dandgedu.com')&&(curProtocol == 'http:')){
    var curHref = window.location.href;
    window.location.href = curHref.replace(curProtocol, "https:");
}
//网页地址
var indexUrl = "tnstate.html";  //取号页面
var loginUrl = "login.html";    //登录页面
var stdListUrl = "studentlist.html";  //我的小朋友页面
var mytnUrl = "mytn.html?v=1.0";  //我的取号信息页面
var tngoUrl = "tngo.html";  //具体校区取号页面

/* 取消事件的默认行为(兼容IE) */
function stopDefault(e) {
    if (e && e.preventDefault){
        e.preventDefault();
    } else {
        window.event.returnValue = false;
    }
}

$(document).ready(function(){
    //底部导航栏菜单
    mainMenuInit();
});

//登录判断
function checkLogin(){
    //发送服务器
    $.ajax({
        type: 'get',
        url: ajaxUrlBase+'/user/login/valid.do',
        async: true,
        data: '',
        dataType: 'jsonp',
        jsonp: "callback",
        success:function(data){
            var jsonData = eval(data);
            if(jsonData['isLogined'] == false){
                window.location.href = loginUrl;
            }
        },
        error:function(res){
        }
    });
}

//退出登录
function loginOut(){
    //发送服务器
    $.ajax({
        type: 'get',
        url: ajaxUrlBase+'/user/logout.do',
        async: true,
        data: '',
        dataType: 'jsonp',
        jsonp: "callback",
        success:function(data){
            var jsonData = eval(data);
            var res = Number(jsonData['code']);
            if(res == 1){
                window.location.href = loginUrl;
            }else{
                tipShow($("#loginOutObj"), "退出登录失败！");
            }
        },
        error:function(res){
        }
    });
}

//显示提示信息
function tipShow($elem, msgStr){
    $elem.tips({
        side: 1,
        msg: msgStr,
        bg: '#307FC1',
        time: 2
    });
}
//关闭提示信息
function tipHide(){
    $(".jq_tips_box").hide();
}

//获取请求参数
function GetRequest(){
    // var url = location.search; //获取url中"?"符后的字串
    var url = decodeURI(location.search);
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        var strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {
            var paraItem = strs[i].split("=");
            theRequest[paraItem[0]]=(paraItem[1]);
        }
    }
    return theRequest;
}

//性别字符串转换
function sexFat(sex){
    var sexStr = "男";
    if(sex == "f"){
        sexStr = "女";
    }
    return sexStr;
}
//出生日期字符串转换
function birthFat(birth){
    return (birth.split('-')).join('.');
}

//菜单样式切换
function weuiTabbarGo(elem){
    var curElem = elem;
    var myClass = "weui-bar__item_on";
    var $tnTabbar = $("#tnTabbar");
    var $navs = $tnTabbar.find(".weui-tabbar__item");
    var index = $navs.index(curElem);
    $navs.removeClass(myClass);
    $(curElem).addClass(myClass);
}
// 底部导航栏菜单
function mainMenuInit(){
    var $actionSheetWrap = $("#actionSheetWrap");
    var $tnActionsheet = $('#tnActionsheet');
    var $iosActionsheet = $('#iosActionsheet');
    var $informActionsheet = $('#informActionsheet');
    var $iosMask = $('#iosMask');

    function hideActionSheet() {
        $actionSheetWrap.find(".weui-actionsheet").removeClass('weui-actionsheet_toggle');
        $iosMask.fadeOut(200);
    }

    $iosMask.on('click', hideActionSheet);
    //个人中心
    $('#iosActionsheetCancel').on('click', hideActionSheet);
    $("#centerMenuGo").on("click", function(ev){
        stopDefault(ev);
        weuiTabbarGo(this);
        $actionSheetWrap.find(".weui-actionsheet").removeClass('weui-actionsheet_toggle');
        $iosActionsheet.addClass('weui-actionsheet_toggle');
        $iosMask.fadeIn(200);
    });
    //取号信息
    $('#tnActionsheetCancel').on('click', hideActionSheet);
    $("#takeNumGo").on("click", function(ev){
        stopDefault(ev);
        weuiTabbarGo(this);
        $actionSheetWrap.find(".weui-actionsheet").removeClass('weui-actionsheet_toggle');
        $tnActionsheet.addClass('weui-actionsheet_toggle');
        $iosMask.fadeIn(200);
    });
    //通知
    $('#informActionsheetCancel').on('click', hideActionSheet);
    $("#informGo").on("click", function(ev){
        stopDefault(ev);
        weuiTabbarGo(this);
        $actionSheetWrap.find(".weui-actionsheet").removeClass('weui-actionsheet_toggle');
        $informActionsheet.addClass('weui-actionsheet_toggle');
        $iosMask.fadeIn(200);
    });

    //退出登录注册
    $("#loginOutObj").on("click", loginOut);
}

//校验相关函数

//手机号码校验
function telCheck($elem){
    var curVal = $elem.val();
    if (curVal == "") {
        tipShow($elem, "手机号不能为空");
        return false;
    } else if (curVal.length != 11) {
        tipShow($elem, "请输入有效手机号");
        return false;
    } else {
        $elem.val(jQuery.trim(curVal));
        tipHide();
        return true;
    }
}
//密码校验
function passwdCheck($elem){
    var curVal = $elem.val();
    if (curVal == "") {
        tipShow($elem, "密码不能为空");
        return false;
    } else if (curVal.length < 6) {
        tipShow($elem, "密码长度不能小于6位");
        return false;
    } else {
        $elem.val(jQuery.trim(curVal));
        tipHide();
        return true;
    }
}
//确认密码校验
function passwd2Check($elem){
    var newPasswd = $("#userPasswd").val();
    var curVal = $elem.val();
    if (curVal == "") {
        tipShow($elem, "密码不能为空");
        return false;
    } else if (curVal != newPasswd) {
        tipShow($elem, "两次输入密码不一致");
        return false;
    } else {
        $elem.val(jQuery.trim(curVal));
        tipHide();
        return true;
    }
}
//验证码校验
function vcodeCheck($elem){
    var curVal = $elem.val();
    if (curVal == "") {
        tipShow($elem, "验证码不能为空");
        return false;
    } else {
        $elem.val(jQuery.trim(curVal));
        tipHide();
        return true;
    }
}
//姓名校验
function nameCheck($elem){
    var curVal = $elem.val();
    if (curVal == "") {
        tipShow($elem, "姓名不能为空");
        return false;
    } else {
        $elem.val(jQuery.trim(curVal));
        tipHide();
        return true;
    }
}
//性别校验
function sexCheck($elem){
    var curVal = $elem.val();
    if (curVal == "") {
        tipShow($elem, "性别不能为空");
        return false;
    } else {
        $elem.val(jQuery.trim(curVal));
        tipHide();
        return true;
    }
}
//生日校验
function birthCheck($elem){
    var curVal = $elem.val();
    if (curVal == "") {
        tipShow($elem, "生日不能为空");
        return false;
    } else {
        $elem.val(jQuery.trim(curVal));
        tipHide();
        return true;
    }
}

//是否允许获取短信验证码 flag : true->允许; fasle->禁止，显示倒计时
function allowGetVcode(flag){
    var invalid = "invalid";
    var secNum = 90;
    var secTxt = "秒后重取";
    var noTimer;
    var $vcodeBox = $("#vcodeBox");
    var $vcodeNo = $("#vcodeNo");
    if(!flag){
        $vcodeNo.text(secNum+secTxt);
        $vcodeBox.addClass(invalid);
        noTimer = setInterval(function(){
            secNum--;
            $vcodeNo.text(secNum+secTxt);
            if(secNum < 0){
                clearInterval(noTimer);
                $vcodeBox.removeClass(invalid);
            }
        },1000);
    }
    else{
        if(noTimer){
            clearInterval(noTimer);
        }
        $vcodeBox.removeClass(invalid);
    }
}
