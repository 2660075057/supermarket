<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>微信超市</title>
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/lib/weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/css/jquery-weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/base.css">
    <script src="${ctx}/wechatPage/assets/weui/lib/zepto.js"></script>
    <script src="${ctx}/wechatPage/assets/weui/lib/fastclick.js"></script>
    <script src="${ctx}/wechatPage/assets/weui/js/jquery-weui.min.js"></script>
    <script src="${ctx}/wechatPage/assets/base.js"></script>
</head>
<body>
<div class="search-page md-bg">
    <div class="header-search">
        <div class="wrap">
            <i class="icon icon-search"></i>
            <input type="text" placeholder="请输入关键词搜索" id="keyword" />
            <button type="submit" id="submit" class="button">搜索</button>
        </div>
    </div>
    <div class="header-search-bar">
        <div class="weui-flex">
            <div class="weui-flex__item text-center active" id="shop">
                <span id="search-city">全部商店</span>
                <i class="icon icon-arrow"></i>
            </div>
            <div class="line-y"></div>
            <div class="weui-flex__item text-center" id="type">
                <span id="search-area">全部分类</span>
                <i class="icon icon-arrow"></i>
            </div>
        </div>
    </div>
    <ul class="side-nav search-nav" id="shopx">
        <li class="active">
            <input type="hidden" value="-1" id="shopid">
            <span>全部商店</span>
            <span class="weui-cells_checkbox"><i class="weui-icon-checked"></i></span>
        </li>
        <c:forEach var="shop" items="${shoplist}">
            <li><input type="hidden" value="${shop.shopId}"><span>${shop.shopName}</span> <span class="weui-cells_checkbox"><i class="weui-icon-checked"></i></li>
        </c:forEach>
    </ul>
    <ul class="side-nav search-nav" id="typex">
        <li class="active">
            <input type="hidden" value="-1" id="typeid">
            <span>全部分类</span>
            <span class="weui-cells_checkbox"><i class="weui-icon-checked"></i></span>
        </li>
        <c:forEach var="type" items="${typelist}">
            <li><input type="hidden" value="${type.typeId}"><span>${type.typeName}</span> <span class="weui-cells_checkbox"><i class="weui-icon-checked"></i></li>
        </c:forEach>
    </ul>
</div>
<script>
    $(function () {
        $("#typex").hide();
    });
    $("#keyword").on("focus",function(){
        $(".button").addClass("button-active")
    });
    $("#keyword").on("blur",function(){
        if(!$("#keyword").val()){
            $(".button").removeClass("button-active")
        }
    });
    $("#shopx li").on("click",function(){
        $(this).children("input").attr("id","shopid");
        $(this).siblings().children("input").attr("id","");
        $(this).addClass("active").siblings().removeClass("active");
        $("#search-city").html($(this).find("span").html());
        $(".modal-picker").hide();
    });
    $("#typex li").on("click",function(){
        $(this).children("input").attr("id","typeid");
        $(this).siblings().children("input").attr("id","");
        $(this).addClass("active").siblings().removeClass("active");
        $("#search-area").html($(this).find("span").html());
        $(".modal-picker").hide();
    });

    $("#type").on("click",function(){
        $("#type").addClass("active");
        $("#shop").removeClass("active");
        $("#shopx").hide();
        $("#typex").show();
    });
    $("#shop").on("click",function(){
        $("#shop").addClass("active");
        $("#type").removeClass("active");
        $("#shopx").show();
        $("#typex").hide();
    });

    $("#submit").on("click",function(){
        var shopid = $("#shopid").val();
        var typeid = $("#typeid").val();
        var queryVal = $("#keyword").val();
        window.location.href = '${ctx}/wechat/commodity?shopId='+shopid+'&typeId='+typeid+'&queryVal='+queryVal
    });
</script>
</body>
</html>
