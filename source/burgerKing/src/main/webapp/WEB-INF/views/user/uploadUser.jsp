<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<form id="form1" method="post">
	<table class="addform_wrap">
		<tr>
			<td class="title"><b>*</b>请选择要导入的文件：</td>
			<td><div class="clearfix"><input type="file" onchange="checkFileSuffix('storeFile')"
				id="storeFile" name="userFile"
				class="files"></div>
				<p class="tips">上传文件的数据，请按照"角色"这一列职位由高到低排序。
				</p>
				</td>
		</tr>
		<tr id="loading" style="display:none">
			<td  colspan="2" class="loading_wrap" style="text-align:center;">
				<img src="${basePath}/images/loading_b.gif" width="32" height="32"/>&nbsp;&nbsp;&nbsp;数据正在导入请稍后。。。
			</td>
		</tr>
		<tr>
			<td class="title"></td>
			<td>
				<div class="qjact_btn clearfix">
					<a href="javascript:void(0)" onclick="closeOutWindow()"
						class="big_ea"><span>取消</span></a> <a href="javascript:void(0)"
						class="big_green" id="btn_save" onclick="ajaxS();"><span>保存</span></a>
				</div>
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>
<script>
$(function(){
	$("#btn_save").removeClass("big_ddd");
});
	function checkFileSuffix(fileId) {
		var inputComp = document.getElementById(fileId);
		var fileExt = inputComp.value.substr(inputComp.value.lastIndexOf("."))
				.toLowerCase();
		if (!(fileExt == ".xls") && !(fileExt == ".xlsx")) {
			document.getElementById(fileId).outerHTML += '';//清空IE的
			document.getElementById(fileId).value = "";//可以清空火狐的
			alert("支持文件格式xls、xlsx");
			return false;
		}
	}
	function ajaxS() {
		var url = $("#storeFile").val();
		var _obj = $("#btn_save"),_cname = _obj.prop("class");
		if(_cname.indexOf("big_ddd")>=0){
			return false;
		}
		if($.trim(url)=="" ){
			alert("请选择要导入的文件");
			return false;
		}
		$("#btn_save").addClass("big_ddd").removeClass("big_green");
		$("#loading").show();
		var options = {
			url : "${basePath}/user/toImport",
			dataType : "text",
			type : 'POST',
			success : function(data, status) {
				$("#loading").hide();
				$("#btn_save").removeClass("big_ddd");
				data = $.parseJSON(data);
				if (data.success) {
					alert("批量导入成功");
					closeOutWindow();
					
				} else {
					alert("批量导入失败");
				}
				$("#gridTableUser").jqGrid().trigger("reloadGrid");
			}
		};
		var form = $('#form1');
		form.attr("enctype", "multipart/form-data");
		$('#form1').ajaxSubmit(options);
	}
</script>
