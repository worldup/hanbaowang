<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>图片列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${basePath}/css/base.css" rel="stylesheet" type="text/css" />

<script language="javascript">
	function getparam() {
		var filePath = "";
		var filenames = "";
		var query = location.search.substring(1);
		var values = query.split("&");
		for (var i = 0; i < values.length; i++) {
			var pos = values[i].indexOf('=');
			if (pos == -1)
				continue;
			var paramname = values[i].substring(0, pos);
			if (paramname == "filePath") {
				filePath = values[i].substring(pos + 1);
			}
			if (paramname == "filenames") {
				filenames = values[i].substring(pos + 1);
			}
		}
		
		var dv = document.getElementById("pic");

		var files = filenames.split(",");
		for (var i = 0; i < files.length; i++) {
			var img = document.createElement("img");
			img.src = filePath+files[i];
			img.id = img.name = "img" + i;
			dv.appendChild(img);
		}
		
	}
</script>
</head>
<body onload="getparam();">
	<div id="pic"></div>
</body>
</html>