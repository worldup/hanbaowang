$(function() {
	$("#gridTableShopStatistic")
	.jqGrid(
			{
				url : basePath + "/shopStatistic/list",
				mtype : "POST",
				postData : {
					"sales" : function() {
						return $.trim($('#sales').val());
					},
					"month" : function() {
						return $.trim($('#month').val());
					}
				},
				altRows : true,
				altclass : "jqgrid_alt_row",
				datatype : "json",
				/*
				 * width : 958, height : 300, autowidth : true,
				 */
				multiselect : false,
				forceFit:true,
				rownumbers : true,
				rownumWidth:"80",
				viewsortcols : [ true, 'vertical', true ],
				colModel : [
							{
								name : "id",
								hidden : true
							},
							{
								label : "门店编号",
								name : "shopId",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "月份",
								name : "month",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "门店名称",
								name : "shopName",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "sales",
								name : "sales",
								sortable : true,
								width : 200
							},
							{
								label : "tc",
								name : "tc",
								sortable : true,
								width : 200
							}/*,
							{
								label : "评测分数1",
								name : "score1",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "评测分数2",
								name : "score2",
								title : false,
								sortable : false,
								width : 200
							}*/],
				rowNum : 10,
				rowList : [ 10, 20, 50 ],
				pager : "#gridPagerShopStatistic",
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
	//文本框搜索
	$("#sales").val("");
	var sales = $("#sales");
	sales.focus(function(){
		sales.next(".defalut_val").hide();
	}).blur(function(){
		if(sales.val() == ""){
			sales.next(".defalut_val").show();
		}
	});
	//文本框搜索
	$("#month").val("");
	var month = $("#month");
	month.focus(function(){
		month.next(".defalut_val").hide();
	}).blur(function(){
		if(month.val() == ""){
			month.next(".defalut_val").show();
		}
	});
	
});

