<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="ilas" uri="/WEB-INF/permissionTag.tld" %>
<%@taglib prefix="ilasFun" uri="/WEB-INF/permissionFun.tld" %>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<title>用户管理</title>
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
<input type="hidden" value="" id="operId">
<%@ include file="global/header.jsp" %>
<div class="main-content">
	<jsp:include page="menu.jsp" flush="true" >
        <jsp:param name="type" value="operator"  />
    </jsp:include>
	<div class="main">
		<div class="bread">
			<a href="#">首页</a> &gt; <a href="#">用户管理</a> &gt; 操作员管理
		</div>
		<div class="main-container">
			<div class="page-bar clearfix">
				<div class="pull-left">
					<div class="page-title">
						<span class="line"></span>
						操作员管理
					</div>
				</div>
                <ilas:hasPermission code="0102">
				<div class="pull-right">
					 <button type="button" class="btn btn-primary" id="add_operator"><i class="btn-icon btn-icon-add"></i>  添加操作员</button>
				</div>
                </ilas:hasPermission>
			</div>
			<div class="global-table margin-top-gap">
				<div id="main-table">
					<div class="table-responsive">
					    <table class="table table-bordered table-striped table-global">
						    <thead>
						        <tr>								
						        	<th width="100">序号</th>
									<th>用户名</th>
									<th>姓名</th>
									<th>性别</th>
									<th>手机</th>
									<th>用户类型</th>
									<th>所属站点</th>
									<th>权限组</th>
									<th>账户状态</th>
									<th>操作</th>
						        </tr>
						    </thead>
						    <tbody>
						       
						    </tbody>
						</table>
					</div>
				</div>
			</div>
			<%-- <%@include file="page/pagination.jsp" %>  --%>
			<div class="pager-md clearfix margin-top-gap">
                <div class="pull-right">
                    <div id="layuipage"></div>
                </div>
            </div>
		</div>
	</div>
</div>
<script>
//检查是否有编辑权限
var _edit = ${ilasFun:checkPermissionByCode("0103")};
$(function(){
    $("#add_operator").on("click", function () {
        layer.open({
            title: '添加操作员',
            type: 2,
            area: ['1000px', '580px'],
            fixed: false, //不固定
            maxmin: true,
            content: '../operator/user/add'
        });
    });//初始化添加操作员按钮
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
                queryOperator(page, currPage, pageSize);
            }
        });
    });
	
});

function count() {
    var count=0;
    $.ajax({
        url: "${pageContext.request.contextPath}/operator/user/queryOperatorCount",
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

function queryOperator(param, currPage, pageSize){
	 	$.ajax({
 			url: "${pageContext.request.contextPath}/operator/user/queryOperator",
 			type: "POST",
 			data: param,
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
		tr+='<td>'+newsEntity.operAccount+'</td>';
		tr+='<td>'+newsEntity.operName+'</td>';
		tr+='<td>'+newsEntity.sex+'</td>';
		tr+='<td>'+newsEntity.phone+'</td>';
		tr+='<td>'+userType(newsEntity.shops)+'</td>';
		tr+='<td>'+dealNull(newsEntity.shops)+'</td>';
		tr+='<td>'+roleType(newsEntity.userGroup)+'</td>';
		tr+='<td>'+stateDeal(newsEntity.state)+'</td>';
		tr+='<td>';
		if(_edit){
		    tr += '<button onclick=\"javascript:edit(\''+newsEntity.operId+'\');\" class=\"layui-btn\">修改</button>';
        }
        tr += '</td>';
		/* tr+='<td><a href="javascript:edit(\''+newsEntity.operId+'\');">修改账户</a></td>'; */
		tr+='</tr>';
		html+=tr;
	}
	$tbody.html(html);
};

function edit(operId){
	$("#operId").val(operId);
	layer.open({
		title: '修改操作员',
		type: 2,
		area: ['1000px', '580px'],
		fixed: false, //不固定
		maxmin: true,
		content: '../operator/user/edit'
	});
}

function dealNull(probablyNull){//处理空值
	if(probablyNull==null){
		return "";
	}
	var result = "";
	for(var s in probablyNull){
		result += probablyNull[s].shopName+",";
	}
	if($.trim(result).length>1){
		result = result.substring(0, result.length-1);
	}
	return result;
}

function userType(probablyNull){//用户类型
	if(probablyNull==null){
		return "平台管理员";
	}
	return "站点管理员";
}

function roleType(obj){//权限组处理
	if(obj==null){
		return "";
	}
	return obj.groupName;
}

function stateDeal(state){//处理状态
	if(state==0){
		return "正常<span class=\"ico-status ico-status-success\"></span>";
	}else{
		return "正常 <span class=\"ico-status ico-status-disabled\"></span>";
	}
}
</script>
</body>
</html>