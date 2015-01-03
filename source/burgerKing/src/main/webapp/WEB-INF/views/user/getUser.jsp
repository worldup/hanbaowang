<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<table class="addform_wrap">
			<tr>
				<td class="title">用户登录名：</td>
				<td>${user.name}</td>
			</tr>
			<tr>
				<td class="title">所属角色：</td>
				<td>
				<c:if test="${user.role==0 }">
				超级管理员
				</c:if>
				<c:if test="${user.role==1 }">
				OM
				</c:if>
					<c:if test="${user.role==2 }">
				OC
				</c:if>
					<c:if test="${user.role==3 }">
				OM+
				</c:if>
				</td>
			</tr>
			
			<tr>
				<td class="title">创建时间：</td>
				<td><fmt:formatDate value="${user.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr>
				<td class="title">更新时间：</td>
				<td><fmt:formatDate value="${user.modifydate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr>
				<td class="title">真实姓名：</td>
				<td>${user.realname}</td>
			</tr>
			<tr>
				<td class="title">手机号：</td>
				<td>${user.telephone}</td>
			</tr>
			<tr>
				<td class="title">职工号：</td>
				<td>${user.emp}</td>
			</tr>
		</table>
<script>
	var createdate = "${user.createdate}";
	
</script>