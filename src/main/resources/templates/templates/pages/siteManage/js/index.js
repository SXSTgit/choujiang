layui.config({
    base: "{/}/templates/lib/layui-v2.5.4/lay/my_modules/"
}).extend({
    layuimini: 'layuimini',
    formSelects: 'formSelects/formSelects-v4',
    treeTable: 'treeTable/treeTable'
}).use(['laypage', 'layer', 'form','upload', 'table', 'laydate', 'element', 'layuimini', 'carousel', 'treeTable'], function () {
    var laypage = layui.laypage,
        layer = layui.layer,
        form = layui.form,
        table = layui.table,
        element = layui.element,
        laydate = layui.laydate,
        layuimini = layui.layuimini,
        carousel = layui.carousel,
        upload = layui.upload,
        treeTable = layui.treeTable;
    var user = C.getCache('admin');

    var uploadInst = upload.render({
        elem: '#test1'
        , url: baseURL + 'api/file/upload' //改成您自己的上传接口
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#ico').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            //如果上传失败
            if (res.code > 0) {
                return layer.msg('上传失败');
            }
            //上传成功
            $('#ico').attr('src', baseURL + res.body.filePathURL);
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

    var uploadInst = upload.render({
        elem: '#test2'
        , url: baseURL + 'api/file/upload' //改成您自己的上传接口
        , before: function (obj) {
            //预读本地文件示例，不支持ie8
            obj.preview(function (index, file, result) {
                $('#Logo').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            //如果上传失败
            if (res.code > 0) {
                return layer.msg('上传失败');
            }
            //上传成功
            $('#Logo').attr('src', baseURL + res.body.filePathURL);
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




    function show() {
        var list = [];
        findAjax("site/getOneSite", {}, function (res) {
            var data = res.body;
            $(" [name='name']").val(data.name);
            $("[name='title']").val(data.title);
            $(" [name='keyword']").val(data.keyword);
            $(" [name='description']").val(data.description);
            $(" [name='copyright']").val(data.copyright);
            $("[name='tips']").val(data.tips);
            $("[name='path']").val(data.path);
            $("#ico").attr("src",data.ico);
            $("#Logo").attr("src",data.logo);
        });
    }


    show();


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

    $("#commit").on('click', function (obj) {
        var name = $(" [name='name']").val();
        var title = $("[name='title']").val();
        var keyword = $(" [name='keyword']").val();
        var description = $(" [name='description']").val();
        var copyright = $(" [name='copyright']").val();
        var ico  = $("[name='ico']")[0].src;
        var Logo = $("[name='imageUrl']")[0].src;
        var tips=$("[name='tips']").val();
        var path=$("[name='path']").val();
        findAjax("site/updateByIdSite", {
            "id": 1,
            "name": name,
            "title": title,
            "keyword": keyword,
            "description": description,
            "copyright": copyright,
            "tips" : tips,
            "logo":Logo,
            "path":path,
            "ico":ico
        }, function (res) {
            if (res.message == 'success') {
                layer.msg("添加成功", function () {
                    setTimeout(show(), 100);

                    layer.close(index);
                    show();
                });
            }
        });
    });

});