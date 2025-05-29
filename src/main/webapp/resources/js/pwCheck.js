let cpw;
let pw1;
let pw2;
let submitButton;
let pwcheck = 0;

document.addEventListener('DOMContentLoaded', function() {
    cpw = document.getElementById("pw");
	pw1 = document.getElementById("pw1");
	pw2 = document.getElementById("pw2");
	submitButton = document.getElementById("pwChange");
	
    cpw.addEventListener('blur', function() 
	{
        pwCheck(cpw.value.trim());
    });
	pw1.addEventListener('blur', function()
	{
		pwValidator();
	});
	pw2.addEventListener('blur', function()
	{
		passwordCheck();
	});
	submitButton.addEventListener('click', function()
	{
		submit(event);
	});
});


function pwCheck(value) 
{
    $.ajax({
        url: contextPath + '/members/checkCurrentPw',  // 올바른 URL인지 확인
        method: 'GET',
        data: { value: value },  // 'value' 파라미터가 제대로 전달되는지 확인
        success: function(response) {
            console.log("컨트롤러에서 받아온 비밀번호는", response.pw);
            let cpw = document.getElementById("pw").value.trim();
            console.log("폼에서 받아온 비밀번호는", cpw);
            let message = document.getElementById("currentPwMessage");
            if (response.true) {
                message.innerHTML = '<span style="color:green;">비밀번호가 확인됐습니다.</span>';
				pwcheck = 1;
                return true;
            } else if (response.none) {
                message.innerHTML = ""
                return false;
            } else {
                message.innerHTML = '<span style="color:red;">비밀번호가 틀렸습니다.</span>';
                return false;
            }
        },
        error: function(error) {
            console.error('비밀번호 확인 중 오류 발생:', error);
        }
    });
}

function pwValidator() { //비밀번호 유효성 체크
    pw1 = document.getElementById("pw1").value;
    const pwValidationMessage = document.getElementById('pwValidationMessage');
    const pwPattern = /^(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*()_+])[A-Za-z\d!@#$%^&*()_+]{10,}$/;

    pwValidationMessage.innerHTML = '';
    
    if (pw1 === '') {    
        pwValidationMessage.innerHTML = '';
        return false;
    }
    
    if (!pwPattern.test(pw1)) {
        pwValidationMessage.innerHTML = '<span style="color:red;">알파벳, 숫자 그리고 특수문자가 포함되어야 하며 최소 10글자 이상이어야 합니다.</span>';
        return false;
    } else if (pwPattern.test(pw1)) {
        pwValidationMessage.innerHTML = '<span style="color:green;">비밀번호가 유효합니다.</span>';
        return true;
    }
}


function passwordCheck() {
    let cpw = document.getElementById("pw").value; // 현재 비밀번호
    let pw1 = document.getElementById("pw1").value; // 새 비밀번호
    let pw2 = document.getElementById("pw2").value; // 새 비밀번호 확인
    let pwCheck = document.getElementById("pwCheck"); // 메시지 표시 div

    // 새 비밀번호와 비밀번호 확인이 일치하는지 체크
    if (pw1 !== pw2) {
        pwCheck.innerHTML = '<span style="color:red;">비밀번호가 서로 일치하지 않습니다.</span>';
        return false;
    }

    // 새 비밀번호와 현재 비밀번호가 동일한지 체크
    if (cpw === pw1) {
        pwCheck.innerHTML = '<span style="color:red;">현재 비밀번호와 새 비밀번호가 같습니다.</span>';
        return false;
    }

    // 비밀번호 일치 및 현재 비밀번호와의 중복 검사를 통과하면 비밀번호를 설정하고 메시지를 비웁니다.
    pwCheck.innerHTML = "";
    document.getElementById("memPassword").value = pw1;
    return true;
}


function submit(event)
{
	if(passwordCheck() == true && pwValidator() == true && pwcheck === 1)
	{
		return true;
	}else
	{
		console.log("bye");
		event.preventDefault();
	}
};


