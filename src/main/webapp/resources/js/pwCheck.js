	let currentPw = '<%= member.getPw() %>';
    let pwChangeForm = document.getElementById("pwChangeForm");
    let currentPwMessage = document.getElementById("currentPwMessage");
    let count = 0;
    
    
    function check(event)
    {
    	count = 0;
    	let ckCurrentPw = document.getElementById("pw").value;
    	if(ckCurrentPw != null && ckCurrentPw === currentPw)
    	{
    		currentPwMessage.innerHTML = "일치합니다";
    		count += 1;
    	}else
    	{
    		currentPwMessage.innerHTML = "일치하지 않습니다";
    	}
    	passwordCheck();
    	if(count === 2 && pwValidator())
    	{
    		pwChangeForm.submit();
    	}else
    	{
    		event.preventDefault();
    	}
    }
    
    function passwordCheck() {
        var pw1 = document.getElementById("pw1").value; //새 비밀번호
        var pw2 = document.getElementById("pw2").value; //새 비밀번호 확인
        var pwCheck = document.getElementById("pwCheck"); //메시지
        var resultMessage = document.getElementById("resultMessage");

        if (pw1 !== pw2) {
            pwCheck.innerHTML = '<span style="color:red;">비밀번호가 서로 일치하지 않습니다.</span>';
            resultMessage.innerHTML = '<span style="color:red;">비밀번호를 다시 확인 해주세요</span>';
        } else {
            pwCheck.innerHTML = "";
            document.getElementById("memPassword").value = pw1;
            resultMessage.innerHTML = "";
            count += 1;
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