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

	

	function initData(){
		var search = C.getSearch();
		$.ajax({
			url:baseURL+"manager/findManagerById",
			data: {
                id: search.userId
            },
			async:true,
			type: "post",
            dataType: "JSON",
            beforeSend:function(xhr){
            	xhr.setRequestHeader("X-Token",C.find("X-Token"));
            },
            success:function(data){
            	if(data.message == "success"){
            		userData = data.body;
            		
            		$("[name='userName']").val(userData.userName);
            		$("[name='phone']").val(userData.phone);
            		$("[name='vxCode']").val(userData.vxCode);
            		$("[name='address']").val(userData.address);
            		$("[name='identity']").val(userData.identity);
            		$("[name='creDate']").val(userData.creDate);
            		$("[name='dongjie']").val(userData.isStatus);
            		$("[name='userId']").val(userData.id);
            		$("#demo1").attr("src",baseURL+userData.headImage);
            	}
            }
		});
	}
	
	
	initData();
	
	$(".back").on('click',function(obj){
		location.replace("index.html");
	});
	


});