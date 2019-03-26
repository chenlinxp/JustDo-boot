
var prefix = "/system/notice"
$(function() {
	load();
});

function load() {
	$('#bTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/list", // 服务器数据的加载地址
				showRefresh : true,
				//	showToggle : true,
				//	showColumns : true,
				iconSize : 'outline',
				toolbar : '#bToolbar',
				striped : true, // 设置为true会有隔行变色效果
				dataType : "json", // 服务器返回的数据类型
				pagination : true, // 设置为true会在底部显示分页条
				// queryParamsType : "limit",
				// //设置为limit则会发送符合RESTFull格式的参数
				singleSelect : false, // 设置为true将禁止多选
				// contentType : "application/x-www-form-urlencoded",
				// //发送到服务器的数据编码类型
				pageSize : 25, // 如果设置了分页，每页数据条数
				pageNumber : 1, // 如果设置了分布，首页页码
				//search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
				queryParams : function(params) {
					return {
						//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset
					// name:$('#searchName').val(),
					// username:$('#searchName').val()
					};
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
						visible :false,
						field : 'id',
						title : '编号'
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
						field : 'title',
						title : '标题',
						formatter: function(value,row,index){
							return '<a href="#" onclick="edit(\''+row.id+'\')">'+row.title+'</a>';
						}
					},
					{
						field : 'type',
						title : '类型'
					},
					{
						visible : false,
						field : 'content',
						title : '内容'
					},
					{
						visible : false,
						field : 'files',
						title : '附件'
					},
					{
						field : 'status',
						title : '状态',
						align :'center',
						formatter : function(value, row, index){
							if(value==0){
								return '<span class="label label-danger">草稿</span>';
							}else if(value==1){
								return '<span class="label label-primary">发布</span>';
							}
						}
					},
					{
						visible : false,
						field : 'createBy',
						title : '创建者'
					},
					{
						visible : false,
						field : 'createDate',
						title : '创建时间'
					},
					{
						visible : false,
						field : 'updateBy',
						title : '更新者'
					},
					{
						visible : false,
						field : 'updateDate',
						title : '更新时间'
					},
					{
						field : 'remarks',
						title : '备注信息'
					},
					{
						visible : false,
						field : 'delFlag',
						title : '删除标记'
					},
					{
						title : '操作',
						field : 'operation',
						align : 'center',
						formatter : function(value, row, index) {
							var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
								+ row.id
								+ '\')"><i class="fa fa-edit"></i></a> ';
							var d = '<a class="btn btn-warning btn-sm ' + s_delete_h + '" href="#" title="删除"  mce_href="#" onclick="remove(\''
								+ row.id
								+ '\')"><i class="fa fa-remove"></i></a> ';
							return e + d;
						}
					} ]
			});
}
function reLoad() {
	$('#bTable').bootstrapTable('refresh');
}
function add() {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add' // iframe的url
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
			ids[i] = row['id'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : prefix + '/batchDel',
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