$(function() {
	$("#gridTableBeacon")
	.jqGrid(
			{
				url : basePath + "/beacon/list",
				mtype : "POST",
				postData : {
					"beaconName" : function() {
						return $.trim($('#beaconName').val());
					}
				},
				forceFit:true,
				altRows : true,
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
								label : "设备号",
								name : "uuid",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "majorId",
								name : "majorId",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "minorId",
								name : "minorId",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "门店",
								name : "shopName",
								title : false,
								sortable : false,
								width : 200
							},
							/*{
								label : "操作系统",
								name : "deviceos",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "操作系统版本",
								name : "deviceosversion",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "分辨率",
								name : "deviceresolution",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "用户",
								name : "userName",
								title : false,
								sortable : false,
								width : 200
							},*/
							{
								label : "操作",
								sortable : false,
								width :200,
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
				pager : "#gridPagerBeacon",
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
	$("#beaconName").val("");
	var searchtxt = $("#beaconName");
	searchtxt.focus(function(){
		$(".defalut_val").hide();
	}).blur(function(){
		if(searchtxt.val() == ""){
			$(".defalut_val").show();
		}
	});
});
//查看
function detail(id) {
	var url =basePath+"/beacon/get?id="+id;
    tipsWindown("查看beacon","url:post?"+url,"760","400","true","","true","");	
}
// 修改
function modify(id) {
	var url = basePath + "/beacon/addOrUpdatePage?id=" + id;
	tipsWindown("修改beacon","url:post?"+url,"760","400","true","","true","");
}
// 删除
function del(id) {
	if(confirm('确定删除么?')){
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/beacon/delete",
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
				$("#gridTableBeacon").jqGrid().trigger("reloadGrid");
			}
		});
	}
}

$("#addBeacon").bind("click",function(event){
	var url = basePath + "/beacon/addOrUpdatePage";
	tipsWindown("添加beacon","url:post?"+url,"760","400","true","","true","");
	
});

