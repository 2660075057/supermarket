<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ilasFun" uri="/WEB-INF/permissionFun.tld" %>
<%@taglib prefix="ilas" uri="/WEB-INF/permissionTag.tld" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<title>商品调价</title>
    <%@ include file="global/mylink.jsp" %>
    <style>
        .btn:hover, .btn:active, .btn:link {
            background-color: #009688;
        }
    </style>
</head>
<body>
<%@ include file="global/header.jsp" %>
<div class="main-content">
    <jsp:include page="menu.jsp" flush="true" >
        <jsp:param name="type" value="shop_comm"  />
    </jsp:include>
	<div class="main">
		<div class="bread">
			<a href="#">首页</a> &gt; <a href="#">商品管理</a> &gt; 商品调价
		</div>
		<div class="main-container">
			<div class="page-bar clearfix">
				<div class="pull-left">
					<div class="page-title">
						<span class="line"></span>
                        商品调价
					</div>
				</div>
				<div class="pull-right">
					<form class="form-inline">
						<select id="shop" class="selectpicker margin-right-5" style="width: 100px">

						</select>
						<select id="queryType" class="selectpicker margin-right-5">
							<option value="1">商品条码</option>
							<option value="2">商品名称</option>
						</select>
						<input type="text" id="queryVal" class="form-control bar-form-control margin-right-5" placeholder="请输入检索值" />
						<button type="button" class="btn btn-primary margin-right-5" id="query">查询</button>
                        <ilas:hasPermission code="0702">
                            <button id="add_shopComm" type="button" class="btn btn-primary" data-toggle="modal" data-target="#add-stock-modal">
                                <i class="btn-icon btn-icon-add"></i>  新增站点销售
                            </button>
                        </ilas:hasPermission>
					</form>
				</div>
			</div>
			<div class="global-table margin-top-gap">
				<div id="main-table">
					<div class="table-responsive">
					    <table class="table table-bordered table-striped table-global">
						    <thead>
						        <tr>	
						        	<th width="100">序号</th>							
						        	<th width="160">商品条码</th>
									<th>商品名称</th>
									<th>类型</th>
									<th>售价</th>
									<th>站点名</th>
									<th width="200">操作</th>
						        </tr>
						    </thead>
						    <tbody id="datalist">


						    </tbody>
						</table>
					</div>
				</div>
			</div>
            <div class="pager-md clearfix margin-top-gap">
                <div class="pull-right">
                    <div id="layuipage"></div>
                </div>
            </div>
		</div>
	</div>
</div>
<!-- 弹出添加销售设置框框-->
<div class="modal fade" id="edit-stock-modal" tabindex="-1" role="dialog" aria-hidden="true" >
    <input type="hidden" id="shopid">
    <input type="hidden" id="commid">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <a href="javascript:close('#edit-stock-modal');" class="close" data-dismiss="modal" aria-hidden="true">&times;</a>
                <h4 class="modal-title">修改销售价格</h4>
            </div>
            <div class="modal-body">
                <div class="form-horizontal" id="form-edit">
                    <div class="form-group">
                        <label class="col-md-3 control-label">商品名称</label>
                        <div class="col-md-8">
                            <p class="form-control-static" id="commName"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">商品条码</label>
                        <div class="col-md-8">
                            <p class="form-control-static" id="commCode"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">销售站点</label>
                        <div class="col-md-8">
                            <p class="form-control-static" id="shopName"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">当前售价</label>
                        <div class="col-md-8">
                            <p class="form-control-static" id="oldPrice"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">修改售价</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" id="newPrice" onchange="if(/[^\d.]/g.test(this.value)){layer.msg('只能输入数字');this.value='';}" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label"> </label>
                        <div class="col-md-8">
                            <button type="submit" id="update" class="btn btn-primary">提交</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="add-stock-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">添加站点销售</h4>
            </div>
            <div class="modal-body">
                <div class="form-horizontal" id="form-add">
                    <div class="form-group">
                        <label class="col-md-3 control-label">站点选择</label>
                        <div class="col-md-6">
                            <select id="shopc" class="selectpicker">

                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">商品选择</label>
                        <div class="col-md-6">
                            <select id="comm" class="selectpicker">

                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label">销售价格</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="comm_price" onchange="if(/[^\d.]/g.test(this.value)){layer.msg('只能输入数字');this.value='';}" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label"> </label>
                        <div class="col-md-8">
                            <button type="submit" id="add" class="btn btn-primary">提交</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 弹出删除框框-->
