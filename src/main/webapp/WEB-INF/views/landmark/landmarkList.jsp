<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Landmarks</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/normalize.css">
    <script>var contextPath = '${pageContext.request.contextPath}';</script>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>






<body>
<a href="${pageContext.request.contextPath}" class="aTag">Home</a>
<a href="${pageContext.request.contextPath}/landmark/filterTest" class="aTag">필터 테스트 페이지</a>

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

			
			
			<div class="region" id="daegu">대구

			</div>
			
			<div class="region" id="incheon">인천

			</div>
			
			<div class="region" id="gwangju">광주

			</div>
			
			<div class="region" id="daejeon">대전

			</div>
			
			<div class="region" id="ulsan">울산

			</div>
			
			<div class="region" id="sejong">세종
			</div>


			<div class="region" id="jeju">제주

			</div>
			
			<div class="region" id="gyeonggi">경기

			</div>
			
			<div class="region" id="gangwon">강원

			</div>
			
			<div class="region" id="chungbuk">충북

			</div>
			
			<div class="region" id="chungnam">충남

			</div>
			
			<div class="region" id="jeonbuk">전북</div>
			<div class="district">

			</div>
			
			<div class="region" id="jeonnam">전남

			</div>
			
			<div class="region" id="gyeongbuk">경북

			</div>
			
			<div class="region" id="gyeongnam">경남
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
</body>

<script src="${pageContext.request.contextPath}/resources/js/landmark.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/header.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/landmarkList.css">


</html>