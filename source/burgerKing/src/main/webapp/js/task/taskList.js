$(function() {
	$("#gridTableTask")
			.jqGrid(
					{
						url : basePath + "/task/list",
						mtype : "POST",
						postData : {
							"taskName" : function() {
								return $.trim($('#taskName').val());
							},
							"userName" : function() {
								return $.trim($('#userName').val());
							},
							"sDate" : function() {
								return $.trim($('#sDate').val());
							}
						},
						altRows : true,
						forceFit:true,
						altclass : "jqgrid_alt_row",
						datatype : "json",
						/*
						 * width : 958, height : 300, autowidth : true,
						 */
						multiselect : false,
						rownumbers : true,
						rownumWidth:"80",
						viewsortcols : [ true, 'vertical', true ],
						colModel : [
								{
									name : "id",
									hidden : true
								},
								/*{
									label : "任务名称",
									name : "name",
									sortable : false,
									width : 150
								},*/
								{
									label : "任务类型",
									name : "workTypeName",
									sortable : false,
									width : 150
								},
								{
									label : "季度",
									name : "quarter",
									sortable : false,
									width : 130
								},
								{
									label : "任务标识",
									name : "isSelfCreate",
									sortable : false,
									width : 150,
									formatter:'select',
									editoptions:{value:"0:自主;1:委派"}
								},
								{
									label : "任务详情",
									name : "content",
									sortable : false,
									width : 150
								},
								{
									label : "门店名称",
									name : "storeName",
									sortable : false,
									width : 150
								},
								{
									label : "创建用户",
									name : "userName",
									sortable : false,
									width : 150
								},
								{
									label : "任务开始时间",
									name : "starttime",
									sortable : true,
									width : 250
								},
								/*{
									label : "任务预计开始时间",
									name : "finishpretime",
									sortable : false,
									width : 180
								},
								{
									label : "任务预计完成结果",
									name : "preresult",
									sortable : false,
									width : 180
								},*/
								{
									label : "状态",
									name : "state",
									sortable : false,
									width : 150,
									formatter:'select',
									editoptions:{value:"0:未完成;1:已完成"}
								},								
								{
									label : "执行人员",
									name : "executeName",
									sortable : false,
									width : 150,
								},								
								{
									label : "操作",
									sortable : false,
									width : 250,
									formatter : function(cellvalue, options,
											rowObject) {
										var id = rowObject.id;
										var html = "<input type='button' class='details' title='查看' value='查看' onclick=\"detail(\'"
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
						pager : "#gridPagerTask",
						viewrecords : true,
						jsonReader : {
							repeatitems : false
						},
						autowidth : true,
						width : "100%",
						height : "100%",
						sortname:"t.id",
						sortorder:"desc",
						sortable:true,
						gridComplete : function() {
							var neww = $(".ui-jqgrid").width();
							var tablew = $(".ui-jqgrid-btable").width();
							if(tablew<neww){
								$(".ui-jqgrid-htable").width(neww);
								$(".ui-jqgrid-btable").width(neww);
							}
						}
					});
	//文本框搜索
	$("#taskName").val("");
	var taskName = $("#taskName");
	taskName.focus(function(){
		taskName.next(".defalut_val").hide();
	}).blur(function(){
		if(taskName.val() == ""){
			taskName.next(".defalut_val").show();
		}
	});
	//文本框搜索
	$("#userName").val("");
	var userName = $("#userName");
	userName.focus(function(){
		userName.next(".defalut_val").hide();
	}).blur(function(){
		if(userName.val() == ""){
			userName.next(".defalut_val").show();
		}
	});
	
	//文本框搜索
	$("#sDate").val("");
	var sDate = $("#sDate");
	sDate.focus(function(){
		sDate.next(".defalut_val").hide();
	}).blur(function(){
		if(sDate.val() == ""){
			sDate.next(".defalut_val").show();
		}
	});
	
});

// 查看
function detail(id) {
	var url =basePath+"/task/get?id="+id;
    tipsWindown("查看任务","url:post?"+url,"660","400","true","","true","");	

}
// 修改
function modify(id) {
	var url =basePath+"/task/create?id="+id;
    tipsWindown("修改任务","url:post?"+url,"660","400","true","","true","");	
}
//删除
function del(id) {
	if(confirm('确定删除么?')){
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/task/del",
			data : {
				"id" : id
			},
			dataType : "json",
			cache : false,
			error : function(err) {
				alert("系统出错了，请联系管理员！");
			},
			success : function(data) {
				if(data=='0'){
					alert('删除成功!');
				}
				$("#gridTableTask").jqGrid().trigger("reloadGrid");
			}
		});
	}
}
function addTask(){
	var url =basePath+"/task/create";
    tipsWindown("添加任务","url:post?"+url,"660","400","true","","true","");	
}

