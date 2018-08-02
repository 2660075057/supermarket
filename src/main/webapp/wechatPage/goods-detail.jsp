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
    <script type="text/javascript" src="${pageContext.request.contextPath}/operator/layui/layui.js" ></script>
    <style>
        body{
            background-color: #fff;
        }
        .swiper-slide img{
            width: 75%;
        }
    </style>
</head>
<body>
<input type="hidden" id="shopid" value="${shopId}">
    <div class="goods-detail">
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
            <!-- Add Pagination -->
            <div class="swiper-pagination"></div>
        </div>
        <div class="goods-show goods-md">
            <div class="title">
                <c:if test="${amount <= 0}">
                    <label class="label">无货</label>
                </c:if>
                ${comm.commName}
            </div>
            <div class="good-price text-center color-highlight font18">￥${comm.commPrice/100}</div>
            <div class="weui-btn-area">
                <a class="weui-btn weui-btn_primary" href="javascript:" id="showLeave">我要留言</a>
            </div>
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
        <%--<div class="goods-md">
            <div class="goods-md-title">
                <div class="line"></div>
                <div class="title scale-1px">商品详情</div>
            </div>
            <div class="editor">
                <img src="${ctx}/wechatPage/images/good-show.png" alt="" class="wimg" />
            </div>
        </div>--%>
    </div>

    <div class="modal-leave-fixed">
        <div class="bg"></div>
        <div class="wrap">
            <form class="modal-form">
                <div class="title text-center">我要催货</div>
                <div class="row">
                    <select name="" id="type" class="select">
                        <option value="1">我要留言</option>
                        <option value="2">我要催货</option>
                    </select>
                </div>
                <div class="row">
                    <textarea id="content" class="textarea" placeholder="请输入您要反馈的内容" rows="3"></textarea>
                </div>
                <div class="row">
                    <a href="javascript:addMaessage();" class="weui-btn weui-btn_primary">提交</a>
                </div>
            </form>
        </div>
    </div>
    <script type='text/javascript' src='${ctx}/wechatPage/assets/weui/js/swiper.min.js'></script>
    <script>
        var shopId = -1;
        $(function () {
            shopId = $('#shopid').val();
            var swiper = new Swiper('.swiper-container', {
                pagination: {
                    el: '.swiper-pagination'
                }
            });
            $("#showLeave").on("click",function(){
                $('#type').val(1);
                $('#content').val("");
                $.modalLeave("show")
                // 隐藏 $.modalLeave("hide")
            });
            layui.use('layer', function(){
                var layer = layui.layer;
            });
        });
        function addMaessage() {
            var param = {};
            var shopid = shopId;
            var type = $('#type').val();
            var content = $('#content').val();
            if(content == null || content.length <=0){
                layer.tips('请填写内容！', '#content', {
                    tips: [3,'red']
                });
                return;
            }
            if(shopId == null || shopId ==-1){
                layer.tips('留言失败，未找到站点！', '#type', {
                    tips: [3,'red']
                });
                return;
            }
            param.shopId = shopid;
            param.messageType = type;
            param.userType = 2;
            param.content = content;
            console.log(param);
            $.ajax({
                url: "${ctx}/wechat/message/addMessage",
                type: "POST",
                async: false,
                data: param,
                success: function (data) {
                    if(data){
                        //location.reload();
                        $.modalLeave("hide");
                    }
                }
            });
        }
    </script>
</body>
</html>
