<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f7f6;
        margin: 0;
        padding: 0;
    }

    h1 {
        text-align: center;
        margin-top: 20px;
        font-size: 36px;
        color: #333;
    }

    table {
        width: 80%;
        margin: 20px auto;
        border-collapse: collapse;
        background-color: #fff;
        box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
    }

    th, td {
        border: 1px solid #ddd;
        padding: 12px;
        text-align: center;
    }

    th {
        background-color: #4CAF50;
        color: white;
        font-weight: bold;
    }

    tr:nth-child(even) {
        background-color: #f2f2f2;
    }

    tr:hover {
        background-color: #ddd;
    }

    .pagination {
        text-align: center;
        margin-top: 20px;
    }

    .pagination a {
        display: inline-block;
        padding: 8px 16px;
        margin: 0 5px;
        color: #4CAF50;
        text-decoration: none;
        border-radius: 5px;
        border: 1px solid #ddd;
        font-size: 16px;
    }

    .pagination a:hover {
        background-color: #4CAF50;
        color: white;
    }

    .pagination .active {
        background-color: #4CAF50;
        color: white;
    }

    .pagination .disabled {
        color: #ccc;
        pointer-events: none;
    }
</style>
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
</html>
