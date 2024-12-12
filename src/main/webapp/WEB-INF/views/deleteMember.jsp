<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TripPlanner</title>
</head>
<body>
	<h1>정말로 삭제하시겠습니까?!?!?!?!?</h1><a href="${pageContext.request.contextPath}">Home</a>
	<%
		Member member = (Member)session.getAttribute("user");
	%>
	<form method="POST" action="${pageContext.request.contextPath}/members/deleteMember">
	    <p>아이디 : <%= member.getId()%>
	    <br>
	    <p>이름 : <%= member.getName()%>
	    <br>
	    <p>이메일 : <%= member.getEmail()%>
	    <br>
	    <p>지역 : <%= member.getRegion()%>
	    <br>
	    <p>전화번호 : <%= member.getPhone1()%>-<%= member.getPhone2()%>-<%= member.getPhone3()%>
	    <br>
	    <p>생년월일 : <%= member.getBirthday()%>
	    <br>
	    <input type="submit" value="회원 삭제">
	</form>
</body>
</html>