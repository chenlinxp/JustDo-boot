$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/system/dept/update",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("服务异常，请稍后再试");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.alert(data.msg)
			}

		}
	});

}
function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules : {
            deptname : {
                required : true
            },
            organid : {
                required : true
            }
        },
        messages : {
            deptname : {
                required : icon + "请输入部门名称"
            },
            organid : {
                required : icon + "请选择机构"
            }
        }
    })
}
var openOrgan = function(){
    layer.open({
        type:2,
        title:"选择机构",
        area : [ '300px', '450px' ],
        content:"/system/organ/treeView"
    })
}
function loadOrgan( organid,organname){
    $("#organid").val(organid);
    $("#organname").val(organname);
}

var openDept = function(){
    layer.open({
        type:2,
        title:"选择部门",
        area : [ '300px', '450px' ],
        content:"/system/dept/treeView"
    })
}
function loadDept( deptpid,deptpname){
    $("#deptpid").val(deptpid);
    $("#deptpname").val(deptpname);
}