
$(document).ready(function () {
    $("#login").on('click',function(){$("#signupForm").submit();});
    validateRule();
});

$.validator.setDefaults({
    submitHandler: function () {
        login();
    }
});
function keyLogin(){
    //回车键的键值为13
    if (event.keyCode==13)
    //调用登录按钮的登录事件
        $("#login").click();
}

function refreshCode(){
$("#verificationimg").attr("src","/verification")
}
function login() {
    $.ajax({
        type: "POST",
        url: "/login",
        data: $('#signupForm').serialize(),
        success: function (r) {
            console.log(r);
            if (r.code == 0) {
                var index = layer.load(1, {
                    shade: [0.1,'#fff'] //0.1透明度的白色背景
                });
                parent.location.href = '/index';
            } else {
                layer.msg(r.msg);
            }
        },
    });
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            },
            verificationcode:{
                required: true
            }
        },
        messages: {
            username: {
                required: icon + "请输入您的用户名",
            },
            password: {
                required: icon + "请输入您的密码",
            },
            verificationcode: {
                required: icon + "请输入验证码",
            }
        }
    })
}