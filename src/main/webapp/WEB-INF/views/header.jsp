<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
	<div id="headerContainer">
		<div class="logo">
			<a href="${pageContext.request.contextPath}" id="iconLogo" class="aTag">T</a>
			<a href="${pageContext.request.contextPath}" id="textLogo" class="aTag">트립플래너</a>
		</div>
		<div class="board">
			<a href="#" class="aTag">인기 여행코스</a>
			<a href="#" class="aTag">인기 명소</a>
		</div>
		<div class="member">
		<c:if test="${empty member}">
			<a href="/TripPlanner/members/signIn" id="login" class="aTag">로그인</a>
		</c:if>
		<c:if test="${not empty member}">
			<a href="/TripPlanner/members/signOut" id="logout" class="aTag">로그아웃</a>
		</c:if>
		</div>
	</div>
</header>