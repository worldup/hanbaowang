$(function() {
	$("#gridTableLoseSts")
	.jqGrid(
			{
				url : basePath + "/examManager/loseSts/list",
				mtype : "POST",
				postData : {
					"examType" : function() {
						// 拿到选中的id值
						var zTree = $.fn.zTree.getZTreeObj("treeDemo");
						var nodes = zTree.getCheckedNodes(true);
						var value='';
						for ( var i = 0; i < nodes.length; i++) {
							var node = nodes[i];
							value += node.id + ",";
						}
						value = value.substring(0, value.length - 1);
						return value;
					},
					"storeName":function(){
						return $.trim($('#storeName').val());
					}
				},
				altRows : true,
				forceFit:true,
				altclass : "jqgrid_alt_row",
				datatype : "json",
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
								label : "失分项编号",
								name : "num",
								sortable : false,
								width : 80
								
							},
							{
								label : "失分项名称",
								name : "description",
								sortable : false,
								width : 300,
								formatter : function(cellvalue, options,
										rowObject) {
									var id = rowObject.id;
									var html = "<a href='javascript:void(0);' onclick='showDetail("+id+")' style='color:blue;'>"+ cellvalue +"<a>";
									return html;
								}
							},
							{
								label : "失分项所属模块",
								name : "model",
								sortable : false,
								width : 160
							},
							{
								label : "失分",
								name : "points",
								sortable : false,
								width : 100
							},
							{
								label : "总分",
								name : "aggregate",
								sortable : false,
								width : 100
							},
							{
								label : "失分率",
								name : "losing",
								sortable : true,
								width : 100
							},
							{
								label : "试卷名称",
								name : "ename",
								sortable : false,
								width : 150
							},
							{
								label : "试卷类型",
								name : "qtype",
								sortable : false,
								width : 150
							}/*,
							{
								label : "操作",
								sortablse : false,
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
							} */],
				rowNum : 10,
				rowList : [ 10, 20, 50 ],
				pager : "#gridPagerLoseSts",
				viewrecords : true,
				jsonReader : {
					repeatitems : false
				},
				autowidth : true,
				sortname:"losePercent",
				sortorder:"desc",
				sortable:true,
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

//查询具体失分情况
function showDetail(id){
	var url =basePath+"/examManager/loseSts/showDetail?id="+id;
    tipsWindown("失分详情查看","url:post?"+url,"960","500","true","","true","");
}

