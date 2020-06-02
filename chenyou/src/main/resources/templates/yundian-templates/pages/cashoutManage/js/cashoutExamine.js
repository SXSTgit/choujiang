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

	var user = C.getCache('admin');

	var recordId = $('[name="recordId"]').val();

	var data = {
		id: recordId
	};

	function showNameByAgentId(agentid) {
		var agentName = '';
		$.ajax({
			url: baseURL + 'agent/selectAgentById',
			type: 'post',
			async: false,
			data: {
				id: agentid
			},
			dataType: 'JSON',
			beforeSend: function(xhr) {
				xhr.setRequestHeader("X-Token", C.find("X-Token"));
			},
			success: function(res) {
				agentName = res.body.agentName;
			}
		});

		return agentName;

	}

	table.render({
		elem: '#cashoutRecordtable',
		defaultToolbar: ['filter', 'print', 'exports'],
		url: baseURL + 'cashoutRecord/displayCashoutRecordPage',
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
			pageName: 'pageIndex', //页码的参数名称，默认：page
			limitName: 'pageSize' //每页数据量的参数名，默认：limit
		},
		where: data,
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
					title: '代理商名称',
					align: 'center',
					templet: function(d) {
						if(d.agentId == null) {
							return '--';
						} else {
							return showNameByAgentId(d.agentId);
						}
					}
				},
				{
					field: 'cashoutAmount',
					title: '提现金额',
					align: 'center'
				},
				{
					field: 'cashoutType',
					title: '提现类型',
					align: 'center'
				},
				{
					title: '状态',
					align: 'center',
					templet: function(d) {
						if(d.examineState == 0) {
							return "<span style='color:limegreen;'>未审核</span>";
						} else if(d.examineState == 1) {
							return '已审核';
						} else if(d.examineState == 2) {
							return "<span style='color:red;'>拒绝</span>";
						}
					}
				},
				{
					field: 'auditDate',
					title: '审核时间',
					align: 'center'
				},
				{
					field: 'applyReason',
					title: '拒绝原因',
					align: 'center',
					templet: function(d) {
						if(d.applyReason == null) {
							return '';
						} else {
							return d.applyReason;
						}
					}
				},
				{
					field: 'creDate',
					title: '申请时间',
					align: 'center'
				}
			]
		],
		page: true,
		done: function(res, curr, count) {
			pageIndex = curr;
		}
	});
	
	
	
	//批量删除
	var active = {
		getCheckData: function() { //获取选中数据
			var checkStatus = table.checkStatus('cashoutRecordtable'),
				data = checkStatus.data;
			//console.log(data);
			
			var id = data[0].id;
			layer.open({
				type: 1,
				title: "审核提现",
				id: "alert",
				area: ['800px', '600px'],
				content: $("#cashoutexamHtml").html(),
				success: function(layero, index) {
					form.render();
					$('.examineSubmit').on('click',function(){
						var examineState = $('[name="examineState"]').val();
						var applyReason = $('[name="applyReason"]').val();
						if(examineState==1){
							var data = {
								"examineState": examineState,
								"id": id
							}
							$.ajax({
								type: "post",
								url: baseURL + "cashoutRecord/updateCashoutRecordById",
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
										layer.msg('审核成功',function(){
											layer.close(index);
											show();
										});
									}else{
										layer.msg(res.message);
									}
								}
							});
						}else if(examineState==2){
							var data = {
								"applyReason": applyReason,
								"examineState": examineState,
								"id": id
							}
							$.ajax({
								type: "post",
								url: baseURL + "cashoutRecord/updateCashoutRecordById",
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
										layer.msg('审核成功',function(){
											layer.close(index);
											show();
										});
									}else{
										layer.msg(res.message);
									}
								}
							});
						}
					});
					$('.reset').on('click',function(){
						layer.close(index);
					});
				}
			});
	
		}
	};
	
	
	
	/*审核*/
	$('.examinebtn1').on('click', function() {
			var type = $('.examinebtn1').data('type');
			active[type] ? active[type].call(this) : '';
	
	});
	
	
	$('[lay-filter="search"]').on('click', function(obj) {
		
		
		show();
		
		
	});
	
	function show() {
		
	
		var recordId = $('[name="recordId"]').val();

		var data = {
			id: recordId
		};
		//console.log(data);
		table.reload('cashoutRecordtable', {
			where: data,
			page: {
				curr: 1
			}
		});
	}
	
	
	$('[lay-filter="resetSearch"]').on('click', function(obj) {
		
		
		$('[name="recordId"]').val('');
		
		
		
	});

});