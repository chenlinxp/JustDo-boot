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
		url : "/system/dict/edit",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
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
            dcvalue : {
				required : true
			},
            dccode : {
                required : true
            },
            orderno : {
                required : true
            }
		},
		messages : {
            dcvalue : {
				required : icon + "请输入文本内容"
			},
            dccode : {
                required : icon + "请输入编码"
            },
            orderno : {
                required : icon + "请输入排序"
            }
		}
	})
}