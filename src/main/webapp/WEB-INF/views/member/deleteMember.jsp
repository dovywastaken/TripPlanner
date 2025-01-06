<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TripPlanner</title>
    <link rel="stylesheet" href="/TripPlanner/resources//css/normalize.css">
    <link href="https://cdn.jsdelivr.net/gh/sun-typeface/SUITE@2/fonts/static/woff2/SUITE.css" rel="stylesheet">
</head>

<style>
    body {
        background-image: url("/TripPlanner/resources/img/Wallpaper.png");
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        padding : 0 300px;
        margin: 0;
    }

    .form-container {
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
        position: relative;
    }

    .home-link {
        /* 홈 링크 스타일 */
        display: block;
        margin-bottom: 20px;
        color: #67FFB0;
        text-decoration: none;
        font-weight: bold;
    }

    #headText {
        /* 제목 스타일 */
        color: #333;
    }


    .goodbyeMessage
    {
        font-size: 18px;
        line-height: 1.6;
        text-align: center;
        color: #555;
    }

    .goodbyeMessage:first-child
    {
        margin-top: 20px;
    }

    .goodbyeMessage:last-child
    {
        margin-bottom: 20px;
    }

    .form-group {
        /* 폼 그룹 스타일 */
        width: 90%;
        margin-bottom: 15px;
    }

    .form-group input[type="submit"] {
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

    .form-group input[type="submit"]:hover {
        background: linear-gradient(0deg, rgba(103, 255, 176, 1) 0%, rgba(0, 255, 164, 1) 5%, rgba(0, 255, 164, 1) 12%, rgba(145, 255, 198, 1) 91%, rgba(130, 255, 190, 1) 100%);
        box-shadow: 0px 0px 15px 3px rgba(133, 255, 188, 0.6);
    }


    #backButton {
            position: absolute;
            top: 20px;
            left: 20px;
            z-index: 999;
            transition: top 0.2s ease-out; /* 위치 변경 시 부드러운 애니메이션 */
            background-color: #007bff; 
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
            background-color: #0056b3; 
            box-shadow: 0 6px 8px rgba(0, 0, 0, 0.15); 
            transform: translateY(-2px); 
        }

        #backButton:active {
            background-color: #003f7f; 
            box-shadow: 0 3px 4px rgba(0, 0, 0, 0.1); 
            transform: translateY(0); 
        }
</style>

<body>

	<%@ include file="../header.jsp" %>

    <div class="form-container">
        <div id="backButton" onclick="goBack()">뒤로 가기</div>
        <!-- 회원 이름을 표시하는 부분 -->
        <h2 id="headText">${member.name}님! 잠깐만요!</h2>
        
        <c:choose>
            <c:when test="${not empty userPosts}">
                <!-- 글이 있을 경우 -->
                <p class="goodbyeMessage">지금까지 ${post.size()} 개의 여행 계획을 저희와 함께 했습니다 </p>
                <p class="goodbyeMessage">회원 탈퇴시 해당 글 삭제 및 수정이 불가능 해집니다.</p>
            </c:when>
            <c:otherwise>
                <!-- 글이 없을 경우 -->
                <p class="goodbyeMessage">작성한 여행 계획이 없습니다. 바로 회원 탈퇴가 가능합니다.</p>
            </c:otherwise>
        </c:choose>

        <form method="POST" action="${pageContext.request.contextPath}/members/deleteMember">
            <div class="form-group">
                <input type="submit" id="deleteMember" value="회원 탈퇴" class="submit-button"/>
            </div>
        </form>
    </div>
    <%@ include file="../footer.jsp" %>
</body>

<script>function goBack(){window.history.back();}</script>

</html>
