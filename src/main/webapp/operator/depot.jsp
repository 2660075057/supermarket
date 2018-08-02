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
<title>库存管理</title>
    <%@ include file="global/mylink.jsp" %>
</head>
<body>
<input type="hidden" id="pageNum" value="1">
<input type="hidden" id="pageSum" value="10">
<%@ include file="global/header.jsp" %>
<div class="main-content">
    <jsp:include page="menu.jsp" flush="true" >
        <jsp:param name="type" value="depot_list"  />
    </jsp:include>
	<div class="main">
		<div class="bread">
			<a href="#">首页</a> &gt; 库存管理
		</div>
		<div class="main-container">
			<div class="page-bar clearfix">
				<div class="pull-left">
					<div class="page-title">
						<span class="line"></span>
						库存管理
					</div>
				</div>
                <div class="pull-right">
                    <div class="form-inline">
                        <label>
                            <input data-id="1" data-text="只看有库存" type="checkbox" class="icheck" id="denum" value="1"/> <span class="text">只看有库存的</span>
                        </label>&nbsp;&nbsp;&nbsp;&nbsp;
                        <select id="shop" class="selectpicker margin-right-5" style="width: 100px">

                        </select>
                        <select id="queryType" class="selectpicker margin-right-5">
                            <option value="1">商品条码</option>
                            <option value="2">商品名称</option>
                            <option value="3">商品类型</option>
                        </select>
                        <input type="text" id="queryVal" class="form-control bar-form-control margin-right-5" placeholder="请输入检索值" />
                        <button type="button" class="btn btn-primary margin-right-5" id="query">查询</button>
                        <button id="add_shopComm" type="button" class="btn btn-primary" data-toggle="modal" data-target="#add-stock-modal2">
                            <i class="btn-icon btn-icon-add"></i>  新增库存
                        </button>
                        <button onclick="javascript:daoRu();" class="btn btn-primary">批量导入</button>
                    </div>
                    
                </div>
			</div>
			<div class="global-table margin-top-gap">
				<div id="main-table">
					<div class="table-responsive">
					    <table class="table table-bordered table-striped table-global">
						    <thead>
						        <tr>	
						        	<th width="100">序号</th>							
									<th>商品条码</th>
									<th>商品名称</th>
									<th>类型</th>
									<th>所属站点</th>
									<th>库存</th>
									<th>报警阀值</th>
									<th>状态</th>
									<th width="400">操作</th>
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
                <input style="display:none;color:red;font-size:20px; border:0;" id="total" />
            </div>
		</div>
	</div>
