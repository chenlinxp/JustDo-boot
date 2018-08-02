
var prefix = "/system/dept"
$(function() {
	load();
});

function load() {
	$('#bTable')
		.bootstrapTreeTable(
			{
				id : 'deptid',
				code : 'deptid',
                parentCode : 'deptpid',
				type : "GET", // 请求数据的ajax类型
				url : prefix + '/list', // 请求数据的ajax的url
				ajaxParams : {}, // 请求数据的ajax的data属性
				expandColumn : '2', // 在哪一列上面显示展开按钮
				striped : true, // 是否各行渐变色
				bordered : true, // 是否显示边框
				expandAll : false, // 是否全部展开
				// toolbar : '#bToolbar',
				columns : [
                    {
                        title : '序号',
                        field : 'rowalias',
                        align : 'left',
                        valign : 'center',
                        width : '50px'
                    },
					{
						title : '编号',
						field : 'deptid',
						visible : false,
                        width : '50px'
					},
					{
						field : 'deptname',
						title : '部门名称',
                        valign : 'center'
					},
					{
						field : 'ordernum',
						title : '排序',
                        align : 'center',
                        valign : 'center',
					},
					{
						field : 'isvalidation',
						title : '状态',
						align : 'center',
                        valign : 'center',
						formatter : function(item, index) {
							if (item.isvalidation == '0') {
								return '<span class="label label-danger">禁用</span>';
							} else if (item.isvalidation == '1') {
								return '<span class="label label-primary">正常</span>';
							}
						}
					},
					{
						title : '操作',
						field : 'operation',
						align : 'center',
                        valign : 'center',
						formatter : function(item, index) {
							var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
								+ item.deptid
								+ '\')"><i class="fa fa-edit"></i></a> ';
							var a = '<a class="btn btn-primary btn-sm ' + s_add_h + '" href="#" title="增加下級"  mce_href="#" onclick="add(\''
								+ item.deptid
								+ '\')"><i class="fa fa-plus"></i></a> ';
							var d = '<a class="btn btn-warning btn-sm ' + s_delete_h + '" href="#" title="删除"  mce_href="#" onclick="delone(\''
								+ item.deptid
								+ '\')"><i class="fa fa-remove"></i></a> ';
							return e + a + d;
						}
					} ]
			});
}
function reLoad() {
	load();
}
function add(pId) {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add/' + pId
	});
}
function add() {
    layer.open({
        type : 2,
        title : '增加',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : prefix + '/add'
    });
}
function edit(id) {
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/edit/' + id // iframe的url
	});
}
function delone(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix + "/del",
			type : "post",
			data : {
				'deptid' : id
			},
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
			 	}
			}
		});
	})
}

