<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>트립플래너</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/normalize.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/boardTours.css">
<link href="https://cdn.jsdelivr.net/gh/sun-typeface/SUITE@2/fonts/static/woff2/SUITE.css" rel="stylesheet">
</head>

<body>
<%@ include file="../header.jsp" %>

<div class="container">
	<h1>추천 음식점 게시판입니다.</h1>
	<div class="section">
        <div id="planner">
			<c:choose>
				<c:when test="${not empty restaurants}">
					<c:forEach var="restaurants" items="${restaurants}">
			            <div class="recommandCard">
			            	<div class="imgFrame">
				                <a href="${pageContext.request.contextPath}/board/detailedInfo?contentTypeId=${restaurants.contenttypeid}&contentId=${restaurants.contentid}"">
				                    <img src="${restaurants.firstimage}" class="pImg">
				                </a>
				            </div>
			                <div class="plannerCol" id="pText">
			                    <a href="${pageContext.request.contextPath}/board/detailedInfo?contentTypeId=${restaurants.contenttypeid}&contentId=${restaurants.contentid}" class="plannerTitle">${restaurants.title}</a>
			                    <p class="hashtag">#${restaurants.addr1}</p>
			                </div>
			            </div>
		           	</c:forEach>
		        </c:when>
		        <c:otherwise>
		        	<div></div>
		        </c:otherwise>
			</c:choose>
        </div>
    </div>
</div>

	<aside>
        <div class="sidePanelContainer">
            <div id="myPanel">
                    <!-- 로그인한 사용자가 있을 때 보여줄 내용 -->
                    <div id="userInfo">
                        <h1>${user.nickname}</h1>
                        <h2>${user.email}</h2>
                    </div>
                    <p id="currentDate" style="text-align: center; width: 100%; color: #2C3F3C;"></p>
					<c:if test="${user.emailCheck == 0}">
                    <p style="text-align : center; margin-top : 12px; width: 100%;">아직 이메일 인증이 안됐어요!</p>
                    	<a href="${pageContext.request.contextPath}/members/myPage" 
                    		style="outline: 2px solid #313339; 
					        background-color: #ffffff;
					        line-height: 0;
					        height: 21px;
					        width: 50%;
					        margin : 15px auto 0;
					        border: none;
					        border-radius: 34px;
					        font-size: 13px;
					        font-weight: bold;
					        display: flex;
					        justify-content: center;
					        align-items: center;
					        color: #313339;
					        text-align: center;">인증하러 가기
					    </a>
                    </c:if>
                    <hr style="border: 1px solid #F1F3F9; margin : 34px auto 10px auto; width: 80%;">
    
                    <div id="links"> 
                    	<c:if test="${not empty user}">
                    	<a href="${pageContext.request.contextPath}/board/myBoard">• 내 여행 계획</a>
                    	</c:if>
                        <a href="${pageContext.request.contextPath}/board/hot">• 추천 여행 계획</a>
                        <a href="${pageContext.request.contextPath}/board/all">• 전체 게시판</a>
                        <a href="${pageContext.request.contextPath}/board/festival">• 인기 축제</a>
                        <a href="${pageContext.request.contextPath}/board/tour">• 인기 관광지</a>
                        <a href="${pageContext.request.contextPath}/board/restaurant">• 인기 음식점</a>
                    </div>
                    <a href="${pageContext.request.contextPath}/members/signOut" class="signOutButton">로그아웃</a>
            </div>
            <div id="backButton" onclick="goBack()">뒤로 가기</div>
        </div>
    </aside>
	
	
<%@ include file="../footerCompact.jsp" %>
</body>

<script src="${pageContext.request.contextPath}/resources/js/mainPage.js" defer></script>
<script>

function goBack(){window.history.back();}
    // 요일 이름 배열
    const dayNames = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'];
    
    // 월 이름 배열
    const monthNames = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];

    // 현재 날짜 가져오기
    const today = new Date();

    // 필요한 데이터 추출
    const year = today.getFullYear();
    const month = monthNames[today.getMonth()]; // 월은 0부터 시작
    const date = today.getDate();
    const day = dayNames[today.getDay()]; // 요일

    // 원하는 형식으로 문자열 생성
    const formattedDate = month + " " + date + "일 " + day;

    // <p> 태그에 삽입
    document.getElementById('currentDate').textContent = formattedDate;
</script>
</html>