


function install_loading(type) {


    if (aType == '1') {
        if (!isMobileRequest) {
            alert(askBrowserAlert);
            return;
        }
    }
    if (isUCRequest && !isIOS) {
        alert(reminderUCContent);
        return ;
    }

    if (!isMobileRequest) {
        alert(askBrowserAlert);
        return;
    }
    if (aType == '2' && browseType == 'android') {
        alert(forIosAlert);
        return;
    }else if (aType == '1' && browseType == 'ios') {
        alert(forAndroidAlert);
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
        setTimeout(check(),5000);
        $(".loading").css("display","inline-block");

        url = resquestIp+"/portal/app/install/"+$("#download-href_"+type).val();
    }
    if (type == '1'){
        $("#down_load_type_1").hide();
        setTimeout(check(),5000);
        $(".loading").css("display","inline-block");

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

function isWeiXin(){
    var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
        return false;
    }
}
function isMobile() {
    return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent);
}


function check() {
    $(".loading").hide();
    $("#showtext1").show();
    $("#showtext2").show();
}

function init(){

    $("#showtext1").hide();
    $("#showtext2").hide();
    if(isWeiXin()){
        //weixin为提示使用浏览器打开的div
        setTimeout(function () {
            $('.view-alert').slideDown(1000);
        }, 200);
        if(browser.versions.ios || browser.versions.iPhone || browser.versions.iPad){
            document.getElementById("tiptext").innerHTML="请点击微信右上角按钮，然后在弹出的菜单中，点击在Safari浏览器中打开，即可下载";
        }else{
            document.getElementById("tiptext").innerHTML=reminderWechatDownloadContent;
        }
    }


    if(browser.versions.ios || browser.versions.iPhone||browser.versions.iPad){
        document.getElementById("iosDiv").style.display="block";
        document.getElementById("iosDiv1").style.display="none";
    }
    if(browser.versions.android){
        document.getElementById("androidDiv").style.display="block";
        document.getElementById("androidDiv1").style.display="none";
    }
    if(!browser.versions.android&&!browser.versions.ios){
        document.getElementById("iosDiv").style.display="block";
        document.getElementById("androidDiv").style.display="block";
    }
}

function checkIsUc() {
    var browser = navigator.userAgent;

    // 是否为safari
    if (/Safari/.test(browser) && !isChrome) {

        var ua = browser.toLowerCase();
        var m = ua.match(/cpu iphone os (.*?) like mac os/);
        var ver;

        if (!m) {
            return false;
        } else {
            ver = m[1].replace(/_/g,".");
        }

        var osVer = ver.split('.')[0] + '.' + ver.split('.')[1]

        // 是否有apple
        if (typeof window.ApplePaySession === 'function') {
            return false;
        } else if ( osVer && osVer <= 11.2){
            return false;
        } else {
            return true;
        }
    } else {
        return false;
    }
}

function isNeedCheckUC(){
    return isUCRequest?!1:isIOS&&isMobileRequest?isWechatRequest||isWeiboRequest||isQQRequest?!1:!0:!1
}


