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
<title>财经管理</title>
    <%@ include file="global/mylink.jsp" %>
</head>
<body>
<%@ include file="global/header.jsp" %>
<div class="main-content">
    <jsp:include page="menu.jsp" flush="true" >
        <jsp:param name="type" value="finance_list"  />
    </jsp:include>
	<div class="main">
		<div class="bread">
			<a href="#">首页</a> &gt; 财经管理
		</div>
		<div class="main-container">
			<div class="page-bar clearfix">
				<div class="pull-left">
					<div class="page-title">
						<span class="line"></span>
						财经管理
					</div>
				</div>
				<div class="pull-right">
					<form class="form-inline">
							<div class="form-control-hasIcon w250 margin-right-5">
								<i class="right-icon glyphicon glyphicon-calendar"></i>
								<input type="text" class="form-control margin-right-10" placeholder="请选择起始时间" value="" id="daterangepicker" />
							</div>
						<input type="text" class="form-control bar-form-control margin-right-5" id="shopName" placeholder="请输入关键字" />
						<button type="button" id="query" class="btn btn-primary">查询</button>
					</form>
				</div>
			</div>
			<div class="margin-top-gap">
				<ul class="nav nav-tabs">
					<li class="active">
						<a href="#view_table" data-toggle="tab">详细数据</a>
					</li>
					<li>
						<a href="#sale_chart" role="tab" data-toggle="tab">图表统计</a>
					</li>
				</ul>
				<div class="tab-content margin-top-gap"  id="main">
					<div class="tab-pane active" role="tabpanel" id="view_table">
						<div class="global-table margin-top-gap">
							<div class="table-responsive">
								<table class="table table-bordered table-striped table-global">
									<thead>
										<tr>	
											<th width="100">序号</th>							
											<th>站点名</th>
											<th>销售额</th>
											<th>毛利</th>
											<!-- <th>进店数</th> -->
											<th>客单量</th>
											<th>客单价</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody id="datalist">

									</tbody>
								</table>
							</div>
						</div>
                        <div class="pager-md clearfix margin-top-gap">
                            <div class="pull-right">
                                <div id="layuipage"></div>
                            </div>
                        </div>
					</div>
					<div class="tab-pane" role="tabpanel" id="sale_chart">
						<div class="btn-group tabs-theme-finance" role="group" data-target="#tab_view_chart">
							<button type="button" class="btn btn-default btn-primary">柱状图</button>
							<button type="button" class="btn btn-default">折线图</button>
                            <button type="button" class="btn btn-default">趋势图</button>
						</div>
						<div id="tab_view_chart">
							<div class="panel-theme active">
								<div id="chart" style="width:100%;height:500px"></div>
							</div>
                            <div class="panel-theme">
                                <div id="line-chart" style="width: 970px;height:290px;">
                                </div>
                            </div>
                            <div class="panel-theme">
                                <div id="smooth-chart" style="width: 970px;height:290px;">
                                </div>
                            </div>
						</div>
					</div>
				</div>
			</div>
			
		</div>
	</div>
