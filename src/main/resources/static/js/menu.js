/**
 * 左侧菜单操作（html加载完成之后）
 */
$(function(){
	var request_uri = window.location.pathname;
	$(".layui-nav-child dd a").each(function() {
		var item_uri = $(this).attr("href");
		// 比对当前window.location.pathname是否以功能Item的链接开头；注意，此比对非严格一致，需要考虑功能Item链接的复数形式。
		var pattern = $(this).attr("data-pattern");
		var matchPatterns = [];
		if(pattern) {
			let matched = false;
			pattern.split(",").forEach(function(val) {
				let patternStr = val.replace(/\/\*{2}/g, "/.*?");
				if(new RegExp(patternStr).test(request_uri)) {
					matched = true;
					return;
				}
			});
			if(matched) {
				// 上级dd加样式
				var $active_menu_item = $(this).parent();
				$active_menu_item.addClass("layui-this");
				// 上级li加样式
				var $active_cate_item = $active_menu_item.parents("li");
				$active_cate_item.addClass("active open");
			}
		}
	});
	
});