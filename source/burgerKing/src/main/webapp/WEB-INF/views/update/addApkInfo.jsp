<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />

<form method="post" id="addUpdateFrom">
	<input id="id" type="hidden" name="id" value="${update.id}">
	<table class="addform_wrap">
		<tr>
			<td class="title"><b>*</b>版本号：</td>
			<td><input name="versionNum" id="versionNum" class="text" value="${update.versionNum}"/></td> 
		</tr>
		<tr>
			<td class="title"><b>*</b>APK名称：</td>
			<td><input name="apkName" id="apkName" class="text" value="${update.apkName}"/></td> 
		</tr>
		<tr>
			<td class="title"><b>*</b>APK文件：</td>
			<td>
				<input type="file" onchange="checkFileApkSuffix('apkfile')" 
					id="apkfile" name="apkfile"
					class="files"/><span id="info"></span>
				<input name="url" id="url" type="hidden"  value="${update.url}"/>
				<input type="hidden" name="apkSize" id="apkSize" value="${update.apkSize}"/>
			 </td>
		</tr>
		<%-- <tr>
			<td class="title"><b>*</b>服务端url：</td>
			<td><input name="url" id="url" class="text"  value="${update.url}"/></td> 
		</tr> --%>
		<tr>
			<td class="title"><b>*</b>备注：</td>
			<td><input name="remark" id="remark" class="text"  value="${update.remark}"/></td> 
		</tr>
		<%-- <tr>
			<td class="title"><b>*</b>更新时间：</td>
			<td><input name="updateTime" id="updateTime" class="text"  value="${update.updateTime}"/></td> 
		</tr> --%>
		<tr>
			<td class="title"></td>
			<td>
				<div class="qjact_btn clearfix">
					<a href="javascript:void(0)" onclick="closeOutWindow()" class="big_ea"><span>取消</span></a>
					<a href="javascript:void(0)"  class="big_green" id="submit"><span>发布</span></a>
				</div>
			</td>
		</tr>
	</table>
<!-- 	<input type="submit" value="提交" id="submit"> -->
	
</form>

<script type="text/javascript">
	var apkfile = "${update.url}";
	if(apkfile != ""){
		document.getElementById("info").innerHTML = "<font color='#02335b'>&nbsp;已有文件！</font>";
	}
		function checkFileApkSuffix(fileId) {
			document.getElementById("info").innerHTML = "";
// 			$.trim($("#url").val(""));
			$.trim($("#apkSize").val(""));
			var inputComp = document.getElementById(fileId);
			var fileExt = inputComp.value.substr(inputComp.value.lastIndexOf(".")).toLowerCase();
			if (!(fileExt == ".apk")) {
				document.getElementById(fileId).outerHTML += '';//清空IE的
				document.getElementById(fileId).value = "";//可以清空火狐的
				alert("支持文件格式apk");
				return false;
			}
		}

		$("#submit").bind("click",function(){
			//避免重复提交
			var _obj = $(this),_cname = _obj.prop("class");
			if(_cname.indexOf("big_ddd")>=0){
				return;
			}
			if ($.trim($('#versionNum').val()) == '') {
				alert('版本号不能为空!');
				return false;
			}
			if ($.trim($('#apkName').val()) == '') {
				alert('apk名称不能为空!');
				return false;
			}
			if($.trim($('#url').val()) == ''){
				if($.trim($("#apkfile").val()) == ''){
			  		alert("请选择文件");
			  		$("#apkfile").focus();
			  		return false;
			   	}
			}
			if ($.trim($('#remark').val()) == '') {
				alert('备注信息不能为空!');
				return false;
			}
			var id = $("#id").val();
		 	var url = id ? "${basePath}/update/updateApkInfo" : "${basePath}/update/addApkInfo";
			_obj.addClass("big_ddd").removeClass("big_green");
			$("#addUpdateFrom").ajaxSubmit({
				url : url,
				type : "POST",
				dataType : "json",
				success : function(data){
					if(id == ''){
						alert('发布成功!');
					}else{
						alert('修改成功!');
					}
					closeOutWindow();
					$("#gridTableUpdate").jqGrid().trigger("reloadGrid");
				}
				
			});
		});

$(function(){

// 	$("#addUpdateFrom [name=shopid]").val("${beacon.shopId}");
	$(".big_green").removeClass("big_ddd");
});

</script>


