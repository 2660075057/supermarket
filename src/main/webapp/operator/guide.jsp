<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ilas" uri="/WEB-INF/permissionTag.tld" %>
<%@taglib prefix="ilasFun" uri="/WEB-INF/permissionFun.tld" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>功能指引</title>
    <!--[if IE]>
    <link rel="stylesheet" type="text/css" href="css/ie.css"/>
    <![endif]-->
    <!--[if lt IE 9]>
    <script type="text/javascript" src="\assets/html5.js"></script>
    <script type="text/javascript" src="js/ie.js"></script>
    <![endif]-->
    <%@ include file="global/mylink.jsp" %>
</head>
<body>
<%@ include file="global/header.jsp" %>
<div class="main-content">
    <div class="main-guide-md">
        <h1 class="guide-title">欢迎 <%=operatorSession.getOperatorInfo().getOperName()%> 来到后台管理系统，请选择你所需要的服务</h1>
        <ul class="main-guide">
            <ilas:hasPermission code="0101,0201">
                <li>
                    <a href="${ctx}/operator/operator.jsp">
                        <i class="guide-icon guide-icon-user"></i>操作员管理
                    </a>
                </li>
            </ilas:hasPermission>
            <ilas:hasPermission code="0301">
                <li>
                    <a href="${ctx}/operator/customer.jsp">
                        <i class="guide-icon guide-icon-user-set"></i>顾客管理
                    </a>
                </li>
            </ilas:hasPermission>
            <ilas:hasPermission code="0401">
                <li>
                    <a href="${ctx}/operator/shop.jsp">
                        <i class="guide-icon guide-icon-site"></i>站点管理
                    </a>
                </li>
            </ilas:hasPermission>
            <ilas:hasPermission code="0501,0601,0801,0701,0901">
                <li>
                    <a href="${ctx}/operator/commoditylist.jsp">
                        <i class="guide-icon guide-icon-goods"></i>商品管理
                    </a>
                </li>
            </ilas:hasPermission>
            <li>
                <a href="${ctx}/operator/depot.jsp">
                    <i class="guide-icon guide-icon-stock"></i>库存管理
                </a>
            </li>
            <ilas:hasPermission code="1001">
                <li>
                    <a href="${ctx}/operator/check.jsp">
                        <i class="guide-icon guide-icon-pie"></i>盘点管理
                    </a>
                </li>
            </ilas:hasPermission>
            <ilas:hasPermission code="1101">
                <li>
                    <a href="${ctx}/operator/finance.jsp">
                        <i class="guide-icon guide-icon-chart"></i>财经管理
                    </a>
                </li>
            </ilas:hasPermission>
            <li>
                <a href="${ctx}/operator/purchase.jsp">
                    <i class="guide-icon guide-icon-shopping"></i>采购单管理
                </a>
            </li>
            <ilas:hasPermission code="1201">
                <li>
                    <a href="${ctx}/operator/message_processing.jsp">
                        <i class="guide-icon guide-icon-message"></i>留言管理
                    </a>
                </li>
            </ilas:hasPermission>
            <ilas:hasPermission code="1301,1302,1401">
                <li>
                    <a href="${ctx}/operator/electag/tagImport">
                        <i class="guide-icon guide-icon-set"></i>系统设置
                    </a>
                </li>
            </ilas:hasPermission>
        </ul>
    </div>
</div>
</body>
<script>
    $(function () {
        layui.use('layer', function () {
            var layer = layui.layer;
        });
    });
</script>
</html>