let checkedId = '';
let idField;

document.addEventListener('DOMContentLoaded', function() {
	//아이디 이름 비밀번호 이메일 휴대폰 생년월일 제출버튼 문서 로딩 시 매핑
    idField = document.getElementById("id");
    let nameField = document.getElementById("name");
    let pwField = document.getElementById("pw1");
    let emailField = document.getElementById("emailDomain");
    let phoneField = document.getElementById("phone1");
    let birthdayField = document.getElementById("birthday");
    let submitButton = document.getElementById("signUp_form");
	let emailSelect = document.getElementById("emailSelect"); //이메일 직접입력 선택창
	let confirmIdButton = document.getElementById("confirmId"); //아이디 중복체크 버튼
	
    // 아이디, 이름, 비밀번호, 이메일도메인, 휴대폰번호, 생년월일, 제출버튼 이벤트리스너
    idField.addEventListener('input', idChecknValidation);
    nameField.addEventListener('input', NameValidator);
    pwField.addEventListener('input', pwValidator);
    emailField.addEventListener('input', domainValidator);
    phoneField.addEventListener('input', phoneValidator);
    birthdayField.addEventListener('input', birthdayValidator);
    submitButton.addEventListener('submit', submit);
	confirmIdButton.addEventListener("click", function() 
		{
	        idField = document.getElementById("id");
	        idCheck("id", idField.value);
	    });
		
		
    emailSelect.value = "custom"; //이메일 입력방식 초기값 custom으로 설정 //이거 안해주면 custom 해놔도 직접입력 placeholder만 보이고 실제론 입력 안 됨
    updateDomainInput(emailSelect); //이메일 직접 입력시 입력창 활성화 시켜주는 코드
    confirmIdButton.disabled = true; //아직 아이디 입력 안했으므로 중복체크 버튼 비활성화로 시작
	confirmIdButton.style.backgroundColor = "gray"; //중복체크 버튼 초기 스타일 값

    let pwCheck = document.getElementById("pw2");
    pwCheck.addEventListener("input", passwordCheck);
});


function submit(event)
{
	//회원가입 제출버튼 유효성 검사에 따라 분기점 주기
    if (!checkFormValidity()) { //유효성 검사가 하나라도 통과 안되면
        event.preventDefault(); //제출 막기
        alert("작성 다시 하세요");
        return;
    }
    if (!combineEmail()) {
        event.preventDefault();
        alert("이메일을 정확히 입력해주세요");
        return;
    }

    if (!passwordCheck()) {
        event.preventDefault();
        return;
    }
    
	if(checkedId !== idField.value.trim())
	{
		event.preventDefault();
		alert("아이디 중복검사를 다시 해주세요!");
		return;	
	}
	
    alert("회원 가입 완료");// 유효성 검사 모두 통과하면 얼러트
}



function idChecknValidation()
{
	let confirmIdButton = document.getElementById("confirmId");
	confirmIdButton.disabled = true; // 중복 체크 버튼 비활성화
    let messageElement = document.getElementById('message_id');
    messageElement.innerHTML = ""; //메세지 초기화
    let resultMessage = document.getElementById("resultMessage");
    resultMessage.innerHTML = '';
	
    IDValidator(); //아이디 유효성 검사
}

//아이디 중복검사 하는 함수
function idCheck(field, value) {
    $.ajax({
        url: contextPath + '/members/checkDuplicate',
        method: 'GET',
        data: { field: field, value: value },
        success: function(response) {
            let messageElement = document.getElementById('message_' + field);
            let resultMessage = document.getElementById("resultMessage");
            
            if (response.available) {
                messageElement.innerHTML = '<span style="color:green;">사용 가능한 ' + field + '입니다.</span>';
                resultMessage.innerHTML = '';
				checkedId = value;
				console.log(checkedId);
                alert("사용 가능한 ID 입니다");
				
                return true;
            } else {
                messageElement.innerHTML = '<span style="color:red;">이미 사용 중인 ' + field + '입니다.</span>';
                resultMessage.innerHTML = field + '아이디 입력란을 다시 확인해주세요!';
                resultMessage.style.color = "red";
				checkedId = value;
				console.log('');
                alert("이미 사용 중인 ID 입니다");
                return false;
            }
        },
        error: function() {
            console.error(field + ' 중복 확인 중 오류 발생');
			checkedId = ''
        }
    });
}


