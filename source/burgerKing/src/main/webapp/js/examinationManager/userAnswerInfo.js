$(function() {
	$("#gridTable")
	.jqGrid(
			{
				url : basePath + "/examManager/userAnswerInfoList",
				mtype : "POST",
				postData : {
					examId : function(){
						return $('#examId').val();
					},
					"shopName" : function()
					{
						return $.trim($('#shopName').val());
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
								width : 200
							},
							{
								name : "userid",
								hidden : true,
								width : 200
							},
							{
								name : "storeid",
								hidden : true,
								width : 200
							}/*,
							{
								label:"问卷名称",
								name : "examName",
								sortable : false,
								width : 150
							}*/,
							{
								label : "用户名称",
								name : "username",
								sortable : false,
								width : 200
							},
							{
								label : "门店名称",
								name : "shopname",
								sortable : false,
								width : 250
							},
							{
								label : "开始时间",
								name : "tbegin",
								sortable : false,
								width : 150
							},
							{
								label : "结束时间",
								name : "tend",
								sortable : false,
								width : 150
							},
							{
								label : "成绩",
								name : "ttotal",
								sortable : false,
								width : 100
							},
							{
								label : "是否通过",
								name : "",
								title : false,
								sortable : false,
								width : 100,
								formatter : function(cellvalue, options,
										rowObject) {
									var passScore = $('#passScore').val();
									return rowObject.ttotal >= passScore ? '通过' : '未通过';
								}
							},
							{
								label : "操作",
								name : "act",
								title : false,
								sortable : false,
								width : 100,
								formatter : function(cellvalue, options,
										rowObject) {
									var id = rowObject.id;
										//userId = rowObject.userid,
										//examId = $('#examId').val();
										//stateDesc = rowObject.stateDesc;
									/*var 
									html = stateDesc == '未完成' ? '' : "<input type='button' class='edit' title='详情' value='详情' onclick='viewDetail("+userId+","+examId+")'/>";*/
									var	html = "<input type='button' class='del' title='删除' value='删除' onclick=\"del(\'"
											+ id + "\')\"/>";
									return html;
								}
							}],
				rowNum : 10,
				rowList : [ 10, 20, 50 ],
				pager : "#gridPager",
				viewrecords : true,
				jsonReader : {
					repeatitems : false
				},
				autowidth : true,
				width : "100%",
				height : "100%",
				gridComplete : function() {
					var neww = $(".dz_crumbs").width();
					$("#gridTable").jqGrid().setGridWidth(neww);
					var neww = $(".ui-jqgrid").width();
					var tablew = $(".ui-jqgrid-btable").width();
					if(tablew<neww){
						$(".ui-jqgrid-htable").width(neww);
						$(".ui-jqgrid-btable").width(neww);
					}
				}
			});
	//文本框搜索
	$("#shopName").val("");
	var searchtxt = $("#shopName");
	searchtxt.focus(function(){
		$(".defalut_val").hide();
	}).blur(function(){
		if(searchtxt.val() == ""){
			$(".defalut_val").show();
		}
	});
	
});
function viewDetail(userId,examId){
	var url = basePath + "/examManager/testPaperDetail?userId=" + userId + "&examId=" + examId;
	window.location.href = url;
}
//删除
function del(id) {
	if(confirm('是否确认删除?')){
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/examManager/del",
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
				$("#gridTable").jqGrid().trigger("reloadGrid");
			}
		});
	}
}
