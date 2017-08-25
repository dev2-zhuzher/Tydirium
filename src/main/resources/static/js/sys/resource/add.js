/**
 * 模块管理:新增或修改
 */
$(function() {
	// 加载控件
	layui .use( [ 'element','form', 'layedit', 'laydate' ], function() {
		var form = layui.form, layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate;
 
		// 自定义验证规则
		form.verify({ 
			password : [ /(.+){6,12}$/, '密码必须6到12位' ],
		});

		// 监听提交
		form.on('submit(resourceAddOrEditForm)', function(data) {
			 // 目前不在此操作
		});

	});
})