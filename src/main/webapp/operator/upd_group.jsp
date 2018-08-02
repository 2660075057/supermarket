<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!-->
<html>
<!--<![endif]-->

<head>
<style type="text/css">
.checkbox-list-scroll{
	height:130px;
	overflow-y:scroll;
}
</style>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<title>添加权限</title>
<%@ include file="global/mylink.jsp"%>
</head>

<body class="layer-body">
	<div class="layer-show">
		<div class="form-horizontal" id="form-user">
          		<div class="form-group">
			    <label for="roleName" class="col-md-3 control-label">权限名</label>
			    <div class="col-md-8">
			    	<input type="text" class="form-control" name="groupName"/>
			    </div>
			</div>
			<div class="form-group">
			    <label class="col-md-3 control-label">设置权限</label>
				<div class="col-md-8">
					<div id="set-role-list" class="btns-list">
					</div>
						<div class="layui-collapse" lay-accordion="" >
			  				<div class="layui-colla-item">
						<h2 class="layui-colla-title">用户管理</h2>
			    		<div class="layui-colla-content layui-show">
							<c:forEach items="${permissions}" var="permission" begin="0" end="7">
									<label style="width:18%">
										<input data-id="${permission.id}" data-text="${permission.name}" type="checkbox" class="icheck" name="roleType" value="${permission.id}"/> <span class="text">${permission.name}</span>
									</label>
							</c:forEach>
							</div>
							</div>
		 			 </div>
		 			 <div class="layui-collapse" lay-accordion="" >
			  				<div class="layui-colla-item">
						<h2 class="layui-colla-title">顾客管理</h2>
			    		<div class="layui-colla-content layui-show">
							<c:forEach items="${permissions}" var="permission" begin="8" end="8">
									<label style="width:18%">
										<input data-id="${permission.id}" data-text="${permission.name}" type="checkbox" class="icheck" name="roleType" value="${permission.id}"/> <span class="text">${permission.name}</span>
									</label>
							</c:forEach>
							</div>
							</div>
		 			 </div>
		 			 <div class="layui-collapse" lay-accordion="" >
			  				<div class="layui-colla-item">
						<h2 class="layui-colla-title">站点管理</h2>
			    		<div class="layui-colla-content layui-show">
							<c:forEach items="${permissions}" var="permission" begin="9" end="11">
									<label style="width:18%">
										<input data-id="${permission.id}" data-text="${permission.name}" type="checkbox" class="icheck" name="roleType" value="${permission.id}"/> <span class="text">${permission.name}</span>
									</label>
							</c:forEach>
							</div>
							</div>
		 			 </div>
		 			 <div class="layui-collapse" lay-accordion="" >
			  				<div class="layui-colla-item">
						<h2 class="layui-colla-title">商品管理</h2>
			    		<div class="layui-colla-content layui-show">
							<c:forEach items="${permissions}" var="permission" begin="12" end="30">
									<label style="width:18%">
										<input data-id="${permission.id}" data-text="${permission.name}" type="checkbox" class="icheck" name="roleType" value="${permission.id}"/> <span class="text">${permission.name}</span>
									</label>
							</c:forEach>
							</div>
							</div>
		 			 </div>
		 			 <div class="layui-collapse" lay-accordion="" >
			  				<div class="layui-colla-item">
						<h2 class="layui-colla-title">盘点管理</h2>
			    		<div class="layui-colla-content layui-show">
							<c:forEach items="${permissions}" var="permission" begin="31" end="32">
									<label style="width:18%">
										<input data-id="${permission.id}" data-text="${permission.name}" type="checkbox" class="icheck" name="roleType" value="${permission.id}"/> <span class="text">${permission.name}</span>
									</label>
							</c:forEach>
							</div>
							</div>
		 			 </div>
		 			 <div class="layui-collapse" lay-accordion="" >
			  				<div class="layui-colla-item">
						<h2 class="layui-colla-title">财经管理</h2>
			    		<div class="layui-colla-content layui-show">
							<c:forEach items="${permissions}" var="permission" begin="33" end="33">
									<label style="width:18%">
										<input data-id="${permission.id}" data-text="${permission.name}" type="checkbox" class="icheck" name="roleType" value="${permission.id}"/> <span class="text">${permission.name}</span>
									</label>
							</c:forEach>
							</div>
							</div>
		 			 </div>
		 			 <div class="layui-collapse" lay-accordion="" >
			  				<div class="layui-colla-item">
						<h2 class="layui-colla-title">消息管理</h2>
			    		<div class="layui-colla-content layui-show">
							<c:forEach items="${permissions}" var="permission" begin="34" end="36">
									<label style="width:18%">
										<input data-id="${permission.id}" data-text="${permission.name}" type="checkbox" class="icheck" name="roleType" value="${permission.id}"/> <span class="text">${permission.name}</span>
									</label>
							</c:forEach>
							</div>
							</div>
		 			 </div>
		 			 <div class="layui-collapse" lay-accordion="" >
			  				<div class="layui-colla-item">
						<h2 class="layui-colla-title">系统管理</h2>
			    		<div class="layui-colla-content layui-show">
							<c:forEach items="${permissions}" var="permission" begin="37" end="39">
									<label style="width:25%">
										<input data-id="${permission.id}" data-text="${permission.name}" type="checkbox" class="icheck" name="roleType" value="${permission.id}"/> <span class="text">${permission.name}</span>
									</label>
							</c:forEach>
							</div>
							</div>
		 			 </div>
		 			 <div class="layui-collapse" lay-accordion="" >
			  				<div class="layui-colla-item">
						<h2 class="layui-colla-title">其它</h2>
			    		<div class="layui-colla-content">
							</div>
							</div>
		 			 </div>
		 		</div>
			</div>
			<div class="form-group" id="float_father" style="background-color:#ffffff; width:100%;"> 
			    <label class="col-md-3 control-label"> </label>
			    <div class="col-md-8" >
					<button type="submit" class="btn btn-primary">提交</button>
					<span id="resultMessage" style="color: red;"></span>
				</div>
			</div>
          	</div>
	</div>
