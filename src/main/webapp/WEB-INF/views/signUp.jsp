<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TripPlanner</title>
<script>var contextPath = '${pageContext.request.contextPath}';</script>
<script src="${pageContext.request.contextPath}/resources/js/signUp.js"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<link href="/TripPlanner/resources/css/signUp.css" rel="stylesheet">
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
    .form-container h2 {
        text-align: center;
    }
    .form-group {
        margin-bottom: 15px;
    }
    .form-group label {
        display: inline-block;
        width: 150px;
        font-weight: bold;
    }
    .form-group input, .form-group select {
        width: calc(100% - 160px);
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }
    .form-group .checkSome {
        width: auto;
        margin-left: 5px;
        padding: 6px 12px;
        background-color: #007bff;
        border: none;
        color: white;
        cursor: pointer;
        border-radius: 4px;
    }
    .form-group .checkSome:hover {
        background-color: #0056b3;
    }
    .form-group .error-color {
        color: red;
    }
    .form-group .gender-label {
        width: auto;
    }
    .form-group .gender-input {
        width: auto;
        margin-left: 10px;
    }
    .form-group .submit-button {
        width: 100%;
        padding: 10px;
        background-color: #28a745;
        border: none;
        color: white;
        font-size: 16px;
        cursor: pointer;
        border-radius: 4px;
    }
    .form-group .submit-button:hover {
        background-color: #218838;
    }
</style>
</head>
<body>
    <div class="form-container">
        <h2>회원가입</h2>
        <a href="${pageContext.request.contextPath}">Home</a>

        <form:form modelAttribute="member" method="POST" action="${pageContext.request.contextPath}/members/signUp" id="signUp_form" onsubmit="combineEmail()">
            <div class="form-group"> <label for="id">아이디 : </label> 
            <form:input path="id" id="id" placeholder="아이디" required="required" title="알파벳 소문자 1개 이상, 숫자 1개 이상을 포함한 최소 2자, 최대 15자"/>
            <input type="button" id="confirmId" class="checkSome" value="중복체크"> 
            <div id="message_id"></div> 
            <form:errors path="id" class="error-color"/> </div>

            <div class="form-group">
                <label for="name">이름 : </label>
                <form:input path="name" id="name" placeholder="이름" required="required" min="5"/>
            </div>
            
            <div class="form-group">
                <label for="pw">비밀번호 : </label>
                <form:input path="pw" id="pw" placeholder="비밀번호" required="required" type="password"/>
            </div>
            
            <div class="form-group">
                <label for="pwck">비밀번호 확인 : </label>
                <form:input path="pw" id="pwck" placeholder="비밀번호 확인" required="required" type="password"/>
            </div>
            
            <div class="form-group">
                <label for="emailId">Email : </label>
                <input type="text" id="emailId" placeholder="이메일" required="required"/>@
                <input type="text" id="emailDomain" placeholder="sample.com" readonly="readonly"/>
                <select id="emailSelect" required="required" onchange="updateDomainInput(this)">
                    <option value="custom">직접입력</option>
                    <option value="naver.com">naver.com</option>
                    <option value="gmail.com">gmail.com</option>
                    <option value="hanmail.net">hanmail.net</option>
                    <option value="daum.net">daum.net</option>
                    <option value="icloud.com">icloud.com</option>
                </select>         
            </div>
            
            <form:input type="hidden" id="email" path="email"/>

            <div class="form-group">
                <label for="region">지역 : </label>
                <form:select path="region" id="region" required="required">
                    <form:option value="" label="지역 선택"/>
                    <form:option value="서울" label="서울"/>
                    <form:option value="부산" label="부산"/>
                    <form:option value="대구" label="대구"/>
                    <form:option value="인천" label="인천"/>
                    <form:option value="광주" label="광주"/>
                    <form:option value="대전" label="대전"/>
                    <form:option value="울산" label="울산"/>
                    <form:option value="세종" label="세종"/>
                    <form:option value="경기" label="경기"/>
                    <form:option value="강원" label="강원"/>
                    <form:option value="충북" label="충북"/>
                    <form:option value="충남" label="충남"/>
                    <form:option value="전북" label="전북"/>
                    <form:option value="전남" label="전남"/>
                    <form:option value="경북" label="경북"/>
                    <form:option value="경남" label="경남"/>
                    <form:option value="제주" label="제주"/>
                </form:select>
            </div>
            
            <div class="form-group">
                <label>성별 : </label>
                <label class="gender-label" for="male">남</label>
                <form:radiobutton path="sex" value="남" id="male" required="required" class="gender-input"/>
                <label class="gender-label" for="female">여</label>
                <form:radiobutton path="sex" value="여" id="female" required="required" class="gender-input"/>
            </div>
            
            <div class="form-group">
                <label for="phone1">전화번호 : </label>
                <form:input path="phone1" id="phone1" placeholder="전화번호"/>
                <!-- <form:input path="phone2" placeholder="전화번호(중간자리)"/>-->
                <!--<form:input path="phone3" placeholder="전화번호(끝자리)"/>-->
            </div>
            
            <div class="form-group">
                <label for="birthday">생년월일 : </label>
                <form:input path="birthday" id="birthday" placeholder="생년월일" type="date"/>
            </div>
            
            <div class="form-group">
                <input type="submit" class="submit-button" value="회원가입"/>
            </div>
        </form:form>
    </div>
</body>
</html>
