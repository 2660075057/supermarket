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
<title>促销管理</title>
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
        <jsp:param name="type" value="comm_promotion"  />
    </jsp:include>
	<div class="main">
		<div class="bread">
			<a href="#">首页</a> &gt; <a href="#">商品管理</a> &gt; 促销管理
		</div>
		<div class="main-container">
			<div class="page-bar clearfix">
				<div class="pull-left">
					<div class="page-title">
						<span class="line"></span>
						促销管理
					</div>
				</div>
				<div class="pull-right">
					<form class="form-inline">
                        <select id="shop" class="selectpicker">

                        </select>
                        <div class="form-control-hasIcon w250 margin-right-5">
                            <i class="right-icon glyphicon glyphicon-calendar"></i>
                            <input type="text" class="form-control margin-right-10" placeholder="请选择起始时间" value="" id="daterangepicker" />
                        </div>
						<button type="button" id="query" class="btn btn-primary margin-right-5">查询</button>
                        <ilas:hasPermission code="0902">
                            <button id="add_shopDis" type="button" class="btn btn-primary" data-toggle="modal" data-target="#add-stock-modal">
                                <i class="btn-icon btn-icon-add"></i>  新增促销
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
						        	<th>折扣组</th>
									<th>站点</th>
									<th>开始时间</th>
									<th>结束时间</th>
                                    <th>创建时间</th>
									<th width="300">操作</th>
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
<div class="modal fade" id="add-stock-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">添加促销单</h4>
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
                        <label class="col-md-3 control-label">折扣组选择</label>
                        <div class="col-md-6">
                            <select id="disGroup" class="selectpicker">

                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">有效时间段</label>
                        <div class="col-md-6">
                            <div class="form-control-hasIcon w250 margin-right-5">
                                <i class="right-icon glyphicon glyphicon-calendar"></i>
                                <input type="text" class="form-control margin-right-10" placeholder="请选择起始时间" value="" id="openDate" />
                            </div>
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
<div class="modal fade" id="edit-stock-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <input type="hidden" id="edit_id" value="">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <a href="javascript:close('#edit-stock-modal');" class="close" data-dismiss="modal" aria-hidden="true">&times;</a>
                <h4 class="modal-title">修改促销单</h4>
            </div>
            <div class="modal-body">
                <div class="form-horizontal">
                    <div class="form-group">
                        <label class="col-md-3 control-label">站点选择</label>
                        <div class="col-md-6">
                            <select id="shopEdit" class="selectpicker">

                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">折扣组选择</label>
                        <div class="col-md-6">
                            <select id="disGroupEdit" class="selectpicker">

                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">有效时间段</label>
                        <div class="col-md-6">
                            <div class="form-control-hasIcon w250 margin-right-5">
                                <i class="right-icon glyphicon glyphicon-calendar"></i>
                                <input type="text" class="form-control margin-right-10" placeholder="请选择起始时间" value="" id="openDateEdit" />
                            </div>
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-md-3 control-label"> </label>
                        <div class="col-md-8">
                            <button type="submit" id="edit" class="btn btn-primary">提交</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="modal-delete" tabindex="-1" role="dialog" aria-hidden="true">
    <input type="hidden" id="disid" value="">
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
    $(document).ready(function() {
        layui.use('layer', function () {
            var layer = layui.layer;
        });

        $("#daterangepicker").daterangepicker({
                "startDate": "2017-01-01",
                "endDate": "2017-12-01",
                "applyClass": "btn-danger",
                "opens": "left",
                "locale": $.data_picker_locale,
            },
            function (start, end, label) {
                /*console.log(start);
                console.log(end);*/
            }
        );
        $("#openDate").daterangepicker({
                "startDate": "2017-01-01",
                "endDate": "2017-12-01",
                "applyClass": "btn-danger",
                "opens": "left",
                "locale": $.data_picker_locale,
            },
            function (start, end, label) {
                /*console.log(start);
                console.log(end);*/
            }
        );
        $("#openDateEdit").daterangepicker({
                "startDate": "2017-01-01",
                "endDate": "2017-12-01",
                "applyClass": "btn-danger",
                "opens": "left",
                "locale": $.data_picker_locale,
            },
            function (start, end, label) {
                /*console.log(start);
                console.log(end);*/
            }
        );
    });
