var preUrl = "/system/position"
$(function() {
	validateRule();
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
    postname :{required : true}
,
    postcode :{required : true}
,
    isvalidation :{required : true}
,
    remark :{required : true}
		},
		messages : {

	    postname :{required : icon + "请输入岗位名称"}

,

	    postcode :{required : icon + "请输入岗位编号"}

,

        isvalidation :{required : icon + "请选择是否有效"}

,

	    remark :{required : icon + "请输入备注"}

		}
	})
}