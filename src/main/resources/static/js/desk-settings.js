//自定义js

//公共配置

var preUrl = "/system/desksettings";
var settingsItem = "default-skin";
var fixedNavBar = 0;
var collapsMenu = 0;
var boxedLayout = 0;

$(function () {

    // MetsiMenu
    $('#side-menu').metisMenu();

    // 打开右侧边栏
    $('.right-sidebar-toggle').click(function () {
        $('#right-sidebar').toggleClass('sidebar-open');
    });

    // 右侧边栏使用slimscroll
    $('.sidebar-container').slimScroll({
        height: '100%',
        railOpacity: 0.4,
        wheelStep: 10
    });

    // 打开聊天窗口
    $('.open-small-chat').click(function () {
        $(this).children().toggleClass('fa-comments').toggleClass('fa-remove');
        $('.small-chat-box').toggleClass('active');
    });

    // 聊天窗口使用slimscroll
    $('.small-chat-box .content').slimScroll({
        height: '234px',
        railOpacity: 0.4
    });

    // Small todo handler
    $('.check-link').click(function () {
        var button = $(this).find('i');
        var label = $(this).next('span');
        button.toggleClass('fa-check-square').toggleClass('fa-square-o');
        label.toggleClass('todo-completed');
        return false;
    });

    //固定菜单栏
    $(function () {
        $('.sidebar-collapse').slimScroll({
            height: '100%',
            railOpacity: 0.9,
            alwaysVisible: false
        });
    });


    // 菜单切换
    $('.navbar-minimalize').click(function () {
        $("body").toggleClass("mini-navbar");
        SmoothlyMenu();
    });


    // 侧边栏高度
    function fix_height() {
        var heightWithoutNavbar = $("body > #wrapper").height() - 61;
        $(".sidebard-panel").css("min-height", heightWithoutNavbar + "px");
    }
    fix_height();

    $(window).bind("load resize click scroll", function () {
        if (!$("body").hasClass('body-small')) {
            fix_height();
        }
    });

    //侧边栏滚动
    $(window).scroll(function () {
        if ($(window).scrollTop() > 0 && !$('body').hasClass('fixed-nav')) {
            $('#right-sidebar').addClass('sidebar-top');
        } else {
            $('#right-sidebar').removeClass('sidebar-top');
        }
    });

    $('.full-height-scroll').slimScroll({
        height: '100%'
    });

    $('#side-menu>li').click(function () {
        if ($('body').hasClass('mini-navbar')) {
            NavToggle();
        }
    });
    $('#side-menu>li li a').click(function () {
        if ($(window).width() < 769) {
            NavToggle();
        }
    });

    $('.nav-close').click(NavToggle);

    //ios浏览器兼容性处理
    if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
        $('#content-main').css('overflow-y', 'auto');
    }

    $.ajax({
        cache : true,
        type : "get",
        url : preUrl+"/info",
        data : "",// 你的formid
        async : false,
        error : function(request) {
           console.error(request);
        },
        success : function(data) {
            console.error(JSON.stringify(data));
            if (data.code == 0 && data.data !=null) {
                settingsItem = data.data.settingsItem;
                 fixedNavBar = data.data.fixedNavBar;
                 collapsMenu = data.data.collapsMenu;
                 boxedLayout = data.data.boxedLayout;
            }
        }
    });

});

$(window).bind("load resize", function () {
    if ($(this).width() < 769) {
        $('body').addClass('mini-navbar');
        $('.navbar-static-side').fadeIn();
    }
});

function NavToggle() {
    $('.navbar-minimalize').trigger('click');
}

function SmoothlyMenu() {
    if (!$('body').hasClass('mini-navbar')) {
        $('#side-menu').hide();
        setTimeout(
            function () {
                $('#side-menu').fadeIn(500);
            }, 100);
    } else if ($('body').hasClass('fixed-sidebar')) {
        $('#side-menu').hide();
        setTimeout(
            function () {
                $('#side-menu').fadeIn(500);
            }, 300);
    } else {
        $('#side-menu').removeAttr('style');
    }
}


