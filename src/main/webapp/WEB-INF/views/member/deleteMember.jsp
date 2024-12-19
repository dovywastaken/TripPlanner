<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TripPlanner</title>
</head>
<body>
    <h1>정말로 삭제하시겠습니까?!?!?!?!?</h1>
    <a href="${pageContext.request.contextPath}">Home</a>
    
    <form method="POST" action="deleteMember">
        <p>아이디 : ${member.id}</p>
        <p>이름 : ${member.name}</p>
        <p>이메일 : ${member.email}</p>
        <p>지역 : ${member.region}</p>
        <p>전화번호 : ${member.phone1}-${member.phone2}-${member.phone3}</p>
        <p>생년월일 : ${member.birthday}</p>
        <input type="submit" value="회원 삭제">
    </form>
</body>
</html>
