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
    var pageIndex = C.find("pageIndex");

//	var recordId = $('[name="recordId"]').val();

	var data = {
		id: user.id
	};
	
	/*function showNameByAgentId(agentid){
		var agentName = '';
		$.ajax({
			url:baseURL+'userOrder/selectUserOrderPage',
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
		elem: '#cashoutRecordtable',
		defaultToolbar: ['filter', 'print', 'exports'],
		url: baseURL + 'userOrder/selectUserOrderPage',
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
					title: '会员名字',
					align: 'center',
					templet:function(d){
						if(d.agentId==null){
							return '--';
						}else{
							return showNameByAgentId(d.name);
						}
					}
				},
				{
					field: 'money',
					title: '金额',
					align: 'center'
				},
                ,
                {
                    field: 'balance',
                    title: '余额',
                    align: 'center'
                },
				{
					title: '提现类型',
					align: 'center',
                    templet:function(d){
                        if(d.type==0){
                            return '充值';
                        }else{
                            return '消费';
                        }}
				},
				{
					title: '状态',
					align: 'center',
					templet: function(d) {
						if(d.examineState==0){
							return "<span style='color:limegreen;'>未审核</span>";
						}else if(d.examineState==1){
							return '已审核';
						}else if(d.examineState==2){
							return "<span style='color:red;'>拒绝</span>";
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
*/



    var findAjax = function(url, data, success) {
        $.ajax({
            type: "POST",
            url: baseURL + url,
            data: JSON.stringify(data),
            dataType: "JSON",
            contentType:"application/json",
            /*xhrFields: {
                withCredentials: true
            },*/
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

    function initTable(data, limit) {
        table.render({
            elem: '#table1',
            height: 'full-160' //高度最大化减去差值
            ,
            limit: limit,
            data: data,
            page: false //开启分页
            ,
            cols: [
                [{
                    field: 'xh',
                    title: '序号',
                    width: 100,
                    align: 'center',
                    type: 'numbers',
                    templet: function(d) {
                        return d.xh;
                    }
                },{
                    title: '会员名字',
                    align: 'center',
                    width: 200,
                    templet:function(d) {
                        return d.name;
                    }
                },
                    {
                        title: '类型',
                        align: 'center',
                        width: 200,
                        templet:function(d){
                            if(d.type==0){
                                return '充值';
                            }else{
                                return '消费';
                            }}
                    },

                    {
                        field: 'money',
                        title: '金额',
                        width: 120,
                        align: 'center',
                        templet: function(d) {
                            return d.money;
                        }
                    },
                    {
                        field: 'balance',
                        title: '余额',
                        width: 200,
                        align: 'center',
                        templet: function(d) {
                            if (d.balance == null) {
                                return "略";
                            } else {
                                return d.balance;
                            }
                        }
                    },

                    {
                        field: 'creDate',
                        title: '创建时间',
                        width: 200,
                        align: 'center',
                        templet: function(d) {
                            return d.creattime;
                        }
                    }
                ]
            ]
        });
    }
    function initPage(total, limit, curr) {
        //执行一个laypage实例
        laypage.render({
            elem: 'page',
            count: total,
            limit: limit,
            curr: curr,
            layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh'],
            jump: function(obj, first) {
                //首次不执行
                if (!first) {
                    pageIndex = curr;

                    show(user.id,obj.curr, obj.limit);
                }


            }
        });
    }

    function show(managerId,pageIndex,pageSize) {
        C.save("pageIndex",pageIndex);
        findAjax("userOrder/selectUserOrderPage", {
            "managerId": managerId,
            "pageIndex": pageIndex,
            "pageSize": pageSize,
        }, function(res) {
            if (res) {
                initTable(res.body.records, pageSize);
                initPage(res.body.total, pageSize, pageIndex);
            }
        });
    }

    show(user.id,pageIndex,10);

	$('[lay-filter="search"]').on('click', function(obj) {
		show();
	});
	
	/*function show() {

		var recordId = $('[name="recordId"]').val();
		var data = {
			id: user.id
		};
		//console.log(data);
		table.reload('cashoutRecordtable', {
			where: data,
			page: {
				curr: 1
			}
		});
	}*/
	
	
	$('[lay-filter="resetSearch"]').on('click', function(obj) {
		$('[name="recordId"]').val('');

	});

});