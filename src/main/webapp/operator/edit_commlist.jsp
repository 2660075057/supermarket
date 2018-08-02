<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>站点管理</title>
    <style type="text/css">
        *{
            margin:0px;
            padding:0px;
        }
        body, button, input, select, textarea {
            font: 12px/16px Verdana, Helvetica, Arial, sans-serif;
        }
        p{
            width:603px;
            padding-top:3px;
            overflow:hidden;
        }
        [v-cloak] {
            display: none;
        }
    </style>
    <%@ include file="global/mylink.jsp"%>
</head>

<body class="layer-body">
<input type="hidden" id="commid" value="-1">
<div class="layer-show">
    <div class="form-horizontal" id="form-user">
        <div class="form-group">
            <label for="barcode" class="col-md-3 control-label">商品条码</label>
            <div class="col-md-8">
                <input type="text" class="form-control" id="barcode" />
            </div>
        </div>
        <div class="form-group">
            <label for="commName" class="col-md-3 control-label">商品名称</label>
            <div class="col-md-8">
                <input type="text" class="form-control" id="commName" />
            </div>
        </div>
        <div class="form-group">
            <label for="costPrice" class="col-md-3 control-label">进货价</label>
            <div class="col-md-8">
                <div class="input-group">
                    <div class="input-group-addon">￥</div>
                    <input type="text" class="form-control border-left-none" id="costPrice" onchange="if(/[^\d.]/g.test(this.value)){layer.msg('只能输入数字');this.value='';}" />
                    <%--<div class="input-group-addon">元</div>--%>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="commPrice" class="col-md-3 control-label">售价</label>
            <div class="col-md-8">
                <div class="input-group">
                    <div class="input-group-addon">￥</div>
                    <input type="text" class="form-control border-left-none" id="commPrice" onchange="if(/[^\d.]/g.test(this.value)){layer.msg('只能输入数字');this.value='';}" />
                    <%--<div class="input-group-addon">元</div>--%>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-3 control-label">商品类型</label>
            <div class="col-md-4">
                <select id="commType" class="selectpicker">

                </select>
            </div>
        </div>
        <div id="attr">

        </div>
        <div id="imgApp" v-cloak>
            <div class="col-md-12">
                <input id="fileToUpload_" type="file" style="display: none;">
                <label>
                    <div v-for="(item,index) in imgData">
                        <img style="width: 100px;height: 100px;" :src="'${ctx}'+item"/>
                        <a @click="first(index)" v-if="index != 0">设为封面</a>
                        <a @click="remove(index)">删除</a>
                    </div>
                </label>
                <label>
                    <input style="display: none" type="file" id="uploadInput" />
                    <button type="button" class="layui-btn" @click="uploadImg()"><i class="layui-icon">&#xe67c;</i>上传图片</button>
                </label>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 control-label"> </label>
            <div class="col-md-8">
                <button type="submit" id="submit" class="layui-btn">提交</button>
            </div>
        </div>
    </div>
</div>
</body>
<script src="${ctx}/plugins/vue/vue.js"></script>
<script>
    var imgApp = new Vue({
        el:'#imgApp',
        data:{
          imgData:[]
        },
        methods:{
            uploadFile: function (e, len) {
                e = e || event;
                var that = this;
                this.file = e.target.files[0];
                var config = {
                    headers: {'Content-Type': 'multipart/form-data'}
                };
                var params = new FormData();
                params.append('file', this.file);
                params.append("imgType", "commodity_file");
                if (this.file.size > 1024 * 200) {
                    layer.alert('上传的文件大小不能超过200KB', {icon: 5});
                    return;
                }
                $.ajax({
                    url: "${ctx}/operator/file/img",
                    type: "POST",
                    data: params,
                    processData: false,
                    contentType: false
                }).then(function (data) {
                    console.log(data);
                    if (data != null) {
                        var row = JSON.parse(data);
                        if (row.code == 0) {
                            that.imgData.push(row.data[0]);
                        }
                    }
                }).catch(function (error) {
                    console.error(error);
                });
            },
            uploadImg:function () {
                var that = this;
                var obj = $('#uploadInput');
                obj.click();
                obj.off('change').change(function (e) {
                    that.uploadFile(e);
                });
            },
            first: function (index) {
                var item = this.imgData[index];
                this.imgData.splice(index, 1);
                this.imgData.splice(0, 0, item);
            },
            remove: function (index) {
                this.imgData.splice(index, 1);
            }
        },
    });
