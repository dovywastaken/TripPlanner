<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TripPlanner</title>
<link rel="stylesheet" href="/TripPlanner/resources/css/normalize.css">
<link rel="stylesheet" href="/TripPlanner/resources/css/detailedPage.css">
<link href="https://cdn.jsdelivr.net/gh/sun-typeface/SUITE@2/fonts/static/woff2/SUITE.css" rel="stylesheet">	
<script src="/TripPlanner/resources/js/boardTours.js" defer></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
    <%@ include file="../header.jsp" %>

    <div class="container">
        <div id="containerPanel">
            <div id="containerPanel_A">

                <div class="imgBox">
                    <img src="image.jpg" id="image">
                </div>

                <div class="info">
                    <div id="infoHeader">
                        <h3>상세 정보</h3>
                        <p id="modifiedtime">최근 업데이트: 2010.10.10</p>
                    </div>    
        
                    <div id="detailedInfo">
                        <p><b>주소: </b><span id="addr1"></span> <span id="zipcode">(우)</span></p>
                        <p><b>주최자 정보: </b><span id="sponsor1"></span> <span id="sponsor2"></span></p>
                        <p><b>주최자 연락처: </b><span id="sponsor1tel"></span></p>
                        <p><b>행사기간: </b><span id="eventstartdate">2024.01.01</span> ~ <span id="eventenddate"> 2024.12.25</span></p>
                        <p><b>행사장소: </b><span id="eventplace">진주시</span></p>
                        <p><b>이용시간: </b><span id="usetime">18:00 ~ 23:00</span></p>
                        <p><b>쉬는 날: </b><span id="restdate">연중무휴</span></p>
                        <p><b>웹사이트: </b><a href="" target="_blank" id="homepage"></a></p>
                        <p><b>문의 및 안내: </b><span id="infocenter"></span></p>
                        <p><b>체험가능연령: </b><span id="expagerange"></span></p>
                        <p><b>체험안내: </b><span id="expguide"></span></p>
                        <p><b>식당 영업 시간: </b><span id="opentimefood"></span></p>
                        <p><b>식당 쉬는 날: </b><span id="restdatefood"></span></p>
                        <p><b>대표 메뉴	: </b><span id="firstmenu"></span></p>
                        <p><b>취급 메뉴	: </b><span id="treatmenu"></span></p>
                        <p><b>포장 가능	: </b><span id="packing"></span></p>
                        <p><b>식당 신용 카드 정보: </b><span id="chkcreditcardfood"></span></p>
                        <p><b>식당 예약 안내: </b><span id="reservationfood"></span></p>
                        <p><b>식당 문의 및 안내: </b><span id="infocenterfood"></span></p>
                        <p><b>식당 할인정보: </b><span id="discountinfofood"></span></p>
                        <p><b>공연 시간: </b><span id="playtime"></span></p>
                    </div>

                    <div id="accessibilityInfo">
                        <h3>시설 정보</h3>
                        <p><b>주차시설: </b><span id="parking">주차 가능</span></p>
                        <p><b>이용 요금: </b><span id="usetimefestival">무료</span></p>
                        <p><b>유모차 대여 여부: </b><span id="chkbabycarriage">없음</span></p>
                        <p><b>신용카드 가능 여부: </b><span id="chkcreditcard"></span></p>
                        <p><b>애완동물 동반가능여부: </b><span id="chkpet"></span></p>
                        <p><b>식당 주차 시설: </b><span id="parkingfood"></span></p>
                        <p><b>금연/흡연: </b><span id="smoking"></span></p>
                        <p><b>좌석수: </b><span id="seat"></span></p>
                    </div>
                </div>
            </div>

            <div id="containerPanel_B">
                <div id="mainInfoBanner">
                    <h1 id="title">가계해수욕장</h1>
                    <div id="mainInfoHashtags">
                        <p id="addr1Tag"></p> <p id="cat2"></p><p id="cat3"></p>
                    </div>
                    <p> &nbsp &nbsp &nbsp<span id="overview">바닷물이 갈라지는 현대판 모세의 기적으로 유명한 신비의 바닷길옆에 위치한 해수욕장이다. 넓은 백사장과 다도해상을 마주하고 풍경이 아름다운 해수욕장으로 백사장 길이는 3km이다. 교통이 편리할 뿐 아니라 넓은 주차장과 샤워장, 음수대 등 편의시설이 잘 갖추어져 있고 교통이 편리하여 자가용을 이용한 피서객들이 많이 찾는다. 해수욕장 인근에는 조형물들이 있어서 포토존으로 인기가 좋으며 해변가를 따라 소나무와 잔디가 있어 나무아래 그늘에서 햇볕을 피할 수 있고 초록색과 파란 바다색이 어우러져 아름다운 경치를 즐기며 물놀이와 휴식을 즐길 수 있다. 또한, 갯바위와 선착장, 무인도에서 감성동, 도다리, 농어, 숭어, 장어, 보리멸 등의 바다낚시를 즐길 수 있다. 인근에 진도해양생태관, 진도 신비의 바닷길, 금호도 등의 관광지가 있다.</span>
                    </p>
                </div>

            </div>
        </div>
    </div>
    <%@ include file="../footer.jsp" %>
</body>
</html>