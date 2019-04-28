var preUrl = "/system/employee"
$(function () {
    laydate({
        elem : '#birthday'
    });
});
$.validator.setDefaults({
    submitHandler: function(form) {
        form.submit();
    }
});
/**
 * 基本信息提交
 */
$("#base_save").click(function () {
    var hobbyStr = getHobbyStr();
    $("#hobby").val(hobbyStr);
    if($("#basicInfoForm").valid()){
            $.ajax({
                cache : true,
                type : "POST",
                url :preUrl+"/updatePeronal",
                data : $('#basicInfoForm').serialize(),
                async : false,
                error : function(request) {
                    laryer.alert("服务异常，请稍后再试");
                },
                success : function(data) {
                    if (data.code == 0) {
                        parent.layer.msg("更新成功");
                    } else {
                        parent.layer.alert(data.msg)
                    }
                }
            });
        }

});
$("#pwd_save").click(function () {
    if($("#modifyPwd").valid()){
        $.ajax({
            cache : true,
            type : "POST",
            url :preUrl+"/resetPwd",
            data : $('#modifyPwd').serialize(),
            async : false,
            error : function(request) {
                parent.laryer.alert("服务异常，请稍后再试");
            },
            success : function(data) {
                if (data.code == 0) {
                    parent.layer.alert("更新密码成功");
                    $("#photo_info").click();
                } else {
                    parent.layer.alert(data.msg)
                }
            }
        });
    }
});
function getHobbyStr(){
    var hobbyStr ="";
    $(".hobby").each(function () {
        if($(this).is(":checked")){
            hobbyStr+=$(this).val()+";";
        }
    });
   return hobbyStr;
}
//校验基础信息表单
$("#basicInfoForm").validate({
    errorElement:'span',
    errorClass:'help-block error-mes',
    rules:{
        name:{
            required:true,
            realName:true
        },
        sex:"required",
        birth:"required",
        mobile:{
            required:true,
            phone:true
        },
        email:{
            required:true,
            email:true
        }
    },
    messages:{
        name:{
            required:"请输入中文姓名",
            realName:"姓名只能为汉字"
        },
        sex:{
            required:"请输入性别"
        },
        birth:{
            required:"请输入出生年月"
        },
        mobile:{
            required:"请输入手机号",
            phone:"请填写正确的11位手机号"
        },
        email:{
            required:"请输入邮箱",
            email:"请填写正确的邮箱格式"
        }
    },

    errorPlacement:function(error,element){
        element.next().remove();
        element.closest('.gg-formGroup').append(error);
    },

    highlight:function(element){
        $(element).closest('.gg-formGroup').addClass('has-error has-feedback');
    },
    success:function(label){
        var el = label.closest('.gg-formGroup').find("input");
        el.next().remove();
        label.closest('.gg-formGroup').removeClass('has-error').addClass("has-feedback has-success");
        label.remove();
    },
    submitHandler:function(form){
        alert("保存成功!");
    }
});

//校验修改密码表单
$("#modifyPwd").validate({
    onfocusout: function(element) { $(element).valid()},
    debug:false, //表示校验通过后是否直接提交表单
    onkeyup:false, //表示按键松开时候监听验证
    rules:{
        pwdOld:{
            required:true,
            minlength:6
        },
        pwdNew:{
            required:true,
            minlength:6,
            isdiff:true,
            //issame:true,
        },
        confirm_password:{
            required:true,
            minlength:6,
            issame:true,
        }

    },
    messages:{
        pwdOld : {
            required:'必填',
            minlength:$.validator.format('密码长度要大于6')
        },
        pwdNew:{
            required:'必填',
            minlength:$.validator.format('密码长度要大于6'),
            isdiff:'原密码与新密码不能重复',

        },
        confirm_password:{
            required:'必填',
            minlength:$.validator.format('密码长度要大于6'),
            issame:'新密码要与确认新密码一致',
        }

    },
    errorElement:"mes",
    errorClass:"gg-star",
    errorPlacement: function(error, element)
    {
        element.closest('.gg-formGroup').append(error);

    }
});