<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ilasFun" uri="/WEB-INF/permissionFun.tld" %>
<%@taglib prefix="ilas" uri="/WEB-INF/permissionTag.tld" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%
    request.setAttribute("type", request.getParameter("type"));
%>
<script>
    <%--$(function() {--%>
    <%--var id = "#" + '<%=request.getParameter("type")%>';--%>
    <%--var dl = $(id).parents("dl");--%>
    <%--if(dl.find("dd").length){--%>
    <%--if(dl.hasClass("active")){--%>
    <%--dl.removeClass("active");--%>
    <%--dl.find("dd").slideUp();--%>
    <%--}else{--%>
    <%--dl.addClass("active");--%>
    <%--dl.find("dd").slideDown();--%>
    <%--}--%>
    <%--$(id).attr("class", "active");--%>
    <%--}else{--%>
    <%--dl.addClass("active");--%>
    <%--$(id).attr("class", "font-imt");--%>
    <%--}--%>
    <%--});--%>
</script>
<div class="global-nav" id="side-nav">
    <dl <c:if test="${type == 'operator' || type == 'group'}">class="active"</c:if>>
        <dt>
            <a href="javascript:;">
                <i class="nav-icon icon-user-set"></i>
                用户管理
                <span class="caret"></span>
            </a>
        </dt>
        <dd>
            <ul>
                <ilas:hasPermission code="0101">
                    <li id="operator" <c:if test="${type == 'operator'}">class="active"</c:if>><a
                            href="${ctx}/operator/operator.jsp">操作员管理</a></li>
                </ilas:hasPermission>
                <ilas:hasPermission code="0201">
                    <li id="group" <c:if test="${type == 'group'}">class="active"</c:if>><a
                            href="${ctx}/operator/group.jsp">权限管理</a></li>
                </ilas:hasPermission>
            </ul>
        </dd>
    </dl>
    <ilas:hasPermission code="0301">
        <dl <c:if test="${type == 'customer'}">class="active"</c:if>>
            <dt>
                <a href="${ctx}/operator/customer.jsp" id="customer"
                   <c:if test="${type == 'customer'}">class="active"</c:if>>
                    <i class="nav-icon icon-users"></i>
                    顾客管理
                </a>
            </dt>
        </dl>
    </ilas:hasPermission>
    <ilas:hasPermission code="0401">
    <dl <c:if test="${type == 'shop_list'}">class="active"</c:if>>
        <dt>
            <a id="shop_a" href="javascript:;">
                <i class="nav-icon icon-site"></i>
                站点管理
                <span class="caret"></span>
            </a>
        </dt>
        <dd>
            <ul>
                <ilas:hasPermission code="0401">
                    <li id="shop_list" <c:if test="${type == 'shop_list'}">class="active"</c:if>><a
                            href="${ctx}/operator/shop.jsp">站点列表</a></li>
                </ilas:hasPermission>
                <!-- <li id="shop_comm_dis"><a href="shopCommDiscount.jsp">销售折扣设置</a></li>
                <li id="shop_comm"><a href="shopCommodity.jsp">站点销售设置</a></li> -->
            </ul>
        </dd>
    </dl>
    </ilas:hasPermission>
    <ilas:hasPermission code="0501,0601,0801,0701,0901">
    <dl <c:if test="${type == 'comm_list' || type == 'comm_type' || type == 'shop_comm_dis' || type == 'shop_comm' || type == 'comm_promotion'}">class="active"</c:if>>
        <dt>
            <a href="javascript:;">
                <i class="nav-icon icon-goods"></i>
                商品管理
                <span class="caret"></span>
            </a>
        </dt>
        <dd>
            <ul>
                <ilas:hasPermission code="0501">
                    <li id="comm_list" <c:if test="${type == 'comm_list'}">class="active"</c:if>><a
                            href="${ctx}/operator/commoditylist.jsp">商品列表</a></li>
                </ilas:hasPermission>
                <ilas:hasPermission code="0601">
                    <li id="comm_type" <c:if test="${type == 'comm_type'}">class="active"</c:if>><a
                            href="${ctx}/operator/commoditytype.jsp">分类设置</a></li>
                </ilas:hasPermission>
                <ilas:hasPermission code="0801">
                    <li id="shop_comm_dis" <c:if test="${type == 'shop_comm_dis'}">class="active"</c:if>><a
                            href="${ctx}/operator/shopCommDiscount.jsp">促销单管理</a></li>
                </ilas:hasPermission>
                <ilas:hasPermission code="0701">
                    <li id="shop_comm" <c:if test="${type == 'shop_comm'}">class="active"</c:if>><a
                            href="${ctx}/operator/shopCommodity.jsp">商品调价</a></li>
                </ilas:hasPermission>
                <ilas:hasPermission code="0901">
                    <li id="comm_promotion" <c:if test="${type == 'comm_promotion'}">class="active"</c:if>><a
                            href="${ctx}/operator/commoditypromotion.jsp">促销管理</a></li>
                </ilas:hasPermission>
            </ul>
        </dd>
    </dl>
    </ilas:hasPermission>
    <dl <c:if test="${type == 'depot_list'}">class="active"</c:if>>
        <dt>
            <a id="depot_list"
               <c:if test="${type == 'depot_list'}">class="active"</c:if> href="depot.jsp">
                <i class="nav-icon icon-stock"></i>
                库存管理
            </a>
        </dt>
    </dl>
    <ilas:hasPermission code="1001">
        <dl <c:if test="${type == 'check_list'}">class="active"</c:if>>
            <dt>
                <a id="check_list"
                   <c:if test="${type == 'check_list'}">class="active"</c:if> href="${ctx}/operator/check.jsp">
                    <i class="nav-icon icon-pie"></i>
                    盘点管理
                </a>
            </dt>
        </dl>
    </ilas:hasPermission>
    <ilas:hasPermission code="1101">
        <dl <c:if test="${type == 'finance_list' || type == 'order_list'}">class="active"</c:if>>
            <dt>
                <a href="javascript:;">
                    <i class="nav-icon icon-chart"></i>
                    财经管理
                    <span class="caret"></span>
                </a>
            </dt>
            <dd>
                <ul>
                    <li id="finance_list" <c:if test="${type == 'finance_list'}">class="active"</c:if>>
                        <a href="${ctx}/operator/finance.jsp">财经管理</a></li>
                    <li id="order_list" <c:if test="${type == 'order_list'}">class="active"</c:if>>
                        <a href="${ctx}/operator/orderList.jsp">销售管理</a></li>
                </ul>
            </dd>
        </dl>
    </ilas:hasPermission>
    <dl <c:if test="${type == 'purchase_list'}">class="active"</c:if>>
        <dt>
            <a href="javascript:;">
                <i class="nav-icon icon-shopping"></i>
                采购单管理
                <span class="caret"></span>
            </a>
        </dt>
        <dd>
            <ul>
                <li id="purchase_list" <c:if test="${type == 'purchase_list'}">class="active"</c:if>>
                    <a href="${ctx}/operator/purchase.jsp">采购单管理</a></li>
            </ul>
        </dd>
    </dl>
    <ilas:hasPermission code="1201">
    <dl <c:if test="${type == 'news'}">class="active"</c:if>>
        <dt>
            <a href="javascript:;">
                <i class="nav-icon icon-message"></i>
                消息管理
                <span class="caret"></span>
            </a>
        </dt>
        <dd>
            <ul>
                <li id="news" <c:if test="${type == 'news'}">class="active"</c:if>>
                    <a href="${ctx}/operator/message_processing.jsp">留言管理</a></li>
            </ul>
        </dd>
    </dl>
    </ilas:hasPermission>
    <ilas:hasPermission code="1301,1302,1401">
    <dl <c:if test="${type == 'tag_import' || type == 'wechat_setting' || type == 'operator_log'}">class="active"</c:if>>
        <dt>
            <a href="javascript:;">
                <i class="nav-icon icon-set"></i>
                系统设置
                <span class="caret"></span>
            </a>
        </dt>
        <dd>
            <ul>
                <ilas:hasPermission code="1401">
                    <li id="tag_import" <c:if test="${type == 'tag_import'}">class="active"</c:if>>
                        <a href="${ctx}/operator/electag/tagImport">标签导入</a>
                    </li>
                    <li id="operator_log" <c:if test="${type == 'operator_log'}">class="active"</c:if>>
                        <a href="${ctx}/operator/operatorLog">标签导入日志</a>
                    </li>
                </ilas:hasPermission>
                <ilas:hasPermission code="1301,1302">
                    <li id="wechat_setting" <c:if test="${type == 'wechat_setting'}">class="active"</c:if>>
                        <a href="${ctx}/operator/wechat_setting.jsp">微信设置</a>
                    </li>
                </ilas:hasPermission>
            </ul>
        </dd>
    </dl>
    </ilas:hasPermission>
</div>
