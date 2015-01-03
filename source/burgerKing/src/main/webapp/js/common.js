var formatNum = function(srcNumber) {
	var txtNumber = '' + srcNumber;
	if (isNaN(txtNumber) || txtNumber == "") {
		alert("Oops!  That does not appear to be a valid number.  Please try again.");
		fieldName.select();
		fieldName.focus();
	} else {
		var rxSplit = new RegExp('([0-9])([0-9][0-9][0-9][,.])');
		var arrNumber = txtNumber.split('.');
		arrNumber[0] += '.';
		do {
			arrNumber[0] = arrNumber[0].replace(rxSplit, '$1,$2');
		} while (rxSplit.test(arrNumber[0]));
		if (arrNumber.length > 1) {
			return arrNumber.join('');
		} else {
			return arrNumber[0].split('.')[0];
		}
	}
}

/**
 * closeHandler:窗口关闭时的回调函数
 */
var showWindow = function(id,titleName,closeHandler,isModel) {
	//隐藏错误提示
	$(".ball_layer_pointout").css("display","none");
	var obj = $("#" + id);
	obj.mousedown(function(e){
		//隐藏错误提示
		$(".ball_layer_pointout").text("");
		$(".ball_layer_pointout").css("display","none");
	});
	var title = $("#" + id + " h2");
	//标题
	title.html(titleName);
	//计算中心位置
	var setPosition = function(obj) {
		var screenWidth = $(window).width();
		var screenHeight = $(window).height(); 
		var scrolltop = $(document).scrollTop();// 获取当前窗口距离页面顶部高度
		var objLeft = (screenWidth - obj.width()) / 2;
		var objTop = (screenHeight - obj.height()) / 2 + scrolltop;
		obj.css({
			left : objLeft + 'px',
			top : objTop + 'px'
		});
	};
	
	//上传窗口不需要模态化
	if(isModel!='0'){
		 $("body").append("<div id='window_overlay' class='ui-widget-overlay ui-front'></div>");	
	} 
	setPosition(obj);
	obj.fadeIn(200);
	
	//拖拽效果
	var isDraging = false;
	var x,y;//鼠标距离window左上角的偏移位置
	
	title.mousedown(function(e){
		isDraging = true;
		x=e.pageX-parseInt(obj.css("left"));
		y=e.pageY-parseInt(obj.css("top"));
	});
	$(document).mousemove(function(e){
		if(isDraging) {
			var minLeft = $("div.main").offset().left;
			var maxRight =  $("div.mid_main").width()-minLeft;
			var maxHeight = $("body").height();
			var left = e.pageX - x;
			var top = e.pageY - y;
			left = left <= minLeft ? minLeft: (left+obj.width() >= maxRight ? maxRight-obj.width()-10 : left);
			top = top + obj.height() >= maxHeight ? maxHeight-obj.height() : top < 0 ? 0 : top;
			obj.css({"top":top,"left":left});
		}
	}).mouseup(function(){
		isDraging = false;
	});
	$("a.ball_btn_no,a.del").click(function(){
		//防止关闭窗口时候关闭上传窗口
		if(id!='fileUploadDialog'&&id!='fileUploadDialogMin'){
			closeWindow(id);	
		} 
		if($.isFunction(closeHandler)){
			closeHandler();
		}
	});
	
	//消息提示框
	$("#messageInfoWindow a.ball_btn_true").click(function(){
		closeWindow("messageInfoWindow");
	});
	
	//异常信息框
	$("#exceptionWindow a.ball_btn_true").click(function(){
		closeWindow("exceptionWindow");
	});
	
	//消息提示框
	$("#messageConfirmWindow a.ball_btn_true,#messageConfirmWindow a.ball_btn_no_cf").click(function(){
		closeWindow("messageConfirmWindow");
		$("#messageConfirmWindow a.ball_btn_true").unbind();
		$("#typeCheckbox").hide();
	});
	
	//添加ESC监听事件
	$("body").bind("keyup",function(event){
		if(event.keyCode==27) {
			closeWindow(id);
			$("#messageConfirmWindow a.ball_btn_true").unbind();
			$("#typeCheckbox").hide();
		}
	});
	
};

//重写消息提示框
$.messager={alert:function(title,info,type){
	if($("#messageInfoWindow .ball_layer_pointout03").is(":visible")){
		$("#messageInfoWindow .ball_layer_pointout03").append(info);
	}else{
		showWindow("messageInfoWindow","提示");
		$("#messageInfoWindow .ball_layer_pointout03").text(info);
	} 
	
},confirm:function(title,info,fn){
    showWindow("messageConfirmWindow","提示");
    $("#messageConfirmWindow .ball_layer_pointout03").text(info);
    $("#messageConfirmWindow a.ball_btn_true").click(function(){
        if(fn){
            fn(true);
            return false;
            }
    }); 
    $("#messageConfirmWindow a.ball_btn_no_cf").click(function(){
    	$("#messageConfirmWindow a.ball_btn_true").unbind();
    	  if(fn){
              fn(false);
              return false;
              }
    }); 
}
};
var messageInfo = function(msg){
	showWindow("commonInfoWindow","提示");
	$("#commonInfoWindow .ball_layer_pointout04").text(msg);
	setTimeout('closeCommonInfoWindow()',1000);
	
};

var showExceptionInfo = function(msg){
	showWindow("exceptionWindow","错误提示");
	if(msg!=null && msg!=""){
		if(msg == 'CONNECTION_EXCEPTION'){
			msg = '中心平台数据同步接口通讯失败！';
		}
		$("#exceptionWindow .ball_layer_pointout06").text("系统服务异常，异常信息："+msg);
	}
};

var closeCommonInfoWindow = function(){
	closeWindow("commonInfoWindow");
};


