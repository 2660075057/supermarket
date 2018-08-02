<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="renderer" content="webkit">
<title>销售管理</title>
    <%@ include file="global/mylink.jsp" %>
</head>
<body>
<input type="hidden" value="" id="orderId">
<%@ include file="global/header.jsp" %>
<div class="main-content">
    <jsp:include page="menu.jsp" flush="true" >
        <jsp:param name="type" value="order_list"  />
    </jsp:include>
	<div class="main">
		<div class="bread">
			<a href="#">首页</a> &gt; 销售管理
		</div>
		<div class="main-container">
			<div class="page-bar clearfix">
				<div class="pull-left">
					<div class="page-title">
						<span class="line"></span>
						销售管理
					</div>
				</div>
				<div class="pull-right">
					<form class="form-inline">
							<select id="shopId" class="selectpicker margin-right-5" style="width: 100px">
	
	                        </select>
							<div class="form-control-hasIcon w250 margin-right-5">
								<i class="right-icon glyphicon glyphicon-calendar"></i>
								<input type="text" class="form-control margin-right-10" placeholder="请选择起始时间" value="" id="daterangepicker" />
							</div>
						<button type="button" class="btn btn-primary" onclick="onSearch()">查询</button>
						<button type="button" class="btn btn-primary" onclick="exPort()">导出</button>
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
									<th>ID</th>
									<th>顾客姓名</th>
									<th>站点名称</th>
									<th>付款总额</th>
									<th>毛利</th>
									<th>优惠金额</th>
									<th>状态</th>
									<th>创建时间</th>
									<th>操作</th>
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
<script>
$(document).ready(function(){
	initShopList();
	
    layui.use('layer', function(){
        var layer = layui.layer;
    });

	$("#daterangepicker").daterangepicker({
		"startDate": "2017-10-01",
		"endDate": "2017-12-01",
		"applyClass": "btn-danger",
		"opens": "left",
		"locale": $.data_picker_locale,
		},function (start, end, label) {
			
		}
	);
	
	var entity = {};
	var shopId = $("#shopId").val();
	if($.trim(shopId)==-1){
		entity.shopId = null;
	}else{
		entity.shopId = shopId;
	}
	var date = $("#daterangepicker").val();//时间
    layuiPage(entity, date);
});

function onSearch(){//搜索
	$(".table").find("tbody").html("");
	var entity = {};
	var shopId = $("#shopId").val();
	var date = $("#daterangepicker").val();//时间
	if($.trim(shopId)==-1){
		entity.shopId = null;
	}else{
		entity.shopId = shopId;
	}
	var date = $("#daterangepicker").val();//时间
    layuiPage(entity, date);
}

function exPort(){//导出
	var shopId = $("#shopId").val();
	var date = $("#daterangepicker").val();//时间
	if($.trim(shopId)==-1){
		shopId = null;
		window.open("${ctx}/operator/order/downLoad?date="+date);
	}else{
		shopId = shopId;
		window.open("${ctx}/operator/order/downLoad?date="+date+"&shopId=" +shopId);
	}
	
	
}

function count() {
    var count=0;
	var entity = {};
	var shopId = $("#shopId").val();
	if($.trim(shopId)==-1){
		entity.shopId = null;
	}else{
		entity.shopId = shopId;
	}
	var date = $("#daterangepicker").val();//时间
    $.ajax({
        url: "${pageContext.request.contextPath}/operator/order/queryOrderListCount",
        type: "POST",
        async: false,
        data: {"param":JSON.stringify(entity),"date":date},
        success: function (data) {
        	var row = JSON.parse(data);
        	if(row.code==0){//成功
	            count = parseInt(row.data);
        	}
        }
    });
    return count;
}


function layuiPage(param, date) {
    layui.use(['laypage', 'layer'], function(){
        var laypage = layui.laypage
            ,layer = layui.layer;
        laypage.render({
            elem: 'layuipage'
            ,count: count()
            ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
            ,limit : 10
            ,limits: [10,20,30,40]
            ,jump: function(obj){
            	var currPage = obj.curr;
	            var pageSize = obj.limit;
                var page = {};
                page["pageSize"] = obj.limit;
                page["pageCurrent"] = obj.curr;
                queryOrderList(page, param, date, currPage, pageSize);
            }
        });
    });
}

function queryOrderList(page, param, date, currPage, pageSize){
 	$.ajax({
			url: "${pageContext.request.contextPath}/operator/order/queryOrderList",
			type: "POST",
			data: {"param":JSON.stringify(param), "page":JSON.stringify(page), "date":date},
			timeout: 8000
		}).done(function(data){
			var row = JSON.parse(data);
			if(row.code==0){//成功
				var rows = row.data;
				drawRow(rows, currPage, pageSize);
			}
		}) 
}

function drawRow(rows, currPage, pageSize){//画表格
	console.log(rows);
	var startIndex = 0;
	if(currPage>0){
		startIndex = (currPage-1)*pageSize;
	}
	var $tbody = $(".table").find("tbody");
	if(!rows||rows.length==0) 
		return;
	var html='';
	for(var i=0,len=rows.length; i<len; i++){
		var number = parseInt(startIndex)+parseInt(i+1);
		var newsEntity = rows[i];
		var tr='';
		tr+='<tr>';
		tr+='<td>'+number+'</td>';
		tr+='<td>'+newsEntity.orderId+'</td>';
		if(newsEntity.customer !=null){
            tr+='<td>'+newsEntity.customer.coustName+'</td>';
        }else{
            tr+='<td>未知</td>';
        }
		tr+='<td>'+newsEntity.shopEntity.shopName+'</td>';
		tr+='<td>'+mustTwoDecimal(newsEntity.payment/100)+'</td>';
		tr+='<td>'+mustTwoDecimal(newsEntity.grossProfit/100)+'</td>';
		tr+='<td>'+mustTwoDecimal(newsEntity.preferential/100)+'</td>';
		tr+='<td>'+stateDeal(newsEntity.deleteMark)+'</td>';
		tr+='<td>'+getLocalTime(newsEntity.createTime)+'</td>';
		tr+='<td><button onclick=\"javascript:detail(\''+newsEntity.orderId+'\');\" class=\"layui-btn\">详细</button></td>';
		tr+='</tr>';
		html+=tr;
	}
	$tbody.html(html);
};

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

function stateDeal(state){//处理状态
	if(state==0){
		return "正常<span class=\"ico-status ico-status-success\"></span>";
	}else{
		return "删除 <span class=\"ico-status ico-status-disabled\"></span>";
	}
}

function initShopList(){//初始化站点
	$.ajax({
        url: "../operator/shop/shopList",
        type: "POST",
        async: false,
        data: {"page":"{}","req":"{}"},
        success: function (data) {
            var str = "<option value=\"-1\">全部站点</option>";
            var rows = jQuery.parseJSON(data);
            for(var i=0;i<rows.length;i++){
                var row = rows[i];
                str +="<option value=\""+row.shopId+"\">"+row.shopName+"</option>";
            }
            $("#shopId").html(str);
            $("#shopId").selectpicker('refresh');
        }
    });
}

function detail(orderId){
	$("#orderId").val(orderId);
	layer.open({
		title: '订单详情',
		type: 2,
		area: ['1000px', '580px'],
		fixed: false, //不固定
		maxmin: true,
		content: '../operator/order/detail?orderId='+orderId
	});
}

</script>
</body>
</html>