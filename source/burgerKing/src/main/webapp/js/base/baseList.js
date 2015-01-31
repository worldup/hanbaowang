$(function() {
	$("#gridTableBase")
			.jqGrid(
					{
						url : basePath + "/base/list",
						mtype : "POST",
						postData : {
							"baseName" : function() {
								return $.trim($('#baseName').val());
							}
						},
						forceFit:true,
						altRows : true,
						altclass : "jqgrid_alt_row",
						datatype : "json",
						multiselect : false,
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
									label : "上级",
									name : "pvalue",
									title : false,
									width : 300,
									sortable : false,
									formatter : function(cellvalue, options,
											rowObject) {
										return cellvalue==null?"-":cellvalue;
									}
								},
								{
									label : "类型",
									name : "bvalue",
									title : false,
									width : 300,
									sortable : false
								},
								{
									label : "是否筛选",
									name : "brushElection",
									title : false,
									sortable : false,
									formatter:'select',
									editoptions:{value:"0:否;1:是"}
								},
								{
									label : "描述",
									name : "bcomments",
									title : false,
									width : 300,
									sortable : false
								},
								{
									label : "是否关键项",
									name : "isKey",
									title : false,
									width : 300,
									sortable : false,
									formatter:'select',
									editoptions:{value:"0:否;1:是"}
								},
								{
									label : "操作",
									sortable : false,
									width : 300,
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
						pager : "#gridPagerBase",
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
	
	$("#baseName").val("");
	//文本框搜索
	var searchtxt = $("#baseName");
	searchtxt.focus(function(){
		$(".defalut_val").hide();
	}).blur(function(){
		if(searchtxt.val() == ""){
			$(".defalut_val").show();
		}
	});
});
function detail(id) {
	var url =basePath+"/base/get?id="+id;
    tipsWindown("查看表单类型","url:post?"+url,"620","400","true","","true","");	
}
// 修改

	function modify(id) {
		var url =basePath+"/base/create?id="+id;
	    tipsWindown("修改表单类型","url:post?"+url,"620","400","true","","true","");	

}
// 删除
function del(id) {
	if(confirm('确定删除么?')){
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/base/del",
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
				$("#gridTableBase").jqGrid().trigger("reloadGrid");
			}
		});
	}
}

function addUser(){
	var url =basePath+"/base/create";
    tipsWindown("添加评估表类型","url:post?"+url,"620","400","true","","true","");
}

