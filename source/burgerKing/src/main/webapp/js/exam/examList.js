$(function() {
	$("#gridTableExam")
			.jqGrid(
					{
						url : basePath + "/exam/list",
						mtype : "POST",
						postData : {
							"examName" : function() {
								return $.trim($('#examName').val());
							}
						},
						altRows : true,
						forceFit:true,
						altclass : "jqgrid_alt_row",
						datatype : "json",
						multiselect : false,
						rownumbers : true,
						rownumWidth:"80",
						viewsortcols : [ true, 'vertical', true ],
						colModel : [
									{
										name : "id",
										hidden : true
									},
									{
										label : "名称",
										name : "name",
										sortable : false,
										width : 300
									},
									{
										label : "时长",
										name : "timer",
										sortable : false,
										width : 80
									},
									{
										label : "发布日期",
										name : "date",
										sortable : false,
										width : 300
									},
									{
										label : "编号",
										name : "num",
										sortable : false,
										width : 80
									},
									{
										label : "类型",
										name : "type",
										sortable : true,
										width :100,
										formatter:'select',
										editoptions:{value:"1:DVD LIST;2:营运警戒列表;"}
									},
									{
										label : "操作",
										sortable : false,
									width : 150,
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
						pager : "#gridPagerExam",
						viewrecords : true,
						jsonReader : {
							repeatitems : false
						},
						sortname:"t.id",
						sortorder:"desc",
						sortable:true,
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
	
	$('#examName').val("");
	//文本框搜索
	var searchtxt = $("#examName");
	searchtxt.focus(function(){
		$(".defalut_val").hide();
	}).blur(function(){
		if(searchtxt.val() == ""){
			$(".defalut_val").show();
		}
	});
});

//查看
function detail(id) {
	var url =basePath+"/exam/get?id="+id;
    tipsWindown("查看参照表","url:post?"+url,"660","400","true","","true","");	

}
// 修改
function modify(id) {
	var url =basePath+"/exam/create?id="+id;
    tipsWindown("修改参照表","url:post?"+url,"660","400","true","","true","");	
}
//删除
function del(id) {
	if(confirm('确定删除么?')){
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/exam/del",
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
				$("#gridTableExam").jqGrid().trigger("reloadGrid");
			}
		});
	}
}
function addExam(){
	var url =basePath+"/exam/create";
    tipsWindown("添加问卷参照表","url:post?"+url,"660","400","true","","true","");	
}