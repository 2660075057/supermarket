<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>购买记录</title>
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/lib/weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/css/jquery-weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/base.css">
    <script src="${ctx}/wechatPage/assets/weui/lib/zepto.js"></script>
    <script src="${ctx}/wechatPage/assets/weui/lib/fastclick.js"></script>
    <script src="${ctx}/wechatPage/assets/weui/js/jquery-weui.min.js"></script>
    <script src="${ctx}/wechatPage/assets/base.js"></script>
    <script src="${ctx}/wechatPage/assets/vconsole.min.js"></script>
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/mui-icons-extra.css">
    <style>
        body{ text-align:center}
        #divs{
            margin:0 auto;
            margin-top: 45%;
        }
        .tb{
            font-size: 108px;
        }
        #fh{
            margin:0 auto;
            margin-top: 15%;
        }
    </style>
</head>
<body>
    <div class="weui-tab__bd">
        <div id="tab-all" class="weui-tab__bd-item weui-tab__bd-item--active">
            <input type="hidden" id="count" value="${orderList.size()}">
            <div class="weui-re-list" id="goods">
                <c:forEach var="order" items="${orderList}">
                    <div id="data${order.orderId}" class="weui-panel weui-panel_access">
                        <div class="weui-panel__hd color-sub">
                            <div class="pull-right item-delete" onclick="deleteOrder(${order.orderId})">
                                <i class="icon icon-delete"></i>
                            </div>
                            <i class="icon icon-shop"></i> ${order.shopEntity.shopName}
                        </div>
                        <div class="weui-panel__bd">
                          <c:forEach var="orderd" items="${order.orderDetail}">
                            <div class="weui-media-box weui-media-box_appmsg">
                                <div class="weui-media-box__hd scale-1px">
                                    <img class="weui-media-box__thumb" src="${ctx}/wechatPage/images/good-off-find.png" alt="">
                                </div>
                                <div class="weui-media-box__bd">
                                    <h4 class="weui-media-box__title">${orderd.commName}</h4>
                                    <p class="color-highlight">￥${orderd.payment/100} 元</p>
                                    <p class="color-info">x ${orderd.commCount}</p>
                                </div>
                            </div>
                          </c:forEach>
                        </div>
                        <div class="weui-panel__ft">
                            <div class="weui-cell weui-cell_link">
                                <div class="clearfix color-sub">
                                    <div class="pull-left"><fmt:formatDate value="${order.createTime}" type="date" pattern="yyyy-MM-dd HH:mm"/></div>
                                    <div class="pull-right">合计:${order.payment/100.0}元&nbsp;&nbsp;实付:${(order.payment-order.preferential)/100.0}元</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <c:if test="${orderList.size() <= 0}">
                    <div id="divs">
                        <span class="mui-icon-extra mui-icon-extra-cart tb"></span><br><br><br>
                        <span class="mui-icon-extra">购买记录空空如也</span>
                    </div>
                    <div id="fh" onclick="returnMain()">
                        <span class="mui-icon-extra mui-icon-extra-arrowleftcricle"></span>
                        <span class="mui-icon-extra">返回</span>
                    </div>
                </c:if>

            </div>
        </div>
        <div class="weui-loadmore">
            <i class="weui-loading"></i>
            <span class="weui-loadmore__tips">加载中…</span>
        </div>
    </div>
<script>
    var loading = false;  //状态标记
    var tem = 1;
    var pageSize = 10;
    var count = 0;
    //var vconsole = new VConsole();
    $(function () {
        count = $('#count').val();
        if(count < pageSize){
            $('.weui-loadmore').hide();
        }else{
            $(document.body).infinite().on("infinite", function() {
                if(loading) return;
                loading = true;
                setTimeout(function() {
                    tem +=1;
                    var page = {};
                    page["pageSize"]=pageSize;
                    page["pageCurrent"]=tem;
                    var str = selectList(page);
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
    function selectList(page) {
        count = 0;
        var str = "";
        $.ajax({
            url: "${ctx}/wechat/myOrder/orderListParam",
            type: "POST",
            async: false,
            data: page,
            success: function (data) {
                if(data != null && data != "[]"){
                    var rows = JSON.parse(data);
                    count = rows.length;
                    for(var i=0;i<rows.length;i++){
                        var row = rows[i];
                        var shop = row.shopEntity;
                        var orderDetail = row.orderDetail;
                        str +="<div id=\"data"+row.orderId+"\" class=\"weui-panel weui-panel_access\"><div class=\"weui-panel__hd color-sub\">";
                        str +="<div class=\"pull-right item-delete\" onclick=\"deleteOrder("+row.orderId+")\"><i class=\"icon icon-delete\"></i></div>";
                        str +="<i class=\"icon icon-shop\"></i> "+shop.shopName+"</div><div class=\"weui-panel__bd\">";
                        for(var j=0;j<orderDetail.length;j++){
                            var od = orderDetail[j];
                            str +="<div class=\"weui-media-box weui-media-box_appmsg\"><div class=\"weui-media-box__hd scale-1px\">";
                            str +="<img class=\"weui-media-box__thumb\" src=\"${ctx}/wechatPage/images/good-off-find.png\" alt=\"\">";
                            str +="</div><div class=\"weui-media-box__bd\">";
                            str +="<h4 class=\"weui-media-box__title\">"+od.commName+"</h4>";
                            str +="<p class=\"color-highlight\">￥"+od.payment/100+" 元</p>";
                            str +="<p class=\"color-info\">x "+od.commCount+"</p></div></div>";
                        }
                            str +="</div><div class=\"weui-panel__ft\"><div class=\"weui-cell weui-cell_link\">";
                            str +="<div class=\"clearfix color-sub\">";
                            str +="<div class=\"pull-left\">"+getLocalTime(row.createTime)+"</div>";
                            str +="<div class=\"pull-right\">共"+orderDetail.length+"件商品 合计："+row.payment/100+" 元</div>";
                            str +="</div></div></div></div>";
                    }
                }
            }
        });
        return str;
    }
    function getLocalTime(nS) {
        return new Date(parseInt(nS)).Format("yyyy-MM-dd hh:mm:ss");
    }
    Date.prototype.Format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o){
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    }
    function deleteOrder(orderId) {
        $.confirm({
            title: '确定要删除该记录吗？',
            //text: '您确定要删除记录 <span class="color-highlight">xxxx</span> 吗?',
            onOK: function () {
                //点击确认
                $.ajax({
                    url: "${ctx}/wechat/myOrder/wechatDeleteById",
                    type: "POST",
                    data: {"orderId":orderId},
                    success: function (data) {
                        var row = JSON.parse(data);
                        if(row.data){
                            var id = "#data"+orderId;
                            $(id).hide();
                            $.toast("删除成功", "text");
                        }else{
                            $.toast("删除失败", "text");
                        }
                    }
                });

            },
            onCancel: function () {
            }
        });
    }
    function returnMain() {
        if (document.referrer === '') {
            WeixinJSBridge.call('closeWindow');
        } else {
            window.history.go(-1);
        }
    }
</script>
</body>
</html>
