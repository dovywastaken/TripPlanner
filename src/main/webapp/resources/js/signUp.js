document.addEventListener('DOMContentLoaded', initForm);
var isDuplicateCheck = 0;


function initForm() 
{
    const emailSelect = document.getElementById("emailSelect");
    emailSelect.value = "custom";
    updateDomainInput(emailSelect); //이메일 직접 입력시 입력창 활성화 시켜주는 코드
	
	var submitButton = document.querySelector('#signUp_form input[type="submit"]');
	submitButton.distabled = true;
	
    const confirmIdButton = document.getElementById("confirmId"); //아이디 중복체크 이벤트 매핑
	confirmIdButton.disabled = true;
    confirmIdButton.addEventListener("click", function() 
		{
	        const idField = document.getElementById("id");
	        idCheck("id", idField.value);
	    }
	);

    // 폼 제출 이벤트 리스너 추가
	const signUpForm = document.getElementById("signUp_form");
	    signUpForm.addEventListener("submit", function(event) {
	        if (!combineEmail(event)) {
	            event.preventDefault(); // 폼 제출 중단
	        }
	    });

}

function handleFormSubmit(event) 
{
	if(isDuplicateCheck === 0)
	{
		//alert("아이디 중복 체크를 해주세요!");
		event.preventDefault(); // 제출 방지
	}
}


function updateDomainInput(selectElement) 
{
    const domainInput = document.getElementById("emailDomain");
    if (selectElement.value === "custom") 
	{
        domainInput.readOnly = false;
        domainInput.value = "";
        domainInput.placeholder = "직접 입력";
    } 
	else 
	{
        domainInput.readOnly = true;
        domainInput.value = selectElement.value;
    }
}

//이메일 아이디랑 도메인이랑 합쳐주는 함수
function combineEmail() {
    const emailId = document.getElementById("emailId").value.trim();
    const emailDomain = document.getElementById("emailDomain").value.trim();
    const emailField = document.getElementById("email");

    if (emailId && emailDomain) {
        emailField.value = emailId + "@" + emailDomain;
        console.log("결합된 이메일:", emailField.value);
        
    } else {
        //alert("이메일을 정확히 입력하세요.");
        return false; // 폼 제출 중단
    }
}

// 비밀번호 일치 확인 함수
function passwordCheck() 
{
    var pw1 = document.getElementById("pw1").value;
    var pw2 = document.getElementById("pw2").value;
    var passMessage = document.getElementById("passMessage");
	var resultMessage = document.getElementById("resultMessage");

	if (pw1 === "" || pw2 === "") 
	{
        passMessage.innerHTML = ""; // 메시지 숨김
        resultMessage.innerHTML = "";
		
        return false;
	}
    if (pw1 !== pw2) 
	{
        passMessage.innerHTML = "비밀번호가 서로 일치하지 않습니다.";
        passMessage.style.color = "red";
		resultMessage.innerHTML = "비밀번호를 다시 확인 해주세요";
		resultMessage.style.color = "red";
		
        return false; // 폼 제출 방지
    } 
	if(pw1 == pw2)
	{
        passMessage.innerHTML = "";
        document.getElementById("memPassword").value = pw1;
		resultMessage.innerHTML = "";
		
        return true; // 폼 제출 허용
    }
}


// 중복 체크 함수
function idCheck(field, value) 
{
    $.ajax(
		{
        url: contextPath + '/members/checkDuplicate',
        method: 'GET',
        data: { field: field, value: value},
        success: function(response) 
		{
			var messageElement = document.getElementById('message_' + field);
			//var submitButton = document.querySelector('#signUp_form input[type="submit"]');
			var resultMessage = document.getElementById("resultMessage");
			
            if (response.available) 
			{
                messageElement.innerHTML = '<span style="color:green;">사용 가능한 ' + field + '입니다.</span>';
				resultMessage.innerHTML = '';
				alert("사용 가능한 ID 입니다");
                //submitButton.disabled = false; // 제출 버튼 활성화
				isDuplicateCheck = 1;
				
				return true;
            } 
			else 
			{
                messageElement.innerHTML = '<span style="color:red;">이미 사용 중인 ' + field + '입니다.</span>';
				resultMessage.innerHTML = field + '아이디 입력란을 다시 확인해주세요!';
				resultMessage.style.color = "red";
				alert("이미 사용 중인 ID 입니다");
                //submitButton.disabled = true; // 제출 버튼 비활성화
				
				return false;
            }
        },
        error: function() 
		{
            console.error(field + ' 중복 확인 중 오류 발생');
        }
    });
}






