<%@ page import="com.upbest.mvc.entity.Buser" %>
<%@ page import="java.util.Date" %>
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
    var questionNumMapping={
        A01	:	28	,
        A02	:	29	,
        A03	:	30	,
        A04	:	31	,
        B01	:	32	,
        B02	:	33	,
        B03	:	34	,
        B04	:	35	,
        B05	:	36	,
        B06	:	37	,
        B07	:	38	,
        B08	:	39	,
        B09	:	40	,
        B10	:	41	,
        B11	:	42	,
        B12	:	43	,
        B13	:	44	,
        B14	:	45	,
        B15	:	46	,
        B16	:	47	,
        B17	:	48	,
        B18	:	49	,
        B19	:	50	,
        B20	:	51	,
        B21	:	52	,
        B22	:	53	,
        B23	:	54	,
        B24	:	55	,
        B25	:	56	,
        B26	:	57	,
        B27	:	58	,
        B28	:	59	,
        B29	:	60
    }
    $(document).ready(function(){
          function formatterDate (date) {
            var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
            var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
            + (date.getMonth() + 1);
            return date.getFullYear() + '-' + month + '-' + day;
        };
        $("#saveBtn").click(function(){
            if($("#restMananger").val()==''){
                alert("请填写餐厅经理");
                return;
            }
            if($("#currentMananger").val()==''){
                alert("请填写值班经理");
                return;
            }
            if($("#restName").combobox('getValue')==''){
                alert("请选择餐厅");
                return;
            }
            var data={};
            data.actionPlanList=[];
            data.beginTime= '<%=new Date().getTime()%>';
            data.endTime=new Date().getTime();
            data.examHeading=[];
            var jiheDateVals= $("#jiheDate").datebox("getValue").split("/");
            var jiheDate=new Date();
            jiheDate.setMonth(parseInt(jiheDateVals[0])-1) ;
            jiheDate.setDate(jiheDateVals[1]) ;
            jiheDate.setFullYear(jiheDateVals[2]) ;
            data.examHeading.push({hId:4,value:jiheDate.getTime()});
            data.examHeading.push({hId:5,value:$("#restMananger").val()});
            data.examHeading.push({hId:6,value:$("#jiheMan").val()});
            data.examHeading.push({hId:7,value:$("#restName").combobox('getValue')});
            data.examHeading.push({hId:8,value:$("#lastGrade").val()});
            data.examHeading.push({hId:9,value:$("#currentGrade").val()});
            data.examHeading.push({hId:10,value:$("#currentMananger").val()});
            data.examHeading.push({hId:11,value:$("#lostGradeNum").val()});
            data.examId=25;
            data.problemAnalysisList=[];

            data.questionList=[];
            var totalGrade=0;
            $.each($(":radio:checked"),function(i,v){
                var radioName=$(v).attr("name");
                var grade=$("#"+radioName.split("_")[0]+"_radio_grade").text();
                if(grade==''){
                    grade=0;
                }
                totalGrade+=parseInt(grade);
                data.questionList.push({attr:[{hId: 15,value: grade>0?1:0},{hId: 16,value: grade>0?0:1},{hId: 27,value: "0"},{hId: 13,value: ""}],qEvidence: "",questionId: questionNumMapping[radioName.split("_")[0]],tValue: grade});
            });
            $.each($(':checkbox:checked.shifenxiang'),function(i,v){
                var scoreNum = $(v).val().split(":")[0];
                var resource = $(v).parents("table").find("input[type='text'].genyuan").val();
                var resolve = $(v).parents("table").find("input[type='text'].fangan").val();
                data.problemAnalysisList.push({resolve: resolve, resource: resource, scoreNum: scoreNum});
            })
            data.level=0;
            data.storeId=$("#restName").combobox('getValue');
            data.total=totalGrade;
            data.userId='<%=userId%>';
            $.post("${pageContext.request.contextPath}/api/exam/securi_saveExamResult",{sign: $.toJSON(data)},function(data){
                alert("保存成功")
            })
            $("#saveBtn").remove();
        })
        $("#jiheDate").datebox("setValue",formatterDate(new Date()));
        $("#tt").tabs("select",4)
        $("#tt").tabs("select",3)
        $("#tt").tabs("select",2)
        $("#tt").tabs("select",1)
        $("#tt").tabs("select",0)
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

