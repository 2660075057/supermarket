<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>商品查询</title>
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/lib/weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/css/jquery-weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/base.css">
    <script src="${ctx}/wechatPage/assets/weui/lib/zepto.js"></script>
    <script src="${ctx}/wechatPage/assets/weui/lib/fastclick.js"></script>
    <script src="${ctx}/wechatPage/assets/weui/js/jquery-weui.min.js"></script>
    <script src="${ctx}/wechatPage/assets/base.js"></script>
    <script src="${ctx}/wechatPage/assets/vconsole.min.js"></script>
    <style>
        .side-nav ul {
            background-color: #f1f1f1;
            overflow: auto;
            height: 200px;
        }
    </style>
</head>
<body>
<input type="hidden" id="shopId" value="${shopId}">
<input type="hidden" id="typeId" value="${typeId}">
<input type="hidden" id="queryVal" value="${queryVal}">
    <div class="shops-goods">
        <header class="header-fixed">
            <div class="header-search-bar">
                <div class="weui-flex">
                    <div id="zh" class="weui-flex__item text-center active">
                        <span id="search-city">默认</span>
                        <i class="icon icon-arrow"></i>
                    </div>
                    <%--<div class="weui-flex__item text-center">
                        <span id="search-area">销量</span>
                    </div>--%>
                    <div id="price" class="weui-flex__item text-center">
                        <span id="search-area2">价格</span>
                        <span class="price-icons">
                            <i class="icon icon-arrow up" id="up"></i>
                            <i class="icon icon-arrow down" id="down"></i>
                        </span>
                    </div>
                    <div class="weui-flex__item text-center" id="search-filter">
                        <span>筛选</span>
                        <i class="icon icon-filter"></i>
                    </div>
                    <div class="search-md">
                        <div class="line"></div>
                        <i class="icon icon-search"></i>
                    </div>
                </div>
            </div>
        </header>
        <div class="header-fixed-gap"></div>
        <div class="weui-grids goods-list" id="goods">
            <input type="hidden" id="count" value="${commList.size()}">
            <c:forEach var="comm" items="${commList}">
                <a href="${ctx}/wechat/commoditydetail?commId=${comm.commId}" class="weui-grid js_grid">
                    <div class="goods-img">
                        <c:if test="${!comm.picture.isEmpty()}">
                            <img src="${ctx}${comm.picture.get(0).imgUrl}"/>
                        </c:if>
                        <c:if test="${comm.picture.isEmpty()}">
                            <img src="${ctx}/wechatPage/images/good-off-find.png"/>
                        </c:if>
                    </div>
                    <div class="title">${comm.commName}</div>
                    <div class="color-highlight">￥${comm.commPrice/100}</div>
                </a>
            </c:forEach>
        </div>
        <div class="weui-loadmore">
            <i class="weui-loading"></i>
            <span class="weui-loadmore__tips">加载中…</span>
        </div>
    </div>
    <div class="side-search-fixed">
        <div class="bg"></div>
        <div class="wrap">
            <dl class="side-nav" id="shopChoice">
                <dt><i class="icon icon-shop"></i> 商店 <i class="icon icon-arrow"></i> </dt>
                <dd>
                    <ul>
                        <c:forEach var="shop" items="${shoplist}">
                            <li class="shopl">${shop.shopName}<input type="hidden" value="${shop.shopId}"> <span class="weui-cells_checkbox"><i class="weui-icon-checked"></i></span> </li>
                        </c:forEach>
                    </ul>
                </dd>
            </dl>
            <dl class="side-nav" id="typeChoice">
                <dt><i class="icon icon-dir"></i> 分类 <i class="icon icon-arrow"></i></dt>
                <dd>
                    <ul>
                        <c:forEach var="type" items="${typelist}">
                            <li>${type.typeName}<input type="hidden" value="${type.typeId}"> <span class="weui-cells_checkbox"><i class="weui-icon-checked"></i></span> </li>
                        </c:forEach>
                    </ul>
                </dd>
            </dl>
            <dl class="side-nav" id="priceChoice">
                <dt><i class="icon icon-price"></i> 价格 <i class="icon icon-arrow"></i></dt>
                <dd>
                    <ul>
                        <li>0 ~ 50元<input type="hidden" value="0-50"> <span class="weui-cells_checkbox"><i class="weui-icon-checked"></i></span></li>
                        <li>50 ~ 100元<input type="hidden" value="50-100"> <span class="weui-cells_checkbox"><i class="weui-icon-checked"></i></span></li>
                        <li>100元以上<input type="hidden" value="100-#"> <span class="weui-cells_checkbox"><i class="weui-icon-checked"></i></span></li>
                    </ul>
                </dd>
            </dl>
            <div class="text-center">
                <a href="javascript:;" class="weui-btn weui-btn_primary" id="submit-filter">提交</a>
                <a href="javascript:;" class="weui-btn weui-btn_default" id="reset-filter">重置</a>
            </div>
        </div>
    </div>
    <script>
        var loading = false;  //状态标记
        var tem = 1;
        var pageSize = 11;
        var count = 0;
        var priceSort = "";
        //var vconsole = new VConsole();
        var shopId=-1;
        var typeId=-1;
        var queryVal="";
        $(function () {
            shopId = $('#shopId').val();
            typeId = $('#typeId').val();
            queryVal = $('#queryVal').val();
            count = $('#count').val();
            if(count < pageSize){
                $('.weui-loadmore').hide();
            }else{
                $(document.body).infinite().on("infinite", function() {
                    if(loading) return;
                    loading = true;
                    setTimeout(function() {
                        tem +=1;
                        var param ={};
                        if(shopId != null && shopId != -1){
                            param["shopId"] = shopId;
                        }
                        if(typeId != null && typeId != -1){
                            param["typeId"] = typeId;
                        }
                        if(queryVal != null && queryVal.length >0){
                            param["commName"] = queryVal;
                        }
                        if(priceSort.length >0){
                            param["priceSort"]=priceSort;
                        }
                        var shopid = $("#shopid").val();
                        var typeid = $("#typeid").val();
                        var price = $("#priceid").val();
                        if(typeof(shopid) !="undefined"){
                            param["shopId"]=shopid;
                        }
                        if(typeof(price) !="undefined"){
                            var parr = price.split("-");
                            param["startPrice"]=parr[0]*100;
                            if(parr[1] != "#"){
                                param["endPrice"]=parr[1]*100;
                            }
                        }
                        if(typeof(typeid) !="undefined"){
                            param["typeId"]=typeid;
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

            $("#search-filter").on("click",function(){
                $.modalFilter("show")
            });
            $(".side-nav dt").on("click",function(){
                $(".side-nav dd").hide();
                if($(this).hasClass("active")){
                    $(this).removeClass("active");

                }else{
                    $(".side-nav dt").removeClass("active");
                    $(this).addClass("active");
                    $(this).next().show();
                }
            });
            $("#shopChoice li").on("click",function(){
                $(this).children("input").attr("id","shopid");
                $(this).siblings().children("input").attr("id","");
                $(this).addClass("active").siblings().removeClass("active");
            });
            $("#typeChoice li").on("click",function(){
                $(this).children("input").attr("id","typeid");
                $(this).siblings().children("input").attr("id","");
                $(this).addClass("active").siblings().removeClass("active");
            });
            $("#priceChoice li").on("click",function(){
                $(this).children("input").attr("id","priceid");
                $(this).siblings().children("input").attr("id","");
                $(this).addClass("active").siblings().removeClass("active");
            });
            $("#submit-filter").on("click",function(){
                var param ={};
                if(shopId != null && shopId != -1){
                    param["shopId"] = shopId;
                }
                if(typeId != null && typeId != -1){
                    param["typeId"] = typeId;
                }
                if(queryVal != null && queryVal.length >0){
                    param["commName"] = queryVal;
                }
                if(priceSort.length >0){
                    param["priceSort"]=priceSort;
                }
                var shopid = $("#shopid").val();
                var typeid = $("#typeid").val();
                var price = $("#priceid").val();
                if(typeof(shopid) !="undefined"){
                    param["shopId"]=shopid;
                }
                if(typeof(price) !="undefined"){
                    var parr = price.split("-");
                    param["startPrice"]=parr[0]*100;
                    if(parr[1] != "#"){
                        param["endPrice"]=parr[1]*100;
                    }
                }
                if(typeof(typeid) !="undefined"){
                    param["typeId"]=typeid;
                }
                var page = {};
                page["pageSize"]=pageSize;
                page["pageCurrent"]=1;
                var str = selectList(param,page);
                $("#goods").html(str);
                if(count >= pageSize){
                    tem =1;
                    loading = false;
                    $('.weui-loadmore').show();
                }else{
                    $('.weui-loadmore').hide();
                    loading = true;
                }
                $.modalFilter("hide")
            });
            $("#reset-filter").on("click",function(){
                $(".side-nav li").removeClass("active");
                $(".side-nav li").siblings().children("input").attr("id","");
            });
            $("#zh").on("click",function(){
                priceSort = "";
                $("#up").removeClass("active");
                $("#price").removeClass("active");
                $("#down").removeClass("active");
                $("#zh").addClass("active");
                var param ={};
                if(shopId != null && shopId != -1){
                    param["shopId"] = shopId;
                }
                if(typeId != null && typeId != -1){
                    param["typeId"] = typeId;
                }
                if(queryVal != null && queryVal.length >0){
                    param["commName"] = queryVal;
                }
                var shopid = $("#shopid").val();
                var typeid = $("#typeid").val();
                var price = $("#priceid").val();
                if(typeof(shopid) !="undefined"){
                    param["shopId"]=shopid;
                }
                if(typeof(price) !="undefined"){
                    var parr = price.split("-");
                    param["startPrice"]=parr[0]*100;
                    if(parr[1] != "#"){
                        param["endPrice"]=parr[1]*100;
                    }
                }
                if(typeof(typeid) !="undefined"){
                    param["typeId"]=typeid;
                }
                var page = {};
                page["pageSize"]=pageSize;
                page["pageCurrent"]=1;
                var str = selectList(param,page);
                if(str != null && str.length >0){
                    $("#goods").html(str);
                }
                if(count >= pageSize){
                    tem =1;
                    loading = false;
                    $('.weui-loadmore').show();
                }else{
                    $('.weui-loadmore').hide();
                    loading = true;
                }
            });
            $("#price").on("click",function(){

                priceSort = "asc";
                $("#zh").removeClass("active");
                if($("#price").hasClass("active")){
                    if($("#up").hasClass("active")){
                        $("#up").removeClass("active");
                        $("#down").addClass("active");
                        priceSort = "desc";
                    }else{
                        $("#down").removeClass("active");
                        $("#up").addClass("active");
                    }
                }else{
                    $("#price").addClass("active");
                    $("#up").addClass("active");
                }

                var param ={};
                if(shopId != null && shopId != -1){
                    param["shopId"] = shopId;
                }
                if(typeId != null && typeId != -1){
                    param["typeId"] = typeId;
                }
                if(queryVal != null && queryVal.length >0){
                    param["commName"] = queryVal;
                }
                if(priceSort.length >0){
                    param["priceSort"]=priceSort;
                }
                var shopid = $("#shopid").val();
                var typeid = $("#typeid").val();
                var price = $("#priceid").val();
                if(typeof(shopid) !="undefined"){
                    param["shopId"]=shopid;
                }
                if(typeof(price) !="undefined"){
                    var parr = price.split("-");
                    param["startPrice"]=parr[0]*100;
                    if(parr[1] != "#"){
                        param["endPrice"]=parr[1]*100;
                    }
                }
                if(typeof(typeid) !="undefined"){
                    param["typeId"]=typeid;
                }
                var page = {};
                page["pageSize"]=pageSize;
                page["pageCurrent"]=1;
                var str = selectList(param,page);
                if(str != null && str.length >0){
                    $("#goods").html(str);
                }
                if(count >= pageSize){
                    tem =1;
                    loading = false;
                    $('.weui-loadmore').show();
                }else{
                    $('.weui-loadmore').hide();
                    loading = true;
                }
            });

        });
        function selectList(param,page) {
            count = 0;
            var str = "";
            $.ajax({
                url: "${ctx}/wechat/commodity/commListParam",
                type: "POST",
                async: false,
                data: {"page":JSON.stringify(page),"req":JSON.stringify(param)},
                success: function (data) {
                    if(data != null && data != "[]"){
                        var rows = JSON.parse(data);
                        count = rows.length;
                        for(var i=0;i<rows.length;i++){
                            var comm = rows[i];
                            str +="<a href=\"${ctx}/wechat/commoditydetail?commId="+comm.commId+"\" class=\"weui-grid js_grid\">";
                            var imgUrl = '${ctx}/wechatPage/images/good-off-find.png';
                            if (comm.picture.length > 0) {
                                imgUrl = '${ctx}'+dep.picture[0].imgUrl;
                            }
                            str += "<div class=\"goods-img\"><img src=\"" + imgUrl + "\"></div>";
                            str +="<div class=\"title\">"+comm.commName+"</div>";
                            str +="<div class=\"color-highlight\">￥"+comm.commPrice/100+"</div></a>";
                        }
                    }
                }
            });
            return str;
        }
    </script>
</body>
</html>
