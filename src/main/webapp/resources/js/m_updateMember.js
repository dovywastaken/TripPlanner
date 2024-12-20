document.addEventListener('DOMContentLoaded', function () {
    // 필요한 요소들 가져오기
    var emailId = document.getElementById("emailId");
    var emailDomain = document.getElementById("emailDomain");
    var emailSelect = document.getElementById("emailSelect");
    var messageBox = document.getElementById("message_email_warning");
    var phone = document.getElementById("phone");

    // 이메일 ID에 포커스되면 메시지 보여주기
    emailId.addEventListener('focus', function () {
        messageBox.textContent = "이메일을 변경하면 다시 이메일 인증이 필요합니다!";
        messageBox.style.color = "blue";
    });

    // 이메일 도메인에 포커스되면 메시지 보여주기
    emailDomain.addEventListener('focus', function () {
        messageBox.textContent = "이메일을 변경하면 다시 이메일 인증이 필요합니다!";
        messageBox.style.color = "blue";
    });

    // 포커스가 없어지면 메시지 지우기
    emailId.addEventListener('blur', function () {
        messageBox.textContent = "";
    });

    emailDomain.addEventListener('blur', function () {
        messageBox.textContent = "";
    });

    // 이메일 입력할 때마다 체크하기
    emailId.addEventListener('input', function () {
        emailValidator();
    });

    emailDomain.addEventListener('input', function () {
        emailValidator();
    });

    // 이메일 선택할 때 메시지 보여주기
    emailSelect.addEventListener('change', function () {
        messageBox.textContent = "이메일을 변경하면 다시 이메일 인증이 필요합니다!";
        messageBox.style.color = "blue";
    });

    // 전화번호 입력할 때마다 체크하기
    phone.addEventListener('input', function () {
        phoneValidator();
    });
});

function emailValidator() {
    let emailId = document.getElementById("emailId").value;
    let emailDomain = document.getElementById("emailDomain").value;

    // 이메일 ID 검사 (영문자와 숫자의 조합, 최소 5글자 이상)
    let emailIdPattern = /^(?=.*[a-z])(?=.*\d)[a-z\d]{5,}$/;
    // 이메일 도메인 검사 (일반적인 도메인 형식)
    let domainPattern = /^[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

    var messageElement = document.getElementById('message_email');

    // 이메일 ID 검사
    if (emailId === '') {
        messageElement.innerHTML = '<span style="color:red;">이메일 ID를 입력하세요.</span>';
        return false;
    }

    // 이메일 ID가 영문자와 숫자만 포함하고 최소 5글자 이상이어야 하며, 영문자와 숫자 조합이어야 합니다.
    if (!emailIdPattern.test(emailId)) {
        messageElement.innerHTML = '<span style="color:red;">이메일 ID는 최소 5글자 이상, 영문자와 숫자의 조합이어야 합니다.</span>';
        return false;
    }

    // 이메일 도메인 검사
    if (emailDomain === '') {
        messageElement.innerHTML = '<span style="color:red;">이메일 도메인을 입력하세요.</span>';
        return false;
    }

    if (!domainPattern.test(emailDomain)) {
        messageElement.innerHTML = '<span style="color:red;">유효한 이메일 도메인 형식이 아닙니다.</span>';
        return false;
    }

    // 이메일 ID와 도메인 모두 유효한 경우
    messageElement.innerHTML = '<span style="color:green;">이메일이 유효합니다.</span>';
    return true;
}

function phoneValidator() {
    let phone = document.getElementById("phone").value;
    let phonePattern = /^\d{3}-\d{4}-\d{4}$/;

    var messageElement = document.getElementById('message_phone');
	if(phone === '')
	{
		messageElement.innerHTML = '<span style="color:red;">전화번호를 비울순 없습니다!</span>';
        return false;		
	}
	
    if (!phonePattern.test(phone)) {
        messageElement.innerHTML = '<span style="color:red;">전화번호는 010-1234-5678 형식이어야 합니다.</span>';
        return false;
    } else {
        messageElement.innerHTML = '<span style="color:green;">전화번호가 유효합니다.</span>';
        return true;
    }
}