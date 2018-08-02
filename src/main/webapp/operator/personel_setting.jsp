<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!-->
<html>
<!--<![endif]-->
<head>
    <style type="text/css">
        .checkbox-list-scroll {
            height: 130px;
            overflow-y: scroll;
        }
    </style>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>个人信息</title>
    <%@ include file="global/mylink.jsp" %>
</head>

<body class="layer-body">
<div class="layer-show">
    <div class="form-horizontal" id="form-user">
        <div class="form-group">
            <label class="col-md-3 control-label">用户名</label>
            <div class="col-md-8">
                <input type="text" disabled class="form-control" name="operAccount" value="${user.operAccount}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label">微信id</label>
            <div class="col-md-5">
                <input type="text" disabled class="form-control" name="openId" value="<c:if test="${user.openId == null}">未绑定</c:if>${user.openId}"/>
            </div>
            <div class="pull-right">
                <button type="button" onclick="unbind()" class="layui-btn layui-btn-danger">解除绑定</button>
                <button type="button" onclick="bind()" class="layui-btn layui-btn-normal">绑定帮助</button>
                <button type="button" style="visibility: hidden" class="btn btn-info"></button>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label">密码</label>
            <div class="col-md-8">
                <input type="text" class="form-control" name="operPwd" placeholder="不填则不修改"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label">姓名</label>
            <div class="col-md-8">
                <input type="text" class="form-control" name="operName" value="${user.operName}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label">性别</label>
            <div class="col-md-8">
                <div class="icheck-list">
                    <label>
                        <input type="radio" class="icheck" name="sex" value="男"
                               <c:if test="${user.sex == '男'}">checked</c:if> /> <span class="text">男</span>
                    </label>
                    <label>
                        <input type="radio" class="icheck" name="sex" value="女"
                               <c:if test="${user.sex == '女'}">checked</c:if> /> <span class="text">女</span>
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label">手机号码</label>
            <div class="col-md-8">
                <input type="text" class="form-control" name="phone" value="${user.phone}"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label">权限组</label>
            <div class="col-md-8">
                <c:if test="${group != null}">
                    <input type="text" class="form-control" disabled name="group" value="${group.groupName}"/>
                </c:if>
                <c:if test="${group == null}">
                    <input type="text" class="form-control" disabled name="group" value="超级管理员"/>
                </c:if>
            </div>
        </div>
         <div class="form-group">
            <label class="col-md-3 control-label">管理站点</label>
            <div class="col-md-8">
                <div class="layui-collapse" lay-accordion="" >
			  				<div class="layui-colla-item">
						<h2 class="layui-colla-title">站点列表</h2>
			    		<div class="layui-colla-content layui-show">
							<c:forEach items="${shops}" var="shop">
									<label style="width:30%">
	                                    <input data-id="${shop.shopId}" checked disabled data-text="${shop.shopName}"
	                                           type="checkbox" class="icheck" name="shop_id" value="${shop.shopId}"/> <span
	                                        class="text">${shop.shopName}</span>
                               		</label>
							</c:forEach>
							</div>
							</div>
		 			 </div>
            </div>
        </div>
        <div class="form-group" id="float_father" style="background-color:#ffffff; width:100%;">
            <label class="col-md-3 control-label"> </label>
            <div class="col-md-6">
                <button type="submit" id="submit" class="btn btn-primary">修改个人资料</button>
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
    $(function () {
    	scrollx({id:'float_father', l:-18, t:505, f:1});
    	layui.use(['element', 'layer'], function(){
   		 var element = layui.element();
   		  var layer = layui.layer;
   		});

        $("#submit").on("click", function () {
            $("#resultMessage").html("");//点击提交按钮时，先将操作结果置空
            var operAccount = $("input[name='operAccount']").val();//操作员账号
            var operPwd = $("input[name='operPwd']").val();//操作员密码
            var operName = $("input[name='operName']").val();//操作员姓名
            var sex = $("input[name='sex']:checked").val();//操作员状态
            var phone = $("input[name='phone']").val();//操作员姓名
            var param = {};
            if ($.trim(operAccount).length == 0) {
                $("#resultMessage").html("用户名不能为空");
                return;
            }
            param.operAccount = operAccount;
            if ($.trim(operName).length == 0) {
                $("#resultMessage").html("姓名不能为空");
                return;
            }
            param.operName = operName;
            param.sex = sex;
            if ($.trim(phone).length == 0) {
                $("#resultMessage").html("手机号码不能为空");
                return;
            }
            if (!phone.match(/^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/)) {
                $("#resultMessage").html("手机号码格式不正确");
                return;
            }
            param.phone = phone;
            if (operPwd) {
                if ($.trim(operPwd).length < 6) {
                    $("#resultMessage").html("用户密码过短");
                    return;
                } else {
                    param.operPwd = operPwd;
                }
            }
            $.ajax({
                url: "${ctx}/operator/user/personel_setting",
                type: "POST",
                data: param,
                timeout: 8000
            }).done(function (data) {
                var row = JSON.parse(data);
                if (row.code == 0 && row.data) {//成功
                    layer.msg("修改成功", {icon: 1});
                    setTimeout("closePanel()", 1000);
                    return;
                }
                $("#resultMessage").html("修改失败，请稍后再试");
            })

        });
    });

    function unbind() {
        $.post("${ctx}/operator/user/unbind", {}, function (data) {
            if (data.code == 0 && data.data) {
                $("input[name='openId']").val("未绑定");
                layer.msg("解绑成功", {icon: 1});
            }else{
                layer.msg("解绑失败，请稍后再试", {icon: 5});
            }
        },'json');
    }

    function bind() {
        layer.open({
            type: 1,
            area: ['500px', '300px'],
            fixed: false, //不固定
            content: '&nbsp;&nbsp;1、关注微信公众号<br/>&nbsp;&nbsp;2、在输入框输入“bind_operator 用户名 密码”（不含双引号）',
        });
    }

    function closePanel() {//关闭
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index); //执行关闭
    }
</script>
</html>