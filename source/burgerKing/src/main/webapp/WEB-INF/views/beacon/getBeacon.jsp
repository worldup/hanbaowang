<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style>
 #addBeaconForm .text{width:200px;}
  #addBeaconForm .title{width:120px;}
</style>
		<table class="addform_wrap" id="addBeaconForm">
			<colgroup>
				<col style="width:120px;"/>
				<col style="width:210px;"/>
				<col style="width:120px;"/>
				<col style="width:210px;"/>
			</colgroup>
			<tr>
				<td class="title"><b></b>设备号：</td>
				<td> ${beacon.uuid}</td> 
				<td class="title"><b></b>majorId：</td>
				<td>${beacon.majorId}</td> 
			</tr>
			<tr>
				<td class="title"><b></b>minorId：</td>
				<td>${beacon.minorId}</td> 
				<td class="title"><b></b>名称：</td>
				<td>${beacon.name}</td> 
			</tr>
			<tr>
				<td class="title"><b></b>MAC：</td>
				<td>${beacon.mac}</td> 
				<td class="title"><b></b>校正值：</td>
				<td>${beacon.measurrdPower}</td> 
			</tr>
			<tr>
				<td class="title"><b></b>信号强度：</td>
				<td>${beacon.signalStrength}</td> 
				<td class="title"><b></b>电池耐久度：</td>
				<td>${beacon.remainingPower}</td> 
			</tr>
			<tr>
				<td class="title"><b></b>更新时间：</td>
				<td><fmt:formatDate value="${beacon.lastDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td> 
				<td class="title"><b></b>公司：</td>
				<td>${beacon.company}</td> 
			</tr>
			<tr>
				<td class="title"><b></b>型号：</td>
				<td>${beacon.model}</td> 	
				<td class="title"><b></b>类型：</td>
				<td>${beacon.mold}</td>
			<tr> 
				<td class="title"><b></b>覆盖距离：</td>
				<td>${beacon.distance}</td>
				<td class="title"><b></b>备注：</td>
				<td>${beacon.remarks}</td> 
			<tr>
				<td class="title"><b></b>门店名称：</td>
				<td>${beacon.shopName}</td>
			</tr>
			
			<%-- <tr>
				<td class="title"><b></b>门店：</td>
				<td align="left">
					<select name="shopid" id="shopid" >
						<option value="-1">---请选择---</option>
						<c:forEach items="${shopNames}" var="shop">
							<option value="${shop.value}">${shop.name}</option>
						</c:forEach>
					</select>
				</td> 
			</tr>  --%>
		</table>
