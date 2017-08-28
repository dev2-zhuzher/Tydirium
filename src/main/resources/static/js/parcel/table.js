/**
 * 分页组件
 */
$(function() {
	// 加载组件
	layui.define([ 'element', 'layer', 'table' ], function(exports) {
		var $ = layui.jquery;
		var layer = layui.layer; // 弹出层
		var laypage = layui.laypage;
		var table = layui.table; // 数据table

		table.render({
			elem : '#table', 
			url : '/admin/parcel/lists',
			method : "post",
			loading: true,
			page : true, // 开启分页
			size:"lg",
			where : {},
			height : 500,
			cols : [ [ {
				checkbox : true,
				space : true
			}, {
				field : 'projectName',
				title : '项目名称',
				width : 100
			}, {
				field : 'id',
				title : '邮包ID',
				width : 100
			}, {
				field : 'logisticCode',
				title : '运单编号',
				width : 100
			}, {
				field : 'shipperCode',
				title : '快递公司',
				width : 100
			}, {
				field : 'receiverMobile',
				title : '收件人手机号',
				width : 150
			}, {
				field : 'receiveAddress',
				title : '收件人地址',
				width : 100
			}, {
				field : 'itemType',
				title : '物品类型',
				width : 100
			}, {
				field : 'createTime',
				title : '创建时间',
				width : 100
			}, {
				field : '',
				title : '最新备注',
				width : 100
			}, {
				//fixed : 'right',
				width : 150,
				title : "操作",
				align : 'center',
				toolbar : '#tableBar'
			} ] ],
			done : function(res, curr, count) {
				// 回调函数：res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
				// ，curr为当前页码
				 

			}
		});
		
		//监听事件
		table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		  var data = obj.data; //获得当前行数据
		  var layEvent = obj.event; //获得 lay-event 对应的值
		  var tr = obj.tr; //获得当前行 tr 的DOM对象
		 
		  if(layEvent === 'detail'){ //查看
		    //do somehing
		  } else if(layEvent === 'del'){ //删除
		    layer.confirm('真的删除行么', function(index){
		      obj.del(); //删除对应行（tr）的DOM结构
		      layer.close(index);
		      //向服务端发送删除指令
		    });
		  } else if(layEvent === 'edit'){ //编辑
		    //do something
		    
		    //同步更新缓存对应的值
		    obj.update({
		      username: '123'
		      ,title: 'xxx'
		    });
		  }
		});
	});

});
