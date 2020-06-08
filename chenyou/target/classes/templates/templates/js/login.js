// 粒子线条背景
$(document).ready(function () {
    $('.layui-container').particleground({
        dotColor: '#5cbdaa',
        lineColor: '#5cbdaa'
    });
});
layui.config({
    base: "lib/layui-v2.5.4/lay/my_modules/"
}).extend({
    layuimini: "layuimini"
}).use(['element', 'layer', 'layuimini'], function () {
    var element = layui.element,
        layer = layui.layer, layuimini = layui.layuimini;
    C.removeCache('token');
    C.removeCache('admin');
    
   
	
	
    $('[lay-filter="login"]').on('click', function (obj) {
        var username = $('[name="username"]').val();
        var password = $('[name="password"]').val();
        var captcha = $('[name="captcha"]').val();
        if (verVal != captcha.toUpperCase()) {
            layer.msg("验证码不正确");
            return false;
        }
        var params = {
        	"password":password,
        	"userName":username
        }
        $.ajax({
            url: baseURL + "manager/login",
            data: JSON.stringify(params),
            async: false,
            type: "post",
            dataType: "JSON",
            contentType: 'application/json',
            success: function (data) {
            	//console.log(data);
            	if(data.errorCode == 0){
            		layer.msg("登录成功.",function(obj){
						var result = data.body;
                        C.setCache("token", result.token, 21600);
                    	C.setCache("admin", result.userInfo, 21600);
                    	C.save("X-Token",result.tokenType+" "+result.token)
                    	location.replace("index.html");
					});
            	} else {
                	layer.msg(data.message)
                	return;
                }
            },
            error: function (e) {
                layer.msg("系统错误");
            }
        });
    });
});
