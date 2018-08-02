<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<title>盘点管理</title>
    <%@ include file="global/mylink.jsp" %>
</head>
<body>
<%@ include file="global/header.jsp" %>
<div class="main-content">
    <jsp:include page="menu.jsp" flush="true" >
        <jsp:param name="type" value="check_list"  />
    </jsp:include>
	<div class="main">
		<div class="bread">
			<a href="#">首页</a> &gt; 盘点管理
		</div>
		<div class="main-container">
			<div class="page-bar clearfix">
				<div class="pull-left">
					<div class="page-title">
						<span class="line"></span>
						盘点管理
					</div>
				</div>
				<div class="pull-right">
					<form class="form-inline">
                        <select id="shop" class="selectpicker margin-right-5" style="width: 100px">

                        </select>
						<button type="button" id="query" class="btn btn-primary">查询</button>
					</form>
				</div>
			</div>
			<div class="global-table margin-top-gap">
				<div id="main-table">
					<div class="table-responsive">
					    <table class="table table-bordered table-striped table-global">
						    <thead>
						        <tr>								
									<th>盘点号</th>
									<th>盘点日期</th>
									<th>站点名</th>
									<th>操作员</th>
									<th>盘前数量</th>
									<th>盘后数量</th>
									<th>异常数</th>
									<th>售出数</th>
									<th>操作</th>
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

<script>
$(document).ready(function(){
    layui.use('layer', function(){
        var layer = layui.layer;
    });
})
</script>
<script type="text/javascript">
    $(function() {
        var param={};
        setpage(param);
        $.ajax({
            url: "../operator/shop/shopList",
            type: "POST",
            async: false,
            data: {"page":"{}","req":"{}"},
            success: function (data) {
                var str = "<option value=\"-1\">全部站点</option>";
                var rows = jQuery.parseJSON(data);
                for(var i=0;i<rows.length;i++){
                    var row = rows[i];
                    str +="<option value=\""+row.shopId+"\">"+row.shopName+"</option>";
                }
                $("#shop").html(str);
                $('#shop').selectpicker('refresh');
            }
        });
    });
    $("#query").click(function(){
        var shopid = $('#shop').val();
        var param={};
        if(shopid != -1){
            param["shopId"]=shopid;
        }
        setpage(param);
    });
    function selectList(page,param) {
        $.ajax({
            url: "../operator/inventory/inventoryList",
            type: "POST",
            async: false,
            data: {"page":JSON.stringify(page),"req":JSON.stringify(param)},
            success: function (data) {
                var str = "";
                if(data !=null && data !=""){
                    var rows = jQuery.parseJSON(data);
                    for(var i=0;i<rows.length;i++){
                        var row = rows[i];
                        var shop = row.shop;
                        var operator = row.operator;
                        str +="<tr>";
                        str +="<td>"+row.inventoryId+"</td><td>"+getLocalTime(row.inventoryTime)+"</td><td>"+shop.shopName+"</td><td>"+operator.operName+"</td>";
                        str +="<td>"+row.befor+"</td><td>"+row.after+"</td><td>"+row.error+"</td><td>"+row.sold+"</td>";
                        str +="<td><button onclick=\"javascript:showDetail("+row.inventoryId+");\" class=\"layui-btn\">详情</button></td>";
                        str +="</tr>";
                    }
                }
                $('#datalist').html(str);
            }
        });
    }
    function getcount() {
        var shopid = $('#shop').val();
        var param={};
        if(shopid != -1){
            param["shopId"]=shopid;
        }
        var count=0;
        $.ajax({
            url: "../operator/inventory/countInventory",
            type: "POST",
            async: false,
            data: param,
            success: function (data) {
                count = parseInt(data);
            }
        });
        return count;
    }
    function setpage(param) {
        layui.use(['laypage', 'layer'], function(){
            var laypage = layui.laypage
                ,layer = layui.layer;
            laypage.render({
                elem: 'layuipage'
                ,count: getcount()
                ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
                ,limit : 10
                ,limits: [10,20,30,40]
                ,jump: function(obj){
                    var page={};
                    page["pageSize"]=obj.limit;
                    page["pageCurrent"]=obj.curr;
                    selectList(page,param);
                }
            });
        });
    }
    function getLocalTime(nS) {
        return new Date(parseInt(nS)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
    }
    function showDetail(inventoryId) {
        layer.open({
            type: 2,
            area: ['1000px', '580px'],
            fixed: false, //不固定
            maxmin: true,
            content: 'modal/check_modal.jsp?inventoryId='+inventoryId
        });
    }
</script>
</body>
</html>