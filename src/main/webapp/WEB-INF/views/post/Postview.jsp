<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 보기</title>
<style>
    /* 기본 스타일 */
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f7f6;
        margin: 0;
        padding: 0;
    }

    .container {
        max-width: 800px;
        margin: 30px auto;
        padding: 20px;
        background-color: #fff;
        border-radius: 8px;
        box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.1);
    }

    h1 {
        text-align: center;
        font-size: 24px;
        margin-bottom: 20px;
    }

    /* 게시글 상세 내용 */
    .post-details {
        font-size: 0.9em;
        color: #555;
        margin-bottom: 20px;
    }

    .post-details p {
        margin: 5px 0;
    }

    .post-content {
        font-size: 1.2em;
        color: #333;
        line-height: 1.8;
        padding: 20px;
        background-color: #f9f9f9;
        border-radius: 5px;
    }

    /* 댓글 입력 폼 */
    .comment-box {
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 20px;
        background-color: #f9f9f9;
        border-radius: 8px;
        box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
        margin-top: 20px;
    }

    .comment-box textarea {
        width: 100%;
        max-width: 600px;
        padding: 12px;
        margin-bottom: 15px;
        border-radius: 5px;
        border: 1px solid #ddd;
        font-size: 14px;
        resize: vertical;
        background-color: #fefefe;
    }

    .comment-box button {
        padding: 10px 20px;
        background-color: #4CAF50;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 16px;
        transition: background-color 0.3s ease;
    }

    .comment-box button:hover {
        background-color: #45a049;
    }

    /* 로그인 유도 메시지 */
    .login-prompt {
        display: flex;
        flex-direction: column;
        align-items: center;
        padding: 20px;
        background-color: #f9f9f9;
        border-radius: 8px;
        box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
        margin-top: 20px;
    }

    .login-prompt textarea {
        width: 100%;
        max-width: 600px;
        padding: 12px;
        margin-bottom: 15px;
        border-radius: 5px;
        border: 1px solid #ddd;
        font-size: 14px;
        resize: vertical;
        background-color: #f0f0f0;
    }

    .login-prompt button {
        padding: 10px 20px;
        background-color: #007BFF;
        color: white;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 16px;
        transition: background-color 0.3s ease;
    }

    .login-prompt button:hover {
        background-color: #0056b3;
    }

    /* 버튼 그룹 (목록, 삭제, 수정) */
    .button-group {
        text-align: center;
        margin-bottom: 20px;
    }

    .button-group a {
        text-decoration: none;
        background-color: #4CAF50;
        color: white;
        padding: 10px 20px;
        border-radius: 5px;
        margin: 0 10px;
    }

    .button-group a:hover {
        background-color: #45a049;
    }

</style>

</head>
<body>
    <div class="container">
        <h1>${onepost.title}</h1>
        <div>
            작성자: ${onepost.id}<br>
            좋아요: <span id="postLikes">${onepost.likes}</span> 
            <button id="postLikeBtn">포스트 좋아요</button><br>
            조회수: ${onepost.view}<br>
            작성일: ${postdate}<br>
        </div>
        <p>${onepost.contents}</p>

        <c:if test="${not empty member.id}">
            <div class="comment-box">
                <form id="commentForm">
                    <textarea id="commentContent" rows="4" placeholder="댓글을 입력해주세요" required></textarea>
                    <button type="button" id="submitCommentBtn">댓글 작성</button>
                </form>
            </div>
        </c:if>

        <c:if test="${empty member.id}">
            <div class="login-prompt">
                <textarea rows="4" placeholder="댓글을 입력할 수 없습니다. 로그인해주세요" readonly></textarea>
                <button type="button" onclick="location.href='/TripPlanner/login'">로그인</button>
            </div>
        </c:if>

        <div id="commentSection"></div>
        <div id="pagination"></div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
  		var contextPath = "${pageContext.request.contextPath}";
        var postId = "${postId}";
        var sessionId = "${member.id}";
        var totalPages = ${totalPages};
    </script>
    <script src="<%= request.getContextPath() %>/resources/js/comment.js"></script>
</body>
</html>
