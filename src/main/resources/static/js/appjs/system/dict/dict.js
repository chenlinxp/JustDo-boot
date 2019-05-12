
var preUrl = "/system/dict"
$(function() {
    $('#searchDName').bind('keypress',function(event){
        if(event.keyCode == "13") {
            dictTypeLoad();
        }
    });
	load();
});
function selectLoad() {
	var html = "";
	$.ajax({
		url : preUrl+'/type',
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
    var dname =$('#searchDName').val();
    $.ajax({
        url : preUrl+'/dicttype',
		data : {  "dname" : dname },
        success : function(data) {
            //加载数据
            html ='<ul class="folder-list" style="padding: 0">';
            html +='<li value=""><a href="javascript:void(0)"><i class="fa fa-folder"></i>全部类型</a></li>';
            for (var i = 0; i < data.length; i++) {
                html += '<li value="' + data[i].did + '"><a href="javascript:void(0)"><i class="fa fa-folder"></i>'+ data[i].dname+ '</a></li>'
            }
            html += '</ul>';
            $("#dicttype").empty();
            $("#dicttype").append(html);
            //点击事件
            $('#dicttype li').on('click', function() {
                $('#dicttype li a').removeClass('hactive aactive');//去除之前选中的行的，选中样式
                $(this).find('a').addClass('hactive aactive');//添加当前选中的 success样式用于区别
				var id =  $(this).attr("value");
                var opt = {
                    query : {
                        did : id,
                    }
                }
                $('#bTable').bootstrapTable('refresh', opt);
            });
        }
    });
}
function load() {
    dictTypeLoad();
    $('#bTable')
        .bootstrapTable(
            {
                method : 'get', // 服务器数据的请求方式 get or post
                url : preUrl+ "/list", // 服务器数据的加载地址
                showRefresh : true,
                //	showToggle : true,
                //	showColumns : true,
                iconSize : 'outline',
                toolbar : '#bToolbar',
                striped : true, // 设置为true会有隔行变色效果
                dataType : "json", // 服务器返回的数据类型
                pagination : true, // 设置为true会在底部显示分页条
                // queryParamsType : "limit",
                // //设置为limit则会发送符合RESTFull格式的参数s
                singleSelect : false, // 设置为true将禁止多选
                // contentType : "application/x-www-form-urlencoded",
                // //发送到服务器的数据编码类型
                pageSize : 10, // 如果设置了分页，每页数据条数
                pageList: [10, 20, 50, 100],
                pageNumber : 1, // 如果设置了分布，首页页码
                //search : true, // 是否显示搜索框
                showColumns : false, // 是否显示内容下拉框（选择显示的列）
                sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
                queryParams : function(params) {
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit: params.limit,
                        offset:params.offset,
                        sort: 'dccode',
                        order: 'asc',
                        dcvalue:$('#searchName').val(),
                        did:$('#dicttype li a.hactive.aactive').parent().attr("value") || ""
                    };
                },
                onDblClickRow: function (row, element) {
                    view(row["id"]);
                },
                onClickRow: function (row, element) {
                    $('.success').removeClass('success');//去除之前选中的行的，选中样式
                    $(element).addClass('success');//添加当前选中的 success样式用于区别
                    $("#bTable").bootstrapTable("uncheckAll");
                    var rowindex=$(element).attr("data-index");
                    $("#bTable").bootstrapTable('check',rowindex);
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
                        field : 'dcid',
                        title : '数据字典表ID',
						visible : false
                    },
                    {
                        field : 'did',
                        title : '数据字典索引表的ID',
                        visible : false
                    },
                    {
                        field : 'dcvalue',
                        title : '文本内容'
                    },
                    {
                        field : 'dccode',
                        title : '编码'
                    },
                    {
                        field : 'orderno',
                        title : '排序',
                        width : '30px'
                    },
                    {
                        field : 'dcvalid',
						title : '是否有效',
                        width : '60px',
						formatter : function(value, row, index) {
							if (value == '0') {
								return '<span class="label label-danger">无效</span>';
							} else if (value == '1') {
								return '<span class="label label-primary">有效</span>';
							}
				    	}

				    },
                    {
                        field : 'remark',
                        title : '说明描述'
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

function view(id) {
    layer.open({
        type : 2,
        title : '查看',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '800px', '520px' ],
        content : preUrl+'/view/'+id // iframe的url
    });
}
function add() {
   var id = $('#dicttype li a.hactive.aactive').parent().attr("value")
    if (id == undefined||id == "") {
        layer.msg("请选择数据字典类型");
        return;
    }
	layer.open({
		type : 2,
		title : '增加',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : preUrl+'/add/'+id// iframe的url
	});
}
function edit() {
    // 返回所有选择的行，当没有选择的记录时，返回一个空数组
    var rows = $('#bTable').bootstrapTable('getSelections');
    var id;
    if (rows.length == 0||rows.length >1) {
        layer.msg("请选择一条要修改的数据");
        return;
    }else{
        id=rows[0]['dcid'];
    }
	layer.open({
		type : 2,
		title : '编辑',
		maxmin : true,
		shadeClose : false, // 点击遮罩关闭层
		area : [ '800px', '520px' ],
		content : preUrl+ '/edit/' + id // iframe的url
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
			ids[i] = row['dcid'];
		});
		$.ajax({
			type : 'POST',
			data : {
				"ids" : ids
			},
			url : preUrl+'/batchDel',
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
        content : preUrl+'/addtype' // iframe的url
    });
}


function editDict() {
    var id = $('#dicttype li a.hactive.aactive').parent().attr("value")
    if (id == undefined||id == "") {
        layer.msg("请选择要修改的数据字典类型");
        return;
    }
    layer.open({
        type : 2,
        title : '修改字典类型',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ '500px', '400px' ],
        content : preUrl+'/edittype/'+id // iframe的url
    });
}

function delDict() {
    var id = $('#dicttype li a.hactive.aactive').parent().attr("value")
    if (id == undefined||id == "") {
        layer.msg("请选择要删除的数据字典类型");
        return;
    }
    layer.confirm("确认要删除选中的数据吗?", {
        btn : [ '确定', '取消' ]
        // 按钮
    }, function() {
        $.ajax({
            type : 'POST',
            data : {
                "id" : id
            },
            url : preUrl+'/deltype',
            success : function(r) {
                if (r.code == 0) {
                    layer.msg(r.msg);
                    dictTypeLoad();
                } else {
                    layer.msg(r.msg);
                }
            }
        });
    }, function() {});
}
