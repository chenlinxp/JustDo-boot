$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
        update();
	}
});

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#dictTypeForm").validate({
		rules : {
			dname : {
				required : true
			},
            dcode : {
                required : true
            }
		},
		messages : {
            dname : {
                required : icon + "请输入标签名"
            },
            dcode : {
                required : icon + "请输入数据值"
            }
		}
	})
}


function update() {
    $.ajax({
        cache : true,
        type : "POST",
        url : "/system/dict/updatetype",
        data : $('#dictTypeForm').serialize(), // 你的formid
        async : false,
        error : function(request) {
            parent.layer.alert("网络超时");
        },
        success : function(data) {
            if (data.code == 0) {
                parent.layer.msg("操作成功");
                parent.dictTypeLoad();
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);

            } else {
                parent.layer.alert(data.msg)
            }

        }
    });

}