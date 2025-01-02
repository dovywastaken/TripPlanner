<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TripPlanner</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .container {
            max-width: 800px;
            margin: auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #f9f9f9;
        }
        h2 {
            text-align: center;
        }
        a, p {
            margin: 10px 0;
            display: block;
        }
        p span {
            margin-left: 10px;
        }
        form p {
            margin: 15px 0;
        }
        .button-container {
            text-align: center;
        }
        .button-container button,
        .button-container a {
            margin: 5px;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .button-container button:hover,
        .button-container a:hover {
            background-color: #0056b3;
        }
        .green-text {
            color: green;
        }
        .email-verification {
            display: flex;
            justify-content: center;
            align-items: center;
            margin: 10px 0;
        }
        .email-verification span {
            margin-left: 10px;
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>let contextPath = '${pageContext.request.contextPath}';</script>
    <script src="${pageContext.request.contextPath}/resources/js/myPage.js"></script>
</head>
<body>

	<%@ include file="../header.jsp" %>

    <div class="container">
        <a href="${pageContext.request.contextPath}">Home</a>
        <hr>
        <h2>내 정보</h2>
        <form method="POST" action="updateMember">
            <p>아이디: ${user.id}
            	<c:if test="${user.emailCheck == 1}">
                    <span class="green-text">✔ 인증 완료</span>
                </c:if>
            </p>
            <p>이름: ${user.name}</p>
            <p>지역: ${user.region}</p>
            <p>전화번호: ${user.phone1}-${user.phone2}-${user.phone3}</p>
            <p>생년월일: ${user.birthday}</p>
        </form>

        <c:if test="${user.emailCheck == 0}">
            <div class="email-verification">
                <button id="email">인증번호 보내기</button>
                <span id="remainingTime"></span>
                <span id="loading" style="display: none;">이메일을 전송 중입니다...</span>
            </div>
        </c:if>

        <a href="updateMember">회원 정보 수정</a>
        <hr>
        <div>
            <p>Password Change, SignOut</p>
            <div class="button-container">
                <a href="pwChange">비밀번호 변경</a>
                <a href="deleteMember">회원 탈퇴</a>
            </div>
        </div>
    </div>
    <%@ include file="../footer.jsp" %>
</body>
</html>
