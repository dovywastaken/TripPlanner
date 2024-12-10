//페이지 로드 될 때 실행할 코드들
document.addEventListener('DOMContentLoaded', function() {
    const idField = document.getElementById("id");
    const nameField = document.getElementById("name");
    const pwField = document.getElementById("pw1");
    const emailField = document.getElementById("emailDomain");
    const phoneField = document.getElementById("phone1");
    const birthdayField = document.getElementById("birthday");
	const submitButton = document.getElementById("signUp_form");
	
	const testChamber = document.getElementById("testButton");
	
    // 아이디, 이름, 비밀번호, 이메일도메인, 휴대폰번호, 생년월일 이벤트리스너
    idField.addEventListener('input', IDValidator);
    nameField.addEventListener('input', NameValidator);
    pwField.addEventListener('input', pwValidator);
    emailField.addEventListener('input', domainValidator);
    phoneField.addEventListener('input', phoneValidator);
    birthdayField.addEventListener('input', birthdayValidator);
	
	
	
	submitButton.addEventListener('submit',(event)=> 
	{
		if(!checkFormValidity())
		{
			event.preventDefault();
			alert("작성 다시 하세요");
			return;
		}
		if(!combineEmail())
		{
			event.preventDefault();
			alert("이메일을 정확히 입력해주세요")
			return;
		}

		if(!passwordCheck())
		{
			event.preventDefault();
			return;
		}
		
		alert("회원 가입 완료");
	});
	
	
	
	
	
	
	testChamber.addEventListener('click', function()
	{
		console.log(checkFormValidity);
		checkFormValidity(event);
	});
});


//유효성 검사 함수
function IDValidator() 
{
    var idValue = document.getElementById("id").value.trim(); // 아이디 값 가져오기
    var idPattern = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{5,15}$/; // 정규식: 글자+숫자, 5~15글자
	
	var messageElement = document.getElementById('message_id'); 
	const confirmIdButton = document.getElementById("confirmId"); //아이디 중복체크 이벤트 매핑
	var resultMessage = document.getElementById("resultMessage");
    // 1. 공백이 있을 경우
    if (idValue === '') {
        messageElement.innerHTML = '';
		confirmIdButton.disabled = true;
		resultMessage.innerHTML = '';
		
        return false;
    }

    // 2. 특수문자 및 공백이 포함된 경우
    if (/[^a-zA-Z0-9]/.test(idValue)) {
		messageElement.innerHTML = '<span style="color:red;">아이디는 알파벳과 숫자만 포함해야 합니다.</span>';
		confirmIdButton.disabled = true;
		resultMessage.innerHTML = '<span style="color:red;">회원 가입란을 다시 확인 부탁드립니다!</span>';
		
        return false;
    }

    // 3. 정규식 검사 (글자 1개 + 숫자 1개 이상, 5~15글자)
    if (!idPattern.test(idValue)) {
		messageElement.innerHTML = '<span style="color:red;">아이디는 최소 5글자 이상, 최대 15글자 이하로, 알파벳과 숫자를 포함해야 합니다.</span>';
		confirmIdButton.disabled = true;
		resultMessage.innerHTML = '<span style="color:red;">회원 가입란을 다시 확인 부탁드립니다!</span>';
		
        return false;
    }

    // 모든 조건을 통과하면 true 반환
    messageElement.innerHTML = '<span style="color:green;">아이디가 유효합니다</span>';
	confirmIdButton.disabled = false;
	resultMessage.innerHTML = '';
	
    return true;
	
}

function NameValidator()
{
	var nameValue = document.getElementById("name").value.trim();
	console.log(nameValue);	
	var messageElement = document.getElementById('message_name'); 
	var resultMessage = document.getElementById("resultMessage");
	
	// 정규식 검사 (글자가 2개 이상)
	    if (nameValue.length == 1) {
			messageElement.innerHTML = '<span style="color:red;">이름은 2글자 이상</span>';
			resultMessage.innerHTML = '<span style="color:red;">회원 가입란을 다시 확인 부탁드립니다!</span>';
			
			return false;
	    }else if(nameValue.length == 0)
		{
			messageElement.innerHTML = '';
			resultMessage.innerHTML = '';
			
			return false;
		}else
		{
			messageElement.innerHTML = '';
			resultMessage.innerHTML = '';
			
			return true;
		}
}
		

