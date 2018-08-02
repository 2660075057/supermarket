/*
 * 分页插件 使用时，需要页面引入pagination.jsp
 * 
<div class="pagination_wrap">
	<div class="tcdPageCode">
		<div style="float:left;">
			<a href="javascript:;" class="prevPage">上一页</a>
			<a href="javascript:;" class="tcdNumber">1</a>
			<span class="current">2</span>
			<a href="javascript:;" class="tcdNumber">3</a>
			<a href="javascript:;" class="tcdNumber">4</a>
			<a href="javascript:;" class="tcdNumber">5</a>
			<a href="javascript:;" class="tcdNumber">6</a>
			<a href="javascript:;" class="nextPage">下一页</a>
		</div>
		<input type="text" class="form-control" style="width:30px;margin-left:10px;">
		<a href="javascript:;" class="turn">跳转</a>
	</div>

	<span class="num">共计：300</span>
</div>
 */

;

$(function() {
	var method = null; //页面查询数据的方法名，此方法必须要接收两个参数 page,pageSize 如method(page,pageSize);
	var maxpage = null;
	var page = 1;
	var pagesize = 10;
	var shownum = 8;// 大于等于8
	/*
	 * 动态分页控件
	 * queryMethod 为页面的查询方法，此方法必须要接收两个参数 page,pageSize 如method(page,pageSize);
	 */
	jQuery.pagination = function(pageinfo, queryMethod) {
		method = queryMethod;
		var total = pageinfo.total;
		var currentpage = pageinfo.page;
		pagesize = pageinfo.pageSize;
		var totalpage = (total % pagesize == 0 ? total / pagesize : parseInt(total / pagesize) + 1);
		maxpage = totalpage;
		$(".pagination_wrap .num").html("共计：" + total + "条" + "/" + totalpage + "页");
		var pageHtml = "";
		
		if (currentpage > 1) {
			pageHtml += "<a href=\"javascript:;\" class=\"prevPage\">上一页</a>";
		}else{
			pageHtml += "<a href=\"javascript:return false;\" class=\"prevPage disabled\">上一页</a>";
		}
		
		/* 小于10页 不加省略号*/
		if (totalpage < (shownum + 2)) {
			for (var i = 0; i < totalpage; i++) {
				if ((i + 1) == currentpage) {
					pageHtml += "<span class=\"current\">" + (i + 1) + "</span>";
				} else {
					pageHtml += "<a href=\"javascript:;\" class=\"tcdNumber\">" + (i + 1) + "</a>";
				}
			}
		} else {
			for (var i = 0; i < totalpage; i++) {
				if (currentpage <= (shownum - 3)) {
					if ((i + 1) == currentpage) {
						pageHtml += "<span class=\"current\">" + (i + 1) + "</span>";
					} else {
						pageHtml += "<a href=\"javascript:;\" class=\"tcdNumber\">"	+ (i + 1) + "</a>";
						if ((i + 1) == shownum) {
							pageHtml += "<span  class=\"sufdoc\">...</span>";
							i = totalpage - 2;
						}
					}
				} else if (currentpage >= (totalpage - shownum + 3)) {
					if ((i + 1) == currentpage) {
						pageHtml += "<span class=\"current\">" + (i + 1) + "</span>";
					} else {
						pageHtml += "<a href=\"javascript:;\" class=\"tcdNumber\">" + (i + 1) + "</a>";
						if ((i + 1) == 2) {
							pageHtml += "<span  class=\"predoc\">...</span>";
							i = totalpage - shownum;
						}
					}
				} else {
					if ((i + 1) == currentpage) {
						pageHtml += "<span class=\"current\">" + (i + 1) + "</span>";
					} else {
						pageHtml += "<a href=\"javascript:;\" class=\"tcdNumber\">"	+ (i + 1) + "</a>";
						if ((i + 1) == 2) {
							pageHtml += "<span  class=\"predoc\">...</span>";
							i = currentpage - 4;
						}
						if ((i + 1) == currentpage + 2) {
							pageHtml += "<span  class=\"sufdoc\">...</span>";
							i = totalpage - 2;
						}
					}
				}
			}
		}
		if (currentpage < totalpage) {
			pageHtml += "<a href=\"javascript:;\" class=\"nextPage\">下一页</a>";
		}else{
			pageHtml += "<a href=\"javascript:return false;\" class=\"nextPage disabled\">下一页</a>";
		}
		
		
		$(".tcdPageCode div").html(pageHtml);
		
		
		/*点击上一页*/
		$(".tcdPageCode .prevPage").on("click", function() {
			var prevpage = parseInt($(".tcdPageCode .current").html()) - 1;
			if (prevpage >= 1) {
				var func = eval(method);
				new func(prevpage,pagesize);
			}
			return false;
		});
		
		/*点击下一页*/
		$(".tcdPageCode .nextPage").on("click", function() {
			var nextpage = parseInt($(".tcdPageCode .current").html()) + 1;
			if (nextpage <= maxpage) {
				var func = eval(method);
				new func(nextpage,pagesize);
			}
			return false;
		});
		
		
		/*点击某一页*/
		$(".tcdPageCode .tcdNumber").on("click",function(){
			var clickpage = parseInt($(this).html());
			if (clickpage >=1 && clickpage <= maxpage) {
				var func = eval(method);
				new func(clickpage,pagesize);
			}
			return false;
		});
		
		/*点击...事件 */
		$(".tcdPageCode .predoc").on("click",function(){
			var page = parseInt($(this).next().html()) - 3;
			if (page >=1 && page <= maxpage) {
				var func = eval(method);
				new func(page,pagesize);
			}
			return false;
		});
		
		/*点击...事件 */
		$(".tcdPageCode .sufdoc").on("click",function(){
			var page = parseInt($(this).prev().html()) + 3;
			if (page >=1 && page <= maxpage) {
				var func = eval(method);
				new func(page,pagesize);
			}
			return false;
		});
		
		/* 跳转到某一页*/
		$(".tcdPageCode .turn").off("click").on("click",function(){
			var turnpage = $(".tcdPageCode input").val();
			if(turnpage==null || $.trim(turnpage)=="" || isNaN(turnpage)){
				return;
			}else{
				var num = parseInt(turnpage);
				if(num>maxpage){
					num = maxpage;
				}
				if(num<1){
					num = 1;
				}
				$(".tcdPageCode input").val(num);
				var func = eval(method);
				new func(num,pagesize);
			}
			return false;
		});
		
		/* 回车按键响应 */
		$(".tcdPageCode input").off("keydown").on("keydown",function(e){
			if(e.keyCode==13){
				$(".tcdPageCode .turn").trigger("click");
			}
		});
	}
	
	/* 刷新当前页 */
	jQuery.refreshCur = function(){
		var curpage = parseInt($(".tcdPageCode span.current").html());
		if (curpage >=1 && curpage <= maxpage) {
			var func = eval(method);
			new func(curpage,pagesize);
		}
		return false;
	}
	

});
