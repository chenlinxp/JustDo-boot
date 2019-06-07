var preUrl3= "/activiti/task"
$(function() {
	load3();
});

function load3() {
	$('#bTable3')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : preUrl3 + "/gotoList", // 服务器数据的加载地址
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
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
				// "server"
				queryParams : function(params) {
					return {
						// 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
						name : $('#searchName').val(),
					};
				},
                onClickRow: function (row, element) {
                    $('.success').removeClass('success');//去除之前选中的行的，选中样式
                    $(element).addClass('success');//添加当前选中的 success样式用于区别
                    $("#bTable3").bootstrapTable("uncheckAll");
                    var rowindex=$(element).attr("data-index");
                    $("#bTable3").bootstrapTable('check',rowindex);
                    // $.each($("#bTable  input[type='checkbox']"), function(index, value) {
                    //     $(value).prop("checked",false);
                    // });
                    // $(element).find("input[type='checkbox']").prop("checked","checked");
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
                            var pageNumber=$('#bTable3').bootstrapTable("getOptions").pageNumber;
                            var pageSize=$('#bTable3').bootstrapTable("getOptions").pageSize;
                            return (pageNumber-1)*pageSize+index+1;
                        }
                    },
                    {
                        field : 'id', // 列字段名
                        title : '编号' // 列标题
                    },

                    {
                        field : 'name',
                        title : '流程名称'
                    },
                    {
                        field : 'id',
                        title : '流程图',
                        formatter:function (value,row,index) {
                            var e = '<a   href="/activiti/process/resource/read/image/'+row.id+'"  title="图片" target="_blank">图片</a> ';
                            return e;
                        }
                    },
					{
						title : '操作',
						field : 'id',
						align : 'center',
						formatter : function(value, row, index) {
							var f = '<a class="btn btn-success btn-sm ' + s_startTask_h + '" href="#" title="发起任务"  mce_href="#" onclick="startTask(\''
								+ row.id
								+ '\')">发起<i class="fa  fa-paper-plane"></i></a> ';
							return f;
						}
					} ]
			});
}

function startTask(id) {
    layer.open({
        type : 2,
        title : '发起流程',
        maxmin : true,
        shadeClose : false,
        area : [ '100%', '100%' ],
        content : preUrl3 +'/start/'+id
    })
}

