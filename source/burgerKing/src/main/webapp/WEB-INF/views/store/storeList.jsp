<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>汉堡王首页</title>
</head>
<body>
<div class="dzwidth_960 store">
	<table class="qjabtn_wrap" id="searchDiv">
		<tr>
			<td>
				<div class="qjact_btn clearfix">
					<a class="green" name="addStore" id="addStore" onclick="addStore();" href="javascript:void(0)"><span>新增</span></a>
					<a href="javascript:void(0)" id="batchImport" class="small_ea"><span>批量导入</span></a>
					<a href="javascript:void(0)" id="downloadTemp" class="small_ea"><span>下载模板</span></a>
				</div>
			</td>
			</tr>
		</table>

	<table class="qjabtn_wrap" >
		<tr>
			<td>
				<label style="font-size:14px;">区域：</label>
			</td>
			<td>
				<div class=" clearfix">
					<select id="searchRegion" name="searchRegion" class="select">
					</select>
				</div>
			</td>
			<td>
				<label style="font-size:14px;">OM：</label>
			</td>
			<td>
				<div class="clearfix">
					<select name="OMID" id="OMID" multiple="multiple" class="stxt">
					</select>
				</div>
			</td>
			<td>
				<label style="font-size:14px;">门店名称：</label>
			</td>
			<td>
				<div class=" clearfix">
					<input type="text" class="stxt" name="storeName" id="storeName"/>
				</div>
			</td>
			<td >
				<label style="font-size:14px;">OC：</label>
			</td>

			<td>
				<div class=" clearfix">

					<input id="realName" class="easyui-combobox" name="realName"/>
					<input type="button" value="搜&nbsp;索" id="searchBtn" class="search" />
				
				</div>
			</td>
		</tr>
	</table>
	<table id="gridTableStore"></table>
	<div id="gridPagerStore"></div>
	
</div>
<link rel="stylesheet" type="text/css" href="${basePath}/js/multiselect/css/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/js/multiselect/assets/style.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/js/multiselect/assets/prettify.css" />
<script type="text/javascript" src="${basePath}/js/multiselect/js/jquery.multiselect.js"></script>
<script type="text/javascript" src="${basePath}/js/multiselect/assets/prettify.js"></script>
<script type="text/javascript" src="${basePath}/js/store/storeList.js"></script>

<script type="text/javascript">
	
	$(document).ready(function(){
		//初始化区域
		$.post("${basePath}"+"/area/getRootArea",{},function(data){
			var jsonResult=$.parseJSON(data);
			var html='<option value="0">---请选择---</option>';
			$.each(jsonResult,function(i, value) {
				var seled = "",val = value.id;
				html+="<option "+seled+" value="+val+">"+value.area+"</option>";
			});
			$('#searchRegion').append(html);

		})

		//初始化OM
		$.post("${basePath}"+"/user/loadUser",{role:1},function(data){
			var jsonResult=$.parseJSON(data);
			var html="";
			$.each(jsonResult,function(i, value) {
				html+="<option  value="+value.id+">"+value.name+"</option>";
			});
			$('#OMID').append(html);
			$("#OMID").multiselect({
				minWidth: 250,
				noneSelectedText: '请选择',
				checkAllText: '全选',
				uncheckAllText: '取消全选'
			}  );

		})
		//初始化oc
		var localCache=[];
		$.post("${basePath}"+"/user/loadUser",{role:2},function(data){
			var jsonResult=$.parseJSON(data);
			$.each(jsonResult,function(i, value) {
				localCache.push({ 'label': value.name, 'value': value.value })
			});
			console.log(localCache)
			$('#realName').combobox({
				data:localCache,
				valueField:'value',
				textField:'label'
			});

		})

		$("#searchBtn").bind("click",function(){
			$("#gridTableStore").setGridParam({page:1}).jqGrid().trigger("reloadGrid");
    });
		$("#batchImport").bind('click',function(){
			var url = basePath + "/store/import";
			tipsWindown("批量导入","url:post?"+url,"660","400","true","","true","");
		});
		$("#downloadTemp").bind('click',function(){
			var url = basePath + "/store/downloadTemp";
			window.location = url;
		});
		$('#test').bind('click',function(){
			var url =basePath+"/base/toPaperAssign?examId=1";
		    tipsWindown("问卷下发","url:post?"+url,"660","400","true","","true","");	
		});
	});
	
</script>
</body>
</html>
