<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>트립플래너</title>
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mainPage.css">
</head>
<body>
<div class="container-fluid">
	<%@ include file="/WEB-INF/views/header.jsp"%>
		
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
    </main>
	<%@ include file="/WEB-INF/views/footer.jsp"%>
</div>


</body>
</html>

