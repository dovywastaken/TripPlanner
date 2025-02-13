<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/normalize.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board.css">
<link href="https://cdn.jsdelivr.net/gh/sun-typeface/SUITE@2/fonts/static/woff2/SUITE.css" rel="stylesheet">
</head>
<script src="https://kit.fontawesome.com/96b1ce314a.js"></script>

<body>

	<%@ include file="../header.jsp" %>
    <div id="container">
        <div id="titleBanner">추천 여행</div>
        <div id="boardContainer"> 
            <table id="table">
                <thead>
                    <tr>
                        <th class="tableHead">제목</th>
                        <th class="tableHead">글쓴이</th>
                        <th class="tableHead">작성일</th>
                        <th class="tableHead">추천</th>
                        <th class="tableHead">조회</th>
                        <!-- <th>글번호</th> -->
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${postList}" var="postList" varStatus="loop">
                    <tr>
                        <td class="tableContent"><a href="${pageContext.request.contextPath}/postView?num=${postList.p_unique}&page=${currentPage}&boardtype='S'">${postList.title}</a></td>
                        <td class="tableContent">${postList.id}</td>
                        <td class="tableContent">${date.get(loop.index)}</td>
                        <td class="tableContent">${postList.likes}</td>
                        <td class="tableContent">${postList.views}</td>
                        <!-- <td class="tableContent">${getpostnumber.get(loop.index)}</td> -->
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div id="paginationAndSearchBar">
                <div class="pagination">
                    <c:set var="pagination" value="${pagination}" />

                    <!-- 첫 페이지 및 이전 그룹 버튼 -->
                    <c:if test="${pagination.startPage > 1}">
                        <div class="pageIndicatorWrapper">
                            <a href="?page=1&type=${type}&keyword=${keyword}"><i class="fa-solid fa-angles-left"></i></a>
                            <a href="?page=${pagination.startPage - 1}&type=${type}&keyword=${keyword}"><i class="fa-solid fa-angle-left"></i></a>
                        </div>
                    </c:if>

                    <!-- 페이지 그룹 표시 -->
                    <c:forEach begin="${pagination.startPage}" end="${pagination.endPage}" var="i">
                        <c:choose>
                            <c:when test="${i == pagination.currentPage}">
                                <strong>${i}</strong>
                            </c:when>
                            <c:otherwise>
                                <a href="?page=${i}&type=${type}&keyword=${keyword}">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>

                    <!-- 다음 그룹 및 마지막 페이지 버튼 -->
                    <c:if test="${pagination.endPage < pagination.totalPages}">
                        <div class="pageIndicatorWrapper">
                            <a href="?page=${pagination.endPage + 1}&type=${type}&keyword=${keyword}"><i class="fa-solid fa-angle-right"></i></a>
                            <a href="?page=${pagination.totalPages}&type=${type}&keyword=${keyword}"><i class="fa-solid fa-angles-right"></i></a>
                        </div>
                     </c:if>
                </div>
                
                <div>
                    <form action="${pageContext.request.contextPath}/board/all/search" method="get">
                        <select name="type" id="filter">
                            <option value="id">글쓴이</option>
                            <option value="title">글제목</option>
                        </select>
                        <input type="text" name="keyword" id="searchBar" placeholder="검색" required>
                        <input type="submit" id="searchButton" value="">
                    </form>
                </div>
            </div>
        </div>
    </div>


    <aside>
        <div class="sidePanelContainer">
            <div id="myPanel">
                    <!-- 로그인한 사용자가 있을 때 보여줄 내용 -->
                    <div id="userInfo">
                        <h1>${user.nickname}</h1>
                        <h2>${user.email}</h2>
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
     <%@ include file="../footerCompact.jsp" %>
</body>

<script>
	function goBack(){window.history.back();}
    // 요일 이름 배열
    const dayNames = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'];
    
    // 월 이름 배열
    const monthNames = ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'];

    // 현재 날짜 가져오기
    const today = new Date();

    // 필요한 데이터 추출
    const year = today.getFullYear();
    const month = monthNames[today.getMonth()]; // 월은 0부터 시작
    const date = today.getDate();
    const day = dayNames[today.getDay()]; // 요일

    // 원하는 형식으로 문자열 생성
    const formattedDate = month + " " + date + "일 " + day;

    // <p> 태그에 삽입
    document.getElementById('currentDate').textContent = formattedDate;
</script>
</html>
