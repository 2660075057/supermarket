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
<title>添加促销单</title>
<%@ include file="global/mylink.jsp"%>
</head>

<body class="layer-body">
	<div class="layer-show">
	  <div class="form-horizontal" id="form-user">
			<div class="form-group">
				<label for="title" class="col-md-3 control-label">促销单名称</label>
				<div class="col-md-8">
					<input type="text" class="form-control" name="title" />
				</div>
			</div>
          	<div class="form-group">
			    <label class="col-md-3 control-label">优惠类型</label>
			    <div class="col-md-8">
			    	<select class="selectpicker" id="discount_type">
						<option value="3">直接折扣</option>
						<option value="1">满减折扣</option>
					</select>
			    </div>
			    <a href="javascript:javascript:void(0)" id="addDIV"><img alt="" src="${pageContext.request.contextPath}/operator/images/unfold.png"></a>
			</div>
			<div id="discountDIV">
				
			</div>
			<div id="fullCutDIV">
				
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
		var discount_type = $("#discount_type").val();
		var len = $("a[id^=removeDIV]").length;
		if(discount_type==1){//满减
			var html = "";
			html += "<div class=\"form-group\">";
			html += "<label for=\"fullCut\" class=\"col-md-3 control-label\">满减（元）</label>";
			html += "<div class=\"col-md-8\">";
			html += "<div class=\"col-items col-items-2 clearfix\">";
			html += "<div class=\"item\">";
			html += "<input type=\"text\" class=\"form-control\" name=\"full\" id=\"full_"+len+"\" placeholder=\"消费金额\"/>";
			html += "</div>";
			html += "<div class=\"item item-separator\">减</div>";
			html += "<div class=\"item\">";
			html += "<input type=\"text\" class=\"form-control\" name=\"minus\" id=\"minus_"+len+"\" placeholder=\"优惠金额\"/>";
			html += "</div>";
			html += "</div>";
			html += "</div>";
			html += " <a href=\"javascript:void(0)\" id=\"removeDIV_"+len+"\"><img alt=\"\" src=\"${pageContext.request.contextPath}/operator/images/unfold1.png\"></a>";
			html += "</div>";
			$("#fullCutDIV").append(html);
		}else if(discount_type==3){//折扣
			var html = "";
			html += "<div class=\"form-group\">";
			html += "<label class=\"col-md-3 control-label\">折扣商品</label>";
			html += "<div class=\"col-md-8\">";
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
			html += "</div>";
			html += "<div class=\"form-group\">";
			html += "<label for=\"discount\" class=\"col-md-3 control-label\">折扣</label>";
			html += "<div class=\"col-md-8\">";
			html += "<div class=\"input-group\">";
			html += "<input type=\"text\" class=\"form-control border-right-none\" name=\"discountVal_"+len+"\" id=\"discountVal_"+len+"\" />";
			html += "<div class=\"input-group-addon\">%</div>";
			html += "</div>";
			html += "</div>";
			html += "<a href=\"javascript:void(0)\" id=\"removeDIV_"+len+"\"><img alt=\"\" src=\"${pageContext.request.contextPath}/operator/images/unfold1.png\"></a>";
			html += "</div>";
			$("#discountDIV").append(html);
			$("select[id=commType_"+len+"]").selectpicker('refresh');
			$("select[id=commList_"+len+"]").selectpicker('refresh');
		}
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
		$("a[id=removeDIV_"+len+"]").on("click",function (){
			var pId = $(this).parent().parent().prop("id");
			if(pId=="fullCutDIV"){//满减
				$(this).parent().remove();
			}else if(pId=="discountDIV"){//折扣
				$(this).parent().prev().remove();
				$(this).parent().remove();
			}
		});
	});
	
	$(".btn-primary").on("click",function (){
		$("#resultMessage").html("");//点击提交按钮时，先将操作结果置空
		var title = $("input[name='title']").val();//促销单标题
		var param = {};
		if($.trim(title).length==0){
			$("#resultMessage").html("促销单标题不能为空");
			return ;
		}
		param.title = title;
		if($("select[id^=commType_]").length == 0 && $("input[id^=full_]").length == 0){
			$("#resultMessage").html("请添加优惠类型");
			return ;
		}
		var discounts = new Array();//折扣的数组
		var discountFlag = false;
		var commTypeArr = new Array();//商品类型的数组
		var commArr = new Array();//商品的数组
		$("select[id^=commType_]").each(function (){//折扣
			var discount = {};
			var tmpLen = $(this).prop("id").split("_")[1];
			var commTypeVal = $(this).val();
			var commVal = $("select[id=commList_"+tmpLen+"]").val();
			if($.trim(commTypeVal).length==0&&$.trim(commVal).length==0){
				$("#resultMessage").html("商品类型和商品不能都为空");
				$("select[id=commType_"+tmpLen+"]").prev().prev().focus();//获取商品类型的焦点
				discountFlag = true;
				return false;
			}
			if($.trim(commVal).length==0){
				if($.inArray(commTypeVal, commTypeArr)>-1){
					$("#resultMessage").html("商品类型存在重复");
					$("select[id=commType_"+tmpLen+"]").prev().prev().focus();//获取商品类型的焦点
					discountFlag = true;
					return false;
				}
				commTypeArr.push(commTypeVal);
				discount.typeId = commTypeVal;
			}else{
				var typeAndVal = commTypeVal+"_"+commVal;
				if($.inArray(typeAndVal, commArr)>-1){
					$("#resultMessage").html("商品存在重复");
					$("select[id=commList_"+tmpLen+"]").prev().prev().focus();//获取商品的焦点
					discountFlag = true;
					return false;
				}
				commArr.push(typeAndVal);
				discount.commId = commVal;
			}
			var discountVal = $("input[id^=discountVal_"+tmpLen+"]").val();
			if($.trim(discountVal).length==0){
				$("#resultMessage").html("折扣不能为空");
				$("input[id=discountVal_"+tmpLen+"]").focus();//获取折扣的焦点
				discountFlag = true;
				return false;
			}
			var zkVal = $.trim(discountVal);
			var reg = /^[^0][\d]?$/;
			if(!reg.test(zkVal)){
				$("#resultMessage").html("折扣只能是1-99的整数不能为空");
				$("input[id=discountVal_"+tmpLen+"]").focus();//获取折扣的焦点
				discountFlag = true;
				return false;
			}
			var data = {};
			data.percent = zkVal;
			discount.data = JSON.stringify(data);
			discount.discountType = 3;
			discounts.push(discount);
		});
		if(discountFlag){//折扣验证不通过
			return;
		}
		var fullcutFlag = false;
		var fullcutArr = new Array();//消费金额的数组
		$("input[id^=full_]").each(function (){//满减
			var discount = {};
			var tmpLen = $(this).prop("id").split("_")[1];
			var fullVal = $.trim($(this).val());
			var minusVal = $.trim($("input[id=minus_"+tmpLen+"]").val());
			if(fullVal.length==0){
				$("#resultMessage").html("消费金额不能为空");
				$(this).focus();//获取费金额的焦点
				fullcutFlag = true ;
				return false;
			}
			if(minusVal.length==0){
				$("#resultMessage").html("优惠金额不能为空");
				$("input[id=minus_"+tmpLen+"]").focus();//获取优惠金额的焦点
				fullcutFlag = true ;
				return false;
			}
			var reg = /^[0-9]*[1-9][0-9]*$/;
			var xfVal = parseFloat(fullVal)*100;
			if(!reg.test(xfVal)){
				$("#resultMessage").html("消费金额格式不正确");
				$(this).focus();//获取消费金额的焦点
				fullcutFlag = true;
				return false;
			}
			if($.inArray(xfVal, fullcutArr)>-1){
				$("#resultMessage").html("消费金额存在重复");
				$(this).focus();//获取消费金额的焦点
				fullcutFlag = true;
				return false;
			}
			fullcutArr.push(xfVal);
			var yhVal = parseFloat(minusVal)*100;
			if(!reg.test(yhVal)){
				$("#resultMessage").html("优惠金额格式不正确");
				$("input[id=minus_"+tmpLen+"]").focus();//获取优惠金额的焦点
				fullcutFlag = true;
				return false;
			}
			if(parseInt(xfVal)<=parseInt(yhVal)){
				$("#resultMessage").html("优惠金额不能大于等于消费金额");
				$("input[id=minus_"+tmpLen+"]").focus();//获取优惠金额的焦点
				fullcutFlag = true;
				return false;
			}
			var data = {};
			data.maxPrice = fullVal;
			data.reducePrice = minusVal;
			discount.data = JSON.stringify(data);
			discount.discountType = 1;
			discounts.push(discount);
		});
		if(fullcutFlag){//满减验证不通过
			return;
		}
	 	$.ajax({
 			url: "${pageContext.request.contextPath}/operator/discountGroup/addDiscount",
 			type: "POST",
 			data: {"discountGroup" : JSON.stringify(param), "discounts" : JSON.stringify(discounts)},
 			timeout: 8000
 		}).done(function(data){
 			var row = JSON.parse(data);
 			if(row.code==0){//成功
 				layer.msg("添加促销单成功", {icon: 1});
 				setTimeout("closePanel()", 1000);
 				return;
 			}
 			$("#resultMessage").html("添加促销单失败");
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