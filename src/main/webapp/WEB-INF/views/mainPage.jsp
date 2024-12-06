<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>트립플래너</title>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">
</head>



<body>

  <header class="header">
    <h1 class="logo">트립플래너</h1>
    <div class="search-bar">
      <input type="text" placeholder="검색" class="search-input">
    </div>
  </header>

  <main class="main-content">
    <section class="welcome-section">
      <% 
          Member member = (Member) session.getAttribute("user");

          if (member != null) {
      %>
          <p class="welcome-message">어서오세요, <%= member.getName() %>님!</p>
          <a href="members/signOut" class="nav-link">로그아웃</a>
          <a href="members/updateMember" class="nav-link">마이 페이지</a> 
          <a href="admin/dashboard" class="nav-link">회원 목록</a>
      <% 
          } else {
      %>
          <p class="welcome-message">환영합니다! 로그인을 해주세요.</p>
          <a href="members/signIn" class="nav-link">로그인</a>
          <a href="members/signUp" class="nav-link">회원가입</a>
          <a href="admin/dashboard" class="nav-link">회원 목록</a>
      <% 
          }
      %>
    </section>

    <% if (member == null) { %>
    <section class="intro-section">
      <h2 class="section-title">트립플래너에 오신 것을 환영합니다!</h2>
      <p class="sub-title">로그인 후 여행 계획을 세워보세요!</p>
    </section>
    <% } %>

    <% if (member != null) { %>
    <section class="my-posts">
        <h2 class="section-title"><%= member.getName() %> 님, 떠나보실까요?</h2>
        <p class="sub-title">여행 포스트 4</p>
        <div class="post-cards">
          <div class="card">내 포스트 1</div>
          <div class="card">내 포스트 2</div>
          <div class="card">내 포스트 3</div>
        </div>
        <button class="btn create-btn">새 일정 만들기</button>
    </section>
    <% } %>

    <section class="popular-plans">
      <h2 class="section-title">인기 여행 계획</h2>
      <div class="plan-cards">
        <div class="card">경주 불국사</div>
        <div class="card">추가 여행 계획</div>
        <div class="card">다른 여행 계획</div>
      </div>
    </section>

    <section class="festivals">
      <h2 class="section-title">축제들</h2>
      <div class="festival-cards">
        <div class="card">김밥축제</div>
        <div class="card">다른 축제</div>
      </div>
    </section>

    <section class="landmarks">
      <h2 class="section-title">관광지</h2>
      <div class="landmark-cards">
        <div class="card">경주 대릉원</div>
        <div class="card">추가 관광지</div>
      </div>
    </section>
  </main>

  <footer class="footer">
    <p>© 2024 TripPlanner. All rights reserved.</p>
  </footer>
</body>
</html>
