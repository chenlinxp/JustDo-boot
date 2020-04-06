function isMobile() {
    return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent);
}
function isWeiXin(){
    var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
        return false;
    }
}
function install() {

    // 未认证用户不可下载应用
    if (downloadOnlyForCertification && !userIsCertification) {
        alert(unableDownload);
        return false;
    }

    if (aType == 'ios') {
        if (!isMobileRequest) {
            alert(askBrowserAlert);
            return;
        }
    }

    if (aType == 'ios' && browseType == 'android') {
        alert(forIosAlert);
        return;
    } else if (aType == 'android' && browseType == 'ios') {
//        alert(forAndroidAlert);
//        return;
    }

//    if ( isWechatRequest && aType == 'android') {
    if ( isWechatRequest) {
        alert(reminderWechatContent);
        return;
    }

    if (isQQRequest && aType == 'android') {
        alert(reminderQQContent);
        return ;
    }

    if (isUCRequest && !isIOS) {
        alert(reminderUCContent);
        return ;
    }

    url = "/app/install/" + aKey;
    window.location.href = url;
}

function install_loading() {

    // 未认证用户不可下载应用
    if (downloadOnlyForCertification && !userIsCertification) {
        alert(unableDownload);
        return;
    }

    if (aType == 'ios') {
        if (!isMobileRequest) {
            alert(askBrowserAlert);
            return;
        }
    }

    if (aType == 'ios' && browseType == 'android') {
        alert(forIosAlert);
        return;
    } else if (aType == 'android' && browseType == 'ios') {
//        alert(forAndroidAlert);
//        return;
    }

//    if ( isWechatRequest && aType == 'android') {
    if ( isWechatRequest) {
        alert(reminderWechatContent);
        return;
    }

    if ( isWeiboRequest) {
        alert(reminderWeiboContent);
        return;
    }

    if (isQQRequest && aType == 'android') {
        alert(reminderQQContent);
        return ;
    }

    if (isUCRequest && !isIOS) {
        alert(reminderUCContent);
        return ;
    }

    if(aType == 'ios'){
        $("#down_load").hide();
        $(".loading").css("display","inline-block");
        setTimeout('check()',5000);
    }

    url = "/app/install/" + aKey;
    window.location.href = url;
}
 function check() {
    $(".loading").hide();
    $("#showtext").show();
 }


