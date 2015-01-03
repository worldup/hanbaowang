$(function() {
	$("#gridTableLoseStsDetail")
	.jqGrid(
			{
				url : basePath + "/examManager/loseSts/detail",
				mtype : "POST",
				postData : {
					"userIds":function(){
						// 拿到选中的id值
						var zTree = $.fn.zTree.getZTreeObj("treeDemoAdd");
						var nodes = zTree.getCheckedNodes(true);
						var value='';
						for ( var i = 0; i < nodes.length; i++) {
							var node = nodes[i];
							value += node.id + ",";
						}
						value = value.substring(0, value.length - 1);
						return value;
					},
					"daqu":function(){
						var daqu=$.trim($('#daqu').find("option:selected").text());
						return daqu=='--请选择--'?'':daqu;
					},
					"sheng":function(){
						var sheng=$.trim($('#sheng').find("option:selected").text());
						return sheng=='--请选择--'?'':sheng;
					},
					"shi":function(){
						var shi=$.trim($('#shi').find("option:selected").text());
						return shi=='--请选择--'?'':shi;
					},
					"quxian":function(){
						var quxian=$.trim($('#quxian').find("option:selected").text());
						return quxian=='--请选择--'?'':quxian;
					},
					"storeName":function(){
						return $.trim($('#storeName').val());
					},
					"id":function(){
						return id;
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
								label : "日期",
								name : "beginTime",
								sortable : false,
								width : 250
								
							},
							{
								label : "人员名称",
								name : "realName",
								sortable : false,
								width : 100
							},
							{
								label : "所属角色",
								name : "role",
								sortable : false,
								width : 100
							},
							{
								label : "门店名称",
								name : "shopName",
								sortable : false,
								width : 160
							},
							{
								label : "门店编号",
								name : "shopNum",
								sortable : false,
								width : 130
							},
							{
								label : "问题描述",
								name : "desc",
								sortable : false,
								width : 150
							},
							{
								label : "图片",
								name : "pic",
								sortable : false,
								width : 150,
								formatter : function(cellvalue, options,
										rowObject) {
									if(cellvalue==''||null==cellvalue){
										return "";
									}
									var html = "<a href='javascript:void(0);' onclick=\"showPic('"+cellvalue+"')\" style='color:blue;'>查看图片<a>";
									return html;
								}
							},
							{
								label : "得分",
								name : "testValue",
								sortable : false,
								width : 70
							},
							{
								label : "分值",
								name : "quesValue",
								sortable : false,
								width : 70
							}],
				rowNum : 10,
				rowList : [ 10, 20, 50 ],
				pager : "#gridPagerLoseStsDetail",
				viewrecords : true,
				jsonReader : {
					repeatitems : false
				},
				autowidth : true,
				sortname:"beginTime",
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


function showPic(imgid){
	  window.open(basePath+"/common/showmultipic.jsp?filenames="+imgid, "", "dialogWidth=800px;dialogHeight=600px");
	  return;
}

