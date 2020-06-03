layui.config({
    base: "lib/layui-v2.5.4/lay/my_modules/"
}).extend({
    layuimini: "layuimini"
}).use(['element', 'layer', 'layuimini'], function () {
    var $ = layui.jquery,
        element = layui.element,
        layer = layui.layer, layuimini = layui.layuimini;
    // 初始化主题颜色

    //初始化 菜单
    var indexInit = function () {
        layuimini.initBgColor();
        layuimini.initDevice();
        layuimini.initHome({
            "title": "首页",
            "icon": "fa fa-home",
            "href": "pages/adminsManage/index.html"
        });
         // 初始化网站logo
//      layuimini.initLogo({
//          "title": "抢单后台",
//          "image": "images/icon.png",
//          "href": ""
//      });

        layuimini.initLogo({
            "title": "总控平台",
            "image": "images/2.png",
            "href": ""
        });


        var user = C.getCache('admin');
        if (user == null) {
            layer.msg("获取登录信息失败,请重新登录后在试!", function () {
                location.replace("login.html");
            })
            return false;  //false
        }
        
        $("#userName").text(user.userName);
        $.ajax({
            url: baseURL + "sysMenu/getallmenu",
            data: {
            	'adminId' : user.id,
                'source': 1
            },
            async: false,
            type: "post",
            dataType: "JSON",
            beforeSend:function(xhr){
            	xhr.setRequestHeader("X-Token",C.find("X-Token"));
            },
            success: function (data) {
                if (data.message == "success") {
                    var result = data.body;
                    var list = [];
                    for (var i = 0; i < result.length; i++) {
                        function getList(data) {
                            for (var j = 0; j < data.length; j++) {
                                if (data[j].list != null && data[j].list.length > 0) {
                                    getList(data[j].list);
                                } else {
                                    list.push(data[j]);
                                }
                            }
                        }

                        /*if (result[i].list.length > 0) {
                            getList(result[i].list);
                        }*/
                        list.push(result[i]);
                        result[i].list = null;
                    }

                    function sortNumber(a, b) {
                        return a.menuId - b.menuId
                    }

                    list.sort(sortNumber)
                    for (var i = 0; i < list.length; i++) {
                        list[i]['title'] = list[i].name;
                        list[i]['href'] = list[i].url;
                        list[i]['target'] = "_self";
                    }
                    sessionStorage.setItem("menuList", JSON.stringify(list));
                    layuimini.initMenu({
                        "currency": {
                            "title": "菜单",
                            "icon": "fa fa-address-book",
                            "child": getTreeNode(list, 'parentId', 'menuId')
                        }
                    });
                    //清楚缓存提示
                    layuimini.initClear({
                        "code": 1,
                        "msg": "服务端清理缓存成功"
                    });
                    // 初始化选项卡
                    layuimini.initTab();

                    $('.login-out').on("click", function () {
                        layer.msg('退出登录成功', function () {
                            window.location = 'login.html';
                        });
                    });
                } else {
                    layer.msg(data.message);
                }
            },
            error: function (e) {
                layer.msg("系统错误");
            }
        });
    }
    var findAjax = function (url, data, success) {
        $.ajax({
            type: "POST",
            url: baseURL + url,
            data: data,
            dataType: "JSON",
            async: false,
            success: function (result) {
                if (result.meta.success == true) {
                    success(result);
                    layer.closeAll('loading')
                } else {
                    layer.msg(result.meta.message);
                    layer.closeAll('loading')
                }

            },
            error: function (e) {
                layer.msg("系统错误");
                layer.closeAll('loading')
                //console.log(url);
            }
        });
    }
    /*findAjax("sensitive/readTxtToArr", {}, function (res) {
        window.mgz = res.data;
    })*/
    indexInit();
});