</script>
<script type="text/javascript">
    $(function() {
        var param={};
        var timearr = $('#daterangepicker').val().split("~");
        var startTime = timearr[0];
        var endTime = timearr[1];
        //param["startTime"] = startTime;
        //param["endTime"] = endTime;
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
                $("#shopEdit").html(strc);
                $('#shopEdit').selectpicker('refresh');
            }
        });
        $.ajax({
            url: "../operator/discountGroup/queryDiscountGroup",
            type: "POST",
            async: false,
            data: {"page":"{}","req":"{}"},
            success: function (data) {
                var str = "";
                var rows = jQuery.parseJSON(data).data;
                for(var i=0;i<rows.length;i++){
                    var row = rows[i];
                    str +="<option value=\""+row.groupId+"\">"+row.title+"</option>";
                }
                $("#disGroup").html(str);
                $('#disGroup').selectpicker('refresh');
                $("#disGroupEdit").html(str);
                $('#disGroupEdit').selectpicker('refresh');
            }
        });

    });
    $("#query").click(function(){
        var shopid = $('#shop').val();
        var param={};
        if(shopid != -1){
            param["shopId"]=shopid;
        }
        var timearr = $('#daterangepicker').val().split("~");
        var startTime = timearr[0];
        var endTime = timearr[1];
        param["startTime"] = startTime;
        param["endTime"] = endTime;
        setpage(param);
    });
<ilas:hasPermission code="0904">
    $("#btn-delete").click(function(){
        var id = $('#disid').val();
        $.ajax({
            url: "../operator/shopdiscountgroup/deleteByPrimaryKey",
            type: "POST",
            async: false,
            data: {"id":id},
            success: function (data) {
                if(data > 0){
                    $.success_layer("删除成功！")
                    parent.location.href="${pageContext.request.contextPath}/operator/commoditypromotion.jsp";
                }else{
                    layer.msg('删除分类失败，该分类存在商品信息！');
                    close('#modal-delete');
                }

            }
        });
    });
</ilas:hasPermission>
<ilas:hasPermission code="0902">
    $("#add").click(function(){
        var param = {};
        var groupid = $('#disGroup').val();
        if(groupid == null || groupid == -1 ){
            layer.tips('请选择折扣组！', '#disGroup', {
                tips: [3,'red']
            });
            return;
        }
        var shopid = $('#shopc').val();
        if(shopid == null || shopid == -1 ){
            layer.tips('请选择站点！', '#shopc', {
                tips: [3,'red']
            });
            return;
        }
        var strtime = $('#openDate').val();
        if(strtime == null || strtime.length <=0){
            layer.tips('请选择日期！', '#openDate', {
                tips: [3,'red']
            });
            return;
        }
        var timearr = strtime.split("~");
        var startTime = timearr[0];
        var endTime = timearr[1];
        startTime = startTime.replace(/-/g,"/");
        var sdate = new Date(startTime);
        endTime = endTime.replace(/-/g,"/");
        var edate = new Date(endTime);
        param["startTime"] = sdate;
        param["endTime"] = edate;
        param["groupId"] = groupid;
        param["shopId"] = shopid;
        $.ajax({
            url: "../operator/shopdiscountgroup/insertSelective",
            type: "POST",
            async: false,
            data: param,
            success: function (data) {
                if(data > 0){
                    $.success_layer("添加成功！");
                    parent.location.href="${pageContext.request.contextPath}/operator/commoditypromotion.jsp";
                }else{
                    layer.msg('添加失败，同时间段内已有折扣单！');
                    //close('#modal-delete');
                }

            }
        });
    });
</ilas:hasPermission>
<ilas:hasPermission code="0903">
    $("#edit").click(function(){
        var id = $('#edit_id').val();
        if(id == null || id.length <=0){
            layer.msg('修改失败，未找到记录！');
            return;
        }
        var param = {};
        param["id"] = id;
        var groupid = $('#disGroupEdit').val();
        if(groupid == null || groupid == -1 ){
            layer.tips('请选择折扣组！', '#disGroup', {
                tips: [3,'red']
            });
            return;
        }
        var shopid = $('#shopEdit').val();
        if(shopid == null || shopid == -1 ){
            layer.tips('请选择站点！', '#shopc', {
                tips: [3,'red']
            });
            return;
        }
        var strtime = $('#openDateEdit').val();
        if(strtime == null || strtime.length <=0){
            layer.tips('请选择日期！', '#openDate', {
                tips: [3,'red']
            });
            return;
        }
        var timearr = strtime.split("~");
        var startTime = timearr[0];
        var endTime = timearr[1];
        startTime = startTime.replace(/-/g,"/");
        var sdate = new Date(startTime);
        endTime = endTime.replace(/-/g,"/");
        var edate = new Date(endTime);
        param["startTime"] = sdate;
        param["endTime"] = edate;
        param["groupId"] = groupid;
        param["shopId"] = shopid;
        $.ajax({
            url: "../operator/shopdiscountgroup/updateByPrimaryKeySelective",
            type: "POST",
            async: false,
            data: param,
            success: function (data) {
                if(data > 0){
                    $.success_layer("修改成功！")
                    parent.location.href="${pageContext.request.contextPath}/operator/commoditypromotion.jsp";
                }else{
                    layer.msg('修改失败，同时间段内已有折扣单！');
                    //close('#modal-delete');
                }

            }
        });
    });
