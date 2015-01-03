<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<%-- <link rel="stylesheet" href="${basePath}/css/zTreeStyle/zTreeStyle.css" --%>
<!-- 	type="text/css"> -->
<!-- <script type="text/javascript" -->
<%-- 	src="${basePath}/js/ztree/jquery.ztree.core-3.5.js"></script> --%>
<!-- <script type="text/javascript" -->
<%-- 	src="${basePath}/js/ztree/jquery.ztree.excheck-3.5.js"></script> --%>
<script type="text/javascript">
	var setting = {
		check : {
			enable : true,
			chkStyle: "radio",
			radioType: "all"
		},
		view : {
			dblClickExpand : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			beforeClick : beforeClick,
			onCheck : onCheck
		}
	};

	var zNodes='';
	$(function(){
		 $.ajax({
				async:false,
				url : basePath + "/base/getUserTreeList",//请求的action路径
				cache : false,
				type : 'POST',
				dataType : "json",
				success : function(data) { //请求成功后处理函数。
					zNodes = eval(data); //把后台封装好的简单Json格式赋给treeNodes
				}
			}); 
		
	});
	function beforeClick(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("winDtreeDemo");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}

	function onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("winDtreeDemo"), nodes = zTree
				.getCheckedNodes(true), v = "";
		for (var i = 0, l = nodes.length; i < l; i++) {
			v += nodes[i].name + ",";
			
		}
		if (v.length > 0)
			v = v.substring(0, v.length - 1);
		var cityObj = $("#modelName");
		cityObj.attr("value", v);
	}

	function winDshowMenu() {
		var cityObj = $("#modelName");
		var cityOffset = $("#modelName").offset();
		$("#winDmenuContent").css({
			"z-index" : 10000
		}).slideDown("fast");
		
		$("body").bind("mousedown", onWinBodyDown);
		//$.fn.zTree.init($("#treeDemo"), setting, zNodes);//重新加载ztree默认全部不选择
	}
	function winhideMenu() {
		$("#winDmenuContent").fadeOut("fast");
		$("body").unbind("mousedown", onWinBodyDown);
	}
	function onWinBodyDown(event) {
		if (!(event.target.id == "modelName"
				|| event.target.id == "winDmenuContent" || $(event.target).parents(
				"#winDmenuContent").length > 0)) {
			winhideMenu();
		}
	}

	$(document).ready(function() {
		$.fn.zTree.init($("#winDtreeDemo"), setting, zNodes);
		/* var modelName = $("#modelName").val();
		if(modelName!=''){
			var zTree = $.fn.zTree.getZTreeObj("winDtreeDemo");
			var selNode = zTree.getNodeByParam('name',modelName,null);
			zTree.checkNode(selNode, true, false, false);
		} */
		var modelId = '${question.moduleId}';
		if(modelId!=''){
			var zTree = $.fn.zTree.getZTreeObj("winDtreeDemo");
			var selNode = zTree.getNodeByParam('id',modelId,null);
			zTree.checkNode(selNode, true, false, false);
		} 
		
		$(".big_green").removeClass("big_ddd");
		
		$(".close_tree").bind("click",function(){
			$("#winDmenuContent").fadeOut("fast");
		});
		
	});
</script>
	<form id="form1" method="post">
		<input id="questionId" hidden="hidden" name="id" value="${question.id}">
		<table class="addform_wrap">
			<tr>
				<td class="title"><b>*</b>试题类型：</td>
				<td align="left">
					<select id="qtype" name="qtype"></select>
				</td>
			</tr>
			<tr>
				<td class="title"><b></b>编号：</td>
				<td><input type="text" id="serialNumber" name="serialNumber"
					class="text" value="${question.serialNumber}" /></td>
			</tr>
			<tr>
				<td class="title"><b></b>所属模块：</td>
				<%-- <td><input type="text" id="modelName" name="modelName"
					class="text" value="${question.modelName}" /></td> --%>
				<td style=""><input id="modelName" class="text" type="text" readonly="readonly"
					onclick="winDshowMenu();" name="authNames" value="${question.modelName}" />
					<input id="modelId" name="modelId" type="hidden"  />
					
					<div id="winDmenuContent" class="menuContent"
						style="display: none;position:absolute;background:#fff;overflow-y:auto;height:200px; width:300px;border:1px solid #02335b">
						<p class="clearfix"><a href="javascript:void(0)" class="close_tree">关闭</a></p>
						<ul id="winDtreeDemo" class="ztree clearfix"></ul>
					</div>
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
});
/* var qtype = "${question.qtype}";
$('#qtype').val(qtype);*/

$("#btn_save").bind("click",function(){
	var _obj = $(this),_cname = _obj.prop("class");
	if(_cname.indexOf("big_ddd")>=0){
		return;
	}
	
	var	value = '';
	// 拿到选中的id值
	var zTree = $.fn.zTree.getZTreeObj("winDtreeDemo");
	var nodes = zTree.getCheckedNodes(true);
	for ( var i = 0; i < nodes.length; i++) {
		var node = nodes[i];
		value += node.id + ",";
	}
	value = value.substring(0, value.length - 1);
	
	var reg = /^\d+$/;
	if($.trim($('#qtype').val()) == '-1'){
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
				closeOutWindow();
			$("#gridTableQuestion").jqGrid().trigger("reloadGrid");
		}
	};
	
	var form = $('#form1');
	form.attr("enctype", "multipart/form-data");
	$('#form1').ajaxSubmit(options);
});

$(function() {
	$.ajax({
		async : false,
		type : 'post',
		url : basePath + "/question/questionType",
		dataType : "json",
		cache : false,
		error : function(err) {
			alert("系统出错了，请联系管理员！");
		},
		success : function(data) {
			$('#qtype').empty();
			var html='<option value="-1">---请选择---</option>';
			var qtype = "${question.qtype}";
			$.each(data,function(i, value) {
				var selected = "", val = value.value;
				if(qtype != null && val == qtype){
					selected = "selected=selected";
				}
				html+="<option "+selected+" value="+val+">"+value.name+"</option>";
			});
			$('#qtype').append(html);
		}
	});	
});
</script>
