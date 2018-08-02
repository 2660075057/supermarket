<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ilasFun" uri="/WEB-INF/permissionFun.tld" %>
<%@taglib prefix="ilas" uri="/WEB-INF/permissionTag.tld" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<title>站点列表</title>
    <%@ include file="global/mylink.jsp" %>
</head>
<body>
<%@ include file="global/header.jsp" %>
<div class="main-content">
    <jsp:include page="menu.jsp" flush="true" >
        <jsp:param name="type" value="shop_list"  />
    </jsp:include>
	<div class="main">
		<div class="bread">
			<a href="#">首页</a> &gt; <a href="#">站点管理</a> &gt; 站点列表
		</div>
		<div class="main-container">
			<div class="page-bar clearfix">
				<div class="pull-left">
					<div class="page-title">
						<span class="line"></span>
						站点列表
					</div>
				</div>
				<div class="pull-right">
					<form class="form-inline">
						<input type="text" id="shopName" class="form-control bar-form-control margin-right-5" placeholder="请输入站点名称" />
						<button type="button" id="query" class="btn btn-primary margin-right-5">查询</button>
                        <ilas:hasPermission code="0402">
                            <button id="add_shop" type="button" class="btn btn-primary" data-toggle="modal" data-target="#site-modal">
                                <i class="btn-icon btn-icon-add"></i>  新增站点
                            </button>
                        </ilas:hasPermission>
					</form>
				</div>
			</div>
			<div class="global-table margin-top-gap">
				<div id="main-table">
					<div class="table-responsive">
					    <table class="table table-bordered table-striped table-global">
						    <thead>
						        <tr>	
						        	<th width="55">序号</th>
						        	<th>站点名</th>
									<th>地址</th>
									<th width="140">创建时间</th>
									<th width="95">状态</th>
									<th width="180">操作</th>
						        </tr>
						    </thead>
						    <tbody id="datalist">

						    </tbody>
						</table>
					</div>
				</div>
			</div>
            <div class="pager-md clearfix margin-top-gap">
                <div class="pull-right">
                    <div id="layuipage"></div>
                </div>
            </div>

		</div>
	</div>
</div>
<script type="text/javascript">
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

    $(function() {
        var param={};
        var pages = 1;
        setpage(param,pages);
      <ilas:hasPermission code="0402">
        $("#add_shop").on("click",function(){
            layer.open({
                type: 2,
                area: ['1000px', '580px'],
                fixed: false, //不固定
                maxmin: true,
                content: 'edit_shop.jsp'
            });
        });//初始化添加操作员按钮
        </ilas:hasPermission>
    });
    $("#query").click(function(){
        var shopName = $('#shopName').val();
        var param={};
        var pages = 1;
        if(shopName != null && shopName.length >0){
            param["shopName"]=shopName.replace(/(^\s*)|(\s*$)/g, "");
        }
        setpage(param,pages);
    });
    function selectList(page,param, currPage, pageSize) {
	    var startIndex = 0;
		if(currPage>0){
			startIndex = (currPage-1)*pageSize;
		}
        $.ajax({
            url: "${ctx}/operator/shop/shopList",
            type: "POST",
            async: false,
            data: {"page":JSON.stringify(page),"req":JSON.stringify(param)},
            success: function (data) {
                var str = "";
                var rows = jQuery.parseJSON(data);
                for(var i=0;i<rows.length;i++){
                	var number = parseInt(startIndex)+parseInt(i+1);
                    var row = rows[i];
                    var address = row.address != null?row.address:"";
                    str +="<tr>";
                    str +="<td>"+number+"</td><td>"+row.shopName+"</td><td>"+address+"</td><td>"+getLocalTime(row.createTime)+"</td>";
                    if(row.state == 0){
                        str +="<td>营业中 <span class=\"ico-status ico-status-success\"></span></td>";
                    }else if(row.state == 2){
                        str +="<td>中转仓库 </td>";
                    }
                    else{
                        str +="<td>维护中 <span class=\"ico-status ico-status-disabled\"></span></td>";
                    }
                    str +="<td>";
                    <ilas:hasPermission code="0403">
                       str += "<button onclick=\"javascript:updateShop('"+row.shopId+"','"+currPage+"');\" class=\"layui-btn\">修改</button>";
                    </ilas:hasPermission>
                    str +="<button onclick=\"javascript:showCode('"+row.shopId+"');\" class=\"layui-btn\">二维码</button>";
                    str +="</td>";
                    str +="</tr>";
                }
                $('#datalist').html(str);
            }
        });
    }
    function getcount() {
        var shopName = $('#shopName').val();
        var param={};
        if(shopName != null && shopName.length >0){
            param.shopName=shopName.replace(/(^\s*)|(\s*$)/g, "");
        }
        var count=0;
        $.ajax({
            url: "${ctx}/operator/shop/countShop",
            type: "POST",
            async: false,
            data: param,
            success: function (data) {
                count = parseInt(data);
            }
        });
        return count;
    }
    function setpage(param,pages) {
    	layui.use(['laypage', 'layer'], function(){
            var laypage = layui.laypage
                ,layer = layui.layer;
            laypage.render({
                elem: 'layuipage'
                ,count: getcount()
                ,curr:pages
                ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
                ,limit : 10
                ,limits: [10,20,30,40]
                ,jump: function(obj){
                	var currPage = obj.curr;
	            	var pageSize = obj.limit;
                    var page={};
                    page["pageSize"]=obj.limit;
                    page["pageCurrent"]=obj.curr;
                    selectList(page,param, currPage, pageSize);
                }
            });
        });
    }
    function getLocalTime(nS) {
        return new Date(parseInt(nS)).Format("yyyy-MM-dd HH:mm");
    }
    function updateShop(shopid,currPage) {
     <ilas:hasPermission code="0403">
        layer.open({
            type: 2,
            area: ['1000px', '580px'],
            fixed: false, //不固定
            maxmin: true,
            content: '${ctx}/operator/shop/editShopPage?shopid='+shopid+'&currPage='+currPage
        });
        </ilas:hasPermission>
    }
    function showCode(shopid){
        layer.open({
            type: 2,
            area: ['1000px', '580px'],
            fixed: false, //不固定
            maxmin: true,
            content: '${ctx}/operator/shop/shopBarcode?shopId='+shopid
        });
    }
</script>
</body>
</html>