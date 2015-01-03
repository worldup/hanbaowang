<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${basePath}/js/uploadify/uploadify.css"
     rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="${basePath}/js/uploadify/jquery.uploadify.min.js"></script>
<form id="form1" method="post">
	<table class="addform_wrap">
	<tr>
			<td class="title">月度：</td>
		<td align="left"><input type="text" id="mon"
			name="mon" class="Wdate input_w150"
			onClick="WdatePicker({dateFmt:'yyyy-MM'})" maxlength="10"
			readonly /></td>
	</tr>
		<tr>
			<td class="title"><b>*</b>请选择需上传的报表：</td>
			<td><!-- <span id="upload_mult">上传多张图片</span> -->
				<div class="clearfix">
				    <input type="file" onchange="checkFileSuffix('storeFile')" id="storeFile" name="storeFile" class="files"/>
					<div class="xx_act_btn">
						<a class="xx_add" href="javascript:void(0)" title="增加" onClick="addFile();"></a>
					</div>
				</div>
				<ul class="add_files" id="addFiles">
					
				</ul>
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
function checkFileSuffix(fileId) {
	var inputComp = document.getElementById(fileId);
	var fileExt = inputComp.value.substr(inputComp.value.lastIndexOf("."))
			.toLowerCase();
	if (!(fileExt == ".xls")&&!(fileExt==".xlsx")) {
		if(navigator.userAgent.indexOf("MSIE")>0) {  
			document.getElementById(fileId).outerHTML += '';//清空IE的
	    }else{
	    	document.getElementById(fileId).value = "";//可以清空火狐的
	    } 
		alert("支持文件格式xls、xlsx");
		return false;
	}
}
function ajaxS() {
	var url = $("#storeFile").val();
	if($.trim(url)=="" ){
		alert("请选择需上传的报表");
		return false;
	}
	var id="${id}";
    var options = {
        url: "${basePath}/store/uploadStore?id="+id,
        dataType: "html",
        type: 'POST',
        success: function (data,status) {
            if(status=="success"){
            	  var obj = eval('(' + data + ')');   
            	  if(""!=obj.msg){
						alert("门店报表上传成功");            		 
            	  		closeOutWindow();
						}
					}
				}
			};
			var form = $('#form1');
			form.attr("enctype", "multipart/form-data");
			$('#form1').ajaxSubmit(options);
		}
		
		
var i = 2,ul = document.getElementById("addFiles");
function addFile() {
		var li = document.createElement("li");
		var file = document.createElement("input");
		file.type = "file";
		file.id = "storeFile" + i;
		file.name = "storeFile" + i;
		file.className = "files";
		if(window.addEventListener){ // Mozilla, Netscape, Firefox
			file.addEventListener('change', function(){
				checkFileSuffix(file.id);
			}, false);
		} else { // IE
			file.attachEvent('onchange', function(){
				checkFileSuffix(file.id);
			});
		} 
		var del = $('<div class="xx_act_btn"><a class="xx_del" title="删除" href="javascript:void(0)"></a></div>');
		$(li).append(file).append(del);
		$(ul).append(li);
		del.bind("click",function(){
			$(li).remove();
		});
		i++;
	}

</script>
