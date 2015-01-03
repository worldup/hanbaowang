$(function() {
	$("#gridTableMessage")
			.jqGrid(
					{
						url : basePath + "/message/list",
						mtype : "POST",
						postData : {
							"messageName" : function() {
								return $.trim($('#messageName').val());
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
									label : "消息类型",
									name : "messageType",
									title : true,
									sortable : false,
									width : 100,
									formatter:'select',
									editoptions:{value:"1:公告;2:紧急;3:任务;4:异常"}
								},
								{
									label : "消息标题",
									name : "pushTitle",
									title : true,
									sortable : false,
									width : 200
								},
								/*{
									label : "消息内容",
									name : "pushContent",
									title : true,
									sortable : false,
									width : 90
								},*/
								{
									label : "消息状态",
									name : "state",
									title : true,
									sortable : false,
									width : 100,
									formatter:'select',
									editoptions:{value:"0:未推送;1:已推送;null:-"}
								},
								
								{
									label : "创建者",
									name : "creater",
									title : true,
									sortable : false,
									width : 100
								},
								/*{
									label : "发送者",
									name : "sender",
									title : true,
									sortable : false,
									width : 90
								},*/
								{
									label : "接收者",
									name : "receiver",
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
									width : 80,
									formatter : function(cellvalue, options,
											rowObject) {
										var id = rowObject.id;
										var messageType = rowObject.messageType;
										var html = "<input type='button' class='details' title='查看' value='查看' onclick=\"detail(\'"
												+ id + "\')\"/>";
										if(messageType != 3){
											html += "<input type='button' class='edit' title='修改' value='修改' onclick=\"modify(\'"
												+ id + "\')\"/>";
										}
										html += "<input type='button' class='del' title='删除' value='删除' onclick=\"del(\'"
												+ id + "\')\"/>";
										if(messageType == 2){
											html += "<input type='button' class='ts' title='推送' value='推送' onclick=\"push(\'"
												+ id + "\')\"/>";
										}
										return "<div style='text-align:right'>"+html+"</div>";
									}
								} ],
						rowNum : 10,
						rowList : [ 10, 20, 50 ],
						pager : "#gridPageMessage",
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
	$("#messageName").val("");
	var searchtxt = $("#messageName");
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
	var url =basePath+"/message/get?id="+id;
    tipsWindown("查看消息","url:post?"+url,"660","400","true","","true","");
}
// 修改
function modify(id) {
	var url =basePath+"/message/edite?id="+id;
    tipsWindown("修改消息","url:post?"+url,"660","400","true","","true","");
}
// 删除
function del(id) {
	if(confirm('确定删除么?')){
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/message/del",
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
				$("#gridTableMessage").jqGrid().trigger("reloadGrid");
			}
		});
	}
}

function push(id){
	var url =basePath+"/message/push?id="+id;
    tipsWindown("推送消息","url:post?"+url,"660","400","true","","true","");
}

function addMessage(){
	var url =basePath+"/message/create";
    tipsWindown("创建消息","url:post?"+url,"660","400","true","","true","");	
}