//主题设置
$(function () {
    var body = $('body');
    // 顶部菜单固定
    $('#fixednavbar').click(function () {
        if ($('#fixednavbar').is(':checked')) {
            $(".navbar-static-top").removeClass('navbar-static-top').addClass('navbar-fixed-top');
            body.removeClass('boxed-layout');
            body.addClass('fixed-nav');
            $('#boxedlayout').prop('checked', false);
            boxedLayout = 0;
            fixedNavBar = 1;
        } else {
            $(".navbar-fixed-top").removeClass('navbar-fixed-top').addClass('navbar-static-top');
            body.removeClass('fixed-nav');
            fixedNavBar = 0;
        }
        save();
    });

    // 收起左侧菜单
    $('#collapsemenu').click(function () {
        if ($('#collapsemenu').is(':checked')) {
            body.addClass('mini-navbar');
            SmoothlyMenu();
            collapsMenu = 1;
        } else {
            body.removeClass('mini-navbar');
            SmoothlyMenu();
            collapsMenu = 0;
        }
        save();
    });

    // 固定宽度
    $('#boxedlayout').click(function () {
        if ($('#boxedlayout').is(':checked')) {
            body.addClass('boxed-layout');
            $('#fixednavbar').prop('checked', false);
            $(".navbar-fixed-top").removeClass('navbar-fixed-top').addClass('navbar-static-top');
            body.removeClass('fixed-nav');
            fixedNavBar = 0;
            boxedLayout = 1;
            } else {
            body.removeClass('boxed-layout');
            boxedLayout = 0;
        }
        save();
    });

    // 默认主题
    $('.setings-item.default-skin').click(function () {
        body.removeClass("skin-1");
        body.removeClass("skin-2");
        body.removeClass("skin-3");
        settingsItem = "default-skin";
        save();

        return false;
    });

    // 蓝色主题
    $('.setings-item.blue-skin').click(function () {
        body.removeClass("skin-2");
        body.removeClass("skin-3");
        body.addClass("skin-1");
        settingsItem = "blue-skin";
        save();

        return false;
    });

    // 黄色主题
    $('.setings-item.yellow-skin').click(function () {
        body.removeClass("skin-1");
        body.removeClass("skin-2");
        body.addClass("skin-3");
        settingsItem = "yellow-skin";
        save();
        return false;
    });

        if (collapsMenu == 1) {
            $('#collapsemenu').prop('checked', 'checked')
        }
        if (fixedNavBar == 1) {
            $('#fixednavbar').prop('checked', 'checked')
        }
        if (boxedLayout == 1) {
            $('#boxedlayout').prop('checked', 'checked')
        }
        if (collapsMenu == 1) {
            if (!body.hasClass('body-small')) {
                body.addClass('mini-navbar');
            }
        }
        if (fixedNavBar == 1) {
            $(".navbar-static-top").removeClass('navbar-static-top').addClass('navbar-fixed-top');
            body.addClass('fixed-nav');
        }

        if (boxedLayout == 1) {
            body.addClass('boxed-layout');
        }

        if(settingsItem == "default-skin"){
            body.removeClass("skin-1");
            body.removeClass("skin-2");
            body.removeClass("skin-3");
        }

        if(settingsItem == "blue-skin"){
            body.removeClass("skin-2");
            body.removeClass("skin-3");
            body.addClass("skin-1");
        }

        if(settingsItem == "yellow-skin"){
            body.removeClass("skin-1");
            body.removeClass("skin-2");
            body.addClass("skin-3");
        }
});

// //判断浏览器是否支持html5本地存储
// function localStorageSupport() {
//     return (('localStorage' in window) && window['localStorage'] !== null)
// }
// var collapse = localStorage.getItem("collapse_menu");
// var fixednavbar = localStorage.getItem("fixedNavBar");
// var boxedlayout = localStorage.getItem("boxedlayout");
// localStorage.setItem("boxedlayout", 'off');
// localStorage.setItem("fixedNavBar", 'off');
// localStorage.setItem("collapse_menu", 'off');

function save() {
    $.ajax({
        cache : true,
        type : "POST",
        url : preUrl+"/update",
        data : {"settingsItem":settingsItem,"fixedNavBar":fixedNavBar,"collapsMenu":collapsMenu,"boxedLayout":boxedLayout},
        async : false,
        error : function(request) {
            console.error(request);
        },
        success : function(data) {
            console.error(data);
        }
    });

}