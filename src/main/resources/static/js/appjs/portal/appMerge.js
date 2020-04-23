


function install_merge_loading(type) {


    if (!isMobileRequest) {
        alert(askBrowserAlert);
        return;
    }
    if (aType == '2' && browseType == '1') {
        alert(forIosAlert);
        return;
    }
    if ( isWechatRequest) {
        alert(reminderWechatContent);
        return;
    }
    if ( isWeiboRequest) {
        alert(reminderWeiboContent);
        return;
    }
    if (isQQRequest && aType == '1') {
        alert(reminderQQContent);
        return ;
    }
    if (type == '2'){

        $("#down_load_type_2").hide();
        $(".loading").css("display","inline-block");
        setTimeout('check()',5000);
        url = resquestIp+"/portal/app/install/"+$("#download-href_"+type).val();
    }
    if (type == '1'){
        $("#down_load_type_1").hide();
        $(".loading").css("display","inline-block");
        setTimeout('check()',5000);
        url = resquestIp+"/portal/app/install/"+$("#download-href_"+type).val();
    }
    window.location.href = url;
}


var browser={
    versions:function(){
        var u = navigator.userAgent, app = navigator.appVersion;
        return {
            trident: u.indexOf('Trident') > -1,
            presto: u.indexOf('Presto') > -1,
            webKit: u.indexOf('AppleWebKit') > -1,
            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,
            mobile: !!u.match(/AppleWebKit.*Mobile.*/),
            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/),
            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1,
            iPhone: u.indexOf('iPhone') > -1 ,
            iPad: u.indexOf('iPad') > -1,
            webApp: u.indexOf('Safari') == -1
        }
    }(),
    language:(navigator.browserLanguage || navigator.language).toLowerCase()
};
function is_weixin(){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        return true;
    } else {
        return false;
    }
}


function init(){
    if(is_weixin()){
        //weixin为提示使用浏览器打开的div
        document.getElementById("weixin").style.display="block";
        if(browser.versions.ios || browser.versions.iPhone || browser.versions.iPad){
            document.getElementById("step2").innerHTML="2. 在Safari中打开";
        }else{
            document.getElementById("step2").innerHTML="2. 在浏览器中打开";
        }
    }else{
        //下载页div
         document.getElementById("main").style.display="block";
    }
    if(browser.versions.ios || browser.versions.iPhone||browser.versions.iPad){
        document.getElementById("iosDiv").style.display="block";
    }
    if(browser.versions.android){
        document.getElementById("androidDiv").style.display="block";
    }
    if(!browser.versions.mobile){
        document.getElementById("iosDiv").style.display="block";
        document.getElementById("androidDiv").style.display="block";
    }
}




