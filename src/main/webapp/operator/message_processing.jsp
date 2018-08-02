<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@taglib prefix="ilas" uri="/WEB-INF/permissionTag.tld" %>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<title>留言管理</title>
<%@ include file="global/mylink.jsp" %>
<!--[if IE]>
	<link rel="stylesheet" type="text/css" href="css/ie.css" />
<![endif]-->
<!--[if lt IE 9]>
	<script type="text/javascript" src="assets/html5.js" ></script>
	<script type="text/javascript" src="js/ie.js" ></script>
<![endif]-->
</head>
<body>
<%@ include file="global/header.jsp" %>
<div class="main-content">
    <jsp:include page="menu.jsp" flush="true" >
        <jsp:param name="type" value="news"/>
    </jsp:include>
	<div class="main">
		<div class="bread">
			<a href="#">首页</a> &gt; <a href="#">消息管理</a> &gt; 留言管理
		</div>
		<div class="main-container">
			<div class="page-bar clearfix">
				<div class="pull-left">
					<div class="page-title">
						<span class="line"></span>
                        留言管理
					</div>
				</div>
				<div class="pull-right">
					<form class="form-inline">
						<select id="shopId" class="selectpicker margin-right-5" style="width: 100px">
	
	                    </select>
                        <select id="type" class="selectpicker margin-right-5" style="width: 100px">
                            <option value="-1">请选择状态</option>
                            <option value="0">未回复</option>
                            <option value="1">已回复</option>
                        </select>
                        <select id="messagetype" class="selectpicker margin-right-5" style="width: 100px">
                            <option value="-1">请选择类型</option>
                            <option value="1">留言</option>
                            <option value="2">催货</option>
                        </select>
						<button type="button" class="btn btn-primary margin-right-5" onclick="onSearch()">查询</button>
					</form>
				</div>
			</div>
			<div class="global-table margin-top-gap">
				<div id="main-table">
					<div class="table-responsive">
					    <table class="table table-bordered table-striped table-global">
						    <thead>
						        <tr>
						        	<th width="100">序号</th>
									<th>商店名称</th>
									<th>顾客名称</th>
                                    <th>内容</th>
									<th>状态</th>
                                    <th>类型</th>
									<th>创建时间</th>
									<th width="200">操作</th>
						        </tr>
						    </thead>
						    <tbody>
						        
						    </tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- 分页 -->
			<div class="pager-md clearfix margin-top-gap">
                <div class="pull-right">
                    <div id="layuipage"></div>
                </div>
            </div>
		</div>
	</div>
</div>

<!-- 弹出删除框框-->
<div class="modal fade" id="modal-delete" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" style="width:400px;">
        <div class="modal-content">
            <div class="modal-body">
            	<div class="model-delete-tips text-center">
            		<div class="tips"><i class="icon-tips"></i> 是否确定删除该条数据？</div>
            		<div class="btns">
            			<button type="button" class="btn btn-danger margin-right-10" id="btn-delete">
						 	删除
						 </button>
						 <button type="button" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">
						 	取消
						 </button>
            		</div>
            	</div>
            </div>
        </div>
    </div>
</div>
<script>
$(document).ready(function(){
	$("#add_discount").on("click",function(){
		layer.open({
			title: '添加采购单',
			type: 2,
			area: ['1000px', '580px'],
			fixed: false, //不固定
			maxmin: true,
			content: '../operator/purchase/add'
		});
	});//初始化添加操作员按钮
	var entity = {};
    layuiPage(entity);
    
    initShopList();//初始化站点
});

function onSearch(){
	$(".table").find("tbody").html("");
	var entity = {};
    var type = $("#type").val();
    if(type != -1){
        entity.state = type;
    }
    var messageType = $("#messagetype").val();
    if(messageType != -1){
        entity.messageType = messageType;
    }
	var shopId = $("#shopId").val();
	if($.trim(shopId)==-1){
		entity.shopId = null;
	}else{
		entity.shopId = shopId;
	}
    layuiPage(entity);
}

function layuiPage(param) {
	 layui.use(['laypage', 'layer'], function(){
	        var laypage = layui.laypage
	            ,layer = layui.layer;
	        laypage.render({
	            elem: 'layuipage'
	            ,count: count()
	            ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
	            ,limit : 10
	            ,limits: [10, 20, 30, 40]
	            ,jump: function(obj){
                    var currPage = obj.curr;
                    var pageSize = obj.limit;
	                var page = {};
	                page["pageSize"] = pageSize;
	                page["pageCurrent"] = currPage;
	                queryPurchase(page, param, currPage, pageSize);
	            }
	        });
	    });
}

