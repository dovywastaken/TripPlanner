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
	    
	    <label>Email : </label>
		<input type="text" name="email" placeholder="이메일" required>
		@
		<input type="text" name="domain" id="domainInput" placeholder="sample.com" readonly>
		<select name="emailSelect" id="emailSelect" required onchange="updateDomainInput(this)">
		    <option value="custom">직접입력</option>
		    <option value="naver.com">naver.com</option>
		    <option value="gmail.com">gmail.com</option>
		    <option value="hanmail.net">hanmail.net</option>
		    <option value="daum.net">daum.net</option>
		    <option value="icloud.com">icloud.com</option>
		</select>
		<br>
		
		<script>
		document.addEventListener('DOMContentLoaded', function() {
		    // 페이지가 로드되자마자 '직접입력'을 선택
		    const emailSelect = document.getElementById("emailSelect");
		    emailSelect.value = "custom";
		    updateDomainInput(emailSelect);
		});
		
		function updateDomainInput(selectElement) {
		    const domainInput = document.getElementById("domainInput");
		    if (selectElement.value === "custom") {
		        domainInput.readOnly = false; // 직접입력 가능
		        domainInput.value = ""; // 입력창 초기화
		        domainInput.placeholder = "직접 입력";
		    } else {
		        domainInput.readOnly = true; // 입력 불가
		        domainInput.value = selectElement.value; // 선택한 값 입력
		        domainInput.placeholder = "sample.com"; // 기본 placeholder 복원
		    }
		}
		</script>
		
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