<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>트립플래너</title>
    <link rel="stylesheet" href="/TripPlanner/resources/css/normalize.css">
    <link rel="stylesheet" href="/TripPlanner/resources/css/header.css">
    <link href="https://cdn.jsdelivr.net/gh/sun-typeface/SUITE@2/fonts/static/woff2/SUITE.css" rel="stylesheet">
</head>

<header>
    <div id="headerContainer">
        <div id="headerLeft">
		    <a href="${pageContext.request.contextPath}" id="logo" class="aTag">
		        <img src="/TripPlanner/resources/img/logo.png" id="logoImg">
		    </a>
		    <a href="/TripPlanner/postform" class="headerLeft">글쓰기</a>
		    <a href="/TripPlanner/hotPlanners" class="headerLeft">인기 여행 계획</a>
		</div>
        <div id="headerRight">
	        <c:if test="${empty user}">
	            <a href="/TripPlanner/members/signIn" id="login" class="headerRight">로그인</a>
	        </c:if>
	        <c:if test="${not empty user}">
		        <a href="/TripPlanner/admin/dashboard" class="headerRight">관리자 페이지</a>
		        <a href="/TripPlanner/members/myPage" class="headerRight">내 정보</a>
	            <a href="/TripPlanner/members/signOut" id="logout" class="headerRight">로그아웃</a>
	            <p> ${user.name}님</p>
	        </c:if>
        </div>
    </div>
</header>