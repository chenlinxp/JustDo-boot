var prefix = "/activiti/task"
$(function() {
	load();
});

function load() {
	$('#bTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/todoList", // 服务器数据的加载地址
				// showRefresh : true,
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
						name : $('#searchName').val(),
					};
				},
				// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
				// queryParamsType = 'limit' ,返回参数必须包含
				// limit, offset, search, sort, order 否则, 需要包含:
				// pageSize, pageNumber, searchText, sortName,
				// sortOrder.
				// 返回false将会终止请求
				columns : [
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
                        field : 'id', // 列字段名
                        title : '任务' // 列标题
                    },
                    {
                        field : 'key', // 列字段名
                        title : '任务key' // 列标题
                    },
                    {
                        field : 'processId', // 列字段名
                        title : '流程编号' // 列标题
                    },
                    {
                        field : 'processDefinitionId', // 列字段名
                        title : '流程定义编号' // 列标题
                    },
                    {
                        field : 'name',
                        title : '任务名称'
                    },
                    {
                        field : 'executionId',
                        title : '跟踪',
						formatter:function(value,row,index){
                        	return '<a href="/activiti/task/trace/photo/'+row.processDefinitionId+'/'+row.executionId+'">跟踪</a>';
						}
                    },
					{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(value, row, index) {

							var f = '<a class="btn btn-primary btn-sm " href="#" title="签收任务"  mce_href="#" onclick="form(\''
								+ row.processDefinitionId+'\',\''+row.id
								+ '\')">审批<i class="fa fa-key"></i></a> ';
							return f;
						}
					} ]
			});
}
function reLoad() {
	$('#bTable').bootstrapTable('refresh');
}
function form(proId,id) {
    layer.open({
        type : 2,
        title : '发起流程',
        maxmin : true,
        shadeClose : false,
        area : [ '100%', '100%' ],
        content : prefix + '/form/'+ proId+'/'+id
    })
}
