var preUrl = "/system/resource"
$(document).ready(function () {
    load();
});
var load = function () {
    var query = {
        resourceName : $('#searchName').val(),
        sort:'order_num'
    }
    $('#bTable')
        .bootstrapTreeTable(
            {
                id: 'resourceId',
                code: 'resourceId',
                parentCode: 'parentId',
                type: "GET", // 请求数据的ajax类型
                url: preUrl + '/list', // 请求数据的ajax的url
                ajaxParams: query, // 请求数据的ajax的data属性
                expandColumn: '2',// 在哪一列上面显示展开按钮
                striped: true, // 是否各行渐变色
                bordered: true, // 是否显示边框
                expandAll: false, // 是否全部展开
                toolbar : '#bToolbar',
                columns: [
                    {
                        title : '序号',
                        field : 'rowalias',
                        align : 'left',
                        valign : 'center',
                        width : '50px'
                    },
                    {
                        field : 'resourceId',
                        title : '资源ID',
                        visible :false
                    },
                    {
                        field : 'resourceName',
                        title : '资源名称',
                        width : '200px'
                    },
                    {
                        field : 'resourceIcon',
                        title : '图标',
                        width : '50px',
                        formatter: function (item, index) {
                            return item.resourceIcon == null ? ''
                                : '<i class="' + item.resourceIcon
                                + ' fa-lg"></i>';
                        }
                    },
                    {
                        field : 'deskDisplay',
                        title : '桌面显示',
                        width: '80px',
                        formatter: function (item, index) {
                            if (item.deskDisplay === 1) {
                                return '是';
                            }else{
                                return '否';
                            }
                        }
                    },
                    {
                        field : 'resourceType',
                        title : '资源类型',
                        width: '80px',
                        formatter: function (item, index) {
                            if (item.resourceType === 0) {
                                return '<span class="label label-success">目录</span>';
                            }
                            if (item.resourceType === 1) {
                                return '<span class="label label-success">菜单</span>';
                            }
                            if (item.resourceType === 2) {
                                return '<span class="label label-success">按钮</span>';
                            }
                        }
                    },
                    {
                        field : 'resourceUrl',
                        title : '资源URL',
                        width : '10%',
                        formatter: function (item, index) {
                            if (item.resourceUrl === ""||item.resourceUrl===null) {
                                if (item.parentId === ""||item.parentId===null) {

                                    return '顶级菜单';
                                }
                            }else{
                                return item.resourceUrl;
                            }
                        }
                    },
                    {
                        field : 'resourcePermission',
                        title : '授权标识',
                        formatter: function (item, index) {
                            if (item.resourcePermission === ""||item.resourcePermission===null) {
                                if (item.parentId === ""||item.parentId===null) {

                                    return '顶级菜单';
                                }
                            }else{
                                return item.resourcePermission;
                            }
                        }
                    },
                    {
                        field : 'orderNum',
                        title : '排序',
                        align : 'center',
                        valign : 'center',
                        width : '50px'
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
    load();
}

function add() {
    layer.open({
        type: 2,
        title: '增加菜单',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: preUrl + '/add'
    });
}

function edit() {
    var id = $('#bTable').bootstrapTreeTable('getSelection');
    if (id == undefined) {
        layer.msg("请选择一条要修改的数据");
        return;
    }
    layer.open({
        type: 2,
        title: '菜单修改',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area: ['800px', '520px'],
        content: preUrl + '/edit/' + id
    });
}
function del() {
    layer.confirm('确定要删除选中的记录？', {
        btn : [ '确定', '取消' ]
    }, function() {
        var id = $('#bTable').bootstrapTreeTable('getSelection');
        if (id == undefined) {
            layer.msg("请选择一条要删除的数据");
            return;
        }
        $.ajax({
            url : preUrl+"/del",
            type : "post",
            data : {
                'resourceId' : id
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