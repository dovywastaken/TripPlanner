<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 작성</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        form {
            max-width: 600px;
            margin: auto;
            display: flex;
            flex-direction: column;
        }
        label {
            font-weight: bold;
            margin-top: 10px;
        }
        input[type="text"], textarea, select {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>

    <h1>게시글 작성</h1>
    <form action="/TripPlanner/postview/updatePost?num=${result.p_unique}" method="POST">
        <!-- 작성자 ID -->
        <label for="id">작성자 ID</label>
        <input type="text" id="id" name="id" maxlength="5" value="${result.id}" required>
        <!-- 제목 -->
        <label for="title">제목</label>
        <input type="text" id="title" name="title" maxlength="20" value="${result.title}" required>

        <!-- 내용 -->
        <label for="contents">내용</label>
        <textarea id="contents" name="contents" rows="5"  required >${result.contents}</textarea>

        <!-- 지역 -->
        <label for="region">지역</label>
        <input type="text" id="region" name="region" maxlength="10" value="${result.region}">

        <!-- 비공개 여부 -->
        <c:if test="${result.isPrivate}" >
        <p>공개 여부 : 공개</p>
        </c:if>
        <c:if test="${!result.isPrivate}" >
        <p>공개 여부 : 비공개</p>
        </c:if>
	     <c:if test="${result.isPrivate}">
	  		  <span>공개 :<input type="radio" name="isPrivate" value="1" checked="checked">비공개 :<input type="radio" name="isPrivate" value="0"></span>
		</c:if>
		<c:if test="${!result.isPrivate}">
	    	<span>공개 :<input type="radio" name="isPrivate" value="1" >비공개 :<input type="radio" name="isPrivate" value="0" checked="checked"></span>
		</c:if>
        <!-- 댓글 허용 여부 표시 -->
		<c:if test="${result.commentIsAllowed}" >
		    <p>댓글 허용 여부 : 허용</p>
		</c:if>
		<c:if test="${!result.commentIsAllowed}" >
		    <p>댓글 허용 여부 : 비허용</p>
		</c:if>
		
		<!-- 댓글 허용 여부 선택 라디오 버튼 -->
		<c:if test="${result.commentIsAllowed}">
		    <span>
		        허용 : <input type="radio" name="commentIsAllowed" value="1" checked="checked">
		        비허용 : <input type="radio" name="commentIsAllowed" value="0">
		    </span>
		</c:if>
		<c:if test="${!result.commentIsAllowed}">
		    <span>
		        허용 : <input type="radio" name="commentIsAllowed" value="1">
		        비허용 : <input type="radio" name="commentIsAllowed" value="0" checked="checked">
		    </span>
		</c:if>

        <!-- 만족도 -->
        <label for="satisfaction">만족도 (0~100)</label>
        <input type="number" id="satisfaction" name="satisfaction" value="${result.satisfaction}" min="0" max="100">

        <!-- 작성 버튼 -->
        <button type="submit">작성</button>
    </form>
</body>
</html>