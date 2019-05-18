var preUrl = "/appmanage/app"
$(function() {
	validateRule();
            laydate({
                elem : '#expirerTime'
            });
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : preUrl+"/save",
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
    appName :{required : true}
,
    appKey :{required : true}
,
    bundleName :{required : true}
,
    bundleId :{required : true}
,
    shortUrl :{required : true}
,
    expirerTime :{required : true}
,
    appType :{required : true}
,
    isCombine :{required : true}
,
    description :{required : true}
		},
		messages : {
        appName :{required : icon + "请输入app显示名称"}
,
        appKey :{required : icon + "请输入APP-KEY"}
,
        bundleName :{required : icon + "请输入APP项目名称"}
,
        bundleId :{required : icon + "请输入包名ID"}
,
        shortUrl :{required : icon + "请输入短链接"}
,
        expirerTime :{required : icon + "请输入过期时间"}
,
        appType :{required : icon + "请输入app类型"}
,
        isCombine :{required : icon + "请输入是否合并"}
,
        description :{required : icon + "请输入APP介绍"}
		}
	})
}