$().ready(function() {
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
		url : "/system/organ/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("Connection error");
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
            organname : {
                required : true
            },
            organcode : {
                required : true
            },
            organalias : {
                required : true
            }
        },
        messages : {
            organname : {
                required : icon + "请输入机构名称"
            },
            organcode : {
                required : icon + "请输入机构编码"
            },
            organalias : {
                required : icon + "请输入机构别名"
            }
        }
    })
}


var openOrgan = function(){
    layer.open({
        type:2,
        title:"选择机构",
        area : [ '300px', '450px' ],
        content:"/system/organ/treeView"
    })
}
function loadOrgan( organpid,organpname){
    if(organpid==$("#organid").val()){
        parent.layer.alert("上级机构不能选自己");
        return;
    }
    $("#organpid").val(organpid);
    $("#organpname").val(organpname);
}