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
			<div id="seoul">서울특별시
			    <div id="gangbuk">강북구</div>
			    <div id="gangdong">강동구</div>
			    <div id="gangnam">강남구</div>
			    <div id="gangseo">강서구</div>
			    <div id="guro">구로구</div>
			    <div id="geumcheon">금천구</div>
			    <div id="nowon">노원구</div>
			    <div id="dobong">도봉구</div>
			    <div id="dongdaemun">동대문구</div>
			    <div id="dongjak">동작구</div>
			    <div id="mapo">마포구</div>
			    <div id="seodaemun">서대문구</div>
			    <div id="seocho">서초구</div>
			    <div id="seongdong">성동구</div>
			    <div id="seongbuk">성북구</div>
			    <div id="songpa">송파구</div>
			    <div id="yangcheon">양천구</div>
			    <div id="yeongdeungpo">영등포구</div>
			    <div id="yongsan">용산구</div>
			    <div id="eunpyeong">은평구</div>
			    <div id="jongno">종로구</div>
			    <div id="jung">중구</div>
			    <div id="jungnang">중랑구</div>
			</div>
			
			<div id="busan">부산광역시
			    <div id="gangseo">강서구</div>
			    <div id="geumjeong">금정구</div>
			    <div id="gijang">기장군</div>
			    <div id="nam">남구</div>
			    <div id="dong">동구</div>
			    <div id="dongnae">동래구</div>
			    <div id="busanjin">부산진구</div>
			    <div id="buk">북구</div>
			    <div id="sasang">사상구</div>
			    <div id="saha">사하구</div>
			    <div id="seo">서구</div>
			    <div id="suyeong">수영구</div>
			    <div id="yeonje">연제구</div>
			    <div id="jung">중구</div>
			    <div id="haeundae">해운대구</div>
			    <div id="yeongdo">영도구</div>
			</div>
			
			<div id="daegu">대구광역시
			    <div id="dalseo">달서구</div>
			    <div id="dalseong">달성군</div>
			    <div id="dong">동구</div>
			    <div id="buk">북구</div>
			    <div id="seo">서구</div>
			    <div id="suseong">수성구</div>
			    <div id="nam">남구</div>
			    <div id="jung">중구</div>
			</div>
			
			<div id="incheon">인천광역시
			    <div id="ganghwa">강화군</div>
			    <div id="gyeyang">계양구</div>
			    <div id="namdong">남동구</div>
			    <div id="dong">동구</div>
			    <div id="seo">서구</div>
			    <div id="ongjin">옹진군</div>
			    <div id="yeonsu">연수구</div>
			    <div id="bupyeong">부평구</div>
			    <div id="jung">중구</div>
			</div>
			
			<div id="gwangju">광주광역시
			    <div id="gwangsan">광산구</div>
			    <div id="nam">남구</div>
			    <div id="dong">동구</div>
			    <div id="buk">북구</div>
			    <div id="seo">서구</div>
			</div>
			
			<div id="daejeon">대전광역시
			    <div id="daedeok">대덕구</div>
			    <div id="dong">동구</div>
			    <div id="seo">서구</div>
			    <div id="yuseong">유성구</div>
			    <div id="jung">중구</div>
			</div>
			
			<div id="ulsan">울산광역시
			    <div id="dong">동구</div>
			    <div id="buk">북구</div>
			    <div id="jung">중구</div>
			    <div id="nam">남구</div>
			    <div id="ulju">울주군</div>
			</div>
			
			<div id="sejong">세종특별자치시</div>

		    <div id="jeju">제주특별자치도
			    <div id="jeju-city">제주시</div>
			    <div id="seogwipo">서귀포시</div>
			</div>
			
		    <div id="gyeonggi">경기도
			    <div id="gapyeong">가평군</div>
			    <div id="goyang">고양시</div>
			    <div id="guri">구리시</div>
			    <div id="gunpo">군포시</div>
			    <div id="gwangmyeong">광명시</div>
			    <div id="gwangju">광주시</div>
			    <div id="guri">구리시</div>
			    <div id="gimpo">김포시</div>
			    <div id="namyangju">남양주시</div>
			    <div id="dongducheon">동두천시</div>
			    <div id="bucheon">부천시</div>
			    <div id="seongnam">성남시</div>
			    <div id="suwon">수원시</div>
			    <div id="siheung">시흥시</div>
			    <div id="anseong">안성시</div>
			    <div id="anyang">안양시</div>
			    <div id="yangju">양주시</div>
			    <div id="yangpyeong">양평군</div>
			    <div id="yeoju">여주시</div>
			    <div id="yeoncheon">연천군</div>
			    <div id="osam">오산시</div>
			    <div id="yongin">용인시</div>
			    <div id="uiwang">의왕시</div>
	    		<div id="uijeongbu">의정부시</div>
			    <div id="icheon">이천시</div>
			    <div id="paju">파주시</div>
			    <div id="pyeongtaek">평택시</div>
			    <div id="pocheon">포천시</div>
			    <div id="hanam">하남시</div>
			    <div id="hwaseong">화성시</div>
			</div>
			
			<div id="gangwon">강원도
			    <div id="gangneung">강릉시</div>
			    <div id="goesan">고성군</div>
			    <div id="donghae">동해시</div>
			    <div id="samcheok">삼척시</div>
			    <div id="sokcho">속초시</div>
			    <div id="yanggu">양구군</div>
			    <div id="yangyang">양양군</div>
			    <div id="wonju">원주시</div>
			    <div id="inje">인제군</div>
			    <div id="jeongseon">정선군</div>
			    <div id="cheorwon">철원군</div>
			    <div id="chuncheon">춘천시</div>
			    <div id="taebaek">태백시</div>
			    <div id="hongcheon">홍천군</div>
			    <div id="hoengseong">횡성군</div>
			</div>
			
	        <div id="chungbuk">충청북도
			    <div id="goesan">괴산군</div>
			    <div id="danyang">단양군</div>
			    <div id="boeun">보은군</div>
			    <div id="sangdang">상당구</div>
			    <div id="seowon">서원구</div>
			    <div id="jecheon">제천시</div>
			    <div id="jincheon">진천군</div>
			    <div id="cheongju">청주시</div>
			    <div id="chungju">충주시</div>
			    <div id="eumseong">음성군</div>
			    <div id="okcheon">옥천군</div>
			    <div id="yeongdong">영동군</div>
			</div>
			
	        <div id="chungnam">충청남도
			    <div id="gongju">공주시</div>
			    <div id="geumsan">금산군</div>
			    <div id="nonsan">논산시</div>
			    <div id="dangjin">당진시</div>
			    <div id="boryeong">보령시</div>
			    <div id="seosan">서산시</div>
			    <div id="seocheon">서천군</div>
			    <div id="asansi">아산시</div>
			    <div id="yesan">예산군</div>
			    <div id="cheonansi">천안시</div>
			    <div id="cheongyang">청양군</div>
			    <div id="hongseong">홍성군</div>
			    <div id="taean">태안군</div>
			</div>
			
	        <div id="jeonbuk">전라북도
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
			
			<div id="jeonnam">전라남도
			    <div id="gangjin">강진군</div>
			    <div id="goheung">고흥군</div>
			    <div id="gokseong">곡성군</div>
			    <div id="gwangyang">광양시</div>
			    <div id="gurye">구례군</div>
			    <div id="naju">나주시</div>
			    <div id="damyang">담양군</div>
			    <div id="muan">무안군</div>
			    <div id="mokpo">목포시</div>
			    <div id="boseong">보성군</div>
			    <div id="suncheon">순천시</div>
			    <div id="sinan">신안군</div>
			    <div id="yeosu">여수시</div>
			    <div id="yeonggwang">영광군</div>
			    <div id="yeongam">영암군</div>
			    <div id="wando">완도군</div>
			    <div id="jangseong">장성군</div>
			    <div id="jangheung">장흥군</div>
			    <div id="haenam">해남군</div>
			    <div id="hwasun">화순군</div>
			</div>
			
			<div id="gyeongbuk">경상북도
			    <div id="gimcheon">김천시</div>
			    <div id="mungyeong">문경시</div>
			    <div id="sangju">상주시</div>
			    <div id="andong">안동시</div>
			    <div id="yeongcheon">영천시</div>
			    <div id="yeongju">영주시</div>
			    <div id="yeongdeok">영덕군</div>
			    <div id="uljin">울진군</div>
			    <div id="cheongdo">청도군</div>
			    <div id="cheongsong">청송군</div>
			    <div id="goryeong">고령군</div>
			    <div id="gungju">경주시</div>
			    <div id="gunwi">군위군</div>
			    <div id="seongju">성주군</div>
			    <div id="uiseong">의성군</div>
			    <div id="chilgok">칠곡군</div>
			    <div id="pohang">포항시</div>
			</div>
			
			<div id="gyeongnam">경상남도
			    <div id="geoje">거제시</div>
			    <div id="geochang">거창군</div>
			    <div id="goseong">고성군</div>
			    <div id="gimhae">김해시</div>
			    <div id="namhae">남해군</div>
			    <div id="miryang">밀양시</div>
			    <div id="sacheon">사천시</div>
			    <div id="yangsan">양산시</div>
			    <div id="changnyeong">창녕군</div>
			    <div id="changwon">창원시</div>
			    <div id="tongyeong">통영시</div>
			    <div id="hadong">하동군</div>
			    <div id="haman">함안군</div>
			    <div id="hamyang">함양군</div>
			    <div id="hapcheon">합천군</div>
			    <div id="jinju">진주시</div>
			    <div id="uleung">울릉군</div>
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

