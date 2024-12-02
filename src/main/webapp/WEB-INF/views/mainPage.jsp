<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trip Planner</title>
</head>
<body>
    <h1>Main Page</h1>
    
    <% 
        // 세션에서 로그인된 사용자 정보를 가져옴
        Member user = (Member) session.getAttribute("user");

        // 로그인된 사용자 확인
        if (user != null) {
    %>
        <p>어서오세요, <%= user.getName() %>님!</p> <!-- 로그인된 사용자의 이름을 표시 -->
        <a href="logout">로그아웃</a> <!-- 로그아웃 링크 -->
        <a href="updateMember">회원정보 수정</a>
    <% 
        } else {
    %>
        <a href="signIn">로그인</a>
        <a href="signUp">회원가입</a>
    <% 
        }
    %>

</body>
</html>
