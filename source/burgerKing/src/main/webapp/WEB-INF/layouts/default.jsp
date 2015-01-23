<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@page import="com.upbest.mvc.entity.Buser"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<fmt:requestEncoding value="UTF-8" />
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
<title>汉堡王</title>
<META http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link href="${basePath}/css/base.css" rel="stylesheet" type="text/css" />
<link href="${basePath}/css/main.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/css/jquery-ui-themes-1.10.1/themes/redmond/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css"
	href="${basePath}/css/ui.jqgrid.css" />
	<link rel="stylesheet" type="text/css" href="${basePath}/js/jquery-easyui-1.4.1/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/js/jquery-easyui-1.4.1/themes/icon.css">
<script src="${basePath}/js/jquery-easyui-1.4.1/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${basePath}/js/jquery-ui-1.11.2/jquery-ui.min.js"></script>
<script src="${basePath}/js/common.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${basePath}/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery.jqGrid.src.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${basePath}/js/jquery.json-2.4.min.js"></script>
<script type="text/javascript"
	src="${basePath}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>


<script>
	var imagePath = "${basePath}" + "/images";
	var basePath = "${basePath}";
</script>
<script type="text/javascript" src="${basePath}/js/tipswindown.js"></script>
</head>

<body class="dzmain_body">
	<%-- 	<jsp:include page="/WEB-INF/layouts/common.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/layouts/commonDiv.jsp"></jsp:include> --%>
	<%
	    String name = "";
				if (session.getAttribute("buser") != null) {
					Buser u = (Buser) session.getAttribute("buser");
					name = u.getRealname();
				}
	%>
	<div class="dz_body">
		<div class="dz_around">
			<!--header begin-->
			<div class="dz_header">
				<div class="dz_logo">
					<img src="${basePath}/images/tit_gys.png" />
				</div>
				<div class="login_user">
					<span><%=name%>，欢迎访问巡检管理平台</span><a href="${basePath}/logout">退出</a>
				</div>
			</div>
			<!--header end-->
			<!--subheader begin-->
			<div class="dzsub_header">
				<div class="sub_dhmwrap">
					<ul class="sub_dhmenu clearfix">
						<li class="md"><a href="javascript:void(0)"
							<c:if test="${current=='store'||current=='shopStatistic'}" >class="current"</c:if>><b>门店管理</b></a></li>
						<c:if test="${sessionScope.buser.role ne '2' }">
							<li class="user"><a href="${basePath}/user/index"
								<c:if test="${current=='user'}"> class="current"</c:if>><b>用户管理</b></a></li>
							<li class="kq"><a href="${basePath}/sing/index"
								<c:if test="${current=='sing'}"> class="current"</c:if>><b>考勤管理</b></a></li>
							<li class="rw"><a href="javascript:void(0)"
								<c:if test="${current=='work'||current=='task'}"> class="current"</c:if>><b>任务管理</b></a></li>
							<li class="sheb"><a href="javascript:void(0)"
								<c:if test="${current=='facility'||current=='beacon'}"> class="current"</c:if>><b>设备管理</b></a></li>
							<li class="xxts"><a href="${basePath}/message/index"
								<c:if test="${current=='message'}"> class="current"</c:if>><b>消息推送</b></a></li>
							<li class="tj"><a href="${basePath}/statistic/index"
								<c:if test="${current=='statistic'}"> class="current"</c:if>><b>统计管理</b></a></li>
							<li class="wj"><a href="javascript:void(0)"
								<c:if test="${current=='base'||current=='questionManager'||current=='examManager'||current=='addExamination'||current=='examPageDetail'||current=='userAnswerInfo'||current=='questionTypeManager'||current=='exam'||current=='losePerSts'}"> class="current"</c:if>><b>评估表管理</b></a></li>
							<li class="tool"><a href="javascript:void(0)"
									<c:if test="${current=='toolbox'}"> class="current"</c:if>><b>工具箱</b></a></li>
							<li class="fbgx"><a href="${basePath}/update/index"
								<c:if test="${current=='update'}"> class="current"</c:if>><b>发布更新</b></a></li>
						</c:if>
					</ul>
				</div>
				<div class="subchild_menu rw_sbmenu">
					<ul>
						<li><a href="${basePath}/work/index"
							<c:if test="${current=='work'}"> class="current"</c:if>>类型管理</a></li>
						<li class="current"><a href="${basePath}/task/index"
							<c:if test="${current=='task'}"> class="current"</c:if>>任务管理</a></li>
						<li class="last"><a href="${basePath}/task/commonwords/index"
								<c:if test="${current=='commonwords'}"> class="current"</c:if>>常用语管理</a></li>
					</ul>
				</div>
				<div class="subchild_menu tool_sbmenu">
					<ul>
						<li><a href="${basePath}/toolbox/index"
								<c:if test="${current=='toolbox'}"> class="current"</c:if>>工具箱上传</a></li>

					</ul>
				</div>
				<div class="subchild_menu wj_sbmenu" style="width:118px">
					<ul>
						<li><a href="${basePath}/questionType/index"
							<c:if test="${current=='questionTypeManager'}"> class="current"</c:if>>评估项类别管理</a></li>
						<li><a href="${basePath}/question/index"
							<c:if test="${current=='questionManager'}"> class="current"</c:if>>评估项管理</a></li>
						<li><a href="${basePath}/base/index"
							<c:if test="${current=='base'}"> class="current"</c:if>>评估表类别管理</a></li>
						<li><a href="${basePath}/examManager/index"
							<c:if test="${current=='examManager'||current=='addExamination'||current=='examPageDetail'||current=='userAnswerInfo'}"> class="current"</c:if>>评估表管理</a></li>
						<%--<!--<li><a href="${basePath}/exam/index"--%>
							<%--<c:if test="${current=='exam'}"> class="current"</c:if>>试卷参照表</a></li>-->--%>
						<li class="last"><a href="${basePath}/examManager/loseSts/index"
							<c:if test="${current=='losePerSts'}"> class="current"</c:if>>评估表失分率统计</a></li>
					</ul>
				</div>
				<div class="subchild_menu md_sbmenu">
					<ul>
					<li ><a href="${basePath}/store/index"
							<c:if test="${current=='store'}"> class="current"</c:if>>门店管理</a></li>
					<c:if test="${sessionScope.buser.role ne '2' }">
						<li class="last"><a href="${basePath}/shopStatistic/index"
								<c:if test="${current=='shopStatistic'}"> class="current"</c:if>>门店统计</a></li>
					</c:if>
					<li class="last"><a href="${basePath}/store/mapIndex"
							<c:if test="${current=='storeMap'}"> class="current"</c:if>>门店信息</a></li>
						<li class="last"><a href="${basePath}/store/getLngLat"
							<c:if test="${current=='getLngLat'}"> class="current"</c:if>>门店经纬度获取</a></li>

					</ul>
				</div>
				<div class="subchild_menu sheb_sbmenu">
					<ul>
						<li><a href="${basePath}/facility/index"
							<c:if test="${current=='facility'}"> class="current"</c:if>>PAD管理</a></li>
						<li class="last"><a href="${basePath}/beacon/index"
							<c:if test="${current=='beacon'}"> class="current"</c:if>>beacon管理</a></li>
						<%-- <li class="last"><a href="${basePath}/date/index"
							<c:if test="${current=='date'}"> class="current"</c:if>>日历</a></li>	 --%>
					</ul>
				</div>
			</div>
			<div class="dzwidth_960" style="padding-bottom: 0;">
				<div id="nav" style="font-size: 14px;"></div>
			</div>
			<!--content begin-->

			<sitemesh:body />

			<!--content end-->
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		showNavigater();
	});
</script>
</html>