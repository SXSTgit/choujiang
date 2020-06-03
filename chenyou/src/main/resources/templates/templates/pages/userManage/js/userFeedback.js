layui.config({
	base: "{/}/templates/lib/layui-v2.5.4/lay/my_modules/"
}).extend({
	layuimini: 'layuimini',
	formSelects: 'formSelects/formSelects-v4',
}).use(['laypage', 'layer', 'form', 'table', 'laydate', 'element', 'layuimini', 'carousel'], function() {
	var laypage = layui.laypage,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		element = layui.element,
		laydate = layui.laydate,
		layuimini = layui.layuimini,
		carousel = layui.carousel;

	laydate.render({
		elem: '#date2'
	});

	var isClisk = true;

	var pageIndex = 1;
	var pageSize = 3;
	var user = C.getCache('admin');

	var creDate = $('[name="creDate"]').val();
	var phone = $('[name="phone"]').val();
	var userName = $('[name="userName"]').val();

	var data = {
		"creDate": creDate,
		"phone": phone,
		"userName": userName
	}

	table.render({
		elem: '#table1',
		url: baseURL + 'userFeedback/pageUserFeedback',
		method: 'post',
		headers: {
			'X-Token': C.find("X-Token")
		},
		contentType: 'application/json',
		parseData: function(res) { //res 即为原始返回的数据
			return {
				"code": res.errorCode, //解析接口状态
				"msg": res.message, //解析提示文本
				"count": res.body.total, //解析数据长度
				"data": res.body.records //解析数据列表
			};
		},
		request: {
			pageName: 'pageIndex' //页码的参数名称，默认：page
				,
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		},
		where: data,
		cols: [
			[{
					type: 'numbers',
					width: 150,
					title: '序号',
				},
				{
					field: 'userName',
					title: '登录名',
					align: 'center'
				},
				{
					field: 'phone',
					title: '手机号',
					align: 'center'
				},

				{
					field: 'feedbackImages',
					title: '缩略图',
					align: 'center',
					templet: function(d) {
						return '<img src="' + baseURL + d.feedbackImages + '"/>';
					}
				},
				{
					field: 'creDate',
					title: '创建日期',
					align: 'center',
					templet: function(d) {
						var birth = new Date(d.creDate);
						return birth.getFullYear() + "-" + (birth.getMonth() + 1) + "-" + birth.getDate();
					}
				},
				{
					title: '状态',
					align: 'center',
					templet: function(d) {
						if (d.isStatus == 0) {
							return "已处理";
						} else {
							return "未处理";
						}
					}
				},
				{
					title: '操作',
					align: 'center',
					fixed: 'right',
					templet: function(d) {
						if (d.isStatus == 1) {
							return "<button class='layui-btn layui-btn-xs ' lay-event='chuli'>处理</button>" +
								"<button class='layui-btn layui-btn-xs ' lay-event='xiangqing'>反馈内容</button>" +
								"<button class='layui-btn layui-btn-xs layui-btn-danger' lay-event='delete'>删除</button>";
						} else {
							return "<button class='layui-btn layui-btn-xs ' lay-event='xiangqing'>反馈内容</button>" +
								"<button class='layui-btn layui-btn-xs layui-btn-danger' lay-event='delete'>删除</button>";
						}

					}
				}
			]
		],
		page: true,
		done: function(res, curr, count) {
			pageIndex = curr;
			/*console.log(res)
			console.log(curr)
			console.log(count)*/
		}
	});




	function show() {
		var creDate = $('[name="creDate"]').val();
		var phone = $('[name="phone"]').val();
		var userName = $('[name="userName"]').val();
		var data = {
			"creDate": creDate,
			"phone": phone,
			"userName": userName
		}
		isClisk = true;
		table.reload('table1', {
			where: data,
			page: {
				curr: 1
			}
		});
	}

	show();

	$('[lay-filter="search"]').on('click', function(obj) {
		show();
	});



	table.on('tool(table1)', function(obj) {
		var data = obj.data; //获得当前行数据
		var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
		var tr = obj.tr; //获得当前行 tr 的DOM对象
		var con = data.context;

		if (layEvent === 'xiangqing') {
			//页面层
			layer.open({
				type: 1,
				skin: 'layui-layer-rim', //加上边框
				area: ['520px', '240px'], //宽高
				content: con
			});

		} else if (layEvent === 'delete') {
			if (isClisk) {
				isClisk = false;
				var index = layer.confirm("是否确认删除", function() {


					$.ajax({
						type: "post",
						url: baseURL + "userFeedback/removeUserFeedback",
						async: true,
						data: {
							id: data.id
						},
						dataType: "JSON",
						beforeSend: function(xhr) {
							xhr.setRequestHeader("X-Token", C.find("X-Token"));
						},
						success: function(res) {
							if (res.errorCode == 0) {
								layer.msg("删除成功", function(obj) {
									show();
								});
							} else {
								layer.msg("删除失败");
								isClisk = true;
							}
						}
					});

				}, function() {
					layer.close(index);
					isClisk = true;
				});
			}

		} else if (layEvent === 'chuli') {


			if (isClisk) {
				isClisk = false;
				var index = layer.confirm("是否确认处理", function() {
					$.ajax({
						type: "post",
						url: baseURL + "userFeedback/updateIsStatus",
						async: true,
						data: {
							id: data.id,
							isStatus: 0
						},
						dataType: "JSON",
						beforeSend: function(xhr) {
							xhr.setRequestHeader("X-Token", C.find("X-Token"));
						},
						success: function(res) {
							if (res.errorCode == 0) {
								layer.msg("处理成功", function(obj) {
									show();
								});
							} else {
								layer.msg("处理失败");
								isClisk = true;
							}
						}
					})
				}, function() {
					layer.close(index);
					isClisk = true;
				});
			}


		}
	});
});