function checkHasEmpty(id){
    $(".ball_layer_pointout").text("");
    var hasEmpty = false;
	$("#"+id+" tr").each(function() { 
		 if($(this).find("i").hasClass("red")){
			 
			 if($(this).find("input").val()==''){ 
				  $(".ball_layer_pointout").css("display","");
				  $(".ball_layer_pointout").append(formatLabel($(this).find("label").text())+"不能为空<br>");	 
				  hasEmpty = true;
				  return false;
			 }else if($(this).find("textarea").val()==''){ 
				  $(".ball_layer_pointout").css("display","");
				  $(".ball_layer_pointout").append(formatLabel($(this).find("label").text())+"不能为空<br>"); 
				  hasEmpty = true;
				  return false;
			 } 
			 else if($(this).find("select option").length>0 && $(this).find("select").val()==null){
				  $(".ball_layer_pointout").css("display","");
				  $(".ball_layer_pointout").append(formatLabel($(this).find("label").text())+"不能为空<br>");
				  hasEmpty = true;
				  return false;
			 } 
	  
		 }
		 
	});
	return hasEmpty;
}
function formatLabel(label){
	if(label!=null){
		label = label.replace("：","");
		label = label.replace(":","");
	}
	return label;
}

function appendErrorInfo(errorInfo){
	$(".ball_layer_pointout").css("display","");
    $(".ball_layer_pointout").append(errorInfo);	 
}
function appendErrorInfo2(errorInfo){
	$(".ball_layer_pointout").text("");
	$(".ball_layer_pointout").css("display","");
    $(".ball_layer_pointout").append(errorInfo);	 
}

var closeWindow = function(id) {
	$("#"+id).hide();
	$("#" + id + " .w_clear").each(function(){
		//解决IE报错的问题
		if($(this)[0].nodeName == "INPUT") {
			$(this).val("");
		} 
/*		else {
			$(this).text("");
		}*/
	});
	$("#window_overlay").remove();
	//取消body所有监听
	$("body").unbind();
};
var highlight = function(title) {
	$("#nav_bar>a[name=" + title + "]").addClass("on highlight");
};
var generateNav = function() {
	/*for ( var i = 0; i < arguments.length; i++) {
		if (i < arguments.length - 1) {
			$(".public_nav_info").append("<span style=\"display:inline-block;float:left\">"+arguments[i]+"&nbsp;&gt;&nbsp;</span>");
		} else {
			$(".public_nav_info").append("<span class=\"main_nav\">"+arguments[i]+"</span>");
		}
	}
	$(".public_nav_info").show();*/
};

var reGenerateNav = function() {
	$(".common_nav").text("");
	$(".common_nav").append("<img src='"+basePath+"/themes/blue/images/ico_arrow_right.gif' />&nbsp;&nbsp;");
	for ( var i = 0; i < arguments.length; i++) {
		if (i < arguments.length - 1) {
			$(".common_nav").append("<span>"+arguments[i]+"</span>&nbsp;&gt;&nbsp;");
		} else {
			$(".common_nav").append("<span>"+arguments[i]+"</span>");
		}
	}
};
var hideNav = function() {
	$(".common_nav").hide();
}; 
$(function () {
	if($.messager!=undefined){
		$.messager.defaults = { ok: "确定", cancel: "取消" };
	}
});


//比较日期大小，格式yyyy-mm-dd   a>b 返回true
function dayDateCompare(a, b) {
    var arr = a.split("-");
    var starttime = new Date(arr[0], arr[1]-1, arr[2]);
    var starttimes = starttime.getTime();

    var arrs = b.split("-");
    var lktime = new Date(arrs[0], arrs[1]-1, arrs[2]);
    var lktimes = lktime.getTime();
    if (starttimes > lktimes) {
        return true;
    }
    else
        return false;

}

//时间比较(yyyy-mm-dd hh:mi:ss)
function dateCompare(beginTime,endTime) {
    var beginTimes = beginTime.substring(0, 10).split('-');
    var endTimes = endTime.substring(0, 10).split('-');

    beginTime = beginTimes[1] + '/' + beginTimes[2] + '/' + beginTimes[0] + ' ' + beginTime.substring(10, 19);
    endTime = endTimes[1] + '/' + endTimes[2] + '/' + endTimes[0] + ' ' + endTime.substring(10, 19);

    var a = (Date.parse(endTime) - Date.parse(beginTime)) / 3600 / 1000;
    if (a < 0) {
       return true;
    } else if (a >= 0) {
       return false;
    } else {
        return 'exception';
    }
}

function getRenameReport(ids,tableName,reportName){
	var time=new Date();
	var year=time.getFullYear();
	var month=time.getMonth()+1;
	var day=time.getDate();
	var hour=time.getHours(); //获取当前小时数(0-23)
	var minutes=time.getMinutes(); //获取当前分钟数(0-59)
	var second=time.getSeconds(); //获取当前秒数(0-59)
	var reportNames='';
	for(var i=0;i<ids.length;i++){
		var cl = ids[i];
		reportNames+=jQuery('#'+tableName).jqGrid('getCell', cl, reportName)+'_';
	}
	if(month<10)
		month='0'+month;
	if(day<10)
		day='0'+day;
	if(hour<10)
		hour='0'+hour;
	if(minutes<10)
		minutes='0'+minutes;
	if(second<10)
		second='0'+second;
	reportNames=reportNames+year+month+day+hour+minutes+second+".txt";
	return reportNames;
	
}
function getRenameReportExcel(ids,tableName,reportName){
	var time=new Date();
	var year=time.getFullYear();
	var month=time.getMonth()+1;
	var day=time.getDate();
	var hour=time.getHours(); //获取当前小时数(0-23)
	var minutes=time.getMinutes(); //获取当前分钟数(0-59)
	var second=time.getSeconds(); //获取当前秒数(0-59)
	var reportNames='';
	for(var i=0;i<ids.length;i++){
		var cl = ids[i];
		reportNames+=jQuery('#'+tableName).jqGrid('getCell', cl, reportName)+'_';
	}
	if(month<10)
		month='0'+month;
	if(day<10)
		day='0'+day;
	if(hour<10)
		hour='0'+hour;
	if(minutes<10)
		minutes='0'+minutes;
	if(second<10)
		second='0'+second;
	reportNames=reportNames+year+month+day+hour+minutes+second+".xls";
	return reportNames;
	
}

