$(function() {
	$("#gridTableImge")
			.jqGrid(
					{
						url : basePath + "/toolKit/list",
						mtype : "POST",
						postData : {
							"imgeName" : function() {
								return $.trim($('#imgeName').val());
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
									hidden : true,
									width : 200
								},
								{
									label : "标题",
									name : "originalName",
									title : false,
									sortable : false,
									width : 100
								},
								{
									label : "文件路径",
									name : "path",
									title : false,
									sortable : false,
									width : 100
								},
								{
									label : "创建时间",
									name : "createTime",
									sortable : true,
									width : 100
								},
									
									{
										label : "操作",
										sortable : false,
										width : 100,
										formatter : function(cellvalue, options,
												rowObject) {
											var id = rowObject.id;
											var html = "<input type='button' class='del' title='删除' value='删除' onclick=\"del(\'"
													+ id + "\')\"/>";
											return html;
											}
									}],
						rowNum : 10,
						rowList : [ 10, 20, 50 ],
						pager : "#gridPagerImge",
						viewrecords : true,
						jsonReader : {
							repeatitems : false
						},
						autowidth : true,
						width : "100%",
						height : "100%",
						sortname:"s.id",
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
	$("#imgeName").val("");
	var searchtxt = $("#imgeName");
	searchtxt.focus(function(){
		$(".defalut_val").hide();
	}).blur(function(){
		if(searchtxt.val() == ""){
			$(".defalut_val").show();
		}
	});
});

function del(id) {
	if(confirm('确定删除么?')){
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/toolKit/del",
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
				$("#gridTableImge").jqGrid().trigger("reloadGrid");
			}
		});
	}
}