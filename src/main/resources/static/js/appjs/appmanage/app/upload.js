var preUrl = "/appmanage/app"
$(function() {

});

// $.validator.setDefaults({
// 	submitHandler : function() {
// 		save();
// 	}
// });
function upload() {
    // $.ajax({
    //     type:"post",
    //     url:preUrl+"/upload",
    //     data : $('#signupForm').serialize(),
    //
    //     dataType: 'text', //返回值类型 一般设置为json
    //    // contentType: "application/x-www-form-urlencoded; charset=utf-8",
    //     contentType: false,
    //     processData: false,  // jQuery不要去处理发送的数据
    //     success:function(res){
    //         var data = $(res).find("body").text();//获取返回的字符串
    //         var json = $.parseJSON(data);//把字符串转化为json对象
    //         if (json.code == 0) {
    //          alert(123456);
    //         console.log(json);
    //         parent.layer.msg("操作成功");
    //         parent.reLoad();
    //         var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
    //         parent.layer.close(index);
    //         } else {
    //            	parent.layer.alert(json.msg)
    //           }
    //     },
    //     error:function(){
    //         alert(654321);
    //         console.log("后台处理错误");
    //         parent.layer.alert("服务异常，请稍后再试");
    //     }
    // })


    if($('#appfile').val() == ''){
        //检查input[type=file]是否有值
        console.log('请选择APP再进行上传操作！');
        layer.msg("请选择APP再进行上传操作！");

    }else{
        var formdata  = new FormData();
        var appfile = $("#appfile");
        formdata.append('file',$("#appfile").get(0).files[0]);
        $.ajax({
            url: preUrl+"/upload",
            data: formdata,
            type: "POST",
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function(data) {
                console.log(JSON.stringify(data));
                if (data.code == 0) {
                  parent.layer.msg("上传成功！");
                  parent.reLoad();
                  var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                  parent.layer.close(index);
                } else {
                   	parent.layer.alert(data.msg)
                    }
            },
            error: function(data) {
                console.log(data.status);
                console.log("后台处理错误");
                parent.layer.alert("服务异常，请稍后再试");
            }
        });
    }
}

