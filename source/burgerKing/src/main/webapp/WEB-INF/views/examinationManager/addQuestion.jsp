<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<div style="padding:0 20px;">
	<table class="qjabtn_wrap" id="searchDiv">
		<tr>
			<td><label>试卷类型：</label></td>
			<td>
				<div class="qjstxt_wrap clearfix">
					<div style="qjstxt_wrap">
						<input name="questionType" id="questionType" onclick="showWbMenu();" readonly="readonly" class="text" />
						<input type="button" value="搜&nbsp;索" id="searchBtn" class="search" />
						<input id="questionTypeId" name="questionTypeId" type="hidden" />
					</div>
					<div id="wbmenuContent" class="menuContent"
						style="display: none;position:absolute;top:36px;background:#fff;overflow-y:auto; width:230px;height:160px;border:1px solid #02335b">
						<p class="clearfix"><a href="javascript:void(0)" class="close_tree">关闭</a></p>
						<ul id="wbTree" class="ztree"></ul>
					</div>
				</div>
			</td>
		</tr>
	</table>
<table id="questionTable"></table>
<div id="questionPager"></div>
<div class="txt_center clearfix" style="width:65%;">
	<div class="qjact_btn clearfix">
		<a href="javascript:void(0)"
				class="big_ea" onclick="closeOutWindow()"><span>取消</span></a>
		<a href="javascript:void(0)"
				class="big_green" id="q_confirm" ><span>确定</span></a>
	</div>
</div>
</div>
<script type="text/javascript">
$("#questionTable")
.jqGrid(
		{
			url : basePath + "/question/list",
			mtype : "POST",
			postData : {
				quesType : '${quesType}',
				preids : function() {
					return $.trim($('#questionTypeId').val());
				}
			},
			altRows : true,
			forceFit:true,
			altclass : "jqgrid_alt_row",
			datatype : "json",
			/*
			 * width : 958, height : 300, autowidth : true,
			 */
			multiselect : true,
			//rownumbers : true,
			viewsortcols : [ true, 'vertical', true ],
			colModel : [
						{
							label : "试题流水号",
							name : "id",
							sortable : false,
							width : 100
						},
						{
							label : "知识点",
							name : "modelName",
							title : false,
							sortable : false,
							width : 100
						},
						{
							label : "试题内容",
							name : "qcontent",
							title : false,
							sortable : false,
							width : 200
						},
						{
							label : "分值",
							name : "qvalue",
							title : false,
							sortable : false,
							width : 80
						} ],
			rowNum : 10,
			rowList : [ 10, 20, 100 ],
			pager : "#questionPager",
			viewrecords : true,
			jsonReader : {
				repeatitems : false
			},
			autowidth : true,
			width : "100%",
			height : "100%",
			gridComplete : function() {
				var neww = $("#gbox_questionTable").width();
				
				var tablew = $("#gview_questionTable .ui-jqgrid-btable").width();
				
				if(tablew<neww){
					$("#gview_questionTable .ui-jqgrid-htable").width(neww);
					$("#gview_questionTable .ui-jqgrid-btable").width(neww);
				}	
			}
		});
		
		$('#q_reset').bind('click',function(){
		});
		$('#q_confirm').bind('click',function(){
			
			var selectRows = jQuery("#questionTable").jqGrid('getGridParam','selarrrow');
			if(selectRows.length <= 0){
				return;
			}
			var selectQues = [];
			var totalScore = 0;
			for(var i = 0;i < selectRows.length;i++){
				var rowData = jQuery("#questionTable").jqGrid('getRowData',selectRows[i]);
				selectQues.push(rowData.id);
				totalScore += (rowData.qvalue - 0);
			}
			
			var $tr = $('#questionTypeTable').find('td[title=${quesType}]').parent('tr');
			$tr.find('[name=questionIds]').val(selectQues.join(','));
			$tr.find('[aria-describedby=questionTypeTable_quesNum]').html(selectRows.length);
			$tr.find('[aria-describedby=questionTypeTable_score]').html(totalScore);
			var $totalScore = $('#basicInfo [name=totalValue]');
			$totalScore.val($totalScore.val() - 0 + totalScore);
			closeOutWindow();
			
		});
</script>
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
				autoParam:["id"]
			},
			callback : {
				beforeExpand: beforeExpand,
				onAsyncSuccess: onAsyncSuccess,
				onAsyncError: onAsyncError,
				beforeClick : beforeClick,
				onCheck : onCheck
			}
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
			var zTree = $.fn.zTree.getZTreeObj("wbTree");
				zTree.updateNode(treeNode);
				endTime = new Date();
		}
		function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
			//var zTree = $.fn.zTree.getZTreeObj("treeDemos");
			alert("异步获取数据出现异常。");
			zTree.updateNode(treeNode);
		}
		function beforeClick(treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("wbTree");
			zTree.checkNode(treeNode, !treeNode.checked, null, true);
			return false;
		}
	
		function onCheck(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("wbTree"), nodes = zTree
					.getCheckedNodes(true), v = "",vid = "";
			for (var i = 0, l = nodes.length; i < l; i++) {
				v += nodes[i].name + ",";
				vid+= nodes[i].id+",";
			}
			if (v.length > 0)
				v = v.substring(0, v.length - 1);
			var examOjb = $("#questionType");
			examOjb.attr("value", v);
			$("#questionTypeId").val(vid.substring(0,vid.length - 1));
		}
	
		function showWbMenu() {
			var examOjb = $("#questionType");
			var examOffset = $("#questionType").offset();
			$("#wbmenuContent").css({
				"z-index" : 10000
			}).slideDown("fast");
			
			$("body").bind("mousedown", onWbBodyDown);
			//$.fn.zTree.init($("#treeDemos"), setting, zNodes);//重新加载ztree默认全部不选择
		}
		function hideWbMenu() {
			$("#wbmenuContent").fadeOut("fast");
			$("body").unbind("mousedown", onWbBodyDown);
		}
		function onWbBodyDown(event) {
			if (!(event.target.id == "wbmenuContent" || $(event.target).parents(
					"#wbmenuContent").length > 0)) {
				hideWbMenu();
			}
		}
		$(document).ready(function() {
			$.fn.zTree.init($("#wbTree"), setting);
			$(".close_tree").bind("click",function(){
				$("#wbmenuContent").fadeOut("fast");
			});
		});
		
		$("#searchBtn").bind("click",function(){
			$("#questionTable").setGridParam({page:1}).jqGrid().trigger("reloadGrid");
		});
	</script>
</body>
</html>
