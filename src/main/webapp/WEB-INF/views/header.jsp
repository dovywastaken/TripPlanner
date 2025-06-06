<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>트립플래너</title>
    <script>
         window.contextPath = '${pageContext.request.contextPath}';
    </script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css?v=250606">
    <link href="https://cdn.jsdelivr.net/gh/sun-typeface/SUITE@2/fonts/static/woff2/SUITE.css" rel="stylesheet">
</head>

<header>
    <div id="headerContainer">
		<a href="/" id="logo" class="aTag"> <!-- 배포시에는 / 평소에는 ${pageContext.request.contextPath} -->
			<img src="${pageContext.request.contextPath}/resources/img/logo.png" id="logoImg">
		</a>
        <div id="headerLeft">
		    <c:if test="${not empty user}">
				<a href="${pageContext.request.contextPath}/board/myBoard" class="headerLeft">내 여행 계획</a>
		    </c:if>
		    <a href="${pageContext.request.contextPath}/board/all" class="headerLeft">전체 게시판</a>
		    <a href="${pageContext.request.contextPath}/board/hot" class="headerLeft">추천 여행 계획</a>
		    <c:if test="${user.email == 'admin'}">
        		<a href="${pageContext.request.contextPath}/admin/dashboard" class="headerRight">회원 조회</a>
        	</c:if>
		</div>
    </div>
</header>