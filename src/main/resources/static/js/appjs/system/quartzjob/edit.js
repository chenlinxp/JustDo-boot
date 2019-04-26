var preUrl = "/system/quartzjob"
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
		url :preUrl+"/update",
		data : $('#signupForm').serialize(),
		async : false,
		error : function(request) {
			layer.alert("服务异常，请稍后再试");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("保存成功");
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
            taskName :
                {required : true}
            ,
            taskStatus :
                {required : true}
            ,
            taskGroup :
                {required : true}
            ,
            cronExpression :
                {required : true}
            ,
            methodName :
                {required : true}
            ,
            springBean :
                {required : true}
            ,
            isCurrent :
                {required : true}
            ,
            beanClass :
                {required : true}
        },
        messages : {
            taskName :
                {required : icon + "任务名"}
            ,
            taskStatus :
                {required : icon + "任务状态"}
            ,
            taskGroup :
                {required : icon + "任务分组"}
            ,
            cronExpression :
                {required : icon + "cron表达式"}
            ,
            methodName :
                {required : icon + "任务调用的方法名"}
            ,
            springBean :
                {required : icon + "Spring bean"}
            ,
            isCurrent :
                {required : icon + "任务是否有状态"}
            ,
            beanClass :
                {required : icon + "任务执行时调用哪个类的方法 包名+类名"}
        }
    })
}