function getRenameReportXls(ids,tableName,reportName){
	var time=new Date();
	var year=time.getFullYear();
	var month=time.getMonth()+1;
	var day=time.getDate();
	var hour=time.getHours(); //获取当前小时数(0-23)
	var minutes=time.getMinutes(); //获取当前分钟数(0-59)
	var second=time.getSeconds(); //获取当前秒数(0-59)
	var reportNames=jQuery('#'+tableName).jqGrid('getCell', ids, reportName)+'_';
	if(month<10)
		month='0'+month;
	if(day<10)
		day='0'+day;
	if(hour<10)
		hour='0'+hour;
	if(minutes<10)
		minutes='0'+minutes;
	if(second<10)
		second='0'+second;
	reportNames=reportNames+year+month+day+hour+minutes+second+".xls";
	return reportNames;
}


function getRenameReportXlsWithOneName(ids,tableName,reportName){
	var time=new Date();
	var year=time.getFullYear();
	var month=time.getMonth()+1;
	var day=time.getDate();
	var hour=time.getHours(); //获取当前小时数(0-23)
	var minutes=time.getMinutes(); //获取当前分钟数(0-59)
	var second=time.getSeconds(); //获取当前秒数(0-59)
	var reportNames=reportName+"_";
	if(month<10)
		month='0'+month;
	if(day<10)
		day='0'+day;
	if(hour<10)
		hour='0'+hour;
	if(minutes<10)
		minutes='0'+minutes;
	if(second<10)
		second='0'+second;
	reportNames=reportNames+year+month+day+hour+minutes+second+".xls";
	return reportNames;
}


//格式yyyy-mm-dd  与当前日期比较，大于则返回true
function dayCompareCurrent(a) {
    var arr = a.split("-");
    var starttime = new Date(arr[0], arr[1]-1, arr[2]);
    var starttimes = starttime.getTime();
    var currentTimes = (new Date()).getTime();
    if (starttimes > currentTimes) {
        return true;
    }
    else
        return false;

}

//格式yyyy-mm-dd hh:mi:ss  与当前日期比较，大于则返回true
function timeCompareCurrent(beginTime) {
	  var beginTimes = beginTime.substring(0, 10).split('-'); 
	  beginTime = beginTimes[1] + '/' + beginTimes[2] + '/' + beginTimes[0] + ' ' + beginTime.substring(10, 19);
      var a = (Date.parse(beginTime) - (new Date()).getTime());
	    if (a < 0) {
	       return false;
	    } else if (a >= 0) {
	       return true;
	    } else {
	        return 'exception';
	    }
}
//加br换行
function addBrs(strContent,intLen){
	var strTemp="";
	while(strContent.length>intLen){
	strTemp+=strContent.substr(0,intLen)+"<br />";  
	strContent=strContent.substr(intLen,strContent.length);  
	}
	strTemp+=strContent;
	return strTemp;
}

//-- 消除空格 --//
//去左空格;
//去左空格;
function ltrim(s) {
  s=s.replace(/^　*/g,"");
  return s.replace(/^\s*/, "");
}
//去右空格;
function rtrim(s) {
  s=s.replace(/　*$/g,"");
  return s.replace(/\s*$/, "");
}
//去左右空格;
function trim(s) {
  return rtrim(ltrim(s));
}
//-- 消除空格END --//

var alertSuccessInfo = function(){
	$.messager.alert("提示", "操作成功！", "info");
};
var alertInfo = function(info){
	$.messager.alert("提示", info, "info");
};
var alertFailInfo = function(){
	$.messager.alert("提示", "操作失败！", "warning");
};

//数据格式化  s为需要格式化的数据 整数格式 小数格式会去掉小数部分的数据 
//n为格式化的长度
//例：fnumber(1234567,3)=1,234,567; fnumber(1234567.8901,3)=1,234,567 
function fnumber(s, n)  
{  
   n = n > 0 && n <= 20 ? n : 2;  
   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
   var l = s.split(".")[0].split("").reverse(),  
   //r = s.split(".")[1];  
   t = "";  
   for(i = 0; i < l.length; i ++ )  
   {  
      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
   }  
   return t.split("").reverse().join("");  
} 
//获取当前时间yyyy-mm-dd
function getCurentTime()
{ 
    var now = new Date();
   
    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日
   
    var hh = now.getHours();            //时
    var mm = now.getMinutes();          //分
   
    var clock = year + "-";
   
    if(month < 10)
        clock += "0";
   
    clock += month + "-";
   
    if(day < 10)
        clock += "0";
       
    clock += day + " ";
   
    return(clock); 
} 
function isIP(strIP) { 
    var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/g; //匹配IP地址的正则表达式 
    if(re.test(strIP)) 
    { 
       if( RegExp.$1 <256 &&RegExp.$1>=0 && RegExp.$2<256 &&RegExp.$2>=0 && RegExp.$3<256 &&RegExp.$3>=0 && RegExp.$4<256 &&RegExp.$4>=0) return true; 
      } 
   return false; 
} 
function updateMsgStatu(msgId,statu){
	$.ajax({
		async : true,
		type : "post",
		dataType : 'json',
		url : basePath + '/messageInfo/updateMsgStatu.action',
		data : {
			msgStatu:statu,
			msgId : msgId
		},
		success : function(data) {
			
		}
	});
}
/**
 * 显示消息的详细情况
 * @param msgId 
 * @param msgType
 */
