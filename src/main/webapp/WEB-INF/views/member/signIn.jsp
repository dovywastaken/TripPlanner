<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TripPlanner - 로그인</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .form-container {
            max-width: 400px;
            margin: auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #f9f9f9;
        }
        h2 {
            text-align: center;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }
        .form-group input[type="text"],
        .form-group input[type="password"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .form-group input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #28a745;
            border: none;
            color: white;
            font-size: 16px;
            cursor: pointer;
            border-radius: 4px;
        }
        .form-group input[type="submit"]:hover {
            background-color: #218838;
        }
        .error-message {
            color: red;
            text-align: center;
        }
    </style>
</head>

<body>
	<%@ include file="../header.jsp" %>

    <div class="form-container">
        <h2>로그인</h2>
        <a href="${pageContext.request.contextPath}">Home</a>
        
        <!-- 로그인 폼 -->
        <form:form modelAttribute="member" method="POST" action="signIn">
            <div class="form-group">
                <label for="id">아이디:</label>
                <form:input path="id" id="id" placeholder="아이디"/>
            </div>
            <div class="form-group">
                <label for="pw">비밀번호:</label>
                <form:input path="pw" id="pw" placeholder="비밀번호" type="password"/>
            </div>
            <div class="form-group">
                <input type="submit" id="submitButton" value="로그인">
            </div>
        </form:form>

        <!-- 에러 메시지 표시 -->
        <c:if test="${not empty EmptyForm}">
            <script>
                alert("${EmptyForm}");
            </script>
        </c:if>
        <c:if test="${not empty loginError}">
            <script>
                alert("${loginError}");
            </script>
        </c:if>
    </div>
    <%@ include file="../footer.jsp" %>
</body>
</html>
