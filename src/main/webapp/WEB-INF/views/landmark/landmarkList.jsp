<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Landmarks</title>
</head>
<script>var contextPath = '${pageContext.request.contextPath}';</script>
<script src="${pageContext.request.contextPath}/resources/js/landmark.js"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
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


    </style>
</head>
<body>
<a href="${pageContext.request.contextPath}">Home</a>
    <div class="filter-container">
        <div class="filter-section">
            <div class="filter-group">
                <div class="filter-title">관광 타입</div>
                <div class="filter-options" id="tourType">
                    <div class="filter-option" data-value="tourist">관광지</div>
                    <div class="filter-option" data-value="culture">문화시설</div>
                    <div class="filter-option" data-value="festival">축제공연</div>
                    <div class="filter-option" data-value="leisure">레포츠</div>
                    <div class="filter-option" data-value="shopping">쇼핑</div>
                </div>
            </div>
            
            <div class="filter-group">
                <div class="filter-title">지역</div>
                <div class="filter-options" id="region">
                    <div class="filter-option" data-value="seoul">서울특별시</div>
                    <div class="filter-option" data-value="busan">부산광역시</div>
                    <div class="filter-option" data-value="daegu">대구광역시</div>
                    <div class="filter-option" data-value="incheon">인천광역시</div>
                    <div class="filter-option" data-value="gwangju">광주광역시</div>
                    <div class="filter-option" data-value="daejeon">대전광역시</div>
                    <div class="filter-option" data-value="ulsan">울산광역시</div>
                    <div class="filter-option" data-value="sejong">세종특별자치시</div>
                    <div class="filter-option" data-value="gyeonggi">경기도</div>
                    <div class="filter-option" data-value="gangwon">강원도</div>
                    <div class="filter-option" data-value="chungbuk">충청북도</div>
                    <div class="filter-option" data-value="chungnam">충청남도</div>
                    <div class="filter-option" data-value="jeonbuk">전라북도</div>
                    <div class="filter-option" data-value="jeonnam">전라남도</div>
                    <div class="filter-option" data-value="gyeongbuk">경상북도</div>
                    <div class="filter-option" data-value="gyeongnam">경상남도</div>
                    <div class="filter-option" data-value="jeju">제주특별자치도</div>
                </div>
                <div class="sub-region" id="subRegion">
                    <!-- 하위 지역은 JavaScript로 동적 생성됨 -->
                </div>
            </div>
        </div>
        
        <div class="action-buttons">
            <button class="action-button select-button">선택</button>
            <button class="action-button reset-button">초기화</button>
        </div>

        <div id="selected-filters">
            선택된 필터: <span id="filter-display"></span>
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
</html>