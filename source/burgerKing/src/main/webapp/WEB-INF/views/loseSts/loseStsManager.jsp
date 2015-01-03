<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />

<div class="dzwidth_960 user">
	<table class="qjabtn_wrap" id="searchDiv">
		<tr>
			<td>
				<div class="qjact_btn clearfix">
					<a href="javascript:void(0)" class="small_ea" id="batchExport"><span>失分率批量导出</span></a>
				</div>
			</td>
			<td><label style="font-size: 14px;">表单类型：</label></td>
			<td>
				<div class="qjstxt_wrap clearfix">
					<input name="examType" id="examType" onclick="showMenu();"
					readonly="readonly" class="text" /><input type="button" class="search" onclick="doSearchLoseSts()" value="搜索"/> 
					<input id="authIds"name="authIds" type="hidden" />
				</div>
				<div style="position: relative">
					<div id="menuContent" class="menuContent"
						style="display: none; position: absolute; background: #fff; overflow-y: auto; width: 230px; height: 160px; border: 1px solid #02335b">
						<p class="clearfix">
							<a href="javascript:void(0)" class="close_tree">关闭</a>
						</p>
						<ul id="treeDemo" class="ztree"></ul>
					</div>
				</div>
			</td>
				
		</tr>
	</table>
	<table id="gridTableLoseSts"></table>
	<div id="gridPagerLoseSts"></div>
</div>
<link rel="stylesheet" href="${basePath}/css/zTreeStyle/zTreeStyle.css"
	type="text/css" />
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
		async : {
			enable : true,
			url : basePath + "/examManager/getUserTreeList",
			autoParam : [ "id" ],
			dataFilter : ajaxDataFilter
		},
		callback : {
			beforeExpand : beforeExpand,
			onAsyncSuccess : onAsyncSuccess,
			onAsyncError : onAsyncError,
			beforeClick : beforeClick,
			onCheck : onCheck
		}
	};
	function ajaxDataFilter(treeId, parentNode, responseData) {
		if (responseData) {
			for (var i = 0; i < responseData.length; i++) {
				if (responseData[i].name != 'P类'
						&& responseData[i].name != 'G类'
						&& responseData[i].name != '评估/稽核'
						&& responseData[i].name != '报告') {
					responseData[i].isParent = false;
				}
			}
		}
		return responseData;
	};
	function beforeExpand(treeId, treeNode) {
		if (!treeNode.isAjaxing) {
			startTime = new Date();
			treeNode.times = 1;
			return true;
		} else {
			alert("zTree 正在下载数据中，请稍后展开节点。。。");
			return false;
		}
	}

	function onAsyncSuccess(event, treeId, treeNode, msg) {
		if (!msg || msg.length == 0) {
			return;
		}
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.updateNode(treeNode);
		endTime = new Date();
	}
	function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus,
			errorThrown) {
		//var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		alert("异步获取数据出现异常。");
		zTree.updateNode(treeNode);
	}
	function beforeClick(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		var level = treeNode.level;
		if (level < 2) {
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
		}
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
		var examOjb = $("#examType");
		examOjb.attr("value", v);
	}

	function showMenu() {
		var examOjb = $("#examType");
		var examOffset = $("#examType").offset();
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
		$.fn.zTree.init($("#treeDemo"), setting);
		$(".close_tree").bind("click", function() {
			$("#menuContent").fadeOut("fast");
		});
	});
	$("#batchExport").bind(
			'click',
			function() {
				var selectRows = jQuery("#gridTableLoseSts").jqGrid(
						'getGridParam', 'selarrrow');
				if (selectRows == null || selectRows.length == 0) {
					alert("请选择要导出的失分项!");
					return;
				}
				var url = basePath + "/examManager/examportLose?id="
						+ selectRows.join(',');
				window.location = url;
			});
	function doSearchLoseSts(){
		$("#gridTableLoseSts").setGridParam({page:1}).jqGrid().trigger("reloadGrid");
	}
</script>
<script type="text/javascript"
	src="${basePath}/js/loseStsManager/loseStsManager.js"></script>
