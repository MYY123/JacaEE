<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<table class="easyui-datagrid" fit="true">
			<thead>
				<tr>
					<th data-options="field:'id',width:120">编号</th>
					<th data-options="field:'product',width:120">商品</th>
					<th data-options="field:'arrivecity',width:120">到达城市</th>
					<th data-options="field:'sender',width:120">发件人</th>
					<th data-options="field:'receiver',width:120">收件人</th>
					<th data-options="field:'receiverAddress',width:120">收件人地址</th>
					<th data-options="field:'receiverTelephone',width:120">收件人电话</th>
					<th data-options="field:'check',width:120">启动流程</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${workManager}" var="workOrderManage">  
				<tr>
					
					
					<td> <c:out value="${workOrderManage.id}"></c:out></td>
					<td> <c:out value="${workOrderManage.product}"></c:out></td>
					<td> <c:out value="${workOrderManage.arrivecity}"></c:out></td>
					<td> <c:out value="${workOrderManage.sendername}"></c:out></td>
					<td> <c:out value="${workOrderManage.receivername}"></c:out></td>
					<td> <c:out value="${workOrderManage.receiveraddr}"></c:out></td>
					<td> <c:out value="${workOrderManage.receiverphone}"></c:out></td>
					
					
					<td> 
					
						 <a href="start?id=${workOrderManage.id}" cssClass="easyui-linkbutton" 
						 iconCls="icon-edit">启动
						</a>
					</td>
				</tr>
				
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>