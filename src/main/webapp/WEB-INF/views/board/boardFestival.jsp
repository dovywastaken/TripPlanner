<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>트립플래너</title>
<link rel="stylesheet" href="/TripPlanner/resources/css/boardTours.css">
</head>

<body>
<%@ include file="../header.jsp" %>

<div class="container">
	<h1>추천 축제 게시판 입니다.</h1>

	<div class="section">
        <div id="planner">
			<c:choose>
				<c:when test="${not empty festivals}">
					<c:forEach var="festivals" items="${festivals}">
			            <div class="plannerCard">
			                <div class="imgFrame">
			                    <img src="${festivals.firstimage}" class="pImg">
			                </div>
			                <div class="plannerCol" id="pText">
			                    <h3 class="plannerTitle">${festivals.title}</h3>
			                    <p class="hashtag">#${festivals.addr1}</p>
			                </div>
			            </div>
		           	</c:forEach>
		        </c:when>
		        <c:otherwise>
		        	<div>아직 웹사이트가 응애라 추천할 축제 정보가 없어요 :( </div>
		        </c:otherwise>
			</c:choose>
        </div>
    </div>
</div>

	
<%@ include file="../footer.jsp" %>
</body>
</html>