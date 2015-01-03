<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<table class="addform_wrap">
			
			<tr>
				<td class="title">问题类型：</td>
				<td>${question.questionType}</td>
			</tr>
			<tr>
				<td class="title">编号：</td>
				<td>${question.serialNumber}</td>
			</tr>
			<tr>
				<td class="title" style="width:100px;">所属模块：</td>
				<td>${question.modelName}</td>
			</tr>
			<tr>
				<td class="title">问题描述：</td>
				<td style="width:500px;word-break: break-all; word-wrap:break-word;">${question.qcontent}</td>
			</tr>
			<tr>
				<td class="title">分数：</td>
				<td>${question.qvalue}</td>
			</tr>
			<tr>
				<td class="title">备注：</td>
				<td>${question.qremark}</td>
			</tr>
			<tr>
				<td class="title">是否上传图片：</td>
				<td>
				<c:if test="${question.isUploadPic==1 }">
				是
				</c:if>
					<c:if test="${question.isUploadPic==2 }">
				否
				</c:if>
				</td>
			</tr>
<!-- 			<tr> -->
<!-- 				<input type="button" value="关闭" onclick="closeOutWindow()" /> -->
<!-- 			</tr> -->
		</table>
