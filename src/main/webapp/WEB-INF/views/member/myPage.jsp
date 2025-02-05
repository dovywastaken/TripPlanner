<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TripPlanner</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>let contextPath = '${pageContext.request.contextPath}';</script>
    <script src="${pageContext.request.contextPath}/resources/js/myPage.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources//css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources//css/myPage.css">
    <link href="https://cdn.jsdelivr.net/gh/sun-typeface/SUITE@2/fonts/static/woff2/SUITE.css" rel="stylesheet">
</head>
    
<body>

	<%@ include file="../header.jsp" %>

    <div id="container">
        <div class="wrapper" id="leftWrapper">
        <c:if test="${not empty user}">
            <h1>내 정보</h1>
            <div id="infoWrapper">
                <div class="infoWrapperAlignment"> 
                    <p>이메일
                    <c:if test="${user.emailCheck == 0}">
                    	<span style="text-align: center; width: 100%; white-space: nowrap; font-size: 14px; margin-left: 5px;">아직 이메일 인증이 안됐어요!</span>
                    </c:if>
                    <c:if test="${user.emailCheck == 1}">
                        <span id="emailCheck" class="green-text">인증 완료</span>
                    </c:if>
                    </p>
                    <p class="dataBox">${user.email}</p>
                </div>
                <c:if test="${user.emailCheck == 0}">
	                <div class="email-verification">
	                    <button id="email">인증번호 보내기</button>
	                    <span id="loading" ></span>
	                    <span id="remainingTime"></span>
	                </div>
                </c:if>
                <div class="infoWrapperAlignment">
                    <p>닉네임</p>
                    <p class="dataBox">${user.nickname}</p>
                </div>
                <div class="infoWrapperAlignment">
                    <p>전화번호</p>
                    <p class="dataBox">${user.phone1}-${user.phone2}-${user.phone3}</p>
                </div>
                <div class="infoWrapperAlignment">
                    <p>생년월일</p>
                    <p class="dataBox">${user.birthday}</p>
                </div>
                <div class="infoWrapperAlignment">
                    <p>가입 날짜</p>
                    <p class="dataBox">${user.registerDate}</p>
                </div>
            </div>
            <div id="updateMemberButton">
                <a href="updateMember">회원 정보 수정</a>
            </div>
        </div>
        <div class="wrapper" id="rightWrapper">
            <a href="pwChange" id="pwChange">비밀번호 변경</a>
            <a href="deleteMember" id="deleteMember">회원 탈퇴</a>
        </div>
    </c:if>
    <c:if test="${empty user}">
    	<div style="display : flex; flex-direction : column; align-items: center; justify-content: center; height: 305px;">
    	<h2>로그인이 필요합니다!</h2>
    		<a href="${pageContext.request.contextPath}/members/signIn" id="submitButton">로그인하러 가기</a>
    	</div>
    </c:if>
    </div>
	<c:if test="${not empty user}">
    <aside>
        <div class="sidePanelContainer">
            <div id="myPanel">
                    <!-- 로그인한 사용자가 있을 때 보여줄 내용 -->
                    <div id="userInfo">
                        <h1>${user.nickname}</h1>
                        <h2>${user.email}</h2>
                    </div>
                    <p id="currentDate" style="text-align: center; width: 100%; color: #2C3F3C;"></p>
                <hr style="border: 1px solid #F1F3F9; margin : 21px auto 10px auto; width: 80%;">

                <div id="links"> 
                    <a href="${pageContext.request.contextPath}/hotPlanners">• 추천 여행 계획</a>
                    <a href="${pageContext.request.contextPath}/Allboard">• 전체 게시판</a>
                    <a href="${pageContext.request.contextPath}/boardFestival">• 인기 축제</a>
                    <a href="${pageContext.request.contextPath}/boardTour">• 인기 관광지</a>
                    <a href="${pageContext.request.contextPath}/boardRestaurant">• 인기 음식점</a>
                </div>
                <a href="${pageContext.request.contextPath}/members/signOut" class="signOutButton">로그아웃</a>
            </div>
            <div id="backButton" onclick="goBack()">뒤로 가기</div>
        </div>
    </aside>
    
    <%@ include file="../footerCompact.jsp" %>
    </c:if>
</body>

<script>
	function goBack() {window.history.back();}
	//요일 이름 배열
	const dayNames = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'];
	console.log(dayNames);
	// 월 이름 배열
	const monthNames = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];
	console.log(monthNames);
	// 현재 날짜 가져오기
	const today = new Date();
	console.log(today);
	
	// 필요한 데이터 추출
	const year = today.getFullYear();
	const month = monthNames[today.getMonth()]; // 월은 0부터 시작
	const date = today.getDate();
	const day = dayNames[today.getDay()]; // 요일
	
	
	console.log(year);
	console.log(month);
	console.log(date);
	console.log(day);
	
	// 원하는 형식으로 문자열 생성
	const formattedDate = month + " " + date + '일 ' + day;
	
	// <p> 태그에 삽입
	document.getElementById('currentDate').textContent = formattedDate;
</script>
</html>
