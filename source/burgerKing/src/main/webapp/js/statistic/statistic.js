$(function() {
	$("#gridTableStatistic")
	.jqGrid(
			{
				url : basePath + "/statistic/list",
				mtype : "POST",
				altRows : true,
				altclass : "jqgrid_alt_row",
				datatype : "json",
				multiselect : false,
				forceFit:true,
				rownumbers : true,
				rownumWidth:"80",
				viewsortcols : [ true, 'vertical', true ],
				colModel : [
							{
								label : "用户",
								name : "userName",
								width : 200,
								sortable : false,
							},
							{
								label : "总的发送消息",
								name : "messageCountInfo.totalSendMsgNum",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "总的接受消息",
								name : "messageCountInfo.totalReciveMsgNum",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "公告消息发送数",
								name : "messageCountInfo.noticeMsgSendNum",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "公告消息接受数",
								name : "messageCountInfo.noticeMsgReciveNum",
								title : false,
								sortable : false,
								width : 200
							}],
				rowNum : 10,
				rowList : [ 10, 20, 50 ],
				pager : "#gridPagerStatistic",
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

