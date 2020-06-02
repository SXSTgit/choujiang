layui.config({
	base: "{/}/yundian-templates/lib/layui-v2.5.4/lay/my_modules/"
}).extend({
	layuimini: 'layuimini',
	formSelects: 'formSelects/formSelects-v4',
}).use(['laypage', 'layer', 'form', 'table', 'laydate', 'element', 'layuimini', 'upload', 'carousel'], function() {
	var laypage = layui.laypage,
		layer = layui.layer,
		form = layui.form,
		table = layui.table,
		element = layui.element,
		laydate = layui.laydate,
		layuimini = layui.layuimini,
		carousel = layui.carousel,
		upload = layui.upload;
	
	
	/*var data={
		"identity": "",
		  "isStatus": 0,
		  "pageIndex": 1,
		  "pageSize": 9999,
		  "phone": "",
		  "userName": "",
		  "vxCode": ""
	}*/
	
	/*function initReferrer(){
		$.ajax({
			url:baseURL+'user/getPageUser',
			type:'post',
			data:JSON.stringify(data),
			contentType:'application/json',
			dataType:'JSON',
			beforeSend:function(xhr){
            	xhr.setRequestHeader("X-Token",C.find("X-Token"));
            },
			success:function(res){
				if(res.message='success'){
					var userdata = res.body.records;
					var html = '<option value="">未选择</option>';
					$.each(userdata, function(index) {
						html+='<option value='+this.id+'>'+this.userName+'</option>';
					});
					
					$('[name="referrer"]').html(html);
					form.render();
				}
			}
		});
	}
	
	initReferrer();*/
	
	var referId = 0;
	$('[name="referrer"]').blur(function(){
		var referPhone = $('[name="referrer"]').val();
		if(referPhone==''){
			$('.referrer').html('');
		}else{
			//根据推荐人手机号查询推荐人id
			$.ajax({
				type:"post",
				url:baseURL+"user/getUserByPhone",
				async:true,
				data:{
					phone:referPhone
				},
				dataType:"JSON",
				beforeSend: function(xhr) {
					xhr.setRequestHeader("X-Token", C.find("X-Token"));
				},
				success:function(res){
					if(res.body==null){
						$('.referrer').html('<i class="layui-icon layui-icon-about" style="color:#f00">手机号填写有误</i>');
					}
				}
			});
		}
	});
	
	
	$(".submit").on('click',function(obj){
		var identity = $('[name="identity"]').val();
		var name = $('[name="name"]').val();
		var phone = $('[name="phone"]').val();
		var password = $('[name="password"]').val();
		var referPhone = $('[name="referrer"]').val();
		var sex = $('[name="sex"]').val();
		var userEmail = $('[name="userEmail"]').val();
		var userName = $('[name="userName"]').val();
		var vxCode = $('[name="vxCode"]').val();
		
		
		//根据推荐人手机号查询推荐人id
		$.ajax({
			type:"post",
			url:baseURL+"user/getUserByPhone",
			async:true,
			data:{
				phone:referPhone
			},
			dataType:"JSON",
			beforeSend: function(xhr) {
				xhr.setRequestHeader("X-Token", C.find("X-Token"));
			},
			success:function(res){
				if(res.message=='success'){
					referId = res.body.id;
					
				}
			}
		});
		
		
		var data = {
			"identity": identity,
			"name": name,
			"password": password,
			"phone": phone,
			"referrerId": referId,
			"sex": sex,
			"userEmail": userEmail,
			"userName": userName,
			"vxCode": vxCode
		};
		
		$.ajax({
			type:"post",
			url:baseURL+"user/register",
			async:true,
			data:JSON.stringify(data),
			contentType:'application/json',
			dataType:'JSON',
			beforeSend: function(xhr) {
				xhr.setRequestHeader("X-Token", C.find("X-Token"));
			},
			success:function(res){
				if(res.message == "success") {
					layer.msg("新增成功", function(obj) {
						location.replace("index.html");
					});
				} else {
					layer.msg(res.message);
				}
			}
		});
	});
	
	
	

});