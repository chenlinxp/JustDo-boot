var preUrl = "/system/role"
var resourceIds;
$(function() {
	getResourceTreeData();
	validateRule();
});
$.validator.setDefaults({
	submitHandler : function() {
		getAllSelectNodes();
		update();
	}
});
function loadResourceTree(resourceTree) {
	$('#resourceTree').jstree({
		"plugins" : [ "wholerow", "checkbox" ],
		'core' : {
			'data' : resourceTree
		},
		"checkbox" : {
			//"keep_selected_style" : false,
			//"undetermined" : true
			//"three_state" : false,
			//"cascade" : ' up'
		}
	});
	$('#resourceTree').jstree('open_all');
}
function getAllSelectNodes() {
	var ref = $('#resourceTree').jstree(true); // 获得整个树
    resourceIds = ref.get_selected(); // 获得所有选中节点的，返回值为数组
	$("#resourceTree").find(".jstree-undetermined").each(function(i, element) {
        resourceIds.push($(element).closest('.jstree-node').attr("id"));
	});
	console.log(resourceIds);
}
function getResourceTreeData() {
	var roleId = $('#roleId').val();
	$.ajax({
		type : "GET",
		url : "/system/resource/tree/" + roleId,
		success : function(data) {
			loadResourceTree(data);
		}
	});
}
function update() {
	$('#resourceIds').val(resourceIds);
	var role = $('#signupForm').serialize();
	$.ajax({
		cache : true,
		type : "POST",
		url : preUrl+"/update",
		data : role, // 你的formid
		async : false,
		error : function(request) {
			alert("服务异常，请稍后再试");
		},
		success : function(r) {
			if (r.code == 0) {
				parent.layer.msg(r.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.msg(r.msg);
			}

		}
	});
}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			roleName : {
				required : true
			}
		},
		messages : {
			roleName : {
				required : icon + "请输入角色名"
			}
		}
	});
}