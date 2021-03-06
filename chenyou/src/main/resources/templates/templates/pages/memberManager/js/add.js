layui.config({
	base: "{/}/templates/lib/layui-v2.5.4/lay/my_modules/"
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


    var user = C.getCache('admin');
	
	$('[name="userName"]').blur(function(){
		$('.userName').html('');
	});
	$('[name="phone"]').blur(function(){
		$('.phone').html('');
	});
	
	
	var port = baseURL.substring(baseURL.lastIndexOf(":")+1);
	$(".submit").on('click',function(obj){
		var name = $("[name='userName']").val();
        var phone = $("[name='phone']").val();
        var amount = $("[name='amount']").val();

		/*var img = $("#demo1").attr("src");
		var imgSrc = img.substring(img.indexOf(port)+port.length)*/
		
		var data = {
		  "amount": amount,
		  "phone": phone,
            managerId: user.id,
		  "name": name
		};
		
		$.ajax({
				url:baseURL+"member/addMember",
				data:JSON.stringify(data),
				async:true,
				type: "post",
	            dataType: "JSON",
	            contentType: "application/json",
	            beforeSend:function(xhr){
	            	xhr.setRequestHeader("X-Token",C.find("X-Token"));
	            },
	            success:function(data){
	            	if(data.message == "success"){
	            		layer.msg("新增成功",function(obj){
	                    	location.replace("index.html");
						});
	            	}else{
	            		layer.msg(data.message);
	            	}
	            }
			});
		
		
	});
	
	
	
	
	//普通图片上传
	  /*var uploadInst = upload.render({
	    elem: '#test1'
	    ,url: baseURL + 'api/file/upload' //改成您自己的上传接口
	    ,before: function(obj){
	      //预读本地文件示例，不支持ie8
	      obj.preview(function(index, file, result){
	        $('#demo1').attr('src', result); //图片链接（base64）
	      });
	    }
	    ,done: function(res){
	      //如果上传失败
	      if(res.code > 0){
	        return layer.msg('上传失败');
	      }
	      //上传成功
	      $('#demo1').attr('src', baseURL+res.body.filePathURL);
	    }
	    ,error: function(){
	      //演示失败状态，并实现重传
	      var demoText = $('#demoText');
	      demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
	      demoText.find('.demo-reload').on('click', function(){
	        uploadInst.upload();
	      });
	    }
	  });*/
	
	
	


});