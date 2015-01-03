<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>汉堡王首页</title>
</head>
<body>
<div class="dzwidth_960 shopstatistic">
<table>
<tr>
<td>
<div class="qjact_btn clearfix">
		<a href="javascript:void(0)" id="batchImport" class="small_ea"><span>批量导入</span></a>
		<a href="javascript:void(0)" id="downloadTemp" class="small_ea"><span>下载模板</span></a>
	</div>
</td>
<td>
	<label style="font-size:14px;">门店编号：</label>
</td>
<td>
	<div class="qjstxt_wrap clearfix">
		<input type="text" class="stxt" name="sales" id="sales"/>
	</div>
	</td>
	<td >
		<label style="font-size:14px;">月份：</label>
	</td>
	<td>
		<div class="qjstxt_wrap clearfix">
			<input type="text" id="month"
				name="month" class="text"
				onClick="WdatePicker({dateFmt:'yyyy-MM'})" maxlength="10"
				readonly style="width:150px" />
			
			<input type="button" value="搜&nbsp;索" id="searchBtn1" class="search" />
		
		</div>
	</td>
	</tr>
</table>	
	<table id="gridTableShopStatistic"></table>
		<div id="gridPagerShopStatistic"></div>
</div>
	<script type="text/javascript"
		src="${basePath}/js/shopStatistic/shopStatistic.js"></script>
	<script type="text/javascript">
	$(function(){
		$("#batchImport").bind('click',function(){
			var url = basePath + "/shopStatistic/import";
			tipsWindown("批量导入","url:post?"+url,"660","400","true","","true","");
		});
		$("#downloadTemp").bind('click',function(){
			var url = basePath + "/shopStatistic/downloadTemp";
			window.location = url;
		});
	});
	$("#searchBtn").bind("click",function(){
		$("#gridTableShopStatistic").setGridParam({page:1}).jqGrid().trigger("reloadGrid");
});
	$("#searchBtn1").bind("click",function(){
		$("#gridTableShopStatistic").setGridParam({page:1}).jqGrid().trigger("reloadGrid");
});
</script>
	
</body>
</html>