</script>
<script type="text/javascript">
    var fatherTypeArr = {};
    var trows;
    $(function (){
        $.ajax({
            url: "${ctx}/operator/commoditytype/commodityTypeList",
            type: "POST",
            async: false,
            data: {"page":"{}","req":"{}"},
            success: function (data) {
                var rows = jQuery.parseJSON(data);
                trows = rows;
                for(var i=0;i<rows.length;i++){
                    var row = rows[i];
                    if(row.masterId == 0){
                        fatherTypeArr[row.typeId]=row.typeName;
                    }
                }
            }
        });
        var str = "";
        for(var f in fatherTypeArr){
            str +="<optgroup label=\""+fatherTypeArr[f]+"\">";
            for(var i=0;i<trows.length;i++){
                var row = trows[i];
                if(f == row.typeId || f == row.masterId){
                    if(i == 0){
                        setattrs(row.typeId);
                    }
                    str +="<option value=\""+row.typeId+"\">"+row.typeName+"</option>";
                }
            }
            str +="</optgroup>";
        }
        $("#commType").html(str);
        $('#commType').selectpicker('refresh');
        /*$.ajax({
            url: "../operator/commoditytype/commodityTypeList",
            type: "POST",
            async: false,
            data: {"page":"{}","req":"{}"},
            success: function (data) {
                var str = "";
                var rows = jQuery.parseJSON(data);
                for(var i=0;i<rows.length;i++){
                    var row = rows[i];
                    if(i == 0){
                        setattrs(row.typeId);
                    }
                    if(row.masterId == 0){
                        str +="<optgroup label=\""+row.typeName+"\">";
                    }
                    str +="<option value=\""+row.typeId+"\">"+row.typeName+"</option>";
                }
                str +="</optgroup>";
                $("#commType").html(str);
                $('#commType').selectpicker('refresh');
            }
        });*/
        $("#commType").change(function(){
            setattrs($("#commType").val());
        });
        layui.use('layer', function(){
            var layer = layui.layer;
        });


        var commid = '<%=request.getParameter("commid")%>';
        var currPage = '<%=request.getParameter("currPage")%>';
        if(commid!=null && commid >0){
            $('#commid').val(commid);
            $.ajax({
                url: "${ctx}/operator/commodity/selectByPrimaryKey",
                type: "POST",
                data: {"commId":commid},
                timeout: 8000
            }).done(function(data){
                if(data !=null){
                    var comm = jQuery.parseJSON(data);
                    $("#barcode").val(comm.barcode);
                    $("#commName").val(comm.commName);
                    var costPrice = parseFloat(comm.costPrice/100);
                    var commPrice = parseFloat(comm.commPrice/100);
                    $("#costPrice").val(costPrice);
                    $("#commPrice").val(commPrice);
                    $("#commType").val(comm.commodityType.typeId);
                    $('#commType').selectpicker('refresh');
                    setattrs(comm.commodityType.typeId);
                    console.log(JSON.stringify(comm.picture));
                    for(var index in comm.picture){
                        imgApp.imgData.push(comm.picture[index].imgUrl);
                    }

                    $.ajax({
                        url: "${ctx}/operator/commodityattr/selectByCommId",
                        type: "POST",
                        data: {"commId":comm.commId},
                        timeout: 8000
                    }).done(function(data){
                        if(data != null){
                            var rows = jQuery.parseJSON(data);
                            for(var i=0;i<rows.length;i++){
                                var row = rows[i];
                                var id = "#"+row.attrId;
                                $(id).val(row.value);
                            }
                        }
                    });
                }
            })
        }

        $("#submit").on("click",function (){
            var tem = $('#commid').val();

            var barcode = $("#barcode").val();
            if(barcode == null || barcode.length <=0 ){
                layer.tips('商品条码必填！', '#barcode', {
                    tips: [3,'red']
                });
                $("html,body").animate({scrollTop:$("#barcode").offset().top},100);
                return;
            }
            var commName = $("#commName").val();
            if(commName == null || commName.length <=0 ){
                layer.tips('商品名称必填！', '#commName', {
                    tips: [3,'red']
                });
                $("html,body").animate({scrollTop:$("#commName").offset().top},100);
                return;
            }
            var costPrice = $("#costPrice").val();
            var commPrice = $("#commPrice").val();
            if(commPrice == null || commPrice.length <=0 ){
                layer.tips('商品售价必填！', '#commPrice', {
                    tips: [3,'red']
                });
                $("html,body").animate({scrollTop:$("#commPrice").offset().top},100);
                return;
            }
            var attrarr = new Array();
            var commType = $("#commType").val();
            $("input[name='attrVal']").each(function (){
                var attrs = {};
                var value = $(this).val();
                if(value != null && value.length>0){
                    attrs["attrId"]=$(this).prev().val();
                    attrs["value"]=value;
                    attrarr.push(attrs);
                }
            });
            var param = {};
            param["barcode"]=barcode;
            param["commName"]=commName;
            param["costPrice"]=costPrice*100;
            param["commPrice"]=commPrice*100;
            param["typeId"]=commType;
            param["deleteMark"]=0;
            if(tem == -1){
                console.log(attrarr);
                $.ajax({
                    url: "${ctx}/operator/commodity/addCommodity",
                    type: "POST",
                    data: {"req":JSON.stringify(param),"attrIds":JSON.stringify(attrarr),"imgData":JSON.stringify(imgApp.imgData)},
                    timeout: 8000
                }).done(function(data){
                    if(data == "true"){
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index); //执行关闭
                        parent.location.href="${ctx}/operator/commoditylist.jsp";
                    }else{
                        layer.tips('商品条码已存在！', '#barcode', {
                            tips: [3,'red']
                        });
                        $("html,body").animate({scrollTop:$("#barcode").offset().top},100);
                    }
                })
            }else{
                param["commId"] = tem;
                $.ajax({
                    url: "${ctx}/operator/commodity/updateCommodity",
                    type: "POST",
                    data: {"req":JSON.stringify(param),"attrIds":JSON.stringify(attrarr),"imgData":JSON.stringify(imgApp.imgData)},
                    timeout: 8000
                }).done(function(data){
                    if(data == "true"){
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index); //执行关闭
                        //parent.location.href="${ctx}/operator/commoditylist.jsp";
                        var param={};
                        var queryType = parent.$('#queryType').val();
                        var queryVal = parent.$('#queryVal').val();
                        if(queryVal !=null && queryVal.length >0){
                            if(queryType == 1){
                                param["barcode"]=queryVal.replace(/(^\s*)|(\s*$)/g, "");
                            }else if(queryType == 2){
                                param["commName"]=queryVal.replace(/(^\s*)|(\s*$)/g, "");
                            }
                        }
                        parent.setpage(param,currPage);
                    }else{
                        layer.tips('商品条码已存在！', '#barcode', {
                            tips: [3,'red']
                        });
                        $("html,body").animate({scrollTop:$("#barcode").offset().top},100);
                    }
                })
            }

        });
    });
function setattrs(typeid) {
    var param={};
    param["typeId"]=typeid;
    $.ajax({
        url: "${ctx}/operator/commoditytype/commodityTypeList",
        type: "POST",
        async: false,
        data: {"page":"{}","req":JSON.stringify(param)},
        success: function (data) {
            if(data != null){
                var row = jQuery.parseJSON(data)[0];
                var attrs = row.attrs;
                var astr = "";
                for(var i=0;i<attrs.length;i++){
                    var attr = attrs[i];
                    astr +='<div class="form-group">';
                    astr +='<label for="origin" class="col-md-3 control-label">'+attr.attrName+'</label>';
                    astr +='<div class="col-md-8">';
                    astr +='<input type="hidden" name="attrId" value="'+attr.attrId+'"/>';
                    astr +='<input type="text" id="'+attr.attrId+'" class="form-control" name="attrVal"/>';
                    astr +='</div></div>';
                }
                $('#attr').html(astr);
            }
        }
    });
}
</script>
</html>