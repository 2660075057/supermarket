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
<title>顾客管理</title>
<%@ include file="global/mylink.jsp" %>
<!--[if IE]>
	<link rel="stylesheet" type="text/css" href="css/ie.css" />
<![endif]-->
<!--[if lt IE 9]>
	<script type="text/javascript" src="${ctx}/operator/assets/html5.js" ></script>
	<script type="text/javascript" src="${ctx}/operator/assets/ie.js" ></script>
<![endif]-->
</head>
<body>
<input type="hidden" value="" id="coustId">
<%@ include file="global/header.jsp" %>
<div class="main-content">
	<%--左侧菜单--%>
    <jsp:include page="menu.jsp" flush="true" >
        <jsp:param name="type" value="customer"  />
    </jsp:include>
	<%--左侧菜单--%>
	<div class="main">
		<div class="bread">
			<a href="#">首页</a> &gt; 顾客管理
		</div>
		<div class="main-container">
			<div class="page-bar clearfix">
				<div class="pull-left">
					<div class="page-title">
						<span class="line"></span>
						顾客管理
					</div>
				</div>
				<div class="pull-right">
					<form class="form-inline">
						<input type="text" class="form-control bar-form-control margin-right-5" placeholder="请输入用户名或手机号码查询" id="userNameOrtelPhone"/>
						<button type="button" class="btn btn-primary" onclick="onSearch()">查询</button>
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
						        	<th width="160">ID</th>
									<th>用户名</th>
									<th>性别</th>
									<th>手机号</th>
									<th>最后进店时间</th>
									<th>累计消费</th>
									<th width="160">操作</th>
						        </tr>
						    </thead>
						    <tbody>
						       
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
<script src="${ctx}/operator/assets/echart/echarts.common.min.js"></script>
<script>

$(document).ready(function(){
	var entity = {};
	var condition = $("#userNameOrtelPhone").val();
	if($.trim(condition).length==0){
		entity.queryCondition = null;
	}else{
		entity.queryCondition = condition;
	}
    layuiPage(entity);
})

function onSearch(){
	$(".table").find("tbody").html("");
	var entity = {};
	var condition = $("#userNameOrtelPhone").val();
	if($.trim(condition).length==0){
		entity.queryCondition = null;
	}else{
		entity.queryCondition = condition;
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
                ,limits: [10,20,30,40]
                ,jump: function(obj){
                	var currPage = obj.curr;
	            	var pageSize = obj.limit;
                    var page = {};
                    page["pageSize"] = obj.limit;
                    page["pageCurrent"] = obj.curr;
                    queryCustomer(page,param, currPage, pageSize);
                }
            });
        });
}

function count() {
    var count=0;
	var entity = {};
	var condition = $("#userNameOrtelPhone").val();
	if($.trim(condition).length==0){
		entity.queryCondition = null;
	}else{
		entity.queryCondition = condition;
	}
    $.ajax({
        url: "${pageContext.request.contextPath}/operator/customer/queryCustomerCount",
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

function queryCustomer(page,param, currPage, pageSize){
	 	$.ajax({
 			url: "${pageContext.request.contextPath}/operator/customer/queryCustomer",
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
		tr+='<td>'+newsEntity.coustId+'</td>';
		tr+='<td>'+dealNull(newsEntity.coustName)+'</td>';
		tr+='<td>'+newsEntity.sex+'</td>';
		tr+='<td>'+dealNull(newsEntity.phone)+'</td>';
		tr+='<td>'+getLocalTime(newsEntity.lastCome)+'</td>';
		tr+='<td>'+mustTwoDecimal(newsEntity.paymentTotal/100)+'</td>';
		tr+='<td><button onclick=\"javascript:detail(\''+newsEntity.coustId+'\');\" class=\"layui-btn\">详细</button></td>';
		/* tr+='<td><a href="javascript:detail(\''+newsEntity.coustId+'\');"">详细</a></td>'; */
		tr+='</tr>';
		html+=tr;
	}
	$tbody.html(html);
};

function detail(coustId){
	$("#coustId").val(coustId);
	layer.open({
		title: '顾客详情',
		type: 2,
		area: ['1000px', '580px'],
		fixed: false, //不固定
		maxmin: true,
		content: '../operator/customer/detail'
	});
}



function dealNull(probablyNull){//处理空值
	if(probablyNull==null){
		return "";
	}
	return probablyNull;
}

function getLocalTime(nS) {
    if(!nS){
        return "";
    }
    return new Date(parseInt(nS)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
}

function mustTwoDecimal(number){
    var num = twoDecimal(number).toString();
    var index = num.indexOf(".");
    if(index <= 0){
        num += '.';
        index = num.length - 1;
    }
    while((index + 3) != num.length){
        num += '0';
    }
    return num;
}

function twoDecimal(number){
    if(isNaN(number)){
        return;
    }
    return Math.round(number*100)/100;
}

</script>
</body>
</html>