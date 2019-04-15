
var preUrl = "/system/organ"
$(function() {
	load();
});
function load() {
    var query = {
        organname : $('#searchName').val()
    }
    $('#bTable')
        .bootstrapTreeTable(
            {
                id : 'organid',
                code : 'organid',
                parentCode : 'organpid',
                type : "GET", // 请求数据的ajax类型
                url : prefix + '/list', // 请求数据的ajax的url
                ajaxParams : query, // 请求数据的ajax的data属性
                expandColumn : '2', // 在哪一列上面显示展开按钮
                striped : true, // 是否各行渐变色
                bordered : true, // 是否显示边框
                expandAll : true, // 是否全部展开
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
                        field : 'organid',
                        visible : false,
                        width : '50px'
                    },
                    {
                        field : 'organname',
                        title : '部门名称',
                        valign : 'center'
                    },
                    {
                        field : 'organcode',
                        title : '机构编码'
                    },
                    {
                        field : 'organalias',
                        title : '机构别名'
                    },
                    {
                        field : 'areaid',
                        title : '地区编号'
                    },
                    {
                        field : 'postcode',
                        title : '邮政邮编' ,
                        visible : false
                    },
                    {
                        field : 'address',
                        title : '机构地址'
                    },
                    {
                        field : 'telephone',
                        title : '机构电话' ,
                        visible : false
                    },
                    {
                        field : 'fax',
                        title : '传真号码' ,
                        visible : false
                    },
                    {
                        field : 'organman',
                        title : '负责人姓名' ,
                        visible : false
                    },
                    {
                        field : 'organmanid',
                        title : '负责人编码' ,
                        visible : false
                    },
                    {
                        field : 'remark',
                        title : '机构备注信息'
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
                    }]
            });
}
function reLoad() {
    load();
}
function add() {
	layer.open({
		type : 2,
		title : '增加机构',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add' // iframe的url
	});
}
function edit() {
	var id = $('#bTable').bootstrapTreeTable('getSelection');
	   if (id == undefined) {
            layer.msg("请选择一条要修改的数据");
            return;
        }
	layer.open({
		type : 2,
		title : '编辑机构',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : preUrl + '/edit/' + id // iframe的url
	});
}
function del() {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
        var id = $('#bTable').bootstrapTreeTable('getSelection');
        if (id == undefined) {
            layer.msg("请选择一条要删除的数据");
            return;
        }
		$.ajax({
			url : preUrl+"/del",
			type : "post",
			data : {
				'organid' : id
			},
			success : function(r) {
				if (r.code==0) {
					layer.msg(r.msg);
					reLoad();
				}else{
					layer.msg(r.msg);
				}
			}
		});
	})
}