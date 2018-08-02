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
<title>商品管理</title>
    <%@ include file="global/mylink.jsp" %>
    <style>
        .btn:hover, .btn:active, .btn:link {
            background-color: #009688;
        }

        .commImg {
            width: 200px;
        }
    </style>
</head>
<body>
<%@ include file="global/header.jsp" %>
<div class="main-content">
    <jsp:include page="menu.jsp" flush="true" >
        <jsp:param name="type" value="comm_list"  />
    </jsp:include>
	<div class="main">
		<div class="bread">
			<a href="#">首页</a> &gt; <a href="#">商品管理</a> &gt; 商品列表
		</div>
		<div class="main-container">
			<div class="page-bar clearfix">
				<div class="pull-left">
					<div class="page-title">
						<span class="line"></span>
						商品列表
					</div>
				</div>
				<div class="pull-right">
					<div class="form-inline">
						<select id="Typename" class="selectpicker margin-right-5">
							<option value="1">商品条码</option>
							<option value="2">商品名称</option>
						</select>
					<select id="ziTypename" class="selectpicker margin-right-5">
							<option value="-1">请选择</option>
						</select>
						<select id="queryType" class="selectpicker margin-right-5">
							<option value="1">商品条码</option>
							<option value="2">商品名称</option>
						</select>
						<input type="text" id="queryVal" class="form-control bar-form-control margin-right-5" placeholder="请输入检索值" />
						<button type="button" id="query" class="btn btn-primary margin-right-5">查询</button>
                        <ilas:hasPermission code="0502">
                            <button onclick="javascript:addComm();" class="btn btn-primary"><i class="btn-icon btn-icon-add"></i>  新增商品</button>
                        </ilas:hasPermission>
                        <ilas:hasPermission code="0502">
                            <button onclick="javascript:daoRu();" class="btn btn-primary">导入</button>
                        </ilas:hasPermission>
					</div>
				</div>
			</div>
			<div class="global-table margin-top-gap">
				<div id="main-table">
					<div class="table-responsive">
					    <table class="table table-bordered table-striped table-global">
						    <thead>
						        <tr>
                                    <th width="70">序号</th>
                                    <th width="300">商品条码</th>
                                    <th>商品名称</th>
                                    <th>类型</th>
                                    <th width="100">进货价</th>
                                    <th width="100">售价</th>
                                    <th width="280">操作</th>
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

<!-- 弹出添加商品框框-->
<div class="modal fade" id="goods-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <a href="javascript:close('#goods-modal');" class="close" data-dismiss="modal" aria-hidden="true">&times;</a>
                <h4 class="modal-title">商品详情</h4>
            </div>
            <div class="modal-body" style="max-height: 350px;overflow: auto">
            	<div class="form-horizontal" id="form-user">
            		<div class="form-group">
					    <label class="col-md-3 control-label">商品条码</label>
					    <div class="col-md-8">
					    	<p class="form-control-static" id="barcode"></p>
					    </div>
					</div>
					<div class="form-group">
					    <label class="col-md-3 control-label">商品名称</label>
					    <div class="col-md-8">
					    	<p class="form-control-static" id="commName"></p>
					    </div>
					</div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">商品类型</label>
                        <div class="col-md-8">
                            <p class="form-control-static" id="commType"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-md-3 control-label">进货价</label>
                        <div class="col-md-8">
                            <p class="form-control-static" id="costPrice"></p>
                        </div>
                    </div>
					<div class="form-group">
					    <label class="col-md-3 control-label">售价</label>
					    <div class="col-md-8">
					    	<p class="form-control-static" id="commPrice"></p>
					    </div>
					</div>
                    <div id="attr">

                    </div>
                    <div id="imgList">
                    </div>
            	</div>
            </div>
        </div>
    </div>
