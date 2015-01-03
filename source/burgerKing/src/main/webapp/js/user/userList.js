$(function() {
	$("#gridTableUser")
			.jqGrid(
					{
						url : basePath + "/user/list",
						mtype : "POST",
						postData : {
							"realName" : function() {
								return $.trim($('#reName').val());
							}
						},
						forceFit:true,
						altRows : true,
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
									width : 200,
									sortable : false
								},
								{
									label : "用户真实姓名",
									name : "realname",
									width : 200,
									title : false,
									sortable : false
								},
								{
									label : "所属角色",
									name : "role",
									sortable : true,
									width :100,
									formatter:'select',
									editoptions:{value:"0:超级管理员;1:OM;2:OC;3:OM+"}
								},
								{
									label : "上级",
									name : "pid",
									width : 100,
									title : false,
									sortable : false,
									formatter : function(cellvalue, options,
											rowObject) {
										return cellvalue==null?"-":cellvalue;
									}
								},
								{
									label : "职工号",
									name : "emp",
									width : 100,
									title : false,
									sortable : false,
									hidden:true,
									width : 100,
								},
								{
									label : "操作",
									sortable : false,
									width : 100,
									formatter : function(cellvalue, options,
											rowObject) {
										var id = rowObject.id;
										var html = "<input type='button' class='details' title='查看' value='查看' onclick=\"detail(\'"
												+ id + "\')\"/>";
										html += "<input type='button' class='edit' title='修改' value='修改' onclick=\"modify(\'"
												+ id + "\')\"/>";
										html += "<input type='button' class='del' title='删除' value='删除' onclick=\"del(\'"
												+ id + "\')\"/>";
									
										return html;
									}
								} ],
						rowNum : 10,
						rowList : [ 10, 20, 50 ],
						pager : "#gridPagerUser",
						viewrecords : true,
						sortname:"t.id",
						sortorder:"desc",
						sortable:true,
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
	
	$("#reName").val("");
	//文本框搜索
	var searchtxt = $("#reName");
	searchtxt.focus(function(){
		$(".defalut_val").hide();
	}).blur(function(){
		if(searchtxt.val() == ""){
			$(".defalut_val").show();
		}
	});
});
function detail(id) {
	var url =basePath+"/user/get?id="+id;
    tipsWindown("查看用户","url:post?"+url,"660","400","true","","true","");	
}
// 修改

	function modify(id) {
		var url =basePath+"/user/create?id="+id;
	    tipsWindown("修改用户","url:post?"+url,"660","400","true","","true","");	

}
// 删除
function del(id) {
	if(confirm('确定删除么?')){
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/user/del",
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
				$("#gridTableUser").jqGrid().trigger("reloadGrid");
			}
		});
	}
}

function addUser(){
	var url =basePath+"/user/create";
    tipsWindown("添加用户","url:post?"+url,"660","400","true","","true","");	
}

