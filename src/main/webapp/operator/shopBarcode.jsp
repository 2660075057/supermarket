<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
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
    <title>站点二维码</title>
    <style type="text/css">
        *{
            margin:0px;
            padding:0px;
        }
        body, button, input, select, textarea {
            font: 12px/16px Verdana, Helvetica, Arial, sans-serif;
        }
        p{
            width:603px;
            padding-top:3px;
            overflow:hidden;
        }
        #container {
            min-width:603px;
            min-height:300px;
        }
        #latLng{
            width: 295px;
        }
        #address{
            width: 295px;
        }
    </style>
    <%@ include file="global/mylink.jsp"%>
    <script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp"></script>

</head>

<body class="layer-body">
<input type="hidden" id="shopid" value="-1">
<div class="layer-show">
    <%--<div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">添加站点</h4>
    </div>--%>
    <div class="form-horizontal" id="form-user">
        <div class="form-group">
            <label class="col-md-2 control-label" style="color: #000000">进店二维码</label>
            <div class="col-md-3"><img src="${ctx}/operator/img/barcodeImg?deleteWhite=true&data=${inBarcode}"></div>


            <div class="col-md-3">
                <div class="form-group">
                    <div>进店二维码生成参数</div>
                    <div style="margin-top: 5px">
                        <textarea class="form-control" style="height:130px;width: 300px">${inBarcode}</textarea>
                    </div>
                </div>
            </div>
        </div>
        <br/>
        <br/>
        <br/>
        <div class="form-group">
            <label class="col-md-2 control-label" style="color: #000000">出店二维码</label>
            <div class="col-md-3"><img src="${ctx}/operator/img/barcodeImg?deleteWhite=true&data=${outBarcode}"></div>
            <div class="col-md-3">
                <div class="form-group">
                    <div>出店二维码生成参数</div>
                    <div style="margin-top: 5px">
                        <textarea class="form-control" style="height:130px;width: 300px">${outBarcode}</textarea>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>