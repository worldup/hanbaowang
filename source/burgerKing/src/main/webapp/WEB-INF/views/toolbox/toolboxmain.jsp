<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${basePath}/css/zTreeStyle/zTreeStyle.css"
      type="text/css">
<script type="text/javascript"
        src="${basePath}/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript"
        src="${basePath}/js/ztree/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="${basePath}/js/toolbox/toolbox.js"></script>

<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${basePath}/js/imge/imgeList.js"></script>
<script type="text/javascript" src="${basePath}/js/jquery.form.js"></script>
<script type="text/javascript">


</script>
<style>
    #storeForm .text {
        width: 200px;
    }

    #storeForm .title {
        width: 120px;
    }

</style>


<div class="dzwidth_960 user">
    <table class="qjabtn_wrap" id="searchDiv">
        <tr>
            <td>
                <div class="qjact_btn clearfix">
                    <a href="javascript:void(0)" class="small_ea" id="importfile" onclick="importfileClick()"><span>工具箱上传</span></a>
                </div>
            </td>
        </tr>
    </table>

    <div class="qjstxt_wrap clearfix">
        <input type="text" class="text" name="imgeName" id="imgeName"/>
        <label class="defalut_val" for="imgeName">根据文件称查询</label>
        <input type="button" value="搜&nbsp;索" id="repSearchBtn" onclick='repSearchClick()' class="search"/>
    </div>

    <table id="gridTableImge"></table>
    <div id="gridPagerImge"></div>
    <input type="hidden" id="shopId" value="${id}">
</div>
