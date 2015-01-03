<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />

<form method="post" id="addFacilityForm">
	<input id="devId" type="hidden" name="id" value="${facility.id}">
	<table class="addform_wrap">
		<tr>
			<td class="title"><b>*</b>标识号：</td>
			<td><input name="deviceid"  class="text" value="${facility.deviceid}"/></td> 
		</tr>
		<tr>
			<td class="title"><b>*</b>品牌：</td><td><input name="devicebrand"  class="text" value="${facility.devicebrand}"/></td> 
		</tr>
		<tr>
			<td class="title"><b>*</b>型号：</td><td><input name="devicemodel"  class="text"  value="${facility.devicemodel}"/></td> 
		</tr>
		<tr>
			<td class="title"><b>*</b>版本：</td><td><input name="deviceversion"  class="text"  value="${facility.deviceversion}"/></td> 
		</tr>
		<tr>
			<td class="title"><b>*</b>分辨率：</td><td><input name="deviceresolution"  class="text"  value="${facility.deviceresolution}"/></td> 
		</tr>
		<tr>
			<td class="title"><b>*</b>操作系统：</td><td><input name="deviceos"  class="text" value="${facility.deviceos}"/></td> 
		</tr>
		<tr>
			<td class="title"><b>*</b>操作系统版本：</td><td><input name="deviceosversion" class="text"  value="${facility.deviceosversion}"/></td> 
		</tr>
		<tr>
			<td class="title"><b>*</b>用户：</td>
			<td align="left">
				<select name="userid" >
					<option value="-1">---请选择---</option>
					<c:forEach items="${users}" var="user">
						<option value="${user.value}">${user.name}</option>
					</c:forEach>
				</select>
			</td> 
		</tr>
		<tr>
			<td class="title"></td>
			<td>
				<div class="qjact_btn clearfix">
					<a href="javascript:void(0)" onclick="closeOutWindow()" class="big_ea"><span>取消</span></a>
					<a href="javascript:void(0)"  class="big_green" id="submit"><span>保存</span></a>
				</div>
			</td>
		</tr>
	</table>
<!-- 	<input type="submit" value="提交" id="submit"> -->
	
</form>

<script type="text/javascript">
$("#submit").bind("click",function(){
	//避免重复提交
	var _obj = $(this),_cname = _obj.prop("class");
	if(_cname.indexOf("big_ddd")>=0){
		return;
	}
	
	if($.trim($('input[name=deviceid]').val())==''){
		alert('请输入标识号!');
		return false;
	}
	if($.trim($('input[name=devicebrand]').val())==''){
		alert('请输入品牌!');
		return false;
	}
	if($.trim($('input[name=devicemodel]').val())==''){
		alert('请输入型号!');
		return false;
	}
	if($.trim($('input[name=deviceversion]').val())==''){
		alert('请输入版本!');
		return false;
	}
	if($.trim($('input[name=deviceresolution]').val())==''){
		alert('请输入分辨率!');
		return false;
	}
	if($.trim($('input[name=deviceos]').val())==''){
		alert('请输入操作系统!');
		return false;
	}
	if($.trim($('input[name=deviceosversion]').val())==''){
		alert('请输入操作系统版本!');
		return false;
	}
	if($.trim($('select[name=userid]').val())=='-1'){
		alert('请选择用户!');
		return false;
	}
	var id = $("#devId").val();
	var url = id ? "${basePath}/facility/update" : "${basePath}/facility/add";
	_obj.addClass("big_ddd").removeClass("big_green");
	$("#addFacilityForm").ajaxSubmit({
		url : url,
		type : "POST",
		dataType : "json",
		success : function(data){
			if(id==''){
				alert('添加成功!');
			}else{
				alert('修改成功!');
			}
			closeOutWindow();
			$("#gridTableFacility").jqGrid().trigger("reloadGrid");
		}
		
	});
});

$(function(){
	$("#addFacilityForm [name=userid]").val("${facility.userid}");
	$(".big_green").removeClass("big_ddd");
});

</script>


