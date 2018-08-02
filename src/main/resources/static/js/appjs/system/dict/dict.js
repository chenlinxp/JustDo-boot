
var prefix = "/system/dict"
$(function() {
	
	//	var config = {
	//		'.chosen-select' : {},
	//		'.chosen-select-deselect' : {
	//			allow_single_deselect : true
	//		},
	//		'.chosen-select-no-single' : {
	//			disable_search_threshold : 10
	//		},
	//		'.chosen-select-no-results' : {
	//			no_results_text : '没有数据'
	//		},
	//		'.chosen-select-width' : {
	//			width : "95%"
	//		}
	//	}
	//	for (var selector in config) {
	//		$(selector).chosen(config[selector]);
	//	}
	load();
    // $("#bTable").on("click-row.bs.table",function (e,row,$element) {
    //     //alert(row["id"]);
    //     //alert($element.data);
    //     view(row["id"]);
    // })

    //点击选中列，判断checkbox选中与取消选中
    // $($(bTable).on).on('click-row.bs.table', function (e,field,value, row, $element){

    //     if(field=="chkbox"||field=="id"){//根据某个字段名判断
    //         if($("chkbox").is(':checked')) {
    //             $("chkbox").prop("checked",false);
    //         }else{
    //             $("chkbox").prop("checked",true);
    //         }
    //     }
    // });
});
function selectLoad() {
	var html = "";
	$.ajax({
		url : '/system/dict/type',
		success : function(data) {
			//加载数据
			for (var i = 0; i < data.length; i++) {
				html += '<option value="' + data[i].type + '">' + data[i].description + '</option>'
			}
			$(".chosen-select").append(html);
			$(".chosen-select").chosen({
				maxHeight : 200
			});
			//点击事件
			$('.chosen-select').on('change', function(e, params) {
				console.log(params.selected);
				var opt = {
					query : {
						type : params.selected,
					}
				}
				$('#bTable').bootstrapTable('refresh', opt);
			});
		}
	});
}

function dictTypeLoad() {
    var html = "";
    var dname = "";
    $.ajax({
        url : '/system/dict/dicttype',
		data : {  "dname" : dname },
        success : function(data) {
            //加载数据
            html ='<ul class="folder-list" style="padding: 0">';
            for (var i = 0; i < data.length; i++) {
                html += '<li value="' + data[i].did + '"><a href="javascript:void(0)"><i class="fa fa-folder"></i>'+ data[i].dname+ '</a></li>'
            }
            html += '</ul>';
            $("#dicttype").append(html);
            // $(".chosen-select").chosen({
            //     maxHeight : 200
            // });
            //点击事件
            $('#dicttype li').on('click', function(e, params) {
                console.log(params.value);
                var opt = {
                    query : {
                        did : params.value,
                    }
                }
                $('#bTable').bootstrapTable('refresh', opt);
            });
        }
    });
}
function load() {
	selectLoad();
   dictTypeLoad();
	$('#bTable')
		.bootstrapTable(
			{
				method : 'get', // 服务器数据的请求方式 get or post
				url : prefix + "/list", // 服务器数据的加载地址
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
				// contentType : "application/x-www-form-urlencoded",
				// //发送到服务器的数据编码类型
				pageSize : 10, // 如果设置了分页，每页数据条数
				pageNumber : 1, // 如果设置了分布，首页页码
				//search : true, // 是否显示搜索框
				showColumns : false, // 是否显示内容下拉框（选择显示的列）
				sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
				queryParams : function(params) {
					return {
						//说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
						limit : params.limit,
						offset : params.offset,
						// name:$('#searchName').val(),
						type : $('#searchName').val(),
					};
				},
				// //请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
				// queryParamsType = 'limit' ,返回参数必须包含
				// limit, offset, search, sort, order 否则, 需要包含:
				// pageSize, pageNumber, searchText, sortName,
				// sortOrder.e, row, element
				// 返回false将会终止请求
                onDblClickRow: function (row, element) {
                    view(row["id"]);
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
                        field : 'chkbox',
						checkbox : true
					},
                    {
                        field : 'id',
                        visible : false,
                        title : 'id'
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
						field : 'name',
						title : '标签名'
					},
					{
						field : 'value',
						title : '数据值',
						width : '100px'
					},
					{
						field : 'type',
						title : '类型'
					},
					{
						field : 'description',
						title : '描述'
					},
					{
						visible : false,
						field : 'sort',
						title : '排序（升序）'
					},
					{
						visible : false,
						field : 'parentId',
						title : '父级编号'
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
						visible : false,
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
						width : '150px',
						formatter : function(value, row, index) {
							var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
								+ row.id
								+ '\')"><i class="fa fa-edit"></i></a> ';
							var d = '<a class="btn btn-warning btn-sm ' + s_delete_h + '" href="#" title="删除"  mce_href="#" onclick="del(\''
								+ row.id
								+ '\')"><i class="fa fa-remove"></i></a> ';
							var f = '<a class="btn btn-success btn-sm ' + s_add_h + '" href="#" title="增加"  mce_href="#" onclick="addD(\''
								+ row.type +'\',\''+row.description
								+ '\')"><i class="fa fa-plus"></i></a> ';
							return e + d +f;
						}
					} ]
			});
}
function reLoad() {
	var opt = {
		query : {
			type : $('.chosen-select').val(),
		}
	}
	$('#bTable').bootstrapTable('refresh', opt);
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
function del(id) {
	layer.confirm('确定要删除选中的记录？', {
		btn : [ '确定', '取消' ]
	}, function() {
		$.ajax({
			url : prefix + "/del",
			type : "post",
			data : {
				'id' : id
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
	})
}

function addD(type,description) {
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : prefix + '/add/'+type+'/'+description // iframe的url
	});
}
function batchDel() {
	var table=$('#bTable');
	var rows = table.bootstrapTable('getSelections'); // 返回所有选择的行，当没有选择的记录时，返回一个空数组
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


function addDict() {
    layer.open({
        type : 2,
        title : '增加字典类型',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '500px', '400px' ],
        content : '/system/dict/addtype' // iframe的url
    });
}