<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TripPlanner</title>
<script>var contextPath = '${pageContext.request.contextPath}';</script>
<script src="${pageContext.request.contextPath}/resources/js/signUp.js"></script>
</head>
<body>
<%! String email;
	String domain;%>
	<%
		Member member = (Member)session.getAttribute("user");
		String fullEmail = member.getEmail();
		
	    String[] emailnDomain = fullEmail.split("@");
	    
	    for(int i=1; i<=emailnDomain.length; i++) 
	    {
	    	email = emailnDomain[0];
	        domain = emailnDomain[1];
	    }
	%>
	<a href="${pageContext.request.contextPath}">Home</a>
	<form method="POST" action="${pageContext.request.contextPath}/members/updateMember">
	    <input type="text" name="id" value="<%= member.getId()%>" placeholder="아이디" readonly>
	    <br>
	    <input type="text" name="name" value="<%= member.getName()%>" placeholder="이름" readonly>
	    <br>
	    <div class="form-group">
            <label for="emailId">Email : </label>
            <input type="text" id="emailId" name="emailId" placeholder="이메일" value="<%= email %>" />@
            <input type="text" id="emailDomain" name="emailDomain" value="<%= domain %>" placeholder="sample.com" readonly="readonly"/>
            <select id="emailSelect" required="required" onchange="updateDomainInput(this)">
                <option value="custom">직접입력</option>
                <option value="naver.com">naver.com</option>
                <option value="gmail.com">gmail.com</option>
                <option value="hanmail.net">hanmail.net</option>
                <option value="daum.net">daum.net</option>
                <option value="icloud.com">icloud.com</option>
            </select>         
            <div id="message_email"></div> 
        </div>
	    <br>
	    <input type="text" name="region" value="<%= member.getRegion()%>" placeholder="지역">
	    <br>
	    <input type="text" name="phone1" value="<%= member.getPhone1()%>-<%= member.getPhone2()%>-<%= member.getPhone3()%>" placeholder="전화번호(앞자리)">
	    <br>
	    <input type="date" name="birthday" value="<%= member.getBirthday()%>" placeholder="생년월일" readonly>
	    <br>
	    <input type="submit" value="회원 정보 수정">
	</form>
	
</body>
</html>