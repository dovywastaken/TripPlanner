<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TripPlanner</title>
</head>
<body>
    <h1>Main Page</h1>
    
    <% 
        // 세션에서 로그인된 사용자 정보를 가져옴
        Member member = (Member) session.getAttribute("user");

        // 로그인된 사용자 확인
        if (member != null) {
    %>
        <p>어서오세요, <%= member.getName() %>님!</p> <!-- 로그인된 사용자의 이름을 표시 -->
        <a href="logout">로그아웃</a> <!-- 로그아웃 링크 --> |
        <a href="updateMember">마이 페이지</a> 
        <a href="admin"> | 회원 목록</a>
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
