<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>트립플래너</title>
    <script>
         window.contextPath = '${pageContext.request.contextPath}';
    </script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/normalize.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/mainPage.css">
    <link href="https://cdn.jsdelivr.net/gh/sun-typeface/SUITE@2/fonts/static/woff2/SUITE.css" rel="stylesheet">
    
	<script src="https://kit.fontawesome.com/96b1ce314a.js"></script>

</head>
<%@ include file="header.jsp" %>
	<div class="container-fluid">
	    <main>
	        <div class="mainContainer">
	            <div class="section">
	                <div class="title">
	                    <span>인기 여행 계획</span>
	                    <a href="${pageContext.request.contextPath}/board/hot" class="morePost">더보기</a>
	                </div>
	                <div id="planner">
	                <c:choose>
	               			<c:when test="${not empty result.postList}">
								<c:forEach var="postList" items="${result.postList}">
									<div class="plannerDoubling">
							        	<div class="plannerCard">
								            <div class="imgFrame">
								           		<img src="<c:url value='/uploads/${postList.fileImage[0]}'/>" class="pImg">
								           <!-- <img src="${pageContext.request.contextPath}/resources/upload/${postList.fileImage[0]}" class="pImg"> -->  
								            </div>
								            <div class="plannerCol" id="pText">
								            	<div>
													<h4>${postList.id} 님의</h4> 
								                	<h3 class="plannerTitle">${postList.title}</h3>
												</div>
												<div class="plannerStatus">
													<span>좋아요: ${postList.likes}</span>
													<p>조회수: ${postList.views}</p>
												</div>
								            </div>
							        	</div>
									</div>
								</c:forEach>
							</c:when>
					</c:choose>
	                </div>
	            </div>
	
	
				<div id="tourDividerTitle">트립플래너가 추천하는 지역 명소들</div>
				<br>
	            <div class="section">
	                <div class="title">
	                    <span>추천 축제</span>
	                    <a href="${pageContext.request.contextPath}/board/festival" class="morePost">더보기</a>
	                </div>
		            <div id="planner">
		                <c:choose>
	               			<c:when test="${not empty festival}">
	               				<c:forEach var="festival" items="${festival}">
				                      <div class="recommandCard">
				                          <div class="imgFrame">
				                              <img src="${festival.firstimage}" class="pImg">
				                          </div>
				                          <div class="plannerCol" id="pText">
											<div>
				                            	<h3 class="plannerTitle">${festival.title}</h3>
				                            	<span class="hashtag">#${festival.addr1}</span>
				                            </div>
											</div>
				                      </div>
		                     	 </c:forEach>
	                    	</c:when>
	   					</c:choose>
	   				</div>
	            </div>
	
				 <div class="section">
	                  <div class="title">
	                      <span>추천 관광지</span>
	                      <a href="${pageContext.request.contextPath}/board/tour" class="morePost">더보기</a>
	                  </div>
	                  <div id="planner">
	                  		<c:choose>
	                  			<c:when test="${not empty tourSpots}">
	                  				<c:forEach var="tourSpots" items="${tourSpots}">
					                      <div class="recommandCard">
					                          <div class="imgFrame">
					                              <img src="${tourSpots.firstimage}" class="pImg">
					                          </div>
					                          <div class="plannerCol" id="pText">
					                            <div>
													<h3 class="plannerTitle">${tourSpots.title}</h3>
					                            	<p class="hashtag">#${tourSpots.addr1}</p>  
												</div>
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
	                      <a href="${pageContext.request.contextPath}/board/restaurant" class="morePost">더보기</a>
	                  </div>
	                  <div id="planner">
	                      <c:choose>
	                  			<c:when test="${not empty restaurants}">
	                  				<c:forEach var="restaurants" items="${restaurants}">
					                      <div class="recommandCard">
					                          <div class="imgFrame">
					                              <img src="${restaurants.firstimage}" class="pImg">
					                          </div>
					                          <div class="plannerCol" id="pText">
												<div>
					                            	<h3 class="plannerTitle">${restaurants.title}</h3>
					                            	<p class="hashtag">#${restaurants.addr1}</p>       
												</div>
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
				        <h1>${user.nickname} 님, <br>떠날 준비 되셨나요?</h1>
				        <div class="myPost">
				            <div class="myPostTitle">
				                <div class="postTitle">최근 작성 글</div>
				            </div>
				            <c:if test="${postList.size() == 0}">
				            	<h3>최근 작성글이 없어요!</h3>
				            </c:if>
				            <c:forEach var="post" items="${postList}" varStatus="status">
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
							                    <a class="postMore" href='${pageContext.request.contextPath}/board/myBoard'>더보기</a>
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
				        <a href="${pageContext.request.contextPath}/postForm" class="postButton">+ 새 여정 만들기</a>
				        <a href="${pageContext.request.contextPath}/members/signOut" class="signOutButton">로그아웃</a>
				    </c:if>
				
				    <c:if test="${empty user}">
				    <br>
				    <h1 class="signInTitle">로그인하고</h1>
				    <h1 class="signInTitle">여정을 떠나봐요!</h1>
					    <!-- 로그인 폼이 보이는 부분 -->
				        <div class="form-container">
				            
				            <!-- 로그인 폼 -->
				            <form:form modelAttribute="member" method="POST" action="${pageContext.request.contextPath}/mainSignIn">
				                <div class="form-group">
				                    <label for="id">아이디:</label>
				                    <form:input path="email" id="id" placeholder="아이디" />
				                </div>
				                <div class="form-group">
				                    <label for="pw">비밀번호:</label>
				                    <form:input path="pw" id="pw" placeholder="비밀번호" type="password" />
				                </div>
				                <!-- hidden 필드 추가 -->
				                <div class="form-group">
				                    <input type="submit" id="submitButton" value="로그인">
				                </div>
				                <a href="${pageContext.request.contextPath}/members/signUp" id="signUpButton">가입하기</a>
				            </form:form>
				        </div>
					</c:if>
				</div>

	        </div>
	    </aside>
	    
	</div>
	<%@ include file="footer.jsp" %>
</body>
<script src="${pageContext.request.contextPath}/resources/js/mainPage.js" defer></script>
</html>

