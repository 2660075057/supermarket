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
    <title>标签导入</title>
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
        <jsp:param name="type" value="tag_import"  />
    </jsp:include>
	<div class="main" id="app" v-cloak>
		<div class="bread">
			<a href="#">首页</a> &gt; <a href="#">系统设置</a> &gt; 标签导入
		</div>
		<div class="main-container">
			<div class="page-bar clearfix">
				<div class="pull-left">
					<div class="page-title">
						<span class="line"></span>
                        标签导入
					</div>
				</div>
                <div class="pull-right">
                    <button type="button" class="btn margin-right-5" @click="newList">新建导入表</button>
                    <button type="button" class="btn btn-primary margin-right-5" v-show="canUpload" @click="importElec">导入EPC</button>
                    <button type="button" class="btn btn-primary margin-right-5" v-show="downloadHandle" @click="downLoad">下载EXCEL</button>
                </div>
			</div>
            <div style="margin-top: 10px;">
                <table class="table table-bordered table-striped table-global">
                    <thead>
                    <tr>
                        <th width="100">序号</th>
                        <th>商品名称</th>
                        <th>EPC</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(item,index) in tableData">
                        <td>{{index+1}}</td>
                        <td>{{item.comm.commName}}</td>
                        <td>{{item.msg?item.msg:item.epc.data}}</td>
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
        el: "#app",
        data: {
            epcData: [],
            canUpload:false,
            downloadHandle:null
        },
        methods: {
            newList: function () {
                layer.open({
                    type: 2,
                    area: ['1000px', '580px'],
                    fixed: false, //不固定
                    maxmin: true,
                    content: '${ctx}/operator/electag/createEpc'
                });
            },
            importElec:function () {
                var that = this;

                var data = [];
                for (var i in this.epcData){
                    for(var j in this.epcData[i].epc){
                        var temp = JSON.parse(JSON.stringify(this.epcData[i].epc[j]));
                        temp.commName = this.epcData[i].comm.commName;
                        temp.barcode = this.epcData[i].comm.barcode;
                        data.push(temp);
                    }
                }
                var loadIndex = layer.load(1, {
                    shade: [0.3,'#fff'] //0.1透明度的白色背景
                });
                $.post('${ctx}/operator/electag/tagImport',{elecTagJson:JSON.stringify(data)},function (data) {
                    if(data.code == 0){
                        that.downloadHandle = data.data;
                        that.canUpload = false;
                        if(data.data == null){
                            layer.alert('导入条码失败，请重新生成条码', {
                                skin: 'layui-layer-lan'
                                , closeBtn: 0
                            });
                        }
                    }
                    layer.close(loadIndex);
                },'json');
            },
            downLoad:function () {
                window.open('${ctx}/operator/electag/downLoad?handle='+this.downloadHandle);
            }
        },
        watch: {
            epcData:function () {
                this.downloadHandle = null;
                this.canUpload = false;
            }
        },
        computed: {
            tableData:function () {
                var data = [];
                var b = false;
                for (var i in this.epcData) {
                    var temp = this.epcData[i];
                    if (temp.epc.length == 0) {
                        data.push({comm: temp.comm, msg: '无法生成EPC码'});
                    } else {
                        b = true;
                        for (var j in temp.epc) {
                            data.push({comm: temp.comm, epc: temp.epc[j]});
                        }
                    }
                }
                this.canUpload = b;
                return data;
            }
        }
    });
</script>
</html>