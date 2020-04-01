/**
 * Created by chenlin on 2019/11/2.
 */
var sock = new SockJS("/justdo-websocket");
var stompClient = Stomp.over(sock);
$(function () {
    connect();
});

function connect() {
    stompClient.connect( { name:$("#userid").val()},
        function connectCallback(frame) {
            // 连接成功时（服务器响应 CONNECTED 帧）的回调方法
            console.info("success");
            subscribe1();
            subscribe2();
        },
        function errorCallBack(error) {
            // 连接失败时（服务器响应 ERROR 帧）的回调方法
            console.info("error");
        });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
//      setConnected(false);
    console.log("Disconnected");
}

//订阅消息
/**  订阅了/user/queue/notifications 发送的消息,这里雨在控制器的 convertAndSendToUser 定义的地址保持一致, 
 *  这里多用了一个/user,并且这个user 是必须的,使用user 才会发送消息到指定的用户。 
 *  */
function subscribe1() {
    stompClient.subscribe('/justdo/queue/message', function (response) {
        console.log("/justdo/queue/message 你接收到的消息为:" + response);
        wrapper.notice();
        toastr.info(JSON.parse(response.body).messageTitle);
    });

}

//订阅消息
//订阅/topic/getResponse 目标发送的消息。这个是在控制器的@SendTo中定义的。
function subscribe2() {
    stompClient.subscribe('/topic/greetings', function (response) {
        toastr.options = {
            "closeButton": true,
            "debug": false,
            "progressBar": true,
            "positionClass": "toast-bottom-left",
            "onclick": null,
            "showDuration": "400",
            "hideDuration": "1000",
            "timeOut": "7000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut"
        }
        toastr.info(JSON.parse(response.body).responseMessage.messageTitle);
    });

}

/**
 * 发送用户信息
 * */
function send0() {
    stompClient.send("/app/msg.welcome", {},
        {});
}

/**
 * 发送JSON数据体
 * */
function send() {
    send3();
    stompClient.send("/app/msg.welcome", {"name":"1"},
        JSON.stringify({
            "messageId":"www",
            "messageTitle":"rrrrr",
            "messageContent":"eergg"
        }));
}

/**
 * 发送路径参数
 * */
function send2() {
    stompClient.send("/app/path/zhangsan/XXX公司", {},
        {});
}

/**
 * 发送header参数
 * */
function send3() {
    stompClient.send("/app/msg.sendPointToPoint", {"name":"1", "two":"中国"},
        JSON.stringify({
            "messageId":"www",
            "messageTitle":"rrrrr",
            "messageContent":"eergg"
        }));
}

/**
 * 发送httpsession
 * */
function send4() {
    stompClient.send("/app/httpsession", {},
        {});
}

// /**
//  * 发送URL中?&参数
//  * */
// function send5() {
//     stompClient.send("/app/param?name=张三", {},
//         {});
// }

var wrapper = new Vue({
    el: '#wrapper',
    data: {
        total: '',
        rows: '',
    },
    methods: {
        notice: function () {
            $.getJSON('/system/notice/message', function (r) {
                wrapper.total = r.total;
                wrapper.rows = r.rows;
            });
        },
        personal: function () {
            layer.open({
                type: 2,
                title: '个人设置',
                maxmin: true,
                shadeClose: false,
                area: ['800px', '600px'],
                content: '/system/employee/personal'
            });
        }
    },
    created: function () {
        this.notice()
    }
})