function viewMsgDetail(msgId,msgType,isRead,closeCallback){
	"use strict";
	if(!isRead)
		{
		updateMsgStatu(msgId,'T');
		}
	var DEFAULT_VALUE = "-",
		_config = { //对话框的name名称跟java对象属性的映射
			msgTask : {//应急任务，专项任务
				taskname : {
					jattrName : 'taskName'
				},
				taskId : {
					jattrName : 'taskId'
				},
				taskInfo : {
					jattrName : 'taskInfo'
				},
				keyWordsAll : {
					jattrName : 'keywordsAll'
				},
				keyWordsOne : {
					jattrName : 'keywordsOne'
				},
				keyWordsNone : {
					jattrName : 'keywordsNone'
				},
				type : {
					jattrName : 'type',
					formatter : function(type){
						if(type == DEFAULT_VALUE){
							return type;
						}
						return type == '1' ? '应急任务' : '专项任务';
					}
				},
				timeInterval : {
					jattrName : 'timeInterval'
				},
				startTime : {
					jattrName : 'startTime'
				},
				endTime : {
					jattrName : 'endTime'
				}
			},
			keyWord : {
				categoryName : {
					jattrName : 'categoryName'
				},
				parentCategory : {
					jattrName : 'parentCategoryName'
				},
				isLeaf : {
					jattrName : 'isLeaf',
					formatter : function(data){
						if(data == DEFAULT_VALUE){
							return data;
						}
						return data == 0?'否':'是';
					}
				},
				createTime : {
					jattrName : 'createTime'
				},
				updateTime : {
					jattrName : 'updateTime'
				},
				keywordsAll : {
					jattrName : 'keywordsAll'
				},
				keywordsOne : {
					jattrName : 'keywordsOne'
				},
				keywordsNone : {
					jattrName : 'keywordsNone'
				}
			}
		};
	msgId = parseInt(msgId);
	msgType = parseInt(msgType);
	_getDetailObj(msgId,msgType,closeCallback);
	var $msgTask = $("#msgTask");
	$msgTask.find("#addTaskBtn").bind('click',function(){
		var paramObj = {},baseUrl,
		 taskId = $msgTask.find("span[name='taskId']").text()=='-'?'': $msgTask.find("span[name='taskId']").text(),
		 taskname = $msgTask.find("span[name='taskname']").text()=='-'?'':$msgTask.find("span[name='taskname']").text();
		paramObj.fromMsgReq = true;
		paramObj.keywordAll = $msgTask.find("span[name='keyWordsAll']").text()=='-'?'':$msgTask.find("span[name='keyWordsAll']").text();
		paramObj.keywordOne = $msgTask.find("span[name='keyWordsOne']").text()=='-'?'':$msgTask.find("span[name='keyWordsOne']").text();
		paramObj.keywordNone = $msgTask.find("span[name='keyWordsNone']").text()=='-'?'':$msgTask.find("span[name='keyWordsNone']").text();
		if(msgType == 1){
			paramObj.taskId = taskId;
			paramObj.taskName = taskname;
			baseUrl = basePath + "/emergencyAction/queryEmergencyIndex.action";
			$.cookie('fromMsgReq', 'true',{path:'/'}); 
		}else {
			paramObj.staskName = taskname;
			paramObj.specailTaskId = taskId;
			paramObj.taskBeginDate = $msgTask.find("span[name='startTime']").text()=='-'?'': $msgTask.find("span[name='startTime']").text();
			paramObj.taskEndDate = $msgTask.find("span[name='endTime']").text()=='-'?'':$msgTask.find("span[name='endTime']").text();
			paramObj.execCycle = $msgTask.find("span[name='timeInterval']").text()=='-'?'': $msgTask.find("span[name='timeInterval']").text();
			baseUrl = basePath + "/specialSupervisionAction/index.action";
			$.cookie('fromMsgReq_s', 'true',{path:'/'}); 
		}
		window.location = baseUrl + "?" + $.param(paramObj);
	});
	
	function _getDetailObj(msgId,msgType,closeCallback){
		var detailObj = null;
		if(msgType != 3){//非 应急任务初步研判结果下发 的情况下才向后台发送请求信息，因为应急任务初步研判结果下发 可以直接在jqgrid中直接指定
			$.ajax({
				async : true,
				type : "post",
				dataType : 'json',
				url : basePath + '/messageInfo/showMessageDetail.action',
				data : {
					msgId : msgId,
					msgType : msgType
				},
				success : function(jsonData) {
//					try{
						detailObj = jsonData;
						_showDetailDlg(detailObj,msgType,msgId,closeCallback);
					/*}catch(e){
						throw new Error(jsonData + " is not a json obj");
					}*/
				}
			});
		}else{
			_showDetailDlg(detailObj,msgType,msgId,closeCallback);
		}
		return detailObj;
	}
/**/
	function getConcreteMsgType(msgType){
		var type='';
		switch(msgType){
			case 1:
				type = '应急任务';
				break;
			case 2:
				type = '专项任务';
				break;
			case 3:
				type = '应急任务初步研判结果下发';
				break;
			case 4:
				type = '关键字库下发';
				break;
			default:
					break;
		}
		return type;
	}
	
	function _showDetailDlg(detailObj,msgType,msgId,closeCallback){
		var $directContainer = $("#detailDlg .bg_boder"),
			fillContext = null,//填充数据的上下文
			dataMap = null,
			$task = $("#msgTask"),
			$keyWord = $("#keyWord"),
			$auditDetails = $("#auditDetails");
		$("#detailDlg").css('width','340px');
		switch(msgType){
			case 1:
			case 2:
				var 
					specialTaskInputs = $task.find("div[data-belong=2]"),
					emegercyTask = $task.find("div[data-belong=1]");
				$keyWord.hide();
				$auditDetails.hide();
				
				$directContainer.append($task.show());
				if(msgType == 1){
					specialTaskInputs.hide();
					emegercyTask.show();
				}else{
					specialTaskInputs.show();
					emegercyTask.hide();
				}
				msgType == 1 ? specialTaskInputs.hide() : specialTaskInputs.show();
				fillContext = $task;
				dataMap = _config.msgTask;
				_fillData(fillContext,detailObj,dataMap);
				break;
			case 3:
				$keyWord.hide();
				$task.hide();
				$directContainer.append($auditDetails.show());
				_initAuditDetailGrid(msgId,msgType);
				$("#detailDlg").css('width','711px');
				break;
			case 4:
				$task.hide();
				$auditDetails.hide();
				$directContainer.append($keyWord.show());
				
				fillContext = $keyWord;
				dataMap = _config.keyWord;
				_fillData(fillContext,detailObj,dataMap);
				break;
			default:
				break;
		
		}
		showWindow('detailDlg',getConcreteMsgType(msgType),closeCallback);
	}
	
	function _fillData($context,detailObj,dataMap){
		var $inputs = $context.find("span");
		if(detailObj){
			$.each($inputs,function(i,item){
				var name = $(item).attr('name');
				var obj = dataMap[name],
					value = detailObj[obj.jattrName] ? detailObj[obj.jattrName] : DEFAULT_VALUE;
				if(obj.formatter){
					$(item).html("<a title='" +obj.formatter(value)+"'>"+obj.formatter(value)+"</a>");
				}else{
					$(item).html("<a title='" +value+"'>"+value+"</a>");
				}
			});
		}
	}
	
	function _initAuditDetailGrid(msgId,msgType){
		if($("#auditDetails").find(".ui-jqgrid-view").get(0)){
			$("#auditDetails_datagrid").setGridParam({
				url : basePath + '/messageInfo/showMessageDetail.action',
				postData : {
					msgId : msgId,
					msgType : msgType
				}
			}).trigger("reloadGrid");
		}else{
			$("#auditDetails_datagrid").jqGrid({
				altRows : true,
				altclass : "jqgrid_alt_row",
				url: basePath + '/messageInfo/showMessageDetail.action',
				postData : {
					msgId : msgId,
					msgType : msgType
				},
				datatype: "json",
				colNames:['任务名称','节目名称','节目URL','是否通过','备注信息'],
				colModel:[ 
				          {name:'taskName',index:'taskName', width:100,sortable:false},
				          {name:'programName',index:'programName', width:100,sortable:false,formatter:_pNameFormate},
				          {name:'programUrl',index:'programUrl', width:200,sortable:false,formatter:_pNameFormate},
				          {name:'result',index:'result', width:100,sortable:false,formatter:_passFormate},
				          {name:'remark',index:'remark',width:150,align:"center",sortable:false}
				          ],
				          rowNum:20,
				          rowList:[10,20,50],
				          pager: '#auditDetails_pager',
				          viewrecords: true,
				          jsonReader: {
				        	  repeatitems : false
				          },
				          autowidth : true,
				          height:"300px",
				          width : "600px", 
				          rownumbers:true
			});
			jQuery("#auditDetails_datagrid").jqGrid('navGrid','#auditDetails_pager',{edit:false,add:false,del:false,refresh:false,search:false});
			$("#auditDetails_datagrid").closest(".ui-jqgrid-bdiv").css({ 'overflow-x' : 'hidden' });
		}
	}
	function _passFormate(cellvalue, options, rowObject) {
		var result = '<a class="table_notPass_btn"></a>';
		if(cellvalue && cellvalue.length > 0){
			cellvalue = cellvalue.toUpperCase();
			result = (cellvalue == 1)? '<a class="table_passed_btn"></a>' : '<a class="table_notPass_btn"></a>';
		}
		return result;
	}
	
	function _pNameFormate(cellvalue, options, rowObject) {
		//"<a href="" target="_blank"></a>"
		//var result = "<a href="++" target="_blank"></a>";
		var url = rowObject.programUrl,
			result = "<a href="+url+" target='_blank' style='color:#0068B7;'>"+cellvalue+"</a>";
		return result;
	}
	
	function _boolFormate(cellvalue, options, rowObject) {
		var result = '否';
		if(cellvalue && cellvalue.length > 0){
			cellvalue = cellvalue.toUpperCase();
			result = (cellvalue == 'T')? '是' : '否';
		}
		return result;
	}
}
function plugin()
{
    return document.getElementById('pluginCamStudio');
}

