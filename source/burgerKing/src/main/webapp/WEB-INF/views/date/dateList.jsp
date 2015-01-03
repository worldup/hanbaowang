<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.upbest.mvc.entity.Buser"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${basePath}/css/mainstructure.css"> 
<link rel="stylesheet" href="${basePath}/css/maincontent.css"> 

<script type="text/javascript" src="${basePath}/js/date/jquery-1.4.2.min.js"></script>

<script type="text/javascript" src="${basePath}/js/date/jquery-ui-1.8.6.custom.min.js"></script>

<script type="text/javascript" src="${basePath}/js/date/jquery-ui-timepicker-addon.js"></script>

<link rel="stylesheet" href="${basePath}/css/jquery-ui-1.8.1.custom.css"> 

<script src="${basePath}/js/date/jquery.validationEngine.js" type="text/javascript"></script>

<script src="${basePath}/js/date/jquery.validationEngine-en.js" type="text/javascript"></script>

<link rel="stylesheet" href="${basePath}/css/validationEngine.jquery.css" type="text/css" media="screen" charset="utf-8" />

<link rel='stylesheet' type='text/css' href='${basePath}/css/fullcalendar.css' />

<script type='text/javascript' src='${basePath}/js/date/fullcalendar.js'></script>
<%
	Buser u = null;
	if(session.getAttribute("buser")!=null){
	    u = (Buser)session.getAttribute("buser");
	}
	int userId =u.getId();
