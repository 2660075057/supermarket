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
    <title>微信设置</title>
    <%@ include file="global/mylink.jsp" %>
    <!--[if IE]>
    <link rel="stylesheet" type="text/css" href="css/ie.css"/>
    <![endif]-->
    <!--[if lt IE 9]>
    <script type="text/javascript" src="assets/html5.js"></script>
    <script type="text/javascript" src="js/ie.js"></script>
    <![endif]-->
</head>
<body>
<%@ include file="global/header.jsp" %>
<div class="main-content">
    <jsp:include page="menu.jsp" flush="true">
        <jsp:param name="type" value="wechat_setting"/>
    </jsp:include>
    <div class="main">
        <div class="bread">
            首页 &gt; 系统设置 &gt; 微信设置
        </div>
        <div class="main-container">
            <div class="page-bar clearfix">
                <div class="pull-left">
                    <div class="page-title">
                        <span class="line"></span>
                        微信设置
                    </div>
                </div>
                <div class="pull-right">
                    <form class="form-inline">
                    </form>
                </div>
            </div>
            <div class="global-table margin-top-gap">
                <div id="main-table">
                    <div class="table-responsive">
                        <table class="table table-bordered table-striped table-global">
                            <thead>
                            <tr>
                                <th width="200px">功能名称</th>
                                <th>功能描述</th>
                                <th width="150px">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <ilas:hasPermission code="1302">
                            <tr>
                                <td>微信banner设置</td>
                                <td>设置微信首页的banner</td>
                                <td>
                                    <button type="button" class="btn btn-primary margin-right-5" onclick="bannerSetting()">设置</button>
                                </td>
                            </tr>
                            </ilas:hasPermission>
                            <ilas:hasPermission code="1301">
                            <tr>
                                <td>微信菜单设置</td>
                                <td>设置微信主页的下方菜单</td>
                                <td>
                                    <button type="button" class="btn btn-primary margin-right-5" onclick="menuSetting()">设置</button>
                                </td>
                            </tr>
                            </ilas:hasPermission>
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
<script>
    $(function () {
        layui.use('layer');
    });
    <ilas:hasPermission code="1302">
    function bannerSetting() {
    	layer.open({
			title: '微信banner设置',
			type: 2,
			area: ['800px', '580px'],
			fixed: false, //不固定
			maxmin: true,
			content: '${ctx}/operator/wechatSetting/banner'
		});
    }
    </ilas:hasPermission>
    <ilas:hasPermission code="1301">
    function menuSetting() {
        layer.open({
            title: '微信菜单设置',
            type: 2,
            area: ['1000px', '580px'],
            fixed: false, //不固定
            maxmin: false,
            content: 'wechatMenuEdit.jsp'
        });
    }
    </ilas:hasPermission>
</script>
</body>
</html>