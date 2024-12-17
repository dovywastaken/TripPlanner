<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TripPlanner</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>var contextPath = '${pageContext.request.contextPath}';</script>
    <script src="${pageContext.request.contextPath}/resources/js/myPage.js"></script>
</head>
<body>
    <%
    Member member = (Member)session.getAttribute("user");
    int emailCheck = member.getEmailCheck();
    %>
    <a href="${pageContext.request.contextPath}">Home</a>
    <hr>
    <form method="POST" action="${pageContext.request.contextPath}/members/updateMember">
        <p>아이디 : <%= member.getId() %></p>
        <p>이름 : <%= member.getName() %></p>
        <p>이메일 : <%= member.getEmail() %> 
            <% if(emailCheck == 1) { %>
                <span style="color: green;">✔ 인증 완료</span>
            <% } %>
        </p>
        <p>지역 : <%= member.getRegion() %></p>
        <p>전화번호 : <%= member.getPhone1() %>-<%= member.getPhone2() %>-<%= member.getPhone3() %></p>
        <p>생년월일 : <%= member.getBirthday() %></p>
    </form>

    <% if(emailCheck == 0) { %>
        <button id="email">인증번호 보내기</button>
        <span id="remainingTime"></span>
        <span id="loading" style="display: none;">이메일을 전송 중입니다...</span>
        <br>
    <% } %>

    <a href="updateMember">회원 정보 수정</a>

    <hr>

    <div>
        <div><p>Password Change, SignOut</p></div>
        <div>
            <a href="pwChange">비밀번호 변경</a>
            <a href="deleteMember">회원 탈퇴</a>
        </div>
    </div>
    <hr>
</body>
</html>
