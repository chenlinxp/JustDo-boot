var preUrl = "/system/user"
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
        url : preUrl+"/adminResetPwd",
        data : $('#signupForm').serialize(),// 你的formid
        async : false,
        error : function(request) {
            parent.layer.msg("系统错误，联系管理员");
        },
        success : function(data) {
            if (data.code == 0) {
                parent.layer.msg(data.msg);
                parent.reLoad();
                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                parent.layer.close(index);

            } else {
                parent.layer.msg(data.msg);
            }
        }
    });
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules : {
            password : {
                required : true,
                minlength : 6
            },
            password2 : {
                required : true,
                minlength : 6
            }
        },
        messages : {
            password : {
                required : icon + "请输入您的密码",
                minlength : icon + "密码必须6个字符以上"
            },
            password2 : {
                required : icon + "请再次输入您的密码",
                minlength : icon + "密码必须6个字符以上"
            }
        }
    })
}