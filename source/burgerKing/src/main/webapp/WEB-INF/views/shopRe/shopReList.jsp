<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${basePath}/js/uploadify/uploadify.css"
     rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${basePath}/js/uploadify/jquery.uploadify.min.js"></script>
<form id="form1" method="post">
	<table class="addform_wrap">
		<tr>
			<td class="title"><b>*</b>月度：</td>
			<td align="left"><input type="text" id="mon"
				name="mon" class="Wdate input_w150"
				onClick="WdatePicker({dateFmt:'yyyy-MM'})" maxlength="10"
				readonly /></td>
			<td class="title">&nbsp;&nbsp;报表类型：</td>
			<td align="left">
				<select name="reportType">
					<option value="Restaurant P&L">Restaurant P&L</option>
					<option value="Restaurant Benchmark">Restaurant Benchmark</option>
				</select>
			</td>
		<tr>
			<td class="title"><b>*</b>上传的报表：</td>
			<td><!-- <span id="upload_mult">上传多张图片</span> -->
				<div class="clearfix">
				    <input type="file" onchange="checkFileSuffix('storeFile')" id="storeFile" name="storeFile" class="files"/>
				</div>
			</td>
			<td class="title"></td>
			<td><div class="qjact_btn clearfix">
					<a href="javascript:void(0)"
						class="green" id="btn_save" onclick="ajaxS();" style="margin:0;"><span>上&nbsp;&nbsp;传</span></a>
				</div></td>
		</tr>
		
	</table>
</form>
<div class="dzwidth_960 sing">
	<div class="qjstxt_wrap clearfix">
		<input type="text" class="text" name="reName" id="reName"/>
		<label class="defalut_val" for="reName">根据用户名称查询</label>
		<input type="button" value="搜&nbsp;索" id="repSearchBtn" class="search" />
	</div>

	<table id="gridTableShopRe"></table>
	<div id="gridPagerShopRe"></div>
	<input type="hidden" id="shopId" value="${id}">
</div>
<script type="text/javascript" src="${basePath}/js/shopRe/shopReList.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>
<script>
	function checkFileSuffix(fileId) {
		var inputComp = document.getElementById(fileId);
		var fileExt = inputComp.value.substr(inputComp.value.lastIndexOf("."))
				.toLowerCase();
		if (!(fileExt == ".xls")&&!(fileExt==".xlsx")&&!(fileExt==".pdf")) {
			if(navigator.userAgent.indexOf("MSIE")>0) {  
				document.getElementById(fileId).outerHTML += '';//清空IE的
		    }else{
		    	document.getElementById(fileId).value = "";//可以清空火狐的
		    } 
			alert("支持文件格式xls、xlsx、pdf");
			return false;
		}
	}
	function ajaxS() {
		var url = $("#storeFile").val();
		if($.trim($('#mon').val())==''){
			alert('请选择月份');
			return false;
		}
		if($.trim(url)=="" ){
			alert("请选择需上传的报表");
			return false;
		}
		var reportType = $('#form1').find("select[name='reportType'] option:selected").val();
		if($.trim(url)=="" ){
			alert("请选择报表类型");
			return false;
		}
		
		var id="${id}";
	    var options = {
	        url: "${basePath}/store/uploadStore?id="+id,
	        dataType: "html",
	        type: 'POST',
	        success: function (data,status) {
	        	data = JSON.parse(data);
	            if(status=="success"){
            	  
						alert("门店报表上传成功");   
						$("#gridTableShopRe").setGridParam({page:1}).jqGrid().trigger("reloadGrid");
					
				}
			}
		};
		var form = $('#form1');
		form.attr("enctype", "multipart/form-data");
		$('#form1').ajaxSubmit(options);
	}
			
			
	$("#repSearchBtn").bind("click",function(){
		$("#gridTableShopRe").setGridParam({page:1}).jqGrid().trigger("reloadGrid");
	});

</script>
