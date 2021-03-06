var preUrl = "/system/permissioninit"
$(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	console.info(234);
    console.info($('#signupForm').serialize());
	$.ajax({
		cache : true,
		type : "POST",
		url : preUrl+"/update",
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
			permissionUrl :{required : true},
			permissionInit :{required : true},
			permissionSort :{required : true}
},
        messages : {
			permissionUrl :{required : icon + "请输入链接地址"},
			permissionInit :{required : icon + "请输入需要具备的权限"},
			permissionSort :{required : icon + "请输入排序"}
        }
	})
}