<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>传递事件参数</title>
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
        #container {
            min-width:603px;
            min-height:600px;
        }
    </style>
    <script charset="utf-8" src="http://map.qq.com/api/js?v=2.exp"></script>
    <script>
        var citylocation,map,marker = null;
        var init = function() {

            var city = document.getElementById("city");
            var map = new qq.maps.Map(document.getElementById("container"),{
                center: new qq.maps.LatLng(39.916527,116.397128),
                zoom: 13
            });
            var geocoder = new qq.maps.Geocoder({
                complete:function(result){
                    console.log('成功：'+result.detail.address);
                }
            });
            //绑定单击事件添加参数
            qq.maps.event.addListener(map, 'click', function(event) {
                var lat = event.latLng.getLat();
                var lng = event.latLng.getLng();
                console.log('您点击的位置为: [' + event.latLng.getLat() + ', ' +
                    event.latLng.getLng() + ']');
                var coord=new qq.maps.LatLng(lat,lng);
                geocoder.getAddress(coord);
                geolocation_latlng(event.latLng.getLat()+","+event.latLng.getLng());
            });
            //调用城市服务信息
            citylocation = new qq.maps.CityService({
                complete : function(results){
                    map.setCenter(results.detail.latLng);
                    city.style.display = 'inline';
                    city.innerHTML = '所在位置: ' + results.detail.name;

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
        }
        function geolocation_latlng(input) {
            //获取经纬度信息
            //var input = document.getElementById("latLng").value;
            //用,分割字符串截取两位长度
            var latlngStr = input.split(",",2);
            //解析成浮点数 取值第一位 第二位
            var lat = parseFloat(latlngStr[0]);
            var lng = parseFloat(latlngStr[1]);
            //设置经纬度信息
            var latLng = new qq.maps.LatLng(lat, lng);
            //调用城市经纬度查询接口实现经纬查询
            citylocation.searchCityByLatLng(latLng);
        }
    </script>
</head>
<body onload="init()">
<div>
    <input id="latLng" type="textbox" value="40.0680881132379, 116.45713806152344">
    <input type="button" value="search" onclick="geolocation_latlng()">
    <span style="height:30px;display:none" id="city"></span>
</div>
<div id="container"></div>
</body>
</html>


