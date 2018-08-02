<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- 分页插件代码，对应的操作方法在pagination.js  --%>
<div class="pager-md clearfix margin-top-gap">
	<div class="pull-left">
		<div class="select-size">
			<span class="text margin-right-5">显示</span>
			<select class="selectpicker" id="pageSize">
				<option>10</option>
				<option>20</option>
				<option>30</option>
				<option>40</option>
				<option>50</option>
			</select>
			<span class="num">共计：0/0</span>
		</div>
	</div>
	<div class="pull-right">
		<ul class="pagination">
			<!-- <li><a href="#">&laquo;</a></li>
			<li class="active"><a href="#">1</a></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li class="more"><a href="javascript:;">...</a></li>
			<li><a href="#">80</a></li>
			<li><a href="#">&raquo;</a></li> -->
		</ul>
	</div>
</div>

