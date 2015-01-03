<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<table class="addform_wrap">
			<tr>
				<td class="title">上级：</td>
				<td>${base.pvalue}</td>
			</tr>
			<tr>
				<td class="title">基本类型：</td>
				<td>${base.bvalue}</td>
			</tr>
			<tr>
		<td class="title">是否筛选：</td>
		<td>
			<c:if test="${base.brushElection==0 }">
			否
			</c:if>
				<c:if test="${base.brushElection==1 }">
			是
				</c:if>
		</td>
		</tr>
			<tr>
				<td class="title">描述：</td>
				<td>${base.bcomments}</td>
			</tr>
		<tr>
		<td class="title">是否关键项：</td>
		<td>
			<c:if test="${base.isKey==0 }">
			否
			</c:if>
				<c:if test="${base.isKey==1 }">
			是
				</c:if>
		</td>
		</tr>
		</table>
		
		
		
