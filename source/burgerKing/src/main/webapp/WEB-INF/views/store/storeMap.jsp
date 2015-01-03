<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css"
	href="http://developer.amap.com/Public/css/demo.Default.css" />
<div id="iCenter"></div>
<script type="text/javascript"
	src="http://webapi.amap.com/maps?v=1.3&key=5103133477d912e17dad755100300554"></script>
<script type="text/javascript">
var mapObj,cluster;
var markers= [];
	$(function() {
		//加载地图
		mapInit();
		$.ajax({
			//async : false,
			type : 'post',
			url : basePath + "/store/mapLoad",
			data : {
				//"id" : id
			},
			dataType : "json",
			cache : false,
			error : function(err) {
				alert("系统出错了，请联系管理员！");
			},
			success : function(data) {
				$.each(eval(data),function(i,item){
					//门店名称
					var shopName=getEmptyString(item.shopname);
					//门店名称
					var chineseName=getEmptyString(item.chineseName);
					
					//门店图片
					var shopimage=getEmptyString(item.shopimage);
					
					//月份
					var month=getMonthTime(item.latestSts.month);
					//sales
					var sales=getEmptyString(item.latestSts.sales);
					//comp
					var salesComp=getEmptyString(item.latestSts.salesComp);
					//rank
					var salesRank=getEmptyString(item.latestSts.salesRank);
					//tc
					var tc=getEmptyString(item.latestSts.tc);
					//comp
					var tcComp=getEmptyString(item.latestSts.tcComp);
					//rank
					var tcRank=getEmptyString(item.latestSts.tcRank);
					//gtNps
					var gtNps=getEmptyString(item.latestSts.gtNps);
					//rank
					var rank=getEmptyString(item.latestSts.rank);
					//revBs
					var revBs=getEmptyString(item.latestSts.revBs);
					//revFs
					var revFs=getEmptyString(item.latestSts.revFs);
					//cashAudit
					var cashAudit=getEmptyString(item.latestSts.cashAudit);
					//OC用户名称
					var userName=getEmptyString(item.userNames);
					//经度
					var longitude=getEmptyString(item.longitude);
					//纬度
					var latitude=getEmptyString(item.latitude);
					  var markerPosition = new AMap.LngLat(longitude,latitude);
						var marker = new AMap.Marker({
							position:markerPosition, //基点位置
							icon:"http://developer.amap.com/wp-content/uploads/2014/06/marker.png", //marker图标，直接传递地址url
							offset:{x:-8,y:-34}, //相对于基点的位置
							content:getMarkerContent(chineseName)
						});
						markers.push(marker);
					var content=[];
					/* content.push("<img src="+basePath+"/upload/storeImage/"+shopimage+" style='position:relative;float:left;margin:0 5px 5px 0;'>"+shopName+""); */
					content.push("<img src='http://tpc.googlesyndication.com/simgad/5843493769827749134' style='position:relative;float:left;margin:0 5px 5px 0;'>"+shopName+"");
					content.push(""+month+"月：Sales:"+sales+" Comp:"+salesComp+" Rank:"+salesRank+"");
					content.push(""+month+"月：TC:"+tc+" Comp:"+tcComp+" Rank:"+tcRank+"");
					content.push("GT-NPS:"+gtNps+"% RANK:"+rank+"");
					content.push("REV-BS:"+revBs+" REV-FS:"+revFs+"");
					content.push("Cash Audit:"+cashAudit+"");
					content.push("负责OC "+userName+"");
					  $.each(item.latestSignInfo,function(j,latest){
							//最近一次签到时间
							var signintime=getEmptyString(latest.signintime);
							console.log(signintime);
							if(signintime==''){
								content.push("未签到");
							}else{
							    content.push(""+getMonth(signintime)+"月"+getDay(signintime)+"日已签到");
							}
						});  
					 new AMap.InfoWindow({
							isCustom:true,  //使用自定义窗体
							content:createInfoWindow("",content.join('<br/>')),
							offset:new AMap.Pixel(16, -45)//-113, -140
						}).open(mapObj,marker.getPosition());	
					AMap.event.addListener(marker,'click',function(){ //鼠标点击marker弹出自定义的信息窗体
						 new AMap.InfoWindow({
								isCustom:true,  //使用自定义窗体
								content:createInfoWindow("",content.join('<br/>')),
								offset:new AMap.Pixel(16, -45)//-113, -140
							}).open(mapObj,marker.getPosition());	
				   });	
				});
		addCluster(0);
			}
		});
		
	});
	function getEmptyString(str){
		return str==null?'':str;
	}
	function getMonthTime(nS) {     
		   return new Date(parseInt(nS)).getMonth()+1;      
		} 
	function getMonth(str){
		str = str.replace(/-/g,"/");
		var date = new Date(str );
		 return date.getMonth()+1;  
	}
	function getDay(str){
		str = str.replace(/-/g,"/");
		var date = new Date(str );
		 return date.getDate(); 
	}
	/*
	 *地图初始化&向地图随机加点
	 */
	function mapInit() {
		mapObj = new AMap.Map("iCenter",{
			//二维地图显示视口
			view: new AMap.View2D({
				//center:new AMap.LngLat(116.397428,39.90923),//地图中心点
				zoom:13 //地图显示的缩放级别
			})
		});	
	}   
	
	var getMarkerContent= function(shopName){
		var markerContent = document.createElement("div");
		markerContent.className = "markerContentStyle";
	    
		//点标记中的图标
		var markerImg= document.createElement("img");
	     markerImg.className="markerlnglat";
		 markerImg.src=basePath+"/img/icon_mendiandingwei.png";	
		 markerContent.appendChild(markerImg);
		 
		 //点标记中的文本
		  var markerSpan = document.createElement("span");
		 markerSpan.innerHTML = shopName;
		 markerContent.appendChild(markerSpan); 
		 return markerContent;
	}
	
	/*
	 *添加点聚合
	 */
	function addCluster(tag)
	{
		if(cluster) {	
			cluster.setMap(null);
		}
		if(tag==1) {
			var sts=[{url:"http://developer.amap.com/wp-content/uploads/2014/06/1.png",size:new AMap.Size(32,32),offset:new AMap.Pixel(-16,-30)},
				{url:"http://developer.amap.com/wp-content/uploads/2014/06/2.png",size:new AMap.Size(32,32),offset:new AMap.Pixel(-16,-30)},
				{url:"http://developer.amap.com/wp-content/uploads/2014/06/3.png",size:new AMap.Size(48,48),offset:new AMap.Pixel(-24,-45),textColor:'#CC0066'}];
			mapObj.plugin(["AMap.MarkerClusterer"],function(){
				cluster = new AMap.MarkerClusterer(mapObj,markers,{styles:sts});
			});
		}
		else {
			mapObj.plugin(["AMap.MarkerClusterer"],function(){
				cluster = new AMap.MarkerClusterer(mapObj,markers);
			});
		}
	}

	//实例化信息窗体
	var infoWindow = new AMap.InfoWindow({
			isCustom:true,  //使用自定义窗体
			content:createInfoWindow('方恒假日酒店&nbsp;&nbsp;<span style="font-size:11px;color:#F00;">价格:318</span>',"<img src='http://tpc.googlesyndication.com/simgad/5843493769827749134' style='position:relative;float:left;margin:0 5px 5px 0;'>地址：北京市朝阳区阜通东大街6号院3号楼 东北 8.3 公里<br/>电话：010 64733333<br/><a href='http://baike.baidu.com/view/6748574.htm'>详细信息</a>"),
			offset:new AMap.Pixel(16, -45)//-113, -140
		});

	//构建自定义信息窗体	
	function createInfoWindow(title,content){
		var info = document.createElement("div");
		info.className = "info";

		//可以通过下面的方式修改自定义窗体的宽高
		info.style.width = "400px";

		// 定义顶部标题
		var top = document.createElement("div");
		top.className = "info-top";
		  var titleD = document.createElement("div");
		  titleD.innerHTML = title;
		  var closeX = document.createElement("img");
		  closeX.src = "http://webapi.amap.com/images/close2.gif";
		  closeX.onclick = closeInfoWindow;
		  
		top.appendChild(titleD);
		top.appendChild(closeX);
		info.appendChild(top);
		
	    
		// 定义中部内容
		var middle = document.createElement("div");
		middle.className = "info-middle";
		middle.style.backgroundColor='white';
		middle.innerHTML = content;
		info.appendChild(middle);
		
		// 定义底部内容
		var bottom = document.createElement("div");
		bottom.className = "info-bottom";
		bottom.style.position = 'relative';
		bottom.style.top = '0px';
		bottom.style.margin = '0 auto';
		var sharp = document.createElement("img");
		sharp.src = "http://webapi.amap.com/images/sharp.png";
		bottom.appendChild(sharp);	
		info.appendChild(bottom);
		return info;
	}

	//关闭信息窗体
	function closeInfoWindow(){
		mapObj.clearInfoWindow();
	}

</script>