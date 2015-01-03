<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<style>
 #addBeaconForm .text{width:200px;}
  #addBeaconForm .title{width:200px; word-break: normal;
    word-wrap: normal;}
</style>
<form method="post" id="addBeaconForm">
	<input id="devId" hidden="hidden" name="id" value="${beacon.id}">
	<input id="beaconShopId" hidden="hidden" name="beaconShopId" value="${beacon.beaconShopId}">
	<table class="addform_wrap">
		<tr>
			<td class="title"><b>*</b>设备号：</td>
			<td><input name="uuid" id="uuid" class="text" value="${beacon.uuid}"/></td> 
			<td class="title"><b>*</b>majorId：</td>
			<td><input name="majorId" id="majorId" class="text" value="${beacon.majorId}"/></td> 
		</tr>
		<tr>
			<td class="title"><b>*</b>minorId：</td>
			<td><input name="minorId" id="minorId" class="text"  value="${beacon.minorId}"/></td> 
			<td class="title"><b>*</b>名称：</td>
			<td><input name="name" id="name" class="text"  value="${beacon.name}"/></td> 
		</tr>
		<tr>
			<td class="title"><b>*</b>MAC：</td>
			<td><input name="mac" id="mac" class="text"  value="${beacon.mac}"/></td> 
			<td class="title"><b>*</b>校正值：</td>
			<td><input name="measurrdPower" id="measurrdPower" class="text"  value="${beacon.measurrdPower}"/></td> 
		</tr>
		<tr>
			<td class="title"><b>*</b>信号强度：</td>
			<td><input name="signalStrength" id="signalStrength" class="text"  value="${beacon.signalStrength}"/></td> 
			<td class="title"><b>*</b>电池耐久度：</td>
			<td><input name="remainingPower" id="remainingPower" class="text"  value="${beacon.remainingPower}"/></td> 
		</tr>
		<tr>
			<td class="title"><b>*</b>公司：</td>
			<td><input name="company" id="company" class="text"  value="${beacon.company}"/></td> 
			<td class="title"><b>*</b>型号：</td>
			<td><input name="model" id="model" class="text"  value="${beacon.model}"/></td> 
		</tr>
		<tr>
			<td class="title"><b>*</b>类型：</td>
			<td><input name="mold" id="mold" class="text"  value="${beacon.mold}"/></td> 
			<td class="title"><b>*</b>覆盖距离：</td>
			<td><input name="distance" id="distance" class="text"  value="${beacon.distance}"/></td> 
		</tr>
		<tr>
			<td class="title"><b>*</b>备注：</td>
			<td><input name="remarks" id="remarks" class="text"  value="${beacon.remarks}"/></td> 
			<td class="title"><b>*</b>门店：</td>
			<td align="left">
				<select name="shopid" id="shopid" style="width:250px">
					<option value="-1">---请选择---</option>
					<c:forEach items="${shopNames}" var="shop">
						<option value="${shop.value}">${shop.name}</option>
					</c:forEach>
				</select>
			</td> 
		</tr>
		<tr>
			<td colspan="4" style="padding-left:220px;">
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
	if ($.trim($('#uuid').val()) == '') {
		alert('设备号不能为空!');
		return false;
	}
	if ($.trim($('#majorId').val()) == '') {
		alert('majorId不能为空!');
		return false;
	}
	if ($.trim($('#minorId').val()) == '') {
		alert('minorId不能为空!');
		return false;
	}
	if ($.trim($('#shopid').val()) == '-1') {
		alert('门店不能为空!');
		return false;
	}
	var id = $("#devId").val();
	var url = id ? "${basePath}/beacon/update" : "${basePath}/beacon/add";
	_obj.addClass("big_ddd").removeClass("big_green");
	$("#addBeaconForm").ajaxSubmit({
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
			$("#gridTableBeacon").jqGrid().trigger("reloadGrid");
		}
		
	});
});

$(function(){
	$("#addBeaconForm [name=shopid]").val("${beacon.shopId}");
	$(".big_green").removeClass("big_ddd");
});

</script>


