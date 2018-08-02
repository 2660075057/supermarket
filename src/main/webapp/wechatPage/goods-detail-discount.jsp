<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>商品详情</title>
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/lib/weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/css/jquery-weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/base.css">
    <script src="${ctx}/wechatPage/assets/weui/lib/zepto.js"></script>
    <script src="${ctx}/wechatPage/assets/weui/lib/fastclick.js"></script>
    <script src="${ctx}/wechatPage/assets/weui/js/jquery-weui.min.js"></script>
    <script src="${ctx}/wechatPage/assets/base.js"></script>
    <style>
        .goods-md .xg {
            margin-bottom: -44px;
        }
        .swiper-slide img{
            width: 75%;
        }
    </style>
</head>
<body>
    <div class="goods-detail">
        <%--<i class="icon icon-goods-last"></i>--%>
        <div class="swiper-container">
            <div class="swiper-wrapper">
                <c:if test="${!comm.picture.isEmpty()}">
                    <c:forEach var="item" items="${comm.picture}">
                        <div class="swiper-slide"><img src="${ctx}${item.imgUrl}"/></div>
                    </c:forEach>
                </c:if>
                <c:if test="${comm.picture.isEmpty()}">
                    <div class="swiper-slide"><img src="${ctx}/wechatPage/images/good-off-find.png"/></div>
                </c:if>
            </div>
            <div class="swiper-pagination"></div>
        </div>
        <div class="goods-show goods-md">
            <div class="title">${comm.commName}</div>
            <%--<div class="good-discount text-center">
                <div class="md font12">
                    <span class="color-primary">7折</span> ￥<del>2888.0</del>
                </div>
            </div>--%>
            <div class="good-price text-center color-highlight font18">￥${comm.commPrice/100}</div>
           
        </div>
        <div class="goods-md">
            <div class="goods-md-title">
                <div class="line"></div>
                <div class="title scale-1px">产品参数</div>
            </div>
            <div class="goods-param">
                <table width="100%">
                    <c:forEach var="attr" items="${commAttr}">
                        <tr>
                            <td class="color-info">${attr.attrName}:</td>
                            <td><strong>${attr.value}</strong> </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <div class="goods-md">
            <div class="goods-md-title">
                <div class="line"></div>
                <div class="title scale-1px">商店售卖情况</div>
            </div>
            <c:forEach var="shop" items="${shopList}">
                <div class="goods-show goods-md xg">
                    <div class="good-discount text-center">
                        <table>
                            <tr>
                                <td>
                                    <a href="${ctx}/wechat/goods?shopId=${shop.shopId}" class="title">${shop.shopName}</a>
                                </td>
                                <td>
                                    <div class="md font12">
                                        <span class="color-primary">￥${priceMap.get(shop.shopId)/100}</span>
                                        <c:if test="${priceMap.get(shop.shopId) != comm.commPrice}">
                                            ￥<del>${comm.commPrice/100}</del>
                                        </c:if>
                                    </div>
                                </td>
                            </tr>
                        </table>


                    </div>
                </div>
            </c:forEach>
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
