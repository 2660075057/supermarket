<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>404</title>
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/lib/weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/css/jquery-weui.min.css">
    <style type="text/css">
        body{
            margin: 0;
            padding: 0;
            background-color: #F4F4F4;
        }
        .err-box{
            position: relative;
            padding-top: 35%;
            text-align: center;

        }
        .err-img img{
            position: relative;
            width:60%;
        }
        .err-text p{
            color: #939393;
            font-size:24px;
            font-weight: 600;
            padding:10px 0;
            letter-spacing: 2px;
            font-family: "YouYuan";
        }
        .err-btn{
            width: 62%;
            margin: 0 auto;
            color: #ffffff;
            padding:15px 15px 0 ;
            border-radius: 50px;
            font-size:24px;
            font-weight: 600;
        }
        .err-btn .weui-btn_default{
            background-color:#9795e3;
            color: #ffffff;
            border-radius: 30px;
        }
        .err-btn .weui-btn:focus,.err-btn .weui-btn:active,
        .err-btn .weui-btn:hover{
            background-color:#9795e3;
            color: #ffffff;
        }
    </style>
</head>
<body>
<div class="err-box">
    <div class="err-img"> <img src="${ctx}/wechatPage/images/404.png"></div>
    <div class="err-text">
        <p>出错啦，请稍后再试</p>
    </div>
    <div class="err-btn">
        <button class="weui-btn weui-btn_default" onclick="back()">返回</button>
    </div>
</div>
<script src="${ctx}/wechatPage/assets/weui/js/jquery-weui.min.js"></script>
<script>
    function back() {
        if (document.referrer === '') {
            WeixinJSBridge.call('closeWindow');
        } else {
            window.history.go(-1);
        }
    }
</script>
</body>
</html>