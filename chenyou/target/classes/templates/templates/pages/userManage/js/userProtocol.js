var wangEditor;

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
	
	function initEditor() {
		var E = wangEditor;
		editor = new E('#myEditor');
		editor.customConfig.uploadImgServer = baseURL + 'api/file/upload';
		editor.customConfig.uploadFileName = 'file';
		editor.customConfig.zIndex = 0;
		editor.customConfig.uploadImgMaxSize = 2 * 1024 * 1024;
		editor.customConfig.uploadImgHooks = {
			customInsert: function(insertImg, result, editor) {
				insertImg(baseURL + result.body.filePathURL);
			}
		};
		editor.customConfig.uploadHeaders = {
			'Accept': 'text/x-json'
		};
		editor.create();
		editor.config.hideLinkImg = true;
	}

	initEditor();

	$.ajax({
		type: "post",
		url: baseURL + "userProtocol/showProtocol",
		async: true,
		data: {},
		dataType: "JSON",
		beforeSend: function(xhr) {
			xhr.setRequestHeader("X-Token", C.find("X-Token"));
		},
		success: function(res) {
			editor.txt.html(res.body.context);
			$("[name='id']").val(res.body.id)
		}
	});
	
	$(".submit").click(function() {
		var context = editor.txt.html();
		var id = $("[name='id']").val();
		$.ajax({
			type: "post",
			url: baseURL + "userProtocol/updateUserProtocol",
			async: true,
			data: {
				id:id,
				context:context
			},
			dataType: "JSON",
			beforeSend: function(xhr) {
				xhr.setRequestHeader("X-Token", C.find("X-Token"));
			},
			success: function(res) {
				layer.msg("保存成功.",function(obj){
				   	location.replace("userProtocol.html");
				});
			}
		});
	})


});
