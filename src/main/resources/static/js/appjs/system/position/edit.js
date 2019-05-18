var preUrl = "/system/position"
var preUrl2 = "/system/organ";
var preUrl3 = "/system/dept";
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
            organname :{required : true},
            deptname :{required : true},
            postname :{required : true},
            postcode :{required : true},
            isvalidation :{required : true},
            remark :{required : true}
        },
        messages : {
            organname :{required : icon + "请选择机构"},
            deptname :{required : icon + "请选择部门"},
            postname :{required : icon + "请输入岗位名称"},
            postcode :{required : icon + "请输入岗位编号"},
            isvalidation :{required : icon + "请选择是否有效"},
            remark :{required : icon + "请输入备注"}
        }
    })
}


var openOrgan = function(){
    layer.open({
        type:2,
        title:"选择机构",
        area : [ '300px', '450px' ],
        content:preUrl2+"/treeView"
    })
}
function loadOrgan( organid,organname){
    $("#organid").val(organid);
    $("#organname").val(organname);
}

var openDept = function(){
    var organid =  $("#organid").val();
    if(organid=="0"){
        parent.layer.alert("请先选择机构");
    }else {
        layer.open({
            type: 2,
            title: "选择部门",
            area: ['300px', '450px'],
            content: preUrl3 + "/treeView/" + organid
        })
    }
}
function loadDept( deptid,deptname){
    $("#deptid").val(deptid);
    $("#deptname").val(deptname);
}

var openPost = function(){
    var deptid =  $("#deptid").val();
    if(deptid=="0"){
        parent.layer.alert("请先选择部门");
    }else {
        layer.open({
            type: 2,
            title: "选择岗位",
            area: ['300px', '450px'],
            content: preUrl + "/treeView/" + deptid
        })
    }
}
function loadPost(postpid,postpname){
    if(postpid==$("#postid").val()){
        parent.layer.alert("上级岗位不能选自己");
        return;
    }
    $("#postpid").val(postpid);
    $("#postpname").val(postpname);
}

