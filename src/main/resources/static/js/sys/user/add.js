/**
 * 模块管理:新增或修改
 */
$(function() {
	// 加载控件
	layui .use( [ 'element','form', 'layedit', 'laydate' ], function() {
		var form = layui.form, layer = layui.layer, layedit = layui.layedit, laydate = layui.laydate;
 
		// 自定义验证规则
		form.verify({ 
			password : function(value){
				var id = $("input[name='id']").val();
				var pass = $("input[type='password']");
				var reg = /(.+){6,12}$/;
				if(id != "" && pass[0].value == pass[1].value && pass[0].value == ""){
					
				}else{
					if(!reg.test(value)){
						return '密码必须6到12位';
					}
					if(pass[0].value != pass[1].value){
						return '密码输入不一致';
					}
				}
			},
			checkRole : function(value){
				var checkRole = $("input[name='roleIds']").next(".layui-form-checked");
				if(checkRole.length == 0){
					return "所属角色必选";
				}
			}
		});

		// 监听提交
		form.on('submit(userAddOrEditForm)', function(data) {
			 
		});

	});
})