</div>
<!-- 弹出添加添加框框-->
<div class="modal fade" id="add-stock-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <input type="hidden" id="add_depot" value="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <a href="javascript:close('#add-stock-modal');" class="close" data-dismiss="modal" aria-hidden="true">&times;</a>
                <h4 class="modal-title">添加库存</h4>
            </div>
            <div class="modal-body">
            	<div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 control-label">商品名称</label>
                        <div class="col-md-8">
                            <p class="form-control-static" id="add_commName"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">商品条码</label>
                        <div class="col-md-8">
                            <p class="form-control-static" id="add_barcode"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">当前库存</label>
                        <div class="col-md-8">
                            <p class="form-control-static" id="add_amount"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">入库站点</label>
                        <div class="col-md-8">
                            <p class="form-control-static" id="add_shop"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">报警阀值</label>
                        <div class="col-md-8">
                            <p class="form-control-static" id="add_threshold"></p>
                        </div>
                    </div>
					<%--<div class="form-group">--%>
						<%--<label class="col-md-3 control-label" for="orderTime">订单批次</label>--%>
					    <%--<div class="col-md-5">--%>
							<%--<input type="text" class="form-control" name="orderTime" id="orderTime" />--%>
							<%--<div class="tips margin-top-5 font-danger">--%>
								<%--该批次剩余商品数：50--%>
							<%--</div>--%>
					    <%--</div>--%>
					<%--</div>--%>
					<div class="form-group">
					    <label class="col-md-3 control-label">添加数量</label>
					    <div class="col-md-5">
							<input type="text" class="form-control" id="amount" />
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
<!-- 弹出转仓框框-->
<div class="modal fade" id="turn-stock-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <input type="hidden" id="turn_depot" value="-1">
    <input type="hidden" id="turn_commid" value="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<a href="javascript:close('#turn-stock-modal');" class="close" data-dismiss="modal" aria-hidden="true">&times;</a>
				<h4 class="modal-title">转仓</h4>
			</div>
			<div class="modal-body">
				<div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 control-label">商品名称</label>
                        <div class="col-md-8">
                            <p class="form-control-static" id="turn_commName"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">商品条码</label>
                        <div class="col-md-8">
                            <p class="form-control-static" id="turn_barcode"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">当前站点</label>
                        <div class="col-md-8">
                            <p class="form-control-static" id="turn_shop"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">报警阀值</label>
                        <div class="col-md-8">
                            <p class="form-control-static" id="turn_threshold"></p>
                        </div>
                    </div>
					<div class="form-group">
						<label class="col-md-3 control-label">入库站点</label>
						<div class="col-md-6">
							<select class="selectpicker" id="turn_addshop">

							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label" for="orderTime">当前库存</label>
						<div class="col-md-5">
							<p class="form-control-static" id="turn_amount"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">添加数量</label>
						<div class="col-md-6">
							<input type="text" class="form-control" id="AddCount" onchange="if(/[^\d]/g.test(this.value)){layer.msg('请输入正确数字！');this.value='';}" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label"> </label>
						<div class="col-md-8">
							<button type="submit" id="turn" class="btn btn-primary">提交</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 弹出赋值框框-->
