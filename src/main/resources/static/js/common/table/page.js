/**
 * 分页组件
 */
$(function(){ 
	// 加载组件
	layui.define(['element','layer','laypage' ], function(exports) {
		var $ = layui.jquery;
		var layer = layui.layer;
		var laypage = layui.laypage;
		
		laypage.render({
			elem : 'laypage',
			curr: Number($("#page").val()==null ? 0 : $("#page").val()) + 1 ,
			count : Number($("#totalElements").val()==null ? 0 : $("#totalElements").val()),
			theme: '#009688',
			limit: 10,
			jump: function(obj, first){
				//得到了当前页，用于向服务端请求对应数据
			    var curr = obj.curr;
			    $("#page").val(curr - 1); // 当前页数
			    $("#size").val(10); // 每页10条
			    $("#from").submit(); // 提交后台查询 
			}
		});
	});
	
});
 