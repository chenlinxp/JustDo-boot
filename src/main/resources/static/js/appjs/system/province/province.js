
var prefix = "/system/province"
$(function() {
	load();
});

function load() {
	$('#bTable').bootstrapTable(
					{
						method : 'get', // 服务器数据的请求方式 get or post
						url : "/system/province/list", // 服务器数据的加载地址
					//	showRefresh : true,
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
						contentType : "application/x-www-form-urlencoded",
						// //发送到服务器的数据编码类型
						pageSize : 20, // 如果设置了分页，每页数据条数
                        pageList: [10,20,50,100],
						pageNumber : 1, // 如果设置了分布，首页页码
						//search : true, // 是否显示搜索框
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                        detailView:true,
                       // smartDisplay:true,
                        editable:true,//开启编辑模式
                        queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								offset:params.offset
					           // name:$('#searchName').val(),
					           // username:$('#searchName').val()
							};
						},
                        onDblClickRow: function (row, element) {
                            view(row["provinceid"]);
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
						// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
						// queryParamsType = 'limit' ,返回参数必须包含
						// limit, offset, search, sort, order 否则, 需要包含:
						// pageSize, pageNumber, searchText, sortName,
						// sortOrder.
						// 返回false将会终止请求
						columns : [
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
									field : 'provinceid', 
									title : '主键' ,
									visible : false
								},
								{
									field : 'provincecode', 
									title : '编号' ,
                                    width : '60px',
                                    editable: {
                                        type: 'text',
                                        title: '编号',
                                        validate: function (e) {
                                            if (!e) return '编号不能为空';

                                        }
                                    }
								},
								{
									field : 'provincename', 
									title : '名称' ,
                                    width : '120px'
								},
								{
									title: '操作',
									field: 'operation',
									align: 'center',
									width: '120px',
									formatter: function (value, row, index) {
										var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
											+ row.provinceid
											+ '\')"><i class="fa fa-edit"></i></a> ';
										var d = '<a class="btn btn-warning btn-sm ' + s_delete_h + '" href="#" title="删除"  mce_href="#" onclick="del(\''
											+ row.provinceid
											+ '\')"><i class="fa fa-remove"></i></a> ';
										return e + d;
									}
								 },
                                 {
                                field : 'kongbai',
                                title : ''
                                 }],
                        onExpandRow : function (index, row, $detail) {
                            console.log(index);
                            console.log(row);
                            onclick = row.provinceid;
                            var parentId = row.provincecode;
                            var CITYTable = $detail.html('<table></table>').find('table');
                            $(CITYTable).bootstrapTable({
                                method : 'get', // 服务器数据的请求方式 get or post
                                url : "/system/city/list", // 服务器数据的加载地址
                                showRefresh : true,
                                showColumns : true,
                                iconSize : 'outline',
                                toolbar : '#cbToolbar',
                                striped : true, // 设置为true会有隔行变色效果
                                dataType : "json", // 服务器返回的数据类型
                                pagination : false, // 设置为true会在底部显示分页条
                                // queryParamsType : "limit",
                                // //设置为limit则会发送符合RESTFull格式的参数
                                singleSelect : false, // 设置为true将禁止多选
                                // contentType : "application/x-www-form-urlencoded",
                                // //发送到服务器的数据编码类型
                                //search : true, // 是否显示搜索框
                                showColumns : false, // 是否显示内容下拉框（选择显示的列）
                                queryParams : function(params) {
                                    return {
                                        provincecode:parentId,
                                    };
                                },
                                onDblClickRow: function (row, element) {
                                    view(row["cityid"]);
                                },
                                onClickRow: function (row, element) {
                                    $('.success').removeClass('success');//去除之前选中的行的，选中样式
                                    $(element).addClass('success');//添加当前选中的 success样式用于区别
                                    $(CITYTable).bootstrapTable("uncheckAll");
                                    var rowindex=$(element).attr("data-index");
                                    $(CITYTable).bootstrapTable('check',rowindex);
                                },
                                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                                // queryParamsType = 'limit' ,返回参数必须包含
                                // limit, offset, search, sort, order 否则, 需要包含:
                                // pageSize, pageNumber, searchText, sortName,
                                // sortOrder.
                                // 返回false将会终止请求
                                columns : [
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
                                        field : 'cityid',
                                        title : '主键' ,
                                        visible : false
                                    },
                                    {
                                        field : 'citycode',
                                        title : '编号',
										width : '60px'
                                    },
                                    {
                                        field : 'cityname',
                                        title : '名字',
										width : '120px'
                                    },
                                    {
                                        field : 'provincecode',
                                        title : '省编号',
							            visible : false
                                    },
                                    {
                                        field : 'countycode',
                                        title : '区号',
                                        width : '60px',
                                    },
                                    {
                                        field : 'zipcode',
                                        title : '邮编',
                                        width : '60px'
                                    },
                                    {
                                        title : '操作',
                                        field : 'operation',
                                        align : 'center',
                                        width : '120px',
                                        formatter : function(value, row, index) {
                                            var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="edit(\''
                                                + row.cityid
                                                + '\')"><i class="fa fa-edit"></i></a> ';
                                            var d = '<a class="btn btn-warning btn-sm '+s_delete_h+'" href="#" title="删除"  mce_href="#" onclick="del(\''
                                                + row.cityid
                                                + '\')"><i class="fa fa-remove"></i></a> ';
                                            return e + d ;
                                        }
                                    },
                                    {
                                        field : 'kongbai',
                                        title : ''
                                    }]
                            });
                        }
					});
}
function reLoad() {
	$('#bTable').bootstrapTable('refresh');
}
function view(id) {
    layer.open({
        type : 2,
        title : '查看',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : prefix + '/view/'+id // iframe的url
    });
}
function add() {

	// layer.open({
	// 	type : 2,
	// 	title : '增加',
	// 	maxmin : true,
	// 	shadeClose : false, // 点击遮罩关闭层
	// 	area : [ '800px', '520px' ],
	// 	content : prefix + '/add' // iframe的url
	// });
       // $('#bTable').bootstrapTable('selectPage', 1); //Jump to the first page
        var data = {SerialNumber:'',provinceid: '', provincecode: '',provincename:'',operation:''}; //define a new row data，certainly it's empty
        $('#bTable').bootstrapTable('prepend', data); //the method of prepend must defined all fields，but append needn't
        //$("#bTable input")[0].focus();

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
function del(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix+"/del",
			type : "post",
			data : {
				'provinceid' : id
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
			ids[i] = row['provinceid'];
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
	}, function() {

	});
}