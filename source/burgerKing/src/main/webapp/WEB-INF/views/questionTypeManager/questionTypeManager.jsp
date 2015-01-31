<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<div class="dzwidth_960 shopstatistic">
	<table class="qjabtn_wrap" id="searchDiv">
		<tr>
			<td>
				<div class="qjact_btn clearfix">
					<a class="green" name="addQuestion" id="addQuestion" onclick="addQuestion();" href="javascript:void(0)"><span>新增</span></a>

				</div>
			</td>
			
			<td><label style="font-size:14px;">台头/问题类型标识：</label></td>
			<td>
				<select id="questionSel" class="easyui-combobox" name="questionSel" style="width:120px;">
					<option value="">--请选择--</option>
					<option value="1">台头</option>
					<option value="2">问题</option>
				</select>
			</td>
			<td  style="padding-left:20px;"><label style="font-size:14px;">字段名称：</label></td>
			<td>
				<div class="qjstxt_wrap clearfix">
					<input name="hValueSer" id="hValueSer" class="easyui-textbox" data-options="prompt:'输入字段名称'" style="width:200px">
					<a href="#" id="searchBtn" class="easyui-linkbutton">搜&nbsp;索</a>
				</div>
			 
			</td>
		</tr>
	</table>
	<table id="gridTableQuestion"></table>
		<div id="gridPagerQuestion"></div>
</div>
	<script type="text/javascript"
		src="${basePath}/js/questionTypeManager/questionTypeManager.js"></script>
	<script type="text/javascript">
</script>
<script type="text/javascript">
	$(function() {
		$("#searchBtn").bind("click",function(){
			$("#gridTableQuestion").setGridParam({page:1}).jqGrid().trigger("reloadGrid");
		});
		$('#questionSel').combobox({
			onSelect: function(param){
				$("#gridTableQuestion").jqGrid().trigger("reloadGrid");
			}
		});
		$('#questionSel').combobox('setValue','');
	});
	
	
</script>
