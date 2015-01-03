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
<div class="dzwidth_960 user">
	<table class="qjabtn_wrap" id="searchDiv">
		<tr>
			<td>
				<div class="qjact_btn clearfix">
					<a class="green" id="addBeacon"  href="javascript:void(0)"><span>新增</span></a>
					<a href="javascript:void(0)" class="small_ea" id="batchImport"><span>批量导入</span></a>
					<a href="javascript:void(0)" id="downloadTemp" class="small_ea"><span>下载模板</span></a>
				</div>
			</td>
			<td>
				<div class="qjstxt_wrap clearfix">
					<input type="text" class="text" name="beaconName" id="beaconName"/>
					<label class="defalut_val" for="beaconName">根据设备号查询</label>
					<input type="button" value="搜&nbsp;索" id="searchBtn" class="search" />
				</div>
			</td>
		</tr>
	</table>
	
	
	<table id="gridTableBeacon"></table>
		<div id="gridPagerBeacon"></div>
</div>
	<script type="text/javascript"
		src="${basePath}/js/beacon/beaconList.js"></script>
	<script type="text/javascript">
	$(function(){
		$("#searchBtn").bind("click",function(){
					$("#gridTableBeacon").setGridParam({page:1}).jqGrid().trigger("reloadGrid");
		});
		$("#batchImport").bind('click',function(){
			var url = basePath + "/beacon/import";
			tipsWindown("批量导入","url:post?"+url,"660","400","true","","true","");
		})
		$("#downloadTemp").bind('click',function(){
			var url = basePath + "/beacon/downloadTemp";
			window.location = url;
		})
	});
</script>
	
</body>
</html>