</ilas:hasPermission>
    function selectList(page,param, currPage, pageSize) {
    	var startIndex = 0;
		if(currPage>0){
			startIndex = (currPage-1)*pageSize;
		}
        $.ajax({
            url: "${pageContext.request.contextPath}/operator/shopdiscountgroup/selectParam",
            type: "POST",
            async: false,
            data: {"page":JSON.stringify(page),"req":JSON.stringify(param)},
            success: function (data) {
                var str = "";
                if(data != null && data != "[]"){
                    var rows = jQuery.parseJSON(data);
                    for(var i=0;i<rows.length;i++){
                    	var number = parseInt(startIndex)+parseInt(i+1);
                        var row = rows[i];
                        var shop = row.shopEntity;
                        var dis = row.discountGroupEntity;
                        str +="<tr>";
                        str +="<td>"+number+"</td><td>"+dis.title+"</td><td>"+shop.shopName+"</td><td>"+getLocalTime(row.startTime).split(" ")[0]+"</td>";
                        str +="<td>"+getLocalTime(row.endTime).split(" ")[0]+"</td><td>"+getLocalTime(row.createTime).split(" ")[0]+"</td>";
                        str +="<td><button onclick=\"javascript:showDetail('"+dis.groupId+"');\" class=\"layui-btn\">详情</button>";
                        <ilas:hasPermission code="0903">
                          str +="<button onclick=\"javascript:updateShopDis('"+row.id+"');\" class=\"layui-btn\">修改</button>";
                        </ilas:hasPermission>
                        <ilas:hasPermission code="0904">
                          str +="<button onclick=\"javascript:deteleShopDis('"+row.id+"');\" class=\"layui-btn\">删除</button>";
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
        var shopid = $('#shop').val();
        var param={};
        if(shopid != -1){
            param["shopId"]=shopid;
        }
        var timearr = $('#daterangepicker').val().split("~");
        var startTime = timearr[0];
        var endTime = timearr[1];
        startTime = startTime.replace(/-/g,"/");
        var sdate = new Date(startTime);
        endTime = endTime.replace(/-/g,"/");
        var edate = new Date(endTime);
        param["startTime"] = sdate;
        param["endTime"] = edate;
        var count=0;
        $.ajax({
            url: "${pageContext.request.contextPath}/operator/shopdiscountgroup/countShopDisGroup",
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
    function getLocalTime(nS) {
        return new Date(parseInt(nS)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
    }
    function deteleShopDis(id) {
        $('#disid').val(id);
        $('#modal-delete').addClass("in");
        $('#modal-delete').attr("style","display: block;");

    }
    function updateShopDis(id) {
        $('#edit_id').val(id);
        $('#edit-stock-modal').addClass("in");
        $('#edit-stock-modal').attr("style","display: block;");
        $.ajax({
            url: "${pageContext.request.contextPath}/operator/shopdiscountgroup/selectByPrimaryKey",
            type: "POST",
            async: false,
            data: {"id":id},
            success: function (data) {
                if(data != null){
                    var row = jQuery.parseJSON(data);
                    $("#shopEdit").val(row.shopId);
                    $('#shopEdit').selectpicker('refresh');
                    $("#disGroupEdit").val(row.groupId);
                    $('#disGroupEdit').selectpicker('refresh');
                    var startDate = getLocalTime(row.startTime).split(" ")[0];
                    var endDate = getLocalTime(row.endTime).split(" ")[0];
                    $("#openDateEdit").daterangepicker({
                            "startDate": startDate.replace("/","-"),
                            "endDate": endDate.replace("/","-"),
                            "applyClass": "btn-danger",
                            "opens": "left",
                            "locale": $.data_picker_locale,
                        });
                }

            }
        });
    }
    function close(id) {
        $(id).removeClass("in");
        $(id).attr("style","");
    }
    function showDetail(id) {
        layer.open({
            type: 2,
            area: ['1000px', '620px'],
            fixed: false, //不固定
            maxmin: true,
            content: '${pageContext.request.contextPath}/operator/discountGroup/detail?groupId='+id
        });
    }
</script>
</body>
</html>