/**
 * 选择项目角色登陆页JS
 */

layui.define([ 'element', 'layer', 'form' ], function(exports) {
	var form = layui.form();
	var $ = layui.jquery;
	// 自定义验证
	form.verify({
		checkProjectRole : function(value){
			var checkRole = $("input[name='roleCode']").next(".layui-form-radioed");
			if(checkRole.length == 0){
				return "登陆角色必选";
			}
			$("input[name='projectCode']").val(checkRole.prev().attr("projectcode"));
			$("input[name='description']").val(checkRole.prev().attr("title"));
		}
	});
	// 监听登陆提交
	form.on('submit(project)', function(data) {
		
	}); 
	exports('project', {});
});
