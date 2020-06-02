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

	laydate.render({
		elem: '#date1'
	});

	laydate.render({
		elem: '#date2'
	});

	laydate.render({
		elem: '#date3'
	});
	
	laydate.render({
		elem: '#date20',
		type:'datetime'
	});


	var pageIndex = 1;
	var pageSize = 10;
	var user = C.getCache('admin');
	
	//初始化代理商列表
	var data = {
		"agentName": "",
		"pageIndex": 1,
		"pageSize": 9999,
		"supAgentId": 0
	}
	$.ajax({
		url: baseURL + 'agent/pageAgent',
		type: 'post',
		data: JSON.stringify(data),
		dataType: 'JSON',
		contentType: 'application/json',
		beforeSend: function(xhr) {
			xhr.setRequestHeader("X-Token", C.find("X-Token"));
		},
		success: function(res) {
			//console.log(res.body.records);
			var agents = res.body.records;
			var html = '<option value=""></option>';
			$.each(agents, function(index) {
				html += '<option value="' + this.id + '">' + this.agentName + '</option>';
			});
			$('[name="agentIds"]').html(html);
			form.render();
		}
	});

	/*var birthday = $('[name="birthday"]').val();
	var creDate = $('[name="creDate"]').val();
	var identity = $('[name="identity"]').val();
	var isStatus = $('[name="isStatus"]').val();
	var phone = $('[name="phone"]').val();
	var qu = $('[name="district"]').val();
	var sex = $('[name="sex"]').val();
	var sheng = $('[name="province"]').val();
	var shi = $('[name="city"]').val();
	var updDate = $('[name="updDate"]').val();
	var userName = $('[name="userName"]').val();
	var vxCode = $('[name="vxCode"]').val();

	var data = {
		"birthday": birthday,
		"creDate": creDate,
		"identity": identity,
		"isStatus": isStatus,
		"phone": phone,
		"qu": qu,
		"sex": sex,
		"sheng": sheng,
		"shi": shi,
		"updDate": updDate,
		"userName": userName,
		"vxCode": vxCode
	}*/
	
    var userName = $('[name="userName"]').val();
    var agentIds = $('[name="agentIds"]').val();
    var isStatus = $('[name="isStatus"]').val();
    var creDate = $('[name="creDate"]').val();
    
    var data = {
		"belongAgentId": agentIds,
		"creDate": creDate,
		"isStatus": isStatus,
		"userName": userName
	};
	
	function showNameByAgentId(agentid){
		var agentName = '';
		$.ajax({
			url:baseURL+'agent/selectAgentById',
			type:'post',
			async:false,
			data:{
				id:agentid
			},
			dataType:'JSON',
			beforeSend: function(xhr) {
				xhr.setRequestHeader("X-Token", C.find("X-Token"));
			},
			success:function(res){
				agentName = res.body.agentName;
			}
		});
		
		return agentName;
		
	}
    
	table.render({
		elem: '#table1',
		url: baseURL + 'user/getPageUser',
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
			[
				{
					type: 'checkbox',
					fixed: 'left'
				},{
					title: '序号',
					width: 100,
					align: 'center',
					type: 'numbers',
				
				},
				{
					width:150,
					field: 'phone',
					title: '用户名',
					align: 'center'
				},
				{
					width:150,
					field: 'userName',
					title: '昵称',
					align: 'center'
				},
				{
					width:150,
					field: 'headImage',
					title: '头像',
					align: 'center',
					templet:function(d){
						return '<img src="'+baseURL+d.headImage+'"/>';
					}
				},
				{
					width:150,
					title: '所属代理',align: 'center',
					templet:function(d){
						if(d.belongAgentId==null){
							return '--';
						}else{
							return showNameByAgentId(d.belongAgentId);
						}
						
					}
				},
				/*{
					width:170,
					field: 'phone',
					title: '电话',
					align: 'center'
				},*/
				{
					field: 'balance',
					title: '油币',
					align: 'center'
				},
				{
					field: 'userCoin',
					title: '礼币',
					align: 'center'
				},
				{
					width:100,
					title: '状态',
					align: 'center',
					templet: function(d) {
						if (d.isStatus == 1) {
							return "冻结中";
						} else {
							return "正常";
						}
					}

				},
				{
					width:170,
					field: 'exprieDate',
					title: '有效期',
					align: 'center'
				},
				{
					width:170,
					field: 'creDate',
					title: '注册时间',
					align: 'center'
				},
				{
					width: 200,
					title: '操作',
					align: 'center',
					fixed: 'right',
					templet: function(d) {
						if (d.isStatus == 1) {
							//可以解封
							return "<button class='layui-btn layui-btn-xs layui-btn-warm' lay-event='recovery'>解封</button>" +
								   "<button class='layui-btn layui-btn-xs layui-btn-danger' lay-event='delete'>删除</button>"
						} else {
							//可以冻结
							return "<button class='layui-btn layui-btn-xs layui-btn-normal' lay-event='del'>冻结</button>" +
								   "<button class='layui-btn layui-btn-xs layui-btn-danger' lay-event='delete'>删除</button>"
						}
					}
				}
			]
		],
		page: true,
		done: function(res, curr, count) {
			pageIndex = curr;
			$(".topcon b").html('当前一共有<b style=color:#ff5722>'+res.count+'</b>位用户');
		}
	});


	//编辑
	var active = {
		getCheckData: function() { //获取选中数据
			var checkStatus = table.checkStatus('table1'),
				data = checkStatus.data;
			//console.log(data);
			
			var id = data[0].id;
			layer.open({
				type: 1,
				title: "编辑用户",
				id: "alert",
				area: ['800px', '600px'],
				content: $("#userInfo").html(),
				success: function(layero, index) {
					form.render();
					//初始化数据
					$.ajax({
						url: baseURL + 'user/getUser',
						type: 'post',
						data: {
							id:id
						},
						dataType: 'JSON',
						beforeSend: function(xhr) {
							xhr.setRequestHeader("X-Token", C.find("X-Token"));
						},
						success: function(res) {
							var userData = res.body;
							$('[name="name"]').val(userData.phone);
							$('[name="userName1"]').val(userData.userName);
							$('[name="phone"]').val(userData.phone);
							$('[name="balance"]').val(userData.balance);
							$('[name="user_icon"]').val(userData.userCoin);
							$('[name="isStatus1"]').val(userData.isStatus);
							$('[name="expireDate"]').val(userData.exprieDate);
							$('[name="userId"]').val(userData.id);
							form.render();
							laydate.render({
								elem: '#date20',
								type:'datetime'
							});
						}
					});
					$('.editUserSubmit').on('click',function(){
						var name = $('[name="name"]').val();
						var userName = $('[name="userName1"]').val();
						var phone = $('[name="phone"]').val();
						var balance = $('[name="balance"]').val();
						var user_icon = $('[name="user_icon"]').val();
						var isStatus = $('[name="isStatus1"]').val();
						var expireDate = $('[name="expireDate"]').val();
						var userId = $('[name="userId"]').val();
						/*if(name==''){
							$('[name="name"]').parent('div').siblings('div').html('不能为空');
							return;
						}*/
						if(userName==''){
							$('[name="userName1"]').parent('div').siblings('div').html('<span style="color:red">不能为空</span>');
							return;
						}else{
							$('[name="userName1"]').parent('div').siblings('div').html('');
						}
						
						if(phone==''){
							$('[name="phone"]').parent('div').siblings('div').html('<span style="color:red">不能为空</span>');
							return;
						}else{
							$('[name="phone"]').parent('div').siblings('div').html('');
						}
						
						if(balance==''){
							$('[name="balance"]').parent('div').siblings('div').html('<span style="color:red">不能为空</span>');
							return;
						}else{
							$('[name="balance"]').parent('div').siblings('div').html('');
						}
						
						if(user_icon==''){
							$('[name="user_icon"]').parent('div').siblings('div').html('<span style="color:red">不能为空</span>');
							return;
						}else{
							$('[name="user_icon"]').parent('div').siblings('div').html('');
						}
						
						if(expireDate==''){
							$('[name="expireDate"]').parent('div').siblings('div').html('<span style="color:red">不能为空</span>');
							return;
						}else{
							$('[name="expireDate"]').parent('div').siblings('div').html('');
						}
						var data = {
							"balance": balance,
							"exprieDate": expireDate,
							"id": userId,
							"isStatus": isStatus,
							"name": name,
							"phone": phone,
							"userCoin": user_icon,
							"userName": userName
						}
						$.ajax({
							url: baseURL + 'user/updateUser',
							type: 'post',
							data: JSON.stringify(data),
							dataType: 'JSON',
							contentType: 'application/json',
							beforeSend: function(xhr) {
								xhr.setRequestHeader("X-Token", C.find("X-Token"));
							},
							success: function(res) {
								if(res.message=='success'){
									layer.msg('编辑成功',function(){
										layer.close(index);
										show();
									});
								}else{
									layer.msg(res.message);
								}
							}
						});
					});
					$('.reset').on('click',function(){
						layer.close(index);
					});
				}
			});
	
		}
	};
	
	
	
	/*编辑*/
	$('.editUserbtn1').on('click', function() {
			var type = $('.editUserbtn1').data('type');
			active[type] ? active[type].call(this) : '';
	
	});



	$('[lay-filter="search"]').on('click', function(obj) {
		show();
	});
	
	$('.layui-icon-refresh').on('click',function(){
	 	show();
	 });


	function show() {
		var userName = $('[name="userName"]').val();
		var agentIds = $('[name="agentIds"]').val();
		var isStatus = $('[name="isStatus"]').val();
		var creDate = $('[name="creDate"]').val();
		
		var data = {
			"belongAgentId": agentIds,
			"creDate": creDate,
			"isStatus": isStatus,
			"userName": userName
		};
		table.reload('table1', {
			where: data,
			page: {
				curr: 1
			}
		});
	}


	var findAjax = function(url, data, success) {
		//console.log(data);
		$.ajax({
			type: "POST",
			url: baseURL + url,
			data: data,
			dataType: "JSON",
			/*xhrFields: {
				withCredentials: true
			},*/
			async: false,
			beforeSend: function(xhr) {
				xhr.setRequestHeader("X-Token", C.find("X-Token"));
			},
			success: function(result) {
				if (result.errorCode + "" === "0") {
					success(result);
					layer.closeAll('loading');
				} else {
					success(result.msg);
					layer.closeAll('loading');
				}
			},
			error: function(e) {
				layer.msg("系统错误");
				layer.closeAll('loading');
			}
		});
	};


	table.on('tool(table1)', function(obj) {
		var data = obj.data; //获得当前行数据
		var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
		var tr = obj.tr; //获得当前行 tr 的DOM对象

		if (layEvent === 'xiangqing') {

			layuimini.goPage("pages/userManage/detail.html?userid=" + data.id + "", "查看用户详情");


		} else if (layEvent === 'del') {
			//冻结

			layer.confirm("是否确认冻结", function() {
				findAjax("user/updateUserIsstatus", {
					id: data.id,
					isStatus: 1
				}, function(res) {
					layer.msg("冻结成功", function(obj) {
						show();
					});
				});
			});

		} else if (layEvent === 'recovery') {
			layer.confirm("是否确认解封", function() {
				findAjax("user/updateUserIsstatus", {
					id: data.id,
					isStatus: 0
				}, function(res) {
					layer.msg("解封成功", function(obj) {
						show();
					});
				});
			});
		} else if (layEvent === 'delete') {
			layer.confirm("是否确认删除", function() {
				findAjax("user/removeUser", {
					id: data.id
				}, function(res) {
					layer.msg("删除成功", function(obj) {
						show();
					});
				});
			});
		}
	});


	$('[lay-filter="addUser"]').on('click', function(obj) {


		location.replace("add.html");


	});












});
