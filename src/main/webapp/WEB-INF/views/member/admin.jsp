<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 목록</title>
    <script>var contextPath = '${pageContext.request.contextPath}';</script>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/js/adminPage.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources//css/normalize.css">
    <link href="https://cdn.jsdelivr.net/gh/sun-typeface/SUITE@2/fonts/static/woff2/SUITE.css" rel="stylesheet">
</head>

<style>
	body 
    {
        background-color: #f4f7f6;
    }

    #container
    {
        width: 1280px;
        margin: 35px auto;
        /* padding: 0 300px; */
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
    }

    #titleBanner
    {
        width: 100%;
        height: 110px;
        padding: 40px;
        font-weight: bold;
        font-size: 34px;
        background-color: #00AA77;
        color: #FFFFFF;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: row;
        border-radius: 34px 34px 0 0;
        box-shadow: 0px 0px 25px rgba(33,55,88,0.1);
        z-index: 1;
    }

    #boardContainer
    {
        width: 100%;
        min-height: 610px;
        background-color: white;
        padding: 21px 34px 89px 34px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        flex-direction: column;
        border-radius: 0 0 34px 34px;
        box-shadow: 0px 7px 25px rgba(33,55,88,0.2);
        /* outline: 1px solid magenta; */
    }

    #table
    {
        /* outline: 1px solid magenta; */
        min-width: 1200px;
    }

    .tableHead
    {
        height: 44px;
        text-align: center;
        width: 135px;
    }

    .tableHead:nth-child(1),
    .tableHead:nth-child(2)
    {
        width: auto;
    }

    .tableHead:last-child
    {
        width: 50px;
        text-align: center;
    }


    .tableContent
    {
        text-align: start;
        border-top: #e1e3e9 solid 1px;
        height: 55px;
        text-align: center;
    }

    #pagination
    {
        width: 100%;
    }


    .pagination{
        display: flex;
        width: 100%;
        justify-content: center;
        gap: 12px;
        margin-bottom: 13px;
        /* outline: 1px solid magenta; */
    }



    /* --------------------------------------------------------------------------------------------------------- */

    #search
    {
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        margin: 12px 0 18px 0;
    }


    #keyword
    {
        width: 500px;
        height: 38px;
        padding: 21px;
        border: none;
        background-color: #F1F3F9;
        border-radius: 35px;
        box-sizing: border-box;
    }

    #keyword:hover
    {
        outline: 1px solid #0f7060;
        transition: 120ms;
    }

    #keyword:focus
    {
        outline: 3.1px solid #0f7060;
        transition: 120ms;
    }

    #searchButton
    {
        border: none;
        outline: 1px solid #e1e3e9;
        background-color: #ffffff;
        box-shadow: 5px 7px 18px 0px rgba(33,55,55,0.1);
        border-radius: 100%;
        height: 38px;
        width: 38px; 
        padding: 0 13px;
        transition: 120ms;
        box-sizing: border-box;
        background: url('${pageContext.request.contextPath}/resources/img/search.svg') no-repeat center;
        background-color: #007766;
        transform: translateX(-13px);
        cursor : pointer;
    }
    



</style>

<body>

	<%@ include file="../header.jsp" %>
<c:if test="${not empty user}">
	<c:if test="${user.id == 'admin'}">
		<div id="container">
		    <h2 id="titleBanner">회원 목록</h2>
		    <div id="boardContainer"> 
		        <div class="searchAndTableContainer">
		            <form name="search" id="search" method="get" action="dashboard">
		                <input type="submit" id="searchButton" value="" />
		                <input type="text" name="keyword" id="keyword" placeholder="회원 검색하기" />
		            </form>
		        
		            <table id="table">
		                <thead>
		                    <tr>
		                        <th class="tableHead">ID</th>
		                        <th class="tableHead">닉네임</th>
		                        <th class="tableHead">생년월일</th>
		                        <th class="tableHead" id="phone">전화번호</th>
		                        <th class="tableHead">가입일</th>
		                        <th class="tableHead">최근로그인</th>
		                        <th class="tableHead">인증</th>
		                    </tr>
		                </thead>
		                <tbody>
		                    <c:choose>
		                        <c:when test="${not empty memberList}">
		                            <c:forEach var="member" items="${memberList}">
		                                <tr>
		                                    <td class="tableContent">${member.name}</td>
		                                    <td class="tableContent">${member.id}</td>
		                                    <td class="tableContent">${member.birthday}</td>
		                                    <td class="tableContent">${member.phone1}-${member.phone2}-${member.phone3}</td>
		                                    <td class="tableContent">${member.registerDate}</td>
		                                    <td class="tableContent">${member.loginDate}</td>
		                                    <td class="tableContent">
		                                        <c:choose>
		                                            <c:when test="${member.emailCheck == 1}">
		                                                <p>o</p>
		                                            </c:when>
		                                            <c:otherwise>
		                                                <p>x</p>
		                                            </c:otherwise>
		                                        </c:choose>
		                                    </td>
		                                </tr>
		                            </c:forEach>
		                        </c:when>
		                        <c:otherwise>
		                            <tr>
		                                <td colspan="10" class="no-data">회원 정보가 없습니다.</td>
		                            </tr>
		                        </c:otherwise>
		                    </c:choose>
		                </tbody>
		            </table>
		        </div>
		        <div id="pagination">
		            <div class="pagination">
		                <c:forEach var="i" begin="1" end="${totalPages}">
		                    <a href="${pageContext.request.contextPath}/admin/dashboard?page=${i}&keyword=${keyword}">${i}</a>
		                </c:forEach>
		            </div>
		        </div>
		    </div>
		</div>
	</c:if>
</c:if>
<c:if test="${empty user}">
	<div id="container">
		    <div id="boardContainer">
		    	<div style ="display : flex; flex-direction: column">
			    	<h1>잘못된 접근입니다.</h1>
			    	<a href="${pageContext.request.contextPath}" style="text-align : center">메인페이지로 돌아가기</a>
		    	</div>
		    </div>
		</div>
</c:if>
    
    
    <%@ include file="../footerCompact.jsp" %>
    
</body>
</html>
