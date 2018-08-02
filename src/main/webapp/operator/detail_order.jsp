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
<input type="hidden" id="payment" value="${orderPage.payment }">
<input type="hidden" id="grossProfit" value="${orderPage.grossProfit }">
<input type="hidden" id="preferential" value="${orderPage.preferential }">
<!-- 弹出折扣单框框-->
<div class="layer-show">
	 <div class="clearfix">
          <ul class="bar-count-list">
              <li><p class="form-control-static" id="payment_1"></p></li>
              <li><p class="form-control-static" id="grossProfit_1"></p></li>
              <li><p class="form-control-static" id="preferential_1"></p></li>
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
									<th>单价</th>
									<th>购买数量</th>
									<th>小计</th>
									<th>购买时间</th>
								</tr>
							</thead>
							<tbody>


							</tbody>
						</table>
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
		var orderId = parent.$("#orderId").val();//得到操作员的一条记录
		dealRequest(orderId);
		var payment = $("#payment").val();
		var grossProfit = $("#grossProfit").val();
		var preferential = $("#preferential").val();
		$("#payment_1").text("总金额:￥"+mustTwoDecimal(payment/100)+"元");
		$("#grossProfit_1").text("毛利:￥"+mustTwoDecimal(grossProfit/100)+"元");
		$("#preferential_1").text("优惠金额:￥"+mustTwoDecimal(preferential/100)+"元");
		
	});
	
	function dealRequest(orderId){
		$.ajax({
			url: "${pageContext.request.contextPath}/operator/order/queryDetail",
			type: "POST",
			data: {"orderId":orderId},
			timeout: 8000
		}).done(function(data){
			var row = JSON.parse(data);
			console.log(row);
			if(row.code==0){//成功
				var orderDetail = row.data;
				if(orderDetail!=null){
					var html = "";
					$(".table").find("tbody").html("");
					for(var o in orderDetail){
						html += "<tr><td>"+(parseInt(o)+1)+"</td>";
						html += "<td>"+orderDetail[o].commName+"</td>";
						html += "<td>"+mustTwoDecimal(orderDetail[o].commPrice/100)+"</td>";
						html += "<td>"+orderDetail[o].commCount+"</td>";
						html += "<td>"+mustTwoDecimal(orderDetail[o].commCount*orderDetail[o].commPrice/100)+"</td>";
						html += "<td>"+getLocalTime(orderDetail[o].createTime)+"</td></tr>";
					}
					$(".table").find("tbody").append(html);
				}
			}
		});
	}
	
	function getLocalTime(nS){
		var time = new Date(nS);
		var y = time.getFullYear();
		var m = time.getMonth()+1;
		var d = time.getDate();
		var h = time.getHours();
		var mm = time.getMinutes();
		var s = time.getSeconds();
		return y+'-'+addZero(m)+'-'+addZero(d)+' '+addZero(h)+':'+addZero(mm)+':'+addZero(s);
	}
	
	function addZero(m){
		return m<10?'0'+m:m 
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