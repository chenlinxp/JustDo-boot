var preUrl = "/system/employee"
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
            employeeNumber : {required : true}
,
	             loginName : {required : true}
,
	             realName : {required : true}
,
	             password : {required : true}
,
	             passwordSalt : {required : true}
,
	             deptmentId : {required : true}
,
	             organId : {required : true}
,
	             positionId : {required : true}
,
	             email : {required : true}
,
	             mobile : {required : true}
,
	             employeeState : {required : true}
,
	             createEmployeeId : {required : true}
,
	             createTime : {required : true}
,
	             modifyTime : {required : true}
,
	             employeeSex : {required : true}
,
	             birthday : {required : true}
,
	             photoId : {required : true}
,
	             liveAddress : {required : true}
,
	             employeeHobby : {required : true}
,
	             province : {required : true}
,
	             city : {required : true}
,
	             district : {required : true}
,
	             remark : {required : true}
       },
    messages : {
        employeeNumber : {required : icon + "员工编号"}
,
	              loginName : {required : icon + "用户名"}
,
	              realName : {required : icon + "真是姓名"}
,
	              password : {required : icon + "密码"}
,
	              passwordSalt : {required : icon + "密码盐"}
,
	              deptmentId : {required : icon + ""}
,
	              organId : {required : icon + ""}
,
	              positionId : {required : icon + ""}
,
	              email : {required : icon + "邮箱"}
,
	              mobile : {required : icon + "手机号"}
,
	              employeeState : {required : icon + "状态 0:禁用，1:正常"}
,
	              createEmployeeId : {required : icon + "创建用户id"}
,
	              createTime : {required : icon + "创建时间"}
,
	              modifyTime : {required : icon + "修改时间"}
,
	              employeeSex : {required : icon + "性别"}
,
	              birthday : {required : icon + "出身日期"}
,
	              photoId : {required : icon + "照片ID"}
,
	              liveAddress : {required : icon + "现居住地"}
,
	              employeeHobby : {required : icon + "爱好"}
,
	              province : {required : icon + "省份"}
,
	              city : {required : icon + "所在城市"}
,
	              district : {required : icon + "所在地区(县)"}
,
	              remark : {required : icon + ""}
       }
	})
}