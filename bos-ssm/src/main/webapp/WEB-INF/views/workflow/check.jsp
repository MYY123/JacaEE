<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>审核工作单</h3>
	${map }
	<form action="${pageContext.request.contextPath}/taskController/checkWorkOrderManage" method="POST">
		<input name="taskId" type="hidden" value="${taskId}"/>
		
		 审核结果：<select name="check">
		 			<option value="1">通过</option>
		 			<option value="0">不通过</option>
		 		</select>
		 		<input type="submit" value="提交">
	</form>
</body>
</html>