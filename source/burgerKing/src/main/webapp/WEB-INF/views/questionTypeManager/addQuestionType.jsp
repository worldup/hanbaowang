<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
	<form id="form1" method="post">
		<input id="questionId" hidden="hidden" name="id" value="${question.id}">
		<table class="addform_wrap">
			<tr>
				<td class="title"><b>*</b>字段名称：</td>
				<td align="left">
					<input id="hValue" name="hValue" class="text" value="${question.hValue}"/>
				</td>
			</tr>
			<tr>
				<td class="title"><b>*</b>字段类型：</td>
				<td style="">
				 <select id="hType" name="hType" >
				   <option value="">--请选择--</option>
				   <option value="1">填空题</option>
				   <option value="2">选择题</option>
				   <option value="3">简答题</option>
				   <option value="4">日期</option>
				   <option value="5">下拉</option>
				   <option value="6">评估人</option>
				   <option value="7">置顶</option>
				   <option value="8">上次稽核成绩</option>
				   <option value="9">成绩</option>
				   <option value="10">重复扣分项编号</option>
				    <option value="11">时间</option>
				    <option value="12">地区</option>
				 </select>
				</td>
			</tr>
			<tr>
				<td class="title"><b>*</b>台头或者问题标识：</td>
				<td>
				 <select id="type" name="type">
				   <option value="">--请选择--</option>
				   <option value="1">台头</option>
				   <option value="2">问题</option>
				 </select>
				</td>
			</tr>
			<tr>
				<td class="title"></td>
				<td>
					<div class="qjact_btn clearfix">
						<a href="javascript:void(0)" onclick="closeOutWindow()" class="big_ea"><span>取消</span></a>
						<a href="javascript:void(0)"  class="big_green" id="btn_save"><span>保存</span></a>
					</div>
				</td>
			</tr>
		</table>
		
	</form>
<script type="text/javascript">
$(document).ready(function(){
	$(".big_green").removeClass("big_ddd");
	$('#hType').val('${question.hType}');
	$('#type').val('${question.type}');
});
$("#btn_save").bind("click",function(){
	var _obj = $(this),_cname = _obj.prop("class");
	if(_cname.indexOf("big_ddd")>=0){
		return;
	}
	if($.trim($('#hValue').val()) == ''){
		alert("请输入字段名称！");
		$("#hValue").focus();
		return false;
	}
	if($.trim($('#type').val()) == ''){
		alert("请选择台头或者问题标识！");
		$("#type").focus();
		return false;
	}
	if($.trim($('#hType').val()) == ''&&$.trim($('#type').val())=='1'){
		alert("请选择字段类型！");
		$("#hType").focus();
		return false;
	}
	
	var id = $("#questionId").val();
	var url = id ? "${basePath}/questionType/update" : "${basePath}/questionType/add";
	_obj.addClass("big_ddd").removeClass("big_green");
	var options = {
		url : url,
		type : 'post',
		dataType : "html",
		error : function(err) {
			alert("系统出错了，请联系管理员！");
		},
		success : function(data) {
			if(id==''){
				alert('添加成功!');
			}else{
				alert('修改成功!');
			}
				closeOutWindow();
			$("#gridTableQuestion").jqGrid().trigger("reloadGrid");
		}
	};
	var form = $('#form1');
	form.attr("enctype", "multipart/form-data");
	$('#form1').ajaxSubmit(options);
});
</script>
