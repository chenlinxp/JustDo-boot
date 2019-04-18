var preUrl1= "/system/notice"
$(function() {
	loadType();
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
		url : preUrl1+"/save",
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
			name : {
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请输入姓名"
			}
		}
	})
}

function loadType(){
    var html = "";
    $.ajax({
        url : '/system/dict/list/noticeCode',
        success : function(data) {
            // 加载数据
            for (var i = 0; i < data.length; i++) {
                html += '<option value="' + data[i].dccode + '">' + data[i].dcvalue + '</option>'
            }
            $(".chosen-select").append(html);
            $(".chosen-select").chosen({
                maxHeight : 200
            });
            $(".chosen-select").val($("#Ttype").val());
            $(".chosen-select").trigger("chosen:updated");
            // 点击事件
            $('.chosen-select').on('change', function(e, params) {

            });
        }
    });
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