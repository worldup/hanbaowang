<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<script>
var id="${question.id}";
</script>
<link rel="stylesheet" href="${basePath}/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${basePath}/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="${basePath}/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript">
	var settingAdd = {
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
			beforeClick : beforeClickAdd,
			onCheck : onCheckAdd
		}
	};

	var zNodesAdd = '';
	$(function() {
		$.ajax({
			async : false,
			url : basePath + "/base/getPaperSignTree",//请求的action路径
			cache : false,
			type : 'POST',
			dataType : "json",
			success : function(data) { //请求成功后处理函数。
				zNodesAdd = eval(data); //把后台封装好的简单Json格式赋给treeNodes
			}
		});

	});
	function beforeClickAdd(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemoAdd");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}

	function onCheckAdd(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemoAdd"), nodes = zTree
				.getCheckedNodes(true), v = "";
		for (var i = 0, l = nodes.length; i < l; i++) {
			v += nodes[i].name + ",";
		}
		if (v.length > 0)
			v = v.substring(0, v.length - 1);
		var cityObj = $("#citySel");
		cityObj.attr("value", v);
	}

	function showMenuAdd() {
		var cityObj = $("#citySel");
		var cityOffset = $("#citySel").offset();
		$("#menuContentAdd").css({
			// 			left : cityOffset.left + "px",
			// 			top : cityOffset.top + cityObj.outerHeight() + "px",
			"z-index" : 10000
		}).slideDown("fast");

		$("body").bind("mousedown", onBodyDown);
		//$.fn.zTree.init($("#treeDemo"), setting, zNodes);//重新加载ztree默认全部不选择
	}
	function hideMenuAdd() {
		$("#menuContentAdd").slideUp("fast");
		$("body").unbind("mousedown", onBodyDownAdd);
	}
	function onBodyDownAdd(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "citySel"
				|| event.target.id == "menuContentAdd" || $(event.target).parents(
				"#menuContent").length > 0)) {
			hideMenuAdd();
		}
	}

	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemoAdd"), settingAdd, zNodesAdd);
		$(".big_green").removeClass("big_ddd");

		$(".close_tree").bind("click", function() {
			$("#menuContentAdd").fadeOut("fast");
		});
	});
</script>

<p style="padding:10px 0;">${question.serialNumber}&nbsp;${question.qcontent}</p>
<div class="detail" style="width:95%;margin:0 auto">
<!-- 		<div class="qjact_btn clearfix"> -->
<!-- 			<a href="javascript:void(0)" class="small_ea" id="batchExportDetail"><span>失分项批量导出</span></a> -->
<!-- 		</div> -->
		<table class="addform_wrap"style="margin: 0;">
			<tr>
				<td><label style="font-size: 14px;">大区：</label></td>
				<td><select id="daqu" name="daqu">
				<option value="-1">--请选择--</option>
				</select></td>
				<td><label style="font-size: 14px;">省：</label></td>
				<td><select id="sheng" name="sheng"><option value="-1">--请选择--</option></select></td>
				<td><label style="font-size: 14px;">市：</label></td>
				<td><select id="shi" name="shi"><option value="-1">--请选择--</option></select></td>
				<td><label style="font-size: 14px;">区县：</label></td>
				<td><select id="quxian" name="quxian"><option value="-1">--请选择--</option></select></td>
			</tr>
			
		</table>
		<table class="addform_wrap"style="margin: 0;">
			<tr>
				<td><label style="font-size: 14px;">评测人员：</label></td>
				<td><input id="citySel" class="text_170" type="text" 
					readonly="readonly" onclick="showMenuAdd();" name="authNames" /> <input
					id="authIds" name="authIds" type="hidden" />
					<div id="menuContentAdd" class="menuContent"
						style="display: none; position: absolute; left:120px;width:190px;height:200px;background: #fff; overflow-y: auto; border: 1px solid #02335b">
						<p class="clearfix">
							<a href="javascript:void(0)" class="close_tree">关闭</a>
						</p>
						<ul id="treeDemoAdd" class="ztree clearfix"></ul>
					</div></td>
				<td><label style="font-size: 14px;">门店名称：</label></td>
				<td><input name="storeName" id="storeName" type="text" class="text_170"/></td>
				<td><input type="button" class="search" onclick="doSearchLoseSts()" value="搜索"/></td>
				<td><input type="button" class="search" id="batchExportDetail" value="失分项批量导出"/></td>
			</tr>
		
		</table>
	<table id="gridTableLoseStsDetail"></table>
	<div id="gridPagerLoseStsDetail"></div>
</div>
<script type="text/javascript"
	src="${basePath}/js/loseStsManager/showDetail.js"></script>
<script type="text/javascript">
$(function(){
	//加载大区
	loadCity("",'daqu');
	$('#daqu').change(function(){
		//加载省
		loadCity($('#daqu').val(),'sheng');
	});
	$('#sheng').change(function(){
		//加载市
		loadCity($('#sheng').val(),'shi');
	});
	$('#shi').change(function(){
		//加载区县
		loadCity($('#shi').val(),'quxian');
	});
});

function loadCity(cityId,divId){
	  $.ajax({
			async : false,
			type : 'post',
			url : basePath + "/examManager/getCityList",
			data : {
				"pId" :cityId
			},
			dataType : "json",
			cache : false,
			error : function(err) {
				alert("系统出错了，请联系管理员！");
			},
			success : function(data) {
				$('#'+divId).empty();
				var html='<option value="-1">--请选择--</option>';
				$.each(data,function(i, value) {
					html+="<option value="+value.id+">"+value.area+"</option>";
				});
				$('#'+divId).append(html);
			}
		});
}

	$("#batchExportDetail").bind(
			'click',
			function() {
				var selectRows = jQuery("#gridTableLoseStsDetail").jqGrid(
						'getGridParam', 'selarrrow');
				if (selectRows == null || selectRows.length == 0) {
					alert("请选择要导出的失分项");
					return;
				}
				var url = basePath + "/examManager/examportLoseDetail?ids="
						+ selectRows.join(',')+"&id="+id;
				window.location = url;
			});
	function doSearchLoseSts(){
		$("#gridTableLoseStsDetail").jqGrid().trigger("reloadGrid");
	}
</script>
