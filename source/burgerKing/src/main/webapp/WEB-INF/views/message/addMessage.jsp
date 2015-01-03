<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<!-- 	<form id="form1" method="post"> -->
		<table class="addform_wrap">
			<tr>
				<td><b>*</b>消息类型：</td>
				<td>
					<select id="messageType" name="messageType">
						<option value="-1">---请选择---</option>
						<option value="1">公告</option>
						<option value="2">紧急</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="title"><b>*</b>消息名称：</td>
				<td><input type="text" id="pushTitle" name="pushTitle"
					class="text" value="${message.pushTitle}" /></td>
			</tr>
			<tr>
				<td class="title"><b>*</b>消息内容：</td>
				<td>
					<textarea id="pushContent" name="pushContent">${message.pushContent}</textarea></td>
			</tr>
			<tr>
				<td class="title"></td>
				<td>
					<div class="qjact_btn clearfix">
						<a href="javascript:void(0)" onclick="closeOutWindow()" class="big_ea"><span>取消</span></a>
						<a href="javascript:void(0)"  class="big_green" id="btn_save"><span>保存</span></a>
					</div>
				</td>
			</tr>
			<%-- <tr>
				<td>消息状态：</td>
				<td><input type="text" id="state" name="state"
					class="text" value="${message.state}" /></td>
			</tr>
			<tr>
				<td>创建时间：</td>
				<td><input type="text" id="createTime" name="createTime"
					class="text" value="${message.createTime}" /></td>
			</tr>
			<tr>
				<td>推送时间：</td>
				<td><input type="text" id="pushTime" name="pushTime"
					class="text" value="${message.pushTime}" /></td>
			</tr>
			<tr>
				<td>是否读取：</td>
				<td><input type="text" id="isRead" name="isRead"
					class="text" value="${message.isRead}" /></td>
			</tr>
			<tr>
				<td>创建者：</td>
				<td><input type="text" id="creater" name="creater"
					class="text" value="${message.creater}" /></td>
			</tr>
			<tr>
				<td>发送者：</td>
				<td><input type="text" id="sender" name="sender"
					class="text" value="${message.sender}" /></td>
			</tr>
			<tr>
				<td>接受者：</td>
				<td><input type="text" id="receiver" name="receiver"
					class="text" value="${message.receiver}" /></td>
			</tr> --%>
			<%-- <tr>
				<td>创建时间</td>
				<td><input type="text" id="createTime" name="createTime"
					class="Wdate input_w150"value="${shop.createTime}"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					maxlength="10" readonly />
				</td>
			</tr> --%>
			
<!-- 			<tr> -->
<!-- 				<td>消息是否推送：</td> -->
<%-- 				<td><input type="text" id="state" name="state" value="${message.state}" class="text" /></td> --%>
<!-- <!-- 					<td><input type="radio" name="state" value="0" checked="checked"/> 是</td> --> 
<!-- <!-- 					<td><input type="radio" name="state" value="1" /> 否</td> --> 
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td>消息创建者：</td> -->
<%-- 				<td><input type="text" id="creater"  value="${message.creater}" name="creater" --%>
<!-- 					class="text" /></td> -->
<!-- 			</tr> -->
		</table>
		
<!-- 	</form> -->
<script type="text/javascript">
$(document).ready(function(){
	$(".big_green").removeClass("big_ddd");
});
var messageType = "${message.messageType}";
$('#messageType').val(messageType);
function message(){
	var id;
	var pushTitle;
	var pushContent;
	var receiver;
	var state;
	var createTime;
	var pushTime;
	var isRead;
	var creater;
	var sender;
	var messageType;
}
var id='${message.id}';
var receiver='${message.receiver}';
var state='${message.state}';
var createTime='${message.createTime}';
var pushTime='${message.pushTime}';
var isRead='${isRead.isRead}';
var creater='${message.creater}';
var sender='${message.sender}';
var messageType='${message.messageType}';
$("#btn_save").bind("click",function(){
	var _obj = $(this),_cname = _obj.prop("class");
	if(_cname.indexOf("big_ddd")>=0){
		return;
	}
	
	var vo = new message();
	vo.id = id;
	vo.pushTitle = $.trim($('#pushTitle').val());
	vo.pushContent = $.trim($('#pushContent').val());
	vo.messageType = $.trim($('#messageType').val());
	vo.state = state;
	vo.createTime = createTime;
	vo.pushTime = pushTime;
	vo.isRead = isRead;
	vo.creater = creater;
	vo.sender = sender;
	vo.receiver = receiver;
	if(vo.messageType == "-1"){
		alert("请选择消息类型！");
		$("#messageType").focus();
	}else if(vo.pushTitle == ""){
		alert("请输入消息名称！");
		$("#pushTitle").focus();
	}else if(vo.pushContent == ""){
		alert("请输入消息内容！");
		$("#pushContent").focus();
	}else {
		_obj.addClass("big_ddd").removeClass("big_green");
		$.ajax({
			type : 'post',
			url : "${basePath}/message/add",
			data : {"jsons":JSON.stringify(vo)},
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
				$("#gridTableMessage").jqGrid().trigger("reloadGrid");
			}
		})
	}
});
</script>
