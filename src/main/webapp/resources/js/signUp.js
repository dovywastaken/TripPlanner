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
    
    submitButton.addEventListener('submit', function(event) {
        if (!checkFormValidity()) {
            event.preventDefault();
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
        
        alert("회원 가입 완료");
    });

    testChamber.addEventListener('click', function() {
        console.log(checkFormValidity);
        checkFormValidity();
    });

    const emailSelect = document.getElementById("emailSelect");
    emailSelect.value = "custom";
    updateDomainInput(emailSelect); //이메일 직접 입력시 입력창 활성화 시켜주는 코드
    
    const confirmIdButton = document.getElementById("confirmId");
    confirmIdButton.disabled = true;
    confirmIdButton.addEventListener("click", function() {
        const idField = document.getElementById("id");
        idCheck("id", idField.value);
    });

    var pwCheck = document.getElementById("pw2");
    pwCheck.addEventListener("input", passwordCheck);
});

// 유효성 검사 함수
function IDValidator() {
    var idValue = document.getElementById("id").value.trim(); // 아이디 값 가져오기
    var idPattern = /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z\d]{5,15}$/; // 정규식: 글자+숫자, 5~15글자
    
    var messageElement = document.getElementById('message_id'); 
    const confirmIdButton = document.getElementById("confirmId"); //아이디 중복체크 이벤트 매핑
    var resultMessage = document.getElementById("resultMessage");
    
    if (idValue === '') {
        messageElement.innerHTML = '';
        confirmIdButton.disabled = true;
        resultMessage.innerHTML = '';
        return false;
    }

    if (/[^a-zA-Z0-9]/.test(idValue)) {
        messageElement.innerHTML = '<span style="color:red;">아이디는 알파벳과 숫자만 포함해야 합니다.</span>';
        confirmIdButton.disabled = true;
        resultMessage.innerHTML = '<span style="color:red;">회원 가입란을 다시 확인 부탁드립니다!</span>';
        return false;
    }

    if (!idPattern.test(idValue)) {
        messageElement.innerHTML = '<span style="color:red;">아이디는 최소 5글자 이상, 최대 15글자 이하로, 알파벳과 숫자를 포함해야 합니다.</span>';
        confirmIdButton.disabled = true;
        resultMessage.innerHTML = '<span style="color:red;">회원 가입란을 다시 확인 부탁드립니다!</span>';
        return false;
    }

    messageElement.innerHTML = '<span style="color:green;">아이디가 유효합니다</span>';
    confirmIdButton.disabled = false;
    resultMessage.innerHTML = '';
    
    return true;
}

function NameValidator() {
    var nameValue = document.getElementById("name").value.trim();
    var messageElement = document.getElementById('message_name'); 
    var resultMessage = document.getElementById("resultMessage");

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
    var emailDomain = document.getElementById("emailDomain").value;
    var domainPattern = /^[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

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
    var phonePattern = /^\d{3}-\d{4}-\d{4}$/;

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

    if (!birthday) {
        var messageElement = document.getElementById('message_birthday');
        messageElement.innerHTML = '<span style="color:red;">생년월일을 입력해주세요.</span>';
        return false;
    }

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

    if (isIdValid && isNameValid && isPwValid && isDomainValid && isPhoneValid && isBirthdayValid) {
        return true;
    } else {
        return false;
    }
}

function updateDomainInput(selectElement) {
    const domainInput = document.getElementById("emailDomain");
    var messageElement = document.getElementById('message_email');
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
    var pw1 = document.getElementById("pw1").value;
    var pw2 = document.getElementById("pw2").value;
    var pwCheck = document.getElementById("pwCheck");
    var resultMessage = document.getElementById("resultMessage");

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

function idCheck(field, value) {
    $.ajax({
        url: contextPath + '/members/checkDuplicate',
        method: 'GET',
        data: { field: field, value: value },
        success: function(response) {
            var messageElement = document.getElementById('message_' + field);
            var resultMessage = document.getElementById("resultMessage");
            
            if (response.available) {
                messageElement.innerHTML = '<span style="color:green;">사용 가능한 ' + field + '입니다.</span>';
                resultMessage.innerHTML = '';
                alert("사용 가능한 ID 입니다");
                isDuplicateCheck = 1;
                return true;
            } else {
                messageElement.innerHTML = '<span style="color:red;">이미 사용 중인 ' + field + '입니다.</span>';
                resultMessage.innerHTML = field + '아이디 입력란을 다시 확인해주세요!';
                resultMessage.style.color = "red";
                alert("이미 사용 중인 ID 입니다");
                return false;
            }
        },
        error: function() {
            console.error(field + ' 중복 확인 중 오류 발생');
        }
    });
}
