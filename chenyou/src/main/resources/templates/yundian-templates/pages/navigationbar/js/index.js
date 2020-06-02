layui.config({
	base: "{/}/yundian-templates/lib/layui-v2.5.4/lay/my_modules/"
}).extend({
	layuimini: 'layuimini',
	formSelects: 'formSelects/formSelects-v4',
	treeTable: 'treeTable/treeTable'
}).use(['laypage', 'layer', 'form', 'table', 'laydate', 'element', 'layuimini', 'carousel', 'treeTable'], function() {
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
	
	var findAjax = function(url, data, success) {
		//console.log(data);
		$.ajax({
			type: "POST",
			url: baseURL + url,
			data: JSON.stringify(data),
			dataType: "JSON",
			contentType:"application/json",
			async: false,
			beforeSend:function(xhr){
            	xhr.setRequestHeader("X-Token",C.find("X-Token"));
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
	
	
		
        // 渲染表格
        function initTable(data){
        	//console.log(data);
        	var insTb = treeTable.render({
            elem: '#demoTb1',
            data: data,  // 数据
            tree: {
                iconIndex: 1 , // 折叠图标显示在第几列
                idName: 'menuId',  // 自定义id字段的名称
            },
            cols: [
                {
					type: 'numbers'
				}, {
					field: 'menuId',
					title: '菜单id',
					align: 'center',
					width: 150
				}, {
					field: 'name',
					title: '菜单名称',
					edit: 'text',
					align: 'center'
				}, {
					field: 'url',
					title: '菜单URL',
					align: 'center',
					edit: 'text'
				},{
					field: 'type',
					title: '类型',
					align: 'center',
					templet: function(d) {
                		if(d.type==2){
                			return "按钮";
                		}else if(d.type==1){
                			if(d.parentId==-1){
                				return "父级";
                			}else{
                				return "页面";	
                			}
                			
                		}
					}
				}, {
					field: 'icon',
					title: '图标',
					align: 'center',
					edit: 'text'
				},{
					field: 'status',
					title: '当前状态',
					align: 'center',
					templet: function(d) {
                		if(d.status==1){
                			return "正常";
                		}else{
                			return "锁定中";
                		}
					}
				}, /*{
					field: 'source',
					title: '起源',
					align: 'center',
					width: 150
				},*/
				{
					title:'状态', 
					align: 'center',
					templet: '#switchTpl', 
					unresize: true
				},
                {
                	title: '操作',
                	width: 200,
                	fixed: 'right',
                	align:'center',
                	templet: function(d) {
                		if(d.type==2){
                			return "<button class='layui-btn layui-btn-xs layui-btn-normal' lay-event='updatenavi'>编辑</button>"+
								    "<button class='layui-btn layui-btn-xs layui-btn-warm' lay-event='delnavi'>删除</button>";
                		}else{
                			return "<button class='layui-btn layui-btn-xs layui-btn-normal' lay-event='updatenavi'>编辑</button>"+
								    "<button class='layui-btn layui-btn-xs layui-btn-normal' lay-event='addnavi'>添加子集</button>"+
								    "<button class='layui-btn layui-btn-xs layui-btn-warm' lay-event='delnavi'>删除</button>";
                		}
					}
                }
            ]
        	});
        
        }
        

	
	function changeList(list){
		for(var i=0;i<list.length;i++){
			list[i].children=list[i].menuListTwo;	
			delete list[i].menuListTwo;
		}
		for(var i=0;i<list.length;i++){
			for(var j=0;j<list[i].children.length;j++){
				if(list[i].children.length>1){
					var childLength = list[i].children.length;
					//console.log(childLength);
					for(var k=0;k<childLength;k++){
						//console.log(list[i].children[k]);
						if(list[i].children[k].menuListThree!=null){
							list[i].children[k].children = list[i].children[k].menuListThree;
							delete list[i].children[k].menuListThree;
							var childThreeLength = list[i].children[k].children.length;
							for(var p=0;p<childThreeLength;p++){
								list[i].children[k].children[p].children = list[i].children[k].children[p].menuListThree;
								delete list[i].children[k].children[p].menuListThree;
								list[i].children[k].children[p].children = [];
							}
						}
					}
				}else{
					for(var k=0;k<1;k++){
						//console.log(list[i].children[k]);
						if(list[i].children[k].menuListThree!=null){
							list[i].children[k].children = list[i].children[k].menuListThree;
							delete list[i].children[k].menuListThree;
							var childThreeLength = list[i].children[k].children.length;
							for(var p=0;p<childThreeLength;p++){
								list[i].children[k].children[p].children = list[i].children[k].children[p].menuListThree;
								delete list[i].children[k].children[p].menuListThree;
								list[i].children[k].children[p].children = [];
							}
						}
					}
				}
			}
		}
		//console.log(list);
		initTable(list);
	}
	

	
	function show(){
		var list = [];
		findAjax("sysMenu/menuLevel",{
			
		},function(res){
			var datas = res.body;
			for(var i=0;i<datas.length;i++){
				list = list.concat(datas[i]);
			}
			changeList(list);
			
		});
	}
	
	
	show();
	
	
	$('[lay-filter="addSysmenu"]').on('click', function (obj) {
        layer.open({
            type: 1,
            title: "添加侧边栏",
            id: "alert",
            area: ['500px', '250px'],
            content: $("#addNav").html(),
            success: function (layero, index) {
            	$('[name="icon"]').on('click', function (obj) {
			        layer.open({
					  type: 1,
					  area: ['750px', '450px'],
					  fixed: false, //不固定
					  maxmin: true,
					  content: $("#hide_div"),
					  success:function(layero, index){
					  	$(".btn11").on('click',function(){
		                	layer.close(index);
		                })
					  }
					});
			    });
                $("#commit").on('click', function (obj) {
                    var name = $('#alert [name="name"]').val(); 
                    var iconss = $('#alert [name="icon"]').val();
                    //console.log(name+"---"+iconss);
                    findAjax("sysMenu/saveMenuOne",{
                    	"icon": iconss,
  						"name": name
                    },function(res){
                    	if(res.message=='success'){
                    		layer.msg("添加成功", function () {
                    			setTimeout($.ajax({
			                    				type:"POST",
			                    				url:baseURL+"adminMenuRole/initAdminRole",
			                    				data:{
			                    					adminId:user.id
			                    				},
			                    				dataType:"json",
			                    				success:function(){
			                    					layer.msg("重置权限成功");
			                    				}
			                    			}),500);
                    			
                    			
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
    
    
    form.on('switch(sources)', function(obj){
	   //layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
	   if(obj.elem.checked){
	   		//恢复操作
	   		$.ajax({
	   			type:"post",
	   			url:baseURL+"sysMenu/updateMenuStatus",
	   			data:{
	   				menuId:this.value,
		   			status:1
	   			},
	   			beforeSend:function(xhr){
	            	xhr.setRequestHeader("X-Token",C.find("X-Token"));
	            },
	   			success:function(res){
	   				if(res.message=='success'){
			   			layer.msg('恢复成功');
			   			show();
			   		}
	   			}
	   		});
	   }else{
	   		//冻结操作
	   		$.ajax({
	   			type:"post",
	   			url:baseURL+"sysMenu/updateMenuStatus",
	   			data:{
	   				menuId:this.value,
		   			status:-1
	   			},
	   			beforeSend:function(xhr){
	            	xhr.setRequestHeader("X-Token",C.find("X-Token"));
	            },
	   			success:function(res){
	   				if(res.message=='success'){
			   			layer.msg('冻结成功');
			   			show();
			   		}
	   			}
	   		});
	   }
	});
    
    treeTable.on('tool(demoTb1)', function(obj){
	    var data = obj.data;  // 获得当前行数据
	    var layEvent = obj.event; // 获得lay-event对应的值
	    if(layEvent==='delnavi'){
	    	var mId = data.menuId;
	    	//console.log(menuId);
	    	//根据menuId查询该菜单下是否有子集
	    	//查询所有菜单
	    	findAjax("sysMenu/menuLevel",{
			
			},function(res){
				var list=[];
				var datas = res.body;
				for(var i=0;i<datas.length;i++){
					list = list.concat(datas[i]);
				}
				
				//console.log(mId);
				
				for(var i=0;i<list.length;i++){
					if(list[i].menuId==mId){
						//console.log(list[i]);
						var listTwo = list[i].menuListTwo;
						//console.log(listTwo)
						//var listThree = listTwo.menuListThree;
						if(listTwo.length==0){
							//console.log('进入了删除方法')
							//无子集，可以进行删除
							layer.confirm("确认删除此菜单吗?",function(){
						    	$.ajax({
						    		type:"post",
						    		url:baseURL+"sysMenu/removeMenu",
						    		data:{
						    			menuId:mId
						    		},
						    		dataType:"JSON",
						    		beforeSend:function(xhr){
						            	xhr.setRequestHeader("X-Token",C.find("X-Token"));
						            },
						    		success:function(res){
						    			if(res.message=='success'){
						    				layer.msg("删除成功",function(){
						    					setTimeout($.ajax({
			                    				type:"POST",
			                    				url:baseURL+"adminMenuRole/initAdminRole",
			                    				data:{
			                    					adminId:user.id
			                    				},
			                    				dataType:"json",
			                    				success:function(){
			                    					layer.msg("重置权限成功");
			                    				}
			                    			}),500);
                                				show();
						    				});
						    				
						    			}
						    		}
						    	});
						    });
						}else{
							//有子集，不可直接删除
							layer.msg("当前菜单有子集，不可直接删除");
							return;
						}
					}else{
						//遍历所有的二级页面
						//取出所有的二级页面
						var list2=[];
						for(j=0;j<list.length;j++){
							list2 = list2.concat(list[j].menuListTwo);
						}
						for(var j=0;j<list2.length;j++){
							if(list2[j].menuId==mId){
								var listThree = list2[j].menuListThree;
								if(listThree==null){
									//无子集，可以删除
									layer.confirm("确认删除此菜单吗?",function(){
								    	$.ajax({
								    		type:"post",
								    		url:baseURL+"sysMenu/removeMenu",
								    		data:{
								    			menuId:mId
								    		},
								    		dataType:"JSON",
								    		beforeSend:function(xhr){
								            	xhr.setRequestHeader("X-Token",C.find("X-Token"));
								            },
								    		success:function(res){
								    			if(res.message=='success'){
								    				layer.msg("删除成功",function(){
								    					setTimeout($.ajax({
					                    				type:"POST",
					                    				url:baseURL+"adminMenuRole/initAdminRole",
					                    				data:{
					                    					adminId:user.id
					                    				},
					                    				dataType:"json",
					                    				success:function(){
					                    					layer.msg("重置权限成功");
					                    				}
					                    			}),500);
		                                				show();
								    				});
								    				
								    			}
								    		}
								    	});
								    });
								}else{
									//有子集，不可删除
									layer.msg("当前菜单有子集，不可直接删除");
									return;
								}
							}else{
								layer.confirm("确认删除此按钮吗?",function(){
								    	$.ajax({
								    		type:"post",
								    		url:baseURL+"sysMenu/removeMenu",
								    		data:{
								    			menuId:mId
								    		},
								    		dataType:"JSON",
								    		beforeSend:function(xhr){
								            	xhr.setRequestHeader("X-Token",C.find("X-Token"));
								            },
								    		success:function(res){
								    			if(res.message=='success'){
								    				layer.msg("删除成功",function(){
								    					setTimeout($.ajax({
					                    				type:"POST",
					                    				url:baseURL+"adminMenuRole/initAdminRole",
					                    				data:{
					                    					adminId:user.id
					                    				},
					                    				dataType:"json",
					                    				success:function(){
					                    					layer.msg("重置权限成功");
					                    				}
					                    			}),500);
		                                				show();
								    				});
								    				
								    			}
								    		}
								    	});
								    });
								
							}
						}
						
					}
				}
				
			});
	   }else if(layEvent==='updatenavi'){
	   	//console.log(data);
	   		if(data['children']!=undefined){
	   			if(data.children.length==0){
		   			layer.confirm("确认编辑吗?",function(){
			   			findAjax("sysMenu/updateMenuInfoByMenuId",{
			   				"icon": data.icon,
						    "menuId": data.menuId,
						    "name": data.name
			   			},function(res){
			   				if(res.message=='success'){
			   					layer.msg('编辑成功');
			   				}else{
			   					layer.msg('一级菜单不能添加url');
			   				}
			   			})
			   		})
		   			if(data.type==2){
		   				layer.confirm("确认编辑吗?",function(){
				   			findAjax("sysMenu/updateMenuInfoByMenuId",{
				   				"icon": data.icon,
							    "menuId": data.menuId,
							    "name": data.name,
							    "url": data.url
				   			},function(res){
				   				if(res.message=='success'){
				   					layer.msg('编辑成功');
				   				}else{
				   					layer.msg('编辑失败');
				   				}
				   			})
				   		})
		   			}
		   		}else{
		   			layer.confirm("确认编辑吗?",function(){
			   			findAjax("sysMenu/updateMenuInfoByMenuId",{
			   				"icon": data.icon,
						    "menuId": data.menuId,
						    "name": data.name,
						    "url": data.url
			   			},function(res){
			   				if(res.message=='success'){
				   				layer.msg('编辑成功');
				   			}else{
				   				layer.msg('编辑失败');
				   			}
			   			})
			   		})
		   		}
	   		}else{
	   			layer.confirm("确认编辑吗?",function(){
			   			findAjax("sysMenu/updateMenuInfoByMenuId",{
						    "menuId": data.menuId,
						    "name": data.name,
						    "url": data.url
			   			},function(res){
			   				if(res.message=='success'){
				   				layer.msg('编辑成功');
				   			}else{
				   				layer.msg('编辑失败');
				   			}
			   			})
			   		})
	   		}
	   		
	   		
	   }else if(layEvent==='addnavi'){
	   		//console.log(data);
	   		if(data.parentId==-1){
	   			//当前是一级菜单新加子集
	   			layer.open({
	   				type: 1,
		            title: "新增页面",
		            id: "alert1",
		            area: ['500px', '250px'],
		            content: $("#hide_div1").html(),
		            success: function (layero, index) {
		            	$("#commit1").on('click', function (obj) {
		                    var name = $('#alert1 [name="name"]').val(); 
		                    var url = $('#alert1 [name="url"]').val();
	                    	var parentIds = data.menuId;
	                    	findAjax("sysMenu/saveMenuTwo",{
				   				"name": name,
							    "parentId": parentIds,
							    "url": url
				   			},function(res){
				   				if(res.message=='success'){
				   					layer.msg('新增页面成功', function () {
		                    			setTimeout($.ajax({
					                    				type:"POST",
					                    				url:baseURL+"adminMenuRole/initAdminRole",
					                    				data:{
					                    					adminId:user.id
					                    				},
					                    				dataType:"json",
					                    				success:function(){
					                    					layer.msg("重置权限成功");
					                    				}
					                    			}),500);
		                                layer.close(index);
		                                show();
		                            });
				   				}
				   			})
	                	});
	                	$("#cancle1").on('click', function (obj) {
		                    layer.close(index);
		                });
		            }
	   			});
	   		}else{
	   			//当前是二级页面添加按钮
	   			layer.open({
	   				type: 1,
		            title: "新增按钮",
		            id: "alert2",
		            area: ['500px', '250px'],
		            content: $("#hide_div1").html(),
		            success: function (layero, index) {
		            	$("#commit1").on('click', function (obj) {
		                    var name = $('#alert2 [name="name"]').val(); 
		                    var url = $('#alert2 [name="url"]').val();
	                    	var parentIds = data.parentId;
	                    	var parentTwoIds = data.menuId;
	                    	//console.log(parentIds);
	                    	findAjax("sysMenu/saveMenuThree",{
				   				"name": name,
							    "parentId": parentIds,
							    "parenttwoId": parentTwoIds,
							    "url": url
				   			},function(res){
				   				if(res.message=='success'){
				   					layer.msg('新增按钮成功', function () {
		                    			setTimeout($.ajax({
					                    				type:"POST",
					                    				url:baseURL+"adminMenuRole/initAdminRole",
					                    				data:{
					                    					adminId:user.id
					                    				},
					                    				dataType:"json",
					                    				success:function(){
					                    					layer.msg("重置权限成功");
					                    				}
					                    			}),500);
		                                layer.close(index);
		                                show();
		                            });
				   				}
				   			});
	                	});
	                	$("#cancle1").on('click', function (obj) {
		                    layer.close(index);
		                });
		            }
	   			});
	   		}
	   }
	});
	
	
	
	
	
	
	
	
	
	

});