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
		<table class="addform_wrap" id="basicInfo" style="margin:0;">
			<tr>
				<td>
					<label>试卷名称：</label><input value="${examPageDetail.examName}" class="text_170" readonly="readonly" />
				</td>
				<td>
					<label>试卷类型：</label><input value="${examPageDetail.examTypeName}"  class="text_170" readonly="readonly"/>
				</td>
				<td>
					<label>答题时间：</label><input value="${examPageDetail.examTimer}"  class="text_170"  readonly="readonly"/><span>&nbsp;分钟</span>
				</td>
			</tr>
			<tr>
				<td>
					<label>试卷总分：</label><input value="${examPageDetail.totalValue}" class="text_170" readonly="readonly"/>
				</td>
				<td colspan="2">
					<label>通过分数：</label><input value="${examPageDetail.passValue}"  class="text_170"  readonly="readonly"/>
				</td>
			</tr>
		</table>
		
		<c:forEach items="${examPageDetail.questionMap}" var="quesMap">
			<div class="dz_crumbs"><b class="details">试题详情</b>&gt;${quesMap.key}</div>
			<table width="100%" cellpadding="0" cellspacing="0" class="list_table" style="margin-bottom:25px">
				<colgroup>
					<col style="width:50%"/>
					<col style="width:30%"/>
					<col style="width:20%"/>
				</colgroup>
				<tr>
					<th>试题内容</th>
					<th>知识点</th>
					<th>分数</th>
				</tr>
				<c:forEach items="${quesMap.value}" var="question" varStatus="index">
					<c:if test="${index.index % 2==0}">
					<tr class="even">
					</c:if>
					<c:if test="${index.index % 2!=0}">
					<tr class="odd">
					</c:if>
						<td>${question.qcontent}</td>
						<td>${question.modelName}</td>
						<td>${question.qvalue}</td>
					</tr>
				</c:forEach>
			</table>
		</c:forEach>
		<div class="txt_center clearfix">
			<div class="qjact_btn clearfix" >
				<a href="javascript:history.go(-1)"
						class="big_ea"><span>返&nbsp;&nbsp;回</span></a>
				
			</div>
		</div>
	</div>
	
</body>
</html>
