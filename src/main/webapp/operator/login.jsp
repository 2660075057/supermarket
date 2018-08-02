<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
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
    <title>登录</title>
    <script type="text/javascript" src="${ctx}/operator/assets/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/operator/assets/bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/operator/assets/bootstrap/css/bootstrap.min.css"/>

    <!-- icheck -->
    <script type="text/javascript" src="${ctx}/operator/assets/icheck/icheck.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/operator/assets/icheck/minimal/orange.css"/>

    <!-- bootstrap select -->
    <script type="text/javascript" src="${ctx}/operator/assets/select/select.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/operator/assets/select/select.min.css"/>

    <!-- bootstrap data time range picker -->
    <script type="text/javascript" src="${ctx}/operator/assets/daterangepicker/moment.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${ctx}/operator/assets/daterangepicker/daterangepicker.css"/>
    <script type="text/javascript" src="${ctx}/operator/assets/daterangepicker/daterangepicker.js"></script>
    <!-- layui -->
    <link rel="stylesheet" type="text/css" href="${ctx}/operator/layui/css/layui.css" />
    <!-- theme green -->
    <link rel="stylesheet" type="text/css" href="${ctx}/operator/assets/bootstrap-green.css"/>
    <script type="text/javascript" src="${ctx}/operator/assets/base.js"></script>
    <!--[if IE]>
    <link rel="stylesheet" type="text/css" href="css/ie.css"/>
    <![endif]-->
    <!--[if lt IE 9]>
    <script type="text/javascript" src="\assets/html5.js"></script>
    <script type="text/javascript" src="js/ie.js"></script>
    <![endif]-->
</head>
<body class="body-login">
<div class="login-box">
    <div class="form-horizontal" id="form-login">
        <div class="col-md-6">
            <div class="login-title">
                <img src="${ctx}/operator/images/logo-login.png" alt="" />
                <div class="txt">
                    广西葡萄物联网科技有限公司
                    <div class="version">后台管理系统 v 1.0</div>
                </div>
                <!-- <span class="version">v1.0</span> -->
            </div>
            <div class="login-container">
                <div class="form-group">
                    用户账号
                    <div class="margin-top-10 relative">
                        <i class="icon-login-user"></i>
                        <input type="text" id="account" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    账号密码
                    <div class="margin-top-10 relative">
                        <i class="icon-login-pswd"></i>
                        <input type="password" id="pwd" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <button type="submit" onclick="login()" class="btn btn-primary">登录</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${ctx}/operator/assets/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/operator/layui/layui.js" ></script>
<script>
    $(function () {
        layui.use('layer', function(){
            var layer = layui.layer;
        });
    });
    function login() {
        var account = $('#account').val();
        var pwd = $('#pwd').val();
        if(!account){
            layer.tips('请输入正确的用户名', '#account', {
                tips: [1, '#3595CC'],
                time: 4000
            });
            return ;
        }
        if(!pwd){
            layer.tips('请输入密码', '#pwd', {
                tips: [1, '#3595CC'],
                time: 4000
            });
            return ;
        }

        if (account && pwd) {
            $.post('${ctx}/operator/user/login', {'account': account, 'pwd': pwd}, function (data) {
                if (data.data == 1) {
                    layer.msg('登录成功');
                    window.location.href = '${ctx}/operator/guide.jsp';
                } else {
                    layer.msg('用户名或密码错误');
                }
            }, 'json');
        }
    }
</script>
</html>
