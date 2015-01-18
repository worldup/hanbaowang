$(function() {
	$("#gridTableQuestion").jqGrid(
			{
				url : basePath + "/question/list",
				mtype : "POST",
				postData : {
					"quesType" : function(){return $.trim($("#questionSel").combobox('getValue'));},
					"ids" : function(){return $('#ids').val();}
				},
				altRows : true,
				altclass : "jqgrid_alt_row",
				datatype : "json",
				/*
				 * width : 958, height : 300, autowidth : true,
				 */
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
								label : "试题类型",
								name : "questionType",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "编号",
								name : "serialNumber",
								title : false,
								sortable : false,
								width : 100
							},
							{
								label : "所属模块",
								name : "modelName",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "问题描述",
								name : "qcontent",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "分值",
								name : "qvalue",
								title : false,
								sortable : false,
								width : 100
							},
							/*{
								label : "答案",
								name : "qanswer",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "标准答案",
								name : "qstandard",
								title : false,
								sortable : false,
								width : 200
							},*/
							{
								label : "备注",
								name : "qremark",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "是否上传图片",
								name : "isUploadPic",
								sortable : true,
								width :150,
								formatter:'select',
								editoptions:{value:"1:是;2:否;"}
							},
							{
								label : "操作",
								sortable : false,
								width : 150,
								align : "left",
								formatter : function(cellvalue, options,
										rowObject) {
									var id = rowObject.id;
									var html = "<input type='button' class='details' title='查看' value='查看' onclick=\"detail(\'"
											+ id + "\')\"/>";
										html += "<input type='button' class='edit' title='修改' value='修改' onclick=\"modify(\'"
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
	//文本框搜索
	/*$("#questionName").val("");
	var searchtxt = $("#questionName");
	searchtxt.focus(function(){
		$(".defalut_val").hide();
	}).blur(function(){
		if(searchtxt.val() == ""){
			$(".defalut_val").show();
		}
	});*/
});

//查看
function detail(id) {
	var url =basePath+"/question/detail?id="+id;
    tipsWindown("查看试题","url:post?"+url,"660","400","true","","true","");
}
// 修改
function modify(id) {
	var url =basePath+"/question/addOrUpdatePage?id="+id;
    tipsWindown("修改试题信息","url:post?"+url,"660","400","true","","true","");
}
// 删除
function del(id) {
	if(confirm('确定删除么?')){
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/question/del",
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
	var url =basePath+"/question/addOrUpdatePage";
    tipsWindown("创建试题","url:post?"+url,"660","400","true","","true","");	
}


