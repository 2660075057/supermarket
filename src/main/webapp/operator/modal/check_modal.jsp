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
	<title>盘点管理</title>
    <%@ include file="../global/mylink.jsp" %>

</head>

<body class="layer-body">
	<!-- 弹出顾客框框-->
	<div class="layer-show">
		<div class="clearfix">
			<ul class="bar-count-list">
				<li><p class="form-control-static" id="code"></p></li>
				<li><p class="form-control-static" id="time"></p></li>
				<li><p class="form-control-static" id="shop"></p></li>
				<li><p class="form-control-static" id="oper"></p></li>
			</ul>
		</div>
		<form class="form-inline margin-top-gap">
			<select id="commType"  multiple="multiple" class="selectpicker margin-right-5" data-width="200">

			</select>
			<input type="text" id="queryVal" class="form-control bar-form-control margin-right-5" placeholder="请输入关键字" />
			<button type="button" id="query" class="btn btn-primary">查询</button>
		</form>
		<ul class="nav nav-tabs margin-top-gap">
			<li class="active">
				<a href="#view_data" data-toggle="tab">详细数据</a>
			</li>
			<li>
				<a href="#chart_analysis" role="tab" data-toggle="tab">图表分析</a>
			</li>
		</ul>
		<div class="tab-content margin-top-gap">
			<div class="tab-pane active" role="tabpanel" id="view_data">
				<div class="margin-top-gap">
					<div class="global-table">
						<div class="table-responsive">
							<table class="table table-bordered table-striped table-global">
								<thead>
									<tr>
										<th width="160">条码号</th>
										<th>商品名</th>
										<th>类型</th>
										<th>盘点前数</th>
										<th>盘点后数</th>
										<th>异常数</th>
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
			</div>
			<div class="tab-pane" role="tabpanel" id="chart_analysis">
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
	</div>
	<script src="../assets/echart/echarts.common.min.js"></script>
	<script>
		$(document).ready(function () {
            layui.use('layer', function(){
                var layer = layui.layer;
            });

		});
	</script>
    <script type="text/javascript">
        var datas;
        var counts = 0;
        var datajson1 = {};
        var inventoryId;
        var dataarr1 = new Array();
        var dataarr2 = new Array();
        var dataarr3 = new Array();
        var dataarr4 = new Array();
        var typeArr = {};
        $(function () {
            inventoryId = '<%=request.getParameter("inventoryId")%>';
            $('#code').text("盘点号："+inventoryId);
            var param = {};
            if(inventoryId!=null && inventoryId >0){
                param.inventoryId = inventoryId;
                selectList(param,1);
                setpage(datas,counts);

            }
            var str = "";
            for(var key in typeArr){
                str +="<option value=\""+key+"\">"+typeArr[key]+"</option>";
            }
            $("#commType").html(str);
            $('#commType').selectpicker('refresh');
            $('.filter-option').text("请选择类型");
            drawCharts(dataarr1,dataarr2,dataarr3,dataarr4);

            $("#commType").bind("change",function(){
                var commType = $("#commType").val();
                if(commType == null || commType.length <=0){
                    $('.filter-option').text("请选择类型");
                }
            });
        });
        $("#query").click(function(){
            var param = {};
            param.inventoryId = inventoryId;
            var commType = $('#commType').val();
            var queryVal = $('#queryVal').val();
            var typeIds = "";
            for(var j=0;j<commType.length;j++){
                typeIds += commType[j]+",";
            }
            if(typeIds.length >0){
                typeIds = typeIds.substring(0,typeIds.length-1);
                param.typeIds = typeIds;
            }
            if(queryVal != null && queryVal.length >0){
                param.commName = queryVal.replace(/(^\s*)|(\s*$)/g, "");
            }
            selectList(param,-1);
            setpage(datas,counts);
            drawCharts(dataarr1,dataarr2,dataarr3,dataarr4);
        });
        function selectList(param,set) {
            $.ajax({
                url: "${pageContext.request.contextPath}/operator/inventorydetail/inventoryDetailList",
                type: "POST",
                async: false,
                data: param,
                timeout: 8000
            }).done(function(data){
                datas = data;
                if(data != null && data != "") {
                    var rows = jQuery.parseJSON(data);
                    counts = rows.length;
                    datajson1 = {};
                    for(var i=0;i<rows.length;i++){
                        var row = rows[i];
                        var comm = row.commodityPageEntity;
                        var commtype = comm.commodityType;
                        if(!commtype){
                            datajson1[-1] = "其他";
                        }else{
                            if(set == 1){
                                typeArr[commtype.typeId] =commtype.typeName;
                            }
                            datajson1[commtype.typeId] = commtype.typeName;
                        }
                    }
                    dataarr1 = new Array();
                    dataarr2 = new Array();
                    dataarr3 = new Array();
                    dataarr4 = new Array();
                    for(var key in datajson1){
                        dataarr1.push(datajson1[key]);
                        //param.typeIds = "";
                        getInvenByTypeId(param,key,datajson1[key]);
                    }
                }
            });
        }
        function showdata(data,pageN,pageS) {
            var str = "";
            if(data != null && data != "[]"){
                var rows = jQuery.parseJSON(data);
                var inven = rows[0].inventoryPageEntity;
                var oper = inven.operator;
                var shop = inven.shop;
                $('#time').text("盘点时间："+getLocalTime(inven.inventoryTime));
                $('#shop').text("盘点站点："+shop.shopName);
                $('#oper').text("操作人："+oper.operName);
                var start = (pageN-1)*pageS;
                var end = start + pageS;
                if(end > counts){
                    end = counts;
                }
                for(var i=start;i<end;i++){
                    var row = rows[i];
                    var comm = row.commodityPageEntity;
                    var commtype = comm.commodityType;
                    var typeName = "其他";
                    if(commtype){
                        typeName
                    }
                    str +="<tr>";
                    str +="<td>"+comm.barcode+"</td><td>"+comm.commName+"</td><td>"+commtype.typeName+"</td>";
                    str +="<td>"+row.befor+"</td><td>"+row.after+"</td><td>"+row.error+"</td><td>"+row.sold+"</td>";
                    str +="</tr>";
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
                        showdata(data,obj.curr,obj.limit);
                    }
                });
            });
        }
        function getLocalTime(nS) {
            return new Date(parseInt(nS)).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
        }
        function drawCharts(data1,data2,data3,data4) {
            var lineChart = echarts.init(document.getElementById('line-chart'));
            option = {
                title: {
                    text: '盘点分析'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data:data1
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
                    data: ['盘点前数量','盘点后数量','异常数量','出售数量']
                },
                yAxis: {
                    type: 'value',
                    axisLine: {
                        show: false
                    },
                },
                series: data2
            };
            lineChart.setOption(option,true);
            var chartBar = echarts.init(document.getElementById('bar-chart'));

            optionBar = {
                title: {
                    text: '盘点分析'
                },
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
                        data: ['盘点前数量','盘点后数量','异常数量','出售数量'],
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
                series : data3
            };
            chartBar.setOption(optionBar,true);
            var smoothChart = echarts.init(document.getElementById('smooth-chart'));
            optionSmooth = {
                title: {
                    text: '盘点分析'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data:data1
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
                    data: ['盘点前数量','盘点后数量','异常数量','出售数量']
                },
                yAxis: {
                    type: 'value',
                    axisLine: {
                        show: false
                    },
                },
                series: data4
            };
            smoothChart.setOption(optionSmooth,true);
        }
        function getInvenByTypeId(param,typeid,typename) {
            var datajson2 = {};
            var datajson3 = {};
            var datajson4 = {};
            param.typeId = typeid;
            $.ajax({
                url: "${pageContext.request.contextPath}/operator/inventorydetail/inventoryDetailListByTypeId",
                type: "POST",
                async: false,
                data: param,
                timeout: 8000
            }).done(function(data){
                if(data != null && data != "[]") {
                    var rows = jQuery.parseJSON(data);
                    datajson2["name"] = typename;
                    datajson2["type"] = "line";
                    datajson3["name"] = typename;
                    datajson3["type"] = "bar";

                    datajson4["name"] = typename;
                    datajson4["type"] = "line";
                    datajson4["smooth"] = true;
                    var arr = new Array();
                    var befor = 0;
                    var after = 0;
                    var error = 0;
                    var sold = 0;
                    for(var i=0;i<rows.length;i++){
                        var row = rows[i];
                        befor += parseInt(row.befor);
                        after += parseInt(row.after);
                        error += parseInt(row.error);
                        sold += parseInt(row.sold);

                    }
                    arr = [befor,after,error,sold];
                    datajson2["data"] = arr;
                    datajson3["data"] = arr;
                    datajson4["data"] = arr;
                    dataarr2.push(datajson2);
                    dataarr3.push(datajson3);
                    dataarr4.push(datajson4);
                }
            });
        }
    </script>
</body>

</html>