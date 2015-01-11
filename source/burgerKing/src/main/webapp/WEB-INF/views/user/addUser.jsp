<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
		<table class="addform_wrap">
			<tr>
				<td class="title"><b>*</b>用户名：</td>
				<td><input type="text" id="name" name="name"
					class="text" value="${user.name}"/></td>
			</tr>
			<tr>
			
				<td class="title"><b>*</b>密码：</td>
				<td><input type="password" id="pwd" name="pwd"
					class="text" value="${user.pwd}"/></td>
				<input type="hidden" value="${user.pwd}" name="pwdHidden" />
			</tr>
			<tr>
				<td class="title"><b>*</b>真实姓名：</td>
				<td><input type="text" id="realname" name="realname"
					class="text" value="${user.realname}"/></td>
			</tr>
			<tr>
				<td class="title"><b>*</b>手机号：</td>
				<td><input type="text" id="telephone" name="telephone"
					class="text" value="${user.telephone}"/></td>
			</tr>
			<tr>
				<td class="title"><b>*</b>所属角色：</td>
				<td  align="left">
				<select name="role" id="role">
					<option value="-1">---请选择---</option>
					<option value="1">OM</option>
					<option value="2">OC</option>
					<option value="3">OM+</option>
					<option value="0">超级管理员</option>
				</select></td>
			</tr>
			<tr id="sel_area_idWrap">
				<td class="title"><b>*</b>所属区域：</td>
				<td  align="left">
					<select id="sel_area_id" name="sel_area_id" class="select">
						<option value="0">---请选择---</option>
					</select>
					<input type="hidden" value="${user.areaId}" id="hiddenAreaId"/>
			</tr>
			<tr id="pidWrap">
				<td class="title"><b>*</b>上级：</td>
				<td align="left">
					<select id="pid" name="pid">
					</select>
					<input type="hidden" value="${user.pid}" id="pidvalue"/>
				</td>
			</tr>
			<tr style="display:none">
				<td class="title">注册时间：</td>
				<td align="left"><input type="text" id="createdate" name="createdate"
					class="Wdate input_w150"value="${user.createdate}"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					maxlength="10" readonly /></td>
			</tr>
			<tr>
				<td class="title"><b>*</b>职工号：</td>
				<td><input type="text" id="emp" name="emp"
					class="text" value="${user.emp}"/></td>
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
$(document).ready(function(){
	//初始化区域
	$.post(basePath+"/area/getRootArea",{},function(data){
		var jsonResult=$.parseJSON(data);
		$('#sel_area_id').empty();
		var html='<option value="0">---请选择---</option>';
		var pidvalue = $("#hiddenAreaId").val();
		$.each(jsonResult,function(i, value) {
			var seled = "",val = value.id;
			if(pidvalue!=""&&val==pidvalue){seled = "selected=selected"}
			html+="<option "+seled+" value="+val+">"+value.area+"</option>";
		});
		$('#sel_area_id').append(html);

	})
})
function setPid(role){
	 var rl = role;
	 if(role=='1'){
		  //OM
		  rl=3;
	  }else if(role=='2'){
		  //OC
		  rl=1;
	  }
	 
	  if(role!='3'&&role!='0'){
		  $.ajax({
				async : false,
				type : 'post',
				url : basePath + "/user/loadUser",
				data : {
					"role" :rl
				},
				dataType : "json",
				cache : false,
				error : function(err) {
					alert("系统出错了，请联系管理员！");
				},
				success : function(data) {
					$("#pidWrap").show();
					$('#pid').empty();
					var html='<option value="-1">---请选择---</option>';
					var pidvalue = $("#pidvalue").val();
					$.each(data,function(i, value) {
						var seled = "",val = value.value;
						if(pidvalue!=""&&val==pidvalue){seled = "selected=selected"}
						html+="<option "+seled+" value="+val+">"+value.name+"</option>";
					});
					$('#pid').append(html);
				}
			});
	  }else{
		  $("#pidWrap").hide();
		  $('#pid').empty();
	  }
}
var count=0;
$(function(){
	$(".big_green").removeClass("big_ddd");
	if("${not empty user}"){
		$('#role').val('${user.role}');
	}
	$('#role').change(function(){
	  var  role = $('#role').val();
	  setPid(role);
	});
	
	var r = $('#role').val();
	setPid(r);
});
function user(){
	var id;

	var name;

	var pwd;

	var realname;
	
	var telephone;

	var createdate;

	var modifydate;
	
	var role;
	 
	var pid;
	
	var emp;
	var areaId;
}
var id='${user.id}';
if(id!=''){
	count=1;
}
$("#btn_save").bind("click",function(){
	
	$.ajax({
		type : 'post',
		url : '${basePath}/user/countUser',
		data : {
			"username":$('#name').val()
		},
		dataType : "json",
		cache : false,
		error : function(err) {
			alert("系统出错了，请联系管理员！");
		},
		success : function(data) {
			if(data >count){
				alert('用户名已经存在');
			}
			else
			{
				$.ajax({
					type : 'post',
					url : '${basePath}/user/empUser',
					data : {
						"emp":$('#emp').val()
					},
					dataType : "json",
					cache : false,
					error : function(err) {
						alert("系统出错了，请联系管理员！");
					},
					success : function(data) {
						if(data > count){
							alert('员工号已经存在');
						}
						else
						{
							//避免重复提交
							var _obj = $('#btn_save'),_cname = _obj.prop("class");
							if(_cname.indexOf("big_ddd")>=0){
								return;
							}
							var reg = /^0?1[3|4|5|7|8][0-9]\d{8}$/;
							if($.trim($('#name').val())==''){
								alert('请输入用户名!');
								return false;
							}
							if($.trim($('#pwd').val())==''){
								alert('请输入密码!');
								return false;
							}
							if($.trim($('#realname').val())==''){
								alert('请输入真实姓名!');
								return false;
							}
							if($.trim($('#telephone').val())==''){
								alert('请输入手机号!');
								$("#telephone").focus();
								return false;
							}else{
								if(!reg.test($.trim($('#telephone').val()))){
									alert('手机号码有误!');
									return false;
								}
							}
							if($.trim($('#role').val())=='-1'){
								alert('请选择所属角色!');
								return false;
							}
							if($.trim($('#pid').val())=='-1'){
								alert('请选择上级!');
								return false;
							}
							if($.trim($('#emp').val())==''){
								alert('请输入职工号!');
								return false;
							}
							var vo =new user();
							vo.id=id;
							vo.name=$.trim($('input[name=name]').val());
							vo.pwd=$.trim($('input[name=pwd]').val());
							vo.realname=$.trim($('input[name=realname]').val());
							vo.telephone=$.trim($('input[name=telephone]').val());
							vo.createdate=$.trim($('input[name=createdate]').val());
							vo.modifydate=$.trim($('input[name=modifydate]').val());
							vo.role=$.trim($('select[name=role]').val());
							vo.pid=$.trim($('select[name=pid]').val());
							vo.emp=$.trim($('input[name=emp]').val());
							vo.areaId=$.trim($('select[name=sel_area_id]').val());
							var url = "${basePath}/user/addUser";
							var oldPwd=$.trim($('input[name=pwdHidden]').val());
							_obj.addClass("big_ddd").removeClass("big_green");
							$.ajax({
								type : 'post',
								url : url,
								data : {
									"jsons":JSON.stringify(vo),
									"oldPwd":oldPwd
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
							
						}
					}
				});
			}
		}
	});
});

</script>

