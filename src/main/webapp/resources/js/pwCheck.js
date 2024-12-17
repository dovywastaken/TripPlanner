let cpw;
let pw1;
let pw2;
let submitButton

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


function pwCheck(value) //현재 비밀번호 확인
{
    $.ajax({
        url: contextPath + '/members/checkCurrentId',
        method: 'GET',
        data: {value: value},
        success: function(response) 
		{
			console.log("컨트롤러에서 받아온 비밀번호는" + response.pw);
			let cpw = document.getElementById("pw").value.trim();
			console.log("폼에서 받아온 비밀번호는" + cpw);
			let message = document.getElementById("currentPwMessage");
            if (response.pw === cpw) 
			{
                message.innerHTML = '<span style="color:green;">비밀번호가 확인됐습니다.</span>';
                return true;
            } else if(!pw.value)
			{
				message.innerHTML = "";
				return false;
			}
			else 
			{
				message.innerHTML = '<span style="color:red;">비밀번호가 틀렸습니다.</span>';
                return false;
            }
        },
        error: function() 
		{
            console.error(' 비밀번호 확인 중 오류 발생');
        }
    });
}

function pwValidator() { //비밀번호 유효성 체크
    pw1 = document.getElementById("pw1").value;
    const pwValidationMessage = document.getElementById('pwValidationMessage');
    const pwPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[A-Za-z\d!@#$%^&*()_+]{10,}$/;

    pwValidationMessage.innerHTML = '';
    
    if (pw1 === '') {    
        pwValidationMessage.innerHTML = '';
        return false;
    }
    
    if (!pwPattern.test(pw1)) {
        pwValidationMessage.innerHTML = '<span style="color:red;">비밀번호는 대문자, 소문자, 숫자가 포함되어야 하며 최소 10글자 이상이어야 합니다.</span>';
        return false;
    } else if (pwPattern.test(pw1)) {
        pwValidationMessage.innerHTML = '<span style="color:green;">비밀번호가 유효합니다.</span>';
        return true;
    }
}


function passwordCheck() {
    pw1 = document.getElementById("pw1").value;
    pw2 = document.getElementById("pw2").value;
    let pwCheck = document.getElementById("pwCheck");

    if (pw1 !== pw2) {
        pwCheck.innerHTML = '<span style="color:red;">비밀번호가 서로 일치하지 않습니다.</span>';
        return false;
    } else {
        pwCheck.innerHTML = "";
        document.getElementById("memPassword").value = pw1;
        return true;
    }
}
function submit(event)
{
	if(passwordCheck == true && pwValidator == true && pwCheck == true)
	{
		
		
		return true;
	}
};


