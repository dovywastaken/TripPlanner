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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources//css/normalize.css">
<link href="https://cdn.jsdelivr.net/gh/sun-typeface/SUITE@2/fonts/static/woff2/SUITE.css" rel="stylesheet">
<style>
    body
    {
        background-image: url("${pageContext.request.contextPath}/resources/img/Wallpaper.png");
    }

    .form-container 
    {
        max-width: 600px;
        margin: 89px auto;
        padding: 80px;
        background-color: white;
        border-radius: 35px;
        box-shadow: 0px 7px 30px rgba(33,55,55,0.1);
        /* outline: 1px solid magenta; */
    }
    .form-container h2 
    {
        font-size: 34px;
        line-height: 55px;
        text-align: center;
    }

    .form-group 
    {
        margin-bottom: 20px;
    }

   



    #submit
    {
        background: rgb(103,255,176);
        background: linear-gradient(0deg, rgba(103,255,176,1) 0%, rgba(12,245,151,1) 5%, rgba(0,238,153,1) 12%, rgba(103,255,176,1) 91%, rgba(0,255,164,1) 100%);
        box-shadow: 0px 1px 2px rgba(33,55,55,0.2);
        padding: 8px;
        height: 47px;
        border: none;
        border-radius: 13px;
        width: 100%;
        border-radius: 35px;
        text-align: center;
        line-height: 0;
        display: flex;
        align-items: center;
        justify-content: center;
        margin-top: 40px;
        font-size: 18px;
        color: #2C3F3C;
        text-decoration: none;
        transition: 300ms;
    }

    #submit:hover
    {
        cursor:pointer;
        background: rgb(103,255,176);
        background: linear-gradient(0deg, rgba(103,255,176,1) 0%, rgba(0,255,164,1) 5%, rgba(0,255,164,1) 12%, rgba(145,255,198,1) 91%, rgba(130,255,190,1) 100%);
        box-shadow: 0px 0px 15px 3px rgba(133,255,188,0.6);
        transition: 300ms;
    }

    .textBox
    {
        width: 100%;
        height: 47px;
        padding: 21px;
        /* border: 1px solid #ccc; */
        border: none;
        background-color: #F1F3F9;
        border-radius: 35px;
        box-sizing: border-box;
    }

    .formName
    {
        display: flex;
        justify-content: start;
        align-items: center;
        flex-direction: row;
        font-weight: bold;
        margin-left: 15px;
        margin-bottom: 5px;
    }


    .checkButton
    {
        cursor:pointer;
        border: none;
        border-radius: 35px;
        font-size: 14px;
        height: 21px;
        line-height: 0;
        margin-left: 15px;
    }

    #buttonDisabled
    {
        background-color: #e1e3e9;
        color: #999;
    }

    #buttonActive
    {
        background-color: #00deff;
        color: black;
    }

    #buttonConfirmed
    {
        background: rgb(103,255,176);
        background: linear-gradient(0deg, rgba(103,255,176,1) 0%, rgba(0,255,164,1) 5%, rgba(0,255,164,1) 12%, rgba(145,255,198,1) 91%, rgba(130,255,190,1) 100%);
        box-shadow: 0px 0px 15px 3px rgba(133,255,188,0.6);
    }

    #region
    {
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: row;
        width: 144px;
        height: 47px;
        padding: 0 7px;
        border-radius: 35px;
        margin : 0 5px 0 5px;
        background-color: white;
    }

    /* 이거는 중복검사 통과했을 때만 */
    /* #buttonDisabled:hover
    {
        color: #2C3F3C;
        background: rgb(103,255,176);
        background: linear-gradient(0deg, rgba(103,255,176,1) 0%, rgba(0,255,164,1) 5%, rgba(0,255,164,1) 12%, rgba(145,255,198,1) 91%, rgba(130,255,190,1) 100%);
        box-shadow: 0px 0px 15px 3px rgba(133,255,188,0.6);
    } */
</style>
</head>
<body>
<%@ include file="../header.jsp" %>

    <div class="form-container">
	    <h2>회원가입</h2>
	
	    <form:form modelAttribute="member" method="POST" action="${pageContext.request.contextPath}/members/signUp" id="signUp_form" onsubmit="combineEmail()">
	        <div class="form-group"> 
	            <div class="formName"> 
	                <label for="id" class="formLabel">이메일</label>
	                <input type="button" id="buttonDisabled" class="checkButton" value="중복체크">
	                <input type="button" id="buttonActive" class="checkButton" value="중복체크" style="display:none">
	                <input type="button" id="buttonConfirmed" class="checkButton" value="체크완료" style="display:none">
	            </div>
	            <form:input path="id" id="id" class="textBox" placeholder="이메일" onchange="IDValidator()" />
	            <div id="message_id"></div> 
	            <form:errors path="id" class="error-color"/>
	        </div>
	
	        <div class="form-group">
	            <label for="pw1" class="formName">비밀번호</label>
	            <form:input id="pw1" class="textBox" name="pw1" path="pw" placeholder="비밀번호" type="password"/>
	            <input type="hidden" id="memPassword" name="memPassword" value=""/>
	            <div id="pwValidationMessage"></div> <!-- 유효성 검사 메시지 -->
	        </div>
	
	        <div class="form-group">
	            <label for="pw2" class="formName">비밀번호 확인</label>
	            <input id="pw2" placeholder="비밀번호 확인" class="textBox" type="password"/>
	            <div id="pwCheck"></div> <!-- 비밀번호 확인시 메시지 -->
	        </div>
	
	        <hr style="border: 1px solid #F1F3F9; margin : 34px auto 10px auto; width: 30%;">
	
	        <div class="form-group">
	            <label for="name" class="formName">닉네임</label>
	            <form:input path="nickname" id="name" class="textBox" onchange="NameValidator()" placeholder="이름"/>
	            <div id="message_name"></div>
	        </div>
	
	        <div class="form-group">
	            <label for="phone1" class="formName">전화번호</label>
	            <form:input path="phone1" id="phone1" class="textBox" placeholder="010-1234-5678"/>
	            <div id="message_phone"></div>
	        </div>
	
	        <div class="form-group">
	            <label for="birthday" class="formName">생년월일</label>
	            <form:input path="birthday" id="birthday" class="textBox" placeholder="생년월일 8자리" type="text"/>
	            <div id="message_birthday"></div>
	        </div>
	        <input type="submit" id="submit" value="회원가입"/>
	        <div id="resultMessage"></div>
	    </form:form>
	</div>
    <%@ include file="../footerCompact.jsp" %>
</body>
</html>
