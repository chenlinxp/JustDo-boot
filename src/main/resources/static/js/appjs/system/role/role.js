var preUrl = "/system/role";
$(function() {
	load();　
});

function load() {
	$('#bTable')
			.bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : preUrl + "/list", // 服务器数据的加载地址
                        showRefresh : true,
						striped : true, // 设置为true会有隔行变色效果
						dataType : "json", // 服务器返回的数据类型
						pagination : true, // 设置为true会在底部显示分页条
						// queryParamsType : "limit",
						// //设置为limit则会发送符合RESTFull格式的参数
						singleSelect : false, // 设置为true将禁止多选
						iconSize : 'outline',
						toolbar : '#bToolbar',
						// contentType : "application/x-www-form-urlencoded",
						// //发送到服务器的数据编码类型
						pageSize : 25, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "client", // 设置在哪里进行分页，可选值为"client" 或者
						// "server"
						// queryParams : queryParams,
						// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
						// queryParamsType = 'limit' ,返回参数必须包含
						// limit, offset, search, sort, order 否则, 需要包含:
						// pageSize, pageNumber, searchText, sortName,
						// sortOrder.
						// 返回false将会终止请求
                        onDblClickRow: function (row, element) {
                            view(row["roleId"]);
                        },
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
									visible:false,
									field : 'roleId', // 列字段名
									title : '主键ID' // 列标题
								},
								{
									field : 'SerialNumber',
									title : '序号',
									align : 'center',
									width : '50px',
									formatter: function (value ,row ,index){
										var pageNumber=$('#bTable').bootstrapTable("getOptions").pageNumber;
										var pageSize=$('#bTable').bootstrapTable("getOptions").pageSize;
										return (pageNumber-1)*pageSize+index+1;
									}
								},
								{
									field : 'roleName',
									title : '角色名称'
								},
								{
									field : 'roleSign',
									title : '角色标识',
                                    width : '100px'
								},
								{
									field : 'roleState',
									title : '角色状态',
                                    width : '50px',
                                    formatter : function(value, row, index) {
                                        if (value == '1') {
                                            return '<span class="label label-primary">正常</span>';
                                        } else  {
                                            return '<span class="label label-danger">禁用</span>';
                                        }
                                    }
								},
								{
									field : 'remark',
									title : '备注'
								},
								{
                                    visible:false,
									field : 'createEmployeeId',
									title : '创建员工'
								},
								{
									field : 'createTime',
									title : '创建时间',
                                    width : '150px'
								},
								{
									field : 'modifyTime',
									title : '创建时间',
                                    width : '150px'
								}]
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
        id=rows[0]['roleId'];
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
		title : '添加角色',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : preUrl + '/add' // iframe的url
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
        id=rows[0]['roleId'];
    }
	layer.open({
		type : 2,
		title : '角色修改',
		maxmin : true,
		shadeClose : true, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : preUrl + '/edit/' + id // iframe的url
	});
}
function batchDel() {
    // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	var rows = $('#bTable').bootstrapTable('getSelections');
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?",
		{ btn : [ '确定', '取消' ]},
		function() {
			var ids = new Array();
			$.each(rows, function(i, row) {
				ids[i] = row['roleId'];
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
	    },
		function() {});
}