<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
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
    <title>标签导入详情</title>
    <%@ include file="../global/mylink.jsp"%>
    <style>
        [v-cloak] {
            display: none;
        }
    </style>
</head>

<body class="layer-body">
<div class="layer-show" id="app" v-cloak>
    <div class="form-horizontal">
        <div class="form-group">
            <label class="col-md-3">操作员:{{logData.operatorName}}</label>
            <label class="col-md-3">操作时间:{{formatDate(logData.createTime)}}</label>
            <div class="col-md-3 pull-right">
                <button type="submit" class="btn btn-primary" @click="downLoad">下载EPC列表</button>
            </div>
        </div>
        <div class="form-group">
            <div class="col-md-8">
                <table class="table table-bordered ">
                    <thead>
                    <tr>
                        <th width="70">序号</th>
                        <th>商品名称</th>
                        <th>条码号</th>
                        <th width="100">数量</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(item,index) in tableData">
                        <td>{{index+1}}</td>
                        <td>{{item.commName}}</td>
                        <td>{{item.barcode}}</td>
                        <td>{{item.num}}</td>
                    </tr>
                    </tbody>
                </table>
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
</script>
<script>
    var vm = new Vue({
        el: "#app",
        data: {
            logData: {}
        },
        methods: {
            formatDate: function (date) {
                return new Date(date).format("yyyy-MM-dd hh:mm:ss");
            },
            getData: function () {
                var that = this;
                $.get('${ctx}/operator/operatorLog/logInfo?id=' +${param.id}, {}, function (data) {
                    if (data.code == 0 && data.data) {
                        that.logData = data.data;
                    }
                }, 'json');
            },
            downLoad:function () {
                if(this.logData.id){
                    window.open("${ctx}/operator/electag/downLoad?handle="+this.logData.id);
                }
            }
        },
        computed: {
            tableData: function () {
                if (this.logData.data) {
                    var commData = JSON.parse(this.logData.data);
                    var showData = [];
                    for (var i = 0; i < commData.length; i++) {
                        var comm = commData[i];
                        var add = true;
                        for (var j = 0; j < showData.length; j++) {
                            if (showData[j].commId == comm.commId) {
                                showData[j].num++;
                                add = false;
                                break;
                            }
                        }
                        if (add) {
                            var temp = JSON.parse(JSON.stringify(comm));
                            temp.num = 1;
                            showData.push(temp);
                        }
                    }

                    return showData;
                }
            }
        },
        mounted: function () {
            this.getData();
        }
    });
</script>
</html>