var preUrl = "/system/employee"
$(document).ready(function() {
	validateRule();
    laydate({
        elem : '#birthday'
    });
});

$.validator.setDefaults({
	submitHandler : function() {
		update();
	}
});
function update() {
	$("#roleIds").val(getCheckedRoles());
	$.ajax({
		cache : true,
		type : "POST",
		url : preUrl+"/update",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			alert("服务异常，请稍后再试");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg(data.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.msg(data.msg);
			}

		}
	});

}
function getCheckedRoles() {
	var adIds = "";
	$("input:checkbox[name=role]:checked").each(function(i) {
		if (0 == i) {
			adIds = $(this).val();
		} else {
			adIds += ("," + $(this).val());
		}
	});
	return adIds;
}
function setCheckedRoles() {
	var roleIds = $("#roleIds").val();
	alert(roleIds);
	var adIds = "";
	$("input:checkbox[name=role]:checked").each(function(i) {
		if (0 == i) {
			adIds = $(this).val();
		} else {
			adIds += ("," + $(this).val());
		}
	});
	return adIds;
}
function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules : {
            realName : {
                required : true
            },
            employeeNumber : {
                required : true
            },
            loginName : {
                required : true,
                minlength : 2,
                remote : {
                    url : preUrl+"/exist", // 后台处理程序
                    type : "post", // 数据发送方式
                    dataType : "json", // 接受数据格式
                    data : { // 要传递的数据
                        loginName : function() {
                            return $("#loginName").val();
                        }
                    }
                }
            },
            password : {
                required : true,
                minlength : 6
            },
            confirm_password : {
                required : true,
                minlength : 6,
                equalTo : "#password"
            },
            email : {
                required : true,
                email : true
            },
            mobile : {
                required : true,
                mobile : true
            }
        },
        messages : {

            realName : {
                required : icon + "请输入员工姓名"
            },
            employeeNumber : {
                required : icon + "请输入员工编号"
            },
            loginName : {
                required : icon + "请输入您的账名",
                minlength : icon + "账名必须两个字符以上",
                remote : icon + "账名已经存在"
            },
            password : {
                required : icon + "请输入您的密码",
                minlength : icon + "密码必须6个字符以上"
            },
            confirm_password : {
                required : icon + "请再次输入密码",
                minlength : icon + "密码必须6个字符以上",
                equalTo : icon + "两次输入的密码不一致"
            },
            email : icon + "请输入您的E-mail",
            mobile : icon + "请输入您的手机号",
        }
    })
}
var openDept = function(){
	layer.open({
		type:2,
		title:"选择部门",
		area : [ '300px', '450px' ],
		content:"/system/dept/treeView"
	})
}
function loadDept( deptId,deptName){
	$("#deptmentId").val(deptId);
	$("#deptName").val(deptName);
}