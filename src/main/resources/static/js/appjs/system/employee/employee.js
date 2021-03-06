var preUrl = "/system/employee"
$(function() {
	var organId = '';
	var deptmentId = '';
	var positionId = '';
	getTreeData();
	load(organId,deptmentId,positionId);
});

function load(organId,deptId,postId) {
	$('#bTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : preUrl + "/list", // 服务器数据的加载地址
				showRefresh : true,
				// showToggle : true,
				// showColumns : true,
				iconSize : 'outline',
				toolbar : '#bToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				pagination : true, // 设置为true会在底部显示分页条
				// queryParamsType : "limit",
				// //设置为limit则会发送符合RESTFull格式的参数
				singleSelect : false, // 设置为true将禁止多选
				// contentType : "application/x-www-form-urlencoded",
				// 发送到服务器的数据编码类型
                sortable: true,
				pageSize : 10, // 如果设置了分页，每页数据条数
                pageList: [10,20,50,100],
				pageNumber : 1, // 如果设置了分布，首页页码
				// search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
				// "server"
				queryParams : function(params) {
					return {
						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
                        sort: 'CREATE_TIME',
                        order: 'desc',
                        loginName : $('#searchName').val(),
                        deptmentId : deptId,
						organId: organId,
                        positionId: postId
					};
				},
                onDblClickRow: function (row, element) {
                    view(row["employeeId"]);
                },
				// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
				// queryParamsType = 'limit' ,返回参数必须包含
				// limit, offset, search, sort, order 否则, 需要包含:
				// pageSize, pageNumber, searchText, sortName,
				// sortOrder.
				// 返回false将会终止请求
                onClickRow: function (row, element) {
                    $('.success').removeClass('success');//去除之前选中的行的，选中样式
                    $(element).addClass('success');//添加当前选中的 success样式用于区别
                    $("#bTable").bootstrapTable("uncheckAll");
                    var rowindex=$(element).attr("data-index");
                    $("#bTable").bootstrapTable('check',rowindex);
                    // $.each($("#bTable  input[type='checkbox']"), function(index, value) {
                    //     $(value).prop("checked",false);
                    // });
                    // $(element).find("input[type='checkbox']").prop("checked","checked");
                },
				columns : [
					{
						checkbox : true
					},
					{
                        visible : false,
						field : 'employeeId', // 列字段名
						title : 'ID' // 列标题
					},
                    {
                        field : 'SerialNumber',
                        title : '序号',
                        align : 'center',
                        width : '30px',
                        formatter: function (value ,row ,index){
                            var pageNumber=$('#bTable').bootstrapTable("getOptions").pageNumber;
                            var pageSize=$('#bTable').bootstrapTable("getOptions").pageSize;
                            return (pageNumber-1)*pageSize+index+1;
                        }
                    },
                    {
                        field : 'loginName',
                        title : '账号名称',
                        sortable: true
                    },
                    {
                        field : 'realName',
                        title : '员工姓名',
                        sortable: true
                    },
                    {
                        field : 'employeeNumber',
                        title : '员工编号',
                        sortable: true
                    },
					{
						field : 'email',
						title : '邮箱',
                        sortable: true
					},
					{
						field : 'employeeState',
						title : '账号状态',
						align : 'center',
                        sortable: true,
						formatter : function(value, row, index) {
							if (value == '0') {
								return '<span class="label label-danger">禁用</span>';
							} else if (value == '1') {
								return '<span class="label label-primary">正常</span>';
							}
						}
					},
                    {
                        field : 'createTime',
                        title : '创建时间',
                        width : '150px',
                        sortable: true
                    },
                    {
                        field : 'modifyTime',
                        title : '修改时间',
                        width : '150px',
                        sortable: true
                    },
					{
						title : '操作',
						field : 'employeeNumber',
						align : 'center',
						formatter : function(value, row, index) {
							var f = '<a class="btn btn-success btn-sm ' + s_resetPwd_h + '" href="#" title="重置密码"  mce_href="#" onclick="resetPwd(\''
								+ row.employeeId
								+ '\')"><i class="fa fa-key"></i></a> ';
							return  f;
						}
					} ]
			});
}
function reLoad() {
	$('#bTable').bootstrapTable('refresh');
}
function view() {
    // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    var rows = $('#bTable').bootstrapTable('getSelections');
    var id;
    if (rows.length == 0||rows.length >1) {
        layer.msg("请选择一条数据");
        return;
    }else{
        id=rows[0]['employeeId'];
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
	// iframe层
	layer.open({
		type : 2,
		title : '增加员工',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : preUrl + '/add'
	});
}
function edit() {
    // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    var rows = $('#bTable').bootstrapTable('getSelections');
    var id;
    if (rows.length == 0||rows.length >1) {
        layer.msg("请选择一条数据");
        return;
    }else{
        id=rows[0]['employeeId'];
    }
	layer.open({
		type : 2,
		title : '员工修改',
		maxmin : true,
		shadeClose : false,
		area : [ '800px', '520px' ],
		content : preUrl + '/edit/' + id // iframe的url
	});
}

function unlock(){
    // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    var rows = $('#bTable').bootstrapTable('getSelections');
    var loginname;
    if (rows.length == 0||rows.length >1) {
        layer.msg("请选择一条数据");
        return;
    }else{
        loginname = rows[0]['loginName'];
    }
    $.ajax({
        type : 'POST',
        data : {
            "loginname" : loginname
        },
        url : preUrl + '/unlock',
        success : function(r) {
            if (r.code == 0) {
                layer.msg(r.msg);
                reLoad();
            } else {
                layer.msg(r.msg);
            }
        }
    });
}
function resetPwd(id) {
	layer.open({
		type : 2,
		title : '重置密码',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '400px', '400px' ],
		content : preUrl + '/resetPwd/' + id // iframe的url
	});
}
function batchDel() {
	var rows = $('#bTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['employeeId'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : preUrl + '/batchDel',
			success : function(r) {
				if (r.code == 0) {
					layer.msg(r.msg);
					reLoad();
				} else {
					layer.msg(r.msg);
				}
			}
		});
	}, function() {});
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
            },
            'check_callback':true
		},
		"plugins" : [ "search" ]
	});
	//$('#jstree').jstree().open_all();
}
$('#jstree').on("changed.jstree", function(e, data) {
	var organId = '';
    var deptmentId = '';
    var positionId = '';
    var opt = {
        query : {
            deptmentId : deptmentId,
            organId :organId,
            positionId : positionId
        }
    }
    if(data.selected != -1){
        if(data.node["icon"]=="fa fa-institution"){
            opt.query.deptmentId = data.node["id"];
        }else if(data.node["icon"]=="fa fa-users"){
            opt.query.positionId = data.node["id"];
        }
        else{
            opt.query.organId = data.node["id"];
        }
        $('#bTable').bootstrapTable('refresh', opt);
	}
});

