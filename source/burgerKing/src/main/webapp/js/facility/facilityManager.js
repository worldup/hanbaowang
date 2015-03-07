$(function() {
	$("#gridTableFacility")
	.jqGrid(
			{
				url : basePath + "/facility/list",
				mtype : "POST",
				postData : {
					"facilityName" : function() {
						return $.trim($('#facilityName').val());
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
								label : "品牌",
								name : "devicebrand",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "标识号",
								name : "deviceid",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "型号",
								name : "devicemodel",
								title : false,
								sortable : false,
								width : 200
							},
							{
								label : "版本",
								name : "deviceversion",
								title : false,
								sortable : false,
								width : 200
							},
							{
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
							},
                             {
                            label : "登陆限制",
                            name : "needUserMatch",
                            title : false,
                            sortable : false,
                             formatter : function(cellvalue, options,
                                                  rowObject) {
                                 return cellvalue==1?'是':'否';
                             },
                            width : 200
                             },
							{
								label : "操作",
								sortable : false,
								width :200,
								formatter : function(cellvalue, options,
										rowObject) {
									var id = rowObject.id;
									var 
									html = "<input type='button' class='edit' title='修改' value='修改' onclick=\"modify(\'"
											+ id + "\')\"/>";
									html += "<input type='button' class='del' title='删除' value='删除' onclick=\"del(\'"
											+ id + "\')\"/>";
									return html;
								}
							} ],
				rowNum : 10,
				rowList : [ 10, 20, 50 ],
				pager : "#gridPagerFacility",
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
	$("#facilityName").val("");
	var searchtxt = $("#facilityName");
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
	$.ajax({
		async : false,
		type : 'post',
		url : basePath + "/facility/detail",
		data : {
			"id" : id
		},
		dataType : "json",
		cache : false,
		error : function(err) {
			alert("系统出错了，请联系管理员！");
		},
		success : function(data) {
			alert(data.toSource());
			showWindow("detailUser", "添加用户");
		}
	});

}
// 修改
function modify(id) {
	var url = basePath + "/facility/addOrUpdatePage?id=" + id;
	tipsWindown("修改设备","url:post?"+url,"620","400","true","","true","");
}
// 删除
function del(id) {
	if(confirm('确定删除么?')){
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/facility/delete",
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
				$("#gridTableFacility").jqGrid().trigger("reloadGrid");
			}
		});
	}
}

$("#addFacility").bind("click",function(event){
	var url = basePath + "/facility/addOrUpdatePage";
	tipsWindown("添加设备","url:post?"+url,"660","400","true","","true","");
	
});

