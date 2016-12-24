$.extend($.fn.validatebox.defaults.rules,{   
    CHS: {
        validator: function (value, param) {
            return /^[\u0391-\uFFE5\w]+$/.test(value);
        },
        message: '请输入中文'
    },
    mobile: {// 验证手机号码
        validator: function (value) {
            return /^(13|15|18)\d{9}$/i.test(value);
        },
        message: '手机号码格式不正确'
    },
    number: {
        validator: function (value, param) {
            return /^\d+$/.test(value);
        },
        message: '该项只能输入数字'
    },
    idcard: {// 验证身份证
        validator: function (value) {
            //return idCard(value);
        	return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message: '身份证号码格式不正确'
    },
    pwdEquals: {
        validator: function(value,param){
            return value == $(param[0]).val();
        },
        message: '密码不匹配'
    },
    username : {// 验证用户名         
    	validator : function(value) {              
    		return /^[a-zA-Z0-9_]{6,16}$/i.test(value);           
    	},          
    	message : '请输入6~16位字母数字下划线组成的密码'      //（字母开头，允许6-16字节，允许字母数字下划线）
    },
    pwd : {      
    	validator : function(value) {              
    		//return /^[a-zA-Z0-9_]{6,16}$/i.test(value);   
    		return /^[a-zA-Z0-9_!@#$%^&*]{6,16}$/i.test(value);
    	},          
    	message : '请输入6~16位密码'      //（字母开头，允许6-16字节，允许字母数字）
    },
    unnormal:{// 验证是否包含空格和非法字符
        validator : function(value) {
        	return /.+/i.test(value);
        },
        message : '输入值不能为空和包含其他非法字符'
    },
    isNumber: {
        validator: function (value, param) {
             return /^-?\d+\.?\d*$/.test(value);
        },
        message: '请输入正确的数字!'
    },
    
    macAddr: {
    	validator: function(value) {
	    	return /[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}:[A-Fa-f0-9]{2}/.test(value);
           
    	},
    	message: '请输入正确的Mac地址'
    },
    
    domainType: {
    	validator: function(value) {
    		return /^((https?|ftp|news):\/\/)?([a-z]([a-z0-9\-]*[\.。])+([a-z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel)|(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))(\/[a-z0-9_\-\.~]+)*(\/([a-z0-9_\-\.]*)(\?[a-z0-9+_\-\.%=&]*)?)?(#[a-z][a-z0-9_]*)?$/.test(value);
    	},
    	message: '请输入正确的域名'
    },
    
    postCode: {
    	validator: function(value) {
    		return /^[1-9]\d{5}$/.test(value);
    	},
    message: '请输入正确的邮编'
    },
    
}); 
