<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

		<h2>Metrics Data Form</h2>
		<form:form method="POST" action="addEmployee">
	   		<table>
			  <tr>
			        <td><form:label path="name">Name:</form:label></td>
			        <td><form:input path="name" /></td>
			    </tr>
			    <tr>
			        <td><form:label path="weight">Weight</form:label></td>
			        <td><form:input path="weight" /></td>
			    </tr>
				<tr>
			      <td colspan="2"><input type="submit" value="Submit"/></td>
		      </tr>
			</table>  
		</form:form>
</body>
</html>