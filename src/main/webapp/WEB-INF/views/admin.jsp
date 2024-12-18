<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 목록</title>
    
<script>
    var contextPath = '${pageContext.request.contextPath}';
</script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/adminPage.js"></script>
</head>
<body>
    <h1>어드민 페이지</h1>
    <a href="${pageContext.request.contextPath}">Home</a>

    <h2>회원 목록</h2>
    
    <form name="search" id="search" method="get" action="dashboard">
	    <input type="text" name="keyword" id="keyword" placeholder="회원 검색하기" />
	    <input type="submit" id="searchButton" value="검색" />
	</form>
    
    <button id="test">Test</button>

    <br>
    <div>
	    <table border="1" cellspacing="0" cellpadding="5" id="member">
	        <thead>
	            <tr>
	            	<th onclick="sortTable(1)" style="cursor: pointer;">이름 &darr;</th>
	                <th onclick="sortTable(2)" style="cursor: pointer;">ID &darr;</th>
	                <th onclick="sortTable(3)" style="cursor: pointer;">Email &darr;</th>
	                <th onclick="sortTable(4)" style="cursor: pointer;">지역 &darr;</th>
	                <th onclick="sortTable(5)" style="cursor: pointer;">성별 &darr;</th>
	                <th onclick="sortTable(6)" style="cursor: pointer;">생년월일 &darr;</th>
	                <th>전화번호</th>
	            </tr>
	        </thead>
	        <tbody>
	             	<c:choose>
                    <c:when test="${not empty memberList}">
                        <c:forEach var="member" items="${memberList}">
                            <tr>
                                <td>${member.name}</td>
                                <td>${member.id}</td>
                                <td>${member.email}</td>
                                <td>${member.region}</td>
                                <td>${member.sex}</td>
                                <td>${member.birthday}</td>
                                <td>${member.phone1}-${member.phone2}-${member.phone3}</td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="7">회원 정보가 없습니다.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
	        </tbody>
	    </table>
    </div>
    
    <div>
	    <c:forEach var="i" begin="1" end="${totalPages}">
            <a href="${pageContext.request.contextPath}/admin/dashboard?page=${i}&keyword=${keyword}">${i}</a>
        </c:forEach>
	</div>

    
</body>
</html>
