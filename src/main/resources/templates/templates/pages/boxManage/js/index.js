layui.config({
    base: "{/}/templates/lib/layui-v2.5.4/lay/my_modules/"
}).extend({
    layuimini: 'layuimini',
    formSelects: 'formSelects/formSelects-v4',
    treeTable: 'treeTable/treeTable'
}).use(['laypage', 'layer', 'form', 'table', 'laydate', 'element', 'layuimini', 'carousel', 'treeTable'], function () {
    var laypage = layui.laypage,
        layer = layui.layer,
        form = layui.form,
        table = layui.table,
        element = layui.element,
        laydate = layui.laydate,
        layuimini = layui.layuimini,
        carousel = layui.carousel,
        treeTable = layui.treeTable;
    var user = C.getCache('admin');


    var findAjax = function (url, data, success) {
        //console.log(data);
        $.ajax({
            type: "POST",
            url: baseURL + url,
            data: JSON.stringify(data),
            dataType: "JSON",
            contentType: "application/json",
            async: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader("X-Token", C.find("X-Token"));
            },
            success: function (result) {
                if (result.errorCode + "" === "0") {
                    success(result);
                    layer.closeAll('loading');
                } else {
                    success(result.msg);
                    layer.closeAll('loading');
                }
            },
            error: function (e) {
                layer.msg("系统错误");
                layer.closeAll('loading');
            }
        });
    };


    // 渲染表格
    function initTable(data) {
        //console.log(data);
        var insTb = treeTable.render({
            elem: '#demoTb1',
            data: data,  // 数据
            /*tree: {
                iconIndex: 1, // 折叠图标显示在第几列
                idName: 'menuId',  // 自定义id字段的名称
            },*/
            cols: [
                {
                    field: 'id',
                    title: '箱子编号',
                    align: 'center',
                    width: 150
                }, {
                    field: 'name',
                    title: '名称',
                    edit: 'text',
                    align: 'center'
                }, {
                    field: 'image',
                    title: '主图',
                    align: 'center',
                    templet: function (d) {
                        return "<img src='" + d.image + "' width='50'height='50'/>"
                    }
                }, {
                    field: 'price',
                    title: '价格',
                    align: 'center',
                    edit: 'text'
                },
                {
                    field: 'type',
                    title: '类型',
                    align: 'center',
                    templet: function (d) {
                        var strs = [
                            '<span class="layui-badge layui-badge-green">限量</span>',
                            '<span class="layui-badge layui-badge-green">稀有</span>',
                            '<span class="layui-badge layui-badge-green">特殊</span>',
                            '<span class="layui-badge layui-badge-green">经典</span>',
                            '<span class="layui-badge layui-badge-green">YOUTUBE 武器箱</span>',
                            '<span class="layui-badge layui-badge-green">仅在SKINCLUB</span>',
                            '<span class="layui-badge layui-badge-green">收藏</span>',

                        ];
                        return strs[d.type];
                    }
                },{
                    field: 'outTime',
                    title: '过期时间',
                    align: 'center',
                },
                {
                    title: '状态',
                    align: 'center',
                    templet: '#switchTpl',
                    unresize: true
                },
                {
                    title: '操作',
                    width: 200,
                    fixed: 'right',
                    align: 'center',
                    templet: function (d) {
                        return "<button class='layui-btn layui-btn-xs layui-btn-normal' lay-event='updatenavi'>编辑</button>" +
                            // "<button class='layui-btn layui-btn-xs layui-btn-normal' iflag='" + d.id + "' lay-event='chakan'>查看</button>" +
                            "<button class='layui-btn layui-btn-xs layui-btn-normal' iflag='" + d.id + "' lay-event='updateInfo'>修改信息</button>" +
                            "<button class='layui-btn layui-btn-xs layui-btn-warm' lay-event='delnavi'>删除</button>";
                    }
                }
            ]
        });

    }


    function changeList(list) {
        initTable(list);
    }


    function show() {
        var list = [];
        findAjax("box/getAllBox", {}, function (res) {
            var datas = res.body;
            for (var i = 0; i < datas.length; i++) {
                list = list.concat(datas[i]);
            }
            changeList(list);

        });
    }


    show();

    $('[lay-filter="toSteam"]').on("click", function (obj) {
        $.ajax({
            type: "POST",
            url: baseURL + "steam/login",
            dataType: "json",
            success: function (res) {
                if(res.body!=null){
                    window.open(res.body);
                }
            }
        });
    });

    $('[lay-filter="zfb"]').on("click", function (obj) {
        $.post(baseURL + "zfb/pagePay",{"amount":"1","playerId":"1"},function (res) {
            console.log(res.body)
            $(".tzzfb").html(res.body)
        },"json")
    });

    $('[lay-filter="gouBox"]').on("click",function (obj) {
        findAjax("playersArms/buybox", {
            "boxId": 2,
            "id": 1
        }, function (res) {

        })
    })

    $('[lay-filter="huoqbl"]').on("click",function (obj) {
        findAjax("upgrading/huoqujl", {
            "oldPrice": 13,
            "newPrice": 14
        }, function (res) {

        })
    })

    $('[lay-filter="shengji"]').on("click",function (obj) {
        findAjax("upgrading/shenji", {
            "jilv": 2.00,
            "newPrice": 14
        }, function (res) {

        })
    })

    $('[lay-filter="addSysmenu"]').on('click', function (obj) {
    layer.open({
        type: 1,
        title: "添加箱子",
        id: "alert",
        area: ['90%', '90%'],
        content: $("#addBox").html(),
        success: function (layero, index) {
            laydate.render({
                elem: 'input[name="outTime"]',
                type: 'datetime',
                trigger: 'click'
            });

            layui.use(['form', 'upload'], function () {
                var $ = layui.jquery, upload = layui.upload;
                //普通图片上传
                var uploadInst = upload.render({
                    elem: '#test1'
                    , url: baseURL + 'api/file/upload' //改成您自己的上传接口
                    , before: function (obj) {
                        //预读本地文件示例，不支持ie8
                        obj.preview(function (index, file, result) {
                            $('#image').attr('src', result); //图片链接（base64）
                        });
                    }
                    , done: function (res) {
                        //如果上传失败
                        if (res.code > 0) {
                            return layer.msg('上传失败');
                        }
                        //上传成功
                        $('#image').attr('src', baseURL + res.body.filePathURL);
                    }
                    , error: function () {
                        //演示失败状态，并实现重传
                        var demoText = $('#demoText');
                        demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                        demoText.find('.demo-reload').on('click', function () {
                            uploadInst.upload();
                        });
                    }
                });
            });

            form.render('select');
            $("#commit").on('click', function (obj) {
                var name = $(" [name='name']").val();
                var image = $(" #image").attr("src");
                var price = $(" [name='price']").val();
        /*        var count = $(" [name='count']").val();*/
                var isStatus = $(" [name='isStatus']").val();
                var type = $("[name='type']").val();
                var outTime = $("[name='outTime']").val();
                var josn = new Array();
                $("[name='glArmsId']").each(function (res) {

                    var id = $(this).val();
                    var jl = $(this).parent(".layui-inline").find("[name='chance']").val();
                    var kc = $(this).parent(".layui-inline").find("[name='count']").val();
                    var obj = {"chance": jl, "count": kc, "id": id};
                    josn.push(obj)
                })
                var zjlv=$("[name='zjlv']").val();
                if(zjlv!="100"){
                    alert("总几率需为100，请调整几率！");
                }
                findAjax("box/addInfo", {
                    "name": name,
                    "image": image,
                    "price": price,
                   /* "count": count,*/
                    "isStatus": isStatus,
                    "type": type,
                    "outTime" : outTime,
                    "glId": JSON.stringify(josn)
                }, function (res) {
                    if (res.message == 'success') {
                        layer.msg("添加成功", function () {
                            setTimeout($.ajax({
                                type: "POST",
                                url: baseURL + "arms/getAll",
                                data: {
                                    adminId: user.id
                                },
                                dataType: "json",
                                success: function () {
                                }
                            }), 100);

                            layer.close(index);
                            show();
                        });
                    }
                });
            });
            $("#cancle").on('click', function (obj) {
                layer.close(index);
            });


        }
    });
});


form.on('switch(sources)', function (obj) {
    //layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
    if (obj.elem.checked) {
        //恢复操作
        $.ajax({
            type: "post",
            url: baseURL + "arms/updateAramStatus",
            data: {
                id: this.value,
                "isStatus": "0"
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader("X-Token", C.find("X-Token"));
            },
            success: function (res) {
                if (res.message == 'success') {
                    layer.msg('恢复成功');
                    show();
                }
            }
        });
    } else {
        //冻结操作
        $.ajax({
            type: "post",
            url: baseURL + "arms/updateAramStatus",
            data: {
                "id": this.value,
                "isStatus": "1"
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader("X-Token", C.find("X-Token"));
            },
            success: function (res) {
                if (res.message == 'success') {
                    layer.msg('冻结成功');
                    show();
                }
            }
        });
    }
});
var xgFlag = "1";
treeTable.on('tool(demoTb1)', function (obj) {
    var data = obj.data;  // 获得当前行数据
    var layEvent = obj.event; // 获得lay-event对应的值
    if (layEvent === 'delnavi') {
    alert(data.id);
        layer.confirm("确认删除此箱子吗?", function () {
            findAjax("box/romveBox", {"id": data.id}, function (res) {
                if (res.message == 'success') {
                    layer.msg('删除成功！');

                    show();
                }
            })
        });

    } else if (layEvent === 'updatenavi') {
        //console.log(data);
        layer.confirm("确认编辑吗?", function () {
            findAjax("arms/updateById", {
                "id": data.id,
                "name": data.name,
                "image": data.image,
                "price": data.price,
              /*  "count": data.count,*/
                "productId": data.productId
            }, function (res) {
                if (res.message == 'success') {
                    layer.msg('编辑成功');
                    setTimeout($.ajax({
                        type: "POST",
                        url: baseURL + "arms/getAll",
                        data: {
                            adminId: user.id
                        },
                        dataType: "json",
                        success: function () {
                        }
                    }), 100);

                    layer.close(index);
                    show();
                }
            })
        });
    } else if (layEvent === 'chakan') {
        var iflag = $(this).attr("iflag")
        findAjax("boxArms/getAll", {
            "boxId": iflag,
        }, function (res) {
            if (res.message == 'success') {
                var data = res.body;
                var htmlInfo = "";
                for (var i = 0; i < data.length; i++) {
                    var info = data[i];
                    htmlInfo += "<div class=\"layui-form-item\" style=\"display: inline-block;\">\n" +
                        "                    <div class=\"layui-input-block\" style=\"border-style: none;display: inline-block;\">\n" +
                        "                        <div class=\"layui-inline\" style=\"width: 100px;\">\n" +
                        "                            <img src=\"" + info.arms.imageUrl + "\" width=\"80\" height=\"80\" style=\"display: block;margin-bottom: 5px\"/>\n" +
                        "                            <p style='text-align: center;width: 80px;overflow: hidden;white-space: nowrap;text-overflow:ellipsis;'>几率:" + info.chance + "</p>\n" +
                        "                            <p style='text-align: center;width:80px;'>数量:" + info.count + "</p>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "                </div>";
                }
                layer.open({
                    type: 1,
                    title: "",
                    id: "alert2",
                    area: ['90%', '90%'],
                    content: $("#addArms").html(),
                    success: function (layero, index) {
                        $("#wqxzDiv").html(htmlInfo);
                        $("#cancle2").on('click', function (obj) {
                            layer.close(index);
                        });
                    }
                });
            }
        })
    } else if (layEvent === "updateInfo") {
        xgFlag = "2";
        layer.open({
            type: 1,
            title: "修改箱子",
            id: "alert",
            area: ['90%', '90%'],
            content: $("#addBox").html(),
            success: function (layero, index) {
                layui.use(['form', 'upload'], function () {
                    laydate.render({
                        elem: "[name='outTime']",
                        format:"yyyy-MM-dd",
                        trigger: 'click',
                        type:"date"
                    });

                    var $ = layui.jquery, upload = layui.upload;
                    //普通图片上传
                    var uploadInst = upload.render({
                        elem: '#test1'
                        , url: baseURL + 'api/file/upload' //改成您自己的上传接口
                        , before: function (obj) {
                            //预读本地文件示例，不支持ie8
                            obj.preview(function (index, file, result) {
                                $('#image').attr('src', result); //图片链接（base64）
                            });
                        }
                        , done: function (res) {
                            //如果上传失败
                            if (res.code > 0) {
                                return layer.msg('上传失败');
                            }
                            //上传成功
                            $('#image').attr('src', baseURL + res.body.filePathURL);
                        }
                        , error: function () {
                            //演示失败状态，并实现重传
                            var demoText = $('#demoText');
                            demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                            demoText.find('.demo-reload').on('click', function () {
                                uploadInst.upload();
                            });
                        }
                    });
                });
                $("[name='name']").val(obj.data.name);
                $("[name='price']").val(obj.data.price);
                $("[name='isStatus']").val(obj.data.isStatus);
                $("[name='count']").val(obj.data.count);
                $("#image").attr("src", obj.data.image);
                findAjax("boxArms/getAll", {
                    "boxId": obj.data.id,
                }, function (res) {
                    if (res.message == 'success') {
                        var data = res.body;
                        var infoHtml = "";
                        var zjl=0;
                        for (var i = 0; i < data.length; i++) {
                            var info = data[i];
                            infoHtml += " <div class=\"layui-input-block\" style=\"border-style: none;display: inline-block;\">\n" +
                                "                        <div class=\"layui-inline\" >\n" +
                                "                            <input type='hidden' name='glArmsId' value='" + info.armsId + "'>" +
                                "                            <img src=\"" + info.arms.imageUrl + "\" width=\"68\" height=\"68\" style=\"display: block;margin-bottom: 5px\"/>\n" +
                                "                            <input type=\"text\" value='" + info.chance + "' onkeyup='jisuangJl(this)' style=\"width: 28px;\" name=\"chance\" placeholder=\"几率\" />\n" +
                                "                            <input type=\"text\" value='" + info.count + "' style=\"width: 28px;\" name=\"count\" placeholder=\"库存\" />\n" +
                                "                            <div style=\"text-align: center;\"><a onclick=\"delWq(this)\" href=\"javascript:void(0);\">删除</a></div>\n" +
                                "                        </div>\n" +
                                "                    </div>";
                            zjl+=parseFloat(info.chance);
                        }
                        $("[name='zjlv']").val(zjl);
                        $("#glImg").html(infoHtml);
                    }

                });
                var objId = obj.data.id
                form.render('select');
                $("#commit").on('click', function (obj) {
                    var name = $(" [name='name']").val();
                    var image = $(" #image").attr("src");
                    var price = $(" [name='price']").val();
                    var count = $(" [name='count']").val();
                    var isStatus = $(" [name='isStatus']").val();
                    var type = $("[name='type']").val();
                    var josn = new Array();
                    $("[name='glArmsId']").each(function (res) {

                        var id = $(this).val();
                        var jl = $(this).parent(".layui-inline").find("[name='chance']").val();
                        var kc = $(this).parent(".layui-inline").find("[name='count']").val();
                        var obj = {"chance": jl, "count": kc, "id": id};
                        josn.push(obj)
                    })
                    findAjax("box/updateById", {
                        "id": objId,
                        "name": name,
                        "image": image,
                        "price": price,
                        "count": count,
                        "isStatus": isStatus,
                        "glId": JSON.stringify(josn),
                        "type": type
                    }, function (res) {
                        if (res.message == 'success') {
                            layer.msg("修改成功", function () {
                                setTimeout($.ajax({
                                    type: "POST",
                                    url: baseURL + "box/getAllBox",
                                    data: {
                                        adminId: user.id
                                    },
                                    dataType: "json",
                                    success: function () {
                                    }
                                }), 100);

                                layer.close(index);
                                show();
                            });
                        }
                    });
                });
                $("#cancle").on('click', function (obj) {
                    layer.close(index);
                });


            }
        });
    }
});
var map = new Map();
window.delWq = function (res) {
    $(res).parents(".layui-inline").remove();
}

window.jisuangJl = function(res){
    var zjl=0;
    $("[name='chance']").each(function (res) {
        var jl=$(this).val();
        zjl=(zjl*100+jl*100)/100
    })
    $("[name='zjlv']").val(zjl);
}

window.xzwqCheck = function (res) {
    var infoHtml = "";
    $("[name='armsInfo']:checked").each(function (res) {
        var id = $(this).val()
        var obj = map.get(id);
        infoHtml += " <div class=\"layui-input-block\" style=\"border-style: none;display: inline-block;\">\n" +
            "                        <div class=\"layui-inline\" >\n" +
            "                            <input type='hidden' name='glArmsId' value='" + obj.id + "'>" +
            "                            <img src=\"" + obj.imageUrl + "\" width=\"68\" height=\"68\" style=\"display: block;margin-bottom: 5px;background-image: url('img/"+obj.type+".png');background-size: 100% 100%;background-repeat: no-repeat;\"/>\n" +
            "                            <p style='text-align: center;width: 130px;overflow: hidden;text-overflow:ellipsis;'>" + obj.name + "</p>\n" +
            "                            <p style='text-align: center;width:130px;'>$" + obj.price + "</p>\n" +
            "                            <input type=\"text\" style=\"width: 28px;\" onkeyup='jisuangJl(this)' name=\"chance\" placeholder=\"几率\" />\n" +
            "                            <input type=\"text\" style=\"width: 28px;\" name=\"count\" placeholder=\"库存\" />\n" +
            "                            <div style=\"text-align: center;\"><a onclick=\"delWq(this)\" href=\"javascript:void(0);\">删除</a></div>\n" +
            "                        </div>\n" +
            "                    </div>"
    })
    $("#glImg").html(infoHtml);
    $(res).parents(".layui-layer-page").find(".layui-layer-close").click();
}




window.addWuqi = function (res) {
    layer.open({
        type: 1,
        title: "选择武器",
        id: "alert2",
        area: ['90%', '90%'],
        content: $("#addArms").html(),
        success: function (layero, index) {

            form.render('select');
            xin(1);
            $("#cancle2").on('click', function (obj) {
                layer.close(index);
            });
        }
    });
}

    window.sousuo=function (res) {
        xin(1);
    }

    window.toFenye = function (idx) {
        // xin(idx)
    }

function xin(size) {
    if( size == 0 || size == null){
        size = 1;
    }
    var name = $("input[name='title']").val();
    var city = $("select[name='city']").val();
    // alert(city+""+name);
    findAjax("arms/getAll",{"name":name,"type":city,"pageIndex":size,"pageSize":"20000"}, function (res) {
        if (res.message == 'success') {
            var htmlInfo = " <div class=\"layui-form-item\">\n" +
                "                    <label class=\"layui-form-label\">名称：</label>\n" +
                "                    <div class=\"layui-input-inline\">\n" +
                "                        <input type=\"text\" name=\"title\" required  lay-verify=\"required\" placeholder=\"请输入武器名称\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "                    </div>\n" +
                "\n" +
                "                    <label class=\"layui-form-label\">\n" +
                "                        选择级别\n" +
                "                    </label>\n" +
                "                    <div class=\"layui-input-inline\">\n" +
                "                        <select name=\"city\" lay-verify=\"required\">\n" +
                "                            <option value=\"\">请选择武器级别</option>\n" +
                "                            <option value=\"1\">消费级</option>\n" +
                "                            <option value=\"2\">工业级</option>\n" +
                "                            <option value=\"3\">军规级</option>\n" +
                "                            <option value=\"4\">受限级</option>\n" +
                "                            <option value=\"5\">保密级</option>\n" +
                "                            <option value=\"6\">隐秘级</option>\n" +
                "                            <option value=\"7\">特殊物品*</option>\n" +
                "                        </select>\n" +
                "                    </div>\n" +
                "                    <button type=\"button\" class=\"layui-btn\" onclick=\"sousuo()\" id=\"btnss\">搜索</button>\n" +
                "                </div>";
            for (var i = 0; i < res.body.records.length; i++) {
                var info = res.body.records[i];
                if (info.isStatus == 0) {
                    map.set(info.id + "", info);
                    htmlInfo += "<div class=\"layui-form-item\" style=\"display: inline-block;\">\n" +
                        "                    <div class=\"layui-input-block\" style=\"border-style: none;display: inline-block;\">\n" +
                        "                        <div class=\"layui-inline\" style=\"width: 150px;\">\n" +
                        "                            <img src=\"" + info.imageUrl + "\" width=\"130\" height=\"130\" style=\"display: block;margin-bottom: 5px;background-image: url('img/"+info.type+".png'); background-size:  100% 100%; background-repeat: no-repeat;\"/>\n" +
                        "                            <p style='text-align: center;width: 130px;overflow: hidden;text-overflow:ellipsis;'>" + info.name + "</p>\n" +
                        "                            <p style='text-align: center;width:130px;'>" + info.price + "</p>\n" +
                        "                            <input type=\"checkbox\" value='" + info.id + "' name=\"armsInfo\" title=\"选择\" class=\"layui-form-checkbox\" lay-skin=\"primary\">\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "                </div>";
                }
            }
            var page;
            var total = res.body.total;
            var inx=0;
            var  en = 0;
            var len = total % 36 == 0 ? total / 36: parseInt((total / 36) + 1);
            page = "<a class=\"page1\" href='javascript:void(0);' onclick='toFenye(1)' style='background-image: url(\"img/yema_bg2.png\")'><img src=\"img/icon_zuo1.png\" height='16px' style='margin-top: 10px'></a> \n" +
                "<a class=\"page1\" href='javascript:void(0);' onclick='toFenye("+(parseInt(size)-1)+")' style='background-image: url(\"img/yema_bg2.png\")'><img src=\"img/icon_zuo2.png\" height='16px' style='margin-top: 10px'></a>\n" ;
            if(size == 1){
                page=" <a class=\"page1\" style='background-image: url(\"img/yema_bg.png\")'><img src=\"img/icon_zuo1.png\" style='margin-top: 10px' height='16px'></a> \n " +
                    " <a class=\"page1\"  style='background-image: url(\"img/yema_bg.png\")'><img src=\"img/icon_zuo2.png\" style='margin-top: 10px' height='16px'></a>\n" ;
                inx = 1;
            }else {
                inx = ((size-1) * 36+1);
            }
            page+="<label style='display: inline-block;width: 35px;text-align: center; '>第</label><input type='text' value='"+size+"' readonly='readonly' style='display: inline-block;height:20px; width:50px;padding-left: 15px; background-color: #D3D3D3 ;margin-right: 5px; padding-top: 5px;' ><label>页</label>,<label>共"+len+"页</label>"
            // icon_you1.png
            if(size == len){
                page+= "    <a class=\"page2\" style='background-image: url(\"img/yema_bg.png\")'><img src=\"img/icon_you2.png\" style='height: 15px; margin-top: 10px'></a>\n"+
                    " <a class=\"page2\" style='background-image: url(\"img/yema_bg.png\")'><img src=\"img/icon_you1.png\" style='margin-top: 10px'></a> \n " ;
                en = (total);
            }else{
                en = (size*36);
                page+= "    <a class=\"page2\" href='javascript:void(0)' onclick='toFenye("+(parseInt(size)+1)+")' style='background-image: url(\"img/yema_bg2.png\") '><img src=\"img/icon_you2.png\" style='margin-top: 10px'></a>\n"+
                    " <a class=\"page2\" href='javascript:void(0);' onclick='toFenye("+len+")' style='background-image: url(\"img/yema_bg2.png\")'><img src=\"img/icon_you1.png\" style='margin-top: 10px'></a> \n " ;
            }
            page+="<label style='display: inline-block;margin-left: 10px;'>显示"+inx+"-"+en+"条,共<span style='color: #1597EE;' '>"+total+"</span>条</label>";
            // htmlInfo+=page;
            $("#wqxzDiv").html(htmlInfo);
            form.render("select");
        }
        form.render('checkbox');
    });
}

})
;