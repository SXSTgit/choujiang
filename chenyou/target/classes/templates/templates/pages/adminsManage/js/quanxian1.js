layui.config({
	base: "{/}/templates/lib/layui-v2.5.4/lay/my_modules/"
}).extend({
	layuimini: 'layuimini',
	formSelects: 'formSelects/formSelects-v4',
}).use(['laypage', 'layer', 'form', 'table', 'laydate', 'element', 'layuimini', 'carousel', 'transfer', 'util'], function() {
	var laypage = layui.laypage,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		element = layui.element,
		laydate = layui.laydate,
		layuimini = layui.layuimini,
		carousel = layui.carousel,
		transfer = layui.transfer,
		util = layui.util;
	
	
	var search = C.getSearch();
	var userid = search.userid;
	
	
	function initAllquanxian() {

		$.ajax({
			url: baseURL + "adminMenuRole/listAdminAddRoles",
			data: {
				adminId: 1
			},
			async: true,
			type: "post",
			dataType: "JSON",
			beforeSend:function(xhr){
            	xhr.setRequestHeader("X-Token",C.find("X-Token"));
            },
			success: function(data) {
				if(data.message == "success") {
					var result = data.body;
					var data2 = [];
					result.forEach(function(item) {
						var par = {
							'value': item.menuId,
							'title': item.name+item.parentId
						}
						data2.push(par);
					});
					

					$.ajax({
						url: baseURL + "adminMenuRole/listAdminAddRoles",
						data: {
							adminId: userid
						},
						async: true,
						type: "post",
						dataType: "JSON",
						beforeSend:function(xhr){
			            	xhr.setRequestHeader("X-Token",C.find("X-Token"));
			            },
						success: function(data) {
							if(data.message == "success") {

								var checkedResult = data.body;
								var checkVal = [];

								checkedResult.forEach(function(item2) {
									checkVal.push(item2.menuId + "");
								})


								//初始右侧数据
								transfer.render({
									elem: '#test1',
									title: ['可选的权限', '选择已拥有的权限'], //自定义标题
									data: data2,
									value: checkVal,
									onchange: function(data, index){
									    if(index == 0){
									    	//新增权限
									    	var menuId = data[0].value;
									    	$.ajax({
									    		url: baseURL + "adminMenuRole/saveAdminRoles",
												data: {
													adminId: userid,
													menuId: menuId
												},
												async: false,
												type: "post",
												dataType: "JSON",
												beforeSend:function(xhr){
									            	xhr.setRequestHeader("X-Token",C.find("X-Token"));
									            },
												success: function(data) {
													if(data.message=="success"){
														layer.msg("新增权限成功");
													}else{
														layer.msg("新增权限失败");
													}
												}
									    	});
									    }else if(index == 1){
									    	//删除权限
									    	var menuId = data[0].value;
									    	var items = data[0].title;
									    	var parentId = items.replace(/[^0-9]/ig,"");
									    	$.ajax({
									    		url: baseURL + "adminMenuRole/removeAdminRoles",
												data: {
													adminId: userid,
													menuId: menuId,
													parentId : parentId
												},
												async: false,
												type: "post",
												dataType: "JSON",
												beforeSend:function(xhr){
									            	xhr.setRequestHeader("X-Token",C.find("X-Token"));
									            },
												success: function(data) {
													if(data.message=="success"){
														layer.msg("删除权限成功");
													}else{
														layer.msg("删除权限失败");
													}
												}
									    	});
									    }
									}
								});
							} else {
								//初始右侧数据
								transfer.render({
									elem: '#test1',
									title: ['可选的权限', '选择已拥有的权限'],
									data: data2,
									onchange: function(data, index){
									    if(index == 0){
									    	//新增权限
									    	var menuId = data[0].value;
									    	$.ajax({
									    		url: baseURL + "adminMenuRole/saveAdminRoles",
												data: {
													adminId: userid,
													menuId: menuId
												},
												async: false,
												type: "post",
												dataType: "JSON",
												beforeSend:function(xhr){
									            	xhr.setRequestHeader("X-Token",C.find("X-Token"));
									            },
												success: function(data) {
													if(data.message=="success"){
														layer.msg("新增权限成功");
													}else{
														layer.msg("新增权限失败");
													}
												}
									    	});
									    }else if(index == 1){
									    	//删除权限
									    	var menuId = data[0].value;
									    	var items = data[0].title;
									    	var parentId = items.replace(/[^0-9]/ig,"");
									    	$.ajax({
									    		url: baseURL + "adminMenuRole/removeAdminRoles",
												data: {
													adminId: userid,
													menuId: menuId,
													parentId : parentId
												},
												async: false,
												type: "post",
												dataType: "JSON",
												beforeSend:function(xhr){
									            	xhr.setRequestHeader("X-Token",C.find("X-Token"));
									            },
												success: function(data) {
													if(data.message=="success"){
														layer.msg("删除权限成功");
													}else{
														layer.msg("删除权限失败");
													}
												}
									    	});
									    }
									  }
								});
							}
						}
					});
				}
			}
		});

	}
	
	
	
	initAllquanxian();
	


});