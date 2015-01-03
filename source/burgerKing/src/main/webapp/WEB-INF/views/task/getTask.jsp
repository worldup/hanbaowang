<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table class="addform_wrap">
	<%-- <tr>
		<td class="">任务名称：</td>
		<td>${task.name}</td>
	</tr> --%>
	<tr align="left">
		<td class="">任务类型：</td>
		<td>${task.workTypeName }</td>
	</tr>
	<tr align="left">
		<td class="">季度：</td>
		<td>${task.quarter }</td>
	</tr>
	<tr align="left">
		<td class="">任务标识：</td>
		<td>
			<c:if test="${task.isSelfCreate == 0}">自主</c:if>
			<c:if test="${task.isSelfCreate == 1}">委派</c:if>
		</td>
	</tr>
	<tr align="left">
		<td class="">任务详情：</td>
		<td>${task.content }</td>
	</tr>
	<tr align="left">
		<td class="">任务开始时间：</td>
		<td><fmt:formatDate value="${task.starttime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
	<tr align="left">
		<td class="">任务预计完成时间：</td>
		<td><fmt:formatDate value="${task.finishpretime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	</tr>
	<tr align="left">
		<td class="">任务实际完成时间：</td>
		<td>${task.finishrealtime }</td>
	</tr>
	<tr align="left">
		<td class="">任务预计完成结果：</td>
		<td>${task.preresult }</td>
	</tr>
	<tr align="left">
		<td class="">任务实际完成结果：</td>
		<td>${task.realresult }</td>
	</tr>
	<tr align="left">
		<td class="">门店名称：</td>
		<td>${task.storeName }</td>
	</tr>
	<tr align="left">
		<td class="">执行人员：</td>
		<td>${task.executeName }</td>
	</tr>
</table>