$('#jstree').on("open_node.jstree", function(e, data){
    if(data.selected != -1){
        if(data.node["icon"]=="fa fa-institution"){
            var deptmentId = data.node["id"];

            var inst = data.instance;

            var selectedNode = inst.get_node(data.selected);

           // console.log(data.node.children.length);
            for(var j = 0;j<data.node.children.length;j++){

                var childrennode = inst.get_node(data.node.children[j]);

                // console.log(childrennode);
                if(childrennode["icon"]!="fa fa-institution"){

                    inst.delete_node(childrennode)
                }
            }
            loadPostion(deptmentId);
        }
    }
});
function loadPostion(deptmentId) {
    $.ajax({
        url: "/system/position/tree/"+deptmentId,
        dataType: "json",
        type: "get",
        success: function (data) {
           console.log(data);
            //console.log($.parseJSON(data));
            console.log(JSON.stringify(data));

            console.log(data.hasOwnProperty.length);
            if (data) {
                if(data.hasOwnProperty.length==1){
                    $('#jstree').jstree('create_node', deptmentId, data, 'last');
                }else{
                    $.each(data, function (i, item) {
                        $('#jstree').jstree('create_node', deptmentId, item, 'last');
                    });
                }
            }
        }
    });
}


$("#treeForm").submit(function(e) {
    e.preventDefault();
    $("#jstree").jstree(true).search($("#selectDept").val());
});