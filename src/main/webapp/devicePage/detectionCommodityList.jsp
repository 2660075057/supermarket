<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" type="text/css" href="${ctx}/devicePage/assets/base.css"/>
</head>
<body <c:if test="${debug =='true'}">style="overflow: auto"</c:if>>
<div class="goods-md md-center">
    <div class="module-title">欢迎您下次光临</div>
    <div class="container">
        <div class="scroll-md">
            <div class="goods-list">
                <table width="100%" class="table">
                    <thead>
                    <tr>
                        <td>商品名称</td>
                        <td width="15%">购买数量</td>
                    </tr>
                    </thead>
                </table>
                <div class="goods-scroll" style="height: 660px">
                    <table width="100%" class="table">
                        <tbody id="bodyData">
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="list-total text-right">
                共<span class="text-danger"> <span id="count">0</span> </span>件商品
            </div>
        </div>
    </div>
</div>
<div class="danger-tips" id="msgDiv" style="display: none">
    <i class="icon icon-danger"></i> <span id="msg">有未售出商品，请将商品放回原处</span>
</div>
</body>
<script src="${ctx}/operator/assets/jquery.min.js"></script>
<script>
    var countDown;

    function getSellTr(name,count) {
        return '<tr>' +
            '<td>'+name+'</td>'+
            '<td width="15%">x'+count+'</td>'+
            '</tr>';
    }

    function getUnSellTr(name,count) {
        return '<tr>' +
                '<td class="text-danger"><span class="label label-danger">未售出</span>'+name+'</td>'+
                '<td width="15%">x'+count+'</td>'+
            '</tr>';
    }

    function clearView() {
        $('#bodyData').html('');
        $('#count').html(0);
        hideMessage();
    }

    function showCommoditys(commoditys) {
        clearView();

        //设置数量
        $('#count').html(commoditys.length);
        var html = "";

        var buyState = 0;
        for (var i = 0; i < commoditys.length; i++) {
            if (!commoditys[i]) {
                continue;
            }
            var text;
            if(commoditys[i].state == 1){
                text = getSellTr(commoditys[i].name,1);
            }else{
                buyState = -1;
                text = getUnSellTr(commoditys[i].name,1);
            }
            html += text;
        }
        $('#bodyData').html(html);
        //存在未购买商品
        if(buyState == -1){
            showMessage('有未售出商品，请将商品放回原处');
        }
        setPageTimeout(20000);
    }

    function showMessage(msg) {
        $('#msg').html(msg);
        $('#msgDiv').show();
    }

    function hideMessage() {
        $('#msgDiv').hide();
    }

    function setPageTimeout(time) {
        if(countDown){
            clearInterval(countDown);
        }
        var t = time/1000;
        countDown = setInterval(function () {
            function a() {
                t--;
                if(t <= 0){
                    clearView();
                    clearInterval(countDown);
                }
            }
            a();
        },1000);
    }
</script>
</html>