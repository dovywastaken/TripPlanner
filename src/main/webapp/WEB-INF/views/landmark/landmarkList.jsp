<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Landmarks</title>
</head>
<script>var contextPath = '${pageContext.request.contextPath}';</script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>

</head>
<body>
<a href="${pageContext.request.contextPath}">Home</a>
<a href="${pageContext.request.contextPath}/landmark/filterTest">필터 테스트 페이지</a>

<body>
<div class="filterContainer">
	<div class="filterBox">
		<h2>관광 지역</h2>
		<div class="regionType">
			<div class="region" id="seoul">서울</div>
			<div>
				<div class="district">강북구</div>
			    <div class="district">강동구</div>
			    <div class="district">강남구</div>
			    <div class="district">강서구</div>
			    <div class="district">구로구</div>
			    <div class="district">금천구</div>
			    <div class="district">노원구</div>
			    <div class="district">도봉구</div>
			    <div class="district">동대문구</div>
			    <div class="district">동작구</div>
			    <div class="district">마포구</div>
			    <div class="district">서대문구</div>
			    <div class="district">서초구</div>
			    <div class="district">성동구</div>
			    <div class="district">성북구</div>
			    <div class="district">송파구</div>
			    <div class="district">양천구</div>
			    <div class="district">영등포구</div>
			    <div class="district">용산구</div>
			    <div class="district">은평구</div>
			    <div class="district">종로구</div>
			    <div class="district">중구</div>
			    <div class="district">중랑구</div>
			</div>
			
			<div class="region" id="busan">부산</div>
			<div>
				<div class="district">강서구</div>
			    <div class="district">금정구</div>
			    <div class="district">기장군</div>
			    <div class="district">남구</div>
			    <div class="district">동구</div>
			    <div class="district">동래구</div>
			    <div class="district">부산진구</div>
			    <div class="district">북구</div>
			    <div class="district">사상구</div>
			    <div class="district">사하구</div>
			    <div class="district">서구</div>
			    <div class="district">수영구</div>
			    <div class="district">연제구</div>
			    <div class="district">중구</div>
			    <div class="district">해운대구</div>
			    <div class="district">영도구</div>
			</div>
			
			
			<div class="region" id="daegu">대구
			    <div class="district">달서구</div>
			    <div class="district">달성군</div>
			    <div class="district">동구</div>
			    <div class="district">북구</div>
			    <div class="district">서구</div>
			    <div class="district">수성구</div>
			    <div class="district">남구</div>
			    <div class="district">중구</div>
			</div>
			
			<div class="region" id="incheon">인천
			    <div class="district">강화군</div>
			    <div class="district">계양구</div>
			    <div class="district">남동구</div>
			    <div class="district">동구</div>
			    <div class="district">서구</div>
			    <div class="district">옹진군</div>
			    <div class="district">연수구</div>
			    <div class="district">부평구</div>
			    <div class="district">중구</div>
			</div>
			
			<div class="region" id="gwangju">광주
			    <div class="district">광산구</div>
			    <div class="district">남구</div>
			    <div class="district">동구</div>
			    <div class="district">북구</div>
			    <div class="district">서구</div>
			</div>
			
			<div class="region" id="daejeon">대전
			    <div class="district">대덕구</div>
			    <div class="district">동구</div>
			    <div class="district">서구</div>
			    <div class="district">유성구</div>
			    <div class="district">중구</div>
			</div>
			
			<div class="region" id="ulsan">울산
			    <div class="district">동구</div>
			    <div class="district">북구</div>
			    <div class="district">중구</div>
			    <div class="district">남구</div>
			    <div class="district">울주군</div>
			</div>
			
			<div class="region" id="sejong">세종
			</div>


			<div class="region" id="jeju">제주
			    <div class="district" id="jeju-city">제주시</div>
			    <div class="district" id="seogwipo">서귀포시</div>
			</div>
			
			<div class="region" id="gyeonggi">경기
			    <div class="district" id="gapyeong">가평군</div>
			    <div class="district" id="goyang">고양시</div>
			    <div class="district" id="guri">구리시</div>
			    <div class="district" id="gunpo">군포시</div>
			    <div class="district" id="gwangmyeong">광명시</div>
			    <div class="district" id="gwangju">광주시</div>
			    <div class="district" id="guri">구리시</div>
			    <div class="district" id="gimpo">김포시</div>
			    <div class="district" id="namyangju">남양주시</div>
			    <div class="district" id="dongducheon">동두천시</div>
			    <div class="district" id="bucheon">부천시</div>
			    <div class="district" id="seongnam">성남시</div>
			    <div class="district" id="suwon">수원시</div>
			    <div class="district" id="siheung">시흥시</div>
			    <div class="district" id="anseong">안성시</div>
			    <div class="district" id="anyang">안양시</div>
			    <div class="district" id="yangju">양주시</div>
			    <div class="district" id="yangpyeong">양평군</div>
			    <div class="district" id="yeoju">여주시</div>
			    <div class="district" id="yeoncheon">연천군</div>
			    <div class="district" id="osam">오산시</div>
			    <div class="district" id="yongin">용인시</div>
			    <div class="district" id="uiwang">의왕시</div>
			    <div class="district" id="uijeongbu">의정부시</div>
			    <div class="district" id="icheon">이천시</div>
			    <div class="district" id="paju">파주시</div>
			    <div class="district" id="pyeongtaek">평택시</div>
			    <div class="district" id="pocheon">포천시</div>
			    <div class="district" id="hanam">하남시</div>
			    <div class="district" id="hwaseong">화성시</div>
			</div>
			
			<div class="region" id="gangwon">강원
			    <div class="district" id="gangneung">강릉시</div>
			    <div class="district" id="goesan">고성군</div>
			    <div class="district" id="donghae">동해시</div>
			    <div class="district" id="samcheok">삼척시</div>
			    <div class="district" id="sokcho">속초시</div>
			    <div class="district" id="yanggu">양구군</div>
			    <div class="district" id="yangyang">양양군</div>
			    <div class="district" id="wonju">원주시</div>
			    <div class="district" id="inje">인제군</div>
			    <div class="district" id="jeongseon">정선군</div>
			    <div class="district" id="cheorwon">철원군</div>
			    <div class="district" id="chuncheon">춘천시</div>
			    <div class="district" id="taebaek">태백시</div>
			    <div class="district" id="hongcheon">홍천군</div>
			    <div class="district" id="hoengseong">횡성군</div>
			</div>
			
			<div class="region" id="chungbuk">충북
			    <div class="district" id="goesan">괴산군</div>
			    <div class="district" id="danyang">단양군</div>
			    <div class="district" id="boeun">보은군</div>
			    <div class="district" id="sangdang">상당구</div>
			    <div class="district" id="seowon">서원구</div>
			    <div class="district" id="jecheon">제천시</div>
			    <div class="district" id="jincheon">진천군</div>
			    <div class="district" id="cheongju">청주시</div>
			    <div class="district" id="chungju">충주시</div>
			    <div class="district" id="eumseong">음성군</div>
			    <div class="district" id="okcheon">옥천군</div>
			    <div class="district" id="yeongdong">영동군</div>
			</div>
			
			<div class="region" id="chungnam">충남
			    <div class="district" id="gongju">공주시</div>
			    <div class="district" id="geumsan">금산군</div>
			    <div class="district" id="nonsan">논산시</div>
			    <div class="district" id="dangjin">당진시</div>
			    <div class="district" id="boryeong">보령시</div>
			    <div class="district" id="seosan">서산시</div>
			    <div class="district" id="seocheon">서천군</div>
			    <div class="district" id="asansi">아산시</div>
			    <div class="district" id="yesan">예산군</div>
			    <div class="district" id="cheonansi">천안시</div>
			    <div class="district" id="cheongyang">청양군</div>
			    <div class="district" id="hongseong">홍성군</div>
			    <div class="district" id="taean">태안군</div>
			</div>
			
			<div class="region" id="jeonbuk">전북</div>
			<div class="district">
			    <div id="gunsan">군산시</div>
			    <div id="gimje">김제시</div>
			    <div id="namwon">남원시</div>
			    <div id="buan">부안군</div>
			    <div id="sunchang">순창군</div>
			    <div id="iksan">익산시</div>
			    <div id="imshil">임실군</div>
			    <div id="jeongeup">정읍시</div>
			    <div id="jeonju">전주시</div>
			    <div id="jangsu">장수군</div>
			    <div id="jinan">진안군</div>
			</div>
			
			<div class="region" id="jeonnam">전남
			    <div class="district" id="gangjin">강진군</div>
			    <div class="district" id="goheung">고흥군</div>
			    <div class="district" id="gokseong">곡성군</div>
			    <div class="district" id="gwangyang">광양시</div>
			    <div class="district" id="gurye">구례군</div>
			    <div class="district" id="naju">나주시</div>
			    <div class="district" id="damyang">담양군</div>
			    <div class="district" id="muan">무안군</div>
			    <div class="district" id="mokpo">목포시</div>
			    <div class="district" id="boseong">보성군</div>
			    <div class="district" id="suncheon">순천시</div>
			    <div class="district" id="sinan">신안군</div>
			    <div class="district" id="yeosu">여수시</div>
			    <div class="district" id="yeonggwang">영광군</div>
			    <div class="district" id="yeongam">영암군</div>
			    <div class="district" id="wando">완도군</div>
			    <div class="district" id="jangseong">장성군</div>
			    <div class="district" id="jangheung">장흥군</div>
			    <div class="district" id="haenam">해남군</div>
			    <div class="district" id="hwasun">화순군</div>
			</div>
			
			<div class="region" id="gyeongbuk">경북
			    <div class="district" id="gimcheon">김천시</div>
			    <div class="district" id="mungyeong">문경시</div>
			    <div class="district" id="sangju">상주시</div>
			    <div class="district" id="andong">안동시</div>
			    <div class="district" id="yeongcheon">영천시</div>
			    <div class="district" id="yeongju">영주시</div>
			    <div class="district" id="yeongdeok">영덕군</div>
			    <div class="district" id="uljin">울진군</div>
			    <div class="district" id="cheongdo">청도군</div>
			    <div class="district" id="cheongsong">청송군</div>
			    <div class="district" id="goryeong">고령군</div>
			    <div class="district" id="gungju">경주시</div>
			    <div class="district" id="gunwi">군위군</div>
			    <div class="district" id="seongju">성주군</div>
			    <div class="district" id="uiseong">의성군</div>
			    <div class="district" id="chilgok">칠곡군</div>
			    <div class="district" id="pohang">포항시</div>
			</div>
			
			<div class="region" id="gyeongnam">경남
			    <div class="district" id="geoje">거제시</div>
			    <div class="district" id="geochang">거창군</div>
			    <div class="district" id="goseong">고성군</div>
			    <div class="district" id="gimhae">김해시</div>
			    <div class="district" id="namhae">남해군</div>
			    <div class="district" id="miryang">밀양시</div>
			    <div class="district" id="sacheon">사천시</div>
			    <div class="district" id="yangsan">양산시</div>
			    <div class="district" id="changnyeong">창녕군</div>
			    <div class="district" id="changwon">창원시</div>
			    <div class="district" id="tongyeong">통영시</div>
			    <div class="district" id="hadong">하동군</div>
			    <div class="district" id="haman">함안군</div>
			    <div class="district" id="hamyang">함양군</div>
			    <div class="district" id="hapcheon">합천군</div>
			    <div class="district" id="jinju">진주시</div>
			    <div class="district" id="uleung">울릉군</div>
			</div>

		</div>
		
		<h2>관광 타입</h2>
		<div class="tourType">
			<div id="culture">문화시설</div>
			<div id="festival">축제공연</div>
			<div id="touristSpot">관광지</div>
			<div id="leisure">레포츠</div>
			<div id="restaurant">맛집</div>
			<div id="shopping">쇼핑</div>
		</div>
		
		<div id="filterResult">선택한 필터 : </div>

		<div class="buttonBox">
			<div id="requestAPI">선택</div>
			<div id="reset">초기화</div>
		</div>
	</div>
	<div class="searchBox">검색</div>
