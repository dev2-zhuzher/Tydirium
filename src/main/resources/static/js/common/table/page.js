/**
 * 分页组件
 */
$(function(){ 
	// 加载组件
	layui.define([ 'element','layer','laypage','pagesize' ], function(exports) {
		var $ = layui.jquery;
		var element = layui.element();
		var layer = layui.layer;
		var laypage = layui.laypage;
		var pagesize = layui.pagesize;

		laypage({
			cont : 'laypage',
			curr: Number($("#page").val()==null ? 1 : $("#page").val()),
			pages : Number($("#pagerTotalPages").val()==null ? 0 : $("#pagerTotalPages").val()),
			skip : true,
			skin: '#1E9FFF',
			jump: function(obj, first){
				//得到了当前页，用于向服务端请求对应数据
			    var curr = obj.curr;
			    $("#page").val(curr); // 当前页数
			    $("#size").val(10); // 每页10条
			    $("#from").submit(); // 提交后台查询 
			}
		});
	});
	
});
 