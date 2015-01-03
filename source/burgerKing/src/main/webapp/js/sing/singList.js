$(function() {
	$("#gridTableSing")
			.jqGrid(
					{
						url : basePath + "/sing/list",
						mtype : "POST",
						postData : {
							"singName" : function() {
								return $.trim($('#singName').val());
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
									label : "用户名称",
									name : "name",
									title : false,
									sortable : false,
									width : 100
								},
								{
									name : "id",
									hidden : true,
									width : 200
								},
								{
									label : "签到时间",
									name : "signintime",
									sortable : true,
									width : 100
								},
								{
									label : "签出时间",
									name : "signouttime",
									sortable : true,
									width : 100 ,
								},
								{
									label : "经度",
									name : "signinlongitude",
									title : false,
									sortable : false,
									width : 100,
								},
								{
									label : "维度",
									name :"signinlatitude",
									sortable : false,
									width : 80,
								},
								{
									label : "地址",
									name :"location",
									sortable : false,
									width : 150,
								}
									
								 ],
						rowNum : 10,
						rowList : [ 10, 20, 50 ],
						pager : "#gridPagerSing",
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
	$("#singName").val("");
	var searchtxt = $("#singName");
	searchtxt.focus(function(){
		$(".defalut_val").hide();
	}).blur(function(){
		if(searchtxt.val() == ""){
			$(".defalut_val").show();
		}
	});
});

