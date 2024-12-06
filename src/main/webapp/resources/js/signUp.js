document.addEventListener('DOMContentLoaded', function() {
    const emailSelect = document.getElementById("emailSelect");
    emailSelect.value = "custom";
    updateDomainInput(emailSelect);

    const confirmIdButton = document.getElementById("confirmId");
    confirmIdButton.addEventListener("click", function() {
        const idField = document.getElementById("id");
        checkDuplicate("id", idField.value);
    });
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

// 중복 체크 함수
function checkDuplicate(field, value) {
    $.ajax({
        url: contextPath + '/members/checkDuplicate',
        type: 'GET',
        data: { field: field, value: value },
        success: function(response) {
            const messageElement = document.getElementById(`message_${field}`);
            if (response.available) {
                messageElement.innerHTML = `<span style="color:green;">사용 가능한 ${field}입니다.</span>`;
            } else {
                messageElement.innerHTML = `<span style="color:red;">이미 사용 중인 ${field}입니다.</span>`;
            }
        },
        error: function() {
            console.error(`${field} 중복 확인 중 오류 발생`);
        }
    });
}
