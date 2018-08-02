<%@ page language="java" pageEncoding="UTF-8"%>
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
    <title>商品列表</title>
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
        [v-cloak] {
            display: none;
        }
    </style>
    <%@ include file="global/mylink.jsp"%>
</head>

<body class="layer-body">
<div class="main-content">
	<div class="main">
		<div class="main-container">
			<div class="page-bar clearfix">

				<div class="pull-left">
                    <button type="button" class="layui-btn" onclick="downloadTemplate()">下载导入模板</button>
                    <button type="button" class="layui-btn" id="test3"><i class="layui-icon"></i><span id="sc">上传文件</span></button>
				</div>
			</div>
            <div id="show">
                <fieldset class="layui-col-md12" id="progressrow">
                    <legend><span style="font-size: 18px;color: #aaa;padding: 8px;font-weight: bolder;font-family: 华文楷体;">导入数据</span></legend>
                    <div class="col-md-6">
                        <div class="icheck-list">
                            <button type="submit" id="turn" class="btn btn-primary">开始导入</button>
                            <button type="submit" id="batchDown" onclick="batchDown()" class="btn btn-primary">下载结果文件</button>
                        </div>
                    </div>
                    <br>
                    <div class="modal-body">
                    </div>
                </fieldset>
            </div>

		</div>

	</div>
</div>
</body>
<script type="text/javascript">
    var uuid="";
    $(function() {
        $('#batchDown').hide();
        layui.use('upload', function(){
            var $ = layui.jquery,upload = layui.upload;
            upload.render({
                elem: '#test3'
                ,url: '${ctx}/operator/file/upShopData'
                ,accept: 'file'
                ,exts: 'xls|xlsx'
                ,done: function(res){
                    uuid = res.data;
                    layer.msg('上传文件成功');
                    $('#sc').text("重新上传");
                    $('#turn').show();
                    $('#batchDown').hide();
                }
            });
        });
        layui.use('element', function(){
           element = layui.element;
        });

        $("#turn").click(function (event) {
            if(uuid != null && uuid.length >0){
                var index = layer.load(1, {
                    shade: [0.1,'#fff'] //0.1透明度的白色背景
                });
                var param = {};
                var url = "${ctx}/operator/commodity/importCommodity";
                $.ajax({
                    url: url,
                    type: "POST",
                    data: {"req":JSON.stringify(param),"uuid":uuid},
                    dataType:'json',
                    success: function (data) {
                        if (data.code == 0) {
                            if(data.data == 'success'){
                                layer.msg('导入商品成功');
                            }else if(data.data == 'has_fail'){
                                layer.msg('存在导入失败数，请下载结果文件查看');
                                $('#batchDown').show();
                            }
                        } else {
                            console.warn(data);
                            layer.msg('文件损坏，请重新上传');
                        }
                        layer.close(index);
                    }
                });
            }else{
                layer.tips('请上传文件,上传格式(.xls、.xlsx)', '#test3', {
                    tips: [3,'red']
                });
            }
            return false;
        });
    });

    function batchDown() {
        var url = "${ctx}/operator/commodity/importCommodityResult";
        window.open(url);
    }

    function downloadTemplate() {
        window.open('${ctx}/operator/enclosure/commodityExport.xls');
    }
    
</script>
</html>