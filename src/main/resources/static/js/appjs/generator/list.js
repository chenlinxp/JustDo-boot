var preUrl = "/system/generator"
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
						showToggle : true,
						//showColumns : true,
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
						pageSize : 20, // 如果设置了分页，每页数据条数
                        pageList: [10,20,50,100],
						pageNumber : 1, // 如果设置了分布，首页页码
						search : true, // 是否显示搜索框
						showColumns : true, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "client", // 设置在哪里进行分页，可选值为"client" 或者
						// "server"
						// queryParams : queryParams,
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
                        },
                        icons: {
                            refresh: 'glyphicon-repeat',
                            toggle: 'glyphicon-list-alt',
                            columns: 'glyphicon-list'
                        },
						columns :[
							    {
									checkbox : true
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
									field : 'tableName', // 列字段名
									title : '表名称' // 列标题
								},
								{
									field : 'engine',
									title : 'engine'
								},
								{
									field : 'tableComment',
									title : '表描述'
								},
								{
									field : 'createTime',
									title : '创建时间',
                                    width : '150px'
								} ]
					});
}
function reLoad() {
	$('#bTable').bootstrapTable('refresh');
}
function generateCode(tableName) {
	location.href = preUrl + "/code/" + tableName;
}
function batchCode() {
    // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	var rows = $('#bTable').bootstrapTable('getSelections');
	if (rows.length == 0) {
		layer.msg("请选择要生成代码的表");
		return;
	}
	var tables = new Array();
	// 遍历所有选择的行数据，取每条数据对应的ID
	$.each(rows, function(i, row) {
		tables[i] = row['tableName'];
	});
	location.href = preUrl + "/batchCode?tables=" + JSON.stringify(tables);
}

function edit(){
    layer.open({
		type : 2,
		title : '编辑策略',
		maxmin : true,
		shadeClose : false, 
		area : [ '900px', '520px' ],
		content : preUrl + '/edit'
	});
}
function code(){
    // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    var rows = $('#bTable').bootstrapTable('getSelections');
    var tablename;
    if (rows.length == 0||rows.length >1) {
        layer.msg("请选择一条数据");
        return;
    }else{
        tablename=rows[0]['tableName'];
    }
    var addPage =layer.open({
        type : 2,
        title : '单表生成设置',
        maxmin : true,
        shadeClose : false,
        area : [ '800px', '520px' ],
        content : preUrl + '/settingCode/'+tablename
    });
    layer.full(addPage);
}