<div class="modal fade" id="modal-delete" tabindex="-1" role="dialog" aria-hidden="true">
    <input type="hidden" id="del_commid" value="">
    <input type="hidden" id="del_shopid" value="">
    <div class="modal-dialog" style="width:400px;">
        <div class="modal-content">
            <div class="modal-body">
                <div class="model-delete-tips text-center">
                    <div class="tips"><i class="icon-tips"></i> 是否确定删除该条数据？</div>
                    <div class="btns">
                        <button type="button" class="btn btn-danger margin-right-10" id="btn-delete">
                            删除
                        </button>
                        <a href="javascript:close('#modal-delete');" class="btn btn-primary" data-dismiss="modal" aria-hidden="true">
                            取消
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function(){
        $("#btn-delete").on("click" , function(){
            $("#modal-delete").modal("hide");
            $.success_layer("删除成功！")
        })
    })
</script>
<script type="text/javascript">
    $(function() {
        var param={};
        setpage(param);
        $.ajax({
            url: "../operator/shop/shopList",
            type: "POST",
            async: false,
            data: {"page":"{}","req":"{}"},
            success: function (data) {
                var str = "<option value=\"-1\">全部站点</option>";
                var strc = "";
                var rows = jQuery.parseJSON(data);
                for(var i=0;i<rows.length;i++){
                    var row = rows[i];
                    str +="<option value=\""+row.shopId+"\">"+row.shopName+"</option>";
                    strc +="<option value=\""+row.shopId+"\">"+row.shopName+"</option>";
                }
                $("#shop").html(str);
                $('#shop').selectpicker('refresh');
                $("#shopc").html(strc);
                $('#shopc').selectpicker('refresh');
            }
        });
        $.ajax({
            url: "../operator/commodity/commodityList",
            type: "POST",
            async: false,
            data: {"page":"{}","req":"{}"},
            success: function (data) {
                var str = "";
                var rows = jQuery.parseJSON(data);
                for(var i=0;i<rows.length;i++){
                    var row = rows[i];
                    str +="<option value=\""+row.commId+"\">"+row.commName+"</option>";
                }
                $("#comm").html(str);
                $('#comm').selectpicker('refresh');
            }
        });

        layui.use('layer', function(){
            var layer = layui.layer;
        });

    });
    $("#query").click(function(){

        var queryType = $('#queryType').val();
        var queryVal = $('#queryVal').val();
        var shopId = $("#shop").val();
        var param={};
        if(shopId != -1){
            param["shopId"]=shopId;
        }
        if(queryVal !=null && queryVal.length >0){
            if(queryType == 1){
                param["barcode"]=queryVal.replace(/(^\s*)|(\s*$)/g, "");
            }else if(queryType == 2){
                param["commName"]=queryVal.replace(/(^\s*)|(\s*$)/g, "");
            }
        }
        setpage(param);
    });
    function selectList(page,param, currPage, pageSize) {
    	var startIndex = 0;
		if(currPage>0){
			startIndex = (currPage-1)*pageSize;
		}
        $.ajax({
            url: "../operator/shopcommodity/selectShopCommListParam",
            type: "POST",
            async: false,
            data: {"page":JSON.stringify(page),"req":JSON.stringify(param)},
            success: function (data) {
                var str = "";
                if(data != null && data.length >0){
                    var rows = jQuery.parseJSON(data);
                    for(var i=0;i<rows.length;i++){
                    	var number = parseInt(startIndex)+parseInt(i+1);
                        var row = rows[i];
                        var price = parseFloat(row.price/100)+" 元";
                        str +="<tr>";
                        str +="<td>"+number+"</td><td>"+row.barcode+"</td><td>"+row.commName+"</td><td>"+row.typeName+"</td>";
                        str +="<td>"+price+"</td><td>"+row.shopName+"</td>";
                        str +="<td>";
                        <ilas:hasPermission code="0703">
                          str +="<button onclick =\"javascript:updateShopComm("+row.shopId+","+row.commId+");\" class=\"layui-btn\">修改</button>";
                        </ilas:hasPermission>
                        <ilas:hasPermission code="0704">
                          str +="<button onclick =\"javascript:deteleShopComm("+row.shopId+","+row.commId+");\" class=\"layui-btn\">删除</button>";
                        </ilas:hasPermission>
                        str +="</td>";
                        str +="</tr>";
                    }
                }
                $('#datalist').html(str);
            }
        });
    }
    function getcount() {
        var queryType = $('#queryType').val();
        var queryVal = $('#queryVal').val();
        var shopId = $("#shop").val();
        var param={};
        if(shopId != -1){
            param.shopId=shopId;
        }
        if(queryVal !=null && queryVal.length >0){
            if(queryType == 1){
                param.barcode=queryVal.replace(/(^\s*)|(\s*$)/g, "");
            }else if(queryType == 2){
                param.commName=queryVal.replace(/(^\s*)|(\s*$)/g, "");
            }
        }
        var count=0;
        $.ajax({
            url: "../operator/shopcommodity/countShopCommList",
            type: "POST",
            async: false,
            data: param,
            success: function (data) {
                count = parseInt(data);
            }
        });
        return count;
    }
    function setpage(param) {
        layui.use(['laypage', 'layer'], function(){
            var laypage = layui.laypage
                ,layer = layui.layer;
            laypage.render({
                elem: 'layuipage'
                ,count: getcount()
                ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
                ,limit : 10
                ,limits: [10,20,30,40]
                ,jump: function(obj){
                	var currPage = obj.curr;
	            	var pageSize = obj.limit;
                    var page={};
                    page["pageSize"]=obj.limit;
                    page["pageCurrent"]=obj.curr;
                    selectList(page,param, currPage, pageSize);
                }
            });
        });
    }
    function updateShopComm(shopid,commid) {
        $('#edit-stock-modal').addClass("in");
        $('#edit-stock-modal').attr("style","display: block;");
        $('#newPrice').val("");
        var param={};
        param.shopId=shopid;
        param.commId = commid;
        $.ajax({
            url: "../operator/shopcommodity/selectByPrimaryKey",
            type: "POST",
            async: false,
            data: param,
            success: function (data) {
                var row = jQuery.parseJSON(data);
                $('#shopid').val(row.shopId);
                $('#commid').val(row.commId);
                $('#commName').html(row.commName);
                $('#commCode').html(row.barcode);
                $('#shopName').html(row.shopName);
                var price = parseFloat(row.price/100)+" 元";
                $('#oldPrice').html(price);
            }
        });

    }
