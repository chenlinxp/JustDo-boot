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
                striped : true,
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
                        title: '数据类型',
                        editable: {
                            type: 'select',
                            title: '数据类型',
                            source: function () {
                                var result = [];
                                $.ajax({
                                    url: '/system/dict/list/dataTypeCode',
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
                        field: 'displayType',
                        title: '显示方式',
                        editable: {
                            type: 'select',
                            title: '显示方式',
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
                            title: '查询类型',
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
                        field: 'dictCode',
                        title: '字典编码',
                        editable: {
                            type: 'select',
                            title: '字典索引编码',
                            source: function () {
                                var result = [];
                                $.ajax({
                                    url: '/system/dict/dicttype',
                                    async: false,
                                    type: "get",
                                    data: {},
                                    success: function (data, status) {
                                        $.each(data, function (key, value) {
                                            result.push({ value: value.dcode, text: value.dname });
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
function removeEmptyArrayEle(arr){
    for(var i = 0; i < arr.length; i++) {
        if(arr[i] == "undefined") {
            arr.splice(i,1);
            i = i - 1; // i - 1 ,因为空元素在数组下标 2 位置，删除空之后，后面的元素要向前补位，
            // 这样才能真正去掉空元素,觉得这句可以删掉的连续为空试试，然后思考其中逻辑
        }
    }
    return arr;
};


function code() {
    //获取表格的所有内容行
   var  allTableData = $('#bTable').bootstrapTable('getData');
   var allTableData2 = [];
    for(var i = 0; i < allTableData.length; i++) {
        var a = allTableData[i];
        //console.log(a);
        delete a["0"];
        allTableData2.push(a);
    }
    console.log(allTableData);
    var tablename = $("#tablename").val()
    //console.log(allTableData);
    if (allTableData.length == 0) {
        layer.msg("请在数据库增加列");
        return;
    }
    layer.confirm("确认要生成表中的'" + allTableData.length + "'列数据吗?", {
        btn : [ '确定', '取消' ]
        // 按钮
    }, function() {
        document.write("<form action='/system/generator/code' method='post' name='form1' style='display:none'>");
        document.write("<input type='hidden' name='tableName' value="+tablename+">");
        document.write("<input type='hidden' name='alltabledata' value="+JSON.stringify(allTableData)+">");
        document.write("</form>");
        document.form1.submit();
    }, function() {});
}