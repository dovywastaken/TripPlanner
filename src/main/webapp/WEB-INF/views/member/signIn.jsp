<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources//css/normalize.css">
    <link href="https://cdn.jsdelivr.net/gh/sun-typeface/SUITE@2/fonts/static/woff2/SUITE.css" rel="stylesheet">
    <title>TripPlanner - 로그인</title>
    <style>
        body
        {
            background-image: url("${pageContext.request.contextPath}/resources/img/Wallpaper.png");
        }


        #form
        {
            /* outline: 1px solid magenta; */
            width: 100%;
        }

        .form-group {
            margin-bottom: 15px;
            width: 100%;
            /* outline: 1px solid red; */
        }

        .form-group label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
            margin-left: 15px;
        }

        .form-group input[type="text"],
        .form-group input[type="password"] {
            width: 100%;
            height: 47px;
            padding: 21px;
            /* border: 1px solid #ccc; */
            border: none;
            background-color: #F1F3F9;
            border-radius: 35px;
            box-sizing: border-box;
        }

        .form-group input[type="submit"] 
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

        .form-group input[type="submit"]:hover 
        {
            cursor:pointer;
            background: rgb(103,255,176);
            background: linear-gradient(0deg, rgba(103,255,176,1) 0%, rgba(0,255,164,1) 5%, rgba(0,255,164,1) 12%, rgba(145,255,198,1) 91%, rgba(130,255,190,1) 100%);
            box-shadow: 0px 0px 15px 3px rgba(133,255,188,0.6);
            transition: 300ms;
        }


        #signInContainer
        {
            display: flex;
            padding: 0 300px;
            margin: 89px 0;
        }

        #signInLeft
        {
            width: 38%;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            background-color: white;
            border: 1px solid transparent;
            border-radius: 35px 0px 0px 35px;
            box-shadow: 0px 7px 30px rgba(33,55,55,0.1);
        }


        #signInLeft h2 {
            font-size: 34px;
            line-height: 55px;
            text-align: center;
        }


        #signInRight
        {
            width: 62%;
            height: 610px;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            background-color: white;
            border: 1px solid transparent;
            border-radius: 0px 35px 35px 0px;
            box-shadow: 0px 7px 30px rgba(33,55,55,0.1);
            /* outline: 1px solid magenta; */

        }


        .wrapper
        {
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
            width: 50%;
            height: 377px;
            /* outline: 1px solid cyan; */
        }

        #formLogo
        {
            width: 60%;
            height: auto;
            /* outline: 1px solid magenta; */
        }


        .wrapper p
        {
            margin-bottom: 55px;
            /* outline: 1px solid magenta; */
        }
    </style>
</head>

<body>
	<%@ include file="../header.jsp" %>

    <div id="signInContainer"> 
        <div id="signInLeft">
            <div class="wrapper">
                <h2>어서오세요!</h2>
                <p>글쓰기, 추천, 댓글 작성이 가능해져요!</p>
                <!-- 로그인 폼 -->
                <form:form modelAttribute="member" id="form" method="POST" action="signIn">
                <%--  <form method="post" id="form">  --%>
                    <div class="form-group">
                        <label for="id">이메일</label>
                       <!--  <input type="text" placeholder="email@domain.com"> -->
                        <form:input path="email" id="id" placeholder="email@domain.com"/>
                    </div>
                    <div class="form-group">
                        <label for="pw">비밀번호</label>
                        <!-- <input type="password" placeholder="비밀번호"> -->
                        <form:input path="pw" id="pw" placeholder="비밀번호" type="password"/>
                    </div>
                    <div class="form-group">
                        <input type="submit" id="submitButton" value="로그인">
                    </div>
                </form:form>

                <a href="${pageContext.request.contextPath}/members/signUp">회원가입하기</a>

                <!-- 에러 메시지 표시 -->
                <c:if test="${not empty EmptyForm}">
                    <script>
                        alert("${EmptyForm}");
                    </script>
                </c:if>
                <c:if test="${not empty loginError}">
                    <script>
                        alert("${loginError}");
                    </script>
                </c:if>
            </div>
        </div>

        <div id="signInRight">
            <img src="${pageContext.request.contextPath}/resources/img/logo.png" id="formLogo"></img>
        </div>
    </div>
    <%@ include file="../footerCompact.jsp" %>
</body>
</html>
