/*
 * 分页插件 使用时，需要页面引入pagination.jsp
 * 
<div class="pager-md clearfix margin-top-gap">
	<div class="pull-left">
		<div class="select-size">
			<span class="text margin-right-5">显示</span>
			<select class="selectpicker">
				<option>12</option>
				<option>24</option>
				<option>36</option>
			</select>
			<span class="num">共计：0/0</span>
		</div>
	</div>
	<div class="pull-right">
		<ul class="pagination">
			<li><a href="#">&laquo;</a></li>
			<li class="active"><a href="#">1</a></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li class="more"><a href="javascript:;">...</a></li>
			<li><a href="#">80</a></li>
			<li><a href="#">&raquo;</a></li>
		</ul>
	</div>
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
		$(".select-size .num").html("共计：" + total + "条" + "/" + totalpage + "页");
		var pageHtml = "";
		
		if (currentpage > 1) {
			pageHtml += "<li><a href=\"javascript:;\" id=\"prevPage\">&laquo;</a></li>";
		}else{
			pageHtml += "";
		}
		
		/* 小于10页 不加省略号*/
		if (totalpage < (shownum + 2)) {
			for (var i = 0; i < totalpage; i++) {
				if ((i + 1) == currentpage) {
					pageHtml += "<li class=\"active\"><a href=\"javascript:;\" id=\"current\">" + (i + 1) + "</a></li>";
				} else {
					pageHtml += "<li><a href=\"javascript:;\" class=\"tcdNumber\">" + (i + 1) + "</a></li>";
				}
			}
		} else {
			for (var i = 0; i < totalpage; i++) {
				if (currentpage <= (shownum - 3)) {
					if ((i + 1) == currentpage) {
						pageHtml += "<li class=\"active\"><a href=\"javascript:;\" id=\"current\">" + (i + 1) + "</a></li>";
					} else {
						pageHtml += "<li><a href=\"javascript:;\" class=\"tcdNumber\">" + (i + 1) + "</a></li>";
						if ((i + 1) == shownum) {
							pageHtml += "<li class=\"more\"><a href=\"javascript:;\" id=\"sufdoc\">...</a></li>";
							i = totalpage - 2;
						}
					}
				} else if (currentpage >= (totalpage - shownum + 3)) {
					if ((i + 1) == currentpage) {
						pageHtml += "<li class=\"active\"><a href=\"javascript:;\" id=\"current\">" + (i + 1) + "</a></li>";
					} else {
						pageHtml += "<li><a href=\"javascript:;\" class=\"tcdNumber\">" + (i + 1) + "</a></li>";
						if ((i + 1) == 2) {
							pageHtml += "<li class=\"more\"><a href=\"javascript:;\" id=\"predoc\">...</a></li>";
							i = totalpage - shownum;
						}
					}
				} else {
					if ((i + 1) == currentpage) {
						pageHtml += "<li class=\"active\"><a href=\"javascript:;\" id=\"current\">" + (i + 1) + "</a></li>";
					} else {
						pageHtml += "<li><a href=\"javascript:;\" class=\"tcdNumber\">" + (i + 1) + "</a></li>";
						if ((i + 1) == 2) {
							pageHtml += "<li class=\"more\"><a href=\"javascript:;\" id=\"predoc\">...</a></li>";
							i = currentpage - 4;
						}
						if ((i + 1) == currentpage + 2) {
							pageHtml += "<li class=\"more\"><a href=\"javascript:;\" id=\"sufdoc\">...</a></li>";
							i = totalpage - 2;
						}
					}
				}
			}
		}
		if (currentpage < totalpage) {
			pageHtml += "<li><a href=\"javascript:;\" id=\"nextPage\">&raquo;</a></li>";
		}else{
			pageHtml += "";
		}
		
		
		$(".pagination").html(pageHtml);
		
		
		/*点击上一页*/
		$("#prevPage").on("click", function() {
			var prevpage = parseInt($("#current").html()) - 1;
			if (prevpage >= 1) {
				var func = eval(method);
				new func(prevpage,pagesize);
			}
			return false;
		});
		
		/*点击下一页*/
		$("#nextPage").on("click", function() {
			var nextpage = parseInt($("#current").html()) + 1;
			if (nextpage <= maxpage) {
				var func = eval(method);
				new func(nextpage,pagesize);
			}
			return false;
		});
		
		
		/*点击某一页*/
		$(".tcdNumber").on("click",function(){
			var clickpage = parseInt($(this).html());
			if (clickpage >=1 && clickpage <= maxpage) {
				var func = eval(method);
				new func(clickpage,pagesize);
			}
			return false;
		});
		
		/*点击...事件 */
		$("#predoc").on("click",function(){
			var page = parseInt($(this).next().html()) - 3;
			if (page >=1 && page <= maxpage) {
				var func = eval(method);
				new func(page,pagesize);
			}
			return false;
		});
		
		/*点击...事件 */
		$("#sufdoc").on("click",function(){
			var page = parseInt($(this).prev().html()) + 3;
			if (page >=1 && page <= maxpage) {
				var func = eval(method);
				new func(page,pagesize);
			}
			return false;
		});
		
	}
	
	/* 刷新当前页 */
	jQuery.refreshCur = function(){
		var curpage = parseInt($("#current").html());
		if (curpage >=1 && curpage <= maxpage) {
			var func = eval(method);
			new func(curpage,pagesize);
		}
		return false;
	}
	

});
