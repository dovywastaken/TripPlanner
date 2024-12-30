<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
    <title>비밀번호 변경</title>
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
        h1 {
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
        .form-group input[type="password"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .form-group div {
            margin-top: 5px;
            color: red;
            font-size: 12px;
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
    <script>var contextPath = '${pageContext.request.contextPath}';</script>
    <script src="${pageContext.request.contextPath}/resources/js/pwCheck.js"></script>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>

	<%@ include file="../header.jsp" %>

    <div class="form-container">
        <a href="${pageContext.request.contextPath}">Home</a>
        <h1>비밀번호 변경</h1>
        <form:form id="pwChangeForm" method="post" modelAttribute="member" action="pwChange">
            <div class="form-group">
                <label for="currentPw">현재 비밀번호</label>
                <input type="password" id="pw" name="cpw" required>
                <div id="currentPwMessage"></div>
            </div>
            <div class="form-group">
                <label for="pw1">비밀번호:</label>
                <form:input id="pw1" name="pw1" path="pw" placeholder="비밀번호" type="password"/>
                <div id="pwValidationMessage"></div>
            </div>
            <div class="form-group">
                <label for="pw2">비밀번호 확인:</label>
                <input id="pw2" placeholder="비밀번호 확인" type="password"/>
                <div id="pwCheck"></div>
            </div>
            <div class="form-group">
                <input type="submit" id="pwChange" value="변경">
            </div>
        </form:form>
    </div>
    <%@ include file="../footer.jsp" %>
</body>
</html>
