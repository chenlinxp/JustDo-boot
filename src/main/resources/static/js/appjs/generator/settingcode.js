var preUrl = "/system/generator"
$(function() {
    load();
});
function load() {
    var tablename = $("#tablename").val()
    $('#bTable')
        .bootstrapTable(
            {
                method : 'get',
                url: preUrl + "/getGeneratorColumns/"+tablename, // 服务器数据的加载地址
                toolbar: "#toolbar",
                idField: "columnName",
                pagination: true,
                showRefresh: true,
                search: false,
                clickToSelect: true,
                pageSize : 20, // 如果设置了分页，每页数据条数
                pageList: [20,50,100],
                pageNumber : 1, // 如果设置了分布，首页页码
                queryParams : function(params) {
                    return {
                        // 说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        sort: 'columnName',
                        order: 'desc',
                        columnName : $('#searchName').val(),
                    };
                },
                columns: [
                    {
                        checkbox : true
                    },
                    {
                        field: 'SerialNumber',
                        title: '序号',
                        align: 'center',
                        width: '30px',
                        formatter: function (value, row, index) {
                            var pageNumber = $('#bTable').bootstrapTable("getOptions").pageNumber;
                            var pageSize = $('#bTable').bootstrapTable("getOptions").pageSize;
                            return (pageNumber - 1) * pageSize + index + 1;
                        }
                    },
                    {
                        field: 'columnName',
                        title: '列名'
                    },
                    {
                        field: 'columnComment',
                        title: '注释',
                        editable: {
                            type: 'text',
                            title: '注释',
                            validate: function (v) {
                                if (!v) return '注释不能为空';
                            }
                        }
                    },
                    {
                        field: 'dataType',
                        title: '数据类型'
                    },
                    {
                        field: 'displayType',
                        title: '显示方式',
                        editable: {
                            type: 'select',
                            title: '数据类型',
                            source: function () {
                                var result = [];
                                $.ajax({
                                    url: '/system/dict/list/pagefieldCode',
                                    async: false,
                                    type: "get",
                                    data: {},
                                    success: function (data, status) {
                                        $.each(data, function (key, value) {
                                            result.push({ value: value.dccode, text: value.dcvalue });
                                        });
                                    }
                                });
                                return result;
                            }
                        }
                    },
                    {
                        field: 'searchType',
                        title: '查询类型',
                        editable: {
                            type: 'select',
                            title: '数据类型',
                            source: function () {
                                var result = [];
                                $.ajax({
                                    url: '/system/dict/list/pagefieldCode',
                                    async: false,
                                    type: "get",
                                    data: {},
                                    success: function (data, status) {
                                        $.each(data, function (key, value) {
                                            result.push({ value: value.dccode, text: value.dcvalue });
                                        });
                                    }
                                });
                                return result;
                            }
                        }
                    },
                    {
                        field: 'orderNum',
                        title: '显示序号',
                        editable: {
                            type: 'text',
                            title: '显示序号',
                            validate: function (v) {
                                if (!v) return '显示序号不能为空';

                            }
                        }
                    }
                ]
            });
}
function reLoad() {
    $('#bTable').bootstrapTable('refresh');
}
function code() {
    //获取表格的所有内容行
    var allTableData = $('#bTable').bootstrapTable('getData');
    var tablename = $("#tablename").val()
    console.log(allTableData);
    if (allTableData.length == 0) {
        layer.msg("请在数据库增加列");
        return;
    }
    layer.confirm("确认要生成表中的'" + allTableData.length + "'列数据吗?", {
        btn : [ '确定', '取消' ]
        // 按钮
    }, function() {
        $.ajax({
            type : 'POST',
            data : {
                "tableName" :tablename ,
                "alltabledata" : allTableData
            },
            url : preUrl + '/code',
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