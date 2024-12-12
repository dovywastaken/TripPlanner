<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TripPlanner</title>
</head>
<body>
	<%
		Member member = (Member)session.getAttribute("user");
	%>
	<a href="${pageContext.request.contextPath}">Home</a>
	<hr>
	<form method="POST" action="${pageContext.request.contextPath}/members/updateMember">
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
	</form>
	<a href="updateMember">회원 정보 수정</a>
	
	<hr>
	
	<div>
		<div> <p>Password Change, SignOut</div>
		<div>
			<a href="pwChange">비밀번호 변경</a>
			<a href="deleteMember">회원 탈퇴</a>
		</div>
	</div>
	<hr>
	
</body>
</html>