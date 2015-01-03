<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<table class="addform_wrap">
			<tr>
				<td class="title">名称：</td>
				<td>${exam.name}</td>
			</tr>
			<tr id="timerTr">
				<td class="title">时长：</td>
				<td>${exam.timer}</td>
			</tr>
			<tr id="dateTr">
				<td class="title">时间：</td>
				<td>${exam.date}</td>
			</tr>
			<tr id="numTr">
				<td class="title">编号：</td>
				<td>${exam.num}</td>
			</tr>
			<tr>
				<td class="title">类型：</td>
				<td>
				<c:if test="${exam.type==1 }">
				DVD LIST
				</c:if>
					<c:if test="${exam.type==2 }">
				营运警戒列表
				</c:if>
				</td>
			</tr>
		</table>
<script type="text/javascript">
$(function(){
	var _type = '${exam.type}';
	showTr(_type);
});
function showTr(sval){
	if(sval=="1"){
		$("#timerTr").show();
		$("#dateTr").hide();
		$("#numTr").hide();
	}
	if(sval=="2"){
		$("#timerTr").hide();
		$("#dateTr").show();
		$("#numTr").show();
	}
}
</script>