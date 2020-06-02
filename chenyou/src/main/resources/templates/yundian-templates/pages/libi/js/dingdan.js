layui.config({
	base: "{/}/yundian-templates/lib/layui-v2.5.4/lay/my_modules/"
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

	window.onload = function () {
	KDNWidget.run({
	"serviceType": "D",//此处为快递鸟类型值，不要更改，D为物流轨迹查询框嵌入
	"container": "myExercise",//此处填写容器id，注意不需要加#
	})
	}

	laydate.render({
		elem: '#creDate'
	});

	var isClisk = true;

	var pageIndex = 1;
	var pageSize = 10;

	var userId = $('[name="userId"]').val();
	var title = $('[name="title"]').val();
	var creDate = $('[name="creDate"]').val();
	var isStatus = $('[name="isStatus"]').val();
	var courier = $('[name="courier"]').val();

	var data = {
		"userId": userId,
		"title": title,
		"creDate": creDate,
		"isStatus": isStatus,
		"courier": courier
	}


	table.render({
		elem: '#table1',
		url: baseURL + 'exchangeOrder/getPageExchangeOrder',
		method: 'post',
		headers: {
			'X-Token': C.find("X-Token")
		},
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
					field: 'name',
					title: '序号',
					width: 100,
					align: 'center',
					type: 'numbers',

				},
				{
					field: 'userId',
					title: '用户Id',
					align: 'center'
				},
				{
					field: 'title',
					title: '商品名称',
					align: 'center'
				},
				{
					field: 'bannerUrl',
					title: '主图',
					align: 'center',
					templet: function(d) {
						return '<img src="' + baseURL + d.aimg + '"/>';
					}
				},
				{
					field: 'price',
					title: '价格',
					align: 'center',
					templet: function(d) {
						if(parseInt(d.price)==d.price){
							return d.price+".00";
						}else{
							return d.price;
						}
					}
				},
				{
					field: 'coin',
					title: '礼币',
					align: 'center'
				},
				{
					field: 'num',
					title: '兑换数量',
					align: 'center'
				},

				{
					field: 'courier',
					title: '快递单号',
					align: 'center'
				},
				{
					field: 'shoujianren',
					title: '收件人',
					align: 'center'
				},
				{
					field: 'lianxifangshi',
					title: '联系方式',
					align: 'center'
				},
				{
					field: 'xiangxidizhi',
					title: '详细地址',
					align: 'center'
				},
				{
					title: '状态',
					align: 'center',
					templet: function(d) {
						if (d.isStatus == 0) {
							return "未付款";
						} else if (d.isStatus == 1) {
							return "未发货";
						} else if (d.isStatus == 2) {
							return "已发货";
						} else if (d.isStatus == 3) {
							return "冻结";
						}
					}
				},
				{
					field: 'creDate',
					title: '创建日期',
					width: 220,
					align: 'center'
				},

				{
					title: '操作',
					align: 'center',
					fixed: 'right',
					width: 200,
					templet: function(d) {
						if (d.isStatus != 3) {
							return "<button class='layui-btn layui-btn-xs' lay-event='dongjie'>冻结</button>" +
								"<button class='layui-btn layui-btn-xs layui-btn-danger' lay-event='tianxie'>填写快递单号</button>";
						} else {
							return "<button class='layui-btn layui-btn-xs layui-btn-danger' lay-event='tianxie'>填写快递单号</button>";
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
		var title = $('[name="title"]').val();
		var isStatus = $('[name="isStatus"]').val();

		var data = {
			"title": title,
			"isStatus": isStatus
		}
		isClisk = true;
		table.reload('table1', {
			where: data,
			page: {
				curr: 1
			}
		});
	}

	$('[lay-filter="search"]').on('click', function(obj) {
		show();
	});

	show();

	table.on('tool(table1)', function(obj) {
		var data = obj.data; //获得当前行数据
		var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
		var tr = obj.tr; //获得当前行 tr 的DOM对象

		if (layEvent === 'tianxie') {
			$('[name="id"]').val(data.id);
			layer.open({
				title: '填写快递单号',
				type: 1,
				//skin: 'layui-layer-rim', //加上边框
				area: ['600px', '400px'], //宽高
				content: $("#hide_div")
			});
			$('[name="id"]').val(data.id);
			/**
			 * 修改订单
			 */
			$(".baocun").on('click', function() {

				var zhuangtai = $('[name="zhuangtai"]').val();
				var danhao = $('[name="danhao"]').val();
				var courierName = $('[name="courierName"]').val();
				$.ajax({
					url: baseURL + "exchangeOrder/upddanhao",
					data: {
						"id": data.id,
						"danhao": danhao,
						"courierName": courierName
					},
					async: true,
					type: "post",
					beforeSend: function(xhr) {
						xhr.setRequestHeader("X-Token", C.find("X-Token"));
					},
					success: function(data) {
						/**
						 * 修改订单后将状态改为已发货
						 */
						if (data.errorCode == 0) {
							var data = obj.data;
							alert(data.id)
							$.ajax({
								url: baseURL + 'exchangeOrder/updOrderisStatus',
								type: 'POST',
								data: {
									"id": data.id,
									"isStatus": 2
								},
								dataType: 'JSON',
								beforeSend: function(xhr) {
									xhr.setRequestHeader("X-Token", C.find("X-Token"));
								},
								success: function(res) {
									layer.msg("添加成功.", function(obj) {
										location.replace("dingdan.html");
									});
								}
							});
							
							
						} else {
							layer.msg("添加失败.", function(obj) {
								location.replace("dingdan.html");
							});
						}
					}
				});
			});

		} else if (layEvent === "dongjie") {
			if (isClisk) {
				isClisk = false;
				var index = layer.confirm('确认冻结吗?', function() {
					$.ajax({
						url: baseURL + 'exchangeOrder/updOrderisStatus',
						type: 'POST',
						data: {
							"id": data.id,
							"isStatus": 3
						},
						dataType: 'JSON',
						beforeSend: function(xhr) {
							xhr.setRequestHeader("X-Token", C.find("X-Token"));
						},
						success: function(res) {
							if (res.message == 'success') {
								layer.msg('冻结成功', function() {
									show();
								});
							} else {
								layer.msg('冻结成功');
								isClisk = true;
							}
						}
					});
				}, function() {
					layer.close(index);
					isClisk = true;
				});
			}
		}
	});

	/* var vNow = new Date();
	var sNow = "";
	sNow += String(vNow.getFullYear());
	sNow += String(vNow.getMonth() + 1);
	sNow += String(vNow.getDate());
	sNow += String(vNow.getHours());
	sNow += String(vNow.getMinutes());
	sNow += String(vNow.getSeconds());
	sNow += String(vNow.getMilliseconds());
	$('[name="danhao"]').val(sNow); */



	$('[lay-filter="add"]').on('click', function(obj) {
		location.replace("addshangpin.html");
	});




});
