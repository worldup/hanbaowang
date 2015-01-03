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
	<div class="dzwidth_960">
		<div class="dz_crumbs"><b class="details">试卷基本信息：</b></div>
		<table class="addform_wrap" id="basicInfo" style="margin:0;">
			<tr>
				<td>
					<label>试卷名称：</label><input value="${basicInfo.examName}"  class="text_170" readonly="readonly"/>
				</td>
				<td>
					<label>试卷类型：</label><input value="${basicInfo.examTypeName}" class="text_170" readonly="readonly"/>
				</td>
				<td>
					<label>答题时间：</label><input value="${basicInfo.examTimer}"  class="text_170" readonly="readonly"/><span>&nbsp;分钟</span>
				</td>
			</tr>
			<tr>
				<td>
					<label>试卷总分：</label><input value="${basicInfo.totalValue}" class="text_170" readonly="readonly"/>
				</td>
				<td colspan="2">
					<label>通过分数：</label><input value="${basicInfo.passValue}" class="text_170" id="passScore" readonly="readonly"/>
				</td>
			</tr>
		</table>
		<div class="dz_crumbs clearfix" style="margin-top:15px;">
			<b class="fl" style="margin:8px 20px 0 0;">考卷信息</b>
			<div class="qjstxt_wrap fl">
				<input type="text" class="text" name="shopName" id="shopName"/>
				<label class="defalut_val" for="shopName">根据门店名称查询</label>
				<input type="button" value="搜&nbsp;索" id="searchBtn" class="search" />
			</div>
		</div>
		
		<table id="gridTable"></table>
		<div id="gridPager"></div>
		<div class="txt_center clearfix">
			<div class="qjact_btn clearfix" >
				<a href="javascript:history.go(-1)"
						class="big_ea"><span>返&nbsp;&nbsp;回</span></a>
				
			</div>
		</div>
	</div>
	
	<input type="hidden" id="examId" value="${examId}"/>
	<script type="text/javascript"
		src="${basePath}/js/examinationManager/userAnswerInfo.js"></script>
		<script type="text/javascript">
	$("#searchBtn").bind("click",function(){
				$("#gridTable").setGridParam({page:1}).jqGrid().trigger("reloadGrid");
	});
</script>
		
</body>
</html>
