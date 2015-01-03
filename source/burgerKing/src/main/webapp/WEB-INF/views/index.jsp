<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@page import="com.upbest.mvc.entity.Buser"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />

 <%
   if(session.getAttribute("buser")!=null){
       Buser u = (Buser)session.getAttribute("buser");
   }else{
   	request.getRequestDispatcher("/toLogin").forward(request, response);
   	return;
   }
%> 
<script type="text/javascript">
var basePath="${basePath}";
function getThisTime(){
	var now = new Date();
	var year = now.getFullYear();       //年
	var month = now.getMonth() + 1;     //月
	var day = now.getDate();            //日
	
	var hh = now.getHours();            //时
	var mm = now.getMinutes();          //分
	var ss = now.getSeconds();  
	var clock = year + "-";
	
	if(month < 10)
	    clock += "0";
	
	clock += month + "-";
	
	if(day < 10)
	    clock += "0";
	   
	clock += day + " ";
	
	if(hh < 10)
	    clock += "0";
	   
	clock += hh + ":";
	if (mm < 10) clock += '0'; 
	clock += mm+":"; 
	if (ss < 10) clock += '0'; 
	clock += ss; 
	$("#thisTime").html("当前时间："+clock);
}
setInterval(function(){
	getThisTime();
},1000);

$(function(){
	var _bodyh = $(document).height();
	var _indexbgh = _bodyh - 63 - 48;
	$(".indexbg").height(_indexbgh);
	$(window).resize(function(){
		var _bodyh = $(document).height();
		var _indexbgh = _bodyh - 63 - 48;
		$(".indexbg").height(_indexbgh);
	});
})
</script>

<div class="dzwidth_960 index" style="padding:0;position:relative;">
	<div class="indexbg">
		<div class="index_wrap">
			<p id="thisTime" class="thitime">当前时间：2014-09-23 19:55:30</p>
		</div>
	</div>
</div>