var toDownLoadVideoPage = function(url){
	if(url!=null&&url!=''){ 
		
		try{
			var ret = plugin().callCamStudio();
			if(ret==0){
				$.messager.confirm("提示", "当前系统未安装CamStudio，请确认是否下载？", function (data) {
		            if (data) { 
		            	location.href=basePath+"/soft/CamStudio.exe";
		            }
		      });  
			}else{
				if(url.indexOf('http://')==-1){
					url = "http://"+url;
				}
				window.open(url); 
			}	
		}catch(e){
			$.messager.confirm("提示", "当前系统未安装取证插件，请确认是否下载？", function (data) {
	            if (data) { 
	            	location.href=basePath+"/soft/CamStudioHelper.exe";
	            }
	      });  
		}
		
		
		
	} 
};


/**
 * 过滤特殊字符的正则表达式（added by zhaojinhua）
 */
var stripscript= function(s)
{
	var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]");
	var rs = "";
	for (var i = 0; i < s.length; i++) {
	rs = rs+s.substr(i, 1).replace(pattern, '');
	}
	return $.trim(rs);
}; 

/**
 * 获取昨天时间 
 */
var getYesterdayDate = function(){
	var yday = new Date(); 
	var yesterday_milliseconds=yday.getTime()-1000*60*60*24; //昨天 时间毫秒数
	yday.setTime(yesterday_milliseconds); //昨天  时间
	var year = yday.getFullYear();
	var month = yday.getMonth()+1;
	var day = yday.getDate();
	
	var clock = year + "-";
	   
    if(month < 10)
        clock += "0";
   
    clock += month + "-";
   
    if(day < 10)
        clock += "0";
       
    clock += day;
	return clock;
};
/**
 * 获取上传文件的实际名称
 */
var getRealFileName=function(fileName){
	if(fileName.indexOf('_')!=-1){
		if(fileName.lastIndexOf('.')!=-1){
			return fileName.substr(0,fileName.lastIndexOf("_"))+"."+fileName.substring(fileName.lastIndexOf('.')+1);
		}
		else{
			return fileName.substr(0,fileName.lastIndexOf("_"));
		}
	}else{
		return fileName;
	}
	
};

