$(function() {
	$("#gridTableShopRe")
			.jqGrid(
					{
						url : basePath + "/shopRe/list",
						mtype : "POST",
						postData : {
							"reName" : function() {
								return $.trim($('#reName').val());
							},
							"shopId" : function(){
								return $("#shopId").val();
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
									label : "用户名称",
									name : "name",
									title : false,
									sortable : false,
									width : 100
								},
								{
									label : "门店名称",
									name : "shopName",
									title : false,
									sortable : false,
									width : 100
								},
								{
									label : "报表",
									name : "report",
									sortable : true,
									width : 100
								},
								{
									label : "报表类型",
									name : "reportType",
									sortable : true,
									width : 100,
									formatter : function(cellvalue, options,
											rowObject) {
										return cellvalue || '-';
									}
								},
								{
									label : "创建时间",
									name : "createTime",
									sortable : true,
									width : 100 ,
								},
								{
									label : "月度",
									name : "mon",
									title : false,
									sortable : false,
									width : 100,
									formatter : function(cellvalue, options,
											rowObject) {
										
										return cellvalue.substring(0,7);
									}
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
						pager : "#gridPagerShopRe",
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
	$("#reName").val("");
	var searchtxt = $("#reName");
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
			url : basePath + "/shopRe/del",
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
				$("#gridTableShopRe").jqGrid().trigger("reloadGrid");
			}
		});
	}
}