<div class="modal fade" id="set-range-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <input type="hidden" id="set_depot" value="-1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<a href="javascript:close('#set-range-modal');" class="close" data-dismiss="modal" aria-hidden="true">&times;</a>
				<h4 class="modal-title">设置报警阀值</h4>
			</div>
			<div class="modal-body">
				<div class="form-horizontal">
					<div class="form-group">
						<label class="col-md-3 control-label">商品名称</label>
						<div class="col-md-8">
							<p class="form-control-static" id="set_commName"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">商品条码</label>
						<div class="col-md-8">
							<p class="form-control-static" id="set_barcode"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">当前库存</label>
						<div class="col-md-8">
							<p class="form-control-static" id="set_amount"></p>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">入库站点</label>
						<div class="col-md-8">
							<p class="form-control-static" id="set_shop"></p>
						</div>
					</div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">原报警阀值</label>
                        <div class="col-md-8">
                            <p class="form-control-static" id="set_threshold"></p>
                        </div>
                    </div>
					<div class="form-group">
						<label class="col-md-3 control-label"  for="rangeDanger">修改报警阀值</label>
						<div class="col-md-5">
							<input type="text" class="form-control" placeholder="不输入阀值默认不报警" name="rangeDanger" id="rangeDanger" onchange="if(/[^\d]/g.test(this.value)){layer.msg('请输入正确数字！');this.value='';}" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label"> </label>
						<div class="col-md-8">
							<button type="submit" id="set" class="btn btn-primary">提交</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="add-stock-modal2" tabindex="-1" role="dialog" aria-hidden="true">
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
                        <label class="col-md-3 control-label">库存数量</label>
                        <div class="col-md-6">
                            <input type="text" class="form-control" id="AddCount2" onchange="if(/[^\d]/g.test(this.value)){layer.msg('请输入正确数字！');this.value='';}" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-md-3 control-label"  for="rangeDanger">报警阀值</label>
                        <div class="col-md-5">
                            <input type="text" class="form-control" placeholder="不输入阀值默认不报警" id="rangeDanger2" onchange="if(/[^\d]/g.test(this.value)){layer.msg('请输入正确数字！');this.value='';}" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label"> </label>
                        <div class="col-md-8">
                            <button type="submit" id="add2" class="btn btn-primary">提交</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    $(function() {
        var param={};
        setpage(param);
        layui.use('layer', function(){
            var layer = layui.layer;
        });
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
                $("#shopc2").html(strc);
                $('#shopc2').selectpicker('refresh');
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
        $.ajax({
            url: "../operator/depot/costPrice",
            type: "POST",
            async: false,
            data: {"req":JSON.stringify(param)},
            success: function (data) {
            	var rows = jQuery.parseJSON(data);
            	if(rows.data != 0){
            		var price = parseFloat(rows.data/100);
            		$('#total').show();
            		$('#total').val("总金额￥" +price+" 元");
            	}else{
            		$('#total').hide();
            	}
            }
        });
    });
    $("#query").click(function(){
        var denum = -1;
        $('#denum:checked').each(function(){
             denum = $(this).val();
        });
        var queryType = $('#queryType').val();
        var queryVal = $('#queryVal').val();
        var shopId = $("#shop").val();
        var param={};
        if(shopId != -1){
            param["shop_id_str"]=shopId;
        }
        if(queryVal !=null && queryVal.length >0){
            if(queryType == 1){
                param["barcode"]=queryVal.replace(/(^\s*)|(\s*$)/g, "");
            }else if(queryType == 2){
                param["commName"]=queryVal.replace(/(^\s*)|(\s*$)/g, "");
            }else if(queryType == 3){
                param["typeName"]=queryVal.replace(/(^\s*)|(\s*$)/g, "");
            }
        }
        if(denum != -1){
            param["amount"]=1;
        }
        $.ajax({
            url: "../operator/depot/costPrice",
            type: "POST",
            async: false,
            data: {"req":JSON.stringify(param)},
            success: function (data) {
            	var rows = jQuery.parseJSON(data);
            	if(rows.data != 0){
            		var price = parseFloat(rows.data/100);
            		$('#total').show();
            		$('#total').val("总金额￥" + price+" 元");
            	}else{
            		$('#total').hide();
            	}
            }
        });
        setpage(param);
    });
    $("#set").click(function(){
        var depotid = $('#set_depot').val();
        var threshold = $('#rangeDanger').val();
        if(threshold == null || threshold.length <=0 || threshold < -1){
            threshold = -1;
        }
        var param={};
        param.depotId = depotid;
        param.threshold = threshold;
        updateDepot(param,'#set-range-modal','/setThreshold');
    });
    $("#add").click(function(){
        var depotid = $('#add_depot').val();
        var amount = $('#amount').val();
        if(amount == null || amount.length <=0){
            amount = 0;
        }
        var param={};
        param.depotId = depotid;
        param.amount = amount;
        updateDepot(param,'#add-stock-modal','/addAmount');
    });
    $("#turn").click(function(){
        var depotid = $('#turn_depot').val();
        var shopid = $('#turn_addshop').val();
        var commid = $('#turn_commid').val();
        var amount = $('#AddCount').val();
        var sum = $('#turn_amount').text();
        if(amount == null || amount.length <=0){
            layer.tips('请输入转仓数量！', '#AddCount', {
                tips: [3,'red']
            });
            $("html,body").animate({scrollTop:$("#AddCount").offset().top},100);
            return;
        }
        if(parseInt(amount) > parseInt(sum)){
            layer.tips('转仓数量不能大于库存数量！', '#AddCount', {
                tips: [3,'red']
            });
            $("html,body").animate({scrollTop:$("#AddCount").offset().top},100);
            return;
        }
        var param={};
        param.depotId = depotid;
        param.amount = amount;
        param.shopId = shopid;
        param.commId = commid;
        updateDepot(param,'#turn-stock-modal','/turnShopId');
    });
    $("#add2").click(function(){
        var shopid = $('#shopc').val();
        var commid = $('#comm').val();
        var amount = $('#AddCount2').val();
        var threshold = $('#rangeDanger2').val();
        if(threshold == null || threshold.length <=0 || threshold < -1){
            threshold = -1;
        }
        if(amount == null || amount.length <=0){
            layer.tips('库存数量！', '#AddCount2', {
                tips: [3,'red']
            });
            $("html,body").animate({scrollTop:$("#AddCount2").offset().top},100);
            return;
        }
        var param={};
        param.shopId = shopid;
        param.commId = commid;
        $.ajax({
            url: "../operator/depot/selectByShopIdAanCommId",
            type: "POST",
            async: false,
            data: param,
            success: function (data) {
                if(data != null && data != ""){
                    layer.tips('商店已存在该商品库存！', '#comm', {
                        tips: [3,'red']
                    });
                }else{
                    param.amount = amount;
                    param.threshold = threshold;
                    $.ajax({
                        url: "../operator/depot/insertDepot",
                        type: "POST",
                        async: false,
                        data: param,
                        success: function (data) {
                            if(data > 0){
                                parent.location.href="${pageContext.request.contextPath}/operator/depot.jsp";
                            }
                        }
                    });
                }
            }
        });
    });
    
    function updateDepot(param,id,url) {
        $.ajax({
            url: "../operator/depot"+url,
            type: "POST",
            async: false,
            data: param,
            success: function (data) {
                if(data >=0){
                    var denum = -1;
                    $('#denum:checked').each(function(){
                        denum = $(this).val();
                    });
                    var queryType = $('#queryType').val();
                    var queryVal = $('#queryVal').val();
                    var shopId = $("#shop").val();
                    var pd={};
                    if(shopId != -1){
                        pd["shop_id_str"]=shopId;
                    }
                    if(queryVal !=null && queryVal.length >0){
                        if(queryType == 1){
                            pd["barcode"]=queryVal.replace(/(^\s*)|(\s*$)/g, "");
                        }else if(queryType == 2){
                            pd["commName"]=queryVal.replace(/(^\s*)|(\s*$)/g, "");
                        }
                    }
                    if(denum != -1){
                        pd["amount"]=1;
                    }
                    var page={};
                    page["pageSize"]=$('#pageSum').val();
                    page["pageCurrent"]=$('#pageNum').val();
                    selectList(page,pd);
                    close(id);
                }
            }
        });
    }
    function selectList(page,param, currPage, pageSize) {
    	var startIndex = 0;
		if(page.pageCurrent>0){
			startIndex = (page.pageCurrent-1)*page.pageSize;
		}
        $.ajax({
            url: "../operator/depot/selectByCondition",
            type: "POST",
            async: false,
            data: {"page":JSON.stringify(page),"req":JSON.stringify(param)},
            success: function (data) {
                var str = "";
                var rows = jQuery.parseJSON(data);
                for(var i=0;i<rows.length;i++){
                	var number = parseInt(startIndex)+parseInt(i+1);
                    var row = rows[i];
                    var thr = "不报警";
                    if(row.threshold != -1){
                        thr = row.threshold;
                    }
                    str +="<tr>";
                    str +="<td>"+number+"</td><td>"+row.barcode+"</td><td>"+row.commName+"</td><td>"+row.typeName+"</td>";
                    str +="<td>"+row.shopName+"</td><td>"+row.amount+"</td><td>"+thr+"</td>";
                    if(row.state == 0){
                        str +="<td>销售中 <span class=\"ico-status ico-status-success\"></span></td>";
                    }else{
                        str +="<td>已下架 <span class=\"ico-status ico-status-disabled\"></span></td>";
                    }
                    str +="<td>";
                    str +="<button onclick=\"javascript:addAmount("+row.depotId+");\" class=\"layui-btn\">添加库存</button>";
                    str +="<button onclick=\"javascript:turnShopId("+row.depotId+","+row.shopId+","+row.commId+");\" class=\"layui-btn\">转仓</button>";
                    str +="<button onclick=\"javascript:setThreshold("+row.depotId+");\" class=\"layui-btn\">报警阀值</button>";
                    if(row.state == 0){
                        str +="<button onclick=\"javascript:openDepot("+row.depotId+",1);\" class=\"layui-btn\">下架</button>";
                    }else{
                        str +="<button onclick=\"javascript:openDepot("+row.depotId+",0);\" class=\"layui-btn\">上架</button>";
                    }
                    str +="</td>";
                    str +="</tr>";
                }
                $('#datalist').html(str);
            }
        });
    }
    function getcount() {
        var denum = -1;
        $('#denum:checked').each(function(){
            denum = $(this).val();
        });
        var queryType = $('#queryType').val();
        var queryVal = $('#queryVal').val();
        var shopId = $("#shop").val();
        var param={};
        if(shopId != -1){
            param["shop_id_str"]=shopId;
        }
        if(queryVal !=null && queryVal.length >0){
            if(queryType == 1){
                param["barcode"]=queryVal.replace(/(^\s*)|(\s*$)/g, "");
            }else if(queryType == 2){
                param["commName"]=queryVal.replace(/(^\s*)|(\s*$)/g, "");
            }
        }
        if(denum != -1){
            param["amount"]=1;
        }
        var count=0;
        $.ajax({
            url: "../operator/depot/countDepotByCondition",
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
                    $('#pageNum').val(obj.curr);
                    $('#pageSum').val(obj.limit);
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
    function close(id) {
        $(id).removeClass("in");
        $(id).attr("style","");
    }
    function addAmount(depotid) {
        $('#add_depot').val(depotid);
        $('#add-stock-modal').addClass("in");
        $('#add-stock-modal').attr("style","display: block;");
        $('#amount').val("");
        selectById(depotid,"#add");
    }
    function turnShopId(depotid,shopid,commid) {
        $('#turn_depot').val(depotid);
        $('#turn_commid').val(commid);
        $('#turn-stock-modal').addClass("in");
        $('#turn-stock-modal').attr("style","display: block;");
        $('#AddCount').val("");
        selectById(depotid,"#turn");
        $.ajax({
            url: "../operator/shop/shopList",
            type: "POST",
            async: false,
            data: {"page":"{}","req":"{}"},
            success: function (data) {
                var str = "";
                var rows = jQuery.parseJSON(data);
                for(var i=0;i<rows.length;i++){
                    var row = rows[i];
                    if(shopid != row.shopId){
                        str +="<option value=\""+row.shopId+"\">"+row.shopName+"</option>";
                    }
                }
                $("#turn_addshop").html(str);
                $('#turn_addshop').selectpicker('refresh');
            }
        });
    }
    function setThreshold(depotid) {
        $('#set_depot').val(depotid);
        $('#set-range-modal').addClass("in");
        $('#set-range-modal').attr("style","display: block;");
        $('#rangeDanger').val("");
        selectById(depotid,"#set");
    }
    /*function closeDepot(depotid,state) {
        $.ajax({
            url: "../operator/depot/closeDepot",
            type: "POST",
            async: false,
            data: {"depotId":depotid,"state":state},
            success: function (data) {
                if(data >=0){
                    var pd={};
                    var page={};
                    page["pageSize"]=$('#pageSum').val();
                    page["pageCurrent"]=$('#pageNum').val();
                    selectList(page,pd);
                }
            }
        });
    }*/
    function openDepot(depotid,state) {
        var param={};
        param.depotId = depotid;
        param.state = state;
        updateDepot(param,'','/openDepot');
    }
    function selectById(depotid,id) {
        var bid = id+"_barcode";
        var cid = id+"_commName";
        var aid = id+"_amount";
        var sid = id+"_shop";
        var tid = id+"_threshold";
        $.ajax({
            url: "../operator/depot/selectById",
            type: "POST",
            async: false,
            data: {"depotId":depotid},
            success: function (data) {
                var row = jQuery.parseJSON(data);
                $(bid).text(row.barcode);
                $(cid).text(row.commName);
                $(aid).text(row.amount);
                $(sid).text(row.shopName);
                var thr = "不报警";
                if(row.threshold != -1){
                    thr = row.threshold;
                }
                $(tid).text(thr);
            }
        });
    }
    
    function daoRu() {
        <ilas:hasPermission code="0502">
           layer.open({
               type: 2,
               area: ['800px', '580px'],
               fixed: false, //不固定
               maxmin: true,
               content: '${ctx}/operator/import_depot.jsp',
            /*    cancel: function(index){
            	   var param={};
                   var pages = 1;
            	   setpage(param,pages);
                 		  return true; 
                 		} */
           });
          </ilas:hasPermission>
    }
</script>
</body>
</html>