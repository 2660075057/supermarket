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
<title>促销单管理</title>
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
<input type="hidden" value="" id="groupId">
<%@ include file="global/header.jsp" %>
<div class="main-content">
    <jsp:include page="menu.jsp" flush="true" >
        <jsp:param name="type" value="shop_comm_dis"/>
    </jsp:include>
	<div class="main">
		<div class="bread">
			<a href="#">首页</a> &gt; <a href="#">商品管理</a> &gt; 促销单管理
		</div>
		<div class="main-container">
			<div class="page-bar clearfix">
				<div class="pull-left">
					<div class="page-title">
						<span class="line"></span>
                        促销单管理
					</div>
				</div>
				<div class="pull-right">
					<form class="form-inline">
						<input type="text" class="form-control bar-form-control margin-right-5" placeholder="请输入促销单名称" id="title"/>
						<button type="button" class="btn btn-primary margin-right-5" onclick="onSearch()">查询</button>
						<button type="button" class="btn btn-primary" id="add_discount">
							<i class="btn-icon btn-icon-add"></i>  新增促销单
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
									<th>促销单名称</th>
									<th>操作员</th>
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
			title: '添加促销单',
			type: 2,
			area: ['1000px', '580px'],
			fixed: false, //不固定
			maxmin: true,
			content: '../operator/discountGroup/add'
		});
	});//初始化添加操作员按钮
	var entity = {};
    layuiPage(entity);
});

function onSearch(){
	$(".table").find("tbody").html("");
	var entity = {};
	entity.title = $("#title").val();
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
	                queryDiscountGroup(page, param, currPage, pageSize);
	            }
	        });
	    });
}

function count() {
    var count=0;
    $.ajax({
        url: "${pageContext.request.contextPath}/operator/discountGroup/queryDiscountGroupCount",
        type: "POST",
        async: false,
        data: {},
        success: function (data) {
        	var row = JSON.parse(data);
        	if(row.code==0){//成功
	            count = parseInt(row.data);
        	}
        }
    });
    return count;
}

function queryDiscountGroup(page, param, currPage, pageSize){
	 	$.ajax({
 			url: "${pageContext.request.contextPath}/operator/discountGroup/queryDiscountGroup",
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
		tr+='<td>'+newsEntity.title+'</td>';
		tr+='<td>'+newsEntity.operator.operName+'</td>';
		tr+='<td>'+getLocalTime(newsEntity.createTime)+'</td>';
		tr+='<td><button onclick=\"javascript:detail(\''+newsEntity.groupId+'\');\" class=\"layui-btn\">详细</button>'
		+'<button onclick=\"javascript:copyGroup(\''+newsEntity.groupId+'\');\" class=\"layui-btn\">复制</button>'
		+'<button onclick=\"javascript:deleteGroup(\''+newsEntity.groupId+'\');\" class=\"layui-btn\">删除</button></td>';
		tr+='</tr>';
		html+=tr;
	}
	$tbody.html(html);
};

function detail(groupId){
	$("#groupId").val(groupId);
	layer.open({
		title: '促销单详情',
		type: 2,
		area: ['1000px', '580px'],
		fixed: false, //不固定
		maxmin: true,
		content: '../operator/discountGroup/detail'
	});
}

function copyGroup(groupId){
	$("#groupId").val(groupId);
	layer.open({
		title: '复制促销单',
		type: 2,
		area: ['1000px', '580px'],
		fixed: false, //不固定
		maxmin: true,
		content: '../operator/discountGroup/copyGroup'
	});
}

function deleteGroup(groupID){
	$("#modal-delete").modal("show");
	$("#btn-delete").on("click",function(){
	 $.ajax({
	        url: "${pageContext.request.contextPath}/operator/discountGroup/deleteDiscountGroup",
	        type: "POST",
	        data: {"groupId":groupID},
	        success: function (data) {
	        	$("#modal-delete").modal("hide");
	        	var row = JSON.parse(data);
	        	//返回0正常删除，返回1表示存在使用的商店,返回-1表示失败
	        	if(row.code==0){
	        		layer.msg('删除成功', {icon: 1});
	        	}else if(row.code==1){
	        		layer.msg('存在使用的商店', {icon: 0});
	        	}else{
	        		layer.msg('删除失败', {icon: 2});
	        	}
	        	var entity = {};
	        	entity.title = $("#title").val();
	            layuiPage(entity);
	        }
	 });
    });
}

function getLocalTime(nS) {
    return new Date(parseInt(nS)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
}

</script>
</body>
</html>