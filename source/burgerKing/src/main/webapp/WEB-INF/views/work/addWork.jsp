<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
		<table class="addform_wrap">
			<tr>
				<td class="title"><b>*</b>类型名称：</td>
				<td><input type="text" id="typename" name="typename"
					class="text" value="${work.typename}"/></td>
			</tr>
			<tr>
				<td class="title"><b>*</b>类型角色：</td>
				<td align="left">
					<input style="vertical-align: -2px;margin-right:3px;" type="checkbox" id="checkall"/>全选
					<input  class="ckbox" id="typerole" name="typerole" type="checkbox" value="OC" />OC
					<input id="typerole" name="typerole" class="ckbox" type="checkbox" value="OM" />OM
					<input id="typerole" name="typerole" class="ckbox" type="checkbox" value="OM+" />OM+
				</td>
			</tr>
	<tr>
		<td class="title"><b>*</b>频率：</td>
		<td align="left"><select name="frequency" id="frequency">
				<option value="-1">---请选择---</option>
				<option value="1">每周</option>
				<option value="2">月度</option>
				<option value="3">季度</option>
				<option value="4">需要时</option>
		</select> <input type="hidden" value="${work.frequency}" id="freval" /></td>
	</tr>
	<tr>
		<td class="title"><b>*</b>是否问卷类型:</td>
		<td align="left"><select id="isExamType" name="isExamType">
				<option value="-1">---请选择---</option>
				<option value="1">是</option>
				<option value="0">否</option>
				 
		</select><input type="hidden" value="${work.isExamType}" id="isExamTypeHid" /></td>
	</tr>
	<tr>
		<td class="title"><b>*</b>是否针对门店:</td>
		<td align="left"><select id="isToStore" name="isToStore">
				<option value="-1">---请选择---</option>
				<option value="1">是</option>
				<option value="0">否</option>
				 
		</select><input type="hidden" value="${work.isToStore}" id="isToStoreHid" /></td>
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
var typerole='${work.typerole}';
$(document).ready(function(){
	var arr = typerole.split(",");
	for(var i = 0;i<arr.length;i++){
		$("input[value='"+arr[i]+"']").prop("checked",true);
	}
	
	//窗体加载时先去除按钮重复提交标记
	$(".big_green").removeClass("big_ddd");
	
	//全选
	$("#checkall").change(function(){
		var _obj = $(this);
		if(_obj.prop("checked")){
			$("input[name=typerole]").prop("checked",true);
		}else{
			$("input[name=typerole]").prop("checked",false);
		}
	});
	//全部选中时，全选选中
	$(".ckbox").change(function(){
		var _obj = $(this);
		var _ckedlen = $(".ckbox:checked").length;
		var _cklen = $(".ckbox").length;
		if(_obj.prop("checked")){
			if(_ckedlen == _cklen){
				$("#checkall").prop("checked",true);
			}
		}else{
			$("#checkall").prop("checked",false);
		}
	});
	var _ckedlen = $(".ckbox:checked").length;
	var _cklen = $(".ckbox").length;
	if(_ckedlen == _cklen){
		$("#checkall").prop("checked",true);
	}
		
		
	var freval = $("#freval").val();
	if(freval!=null&&freval!=""){
		$("#frequency option[value="+freval+"]").prop("selected",true);
	}
	var freval1 = $("#freval1").val();
	if(freval1!=null&&freval1!=""){
		$("#related option[value="+freval1+"]").prop("selected",true);
	}
	var isExamTypeHid = $("#isExamTypeHid").val();
	if(isExamTypeHid!=null&&isExamTypeHid!=""){
		$("#isExamType option[value="+isExamTypeHid+"]").prop("selected",true);
	}
	var isToStoreHid = $("#isToStoreHid").val();
	if(isToStoreHid!=null&&isToStoreHid!=""){
		$("#isToStore option[value="+isToStoreHid+"]").prop("selected",true);
	}
});


function work(){
	var id;

	var typename;

	var typerole;
	
	var frequency;
	var isExamType;//是否问卷类型 added by zhaojinhua
	var isToStore;//是否针对门店 added by zhaojinhua
}

var id='${work.id}';
$("#btn_save").bind("click",function(){
	//避免重复提交
	var _obj = $(this),_cname = _obj.prop("class");
	if(_cname.indexOf("big_ddd")>=0){
		return;
	}
	if($.trim($('#typename').val())==''){
		alert('请输入类型名称!');
		return false;
	}
	
	var rcked = $('input[name=typerole]:checked');
	if(rcked.length==0){
		alert('请选择类型角色!');
		return false;
	}
	
	if($.trim($('#frequency').val())=='-1'){
		alert('请选择频率!');
		return false;
	}
	if($.trim($('#isExamType').val())=='-1'){
		alert('请选择是否问卷类型!');
		return false;
	}
	if($.trim($('#isToStore').val())=='-1'){
		alert('请选择是否针对门店!');
		return false;
	}
	var vo =new work();
	vo.id=id;
	vo.typename=$.trim($('#typename').val());
	var typeRole = [];
	$('input[name=typerole]:checked').each(function(){
		typeRole.push($(this).val());
	});
	vo.typerole=typeRole.join(',');
	vo.frequency=$.trim($('select[name=frequency]').val());
	vo.isExamType = $.trim($('#isExamType').val());
	vo.isToStore = $.trim($('#isToStore').val());
	
	var url = "${basePath}/work/add";
	_obj.addClass("big_ddd").removeClass("big_green");
	
	$.ajax({
		type : 'post',
		url : url,
		data : {
			"jsons":JSON.stringify(vo)
		},
		dataType : "json",
		cache : false,
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
			$("#gridTableWork").jqGrid().trigger("reloadGrid");
		}
	});
	
	
});
</script>

