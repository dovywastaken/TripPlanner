<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>트립플래너</title>
<link rel="stylesheet" href="/TripPlanner/resources/css/normalize.css">
<link rel="stylesheet" href="/TripPlanner/resources/css/boardTours.css">
<link href="https://cdn.jsdelivr.net/gh/sun-typeface/SUITE@2/fonts/static/woff2/SUITE.css" rel="stylesheet">
</head>

<body>
<%@ include file="../header.jsp" %>

<div class="container">
	<h1>추천 축제 게시판입니다.</h1>
	<div class="section">
        <div id="planner">
			<c:choose>
				<c:when test="${not empty festivals}">
					<c:forEach var="festivals" items="${festivals}">
			            <div class="recommandCard">
			            	<div class="imgFrame">
				                <a href="/TripPlanner/detailedInfo?contentTypeId=${festivals.contenttypeid}&contentId=${festivals.contentid}"">
				                    <img src="${festivals.firstimage}" class="pImg">
				                </a>
				            </div>
			                <div class="plannerCol" id="pText">
			                    <a href="/TripPlanner/detailedInfo?contentTypeId=${festivals.contenttypeid}&contentId=${festivals.contentid}" class="plannerTitle">${festivals.title}</a>
			                    <p class="hashtag">#${festivals.addr1}</p>
			                </div>
			            </div>
			            <!-- 
						<div class="recommandCard">
							<div class="imgFrame">
								<img src="${festival.firstimage}" class="pImg">
							</div>
							<div class="plannerCol" id="pText">
							  <div>
								  <h3 class="plannerTitle">${festival.title}</h3>
								  <p class="hashtag">#경산시 #축제 #관광지 #맛집</p>
							  </div>
							  </div>
						</div>
						 -->
		           	</c:forEach>
		        </c:when>
		        <c:otherwise>
		        	<div>아직 웹사이트가 응애라 추천할 축제 정보가 없어요 :( </div>
		        </c:otherwise>
			</c:choose>
        </div>
    </div>
</div>

	<aside>
        <div class="sidePanelContainer">
            <div id="myPanel">
                <c:if test="${not empty user}">
                    <!-- 로그인한 사용자가 있을 때 보여줄 내용 -->
                    <div id="userInfo">
                        <h1>${user.name}</h1>
                        <h2>${user.id}</h2>
                    </div>
                    <p id="currentDate" style="text-align: center; width: 100%;"></p>
                
                    <hr style="border: 1px solid #F1F3F9; margin : 34px auto 10px auto; width: 80%;">

                    <div id="links"> 
                        <a href="">• 내 여행 계획</a>
                        <a href="">• 전체 게시판</a>
                        <a href="">• 인기 축제</a>
                        <a href="">• 인기 관광지</a>
                        <a href="">• 인기 음식점</a>
                    </div>
                    <a href="/TripPlanner/members/signOut" class="signOutButton">로그아웃</a>
                  </c:if>
                  <c:if test="${empty user}">
                  	<br>
				    <h1 class="signInTitle" style="text-align: center; width: 100%;">로그인하고</h1>
				    <h1 class="signInTitle" style="text-align: center; width: 100%;">여정을 떠나봐요!</h1>
				    <!-- 로그인 폼이 보이는 부분 -->
			        <div class="form-container">
			            
			            <!-- 로그인 폼 -->
			            <form:form modelAttribute="member" method="POST" action="members/signIn">
			                <div class="form-group">
			                    <label for="id">아이디:</label>
			                    <form:input path="id" id="id" placeholder="아이디" />
			                </div>
			                <div class="form-group">
			                    <label for="pw">비밀번호:</label>
			                    <form:input path="pw" id="pw" placeholder="비밀번호" type="password" />
			                </div>
			                <!-- hidden 필드 추가 -->
			                <div class="form-group">
			                    <input type="submit" id="submitButton" value="로그인">
			                </div>
			                <a href="/TripPlanner/members/signUp" id="signUpButton">가입하기</a>
			            </form:form>
			        </div>
                  </c:if>
            </div>
            <div id="backButton">뒤로 가기</div>
        </div>
    </aside>
	
	
<%@ include file="../footerCompact.jsp" %>
</body>


<script>
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