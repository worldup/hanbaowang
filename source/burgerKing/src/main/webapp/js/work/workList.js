$(function() {
	$("#gridTableWork")
			.jqGrid(
					{
						url : basePath + "/work/list",
						mtype : "POST",
						postData : {
							"typeName" : function() {
								return $.trim($('#typeName').val());
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
									hidden : true
								},
								{
									label : "类型名称",
									name : "typename",
									sortable : false,
									width : 300
								},
								{
									label : "类型角色",
									name : "typerole",
									sortable : false,
									width : 80
								},
								{
									label : "频率",
									name : "frequency",
									title : false,
									sortable : false,
									
									formatter:'select',
									editoptions:{value:"1:每周;2:月度;3:季度;4:需要时"}
								},
								{
									label : "是否问卷类型",
									name : "isExamType",
									title : false,
									sortable : false,
									formatter:'select',
									editoptions:{value:"0:否;1:是"}
								},
								{
									label : "是否针对门店",
									name : "isToStore",
									title : false,
									sortable : false,
									formatter:'select',
									editoptions:{value:"0:否;1:是"}
								},
								{
									label : "位置调整",
									name : "act",
									sortable : false,
									width : 100,
									formatter : function(cellvalue, options,
											rowObject) {
										var id = rowObject.id;
										var html = "<a  class='px01'  onclick=\"moveDown(this,\'"+ id + "\')\"/>";
										html += "<a class='px02'  onclick=\"moveUp(this,\'"+ id + "\')\"/>";
										return html;
									}
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
						pager : "#gridPagerWork",
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
	$("#typeName").val("");
	var searchtxt = $("#typeName");
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
	var url =basePath+"/work/get?id="+id;
    tipsWindown("查看类型","url:post?"+url,"660","400","true","","true","");	

}
// 修改
function modify(id) {
	var url =basePath+"/work/create?id="+id;
    tipsWindown("修改类型","url:post?"+url,"660","400","true","","true","");	
}
//删除
function del(id) {
	if(confirm('确定删除么?')){
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/work/del",
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
				$("#gridTableWork").jqGrid().trigger("reloadGrid");
			}
		});
	}
}
function addWork(){
	var url =basePath+"/work/create";
    tipsWindown("添加类型","url:post?"+url,"660","400","true","","true","");	
}
//位置下移
function moveUp(obj,id){
	//if(confirm('是否确定上移?')){
	    var curTr = $("#"+id);
	    var preTr= curTr.prev();
	    var preId = preTr.attr("id");
	    var currentId = id;
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/work/changeLocation",
			data : {
				"preId" : preId,
				"currentId":currentId
			},
			dataType : "json",
			cache : false,
			error : function(err) {
				alert("系统出错了，请联系管理员！");
			},
			success : function(data) {
				/*if(data=='0'){
					alert('操作成功!');
				}*/
				$("#gridTableWork").jqGrid().trigger("reloadGrid");
			}
		});
	//}
	
	
}
//位置上移
function moveDown(obj,id){
	//if(confirm('是否确定下移?')){
		    var curTr = $("#"+id);
		    var nextTr= curTr.next();
		    var nextId = nextTr.attr("id");
		    var currentId = id;
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/work/changeLocation",
			data : {
				"preId" : nextId,
				"currentId":currentId
			},
			dataType : "json",
			cache : false,
			error : function(err) {
				alert("系统出错了，请联系管理员！");
			},
			success : function(data) {
				/*if(data=='0'){
					alert('操作成功!');
				}*/
				$("#gridTableWork").jqGrid().trigger("reloadGrid");
			}
		});
	//}
	
}
