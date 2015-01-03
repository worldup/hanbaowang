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
					<a class="green" id="addExamPage"  href="javascript:void(0)"><span>添加表单</span></a>
					<a href="javascript:void(0)" class="small_ea" id="batchExport"><span>表单问题批量导出</span></a>
					<a href="javascript:void(0)" class="small_ea" id="importfile"><span>工具箱上传</span></a>
				</div>
			</td>
		</tr>
	</table>
	
	<table id="gridTableExam"></table>
		<div id="gridPagerExam"></div>
</div>
	<script type="text/javascript"src="${basePath}/js/examinationManager/examinationManager.js"></script>
	<script>
// 		$(function(){
		$("#batchExport").bind('click',function(){
			var selectRows = jQuery("#gridTableExam").jqGrid('getGridParam','selarrrow');
			if(selectRows==null||selectRows.length==0){
				alert("请选择要导出的问卷");
				return;
			}
			var url = basePath + "/examManager/examport?id=" + selectRows.join(',');
			window.location = url;
		});
// 		});
		
		</script>
</body>
</html>
