<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Filter Test</title>
</head>
<script>var contextPath = '${pageContext.request.contextPath}';</script>
<body>
<div class="filterContainer">
	<div class="filterBox">
		<h2>관광 지역</h2>
		<div class="regionType">
			<div id="seoul">서울</div>
		    <div id="busan">부산</div>
		    <div id="incheon">인천</div>
		    <div id="daegu">대구</div>
		    <div id="gwangju">광주</div>
		    <div id="daejeon">대전</div>
		    <div id="ulsan">울산</div>
		    <div id="jeju">제주</div>
		    <div id="gyeonggi">경기</div>
	        <div id="chungbuk">충북</div>
	        <div id="chungnam">충남</div>
	        <div id="gangwon">강원</div>
			<div id="jeonbuk">전북</div>
			<div id="jeonnam">전남</div>
			<div id="gyeongbuk">경북</div>
			<div id="gyeongnam">경남</div>
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
<script src="${pageContext.request.contextPath}/resources/js/filter.js"></script>

<style>

.filterContainer {
  display: flex; /* 가로로 배치 */
  flex-direction: column; /* 세로 방향으로 정렬 (필터 박스와 검색 박스) */
  width: 300px; /* 적절한 너비 설정 */
  border: 1px solid #ccc; /* 테두리 추가 */
  padding: 10px; /* 내부 여백 추가 */
  box-sizing: border-box; /* padding과 border가 width에 포함되도록 설정 */
}

.filterBox {
  border-bottom: 1px solid #ccc; /* 필터 박스 하단 테두리 */
  padding-bottom: 10px; /* 하단 여백 추가 */
  margin-bottom: 10px; /* 하단 마진 추가 */
}

.filterBox h2 {
  font-size: 1.2em; /* 제목 크기 조절 */
  margin-bottom: 5px; /* 제목 하단 여백 */
}

.regionType, .tourType {
  display: grid; /* 그리드 레이아웃 사용 */
  grid-template-columns: repeat(4, 1fr); /* 4개의 동일한 너비의 열 생성 */
  gap: 5px; /* 그리드 아이템 간 간격 */
  margin-bottom: 10px; /* 하단 여백 추가 */
}

.regionType div, .tourType div {
  border: 1px solid #ccc; /* 버튼 테두리 */
  padding: 5px; /* 버튼 내부 여백 */
  text-align: center; /* 텍스트 가운데 정렬 */
  cursor: pointer; /* 마우스 커서 변경 */
  border-radius: 5px; /* 모서리 둥글게 */
  font-size: 0.9em;
}

.buttonBox {
  display: flex; /* 가로로 배치 */
  justify-content: space-between; /* 양쪽 끝으로 정렬 */
}

.buttonBox div {
  border: 1px solid #ccc; /* 버튼 테두리 */
  padding: 5px 15px; /* 버튼 내부 여백 */
  cursor: pointer; /* 마우스 커서 변경 */
  border-radius: 20px; /* 둥근 버튼 형태 */
  font-size: 0.9em;
}

.searchBox {
  border: 1px solid #ccc; /* 검색 박스 테두리 */
  padding: 10px; /* 내부 여백 */
  text-align: center; /* 텍스트 가운데 정렬 */
  border-radius: 20px; /* 둥근 테두리 */
  cursor: pointer;
}
</style>


</html>