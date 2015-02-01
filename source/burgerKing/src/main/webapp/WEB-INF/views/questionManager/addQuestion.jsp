<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="basePath" value="${pageContext.request.contextPath}" />
	<form id="form1" method="post">
		<input id="questionId" hidden="hidden" name="id" value="${question.id}">
		<table class="addform_wrap">
			<tr>
				<td class="title"><b>*</b>评估项类型：</td>
				<td align="left">
					<select id="qtype" class="easyui-combobox" name="qtype" style="width:150px;">
					</select>
				</td>
			</tr>
			<tr>
				<td class="title"><b></b>编号：</td>
				<td><input type="text" id="serialNumber" name="serialNumber"
					class="text" value="${question.serialNumber}" /></td>
			</tr>
			<tr>
				<td class="title"><b></b>所属模块：</td>
				<td>
					<input id="modelComboTree" class="easyui-combotree"  style="width:200px;"/>
				</td>
			</tr>
			<tr>
				<td class="title"><b>*</b>问题描述：</td>
				<td>
					<textarea id="qcontent" name="qcontent">${question.qcontent}</textarea></td>
			</tr>
			<tr>
				<td class="title">分值：</td>
				<td><input type="text" id="qvalue" name="qvalue"
					class="text" value="${question.qvalue}" /></td>
			</tr>
			<tr>
				<td class="title">备注：</td>
				<td>
					<textarea id="qremark" name="qremark">${question.qremark}</textarea></td>
			</tr>
			<tr>
				<td class="title"><b>*</b>是否上传图片：</td>
				<td  align="left">
				<select name="isUploadPic" id="isUploadPic">
					<option value="-1">---请选择---</option>
					<option value="1">是</option>
					<option value="2">否</option>
				</select>
					<input type="hidden" value="${question.isUploadPic}" id="freval"/>
				</td>
			</tr>
			<tr>
				<td class="title"></td>
				<td>
					<div class="qjact_btn clearfix">
						<a href="javascript:void(0)" onclick="closeOutWindow()" class="big_ea"><span>取消</span></a>
						<a href="javascript:void(0)"  class="big_green" id="btn_save"><span>保存</span></a>
					</div>
				</td>
			</tr>
		</table>
		
	</form>

<script type="text/javascript">
$(document).ready(function(){
	var freval = $("#freval").val();
	if(freval!=null&&freval!=""){
		$("#isUploadPic option[value="+freval+"]").prop("selected",true);
	}
	$(".big_green").removeClass("big_ddd");

	$.post("${basePath}"+"/question/questionType",{},function(data){

		var localCache=[];
		$.each(data,function(i, value) {
			localCache.push({ 'label': value.name, 'value': value.value })
		});
		$('#qtype').combobox({
			data:localCache,
			valueField:'value',
			textField:'label'
		});
        $('#qtype').combobox('setValue','${question.questionType}');
	});
	$.post("${basePath}/base/getExamUserTreeList",{},function(data){
		var comboData= treeconvert($.parseJSON(data));
	    $('#modelComboTree').combotree('loadData',comboData);
        $('#modelComboTree').combotree('setValue', '${question.moduleId}');
	});

});


$("#btn_save").bind("click",function(){
	var _obj = $(this),_cname = _obj.prop("class");
	if(_cname.indexOf("big_ddd")>=0){
		return;
	}
	
	var	value =  $('#modelComboTree').combotree('getValue');

	var reg = /^\d+$/;
	if($.trim($('#qtype').combobox('getValue')) == ''){
		alert("请选择试题类型！");
		return false;
	}/* else if($.trim($('#serialNumber').val()) == ''){
		alert("请输入编号");
		$("#serialNumber").focus();
		return false;
	} *//* else if(isNaN($.trim($('#serialNumber').val()))){
		alert("编号为数字");
		$("#serialNumber").focus();
		return false;
	} *//* else if($.trim($('#modelName').val()) == ''){
		alert("请选择所属模块！");
		$("#citySel").focus();
		return false;
	} */else if($.trim($('#qcontent').val()) == ''){
		alert("请输入问题描述！");
		$("#qcontent").focus();
		return false;
	}else if($.trim($('#isUploadPic').val()) == '-1'){
		alert("是否上传图片");
		return false;
	}
	var id = $("#questionId").val();
	var url = id ? "${basePath}/question/update" : "${basePath}/question/add";
	_obj.addClass("big_ddd").removeClass("big_green");
	var options = {
		url : url,
		type : 'post',
		data:{
			"ids":value
		},
		dataType : "html",
		error : function(err) {
			alert("系统出错了，请联系管理员！");
		},
		success : function(data) {
			if(id==''){
				alert('添加成功!');
			}else{
				alert('修改成功!');
			}
            $('#win').window('close');
			$("#gridTableQuestion").jqGrid().trigger("reloadGrid");
		}
	};
	
	var form = $('#form1');
	form.attr("enctype", "multipart/form-data");
	$('#form1').ajaxSubmit(options);
});


</script>
