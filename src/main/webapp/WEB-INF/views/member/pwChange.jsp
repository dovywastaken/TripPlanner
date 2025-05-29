<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
    <title>비밀번호 변경</title>
    <script>var contextPath = '${pageContext.request.contextPath}';</script>
    <script src="${pageContext.request.contextPath}/resources/js/pwCheck.js"></script>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
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
	            position: relative;
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
	
	        #submit, #pwChange
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
	
	        #submit:hover, #pwChange:hover
	        {
	            cursor: pointer;
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
	            border: none;
	            background-color: #F1F3F9;
	            border-radius: 35px;
	            box-sizing: border-box;
	        }
	
	        #pw1
	        {
	            width: 100%;
	            height: 47px;
	            padding: 21px;
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
	
	
	        #backButton {
	            position: absolute;
	            top: 20px;
	            left: 20px;
	            z-index: 999;
	            transition: top 0.2s ease-out; /* 위치 변경 시 부드러운 애니메이션 */
	            background-color: #313339; 
	            color: white;
	            padding: 10px 20px; 
	            border-radius: 34px; 
	            font-size: 14px;
	            font-weight: bold; 
	            text-align: center; 
	            width: 95px;
	            cursor: pointer; 
	            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); 
	            transition: all 0.3s ease; 
	        }
	
	        #backButton:hover {
	            background-color: #616369;
	            box-shadow: 0 6px 8px rgba(0, 0, 0, 0.15); 
	            transform: translateY(-2px); 
	        }
	
	        #backButton:active {
	            background-color: #003f7f;
	            box-shadow: 0 3px 4px rgba(0, 0, 0, 0.1); 
	            transform: translateY(0); 
	        }
	</style>

</head>
<body>




	<%@ include file="../header.jsp" %>

    <div class="form-container">
        <div id="backButton" onclick="goBack()">뒤로 가기</div>
        <h2>비밀번호 변경</h2>
        <form:form id="pwChangeForm" method="post" modelAttribute="member" action="pwChange">
            <div class="form-group">
                <label for="currentPw" class="formName">현재 비밀번호</label>
                <input type="password" id="pw" name="cpw" class="textBox" placeholder="기존 비밀번호" required />
                <div id="currentPwMessage"></div>
            </div>
            <div class="form-group">
                <label for="pw1" class="formName">새 비밀번호</label>
                <form:input id="pw1" name="pw1" path="pw" placeholder="비밀번호" type="password" class="textBox" />
                <div id="pwValidationMessage"></div>
            </div>
            <div class="form-group">
                <label for="pw2" class="formName">비밀번호 확인</label>
                <input id="pw2" placeholder="비밀번호 확인" type="password" class="textBox" />
                <div id="pwCheck"></div>
            </div>
            <div class="form-group">
                <input type="submit" id="pwChange" value="변경" class="submit-button" />
            </div>
        </form:form>
    </div>
    <%@ include file="../footerCompact.jsp" %>
</body>

<script>function goBack() {window.history.back();}</script>
</html>
