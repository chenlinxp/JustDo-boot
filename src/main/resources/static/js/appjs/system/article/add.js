var preUrl = "/system/article"
$(function() {
    $('.summernote').summernote({
        height : '220px',
        lang : 'zh-CN',
        callbacks: {
            onImageUpload: function(files, editor, $editable) {
                sendFile(files);
            }
        }
    });
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save(1);
	}
});
function save(status) {
    $("#articleStatus").val(status);
    var content_sn = $("#content_sn").summernote('code');
    $("#articleContent").val(content_sn);
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
                $("#articleId").val(data.articleId);
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				if(index){
				parent.layer.close(index);}

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
           articleTitle :{required : true},
		   articleAuthor :{required : true},
			articleContent :{required : true},
           articleType :{required : true},
           articleTags :{required : true},
           articleCategories :{required : true}

		},
		messages : {
	       articleTitle :{required : icon + "请输入标题"},
           articleAuthor :{required : icon + "请输入作者"},
           articleContent :{required : icon + "请输入文章内容"},
	       articleType :{required : icon + "请输入类型"},
	       articleTags :{required : icon + "请输入标签"},
	       articleCategories :{required : icon + "请输入分类"}
		}
	})
}