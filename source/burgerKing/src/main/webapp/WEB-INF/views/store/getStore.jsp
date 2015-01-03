<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
 #storeForm .text{width:200px;}
  #storeForm .title{width:120px;text-align:left}
</style>
		<table class="addform_wrap" id="storeForm" style="margin:20px;">	
			<colgroup>
				<col style="width:120px;"/>
				<col style="width:210px;"/>
				<col style="width:120px;"/>
				<col style="width:210px;"/>
			</colgroup>
		
			<tr>
				<td class="title">餐厅名称：</td>
				<td>${shop.shopname}</td>
				<td class="title" style="padding-left:120px">门店地址：</td>
				<td>${shop.shopaddress}</td>
			</tr>
			<tr>
				<td class="title">投资类型：</td>
				<td>${shop.straightJointJoin}</td>
				<td class="title" style="padding-left:120px">开业时间：</td>
				<td><fmt:formatDate value="${shop.shopopentime}" pattern="yyyy-MM-dd"/></td>
			</tr>
			<tr>
				<td class="title">餐厅商圈类型：</td>
				<td>${shop.businessCircle}</td>
				<td class="title" style="padding-left:120px">价格组：</td>
				<td>${shop.tiers}</td>
			</tr>
			<tr>
				<td class="title">品牌延伸：</td>
				<td>${shop.brandExtensionChDesc}</td>
				<td class="title" style="padding-left:120px">餐厅面积：</td>
				<td>${shop.shopsize}</td>
			</tr>
			<tr>
				<td class="title">厨房面积：</td>
				<td>${shop.kitchenArea}</td>
				<td class="title" style="padding-left:120px">餐厅座位数：</td>
				<td>${shop.shopseatnum}</td>
			</tr>
			<tr>
				<td class="title">营业时间：</td>
				<td>${shop.shopbusinesstime}</td>
				<td class="title" style="padding-left:120px">联系方式：</td>
				<td>${shop.shopphone}</td>
			</tr>
			<tr>
				<td class="title">餐厅租期到期日期：</td>
				<td><fmt:formatDate value="${shop.expireTime}" pattern="yyyy-MM-dd"/></td>
				<td class="title" style="padding-left:120px">租金信息：</td>
				<td>${shop.currentRent}</td>
			</tr>
			<tr>
				<td class="title">重装开业：</td>
				<td><fmt:formatDate value="${shop.reOpenTime}" pattern="yyyy-MM-dd"/></td>
				<td class="title" style="padding-left:120px">设备折旧：</td>
				<td>${shop.equipment}</td>
			</tr>
			<tr>
				<td class="title">装修摊销：</td>
				<td>${shop.lhi}</td>
				<td class="title" style="padding-left:120px">城市：</td>
				<td>${shop.prefecture}</td>
			</tr>
			<tr>
				<%-- <td class="title">省份：</td>
				<td>${shop.province}</td> --%>
				<td class="title">所属区域：</td>
				<td>${shop.regional}</td>
				<td class="title"  style="padding-left:120px">OC：</td>
				<td>${shop.userNames}</td>
			</tr>
			<tr>
				<td class="title">OM：</td>
				<td>${shop.om}</td>
				<td class="title"  style="padding-left:120px">营业状态：</td>
				<td>${shop.status}</td>
			</tr>
			<tr>
				<td class="title">保本营业额：</td>
				<td>${shop.ebitda}</td>
				<td class="title" style="padding-left:120px">邮箱：</td>
				<td>${shop.email}</td>
			</tr>
			<tr>
				<td class="title">门店图片：</td>
				<td colspan="3">
					<c:if
						test="${shop.shopimage!=''&&shop.shopimage!=null}">
						<span
							onclick="showpic('${shop.shopimage}')"
							style="cursor: pointer; color: blue">查看图片</span>
					</c:if>
					<c:if
						test="${shop.shopimage==''||shop.shopimage==null}">
						<span>无图片</span>
					</c:if>
				</td>
			</tr>
		</table>
		<script>
		function showpic(imgid){
			  var iurl=basePath+"/upload/storeImage/";
			  window.open(basePath+"/common/showmultipic.jsp?filePath="+iurl+"&filenames="+imgid, "newwindow", "scrollbars=yes");
			  return;
		}
		
		</script>
