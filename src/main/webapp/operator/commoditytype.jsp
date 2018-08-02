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
<title>分类管理</title>
    <%@ include file="global/mylink.jsp" %>
    <style>
        .btn:hover, .btn:active, .btn:link {
            background-color: #009688;
        }
        table,td,th {  border: 1px solid #E8E8E8; padding:5px; border-collapse: collapse; font-size:14px; }
    </style>
</head>
<link href="${pageContext.request.contextPath}/operator/js/treeTable/vsStyle/jquery.treeTable.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/operator/js/treeTable/jquery.treeTable.js" type="text/javascript"></script>
<body>
<%@ include file="global/header.jsp" %>
<div class="main-content">
    <jsp:include page="menu.jsp" flush="true" >
        <jsp:param name="type" value="comm_type"  />
    </jsp:include>
	<div class="main">
		<div class="bread">
			<a href="#">首页</a> &gt; <a href="#">商品管理</a> &gt; 分类设置
		</div>
		<div class="main-container">
			<div class="page-bar clearfix">
				<div class="pull-left">
					<div class="page-title">
						<span class="line"></span>
						分类设置
					</div>
				</div>
				<div class="pull-right">
					<div class="form-inline">
						<input type="text" id="queryVal" class="form-control bar-form-control margin-right-5" placeholder="请输入关键字" />
						<button type="button" id="query" class="btn btn-primary margin-right-5" data-toggle="modal" data-target="#modal">查询</button>
                        <ilas:hasPermission code="0602">
						  <button onclick="javascript:addCommType();" class="btn btn-primary"><i class="btn-icon btn-icon-add"></i>  新增分类</button>
                        </ilas:hasPermission>
					</div>
				</div>
			</div>
			<div class="global-table margin-top-gap">
				<div id="main-table">
					<div class="table-responsive">
					    <table id="treeTable1" style="width:100%">
						     <thead style="background-color:#FCAF50; color:#FFFFFF;">
						        <tr>
						        	<th style="width:400; height:40px">类型名称</th>
									<th>类型属性</th>
									<th width="160">操作</th>
						        </tr>
						    </thead>
						     <tbody id="datalist">


						    </tbody> 
						</table>
					</div>
				</div>
			</div>
            <!-- <div class="pager-md clearfix margin-top-gap">
                <div class="pull-right">
                    <div id="layuipage"></div>
                </div>
            </div> -->
		</div>
	</div>
</div>
<!-- 弹出添加分类框框-->
<div class="modal fade" id="directory-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <input type="hidden" id="edit" value="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <a href="javascript:close('#directory-modal');" class="close" data-dismiss="modal" aria-hidden="true">&times;</a>
                <h4 id="title" class="modal-title">添加分类</h4>
            </div>
            <div class="modal-body">
            	<div class="form-horizontal" id="form-user">
                    <div class="form-group" id="zid">
                        <label class="col-md-2 control-label">主类型</label>
                        <div class="col-md-8">
                            <select id="commType" class="selectpicker margin-right-5" data-width="200">

                            </select>
                        </div>
                    </div>
            		<div class="form-group">
					    <label for="directoryName" class="col-md-2 control-label">分类名称</label>
					    <div class="col-md-8">
					    	<input type="text" class="form-control" name="directoryName" id="directoryName" />
					    </div>
					</div>
                    <div class="form-group">
                        <label class="col-md-2 control-label">分类属性</label>
                        <div class="col-md-8">
                            <div id="set-role-list" class="btns-list">
                            </div>
                            <div id="attrlist" class="checkbox-list-scroll">

                            </div>
                        </div>
                    </div>
					<div class="form-group">
					    <label class="col-md-2 control-label"> </label>
					    <div class="col-md-8">
							<button type="submit" id="add_commType" class="btn btn-primary">提交</button>
						</div>
					</div>
            	</div>
            </div>
        </div>
    </div>
</div>

<!-- 弹出删除框框-->
<div class="modal fade" id="modal-delete" tabindex="-1" role="dialog" aria-hidden="true">
    <input type="hidden" id="typeid" value="">
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
<script type="text/javascript">
	var MaxList = [];
    $(function() {
        var param={};
        selectList(param);
        getAttrAll();
        $('#zid').show();
        var str = "<option value='-1'>主类型选择</option>";
        setType(str);
    });
    $("#query").click(function(){
        var queryVal = $('#queryVal').val();
        var param={};
        if(queryVal !=null && queryVal.length >0){
            param["typeName"]=queryVal.replace(/(^\s*)|(\s*$)/g, "");
        }
        var option = {
                theme: 'vsStyle',
                expandLevel: 2,
            };
        $('#treeTable1').treeTable(option);
		selectLists(param);
    });
    function selectList(param) {
    	MaxList = [];
        $.ajax({
            url: "../operator/commoditytype/commodityTree",
            type: "POST",
            async: false,
            data: {"req":JSON.stringify(param)},
            success: function (data) {
            	 var str = "";
            	 var row = jQuery.parseJSON(data);
            	 var con = row.rows;//获取json中的list列表
            	 MaxList = con;
            	 for (var i = 0, l = con.length; i < l; i++) {
                         var cons = con[i];
                         var attrs = cons.attrs;
                         if(con[i].masterId == 0){  //判断是否是一级节点
	                         str += "<tr id='" + cons.typeId + "'>";
	                         str +="<td><span controller='true'>"+cons.typeName+"</span></td>";
	                         var ats = "";
	                         for(var j=0;j<attrs.length;j++){
	                             if(j+1 == attrs.length){
	                                 ats += attrs[j].attrName;
	                             }else{
	                                 ats += attrs[j].attrName+",";
	                             }
	                         }
	                         str +="<td>"+ats+"</td>";
	                         str +="<td>";
	                         <ilas:hasPermission code="0603">
	                           str+="<button onclick =\"javascript:updateCommType("+cons.typeId+");\" class=\"layui-btn\">修改</button>";
	                         </ilas:hasPermission>
	                         <ilas:hasPermission code="0604">
	                           str +="<button onclick =\"javascript:deteleCommType("+cons.typeId+");\" class=\"layui-btn\">删除</button>";
	                         </ilas:hasPermission>
	                         str +="</td>";
	                         str +="</tr>";
	                         
                    	 }else{
	                         str += "<tr id='" + cons.typeId + "' pid='" + cons.masterId + "'>";
	                         str +="<td>"+"→"+cons.typeName+"</td>";
	                         var ats = "";
	                         for(var j=0;j<attrs.length;j++){
	                             if(j+1 == attrs.length){
	                                 ats += attrs[j].attrName;
	                             }else{
	                                 ats += attrs[j].attrName+",";
	                             }
	                         }
	                         str +="<td>"+ats+"</td>";
	                         str +="<td>";
	                         <ilas:hasPermission code="0603">
	                           str+="<button onclick =\"javascript:updateCommType("+cons.typeId+");\" class=\"layui-btn\">修改</button>";
	                         </ilas:hasPermission>
	                         <ilas:hasPermission code="0604">
	                           str +="<button onclick =\"javascript:deteleCommType("+cons.typeId+");\" class=\"layui-btn\">删除</button>";
	                         </ilas:hasPermission>
	                         str +="</td>";
	                         str +="</tr>";
	                         
                    	 }
                     }
            	 $('#datalist').html(str);
                 //以下为初始化表格样式
                 var option = {
                     theme: 'vsStyle',
                     expandLevel: 2,
                 };
                 $('#treeTable1').treeTable(option);
                 
               
            }
        });
    } 
    
    
    function selectLists(param) {
        $.ajax({
            url: "../operator/commoditytype/commodityTree",
            type: "POST",
            async: false,
            data: {"req":JSON.stringify(param)},
            success: function (data) {
            	 var str = "";
            	 var row = jQuery.parseJSON(data);
            	 var con = row.rows;//获取json中的list列表
            	 var parentList= [];
            	 var childrenList = [];
            	 var parentsList = [];
            	 
            	 for(var i = 0; i<con.length; i++){         //循环添加所有父节点和子节点
	            	 if(con[i].masterId == 0){
	                	 for (var j = 0; j < MaxList.length; j++) {
	                		 if(con[i].typeId == MaxList[j].masterId){
	                			 childrenList.push(MaxList[j]);
            	 		 	}
	                	 }
	                	 parentList.push(con[i]);
	                 }else{
	                	 for (var k = 0; k < MaxList.length; k++) {
	                		 if(con[i].masterId == MaxList[k].typeId){
	                			 parentList.push(MaxList[k]);
	                		 }
	                	 }
	                	 childrenList.push(con[i]);
	                 }
            	 }
            	 
            	 for(var  i  = 0 ; i<  parentList.length-1 ; i ++ )  {       //parentList去重
           	     	 for( var  j  =  parentList.length-1 ; j > i; j -- )  {       
           	           if (parentList[j].typeId == parentList[i].typeId)  {       
           	        	parentList.splice(j,1);       
           	            }        
           	        }        
           	      } 
            	 
            	 for(var  i  = 0 ; i<  childrenList.length-1 ; i ++ )  {       //childrenList去重
           	     	 for( var  j  =  childrenList.length-1 ; j > i; j -- )  {       
           	           if (childrenList[j].typeId == childrenList[i].typeId)  {       
           	        	childrenList.splice(j,1);       
           	            }        
           	        }        
           	      } 
           	   	parentsList  = parentList;
            	 for(var i=0; i<parentsList.length; i++){                      //按树形顺序排序
                 	for(var j=0; j<childrenList.length; j++){
                 		if(parentsList[i].typeId == childrenList[j].masterId){
                 			parentList.splice(i+1,0,childrenList[j]);
                 		}
                 	}	
            	 }
            	 for (var i = 0, l = parentList.length; i < l; i++) {
                         var cons = parentList[i];
                         var attrs = cons.attrs;
                         if(cons.masterId == 0){  //判断是否是父节点
	                         str += "<tr id='" + cons.typeId + "'>";
	                         str +="<td><span controller='true'>"+cons.typeName+"</span></td>";
	                         var ats = "";
	                         for(var j=0;j<attrs.length;j++){
	                             if(j+1 == attrs.length){
	                                 ats += attrs[j].attrName;
	                             }else{
	                                 ats += attrs[j].attrName+",";
	                             }
	                         }
	                         str +="<td>"+ats+"</td>";
	                         str +="<td>";
	                         <ilas:hasPermission code="0603">
	                           str+="<button onclick =\"javascript:updateCommType("+cons.typeId+");\" class=\"layui-btn\">修改</button>";
	                         </ilas:hasPermission>
	                         <ilas:hasPermission code="0604">
	                           str +="<button onclick =\"javascript:deteleCommType("+cons.typeId+");\" class=\"layui-btn\">删除</button>";
	                         </ilas:hasPermission>
	                         str +="</td>";
	                         str +="</tr>";
	                         
                    	 }else{
	                         str += "<tr id='" + cons.typeId + "' pid='" + cons.masterId + "'>";
	                         str +="<td>"+"→"+cons.typeName+"</td>";
	                         var ats = "";
	                         for(var j=0;j<attrs.length;j++){
	                             if(j+1 == attrs.length){
	                                 ats += attrs[j].attrName;
	                             }else{
	                                 ats += attrs[j].attrName+",";
	                             }
	                         }
	                         str +="<td>"+ats+"</td>";
	                         str +="<td>";
	                         <ilas:hasPermission code="0603">
	                           str+="<button onclick =\"javascript:updateCommType("+cons.typeId+");\" class=\"layui-btn\">修改</button>";
	                         </ilas:hasPermission>
	                         <ilas:hasPermission code="0604">
	                           str +="<button onclick =\"javascript:deteleCommType("+cons.typeId+");\" class=\"layui-btn\">删除</button>";
	                         </ilas:hasPermission>
	                         str +="</td>";
	                         str +="</tr>";
	                         
                    	 }
                     }
            	 $('#datalist').html(str);
                 //以下为初始化表格样式
                 var option = {
                     theme: 'vsStyle',
                     expandLevel: 2,
                 };
                 $('#treeTable1').treeTable(option);
                 
               
            }
        });
    } 
    
    function getcount() {
        var queryVal = $('#queryVal').val();
        var param={};
        if(queryVal !=null && queryVal.length >0){
            param["typeName"]=queryVal.replace(/(^\s*)|(\s*$)/g, "");
        }
        var count=0;
        $.ajax({
            url: "../operator/commoditytype/countByParam",
            type: "POST",
            async: false,
            data: param,
            success: function (data) {
                count = parseInt(data);
            }
        });
        return count;
    }
 /*    function setpage(param) {
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
    } */
    $("#add_commType").click(function(){
        $('#zid').show();
        var tem = $('#edit').val();


        var attrids="";
        $('input[name="attrType"]:checked').each(function(){
            attrids += $(this).val()+",";
        });
        var typeName = $('#directoryName').val();
        if(typeName ==null || typeName.length <= 0){
            layer.tips('请填写分类名称！', '#directoryName', {
                tips: [3,'red']
            });
            $("html,body").animate({scrollTop:$("#directoryName").offset().top},100);
            return;
        }
        if(attrids ==null || attrids.length <= 0){
            layer.tips('请选择分类属性！', '#attrlist', {
                tips: [3,'red']
            });
            $("html,body").animate({scrollTop:$("#attrlist").offset().top},100);
            return;
        }
        var param={};
        param["typeName"]=typeName;
        var zid = $("#commType").val();
        if(zid != -1){
            param["masterId"]=zid;
        }else{
        	param["masterId"]=0;
        }
        if(tem >0){
           <ilas:hasPermission code="0603">
            param["typeId"]=$('#edit').val();;
            $.ajax({
                url: "../operator/commoditytype/updateCommodityType",
                type: "POST",
                async: false,
                data: {"req":JSON.stringify(param),"attrIds":attrids.substring(0,attrids.length-1)},
                success: function (data) {
                    if(data){
                        parent.location.href="${pageContext.request.contextPath}/operator/commoditytype.jsp";
                    }
                }
            });
            </ilas:hasPermission>
        }else{
           <ilas:hasPermission code="0602">
            $.ajax({
                url: "../operator/commoditytype/addCommodityType",
                type: "POST",
                async: false,
                data: {"req":JSON.stringify(param),"attrIds":attrids.substring(0,attrids.length-1)},
                success: function (data) {
                    if(data){
                        parent.location.href="${pageContext.request.contextPath}/operator/commoditytype.jsp";
                    }
                }
            });
            </ilas:hasPermission>
        }

    });
<ilas:hasPermission code="0604">
    $("#btn-delete").click(function(){
        var typeid = $('#typeid').val();
        $.ajax({
            url: "../operator/commoditytype/deleteCommodityType",
            type: "POST",
            async: false,
            data: {"typeId":typeid},
            success: function (data) {
                if(data == 0){
                    $.success_layer("删除成功！")
                    parent.location.href="${pageContext.request.contextPath}/operator/commoditytype.jsp";
                }else{
                    layer.msg('删除分类失败，该分类存在商品信息！');
                    close('#modal-delete');
                }

            }
        });
    });
    </ilas:hasPermission>
    function deteleCommType(typeid) {
        $('#typeid').val(typeid);
        $('#modal-delete').addClass("in");
        $('#modal-delete').attr("style","display: block;");

    }
    function close(id) {
        $(id).removeClass("in");
        $(id).attr("style","");
    }
    function updateCommType(typeid) {
        $('#zid').show();
        var str = "<option value='-1'>主类型选择</option>";
        setType(str);
        $('#commType').val(-1);
        $('#commType').selectpicker('refresh');
        $('#edit').val(typeid);
        var tem = $('#edit').val();
        $('#title').html("修改分类");
        $('#directory-modal').addClass("in");
        $('#directory-modal').attr("style","display: block;");
        var param={};
        param["typeId"]=typeid;
        $.ajax({
            url: "../operator/commoditytype/commodityTypeList",
            type: "POST",
            async: false,
            data: {"page":"{}","req":JSON.stringify(param)},
            success: function (data) {
                if(data != null){
                    var row = jQuery.parseJSON(data)[0];
                    if(row.masterId > 0){
                        $('#commType').val(row.masterId);
                        $('#commType').selectpicker('refresh');
                    }else{
                        $('#zid').hide();
                    }
                    $('#directoryName').val(row.typeName);
                    var attrs = row.attrs;
                    $("input[name='attrType']").each(function (){
                        $(this).parent().removeClass("checked");
                        $(this).removeAttr("checked");
                    });
                    for(var i=0;i<attrs.length;i++){
                        $("input[name='attrType']").each(function (){
                            if($(this).val()==attrs[i].attrId){
                                $(this).parent().addClass("checked");
                                $(this).attr("checked","checked");
                            }
                        });
                    }
                }
            }
        });
    }
    function getAttrAll() {
        $.ajax({
            url: "../operator/attribute/selectAll",
            type: "POST",
            async: false,
            data: {},
            success: function (data) {
                var rows = jQuery.parseJSON(data);
                var str = "<ul>";
                for(var i=0;i<rows.length;i++){
                    var row = rows[i];
                    str +="<li><label>";
                    str +="<input data-id=\""+row.attrId+"\" value=\""+row.attrId+"\" data-text=\""+row.attrName+"\" type=\"checkbox\" class=\"icheck\" name=\"attrType\" /> <span class=\"text\">"+row.attrName+"</span>";
                    str +="</label></li>";
                }
                str += "</ul>";
                $('#attrlist').html(str);
            }
        });
    }
    function addCommType() {
        $('#zid').show();
        var str = "<option value='-1'>主类型选择</option>";
        setType(str);
        $('#title').html("添加分类");
        $('#edit').val(-1);
        $('#directoryName').val("");
        $("input[name='attrType']").each(function (){
            $(this).parent().removeClass("checked");
            $(this).removeAttr("checked");
        });
        $('#directory-modal').addClass("in");
        $('#directory-modal').attr("style","display: block;");
    }
    function setType(str) {
        $.ajax({
            url: "${pageContext.request.contextPath}/operator/commoditytype/commodityTypeList",
            type: "POST",
            async: false,
            data: {"page":"{}","req":"{}"},
            success: function (data) {
                var rows = jQuery.parseJSON(data);
                for(var i=0;i<rows.length;i++){
                    var row = rows[i];
                    if(row.masterId == 0){
                        str +="<option value=\""+row.typeId+"\">"+row.typeName+"</option>";
                    }
                }
                $("#commType").html(str);
                $('#commType').selectpicker('refresh');
            }
        });
    }
</script>
</body>
</html>