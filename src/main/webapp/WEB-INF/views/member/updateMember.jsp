<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
        .form-container {
            max-width: 600px;
            margin: auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #f9f9f9;
        }
        h2 {
            text-align: center;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: inline-block;
            width: 120px;
            font-weight: bold;
        }
        .form-group input,
        .form-group select {
            width: calc(100% - 130px);
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .form-group input[disabled] {
            background-color: #e9ecef;
        }
        .form-group div {
            margin-top: 5px;
            font-size: 12px;
        }
        .form-group input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #28a745;
            border: none;
            color: white;
            font-size: 16px;
            cursor: pointer;
            border-radius: 4px;
        }
        .form-group input[type="submit"]:hover {
            background-color: #218838;
        }
        .error-message {
            color: red;
            text-align: center;
        }
        a {
            display: block;
            text-align: center;
            margin-bottom: 20px;
            color: #007bff;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        hr {
            margin: 20px 0;
        }
    </style>
    <script>var contextPath = '${pageContext.request.contextPath}';</script>
    <script src="${pageContext.request.contextPath}/resources/js/signUp.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/m_updateMember.js"></script>
</head>
<body>
<div class="form-container">
    <h2>회원 정보 수정</h2>
    <c:choose>
        <c:when test="${not empty user}">
            <a href="${pageContext.request.contextPath}">Home</a>
            <form method="POST" action="${pageContext.request.contextPath}/members/updateMember">
                <div class="form-group">
                    <label for="id">아이디 :</label>
                    <input type="text" value="${user.id}" placeholder="아이디" disabled/>
                    <input type="hidden" name="id" id="id" value="${user.id}"/>
                </div>
                <div class="form-group">
                    <label for="name">이름 :</label>
                    <input type="text" name="name" value="${user.name}" placeholder="이름" disabled/>
                </div>
                <div class="form-group">
                    <label for="emailId">Email :</label>
                    <c:set var="emailParts" value="${fn:split(user.email, '@')}"/>
                    <input type="text" id="emailId" name="emailId" placeholder="이메일" value="${emailParts[0]}"/>@
                    <input type="text" id="emailDomain" name="emailDomain" value="${emailParts[1]}" placeholder="sample.com" readonly/>
                    <select id="emailSelect" required="required" onchange="updateDomainInput(this)">
                        <option value="${emailParts[1]}" disabled selected>--</option>
                        <option value="custom">직접입력</option>
                        <option value="naver.com">naver.com</option>
                        <option value="gmail.com">gmail.com</option>
                        <option value="hanmail.net">hanmail.net</option>
                        <option value="daum.net">daum.net</option>
                        <option value="icloud.com">icloud.com</option>
                    </select>
                    <div id="message_email"></div>
                    <div id="message_email_warning"></div>
                </div>
                <div class="form-group">
                    <label for="region">지역 :</label>
                    <select id="region" name="region">
                        <option value="${member.region}" label="지역 선택하기" selected/>
                        <option value="서울" label="서울"/>
                        <option value="부산" label="부산"/>
                        <option value="대구" label="대구"/>
                        <option value="인천" label="인천"/>
                        <option value="광주" label="광주"/>
                        <option value="대전" label="대전"/>
                        <option value="울산" label="울산"/>
                        <option value="세종" label="세종"/>
                        <option value="경기" label="경기"/>
                        <option value="강원" label="강원"/>
                        <option value="충북" label="충북"/>
                        <option value="충남" label="충남"/>
                        <option value="전북" label="전북"/>
                        <option value="전남" label="전남"/>
                        <option value="경북" label="경북"/>
                        <option value="경남" label="경남"/>
                        <option value="제주" label="제주"/>
                    </select>
                </div>
                <div class="form-group">
                    <label for="phone1">전화번호 :</label>
                    <input type="text" name="phone1" value="${user.phone1}-${user.phone2}-${user.phone3}" id="phone" placeholder="전화번호"/>
                    <div id="message_phone"></div>
                </div>
                <div class="form-group">
                    <input type="submit" class="submit-button" value="회원 정보 수정">
                </div>
            </form>
        </c:when>
        <c:otherwise>
            <p>로그인이 필요합니다. <a href="${pageContext.request.contextPath}/members/signIn">로그인 페이지로 이동</a></p>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