%>
<c:set var="u" value="<%=userId%>" />

    <div id="wrap">
        <div id='calendar'>
        </div>
        <div id="reserveinfo" title="Details">
            <div id="revtitle">
            </div>
            <div id="revdesc">
            </div>
        </div>
        <div style="display: none" id="reservebox" title="Reserve meeting room">
            <form id="reserveformID" method="post">
            <div class="sysdesc">
                &nbsp;</div>
            <div class="rowElem">
                <label>
                    标题:</label>
                <input id="title" name="start">
            </div>
            <div class="rowElem">
                <label>
                    重要程度:</label>
                <input id="chengdu" name="start">
            </div>
            <div class="rowElem">
                <label>
                    创建时间:</label>
                <input id="start" name="start">
            </div>
           <!--  <div class="rowElem">
                <label>
                    结束时间:</label>
                <input id="end" name="end">
            </div> -->
            <div class="rowElem">
                <label>
                    备忘内容:</label>
                <textarea id="details" rows="3" cols="43" name="details"></textarea>
            </div>
            <div class="rowElem">
            </div>
            <div class="rowElem">
            </div>
            <div id="addhelper" class="ui-widget">
                <div style="padding-bottom: 5px; padding-left: 5px; padding-right: 5px; padding-top: 5px"
                    class="ui-state-error ui-corner-all">
                    <div id="addresult">
                    </div>
                </div>
            </div>
            </form>
        </div>
       
    </div>
    
     <script type='text/javascript'>
       $(document).ready(function () {
           $("#start").timepicker({ dateFormat: 'yy-mm-dd', timeFormat: 'hh:mm', hourMin: 5, hourMax: 24, hourGrid: 3, minuteGrid: 15, timeText: '时间', hourText: '时', minuteText: '分', timeOnlyTitle: '选择时间', onClose: function (dateText, inst) {
               if ($('#start').val() != '') {
                   var testStartDate = $('#start').datetimepicker('getDate');
                   var testEndDate = $('#end').datetimepicker('getDate');
                  if (testStartDate > testEndDate)
                      $('#end').datetimepicker('setDate', testStartDate);
              } else {
                  $('#end').val(dateText);
              }
          },
             onSelect: function (selectedDateTime) {
                  $('#end').datetimepicker('option', 'minDate', $('#end').datetimepicker('getDate'));
              }
          });
          $("#end").datetimepicker({ dateFormat: 'yy-mm-dd', hourMin: 5, hourMax: 23, hourGrid: 3, minuteGrid: 15, timeText: '时间', hourText: '时', minuteText: '分', onClose: function (dateText, inst) {
              if ($('#start').val() != '') {
                  var testStartDate = $('#start').datetimepicker('getDate');
                  var testEndDate = $("#end").datetimepicker('getDate');
                 if (testStartDate > testEndDate)
                      $('#start').datetimepicker('setDate', testEndDate);
              } else {
                  $('#start').val(dateText);
              }
          },
              onSelect: function (selectedDateTime) {
                  $('#start').timepicker('option', 'maxDate', $("#end").timepicker('getDate'));
              }
          });
          $("#addhelper").hide();
 
          function signInfo(){
        	  var userId;
        	  var year;
        	  var month;
          }
          var vo = new signInfo();
          var date = new Date();
          var d = date.getDate();
          var m = date.getMonth();
          var y = date.getFullYear();
          vo.month = date.getMonth()+1;
          vo.year = date.getFullYear();
          vo.userId="${u}";
          
          var strInfo = JSON.stringify(vo);
          var strInfoAfter64 = base64encode(strInfo); 
//           alert(strInfo);
          
          $('#calendar').fullCalendar({
        	  weekends: true,//显示周日和周六
              theme: true,//显示主题
              header: {
                  left: 'prev,next today',
                  center: 'title',
                  right: 'month,agendaWeek,agendaDay'
              },
           
              monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
              monthNamesShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
              dayNames: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
              dayNamesShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
              today: ["今天"],
              firstDay: 1,
              buttonText: {
                  today: '本月',
                  month: '月',
                  week: '周',
                  day: '日',
                  prev: '上一月',
                  next: '下一月'
              },
              viewDisplay: function (view) {//动态把数据查出，按照月份动态查询
              var viewStart = $.fullCalendar.formatDate(view.start, "yyyy-MM-dd HH:mm:ss");
              var viewEnd = $.fullCalendar.formatDate(view.end, "yyyy-MM-dd HH:mm:ss");
                  $("#calendar").fullCalendar('removeEvents');
                  $.get("/api/task/securi_caList", { sign : strInfoAfter64}, function (data) {
//                       var resultCollection = jQuery.parseJSON(data);
//                       alert(resultCollection);
//                       $.each(data.obj, function (index, term) {
//                     	 console.log(term);
                    	 for(var i=0; i<data.obj.length; i++) {
                    		  for(var j=0; j<data.obj[i].list.length; j++){
	                              var obj = new Object();
	                              obj.id = data.obj[i].list[j].id;  
	                              obj.title = data.obj[i].list[j].workTypeName;
	                              obj.fullname = data.obj[i].list[j].workTypeName;
	                              obj.description = data.obj[i].list[j].content;
	                              obj.messagenotice = data.obj[i].list[j].content;
	                              obj.start = $.fullCalendar.parseDate(new Date(data.obj[i].list[j].starttime)); 
	                              obj.end = $.fullCalendar.parseDate(new Date(data.obj[i].list[j].starttime)); 
	                              
// 	                              obj.start = $.fullCalendar.parseDate(viewStart); 
// 	                              obj.end = $.fullCalendar.parseDate(viewEnd); 
// 	                              obj.start = getLocalTime(data.obj[i].list[j].starttime);                 
// 	                              obj.end = getLocalTime(data.obj[i].list[j].starttime);
	                              $("#calendar").fullCalendar('renderEvent', obj, true); //把从后台取出的数据进行封装以后在页面上以fullCalendar的方式进行显示 
                    		  }
                      	  }
//                           $("#calendar").fullCalendar('renderEvent', term, true);
//                       });
  
                  }); //把从后台取出的数据进行封装以后在页面上以fullCalendar的方式进行显示
              },
              editable: true,//判断该日程能否拖动
              dayClick: function (date, allDay, jsEvent, view) {//日期点击后弹出的jq ui的框，添加日程记录
                  var selectdate = $.fullCalendar.formatDate(date, "yyyy-MM-dd");//选择当前日期的时间转换
                  $("#end").datetimepicker('setDate', selectdate);//给时间空间赋值
                  $("#reservebox").dialog({
                      autoOpen: false,
                      height: 450,
                      width: 400,
                      title: '创建任务 ' + selectdate,
                      modal: true,
                      position: "center",
                      draggable: false,
                      beforeClose: function (event, ui) {
                          //$.validationEngine.closePrompt("#meeting");
                          //$.validationEngine.closePrompt("#start");
                          //$.validationEngine.closePrompt("#end");
                      },
                      timeFormat: 'HH:mm{ - HH:mm}',
  
                      buttons: {
                          "关闭": function () {
                              $(this).dialog("close");
                          },
                          "保存": function () {
 
                             var startdatestr = $("#start").val(); //开始时间
                             var enddatestr = $("#end").val(); //结束时间
                             var confid = $("#title").val(); //标题
                             var det = $("#details").val(); //内容 
                             var cd = $("#chengdu").val(); //重要程度 
                             var id2;
                             var startdate = $.fullCalendar.parseDate(selectdate + "T" + startdatestr);//时间和日期拼接
                             var enddate = $.fullCalendar.parseDate(enddatestr);
                             var schdata = { title: confid, fullname: confid, description: det, confname: cd, confshortname: 'M1', start: selectdate + ' ' + startdatestr, end: enddatestr };
                             $.ajax({
                                type: "POST", //使用post方法访问后台
 
                                url: "http://www.cnblogs.com/sr/getallid.ashx", //要访问的后台地址
                                data: schdata, //要发送的数据
                                 success: function (data) {
                                    //对话框里面的数据提交完成，data为操作结果
                                    id2 = data;
                                    var schdata2 = { title: confid, fullname: confid, description: det, confname: cd, confshortname: 'M1', start: selectdate + ' ' + startdatestr, end: enddatestr, id: id2 };
                                    $('#calendar').fullCalendar('renderEvent', schdata2, true);
                                    $("#start").val(''); //开始时间
                                    $("#end").val(''); //结束时间
                                    $("#title").val(''); //标题
                                    $("#details").val(''); //内容 
                                    $("#chengdu").val(''); //重要程度 
 
                                 }
                             });


                             $(this).dialog("close");
 
 
                         }
 
                     }
                 });
                 $("#reservebox").dialog("open");
                 return false;
             },
      
             loading: function (bool) {
                 if (bool) $('#loading').show();
                 else $('#loading').hide();
             },
             eventAfterRender: function (event, element, view) {//数据绑定上去后添加相应信息在页面上
                 var fstart = $.fullCalendar.formatDate(event.start, "HH:mm");
                 var fend = $.fullCalendar.formatDate(event.end, "HH:mm");
                
 
                 var confbg = '';
                 if (event.confid == 1) {
                     confbg = confbg + '<span class="fc-event-bg"></span>';
                 } else if (event.confid == 2) {
                     confbg = confbg + '<span class="fc-event-bg"></span>';
                 } else if (event.confid == 3) {
                     confbg = confbg + '<span class="fc-event-bg"></span>';
                 } else if (event.confid == 4) {
                     confbg = confbg + '<span class="fc-event-bg"></span>';
                 } else if (event.confid == 5) {
                     confbg = confbg + '<span class="fc-event-bg"></span>';
                 } else if (event.confid == 6) {
                     confbg = confbg + '<span class="fc-event-bg"></span>';
                 } else {
                     confbg = confbg + '<span class="fc-event-bg"></span>';
                 }
 
               //  var titlebg = '<span class="fc-event-conf" style="background:' + event.confcolor + '">' + event.confshortname + '</span>';
 
 //                if (event.repweeks > 0) {
 //                    titlebg = titlebg + '<span class="fc-event-conf" style="background:#fff;top:0;right:15;color:#3974BC;font-weight:bold">R</span>';
 //                }
 
                 if (view.name == "month") {//按月份
                     var evtcontent = '<div class="fc-event-vert"><a>';
                     evtcontent = evtcontent + confbg;
                     evtcontent = evtcontent + '<span class="fc-event-titlebg">' + fstart + " - " + fend + event.fullname + '</span>';
 
                     element.html(evtcontent);
                 } else if (view.name == "agendaWeek") {//按周
                     var evtcontent = '<a>';
                     evtcontent = evtcontent + confbg;
                     evtcontent = evtcontent + '<span class="fc-event-time">' + fstart + "-" + fend  + '</span>';
                     evtcontent = evtcontent + '<span>'+ event.fullname + '</span>';
     
                     element.html(evtcontent);
                 } else if (view.name == "agendaDay") {//按日
                     var evtcontent = '<a>';
                     evtcontent = evtcontent + confbg;
                     evtcontent = evtcontent + '<span class="fc-event-time">' + fstart + " - " + fend +  '</span>';
     //              evtcontent = evtcontent + '<span>Room: ' + event.confname + '</span>';
   //                evtcontent = evtcontent + '<span>Host: ' + event.fullname + '</span>';
 //                    evtcontent = evtcontent + '<span>Topic: ' + event.topic + '</span>';
                  // evtcontent = evtcontent + '</a><span class="ui-icon ui-icon-arrow-2-n-s"><div class="ui-resizable-handle ui-resizable-s"></div></span>';
                     element.html(evtcontent);
                 }
             },
             //鼠标悬停事件
             eventMouseover: function (calEvent, jsEvent, view) {
            	 
                 var fstart = $.fullCalendar.formatDate(calEvent.start, "yyyy/MM/dd HH:mm");
                 var fend = $.fullCalendar.formatDate(calEvent.end, "yyyy/MM/dd HH:mm");
                 $(this).attr('title', fstart + " - " + fend + " " + "标题" + " : " + calEvent.title);
                 $(this).css('font-weight', 'normal');
                 $(this).tooltip({
                     effect: 'toggle',
                     cancelDefault: true
                 });
             },
			 //鼠标点击事件
             eventClick: function (event) {
                 var fstart = $.fullCalendar.formatDate(event.start, "HH:mm");
                 var fend = $.fullCalendar.formatDate(event.end, "HH:mm");
                 //  var schdata = { sid: event.sid, deleted: 1, uid: event.uid };
                 var selectdate = $.fullCalendar.formatDate(event.start, "yyyy-MM-dd");
                 $("#start").val(fstart); ;
                 $("#end").datetimepicker('setDate', event.start);
 
 
                 $("#title").val(event.title); //标题
                 $("#details").val(event.description); //内容 
                 $("#chengdu").val(event.confname); //重要程度 
 
 
 
                 $("#reservebox").dialog({
                     autoOpen: false,
                     height: 450,
                     width: 400,
                     title: '任务详情 ',
                     modal: true,
                     position: "center",
                     draggable: false,
                     beforeClose: function (event, ui) {
                         //$.validationEngine.closePrompt("#meeting");
                         //$.validationEngine.closePrompt("#start");
                         //$.validationEngine.closePrompt("#end");
                         $("#start").val(''); //开始时间
                         $("#end").val(''); //结束时间
                         $("#title").val(''); //标题
                         $("#details").val(''); //内容 
                         $("#chengdu").val(''); //重要程度 
                     },
                     timeFormat: 'HH:mm{ - HH:mm}',
 
                     buttons: {
                    	 "关闭": function () {
                             $(this).dialog("close");
                         },
                         "删除": function () {
                             var aa = window.confirm("警告：确定要删除记录，删除后无法恢复！");
                             if (aa) {
                                 var para = { id: event.id };
 
 
                                 $.ajax({
                                     type: "POST", //使用post方法访问后台
 
                                     url: "http://www.cnblogs.com/sr/removedate.ashx", //要访问的后台地址
                                     data: para, //要发送的数据
                                     success: function (data) {
                                         //对话框里面的数据提交完成，data为操作结果
 
 
                                         $('#calendar').fullCalendar('removeEvents', event.id);
                                     }
 
 
                                 });
 
                             }
                             $(this).dialog("close");
                         },
                         "保存": function () {
 
                             var startdatestr = $("#start").val(); //开始时间
                             var enddatestr = $("#end").val(); //结束时间
                             var confid = $("#title").val(); //标题
                             var det = $("#details").val(); //内容 
                             var cd = $("#chengdu").val(); //重要程度 
                             var startdate = $.fullCalendar.parseDate(selectdate + "T" + startdatestr);
                             var enddate = $.fullCalendar.parseDate(enddatestr);
 
                             event.fullname = confid;
                             event.confname = cd;
                             event.start = startdate;
                             event.end = enddate;
                             event.description = det;
                             var id2;
 
                             var schdata = { title: confid, fullname: confid, description: det, confname: cd, confshortname: 'M1', start: selectdate + ' ' + startdatestr, end: enddatestr, id: event.id };
                             $.ajax({
                                 type: "POST", //使用post方法访问后台
 
                                 url: "http://www.cnblogs.com/sr/Updateinfo.ashx", //要访问的后台地址
                                 data: schdata, //要发送的数据
                                 success: function (data) {
                                     //对话框里面的数据提交完成，data为操作结果
 
                                     var schdata2 = { title: confid, fullname: confid, description: det, confname: cd, confshortname: 'M1', start: selectdate + ' ' + startdatestr, end: enddatestr, id: event.id };
                                     $('#calendar').fullCalendar('updateEvent', event);
                                 }
                             });
 
 
 
 
 
                             $(this).dialog("close");
                         }
 
                     }
                 });
                 $("#reservebox").dialog("open");
                 return false;
             },
             //            events: "http://www.cnblogs.com/sr/AccessDate.ashx"
             events: []
         });
 
 
 
         //goto date function
         if ($.browser.msie) {
             $("#calendar .fc-header-right table td:eq(0)").before('<td><div class="ui-state-default ui-corner-left ui-corner-right" style="border-right:0px;padding:1px 3px 2px;" ><input type="text" id="selecteddate" size="10" style="padding:0px;"></div></td><td><div class="ui-state-default ui-corner-left ui-corner-right"><a><span id="selectdate" class="ui-icon ui-icon-search">goto</span></a></div></td><td><span class="fc-header-space"></span></td>');
         } else {
             $("#calendar .fc-header-right table td:eq(0)").before('<td><div class="ui-state-default ui-corner-left ui-corner-right" style="border-right:0px;padding:3px 2px 4px;" ><input type="text" id="selecteddate" size="10" style="padding:0px;"></div></td><td><div class="ui-state-default ui-corner-left ui-corner-right"><a><span id="selectdate" class="ui-icon ui-icon-search">goto</span></a></div></td><td><span class="fc-header-space"></span></td>');
         }
 
         $("#selecteddate").datepicker({
             dateFormat: 'yy-mm-dd',
             beforeShow: function (input, instant) {
                 setTimeout(
                             function () {
                                 $('#ui-datepicker-div').css("z-index", 15);
                             }, 100
                         );
             }
         });
 
 
 
         $("#selectdate").click(function () {
             var selectdstr = $("#selecteddate").val();
             var selectdate = $.fullCalendar.parseDate(selectdstr, "yyyy-mm-dd");
             $('#calendar').fullCalendar('gotoDate', selectdate.getFullYear(), selectdate.getMonth(), selectdate.getDate());
         });
 
 
         // conference function
         $("#calendar .fc-header-left table td:eq(0)").before('<td><div class="ui-state-default ui-corner-left ui-corner-right" id="selectmeeting"><a><span id="selectdate" class="ui-icon ui-icon-search" style="float: left;padding-left: 5px; padding-top:1px"></span>meeting room</a></div></td><td><span class="fc-header-space"></span></td>');
 
 
 
         //        $(".fc-button-prev").click(function () {
         //            alert("fasdf");
         //        });
 
     });
   
