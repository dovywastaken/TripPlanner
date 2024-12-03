<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.spring.domain.Member" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TripPlanner</title>
</head>

<body>
    <h1>어드민 페이지</h1>
    <a href="${pageContext.request.contextPath}">Home</a>

    <h2>회원 목록</h2>
    <table border="1" cellspacing="0" cellpadding="5">
        <thead>
		    <tr>
		        <th><button onclick="sortMembers('id')">ID</button></th>
		        <th><button onclick="sortMembers('name')">이름</button></th>
		        <th><button onclick="sortMembers('region')">지역</button></th>
		        <th><button onclick="sortMembers('sex')">성별</button></th>
		        <th><button onclick="sortMembers('birthday')">생년월일</button></th>
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
                <td><%= member.getId() %></td>
                <td><%= member.getName() %></td>
                <td><%= member.getRegion() %></td>
                <td><%= member.getSex() %></td>
                <td><%= member.getBirthday() %></td>
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
</body>
</html>