function IDValidator() // 아이디 유효성 검사 함수
{
    let idValue = document.getElementById("id").value.trim(); // 아이디 값 가져오기
    let idPattern = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{5,15}$/; // 정규식: 글자+숫자, 5~15글자
    
    let messageElement = document.getElementById('message_id'); 
    const confirmIdButton = document.getElementById("confirmId"); //아이디 중복체크 이벤트 매핑
    let resultMessage = document.getElementById("resultMessage");
    
    if (idValue === '') {
        messageElement.innerHTML = '';
        confirmIdButton.disabled = true;
		confirmIdButton.style.backgroundColor = "gray";
        resultMessage.innerHTML = '';
        return false;
    }

    if (/[^a-zA-Z0-9]/.test(idValue)) {
        messageElement.innerHTML = '<span style="color:red;">아이디는 알파벳과 숫자만 포함해야 합니다.</span>';
        confirmIdButton.disabled = true;
		confirmIdButton.style.backgroundColor = "gray";
        resultMessage.innerHTML = '<span style="color:red;">회원 가입란을 다시 확인 부탁드립니다!</span>';
        return false;
    }

    if (!idPattern.test(idValue)) {
        messageElement.innerHTML = '<span style="color:red;">아이디는 최소 5글자 이상, 최대 15글자 이하로, 알파벳과 숫자를 포함해야 합니다.</span>';
        confirmIdButton.disabled = true;
		confirmIdButton.style.backgroundColor = "gray";
        resultMessage.innerHTML = '<span style="color:red;">회원 가입란을 다시 확인 부탁드립니다!</span>';
        return false;
    }

    messageElement.innerHTML = '<span style="color:green;">아이디가 유효합니다</span>';
    confirmIdButton.disabled = false;
    resultMessage.innerHTML = '';
	confirmIdButton.style.backgroundColor = "#28a745";
	
    return true;
}

function NameValidator() {
    let nameValue = document.getElementById("name").value.trim();
    let messageElement = document.getElementById('message_name'); 
    let resultMessage = document.getElementById("resultMessage");

    if (nameValue.length == 1) {
        messageElement.innerHTML = '<span style="color:red;">이름은 2글자 이상</span>';
        resultMessage.innerHTML = '<span style="color:red;">회원 가입란을 다시 확인 부탁드립니다!</span>';
        return false;
    } else if (nameValue.length == 0) {
        messageElement.innerHTML = '';
        resultMessage.innerHTML = '';
        return false;
    } else {
        messageElement.innerHTML = '';
        resultMessage.innerHTML = '';
        return true;
    }
}

function pwValidator() {
    const pw = document.getElementById("pw1").value;
    const pwValidationMessage = document.getElementById('pwValidationMessage');
    const pwPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d!@#$%^&*()_+]{10,}$/;

    pwValidationMessage.innerHTML = '';
    
    if (pw === '') {    
        pwValidationMessage.innerHTML = '';
        return false;
    }
    
    if (!pwPattern.test(pw)) {
        pwValidationMessage.innerHTML = '<span style="color:red;">비밀번호는 대문자, 소문자, 숫자가 포함되어야 하며 최소 10글자 이상이어야 합니다.</span>';
        return false;
    } else if (pwPattern.test(pw)) {
        pwValidationMessage.innerHTML = '<span style="color:green;">비밀번호가 유효합니다.</span>';
        return true;
    }
}

