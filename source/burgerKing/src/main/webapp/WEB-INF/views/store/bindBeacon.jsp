<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<!DOCTYPE table PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="${basePath}/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${basePath}/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="${basePath}/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript">
	
	var setting = {
		check : {
			enable : true,
			chkboxType : {
				"Y" : "s",
				"N" : "s"
			}
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
				url : basePath + "/store/getBeaconTreeList",//请求的action路径
				/* data:{
					"role":2
				}, */
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
				.getCheckedNodes(true), v = "";
		for (var i = 0, l = nodes.length; i < l; i++) {
			v += nodes[i].name + ",";
		}
		if (v.length > 0)
			v = v.substring(0, v.length - 1);
		var cityObj = $("#citySel");
		cityObj.attr("value", v);
	}

	function showMenu() {
		var cityObj = $("#citySel");
		var cityOffset = $("#citySel").offset();
		$("#menuContent").css({
// 			left : cityOffset.left + "px",
// 			top : cityOffset.top + cityObj.outerHeight() + "px",
			"z-index" : 10000
		}).slideDown("fast");
		
		$("body").bind("mousedown", onBodyDown);
		//$.fn.zTree.init($("#treeDemo"), setting, zNodes);//重新加载ztree默认全部不选择
	}
	function hideMenu() {
		$("#menuContent").slideUp("fast");
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
		$(".big_green").removeClass("big_ddd");
		
		$(".close_tree").bind("click",function(){
			$("#menuContent").fadeOut("fast");
		});
	});
	
	
</script>

</head>
<body>
		<table class="addform_wrap">
			<tr>
				<td class="title"><b>*</b>BEACON设备：</td>
				<td style=""><input id="citySel" class="text" type="text" readonly
					onclick="showMenu();" name="authNames" />
					<input id="authIds" name="authIds" type="hidden" />
					
					<div id="menuContent" class="menuContent"
						style="display: none;position:absolute;background:#fff;overflow-y:auto;height:200px; width:300px;border:1px solid #02335b">
						<p class="clearfix"><a href="javascript:void(0)" class="close_tree">关闭</a></p>
						<ul id="treeDemo" class="ztree clearfix"></ul>
					</div>
				</td>
			</tr>
			<tr>
				<td class="title"></td>
				<td>
					<div class="qjact_btn clearfix">
						<a href="javascript:void(0)" onclick="closeOutWindow()" class="big_ea"><span>取消</span></a>
						<a href="javascript:void(0)" class="big_green" id="btn_save"><span>保存</span></a>
					</div>
				</td>
			</tr>
		</table>

<script type="text/javascript">	
	var id = '${shopid}';
	$("#btn_save").bind("click", function() {
		//避免重复提交
		var _obj = $(this),_cname = _obj.prop("class");
		if(_cname.indexOf("big_ddd")>=0){
			return;
		}
		
	    var	value = '';
		// 拿到选中的id值
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = zTree.getCheckedNodes(true);
		for ( var i = 0; i < nodes.length; i++) {
			var node = nodes[i];
			value += node.id + ",";
		}
		value = value.substring(0, value.length - 1);
		var url = "${basePath}/store/bindShop";
		_obj.addClass("big_ddd").removeClass("big_green");
		$.ajax({
			type : 'post',
			url : url,
			data : {
				"id" : id,
				"ids":value
			},
			dataType : "json",
			cache : false,
			error : function(err) {
				alert("系统出错了，请联系管理员！");
			},
			success : function(data) {
					alert('绑定成功!');
				closeOutWindow();
				$("#gridTableMessage").jqGrid().trigger("reloadGrid");
			}
		});

	});
</script>

</body>
</html>