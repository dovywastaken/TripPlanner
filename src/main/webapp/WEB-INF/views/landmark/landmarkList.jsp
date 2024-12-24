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

    </style>
</head>
<body>
<a href="${pageContext.request.contextPath}">Home</a>
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