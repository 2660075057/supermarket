<%@ page language="java"
         pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>微信banner设置</title>
<%@ include file="global/mylink.jsp"%>
</head>

<body class="layer-body">
	<div class="layer-show">
		<div class="form-horizontal" id="form-user">
			<div class="form-group">
				<label class="col-md-3 control-label"></label>
				<div class="col-md-8">
					<div class="fr">
						<div class="add-field" id="add-field">
							<a href="javascript:void(0);" id="addDIV" style="color: #00a2e9;"><strong>+</strong>
								微信banner设置</a>
						</div>
					</div>
				</div>
			</div>
			<div id="elementDIV">
				
			</div>
			<div class="form-group">
			    <label class="col-md-3 control-label"> </label>
			    <div class="col-md-8">
					<center><button type="submit" class="btn btn-primary">保存</button></center>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var layer = null;
$(document).ready(function(){
	layui.use('layer', function(){
		  layer = layui.layer;
	});

	$.post("${ctx}/operator/wechatSetting/getWechatBanner", {}, function (data){
		if(data!=null && $.trim(data).length!=0){
			data = JSON.parse(data);
			var rows = data.data;
			if(rows!=null){
				for(var r in rows){
					var row = rows[r];
					var imgUrl = row.imgUrl;
					var url = row.url;
					var len = $("a[id^=removeDIV]").length;
					var html = "";
					html += "<div class=\"col-md-12\">";
					html += "<input id=\"imgUrl_"+len+"\" type=\"hidden\" value=\""+imgUrl+"\">";
					html += "<input id=\"fileToUpload_"+len+"\" type=\"file\" style=\"display: none;\">";
					html += "<label class=\"control-label\">banner&nbsp;&nbsp;&nbsp;</label>";
					html += "<label> <button type=\"button\" class=\"layui-btn\" id=\"imgBtn_"+len+"\"><i class=\"layui-icon\">&#xe67c;</i>上传图片</button>&nbsp;&nbsp;&nbsp;<img id=\"imgURL_"+len+"\" alt=\"\" src=\"${ctx}"+imgUrl+"\" style=\"width: 100px;height: 100px;\">";
					html += "</label> <label> <input type=\"text\" class=\"form-control\" id=\"address_"+len+"\" placeholder=\"地址\" style=\"width: 300px;\" value=\""+(url==null?"":url)+"\"/>";
					html += "</label>";
					html += "&nbsp;&nbsp;&nbsp<a href=\"javascript:void(0)\" id=\"removeDIV_"+len+"\"><img alt=\"\" src=\"${pageContext.request.contextPath}/operator/images/unfold1.png\"></a>";
					html += "</div>";
					$("#elementDIV").append(html);
					$("button[id=imgBtn_"+len+"]").on("click", function(){
						showPath(len);
					});
					//删除一列的按钮绑定事件
					$("a[id=removeDIV_"+len+"]").on("click",function (){
						$(this).parent().remove();
					});
				}
			}
		}
	});

	$("#addDIV").on("click",function (){
		var len = $("a[id^=removeDIV]").length;
		var html = "";
		html += "<div class=\"col-md-12\">";
		html += "<input id=\"imgUrl_"+len+"\" type=\"hidden\">";
		html += "<input id=\"fileToUpload_"+len+"\" type=\"file\" style=\"display: none;\">";
		html += "<label class=\"control-label\">banner&nbsp;&nbsp;&nbsp;</label>";
		html += "<label> <button type=\"button\" class=\"layui-btn\" id=\"imgBtn_"+len+"\"><i class=\"layui-icon\">&#xe67c;</i>上传图片</button>&nbsp;&nbsp;&nbsp;<img id=\"imgURL_"+len+"\" alt=\"\" src=\"\" style=\"width: 100px;height: 100px;\">";
		html += "</label> <label> <input type=\"text\" class=\"form-control\" id=\"address_"+len+"\" placeholder=\"地址\" style=\"width: 300px;\"/>";
		html += "</label>";
		html += "&nbsp;&nbsp;&nbsp<a href=\"javascript:void(0)\" id=\"removeDIV_"+len+"\"><img alt=\"\" src=\"${pageContext.request.contextPath}/operator/images/unfold1.png\"></a>";
		html += "</div>";
		$("#elementDIV").append(html);
		$("button[id=imgBtn_"+len+"]").on("click", function(){
			showPath(len);
		});
		//删除一列的按钮绑定事件
		$("a[id=removeDIV_"+len+"]").on("click",function (){
			$(this).parent().remove();
		});
	});

	$(".btn-primary").on("click",function (){//提交按钮
		var bannerArr = new Array();
		var bannerFlag = false;
		$("input[id^='imgUrl_']").each(function (){
			var v = $(this).val();
			var id = "#imgBtn_"+$(this).prop("id").split("_")[1];
			if($.trim(v).length==0){
				layer.tips('请选择图片', id,
		                {tips: [1, 'red'], time: 2000}
		         );
				bannerFlag = true;
		        return false;
			}
			var imgUrl = $(this).val();
			var url = $(id).val();
			var banner = {};
			banner.imgUrl = imgUrl;
			if($.trim(url).length==0){
				banner.url = null; 
			}else{
				banner.url = url; 
			}
			bannerArr.push(banner);
		});
		if(bannerFlag){//图片验证不通过
			return;
		}
 		$.ajax({
			  url: "${ctx}/operator/wechatSetting/saveWechatBanner",
			  type: "POST",
			  data: {"bannerArr":JSON.stringify(bannerArr)},
			  success: function(data){
				var row = JSON.parse(data);
	        	if(row.data){
	        		layer.msg('保存成功', {icon: 1});
	        		setTimeout("closePanel()", 1000);
	        	}else{
	        		layer.msg('保存失败', {icon: 2});
	        	}
			  },
			  error: function(error){
				  console.error(error);
			  }
			});
	});
	

});

function showPath(len){
	var $FID = "#fileToUpload_"+len;
	$($FID).click();
	$($FID).change(function (e){
		uploadFile(e, len);
	});
}

function uploadFile(e, len){
	e = e || event;
    var that = this;
    this.file = e.target.files[0];
    var config = {
        headers: {'Content-Type': 'multipart/form-data'}
    };
    var params = new FormData();
    params.append('file', this.file);
    params.append("imgType","wechat_banner");
    if (this.file.size > 1024*1024) {
    	layer.alert('上传的文件大小不能超过1M', {icon: 5});
		return ;
    }
    $.ajax({
        url: "${ctx}/operator/file/img",
        type: "POST",
        data: params,
        processData: false,
        contentType: false
    }).then(function (data) {
        console.log(data);
       if(data!=null){
           var row = JSON.parse(data);
           if(row.code==0){
               var da = row.data;
               var $ID = "#imgURL_"+len;
               $($ID).attr("src", "${ctx}"+da[0]);
               var $IMGID = "#imgUrl_"+len;
               $($IMGID).val(da[0]);
           }
       }
    }).catch(function (error) {
        console.error(error);
    });
 
	
}


function closePanel(){//关闭
	var index = parent.layer.getFrameIndex(window.name);
	parent.layer.close(index); //执行关闭
	parent.location.reload();
}
</script>
</html>