var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";  
var base64DecodeChars = new Array(  
　　-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  
　　-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  
　　-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,  
　　52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,  
　　-1,　0,　1,　2,　3,  4,　5,　6,　7,　8,　9, 10, 11, 12, 13, 14,  
　　15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,  
　　-1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,  
　　41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);  
function base64encode(str) {  
　　var out, i, len;  
　　var c1, c2, c3;  
　　len = str.length;  
　　i = 0;  
　　out = "";  
　　while(i < len) {  
 c1 = str.charCodeAt(i++) & 0xff;  
 if(i == len)  
 {  
　　 out += base64EncodeChars.charAt(c1 >> 2);  
　　 out += base64EncodeChars.charAt((c1 & 0x3) << 4);  
　　 out += "==";  
　　 break;  
 }  
 c2 = str.charCodeAt(i++);  
 if(i == len)  
 {  
　　 out += base64EncodeChars.charAt(c1 >> 2);  
　　 out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));  
　　 out += base64EncodeChars.charAt((c2 & 0xF) << 2);  
　　 out += "=";  
　　 break;  
 }  
 c3 = str.charCodeAt(i++);  
 out += base64EncodeChars.charAt(c1 >> 2);  
 out += base64EncodeChars.charAt(((c1 & 0x3)<< 4) | ((c2 & 0xF0) >> 4));  
 out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >>6));  
 out += base64EncodeChars.charAt(c3 & 0x3F);  
　　}  
　　return out;  
}  
function base64decode(str) {  
　　var c1, c2, c3, c4;  
　　var i, len, out;  
　　len = str.length;  
　　i = 0;  
　　out = "";  
　　while(i < len) {  
 /* c1 */  
 do {  
　　 c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];  
 } while(i < len && c1 == -1);  
 if(c1 == -1)  
　　 break;  
 /* c2 */  
 do {  
　　 c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];  
 } while(i < len && c2 == -1);  
 if(c2 == -1)  
