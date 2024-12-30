<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TripPlanner</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        .form-container {
            max-width: 600px;
            margin: auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #f9f9f9;
        }
        h1 {
            text-align: center;
            color: red;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group p {
            font-size: 16px;
            margin: 5px 0;
        }
        .form-group label {
            display: inline-block;
            width: 100px;
            font-weight: bold;
        }
        .form-group input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: #dc3545;
            border: none;
            color: white;
            font-size: 16px;
            cursor: pointer;
            border-radius: 4px;
        }
        .form-group input[type="submit"]:hover {
            background-color: #c82333;
        }
        a {
            display: block;
            text-align: center;
            margin-bottom: 20px;
            color: #007bff;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

	<%@ include file="../header.jsp" %>

    <div class="form-container">
        <h1>정말로 삭제하시겠습니까?!?!?!?!?</h1>
        <a href="${pageContext.request.contextPath}">Home</a>
        
        <form method="POST" action="deleteMember">
            <div class="form-group">
                <p>아이디: ${member.id}</p>
            </div>
            <div class="form-group">
                <p>이름: ${member.name}</p>
            </div>
            <div class="form-group">
                <p>이메일: ${member.email}</p>
            </div>
            <div class="form-group">
                <p>지역: ${member.region}</p>
            </div>
            <div class="form-group">
                <p>전화번호: ${member.phone1}-${member.phone2}-${member.phone3}</p>
            </div>
            <div class="form-group">
                <p>생년월일: ${member.birthday}</p>
            </div>
            <div class="form-group">
                <input type="submit" value="회원 삭제">
            </div>
        </form>
    </div>
    <%@ include file="../footer.jsp" %>
</body>
</html>
