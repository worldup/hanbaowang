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
					<a class="green"  name="addname" id="addname" onclick="addWork();" href="javascript:void(0)"><span>新增</span></a>
					
				</div>
			</td>
			<td>
				<div class="qjstxt_wrap clearfix">
					<input type="text"   class="text" name="typeName" id="typeName"/>
					<label class="defalut_val" for="typeName">根据类型名称查询</label>
					<input type="button" value="搜&nbsp;索" id="searchBtn" class="search" />
				</div>
			</td>
		</tr>
	</table>
	<table id="gridTableWork"></table>
		<div id="gridPagerWork"></div>
</div>

<script type="text/javascript" src="${basePath}/js/work/workList.js"></script>
<script type="text/javascript">
$("#searchBtn").bind("click",function(){
	$("#gridTableWork").setGridParam({page:1}).jqGrid().trigger("reloadGrid");
});
</script>
</body>
</html>
