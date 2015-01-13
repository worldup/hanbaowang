$(function() {
	$("#gridTableStore")
			.jqGrid(
					{
						url : basePath + "/store/list",
						mtype : "POST",
						postData : {
							"shopName" : function() {
								return $.trim($('#storeName').val());
							},
							"realName" : function(){
								return $.trim($('#realName').val());
							},
							"OMIDS":function(){
								return $.trim($("#OMID").val())
							},
							"areaId":function(){
								return $.trim($("#searchRegion").val())
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
									label : "门店名称",
									name : "shopname",
									sortable : false,
									width :150
								},
								{
									label : "门店编号",
									name : "shopnum",
									sortable : false,
									width :60
								}/*,{
									label : "门面",
									name : "shopimage",
									sortable : false,
									width : 80,
									formatter : function(cellvalue, options,
											rowObject) {
										var val='';
										if(cellvalue!=null){
										  val="../storeImage/"+cellvalue;
										}else{
											val="../images/default.jpg";
										}
										var html = "<img width=50 height=50 src=\""+val+"\" onerror=\"this.src='../images/default.jpg'\"/>";
										return html;
									}
								}*/,
								
								{
									label : "营业时间",
									name : "shopbusinesstime",
									sortable : true,
									width : 80
								},
								{
									label : "开店时间",
									name : "shopopentime",
									sortable : true,
									width : 80,
									formatter : function(cellvalue, options,
											rowObject) {
										return cellvalue==null?"-":cellvalue;
									}
								},
								{
									label : "OC",
									name : "userNames",
									sortable : false,
									width : 100,
									formatter : function(cellvalue, options,
											rowObject) {
										return cellvalue== ''?"-":cellvalue;
									}
								},
								{
									label : "操作",
									sortable : false,
									width : 150,
									formatter : function(cellvalue, options,
											rowObject) {
										var id = rowObject.id;
										var html = "<input type='button' class='details' title='查看' value='查看' onclick=\"detail(\'"
												+ id + "\')\"/>";
										html += "<input type='button' class='edit' title='修改' value='修改' onclick=\"modify(\'"
												+ id + "\')\"/>";
										/*html += "<input type='button' class='del' title='删除' value='删除' onclick=\"del(\'"
												+ id + "\')\"/>";*/
										html += "<input type='button' class='upload' title='上传报表' value='报表查看' onclick=\"viewReport(\'"
											+ id + "\')\"/>";
										/*html += "<input type='button' class='bind' title='绑定' value='绑定' onclick=\"bind(\'"
											+ id + "\')\"/>";*/
										return html;
									}
								} ],
						rowNum : 10,
						rowList : [ 10, 20, 50 ],
						pager : "#gridPagerStore",
						viewrecords : true,
						jsonReader : {
							repeatitems : false
						},
						sortname:"t.id",
						sortorder:"desc",
						sortable:true,
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
	
	$('#storeName').val("");
	//文本框搜索
	var searchtxt = $("#storeName");
	searchtxt.focus(function(){
		$(".defalut_val").hide();
	}).blur(function(){
		if(searchtxt.val() == ""){
			$(".defalut_val").show();
		}
	});
});

//上传
function showStoreUpload(){
	doUploadOperate();
}
// 查看
function detail(id) {
	var url =basePath+"/store/get?id="+id;
    tipsWindown("查看门店","url:post?"+url,"960","600","true","","true","");	

}
// 修改
function modify(id) {
	var url =basePath+"/store/create?id="+id;
    tipsWindown("修改门店","url:post?"+url,"960","600","true","","true","");	
}
//删除
function del(id) {
	if(confirm('确定删除么?')){
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/store/del",
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
				$("#gridTableStore").jqGrid().trigger("reloadGrid");
			}
		});
	}
}
function addStore(){
	var url =basePath+"/store/create";
    tipsWindown("添加门店","url:post?"+url,"960","600","true","","true","");	
}

function viewReport(id){
	var url =basePath+"/shopRe/index?shopId="+id;
    tipsWindown("查看报表","url:post?"+url,"960","600","true","","true","");	
}

function bind(id){
	var url =basePath+"/store/bind?id="+id;
	tipsWindown("绑定beacon设备","url:post?"+url,"660","400","true","","true","");	
}
