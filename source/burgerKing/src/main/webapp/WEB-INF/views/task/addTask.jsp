<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<table class="addform_wrap">
	<%-- <tr>
		<td class="title"><b>*</b>任务名称：</td>
		<td><input type="text" id="name" name="name" class="text"
			value="${task.name}" /></td>
	</tr> --%>
	<tr>
		<td class="title"><b>*</b>任务类型：</td>
		<td align="left"><select id="worktypeid" name="worktypeid"></select></td>
	</tr>
	<tr>
		<td class="title"><b>*</b>季度：</td>
		<td>
		<%-- <input type="text" id="quarter" name="quarter" class="text"
			value="${task.quarter}" /> --%>
			<select id="quarter" name="quarter">
				<option value="-1">---请选择---</option>
				<option value="第一季度">第一季度</option>
				<option value="第二季度">第二季度</option>
				<option value="第三季度">第三季度</option>
				<option value="第四季度">第四季度</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="title"><b>*</b>任务标识：</td>
		<td>
			<select id="isSelfCreate" name="isSelfCreate">
				<option value="-1">---请选择---</option>
				<option value="0">自主</option>
				<option value="1">委派</option>
			</select>
		</td>
	</tr>
	
	<tr>
		<td class="title"><b>*</b>任务开始时间：</td>
		<td align="left"><input type="text" id="starttime" name="starttime"
			class="Wdate input_w150" value="${task.starttime}"
			onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" maxlength="10"
			readonly /></td>
	</tr>
	<tr>
		<td class="title"><b>*</b>任务预计完成时间：</td>
		<td align="left"><input type="text" id="finishpretime" name="finishpretime"
			class="Wdate input_w150" value="${task.finishpretime}"
			onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" maxlength="10"
			readonly /></td>
	</tr>
	<tr>
		<td class="title"><b>*</b>任务详情：</td>
		<td align="left"><textarea id="content" name="content">${task.content}</textarea></td>
	</tr>
	<tr>
		<td class="title">任务预计完成结果：</td>
		<td align="left"><textarea id="preresult" name="preresult">${task.preresult}</textarea></td>
	</tr>
	<tr>
		<td class="title"><b>*</b>执行人：</td>
		<td align="left"><select id="executeid" name="executeid"></select></td>
	</tr>
	<tr id="hei">
		<td class="title"><b></b>门店名称：</td>
		<td align="left"><select id="storeid" name="storeid">
		<option value="-1">---请选择---</option></select></td>
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
<!-- <input type="button" id="btn_save" value="保存" /> -->
<script type="text/javascript">
	$(function() {
		$('#worktypeid').change(function()
		{
			var valueid = $('#worktypeid option:selected').attr('valueid');
			if(valueid=='1')	
			{
				$('#hei').show();
			}else
			{
				$('#hei').hide();
			}
		});
		
		var executeid = '${task.executeid}';
		if(executeid!=null&&executeid!=""){
			showStoreOpts(executeid);
		}
		$('#executeid').change(function(){
			var vid = $('#executeid').val();
			showStoreOpts(vid);
			
		});
		function showStoreOpts(vid){
			//加载门店信息
			$.ajax({
				async : false,
				type : 'post',
				url : basePath + "/task/loadStore",
				data : {
					"id" : vid
				},
				dataType : "json",
				cache : false,
				error : function(err) {
					alert("系统出错了，请联系管理员！");
				},
				success : function(data) {
					$('#storeid').empty();
					var html='<option value="-1">---请选择---</option>';
					$.each(data,function(i, value) {
						html+="<option value="+value.value+">"+value.name+"</option>";
					});
					$('#storeid').append(html);
				}
			});
			
		}
						
		$(".big_green").removeClass("big_ddd");
		
		
		//加载任务类型
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/task/loadTask",
			dataType : "json",
			cache : false,
			error : function(err) {
				alert("系统出错了，请联系管理员！");
			},
			success : function(data) {
				$('#worktypeid').empty();
				var html='<option value="-1">---请选择---</option>';
				$.each(data,function(i, value) {
					html+="<option valueid="+value.related+" value="+value.value+">"+value.name+"</option>";
				});
				$('#worktypeid').append(html);
			}
		});
		//加载执行人
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/task/loadUser",
			dataType : "json",
			cache : false,
			error : function(err) {
				alert("系统出错了，请联系管理员！");
			},
			success : function(data) {
				$('#executeid').empty();
				var html='<option value="-1">---请选择---</option>';
				$.each(data,function(i, value) {
					html+="<option value="+value.value+">"+value.name+"</option>";
				});
				$('#executeid').append(html);
			}
		});

	});

	var storeId="${task.storeid}";
	var worktypeid="${task.worktypeid}";
	var userid = "${task.userid}";
	var executeid = "${task.executeid}";
	var isSelfCreate = "${task.isSelfCreate}";
	var quarter = "${task.quarter}";
	$('#storeid').val(storeId);
	$('#worktypeid').val(worktypeid);
	$('#executeid').val(executeid);
	$('#isSelfCreate').val(isSelfCreate);
	$('#quarter').val(quarter);
	function task() {
		var id;
		/* var name; */
		var content;
		var starttime;
		var finishpretime;
		var finishrealtime;
		var preresult;
		var realresult;
		var userid;
		var state;
		var storeid;
		var worktypeid;
		var executeid;
		var isSelfCreate;
		var quarter;
	}

	var id = '${task.id}';
	$("#btn_save").bind("click", function() {
		//避免重复提交
		var _obj = $(this),_cname = _obj.prop("class");
		if(_cname.indexOf("big_ddd")>=0){
			return;
		}
		if($.trim($('select[name=worktypeid]').val())=='-1'){
			alert('请选择任务类型!');
			$('#worktypeid').focus();
			return false;
		}
		if($.trim($('select[name=quarter]').val())=='-1'){
			alert('请选择季度!');
			$('#quarter').focus();
			return false;
		}
		if($.trim($('select[name=isSelfCreate]').val())=='-1'){
			alert('请选择任务标识!');
			$('#isSelfCreate').focus();
			return false;
		}
		if($.trim($('input[name=starttime]').val())==''){
			alert('任务开始时间不能为空!');
			$('#starttime').focus();
			return false;
		}
		if($.trim($('input[name=finishpretime]').val())==''){
			alert('任务预计完成时间不能为空!');
			$('#finishpretime').focus();
			return false;
		}
		if($.trim($('#content').val())==''){
			alert('任务详情不能为空!');
			$('#content').focus();
			return false;
		}
	/* 	if($('select[name=storeid]:hidden').length==0){
			if($.trim($('select[name=storeid]').val())=='-1'){
				alert('请选择门店名称!');
				$('#storeid').focus();
				return false;
			}	
		} */
		
		
		if($.trim($('#executeid').val())=='-1'){
			alert('请选择执行人!');
			$('#executeid').focus();
			return false;
		}
		
		
		var vo = new task();
		vo.id = id;
		/* vo.name = $.trim($('input[name=name]').val()); */
		vo.content = $.trim($('#content').val());
		vo.starttime = $.trim($('input[name=starttime]').val());
		vo.finishpretime = $.trim($('input[name=finishpretime]').val());
		vo.preresult = $.trim($('#preresult').val());
		vo.storeid = $.trim($('select[name=storeid]').val());
		vo.worktypeid = $.trim($('select[name=worktypeid]').val());
		vo.executeid = $.trim($('#executeid').val());
		vo.isSelfCreate = $.trim($('#isSelfCreate').val());
		vo.quarter = $.trim($('#quarter').val());
		var url = "${basePath}/task/add";
		_obj.addClass("big_ddd").removeClass("big_green");
		$.ajax({
			type : 'post',
			url : url,
			data : {
				"jsons" : JSON.stringify(vo)
			},
			dataType : "json",
			cache : false,
			error : function(err) {
				alert("系统出错了，请联系管理员！");
			},
			success : function(data) {
				if (id == '') {
					alert('添加成功!');
				} else {
					alert('修改成功!');
				}
				closeOutWindow();
				$("#gridTableTask").jqGrid().trigger("reloadGrid");
			}
		});

	});
</script>
