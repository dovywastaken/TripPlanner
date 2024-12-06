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
	    <input type="text" name="id" value="<%= member.getId()%>" placeholder="아이디" readonly>
	    <br>
	    <input type="text" name="name" value="<%= member.getName()%>" placeholder="이름" readonly>
	    <br>
	    <input type="password" name="pw" value="<%= member.getPw()%>" placeholder="비밀번호">
	    <br>
	    <input type="text" name="region" value="<%= member.getRegion()%>" placeholder="지역" readonly>
	    <br>
	    <input type="text" name="phone1" value="<%= member.getPhone1()%>" placeholder="전화번호(앞자리)" readonly>
	    <input type="text" name="phone2" value="<%= member.getPhone2()%>" placeholder="전화번호(중간자리)" readonly>
	    <input type="text" name="phone3" value="<%= member.getPhone3()%>" placeholder="전화번호(끝자리)" readonly>
	    <br>
	    <input type="date" name="birthday" value="<%= member.getBirthday()%>" placeholder="생년월일" readonly>
	    <br>
	    <input type="submit" value="회원 삭제">
	</form>
</body>
</html>