<ilas:hasPermission code="0703">
    $("#update").click(function(){
        var price = $('#newPrice').val();
        if(price ==null || price.length <= 0){
            layer.tips('请填写修改价格！', '#newPrice', {
                tips: [3,'red']
            });
            $("html,body").animate({scrollTop:$("#newPrice").offset().top},100);
            return;
        }
        var param={};
        param.shopId=$('#shopid').val();
        param.commId = $('#commid').val();
        param.price = price*100;
        $.ajax({
            url: "../operator/shopcommodity/updatePrice",
            type: "POST",
            async: false,
            data: param,
            success: function (data) {
               if(data == 1){
                   parent.location.href="${pageContext.request.contextPath}/operator/shopCommodity.jsp";
               }
            }
        });
    });
</ilas:hasPermission>
    function close(id) {
        $(id).removeClass("in");
        $(id).attr("style","");
    }
    function deteleShopComm(shopid,commid) {
        $('#del_shopid').val(shopid);
        $('#del_commid').val(commid);
        $('#modal-delete').addClass("in");
        $('#modal-delete').attr("style","display: block;");

        /*layer.confirm('你确定要删除这条记录吗？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            var param={};
            param.shopId=shopid;
            param.commId = commid;
            $.ajax({
                url: "../operator/shopcommodity/deleteByPrimaryKey",
                type: "POST",
                async: false,
                data: param,
                success: function (data) {
                    parent.location.href="${pageContext.request.contextPath}/operator/shopCommodity.jsp";
                }
            });
        }, function(){

        });*/
    }
<ilas:hasPermission code="0704">
    $("#btn-delete").click(function(){
        var param={};
        param.shopId=$('#del_shopid').val();
        param.commId = $('#del_commid').val();
        $.ajax({
            url: "../operator/shopcommodity/deleteByPrimaryKey",
            type: "POST",
            async: false,
            data: param,
            success: function (data) {
                parent.location.href="${pageContext.request.contextPath}/operator/shopCommodity.jsp";
            }
        });
    });
</ilas:hasPermission>
<ilas:hasPermission code="0702">
    $("#add").click(function(){
        var price = $('#comm_price').val();
        if(price ==null || price.length <= 0){
            layer.tips('请填写销售价格！', '#comm_price', {
                tips: [3,'red']
            });
            $("html,body").animate({scrollTop:$("#comm_price").offset().top},100);
            return;
        }
        var param={};
        param.shopId=$('#shopc').val();
        param.commId = $('#comm').val();
        param.price = price*100;
        $.ajax({
            url: "../operator/shopcommodity/insertSelective",
            type: "POST",
            async: false,
            data: param,
            success: function (data) {
                if(data == 1){
                    parent.location.href="${pageContext.request.contextPath}/operator/shopCommodity.jsp";
                }else{
                    layer.tips('商店已存在该商品！', '#comm', {
                        tips: [3,'red']
                    });
                }
            }
        });
    });
</ilas:hasPermission>
</script>
</body>
</html>