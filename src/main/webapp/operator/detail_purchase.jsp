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
<title>采购单详情</title>
<%@ include file="global/mylink.jsp"%>
</head>
<body class="layer-body">
<!-- 弹出折扣单框框-->
<div class="layer-show">
	 <div class="clearfix">
          <ul class="bar-count-list">
              <li><p class="form-control-static" id="total"></p></li>
              <li><p class="form-control-static" id="state"></p></li>
              <li><p class="form-control-static" id="remark"></p></li>
          </ul>
      </div>
	<ul class="nav nav-tabs margin-top-gap">
		<li class="active">
			<a href="#view_table" data-toggle="tab">详细数据</a>
		</li>
	</ul>
	<div class="tab-content margin-top-gap">
			<div class="tab-pane active" role="tabpanel" id="view_table">
				<div class="global-table">
					<div class="table-responsive">
						<table class="table table-bordered table-striped table-global">
							<thead>
								<tr>
									<th width="160">序号</th>
									<th>商品名</th>
									<th>进货价</th>
									<th>购买数量</th>
									<th>小计</th>
								</tr>
							</thead>
							<tbody>


							</tbody>
						</table>
					</div>
				</div>
				<div>
				<div class="form-group">
				    <label class="col-md-4 control-label"> </label>
				    <div class="col-md-6" id="postDIV">
						
					</div>
				</div>
				</div>
			</div>
		</div>
</div>
<script>
	$(document).ready(function () {
		//初始化插件
		layui.use('layer', function(){
			  var layer = layui.layer;
		});	
		var purId = parent.$("#purId").val();//得到采购单的一条记录
		dealRequest(purId);
		
	});
	
	function agree(){//同意
		var entity = {};
		entity.state = 1;//审核通过
		postResult(entity);
	}
	
	function refuse(){//拒绝
	  layer.prompt({title: '拒绝的理由', value: '拒绝', formType: 2}, function(text, index){
		  layer.close(index);
		  var entity = {};
		  entity.state = -1;//审核不通过
		  entity.remark = text;//审核不通过
		  postResult(entity);
	  });
	}
	
	function postResult(purchase){
		var goods = [];
		purchase.purId = parent.$("#purId").val();//采购单的purId
		$.ajax({
 			url: "${pageContext.request.contextPath}/operator/purchase/updPurchase",
 			type: "POST",
 			data: {"purchase" : JSON.stringify(purchase), "goods" : JSON.stringify(goods)},
 			timeout: 8000
 		}).done(function(data){
 			var row = JSON.parse(data);
 			if(row.code==0){//成功
 				layer.msg("审核采购单成功", {icon: 1});
 				setTimeout("closePanel()", 1000);
 				return;
 			}
 			$("#resultMessage").html("审核采购单失败");
 		})
	}
	
	function dealRequest(purId){
		$.ajax({
			url: "${pageContext.request.contextPath}/operator/purchase/queryDetail",
			type: "POST",
			data: {"purId":purId},
			timeout: 8000
		}).done(function(data){
			var row = JSON.parse(data);
			console.log(row);
			var total = 0;
			if(row.code==0){//成功
				var purchaseDetailPages = row.data.purchaseDetailPages;
				if(purchaseDetailPages!=null){
					var html = "";
					$(".table").find("tbody").html("");
					for(var p in purchaseDetailPages){
						var costPrice = mustTwoDecimal(purchaseDetailPages[p].amount*purchaseDetailPages[p].commodity.costPrice/100);
						html += "<tr><td>"+(parseInt(p)+1)+"</td>";
						html += "<td>"+purchaseDetailPages[p].commodity.commName+"</td>";
						html += "<td>"+mustTwoDecimal(purchaseDetailPages[p].commodity.costPrice/100)+"</td>";
						html += "<td>"+purchaseDetailPages[p].amount+"</td>";
						html += "<td>"+costPrice+"</td>";
						total = parseFloat(total) + parseFloat(costPrice);
					}
					$(".table").find("tbody").append(html);
				}
			}
			$("#total").text("总金额:￥"+mustTwoDecimal(total)+"元");
			$("#state").text("采购单状态:	"+dealState(row.data.state));
			if(row.data.state!=null&&row.data.state==-1){
				$("#remark").text("未通过理由:	"+dealNull(row.data.remark));
			}
			if(row.data.state!=null&&row.data.state==0){
				var html = "";
				$("#postDIV").html(html);
				html += "<button type=\"button\" class=\"btn btn-primary margin-right-5\" onclick=\"agree()\">同意</button>";
				html += "<button type=\"button\" class=\"btn btn-primary margin-right-5\" onclick=\"refuse()\" style=\"background-color: #fcaf50;border-color: #fcaf50;\">拒绝</button>";
				$("#postDIV").append(html);
			}
		});
	}
	
	function dealNull(probablyNull){//处理空值
		if(probablyNull==null){
			return "";
		}
		return probablyNull;
	}
	
	function dealState(state){
		if(state==null){
			return "";
		}else if(state=="0"){
			return "待审核";
		}else if(state=="1"){
			return "审核通过";
		}else if(state=="-1"){
			return "审核未通过";
		}
		return state;
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
	
	function closePanel(){//关闭
		var index = parent.layer.getFrameIndex(window.name);
		parent.layer.close(index); //执行关闭
		parent.location.reload();
	}
</script>
</body>

</html>