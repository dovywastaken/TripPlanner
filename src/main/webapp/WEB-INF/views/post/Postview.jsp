<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시물 보기</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/viewpost.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <div class="container">
        <!-- 상단 버튼 그룹 -->
        <div class="top-button-group">
            <a href="/TripPlanner/board" class="btn list-btn">목록</a>
            <c:if test="${onepost != null and member != null and onepost.id == member.id}">
                <a href="/TripPlanner/postview/update?num=${onepost.p_unique}" class="btn edit-btn">수정</a>
                <a href="/TripPlanner/postview/delete?num=${onepost.p_unique}" class="btn delete-btn">삭제</a>
            </c:if>
        </div>

        <!-- 게시글 헤더 -->
        <div class="post-header">
            <h1>${onepost.title}</h1>
            <div class="post-info">
                <span class="info-item"><i class="user-icon"></i>${onepost.id}</span>
                <span class="info-item">
                    <i class="like-icon"></i>
                    <span id="postLikes">${onepost.likes}</span>
                    <c:if test="${onepost.id != member.id}">
                        <button id="postLikeBtn" class="like-btn">좋아요</button>
                    </c:if>
                </span>
                <span class="info-item"><i class="view-icon"></i>조회 ${onepost.view}</span>
                <span class="info-item"><i class="date-icon"></i>${postdate}</span>
            </div>
        </div>

        <!-- 게시글 내용 -->
        <div class="post-content">
            ${onepost.contents}
        </div>

        <!-- 댓글 영역 -->
        <div class="comment-section">
            <h3>댓글</h3>
            <c:choose>
                <c:when test="${not empty member.id}">
                    <div class="comment-box">
                        <form id="commentForm">
                            <textarea id="commentContent" rows="4" placeholder="댓글을 입력해주세요" required></textarea>
                            <button type="button" id="submitCommentBtn" class="btn submit-btn">댓글 작성</button>
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="login-prompt">
                        <textarea rows="4" placeholder="댓글을 작성하려면 로그인이 필요합니다" readonly></textarea>
                        <button type="button" onclick="location.href='/TripPlanner/login'" class="btn login-btn">로그인</button>
                    </div>
                </c:otherwise>
            </c:choose>

            <div id="commentSection" class="comments-list"></div>
            <div id="pagination" class="pagination"></div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        var contextPath = "${pageContext.request.contextPath}";
        var postId = "${postId}";
        var sessionId = "${member.id}";
        var totalPages = ${totalPages};
    </script>
    <script src="${pageContext.request.contextPath}/resources/js/comment.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/viewpost.js"></script>
</body>
</html>