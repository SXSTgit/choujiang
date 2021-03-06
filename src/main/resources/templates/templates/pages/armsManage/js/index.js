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

// initTable();
    // 渲染表格
    function initTable(data) {
        //console.log(data);
        var insTb = treeTable.render({
            elem: '#demoTb1',
            data: data,  // 数据
            // url:baseURL+"arms/getAll",
            /*tree: {
                iconIndex: 1, // 折叠图标显示在第几列
                idName: 'menuId',  // 自定义id字段的名称
            },*/
            page:true,
            cols: [
               {
                    field: 'id',
                    title: '武器id',
                    align: 'center',
                    width: 150
                }, {
                    field: 'name',
                    title: '饰品名称',
                    edit: 'text',
                    align: 'center',
                    width:400
                },
                {   field: 'type',
                    title: '饰品品质',
                    align: 'center',
                    templet: function (d) {
                        var strs = [
                            '<span></span>',
                            '<span class="layui-badge layui-badge-green">消费级</span>',
                            '<span class="layui-badge layui-badge-green">工业级</span>',
                            '<span class="layui-badge layui-badge-green">军规级</span>',
                            '<span class="layui-badge layui-badge-green">受限级</span>',
                            '<span class="layui-badge layui-badge-green">保密级</span>',
                            '<span class="layui-badge layui-badge-green">隐秘级</span>',
                            '<span class="layui-badge layui-badge-green">特殊物品*</span>',
                        ];
                        return strs[d.type];
                    }
                }
                ,{
                    field: 'imageUrl',
                    title: '饰品主图',
                    align: 'center',
                    templet: function(d) {
                        var url="img/"+d.type+".png";
                        var htmlInfo= '<span class="backImg" style="background-image: url(\''+url+'\');display: inline-block;\n' +
                            '    background-repeat: no-repeat;\n' +
                            '    background-size: contain;\n' +
                            '    width: 100px;\n' +
                            '    height: 100px;"><img  src="'+d.imageUrl+'" width="50"height="50"/></span>'
                        return htmlInfo;
                    }
                }, {
                    field: 'price',
                    title: '价格',
                    align: 'center',
                    edit: 'text'
                }, {
                    field: 'count',
                    title: '库存',
                    align: 'center',
                    edit: 'text'
                }, {
                    field: 'productId',
                    title: '第三方武器ID',
                    align: 'center',
                    edit: 'text'
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
                            "<button class='layui-btn layui-btn-xs layui-btn-normal' lay-event='updateid'>修改</button>"+
                            "<button class='layui-btn layui-btn-xs layui-btn-warm' lay-event='delnavi'>删除</button>";
                    }
                }
            ]
        });

    }

    window.sousuo=function (res) {
        show();
    }

    window.toFenye = function (idx) {
        show(idx)
    }
    function show(size) {
        if(size == 0 || size == null){
            size = 1;
        }
        var name = $("input[name='title']").val();
        var city = $("select[name='city']").val();
        var list = [];
        findAjax("arms/getAll", {"name":name,"type":city,"pageIndex":size,"pageSize":"20"}, function (res) {
            var datas = res.body.records;
            for (var i = 0; i < datas.length; i++) {
                list = list.concat(datas[i]);
            }
            initTable(list);
            var page;
            var total = res.body.total;
            var inx=0;
            var  en = 0;
            var len = total % 20 == 0 ? total / 20: parseInt((total / 20) + 1);
            page = "<a class=\"page1\" href='javascript:void(0);' onclick='toFenye(1)' style='background-image: url(\"img/yema_bg2.png\")'><img src=\"img/icon_zuo1.png\" height='16px' style='margin-top: 10px'></a> \n" +
                "<a class=\"page1\" href='javascript:void(0);' onclick='toFenye("+(parseInt(size)-1)+")' style='background-image: url(\"img/yema_bg2.png\")'><img src=\"img/icon_zuo2.png\" height='16px' style='margin-top: 10px'></a>\n" ;
            if(size == 1){
                page=" <a class=\"page1\" style='background-image: url(\"img/yema_bg.png\")'><img src=\"img/icon_zuo1.png\" style='margin-top: 10px' height='16px'></a> \n " +
                    " <a class=\"page1\"  style='background-image: url(\"img/yema_bg.png\")'><img src=\"img/icon_zuo2.png\" style='margin-top: 10px' height='16px'></a>\n" ;
                inx = 1;
            }else {
                inx = ((size-1) * 20+1);
            }
            page+="<label style='display: inline-block;width: 35px;text-align: center; '>第</label><input type='text' value='"+size+"' readonly='readonly' style='display: inline-block;height:20px; width:50px;padding-left: 15px; background-color: #D3D3D3 ;margin-right: 5px; padding-top: 5px;' ><label>页</label>,<label>共"+len+"页</label>"
            // icon_you1.png
            if(size == len){
                page+= "    <a class=\"page2\" style='background-image: url(\"img/yema_bg.png\")'><img src=\"img/icon_you2.png\" style='height: 15px; margin-top: 10px'></a>\n"+
                    " <a class=\"page2\" style='background-image: url(\"img/yema_bg.png\")'><img src=\"img/icon_you1.png\" style='margin-top: 10px'></a> \n " ;
                en = (total);
            }else{
                en = (size*20);
                page+= "    <a class=\"page2\" href='javascript:void(0)' onclick='toFenye("+(parseInt(size)+1)+")' style='background-image: url(\"img/yema_bg2.png\") '><img src=\"img/icon_you2.png\" style='margin-top: 10px'></a>\n"+
                    " <a class=\"page2\" href='javascript:void(0);' onclick='toFenye("+len+")' style='background-image: url(\"img/yema_bg2.png\")'><img src=\"img/icon_you1.png\" style='margin-top: 10px'></a> \n " ;
            }
            page+="<label style='display: inline-block;margin-left: 10px;'>显示"+inx+"-"+en+"条,共<span style='color: #1597EE;' '>"+total+"</span>条</label>";
            $(".page").html(page);
        });
    }


    show();
    $('[lay-filter="addSysmenu"]').on('click', function (obj) {
        layer.open({
            type: 1,
            title: "添加武器",
            id: "alert",
            area: ['500px', '500px'],
            content: $("#addArms").html(),
            success: function (layero, index) {
                layui.use(['form','upload'],function() {
                    var $ = layui.jquery,upload = layui.upload;
                    //普通图片上传
                    var uploadInst = upload.render({
                        elem: '#test1'
                        , url: baseURL + 'api/file/upload' //改成您自己的上传接口
                        , before: function (obj) {
                            //预读本地文件示例，不支持ie8
                            obj.preview(function (index, file, result) {
                                $('#imageUrl').attr('src', result); //图片链接（base64）
                            });
                        }
                        , done: function (res) {
                            //如果上传失败
                            if (res.code > 0) {
                                return layer.msg('上传失败');
                            }
                            //上传成功
                            $('#imageUrl').attr('src', baseURL + res.body.filePathURL);
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
                    var imageUrl = $(" #imageUrl").attr("src");
                    var price = $(" [name='price']").val();
                    var count = $(" [name='count']").val();
                    var isStatus = $(" [name='isStatus']").val();
                    var productId=$("[name='productId']").val();
                    var type=$("[name='type']").val();
                    findAjax("arms/addInfo", {
                        "name": name,
                        "imageUrl": imageUrl,
                        "price": price,
                        "count": count,
                        "isStatus": isStatus,
                        "type" : type,
                        "productId":productId
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

    treeTable.on('tool(demoTb1)', function (obj) {
        var data = obj.data;  // 获得当前行数据
        var layEvent = obj.event; // 获得lay-event对应的值
        if (layEvent === 'delnavi') {

            layer.confirm("确认删除此武器吗?", function () {
                findAjax("arms/romveArms", {"id":data.id}, function (res) {
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
                            "id":data.id,
                            "name": data.name,
                            "imageUrl": data.imageUrl,
                            "price": data.price,
                            "count": data.count,
                            "productId":data.productId
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
        }else if(layEvent === "updateid"){
            layer.open({
                type:1,
                title:"修改",
                id:"alert",
                area:['500px','500px'],
                content: $("#addArms").html(),
                success: function (layero, index) {
                    // alert(obj);
                    layui.use(['form', 'upload'], function () {
                        var $ = layui.jquery, upload = layui.upload;
                        //普通图片上传
                        var uploadInst = upload.render({
                            elem: '#test1'
                            , url: baseURL + 'api/file/upload' //改成您自己的上传接口
                            , before: function (obj) {
                                //预读本地文件示例，不支持ie8
                                obj.preview(function (index, file, result) {
                                    $('#imgUrl').attr('src', result); //图片链接（base64）
                                });
                            }
                            , done: function (res) {
                                //如果上传失败
                                if (res.code > 0) {
                                    return layer.msg('上传失败');
                                }
                                //上传成功
                                $('#imageUrl').attr('src', baseURL + res.body.filePathURL);
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

                    $("[name='productId']").val(obj.data.productId);
                    $("[name='type']").val(obj.data.type);
                    $("#imageUrl").attr("src", obj.data.imageUrl)
                    var objId=obj.data.id;
                    form.render('select');
                    $("#commit").on('click', function (obj) {
                        var name = $(" [name='name']").val();
                        var image = $(" #imageUrl").attr("src");
                        var price = $(" [name='price']").val();
                        var count = $(" [name='count']").val();
                        var isStatus = $(" [name='isStatus']").val();
                        var type = $("[name='type']").val();
                        var productId=$("[name='productId']").val();
                        findAjax("arms/updateById", {
                            "id": objId,
                            "name": name,
                            "imageUrl": image,
                            "price": price,
                            "count": count,
                            "isStatus": isStatus,
                            "productId": productId,
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

                }
            })

        }
    });


});