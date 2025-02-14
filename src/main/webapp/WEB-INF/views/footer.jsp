<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
    <script>
         window.contextPath = '${pageContext.request.contextPath}';
    </script>
<script src="https://kit.fontawesome.com/96b1ce314a.js"></script>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/normalize.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/footer.css">
</head>
	<footer>
	    <div class="sitemap">
	        <h3>사이트맵</h3>
	        <br>
	        <br>
	        <div id="members">
		        <div class="member">
	                <ul>
	                    <h2>메인 페이지</h2>
	                    <br>
	                    <li><a href="${pageContext.request.contextPath}">• 메인 페이지</a></li>
	                    <li><a href="#">• 에러 페이지</a></li>
	                </ul>
	            </div>
	            <div class="member">
	                <ul>
	                    <h2>회원 관련</h2>
	                    <br>
	                    <li><a href="${pageContext.request.contextPath}/members/signUp">• 회원가입 페이지</a></li>
	                    <li><a href="${pageContext.request.contextPath}/members/signIn">• 로그인 페이지</a></li>
	                    <li><a href="${pageContext.request.contextPath}/members/myPage">• 내 정보 페이지</a></li>
	                    <li><a href="${pageContext.request.contextPath}/members/updateMember">• 정보 수정 페이지</a></li>
	                    <li><a href="${pageContext.request.contextPath}/members/deleteMember">• 회원 삭제 페이지</a></li>
	                    <li><a href="${pageContext.request.contextPath}/members/pwChange">• 비밀번호 변경 페이지</a></li>
	                    <li><a href="${pageContext.request.contextPath}/admin/dashboard">• 관리자 페이지</a></li>
	                </ul>
	            </div>
	            
	            <div class="member">
	                <ul>
	                    <h2>게시판 관련</h2>
	                    <br>
	                    <li><a href="${pageContext.request.contextPath}/Allboard">• 전체 플래너 페이지</a></li>
	                    <li><a href="${pageContext.request.contextPath}/Myboard">• 개인 플래너 페이지</a></li>
	                    <li><a href="${pageContext.request.contextPath}/hotPlanners">• 인기 플래너 페이지</a></li>
	                    <li><a href="${pageContext.request.contextPath}/boardFestival">• 추천 축제 페이지</a></li>
	                    <li><a href="${pageContext.request.contextPath}/boardTour">• 추천 관광지 페이지</a></li>
	                    <li><a href="${pageContext.request.contextPath}/boardRestaurant">• 추천 음식점 페이지</a></li>
	                </ul>
	            </div>
	            
	            <div class="member">
	                <ul>
	                    <h2>플래너 관련</h2>
	                    <br>
	                    <li><a href="${pageContext.request.contextPath}/postform">• 플래너 작성 페이지</a></li>
	                    <li><a href="${pageContext.request.contextPath}/Maps">• 명소 가져오기 페이지</a></li>
	                </ul>
	            </div>
	        </div>
	    </div>
	    
	    <div class="social">
	        <h3>Contacts</h3>
	        <br>
	        <br>
	        <div id="teammateContainer">
	            <div class="teammate">
	                <h1>황현제</h1>
	                <br>
	                <p>이메일 : guswpwkf@naver.com</p>
	                <p>연락처 : 010-8788-3121</p>
	                <br>
	                <div id="icons">
<!-- 	                    <a href="https://www.naver.com"><i class="fa-brands fa-instagram"></i></a>
	                    <a href="https://www.naver.com"><i class="fa-brands fa-github"></i></a> -->
	                </div>
	            </div>
	            
	            <div class="teammate">
	                <h1>김의신</h1>
	                <br>
	                <p>이메일 : dovies401@gmail.com</p>
	                <p>연락처 : 010-5285-8933</p>
	                <br>
	                <div id="icons">
	                    <a href="https://www.naver.com/" target="blank"><i class="fa-brands fa-linkedin"></i></a>
	                    <a href="https://www.instagram.com/tonald_drump111/" target="blank"><i class="fa-brands fa-instagram"></i></a>
	                    <a href="https://github.com/dovywastaken" target="blank"><i class="fa-brands fa-github"></i></a>
	                </div>
	            </div>
	        </div>
	    </div>
	    <div class="copyright">
	        <span id="copyright">Copyright &copy;  2024 Team Init All rights reserved.</span>
	    </div>
	</footer>

</body>
</html>