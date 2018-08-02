<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>首页</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/devicePage/assets/base.css" />
</head>
<body <c:if test="${debug =='true'}">style="overflow: auto"</c:if>>
<div class="index-md md-center">
    <div class="title">葡萄盒子24小时自助超市</div>
    <div class="list">
        <ul class="clearfix">
            <li>
                商品扫描
                <div class="sub">将商品放置于感应区扫描</div>
            </li>
            <li>
                确认商品
                <div class="sub">确认购买商品信息是否无误</div>
            </li>
            <li>
                扫码支付
                <div class="sub">扫描屏幕支付二维码付款</div>
            </li>
        </ul>
    </div>
</div>
</body>
</html>