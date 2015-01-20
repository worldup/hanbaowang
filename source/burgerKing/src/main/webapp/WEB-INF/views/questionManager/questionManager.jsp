<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
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
			<td><label style="font-size:14px;">评估项类型：</label></td>
			<td>
				<select id="questionSel" class="easyui-combobox" name="questionSel" style="width:130px;">
				</select>
			</td>
			<td style="padding-left:20px;"><label style="font-size:14px;">评估项所属模块：</label></td>
			<td >
				<div class="qjstxt_wrap clearfix">
					<input id="ids" class="easyui-combotree"  style="width:200px;">
					<a href="#" id="searchBtn" class="easyui-linkbutton">搜&nbsp;索</a>
					<a href="#" id="resetBtn" class="easyui-linkbutton">重&nbsp;置</a>
				</div>
			</td>
			
		</tr>
	</table>
	<table id="gridTableQuestion"></table>
		<div id="gridPagerQuestion"></div>
</div>
<script type="text/javascript" src="${basePath}/js/jquery-easyui-1.4.1/treeconvert.js"></script>
	<script type="text/javascript" src="${basePath}/js/questionManager/questionManager.js"></script>


<script type="text/javascript">
	$(document).ready(function() {
		var localCache=[];
		$.post("${basePath}"+"/question/questionType",{},function(data){
			$.each(data,function(i, value) {
				localCache.push({ 'label': value.name, 'value': value.value })
			});
			$('#questionSel').combobox({
				data:localCache,
				valueField:'value',
				textField:'label',
				value:'',
				onSelect: function(param){
					$("#gridTableQuestion").jqGrid().trigger("reloadGrid");
				}
			});
			$('#questionSel').combobox('setValue','');

		});

		$("#searchBtn").bind("click",function(){
			$("#gridTableQuestion").setGridParam({page:1}).jqGrid().trigger("reloadGrid");
		});
		$("#resetBtn").bind("click",function(){
			$('#questionSel').combobox('clear');
			$('#ids').combotree('clear');

		});

		$.post("${basePath}/base/getExamUserTreeList",{},function(data){
			var comboData= treeconvert($.parseJSON(data));
			$('#ids').combotree('loadData',comboData);

		});

	});
	
	
</script>
