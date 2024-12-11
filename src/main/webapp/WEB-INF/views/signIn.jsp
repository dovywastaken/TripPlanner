<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.security.SecureRandom" %>
<%@ page import="java.math.BigInteger" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TripPlanner</title>
<script>var contextPath = '${pageContext.request.contextPath}';</script>
<script src="${pageContext.request.contextPath}/resources/js/signUp.js"></script>
<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>

</head>
<body>
    <h2>로그인</h2>
    <a href="${pageContext.request.contextPath}">Home</a>
    
    <form:form modelAttribute="member" method="POST" action="${pageContext.request.contextPath}/members/signIn">
        <p>아이디 : <form:input path="id" placeholder="아이디" required="required"/></p>
        <p>비밀번호 : <form:input path="pw" placeholder="비밀번호" required="required" type="password"/></p>
        <input type="submit" value="로그인">
    </form:form>
    
    
    <%
    String clientId = "we7MyQW5okbLSDAosq9o";  //애플리케이션 클라이언트 아이디값";
    String redirectURI = URLEncoder.encode("http://localhost:8080/TripPlanner/members/test", "UTF-8");
    SecureRandom random = new SecureRandom();
    String state = new BigInteger(130, random).toString();
    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code"
         + "&client_id=" + clientId
         + "&redirect_uri=" + redirectURI
         + "&state=" + state;
    session.setAttribute("state", state);
 %>
  <a href="<%=apiURL%>"><img height="50" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/></a>
	<%out.println("state : " + state); %>
	<%out.println("apiURL : " + apiURL); %>
	<%out.println(); %>
    
    
    
    <!-- 네이버 로그인 버튼 노출 영역 -->
    <div id="naver_id_login"></div>
    <!-- //네이버 로그인 버튼 노출 영역 -->
    <script type="text/javascript">
        var naver_id_login = new naver_id_login("we7MyQW5okbLSDAosq9o", "http://localhost:8080/TripPlanner/members/test");
        var state = naver_id_login.getUniqState();
        naver_id_login.setButton("white", 2,40);
        naver_id_login.setDomain("http://localhost:8080/TripPlanner/members/signIn");
        naver_id_login.setState(state);
        naver_id_login.setPopup();
        naver_id_login.init_naver_id_login();
    </script>
    
    <a href="#">Naver</a>
    <a href="#">Kakao</a>

    <%
        if (request.getAttribute("loginError") != null) {
            String errorMessage = (String) request.getAttribute("loginError");
    %>
        <script>
            alert("<%= errorMessage %>");
        </script>
    <%
        }
    %>
</body>
</html>
