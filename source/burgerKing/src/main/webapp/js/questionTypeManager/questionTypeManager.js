$(function() {
	$("#gridTableQuestion").jqGrid(
			{
				url : basePath + "/questionType/list",
				mtype : "POST",
				postData : {
					"quesType" : function(){return $.trim( $("#questionSel").combobox('getValue'));},
					"hValue" : function(){return $.trim($('#hValueSer').val());}
				},
				altRows : true,
				altclass : "jqgrid_alt_row",
				datatype : "json",
				multiselect : false,
				rownumbers : true,
				rownumWidth:"80",
				forceFit:true,
				viewsortcols : [ true, 'vertical', true ],
				colModel : [
							{
								name : "id",
								hidden : true
							},
							{
								label : "字段名称",
								name : "hValue",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "字段类型",
								name : "hType",
								title : false,
								sortable : false,
								width : 200,
								formatter:'select',
								editoptions:{value:"1:填空题;2:选择题;3:简答题;4:日期;5:下拉;6:评估人;7:置顶;8:上次稽核成绩;9:成绩;10:重复扣分项编号;11:时间;12:地区"}
							},
							{
								label : "抬头/问题类型标识",
								name : "type",
								title : false,
								sortable : false,
								width : 200,
								formatter:'select',
								editoptions:{value:"1:抬头;2:问题"}	
							},
							{
								label : "操作",
								sortable : false,
								width : 100,
								align : "left",
								formatter : function(cellvalue, options,
										rowObject) {
									var id = rowObject.id;
									var	html = "<input type='button' class='edit' title='修改' value='修改' onclick=\"modify(\'"
											+ id + "\')\"/>";
										html += "<input type='button' class='del' title='删除' value='删除' onclick=\"del(\'"
											+ id + "\')\"/>";
										return "<div style='text-align:center'>"+html+"</div>";
								}
							}],
				rowNum : 10,
				rowList : [ 10, 20, 50 ],
				pager : "#gridPagerQuestion",
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

// 修改
function modify(id) {
	var url =basePath+"/questionType/addOrUpdatePage?id="+id;
    tipsWindown("修改台头/问题信息","url:post?"+url,"660","400","true","","true","");
}
// 删除
function del(id) {
	if(confirm('确定删除么?')){
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/questionType/del",
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
				$("#gridTableQuestion").jqGrid().trigger("reloadGrid");
			}
		});
	}
}

function addQuestion(){
	var url =basePath+"/questionType/addOrUpdatePage";
    tipsWindown("添加台头/问题信息","url:post?"+url,"660","400","true","","true","");	
}