</div>
<script src="assets/echart/echarts.common.min.js"></script>
<script>
$(document).ready(function(){
    layui.use('layer', function(){
        var layer = layui.layer;
    });
    var myDate = new Date();
    var yaer = myDate.getFullYear();//年份
    var month = myDate.getMonth();//月份
    var day= myDate.getDate();//几号
        month=month + 1;
        myDate.setDate(0);
    var startData = yaer +"-" + month + "-" + day;
    var month1 = month ;
    var day1 =(day+7);
    if (day1>myDate.getDate()) {
    	month1 = month1+1;
    	if (month>12) {
    		yaer = yaer + 1;
    		month1 = 1; 
		}
    	day1 = 1;
	}
    var endDate = yaer +"-" + month1 + "-" + day1;
    
	$("#daterangepicker").daterangepicker({
		"startDate": startData,
		"endDate": endDate,
		"applyClass": "btn-danger",
		"opens": "left",
		"locale": $.data_picker_locale,
	},
		function (start, end, label) {
			/*console.log(start);
			console.log(end);*/
		}
	);

	$("#main").height($(window).height() - 260)
	$(window).on("resize",function(){
        $("#main").height($(window).height() - 260)
	});

    var chart = echarts.init(document.getElementById('chart'));

    option = {
        color: ['#7dcab0','#80b543',"#569bac","#6e8bbf"],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
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
                data : ['销售额','毛利','进店数','客单量','客单价'],
                axisTick: {
                    alignWithLabel: true
                },
                axisLine: {
                    show: false
                }
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLine: {
                    show: false
                }
            }
        ],
        series : [
            {
                name:'series1',
                type:'bar',
                data:[10, 52, 200, 334, 390]
            },
            {
                name:'series2',
                type:'bar',
                data:[4, 34, 234, 333, 234,]
            },
            {
                name:'series3',
                type:'bar',
                data:[10, 52, 200, 330, 220]
            },
            {
                name:'series4',
                type:'bar',
                data:[234, 333, 234, 330, 100]
            }
        ]
    };
    chart.setOption(option);

	$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
		if($(e.target).attr("href") == "#sale_chart"){
			$(chart).resize()
		}
	});
	$(".tabs-theme-finance .btn").on("click",function(){
		var target = $(this).parent().data("target");
		$(this).addClass("btn-primary").siblings().removeClass("btn-primary");
		$(target).find(".panel-theme").hide().eq($(this).index()).show();
		$(chart).resize()
	})
})
</script>
<script type="text/javascript">
    var datas;
    var counts = 0;
    var dataarr1 = ['销售额','毛利',/* '进店数', */'客单量','客单价'];
    var linearr = new Array();
    var dataarr2 = new Array();
    var dataarr3 = new Array();
    var dataarr4 = new Array();
    var timeStr="";
    $(function() {
        var param={};
        timeStr = $('#daterangepicker').val();
        var timearr = $('#daterangepicker').val().split("~");
        var startTime = timearr[0];
        var endTime = timearr[1];
        param["startTime"] = startTime;
        param["endTime"] = endTime;
        selectList(param);
        setpage(datas,counts);

    });
    $("#query").click(function(){
        timeStr = $('#daterangepicker').val();
        var timearr = $('#daterangepicker').val().split("~");
        var startTime = timearr[0];
        var endTime = timearr[1];
        var shopName = $('#shopName').val();
        var param={};
        if(shopName != null && shopName.length >0){
            param["shopName"]=shopName.replace(/(^\s*)|(\s*$)/g, "");
        }
        param["startTime"] = startTime;
        param["endTime"] = endTime;
        selectList(param);
        setpage(datas,counts);
    });
    function selectList(param) {
        dataarr2 = new Array();
        dataarr3 = new Array();
        linearr = new Array();
        dataarr4 = new Array();
        $.ajax({
            url: "../operator/finance/financeStatistics",
            type: "POST",
            async: false,
            data: param,
            success: function (data) {
            	console.log(data)
                datas = data;
                if(data != null && data != "") {
                    var rows = jQuery.parseJSON(data);
                    counts = rows.length;
                    var p = 0;
                    var g = 0;
                   /*  var s = 0; */
                    var o = 0;
                    var po = 0;
                    for(var i=0;i<rows.length;i++){
                        var row = rows[i];
                        linearr.push(row.shopName);
                        var datajson1 = {};
                        var datajson2 = {};
                        var datajson3 = {}
                        var arr = new Array();
                        arr = [row.payments/100,row.grossProfits/100/* ,row.storeNum */,row.orderNum,row.payments/row.orderNum/100],
                        datajson1["name"] = row.shopName;
                        datajson1["type"] = "bar";
                        datajson1["stack"] = "总量";

                        datajson1["data"] = arr;
                        datajson2["name"] = row.shopName;
                        datajson2["type"] = "line";
                        datajson2["data"] = arr;
                        datajson3["name"] = row.shopName;
                        datajson3["type"] = "line";
                        datajson3["data"] = arr;
                        dataarr2.push(datajson1);
                        dataarr3.push(datajson2);
                        datajson3["smooth"] = true;
                        dataarr4.push(datajson3);
                        p += row.payments/100;
                        g += row.grossProfits/100;
                        /* s += row.storeNum; */
                        o += row.orderNum;
                        po += row.payments/row.orderNum/100;
                    }
                    var json = {};
                    json["name"] = "合计总量";
                    json["type"] = "bar";
                    json["stack"] = "总量";

                   /* var normal = {};
                    normal["offset"] = ['50','80'];
                    normal["show"] = true;
                    normal["position"] = "position";
                    normal["formatter"] = "{c}";
                    var textStyle = {};
                    textStyle["color"] = "#000";
                    normal["textStyle"] = textStyle;
                    var nm = {};
                    nm["normal"] = normal;
                    json["label"] = nm;*/
                    var nor = {};
                    nor["color"] = "rgba(128, 128, 128, 0)";
                    var n = {};
                    n["normal"] = nor;
                    json["itemStyle"] = n;
                    json["data"] = [p, g, o, po];
                    /* json["data"] = [p, g, s, o, po]; */
                    dataarr2.push(json);
                }
            }
        });
    }
    function showdata(data,pageN,pageS, currPage, pageSize) {
    	var startIndex = 0;
		if(currPage>0){
			startIndex = (currPage-1)*pageSize;
		}
        var str = "";
        if(data != null && data != "[]"){
            var rows = jQuery.parseJSON(data);
            var start = (pageN-1)*pageS;
            var end = start + pageS;
            if(end > counts){
                end = counts;
            }
            for(var i=start;i<end;i++){
            	var number = parseInt(startIndex)+parseInt(i+1);
                var row = rows[i];
                str +="<tr>";
                str +="<td>"+number+"</td><td>"+row.shopName+"</td><td>"+row.payments/100+"元</td><td>"+row.grossProfits/100+"元</td>";
                str +="<td>"+row.orderNum+"</td><td>"+row.payments/row.orderNum/100+"元</td>";
                str +="<td><button onclick=\"javascript:showDetails('"+row.shopId+"','"+timeStr+"',"+row.payments/100+","+row.grossProfits/100+");\" class=\"layui-btn\">详情</button>";
                /* str +="<td>"+row.storeNum+"</td><td>"+row.orderNum+"</td><td>"+row.payments/row.orderNum/100+"元</td>";
                str +="<td><button onclick=\"javascript:showDetails('"+row.shopId+"','"+timeStr+"',"+row.payments/100+","+row.grossProfits/100+","+row.storeNum+");\" class=\"layui-btn\">详情</button>";*/
                str +="</td></tr>";
            }
        }
        $('#datalist').html(str);
    }
    function setpage(data,count) {
        layui.use(['laypage', 'layer'], function(){
            var laypage = layui.laypage
                ,layer = layui.layer;
            laypage.render({
                elem: 'layuipage'
                ,count: count
                ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
                ,limit : 10
                ,limits: [10,20,30,40]
                ,jump: function(obj){
                	var currPage = obj.curr;
	            	var pageSize = obj.limit;
                    showdata(data,obj.curr,obj.limit, currPage, pageSize);
                    showTuBiao(dataarr1,dataarr2,linearr,dataarr3,dataarr4);
                }
            });
        });
    }
    function showDetails(shopid,timeStr,p,g/* ,s */){
        layer.open({
            type: 2,
            area: ['1000px', '580px'],
            fixed: false, //不固定
            maxmin: true,
            content: 'modal/finance_modal.jsp?shopid='+shopid+'&timeStr='+timeStr+'&payments='+p+'&grossProfits='+g/* +'&storeNum='+s */
        });
    }
    function showTuBiao(data1,data2,data3,data4,data5) {
        var chart = echarts.init(document.getElementById('chart'));
        option = {
            color: ['#7dcab0','#80b543',"#569bac","#6e8bbf"],
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
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
                    data : data1,
                    axisTick: {
                        alignWithLabel: true
                    },
                    axisLine: {
                        show: false
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    axisLine: {
                        show: false
                    }
                }
            ],
            series : data2
        };
        chart.setOption(option,true);

        var lineChart = echarts.init(document.getElementById('line-chart'));
        optionLine = {
            title: {
                text: '财经分析'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:data3
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
                data: data1
            },
            yAxis: {
                type: 'value',
                axisLine: {
                    show: false
                },
            },
            series: data4
        };
        lineChart.setOption(optionLine,true);

        var smoothChart = echarts.init(document.getElementById('smooth-chart'));
        optionSmooth = {
            title: {
                text: '财经分析'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data:data3
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
                data: data1
            },
            yAxis: {
                type: 'value',
                axisLine: {
                    show: false
                },
            },
            series: data5
        };
        smoothChart.setOption(optionSmooth,true);
    }
</script>
</body>
</html>