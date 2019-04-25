var preUrl = "/system/notice"
$(function() {
	validateRule();
    $('.chosen-select').on('change', function(e) {
       console.log(e);
        $("#noticeType").val($(".chosen-select").val());
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
	    noticeType :
	               {required : true},
	    noticeTitle :
	               {required : true},
	    noticeContent :
	               {required : true}

		},
		messages : {
	    noticeType :
	              {required : icon + "请选择类型"},
	    noticeTitle :
	              {required : icon + "请输入标题"},
	    noticeContent :
	              {required : icon + "请输入内容"}
        }

	})
}

var openEmployee= function(){
	layer.open({
		type:2,
		title:"选择人员",
		area : [ '300px', '450px' ],
		content:"/system/employee/treeView"
	})
}

function loadEmployee(employeeIds,employeeNames){
	$("#employeeIds").val(employeeIds);
	$("#employeeNames").val(employeeNames);
}