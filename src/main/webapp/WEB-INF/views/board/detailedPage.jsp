<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TripPlanner</title>

<link rel="stylesheet" href="/TripPlanner/resources/css/normalize.css">
<style>
	body
	{
		position : relative;
	}

	#backToBoard
	{
		position : fixed;
		left : 12%;
		bottom : 88%;
		width : 60px;
		height : auto;
		border-radius : 50px;
		background-color : orange;
		text-align : center;
	}

    .container {
        margin: 0 auto;
        padding: 55px 300px;
        width : 100vw;
    }
    .imgBox {
		 width : 100%;
		 height: 300px;
		 margin : 0 auto;
		 outline: 1px solid red;
		 background-color: black;
		 overflow : hidden;
	}
	
	.image {
	 width: 100%;
	 object-fit: contain;
	 min-width: 100%;
	}
    .info {
        margin-top: 20px;
        background-color: #ccc;
    }
    .info h2 {
        margin-bottom: 10px;
    }
    .info p {
        margin: 5px 0;
    }
    .section {
        margin-top: 30px;
        padding-top: 20px;
        border-top: 1px solid #ccc;
    }
    #mainInfoBanner {
        /* outline: 1px solid magenta; */
        display: flex;
        flex-direction: column;
        align-items: center;
        background-color: bisque;
        min-height: 150px; /* 최소 높이를 설정 */
        padding: 20px 0; /* 여백 추가 */
    }

    #placeName {
        /* outline: 1px solid magenta; */
        font-size: 24px; /* 글자 크기 조정 */
        text-align: center; /* 이름 중앙 정렬 */
    }

    #mainInfoHashtags {
        /* outline: 1px solid magenta; */
        display: flex;
        gap: 13px;
        justify-content: center; /* 해시태그들 중앙 정렬 */
    }

    #infoHeader {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    #workTime
    {
        display: flex;
        justify-content: start;
        align-items: center;
    }

    #workTime p
    {
        margin-right: 25px;
    }

    #accessibilityInfo
    {
        background-color: #ccc;
    }

    .accInfo
    {
        display: flex;
    }

    .accInfo p
    {
        margin-right: 25px;
    }

</style>
<script src="/TripPlanner/resources/js/boardTours.js" defer></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<%@ include file="../header.jsp" %>
<body>
	<a href="" id="backToBoard">뒤로가기</a>
    <div class="container">
        <div class="imgBox">
            <img src="" id="image">
        </div>
        <div id="mainInfoBanner">
            <h1 id="title"></h1>
            <div id="mainInfoHashtags">
                <p id="addr1Tag"></p> <p id="cat2"></p><p id="cat3"></p>
            </div>
            <p id="overview"></p>
        </div>
        <div class="info">
            <div id="infoHeader">
                <h3>상세 정보</h3>
                <p id="modifiedtime">업데이트</p>
            </div>        
            <div id="detailedInfo">
                <p>주소: <span id="addr1"></span> <span id="zipcode">(우)</span></p>
                <p id="sponsor1"></p>
                <p id="sponsor1tel"></p>
                <p class="festivalOnly" id="eventstartdate"></p>
                <p id="workTime"> </p>
                <p id='restdatefood'> </p>
                <p id='firstmenu'> </p>
                <p id='treatmenu'> </p>
                
                <p id="homepageHolder"><a href="" target="_blank" id="homepage"></a></p>
                
            </div>
        </div>
        
        <div id="accessibilityInfo">
            <h3>시설 정보</h3>
            <p id="chkpet"></p>
            <p id="parking"></p>
            <p id="parkingfood"></p>
            <p id="usetimefestival"></p>
            <p id="chkcreditcardfood"></p>
            <p id="reservationfood"></p>
            <p id="packing"></p>
            
        </div>
    </div>
</body>
<%@ include file="../footer.jsp" %>
</html>