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
	<!-- 弹出顾客框框-->
	<div class="layer-show">
		<div class="row">
			<div class="col-md-6">
				<form class="form-inline">
					<div class="form-control-hasIcon w250">
						<i class="right-icon glyphicon glyphicon-calendar"></i>
						<input type="text" class="form-control margin-right-10" placeholder="" value="" id="daterangepicker" />
					</div>
					<button type="button" class="btn btn-primary" id="queryBtn">查询</button>
				</form>
			</div>
			<div class="col-md-6">
				<ul class="bar-count-list">
					<li>总消费
						<span class="font-primary" id="totalMoney">￥0.0</span>
					</li>
					<li>进店数
						<span class="font-primary" id="shopVisitCount">0</span>
					</li>
					<li>购买数
						<span class="font-primary" id="buyCount">0</span>
					</li>
				</ul>
			</div>
		</div>
		<ul class="nav nav-tabs margin-top-gap">
			<li class="active">
				<a href="#view_analysis" data-toggle="tab">进店分析</a>
			</li>
			<li>
				<a href="#sale_analysis" role="tab" data-toggle="tab">购买分析</a>
			</li>
		</ul>
		<div class="tab-content margin-top-gap">
			<div class="tab-pane active" role="tabpanel" id="view_analysis">
				<div class="btn-group tabs-theme" role="group" data-target="#tab_view_analysis">
					<button type="button" class="btn btn-default btn-primary">表格</button>
					<button type="button" class="btn btn-default">柱状图</button>
					<button type="button" class="btn btn-default">折线图</button>
					<button type="button" class="btn btn-default">趋势图</button>
				</div>
				<div class="view_analysis_table margin-top-gap">
					<div id="tab_view_analysis">
						<div class="panel-theme active">
							<div class="global-table">
								<div class="table-responsive">
									<table class="table table-bordered table-striped table-global" id="shopLogs">
										<thead>
											<tr>
												<th width="160">序号</th>
												<th>商店名称</th>
												<th>进店时间</th>
											</tr>
										</thead>
										<tbody>
											
										</tbody>
									</table>
								</div>
							</div>
							<div class="pager-md clearfix margin-top-gap">
				                <div class="pull-right">
				                    <div id="layuiShopLog"></div>
				                </div>
				            </div>
						</div>
						<div class="panel-theme">
							<div id="chart" class="chart"></div>
						</div>
						<div class="panel-theme">
							<div id="line-chart" class="line-chart"></div>
						</div>
						<div class="panel-theme">
							<div id="trend-chart" class="trend-chart"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="tab-pane" role="tabpanel" id="sale_analysis">
				<div class="btn-group tabs-theme" role="group" data-target="#tab_view_analysis_cus">
					<button type="button" class="btn btn-default btn-primary">表格</button>
					<button type="button" class="btn btn-default">柱状图</button>
					<button type="button" class="btn btn-default">折线图</button>
					<button type="button" class="btn btn-default">趋势图</button>
				</div>
				<div class="view_analysis_table margin-top-gap">
					<div id="tab_view_analysis_cus">
							<div class="panel-theme active">
								<div class="global-table">
									<div class="table-responsive">
										<table class="table table-bordered table-striped table-global" id="orders">
											<thead>
												<tr>
													<th width="160">序号</th>
													<th>商店名称</th>
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
									<div class="pager-md clearfix margin-top-gap">
						                <div class="pull-right">
						                    <div id="layuiOrder"></div>
						                </div>
						            </div>
								</div>
							</div>
							<div class="panel-theme">
								<div id="buyChart" class="buyChart"></div>
							</div>
							<div class="panel-theme">
								<div id="buyLine-chart" class="buyLine-chart"></div>
							</div>
							<div class="panel-theme">
								<div id="buyTrend-chart" class="buyTrend-chart"></div>
							</div>
						</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/operator/assets/echart/echarts.common.min.js"></script>
	<script>
	$(document).ready(function () {
		//初始化插件
		layui.use('layer', function(){
			  var layer = layui.layer;
		});	
		var coustId = parent.$("#coustId").val();//得到操作员的一条记录
		dealRequest(coustId,null);
		$("#queryBtn").on("click",function (){
			var date = $("#daterangepicker").val();
			dealRequest(coustId, date);
		});

		$("#daterangepicker").daterangepicker({
			"startDate": nowtime(),
			"endDate": addDate(nowtime(), 6),
			"applyClass": "btn-danger",
			"timePicker24Hour": true,
			"timePickerIncrement": 15,
			"locale": $.data_picker_locale,
		},
		function (start, end, label) {
			
		});
		
	});
	
	function dealRequest(coustId, date){
		$.ajax({
			url: "${pageContext.request.contextPath}/operator/customer/queryDetail",
			type: "POST",
			data: {"coustId":coustId, "date":date},
			timeout: 8000
		}).done(function(data){
			var row = JSON.parse(data);
			$("#shopVisitCount").html(0);
			if(row.code==0){//成功
				$("#shopVisitCount").html(row.data.shopLogs.length);
				var shopMap = {};
				if(row.data.shops!=null){
					var shops = row.data.shops;
					for(var s in shops){
						var shop = shops[s];
						var k = shop.shopId;
						var v = shop.shopName;
						shopMap[k] = v;
					}
				}
				fillShopLogs(row.data.shopLogs, shopMap);
				fillOrders(row.data.orders);
			}
		});
	}
	
	function fillShopLogs(shopLogs, shopMap){
		if(shopLogs==null){
			return;
		}
		var count = shopLogs.length;
		layuiShopLog(shopLogs, count);
		
		var dataMap = {};
		for(var s in shopLogs){
			var localTime = getLocalTime(shopLogs[s].createTime);
			
			var timeArr = localTime.split(" ");
			var k = shopLogs[s].shopId+"_"+$.trim(timeArr[0]);
			if(typeof(dataMap[k]) == "undefined"){
				dataMap[k] = 1;
			}else{
				dataMap[k] = parseInt(dataMap[k])+1;
			}
		}
		var date = $("#daterangepicker").val();
		var startTime = "";
		var endTime = "";
		if(date.indexOf("~")>-1){
			var dateArr = date.split("~");
			startTime = $.trim(dateArr[0]);
			endTime = $.trim(dateArr[1]);
		}
		var plusDay = getTime2Time(endTime, startTime)+1;
		var lineYJSON = "";
		var barYJSON = "";
		var trendYJSON = "";
		var xData = null;//得到x轴
		var titleArr = new Array();
		var totalYData = new Array();//总数
		for(var pd=0; pd<plusDay; pd++){
			totalYData[pd] = 0;//给数组赋初值
		}
		$.each(shopMap,function(key, values){
			titleArr.push(values);
			xData = new Array();//得到x轴
			var yData = new Array();//得到y轴
			for(var p=0; p<plusDay; p++){
				var d = addDate(startTime, p);
				if(p==0){
					d = startTime;
				}
				var tmpKey = key+"_"+d;
				xData.push(d);
				if(typeof(dataMap[tmpKey]) == "undefined"){
					yData.push(0);
					totalYData[p] = parseInt(totalYData[p])+0;
				}else{
					yData.push(dataMap[tmpKey]);
					totalYData[p] = parseInt(totalYData[p])+parseInt(dataMap[tmpKey]);
					
				}
			}
			lineYJSON += '{'+
				'"name": "'+values+'",'+
				'"type": "line",'+
				'"data": ['+yData+']'+
			'}'+",";
			
			trendYJSON += '{'+
				'"name": "'+values+'",'+
				'"type": "line",'+
				'"smooth": "true",'+
				'"data": ['+yData+']'+
			'}'+",";
			
			barYJSON += '{'+
				'"name": "'+values+'",'+
				'"type": "bar",'+
				'"stack": "次数",'+
				/* '"label": {'+
				'"normal":{'+
							'"show": "true",'+
							'"position": "left"' +
						'}'+
					'},'+  */
				'"data": ['+yData+']'+
			'}'+",";
		 });
		
		lineYJSON = "["+lineYJSON.substring(0, lineYJSON.length-1)+"]";
		if(shopLogs.length!=0){
			barYJSON += '{'+
				'"name": "进店总次数",'+
				'"type": "bar",'+
				'"stack": "次数",'+
				'"label": {'+
				'"normal":{'+
							'"show": "true",'+
							'"position": "insideBottom",' +
							'"textStyle": "{ color:#000 }"' +
						'}'+
					'},'+ 
				'"itemStyle": {'+
				'"normal":{'+
							'"color": "rgba(128, 128, 128, 0)"' +
						'}'+
					'},'+ 
				'"data": ['+totalYData+']'+
			'}'+",";
		}
		
		
		barYJSON = "["+barYJSON.substring(0, barYJSON.length-1)+"]";
		
		trendYJSON = "["+trendYJSON.substring(0, trendYJSON.length-1)+"]";
		
		drawBar("进店分析（次数）", titleArr, xData, barYJSON, "chart");
		
		drawLine("进店分析（次数）", titleArr, xData, lineYJSON, "line-chart");
		
		drawLine("进店分析（次数）", titleArr, xData, trendYJSON, "trend-chart");
		
		
	}
	
	function layuiShopLog(shopLogs, count) {//进店分析的分页
        layui.use(['laypage', 'layer'], function(){
            var laypage = layui.laypage
                ,layer = layui.layer;
            laypage.render({
                elem: 'layuiShopLog'
                ,count: count
                ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
                ,limit : 10
                ,limits: [10,20,30,40]
                ,jump: function(obj){
                	var pageS = obj.limit;//大小
                	var pageN = obj.curr;//当前页
                	var start = (pageN-1)*pageS;
                    var end = start + pageS;
                    if(end > count){
                         end = count;
                    }
                	var html = "";
                	$("#shopLogs tbody").html(html);
                    for(var s=start; s<end; s++){
            			html += "<tr><td>"+(parseInt(s+1))+"</td>";
            			html += "<td>"+shopLogs[s].shopName+"</td>";
            			html += "<td>"+getLocalTime(shopLogs[s].createTime)+"</td></tr>";
                    	 
                    }
            		$("#shopLogs tbody").html(html);
                }
            });
        });
	}
	
	function fillOrders(orders){
		if(orders==null){
			return;
		}
		
		var count = orders.length;
		layuiOrder(orders, count);
		var shopMap = {};
		for(var o in orders){
			var order = orders[o];
			var k = order.shopId;
			var v = order.shopName;
			shopMap[k] = v;
		}
		
		var dataMap = {};
		$("#totalMoney").html(0);
		$("#buyCount").html(0);
		var buyCount = 0;
		var totalMoney = 0;
		for(var r in orders){
			totalMoney += parseInt(orders[r].payment);
			buyCount += orders[r].commCount;
			var localTime = getLocalTime(orders[r].createTime);
			var timeArr = localTime.split(" ");
			var k = orders[r].shopId+"_"+$.trim(timeArr[0]);
			if(typeof(dataMap[k]) == "undefined"){
				dataMap[k] = parseInt(orders[r].payment);
			}else{
				dataMap[k] = parseInt(dataMap[k])+(parseInt(orders[r].payment));
			}
		}
		$("#totalMoney").html("￥"+mustTwoDecimal(totalMoney/100));
		$("#buyCount").html(buyCount);
		var date = $("#daterangepicker").val();
		var startTime = "";
		var endTime = "";
		if(date.indexOf("~")>-1){
			var dateArr = date.split("~");
			startTime = $.trim(dateArr[0]);
			endTime = $.trim(dateArr[1]);
		}
		var plusDay = getTime2Time(endTime, startTime)+1;
		var lineYJSON = "";
		var barYJSON = "";
		var trendYJSON = "";
		var xData = null;//得到x轴
		var titleArr = new Array();
		var totalYData = new Array();//总数
		for(var pd=0; pd<plusDay; pd++){
			totalYData[pd] = 0;//给数组赋初值
		}
		$.each(shopMap,function(key, values){
			titleArr.push(values);
			xData = new Array();//得到x轴
			var yData = new Array();//得到y轴
			for(var p=0; p<plusDay; p++){
				var d = addDate(startTime, p);
				if(p==0){
					d = startTime;
				}
				var tmpKey = key+"_"+d;
				xData.push(d);
				if(typeof(dataMap[tmpKey]) == "undefined"){
					yData.push(mustTwoDecimal(0/100));
					totalYData[p] = mustTwoDecimal(parseFloat(totalYData[p])+parseFloat(mustTwoDecimal(0/100)));
				}else{
					yData.push(mustTwoDecimal(dataMap[tmpKey]/100));
					totalYData[p] = mustTwoDecimal(parseFloat(totalYData[p])+parseFloat(mustTwoDecimal(dataMap[tmpKey]/100)));
				}
			}
			
			lineYJSON += '{'+
				'"name": "'+values+'",'+
				'"type": "line",'+
				'"data": ['+yData+']'+
			'}'+",";
			
			trendYJSON += '{'+
				'"name": "'+values+'",'+
				'"type": "line",'+
				'"smooth": "true",'+
				'"data": ['+yData+']'+
			'}'+",";
			
			barYJSON += '{'+
				'"name": "'+values+'",'+
				'"type": "bar",'+
				'"stack": "元",'+
				/* '"label": {'+
				'"normal":{'+
							'"show": "true",'+
							'"position": "left"' +
						'}'+
					'},'+  */
				'"data": ['+yData+']'+
			'}'+",";
		 });
		
		
		lineYJSON = "["+lineYJSON.substring(0, lineYJSON.length-1)+"]";
		
		if(orders.length!=0){
			barYJSON += '{'+
			'"name": "消费金额",'+
			'"type": "bar",'+
			'"stack": "元",'+
			'"label": {'+
			'"normal":{'+
						'"show": "true",'+
						'"position": "insideBottom",' +
						'"textStyle": "{ color:#000 }"' +
					'}'+
				'},'+ 
			'"itemStyle": {'+
			'"normal":{'+
							'"color": "rgba(128, 128, 128, 0)"' +
						'}'+
					'},'+ 
				'"data": ['+totalYData+']'+
			'}'+",";
		}
		
		barYJSON = "["+barYJSON.substring(0, barYJSON.length-1)+"]";
		
		trendYJSON = "["+trendYJSON.substring(0, trendYJSON.length-1)+"]";
		
		drawBar("购买分析（元）", titleArr, xData, barYJSON, "buyChart");
		
		drawLine("购买分析（元）", titleArr, xData, lineYJSON, "buyLine-chart");
		
		drawTrend("购买分析（元）", titleArr, xData, trendYJSON, "buyTrend-chart");
		
	}
	
	function layuiOrder(orders, count) {//进店分析的分页
        layui.use(['laypage', 'layer'], function(){
            var laypage = layui.laypage
                ,layer = layui.layer;
            laypage.render({
                elem: 'layuiOrder'
                ,count: count
                ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
                ,limit : 10
                ,limits: [10,20,30,40]
                ,jump: function(obj){
                	var pageS = obj.limit;//大小
                	var pageN = obj.curr;//当前页
                	var start = (pageN-1)*pageS;
                    var end = start + pageS;
                    if(end > count){
                         end = count;
                    }
                    var orderHtml = "";
            		$("#orders tbody").html(orderHtml);
            		for(var r=start; r<end; r++){
            			orderHtml += "<tr><td>"+(parseInt(r+1))+"</td>";
            			orderHtml += "<td>"+orders[r].shopName+"</td>";
            			orderHtml += "<td>"+orders[r].commName+"</td>";
            			orderHtml += "<td>"+mustTwoDecimal(orders[r].commPrice/100)+"</td>";
            			orderHtml += "<td>"+orders[r].commCount+"</td>";
            			orderHtml += "<td>"+mustTwoDecimal(orders[r].commCount*orders[r].commPrice/100)+"</td>";
            			orderHtml += "<td>"+getLocalTime(orders[r].createTime)+"</td></tr>";
            		}
            		$("#orders tbody").html(orderHtml);
            		
                }
            });
        });
	}
	
	function getTime2Time($time1, $time2){//日期作差
		var time1 = arguments[0], time2 = arguments[1];
		time1 = Date.parse(time1)/1000;
		time2 = Date.parse(time2)/1000;
		var time_ = time1 - time2;
		return (time_/(3600*24));
	}
	
	function addDate(date, days) {
        if (days == undefined || days == '') {
            days = 1;
        }
        var date = new Date(date);
        date.setDate(date.getDate() + days);
        var month = date.getMonth() + 1;
        var day = date.getDate();
        return date.getFullYear() + '-' + getFormatDate(month) + '-' + getFormatDate(day);
    }

    // 日期月份/天的显示，如果是1位数，则在前面加上'0'
    function getFormatDate(arg) {
        if (arg == undefined || arg == '') {
            return '';
        }
        var re = arg + '';
        if (re.length < 2) {
            re = '0' + re;
        }
        return re;
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
	
	function nowtime(){//将当前时间转换成yyyy-mm-dd格式
	    var mydate = new Date();
	    var time = new Array(3);
	    var year = mydate.getFullYear();
	    time[0] = year;
	    var month = mydate.getMonth()+1;
	    if(month < 10){
	    	month = "0" + month;
	    }
	    time[1] = month;
	    var day = mydate.getDate();
	    if(day < 10){
	    	day= "0" + day;
	    }
	    time[2] = day;
	    return time.join('-');
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
	
	function drawBar(titleText, titleArr, xData, barYJSON, $Id){//画柱状图
		if(xData!=null&&xData.length>7){
			var id = "."+$Id;
			$(id).css({
				width : xData.length*100,
				height : 480
			});
		}else{
			var id = "."+$Id;
			$(id).css({
				width : 970,
				height : 480
			});
		}
		var chart = echarts.init(document.getElementById($Id));
		chart.resize({
			width : "auto"
		});
		var option = option = {
				title: {
                    text: titleText
                },
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    legend: {
			        data: titleArr
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis : [
			        {
			            type : 'category',
			            data : xData
			        }
			    ],
			    yAxis : [
			        {
						min: 0,
						minInterval: 1,
			            type : 'value'
			        }
			    ],
			    series : JSON.parse(barYJSON)
			};
		chart.setOption(option, true);
	}
	
	function drawLine(titleText, titleArr, xData, lineYJSON, $Id){//画线性图
		if(xData!=null&&xData.length>7){
			var id = "."+$Id;
			$(id).css({
				width : xData.length*100,
				height : 480
			});
		}else{
			var id = "."+$Id;
			$(id).css({
				width : 970,
				height : 480
			});
		}
		var lineChart = echarts.init(document.getElementById($Id));
		lineChart.resize({
			width : "auto"
		});
		var option = {
	                title: {
	                    text: titleText
	                },
	                tooltip: {
	                    trigger: 'axis'
	                },
	                legend: {
	                    data: titleArr
	                },
	                grid: {
	                    left: '3%',
	                    right: '4%',
	                    bottom: '3%',
	                    containLabel: true
	                },
	                toolbox: {
	                    feature: {
	                        saveAsImage: {}
	                    }
	                },
	                xAxis: {
	                    type: 'category',
	                    boundaryGap: false,
	                    data: xData
	                },
	                yAxis: {
	                    type: 'value',
	                    min: 0,
                        minInterval: 1
	                },
	                series: JSON.parse(lineYJSON)
	            };

		lineChart.setOption(option, true);
	}
	
	function drawTrend(titleText, titleArr, xData, trendYJSON, $Id){//画趋势图
		if(xData!=null&&xData.length>7){
			var id = "."+$Id;
			$(id).css({
				width : xData.length*100,
				height : 480
			});
		}else{
			var id = "."+$Id;
			$(id).css({
				width : 970,
				height : 480
			});
		}
		var trendChart = echarts.init(document.getElementById($Id));
		trendChart.resize({
			width : "auto"
		});
		var option = {
	                title: {
	                    text: titleText
	                },
	                tooltip: {
	                    trigger: 'axis'
	                },
	                legend: {
	                    data: titleArr
	                },
	                grid: {
	                    left: '3%',
	                    right: '4%',
	                    bottom: '3%',
	                    containLabel: true
	                },
	                toolbox: {
	                    feature: {
	                        saveAsImage: {}
	                    }
	                },
	                xAxis: {
	                    type: 'category',
	                    boundaryGap: false,
	                    data: xData
	                },
	                yAxis: {
	                    type: 'value',
	                    min: 0,
                        minInterval: 1
	                },
	                series: JSON.parse(trendYJSON)
	            };

		trendChart.setOption(option, true);
	}
	</script>
</body>

</html>