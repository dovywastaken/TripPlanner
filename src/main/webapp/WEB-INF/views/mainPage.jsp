<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.spring.domain.Member" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>트립플래너</title>
  <link href="/TripPlanner/resources/css/style.css" rel="stylesheet">
</head>
<body>
  <header>
    <h1>트립플래너</h1>
    <div class="search-bar">
      <input type="text" placeholder="검색">
    </div>
    
    <nav>
    <% 
        // 세션에서 로그인된 사용자 정보를 가져옴
        Member member = (Member) session.getAttribute("user");

        // 로그인된 사용자 확인
        if (member != null) {
    %>
        <p>어서오세요, <%= member.getName() %>님!</p> <!-- 로그인된 사용자의 이름을 표시 -->
        <a href="members/signOut">로그아웃</a> <!-- 로그아웃 링크 --> |
        <a href="members/updateMember">마이 페이지</a> 
        <a href="admin/dashboard"> | 회원 목록</a>
    <% 
        } else {
    %>
        <p>환영합니다! 로그인을 해주세요.</p>
        <a href="members/signIn">로그인</a>
        <a href="members/signUp">회원가입</a>
        <a href="admin/dashboard"> | 회원 목록</a>
    <% 
        }
    %>
  </nav>
  </header>

  <main>
    <section class="my-posts">
      <% if (member != null) { %>
        <h2><%= member.getName() %> 님, 떠나보실까요?</h2> <!-- 로그인된 사용자 이름 사용 -->
        <p>여행 포스트 4</p>
        <div class="post-cards">
          <div class="card">내 포스트 1</div>
          <div class="card">내 포스트 2</div>
          <div class="card">내 포스트 3</div>
        </div>
        <button class="btn">새 일정 만들기</button>
      <% } else { %>
        <h2>트립플래너에 오신 것을 환영합니다!</h2> <!-- 비로그인 상태 안내 문구 -->
        <p>로그인 후 여행 계획을 세워보세요!</p>
      <% } %>
    </section>

    <section class="popular-plans">
      <h2>인기 여행 계획</h2>
      <div class="plan-cards">
        <div class="card">경주 불국사</div>
        <div class="card">추가 여행 계획</div>
        <div class="card">다른 여행 계획</div>
      </div>
    </section>

    <section class="festivals">
      <h2>축제들</h2>
      <div class="festival-cards">
        <div class="card">김밥축제</div>
        <div class="card">다른 축제</div>
      </div>
    </section>

    <section class="landmarks">
      <h2>관광지</h2>
      <div class="landmark-cards">
        <div class="card">경주 대릉원</div>
        <div class="card">추가 관광지</div>
      </div>
    </section>
  </main>

  <footer>
    <p>© 2024 TripPlanner. All rights reserved.</p>
  </footer>
</body>
</html>