<style>
body {
    font-family: Arial, sans-serif;
    display: flex;
    justify-content: center;
    align-items: flex-start;
    flex-direction: column;
    min-height: 100vh;
    margin: 0;
    background-color: #f4f4f4;
}

a {
    text-decoration: none;
    font-size: 16px;
    color: #007bff;
    margin: 20px;
}

.container {
    width: 100%;
    max-width: 600px;
    margin: 20px 0;
    border: 1px solid #ddd;
    background-color: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.image-placeholder {
    width: 100%;
    height: 200px;
    border: 1px solid #ddd;
    background-color: #f0f0f0;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-bottom: 20px;
    position: relative;
}

.image-placeholder img {
    max-width: 100%;
    max-height: 100%;
    object-fit: cover;
    border-radius: 4px;
}

.content {
    display: flex;
    flex-direction: column;
}

.title {
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 10px;
    color: #333;
}

.overview {
    font-size: 16px;
    color: #555;
    margin-bottom: 20px;
}

.buttons {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
}

#contentTypeId, #cat1, #addr1, #cat3 {
    font-size: 14px;
    color: #007bff;
    cursor: pointer;
    transition: color 0.3s ease;
}

#contentTypeId:hover, #cat1:hover, #addr1:hover, #cat3:hover {
    color: #0056b3;
}

        .filter-container {
            font-family: 'Arial', sans-serif;
            max-width: 800px;
            margin: 20px auto;
            padding: 20px;
        }

        .filter-section {
            display: flex;
            gap: 20px;
            margin-bottom: 20px;
        }

        .filter-group {
            flex: 1;
            border: 1px solid #ddd;
            padding: 10px;
        }

        .filter-title {
            font-weight: bold;
            margin-bottom: 10px;
            padding-bottom: 5px;
            border-bottom: 1px solid #eee;
        }

        .filter-options {
            display: flex;
            flex-direction: column;
            gap: 8px;
            max-height: 400px;
            overflow-y: auto;
        }

        .filter-option {
            padding: 8px 12px;
            background-color: #f5f5f5;
            border: 1px solid #ddd;
            border-radius: 4px;
            cursor: pointer;
            transition: all 0.2s;
        }

        .filter-option:hover {
            background-color: #e9e9e9;
        }

        .filter-option.selected {
            background-color: #007bff;
            color: white;
            border-color: #0056b3;
        }

        .sub-region {
            margin-top: 10px;
            padding-top: 10px;
            border-top: 1px solid #eee;
            display: none;
        }

        .sub-region.visible {
            display: block;
        }

        .action-buttons {
            display: flex;
            gap: 10px;
            justify-content: flex-end;
        }

        .action-button {
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: all 0.2s;
        }

        .select-button {
            background-color: #f8f9fa;
            border: 1px solid #ddd;
        }

        .reset-button {
            background-color: #6c757d;
            color: white;
        }

        .action-button:hover {
        	background-color: red;
            opacity: 0.5;
        }

        #selected-filters {
            margin-top: 20px;
            padding: 10px;
            background-color: #f8f9fa;
            border-radius: 4px;
        }

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