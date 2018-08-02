<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>支付成功</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/devicePage/assets/base.css"/>
</head>
<body <c:if test="${debug =='true'}">style="overflow: auto"</c:if>>
<div class="pay-md md-center text-center">
    <div class="title">
        <div class="sub">本次支付</div>
        ￥ <span class="font-primary" id="payment">0</span>
    </div>
    <i class="icon icon-success"></i>
    <div class="pay-txt">
        <div class="row" id="doorMessage"></div>
        <div class="row">欢迎您下次光临</div>
        <div class="row text-info"><span id="timeshow"></span>秒后将自动返回</div>
        <div class="row">
            <button class="btn btn-primary" id="openButton" onclick="openDoor()" style="display: none">立即开门</button>
            <button class="btn btn-primary" id="scanButton" onclick="scan()" style="display: none;background: #FF5722">
                重新扫描
            </button>
        </div>
    </div>
</div>
</body>
<script src="${ctx}/operator/assets/jquery.min.js"></script>
<script>
    function setPayData(payData) {
        var p = (payData.payment - payData.preferential) / 100.0;
        $('#payment').html(p);
    }

    function setOpenButton(isShow) {
        if (isShow) {
            $("#openButton").show();
        } else {
            $("#openButton").hide();
        }

    }

    function setScanButton(isShow) {
        if (isShow) {
            $("#scanButton").show();
        } else {
            $("#scanButton").hide();
        }
    }

    function setDoorMessage(str) {
        $("#doorMessage").html(str);
    }

    function openDoor() {
        setScanButton(false);
        setOpenButton(false);
        setDoorMessage('门禁已打开');
        JavaApplication.openDoor();
    }

    function scan() {
        JavaApplication.scan();
    }

    function toMain() {
        JavaApplication.toMain();
    }

    var tm;
    var t;
    function timeout(time) {
        if (tm) {
            clearInterval(tm);
        }
        t = time;
        $('#timeshow').html('' + t);
        tm = setInterval(function () {
            if (t == 0) {
                toMain();
                clearInterval(tm);
                return;
            }
            t--;
            $('#timeshow').html('' + t);
        }, 1000, 1000);
    }

    // $(function () {
    //
    // });
    timeout(60);
</script>
</html>