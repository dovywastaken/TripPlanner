let checkedEmail = '';
let emailField = document.getElementById("email");
let nameField = document.getElementById("name");
let pwField = document.getElementById("pw1");
let pwCheck = document.getElementById("pw2");
let phoneField = document.getElementById("phone1");
let birthdayField = document.getElementById("birthday");
let submitButton = document.getElementById("signUp_form");
let confirmIdButton; //이메일 중복체크 버튼 //쿼리셀렉터로 클래스로 잡아서 써. checkButton뭐 이런걸로

document.addEventListener('DOMContentLoaded', function() {
	//이메일 이름 비밀번호 이메일 휴대폰 생년월일 제출버튼 문서 로딩 시 매핑
    emailField = document.getElementById("email");
    nameField = document.getElementById("name");
    pwField = document.getElementById("pw1");
	pwCheck = document.getElementById("pw2");
    phoneField = document.getElementById("phone1");
    birthdayField = document.getElementById("birthday");
    submitButton = document.getElementById("signUp_form");
	confirmIdButton = document.querySelector(".checkButton");
	
    // 이메일, 이름, 비밀번호, 이메일도메인, 휴대폰번호, 생년월일, 제출버튼 이벤트리스너
    emailField.addEventListener('input', emailChecknValidation);
    nameField.addEventListener('input', nameValidator);
    pwField.addEventListener('input', pwValidator);
    phoneField.addEventListener('input', phoneValidator);
    birthdayField.addEventListener('input', birthdayValidator);
    submitButton.addEventListener('submit', function(event)
	{
		submit(event);
	});
	confirmIdButton.addEventListener("click", function() 
	{
        emailCheck(emailField.value.trim());
    });
	pwCheck.addEventListener("input", passwordCheck);
		
    confirmIdButton.disabled = true; //아직 이메일 입력 안했으므로 중복체크 버튼 비활성화로 시작
});





function emailChecknValidation()
{
	confirmIdButton.disabled = true; // 중복 체크 버튼 비활성화
    let messageElement = document.getElementById('message_id');
    messageElement.innerHTML = ""; //메세지 초기화
    let resultMessage = document.getElementById("resultMessage");
    resultMessage.innerHTML = '';
	
   	emailValidator(); //이메일 유효성 검사
}

//이메일 중복검사 하는 함수
function emailCheck(value) {
    $.ajax({
        url: contextPath + '/members/idCheckDuplicate',
        method: 'GET',
        data: {value: value},
        success: function(response) {
			console.log("컨트롤러를 지나 다시 ajax코드로 들어왔음");
            let messageElement = document.getElementById('message_id');
            let resultMessage = document.getElementById("resultMessage");
			confirmIdButton = document.querySelector(".checkButton");
            if (!response.notAvailable) //available 이라면
			{
				console.log("if문 안이요");
				
                messageElement.innerHTML = '<span style="color:green; margin-left: 6px;">사용 가능한 이메일입니다.</span>';
                resultMessage.innerHTML = '';
				confirmIdButton.id="buttonConfirmed";
				checkedEmail = value;
				console.log(checkedEmail);
                alert("사용 가능한 이메일 입니다");
				
                return true;
            } 
			else
			{
				console.log("else문 안이요");
                messageElement.innerHTML = '<span style="color:red; margin-left: 6px;">이미 사용 중인 이메일 입니다.</span>';
                resultMessage.innerHTML = '이메일 입력란을 다시 확인해주세요!';
                resultMessage.style.color = "red";
				checkedEmail = value;
				console.log('');
                alert("이미 사용 중인 이메일 입니다");
                return false;
			}
        },
        error: function() {
            console.error('이메일 중복 확인 중 오류 발생');
			checkedEmail = ''
        }
    });
}

function emailValidator() // 이메일 유효성 검사 함수
{
    const emailValue = document.getElementById("email").value.trim(); // 이메일 값 가져오기
    const emailPattern = /^[a-z0-9._-]{3,}@[a-z0-9.-]+\.[a-z]{2,}$/;
    
    let messageElement = document.getElementById('message_id'); 
    confirmIdButton = document.querySelector(".checkButton"); //이메일 중복체크 이벤트 매핑
    let resultMessage = document.getElementById("resultMessage");
    
    if (emailValue === '') {
		console.log("입력값 없음 ");
        messageElement.innerHTML = '';
        confirmIdButton.disabled = true;
		confirmIdButton.id = "buttonDisabled";
        resultMessage.innerHTML = '';
        return false;
    }

    if (!emailPattern.test(emailValue)) {
		console.log("조건식 통과 실패");
        messageElement.innerHTML = '<span style="color:red; margin-left: 6px;">이메일 형식을 확인해주세요!</span>';
		confirmIdButton.disabled = true;
		confirmIdButton.id = "buttonDisabled";
        resultMessage.innerHTML = '<span style="color:red;">회원 가입란을 다시 확인 부탁드립니다!</span>';
        return false;
    }

    messageElement.innerHTML = '<span></span>';
    confirmIdButton.disabled = false;
    resultMessage.innerHTML = '';
	confirmIdButton.id = "buttonActive";
	
    return true;
}

