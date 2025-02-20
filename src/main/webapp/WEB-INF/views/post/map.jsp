<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>지도 + 오버레이 패널</title>
  <script src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=${mapAPIKey}&libraries=services"></script>
  <script> window.contextPath = '${pageContext.request.contextPath}'; </script>
  <script src="${pageContext.request.contextPath}/resources/js/tourApi.js"></script>
  <script type="module" src="${pageContext.request.contextPath}/resources/js/map.js" defer="defer"></script>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/map.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/maps.css">
</head>


<body>

  <!-- 지도 영역 -->
  <div id="map"></div>

  <!-- 지도 위 오버레이 패널 (필터 / 토글) -->
  <div id="overlayPanel">
    <h2>필터</h2>

    <!-- 관광 지역 섹션 -->
    <div class="filter-section" >
      <strong>관광 지역</strong><br/>
      <button data-area="서울">서울</button>
      <button data-area="부산">부산</button>
      <button data-area="세종">세종</button>
      <button data-area="인천">인천</button>
      <button data-area="대구">대구</button>
      <button data-area="광주">광주</button>
      <button data-area="울산">울산</button>
      <button data-area="제주도">제주도</button>
      <button data-area="경기도">경기도</button>
      <button data-area="충청북도">충북</button>
      <button data-area="충청남도">충남</button>
      <button data-area="강원도">강원도</button>
      <button data-area="전라북도">전라북도</button>
      <button data-area="전라남도">전라남도</button>
      <button data-area="경상북도">경상북도</button>
      <button data-area="경상남도">경상남도</button>
    </div>
    
	<div class="btn-group" id="sigunguButtons">
	</div>
	
    <div class="filter-section">
      <strong>관광 타입</strong><br/>
      <button data-type="문화시설">문화시설</button>
      <button data-type="축제공연">축제공연</button>
      <button data-type="관광지">관광지</button>
      <button data-type="레포츠">레포츠</button>
      <button data-type="맛집">맛집</button>
      <button data-type="쇼핑">쇼핑</button>
    </div>
	

    <!-- 현재 선택된 필터 표시 -->
    <div id="selectedFilters">선택된 필터: 없음</div>

    <button class="search-btn" id="searchBtn">검색</button>
    <button class="reset-btn" id="resetBtn">초기화</button>
  </div>

	<button id="myList-submit">저장</button>
  <div id="searchBar">
    <div class="search-input-wrap">
        <input type="text" id="searchKeyword" placeholder="장소를 검색하세요">
        <button id="searchButton">검색</button>
    </div>
    <div class="search-option">
        <label>
            <input type="checkbox" id="searchInBounds">
            <span>현재 지도 영역에서만 검색</span>
        </label>
    </div>
</div>
  <div id="search-List">
  </div>
  <div id="myList">
	</div>
	<button id="serch-Listbtn">검색바</button>
	<button id="myListbtn">마이리스트</button>
</body>
</html>