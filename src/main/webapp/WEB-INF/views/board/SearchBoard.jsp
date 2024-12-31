<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>검색 결과 게시판</title>
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
</style>
</head>
<body>
    <!-- 제목 -->
    <h1>검색 결과 게시판</h1>
    <p style="text-align:center; font-size: 18px;">'${keyword}'에 대한 검색 결과입니다.</p>

    <!-- 검색 폼 -->
    <form action="${pageContext.request.contextPath}/board/search" method="GET" style="text-align: center;">
        <select name="type">
            <option value="id" ${type == 'id' ? 'selected' : ''}>글쓴이</option>
            <option value="title" ${type == 'title' ? 'selected' : ''}>글제목</option>
        </select>
        <input type="text" name="keyword" value="${keyword}" required>
        <input type="submit" value="검색">
    </form>

    <!-- 게시판 테이블 -->
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
            <!-- 검색 결과가 없을 경우 -->
            <c:if test="${empty Allpost}">
                <tr>
                    <td colspan="6">검색 결과가 없습니다.</td>
                </tr>
            </c:if>
            <!-- 검색 결과가 있을 경우 -->
            <c:forEach items="${Allpost}" var="post" varStatus="loop">
                <tr>
                    <td>${getpostnumber.get(loop.index)}</td>
                    <td><a href="/TripPlanner/postview?num=${post.p_unique}&page=${currentPage}">${post.title}</a></td>
                    <td>${post.id}</td>
                    <td>${date.get(loop.index)}</td>
                    <td>${post.likes}</td>
                    <td>${post.view}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="pagination">
        <c:set var="pagination" value="${pagination}" />

       
        <c:if test="${pagination.startPage > 1}">
            <a href="?page=1&type=${type}&keyword=${keyword}"><<</a>
            <a href="?page=${pagination.startPage - 1}&type=${type}&keyword=${keyword}"><</a>
        </c:if>

     
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

     
        <c:if test="${pagination.endPage < pagination.totalPages}">
            <a href="?page=${pagination.endPage + 1}&type=${type}&keyword=${keyword}">></a>
            <a href="?page=${pagination.totalPages}&type=${type}&keyword=${keyword}">>></a>
        </c:if>
    </div>
    </div>
</body>
</html>