//组装当前时间
function CurentTime()
{ 
	 var now = new Date();
	   
	    var year = now.getFullYear();       //年
	    var month = now.getMonth() + 1;     //月
	    var day = now.getDate();            //日
	   
	    var hh = now.getHours();            //时
	    var mm = now.getMinutes();          //分
	   
	    var clock = year + "";
	   
	    if(month < 10)
	        clock += "0";
	   
	    clock += month + "";
	   
	    if(day < 10)
	        clock += "0";
	       
	    clock += day + " ";
	   
	    /*if(hh < 10)
	        clock += "0";
	       
	    clock += hh + ":";
	    if (mm < 10) clock += '0'; 
	    clock += mm; */
	    return(clock); 
}
var checkDataSynServer = function(){
	$.ajax({
		url : basePath+"/webSiteCheckAction/checkServerStatus.action",
		dataType : "json",
		type : 'post',
		cache : false,
		
		success : function(data, textStatus) {
			 return data; 
		}
	}); 
  	
};

 
function pluginValid()
{
    if(plugin().valid){
        
    } else { 
    	
    }
}


function callTencent(address,port,username,password)
{
	 if(plugin().valid){
			var ret = plugin().loginRTX(address, port, username, password);
			if(ret==0){
				$.messager.confirm("提示", "当前系统未安装腾讯通，请确认是否下载？", function (data) {
		            if (data) { 
		            	location.href=basePath+"/soft/rtxclientl.exe";
		            }
		      });     
			}   
	    } else { 
	    	$.messager.confirm("提示", "当前系统未安装腾讯通登录插件，请确认是否下载？", function (data) {
	            if (data) { 
	            	location.href=basePath+"/soft/CamStudioHelper.exe";
	            }
	      });  
	    } 
}

// 首页数字超过1万，以xxx.x显示
var changeTwoDecimal=function(x){
	if(null==x){
		return "-";
	}
	if(x>10000){
		x=x/10000;
		var f_x=parseFloat(x-0);
		if(isNaN(f_x)){
		return false;
		}
		f_x=Math.round(x*10)/10;
		return formatNumber(f_x)+"万";
	}
	return formatNumber(x);
};

function formatNumber(num){  
	 if(!/^(\+|-)?(\d+)(\.\d+)?$/.test(num)){  
	  return num;  
	 }  
	 var a = RegExp.$1,b = RegExp.$2,c = RegExp.$3;  
	 var re = new RegExp();  
	 re.compile("(\\d)(\\d{3})(,|$)");
	 while(re.test(b)){  
	  b = b.replace(re,"$1,$2$3");  
	 }
	 return a +""+ b +""+ c;  
	}

//display Load
function displayProgress($container) {
	var scrolltop = $(document).scrollTop(),
		maskHeight = 0,
		maskWidth = 0;
		appendTo = null;
	var offset = null;
	if($container){
		maskHeight = $container.height();
		maskWidth = $container.width();
		appendTo = $container;
		offset = $container.offset();
	}else{
		maskHeight = document.body.clientHeight || document.documentElement.clientHeight;
		maskWidth = document.body.clientWidth || document.documentElement.clientWidth;
		appendTo = $('body').get(0);
	}
	//$(document.body).css('overflow','hidden').css('padding-right','17px');
    $("<div id=\"updateProgressBar\" class=\"datagrid-mask\" style=\"z-index: 1000000;\"></div>").
    css({ display: "block", width: maskWidth, height: maskHeight }).appendTo(appendTo);
    $("<div id=\"updateProgressBarMsg\" class=\"datagrid-mask-msg\" style=\"z-index: 1000000;\"></div>").html("正在处理，请稍候")
    .appendTo(appendTo).css('display',"block");
    var $temp = $("#updateProgressBarMsg"),
    	$mask = $('#updateProgressBar'),
    	$win = $container || $(window);
    if(offset){
    	var maskPos = {
    			left : offset.left,
    			top : offset.top + parseInt($win.css('paddingTop'))
    	},
    	msgPos = {
    		left : maskPos.left + ($win.width() - $temp.width()) / 2,
    		top : 	maskPos.top + ($win.height() - $temp.height()) / 2
    	};
    	
    	$mask.css(maskPos);
    	$temp.css(msgPos);
    }else{
    	var viewWidth = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth,
    		viewHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
    	$temp.css({
    		left : (viewWidth - $temp.width()) / 2,
    		top: (viewHeight - $temp.height()) / 2  + scrolltop
    	});
    }
   $("#updateProgressBarMsg").dblclick(function(){
        unDisplayProgress();
    });
	 
}

//hidden Load
function unDisplayProgress() {
	//$(document.body).css('overflow','auto').css('padding-right',0);
    $("#updateProgressBar").remove();
    $("#updateProgressBarMsg").remove();
}

//获取元素的绝对左位置
function getElementLeft(element){
	var actualLeft = element.offsetLeft;
	var current = element.offsetParent;
	while (current !== null){
		actualLeft += current.offsetLeft;
		current = current.offsetParent;
	}
	return actualLeft;
}

//获取元素的绝对上位置
function getElementTop(element){
	var actualTop = element.offsetTop;
	var current = element.offsetParent;
	while (current !== null){
		actualTop += current.offsetTop;
		current = current.offsetParent;
	}
	return actualTop;
}
//将时间yyyy-mm-dd hh:mm:ss转换为一串数字
function getTimeToNum(time){
	var result='';
	var year=time.split('-')[0];
	result+=year;
	var month=time.split('-')[1];
	if(month.length==1){
		month+='0'+month;
	}
	result+=month;
	var date=time.split(' ')[0].split('-')[2];
	if(date.length==1){
		date+='0'+date;
	}
	result+=date+"_";
	var hh=time.split(' ')[1].split(':')[0];
	if(hh.length==1){
		hh+='0'+hh;
	}
	result+=hh;
	var min=time.split(' ')[1].split(':')[1];
	if(min.length==1){
		min+='0'+min;
	}
	result+=min;
	var second=time.split(' ')[1].split(':')[2];
	if(second.length==1){
		second+='0'+second;
	}
	result+=second;
	return result;
}



/**
 * 表格那弹出浮动窗口
 */
