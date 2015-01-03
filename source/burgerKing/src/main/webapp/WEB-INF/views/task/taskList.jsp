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
					<a class="green" name="addTask" id="addTask" onclick="addTask();" href="javascript:void(0)"><span>添加</span></a>
					
				</div>
			</td>
			<td>
				<label style="font-size:14px;">任务类型：</label>
			</td>
			<td>
				<div class="qjstxt_wrap clearfix">
				<input type="text" class="stxt" name="taskName" id="taskName"/>
				</div>
			</td>
			<td >
				<label style="font-size:14px;">执行人员：</label>
			</td>
			<td>
				<div class="qjstxt_wrap clearfix">
				<input type="text" class="stxt" name="userName" id="userName"/>
				</div>
			</td>
			<td >
				<label style="font-size:14px;">创建时间：</label>
			</td>
			<td>
				<div class="qjstxt_wrap clearfix">
				<input type="text" class="text" name="sDate" id="sDate"/>
			
				<input type="button" value="搜&nbsp;索" id="searchBtn1" class="search" />
		
				</div>
			</td>
			<!-- <td>
				<div class="qjstxt_wrap clearfix">
					<input type="text" class="text" name="taskName" id="taskName"/>
					<label class="defalut_val" for="taskName">根据任务类型</label>
					<input type="button" value="搜&nbsp;索" id="searchBtn" class="search" />
				</div>
			</td> -->
		</tr>
	</table>
	<table id="gridTableTask"></table>
	<div id="gridPagerTask"></div>
</div>


<script type="text/javascript" src="${basePath}/js/task/taskList.js"></script>
<script type="text/javascript">
	$("#searchBtn").bind("click",function(){
				$("#gridTableTask").setGridParam({page:1}).jqGrid().trigger("reloadGrid");
	});
	$("#searchBtn1").bind("click",function(){
		$("#gridTableTask").setGridParam({page:1}).jqGrid().trigger("reloadGrid");
});
</script>
</body>
</html>
