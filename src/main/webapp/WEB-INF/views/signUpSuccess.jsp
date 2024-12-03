<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TripPlanner</title>
</head>
<body>
    <h1>회원가입 성공</h1>
    <p>아이디: ${member.getId()}</p>
    <p>비밀번호 : ${member.getPw()}</p>
    <p>이름: ${member.getName()}</p>
    <p>지역: ${member.getRegion()}</p>
    <p>성별 : ${member.getSex()}</p>
    <p>전화번호 : ${member.getPhone1()} - ${member.getPhone2()} - ${member.getPhone3()}</p>
    <p>생년월일 : ${member.getBirthday()}</p>
    <p>
    <a href="${pageContext.request.contextPath}">Home</a>
</body>
</html>
