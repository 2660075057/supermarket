<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>实名认证</title>
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/lib/weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/css/jquery-weui.min.css">
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
    </style>
</head>
<body>
<div class="box-attestation">
    <div class="box-attestation-text">
        <p><i class="weui-icon-info"></i>验证手机号以完成实名认证</p>
    </div>
    <div class="weui-cells weui-cells_form">
        <div class="weui-cell">
            <div class="weui-cell__hd"><label class="weui-label">+86</label></div>
            <div class="weui-cell__bd">
                <input id="phone" class="weui-input" type="number" pattern="[0-9]*" placeholder="请输入手机号码">
            </div>
        </div>
    </div>
    <div class="box-attestation-btn">
        <button class="weui-btn weui-btn_default">下一步</button>
    </div>
</div>


<script src="${ctx}/wechatPage/assets/jquery.min.js"></script>
<script src="${ctx}/wechatPage/assets/weui/js/jquery-weui.min.js"></script>
<c:if test="${!hasAuth}">
<script type="text/javascript">
    var working = false;

    $(".box-attestation-btn").click(function () {
        var phone = $('#phone').val().trim();

        if (!/^1[3|4|5|8][0-9]\d{4,8}$/.test(phone)) {
            $.toptip('请输入正确的手机号码', 'warning');
            return;
        }
        $.confirm({
            title: '确认手机号码',
            text: '向' + phone + '发送短信验证码',
            onOK: function () {
                if (working) {
                    return;
                }
                working = true;
                $.post('${ctx}/wechat/attestation/sendRegisterMessage', {phone: phone}, function (data) {
                    working = false;
                    if (data.code == 0) {
                        if (data.data == 0) {
                            window.location.href = '${ctx}/wechat/attestation/verify?phone=' + phone;
                            return;
                        } else if (data.data == 1) {
                            $.toptip('验证码发送过快，请稍后再试', 'warning');
                            return;
                        }
                    }
                    $.toptip('发送验证码失败，请重试', 'error');
                }, 'json');

            },
            onCancel: function () {
            }
        });
    });

</script>
</c:if>
<c:if test="${hasAuth}">
    <script>
        $(function () {
            $.alert("您已经通过实名认证了，无需重复认证", "提示", function () {
                if (document.referrer === '') {
                    WeixinJSBridge.call('closeWindow');
                } else {
                    window.history.go(-1);
                }
            });
        });
    </script>
</c:if>
</body>
</html>
