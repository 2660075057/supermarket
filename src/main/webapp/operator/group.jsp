<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<title>权限管理</title>
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
        <jsp:param name="type" value="group"  />
    </jsp:include>
	<div class="main">
		<div class="bread">
			<a href="#">首页</a> &gt; <a href="#">用户管理</a> &gt; 权限管理
		</div>
		<div class="main-container">
			<div class="page-bar clearfix">
				<div class="pull-left">
					<div class="page-title">
						<span class="line"></span>
						权限管理
					</div>
				</div>
				<div class="pull-right">
					 <button type="button" class="btn btn-primary" id="add_group">
					 	<i class="btn-icon btn-icon-add"></i>  新增权限
					 </button>
				</div>
			</div>
			<div class="global-table margin-top-gap">
				<div id="main-table">
					<div class="table-responsive">
					    <table class="table table-bordered table-striped table-global">
						    <thead>
						        <tr>
						        	<th width="100">序号</th>								
						        	<th>权限</th>
									<th>概述</th>
									<th width="170">操作</th>
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
$(function(){
	initPage();
	$("#add_group").on("click",function(){
		layer.open({
			title: '添加权限',
			type: 2,
			area: ['1000px', '580px'],
			fixed: false, //不固定
			maxmin: true,
			content: '../operator/group/add'
		});
	});//初始化添加操作员按钮
});

function initPage(){//初始化页面
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
                page["pageSize"]=obj.limit;
                page["pageCurrent"]=obj.curr;
                queryGroup(page, currPage, pageSize);
            }
        });
    });
}

function count() {
    var count=0;
    $.ajax({
        url: "${pageContext.request.contextPath}/operator/group/queryGroupCount",
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

function queryGroup(param, currPage, pageSize){
	 	$.ajax({
 			url: "${pageContext.request.contextPath}/operator/group/queryGroup",
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
		tr+='<td>'+newsEntity.groupName+'</td>';
		tr+='<td>'+permissionDeal(newsEntity.permissions)+'</td>';
		tr+='<td><button onclick=\"javascript:edit(\''+newsEntity.groupId+'\');\" class=\"layui-btn\">修改</button>'
		+'<button onclick=\"javascript:deleteGroup(\''+newsEntity.groupId+'\');\" class=\"layui-btn\">删除</button></td>';
		/* tr+='<td><a href="javascript:edit(\''+newsEntity.groupId+'\');" class="margin-right-10">修改账户</a>'
		+'<a href="javascript:deleteGroup(\''+newsEntity.groupId+'\');">删除</a></td>'; */
		tr+='</tr>';
		html+=tr;
	}
	$tbody.html(html);
};

function edit(groupId){
	$("#groupId").val(groupId);
	layer.open({
		title: '修改权限',
		type: 2,
		area: ['1000px', '580px'],
		fixed: false, //不固定
		maxmin: true,
		content: '../operator/group/edit'
	});
}

//0警告1成功2失败
function deleteGroup(groupID){
	$("#modal-delete").modal("show");
	$("#btn-delete").on("click",function(){
	 $.ajax({
	        url: "${pageContext.request.contextPath}/operator/group/deleteGroup",
	        type: "POST",
	        data: {"groupId":groupID},
	        success: function (data) {
	        	$("#modal-delete").modal("hide");
	        	var row = JSON.parse(data);
	        	//返回0正常删除，返回1表示存在使用权限组的用户,返回-1表示失败
	        	if(row.code==0){//失败
	        		layer.msg('删除成功', {icon: 1});
	        	}else if(row.code==1){
	        		layer.msg('存在使用权限组的用户', {icon: 0});
	        	}else{
	        		layer.msg('删除失败', {icon: 2});
	        	}
	        	initPage();
	        }
	 });
    });
}


function dealNull(probablyNull){//处理空值
	if(probablyNull==null){
		return "";
	}
	return probablyNull;
}


function permissionDeal(obj){//权限组处理
	if(obj==null){
		return "";
	}
	var result = "";
	for(var p in obj){
		result += obj[p].name + ",";
	}
	return result.substring(0, result.length-1);
}


</script>
</body>
</html>