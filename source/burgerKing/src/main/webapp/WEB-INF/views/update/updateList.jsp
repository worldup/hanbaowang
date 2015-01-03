<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<title>汉堡王首页</title>
<div class="dzwidth_960 store">
	<table class="qjabtn_wrap" id="searchDiv">
		<tr>
			<td>
				<div class="qjact_btn clearfix">
					<a class="green" name="addUpdate" id="addUpdate" onclick="addUpdate();" href="javascript:void(0)"><span>新增</span></a>
				</div>
			</td>
			<td>
				<div class="qjstxt_wrap clearfix">
					<input type="text" class="text" name="version" id="version"/>
					<label class="defalut_val" for="version">根据版本号查询</label>
					<input type="button" value="搜&nbsp;索" id="searchBtn" class="search" />
				</div>
			</td>
		</tr>
	</table>
	<table id="gridTableUpdate"></table>
	<div id="gridPageUpdate"></div>
</div>

<script type="text/javascript" src="${basePath}/js/update/updateList.js"></script>
