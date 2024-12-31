<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시글 작성</title>
    <style>
    
        body {
            font-family: Arial, sans-serif;
            background-color: #fff;
            margin: 0;
            padding: 20px;
            display : flex;
            flex-direction : column;
        }

        .container {
            display: flex;
            width: 100%;
        }

        .left {
            width: 20%;
            background-color: #f9f9f9;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .my-list {
            margin-bottom: 20px;
        }

        .my-list p {
            font-size: 16px;
            color: #333;
        }

        .my-list button {
            width: 100%;
            background-color: #4CAF50;
            color: white;
            padding: 12px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .center {
            width: 55%;
            padding: 20px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .center h1 {
            font-size: 24px;
            margin-bottom: 20px;
        }

        .center label {
            font-weight: bold;
            margin-bottom: 5px;
        }

        .center input[type="text"], .center textarea, select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        .center textarea {
            min-height: 150px;
        }

        .right {
            width: 20%;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 5px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .right .radio-group {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        .right .radio-group label {
            font-weight: normal;
        }

        .form-footer {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 20px;
        }

        .form-footer button {
            padding: 10px 20px;
        }

        button:hover {
            background-color: #45a049;
        }
    </style>
    <!-- 외부 JavaScript 파일 참조 -->
    <script src="<%= request.getContextPath() %>/resources/js/editor.js" defer="defer"></script>
</head>
<body>

    <div class="container">
        <!-- 왼쪽: 마이리스트 및 지도 버튼 -->
        <div class="left">
            <div class="my-list">
                <h3>마이 리스트</h3>
                <div id="myListContainer">
                    <p>저장된 장소가 없습니다.</p>
                </div>
            </div>
            <button id="open-map">지도</button>
        </div>

        <!-- 가운데: 제목 입력 및 내용 작성 -->
        <div class="center">
            <h1>게시글 작성</h1>
            <form action="/TripPlanner/postcreate" method="POST" enctype="multipart/form-data">
                <!-- 제목 -->
                <label for="title">제목</label>
                <input type="text" id="title" name="title" maxlength="20" required>

               
                <label for="editor">내용</label>
                <div id="editor" contenteditable="true"></div>

                <!-- 파일 업로드 -->
                <input type="file" id="fileImage" name="fileImg" accept="image/*" multiple>

                <!-- 내용을 동기화할 textarea (숨겨짐) -->
                <textarea id="contents" name="contents" style="display:none"></textarea>

                <button type="submit">작성</button>
            </form>
        </div>

        <!-- 오른쪽: 공개 설정 및 댓글 허용 설정 -->
        <div class="right">
            <div class="radio-group">
                <label for="isPrivate">공개:</label>
                <input type="radio" name="isPrivate" value="1" checked> 공개
                <input type="radio" name="isPrivate" value="0"> 비공개
            </div>
            <div class="radio-group">
                <label for="commentIsAllowed">댓글 허용:</label>
                <input type="radio" name="commentIsAllowed" value="1" checked> 허용
                <input type="radio" name="commentIsAllowed" value="0"> 허용 안함
            </div>
            <button type="button" id="submitBtn">수정 완료</button>
        </div>
    </div>
    
	<%@ include file="../footer.jsp" %>
</body>
</html>
