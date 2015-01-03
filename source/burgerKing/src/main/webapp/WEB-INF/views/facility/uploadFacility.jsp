<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<form id="form1" method="post">
	<table class="addform_wrap">
		<tr>
			<td class="title"><b>*</b>请选择要导入的文件：</td>
			<td><input type="file" onchange="checkFileSuffix('storeFile')"
				id="storeFile" name="facilityFile" class="files"></td>
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
		if($.trim(url)=="" ){
			alert("请选择要导入的文件");
			return false;
		}
		var options = {
			url : "${basePath}/facility/toImport",
			dataType : "text",
			type : 'POST',
			success : function(data, status) {
				data = $.parseJSON(data);
				if (data.success) {
					alert("批量导入成功");
					closeOutWindow();
				} else {
					alert("批量导入失败");
				}
				$("#gridTableFacility").jqGrid().trigger("reloadGrid");
			}
		};
		var form = $('#form1');
		form.attr("enctype", "multipart/form-data");
		$('#form1').ajaxSubmit(options);
	}
</script>
