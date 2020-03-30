
var preUrl = "/appmanage/app";

var preUrl2 = "/appmanage/appversion"
$(function() {
	load();
});

function load() {
	$('#bTable').bootstrapTable(
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
                        editable:true,//开启编辑模式
                        detailView:true,
						queryParams : function(params) {
							return {
								//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
								limit: params.limit,
								offset:params.offset,
                                sort: 'CREATE_TIME',
                                order: 'desc',
                                appName : $('#searchName').val(),
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
                                    field : 'appId',
                                    title : '主键ID',
                                    visible :false
                                },
								{
									field : 'appName',
									title : 'APP名称',
                                    width : '60px'
								},
                                {
                                    field : 'appType',
                                    title : 'APP类型',
                                    align : 'center',
                                    valign : 'center',
                                    width : '40px',
                                    formatter : function(value, row, index) {
                                        if (value == '1') {
                                            return '<span class="label label-warning" style="font-size: larger">安卓</span>';
                                        } else if (value == '2') {
                                            return '<span class="label label-primary" style="font-size: larger">苹果</span>';
                                        }else{
                                            return '<span class="label label-white"  style="font-size: larger">未知</span>';
                                        }
                                    }
                                },
								{
									field : 'iconUrl',
									title : 'LOGO',
                                    width : '60px',
                                    formatter : function(value, row, index) {
                                            return '<img src="'+value+'" height="46px" width="46px"/>';
                                    }
								},
								{
									field : 'appKey',
									title : 'APP-KEY',
                                    visible :false
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
									title : '过期时间',
                                    width : '140px'
								},
								{
									field : 'loadUrl', 
									title : '下载地址',
                                    visible :false
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
									title : '二维码' ,
                                    visible :true,
                                    formatter : function(value, row, index) {
                                        return '<img src="'+value+'" height="50px" width="50px"/>';
                                    }
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
									field : 'isCombine', 
									title : '是否绑定',
                                    align : 'center',
                                    valign : 'center',
                                    width : '40px',
                                    formatter : function(value, row, index) {
                                        if (value == '1') {
                                            return '<span class="label label-primary" style="font-size: larger">是</span>';
                                        } else  {
                                            return '<span class="label label-danger" style="font-size: larger">否</span>';
                                        }
                                    }
								},
								{
									field : 'description', 
									title : 'APP介绍',
                                    visible:false
								},
								{
									field : 'createTime',
									title : '创建时间',
                                    width : '140px'
								},
								{
									field : 'modifyTime',
									title : '修改时间',
                                    width : '140px',
                                    visible:false
								}],
                        onExpandRow : function (index, row, $detail) {
                            // console.log(index);
                            // console.log(row);
                            onclick = row.appId;
                            var parentId = row.appId;
                            var APPVERSIONTable = $detail.html('<table id="child_table"></table>').find('table');
                            $(APPVERSIONTable).bootstrapTable({
                                method : 'get', // 服务器数据的请求方式 get or post
                                url : "/appmanage/appversion/list", // 服务器数据的加载地址
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
                                        appId:parentId,
                                    };
                                },
                                onDblClickRow: function (row, element) {
                                    view(row["appVersionId"]);
                                },
                                onClickRow: function (row, element) {
                                    $('.success').removeClass('success');//去除之前选中的行的，选中样式
                                    $(element).addClass('success');//添加当前选中的 success样式用于区别
                                    $(APPVERSIONTable).bootstrapTable("uncheckAll");
                                    var rowindex=$(element).attr("data-index");
                                    $(APPVERSIONTable).bootstrapTable('check',rowindex);
                                },
                                // //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
                                // queryParamsType = 'limit' ,返回参数必须包含
                                // limit, offset, search, sort, order 否则, 需要包含:
                                // pageSize, pageNumber, searchText, sortName,
                                // sortOrder.
                                // 返回false将会终止请求
                                columns : [
                                    // {
                                    //     checkbox : true
                                    // },
                                    {
                                        field : 'appVersionId',
                                        title : '主键ID',
                                        visible :false
                                    },
                                    {
                                        field : 'SerialNumber',
                                        title : '序号',
                                        align : 'center',
                                        width : '30px',
                                        formatter: function (value ,row ,index){
                                            // var pageNumber=$('#bTable').bootstrapTable("getOptions").pageNumber;
                                            // var pageSize=$('#bTable').bootstrapTable("getOptions").pageSize;
                                            // return (pageNumber-1)*pageSize+index+1;
                                            return index+1;
                                        }
                                    },
                                    {
                                        field : 'appId',
                                        title : 'APP-ID',
                                        visible:false
                                    },
                                    {
                                        field : 'delFlag',
                                        title : '删除标识',
										visible:false
                                    },
                                    {
                                        field : 'versionCode',
                                        title : '版本号',
                                        align : 'center',
                                        width : '40px',
                                    },
                                    {
                                        field : 'buildCode',
                                        title : '构建号',
                                        align : 'center',
                                        width : '30px',
                                    },
                                    {
                                        field : 'appSizes',
                                        title : 'APP大小',
                                        align : 'center',
                                        width : '30px',
                                        formatter: function (value ,row ,index){
                                            return row.appSizes+"MB";
                                        }
                                    },
                                    {
                                        field : 'totalLoadNumber',
                                        title : '总下载数',
                                        align : 'center',
                                        width : '40px',
                                    },
                                    {
                                        field : 'todayLoadNumber',
                                        title : '今天下载数',
                                        align : 'center',
                                        width : '40px',
                                    },
                                    {
                                        field : 'versionDescription',
                                        title : '版本描述',
                                        visible:true
                                    },
                                    {
                                        field : 'updateDescription',
                                        title : '更新描述',
                                        visible:true
                                    },
                                    {
                                        field : 'displayState',
                                        title : '显示状态',
                                        align : 'center',
                                        width : '50px',
                                        valign : 'center',
                                        formatter : function(value, row, index) {
                                            if (value == '1') {
                                                return '<span class="label label-primary" style="font-size: larger">是</span>';
                                            } else if (value == '0') {
                                                return '<span class="label label-danger" style="font-size: larger">否</span>';
                                            }
                                        }
                                    },
                                    {
                                        field : 'createTime',
                                        title : '创建时间',
                                        width : '140px'
                                    },
                                    {
                                        title : '操作',
                                        field : 'operation',
                                        align : 'center',
                                        width : '170px',
                                        formatter : function(value, row, index) {

                                            var a = '<a class="btn btn-sm '+s_delete_h+'" href="#" title="下载"  mce_href="#" onclick="download(\'' + row.appVersionId+'\')"><i class="fa fa-download"></i></a> ';
                                            var b ='';
                                            if(row.displayState=="1"){
                                                 b = '<a class="btn  btn-sm '+s_delete_h+'" href="#" title="隐藏"  mce_href="#" onclick="hide(\'' + row.appVersionId+'\',1)"><i class="fa fa-eye-slash"></i></a> ';
                                            }else{
                                                 b = '<a class="btn btn-sm '+s_delete_h+'" href="#" title="显示"  mce_href="#" onclick="hide(\'' + row.appVersionId+'\',0)"><i class="fa fa-eye"></i></a> ';
                                            }
                                            var c = '<a class="btn btn-sm '+s_delete_h+'" href="#" title="删除"  mce_href="#" onclick="del(\'' + row.appVersionId+'\')"><i class="fa fa-remove"></i></a> ';

                                            var d = '<a class="btn btn-sm" href="#" title="二维码"  mce_href="#">' +'<i class="fa fa-qrcode"></i></a> ';
                                            return d + a + b + c ;
                                        }
                                    } ]
                            });
                        }
					});
}
// /*fa-low-vision*/   '<div class="divclass"> <img src="'+row.codeQr+'"></div>'
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

function upload() {
    layer.open({
        type : 2,
        title : '上传',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '500px', '520px' ],
        content : preUrl + '/upload' // iframe的url
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
function del(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : preUrl2+"/del",
			type : "post",
			data : {
				'appVersionId' : id
			},
			success : function(r) {
				if (r.code==0) {
					layer.msg(r.msg);
					//reLoad();
                    $("#child_table").bootstrapTable('refresh');
				}else{
					layer.msg(r.msg);
				}
			}
		});
	})
}
function hide(id,state) {
    var msg = '确定要显示选中的记录？';
    if(state == 1){
        msg = '确定要隐藏选中的记录？';
    }
    layer.confirm(msg, {
        btn : [ '确定', '取消' ]
    }, function() {
        $.ajax({
            url : preUrl2+"/hidden",
            type : "post",
            data : {
                'appVersionId' : id
            },
            success : function(r) {
                if (r.code==0) {
                    layer.msg(r.msg);
                    $("#child_table").bootstrapTable('refresh');
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
