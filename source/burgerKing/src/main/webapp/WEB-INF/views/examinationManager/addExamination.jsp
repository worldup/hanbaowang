<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />

<link rel="stylesheet" href="${basePath}/css/zTreeStyle/zTreeStyle.css"
	type="text/css" />

<link rel="stylesheet" type="text/css" href="${basePath}/css/jquery.multiselect.css" />
<style>
.ui-multiselect{padding:4px 0 3px 5px}
</style>
<script type="text/javascript" src="${basePath}/js/jquery-ui-1.10.1.custom.min.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery.multiselect.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery.multiselect.zh-cn.js"></script>
<script type="text/javascript"
	src="${basePath}/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="${basePath}/js/ztree/jquery.ztree.excheck-3.5.js"></script>
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
			async: {
				enable: true,
				url: basePath + "/examManager/getUserTreeList",
				autoParam:["id"],
				dataFilter: ajaxDataFilter
			},
			callback : {
				beforeExpand: beforeExpand,
				onAsyncSuccess: onAsyncSuccess,
				onAsyncError: onAsyncError,
				beforeClick : beforeClick,
				onCheck : onCheck
			}
		};
		function ajaxDataFilter(treeId, parentNode, responseData) {
			  if (responseData) {
			      for(var i =0; i < responseData.length; i++) {
			        if(responseData[i].name!='P类' && responseData[i].name!='G类'
			        &&	responseData[i].name!='评估/稽核'
			        &&	responseData[i].name!='报告'
			        ){
			        	responseData[i].isParent=false;
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
		function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
			//var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			alert("异步获取数据出现异常。");
			zTree.updateNode(treeNode);
		}
		function beforeClick(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var level = treeNode.level;
			if(level<2){
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
			$(".close_tree").bind("click",function(){
				$("#menuContent").fadeOut("fast");
			});
		});
	</script>

	<div class="dzwidth_960 user">
		<table class="addform_wrap" id="basicInfo" style="margin:0;">
			<tr>
				<td>
					<label><b>*</b>试卷名称：</label><input name="examName" id="examName" class="text_170" />
				</td>
				<td >
					<label><b>*</b>试卷类型：</label><input name="examType" id="examType" onclick="showMenu();" readonly="readonly" class="text_170" />
					<input id="authIds" name="authIds" type="hidden" />
					<div style="position:relative">
						<div id="menuContent" class="menuContent"
							style="display: none;position:absolute;left:80px;background:#fff;overflow-y:auto; width:230px;height:160px;border:1px solid #02335b">
							<p class="clearfix"><a href="javascript:void(0)" class="close_tree">关闭</a></p>
							<ul id="treeDemo" class="ztree"></ul>
						</div>
					</div>
				</td>
				<td>
					<label><b>*</b>答题时间：</label><input name="examTimer" id="examTimer" class="text_170" /><span>&nbsp;&nbsp;分钟</span>
				</td>
				<td rowspan="2">
					<label>说明：</label><textarea name="description" style="width:260px;" id="description"></textarea>
				</td>
			</tr>
			<tr>
				<td>
					<label><b>*</b>试卷总分：</label><input name="totalValue" id="totalValue" readonly="readonly" value="0" class="text_170"/>
				</td>
				<td>
					<label><b>&nbsp;</b>通过分数：</label><input name="passValue"  id="passValue" class="text_170"/>
				</td>
				<td>
					<label><b>&nbsp;</b>台头字段：</label>
					<select id="headsFields">
						<c:forEach items="${heads}" var="head">
							<option value="${head.value}" >${head.name}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
		
		<div class="dz_crumbs"><b>试卷选题策略列表</b></div>
		<table id="questionTypeTable" style="border-bottom:1px solid #cac8c9"></table>
			<div id="questionTypePager"></div>
	</div>
	
	<div class="txt_center clearfix">
		<div class="qjact_btn clearfix" >
			<a href="javascript:history.go(-1)"
					class="big_ea"><span>返&nbsp;&nbsp;回</span></a>
			<a href="javascript:void(0)"
					class="big_green" id="addExam" ><span>提&nbsp;&nbsp;交</span></a>
		</div>
	</div>
	
	<script type="text/javascript"
		src="${basePath}/js/examinationManager/addExaminationPaper.js"></script>
	<script type="text/javascript">
		ExaminationPaper.init({
			questionType : $.parseJSON('${questionType}')
		});
	</script>

