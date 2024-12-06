document.addEventListener('DOMContentLoaded', function () {
    const emailSelect = document.getElementById("emailSelect");
    emailSelect.value = "custom";
    updateDomainInput(emailSelect);
});

function updateDomainInput(selectElement) {
    const domainInput = document.getElementById("emailDomain");
    if (selectElement.value === "custom") {
        domainInput.readOnly = false;
        domainInput.value = "";
        domainInput.placeholder = "직접 입력";
    } else {
        domainInput.readOnly = true;
        domainInput.value = selectElement.value;
        domainInput.placeholder = "sample.com";
    }
}

function combineEmail(event) {
    const emailId = document.getElementById("emailId").value.trim();
    const emailDomain = document.getElementById("emailDomain").value.trim();
    const emailField = document.getElementById("email");

    if (emailId && emailDomain) {
        emailField.value = `${emailId}@${emailDomain}`;
        console.log("결합된 이메일:", emailField.value); // 로그로 결합 값 확인
    } else {
        alert("이메일을 정확히 입력하세요.");
        event.preventDefault(); // 폼 전송 중단
    }
}

$(function(){
	let checkId = 0;
	
})

$('#confirmId').click(function(){
       if($('#id').val().trim()==''){
           $('#message_id').css('color','#fba082').text('아이디를 입력하세요');
           $('#id').val('').focus();
           return;
       }