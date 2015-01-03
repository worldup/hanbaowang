<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
		<table class="addform_wrap">
			<tr>
				<td class="title">版本号：</td>
				<td>${up.versionNum}</td>
			</tr>
			<tr>
				<td class="title">APK名称：</td>
				<td>${up.apkName}</td>
			</tr>
			<tr>
				<td class="title">文件大小：</td>
				<td>${up.apkSize}</td>
			</tr>
			<tr>
				<td class="title">服务端url：</td>
				<td>${up.url}</td>
			</tr>
			<tr>
				<td class="title">备注：</td>
				<td>${up.remark}</td>
			</tr>
			<tr>
				<td class="title">更新时间：</td>
				<td>${up.updateTime}</td>
			</tr>
			<tr>
			<td class="title"></td>	
			<td>
			<input id="url" type="hidden" value="${up.url}" />
			<div class="qjact_btn clearfix">
					<a href="javascript:void(0)" onclick="closeOutWindow()" class="big_ea"><span>取消</span></a>
					<a href="javascript:void(0)"  class="big_green" id="btn_ok"><span>下载</span></a>
			</div>
			
			</td>
			</tr>
		</table>
<script>
	$(function() {
		$("#btn_ok").click(function() {
			var fileurl = document.getElementById("url").value;
			window.location.href=basePath
			+ "/update/examport?filename="
			+ fileurl;
		
		});
	});
</script>