var preUrl = "/appmanage/appversion"
$(function() {
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
			versionCode :{required : true}
,
			buildCode :{required : true}
,
			appSizes :{required : true}
,
			totalLoadNumber :{required : true}
,
			displayState :{required : true}
,
			todayLoadNumber :{required : true}
,
			versionDescription :{required : true}
,
			updateDescription :{required : true}
},
        messages : {
			versionCode :{required : icon + "请输入版本号"}
,
			buildCode :{required : icon + "请输入构建号"}
,
			appSizes :{required : icon + "请输入APP大小"}
,
			totalLoadNumber :{required : icon + "请输入总有下载次数"}
,
			displayState :{required : icon + "请选择显示状态"}
,
			todayLoadNumber :{required : icon + "请输入今天下载次数"}
,
			versionDescription :{required : icon + "请输入版本描述"}
,
			updateDescription :{required : icon + "请输入更新描述"}
        }
	})
}