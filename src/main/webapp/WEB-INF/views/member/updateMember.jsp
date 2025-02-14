<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TripPlanner</title>
    <script>var contextPath = '${pageContext.request.contextPath}';</script>
    <script src="${pageContext.request.contextPath}/resources/js/signUp.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/m_updateMember.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/normalize.css">
    <link href="https://cdn.jsdelivr.net/gh/sun-typeface/SUITE@2/fonts/static/woff2/SUITE.css" rel="stylesheet">
    <style>
        body {
            background-image: url("${pageContext.request.contextPath}/resources/img/Wallpaper.png");
        }

        .form-container {
            max-width: 600px;
            margin: 89px auto;
            padding: 80px;
            background-color: white;
            border-radius: 35px;
            box-shadow: 0px 7px 30px rgba(33, 55, 55, 0.1);
            position: relative;
        }

        .form-container h2 {
            font-size: 34px;
            line-height: 55px;
            text-align: center;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .textBox {
            width: 100%;
            height: 47px;
            padding: 21px;
            border: none;
            background-color: #F1F3F9;
            border-radius: 35px;
            box-sizing: border-box;
        }

        .formName {
            display: flex;
            justify-content: start;
            align-items: center;
            flex-direction: row;
            font-weight: bold;
            margin-left: 15px;
            margin-bottom: 5px;
        }

        #submit {
            background: rgb(103, 255, 176);
            background: linear-gradient(0deg, rgba(103, 255, 176, 1) 0%, rgba(12, 245, 151, 1) 5%, rgba(0, 238, 153, 1) 12%, rgba(103, 255, 176, 1) 91%, rgba(0, 255, 164, 1) 100%);
            box-shadow: 0px 1px 2px rgba(33, 55, 55, 0.2);
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

        #submit:hover {
            cursor: pointer;
            background: linear-gradient(0deg, rgba(103, 255, 176, 1) 0%, rgba(0, 255, 164, 1) 5%, rgba(0, 255, 164, 1) 12%, rgba(145, 255, 198, 1) 91%, rgba(130, 255, 190, 1) 100%);
            box-shadow: 0px 0px 15px 3px rgba(133, 255, 188, 0.6);
            transition: 300ms;
        }

        .home-link {
            display: block;
            text-align: center;
            margin-bottom: 20px;
            text-decoration: none;
            color: #00deff;
            font-weight: bold;
            font-size: 16px;
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
        <div id="backButton" onclick='goBack()'>뒤로 가기</div>
        <h2>회원 정보 수정</h2>
        <c:choose>
            <c:when test="${not empty user}">
                <form method="POST" action="${pageContext.request.contextPath}/members/updateMember">
                    <div class="form-group">
                        <label for="id" class="formName">아이디</label>
                        <input type="text" value="${user.id}" placeholder="아이디" class="textBox" disabled />
                        <input type="hidden" name="id" id="id" value="${user.id}" />
                    </div>
                    <div class="form-group">
                        <label for="name" class="formName">닉네임</label>
                        <input type="text" name="name" value="${user.name}" id="name" placeholder="닉네임" class="textBox" />
                        <div id="message_name"></div>
                    </div>
                    
                    <div class="form-group">
                        <label for="phone1" class="formName">전화번호</label>
                        <input type="text" name="phone1" value="${user.phone1}-${user.phone2}-${user.phone3}" id="phone" placeholder="전화번호" class="textBox" />
                        <div id="message_phone"></div>
                    </div>
                    <div class="form-group">
                        <input type="submit" id="submit" value="회원 정보 수정" />
                    </div>
                </form>
            </c:when>
        </c:choose>
    </div>
	<%@ include file="../footer.jsp" %>
</body>
<script>function goBack(){window.history.back();}</script>
</html>
