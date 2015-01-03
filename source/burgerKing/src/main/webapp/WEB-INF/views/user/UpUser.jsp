<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
		<table>
			<tr>
				<td class="title"><b>*</b>用户名：</td>
				<td><input type="text" id="name" name="name"
					class="text" value="${user.name}"/></td>
			</tr>
			<tr>
				<td class="title"><b>*</b>密码：</td>
				<td><input type="password" id="pwd" name="pwd"
					class="text" value="${user.pwd}" /></td>
			</tr>
			<tr>
				<td class="title"><b>*</b>真实姓名：</td>
				<td><input type="text" id="realname" name="realname"
					class="text" value="${user.realname}"/></td>
			</tr>
			<tr>
				<td class="title"><b>*</b>所属角色：</td>
				<td  align="left">
				<select name="role" id="role">
					<option value="1">OM</option>
					<option value="2">OC</option>
					<option value="3">总监</option>
				</select></td>
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
				<%-- <tr>
				<td>注册时间：</td>
				<td><input type="text" id="createdate" name="createdate"
					class="Wdate input_w150"value="${user.createdate}"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					maxlength="10" readonly /></td>
			</tr>
				<tr>
				<td>更新时间：</td>
				<td><input type="text" id="modifydate" name="modifydate"
					class="Wdate input_w150"value="${user.modifydate}"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					maxlength="10" readonly /></td>
			</tr> --%>
		</table>
<!-- 		<input type="button" id="btn_save" value="保存"/> -->
<script type="text/javascript">
$(function(){
	$(".big_green").removeClass("big_ddd");
	
	if("${not empty user}"){
		$('#role').val('${user.role}');
	}
});
function user(){
	var id;

	var name;

	var pwd;

	var realname;

	var createdate;

	var modifydate;
	
	var role;
}

var id='${user.id}';
$("#btn_save").bind("click",function(){
	//避免重复提交
	var _obj = $(this),_cname = _obj.prop("class");
	if(_cname.indexOf("big_ddd")>=0){
		return;
	}
	
	
	var vo =new user();
	vo.id=id;
	vo.name=$.trim($('input[name=name]').val());
	vo.pwd=$.trim($('input[name=pwd]').val());
	vo.realname=$.trim($('input[name=realname]').val());
	vo.createdate=$.trim($('input[name=createdate]').val());
	vo.modifydate=$.trim($('input[name=modifydate]').val());
	vo.role=$.trim($('select[name=role]').val());
	var url = "${basePath}/user/addUser";
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
			$("#gridTableUser").jqGrid().trigger("reloadGrid");
		}
	});
	
	
});
</script>

