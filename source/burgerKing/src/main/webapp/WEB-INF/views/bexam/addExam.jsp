<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
		<table class="addform_wrap">
			<tr>
				<td class="title"><b>*</b>类型：</td>
				<td  align="left">
				<select name="type" id="type">
					<option value="-1">---请选择---</option>
					<option value="1">DVD LIST</option>
					<option value="2">营运警戒列表</option>
				</select>
				</td>
			</tr>
			<tr>
				<td class="title"><b>*</b>名称：</td>
				<td><input type="text" id="name" name="name"
					class="text" value="${exam.name}"/></td>
			</tr>
			
			<tr id="timerTr">
				<td class="title"><b></b>时长：</td>
				<td><input type="text" id="timer" name="timer"
					class="text" value="${exam.timer}"/></td>
			</tr>
			<tr id="dateTr">
				<td class="title"><b>*</b>时间：</td>
				<td><input type="text" id="date" name="date" 
					class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${exam.date}"/></td>
			</tr>
			<tr id="numTr">
				<td class="title"><b>*</b>编号：</td>
				<td><input type="text" id="num" name="num"
					class="text" value="${exam.num}"/></td>
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
$(function(){
	$(".big_green").removeClass("big_ddd");
	$("#type").val('${exam.type}');
	var _type = $("#type").val();
	showTr(_type);
	$("#type").change(function(){
		var _sval = $(this).find("option:selected").val();
		showTr(_sval);
	});
});
function showTr(sval){
	if(sval=="1"){
		$("#timerTr").show();
		$("#dateTr").hide();
		$("#numTr").hide();
	}
	if(sval=="2"){
		$("#timerTr").hide();
		$("#dateTr").show();
		$("#numTr").show();
	}
	if(sval=="-1"){
		$("#timerTr").show();
		$("#dateTr").show();
		$("#numTr").show();
	}
}
function exam(){
	var id;

	var name;

	var timer;
	
	var date;
	
	var num;
	
	var type;
}

var id='${exam.id}';
$("#btn_save").bind("click",function(){
	//避免重复提交
	var _obj = $(this),_cname = _obj.prop("class");
	if(_cname.indexOf("big_ddd")>=0){
		return;
	}
	
	if ($.trim($('#type').val()) == '-1') {
		alert('请选择类型');
		return false;
	}else{
		
		if ($.trim($('#name').val()) == '') {
			alert('请输入名称');
			return false;
		}
		
		//根据选择的类型验证显示出来的属性是否输入
		var _sval = $.trim($('#type').val());
		if(_sval=="1"){
			var _timer = $.trim($('#timer').val());
			
			/* if (_timer == '') {
				alert('请输入时长');
				return false;
			} */
			
			if(isNaN(_timer)){
				alert('时长输入数字');
				return false;
			}
		}
		
		if(_sval=="2"){
			
			if ($.trim($('#date').val()) == '') {
				alert('请输入时间');
				return false;
			}
			
			if($.trim($('#num').val()) == ''){
				alert('请输入编号');
				return false;
			}
		}
		
	}
	var vo =new exam();
	vo.id=id;
	vo.name=$.trim($('input[name=name]').val());
	vo.timer=$.trim($('input[name=timer]').val());
	vo.date=$.trim($('input[name=date]').val());
	vo.num=$.trim($('input[name=num]').val());
	vo.type=$.trim($('select[name=type]').val());
	var url = "${basePath}/exam/add";
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
			$("#gridTableExam").jqGrid().trigger("reloadGrid");
		}
	});
	
	
});
</script>

