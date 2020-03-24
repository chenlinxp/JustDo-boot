var preUrl = "/appmanage/app"
$(function() {

});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});
function save() {
	// $.ajax({
	// 	cache : true,
	// 	type : "POST",
	// 	url : preUrl+"/save",
	// 	data : $('#signupForm').serialize(),// 你的formid
	// 	async : false,
	// 	error : function(request) {
	// 		parent.layer.alert("服务异常，请稍后再试");
	// 	},
	// 	success : function(data) {
	// 		if (data.code == 0) {
	// 			parent.layer.msg("操作成功");
	// 			parent.reLoad();
	// 			var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
	// 			parent.layer.close(index);
    //
	// 		} else {
	// 			parent.layer.alert(data.msg)
	// 		}
    //
	// 	}
	// });
    $.ajax({
        type:"post",
        url:preUrl+"/upload",
        data : $('#signupForm').serialize(),
        dataType: 'json', //返回值类型 一般设置为json
        //contentType: "application/x-www-form-urlencoded; charset=utf-8",
        processData: false,  // jQuery不要去处理发送的数据
        success:function(res){
            if (data.code == 0) {
            console.log(res);
            parent.layer.msg("操作成功");
            parent.reLoad();
            var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
            parent.layer.close(index);
            } else {
               	parent.layer.alert(data.msg)
              }
        },
        error:function(){
            console.log("后台处理错误");
            parent.layer.alert("服务异常，请稍后再试");
        }
    })
}


// $("#uploadbtn").on("change",function(){
//
//    // var reads = new FileReader();
//     var fileObj = $(this).get(0).files[0];
//     var rep = /apk|ipa/ig;
//     var gstyle = fileObj.type.split("/")[1];
//     if(rep.test(gstyle)){
//         reads.readAsDataURL(f);
//         var that = this;
//         reads.onload = function(e) {
//         $(that).parent().find("img").attr("src",this.result)
//         };
//         var formData = new FormData();
//         formData.append('file', fileObj);
//     }else{
//         layer.msg("图片格式不正确，请上传 jpeg|png|gif|bmp 格式的图片")
//     }
// });

