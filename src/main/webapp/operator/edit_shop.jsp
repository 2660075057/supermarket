<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        * {
            margin: 0px;
            padding: 0px;
        }

        body, button, input, select, textarea {
            font: 12px/16px Verdana, Helvetica, Arial, sans-serif;
        }

        p {
            width: 603px;
            padding-top: 3px;
            overflow: hidden;
        }

        #container {
            min-width: 603px;
            min-height: 300px;
        }

        #latLng {
            width: 295px;
        }

        #address {
            width: 295px;
        }
    </style>
    <%@ include file="global/mylink.jsp" %>
    <script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp"></script>

</head>

<body class="layer-body">
<input type="hidden" id="shopid" value="-1">
<div class="layer-show">
    <%--<div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title">添加站点</h4>
    </div>--%>
    <div class="form-horizontal" id="form-user">
        <div class="form-group">
            <label for="shopName" class="col-md-2 control-label">站点名称</label>
            <div class="col-md-8">
                <input type="text" class="form-control" name="siteName" id="shopName"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 control-label">状态</label>
            <div class="col-md-8">
                <div class="icheck-list">
                    <label>
                        <input value="0" type="radio" class="icheck" id="yy" name="state" checked/>
                        <span class="text">营业</span>
                    </label>
                    <label>
                        <input value="1" type="radio" class="icheck" id="wh" name="state"/>
                        <span class="text">维护中</span>
                    </label>
                    <label>
                        <input value="2" type="radio" class="icheck" id="zz" name="state"/>
                        <span class="text">中转仓库</span>
                    </label>
                </div>
            </div>
        </div>

        <div class="form-group">
            <label class="col-md-2 control-label">站点地址</label>
            <div class="col-md-8">
                <div class="icheck-list">
                    <label>
                        <input type="text" readonly="readonly" id="latLng" class="form-control" placeholder="经纬度"/>
                    </label>
                    <label>
                        <input type="text" class="form-control" id="address" placeholder="详细地址"/>
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 control-label">搜索城市</label>
            <div class="col-md-8">
                <div class="icheck-list">
                    <label>
                        <input type="text" id="localcity" class="form-control" placeholder="请输入城市名称！"/>
                    </label>
                    <label>
                        <button onclick="javascript:geolocation_localcity();" class="layui-btn">搜索</button>
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 control-label"></label>
            <div class="col-md-9">
                <p class="form-control-static tips">请于下方地图选择站点所在位置</p>
                <div id="site-map" class="margin-top-gap" style="width:600px;">
                    <%--<img src="images/map.jpg" alt="" />--%>
                    <div id="container"></div>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="info" class="col-md-2 control-label">站点描述</label>
            <div class="col-md-8">
                <textarea name="" id="info" class="form-control" style="height:150px"></textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="col-md-2 control-label"> </label>
            <div class="col-md-8">
                <button type="submit" id="submit" class="layui-btn">提交</button>
                <c:if test="${canDelete}">
                <button type="submit" id="delete" class="layui-btn layui-btn-danger">删除</button>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var citylocation, map, marker = null;
    $(function () {
        //var city = document.getElementById("city");
        var map = new qq.maps.Map(document.getElementById("container"), {
            center: new qq.maps.LatLng(39.916527, 116.397128),
            zoom: 13
        });
        var geocoder = new qq.maps.Geocoder({
            complete: function (result) {
                $('#address').val(result.detail.address);
                //console.log('成功：'+result.detail.address);
            }
        });
        //绑定单击事件添加参数
        qq.maps.event.addListener(map, 'click', function (event) {
            var lat = event.latLng.getLat();
            var lng = event.latLng.getLng();
            $('#latLng').val(lat + "," + lng);
            var coord = new qq.maps.LatLng(lat, lng);
            geocoder.getAddress(coord);
            geolocation_latlng(event.latLng.getLat() + "," + event.latLng.getLng());
        });
        //调用城市服务信息
        citylocation = new qq.maps.CityService({
            complete: function (results) {
                map.setCenter(results.detail.latLng);
                //city.style.display = 'inline';
                //city.innerHTML = '所在位置: ' + results.detail.name;

                if (marker != null) {
                    marker.setMap(null);
                }
                //设置marker标记
                marker = new qq.maps.Marker({
                    map: map,
                    position: results.detail.latLng
                });
            }
        });
        var shopid = '<%=request.getParameter("shopid")%>';
        var currPage = '<%=request.getParameter("currPage")%>';
        if (shopid != null && shopid > 0) {
            $('#shopid').val(shopid);
            $.ajax({
                url: "${pageContext.request.contextPath}/operator/shop/selectByPrimaryKey",
                type: "POST",
                data: {"shopId": shopid},
                timeout: 8000
            }).done(function (data) {
                if (data != null) {
                    var row = jQuery.parseJSON(data);
                    $("#shopName").val(row.shopName);
                    //$("input[name='state']").checked(row.state);
                    $("input[name='state']").each(function () {
                        $(this).parent().removeClass("checked");
                        $(this).removeAttr("checked");
                        if ($(this).val() == row.state) {
                            $(this).parent().addClass("checked");
                            $(this).attr("checked", "checked");
                        }
                    });
                    $("#info").val(row.info);
                    $("#latLng").val(row.latitude + "," + row.longitude);
                    if (row.longitude != null && row.latitude != null) {
                        geolocation_latlng(row.latitude + "," + row.longitude);
                    }

                    $("#address").val(row.address);
                }
            })
        }
        layui.use('layer', function () {
            var layer = layui.layer;
        });
        $("#submit").on("click", function () {
            var tem = $('#shopid').val();

            var shopName = $("#shopName").val();
            if (shopName == null || shopName.length <= 0) {
                layer.tips('站点名称必填！', '#shopName', {
                    tips: [3, 'red']
                });
                $("html,body").animate({scrollTop: $("#shopName").offset().top}, 100);
                return;
            }
            var state = $("input[name='state']:checked").val();
            var info = $("#info").val();
            var latLng = $("#latLng").val();
            var address = $("#address").val();
            var param = {};
            param.shopName = shopName;
            if (info != null && info.length > 0) {
                param.info = info;
            }
            if (latLng != null && latLng.length > 0 && latLng.indexOf(",") > 0) {
                var latlngarr = latLng.split(",");
                param.latitude = latlngarr[0];
                param.longitude = latlngarr[1];
            }
            if (address != null && address.length > 0) {
                param.address = address;
            }
            param.state = state;
            if (tem == -1) {
                $.ajax({
                    url: "${pageContext.request.contextPath}/operator/shop/addShop",
                    type: "POST",
                    data: param,
                    timeout: 8000
                }).done(function (data) {
                    if (data) {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index); //执行关闭
                        parent.location.href = "${pageContext.request.contextPath}/operator/shop.jsp";
                    }
                })
            } else {
                param.shopId = tem;
                $.ajax({
                    url: "${pageContext.request.contextPath}/operator/shop/updateShop",
                    type: "POST",
                    data: param,
                    timeout: 8000
                }).done(function (data) {
                    if (data) {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index); //执行关闭
                        var param = {};
                        parent.setpage(param, currPage);
                    }
                })
            }

        });
        $("#delete").on("click", function () {
            deleteShop();
        });
    });

    function geolocation_latlng(input) {
        //获取经纬度信息
        //var input = document.getElementById("latLng").value;
        //用,分割字符串截取两位长度
        var latlngStr = input.split(",", 2);
        //解析成浮点数 取值第一位 第二位
        var lat = parseFloat(latlngStr[0]);
        var lng = parseFloat(latlngStr[1]);
        //设置经纬度信息
        var latLng = new qq.maps.LatLng(lat, lng);
        //调用城市经纬度查询接口实现经纬查询
        citylocation.searchCityByLatLng(latLng);
    }

    function geolocation_localcity() {
        var localcity = $('#localcity').val();
        if (localcity == null || localcity.length <= 0) {
            layer.tips('请填写城市名称！', '#localcity', {
                tips: [3, 'red']
            });
            $("html,body").animate({scrollTop: $("#localcity").offset().top}, 100)
            return;
        }
        citylocation.searchCityByName(localcity);

    }

    function deleteShop() {
        var shopid = '<%=request.getParameter("shopid")%>';
        var currPage = '<%=request.getParameter("currPage")%>';
        layer.confirm('<font color="#000000">确定要删除此站点吗？</font>', {
            btn: ['确定','取消'] //按钮
        }, function(){
            $.post('${pageContext.request.contextPath}/operator/shop/deleteShop',{shopId:shopid},function (data) {
                if (data.code == 0 && data.data) {
                    layer.msg('删除成功', {icon: 1, time: 1500}, function () {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index); //执行关闭
                        var param = {};
                        parent.setpage(param, currPage);
                    });
                } else {
                    layer.msg('删除失败，请刷新后重试');
                }

            },'json');
        });
    }
</script>
</html>