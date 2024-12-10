<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.spring.domain.Member" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 목록</title>
    
<script>
    var contextPath = '${pageContext.request.contextPath}';
</script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/adminPage.js"></script>
</head>
<body>
    <h1>어드민 페이지</h1>
    <a href="${pageContext.request.contextPath}">Home</a>

    <h2>회원 목록</h2>
    
    <form name="search" id="search" onsubmit="return false;">
        <input type="text" name="keyword" id="keyword" placeholder="회원 검색하기" />
        <input type="button" value="검색" />
    </form>
    
    <button id="test">Test</button>

    <br>
    <div>
	    <table border="1" cellspacing="0" cellpadding="5" id="member">
	        <thead>
	            <tr>
	            	<th onclick="sortTable(1)" style="cursor: pointer;">이름 &darr;</th>
	                <th onclick="sortTable(2)" style="cursor: pointer;">ID &darr;</th>
	                <th onclick="sortTable(3)" style="cursor: pointer;">Email &darr;</th>
	                <th onclick="sortTable(4)" style="cursor: pointer;">지역 &darr;</th>
	                <th onclick="sortTable(5)" style="cursor: pointer;">성별 &darr;</th>
	                <th onclick="sortTable(6)" style="cursor: pointer;">생년월일 &darr;</th>
	                <th>전화번호</th>
	            </tr>
	        </thead>
	        <tbody>
	            <% 
	                List<Member> memberList = (List<Member>) request.getAttribute("memberList");
	                if (memberList != null && !memberList.isEmpty()) {
	                    for (Member member : memberList) {
	            %>
	                <tr>
	                	<td data-column="name"><%= member.getName() %></td>
	                    <td data-column="id"><%= member.getId() %></td>
	                    <td data-column="email"><%= member.getEmail() %></td>
	                    <td data-column="region"><%= member.getRegion() %></td>
	                    <td data-column="sex"><%= member.getSex() %></td>
	                    <td data-column="birthday"><%= member.getBirthday() %></td>
	                    <td><%= member.getPhone1() %>-****-<%= member.getPhone3() %></td>
	                </tr>
	            <% 
	                    }
	                } else { 
	            %>
	                <tr>
	                    <td colspan="6">회원 정보가 없습니다.</td>
	                </tr>
	            <% 
	                } 
	            %>
	        </tbody>
	    </table>
    </div>
    
    <% 
	    int totalPages = (Integer) request.getAttribute("totalPages");
	    int currentPage = (Integer) request.getAttribute("currentPage");
	    String keyword = (String) request.getAttribute("keyword");
	%>
    
    <div>
	       <% for (int i = 1; i <= totalPages; i++) { %>
	           <a href="${pageContext.request.contextPath}/admin/dashboard?page=<%= i %>&keyword=<%= keyword == null ? "" : keyword %>"><%= i %></a>
	       <% } %>
	</div>
    
</body>
</html>
