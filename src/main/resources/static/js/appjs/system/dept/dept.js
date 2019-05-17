
var preUrl = "/system/dept";
$(function() {
    var organId = '';
    getTreeData();
    load(organId);
});

function load(organId) {
    var query = {
        deptname : $('#searchName').val(),
        organid: organId
    }
	$('#bTable')
		.bootstrapTreeTable(
			{
				id : 'deptid',
				code : 'deptid',
                parentCode : 'deptpid',
				type : "GET", // 请求数据的ajax类型
				url : preUrl + '/list', // 请求数据的ajax的url
				ajaxParams : query, // 请求数据的ajax的data属性
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
                        valign : 'center',
                        width : '150px'
					},
                    {
                        field : 'deptcode',
                        title : '部门别名'
                    },
                    {
                        field : 'deptman',
                        title : '负责人'
                    },
                    {
                        field : 'deptmanid',
                        title : '部门的责人ID',
                        visible : false
                    },
                    {
                        field : 'ordernum',
                        title : '排序',
                        align : 'center',
                        valign : 'center',
                        width : '50px'
                    },
					{
						field : 'isvalidation',
						title : '状态',
						align : 'center',
                        valign : 'center',
                        width : '50px',
						formatter : function(item, index) {
							if (item.isvalidation == '0') {
								return '<span class="label label-danger">禁用</span>';
							} else if (item.isvalidation == '1') {
								return '<span class="label label-primary">正常</span>';
							}
						}
					},
                    {
                        field : 'remark',
                        title : '备注'
                    },
                    {
                        field : 'createTime',
                        title : '创建时间',
                        width : '150px'
                    },
                    {
                        field : 'modifyTime',
                        title : '修改时间',
                        width : '150px'
                    }]
			});
}
function reLoad() {
	load();
}

function add() {
    layer.open({
        type : 2,
        title : '增加部门',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : preUrl + '/add'
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
        title : '编辑部门',
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
                'deptId' : id
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

function getTreeData() {
    $.ajax({
        type : "GET",
        url : "/system/organ/tree",
        data : {
            "organid" : ''
        },
        success : function(tree) {
            loadTree(tree);
        }
    });
}
function loadTree(tree) {
    $('#jstree').jstree({
        'core' : {
            'data' : tree,
            "multiple" : false, // no multiselection
            "themes" : {
                "dots" : false // no connecting dots between dots
            }
        }
    });
    $('#jstree').jstree().open_all();
}
$('#jstree').on("changed.jstree", function(e, data) {
    var organId = '';
    if(data.selected != -1){
        organId = data.node["id"];
        load(organId);
    }
});


