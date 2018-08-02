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
    <title>选择商品</title>
    <%@ include file="global/mylink.jsp"%>
    <style>
        [v-cloak] {
            display: none;
        }
    </style>
</head>

<body class="layer-body">
<div class="layer-show" id="app" v-cloak>
    <div class="form-horizontal" id="form-user">
        <div class="form-group">
            <label class="col-md-3 control-label">自定义代码</label>
            <div class="col-md-8">
                <input type="text" class="form-control" v-model="userData" id="userData" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label"></label>
            <div class="col-md-8">
                <div class="col-items col-items-2 clearfix">
                    <div class="item" style="width: 30%">
                        <select class="selectpicker" data-live-search="true" title="商品类型" id="commType">
                            <c:forEach var="item" items="${typelist}">
                                <option value="${item.typeId}">${item.typeName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="item item-separator"></div>
                    <div class="item" style="width: 30%">
                        <select class="selectpicker" data-live-search="true" title="商品" id="comm">
                            <option v-bind:value="index" v-for="(item,index) in commodityData">
                                {{item.commName}}
                            </option>
                        </select>
                    </div>
                    <div class="item item-separator"></div>
                    <div class="item" style="width: 30%">
                        <input type="text" class="form-control" placeholder="数量" id="commNum" v-model="num"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label"></label>
            <div class="col-md-8">
                <button type="submit" class="btn btn-primary" @click="createEpc">生成列表</button>
                <button type="submit" class="btn" @click="addCommodity" id="addCommodity">添加商品</button>
                <span id="resultMessage" style="color: red;"></span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label"></label>
            <div class="col-md-8">
                <table class="table table-bordered ">
                    <thead>
                    <tr>
                        <th width="70">序号</th>
                        <th>商品名称</th>
                        <th>条码号</th>
                        <th width="100">数量</th>
                        <th width="100"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(item,index) in tableData">
                        <td>{{index+1}}</td>
                        <td>{{item.commName}}</td>
                        <td>{{item.barcode}}</td>
                        <td>{{item.num}}</td>
                        <td>
                            <button class="btn btn-small" @click="remove(index)">移除</button>
                        </td>
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
    var vm = new Vue({
        el:"#app",
        data:{
            userData:'',
            commodityType:null,
            selectCommodity:null,
            num:'',
            commodityData:[],
            tableData:[]
        },
        methods:{
            addCommodity:function () {
                if (!/^[0-9]+$/.test(this.num) || this.num < 1) {
                    layer.tips('请输入正确的数量', '#commNum', {
                        tips: [1, '#FF5722'],
                        time: 3000
                    });
                    return;
                }
                if(this.selectCommodity == null){
                    layer.tips('请添加商品', '#comm', {
                        tips: [1, '#FF5722'],
                        time: 3000
                    });
                    return ;
                }
                var b = true;
                for (var index in this.tableData) {
                    if (this.tableData[index].commId == this.selectCommodity.commId) {
                        this.tableData[index].num += parseInt(this.num);
                        b = false;
                        break;
                    }
                }
                if (b) {
                    var temp = JSON.parse(JSON.stringify(this.selectCommodity));
                    temp.num = parseInt(this.num);
                    this.tableData.push(temp);
                }
            },
            remove:function (index) {
                this.tableData.splice(index,1);
            },
            createEpc:function () {
                var that = this;
                if(!/^[0-9A-Fa-f]*$/.test(this.userData) || this.userData.length != 6){
                    layer.tips('请6位的十六进制数据', '#userData', {
                        tips: [1, '#FF5722'],
                        time: 3000
                    });
                    return;
                }
                if(this.tableData.length == 0){
                    layer.tips('请选择商品', '#addCommodity', {
                        tips: [1, '#FF5722'],
                        time: 3000
                    });
                    return ;
                }
                var data = {};
                var total = 0;
                for (var index in this.tableData) {
                    data[this.tableData[index].commId] = this.tableData[index].num;
                    total += this.tableData[index].num;
                }
                if(total > 800){
                    layer.alert('生成条码总数过多，请减少条码数量', {
                        skin: 'layui-layer-lan'
                        , closeBtn: 0
                    });
                    return ;
                }
                var loadIndex = layer.load(1, {
                    shade: [0.3,'#fff'] //0.1透明度的白色背景
                });
                $.post('${ctx}/operator/electag/getEpc',
                    {
                        jsonData: JSON.stringify(data),
                        jsonUserData: JSON.stringify({ud1: this.userData})
                    }, function (data) {
                        if (data.code == 0) {
                            var result = data.data;
                            var epcData = [];
                            for(var i in that.tableData){
                                var epc = result[''+that.tableData[i].commId];
                                epcData.push({
                                    comm:that.tableData[i],
                                    epc:epc
                                });
                            }
                            console.log(JSON.stringify(epcData));
                            window.parent.vm.epcData = epcData;
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                        } else {
                            layer.alert('生成EPC列表失败，请重试', {
                                skin: 'layui-layer-lan'
                                , closeBtn: 0
                            });
                        }
                        layer.close(loadIndex);
                    }, 'json');
            }
        },
        mounted:function () {
            layui.use('layer');
            var that = this;
            $('#commType').on("change",function(){
                that.commodityType = $(this).val();
                that.commodityData = [];
                that.selectCommodity = null;
                that.num = '';
                $.ajax({
                    url: "${ctx}/operator/commodity/commodityList",
                    type: "POST",
                    data: {"page":JSON.stringify({}),"req":JSON.stringify({typeId:that.commodityType})},
                    success: function (data) {
                        if(data != null){
                            that.commodityData = JSON.parse(data);
                        }
                    }
                });
            });
            $('#comm').on("change",function(){
                var index = $(this).val();
                that.selectCommodity = that.commodityData[index];
            });
        },
        updated:function () {
            $("#comm").selectpicker('refresh');
        }
    });
</script>
</html>