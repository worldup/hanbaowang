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
		<c:forEach items="${testDetail}" var="detailMap">
			<div class="dz_crumbs"><b class="details">用户&nbsp;${userName}&nbsp;的答题情况</b>&gt;${detailMap.key}</div>
			<table width="100%" cellpadding="0" cellspacing="0" class="list_table" style="margin-bottom:20px">
				<colgroup>
					<col style="width:70%"/>
				</colgroup>
				<tr>
					<th>试题内容</th>
					<!-- <th>知识点</th> -->
					<th>分数</th>
					<th>得分</th>
					<!-- <th>证据</th>
					<th>根源</th>
					<th>解决方案</th>
					<th>备注</th> -->
				</tr>
				<c:forEach items="${detailMap.value}" var="question" varStatus="index">
					<c:if test="${index.index % 2==0}">
					<tr class="even">
					</c:if>
					<c:if test="${index.index % 2!=0}">
					<tr class="odd">
					</c:if>
						<td>${question.questionContent}</td>
						<%-- <td>${question.moduleName}</td> --%>
						<td>${question.originalScore}</td>
						<td>${question.tvalue}</td>
						<%-- <td>${question.qevidence}</td>
						<td>${question.qresource}</td>
						<td>${question.qresolve}</td>
						<td>${question.remark}</td> --%>
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