</div>
<!-- 弹出删除框框-->
<div class="modal fade" id="modal-delete" tabindex="-1" role="dialog" aria-hidden="true">
    <input type="hidden" id="commid" value="">
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
		$.success_layer("删除成功！");
	})
})
</script>
<script type="text/javascript">
    $(function() {
        var param={};
        var pages = 1;
        selecttype();
        setpage(param,pages);
        
        $("#Typename").change(function(){
        	var typeid=$("#Typename").val();
        	$("#ziTypename").html("");
        	if (typeid!="-1") {
        		$.ajax({
            		url:"${ctx}/operator/commodity/ziselectType",
            		type:"POST",
            		data:{"typeid":typeid},
            		success:function(data){
            			if (data!="") {
            				var rows = jQuery.parseJSON(data);
                			$("#ziTypename").append("<option value='-1'>请选择</option>");
                			for(var i=0;i<rows.length;i++){
                				var comm = rows[i];
                				var $option="<option value="+comm.typeId+">"+comm.typeName+"</option>";
                				$("#ziTypename").append($option);
                			}
                			$("#ziTypename").selectpicker('refresh');
                			$("#ziTypename").selectpicker('render');
						}
            			
            			
            		}
            	})
			}
        	
        })
    });
    $("#query").click(function(){
        var queryType = $('#queryType').val();
        var queryVal = $('#queryVal').val();
        var typeId = $('#Typename').val();
        var masterId = $('#ziTypename').val();
        var param={};
        if (typeId!="-1") {
        	if (masterId!="-1") {
            	param.typeId = masterId;
    		}else{
    			param.typeId = typeId;
    		}
		}
        var pages = 1;
        if(queryVal !=null && queryVal.length >0){
            if(queryType == 1){
                param["barcode"]=queryVal.replace(/(^\s*)|(\s*$)/g, "");
            }else if(queryType == 2){
                param["commName"]=queryVal.replace(/(^\s*)|(\s*$)/g, "");
            }
        }
        console.log(param)
        setpage(param,pages);
    });
    $("#btn-delete").click(function(){
        var commid = $('#commid').val();
        $.ajax({
            url: "${ctx}/operator/commodity/deleteCommodity",
            type: "POST",
            async: false,
            data: {"commId":commid},
            success: function (data) {
                parent.location.href="${ctx}/operator/commoditylist.jsp";
            }
        });
    });
	
    function selecttype(){
    	$.ajax({
    		url:"${ctx}/operator/commodity/selectType",
    		type:"POST",
    		success:function(data){
    			$("#Typename").html("");
    			var rows = jQuery.parseJSON(data);
    			$("#Typename").append("<option value='-1'>请选择</option>");
    			for(var i=0;i<rows.length;i++){
    				var comm = rows[i];
    				var $option="<option value="+comm.typeId+">"+comm.typeName+"</option>";
    				$("#Typename").append($option);
    			}
    			$("#Typename").selectpicker('refresh');
    			$("#Typename").selectpicker('render');
    		}
    	})
    }
    
    function selectList(page,param) {
        $.ajax({
            url: "${ctx}/operator/commodity/commodityList",
            type: "POST",
            async: false,
            data: {"page":JSON.stringify(page),"req":JSON.stringify(param)},
            success: function (data) {
                var str = "";
                if(data != null && data.length >0){
                    var rows = jQuery.parseJSON(data);
                    for(var i=0;i<rows.length;i++){
                        var comm = rows[i];
                        var commtype = comm.commodityType;
                        var costPrice = parseFloat(comm.costPrice/100)+" 元";
                        var commPrice = parseFloat(comm.commPrice/100)+" 元";
                        str +="<tr>";
                        str +="<td>"+(i+1+(page.pageCurrent-1)*page.pageSize)+"</td>";
                        str +="<td>"+comm.barcode+"</td><td>"+comm.commName+"</td><td>"+commtype.typeName+"</td>";
                        str +="<td>"+costPrice+"</td><td>"+commPrice+"</td>";
                        str +="<td>";
                        str +="<button onclick =\"javascript:commDetailed("+comm.commId+");\" class=\"layui-btn\">详细</button>";
                        <ilas:hasPermission code="0503">
                           str +="<button onclick =\"javascript:updateComm("+comm.commId+",'"+page.pageCurrent+"');\" class=\"layui-btn\">修改</button>";
                        </ilas:hasPermission>
                        <ilas:hasPermission code="0504">
                           str +="<button onclick =\"javascript:deteleComm("+comm.commId+");\" class=\"layui-btn\">删除</button>";
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
        var typeId = $('#Typename').val();
        var masterId = $('#ziTypename').val();
        var param={};
        if (typeId!="-1") {
        	if (masterId!="-1") {
            	param.typeId = masterId;
    		}else{
    			param.typeId = typeId;
    		}
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
            url: "${ctx}/operator/commodity/countCommodity",
            type: "POST",
            async: false,
            data: param,
            success: function (data) {
                count = parseInt(data);
            }
        }); 
        return count;
    }
    function setpage(param,pages) {
        layui.use(['laypage', 'layer'], function(){
            var laypage = layui.laypage
                ,layer = layui.layer;
            laypage.render({
                elem: 'layuipage'
                ,count: getcount()
                ,curr:pages
                ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
                ,limit : 10
                ,limits: [10,20,30,40]
                ,jump: function(obj){
                    var page={};
                    page["pageSize"]=obj.limit;
                    page["pageCurrent"]=obj.curr;
                    selectList(page,param);
                }
            });
        });
    }
    function daoRu() {
        <ilas:hasPermission code="0502">
           layer.open({
               type: 2,
               area: ['800px', '400px'],
               fixed: false, //不固定
               maxmin: true,
               content: '${ctx}/operator/import_comm.jsp',
               cancel: function(index){
            	   var param={};
                   var pages = 1;
            	   setpage(param,pages);
                 		  return true; 
                 		}
           });
           </ilas:hasPermission>
       }
    function addComm() {
     <ilas:hasPermission code="0502">
        layer.open({
            type: 2,
            area: ['1000px', '500px'],
            fixed: false, //不固定
            maxmin: true,
            content: 'edit_commlist.jsp'
        });
        </ilas:hasPermission>
    }
    function updateComm(commid,currPage) {
      <ilas:hasPermission code="0503">
        layer.open({
            type: 2,
            area: ['1000px', '500px'],
            fixed: false, //不固定
            maxmin: true,
            content: 'edit_commlist.jsp?commid='+commid+'&currPage='+currPage
        });
        </ilas:hasPermission>
    }
    function deteleComm(commid) {
       <ilas:hasPermission code="0504">
        $('#commid').val(commid);
        $('#modal-delete').addClass("in");
        $('#modal-delete').attr("style","display: block;");
        </ilas:hasPermission>

    }
    function close(id) {
        $(id).removeClass("in");
        $(id).attr("style","");
    }
    function commDetailed(commid) {
        $('#goods-modal').addClass("in");
        $('#goods-modal').attr("style","display: block;");
        $('#imgList').html('');
        $.ajax({
            url: "${ctx}/operator/commodity/selectByPrimaryKey",
            type: "POST",
            data: {"commId":commid},
            timeout: 8000
        }).done(function(data){
            if(data !=null){
                var comm = jQuery.parseJSON(data);
                $("#barcode").text(comm.barcode);
                $("#commName").text(comm.commName);
                var costPrice = parseFloat(comm.costPrice/100)+" 元";
                var commPrice = parseFloat(comm.commPrice/100)+" 元";
                $("#costPrice").text(costPrice);
                $("#commPrice").text(commPrice);
                $("#commType").text(comm.commodityType.typeName);
                setattrs(comm.commodityType.typeId);
                var picHtml = '';
                for(var index in comm.picture){
                    picHtml += "<img class='commImg' src='${ctx}"+comm.picture[index].imgUrl+"' />";
                }
                $('#imgList').html(picHtml);
                $.ajax({
                    url: "${ctx}/operator/commodityattr/selectByCommId",
                    type: "POST",
                    data: {"commId":comm.commId},
                    timeout: 8000
                }).done(function(data){
                    if(data != null){
                        var rows = jQuery.parseJSON(data);
                        for(var i=0;i<rows.length;i++){
                            var row = rows[i];
                            var id = "#"+row.attrId;
                            $(id).text(row.value);
                        }
                    }
                });
            }
        })
    }
    function setattrs(typeid) {
        var param={};
        param["typeId"]=typeid;
        $.ajax({
            url: "${ctx}/operator/commoditytype/commodityTypeList",
            type: "POST",
            async: false,
            data: {"page":"{}","req":JSON.stringify(param)},
            success: function (data) {
                if(data != null){
                    var row = jQuery.parseJSON(data)[0];
                    var attrs = row.attrs;
                    var astr = "";
                    for(var i=0;i<attrs.length;i++){
                        var attr = attrs[i];
                        astr +='<div class="form-group">';
                        astr +='<label for="origin" class="col-md-3 control-label">'+attr.attrName+'</label>';
                        astr +='<div class="col-md-8">';
                        astr +='<p type="text" id="'+attr.attrId+'" class="form-control-static" name="attrVal"/>';
                        astr +='</div></div>';
                    }
                    $('#attr').html(astr);
                }
            }
        });
    }
</script>
</body>
</html>