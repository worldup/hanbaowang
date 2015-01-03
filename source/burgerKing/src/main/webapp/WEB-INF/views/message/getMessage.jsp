<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<table class="addform_wrap">
			
			<tr>
				<td class="title">消息类型：</td>
				<td>
					<c:if test="${message.messageType == 1}">公告</c:if>
					<c:if test="${message.messageType == 2}">紧急</c:if>
					<c:if test="${message.messageType == 3}">任务</c:if>
				</td>
			</tr>
			<tr>
				<td class="title" style="width:100px;">消息标题 ：</td>
				<td>${message.pushTitle}</td>
			</tr>
			<tr>
				<td class="title">消息内容：</td>
				<td style="width:500px;word-break: break-all; word-wrap:break-word;">${message.pushContent}</td>
			</tr>
			<c:if test="${message.messageType == 2}">
				<tr>
					<td class="title">消息状态：</td>
					<td>
						<c:if test="${message.state == 0}">未推送</c:if>
						<c:if test="${message.state == 1}">已推送</c:if>
					</td>
				</tr>
			</c:if>
			<tr>
				<td class="title">创建时间：</td>
				<td><fmt:formatDate value="${message.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<c:if test="${message.messageType == 2}">
				<tr>
					<td class="title">推送时间：</td>
					<td><fmt:formatDate value="${message.pushTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
			</c:if>
			<%-- <tr>
				<td class="title">推送时间：</td>
				<td>${message.pushTime}</td>
			</tr> --%>
			<tr>
				<td class="title">是否读取：</td>
				<td>
					<c:if test="${message.isRead == 0}">未读取</c:if>
					<c:if test="${message.isRead == 1}">已读取</c:if>
				</td>
			</tr>
			<tr>
				<td class="title">消息创建者：</td>
				<td>${message.creater}</td>
			</tr>
			<tr>
				<td class="title">消息发送者：</td>
				<td>${message.sender}</td>
			</tr>
			<tr>
				<td class="title">消息接收者：</td>
				<td>${message.receiver}</td>
			</tr>
<!-- 			<tr> -->
<!-- 				<input type="button" value="关闭" onclick="closeOutWindow()" /> -->
<!-- 			</tr> -->
		</table>
