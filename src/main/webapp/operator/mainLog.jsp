<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ilasFun" uri="/WEB-INF/permissionFun.tld" %>
<%@taglib prefix="ilas" uri="/WEB-INF/permissionTag.tld" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<!--[if IE 8]><html class="ie8 oldie"><![endif]-->
<!--[if IE 9]><html class="ie9 oldie"><![endif]-->
<!--[if gt IE 9]><!--><html><!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>标签导入日志</title>
    <%@ include file="global/mylink.jsp" %>
    <style>
        [v-cloak] {
            display: none;
        }
    </style>
</head>
<body>
<%@ include file="global/header.jsp" %>
<div class="main-content">
    <jsp:include page="menu.jsp" flush="true" >
        <jsp:param name="type" value="operator_log"  />
    </jsp:include>
    <div class="main" id="app" v-cloak>
        <div class="bread">
            <a href="#">首页</a> &gt; <a href="#">系统设置</a> &gt; 标签导入日志
        </div>
        <div class="main-container">
            <div class="page-bar clearfix">
                <div class="pull-left">
                    <div class="page-title">
                        <span class="line"></span>
                        标签导入日志
                    </div>
                </div>
                <div class="pull-right">
                    <form class="form-inline">
                        <div class="form-control-hasIcon w250 margin-right-5">
                            <i class="right-icon glyphicon glyphicon-calendar"></i>
                            <input type="text" class="form-control margin-right-10" placeholder="请选择起止时间" id="daterangepicker"/>
                        </div>
                        <button type="button" class="btn btn-primary margin-right-5" @click="search">查询</button>
                    </form>
                </div>
            </div>
            <div style="margin-top: 10px;">
                <table class="table table-bordered table-striped table-global">
                    <thead>
                    <tr>
                        <th width="100">序号</th>
                        <th>操作员</th>
                        <th>日志代码</th>
                        <th>操作类型</th>
                        <th>操作时间</th>
                        <th width="120"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(item,index) in tableData">
                        <td>{{index+1}}</td>
                        <td>{{item.operatorName}}</td>
                        <td>{{item.cmd}}</td>
                        <td>{{cmdAnalysis[item.cmd]}}</td>
                        <td>{{formatDate(item.createTime)}}</td>
                        <td>
                            <button type="submit" class="layui-btn" v-if="item.cmd == '1201'" @click="logDetail(item)">详情</button>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="pager-md clearfix margin-top-gap">
                    <div class="pull-right">
                        <div id="layuipage"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${ctx}/plugins/vue/vue.js"></script>
<script>
    Date.prototype.format = function(fmt) {
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        for(var k in o) {
            if(new RegExp("("+ k +")").test(fmt)){
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
        }
        return fmt;
    };
    function getBeforeDate(n) {
        var d = new Date();
        var year = d.getFullYear();
        var mon = d.getMonth() + 1;
        var day = d.getDate();
        if(day <= n) {
            if(mon > 1) {
                mon = mon - 1;
            } else {
                year = year - 1;
                mon = 12;
            }
        }
        d.setDate(d.getDate() - n);
        year = d.getFullYear();
        mon = d.getMonth() + 1;
        day = d.getDate();
        s = year + "-" + (mon < 10 ? ('0' + mon) : mon) + "-" + (day < 10 ? ('0' + day) : day);
        return s;
    }
</script>
<script>
    var vm = new Vue({
        el: "#app",
        data: {
            selectParam: {
                cmd: 1201
            },
            pageCurrent:1,
            pageSize:10,
            count:0,
            tableData:[],
            cmdAnalysis:{
                "1201":"标签导入"
            },
            cmdDetailAnalysis:{
                "1201":"tagImport"
            }
        },
        methods: {
            formatDate: function (date) {
                return new Date(date).format("yyyy-MM-dd hh:mm:ss");
            },
            search:function () {
                this.initPage();
            },
            getData:function(){
                var that = this;
                var param = {};
                for(var key in this.selectParam){
                    param[key] = this.selectParam[key];
                }
                param['pageCurrent'] = this.pageCurrent;
                param['pageSize'] = this.pageSize;
                $.get("${ctx}/operator/operatorLog/list",param,function (data) {
                    if(data.code == 0){
                        that.tableData = data.data;
                    }
                },'json');
            },
            initPage:function () {
                var that = this;
                var timearr = $('#daterangepicker').val().split("~");
                this.selectParam.start_date = timearr[0].trim();
                this.selectParam.end_date = timearr[1].trim();
                $.get("${ctx}/operator/operatorLog/count",this.selectParam,function (data) {
                    if(data.code == 0){
                        that.pageCurrent = 1;
                        that.count = data.data;
                        that.getData();
                        layui.laypage.render({
                            elem: 'layuipage'
                            ,count: data.data
                            ,layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
                            ,limit : 10
                            ,limits: [10, 20, 30]
                            ,jump: function(obj){
                                var currPage = obj.curr;
                                var pageSize = obj.limit;
                                that.pageCurrent = currPage;
                                that.pageSize = pageSize;
                                that.getData();
                            }
                        });
                    }
                },'json');
            },
            logDetail:function (item) {
                var detail = this.cmdDetailAnalysis[item.cmd];
                var url = '${ctx}/operator/operatorLog/detail/'+detail+'?id='+item.id;
                layer.open({
                    title:'日志详情',
                    type: 2,
                    area: ['800px', '400px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: url
                });
            }
        },
        mounted: function () {
            var that = this;
            layui.use(['laypage', 'layer'],function(){
                var laypage = layui.laypage
                    ,layer = layui.layer;
                that.initPage();
            });
            $("#daterangepicker").daterangepicker({
                "startDate": getBeforeDate(6),
                "endDate": new Date(),
                "applyClass": "btn-danger",
                "opens": "left",
                "locale": $.data_picker_locale
            });
        }
    });
</script>
</html>