function domainValidator() {
    let emailDomain = document.getElementById("emailDomain").value;
    let domainPattern = /^[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

    var messageElement = document.getElementById('message_email');
	if(emailDomain === ''){
			messageElement.innerHTML = '';
			return false;
	}
    if (!domainPattern.test(emailDomain)) {
        messageElement.innerHTML = '<span style="color:red;">유효한 이메일 도메인 형식이 아닙니다.</span>';
        return false;
    } else {
        messageElement.innerHTML = '<span style="color:green;">이메일 도메인이 유효합니다.</span>';
        return true;
    }
	
	
}

function phoneValidator() {
    let phone = document.getElementById("phone1").value;
    let phonePattern = /^\d{3}-\d{4}-\d{4}$/;

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
    let input = document.getElementById("birthday").value;
	let messageElement = document.getElementById('message_birthday');
	
	let year = parseInt(input.substring(0,4),10);
	let month = parseInt(input.substring(4,6),10);
	let day = parseInt(input.substring(6,8),10);
	let birthdate = new Date(year,month,day);
	let age = calculateAge(birthdate);
	
    if (!input) //아무것도 입력 안했을 때
	{
        messageElement.innerHTML = '<span style="color:red;">생년월일을 입력해주세요.</span>';
        return false;
    }

	if (!/^\d{8}$/.test(input)) //8자리가 아닐 때
	{
		 messageElement.innerHTML = '<span style="color:red;">유효한 생년월일 형식(YYYYMMDD)을 입력해주세요.</span>';
		 return false;
	}

    if (age <= 14 || age >= 120) { //14세에서 120세 사이가 아닐 때
        messageElement.innerHTML = '<span style="color:red;">만 14세 이상, 만 120세 이하만 가능합니다.</span>';
        return false;
    }else if(month < 0 || month > 12) //월을 음수나 13 이상으로 넣었을 때
	{
		messageElement.innerHTML = '<span style="color:red;">유효한 생년월일을 입력해주세요.</span>';
		return false;	
	} 
	else if(day < 1 || day > 31) //일을 음수나 32 이상으로 넣었을 때
	{
		messageElement.innerHTML = '<span style="color:red;">유효한 생년월일을 입력해주세요.</span>';
		return false;	
	}
	else {
        messageElement.innerHTML = '<span style="color:green;">생년월일이 유효합니다!</span>';
        return true;
    }
}

function calculateAge(birthdate) //만나이 구하는 함수
{
    const today = new Date();
    let age = today.getFullYear() - birthdate.getFullYear();
    const monthDiff = today.getMonth() - birthdate.getMonth();

    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthdate.getDate())) //생월이 아직 안지났거나 월은 같은데 일이 아직 안지난 경우
	{
        age--; // 나이에서 1을 빼서 만나이를 구한다
    }

    return age;
}


function checkFormValidity() { //유효성 모두 통과하면 true 반환하는 함수
    const isIdValid = IDValidator();
    const isNameValid = NameValidator();
    const isPwValid = pwValidator();
    const isDomainValid = domainValidator();
    const isPhoneValid = phoneValidator();
    const isBirthdayValid = birthdayValidator();

    if (isIdValid && isNameValid && isPwValid && isDomainValid && isPhoneValid && isBirthdayValid) {
        return true;
    } else {
        return false;
    }
}

function updateDomainInput(selectElement) {
    const domainInput = document.getElementById("emailDomain");
    const messageElement = document.getElementById('message_email');
    if (selectElement.value === "custom") {
        domainInput.readOnly = false;
        domainInput.value = "";
        domainInput.placeholder = "직접 입력";
    } else {
        messageElement.innerHTML = '';
        domainInput.readOnly = true;
        domainInput.value = selectElement.value;
    }
}

function combineEmail() {
    const emailId = document.getElementById("emailId").value.trim();
    const emailDomain = document.getElementById("emailDomain").value.trim();
    const emailField = document.getElementById("email");

    if (emailId && emailDomain) {
        emailField.value = emailId + "@" + emailDomain;
        return true;
    } else {
        return false; // 폼 제출 중단
    }
}

function passwordCheck() {
    let pw1 = document.getElementById("pw1").value;
    let pw2 = document.getElementById("pw2").value;
    let pwCheck = document.getElementById("pwCheck");
    let resultMessage = document.getElementById("resultMessage");

    if (pw1 !== pw2) {
        pwCheck.innerHTML = '<span style="color:red;">비밀번호가 서로 일치하지 않습니다.</span>';
        resultMessage.innerHTML = '<span style="color:red;">비밀번호를 다시 확인 해주세요</span>';
        return false;
    } else {
        pwCheck.innerHTML = "";
        document.getElementById("memPassword").value = pw1;
        resultMessage.innerHTML = "";
        return true;
    }
}

