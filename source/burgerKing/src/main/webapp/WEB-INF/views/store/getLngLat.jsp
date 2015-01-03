<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css"
	href="http://developer.amap.com/Public/css/demo.Default.css" />
<div id="iCenter" style="display:none;"></div>
<script type="text/javascript"
	src="http://webapi.amap.com/maps?v=1.3&key=08c1bedf740161a9b8223bda612abd8c"></script>
<script type="text/javascript">
var mapObj;
var result=[];
	$(function() {
		//加载地图
		mapInit();
		$.ajax({
			async : false,
			type : 'post',
			url : basePath + "/store/getAllStoreInfo",
			dataType : "json",
			cache : false,
			error : function(err) {
				alert("系统出错了，请联系管理员！");
			},
			success : function(data) {
				var cnt=data.length;
				$.each(eval(data),function(j,item){
					//门店编号
					var shopnum=getEmptyString(item.shopnum);
					//门店地址
					var chineseAddress=getEmptyString(item.chineseAddress);
					if(chineseAddress!=''){
						chineseAddress=chineseAddress.replace(/#/g,"_");
						 //加载地理编码插件
				 	    mapObj.plugin(["AMap.Geocoder"], function() {        
					        MGeocoder = new AMap.Geocoder(/* { 
					            city:"010", //城市，默认：“全国”
					            radius:1000 //范围，默认：500
					        } */);
					        //返回地理编码结果 
					        AMap.event.addListener(MGeocoder, "complete", function(data){
					        	   //地理编码结果数组
					    	    var geocode = new Array();
					    	    geocode = data.geocodes;  
					    	    for (var i = 0; i < geocode.length; i++) {
					    	    	result.push(shopnum+","+geocode[i].location.getLng()+","+geocode[i].location.getLat());
					    	    	 if(cnt==result.length){
					    	    		//保存进数据库
					    				  $.ajax({
					    					async : false,
					    					type : 'post',
					    					url : basePath + "/store/saveLngLats",
					    					data:{
					    						"lngLats":result.join(';'),
					    						"flag":"1"
					    						},
					    					dataType : "json",
					    					cache : false,
					    					error : function(err) {
					    						alert("系统出错了，请联系管理员！");
					    					},
					    					success : function(data){
					    					} 
					    				}); 	 
					    	    	} 
					    	    }  
					    	    mapObj.setFitView(); 
					        }); 
					        //地理编码
					        MGeocoder.getLocation(chineseAddress); 
					    }); 
					}else{
						cnt-=1;
					}
				});	
			}
		});
		//地理编码返回结果展示   
		function geocoder_CallBack(data){
		    //地理编码结果数组
		    var geocode = new Array();
		    geocode = data.geocodes;  
		    for (var i = 0; i < geocode.length; i++) {
		        //拼接输出html
		       // resultStr += "<span style=\"font-size: 12px;padding:0px 0 4px 2px; border-bottom:1px solid #C1FFC1;\">"+"<b>地址</b>："+geocode[i].formattedAddress+""+ "<b>&nbsp;&nbsp;&nbsp;&nbsp;坐标</b>：" + geocode[i].location.getLng() +", "+ geocode[i].location.getLat() +""+ "<b>&nbsp;&nbsp;&nbsp;&nbsp;匹配级别</b>：" + geocode[i].level +"</span>";   
		    	//console.log(geocode[i].formattedAddress+","+geocode[i].location.getLng()+","+geocode[i].location.getLat());
		    }  
		    mapObj.setFitView();   
		}  
		/*
		 *地图初始化&向地图随机加点
		 */
		function mapInit() {
			mapObj = new AMap.Map("iCenter");	
		} 
		function getEmptyString(str){
			return str==null?'':str;
		}
	});
	
</script>