function pwValidator() {
    const pw = document.getElementById("pw1").value;
	console.log(pw);
    const pwValidationMessage = document.getElementById('pwValidationMessage');
    const pwPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d!@#$%^&*()_+]{10,}$/;

	pwValidationMessage.innerHTML = '';
	
	if(pw === '')
	{	
		pwValidationMessage.innerHTML = '';
	    return false;
	}
	
    if (!pwPattern.test(pw)) {
        pwValidationMessage.innerHTML = '<span style="color:red;">비밀번호는 대문자, 소문자, 숫자가 포함되어야 하며 최소 10글자 이상이어야 합니다.</span>';
        return false;
    }else if(pwPattern.test(pw))
	{
		pwValidationMessage.innerHTML = '<span style="color:green;">비밀번호가 유효합니다.</span>';
		return true;
	}
}


function domainValidator() {
    var emailDomain = document.getElementById("emailDomain").value;
    var domainPattern = /^[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/; // 이메일 도메인 형식 검사

    var messageElement = document.getElementById('message_email');
    if (!domainPattern.test(emailDomain)) {
        messageElement.innerHTML = '<span style="color:red;">유효한 이메일 도메인 형식이 아닙니다.</span>';
		console.log("도메인 유효하지 않음");
        return false;
    } else {
        messageElement.innerHTML = '<span style="color:green;">이메일 도메인이 유효합니다.</span>';
		console.log("도메인 유효");
        return true;
    }
}


function phoneValidator() {
    var phone = document.getElementById("phone1").value;
    var phonePattern = /^\d{3}-\d{4}-\d{4}$/; // 3-4-4 형식

    var messageElement = document.getElementById('message_phone');
    if (!phonePattern.test(phone)) {
        messageElement.innerHTML = '<span style="color:red;">전화번호는 010-1234-5678 형식이어야 합니다.</span>';
        return false;
    } else {
        messageElement.innerHTML = '<span style="color:green;">전화번호가 유효합니다.</span>';
        return true;
    }
}



function birthdayValidator() {
    var birthday = document.getElementById("birthday").value;
	var today = new Date();
    var birthDate = new Date(birthday);
    var age = today.getFullYear() - birthDate.getFullYear();
    var month = today.getMonth() - birthDate.getMonth();
	
    // 생일 값이 비어 있는지 체크
    if (!birthday) 
	{
        var messageElement = document.getElementById('message_birthday');
        messageElement.innerHTML = '<span style="color:red;">생년월일을 입력해주세요.</span>';
        return false;
    }

    // 생일이 지나지 않았다면 나이를 하나 적게 계산
    if (month < 0 || (month === 0 && today.getDate() < birthDate.getDate())) 
	{
        age--;
    }

    var messageElement = document.getElementById('message_birthday');
    if (age < 14 || age > 120) 
	{
        messageElement.innerHTML = '<span style="color:red;">만 14세 이상, 만 120세 이하만 가능합니다.</span>';
		return false;
    } 
	else
	{
        messageElement.innerHTML = '<span style="color:green;">생년월일이 유효합니다.</span>';
		return true;
    }
}



function checkFormValidity(event) {
    var isIdValid = IDValidator();
    var isNameValid = NameValidator();
    var isPwValid = pwValidator();
    var isDomainValid = domainValidator();
    var isPhoneValid = phoneValidator();
    var isBirthdayValid = birthdayValidator();
	console.log("checkFormValidity 함수 작동 중");
	console.log("아이디와 이름 유효성 통과 여부 : " + isIdValid + ", " + isNameValid);
	console.log("비밀번호와 도메인 유효성 통과 여부 : " + isPwValid + ", " + isDomainValid);
	console.log("휴대폰과 생일 유효성 통과 여부 : " + isPhoneValid + ", " + isBirthdayValid);
	
	if (isIdValid && isNameValid && isPwValid && isDomainValid && isPhoneValid && isBirthdayValid) 
	{
		return true;
	} 
   else 
   {
	   return false;
   }
}


