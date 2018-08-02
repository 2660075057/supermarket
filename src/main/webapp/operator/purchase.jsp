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
<title>采购单管理</title>
<%@ include file="global/mylink.jsp" %>
<!--[if IE]>
	<link rel="stylesheet" type="text/css" href="css/ie.css" />
<![endif]-->
<!--[if lt IE 9]>
	<script type="text/javascript" src="\assets/html5.js" ></script>
	<script type="text/javascript" src="js/ie.js" ></script>
<![endif]-->
</head>
<body>
<input type="hidden" value="" id="purId">
<%@ include file="global/header.jsp" %>
<div class="main-content">
    <jsp:include page="menu.jsp" flush="true" >
        <jsp:param name="type" value="purchase_list"/>
    </jsp:include>
	<div class="main">
		<div class="bread">
			<a href="#">首页</a> &gt; <a href="#">采购单管理</a> &gt; 采购单管理
		</div>
		<div class="main-container">
			<div class="page-bar clearfix">
				<div class="pull-left">
					<div class="page-title">
						<span class="line"></span>
                        采购单管理
					</div>
				</div>
				<div class="pull-right">
					<form class="form-inline">
						<select id="shopId" class="selectpicker margin-right-5" style="width: 100px">
	
	                    </select>
						<button type="button" class="btn btn-primary margin-right-5" onclick="onSearch()">查询</button>
						<button type="button" class="btn btn-primary" id="add_discount">
							<i class="btn-icon btn-icon-add"></i>  新增采购单
						</button>
					</form>
				</div>
			</div>
			<div class="global-table margin-top-gap">
				<div id="main-table">
					<div class="table-responsive">
					    <table class="table table-bordered table-striped table-global">
						    <thead>
						        <tr>								
						        	<th width="160">序号</th>
									<th>操作员</th>
									<th>商店</th>
									<th>采购单类型</th>
									<th>订单状态</th>
									<th>创建时间</th>
									<th width="240">操作</th>
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
    var entity = {};
	var shopId = $("#shopId").val();
	if($.trim(shopId)==-1){
		entity.shopId = null;
	}else{
		entity.shopId = shopId;
	}
    $.ajax({
        url: "${pageContext.request.contextPath}/operator/purchase/queryPurchaseCount",
        type: "POST",
        async: false,
        data: {"param":JSON.stringify(entity)},
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
 			url: "${pageContext.request.contextPath}/operator/purchase/queryPurchase",
 			type: "POST",
 			data: {"param":JSON.stringify(param),"page":JSON.stringify(page)},
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
	console.log(rows);
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
		var tr='';
		tr+='<tr>';
		tr+='<td>'+number+'</td>';
		tr+='<td>'+newsEntity.operator.operName+'</td>';
		tr+='<td>'+newsEntity.shop.shopName+'</td>';
		tr+='<td>'+dealType(newsEntity.type)+'</td>';
		tr+='<td>'+dealState(newsEntity.state)+'</td>';
		tr+='<td>'+getLocalTime(newsEntity.createTime)+'</td>';
		tr+='<td><button onclick=\"javascript:detail(\''+newsEntity.purId+'\');\" class=\"layui-btn\">详细</button>';
		if(newsEntity.state != 1){
			tr += '<button onclick=\"javascript:updPurchase(\''+newsEntity.purId+'\');\" class=\"layui-btn\">编辑</button>';
		}
		tr += '<button onclick=\"javascript:deletePurchase(\''+newsEntity.purId+'\');\" class=\"layui-btn\">删除</button></td>';
		tr+='</tr>';
		html+=tr;
	}
	$tbody.html(html);
};

function detail(purId){
	$("#purId").val(purId);
	layer.open({
		title: '采购单详情',
		type: 2,
		area: ['1000px', '580px'],
		fixed: false, //不固定
		maxmin: true,
		content: '../operator/purchase/detail'
	});
}

function updPurchase(purId){
	$("#purId").val(purId);
	layer.open({
		title: '修改采购单',
		type: 2,
		area: ['1000px', '580px'],
		fixed: false, //不固定
		maxmin: true,
		content: '../operator/purchase/updatePage'
	});
}

function deletePurchase(purId){
	$("#modal-delete").modal("show");
	$("#btn-delete").on("click",function(){
	 $.ajax({
	        url: "${pageContext.request.contextPath}/operator/purchase/deletePurchase",
	        type: "POST",
	        data: {"purId":purId},
	        success: function (data) {
	        	$("#modal-delete").modal("hide");
	        	var row = JSON.parse(data);
	        	if(row.code==1){
	        		layer.msg('删除成功', {icon: 1});
	        	}else{
	        		layer.msg('删除失败', {icon: 2});
	        	}
	        	var entity = {};
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

function dealType(type){
	if(type==null){
		return "";
	}else if(type=="1"){
		return "采购";
	}else if(type=="2"){
		return "入库";
	}else if(type=="3"){
		return "转仓";
	}
	return type;
}

function dealState(state){
	if(state==null){
		return "";
	}else if(state=="0"){
		return "待审核";
	}else if(state=="1"){
		return "审核通过";
	}else if(state=="-1"){
		return "审核未通过";
	}
	return state;
}

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