function count() {
    var count=0;
    var param = {};
    var type = $("#type").val();
    if(type != -1){
        param.state = parseInt(type);
    }
    var messageType = $("#messagetype").val();
    if(messageType != -1){
        param.messageType = messageType;
    }
	var shopId = $("#shopId").val();
	if($.trim(shopId)==-1){
        param.shopId = null;
	}else{
        param.shopId = shopId;
	}
    $.ajax({
        url: "${ctx}/operator/messageadmin/countMessage",
        type: "POST",
        async: false,
        data: param,
        success: function (data) {
        	var row = JSON.parse(data);
        	if(row.code==0){//成功
	            count = parseInt(row.data);
        	}
        }
    });
    return count;
}

function queryPurchase(page, param, currPage, pageSize){
	 	$.ajax({
 			url: "${ctx}/operator/messageadmin/messageList",
 			type: "POST",
 			data: {"req":JSON.stringify(param),"page":JSON.stringify(page)},
 			timeout: 8000
 		}).done(function(data){
 			var row = JSON.parse(data);
            if(row.code==0){//成功
                var rows = row.data;
                drawRow(rows, currPage, pageSize);
            }
 		})
}

function drawRow(rows, currPage, pageSize){//画表格
	var startIndex = 0;
	if(currPage>0){
		startIndex = (currPage-1)*pageSize;
	}
    var $tbody = $(".table").find("tbody");
	if(!rows||rows.length==0) 
		return;
	var html='';
	for(var i=0,len=rows.length; i<len; i++){
		var number = parseInt(startIndex)+parseInt(i+1);
		var newsEntity = rows[i];
		var shop = newsEntity.shop;
        var customer = newsEntity.customer;
		var tr='';
		tr+='<tr>';
		tr+='<td>'+number+'</td>';
		if(shop != null){
            tr+='<td>'+shop.shopName+'</td>';
        }else {
            tr+='<td>未知</td>';
        }
        if(customer != null){
            tr+='<td>'+customer.coustName+'</td>';
        }else {
            tr+='<td>未知</td>';
        }
        var con = newsEntity.content;
        if(con.length>10){
            con = con.substring(0,10) + "...";
        }
		tr+='<td title="'+newsEntity.content+'">'+con+'</td>';
		if(newsEntity.state ==1){
            tr+='<td>已回复</td>';
        }else {
            tr+='<td>未回复</td>';
        }
        if(newsEntity.messageType ==1){
            tr+='<td>留言</td>';
        }else {
            tr+='<td>催货</td>';
        }
		tr+='<td>'+getLocalTime(newsEntity.createTime)+'</td>';
		tr+='<td><button onclick=\"javascript:detail(\''+newsEntity.messageId+'\');\" class=\"layui-btn\">详细</button>';
		<ilas:hasPermission code="1203">
        tr += '<button onclick=\"javascript:deleteMessage(\''+newsEntity.messageId+'\');\" class=\"layui-btn\">删除</button></td>';
        </ilas:hasPermission>
		tr+='</tr>';
		html+=tr;
	}
	$tbody.html(html);
};

function detail(messageId){
	layer.open({
		title: '留言详情',
		type: 2,
		area: ['1000px', '550px'],
		fixed: false, //不固定
		maxmin: true,
		content: '${ctx}/operator/messageadmin/detailsMessage?messageId='+messageId
	});
}

<ilas:hasPermission code="1203">
function deleteMessage(messageId){
	$("#modal-delete").modal("show");
	$("#btn-delete").on("click",function(){
	 $.ajax({
	        url: "${ctx}/operator/messageadmin/deleteMessageAdmin",
	        type: "POST",
	        data: {"messageId":messageId},
	        success: function (data) {
	        	$("#modal-delete").modal("hide");
	        	var row = JSON.parse(data);
	        	if(row.data){
	        		layer.msg('删除成功', {icon: 1});
	        	}else{
	        		layer.msg('删除失败', {icon: 2});
	        	}
	        	var entity = {};
                var type = $("#type").val();
                if(type != -1){
                    entity.state = type;
                }
	        	var shopId = $("#shopId").val();
	        	if($.trim(shopId)==-1){
	        		entity.shopId = null;
	        	}else{
	        		entity.shopId = shopId;
	        	}
	            layuiPage(entity);
	        }
	 });
    });
}
</ilas:hasPermission>
function getLocalTime(nS) {
    return new Date(parseInt(nS)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
}

function initShopList(){//初始化站点
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
            $("#shopId").html(str);
            $("#shopId").selectpicker('refresh');
        }
    });
}

</script>
</body>
</html>