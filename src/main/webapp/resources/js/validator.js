
document.addEventListener('DOMContentLoaded', function() {
    // 폼 입력 필드의 값이 변경될 때마다 유효성 검사
    const idField = document.getElementById("id");
    const nameField = document.getElementById("name");
    const pwField = document.getElementById("pw1");
    const emailField = document.getElementById("emailDomain");
    const phoneField = document.getElementById("phone1");
    const birthdayField = document.getElementById("birthday");

    // 유효성 검사 이벤트 리스너
    idField.addEventListener('input', IDValidator);
    nameField.addEventListener('input', NameValidator);
    pwField.addEventListener('input', pwValidator);
    emailField.addEventListener('input', domainValidator);
    phoneField.addEventListener('input', phoneValidator);
    birthdayField.addEventListener('input', birthdayValidator);

    // 폼 제출 이벤트
    const signUpForm = document.getElementById("signUp_form");
    signUpForm.addEventListener("submit", function(event) 
	{
        if (!checkFormValidity()) 
		{
            event.preventDefault(); // 폼 제출을 막음
            alert("모든 필드를 정확히 입력해주세요.");
        }
    });

    // 초기 상태에서 버튼 비활성화
    const submitButton = document.querySelector('#signUp_form input[type="submit"]');
    submitButton.disabled = true; // 초기에는 비활성화
});


//유효성 검사 함수
function IDValidator() 
{
    var idValue = document.getElementById("id").value.trim(); // 아이디 값 가져오기
    var idPattern = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{5,15}$/; // 정규식: 글자+숫자, 5~15글자
	
	var messageElement = document.getElementById('message_id'); 
	const confirmIdButton = document.getElementById("confirmId"); //아이디 중복체크 이벤트 매핑
	//var submitButton = document.querySelector('#signUp_form input[type="submit"]');
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
	//var submitButton = document.querySelector('#signUp_form input[type="submit"]');
	var resultMessage = document.getElementById("resultMessage");
	
	// 정규식 검사 (글자가 2개 이상)
	    if (nameValue.length <= 1) {
			messageElement.innerHTML = '<span style="color:red;">이름 좀 제대로 입력해라!!</span>';
			resultMessage.innerHTML = '<span style="color:red;">회원 가입란을 다시 확인 부탁드립니다!</span>';
	    }else
		{
			messageElement.innerHTML = '';
			resultMessage.innerHTML = '';
		}

		// 모든 조건을 통과하면 true 반환
		resultMessage.innerHTML = '';


}

function pwValidator() {
    var pw = document.getElementById("pw1").value;
    var pwPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d]{10,}$/; // 대소문자, 숫자, 최소 10글자

    var messageElement = document.getElementById('passMessage');
	var resultMessage = document.getElementById("resultMessage");
    if (!pwPattern.test(pw)) {
        messageElement.innerHTML = '<span style="color:red;">비밀번호는 대문자, 소문자, 숫자가 포함되어야 하며 최소 10글자 이상이어야 합니다.</span>';
        return false;
    } else {
        messageElement.innerHTML = '<span style="color:green;">비밀번호가 유효합니다.</span>';
        return true;
    }
}



function domainValidator() {
    var emailDomain = document.getElementById("emailDomain").value;
    var domainPattern = /^[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/; // 이메일 도메인 형식 검사

    var messageElement = document.getElementById('message_email');
    if (!domainPattern.test(emailDomain)) {
        messageElement.innerHTML = '<span style="color:red;">유효한 이메일 도메인 형식이 아닙니다.</span>';
        return false;
    } else {
        messageElement.innerHTML = '<span style="color:green;">이메일 도메인이 유효합니다.</span>';
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
    
    // 생일이 지나지 않았다면 나이를 하나 적게 계산
    if (month < 0 || (month === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }

    var messageElement = document.getElementById('message_birthday');
    if (age < 14 || age > 120) {
        messageElement.innerHTML = '<span style="color:red;">만 14세 이상, 만 120세 이하만 가능합니다.</span>';
        return false;
    } else {
        messageElement.innerHTML = '<span style="color:green;">생년월일이 유효합니다.</span>';
        return true;
    }
}


function checkFormValidity() {
    var isIdValid = IDValidator();
    var isNameValid = NameValidator();
    var isPwValid = pwValidator();
    var isDomainValid = domainValidator();
    var isPhoneValid = phoneValidator();
    var isBirthdayValid = birthdayValidator();
	console.log("체크 작동중");
	
    var submitButton = document.querySelector('#signUp_form input[type="submit"]');
    if (isIdValid && isNameValid && isPwValid && isDomainValid && isPhoneValid && isBirthdayValid) {
        submitButton.disabled = false; // 모든 조건을 통과하면 활성화
        return true;
    } else {
        submitButton.disabled = true; // 하나라도 실패하면 비활성화
        return false;
    }
}


