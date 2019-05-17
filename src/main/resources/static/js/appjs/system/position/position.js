
var preUrl = "/system/position"
$(function() {
    var organId = '';
    var deptmentId = '';
    getTreeData();
    load(organId,deptmentId);
});

function load(organId,deptmentId) {
    var query = {
        postname : $('#searchName').val(),
        organid: organId,
        deptid:deptmentId
    }
	$('#bTable')
			.bootstrapTreeTable(
					{
                        id : 'postid',
                        code : 'postid',
                        parentCode : 'postpid',
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
									field : 'postid',
									title : '主键ID',
									visible :false
								},
								{
									field : 'postname',
									title : '岗位名称'
								},
								{
									field : 'postpid', 
									title : '上级岗位ID',
                                    visible :false
								},
								{
									field : 'deptid', 
									title : '部门ID' ,
                                    visible :false
								},
								{
									field : 'organid', 
									title : '机构ID' ,
                                    visible :false
								},

								{
									field : 'postcode', 
									title : '岗位编号' 
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
function view() {
    // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    var rows = $('#bTable').bootstrapTreeTable('getSelections');
    var id;
    if (rows.length == 0||rows.length >1) {
        layer.msg("请选择一条数据");
        return;
    }else{
        id=rows[0]['postid'];
    }
    layer.open({
        type : 2,
        title : '查看',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : preUrl + '/view/'+id // iframe的url
    });
}
function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : preUrl + '/add' // iframe的url
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
        title : '编辑岗位',
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
				'postid' : id
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
        url : "/system/organ/organdept",
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
    var deptmentId = '';
    if(data.selected != -1){
        if(data.node["icon"]=="fa fa-institution"){
            deptmentId = data.node["id"];
        }else{
            organId = data.node["id"];
        }
        load(organId,deptmentId);
    }
});


