<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 목록</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .container {
            max-width: 1200px;
            margin: auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #f9f9f9;
        }
        h1, h2 {
            text-align: center;
        }
        .form-container {
            margin-bottom: 20px;
            text-align: center;
        }
        .form-container input[type="text"] {
            width: 300px;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .form-container input[type="submit"] {
            padding: 8px 16px;
            background-color: #28a745;
            border: none;
            color: white;
            cursor: pointer;
            border-radius: 4px;
        }
        .form-container input[type="submit"]:hover {
            background-color: #218838;
        }
        .table-container {
            overflow-x: auto;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        table, th, td {
            border: 1px solid #ccc;
        }
        th, td {
            text-align: left;
            padding: 10px;
        }
        th {
            cursor: pointer;
            background-color: #007bff;
            color: white;
        }
        th:hover {
            background-color: #0056b3;
        }
        td p {
            margin: 0;
        }
        .pagination {
            text-align: center;
        }
        .pagination a {
            margin: 0 5px;
            padding: 8px 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            text-decoration: none;
            color: #007bff;
        }
        .pagination a:hover {
            background-color: #ddd;
        }
        .no-data {
            text-align: center;
            color: red;
        }
    </style>
    <script>
        var contextPath = '${pageContext.request.contextPath}';
    </script>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/adminPage.js"></script>
</head>
<body>
    <div class="container">
        <h1>어드민 페이지</h1>
        <a href="${pageContext.request.contextPath}">Home</a>

        <h2>회원 목록</h2>

        <div class="form-container">
            <form name="search" id="search" method="get" action="dashboard">
                <input type="text" name="keyword" id="keyword" placeholder="회원 검색하기" />
                <input type="submit" id="searchButton" value="검색" />
            </form>
        </div>

        <div class="table-container">
            <table id="member">
                <thead>
                    <tr>
                        <th>이름</th>
                        <th>ID</th>
                        <th>Email</th>
                        <th>지역</th>
                        <th>성별</th>
                        <th>생년월일</th>
                        <th>전화번호</th>
                        <th>가입일</th>
                        <th>최근로그인</th>
                        <th>이메일 인증</th>
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
                                    <td>${member.registerDate}</td>
                                    <td>${member.loginDate}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${member.emailCheck == 1}">
                                                <p>o</p>
                                            </c:when>
                                            <c:otherwise><p>x</c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="10" class="no-data">회원 정보가 없습니다.</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>

        <div class="pagination">
            <c:forEach var="i" begin="1" end="${totalPages}">
                <a href="${pageContext.request.contextPath}/admin/dashboard?page=${i}&keyword=${keyword}">${i}</a>
            </c:forEach>
        </div>
    </div>
</body>
</html>
