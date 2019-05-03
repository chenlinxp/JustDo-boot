/*this is basic form validation using for validation.extend*/
$(document).ready(function(){
    // 中文的验证
	jQuery.validator.addMethod("chinese", function(value, element) {
         var chinese = /^[\u4e00-\u9fa5]+$/;
         return this.optional(element) || (chinese.test(value));
        }, "只能输入中文");

    // IP地址验证
    jQuery.validator.addMethod("ip", function(value, element) {
        var ip = /^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
        return this.optional(element) || (ip.test(value) && (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256));
         }, "Ip地址格式错误");

    //手机号码验证身份证正则合并：(^\d{15}$)|(^\d{17}([0-9]|X)$)
	jQuery.validator.addMethod("phone",function(value,element){
		var length = value.length;
		var phone=/^1[3|4|5|7|8][0-9]\d{8}$/;
		return this.optional(element)||(length == 11 && phone.test(value));
	},"请填写正确的11位手机号");

   // 电话号码验证
	jQuery.validator.addMethod("telephone", function(value, element) {
		var tel = /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/;
        return this.optional(element) || (tel.test(value));
       }, "电话号码格式错误");

	// 邮政编码验证
    jQuery.validator.addMethod("zipCode", function(value, element) {
        var tel = /^[0-9]{6}$/;
        return this.optional(element) || (tel.test(value));
       }, "邮政编码格式错误");

    //电话号码验证
	jQuery.validator.addMethod("telephone2",function(value,element){
		var tel = /^(0\d{2,3}-)?\d{7,8}$/g;//区号3,4位,号码7,8位
		return this.optional(element) || (tel.test(value));
	},"请填写正确的座机号码");

	//姓名校验
	jQuery.validator.addMethod("realName",function(value,element){
		var name=/^[\u4e00-\u9fa5]{2,6}$/;
		return this.optional(element) || (name.test(value));
	},"姓名只能用汉字,长度2-4位");

	//校验用户名
	jQuery.validator.addMethod("userName",function(value,element){
		var userName=/^[a-zA-Z0-9]{2,13}$/;
		return this.optional(element) || (userName).test(value);
	},'请输入数字或者字母,不包含特殊字符');
	
	//校验身份证
	jQuery.validator.addMethod("IDCard",function(value,element){
		var id= /^(\d{15}$|^\d{18}$|^\d{17}(\d|X))$/;
		return this.optional(element) || (id.test(value));
	},"请输入正确的15或18位身份证号,末尾为大写X");

	//校验出生日期
	jQuery.validator.addMethod("birthday",function(value,element){
		var birth = /^(19|20)\d{2}-(1[0-2]|0?[1-9])-(0?[1-9]|[1-2][0-9]|3[0-1])$/;
		return this.optional(element) || (birth).test(value);
	},"出生日期格式示例2000-01-01");

   // 字母和数字的验证
   jQuery.validator.addMethod("chrnum", function(value, element) {
        var chrnum = /^([a-zA-Z0-9]+)$/;
        return this.optional(element) || (chrnum.test(value));
         }, "只能输入数字和字母(字符A-Z, a-z, 0-9)");

	// 下拉框验证
   $.validator.addMethod("selectNone", function(value, element) {
       return value == "请选择";
       }, "必须选择一项");
    //校验新旧密码是否相同
	jQuery.validator.addMethod("isdiff",function(){
		var p1=$("#pwdOld").val();
		var p2=$("#pwdNew").val();
		if(p1==p2){
			return false;
		}else{
			 return true;
		}
		});
	//校验新密码和确认密码是否相同
	jQuery.validator.addMethod("issame",function(){
		var p3=$("#confirm_password").val();
		var p4=$("#pwdNew").val();
		if(p3==p4){
			return true;
		}else{
			 return false;
		}
		});
    // 项目包名的验证^([a-zA-Z]+[.][a-zA-Z]+)[.]*.*
    jQuery.validator.addMethod("package", function(value, element) {
        var p = /^([com]+[.][a-zA-Z]+)[.]*.*$/;
        return this.optional(element) || (p.test(value));
    }, "只能输入com.开头的包名");

    // 项目版本号的验证 ^(.+?\s+)(((\d+(\.\d+)*)|(BN\d+)).*?)$
    jQuery.validator.addMethod("version", function(value, element) {
        var p = /^(\d+)[.](\d+)[.](\d+)$/;
        return this.optional(element) || (p.test(value));
    }, "请输入正确的版本号");

});