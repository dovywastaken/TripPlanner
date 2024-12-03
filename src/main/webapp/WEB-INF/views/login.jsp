<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TripPlanner</title>
</head>
<body>
	<h2>로그인</h2><a href="${pageContext.request.contextPath}">Home</a>
	<form method="POST" action="signIn">
		<p>아이디 : <input type="text" name="id" placeholder="아이디" required></p>
		<p>비밀번호 : <input type="password" name="pw" placeholder="비밀번호" required></p>
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
