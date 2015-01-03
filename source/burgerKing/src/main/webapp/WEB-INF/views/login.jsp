<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${basePath}/css/base.css" rel="stylesheet" type="text/css" />
<link href="${basePath}/css/main.css" rel="stylesheet" type="text/css" />
<script src="${basePath}/js/jquery-1.8.2.min.js" type="text/javascript"></script>
<script src="${basePath}/js/common.js" type="text/javascript"></script>
<script>
var basePath="${basePath}";
</script>
</head>
<body class="login_body">
		<div class="main_login">
			<div class="application">
		        <div class="application-content">
		        	<span><img src="${basePath}/images/logo.jpg" width="80px" height="80px" />汉堡王巡检管理系统</span>
		        </div>
		    </div>
			<div class="loginbg">
				<div class="caret"></div>
				<div class="login_txt">
					<h1 class="text-center">登录</h1>
					<div class="ltxt_wrap">
						<div class="icon_ltxt">
							<input type="text" name="name" 
							id="name" /><i class="icon-user muted"></i>
							<span class="defalut_val">用户名</span>
						</div>
					</div>
					<div class="ltxt_wrap">
						<div class="icon_ltxt">
							<input type="password" name="pwd" id="pwd"  /><i class="icon-lock muted"></i>
							<span class="defalut_val">密码</span>
						</div>
					</div>
					<p class="logbtn">
						<input type="submit" id="btnLogin" value="登录"/>
						<input type="button" id="btnReset" value="重置" /> 
					</p>
				</div>
			</div>
		</div>
		<script src="${basePath}/js/login/login.js" type="text/javascript"></script>
</body>

</html>
