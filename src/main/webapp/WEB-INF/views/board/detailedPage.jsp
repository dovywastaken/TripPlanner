<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TripPlanner</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/normalize.css">

<link href="https://cdn.jsdelivr.net/gh/sun-typeface/SUITE@2/fonts/static/woff2/SUITE.css" rel="stylesheet">

<script>
	const key = "${tourAPIKey}"
</script>

<script src="${pageContext.request.contextPath}/resources/js/boardTours.js" defer></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
    <%@ include file="../header.jsp" %>

    <div class="container">
        <div id="containerPanel">
            <div id="containerPanel_A">

                <div class="imgBox">
                    <img src="" id="firstimage">
                </div>

                <div class="info">
                    <div id="infoHeader">
                        <h3>상세 정보</h3>
                        <p id="modifiedtime">최근 업데이트: </p>
                    </div>    
        
                    <div id="detailedInfo">
                        <p><b>주소: </b><span id="addr1"></span> <span id="zipcode"></span></p>
                        <p class="festivalOnly"><b>주최자 정보: </b><span id="sponsor1"></span> <span id="sponsor2"></span></p>
                        <p class="festivalOnly"><b>주최자 연락처: </b><span id="sponsor1tel" class="festivalOnly"></span></p>
                        <p class="festivalOnly"><b>행사기간: </b><span id="eventstartdate" class="festivalOnly"></span> ~ <span id="eventenddate" class="festivalOnly"></span></p>
                        <p class="tourOnly"><b>이용시간: </b><span id="usetime" class="tourOnly"></span></p>
                        <p class="tourOnly"><b>쉬는 날: </b><span id="restdate" class="tourOnly"></span></p>
                        <p class="tourOnly" id="website"><b>웹사이트: </b><a href="" target="_blank" id="homepage" class="tourOnly"></a></p>
                        <p class="tourOnly"><b>문의 및 안내: </b><span id="infocenter" class="tourOnly"></span></p>
                        <p class="tourOnly"><b>체험가능연령: </b><span id="expagerange" class="tourOnly"></span></p>
                        <p class="tourOnly"><b>체험안내: </b><span id="expguide" class="tourOnly"></span></p>
                        <p class="restaurantOnly"><b>식당 영업 시간: </b><span id="opentimefood" class="restaurantOnly"></span></p>
                        <p class="restaurantOnly"><b>식당 쉬는 날: </b><span id="restdatefood" class="restaurantOnly"></span></p>
                        <p class="restaurantOnly"><b>대표 메뉴	: </b><span id="firstmenu" class="restaurantOnly"></span></p>
                        <p class="restaurantOnly"><b>취급 메뉴	: </b><span id="treatmenu" class="restaurantOnly"></span></p>
                        <p class="restaurantOnly"><b>식당 신용 카드 정보: </b><span id="chkcreditcardfood" class="restaurantOnly"></span></p>
                        <p class="restaurantOnly"><b>식당 예약 안내: </b><span id="reservationfood" class="restaurantOnly"></span></p>
                        <p class="restaurantOnly"><b>식당 문의 및 안내: </b><span id="infocenterfood" class="restaurantOnly"></span></p>
                        <p class="festivalOnly"><b>공연 시간: </b><span id="playtime" class="festivalOnly"></span></p>
                    </div>

                    <div id="accessibilityInfo">
                        <h3>시설 정보</h3>
                        <p class="tourOnly"><b>주차시설: </b><span id="parking" class="tourOnly"></span></p>
                        <p class="festivalOnly"><b>이용 요금: </b><span id="usetimefestival" class="festivalOnly"></span></p>
                        <p class="tourOnly"><b>유모차 대여 여부: </b><span id="chkbabycarriage" class="tourOnly"></span></p>
                        <p class="tourOnly"><b>신용카드 가능 여부: </b><span id="chkcreditcard" class="tourOnly"></span></p>
                        <p class="tourOnly"><b>애완동물 동반가능여부: </b><span id="chkpet" class="tourOnly"></span></p>
                        <p class="restaurantOnly"> <b>포장 가능	: </b><span id="packing" class="restaurantOnly"></span></p>
                        <p class="restaurantOnly"><b>식당 주차 시설: </b><span id="parkingfood" class="restaurantOnly"></span></p>
                    </div>
                </div>
            </div>

            <div id="containerPanel_B">
                <div id="mainInfoBanner">
                    <h1 id="title"></h1>
                    <div id="mainInfoHashtags">
                        <p id="addrTag"></p> <p id="cat1"></p><p id="cat3"></p>
                    </div>
                    <p>&nbsp &nbsp<span id="overview"></span>
                    </p>
                </div>

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
                    	<a href="${pageContext.request.contextPath}/Myboard">• 내 여행 계획</a>
                    	</c:if>
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
    <%@ include file="../footer.jsp" %>
</body>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/detailedPage.css">
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