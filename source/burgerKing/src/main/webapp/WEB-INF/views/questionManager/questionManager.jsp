<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${basePath}/css/zTreeStyle/zTreeStyle.css"
	type="text/css"/>
<script type="text/javascript"
	src="${basePath}/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
	src="${basePath}/js/ztree/jquery.ztree.excheck-3.5.js"></script>
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
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
	}

	function onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
				.getCheckedNodes(true), v = "",vid = "";
		for (var i = 0, l = nodes.length; i < l; i++) {
			v += nodes[i].name + ",";
			vid += nodes[i].id + ",";
		}
		if (v.length > 0)
			v = v.substring(0, v.length - 1);
		if (vid.length > 0)
			vid = vid.substring(0, vid.length - 1);
		var cityObj = $("#citySel");
		cityObj.attr("value", v);
		$("#ids").val(vid);
	}

	function showMenu() {
		var cityObj = $("#citySel");
		var cityOffset = $("#citySel").offset();
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
		if (!(event.target.id == "citySel"|| event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
			hideMenu();
		}
	}

	$(document).ready(function() {
		$("#citySel").val("");
		$('#ids').val("");
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		
		$(".big_green").removeClass("big_ddd");

		$(".close_tree").bind("click",function(){
			$("#menuContent").fadeOut("fast");
		});
		
	});
</script>
<div class="dzwidth_960 shopstatistic">
	<table class="qjabtn_wrap" id="searchDiv">
		<tr>
			<td>
				<div class="qjact_btn clearfix">
					<a class="green" name="addQuestion" id="addQuestion" onclick="addQuestion();" href="javascript:void(0)"><span>新增</span></a>
					
				</div>
			</td>
			<td><label style="font-size:14px;">试题类型：</label></td>
			<td>
				<select id="questionSel" name="questionSel"></select>
<!-- 				<div class="select_wrap" style="width:120px;"> -->
<!-- 					<span class="act_btn"><b class="choose_text" value="">---请选择---</b><i></i></span> -->
<!-- 					<ul class="select_list" style="width:118px;"> -->
<!-- 						<li></li> -->
<!-- 					</ul> -->
<!-- 				</div> -->
<!-- 				<div class="qjstxt_wrap clearfix"> -->
<!-- 					<select id="questionSel" name="questionSel"></select> -->
<!-- 				</div> -->
			</td>
			<td style="padding-left:20px;"><label style="font-size:14px;">试题模块：</label></td>
			<td >
				<div style="position:relative">
					<div class="qjstxt_wrap"><input id="citySel" class="text" type="text" readonly="readonly"
						onclick="showMenu();" name="authNames" /><input type="button" value="搜&nbsp;索" id="searchBtn" class="search" />
						<input id="ids" name="ids" type="hidden" /></div>
					
					<div id="menuContent" class="menuContent"
						style="display: none;position:absolute;top:36px;background:#fff;overflow-y:auto;height:200px; width:300px;border:1px solid #02335b">
						<p class="clearfix"><a href="javascript:void(0)" class="close_tree">关闭</a></p>
						<ul id="treeDemo" class="ztree clearfix"></ul>
					</div>
				</div>
			</td>
			
		</tr>
	</table>
	<table id="gridTableQuestion"></table>
		<div id="gridPagerQuestion"></div>
</div>
	<script type="text/javascript"
		src="${basePath}/js/questionManager/questionManager.js"></script>
	<script type="text/javascript">
</script>
<script type="text/javascript">
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
				$('#questionSel').empty();
 				var html='<option value="">---请选择---</option>'; 
				$.each(data,function(i, value) {
					html+="<option value="+value.value+">"+value.name+"</option>";
				});
				$('#questionSel').append(html);
				$('#questionSel').selectui({
					"selectwidth":130,
					"selectedtext":"---请选择---",
					"optionclickcallback":function(val,txt){
						$("#gridTableQuestion").jqGrid().trigger("reloadGrid");
						//alert(val);
						//alert(txt);
					}
				});
			}
		});
		
		$("#searchBtn").bind("click",function(){
			$("#gridTableQuestion").setGridParam({page:1}).jqGrid().trigger("reloadGrid");
		});
		
		
	});
	
	
</script>
