<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TripPlanner</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources//css/normalize.css">
    <link href="https://cdn.jsdelivr.net/gh/sun-typeface/SUITE@2/fonts/static/woff2/SUITE.css" rel="stylesheet">
    
    <style>
        body {
        background-image: url("${pageContext.request.contextPath}/resources/img/Wallpaper.png");
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        padding : 0 300px;
        margin: 0;
        /* outline: 1px solid magenta; */
    }

    .container {
        background-color: #fff;
        border-radius: 35px;
        box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.2);
        padding: 0 100px;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        height: 410px;
        margin : 160px 0;
        /* outline: 1px solid magenta; */
    }

    .container h1 {
        color: #333;
        margin-bottom: 20px;
    }

    .container p {
        font-size: 18px;
        line-height: 1.6;
        text-align: center;
        color: #555;
    }

    .button {
        background: linear-gradient(0deg, rgba(103, 255, 176, 1) 0%, rgba(12, 245, 151, 1) 5%, rgba(0, 238, 153, 1) 12%, rgba(103, 255, 176, 1) 91%, rgba(0, 255, 164, 1) 100%);
        box-shadow: 0px 1px 2px rgba(33, 55, 55, 0.2);
        padding: 10px 20px;
        border: none;
        border-radius: 35px;
        font-size: 18px;
        color: #2C3F3C;
        text-decoration: none;
        transition: 300ms;
        cursor: pointer;
        margin-top: 34px;
    }

    .button:hover {
        background: linear-gradient(0deg, rgba(103, 255, 176, 1) 0%, rgba(0, 255, 164, 1) 5%, rgba(0, 255, 164, 1) 12%, rgba(145, 255, 198, 1) 91%, rgba(130, 255, 190, 1) 100%);
        box-shadow: 0px 0px 15px 3px rgba(133, 255, 188, 0.6);
    }
    </style>
</head>
<body>
	<%@ include file="../header.jsp" %>
    <div class="container">
        <h1>회원 탈퇴가 완료되었습니다.</h1>
        <p>그동안 함께 해주셔서 감사합니다.<br>언제든 다시 돌아오세요!</p>
        <a href="${pageContext.request.contextPath}" class="button">홈으로 돌아가기</a>
    </div>
    <%@ include file="../footerCompact.jsp" %>
</body>
</html>
