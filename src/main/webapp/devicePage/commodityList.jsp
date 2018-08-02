<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>商品列表</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/devicePage/assets/base.css"/>
</head>
<body <c:if test="${debug =='true'}">style="overflow: auto"</c:if>>
<div class="goods-md md-center">
    <div class="module-title">商品列表</div>
    <div class="container">
        <div class="scroll-md">
            <div class="goods-list">
                <table width="100%" class="table">
                    <thead>
                    <tr>
                        <td>商品名称</td>
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
            <div class="list-total text-right">
                共<span class="text-danger"> <span id="count">0</span> </span>件商品
                总计：<span class="text-danger font-primary">
                <span id="payment">￥0.00元</span> </span>
                <span id="allPaymentView" class="font-second" style="display: none">
                    &nbsp;<span id="allPayment">￥0.00元</span><span class="line"></span>&nbsp;
                </span>
            </div>
        </div>
        <div class="btns text-right">
            <button class="btn btn-opacity" onclick="cancel()"><span>取消购买所有商品<span id="timeTip"></span></span></button>
            <button class="btn btn-primary" id="execute" onclick="execute()">立即支付</button>
        </div>
    </div>
</div>
<div class="danger-tips" id="msgDiv">
    <i class="icon icon-danger"></i> <span id="msg">正在为您检测商品，请稍候</span>
</div>
</body>
<script src="${ctx}/operator/assets/jquery.min.js"></script>
<script>
    var countDown;

    var buyState = 0;
    var nowData;

    $(function () {
        setTimeout(function () {
            JavaApplication.commodityPageInit();
        },300);
        setPageTimeout(300000);
    });

    function getNormalTr(name,count,payment) {
        return '<tr>' +
                '<td>' + name + '</td>' +
                '<td width="15%">x' + count + '</td>' +
                '<td  width="15%" class="text-danger">￥' + payment + '</td>' +
            '</tr>';
    }

    function getSellTr(name,count,payment) {
        return '<tr>' +
            '<td><span class="label label-second">已售出</span>'+name+'</td>'+
            '<td width="15%">x'+count+'</td>'+
            '<td class="text-danger" width="15%"></td>'+
            '</tr>';
    }

    function getUnSellTr(name,count,payment) {
        return '<tr>' +
                '<td class="text-danger"><span class="label label-danger">不可售</span>'+name+'</td>'+
                '<td width="15%">x'+count+'</td>'+
                '<td class="text-danger" width="15%">￥'+payment+'</td>'+
            '</tr>';
    }

    function clearView() {
        $('#bodyData').html('');
        $('#count').html(0);
        $('#payment').html("￥0.00元");
        $('#allPaymentView').hide();
    }

    function updateProOrder(preOrder) {
        buyState = 0;
        nowData = preOrder;
        if(!preOrder){
            clearView();
            setPageTimeout(300000);
            return ;
        }
        hideMessage();
        if(preOrder.payment == 0){
            $("#execute").html("立即开门");
        }else {
            $("#execute").html('立即支付');
        }
        //设置总价和数量
        $('#count').html(preOrder.commodityPriceInfos.length);
        if(preOrder.preferential > 0){
            $('#allPayment').html('￥'+(preOrder.payment/100.0)+'元');
            $('#allPaymentView').show();
        }else{
            $('#allPaymentView').hide();
        }
        var p = (preOrder.payment - preOrder.preferential)/100.0;
        $('#payment').html('￥'+p+'元');


        //归类和计算是否达到购买条件
        var commoditys = preOrder.commodityPriceInfos;
        var map = {};
        var c = 0;
        for (var i = 0; i < commoditys.length; i++) {
            if (!commoditys[i]) {
                continue;
            }
            var text;
            if(commoditys[i].state == 0){
            }else if(commoditys[i].state == 1){
                c++;
            }else{
                buyState = -1;
            }
            var key = commoditys[i].commId+""+commoditys[i].state;
            if(!map[key]){
                map[key] = [];
            }
            map[key].push(commoditys[i]);
        }
        /*//合成归并的html
        var html = "";
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
        for (var i = 0; i < commoditys.length; i++) {
            var comm = commoditys[i];
            if (comm.state == 0) {
                html += getNormalTr(comm.name, 1, comm.price / 100.0);
            } else if (comm.state == 1) {
                html += getSellTr(comm.name, 1, comm.price / 100.0);
            } else {
                html += getUnSellTr(comm.name, 1, comm.price / 100.0);
            }
        }

        $('#bodyData').html(html);

        //存在未购买商品
        if(buyState != -1 && c != commoditys.length){
            buyState = 1;
        }
        if(buyState == -1){
            showMessage('有商品暂不可售，请将商品放回原处！');
        }else{
            setPageTimeout(300000);
        }
    }

    function showMessage(msg) {
        $('#msg').html(msg);
        $('#msgDiv').show();
    }

    function hideMessage() {
        $('#msgDiv').hide();
    }

    function emptyComm() {
        hideMessage();
        $("#execute").html("立即开门");
    }

    function setPageTimeout(time) {
        if(countDown){
            clearInterval(countDown);
        }
        var t = time/1000;
        $('#timeTip').html('('+t+'秒)');
        countDown = setInterval(function () {
            function a() {
                t--;
                $('#timeTip').html('('+t+'秒)');
                if(t <= 0){
                    clearInterval(countDown);
                    cancel();
                }
            }
            a();
        },1000);
    }

    function cancel() {
        JavaApplication.cancel();
    }

    function execute() {
        // if(buyState == 0){
        //     showMessage('请将商品放置在检测区内');
        //     $('#msgDiv').fadeOut(3000);
        //     return ;
        // }
        if(buyState == -1){
            showMessage('有商品暂不可售，请将商品放回原处！');
            return ;
        }
        JavaApplication.execute();
    }
</script>
</html>