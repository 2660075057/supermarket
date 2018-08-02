<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <title>留言记录</title>
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/lib/weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/weui/css/jquery-weui.min.css">
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/base.css">
    <script src="${ctx}/wechatPage/assets/weui/lib/zepto.js"></script>
    <script src="${ctx}/wechatPage/assets/weui/lib/fastclick.js"></script>
    <script src="${ctx}/wechatPage/assets/weui/js/jquery-weui.min.js"></script>
    <script src="${ctx}/wechatPage/assets/base.js"></script>
    <script src="${ctx}/wechatPage/assets/vconsole.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/operator/layui/layui.js" ></script>
    <link rel="stylesheet" href="${ctx}/wechatPage/assets/mui-icons-extra.css">
    <style>
        .modal-leave-fixed .wrap{
            height: 320px;
            overflow: auto;
        }
        body{ text-align:center}
        #divs{
            margin:0 auto;
            margin-top: 53%;
        }
        .tb{
            font-size: 108px;
        }
    </style>
</head>

<body>
    <div id="goods" class="leave-list">
        <input type="hidden" id="count" value="${messList.size()}">
        <c:forEach var="message" items="${messList}">
            <div class="weui-panel">
                <div class="weui-panel__hd"><i class="icon icon-shop"></i> ${message.shop.shopName}</div>
                <div class="weui-panel__bd">
                    <div class="weui-media-box weui-media-box_text">
                        <div class="date color-sub"><fmt:formatDate value="${message.createTime}" type="date" pattern="yyyy-MM-dd HH:mm"/></div>
                        <h4 class="title">${message.content}</h4>
                        <c:if test="${message.message != null}">
                            <div class="reply-modal scale-1px">
                                <h6>${message.shop.shopName}：</h6>
                                ${message.message.content}
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
        <c:if test="${messList.size() <= 0}">
            <div id="divs">
                <span class="mui-icon-extra mui-icon-extra-order tb"></span><br><br><br>
                <span class="mui-icon-extra">这货很懒什么也没有留下 ◔ ‸◔？</span>
            </div>
        </c:if>
    </div>
     <!-- 无限加载 -->
     <div class="weui-loadmore">
        <i class="weui-loading"></i>
        <span class="weui-loadmore__tips">加载中…</span>
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
                    <select id="shop" class="select">
                        <c:forEach var="shop" items="${shopList}">
                          <option value="${shop.shopId}">${shop.shopName}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="row">
                    <select id="type" class="select">
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
    <script>
        var loading = false;  //状态标记
        var tem = 1;
        var pageSize = 10;
        var count = 0;
        //var vconsole = new VConsole();
        $(function () {
            layui.use('layer', function(){
                var layer = layui.layer;
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

            $(".footer-leave-btn").on("click",function(){
                $('#type').val(1);
                $('#content').val("");
                $.modalLeave("show");
                // 隐藏 $.modalLeave("hide")
            })
        });

        function selectList(page) {
            count = 0;
            var str = "";
            $.ajax({
                url: "${ctx}/wechat/message/messageParam",
                type: "POST",
                async: false,
                data: page,
                success: function (data) {
                    if(data != null && data != "[]"){
                        var rows = JSON.parse(data);
                        count = rows.length;
                        for(var i=0;i<rows.length;i++){
                            var row = rows[i];
                            var message = row.message;
                            var shop = row.shop;
                            str +="<div class=\"weui-panel\">";
                            str +="<div class=\"weui-panel__hd\"><i class=\"icon icon-shop\"></i> "+shop.shopName+"</div>";
                            str +="<div class=\"weui-panel__bd\"><div class=\"weui-media-box weui-media-box_text\">";
                            str +="<div class=\"date color-sub\">"+getLocalTime(row.createTime)+"</div>";
                            str +="<h4 class=\"title\">"+row.content+"</h4>";
                            if(message != null){
                                str +="<div class=\"reply-modal scale-1px\"><h6>"+shop.shopName+"：</h6>"+message.content+"</div>";
                            }
                            str +="</div></div></div>";
                        }
                    }
                }
            });
            return str;
        }
        function getLocalTime(nS) {
            return new Date(parseInt(nS)).Format("yyyy-MM-dd HH:mm");
        }
        Date.prototype.Format = function (fmt) {
            var o = {
                "M+" : this.getMonth()+1, //月份
                "d+" : this.getDate(), //日
                "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
                "H+" : this.getHours(), //小时
                "m+" : this.getMinutes(), //分
                "s+" : this.getSeconds(), //秒
                "q+" : Math.floor((this.getMonth()+3)/3), //季度
                "S" : this.getMilliseconds() //毫秒
            }
            if (/(y+)/.test(fmt))
                fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o){
                if (new RegExp("(" + k + ")").test(fmt)) {
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                }
            }
            return fmt;
        };
        function addMaessage() {
            var param = {};
            var shopId = $('#shop').val();
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
            param.shopId = shopId;
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
                        location.reload();
                        $.modalLeave("hide");
                    }
                }
            });
        }
    </script>
</body>
</html>