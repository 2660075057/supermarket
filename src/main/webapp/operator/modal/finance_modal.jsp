<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
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
	<title>顾客管理</title>
    <%@ include file="../global/mylink.jsp" %>

</head>

<body class="layer-body">
	<!-- 弹出顾客框框-->
	<div class="layer-show">
        <div class="clearfix">
            <ul class="bar-count-list">
                <li><p class="form-control-static" id="payments"></p></li>
                <li><p class="form-control-static" id="grossProfits"></p></li>
               <!--  <li><p class="form-control-static" id="storeNum"></p></li> -->
            </ul>
        </div>
        <form class="form-inline margin-top-gap">
            <select id="commType" multiple="multiple" class="selectpicker margin-right-5" data-width="200">

            </select>
            <input type="text" id="queryVal" class="form-control bar-form-control margin-right-5" placeholder="请输入关键字" />
            <button type="button" id="query" class="btn btn-primary">查询</button>
            <button type="button" id="export" class="btn btn-primary">导出Excel</button>
        </form>

		<ul class="nav nav-tabs margin-top-gap">
			<li class="active">
				<a href="#view_table" data-toggle="tab">详细数据</a>
			</li>
			<li>
				<a href="#view_chart" role="tab" data-toggle="tab">图表统计</a>
			</li>
		</ul>
		<div class="tab-content margin-top-gap">
			<div class="tab-pane active" role="tabpanel" id="view_table">
				<div class="global-table">
					<div class="table-responsive">
						<table class="table table-bordered table-striped table-global">
							<thead>
								<tr>
									<th width="160">条码号</th>
									<th>商品名</th>
									<th>类型</th>
									<th>销售额</th>
									<th>毛利</th>
									<th>售出数</th>
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
				
			<div class="tab-pane" role="tabpanel" id="view_chart">
				<div class="btn-group tabs-theme" role="group" data-target="#tab_view_chart">
					<button type="button" class="btn btn-default btn-primary">柱状图</button>
					<button type="button" class="btn btn-default">折线图</button>
                    <button type="button" class="btn btn-default">趋势图</button>
				</div>
				<div class="view_analysis_table margin-top-gap">
					<div id="tab_view_chart">
						<div class="panel-theme active">
							<div id="bar-chart" style="width: 970px;height:290px;">
							</div>
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
	<script src="../assets/echart/echarts.common.min.js"></script>

    <script type="text/javascript">
        var shopid;
        var timeStr;
        var dataarr1 = ['销售额','毛利','出售数'];
        var linearr = new Array();
        var dataarr2 = new Array();
        var dataarr3 = new Array();
        var dataarr4 = new Array();
        var typeArr = {};
        $(function() {
            shopid = '<%=request.getParameter("shopid")%>';
            timeStr = '<%=request.getParameter("timeStr")%>';
            var p = "销售额：¥"+'<%=request.getParameter("payments")%>';
            var g = "毛利：¥"+'<%=request.getParameter("grossProfits")%>';
            <%-- var s = "进店数："+'<%=request.getParameter("storeNum")%>'+"人"; --%>
            $('#payments').text(p);
            $('#grossProfits').text(g);
            /* $('#storeNum').text(s); */
            if(shopid != null && shopid.length >0){
                var param={};
                param["shopId"] = shopid;
                var timearr = timeStr.split("~");
                var startTime = timearr[0];
                var endTime = timearr[1];
                param["startTime"] = startTime;
                param["endTime"] = endTime;
                setpage(param,1);
                selectAll(param);
                showTuBiao(dataarr1,dataarr3,linearr,dataarr2,dataarr4);
            }
            $("#commType").bind("change",function(){
                var commType = $("#commType").val();
                if(commType == null || commType.length <=0){
                    $('.filter-option').text("请选择类型");
                }
            });
        });
        $("#query").click(function(){
            var commType = $('#commType').val();
            var queryVal = $('#queryVal').val();
            var param = {};
            var typeIds = "";
            for(var j=0;j<commType.length;j++){
                typeIds += commType[j]+",";
            }
            if(typeIds.length >0){
                typeIds = typeIds.substring(0,typeIds.length-1);
                param["typeIds"] = typeIds;
            }
            param["shopId"] = shopid;
            var timearr = timeStr.split("~");
            var startTime = timearr[0];
            var endTime = timearr[1];
            param["startTime"] = startTime;
            param["endTime"] = endTime;
            if(queryVal != null && queryVal.length >0){
                param["commName"] = queryVal.replace(/(^\s*)|(\s*$)/g, "");
            }
            setpage(param,-1);
            selectAll(param);
            showTuBiao(dataarr1,dataarr3,linearr,dataarr2,dataarr4);
        });
        $("#export").click(function(){
            var timeArr = timeStr.split("~");
            var param = "shopId=" + shopid + "&startDate=" + timeArr[0] + "&endDate=" + timeArr[1];
            window.open('${ctx}/operator/finance/exportFinanceStatisticsDetail?'+param);
        });
        function selectList(page,param,set) {
            $.ajax({
                url: "${pageContext.request.contextPath}/operator/finance/financeStatisticsDetail",
                type: "POST",
                async: false,
                data: {"page":JSON.stringify(page),"req":JSON.stringify(param)},
                success: function (data) {
                    var str = "";
                    if(data!=null && data!="[]"){
                        var rows = jQuery.parseJSON(data);
                        for(var i=0;i<rows.length;i++){
                            var row = rows[i];
                            if(set == 1){
                                typeArr[row.typeId] =row.typeName;
                                setCommType();
                            }
                            str +="<tr>";
                            str +="<td>"+row.barcode+"</td><td>"+row.commName+"</td><td>"+row.typeName+"</td>";
                            str +="<td>"+row.payments/100+"元</td><td>"+row.grossProfits/100+"元</td><td>"+row.sellNum+"</td>";
                            str +="</tr>";
                        }
                    }
                    $('#datalist').html(str);
                }
            });
        }
        function getcount() {
            var commType = $('#commType').val();
            var queryVal = $('#queryVal').val();
            var param = {};
            var typeIds = "";
            for(var j=0;j<commType.length;j++){
                typeIds += commType[j]+",";
            }
            if(typeIds.length >0){
                typeIds = typeIds.substring(0,typeIds.length-1);
                param["typeIds"] = typeIds;
            }
            param["shopId"] = shopid;
            var timearr = timeStr.split("~");
            var startTime = timearr[0];
            var endTime = timearr[1];
            param["startTime"] = startTime;
            param["endTime"] = endTime;
            if(queryVal != null && queryVal.length >0){
                param["commName"] = queryVal.replace(/(^\s*)|(\s*$)/g, "");
            }
            var count=0;
            $.ajax({
                url: "${pageContext.request.contextPath}/operator/finance/countStatisticsDetail",
                type: "POST",
                async: false,
                data: param,
                success: function (data) {
                    count = parseInt(data);
                }
            });
            return count;
        }
        function setpage(param,set) {
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
                        var page={};
                        page["pageSize"]=obj.limit;
                        page["pageCurrent"]=obj.curr;
                        selectList(page,param,set);
                    }
                });
            });
        }
        function selectAll(param) {
            linearr = new Array();
            dataarr2 = new Array();
            dataarr3 = new Array();
            dataarr4 = new Array();
            $.ajax({
                url: "${pageContext.request.contextPath}/operator/finance/financeStatisticsDetail",
                type: "POST",
                async: false,
                data: {"page":"{}","req":JSON.stringify(param)},
                success: function (data) {
                    if(data != null && data != "[]"){
                        var rows = jQuery.parseJSON(data);
                        var typeline = {};
                        for(var i=0;i<rows.length;i++){
                            var row = rows[i];
                            typeline[row.typeId] = row.typeName;
                        }
                        var ps = 0;
                        var gs = 0;
                        var ss = 0;
                        for(var key in typeline){
                            var datajson1 = {};
                            var datajson2 = {};
                            var datajson3 = {};
                            linearr.push(typeline[key]);
                            var p = 0;
                            var g = 0;
                            var s = 0;
                            var arr = new Array();
                            datajson1["name"] = typeline[key];
                            datajson1["type"] = "line";
                            datajson3["name"] = typeline[key];
                            datajson3["type"] = "line";
                            datajson3["smooth"] = true;

                            datajson2["name"] = typeline[key];
                            datajson2["type"] = "bar";
                            datajson2["stack"] = "总量";
                            for(var j=0;j<rows.length;j++){
                                var row = rows[j];
                                if(key == row.typeId){
                                    p += row.payments/100;
                                    g += row.grossProfits/100;
                                    s += row.sellNum;
                                }
                            }
                            arr = [p,g,s];
                            ps += p;
                            gs += g;
                            ss += s;
                            datajson1["data"] = arr;
                            datajson2["data"] = arr;
                            datajson3["data"] = arr;
                            dataarr2.push(datajson1);
                            dataarr3.push(datajson2);
                            dataarr4.push(datajson3);
                        }
                        var json = {};
                        json["name"] = "合计总量";
                        json["type"] = "bar";
                        json["stack"] = "总量";

                        var nor = {};
                        nor["color"] = "rgba(128, 128, 128, 0)";
                        var n = {};
                        n["normal"] = nor;
                        json["itemStyle"] = n;
                        json["data"] = [ps, gs, ss];
                        dataarr3.push(json);

                    }
                }
            });
        }
        function showTuBiao(data1,data2,data3,data4,data5) {
            var chart = echarts.init(document.getElementById('bar-chart'));

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
        function setCommType() {
            var str = "";
            for(var key in typeArr){
                str +="<option value=\""+key+"\">"+typeArr[key]+"</option>";
            }
            $("#commType").html(str);
            $('#commType').selectpicker('refresh');
            $('.filter-option').text("请选择类型");
        }
    </script>
</body>

</html>