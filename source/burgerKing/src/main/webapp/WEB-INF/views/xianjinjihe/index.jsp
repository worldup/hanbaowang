<%@ page import="com.upbest.mvc.entity.Buser" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<%
    String name = "";
    Integer userId=0;
    if (session.getAttribute("buser") != null) {
        Buser u = (Buser) session.getAttribute("buser");
        name = u.getRealname();
        userId=u.getId();
    }
%>
<div id="cc" class="easyui-layout " style="width:100%;height:500px;">
    <div data-options="region:'north',title:'现金稽核',collapsible:false" style="height:20%;">
        <form id="titleForm" method="post">
            <table   style="border:1px dotted #67b3ff">
                <tr>
                    <td>
                        <label style="display:inline-block;width: 100px"   for="restName">餐厅:</label>
                        <input   style="display:inline-block;width: 180px" class="easyui-combobox" type="text" name="restName" id="restName"
                                 data-options="valueField:'id',textField:'shop_name',url:'${pageContext.request.contextPath}/store/listShop4Combobox'"
                                  />
                    </td>

                    <td>
                        <label style="display:inline-block;width: 100px" for="jiheDate">日期:</label>
                        <input  style="display:inline-block;width: 180px" class="easyui-datebox" type="text" name="jiheDate" id="jiheDate" data-options="" />
                    </td>
                    <td>
                        <label style="display:inline-block;width: 100px" for="restMananger">餐厅经理:</label>
                        <input   style="display:inline-block;width: 180px" type="text" name="restMananger" id="restMananger"  class="easyui-validatebox" data-options="validType:'string'">
                    </td>
                    <td>
                        <label  style="display:inline-block;width: 100px" for="jiheMan">稽核人:</label>
                        <input   style="display:inline-block;width: 180px" type="text" name="jiheMan" id="jiheMan"  class="easyui-validatebox" value="<%=name%>" data-options="validType:'string'">
                    </td>
                    <td>
                        <input type="hidden" name="jiheManId" value="<%=userId%>"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label style="display:inline-block;width: 100px" for="lastGrade">上次稽核成绩:</label>
                        <input   style="display:inline-block;width: 180px" class="easyui-validatebox" type="text" name="lastGrade" id="lastGrade" />
                    </td>

                    <td>
                        <label style="display:inline-block;width: 100px" for="currentGrade">本次稽核成绩:</label>
                        <input   style="display:inline-block;width: 180px" class="easyui-validatebox" type="text" name="currentGrade" id="currentGrade" data-options="" />
                    </td>
                    <td>
                        <label style="display:inline-block;width: 100px" for="currentMananger">值班经理:</label>
                        <input   style="display:inline-block;width: 180px" type="text" name="currentMananger" id="currentMananger"  class="easyui-validatebox" data-options="validType:'string'">
                    </td>
                    <td>
                        <label style="display:inline-block;width: 100px" for="lostGradeNum">重复扣分项编号:</label>
                        <input   style="display:inline-block;width: 180px" type="text" name="lostGradeNum" id="lostGradeNum"  class="easyui-validatebox" data-options="validType:'string'">
                    </td>
                    <td>
                            <a href='#' id="saveBtn" data-options="iconCls:'icon-save'" class='easyui-linkbutton' >保存</a>
                    </td>
                </tr>

            </table>

        </form>
    </div>
    <div data-options="region:'south'" style="height:80%;">
        <div id="tt" class="easyui-tabs" style="height:380px;">
            <div title="关键项评估"  href="${pageContext.request.contextPath}/xianjinjihe/guanjianxiangpinggu" data-options="selected:true"  style="padding:20px;">
             </div>
            <div title="保险箱管理"  href="${pageContext.request.contextPath}/xianjinjihe/baoxianxiangguanli" data-options="selected:false" style="padding:20px;">
            </div>
            <div title="转桌管理"  href="${pageContext.request.contextPath}/xianjinjihe/zhuanzhuoguanli"  data-options="selected:false" style="padding:20px;">
            </div>
            <div title="存货管理"  href="${pageContext.request.contextPath}/xianjinjihe/cunhuoguanli"  data-options="selected:false" style="padding:20px;">
            </div>
            <div title="问题分析及行动" href="${pageContext.request.contextPath}/xianjinjihe/wentifenxi"  data-options="selected:false,cache:false" style="padding:20px;;">
            </div>
        </div>
    </div>

</div>
<script type="text/javascript">
    $(document).ready(function(){
          function formatterDate (date) {
            var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
            var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
            + (date.getMonth() + 1);
            return date.getFullYear() + '-' + month + '-' + day;
        };
        $("#saveBtn").click(function(){
            $.each($(":radio:checked"),function(i,v){
               if($(v).val()=='N'){
                 console.log( $(v).attr("name"));
               }
            })
        })
        $("#jiheDate").datebox("setValue",formatterDate(new Date()));
    })
    $('#restName').combobox({
        onSelect: function (data) {
            $.getJSON("${pageContext.request.contextPath}/exam/findRepeatLostScoreItem?shopId=" + data.id,function(result){
                $("#lostGradeNum").val(result.obj)
            })
            $.getJSON("${pageContext.request.contextPath}/exam/findLastExamScore?examType=140&shopId=" + data.id,function(result){
                $("#lastGrade").val(result.obj)
            })
            }
        });

</script>

