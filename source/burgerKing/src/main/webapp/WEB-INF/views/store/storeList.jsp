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
<div class="dzwidth_960 store">
	<table class="qjabtn_wrap" id="searchDiv">
		<tr>
			<td>
				<div class="qjact_btn clearfix">
					<a class="green" name="addStore" id="addStore" onclick="addStore();" href="javascript:void(0)"><span>新增</span></a>
					<a href="javascript:void(0)" id="batchImport" class="small_ea"><span>批量导入</span></a>
					<a href="javascript:void(0)" id="downloadTemp" class="small_ea"><span>下载模板</span></a>
				</div>
			</td>
			<td>
				<label style="font-size:14px;">门店名称：</label>
			</td>
			<td>
				<div class="qjstxt_wrap clearfix">
					<input type="text" class="stxt" name="storeName" id="storeName"/>
				</div>
			</td>
			<td >
				<label style="font-size:14px;">巡检人员：</label>
			</td>
			<td>
				<div class="qjstxt_wrap clearfix">
					<input type="text" class="text" name="realName" id="realName"/>
					
					<input type="button" value="搜&nbsp;索" id="searchBtn" class="search" />
				
				</div>
			</td>
		</tr>
	</table>
	<table id="gridTableStore"></table>
	<div id="gridPagerStore"></div>
	
</div>

<script type="text/javascript" src="${basePath}/js/store/storeList.js"></script>
<script type="text/javascript">
	
	$(function(){
		$("#searchBtn").bind("click",function(){
			$("#gridTableStore").setGridParam({page:1}).jqGrid().trigger("reloadGrid");
    });
		$("#batchImport").bind('click',function(){
			var url = basePath + "/store/import";
			tipsWindown("批量导入","url:post?"+url,"660","400","true","","true","");
		});
		$("#downloadTemp").bind('click',function(){
			var url = basePath + "/store/downloadTemp";
			window.location = url;
		});
		$('#test').bind('click',function(){
			var url =basePath+"/base/toPaperAssign?examId=1";
		    tipsWindown("问卷下发","url:post?"+url,"660","400","true","","true","");	
		});
	});
	
</script>
</body>
</html>
