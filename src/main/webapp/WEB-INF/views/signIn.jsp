<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TripPlanner</title>
<script>var contextPath = '${pageContext.request.contextPath}';</script>
<script src="${pageContext.request.contextPath}/resources/js/signUp.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>

</head>
<body>
    <h2>로그인</h2>
    <a href="${pageContext.request.contextPath}">Home</a>
    
    <form:form modelAttribute="member" method="POST" action="${pageContext.request.contextPath}/members/signIn">
        <p>아이디 : <form:input path="id" placeholder="아이디" required="required"/></p>
        <p>비밀번호 : <form:input path="pw" placeholder="비밀번호" required="required" type="password"/></p>
        <input type="submit" value="로그인">
    </form:form>
    
    <a href="#">Naver</a>
    <a href="#">Kakao</a>

    <%
        if (request.getAttribute("loginError") != null) {
            String errorMessage = (String) request.getAttribute("loginError");
    %>
        <script>
            alert("<%= errorMessage %>");
        </script>
    <%
        }
    %>
</body>
</html>
