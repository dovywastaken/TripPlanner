<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TripPlanner</title>
<script>
  var contextPath = '${pageContext.request.contextPath}';
</script>
<script src="${pageContext.request.contextPath}/resources/js/signUp.js"></script>
</head>
<body>

<c:choose>
  <c:when test="${not empty user}">
    <a href="${pageContext.request.contextPath}">Home</a>
    <form method="POST" action="${pageContext.request.contextPath}/members/updateMember">
      <input type="text" name="id" value="${user.id}" placeholder="아이디" readonly>
      <br>
      <input type="text" name="name" value="${user.name}" placeholder="이름" readonly>
      <br>
      <div class="form-group">
        <label for="emailId">Email : </label>
        <c:set var="emailParts" value="${fn:split(user.email, '@')}" />
        <input type="text" id="emailId" name="emailId" placeholder="이메일" value="${emailParts[0]}" />@
        <input type="text" id="emailDomain" name="emailDomain" value="${emailParts[1]}" placeholder="sample.com" readonly="readonly"/>
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
      <input type="text" name="region" value="${user.region}" placeholder="지역">
      <br>
      <input type="text" name="phone" value="${user.phone1}-${user.phone2}-${user.phone3}" placeholder="전화번호">
      <br>
      <input type="date" name="birthday" value="${user.birthday}" placeholder="생년월일" readonly>
      <br>
      <input type="submit" value="회원 정보 수정">
    </form>
  </c:when>
  <c:otherwise>
    <p>로그인이 필요합니다. <a href="${pageContext.request.contextPath}/members/signIn">로그인 페이지로 이동</a></p>
  </c:otherwise>
</c:choose>

</body>
</html>
