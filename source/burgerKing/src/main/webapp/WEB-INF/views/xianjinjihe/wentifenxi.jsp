<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<ul id="wentilist">

</ul>

<script type="text/javascript">

    $(document).ready(function(){
        var fillWentiList=function(serialNo,title,content){
            var reslt=[];
            result.push("<li><table style='width:50px;height:60px'>");
            result.push("<tr>");
            result.push("<td>"+serialNo+"</td>");
            result.push("<td>"+title+"</td>");
            result.push("</tr>");
            result.push("<tr>");
            result.push("<td>描述问题:</td>");
            result.push("<td>"+content+"</td>");
            result.push("</tr>");
            result.push("</table></li>")
            return result.join("");
        }
        $.each($(":radio:checked"),function(i,v){
            if($(v).val()=='N'){
               $("#wentilist").html(fillWentiList(1,2,$(v).attr("name")) );
            }
        })
    })
</script>