<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>实名认证</title>
    <link rel="stylesheet" href="https://cdn.bootcss.com/weui/1.1.2/style/weui.min.css">
    <link rel="stylesheet" href="https://cdn.bootcss.com/jquery-weui/1.2.0/css/jquery-weui.min.css">
    <style type="text/css">
        body {
            padding: 0;
            margin: 0;
            background-color: #EEEEF3;
            color: #555555;
        }

        .box-attestation {
            position: relative;
            margin-left: auto;
            margin-right: auto;
        }

        .box-attestation .weui-cell {
            padding: 12px 15px;
        }

        .box-attestation .weui-cells {
            margin-top: 0;
        }

        .box-attestation-text {
            text-align: center;
            padding: 20px 0;
        }

        .box-attestation-text i {
            color: #9795e3;
        }

        .box-attestation-btn {
            padding: 0 15px;
        }

        .box-attestation-btn .weui-btn_default {
            margin: 80px auto 0;
            background-color: #9795e3;
            color: #ffffff;
            border: none;
        }

        .box-attestation-btn .weui-btn:focus, .box-attestation-btn .weui-btn:active,
        .box-attestation-btn .weui-btn:hover {
            background-color: #9795e3;
            color: #ffffff;
        }

        .code-false {
            margin: 20px auto;
            text-align: center;
            color: #9795e3;
        }
    </style>
</head>
<body>
<div class="box-attestation">
    <div class="box-attestation-text">
        <p>短信验证码已发送，请填写验证码</p>
    </div>
    <div class="weui-cells weui-cells_form">
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">手机号</label></div>
            <div class="weui-cell__bd">
                ${phone}
            </div>
        </div>
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">验证码</label></div>
            <div class="weui-cell__bd">
                <input id="vcode" class="weui-input" type="number" pattern="[0-9]*" placeholder="请输入验证码">
            </div>
        </div>
    </div>
    <div class="box-attestation-btn">
        <button class="weui-btn weui-btn_default" onClick="submit()">提交</button>
    </div>
    <div class="code-false"><a onClick="sendMessage()">重新发送验证码</a></div>
</div>
<script src="${ctx}/wechatPage/assets/jquery.min.js"></script>
<script src="${ctx}/wechatPage/assets/weui/js/jquery-weui.min.js"></script>
<script>
    var working = false;
    var verifyWorking = false;

    function submit() {
        var vcode = $('#vcode').val().trim();
        if (vcode.length == 0) {
            $.toptip('请输入验证码', 'warning');
            return;
        }

        if (verifyWorking) {
            return;
        }
        verifyWorking = true;

        $.post('${ctx}/wechat/attestation/verifyCode', {code: vcode}, function (data) {
            verifyWorking = false;
            if (data.code == 0) {
                if (data.data == 0) {
                    //具有进出门链接则跳转
                    <c:if test="${sessionScope.attestation != null && !sessionScope.attestation.isEmpty()}">
                    window.location.href = '${ctx}${sessionScope.attestation}';
                    </c:if>
                    //没有进出门链接，关闭浏览器
                    <c:if test="${sessionScope.attestation == null or sessionScope.attestation.isEmpty()}">
                    $.alert("身份证验证成功，请重新扫码", function () {
                        WeixinJSBridge.call('closeWindow');
                    });
                    </c:if>
                } else if (data.data == 1) {
                    $.toptip('验证码过期，请重新获取', 'warning');
                } else if (data.data == 2) {
                    $.toptip('验证码错误，请重新输入', 'warning');
                } else {
                    $.toptip('身份过期，请重新打开页面', 'error');
                }
            } else {
                $.toptip('提交失败，请稍后重试', 'error');
            }
        }, 'json');
    }

    function sendMessage() {
        var phone = '${phone}';
        if (working) {
            return;
        }
        working = true;
        $.post('${ctx}/wechat/attestation/sendRegisterMessage', {phone: phone}, function (data) {
            working = false;
            if (data.code == 0) {
                if (data.data == 0) {
                    $.toptip('验证码发送成功', 'success');
                    return;
                } else if (data.data == 1) {
                    $.toptip('验证码发送过快，请稍后再试', 'warning');
                    return;
                }
            }
            $.toptip('发送验证码失败，请重试', 'error');
        }, 'json');
    }
</script>
</body>
</html>