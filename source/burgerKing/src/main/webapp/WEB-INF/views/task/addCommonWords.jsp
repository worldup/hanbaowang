<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<table class="addform_wrap">
	<tr>
		<td class="title"><b>*</b>任务类型：</td>
		<td align="left"><select id="taskTypeId" name="taskTypeId"></select></td>
	</tr>
	<tr>
		<td class="title"><b>*</b>任务详情：</td>
		<td align="left"><textarea id="content" name="content">${commonwords.content}</textarea></td>
	</tr>

	<tr>
		<td class="title"></td>
		<td>
			<div class="qjact_btn clearfix">
				<a href="javascript:void(0)" onclick='$("#win").window("close",true)' class="big_ea"><span>取消</span></a>
				<a href="javascript:void(0)" class="big_green" id="btn_save"><span>保存</span></a>
			</div>
		</td>
	</tr>
</table>
<!-- <input type="button" id="btn_save" value="保存" /> -->
<script type="text/javascript">
	$(function() {
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
				$('#taskTypeId').empty();
				var html='<option value="-1">---请选择---</option>';
				$.each(data,function(i, value) {
					html+="<option valueid="+value.related+" value="+value.value+">"+value.name+"</option>";
				});
				$('#taskTypeId').append(html);
                var worktypeid="${commonwords.taskTypeId}";
                $('#taskTypeId').val(worktypeid);
			}
		});


	});




	var id = '${commonwords.id}';
	$("#btn_save").bind("click", function() {
		//避免重复提交
		var _obj = $(this),_cname = _obj.prop("class");
		if(_cname.indexOf("big_ddd")>=0){
			return;
		}
		if($.trim($('#taskTypeId').val())=='-1'){
			alert('请选择任务类型!');
			$('#taskTypeId').focus();
			return false;
		}

		if($.trim($('#content').val())==''){
			alert('任务详情不能为空!');
			$('#content').focus();
			return false;
		}

		
		
		var vo ={};
		vo.id = id;
		/* vo.name = $.trim($('input[name=name]').val()); */
		vo.content = $.trim($('#content').val());

		vo.taskTypeId = $.trim($('#taskTypeId').val());

		var url = "${basePath}/task/commonwords/save";
		_obj.addClass("big_ddd").removeClass("big_green");
		$.ajax({
			type : 'post',
			url : url ,
			data : {
                id:vo.id,
                content:vo.content,
                taskTypeId:vo.taskTypeId
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
				 $("#win").window("close",true);
				$("#gridTableStore").jqGrid().trigger("reloadGrid");
			}
		});

	});
</script>
