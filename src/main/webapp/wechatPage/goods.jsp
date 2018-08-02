<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>商店详情</title>
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/lib/weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/css/jquery-weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/base.css">
    <script src="${ctx}/wechatPage/assets/weui/lib/zepto.js"></script>
    <script src="${ctx}/wechatPage/assets/weui/lib/fastclick.js"></script>
    <script src="${ctx}/wechatPage/assets/weui/js/jquery-weui.min.js"></script>
    <script src="${ctx}/wechatPage/assets/base.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/operator/layui/layui.js" ></script>
</head>
<body>
<input type="hidden" id="shopid" value="${shop.shopId}">
    <div class="shops-goods">
        <div class="banner banner-shop">
            <img src="${ctx}/wechatPage/images/banner.png" class="wimg" alt="" />
            <div class="shop-txt">
                <div class="color-primary">${shop.shopName}</div>
                <div class="color-info">${shop.address}</div>
            </div>
        </div>
            <div class="search-page md-bg">
                <div class="header-search">
                    <div class="wrap">
                        <i class="icon icon-search"></i>
                        <input type="search" placeholder="请输入关键词搜索" id="commName" />
                        <button type="submit" id="search" class="button button-active">搜索</button>
                    </div>
                </div>
            </div>
        <div class="dir-nav md-bg">
            <div class="header-search-bar">
                <div class="weui-flex">
                    <div class="weui-flex__item text-center">
                        <div class="dir-list">
                            <div class="swiper-container">
                                <div class="swiper-wrapper">
                                    <input type="hidden" id="typeVal" value="-1">
                                    <div id="qb" class="swiper-slide active"><a href="javascript:selectByType(-1,'#qb');">全部</a></div>
                                    <div id="ls" class="swiper-slide"><a href="javascript:selectByType(2,'#ls');">零食</a></div>
                                    <div id="yl" class="swiper-slide"><a href="javascript:selectByType(1,'#yl');">饮料</a></div>
                                    <div id="zs" class="swiper-slide"><a href="javascript:selectByType(3,'#zs');">主食</a></div>
                                    <div id="qt" class="swiper-slide"><a href="javascript:selectByType(-2,'#qt');">其他</a></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="weui-grids goods-list" id="goods">
            <input type="hidden" id="count" value="${depotList.size()}">
            <c:forEach var="depot" items="${depotList}">
                <a href="${ctx}/wechat/commoditydetail/shopCommDetail?shopId=${depot.shopId}&commId=${depot.commId}&amount=${depot.amount}" class="weui-grid js_grid">
                    <div class="goods-img">
                        <c:if test="${depot.amount <= 0}">
                            <i class="icon icon-sale-off"></i>
                        </c:if>
                        <c:if test="${!depot.picture.isEmpty()}">
                            <img src="${ctx}${depot.picture.get(0).imgUrl}">
                        </c:if>
                        <c:if test="${depot.picture.isEmpty()}">
                            <img src="${ctx}/wechatPage/images/good-off-find.png">
                        </c:if>
                    </div>
                    <div class="title">${depot.commodityEntity.commName}</div>
                    <div class="color-highlight">￥${depot.commodityEntity.commPrice/100}</div>
                </a>
            </c:forEach>
        </div>
        <div class="weui-loadmore">
            <i class="weui-loading"></i>
            <span class="weui-loadmore__tips">加载中…</span>
        </div>
    </div>
    <div class="footer-leave-btn">
        <span class="round"></span><span class="round"></span><span class="round"></span>
    </div>
    <div class="modal-leave-fixed">
        <div class="bg"></div>
        <div class="wrap">
            <form class="modal-form">
                <div class="title text-center">商店留言</div>
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
</body>
<script type='text/javascript' src='${ctx}/wechatPage/assets/weui/js/swiper.min.js'></script>
<script>
    var loading = false;  //状态标记
    var tem = 1;
    var pageSize = 11;
    var count = 0;
    var shopId=-1;
    $(function () {
        layui.use('layer', function(){
            var layer = layui.layer;
        });
        shopId = $('#shopid').val();
        var swiper = new Swiper('.swiper-container', {
            slidesPerView: 4,
            paginationClickable: true,
            spaceBetween: 30,
            freeMode: true
        });
        $(".footer-leave-btn").on("click",function(){
            $('#type').val(1);
            $('#content').val("");
            $.modalLeave("show")
            // 隐藏 $.modalLeave("hide")
        });
        var fiexdHeight = $(".banner").height();
        document.querySelector('.goods-list').addEventListener('touchmove', function(){
            console.log($("body").scrollTop());
            if($("body").scrollTop() > fiexdHeight){
                $(".dir-nav").addClass("dir-nav-fixed");
            }else{
                $(".dir-nav").removeClass("dir-nav-fixed");
            }
        });

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
                    var typeid = $('#typeVal').val();
                    if(typeid != -1){
                        if(typeid == -2){
                            param["notTypeIds"] = "1,2,3";
                        }else{
                            param["typeId"] = typeid;
                        }
                    }
                    var commName = $('#commName').val();
                    if(commName != null && commName.length>0){
                        param["commName"] = commName;
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
            url: "${ctx}/wechat/goods/depotListParam",
            type: "POST",
            async: false,
            data: {"page":JSON.stringify(page),"req":JSON.stringify(param)},
            success: function (data) {
                if(data != null && data != "[]"){
                    var rows = JSON.parse(data);
                    count = rows.length;
                    for(var i=0;i<rows.length;i++){
                        var dep = rows[i];
                        var comm = dep.commodityEntity;
                        str +="<a href=\"${ctx}/wechat/commoditydetail/shopCommDetail?shopId="+dep.shopId+"&commId="+dep.commId+"&amount="+dep.amount+"\" class=\"weui-grid js_grid\">";
                        str +="<div class=\"goods-img\">";
                        if(dep.amount <= 0){
                            str +="<i class=\"icon icon-sale-off\"></i>";
                        }
                        //封面图
                        var imgUrl = '${ctx}/wechatPage/images/good-off-find.png';
                        if (dep.picture.length > 0) {
                            imgUrl = '${ctx}'+dep.picture[0].imgUrl;
                        }
                        str += "<img src=\"" + imgUrl + "\"></div>";
                        str +="<div class=\"title\">"+comm.commName+"</div>";
                        str +="<div class=\"color-highlight\">￥"+comm.commPrice/100+"</div> </a>";
                    }
                }
            }
        });
        return str;
    }
    function selectByType(typeid,id) {
        $('#typeVal').val(typeid);
        tem = 1;
        $('.weui-loadmore').show();
        $(id).addClass("active");
        $(id).siblings().removeClass("active");
        var param = {};
        if(shopId != null && shopId != -1){
            param["shopId"] = shopId;
        }
        if(typeid != -1){
            if(typeid == -2){
                param["notTypeIds"] = "1,2,3";
            }else{
                param["typeId"] = typeid;
            }
        }
        var commName = $('#commName').val();
        if(commName != null && commName.length>0){
            param["commName"] = commName;
        }
        var page = {};
        page["pageSize"]=pageSize;
        page["pageCurrent"]=1;
        var str = selectList(param,page);
        $("#goods").html(str);
        if(count >= pageSize){
            loading = false;
        }else{
            $('.weui-loadmore').hide();
            loading = true;
        }
    }
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
    $("#search").on("click",function(){
        $('.weui-loadmore').show();
        tem = 1;
        var param ={};
        if(shopId != null && shopId != -1){
            param["shopId"] = shopId;
        }
        var typeid = $('#typeVal').val();
        if(typeid != -1){
            if(typeid == -2){
                param["notTypeIds"] = "1,2,3";
            }else{
                param["typeId"] = typeid;
            }
        }
        var commName = $('#commName').val();
        if(commName != null && commName.length>0){
            param["commName"] = commName;
        }
        var page = {};
        page["pageSize"]=pageSize;
        page["pageCurrent"]=1;
        var str = selectList(param,page);
        $("#goods").html(str);
        if(count >= pageSize){
            loading = false;
        }else{
            $('.weui-loadmore').hide();
            loading = true;
        }
    });
</script>
</html>
