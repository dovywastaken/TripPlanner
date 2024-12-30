<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <title>지도 + 오버레이 패널</title>
  <script  src="<%= request.getContextPath() %>/resources/js/tourApi.js"></script>
   <script  type="module" src="<%= request.getContextPath() %>/resources/js/maps.js"></script>
  
  <style>
    /* 전체 화면을 사용하기 위해 기본 마진/패딩 제거 */
    html, body {
      margin: 0; 
      padding: 0;
      width: 100%; 
      height: 100%;
      font-family: sans-serif;
    }

    /* 지도 영역 */
    #map {
      margin-top:2%;
      margin-left: 20%;
      width: 80%; 
      height: 88%;
      position: relative; /* 오버레이 패널을 절대 위치로 배치하기 위해 */
    }

    /* 오버레이 패널 */
    #overlayPanel {
      position: absolute;    /* 지도 위에 겹치도록 */
      top: 60px; 
      right: 20px;
      z-index: 999;          /* 지도의 다른 요소보다 위로 */
      
      width: 330px;          /* 원하는 너비 */
      background-color: #fff;
      border: 1px solid #ccc;
      border-radius: 8px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.15);
      padding: 10px;
      
      transition: all 0.3s ease; /* 접고 펼치기 애니메이션 */
    }

    /* 접힌 상태 클래스(너비·높이를 줄여서 숨기는 효과) */
    #overlayPanel.collapsed {
      width: 50px;
      height: 50px;    
      overflow: hidden; /* 내부 내용이 안 보이도록 */
      padding: 0;       /* 패딩도 없애서 완전히 작게 */
      border-radius: 50%; 
    }

    /* 접기/펼치기 버튼 */
    .toggle-btn {
      position: absolute;
      top: 5px;
      right: 5px;
      cursor: pointer;
      background-color: #eee;
      padding: 5px 8px;
      border-radius: 4px;
      font-size: 12px;
    }

    /* 패널 내부 구조 */
    h2 {
      margin-top: 0;
      font-size: 1.1rem;
      border-bottom: 1px solid #ccc;
      padding-bottom: 5px;
    }
    .filter-section {
      margin-bottom: 10px;
    }
    .filter-section button {
      margin: 3px;
      padding: 5px 8px;
      border: 1px solid #ccc;
      background-color: #f9f9f9;
      cursor: pointer;
      border-radius: 4px;
    }

    /* 검색 버튼, 초기화 버튼 */
    .filter-actions {
      text-align: center;
    }
    .filter-actions button {
      margin: 5px 3px;
      padding: 6px 12px;
      cursor: pointer;
      background-color: #007aff;
      color: #fff;
      border: none;
      border-radius: 4px;
    }
    .filter-actions button.reset-btn {
      background-color: #666;
    }

    /* 예시로 만들어본 '현재 선택 필터' 표시 영역 */
    .selected-filters {
      margin: 10px 0;
      font-size: 0.9rem;
      color: #333;
    }
    
    #myList{
   	 position: absolute;    /* 지도 위에 겹치도록 */
      top: 30px; 
      left: 0px;
      z-index: 998;          /* 지도의 다른 요소보다 위로 */
      
      width: 20%; 
      height: 900px;
      background-color: #fff;
      border: 1px solid #ccc;
      border-radius: 8px;
      box-shadow: 0 2px 5px rgba(0,0,0,0.15);
      
    
    }
    #sigunguButtons {
    display: flex;
    flex-wrap: wrap;
    gap: 5px;              
    padding: 5px;         
}

   .sgbtn {
    background-color: #f9f9f9;
    border: 1px solid #ccc;  
    border-radius: 5px;      
    padding: 5px 5px;        
    font-size: 12px;          
    cursor: pointer;         
    transition: all 0.2s ease; 
}

	#myList-submit{
    display: block; /* 버튼이 보이도록 설정 */
    padding: 10px 20px;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}
  </style>
</head>
<body>

  <!-- 지도 영역 -->
  <div id="map"></div>

  <!-- 지도 위 오버레이 패널 (필터 / 토글) -->
  <div id="overlayPanel">
    <div class="toggle-btn" id="toggleBtn">접기</div>

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
  
  <div id="myList">
	</div>
	<button id="myList-submit">저장</button>
  <div id="searchBar">
    <input type="text" id="searchKeyword" placeholder="검색어를 입력하세요">
    <button id="searchButton">검색</button>
  </div>
  <div id="serch-List">
  <p>안녕하세요</p>
  </div>
  <!-- Kakao Maps SDK (YOUR_APP_KEY 부분에 실제 앱 키를 넣으세요) -->
  <script src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=6c46d3ceb006f99f0537b7a3928c66fb&libraries=services"></script>
</body>
</html>