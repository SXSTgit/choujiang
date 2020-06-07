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

	var user = C.getCache('admin');
	
	
	
	table.render({
		elem: '#recharOpttable',
		defaultToolbar: ['filter', 'print', 'exports'],
		url: baseURL + 'recharge/selectRechargePage',
		method: 'post',
		headers: {
			'X-Token': C.find('X-Token')
		},
		parseData: function(res) {
			return {
				"code": res.errorCode, //解析接口状态
				"msg": res.message, //解析提示文本
				"count": res.body.total, //解析数据长度
				"data": res.body.records //解析数据列表
			};
		},
		request: {
			pageName: 'indexPage', //页码的参数名称，默认：page
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		},
		/*where: data,*/
		cols: [
			[{
					type: 'checkbox',
					fixed: 'left'
				},
				{
					title: '序号',
					width: 100,
					align: 'center',
					type: 'numbers',
				
				},
				{
					field: 'amount',
					title: '充值金额',
					align: 'center',
					templet: function(d) {
						if(parseInt(d.amount)==d.amount){
							return d.amount+'.00';
						}else{
							return d.amount;
						}
					}
					
				},
				{
					field: 'deduction',
					title: '赠送油币',
					align: 'center'
				},
				/*{
					field: 'validity',
					title: '充值有效期(天)',
					align: 'center'
				},
				{
					field: 'moneyExplain',
					title: '充值加油金',
					align: 'center'
				},*/
				{
					field: 'recommend',
					title: '是否推荐',
					align: 'center',
					templet: function(d) {
						if(d.recommend==1){
							return '<span style="color:lightgreen">是</span>';
						}else if(d.recommend==2){
							return '<span>否</span>';
						}
					}
				},
				/*{
					field: 'display',
					title: '是否显示',
					align: 'center',
					templet: function(d) {
						if(d.display==1){
							return '<span style="color:lightgreen">是</span>';
						}else if(d.display==2){
							return '<span>否</span>';
						}
					}
				},*/
				/*{
					field: 'weight',
					title: '权重',
					align: 'center'
				},*/
				{
					field: 'creDate',
					title: '创建时间',
					align: 'center'
				},
				{
					width: 200,
					title: '操作',
					align:'center',
					fixed:'right',
					templet: function(d) {
						if(d.recommend==2){
							//可以推荐
							return  "<button class='layui-btn layui-btn-xs layui-btn-normal' lay-event='editopt'>编辑</button>"+
								    "<button class='layui-btn layui-btn-xs' lay-event='iscommc'>推荐</button>";
						}else if(d.recommend==1){
							//可以不推荐
							return  "<button class='layui-btn layui-btn-xs layui-btn-normal' lay-event='editopt'>编辑</button>"+
							 	    "<button class='layui-btn layui-btn-xs layui-btn-danger' lay-event='nocommc'>不推荐</button>";
						}
					}
				}
			]
		],
		page: true,
		done: function(res, curr, count) {
			pageIndex = curr;
			C.save('recharOptPageIndex',curr);
		}
	});
	
	
	//批量删除
	var active = {
		getCheckData: function() { //获取选中数据
			var checkStatus = table.checkStatus('recharOpttable'),
				data = checkStatus.data;
			//console.log(data);
			var idLists = [];
			for(var i = 0; i < data.length; i++) {
				idLists.push(data[i].id);
			}
			//console.log(idLists)
			$.ajax({
				type: "post",
				url: baseURL + "recharge/removeRecharge",
				async: true,
				traditional: true,
				data: JSON.stringify(idLists),
				dataType: 'JSON',
				contentType: 'application/json',
				beforeSend: function(xhr) {
					xhr.setRequestHeader("X-Token", C.find("X-Token"));
				},
				success: function(res) {
					if(res.message == 'success') {
						layer.msg('删除成功', function() {
							table.reload('recharOpttable', {
								page: {
									curr: C.find('recharOptPageIndex')
								}
							});
						});
					}
				}
			});
	
		}
	};
	
	$('.delRecharOptbtn1').on('click', function() {
		layer.confirm('确认删除吗?', function() {
			var type = $('.delRecharOptbtn1').data('type');
			active[type] ? active[type].call(this) : '';
		});
	
	});
	
	
	$('.addRecharOptbtn1').on('click',function(){
		layer.open({
			type: 1,
			title: "添加充值配置",
			id: "alert",
			area: ['800px', '600px'],
			content: $("#addRecharHtml").html(),
			success: function(layero, index) {
				form.render();
				
				$('.addOptSubmit').on('click', function() {
					var amount = $('[name="amount"]').val();
					var deduction = $('[name="deduction"]').val();
					var isComm = $('[name="isComm"]').val();
					var data = {
						"amount": amount,
						"deduction": deduction,
						"recommend": isComm
					}
					$.ajax({
						type: "post",
						url: baseURL + "recharge/addRecharge",
						async: true,
						traditional: true,
						data: JSON.stringify(data),
						dataType: 'JSON',
						contentType: 'application/json',
						beforeSend: function(xhr) {
							xhr.setRequestHeader("X-Token", C.find("X-Token"));
						},
						success: function(res) {
							if(res.message=='success'){
								layer.msg('添加成功',function(){
									layer.close(index);
									table.reload('recharOpttable', {
										page: {
											curr: C.find('recharOptPageIndex')
										}
									});
								});
							}
						}
					});
				});
				
				
				$('.reset').on('click',function(){
					layer.close(index);
				});
			}
		});
	});
	
	
	table.on('tool(recharOpttable)', function(obj) {
	 	var data = obj.data; //获得当前行数据
	 	var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
	 	var tr = obj.tr; //获得当前行 tr 的DOM对象
	 	var id = data.id;
	 	if(layEvent==='editopt'){
	 		
	 		layer.open({
				type: 1,
				title: "编辑充值配置",
				id: "alert",
				area: ['800px', '600px'],
				content: $("#editRecharHtml").html(),
				success: function(layero, index) {
					form.render();
					//初始化信息
					$.ajax({
						type: "post",
						url: baseURL + "recharge/selectRecharge",
						async: true,
						traditional: true,
						data: {
							id:id
						},
						dataType: 'JSON',
						beforeSend: function(xhr) {
							xhr.setRequestHeader("X-Token", C.find("X-Token"));
						},
						success: function(res) {
							var optData = res.body;
							$('[name="amount"]').val(optData.amount);
							$('[name="deduction"]').val(optData.deduction);
							$('[name="isComms"]').val(optData.recommend);
							form.render();
						}
					});
					
					$('.editOptSubmit').on('click', function() {
						var amount = $('[name="amount"]').val();
						var deduction = $('[name="deduction"]').val();
						var isComm = $('[name="isComms"]').val();
						var data = {
							"amount": amount,
							"deduction": deduction,
							"id":id,
							"recommend": isComm
						}
						$.ajax({
							type: "post",
							url: baseURL + "recharge/updateRechargeById",
							async: true,
							traditional: true,
							data: JSON.stringify(data),
							dataType: 'JSON',
							contentType:'application/json',
							beforeSend: function(xhr) {
								xhr.setRequestHeader("X-Token", C.find("X-Token"));
							},
							success: function(res) {
								if(res.message=='success'){
									layer.msg('编辑成功',function(){
										layer.close(index);
										table.reload('recharOpttable', {
											page: {
												curr: C.find('recharOptPageIndex')
											}
										});
									});
								}
							}
						});
					});
					$('.reset').on('click',function(){
						layer.close(index);
					});
				}
			});
	 	}else if(layEvent==='iscommc'){
	 		layer.confirm('是否推荐', function() {
				//var id = data.id;
				var data = {
					"id": id,
					"recommend": 1
				}
				$.ajax({
					type: "post",
					url: baseURL + "recharge/updateRechargeById",
					async: true,
					traditional: true,
					data: JSON.stringify(data),
					dataType: 'JSON',
					contentType: 'application/json',
					beforeSend: function(xhr) {
						xhr.setRequestHeader("X-Token", C.find("X-Token"));
					},
					success: function(res) {
						if(res.message == 'success') {
							layer.msg('推荐成功', function() {
								table.reload('recharOpttable', {
									page: {
										curr: C.find('recharOptPageIndex')
									}
								});
							});
						}
					}
				});
			});
	 		
	 	}else if(layEvent==='nocommc'){
	 		layer.confirm('是否取消推荐', function() {
				//var id = data.id;
				var data = {
					"id": id,
					"recommend": 2
				}
				$.ajax({
					type: "post",
					url: baseURL + "recharge/updateRechargeById",
					async: true,
					traditional: true,
					data: JSON.stringify(data),
					dataType: 'JSON',
					contentType: 'application/json',
					beforeSend: function(xhr) {
						xhr.setRequestHeader("X-Token", C.find("X-Token"));
					},
					success: function(res) {
						if(res.message == 'success') {
							layer.msg('取消推荐成功', function() {
								table.reload('recharOpttable', {
									page: {
										curr: C.find('recharOptPageIndex')
									}
								});
							});
						}
					}
				});
			});
	 	}
	});
	
	
	$('[lay-filter="search"]').on('click', function(obj) {
		
			var isCo = $('[name="isCo"]').val();
			var data = {
				recommend:isCo
			}
			table.reload('recharOpttable', {
				where:data,
				page: {
					curr: C.find('recharOptPageIndex')
				}
			});
		
		
	});
	
	$('.layui-icon-refresh').on('click', function() {
		var isCo = $('[name="isCo"]').val();
		var data = {
			recommend: isCo
		}
		table.reload('recharOpttable', {
			where: data,
			page: {
				curr: C.find('recharOptPageIndex')
			}
		});
	});
	
	
	
	
	
	

});