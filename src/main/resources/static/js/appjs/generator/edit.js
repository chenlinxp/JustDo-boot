var preUrl = "/system/generator"
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
			parent.layer.alert("网络连接超时");
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
            projectName : {
                required : true
            },
			author : {
				required : true
			},
            email : {
                required : true,
                email:true
            },
            phone : {
                required : true,
                phone:true,
                minlength : 11
            },
			package : {
				required : true,
                package: true
			},
            versionCode : {
                required : true,
                version: true
            }
			
		},
		messages : {
            projectName : {
                required : icon + "请输入项目名"
            },
			author : {
				required : icon + "请输入作者名"
			},
            phone : {
				required : icon + "请输入手机号",
                phone:"请填写正确的11位手机号"
			},
            email : {
                required : icon + "请输入email",
            },
			package : {
				required : icon + "请输入包名",
			},
            versionCode : {
                required : icon + "请输入版本号",
            }
		}
	})
}
