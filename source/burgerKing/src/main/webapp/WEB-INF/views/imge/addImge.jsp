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
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<style>
 #storeForm .text{width:200px;}
  #storeForm .title{width:120px;}
</style>


<form id="form1" method="post">
<table class="addform_wrap" id="storeForm" style="width:900px">
	<tr >
		<td  width="300px"><b>*</b>文件上传：
			    <input type="file" class="files" style="float:none" onchange="checkFileSuffix('shop_image1')" name="shop_image1"
					id="shop_image1" />
		
		</td>
		<td width="280px"><b>*</b>标题：<input type="text" id="originalName" 
			name="originalName" class="text" /></td>
		<td>
			<input class="search" style="padding:5px 15px;"type="button" value="保存" id="btn_save">
		</td>
	</tr>
	<tr id="loading" style="display:none">
		<td width="900" colspan="3" class="loading_wrap" style="text-align:center;">
			<img src="${basePath}/images/loading_b.gif" width="32" height="32" />&nbsp;&nbsp;&nbsp;文件正在上传请稍后。。。
		</td>
	</tr>
</table>
 	</form>
 	<div class="dzwidth_960 sing">
	<div class="qjstxt_wrap clearfix">
		<input type="text" class="text" name="imgeName" id="imgeName"/>
		<label class="defalut_val" for="imgeName">根据文件称查询</label>
		<input type="button" value="搜&nbsp;索" id="repSearchBtn" class="search" />
	</div>

	<table id="gridTableImge"></table>
	<div id="gridPagerImge"></div>
	<input type="hidden" id="shopId" value="${id}">
</div>
<script type="text/javascript" src="${basePath}/js/imge/imgeList.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>
<script type="text/javascript">
function checkFileSuffix(fileId) {
	var inputComp = document.getElementById(fileId);
	var fileExt = inputComp.value.substr(inputComp.value.lastIndexOf("."))
			.toLowerCase();
	if (!(fileExt == ".pdf")&&!(fileExt==".xlsx")&&!(fileExt == ".mp4")
			&&!(fileExt==".avi")&&!(fileExt == ".wmv")) {
 		if(navigator.userAgent.indexOf("MSIE")>0) {  
			document.getElementById(fileId).outerHTML += '';//清空IE的
	    }else{
	    	document.getElementById(fileId).value = "";//可以清空火狐的
	    } 
		alert("支持文件格式pdf、xlsx、mp4、avi、wmv");
		return false;
	}
}
	function shop() {
		var shopimage;
		var originalName;
	}
	var reg = /^\d+$|^\d+\.\d+$/;
	var id = '${shop.id}';
	$("#btn_save").bind("click", function() {
		//避免重复提交
		var _obj = $(this),_cname = _obj.prop("class");
		if(_cname.indexOf("big_ddd")>=0){
			return;
		}
		if($.trim($('#shop_image1').val())==''){
			alert('请选择文件!');
			return false;
		}
		if($.trim($('#originalName').val())=='')
		{
			alert('请输入标题');
			return false;
		}
		var vo = new shop();
		vo.shopimage = $.trim($('input[name=shopimage]').val());
		vo.originalName = $.trim($('input[name=originalName]').val());
	    var	value = '';
		// 拿到选中的id值
		$("#loading").show();
		$("#btn_save").hide();
	    var options = {
	            url: "${basePath}/toolKit/addFiles",
	            data:{
	            	"jsons" : JSON.stringify(vo),
					"ids":value	
	            },
	            dataType: "html",
	            type: 'POST',
	            success: function (data,status) {
	            	$("#loading").hide();
	            	$("#btn_save").show();
	            	if (id == '') {
						alert('添加成功!');
					} else {
						alert('修改成功!');
					}
					
					$("#gridTableImge").jqGrid().trigger("reloadGrid");
	    			}
	    			};
	    			var form = $('#form1');
	    			form.attr("enctype", "multipart/form-data");
	    			$('#form1').ajaxSubmit(options);

	});
	
	$("#repSearchBtn").bind("click",function(){
		$("#gridTableImge").setGridParam({page:1}).jqGrid().trigger("reloadGrid");
	});
	
</script>
