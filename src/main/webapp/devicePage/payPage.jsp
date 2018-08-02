<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>支付</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/devicePage/assets/base.css"/>
    <style type="text/css" rel="stylesheet">
        .checkout-md{
            position: relative;
            padding:25px;
            overflow: hidden;
        }
        .checkout-md .pull-left{
            float: left;
            width: 100%;
            left:0;
        }
        .checkout-md .pull-right{
            right:-33%;
            float:none;
            position:absolute;
            opacity: 0;
            z-index: 1;
        }
        .checkout-pay .pull-left{
            width: 65%;
            float: left;
            -webkit-transition: width 1.0s ease-in-out;
            -moz-transition: width 1.0s ease-in-out;
            transition: width 1.0s ease-in-out;
        }
        .checkout-pay .pull-right{
            position: relative;
            left:25px;
            opacity: 1;
            float:left;
            -webkit-transition: right 1.0s ease-in-out,opacity 1.4s ease-in-out;
            -moz-transition: right 1.0s ease-in-out,opacity 1.4s ease-in-out;
            transition: right 1.0s ease-in-out,opacity 1.4s ease-in-out;
        }
    </style>
</head>
<body <c:if test="${debug =='true'}">style="overflow: auto"</c:if>>
<div class="pay-md md-center">
    <div class="title text-center">
        <div class="sub">本次购物支付</div>
        ￥ <span class="font-primary" id="payment">246.00</span>
    </div>
    <div class="checkout-md clearfix">
        <div class="md-left pull-left">
            <div class="scroll-md">
                <div class="goods-list">
                    <table width="100%" class="table">
                        <thead>
                        <tr>
                            <td >商品名称</td>
                            <td width="15%">购买数量</td>
                            <td width="15%">单价</td>
                        </tr>
                        </thead>
                    </table>
                    <div class="goods-scroll">
                        <table width="100%" class="table">
                            <tbody id="bodyData">

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="md-right pull-right">
            <div class="payway-title">
                请选择支付方式
            </div>
            <dl class="slide-list">
                <dt class="active">
                    <i class="icon icon-wechat"></i>
                    微信支付
                    <i class="icon-arrow-down"></i>
                </dt>
                <dd>
                    <div class="text-center">
                        <div class="">扫描二维码完成支付</div>
                        <div class="text-primary font50 timeout">30</div>
                        <div class="font18 text-info">请在倒计时结束前完成支付，倒计时结束后尚未支<br/>付系统将自动取消本次购物！</div>
                        <div class="qrcode-md" id="wechatDiv" ondragstart="return false;">
                            <div id="wechatBarocde" class="wimg"></div>
                        </div>
                    </div>
                </dd>
            </dl>
            <%--<dl class="slide-list">--%>
            <%--<dt>--%>
            <%--<i class="icon icon-alipay"></i>--%>
            <%--支付宝支付--%>
            <%--<i class="icon-arrow-down"></i>--%>
            <%--</dt>--%>
            <%--<dd style="display:none">--%>
            <%--<div class="text-center">--%>
            <%--<div class=" font18">扫描二维码完成支付</div>--%>
            <%--<div class=" text-primary font50 timeout">30</div>--%>
            <%--<div class=" font14 text-info">请在倒计时结束前完成支付，倒计时结束后尚未支<br />付系统将自动取消本次购物！</div>--%>
            <%--<div class="qrcode-md">--%>
            <%--<img src="images/qr.png" alt="" class="wimg" />--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--</dd>--%>
            <%--</dl>--%>
            <a href="#" class="btn-goto-shop" onClick="cancel()">继续购物，暂不支付</a>
        </div>
    </div>
</div>
<script src="${ctx}/operator/assets/jquery.min.js"></script>
<script src="${ctx}/devicePage/assets/qrCode.min.js"></script>
<script>
    $(document).ready(function () {
        $(".slide-list dt").on("click", function () {
            $(".slide-list dt").removeClass("active");
            $(".slide-list dd").hide();
            $(this).addClass("active");
            $(this).next().show()
        })
    });

    function setPayData(payData) {
        var p = (payData.payment - payData.preferential) / 100.0;
        $('#payment').html(p);
        if (payData.wechat) {
            var wechatDiv = $('#wechatDiv');
            $('#wechatBarocde').html('');
            var qrcode = new QRCode(document.getElementById("wechatBarocde"), {
                width: wechatDiv.width(),//设置宽高
                height: wechatDiv.height()
            });
            qrcode.makeCode(payData.wechat);
            $(".checkout-md").addClass("checkout-pay");
        }
    }

    function setProOrder(preOrder) {
        //统一归类
        var commPriceInfo = preOrder.commodityPriceInfos;
        var map = {};
        for (var i in commPriceInfo) {
            var comm = commPriceInfo[i];
            var key = comm.commId + "" + comm.state;
            if (!map[key]) {
                map[key] = [];
            }
            map[key].push(comm);
        }

        /*var html = "";//归并的分类计算
        for(var key in map){
            var commArr = map[key];
            if(commArr[0].state == 0){
                html += getNormalTr(commArr[0].name,commArr.length,commArr[0].price/100.0);
            }else if(commArr[0].state == 1){
                html += getSellTr(commArr[0].name,commArr.length,commArr[0].price/100.0);
            }else{
                html += getUnSellTr(commArr[0].name,commArr.length,commArr[0].price/100.0);
            }
        }*/
        var html = "";
        for (var i = 0; i < commPriceInfo.length; i++) {
            var comm = commPriceInfo[i];
            if (comm.state == 0) {
                html += getNormalTr(comm.name, 1, comm.price / 100.0);
            } else if (comm.state == 1) {
                html += getSellTr(comm.name, 1, comm.price / 100.0);
            } else {
                html += getUnSellTr(comm.name, 1, comm.price / 100.0);
            }
        }
        $('#bodyData').html(html);
    }

    function timeout(time) {
        var tm;

        function a(time) {
            if (tm) {
                clearTimeout(tm);
            }
            var t = time;
            $('.timeout').html('' + t);
            tm = setInterval(function () {
                if (t == 0) {
                    cancel();
                    clearInterval(tm);
                    return;
                }
                t--;
                $('.timeout').html('' + t);
            }, 1000, 1000);
        }

        a(time);
    }

    function cancel() {
        JavaApplication.cancel();
    }

    function getNormalTr(name, count, payment) {
        return '<tr>' +
            '<td>' + name + '</td>' +
            '<td width="15%">x' + count + '</td>' +
            '<td  width="15%" class="text-danger">￥' + payment + '</td>' +
            '</tr>';
    }

    function getSellTr(name, count, payment) {
        return '<tr>' +
            '<td><span class="label label-second">已售出</span>' + name + '</td>' +
            '<td width="15%">x' + count + '</td>' +
            '<td class="text-danger" width="15%"></td>' +
            '</tr>';
    }

    function getUnSellTr(name, count, payment) {
        return '<tr>' +
            '<td class="text-danger"><span class="label label-danger">不可售</span>' + name + '</td>' +
            '<td width="15%">x' + count + '</td>' +
            '<td class="text-danger" width="15%">￥' + payment + '</td>' +
            '</tr>';
    }

    $(function () {
        timeout(180);
    });
</script>
</html>