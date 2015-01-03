<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<table class="addform_wrap">
			<tr>
				<td class="title">类型名称：</td>
				<td>${work.typename}</td>
			</tr>
			<tr>
				<td class="title">类型角色：</td>
				<td>${work.typerole}</td>
			<tr>
				<td class="title">频率：</td>
				<td>
				<c:if test="${work.frequency==1 }">
				每周
				</c:if>
					<c:if test="${work.frequency==2 }">
				月度
				</c:if>
					<c:if test="${work.frequency==3}">
				季度
				</c:if>
				<c:if test="${work.frequency==4}">
				需要时
				</c:if>
				</td>
			</tr>
		<tr>
		  <td class="title">是否问卷类型：</td>
		  <td>
			<c:if test="${work.isExamType==1 }">
			是
			</c:if>
			<c:if test="${work.isExamType==0 }">
			否
			</c:if>
		  </td>
		</tr>
		<tr>
		  <td class="title">是否针对门店：</td>
		  <td>
			<c:if test="${work.isToStore==1 }">
			是
			</c:if>
			<c:if test="${work.isToStore==0 }">
			否
			</c:if>
		  </td>
		</tr>
		</table>
