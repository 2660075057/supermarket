<%@ page language="java" pageEncoding="UTF-8"%>
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
    <title>商品列表</title>
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
<div class="layer-show">
    <div class="form-horizontal" id="form-user">
         <div class="form-group">
                        <label class="col-md-3 control-label">站点选择</label>
                        <div class="col-md-6">
                            <select id="shopc2" class="selectpicker">

                            </select>
                        </div>
                    </div>
                    
                     <div class="form-group">
		                 <label class="col-md-3 control-label">导入数据</label>
		                 <div class="col-md-8">
		                     <textarea name="" id="plsj" class="form-control" style="height:330px"></textarea>
		                 </div>
             		</div>
             		
             		<div class="form-group">
                        <label class="col-md-3 control-label"> </label>
                        <div class="col-md-8">
                              <button type="submit" id="add" class="btn btn-primary">提交</button>
                        </div>
                    </div>
    </div>
    
    <div class="form-horizontal" id="form-user2">
        
             <div class="global-table margin-top-gap" >
				
				<div id="main-table">
					<div class="table-responsive">
					    <table class="table table-bordered table-striped table-global">
						    <thead>
						        <tr>
						        	<th width="100">序号</th>
                                    <th>商品条码</th>
                                    <th>商品名称</th>
                                    <th>商品数量</th>
                                    <th>报警阀值</th>
						        </tr>
						    </thead>
						    <tbody id="datalist">

						    </tbody>
						</table>
					</div>
				</div>
				
					<div id="main-table">
					<div class="table-responsive">
					    <table class="table table-bordered table-striped table-global">
						    <thead>
						        <tr>
						        	<th width="100">序号</th>
                                    <th width="630">错误数据</th>
						        </tr>
						    </thead>
						    <tbody id="datalist2">

						    </tbody>
						</table>
					</div>
				</div>
			</div>
             		
             		<div class="form-group" id="float_father" style="background-color:#ffffff; width:100%;">
                        <label class="col-md-3 control-label"> </label>
                        <div class="col-md-8">
                            <button type="submit" id="add2" class="btn btn-primary">提交</button>
                        </div>
                    </div>
    </div>
    
</div>
</div>
</body>
<script type="text/javascript">
$(function() {
	scrollx({id:'float_father', l:-5, t:488, f:1});
	$("#form-user2").hide();
    layui.use('layer', function(){
        var layer = layui.layer;
    });
    $.ajax({
        url: "../operator/shop/shopList",
        type: "POST",
        async: false,
        data: {"page":"{}","req":"{}"},
        success: function (data) {
            var str = "<option value=\"-1\">全部站点</option>";
            var strc = "";
            var rows = jQuery.parseJSON(data);
            for(var i=0;i<rows.length;i++){
                var row = rows[i];
                str +="<option value=\""+row.shopId+"\">"+row.shopName+"</option>";
                strc +="<option value=\""+row.shopId+"\">"+row.shopName+"</option>";
            }
            $("#shopc2").html(strc);
            $('#shopc2').selectpicker('refresh');
        }
    });
});
    
$("#add").click(function(){
    var shopid = $('#shopc2').val();
    var param={};
    var str = [];
	var tt;
	var txt = $('#plsj').val();
	var s = txt.split('\n');
	for(var i = 0 ; i<s.length; i++){   // 循环读取数据，添加到str
		tt = s[i].trim().replace(/\s+/g, ':');
		str.push(tt);
	}
    param.shopId = shopid;
    $.ajax({
        url: "../operator/depot/selectWriteDepot",
        type: "POST",
        async: false,
        dataType:'json',
        data: {"str":JSON.stringify(str),"shopid":shopid},
        success: function (data) {
        	 var str = "";
        	 var str2 ="";
        	 var rows = data.data;
        	 var list = rows[0];  // 返回正确的list数据
             if(list != null && list.length >0){
                 for(var i=0;i<list.length;i++){
                     var comm = list[i];
                     str +="<tr>";
                     str +="<td>"+(i+1)+"</td>";
                     str +="<td>"+comm.barcode+"</td><td>"+comm.commName+"</td><td>"+comm.amount+"</td><td>"+comm.threshold+"</td>";
                     str +="</tr>";
                 }

             }else{
            	 layer.msg('没有数据或数据有误');
            	 return false;
             }
             $('#datalist').html(str);
             
             var list2 = rows[1];  //返回错误信息的list数据
             if(list2 != null && list2.length > 0){
            	 for(var i=0;i<list2.length;i++){
            		 str2 +="<tr>";
            		 str2 +="<td>"+(i+1)+"</td>";
            		 str2 +="<td>"+list2[i]+"</td>";
            		 str2 +="</tr>";
           	 	}
             }
             $('#datalist2').html(str2);
             
             $("#form-user2").show();
             $("#form-user").hide();
       	 }
    });
});

$("#add2").click(function(){
    $.ajax({
        url: "../operator/depot/importDepot",
        type: "POST",
        async: false,
        data: null,
        dataType:'json',
        success: function (data) {
        	if(data.data = 'success'){
	        	var index = parent.layer.getFrameIndex(window.name);
	        	parent.layer.close(index); //执行关闭
	        	parent.location.reload();
        	}
        }
    });
});
</script>
<script type="text/javascript">
function scrollx(p) {
 var d = document, dd = d.documentElement, db = d.body, w = window, o = d.getElementById(p.id), ie6 = /msie 6/i.test(navigator.userAgent), style, timer;
 if (o) {
  cssPub = ";position:"+(p.f&&!ie6?'fixed':'absolute')+";"+(p.t!=undefined?'top:'+p.t+'px;':'bottom:0;');
  if (p.r != undefined && p.l == undefined) {
   o.style.cssText += cssPub + ('right:'+p.r+'px;');
  } else {
   o.style.cssText += cssPub + ('margin-left:'+p.l+'px;');
  }
  if(p.f&&ie6){
   cssTop = ';top:expression(documentElement.scrollTop +'+(p.t==undefined?dd.clientHeight-o.offsetHeight:p.t)+'+ "px" );';
   cssRight = ';right:expression(documentElement.scrollright + '+(p.r==undefined?dd.clientWidth-o.offsetWidth:p.r)+' + "px")';
   if (p.r != undefined && p.l == undefined) {
    o.style.cssText += cssRight + cssTop;
   } else {
    o.style.cssText += cssTop;
   }
   dd.style.cssText +=';background-image: url(about:blank);background-attachment:fixed;';
  }else{
   if(!p.f){
    w.onresize = w.onscroll = function(){
     clearInterval(timer);
     timer = setInterval(function(){
      //双选择为了修复chrome 下xhtml解析时dd.scrollTop为 0
      var st = (dd.scrollTop||db.scrollTop),c;
      c = st - o.offsetTop + (p.t!=undefined?p.t:(w.innerHeight||dd.clientHeight)-o.offsetHeight);
      if(c!=0){
       o.style.top = o.offsetTop + Math.ceil(Math.abs(c)/10)*(c<0?-1:1) + 'px';
      }else{
       clearInterval(timer);
      }
     },10)
    }
   }
  }
 }
}
</script>
</html>