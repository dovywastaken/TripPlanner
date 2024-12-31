<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>트립플래너</title>
</head>

<style>
	.container
	{
		padding : 100px 300px;
	}


	.section{
        margin-bottom: 55px;
        width: 100%;
    }

    .title{
        margin: 0 0 0px 8px;
        display: flex;
        align-items: center;
    }


    .title span
    {
        font-size: 25px;
        margin: 13px;
    }
    
    .morePost{
        background-color: #ffee8d;
        font-size: 13px;
        font-weight: bold;
        border-radius: 13px;
        padding: 0 8px;
        height: 21px;
        display: flex;
        align-items: center;
    }

    #planner
    {
        display: flex;
        flex-wrap: wrap;
    }

    .plannerCard {
        position: relative; /* 가상 요소를 위한 상대 위치 설정 */
        display: flex;
        width: calc(50% - 10px);
        min-width: calc(50% - 8px);
        height: 196px;
        background-color: #ffffff;
        box-shadow: 0px 7px 30px rgba(33,55,55,0.1);
        border-radius: 29px;
        overflow: hidden;
        padding: 8px;
        margin: 0 15px 0 0;
    }

    .plannerCard:nth-child(2n)
    {
        margin-right: 0;
    }

    .plannerCard:nth-child(3),
    .plannerCard:nth-child(4)
    {
        margin-top: 35px;
    }

    .imgFrame
    {
        width: 180px;
        min-width: 180px;
        height: 180px;
        background-color: #eaeeef;
        border-radius: 21px;
        margin-right: 10px;
        overflow: hidden;
    }

    .pImg
    {
        width: 100%;
        height: 100%;
        object-fit: cover;
        outline: 1px solid magenta;
    }


    .plannerCol
    {
        width: 40%;
        height: 100%;
    }

    .plannerTitle
    {
        font-size: 21px;
        line-height: 34px;
        font-weight: bold;
        color: #0f7060;
    }

    .hashtag{
        font-size: 13px;
        color: #00ee66;
        font-weight: bold;
    }
    .plannerContents{
        margin-top: 13px;
        font-size: 13px;
        line-height: 21px;
    }

</style>

<body>
<%@ include file="../header.jsp" %>

<div class="container">
	<h1>추천 식당 게시판 입니다.</h1>

	<div class="section">
        <div id="planner">
			<c:choose>
				<c:when test="${not empty restaurants}">
					<c:forEach var="restaurants" items="${restaurants}">
			            <div class="plannerCard">
			                <div class="imgFrame">
			                    <img src="${restaurants.firstimage}" class="">
			                </div>
			                <div class="plannerCol" id="pText">
			                    <h3 class="plannerTitle">${restaurants.title}</h3>
			                    <p class="hashtag">#${restaurants.addr1}</p>
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