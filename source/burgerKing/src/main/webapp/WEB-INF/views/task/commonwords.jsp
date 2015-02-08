<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>汉堡王首页</title>
</head>
<body>
<div class="dzwidth_960 store">
    <table class="qjabtn_wrap" id="searchDiv">
        <tr>
            <td>
                <div class="qjact_btn clearfix">
                    <a class="green" name="addCommonWords" id="addCommonWords" onclick="addCommonWords();" href="javascript:void(0)"><span>新增</span></a>

                </div>
            </td>
        </tr>
    </table>

    <table class="qjabtn_wrap" >
        <tr>
            <td>
                <label style="font-size:14px;">类型：</label>
            </td>
            <td>
                <div class=" clearfix">
                    <select id="type" name="type" class="select">
                    </select>
                </div>
            </td>

            <td>
                <div class=" clearfix">

                    <input type="button" value="搜&nbsp;索" id="searchBtn" class="search" />

                </div>
            </td>
        </tr>
    </table>
    <table id="gridTableStore"></table>
    <div id="gridPagerStore"></div>
    <div id="win"  >
    </div>
</div>
<link rel="stylesheet" type="text/css" href="${basePath}/js/multiselect/assets/style.css" />
<link rel="stylesheet" type="text/css" href="${basePath}/js/multiselect/assets/prettify.css" />
<script type="text/javascript" src="${basePath}/js/multiselect/assets/prettify.js"></script>

<script type="text/javascript" src="${basePath}/js/task/commonwords.js"></script>

<script type="text/javascript">

    $(document).ready(function(){
        $.ajax({
            async : false,
            type : 'post',
            url : basePath + "/task/loadTask",
            dataType : "json",
            cache : false,
            error : function(err) {
                alert("系统出错了，请联系管理员！");
            },
            success : function(data) {
                $('#type').empty();
                var html='<option value="-1">---请选择---</option>';
                $.each(data,function(i, value) {
                    html+="<option valueid="+value.related+" value="+value.value+">"+value.name+"</option>";
                });
                $('#type').append(html);

            }
        });
        $("#searchBtn").bind("click",function(){
            $("#gridTableStore").setGridParam({page:1}).jqGrid().trigger("reloadGrid");
        });
    });

</script>
</body>
</html>
