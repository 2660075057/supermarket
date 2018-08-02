<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ilas" uri="/WEB-INF/permissionTag.tld" %>
<%@taglib prefix="ilasFun" uri="/WEB-INF/permissionFun.tld" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!-->
<html>
<!--<![endif]-->

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<title>留言详情</title>
<%@ include file="global/mylink.jsp"%>
</head>

<body class="layer-body">
<input type="hidden" id="shopId" value="${message.shop.shopId}">
<input type="hidden" id="coustId" value="${message.customer.coustId}">
<input type="hidden" id="messageId" value="${message.messageId}">
	<div class="layer-show">
	  <div class="form-horizontal" id="form-user">
          	<div class="form-group">
			    <label class="col-md-3 control-label">商店名称</label>
			    <div class="col-md-8">
                    <p class="form-control-static" id="shopName">${message.shop.shopName}</p>
			    </div>
			</div>
          	<div class="form-group">
			    <label class="col-md-3 control-label">顾客名称</label>
			    <div class="col-md-8">
                    <p class="form-control-static" id="cusName">${message.customer.coustName}</p>
			    </div>
			</div>
          <div class="form-group">
              <label class="col-md-3 control-label">留言内容</label>
              <div class="col-md-8">
                  <textarea name="" id="messagel" class="form-control" readonly="readonly" style="height:150px">${message.content}</textarea>
              </div>
          </div>
			 <div class="form-group">
                 <label class="col-md-3 control-label">回复内容</label>
                 <div class="col-md-8">
                     <textarea name="" id="messageh" class="form-control" style="height:150px"
                               <c:if test="${message.state == 1 or !ilasFun:checkPermissionByCode(1202)}">readonly</c:if>>${message.message.content}</textarea>
                 </div>
             </div>
          <ilas:hasPermission code="1202">
          <c:if test="${message.state == 0}">
              <div class="form-group">
                  <label class="col-md-3 control-label"> </label>
                  <div class="col-md-8">
                      <button type="submit" onclick="replyMessage()" class="btn btn-primary">回复</button>
                      <span id="resultMessage" style="color: red;"></span>
                  </div>
              </div>
          </c:if>
          </ilas:hasPermission>
        </div>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	layui.use('layer', function(){
		  var layer = layui.layer;
	});
});
<ilas:hasPermission code="1202">
function replyMessage() {
    var shopId = $('#shopId').val();
    var coustId = $('#coustId').val();
    var messageId = $('#messageId').val();
    var messageh = $('#messageh').val();
    var param = {};
    if(messageh == null || messageh.length <=0){
        layer.tips('请输入回复内容', '#messageh', {
            tips: [3,'red']
        });
        return;
    }
    if(shopId == null || shopId.length <=0){
        layer.msg("回复失败，获取不到商店信息，请刷新");
        return;
    }
    if(coustId == null || coustId.length <=0){
        layer.msg("回复失败，获取不到顾客信息，请刷新");
        return;
    }
    if(messageId == null || messageId.length <=0){
        layer.msg("回复失败，获取不到留言信息，请刷新");
        return;
    }
    param.shopId = shopId;
    param.userId = coustId;
    param.replyId = messageId;
    param.content = messageh;
    param.userType = 1;
    $.ajax({
        url: "${ctx}/operator/messageadmin/replyMessage",
        type: "POST",
        data: param,
        success: function (data) {
            console.log(data);
            var row = JSON.parse(data);
            if(row.data){//成功
                layer.msg("回复成功");
                closePanel();
            }else{
                layer.msg("回复失败");
            }
        }
    });
}
</ilas:hasPermission>
function closePanel(){//关闭
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index); //执行关闭
	parent.location.reload();
}
</script>
</html>