</div>
</body>



    <div class="container">
        <div class="image-placeholder">
            썸네일
        </div>
        <div class="content">
            <div class="title">상호명</div>
            <div class="overview">
                Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            </div>
            <div class="buttons">
                <div id="contentTypeId">관광지</div>
                <div id="cat1">자연</div>
                <div id="addr1">성산구</div>
                <div id="cat3">자연 휴양림</div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="image-placeholder">
            썸네일
        </div>
        <div class="content">
            <div class="title">상호명</div>
            <div class="overview">
                Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            </div>
            <div class="buttons">
                <div id="contentTypeId">관광지</div>
                <div id="cat1">자연</div>
                <div id="addr1">성산구</div>
                <div id="cat3">자연 휴양림</div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="image-placeholder">
            썸네일
        </div>
        <div class="content">
            <div class="title">상호명</div>
            <div class="overview">
                Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            </div>
            <div class="buttons">
                <div id="contentTypeId">관광지</div>
                <div id="cat1">자연</div>
                <div id="addr1">성산구</div>
                <div id="cat3">자연 휴양림</div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="image-placeholder">
            썸네일
        </div>
        <div class="content">
            <div class="title">상호명</div>
            <div class="overview">
                Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            </div>
            <div class="buttons">
                <div id="contentTypeId">관광지</div>
                <div id="cat1">자연</div>
                <div id="addr1">성산구</div>
                <div id="cat3">자연 휴양림</div>
            </div>
        </div>
    </div>
    
    <div class="container">
        <div class="image-placeholder">
            썸네일
        </div>
        <div class="content">
            <div class="title">상호명</div>
            <div class="overview">
                Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            </div>
            <div class="buttons">
                <div id="contentTypeId">관광지</div>
                <div id="cat1">자연</div>
                <div id="addr1">성산구</div>
                <div id="cat3">자연 휴양림</div>
            </div>
        </div>
    </div>
    
    <div class="container">
        <div class="image-placeholder">
            썸네일
        </div>
        <div class="content">
            <div class="title">상호명</div>
            <div class="overview">
                Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            </div>
            <div class="buttons">
                <div id="contentTypeId">관광지</div>
                <div id="cat1">자연</div>
                <div id="addr1">성산구</div>
                <div id="cat3">자연 휴양림</div>
            </div>
        </div>
    </div>
    
    <div class="container">
        <div class="image-placeholder">
            썸네일
        </div>
        <div class="content">
            <div class="title">상호명</div>
            <div class="overview">
                Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            </div>
            <div class="buttons">
                <div id="contentTypeId">관광지</div>
                <div id="cat1">자연</div>
                <div id="addr1">성산구</div>
                <div id="cat3">자연 휴양림</div>
            </div>
        </div>
    </div>
    
    <div class="container">
        <div class="image-placeholder">
            썸네일
        </div>
        <div class="content">
            <div class="title">상호명</div>
            <div class="overview">
                Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            </div>
            <div class="buttons">
                <div id="contentTypeId">관광지</div>
                <div id="cat1">자연</div>
                <div id="addr1">성산구</div>
                <div id="cat3">자연 휴양림</div>
            </div>
        </div>
    </div>
</body>
<script src="${pageContext.request.contextPath}/resources/js/landmark.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/landmarkList.css">

</html>