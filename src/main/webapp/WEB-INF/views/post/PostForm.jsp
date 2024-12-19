<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <form action="/TripPlanner/postcreate" method="POST">
        <!-- 제목 -->
        <label for="title">제목</label>
        <input type="text" id="title" name="title" maxlength="20"required>

        <!-- 내용 -->
        <label for="contents">내용</label>
        <textarea id="contents" name="contents" rows="28"  required ></textarea>

        <!-- 지역 -->
        <label for="region">지역</label>
        <input type="text" id="region" name="region" maxlength="10" >

       
	    <span>공개 :<input type="radio" name="isPrivate" value="1" checked="checked">비공개 :<input type="radio" name="isPrivate" value="0"></span>
		<span>댓글 허용 :<input type="radio" name="commentIsAllowed" value="1" checked="checked"> 허용 안함 :<input type="radio" name="commentIsAllowed" value="0"></span>	
   

        <!-- 만족도 -->
        <label for="satisfaction">만족도 (0~5)</label><input type="number" id="satisfaction" name="satisfaction" min="1" max="5">

        <br>
        <button type="submit">작성</button>
    </form>
</body>
</html>