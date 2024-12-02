<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trip Planner - 로그인</title>
</head>
<body>
	<h2>로그인</h2>
	<form method="POST" action="signIn">
		<input type="text" name="id" placeholder="아이디" required>
		<br>
		<input type="password" name="pw" placeholder="비밀번호" required>
		<br>
		<input type="submit" value="로그인">
	</form>

	<%
		if (request.getAttribute("loginError") != null) 
		{
		    String errorMessage = (String) request.getAttribute("loginError");
		%>
		<script>
		    alert("<%= errorMessage %>");
		</script>
		<%
		}
	%>
</body>
</html>
