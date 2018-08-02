<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<title>添加权限</title>
<%@ include file="global/mylink.jsp"%>
</head>
<body class="layer-body">
<!-- 弹出折扣单框框-->
<div class="layer-show">
	<input type="hidden" value="${groupId }" id="shopDiscountGroupID">
	<div id="main-table">
		<div class="table-responsive">
		    <table class="table table-bordered table-striped table-global">
			    <thead>
			        <tr>								
			        	<th width="160">序号</th>
						<th>商品或类型名</th>
						<th>优惠类型</th>
						<th>具体说明</th>
			        </tr>
			    </thead>
			    <tbody>
			        
			    </tbody>
			</table>
		</div>
	</div>
</div>
<script>
	$(document).ready(function () {
		//初始化插件
		layui.use('layer', function(){
			  var layer = layui.layer;
		});	
		var groupId = parent.$("#groupId").val();//得到操作员的一条记录
		var shopDiscountGroupID = $("#shopDiscountGroupID").val();
		if($.trim(shopDiscountGroupID).length!=0){
			dealRequest(shopDiscountGroupID);
		}else{
			dealRequest(groupId);
		}
		
	});
	
	function dealRequest(groupId){
		$.ajax({
			url: "${pageContext.request.contextPath}/operator/discountGroup/queryDetail",
			type: "POST",
			data: {"groupId":groupId},
			timeout: 8000
		}).done(function(data){
			var row = JSON.parse(data);
			console.log(row);
			if(row.code==0){//成功
				var discountGroup = row.data.discountPages;
				if(discountGroup!=null){
					var html = "";
					$(".table").find("tbody").html("");
					for(var d in discountGroup){
						var dg = discountGroup[d];
						html += '<tr>';
						html += '<td>'+(parseInt(d)+1)+'</td>';
						var typeName = "满减";
						if(dg.commodityTypeEntity!=null){
							typeName = dg.commodityTypeEntity.typeName;
						}else if(dg.commodityEntity!=null){
							typeName = dg.commodityEntity.commName;
						}
						html += '<td>'+typeName+'</td>';
						html += '<td>'+dealDiscountType(dg.discountType)+'</td>';
						html += '<td>'+dealDetail(dg.data)+'</td>';
						html += '</tr>';
					}
					$(".table").find("tbody").append(html);
				}
			}
		});
	}
	
	function dealDiscountType(discountType){
		if(discountType==null){
			return "";
		}
		if(discountType=="1"){
			return "满减";
		}else if(discountType=="3"){
			return "直接折扣";
		}
		return discountType;
		
	}
	
	function dealDetail(data){
		if(data==null){
			return "";
		}
		var jsonDATA = JSON.parse(data);
		if(jsonDATA.hasOwnProperty("percent")){
			var val = mustTwoDecimal(jsonDATA["percent"]/10);
			return val.substring(0, val.length-1)+"折";
		}else if(jsonDATA.hasOwnProperty("maxPrice")&&jsonDATA.hasOwnProperty("reducePrice")){
			return "满"+mustTwoDecimal(jsonDATA["maxPrice"]/100)+"元减"+mustTwoDecimal(jsonDATA["reducePrice"]/100)+"元";
		}
		return data;
	}
	
	function mustTwoDecimal(number){
	    var num = twoDecimal(number).toString();
	    var index = num.indexOf(".");
	    if(index <= 0){
	        num += '.';
	        index = num.length - 1;
	    }
	    while((index + 3) != num.length){
	        num += '0';
	    }
	    return num;
	}
	
	function twoDecimal(number){
	    if(isNaN(number)){
	        return;
	    }
	    return Math.round(number*100)/100;
	}
</script>
</body>

</html>