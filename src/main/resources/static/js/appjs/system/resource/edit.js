var preUrl = "/system/resource"
$(function() {
	validateRule();
    var icon = "<i class='fa fa-times-circle'></i> ";
    var a = $("input[name='resourceType']:checked").val();
    if(a == "0"){
        $('#resourceUrl').rules('remove','required');
    }else{
        $('#resourceUrl').rules('add',{required:true,messages:{required : icon + "请输入资源URL地址"}});
    }
    $("input[name='resourceType']").click(function(){
        //获取选中的radio的值
        var value = $(this).val();
        if(value == "0"){
            $('#resourceUrl').rules('remove','required');
        }else{
            $('#resourceUrl').rules('add',{required:true,messages:{required : icon + "请输入资源URL地址"}});
        }
    });
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
            resourceName : {
                required : true
            },
            resourceUrl : {
                required : true
            }
        },
        messages : {
            resourceName : {
                required : icon + "请输入资源名称"
            },
            resourceUrl : {
                required : icon + "请输入资源URL地址"
            }
        }
    })
}