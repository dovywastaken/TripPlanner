<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TripPlanner - 로그인</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>var contextPath = '${pageContext.request.contextPath}';</script>
    <script src="${pageContext.request.contextPath}/resources/js/signUp.js"></script>
</head>
<body>
    <h2>로그인</h2>
    <a href="${pageContext.request.contextPath}">Home</a>
    
    <!-- 로그인 폼 -->
    <form:form modelAttribute="member" method="POST" action="signIn">
        <p>
            아이디: 
            <form:input path="id" placeholder="아이디" required="required"/>
        </p>
        <p>
            비밀번호: 
            <form:input path="pw" placeholder="비밀번호" required="required" type="password"/>
        </p>
        <input type="submit" value="로그인">
    </form:form>

    <!-- 소셜 로그인 버튼 -->
    <a href="#">Naver</a>
    <a href="#">Kakao</a>

    <!-- 에러 메시지 표시 -->
    <c:if test="${not empty loginError}">
        <script>
            alert("${loginError}");
        </script>
    </c:if>
</body>
</html>
