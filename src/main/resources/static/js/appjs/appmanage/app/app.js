
var preUrl = "/appmanage/app"
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
						pageSize : 20, // 如果设置了分页，每页数据条数
                        pageList: [10,20,50,100],
						pageNumber : 1, // 如果设置了分布，首页页码
						//search : true, // 是否显示搜索框
                        sortable: true,
						showColumns : false, // 是否显示内容下拉框（选择显示的列）
						sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
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
                            view(row["appId"]);
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
									checkbox : true
								},
								{
									field : 'appId',
									title : '主键ID',
									visible :false
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
									field : 'appName',
									title : '显示名称'
								},
								{
									field : 'iconUrl',
									title : 'LOGO'
								},
								{
									field : 'appKey',
									title : 'APP-KEY'
								},
								{
									field : 'bundleName',
									title : '项目名称'
								},
								{
									field : 'bundleId',
									title : '包名ID'
								},
								{
									field : 'shortUrl',
									title : '短链接'
								},
								{
									field : 'expirerTime',
									title : '过期时间'
								},
								{
									field : 'loadUrl', 
									title : '下载地址' 
								},
								{
									field : 'combineAppId', 
									title : '合并的APP_ID',
                                    visible :false
								},
								{
									field : 'combineTime', 
									title : '合并的时间' ,
                                    visible :false
								},
								{
									field : 'codeQrA', 
									title : '二维码图片路径A(8cm)' ,
                                    visible :false
								},
								{
									field : 'codeQrB', 
									title : '二维码图片路径B(12cm)',
                                    visible :false

								},
								{
									field : 'codeQrC', 
									title : '二维码图片路径C(15cm)' ,
                                    visible :false
								},
								{
									field : 'appType', 
									title : '类型',
                                    align : 'center',
                                    valign : 'center',
                                    formatter : function(item, index) {
                                        if (item.appType == '001') {
                                            return '<span class="label label-primary">苹果</span>';
                                        } else if (item.appType == '002') {
                                            return '<span class="label label-primary">安卓</span>';
                                        }
                                    }
								},
								{
									field : 'isCombine', 
									title : '是否合并' 
								},
								{
									field : 'description', 
									title : 'APP介绍' 
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
        id=rows[0]['appId'];
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
    // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    var rows = $('#bTable').bootstrapTable('getSelections');
    var id;
    if (rows.length == 0||rows.length >1) {
        layer.msg("请选择一条数据");
        return;
    }else{
        id=rows[0]['appId'];
    }
	layer.open({
		type : 2,
		title : '编辑',
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
		$.ajax({
			url : preUrl+"/del",
			type : "post",
			data : {
				'appId' : id
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
    // 返回所有选择的行，当没有选择的记录时，返回一个空数组
	var rows = $('#bTable').bootstrapTable('getSelections');
	if (rows.length == 0) {
		layer.msg("请选择要删除的数据");
		return;
	}
	layer.confirm("确认要删除选中的'" + rows.length + "'条数据吗?", {
		btn : [ '确定', '取消' ]
	}, function() {
		var ids = new Array();
		// 遍历所有选择的行数据，取每条数据对应的ID
		$.each(rows, function(i, row) {
			ids[i] = row['appId'];
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
	}, function() {

	});
}
