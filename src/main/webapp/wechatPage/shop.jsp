<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>附近商店</title>
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/lib/weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/css/jquery-weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/base.css">
    <script src="${ctx}/wechatPage/assets/weui/lib/zepto.js"></script>
    <script src="${ctx}/wechatPage/assets/weui/lib/fastclick.js"></script>
    <script src="${ctx}/wechatPage/assets/weui/js/jquery-weui.min.js"></script>
    <script src="${ctx}/wechatPage/assets/base.js"></script>
</head>
<body>
    <div class="shops">
        <header class="header-fixed">
            <div class="search-page md-bg">
                <div class="header-search">
                    <div class="wrap">
                        <i class="icon icon-search"></i>
                        <input type="search" placeholder="请输入关键词搜索" id="shopName" />
                        <button type="submit" id="search" class="button button-active">搜索</button>
                    </div>
                </div>
            </div>
        </header>
        <div class="header-fixed-gap"></div>
        <!-- <div class="search-tips text-center">
            搜索“葡萄”共13间相关店铺
        </div> -->
        <div class="weui-re-list">
            <div class="weui-panel">
                <div class="weui-panel__bd" id="goods">
                    <input type="hidden" id="count" value="${shopList.size()}">
                    <c:forEach var="shop" items="${shopList}">
                        <a href="${ctx}/wechat/goods?shopId=${shop.shopId}" class="weui-media-box weui-media-box_appmsg">
                            <div class="weui-media-box__hd scale-1px">
                                <img class="weui-media-box__thumb" src="${ctx}/wechatPage/images/good-off-find.png"
                                     alt="">
                            </div>
                            <div class="weui-media-box__bd">
                                <h4 class="weui-media-box__title">${shop.shopName}</h4>
                                <c:if test="${shop.distance != null}">
                                    <c:choose>
                                        <c:when test="${shop.distance>1000}">
                                            <c:set var="distance" value="${shop.distance/1000.0}"/>
                                            <fmt:formatNumber var="distance" value="${distance}" maxFractionDigits="2" groupingUsed="false"/>
                                            <c:set var="unit" value="千米"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="distance" value="${shop.distance.intValue()}"/>
                                            <c:set var="unit" value="米"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <p class="color-primary">距离您${distance}${unit}</p>
                                </c:if>
                                <p class="color-sub font12">${shop.address}</p>
                            </div>
                        </a>
                    </c:forEach>
                </div>
            </div>
             <!-- 无限加载 -->
             <div class="weui-loadmore">
                <i class="weui-loading"></i>
                <span class="weui-loadmore__tips">加载中…</span>
            </div>
        </div>
    </div>
    <script>
        var loading = false;  //状态标记
        var tem = 1;
        var pageSize = 11;
        var count = 0;
        $(function () {
            count = $('#count').val();
            if(count < pageSize){
                $('.weui-loadmore').hide();
            }else{
                $(document.body).infinite().on("infinite", function() {
                    if(loading) return;
                    loading = true;
                    setTimeout(function() {
                        /*var loadHtml = '<a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">' +
                        $("#goods .weui-media-box").eq(0).html() + '</a>';
                        $("#goods").append(loadHtml);*/
                        tem +=1;
                        var param = {};
                        var shopName = $('#shopName').val();
                        if(shopName != null && shopName.length>0){
                            param["shopName"] = shopName;
                        }
                        var page = {};
                        page["pageSize"]=pageSize;
                        page["pageCurrent"]=tem;
                        var str = selectList(param,page);
                        if(str != null && str.length >0){
                            $("#goods").append(str);
                        }
                        if(count >= pageSize){
                            loading = false;
                        }else{
                            $('.weui-loadmore').hide();
                            $.toast("数据加载完毕", "text");
                            loading = true;
                        }
                    }, 1000);   //模拟延迟
                });
            }
        });

        function selectList(param,page) {
            count = 0;
            var str = "";
            $.ajax({
                url: "${ctx}/wechat/shop/shopListParam",
                type: "POST",
                async: false,
                data: {"page":JSON.stringify(page),"req":JSON.stringify(param)},
                success: function (data) {
                    if(data != null && data != "[]"){
                        var rows = JSON.parse(data);
                        count = rows.length;
                        for(var i=0;i<rows.length;i++){
                            var row = rows[i];
                            str +='<a href="${ctx}/wechat/goods?shopId='+row.shopId+'" class="weui-media-box weui-media-box_appmsg">';
                            str +='<div class="weui-media-box__hd scale-1px">';
                            str +='<img class="weui-media-box__thumb" src="${ctx}/wechatPage/images/good-off-find.png" alt="">';
                            str +='</div><div class="weui-media-box__bd">';
                            str +='<h4 class="weui-media-box__title">'+row.shopName+'</h4>';
                            if(row.distance != null){
                                var distance = row.distance;
                                var unit = '米';
                                if(distance > 1000){
                                    distance = parseInt(distance / 10.0)/100.0;
                                    unit = '公里';
                                }else{
                                    distance = parseInt(distance);
                                }
                                str +='<p class="color-primary">距离您'+distance+unit+'</p>';
                            }
                            str +='<p class="color-sub font12">'+row.address+'</p>';
                            str +='</div></a>';
                        }
                    }
                }
            });
            return str;
        }
        $("#search").on("click",function(){
            $('.weui-loadmore').show();
            loading = false;
            tem = 1;
            var param = {};
            var page = {};
            page["pageSize"]=pageSize;
            page["pageCurrent"]=1;
            var shopName = $('#shopName').val();
            if(shopName != null && shopName.length>0){
                param["shopName"] = shopName;
            }
            var str = selectList(param,page);
            $("#goods").html(str);
            if(count < pageSize){
                $('.weui-loadmore').hide();
                loading = true;
            }
        });
    </script>
</body>
</html>
