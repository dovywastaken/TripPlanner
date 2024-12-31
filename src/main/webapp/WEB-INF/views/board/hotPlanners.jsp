<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>인기 여행 계획</title>
</head>

<body>
    <h1>게시판</h1>
    <table>
        <thead>
            <tr>
                <th>글번호</th>
                <th>제목</th>
                <th>글쓴이</th>
                <th>작성일</th>
                <th>추천</th>
                <th>조회</th>
            </tr>
        </thead>
        <tbody>
         <c:forEach items="${Allpost}" var="All" varStatus="loop">
            <tr>
                <td>${getpostnumber.get(loop.index)}</td>
                <td><a href="/TripPlanner/postview?num=${All.p_unique}&page=${currentPage}">${All.title}</a></td>
                <td>${All.id}</td>
                <td>${date.get(loop.index)}</td>
                <td>${All.likes}</td>
                <td>${All.view}</td>
            </tr>
           </c:forEach> 
        </tbody>
    </table>

     <div class="pagination">
        <c:set var="pagination" value="${pagination}" />

        <!-- 첫 페이지 및 이전 그룹 버튼 -->
        <c:if test="${pagination.startPage > 1}">
            <a href="?page=1&type=${type}&keyword=${keyword}"><<</a>
            <a href="?page=${pagination.startPage - 1}&type=${type}&keyword=${keyword}"><</a>
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
            <a href="?page=${pagination.endPage + 1}&type=${type}&keyword=${keyword}">></a>
            <a href="?page=${pagination.totalPages}&type=${type}&keyword=${keyword}">>></a>
        </c:if>
    </div>
    
    <div>
	  <form action="${pageContext.request.contextPath}/board/search" method="get">
	    <select name="type">
	        <option value="id">글쓴이</option>
	        <option value="title">글제목</option>
	    </select>
	    <input type="text" name="keyword" required>
	    <input type="submit" value="검색">
	</form>
    </div>
    
    
</body>
<%@ include file="../footer.jsp" %>

</html>