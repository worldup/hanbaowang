$(document).ready(function() {

	$('#importfile').bind('click', function (e) {
		var url = basePath + "/toolKit/index";
		tipsWindown("上传文件", "url:post?" + url, "1024", "666", "true", "", "true", "");
	});
	$("#repSearchBtn").bind("click", function () {
		$("#gridTableImge").setGridParam({page: 1}).jqGrid().trigger("reloadGrid");
	});

})



