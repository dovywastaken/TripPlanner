<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>트립플래너</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 20px;
            background-color: #007bff;
            color: white;
            border-radius: 10px;
        }
        .logo {
            font-size: 1.5em;
            font-weight: bold;
            margin: 0;
        }
        .search-bar {
            flex: 1;
            text-align: right;
        }
        .search-input {
            padding: 10px;
            border: none;
            border-radius: 4px;
            width: 200px;
        }
        .main-content {
            margin-top: 20px;
        }
        .section {
            margin-bottom: 20px;
            padding: 20px;
            background-color: #f9f9f9;
            border: 1px solid #ccc;
            border-radius: 10px;
        }
        .section-title {
            font-size: 1.5em;
            margin-bottom: 10px;
        }
        .sub-title {
            font-size: 1.2em;
            margin-bottom: 20px;
        }
        .welcome-message {
            font-size: 1.2em;
            font-weight: bold;
            margin-bottom: 10px;
        }
        .nav-link {
            display: inline-block;
            margin: 5px;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .nav-link:hover {
            background-color: #0056b3;
        }
        .card-container {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
        }
        .card {
            flex: 1 1 calc(33.333% - 20px);
            box-sizing: border-box;
            padding: 20px;
            background-color: white;
            border: 1px solid #ccc;
            border-radius: 10px;
            text-align: center;
        }
        .btn {
            display: block;
            margin: 20px auto;
            padding: 10px 20px;
            background-color: #28a745;
            color: white;
            text-decoration: none;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #218838;
        }
        .footer {
            text-align: center;
            margin-top: 20px;
            padding: 20px;
            background-color: #007bff;
            color: white;
            border-radius: 10px;
        }
    </style>
</head>
<body>
    <header class="header">
        <h1 class="logo">트립플래너</h1>
        <div class="search-bar">
            <input type="text" placeholder="검색" class="search-input">
        </div>
    </header>

    <main class="main-content">
        <section class="section welcome-section">
            <c:choose>
                <c:when test="${not empty member}">
                    <p class="welcome-message">어서오세요, ${member.name}님!</p>
                    <a href="members/signOut" class="nav-link">로그아웃</a>
                    <a href="members/myPage" class="nav-link">마이 페이지</a>
                    <a href="admin/dashboard" class="nav-link">회원 목록</a>
                    <a href="/TripPlanner/Allboard" class="nav-link">게시판</a>
                    
                </c:when>
                <c:otherwise>
                    <p class="welcome-message">환영합니다! 로그인을 해주세요.</p>
                    <a href="members/signUp" class="nav-link">회원가입</a>
                    <a href="admin/dashboard" class="nav-link">회원 목록</a>
                    <a href="/TripPlanner/Allboard" class="nav-link">게시판</a>
                    <a href="landmark/list" class="nav-link">관광지</a>
                </c:otherwise>
            </c:choose>
        </section>

        <c:if test="${empty member}">
            <section class="section intro-section">
                <h2 class="section-title">트립플래너에 오신 것을 환영합니다!</h2>
                <p class="sub-title">로그인 후 여행 계획을 세워보세요!</p>
                <a href="members/signIn" class="nav-link">로그인하기</a>
            </section>
        </c:if>

        <c:if test="${not empty member}">
            <section class="section my-posts">
                <h2 class="section-title">${member.name}님, 떠나보실까요?</h2>
                <p class="sub-title">여행 포스트 4</p>
                <div class="card-container">
                    <div class="card">내 포스트 1</div>
                    <div class="card">내 포스트 2</div>
                    <div class="card">내 포스트 3</div>
                </div>
                <a href="/TripPlanner/postform" class="nav-link">작성</a>
            </section>
        </c:if>

        <section class="section popular-plans">
            <h2 class="section-title">인기 여행 계획</h2>
            <div class="card-container">
                <div class="card">경주 불국사</div>
                <div class="card">추가 여행 계획</div>
                <div class="card">다른 여행 계획</div>
            </div>
        </section>

        <section class="section festivals">
            <h2 class="section-title">축제들</h2>
            <div class="card-container">
                <div class="card">김밥축제</div>
                <div class="card">다른 축제</div>
            </div>
        </section>

        <section class="section landmarks">
            <h2 class="section-title">관광지</h2>
            <div class="card-container">
                <div class="card">경주 대릉원</div>
                <div class="card">추가 관광지</div>
            </div>
        </section>
    </main>

    <footer class="footer">
        <p>© 2024 TripPlanner. All rights reserved.</p>
    </footer>
</body>
</html>

