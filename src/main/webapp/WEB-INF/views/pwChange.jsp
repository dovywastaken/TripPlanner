<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
    <title>비밀번호 변경</title>
</head>
<script>var contextPath = '${pageContext.request.contextPath}';</script>
<script src="${pageContext.request.contextPath}/resources/js/pwCheck.js"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<%
    Member member = (Member)session.getAttribute("user");
%>
<a href="${pageContext.request.contextPath}">Home</a>
<body>
    <h1>비밀번호 변경</h1>
    
    <p>로그인된 유저의 비밀번호 : <%=member.getPw() %>
    <form:form id="pwChangeForm" method="post" modelAttribute= "member" action="${pageContext.request.contextPath}/members/pwChange">
        <div>
            <label for="currentPw">현재 비밀번호</label>
            <input type="password" id="pw" name="cpw" required> <p id="currentPwMessage"></p>
        </div>
        
        <div class="form-group">
			    <label for="pw1">비밀번호 : </label>
			    <form:input id="pw1" name="pw1" path="pw" placeholder="비밀번호" type="password"/>
			    <input type="hidden" id="memPassword" name="memPassword" value=""/>
			    <div id="pwValidationMessage"></div> <!-- 유효성 검사 메시지 -->
			</div>
			
			<div class="form-group">
			    <label for="pw2">비밀번호 확인 : </label>
			    <input id="pw2" placeholder="비밀번호 확인" type="password"/>
			    <div id="pwCheck"></div> <!-- 비밀번호 확인시 메시지 -->
			</div> 
			<input type="hidden" name="id" value="<%=member.getId() %>"/>
        <button type="button" id="pwChange">변경</button>
    </form:form>
</body>
</html>
