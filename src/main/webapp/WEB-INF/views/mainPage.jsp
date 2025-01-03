<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>트립플래너</title>
    
    <link rel="stylesheet" href="/TripPlanner/resources/css/normalize.css">
    <link rel="stylesheet" href="/TripPlanner/resources/css/mainPage.css">
    <link href="https://cdn.jsdelivr.net/gh/sun-typeface/SUITE@2/fonts/static/woff2/SUITE.css" rel="stylesheet">

	<style>
	    body {font-family: 'SUITE', sans-serif;}
	</style>

	<script src="https://kit.fontawesome.com/96b1ce314a.js"></script>

</head>

	<%@ include file="header.jsp" %>

	<div class="container-fluid">
	    <main>
	        <div class="mainContainer">
	            <div class="section">
	                <div class="title">
	                    <span>인기 여행 계획</span>
	                    <a href="/TripPlanner/hotPlanners" class="morePost">더보기</a>
	                </div>
	                <div id="planner">
					<c:forEach var="post" items="${result.Allpost}">
				        <div class="plannerCard">
				            <div class="imgFrame">
				                <img src="/TripPlanner/resources/upload/${post.fileImage[0]}" class="pImg">
				            </div>
				            <div class="plannerCol" id="pText">
				            	<h4>${post.id} 님의</h4> 
				                <h3 class="plannerTitle">${post.title}</h3>
								<span>#좋아요 ${post.likes}</span>
								<p>조회수 : ${post.view}</p>
				            </div>
				        </div>
					</c:forEach>
	                </div>
	            </div>
	
	
				<div id="tourDividerTitle">트립플래너가 추천하는 지역 명소들</div>
				<br>
	            <div class="section">
	                <div class="title">
	                    <span>추천 축제</span>
	                    <a href="/TripPlanner/boardFestival" class="morePost">더보기</a>
	                </div>
	                <c:choose>
               			<c:when test="${not empty festival}">
               				<c:forEach var="festival" items="${festival}">
		                      <div class="plannerCard">
		                          <div class="imgFrame">
		                              <img src="${festival.firstimage}" class="pImg">
		                          </div>
		                          <div class="plannerCol" id="pText">
		                              <h3 class="plannerTitle">${festival.title}</h3>
		                              <p class="hashtag">#경산시 #문화관광축제</p>
		                          </div>
		                      </div>
	                     	</c:forEach>
                    	</c:when>
   					</c:choose>
	            </div>
	
				 <div class="section">
	                  <div class="title">
	                      <span>추천 관광지</span>
	                      <a href="/TripPlanner/boardTour" class="morePost">더보기</a>
	                  </div>
	                  <div id="planner">
	                  		<c:choose>
	                  			<c:when test="${not empty tourSpots}">
	                  				<c:forEach var="tourSpots" items="${tourSpots}">
					                      <div class="plannerCard">
					                          <div class="imgFrame">
					                              <img src="${tourSpots.firstimage}" class="pImg">
					                          </div>
					                          <div class="plannerCol" id="pText">
					                              <h3 class="plannerTitle">${tourSpots.title}</h3>
					                              <p class="hashtag">#성산구 #국립공원</p>  
					                          </div>
					                      </div>
			                      	</c:forEach>
		                      	</c:when>
	      					</c:choose>
	                  </div>
	              </div>
	              <div class="section">
	                  <div class="title">
	                      <span>추천 맛집</span>
	                      <a href="/TripPlanner/boardRestaurant" class="morePost">더보기</a>
	                  </div>
	                  <div id="planner">
	                      <c:choose>
	                  			<c:when test="${not empty restaurants}">
	                  				<c:forEach var="restaurants" items="${restaurants}">
					                      <div class="plannerCard">
					                          <div class="imgFrame">
					                              <img src="${restaurants.firstimage}" class="pImg">
					                          </div>
					                          <div class="plannerCol" id="pText">
					                              <h3 class="plannerTitle">${restaurants.title}</h3>
					                              <p class="hashtag">#경산시 #한식</p>       
					                          </div>
					                      </div>
			                      	</c:forEach>
		                      	</c:when>
	      					</c:choose>
	                  </div>
	              </div>
	        </div>
	    </main>
	
	    <aside>
	        <div class="sidePanelContainer">
	            <div id="searchBar">
	                <input type="text" placeholder="플래너 검색">
	            </div>
	            <div id="myPanel">
				    <c:if test="${not empty user}">
				        <!-- 로그인한 사용자가 있을 때 보여줄 내용 -->
				        <h1>${user.name} 님, <br>떠날 준비 되셨나요?</h1>
				        <div class="myPost">
				            <div class="myPostTitle">
				                <div class="postTitle">최근 작성 글</div>
				            </div>
				            <c:if test="${posts.size() == 0}">
				            	<h3>최근 작성글이 없어요!</h3>
				            </c:if>
				            <c:forEach var="post" items="${posts}" varStatus="status">
							    <c:choose>
							        <c:when test="${status.index == 0}">
							            <div class="post">
							                <div class="postName">${post.title}</div>
							                <div class="postTime">${days[status.index]}</div>
							            </div>
							            <div class="myPost">
							                <div class="myPostTitle">
							                    <div id="postTitle">
							                        <span>내 여행 계획 </span>
							                        <span id="postCount">${count}</span>
							                    </div>
							                    <a href='/TripPlanner/Myboard'>더보기</a>
							                </div>
							            </div>
							        </c:when>
							        <c:otherwise>
							            <div class="post">
							                <div class="postName">${post.title}</div>
							                <div class="postTime">${days[status.index]}</div>
							            </div>
							        </c:otherwise>
							    </c:choose>
							</c:forEach>
						</div>
				        <a href="postform" class="postButton">+ 새 여정 만들기</a>
				        <a href="/TripPlanner/members/signOut" class="signOutButton">로그아웃</a>
				    </c:if>
				
				    <c:if test="${empty user}">
				    <br>
				    <h1 class="signInTitle">로그인하고</h1>
				    <h1 class="signInTitle">여정을 떠나봐요!</h1>
					    <!-- 로그인 폼이 보이는 부분 -->
				        <div class="form-container">
				            
				            <!-- 로그인 폼 -->
				            <form:form modelAttribute="member" method="POST" action="members/signIn">
				                <div class="form-group">
				                    <label for="id">아이디:</label>
				                    <form:input path="id" id="id" placeholder="아이디" />
				                </div>
				                <div class="form-group">
				                    <label for="pw">비밀번호:</label>
				                    <form:input path="pw" id="pw" placeholder="비밀번호" type="password" />
				                </div>
				                <!-- hidden 필드 추가 -->
				                <div class="form-group">
				                    <input type="submit" id="submitButton" value="로그인">
				                </div>
				                <a href="/TripPlanner/members/signUp" id="signUpButton">가입하기</a>
				            </form:form>
				            
				
				            <!-- 에러 메시지 표시 -->
				            <c:if test="${not empty EmptyForm}">
				                <script>
				                    alert("${EmptyForm}");
				                </script>
				            </c:if>
				            <c:if test="${not empty loginError}">
				                <script>
				                    alert("${loginError}");
				                </script>
				            </c:if>
				        </div>
					</c:if>
				</div>
				
	        </div>
	    </aside>
	    
	</div>
	<%@ include file="footer.jsp" %>
</body>

</html>

