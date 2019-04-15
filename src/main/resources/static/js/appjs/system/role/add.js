//var resourceTree;
var preUrl = "/system/role"
var resourceIds;
$(function() {
	getResourceTreeData();
	validateRule();
});
$.validator.setDefaults({
	submitHandler : function() {
		getAllSelectNodes();
		save();
	}
});

function getAllSelectNodes() {
	var ref = $('#resourceTree').jstree(true); // 获得整个树

    resourceIds = ref.get_selected(); // 获得所有选中节点的，返回值为数组

	$("#resourceTree").find(".jstree-undetermined").each(function(i, element) {
        resourceIds.push($(element).closest('.jstree-node').attr("id"));
	});
}
function getResourceTreeData() {
	$.ajax({
		type : "GET",
		url : "/system/resource/tree",
		success : function(resourceTree) {
			loadResourceTree(resourceTree);
		}
	});
}
function loadResourceTree(resourceTree) {
	$('#resourceTree').jstree({
		'core' : {
			'data' : resourceTree
		},
		"checkbox" : {
			"three_state" : true,
		},
		"plugins" : [ "wholerow", "checkbox" ]
	});
	//$('#resourceTree').jstree("open_all");

}

function save() {
	$('#resourceIds').val(resourceIds);
	var role = $('#signupForm').serialize();
	$.ajax({
		cache : true,
		type : "POST",
		url : preUrl+"/save",
		data : role, // 你的formid

		async : false,
		error : function(request) {
			alert("服务异常，请稍后再试");
		},
		success : function(data) {
			if (data.code == 0) {
				parent.layer.msg("操作成功");
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引

				parent.layer.close(index);

			} else {
				parent.layer.msg(data.msg);
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