function nameValidator() 
{
    const nameValue = document.getElementById("name").value.trim();
    let messageElement = document.getElementById('message_name'); 
    let resultMessage = document.getElementById("resultMessage");

    if (nameValue.length == 1) 
	{
        messageElement.innerHTML = '<span style="color:red;">닉네임은 2글자 이상</span>';
        resultMessage.innerHTML = '<span style="color:red;">회원 가입란을 다시 확인 부탁드립니다!</span>';
        return false;
    }
	else if (nameValue.length == 0) {
        messageElement.innerHTML = '';
        resultMessage.innerHTML = '';
        return false;
    }
	else if(nameValue.trim().length >= 8)
	{
		console.log("Nickname too long");
		messageElement.innerHTML = '<span style="color:red;">최대 글자수는 7자 입니다</span>';
        resultMessage.innerHTML = '<span style="color:red;">회원 가입란을 다시 확인 부탁드립니다!</span>';
		return false;
	}
 	else 
	{
        messageElement.innerHTML = '';
        resultMessage.innerHTML = '';
        return true;
    }
}

function pwValidator() {
    const pw = document.getElementById("pw1").value;
    const pwValidationMessage = document.getElementById('pwValidationMessage');
    const pwPattern = /^(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d!@#$%^&*()_+]{10,}$/;

    pwValidationMessage.innerHTML = '';
    
    if (pw === '') {    
        pwValidationMessage.innerHTML = '';
        return false;
    }
    
    if (!pwPattern.test(pw)) {
        pwValidationMessage.innerHTML = '<span style="color:red;">알파벳, 숫자 그리고 특수문자가 포함되어야 하며 최소 10글자 이상이어야 합니다.</span>';
        return false;
    } else if (pwPattern.test(pw)) {
        pwValidationMessage.innerHTML = '<span style="color:green;">비밀번호가 유효합니다.</span>';
        return true;
    }
}

function phoneValidator() {
    const phone = document.getElementById("phone1").value;
    const phonePattern = /^\d{11}$/;
    let messageElement = document.getElementById('message_phone');
	if(phone.length == 11)
	{
	    if (!phonePattern.test(phone)) 
		{
	        messageElement.innerHTML = '<span style="color:red;">전화번호는 01012345678 형식이어야 합니다.</span>';
	        console.log("유효성 검사 안됨");
			return false;
	    }
		else
		{
			console.log("유효성 통과");
	        messageElement.innerHTML = '<span style="color:green;">전화번호가 유효합니다.</span>';
	        return true;
		}
	}
	else if(phone.length > 11)
	{
		console.log("11자 초과");
		messageElement.innerHTML = '<span style="color:red;">01012345678의 형태여야해요!</span>';
		return false;
	}
	else
	{
		console.log("11자가 아님");
		messageElement.innerHTML = ' ';
		return false;
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
    const isEmailValid = emailValidator();
    const isNameValid = nameValidator();
    const isPwValid = pwValidator();
    const isPhoneValid = phoneValidator();
    const isBirthdayValid = birthdayValidator();

    if (isEmailValid && isNameValid && isPwValid && isPhoneValid && isBirthdayValid) {
        return true;
    } else {
        return false;
    }
}

function passwordCheck() { //입력한 비밀번호 2개가 모두 일치하는지 확인하는 함수
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

function submit(event)
{
	if(checkedEmail !== emailField.value.trim()) //존재 확인된 id와 입력창에 입력한 이메일가 다르다면
		{
			console.log("이메일 중복 검사 통과못함");
			event.preventDefault();
			alert("이메일 중복검사를 다시 해주세요!");
			return;	
		}
	//회원가입 제출버튼 유효성 검사에 따라 분기점 주기
    if (!checkFormValidity()) { //유효성 검사가 하나라도 통과 안되면
		console.log("유효성검사 통과 못함");
        event.preventDefault(); //제출 막기
        alert("작성 다시 하세요");
        return;
    }
    if (!passwordCheck()) {
		console.log("새로운 비밀번호 확인 통과 못함");
        event.preventDefault();
        return;
    }
    alert("회원 가입 완료");// 유효성 검사 모두 통과하면 얼러트
}

