$(function() {
	$("#gridTableUpdate")
			.jqGrid(
					{
						url : basePath + "/update/list",
						mtype : "POST",
						postData : {
							"versionNum" : function() {
								return $.trim($('#version').val());
							}
						},
						altRows : true,
						forceFit:true,
						altclass : "jqgrid_alt_row",
						datatype : "json",
						/*
						 * width : 958, height : 300, autowidth : true,
						 */
						multiselect : false,
						rownumbers : true,
						rownumWidth:"80",
						viewsortcols : [ true, 'vertical', true ],
						colModel : [
								{
									name : "id",
									hidden : true,
									width : 90
								},
								{
									label : "版本号",
									name : "versionNum",
									title : true,
									sortable : false,
									width : 100
									/*formatter:'select',
									editoptions:{value:"1:公告;2:紧急;3:任务;4:异常"}*/
								},
								{
									label : "APK名称",
									name : "apkName",
									title : true,
									sortable : false,
									width : 100
								},
								{
									label : "文件大小",
									name : "apkSize",
									title : true,
									sortable : false,
									width : 100,
									formatter : function(cellvalue, options,
											rowObject) {
										return cellvalue+"&nbsp;字节";
									}
									/*formatter:'select',
									editoptions:{value:"0:未推送;1:已推送;null:-"}*/
								},
								
								{
									label : "服务端url",
									name : "url",
									title : true,
									sortable : false,
									width : 100
								},
								{
									label : "备注",
									name : "remark",
									title : true,
									sortable : false,
									width :150,
									formatter : function(cellvalue, options,
											rowObject) {
										return cellvalue==null?"-":cellvalue;
									}
								},
								{
									label : "更新时间",
									name : "updateTime",
									title : true,
									sortable : false,
									width :100,
									formatter : function(cellvalue, options,
											rowObject) {
										return cellvalue==null?"-":cellvalue;
									}
								},
								{
									label : "操作",
									sortable : false,
									width : 100,
									align : "left",
									formatter : function(cellvalue, options,
											rowObject) {
										var id = rowObject.id;
										html = "<input type='button' class='details' title='查看' value='查看' onclick=\"detail(\'"
												+ id + "\')\"/>";
										html += "<input type='button' class='edit' title='修改' value='修改' onclick=\"modify(\'"
												+ id + "\')\"/>";
										html += "<input type='button' class='del' title='删除' value='删除' onclick=\"del(\'"
												+ id + "\')\"/>";
										return "<div style='text-align:center'>"+html+"</div>";
									}
								} ],
						rowNum : 10,
						rowList : [ 10, 20, 50 ],
						pager : "#gridPageUpdate",
						viewrecords : true,
						jsonReader : {
							repeatitems : false
						},
						autowidth : true,
						width : "100%",
						height : "100%",
						sortname:"m.id",
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
	$("#version").val("");
	var searchtxt = $("#version");
	searchtxt.focus(function(){
		$(".defalut_val").hide();
	}).blur(function(){
		if(searchtxt.val() == ""){
			$(".defalut_val").show();
		}
	});
});

// 查看
function detail(id) {
	var url =basePath+"/update/get?id="+id;
    tipsWindown("查看版本信息","url:post?"+url,"660","400","true","","true","");
}
// 修改
function modify(id) {
	var url =basePath+"/update/edite?id="+id;
    tipsWindown("修改版本信息","url:post?"+url,"660","400","true","","true","");
}
// 删除
function del(id) {
	if(confirm('确定删除么?')){
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/update/del",
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
				$("#gridTableUpdate").jqGrid().trigger("reloadGrid");
			}
		});
	}
}

function addUpdate(){
	var url = basePath+"/update/edite";
    tipsWindown("创建版本信息","url:post?"+url,"660","400","true","","true","");	
}


