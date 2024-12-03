<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TripPlanner</title>
</head>
<body>
	<a href="${pageContext.request.contextPath}">Home</a>
	<form method="POST" action="signUp">
		<label>아이디 : </label>
	    <input type="text" name="id" placeholder="아이디" required>
	    <br>
	    
	    <label>이름 : </label>
	    <input type="text" name="name" placeholder="이름" required>
	    <br>
	    
	    <label>비밀번호 : </label>
	    <input type="password" name="pw" placeholder="비밀번호" required>
	    <br>
	    
	    <label for="region">지역 : </label>
			<select name="region" id="region" required>
			    <option value="" disabled selected>지역 선택</option>
			    <option value="서울">서울</option>
			    <option value="부산">부산</option>
			    <option value="대구">대구</option>
			    <option value="인천">인천</option>
			    <option value="광주">광주</option>
			    <option value="대전">대전</option>
			    <option value="울산">울산</option>
			    <option value="세종">세종</option>
			    <option value="경기">경기</option>
			    <option value="강원">강원</option>
			    <option value="충북">충북</option>
			    <option value="충남">충남</option>
			    <option value="전북">전북</option>
			    <option value="전남">전남</option>
			    <option value="경북">경북</option>
			    <option value="경남">경남</option>
			    <option value="제주">제주</option>
			</select>
	    <br>
	    
	    <label>성별 : </label>
	    <input type="radio" id="male" name="sex" value="남" required>
	    <label for="male">남</label>
	    <input type="radio" id="female" name="sex" value="여" required>
	    <label for="female">여</label>
	    <br>
	    
	    <label>전화번호 : </label>
	    <input type="text" name="phone1" placeholder="전화번호(앞자리)">
	    <input type="text" name="phone2" placeholder="전화번호(중간자리)">
	    <input type="text" name="phone3" placeholder="전화번호(끝자리)">
	    <br>
	    
	    <label>생년월일 : </label>
	    <input type="date" name="birthday" placeholder="생년월일">
	    <br>
	    
	    <input type="submit" value="회원가입">
	</form>


</body>
</html>