</body>
<script type="text/javascript">
function scrollx(p) {
 var d = document, dd = d.documentElement, db = d.body, w = window, o = d.getElementById(p.id), ie6 = /msie 6/i.test(navigator.userAgent), style, timer;
 if (o) {
  cssPub = ";position:"+(p.f&&!ie6?'fixed':'absolute')+";"+(p.t!=undefined?'top:'+p.t+'px;':'bottom:0;');
  if (p.r != undefined && p.l == undefined) {
   o.style.cssText += cssPub + ('right:'+p.r+'px;');
  } else {
   o.style.cssText += cssPub + ('margin-left:'+p.l+'px;');
  }
  if(p.f&&ie6){
   cssTop = ';top:expression(documentElement.scrollTop +'+(p.t==undefined?dd.clientHeight-o.offsetHeight:p.t)+'+ "px" );';
   cssRight = ';right:expression(documentElement.scrollright + '+(p.r==undefined?dd.clientWidth-o.offsetWidth:p.r)+' + "px")';
   if (p.r != undefined && p.l == undefined) {
    o.style.cssText += cssRight + cssTop;
   } else {
    o.style.cssText += cssTop;
   }
   dd.style.cssText +=';background-image: url(about:blank);background-attachment:fixed;';
  }else{
   if(!p.f){
    w.onresize = w.onscroll = function(){
     clearInterval(timer);
     timer = setInterval(function(){
      //双选择为了修复chrome 下xhtml解析时dd.scrollTop为 0
      var st = (dd.scrollTop||db.scrollTop),c;
      c = st - o.offsetTop + (p.t!=undefined?p.t:(w.innerHeight||dd.clientHeight)-o.offsetHeight);
      if(c!=0){
       o.style.top = o.offsetTop + Math.ceil(Math.abs(c)/10)*(c<0?-1:1) + 'px';
      }else{
       clearInterval(timer);
      }
     },10)
    }
   }
  }
 }
}
</script>
<script type="text/javascript">
$(document).ready(function(){
	scrollx({id:'float_father', l:-18, t:503, f:1});
	var groupId = parent.$("#groupId").val();//得到权限组的一条记录
	$.ajax({
		url: "${pageContext.request.contextPath}/operator/group/queryOneGroup",
		type: "POST",
		data: {"groupId":groupId},
		timeout: 8000
	}).done(function(data){
		var row = JSON.parse(data);
		if(row.code==0){//成功
			var gr = row.data;
			fillVal(gr);
		}
	});
	layui.use(['element', 'layer'], function(){
		 var element = layui.element();
		  var layer = layui.layer;
	});
	
	$(".btn-primary").on("click",function (){
		$("#resultMessage").html("");//点击提交按钮时，先将操作结果置空
		var groupName = $("input[name='groupName']").val();//权限名
		var permission_ids = "";
		$("input[name='roleType']").each(function (){
			if($(this).parent().hasClass("checked")){
				permission_ids += $(this).val()+",";
			}
		});
		var param = {};
		if($.trim(groupName).length==0){
			$("#resultMessage").html("权限名不能为空");
			return ;
		}
		param.groupName = groupName;
		var groupId = parent.$("#groupId").val();
		param.groupId = groupId;
		if($.trim(permission_ids).length==0){
			$("#resultMessage").html("请选择权限");
			return ;
		}
		permission_ids = permission_ids.substring(0, permission_ids.length-1);
	 	$.ajax({
 			url: "${pageContext.request.contextPath}/operator/group/updateGroup",
 			type: "POST",
 			data: {"group":JSON.stringify(param), "permissionIds":permission_ids},
 			timeout: 8000
 		}).done(function(data){
 			var row = JSON.parse(data);
 			if(row.code==0){//成功
 				layer.msg("修改权限成功", {icon: 1});
 				setTimeout("closePanel()", 1000);
 				return;
 			}
 			$("#resultMessage").html("修改权限失败");
 		}) 
		
	});
});


function closePanel(){//关闭
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index); //执行关闭
	parent.location.reload();
}

function fillVal(group){
	$("input[name='groupName']").val(group.groupName);//权限名
	var roleTypes = new Array();
	var permissions = group.permissions;
	for(var p in permissions){
		var permission = permissions[p];
		roleTypes.push(permission.id);
	}
	$("input[name='roleType']").each(function(){
		for (var i = 0; i < roleTypes.length; i++) {
			if($.inArray($(this).data("id"), roleTypes)>-1){
				$(this).iCheck('check');
			}
		}
	})

}
</script>
</html>