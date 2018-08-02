<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>扫码结算</title>
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/lib/weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/css/jquery-weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/base.css">
    <script src="${ctx}/wechatPage/assets/jquery.min.js"></script>
    <script src="${ctx}/wechatPage/assets/weui/js/jquery-weui.min.js"></script>
</head>
<body>
<div class="welcome">
    <div class="welcome-title">
        欢迎再次光临
        <div class="sub" id="message">点击开门按钮进入结算区</div>
    </div>
    <div class="btns">
        <a onClick="outShop()" class="btn-open" id="step-1"> <i class="icon icon-open"></i></a>
        <a href="javascript:;" class="btn-in" style="display:none" id="step-3"> <i class="icon icon-in"></i></a>
    </div>
</div>
<script>
    var TIME_OUT = ${timeout};
    var nowTime = 0;
    var handle;

    function outShop() {
        $.post('${ctx}/wechat/shop/visit?flag=false', {}, function (data) {
            if (data.code == 0) {
                if (data.data == 1) {
                    if (handle) {
                        clearInterval(handle);
                    }
                    $.showLoading('正在打开门禁');
                    nowTime = (new Date().getTime());
                    handle = setInterval(result, 800);
                } else if (data.data == 2) {
                    $.toptip('门禁准备中，请稍后再试', 'warning');
                } else {
                    $.toptip('此站点暂时无法提供服务', 'error');
                }
            } else {
                $.toptip('请重新扫描二维码', 'warning');
            }
        }, 'json');
    }

    var work = false;

    function result() {
        if (work) {
            return;
        } else {
            work = true;
        }
        //检查轮询是否超时
        if (nowTime + TIME_OUT < (new Date().getTime())) {
            $.hideLoading();
            $.toptip('开启门禁超时，请重试', 'warning');
            return;
        }
        $.post('${ctx}/wechat/shop/visitState', {}, function (data) {
            work = false;
            if (data.code == 0) {
                if (data.data.state == 1) {
                    clearInterval(handle);
                    $.hideLoading();
                    $("#step-1").hide();
                    $("#step-3").show();
                    $("#message").html('门禁已经解除');
                    // $.toptip('开门成功', 'success');
                    setTimeout(closeWindow,3000);
                } else if (data.data.state == 2) {
                    clearInterval(handle);
                    $.hideLoading();
                    $.toptip('此站点暂时无法提供服务', 'error');
                }
            } else {
                clearInterval(handle);
                $.toptip('开门失败，请重试', 'error');
                $.hideLoading();
            }
        }, 'json');
    }

    function closeWindow() {
        WeixinJSBridge.call('closeWindow');
    }

    $(function () {
        function pushHistory() {
            var state = {
                title: "title",
                url: "#"
            };
            window.history.pushState(state, "title", "#");
        };
        pushHistory();
        window.onpopstate = function() {
            WeixinJSBridge.call('closeWindow');
        };
    });
</script>
</body>
</html>