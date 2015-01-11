<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${basePath}/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${basePath}/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="${basePath}/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}/js/uploadify/uploadify.css">
<script type="text/javascript" src="${basePath}/js/uploadify/jquery.uploadify.js"></script>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<style>
 #storeForm .text{width:200px;}
  #storeForm .title{width:120px;}
 .uploadify-queue{
	 height:300px;
	 width:600px;
 }
</style>


<form id="form1" method="post" style="height: 100px">
<table class="addform_wrap" id="storeForm" style="width:900px;height: 100px ">
	<tr >
		<td  width="400px" >

			<input type="file" name="file_upload" id="file_upload" />
		
		</td>
		<td width="200">优先级：
			<select id="selPriority" name="selPriority">
				<option value="0">低</option>
				<option value="1">中</option>
				<option value="2">高</option>
			</select>
		<td>
		<!--<td width="300px"><b>*</b>标题：<input type="text" id="originalName"
			name="originalName" class="text" /></td>
		<td>-->
			<input class="search" style="padding:5px 15px;"type="button" value="保存" id="btn_save">
		</td>
	</tr>

</table>
 	</form>


<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>
<script type="text/javascript">
	$(function(){
		$('#file_upload').uploadify({
			'swf'      :'${basePath}/js/uploadify/uploadify.swf',
			'uploader':'${basePath}/toolKit/addToolKitFiles',
//			'fileTypeExts':'*.pdf,*.xlsx,*.mp4,*.wmv',
			'fileTypeDesc':'*.pdf,*.xlsx,*.mp4,*.wmv,*.PDF,*.XLSX,*.MP4,*.WMV',
			'formData':{
				"ids":""
			},
			'successTimeout':300,
			'debug':false,
			'auto':false,
			'buttonText':'文件选择(支持批量)',
			'method':'post',
			onSelect:function(file){
//				var f_type=file.type;
//				var f_name=file.name;
//				var title=f_name.substr(0,f_name.indexOf(f_type));
//				$("#originalName").val(title);
			},
			onUploadStart : function(file) {
//				var  originalName = $.trim($('input[name=originalName]').val());
				var  priority= $("#selPriority").val();
				$("#file_upload").uploadify("settings", "formData", {'priority':priority});
			},
			onUploadError:function(file){
				alert('添加失败!');
			},
			onUploadSuccess:function(file){
				alert('添加成功!');
			}

		});
	}())


	$("#btn_save").bind("click", function() {
		//避免重复提交
		var _obj = $(this), _cname = _obj.prop("class");
		if (_cname.indexOf("big_ddd") >= 0) {
			return;
		}
//		if ($.trim($('#originalName').val()) == '') {
//			alert('请输入标题');
//			return false;
//		}
		$("#file_upload").uploadify('upload', '*');
	});
	

	
</script>