　　 break;  
 out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));  
 /* c3 */  
 do {  
　　 c3 = str.charCodeAt(i++) & 0xff;  
　　 if(c3 == 61)  
　return out;  
　　 c3 = base64DecodeChars[c3];  
 } while(i < len && c3 == -1);  
 if(c3 == -1)  
　　 break;  
 out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));  
 /* c4 */  
 do {  
　　 c4 = str.charCodeAt(i++) & 0xff;  
　　 if(c4 == 61)  
　return out;  
　　 c4 = base64DecodeChars[c4];  
 } while(i < len && c4 == -1);  
 if(c4 == -1)  
　　 break;  
 out += String.fromCharCode(((c3 & 0x03) << 6) | c4);  
　　}  
　　return out;  
}  
function utf16to8(str) {  
　　var out, i, len, c;  
　　out = "";  
　　len = str.length;  
　　for(i = 0; i < len; i++) {  
 c = str.charCodeAt(i);  
 if ((c >= 0x0001) && (c <= 0x007F)) {  
　　 out += str.charAt(i);  
 } else if (c > 0x07FF) {  
　　 out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));  
　　 out += String.fromCharCode(0x80 | ((c >>　6) & 0x3F));  
　　 out += String.fromCharCode(0x80 | ((c >>　0) & 0x3F));  
 } else {  
　　 out += String.fromCharCode(0xC0 | ((c >>　6) & 0x1F));  
　　 out += String.fromCharCode(0x80 | ((c >>　0) & 0x3F));  
 }  
　　}  
　　return out;  
}  
function utf8to16(str) {  
　　var out, i, len, c;  
　　var char2, char3;  
　　out = "";  
　　len = str.length;  
　　i = 0;  
　　while(i < len) {  
 c = str.charCodeAt(i++);  
 switch(c >> 4)  
 {  
　 case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7:  
　　 // 0xxxxxxx  
　　 out += str.charAt(i-1);  
　　 break;  
　 case 12: case 13:  
　　 // 110x xxxx　 10xx xxxx  
　　 char2 = str.charCodeAt(i++);  
　　 out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));  
　　 break;  
　 case 14:  
　　 // 1110 xxxx　10xx xxxx　10xx xxxx  
　　 char2 = str.charCodeAt(i++);  
　　 char3 = str.charCodeAt(i++);  
　　 out += String.fromCharCode(((c & 0x0F) << 12) |  
　　　　((char2 & 0x3F) << 6) |  
　　　　((char3 & 0x3F) << 0));  
　　 break;  
 }  
　　}  
　　return out;  
}  
  
/* function doit() {  
　　var f = document.f  
　　f.output.value = base64encode(utf16to8(f.source.value))  
　　f.decode.value = utf8to16(base64decode(f.output.value))  
}  */ 
function getLocalTime(nS) {
	return new Date(parseInt(nS)).toLocaleString().replace(/年|月/g);
}
function  timetodate(tim,dat){
    return  new Date(parseInt(tim)*1000).pattern(dat);   //"yyyy-MM-dd hh:mm:ss"    
 }
</script> 
    