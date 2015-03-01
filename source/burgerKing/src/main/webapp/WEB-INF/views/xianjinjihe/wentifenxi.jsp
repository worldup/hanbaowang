<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<ul>
    <li>
        <a href='#' id="tianjiaxingdong" data-options="iconCls:'icon-add'" class='easyui-linkbutton' ></a>
    </li>
</ul>
<ul id="wentilist">

</ul>
<br>
<ul id="xingdonglist">

</ul>

<script type="text/javascript">
    var delXingdong=  function (a){
        $(a).parents("li:first").remove();
    }
    $(document).ready(function(){
        $("#tianjiaxingdong").click(function(){
            $("#xingdonglist").append(fillXingdong())
        })

        var fillXingdong=function(){
            var checkboxText="";
            $.each($(":radio:checked"),function(i,v){
                if($(v).val()=='N'){
                    var serialNo= $(v).parents('ul:first').find("li .serialNo").html()
                    checkboxText+= "<input type='checkbox' class='shifenxiang' value='"+serialNo+"'/><label>"+serialNo+"</label>";
                }
            })
            var text=[];
            text.push("<li><table border='1' width='500px'> ");
            text.push("<tr>");
            text.push("<td  style='width:100px'>根源: <input type='button' value='删除' onclick='delXingdong(this)'/> </td>");
            text.push("<td > <input type='text' style='display:inline-block;width: 395px' class='genyuan' id=''> </td>");
            text.push("</tr>");
            text.push("<tr>");
            text.push("<td  style='width:100px'>行动方案:</td>");
            text.push("<td > <input type='text'style='display:inline-block;width: 395px' class='fangan' id=''> </td>");
            text.push("</tr>");
            text.push("<tr>");
            text.push("<td  style='width:100px'>对应失分项编号:</td>");
            text.push("<td > "+checkboxText+" </td>");
            text.push("</tr>");
            text.push("</table></li>");
            text.push("<br>")
            return text.join("");;
        }
        var fillWentiList=function(serialNo,title,content){
            var result=[];
            result.push("<li><table border='1' width='500px'>");
            result.push("<tr style='color:#00B83F;'>");
            result.push("<td  style='width:100px'>"+serialNo+"</td>");
            result.push("<td>"+title+"</td>");
            result.push("</tr>");
            result.push("<tr>");
            result.push("<td style='width:100px'>描述问题:</td>");
            result.push("<td>"+content+"</td>");
            result.push("</tr>");
            result.push("</table></li>")
            return result.join("");
        }
        $("#wentilist").html();
        $.each($(":radio:checked"),function(i,v){
            if($(v).val()=='N'){
               var serialNo= $(v).parents('ul:first').find("li .serialNo").html()
               var title= $(v).parents('ul:first').find("li .title").html()
                var radioName=$(v).attr("name");
                var textName=radioName.split("_")[0]+"_text";
                var text= $("input[name='"+textName+"']").val();
               $("#wentilist").append(fillWentiList(serialNo,title,text) );
            }
        })
    })
</script>