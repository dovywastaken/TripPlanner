<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시물 보기</title>
    <script>
         window.contextPath = '${pageContext.request.contextPath}';
    </script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/postView.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<body>
<%@ include file="../header.jsp" %>

<div id="header-title" class="banner-title">
    <div class="banner-content">
        <h1 id="titles">${onepost.title}</h1>
    </div>
</div>

    <div class="container">
        <div class="post-view">
            <span class="info-item1"><i class="fa-solid fa-user"></i> ${onepost.id} </i> <i class="fa-solid fa-clock"></i> ${postdate}</span>
            <span class="info-item2"><i class="fa-solid fa-eye"></i> 조회수: ${onepost.views}</span>
        </div>
    
        
        <div class="post-content">
            ${onepost.contents}
        </div>

        
			<div class="like-box">
			    <span class="info-item">
			        <c:if test="${onepost.id != member.email}">
			            <button id="postLikeBtn" class="like-btn">
			                <i class="fa-solid fa-thumbs-up"></i>
			            </button>
			            <span id="postLikes">${onepost.likes}</span>
			        </c:if>
			        <c:if test="${onepost.id == member.email}">
			            <span id="postLikes">
			                <i class="fa-solid fa-thumbs-up"></i> ${onepost.likes}
			            </span>
			        </c:if>
			    </span>
			</div>
        <div id="line"></div>
        
        <div class="comment-section">
       
            <div id="commentSection" class="comments-list"></div>
            <div id="pagination" class="pagination"></div>
            
                 <c:choose>
                <c:when test="${not empty member.email}">
                    <div class="comment-box">
                        <form id="commentForm">
                            <textarea id="commentContent" rows="4" placeholder="댓글을 입력해주세요" required></textarea>
                            <button type="button" id="submitCommentBtn" class="btn submit-btn">댓글 작성</button>
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="login-prompt">
                        <textarea rows="4" id="commentContent-login" placeholder="댓글을 작성하려면 로그인이 필요합니다" readonly></textarea>
                        <button type="button" onclick="location.href='/TripPlanner/login'" class="btn login-btn">로그인</button>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>


	<aside>
        <div class="sidePanelContainer">
            <div id="myPanel">
                    <!-- 로그인한 사용자가 있을 때 보여줄 내용 -->
                    <div id="userInfo">
                        <h1>${user.nickname}</h1>
                        <h2>${user.email}</h2>
                        <c:if test="${onepost.id==user.email}">
                       <span> 
                        <a href="${pageContext.request.contextPath}/postview/update?num=${onepost.p_unique}" class="btn edit-btn">수정</a>
                       <a href="${pageContext.request.contextPath}/postview/delete?num=${onepost.p_unique}" class="btn delet-btn">삭제</a>
                        </span> 
                        </c:if>
                    </div>
                    <p id="currentDate" style="text-align: center; width: 100%; color: #2C3F3C;"></p>
                    <c:if test="${user.emailCheck == 0}">
                    <p style="text-align : center; margin-top : 12px; width: 100%;">아직 이메일 인증이 안됐어요!</p>
                    	<a href="${pageContext.request.contextPath}/members/myPage" 
                    		style="outline: 2px solid #313339; 
					        background-color: #ffffff;
					        line-height: 0;
					        height: 21px;
					        width: 50%;
					        margin : 15px auto 0;
					        border: none;
					        border-radius: 34px;
					        font-size: 13px;
					        font-weight: bold;
					        display: flex;
					        justify-content: center;
					        align-items: center;
					        color: #313339;
					        text-align: center;">인증하러 가기
					    </a>
                    </c:if>
                    <hr style="border: 1px solid #F1F3F9; margin : 34px auto 10px auto; width: 80%;">

                    <div id="links"> 
                    	<c:if test="${not empty user}">
                    	<a href="${pageContext.request.contextPath}/board/myBoard">• 내 여행 계획</a>
                    	</c:if>
                        <a href="${pageContext.request.contextPath}/board/hot">• 추천 여행 계획</a>
                        <a href="${pageContext.request.contextPath}/board/all">• 전체 게시판</a>
                        <a href="${pageContext.request.contextPath}/board/festival">• 인기 축제</a>
                        <a href="${pageContext.request.contextPath}/board/tour">• 인기 관광지</a>
                        <a href="${pageContext.request.contextPath}/board/restaurant">• 인기 음식점</a>
                    </div>
                    <a href="${pageContext.request.contextPath}/members/signOut" class="signOutButton">로그아웃</a>
            </div>
            <div id="backButton" onclick="goBack()">뒤로 가기</div>
        </div>
    </aside>



  


    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        var contextPath = "${pageContext.request.contextPath}";
        var postId = "${postId}";
        var sessionId = "${member.email}";
        var totalPages = ${totalPages};
    </script>
    
    
    
    <%@ include file="../footerCompact.jsp" %>
    <script src="${pageContext.request.contextPath}/resources/js/comment.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/viewPost.js"></script>
</body>
</html>