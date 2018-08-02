<%@ page language="java" import="java.util.*,com.grape.supermarket.entity.page.CommodityTypePageEntity" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>添加采购单</title>
<%@ include file="global/mylink.jsp"%>
</head>

<body class="layer-body">
	<div class="layer-show">
	  <div class="form-horizontal" id="form-user">
          	<div class="form-group">
			    <label class="col-md-3 control-label">采购单类型</label>
			    <div class="col-md-8">
			    	<select class="selectpicker" id="type">
						<option value="1">采购</option>
						<option value="2">入库</option>
					</select>
			    </div>
			</div>
          	<div class="form-group">
			    <label class="col-md-3 control-label">商店</label>
			    <div class="col-md-8">
			    	<select class="selectpicker" id="shopList">
				    	<c:forEach items="${shops}" var="shop">
							<option value="${shop.shopId}">${shop.shopName}</option>
				    	</c:forEach>
					</select>
			    </div>
			</div>
			<div id="goodsDIV">
				
			</div>
			<div class="form-group">
			    <label class="col-md-3 control-label"></label>
			    <div class="col-md-8">
					<div class="fr">
						<div class="add-field" id="add-field">
							<a href="javascript:void(0);" id="addDIV" style="color: #00a2e9;"><strong>+</strong> 添加商品信息</a>
						</div>
					</div>
			    </div>
			</div>
			 <div class="form-group">
                 <label for="message" class="col-md-3 control-label">订单理由</label>
                 <div class="col-md-8">
                     <textarea name="" id="message" class="form-control" style="height:150px"></textarea>
                 </div>
             </div>
			<div class="form-group">
			    <label class="col-md-3 control-label"> </label>
			    <div class="col-md-8">
					<button type="submit" class="btn btn-primary">提交</button>
					<span id="resultMessage" style="color: red;"></span>
				</div>
			</div>
        </div>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function(){
	layui.use('layer', function(){
		  var layer = layui.layer;
	});
	
	$("#addDIV").on("click",function (){
		var len = $("a[id^=removeDIV]").length;
		var html = "";
		html += "<div class=\"form-group\">";
		html += "<label class=\"col-md-3 control-label\">折扣商品</label>";
		html += "<div class=\"col-md-5\">";
		html += "<div class=\"col-items col-items-2 clearfix\">";
		html += "<div class=\"item\">";
		html += "<select class=\"selectpicker\" data-live-search=\"true\" title=\"商品类型\" id=\"commType_"+len+"\">";
		<%
		List<CommodityTypePageEntity> ty = (List<CommodityTypePageEntity>)request.getAttribute("typelist");
		if(ty!=null){
			for(int tl=0,len=ty.size(); tl<len; tl++){
		%>
			html += "<option value=\"<%=ty.get(tl).getTypeId()%>\" >";
			html += '<%=ty.get(tl).getTypeName()%>';
			html += "</option>";
		<% 
			}
		}
		%>
		html += "</select>";
		html += "</div>";
		html += "<div class=\"item item-separator\"></div>";
		html += "<div class=\"item\">";
		html += "<select class=\"selectpicker\" data-live-search=\"true\" title=\"商品\" id=\"commList_"+len+"\">";
		//食品
		html += "</select>";
		html += "</div>";
		html += "</div>";
		html += "</div>";
		html += "<div class=\"col-md-3\">";
		html += "<input type=\"text\" class=\"form-control\" name=\"amount\" id=\"amountVal_"+len+"\" placeholder=\"请输入数量\"/>";
		html += "</div>";
		html += "<a href=\"javascript:void(0)\" id=\"removeDIV_"+len+"\"><img alt=\"\" src=\"${pageContext.request.contextPath}/operator/images/unfold1.png\"></a>";
		html += "</div>";
		$("#goodsDIV").append(html);
		$("select[id=commType_"+len+"]").selectpicker('refresh');
		$("select[id=commList_"+len+"]").selectpicker('refresh');
		//绑定商店类型的change事件
		$("select[id=commType_"+len+"]").on("change", function (){
			var typeId = $(this).val();
			var tmpLen = $(this).prop("id").split("_")[1];
			var page = {};
			var param = {};
			param.typeId = typeId;
			$.ajax({
	            url: "${pageContext.request.contextPath}/operator/commodity/commodityList",
	            type: "POST",
	            data: {"page":JSON.stringify(page),"req":JSON.stringify(param)},
	            success: function (data) {
	                if(data != null && data.length >0){
	                    var rows = jQuery.parseJSON(data);
	                    var commHtml = "";
	                    $("select[id=commList_"+tmpLen+"]").html("");
	                    for(var i=0,len=rows.length; i<len; i++){
	                    	 var row = rows[i];
	                    	 var commId = row.commId;
	                    	 var commName = row.commName;
	                    	 commHtml += "<option value=\""+commId+"\" >"+commName+"</option>";
	                    }
	                    $("select[id=commList_"+tmpLen+"]").append(commHtml);
	                    $("select[id=commList_"+tmpLen+"]").selectpicker('refresh');
	                }
	            }
	        });
		});
		//删除一列的按钮绑定事件
		$("a[id=removeDIV_"+len+"]").on("click",function (){
			$(this).parent().remove();
		});
	});
	
	$(".btn-primary").on("click",function (){
		$("#resultMessage").html("");//点击提交按钮时，先将操作结果置空
		if($("select[id^=commType_]").length == 0){
			$("#resultMessage").html("请添加商品信息");
			return ;
		}
		var goods = new Array();//商品的数组
		var goodsFlag = false;
		var commArr = new Array();//商品的数组
		$("select[id^=commType_]").each(function (){//折扣
			var good = {};
			var tmpLen = $(this).prop("id").split("_")[1];
			var commTypeVal = $(this).val();
			var commVal = $("select[id=commList_"+tmpLen+"]").val();
			if($.trim(commTypeVal).length==0){
				$("#resultMessage").html("商品类型不能为空");
				$("select[id=commType_"+tmpLen+"]").prev().prev().focus();//获取商品类型的焦点
				goodsFlag = true;
				return false;
			}
			if($.trim(commVal).length==0){
				$("#resultMessage").html("商品不能为空");
				$("select[id=commList_"+tmpLen+"]").prev().prev().focus();//获取商品的焦点
				goodsFlag = true;
				return false;
			}
			var typeAndVal = commTypeVal+"_"+commVal;
			if($.inArray(typeAndVal, commArr)>-1){
				$("#resultMessage").html("商品存在重复");
				$("select[id=commList_"+tmpLen+"]").prev().prev().focus();//获取商品的焦点
				goodsFlag = true;
				return false;
			}
			commArr.push(typeAndVal);
			good.commId = commVal;
			var amountVal = $("input[id=amountVal_"+tmpLen+"]").val();
			if($.trim(amountVal).length==0){
				$("#resultMessage").html("采购数量不能为空");
				$("input[id=amountVal_"+tmpLen+"]").focus();//获取采购数量的焦点
				goodsFlag = true;
				return false;
			}
			var cgVal = $.trim(amountVal);
			var reg = /^[0-9]*[1-9][0-9]*$/;
			if(!reg.test(cgVal)){
				$("#resultMessage").html("采购数量只能是正整数");
				$("input[id=amountVal_"+tmpLen+"]").focus();//获取采购数量的焦点
				goodsFlag = true;
				return false;
			}
			good.amount = cgVal;
			goods.push(good);
		});
		if(goodsFlag){//折扣验证不通过
			return;
		}
		var purchase = {};
		purchase.type = $("select[id=type]").val();//采购单类型
		purchase.shopId = $("select[id=shopList]").val();//商店id
		purchase.message = $("#message").val();//创建订单理由
	 	$.ajax({
 			url: "${pageContext.request.contextPath}/operator/purchase/addPurchase",
 			type: "POST",
 			data: {"purchase" : JSON.stringify(purchase), "goods" : JSON.stringify(goods)},
 			timeout: 8000
 		}).done(function(data){
 			var row = JSON.parse(data);
 			if(row.code==0){//成功
 				layer.msg("添加采购单成功", {icon: 1});
 				setTimeout("closePanel()", 1000);
 				return;
 			}
 			$("#resultMessage").html("添加采购单失败");
 		}) 
		
	});

});


function closePanel(){//关闭
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index); //执行关闭
	parent.location.reload();
}
</script>
</html>