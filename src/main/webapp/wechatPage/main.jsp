<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>首页</title>
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/lib/weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/css/jquery-weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/base.css">
    <script src="${ctx}/wechatPage/assets/weui/lib/zepto.js"></script>
    <script src="${ctx}/wechatPage/assets/weui/lib/fastclick.js"></script>
    <script src="${ctx}/wechatPage/assets/weui/js/jquery-weui.min.js"></script>
    <script src="${ctx}/wechatPage/assets/base.js"></script>
</head>
<body>
    <div class="home">
        <div class="clearfix home-top">
            <div class="pull-left">
                葡萄盒子24小时自助超市
            </div>
            <div class="pull-right font12">
                ${GRAPE_WECHAT_SESSION_INFO_ID.nickname} <i class="icon icon-user"></i>
            </div>
        </div>
        <div class="home-banner">
            <div class="swiper-container">
                <div class="swiper-wrapper">
                    <c:if test="${banners.isEmpty()}">
                        <div class="swiper-slide"><img src="${ctx}/wechatPage/images/home-banner-1.png"/></div>
                    </c:if>
                    <c:forEach var="banner" items="${banners}">
                        <div class="swiper-slide" >
                            <img style="max-height:216px" src="${banner.imgUrl}" <c:if test="${banner.url != null}">onclick="javaScript:window.location.href='${banner.url}'"</c:if> />
                        </div>
                    </c:forEach>
                </div>
                <!-- Add Pagination -->
                <div class="swiper-pagination"></div>
            </div>
        </div>
        <div class="home-nav">
            <div class="line-xy"></div>
            <ul class="clearfix">
                <li><a href="${ctx}/wechat/shop"><i class="icon icon-home-near"></i>附近商店</a></li>
                <li><a href="${ctx}/wechat/commodity"><i class="icon icon-home-search"></i>商品查询</a></li>
                <li><a href="${ctx}/wechat/myOrder"><i class="icon icon-home-record"></i>购买记录</a></li>
                <li><a href="${ctx}/wechat/message"><i class="icon icon-home-leave"></i>留言记录</a></li>
            </ul>
        </div>
    </div>
    <script type='text/javascript' src='${ctx}/wechatPage/assets/weui/js/swiper.min.js'></script>
    <script>
        var swiper = new Swiper('.swiper-container', {
            pagination: {
                el: '.swiper-pagination'
            }
        });
    </script>
</body>
</html>
