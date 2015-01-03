<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${basePath}/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<link rel="stylesheet" type="text/css" href="${basePath}/css/jquery-ui-themes-1.10.1/themes/redmond/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/css/jquery.multiselect.css" />

<script type="text/javascript" src="${basePath}/js/jquery-ui-1.10.1.custom.min.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery.multiselect.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery.multiselect.zh-cn.js"></script>

<script type="text/javascript"
	src="${basePath}/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="${basePath}/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	var setting = {
		check: {
			enable: true,
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
				data:{
					"pid":2
				},
				cache : false,
				type : 'POST',
				dataType : "json",
				success : function(data) { //请求成功后处理函数。
					zNodes = eval(data); //把后台封装好的简单Json格式赋给treeNodes
				}
			}); 
		
	});
	function beforeClick(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}

	function onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
				.getCheckedNodes(true);
		var cityObj = $("#pvalue");
		cityObj.attr("value",  nodes[0].name);
	}

	function showMenu() {
		var cityObj = $("#pvalue");
		var cityOffset = $("#pvalue").offset();
		$("#menuContent").css({
			"z-index" : 10000
		}).slideDown("fast");
		
		$("body").bind("mousedown", onBodyDown);
		//$.fn.zTree.init($("#treeDemo"), setting, zNodes);//重新加载ztree默认全部不选择
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "citySel"
				|| event.target.id == "menuContent" || $(event.target).parents(
				"#menuContent").length > 0)) {
			hideMenu();
		}
	}

	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		var pvalue= $.trim($('#pvalue').val());
		if(pvalue!=''){
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var selNode = zTree.getNodeByParam('name',pvalue,null);
			zTree.checkNode(selNode, true, false, false);
		}
		
		$(".big_green").removeClass("big_ddd");
		
		
		$(".close_tree").bind("click",function(){
			$("#menuContent").fadeOut("fast");
		});
		
	});
</script>
<form id="form1" method="post">
		<table class="addform_wrap">
		<tr>
			<td class="title"><b></b>上级：</td>
			<td style=""><input id="pvalue" class="text" type="text"
				readonly onclick="showMenu();" name="pvalue" value="${base.pvalue}" />

				<div id="menuContent" class="menuContent"
					style="display: none; position: absolute; background: #fff; overflow-y: auto; width: 300px; height: 160px; border: 1px solid #02335b">
					<p class="clearfix">
						<a href="javascript:void(0)" class="close_tree">关闭</a>
					</p>
					<ul id="treeDemo" class="ztree"></ul>
				</div></td>
		</tr>
		<tr>
				<td class="title"><b>*</b>类型：</td>
				<td><input type="text" id="bvalue" name="bvalue"
					class="text" value="${base.bvalue}"/></td>
		</tr>
		<tr>
			<td class="title"><b>*</b>是否筛选:</td>
			<td align="left"><select id="brushElection" name="brushElection">
					<option value="-1">---请选择---</option>
					<option value="0">否</option>
					<option value="1">是</option>
					 
			</select><input type="hidden" value="${base.brushElection}" id="freval1" /></td>
		</tr>
		<tr>
				<td class="title">描述：</td>
				<td>
				<textarea id="bcomments" name="bcomments">${base.bcomments}</textarea>
				</td>
		</tr>
		<tr>
			<td class="title"><b></b>涉及类型：</td>
			<td>
				<select id="addtionType">
					<c:forEach items="${modelTypes}" var="modelType">
						<option value="${modelType.value}" >${modelType.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
				<td class="title"><b>*</b>是否关键项：</td>
				<td  align="left">
				<select name="isKey" id="isKey">
					<option value="-1">---请选择---</option>
					<option value="1">是</option>
					<option value="0">否</option>
				</select>
					<input type="hidden" value="${base.isKey}" id="freval"/>
				</td>
			</tr>
		<tr>
			<td class="title"></td>
			<td >
				<div class="qjact_btn clearfix">
					<a href="javascript:void(0)" onclick="closeOutWindow()"
						class="big_ea"><span>取消</span></a> <a href="javascript:void(0)"
						class="big_green" id="btn_save" ><span>保存</span></a>
				</div>
			</td>
		</tr>
		</table>
		</form>
<script type="text/javascript">
function base() {
	var bvalue;
	
	var bcomments;
	
	var brushElection;
	
	var isKey;

}
var freval1 = $("#freval1").val();
var freval = $("#freval").val();
if(freval1!=null&&freval1!=""){
	$("#brushElection option[value="+freval1+"]").prop("selected",true);
}
if(freval!=null&&freval!=""){
	$("#isKey option[value="+freval+"]").prop("selected",true);
}
var id = '${base.id}';
$("#btn_save").bind("click", function() {
	//避免重复提交
	var _obj = $(this),_cname = _obj.prop("class");
	if(_cname.indexOf("big_ddd")>=0){
		return;
	}
	if ($.trim($('#bvalue').val()) == '') {
		alert('类型不能为空!');
		return false;
	}
	if($.trim($('#brushElection').val())=='-1'){
		alert('请选择是否筛选!');
		return false;
	}
	if($.trim($('#isKey').val())=='-1'){
		alert('请选择是否关键项!');
		return false;
	}
	
	var bvalue= $.trim($('input[name=bvalue]').val());
	var bcomments= $.trim($('input[name=bcomments]').val());
	var brushElection = $.trim($('#brushElection').val());
	var isKey = $.trim($('#isKey').val());
    var	value = '';
	// 拿到选中的id值
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = zTree.getCheckedNodes(true);
	for ( var i = 0; i < nodes.length; i++) {
		var node = nodes[i];
		value += node.id + ",";
	}
	value = value.substring(0, value.length - 1);
	
	_obj.addClass("big_ddd").removeClass("big_green");
    var options = {
            url: "${basePath}/base/addBase",
            data:{
				"ids":value,
				"id":id,
				"modelType" : getModelType()
            },
            dataType: "html",
            type: 'POST',
            success: function (data,status) {
            	if (id == '') {
					alert('添加成功!');
				} else {
					alert('修改成功!');
				}
				closeOutWindow();
				$("#gridTableBase").jqGrid().trigger("reloadGrid");
    		 }
    		};
    			var form = $('#form1');
    			form.attr("enctype", "multipart/form-data");
    			$('#form1').ajaxSubmit(options);

});

function getModelType(){
	var modelType = [];
	$("#addtionType").multiselect("getChecked").each(function(i,item){
		modelType.push($(item).val());
	});
	return modelType.join(',');
}

$(function(){
	var typeAry = '${modelType}';
	if(typeAry.length > 0){
		typeAry = $.parseJSON(typeAry);
		$("#addtionType option").each(function(i,item){
			var val = $(item).val();
			for(var i = 0;i < typeAry.length;i++){
				if(typeAry[i] == val){
					$(item).attr('data-selected',true);
				} 
			}
		});
	}
	
     $("#addtionType").multiselect({
        noneSelectedText: "==请选择==",
        checkAllText: "全选",
        uncheckAllText: '全不选',
        selectedList:4
    });  
});
</script>