var AlertFloat = (function($){
	var model = {};
	model = $.extend(model,{
		bindAlertFloatEvent:bindAlertFloatEvent
	});
	
	/**
	 * 
	 * @param id
	 * @param colObj {targetCol:触发事件的col的name（String）,
	 * 				urlCol:(String),
	 * 				postUrl:{
	 * 					colName:col的name（String）,
	 * 					titleCol:海报tile col的name（String）
	 * 				}
	 * 				cols:额外显示的col的name（Array）}
	 */
	function bindAlertFloatEvent(id,colObj){
		$('#'+id).find('td[aria-describedby='+getDesc(id,colObj.targetCol)+'] a[name="grid_program_name"]').mouseover(function(e){
			var $this = $(this);
			var $layer = $(".ball_layer");
			var parent = $this.parent(),liList = [];
			var $tr = $($this.parents('tr')[0]);
			//var url = $(parent.siblings('[aria-describedby='+getDesc(id,colObj.urlCol)+']')[0]).text();
			$layer.empty();
			$layer.append("<em></em>");
			$layer.css('z-index','10000000');
			
			var postUrl = colObj.postUrl;
			if (postUrl) {
				$layer.css('width','420px');
				var posterurl = '',postUrlTitle='';
				if(postUrl.colName){
					posterurl = getTdVal($tr,id,postUrl.colName);
					if(posterurl==='-'){posterurl='';}
				}
				if(postUrl.titleCol){
					postUrlTitle = getTdVal($tr,id,postUrl.titleCol);
				}
				if(posterurl == ''){
					posterurl = basePath+"/themes/blue/images/nopic.gif";
				}
				$layer.append("<a href='' target='_BLANK'><img src='"+posterurl+"' title='"+postUrlTitle+"' width='147' height='165' onError=\"this.src=\'"+basePath+"/themes/blue/images/nopic.gif\';this.onerror=null;\"/></a>");
				$(".ball_layer a").attr("href",getTdVal($tr,id,colObj.urlCol));
			} else {
				$layer.css('width','280px');
			}
			$layer.append("<dl></dl>");
			liList.push(getDefineHTML1(id,colObj.targetCol,colObj.urlCol,$tr));
			if(colObj.urlCol){
				liList.push(getDefineHTML1(id,colObj.urlCol,colObj.urlCol,$tr));
			}
			if(colObj.cols){
				$.each(colObj.cols,function(i,item){
					liList.push(getDefineHTML(id,item,$tr));
				});
			}
			$layer.find('dl').append(liList.join(''));
			
			if (id == 'gridTable3U' || id == 'gridTable5U') {
				$(".ball_layer").hide().css("left",e.pageX+20).css("top",e.pageY-90).show(200);
			} else {
				if(e.pageY<=630) {
					$(".ball_layer").hide().css("left",e.pageX+20).css("top",e.pageY-90).show(200);
				} else {
					$(".ball_layer").hide().css("left",e.pageX+20).css("top",e.pageY-150).show(200);
					$(".ball_layer em").css("top","140px");
				}
			}
			if(getOs()==='Safari'){
				$(".ball_layer dd").css("white-space","pre");
			}
			
		});
		if(parent && parent.jquery){
			$("td").not(parent.get(0)).hover(function(e){
				$(".ball_layer").hide();
			});
		}
		$("#"+id).mouseout(function(){
			$(".ball_layer").hide();
		});
		$(".ball_layer").mouseover(function(){
			$(".ball_layer").show();
		});
		$("html").click(function(){
			$(".ball_layer").hide();
		});
	}
	function getDefineHTML(id,col,$tr){
		return '<dd><i>'+getTitle(id,col)+'：</i>'+getTdVal($tr,id,col)+'</dd>';
	}
	function getDefineHTML1(id,colname,colurl,$tr){
		var url = getTdVal($tr,id,colurl);
		if(!/^http.*/.test(url)){
			url = 'http://' + url;
		}
		turl = getTdVal1($tr,id,colname) == '-' ? url : getTdVal1($tr,id,colname);
		url=url==='-'?"javascript:void(0);":url;
		return '<dd><i>'+getTitle(id,colname)+'：</i><a target="_black" href="'+url+'">'+turl+'</a></dd>';
	}
	function getDesc(id,col){
		return id+"_"+col;
	}
	function getTdVal($tr,id,col){
		return $tr.find('[aria-describedby='+getDesc(id,col)+']').attr('title') ? $tr.find('[aria-describedby='+getDesc(id,col)+']').attr('title') : '-';
	}
	function getTdVal1($tr,id,col){
		return $tr.find('[aria-describedby='+getDesc(id,col)+'] a').text() ? $tr.find('[aria-describedby='+getDesc(id,col)+'] a').text() : '-';
	}
	/**
	 * 获取表格中的标题
	 */
	function getTitle(id,col){
		return $("#jqgh_" + getDesc(id,col)).text();
	}
	return model;
})(window.jQuery);


//查看详情窗口
function queryDetail(data,dialogTitle){
	 $('.ball_layer').css("display","none");
	showWindow('checkBatchDialog',dialogTitle);
			$("#checkBatch").html("");
			$.each(eval(data), function(i, item) {
				$("#checkBatch").append("<div style='margin-top:5px;width:370px;'>"+item+"<br/></div>"
				);
			});
}

var BOMOper = (function(){
	/**
	*	非ie下判断是否含有某个插件
	*/
	function hasPlugin(name){
		//navigator.plugins.refresh(true);
		var plugins = navigator.plugins,
			name = name.toLowerCase();
		for(var i = 0,l = plugins.length;i < l;i++){
			if(plugins[i].name.toLowerCase().indexOf(name) != -1){
				return true;
			}
		}
		
		return false;;
	}
	/**
	**判断ie下是否含有某个插件
	**@param name 插件对应的COM标示符
	*/
	function hasIEPlugin(name){
		try{
			new ActiveXObject(name);
			return true;
		}catch(e){
			return false;
		}
	}
	var operCollect = {
		hasFlash: function(){
			var result = hasPlugin('Flash');
			if(!result){
				result = hasIEPlugin('ShockwaveFlash.ShockwaveFlash');
			}
			return result;
		}
		
	};
	return operCollect;
})();
function getOs()  
{  
    var OsObject = "";  
   if(navigator.userAgent.indexOf("MSIE")>0) {  
        return "MSIE";  
   }  
   if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){  
        return "Firefox";  
   }  
   if(isSafari=navigator.userAgent.indexOf("Safari")>0) {  
        return "Safari";  
   }   
   if(isCamino=navigator.userAgent.indexOf("Camino")>0){  
        return "Camino";  
   }  
   if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0){  
        return "Gecko";  
   }  
    
}  

