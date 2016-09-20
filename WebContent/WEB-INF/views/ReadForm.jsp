<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<table border="1px" bordercolor="black" width=25% align="center">
		<tr>
			<th>Weight</th>
			<th>TimeStamp</th>
		</tr>
		<c:forEach items="${model.read}" var="element">
			<tr>
				<td>${element.weight}</td>
				<td>${element.timestamp}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>