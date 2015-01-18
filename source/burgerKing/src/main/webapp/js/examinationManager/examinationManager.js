$(function() {
	$("#gridTableExam")
	.jqGrid(
			{
				url : basePath + "/examManager/list",
				mtype : "POST",
				postData : {
				},
				altRows : true,
				forceFit:true,
				altclass : "jqgrid_alt_row",
				datatype : "json",
				/*
				 * width : 958, height : 300, autowidth : true,
				 */
				multiselect : true,
				rownumbers : true,
				rownumWidth:"80",
				viewsortcols : [ true, 'vertical', true ],
				colModel : [
							{
								name : "id",
								hidden : true,
								width : 200
							},
							
							{
								label : "试卷名称",
								name : "examName",
								title : false,
								sortable : false,
								width : 200,
								formatter : function(cellvalue, options,
										rowObject) {
									var id = rowObject.id;
									var html = "<a href='javascript:void(0);' onclick='queryExamDetail("+id+")' style='color:blue;'>"+ cellvalue +"<a>";
									return html;
								}
								
							},
							{
								label : "试卷类型",
								name : "examTypeName",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "创建时间",
								name : "createTime",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "操作",
								sortable : false,
								width :200,
								formatter : function(cellvalue, options,
										rowObject) {
									var id = rowObject.id;
									var 
									html = "<input type='button' class='khd' title='客户端' value='客户端' onclick=\"viewAnswerInfo(\'"
											+ id + "\')\"/>";
									html += "<input type='button' class='wjxf' title='问卷下发' value='问卷下发' onclick=\"assign(\'"
										+ id + "\')\"/>";
									html += "<input type='button' class='edit' title='修改' value='修改' onclick=\"modify(\'"
										+ id + "\')\"/>";
									html += "<input type='button' class='del' title='删除' value='删除' onclick=\"del(\'"
											+ id + "\')\"/>";
									return html;
								}
							} ],
				rowNum : 10,
				rowList : [ 10, 20, 50 ],
				pager : "#gridPagerExam",
				viewrecords : true,
				jsonReader : {
					repeatitems : false
				},
				autowidth : true,
				width : "100%",
				height : "100%",
				gridComplete : function() {
					var neww = $(".ui-jqgrid").width();
					var tablew = $(".ui-jqgrid-btable").width();
					if(tablew<neww){
						$(".ui-jqgrid-htable").width(neww);
						$(".ui-jqgrid-btable").width(neww);
					}
				}
			});
	
});

// 查看答题情况
function viewAnswerInfo(id) {
	var url = basePath + "/examManager/userAnswerInfo?examId=" + id;
	window.location.href = url;
}

//问卷下发
function assign(id){
	var url =basePath+"/base/toPaperAssign?examId=" + id;
    tipsWindown("表单下发","url:post?"+url,"660","400","true","","true","");	
}

//修改问卷
function modify(id) {
	var url = basePath + "/examManager/modify?examId=" + id;
	window.location.href = url;
}

// 删除
function del(id) {
	if(confirm('确定删除么?')){
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/examManager/deleteExam",
			data : {
				"examId" : id
			},
			dataType : "json",
			cache : false,
			error : function(err) {
				alert("系统出错了，请联系管理员！");
			},
			success : function(data) {
				if(data.success){
					alert('删除成功');
				}else{
					alert(data.msg);
				}
				$("#gridTableExam").jqGrid().trigger("reloadGrid");
			}
		});
	}
}

function queryExamDetail(examId){
	var url = basePath + "/examManager/examPageDetail?examId=" + examId;
	window.location.href = url;
}

$('#addExamPage').bind('click',function(e){
	var url = basePath + "/examManager/addExamIndex";
	window.location.href = url;
});

$('#importfile').bind('click',function(e){
	var url = basePath + "/toolKit/index";
	tipsWindown("上传文件","url:post?"+url,"1024","666","true","","true","");
});

$("#addFacility").bind("click",function(event){
	var url = basePath + "/facility/addOrUpdatePage";
	tipsWindown("添加设备","url:post?"+url,"660","400","true","","true","");
	
});

