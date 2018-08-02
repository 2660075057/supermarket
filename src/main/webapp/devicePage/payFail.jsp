<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>支付失败</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/devicePage/assets/base.css" />
</head>
<body <c:if test="${debug=='true'}">style="overflow: auto"</c:if> >
<div class="pay-md md-center text-center">
    <div class="title">
        <div class="sub">本次购物支付</div>
        ￥ <span class="font-primary" id="payment"></span>
    </div>
    <i class="icon icon-fail"></i>
    <div class="pay-txt">
        <div class="row">付款失败</div>
        <div class="row">&nbsp;</div>
        <div class="row text-info">请点击稍后再试</div>
        <div class="row">
            <button class="btn btn-primary" onclick="execute()">
                返回（<span id="timeshow"></span>秒）
            </button>
        </div>
    </div>
</div>
</body>
<script src="${ctx}/operator/assets/jquery.min.js"></script>
<script>
    function setPayData(payData) {
        var p = (payData.payment - payData.preferential)/100.0;
        $('#payment').html(p);
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

    function execute() {
        JavaApplication.execute();
    }

    // $(function () {
    //
    // });
    timeout(20);
</script>
</html>