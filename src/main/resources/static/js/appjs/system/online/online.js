var preUrl = "/system/online"
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
						// showToggle : true,
						// showColumns : true,
						iconSize : 'outline',
						toolbar : '#bToolbar',
						striped : true, // 设置为true会有隔行变色效果
						dataType : "json", // 服务器返回的数据类型
						pagination : true, // 设置为true会在底部显示分页条
						// queryParamsType : "limit",
						// //设置为limit则会发送符合RESTFull格式的参数
						singleSelect : true, // 设置为true将禁止多选
						// contentType : "application/x-www-form-urlencoded",
						// //发送到服务器的数据编码类型
						pageSize : 25, // 如果设置了分页，每页数据条数
						pageNumber : 1, // 如果设置了分布，首页页码
						// search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "client", // 设置在哪里进行分页，可选值为"client" 或者
						// "server"
						queryParams : function(params) {
							return {
								// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit : params.limit,
								offset : params.offset,
                                sort: 'START_TIME',
								name : $('#searchName').val()
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
									visible:false,
									field : 'id', // 列字段名
									title : '序号' // 列标题
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
									title : '用户名'
								},
								{
									field : 'status',
									title : '状态',
									align : 'center',
									width : '50px',
									formatter : function(value, row, index) {
										if (value == 'on_line') {
											return '<span class="label label-success">在线</span>';
										} else if (value == 'off_line') {
											return '<span class="label label-primary">离线</span>';
										}
									}
								},
								{
									field : 'hostIP',
									title : '主机IP'
								},
								{
									field : 'hostMAC',
									title : '主机MAC'
								},
								{
									field : 'startTime',
									title : '登录时间'
								},
								{
									field : 'lastTime',
									title : '最后访问时间'
								},
								{
									field : 'timeout',
									title : '过期时间'
								} ]
					});
}
function reLoad() {
	$('#bTable').bootstrapTable('refresh');
}
function forceLogout() {
	var rows = $('#bTable').bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	if (rows.length == 0) {
		layer.msg("请选择一条数据");
		return;
	}
	layer.confirm("确定要强制下线选中的'" + rows.length + "'用户吗?", {
		btn : [ '确定', '取消' ]
	// 按钮
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['id'];
		});
        $.ajax({
            url : preUrl+"/forceLogout/",
            type : "post",
            data : {
                'ids' : ids
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
	}, function() {
	});
}