<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TripPlanner</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>let contextPath = '${pageContext.request.contextPath}';</script>
    <script src="${pageContext.request.contextPath}/resources/js/myPage.js"></script>
</head>
<body>
    <a href="${pageContext.request.contextPath}">Home</a>
    <hr>
    <form method="POST" action="updateMember">
        <p>아이디 : ${user.id}</p>
        <p>이름 : ${user.name}</p>
        <p>이메일 : ${user.email} 
            <c:if test="${user.emailCheck == 1}">
                <span style="color: green;">✔ 인증 완료</span>
            </c:if>
        </p>
        <p>지역 : ${user.region}</p>
        <p>전화번호 : ${user.phone1}-${user.phone2}-${user.phone3}</p>
        <p>생년월일 : ${user.birthday}</p>
    </form>

    <c:if test="${user.emailCheck == 0}">
        <button id="email">인증번호 보내기</button>
        <span id="remainingTime"></span>
        <span id="loading" style="display: none;">이메일을 전송 중입니다...</span>
        <br>
    </c:if>

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
