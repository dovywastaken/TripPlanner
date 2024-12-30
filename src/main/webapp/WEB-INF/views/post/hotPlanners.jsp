<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>인기 여행 계획</title>
</head>
<body>

	<h1>인기 플래너 게시판 입니다.</h1>

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
         <c:forEach var="postList" items="${Allpost} varStatus='loop']">
            <tr>
                <td>${getpostnumber.get(loop.index)}</td>
                <td><a href="/TripPlanner/postview?num=${postList.p_unique}&page=${currentPage}">${postList.title}</a></td>
                <td>${postList.id}</td>
                <td>${date.get(loop.index)}</td>
                <td>${postList.likes}</td>
                <td>${postList.view}</td>
            </tr>
           </c:forEach> 
        </tbody>
    </table>

    
</body>
<%@ include file="../footer.jsp" %>
</body>
</html>