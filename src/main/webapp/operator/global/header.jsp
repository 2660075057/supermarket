<%@ page import="com.grape.supermarket.operator.OperatorSession" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
    OperatorSession operatorSession = (OperatorSession) request.getSession().getAttribute(OperatorSession.SESSION_ID);
    if(operatorSession == null){
        String contextPath = request.getContextPath();
        String url = contextPath.equals("/")?"/operator/login.jsp":contextPath+"/operator/login.jsp";
        response.sendRedirect(url);
        return ;
    }
%>
<div class="global-header" style="z-index: 9999">
	<div class="clearfix">
		<div class="pull-left" onclick="javascript:window.location.href='${ctx}/'">
			<div class="title">
				<span class="logo"></span>
                <div class="txt">
                    广西葡萄物联网科技有限公司
                    <div class="version">后台管理系统 v 1.0</div>
                </div>
			</div>
		</div>
		<div class="pull-right">
			<ul class="nav navbar-nav">
				<li class="margin-right-10" ><a onclick="personalSetting()"><i class="bar-icon bar-icon-user"></i> 管理员 <%=operatorSession.getOperatorInfo().getOperName()%></a></li>
				<li><a href="${ctx}/operator/user/logout">退出</a></li>
			</ul>
		</div>
	</div>
</div>
<script>
    layui.use('layer');
    function personalSetting() {
        layer.open({
            title: '修改操作员',
            type: 2,
            area: ['1000px', '580px'],
            fixed: false, //不固定
            maxmin: true,
            content: '${ctx}/operator/user/personel_setting'
        });
    }
</script>