/**
 * 字符转义
 * @param str 
 * @returns
 */
function charEscape(str){
	if(!str){
		return null;
	}
	return str.replace(/&/g,'&amp;')
				.replace(/</g,'&lt;')
				.replace(/>/g,'&gt;')
				.replace(/"/g,'&quot;')
				.replace(/©/g,'&copy;')
				.replace(/®/g,'&reg;');
}
/**
*点击checkbox之后文字,checkbox框选中
*/
function checkboxSelected(){
	var check=document.getElementById('bigCheckbox');
	if(check.checked == true){
		check.checked = false;
	}else{
		check.checked = true;
	}
}


//添加审核意见框
function addShenHeYiJian(id ,tableName){
	var ur='';
	if(tableName==='specDetailInsiGrid'){
		ur= basePath + "/programDbAction/queryProById.action";
	}else{
		ur= basePath + "/programDbAction/querySoftById.action";
	}
	
	
	$.ajax({
		async : false,
		type : "post",
		url :ur,
		data : {
			"id" : id
		},
		dataType : "json",
		cache : false,
		success : function(data) {
			unDisplayProgress();
			$("#shenHeYiJianDialog").find("#isAccept").val(data.isAccept);
			$("#shenHeYiJianDialog").find("#isExact").val(data.isExact);
		    $("#shenHeYiJianDialog").find("#notAcceptReason").val(data.notAcceptReason);
		    $("#shenHeYiJianDialog").find("#notExactReason").val(data.notExactReason);
			$("#shenHeYiJianDialog").find("#isFirstDiscovery").val(data.isFirstDiscovery);
			$("#shenHeYiJianDialog").find("#remark").val(data.remark);
		}
	});
	showWindow('shenHeYiJianDialog', '审核意见');
	$("#shenHeYiJianDialog").find("#rptDataId").val(id);
	$("#shenHeYiJianDialog").find("#tableName").val(tableName);
	}
//修改审核意见
$(function(){
	$("#shenHeYiJian_Yes").click(function() {
		var id = $.trim($("#shenHeYiJianDialog").find('#rptDataId').val());
		var tableName = $.trim($("#shenHeYiJianDialog").find('#tableName').val());
		var isAccept = $.trim($("#shenHeYiJianDialog").find("#isAccept").val());
		isAccept=isAccept==='-1'?'':isAccept;
		var isExact = $.trim($("#shenHeYiJianDialog").find("#isExact").val());
		isExact=isExact==='-1'?'':isExact;
		var notAcceptReason = $.trim($("#shenHeYiJianDialog").find("#notAcceptReason").val());
		var notExactReason = $.trim($("#shenHeYiJianDialog").find("#notExactReason").val());
		var isFirstDiscovery = $.trim($("#shenHeYiJianDialog").find("#isFirstDiscovery").val());
		isFirstDiscovery=isFirstDiscovery==='-1'?'':isFirstDiscovery;	
		var remark = $.trim($("#shenHeYiJianDialog").find("#remark").val());
		displayProgress();
		$.ajax({
					async : false,
					type : "post",
					url : basePath
							+ "/programDbAction/doUpdate.action",
					data : {
						"id" : id,
						"isAccept":isAccept,
						"isExact":isExact,
						"notAcceptReason":notAcceptReason,
						"notExactReason":notExactReason,
						"isFirstDiscovery":isFirstDiscovery,
						"tabName":tableName,
						"remark":remark
					},
					dataType : "json",
					cache : false,
					success : function(data) {
						unDisplayProgress();
						closeWindow("shenHeYiJianDialog");
						messageInfo("操作成功");
						jQuery('#'+tableName).trigger('reloadGrid');
					}
				});
	});
});

$(function(){
	showSubMenu();
});

function showSubMenu(){
	var  _ismenter = false, _seltimer = null;
	$('.sub_dhmenu li').mouseenter(function(e) {
		_ismenter = true;
		var _obj = $(this),x =_obj.offset().left, 
		_subdivcn = _obj.prop("class"),
		_subdiv = $("."+_subdivcn+"_sbmenu");
		if(_subdiv.html()==""||_subdiv.html==null){return;}
		$(".subchild_menu").slideUp("fast");
		if(_subdivcn=="wj"){x=x-10;}else{x=x;}
		_subdiv.css('left',x).slideDown("fast");
		
		/**子菜单**/
		_subdiv.mouseenter(function () {
			_ismenter = true;
		}).mouseleave(function () {
			_ismenter = false;
			if (_seltimer) clearTimeout(_seltimer);
			_seltimer = setTimeout(function () {
				if (!_ismenter) {
					$(".subchild_menu").slideUp("fast");
				}
			},200);
			
		});
		
	}).mouseleave(function() {
		_ismenter = false;
		if (_seltimer) clearTimeout(_seltimer);
		_seltimer = setTimeout(function () {
			if (!_ismenter) {
				$(".subchild_menu").slideUp("fast");
			}
		}, 200);
		
	});	
	
	
}
//显示导航菜单
function showNavigater(){
	var $header = $('.dzsub_header'),
		$nav = $('#nav');
	var topNav = $.trim($header.find('.sub_dhmwrap .sub_dhmenu a[class="current"]').text()),
		secondNav = $.trim($header.find('.subchild_menu ul a[class="current"]').text());
	var navText = topNav == '' ? secondNav : topNav + (secondNav == '' ? '' : '>>' + secondNav);
	$nav.html(navText);
	
	
}
